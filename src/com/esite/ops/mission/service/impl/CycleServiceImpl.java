package com.esite.ops.mission.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import com.esite.framework.security.entity.Customer;
import com.esite.ops.mission.dao.CycleDao;
import com.esite.ops.mission.entity.CycleEntity;
import com.esite.ops.mission.entity.NoticeEntity;
import com.esite.ops.mission.service.ICycleService;
import com.esite.ops.mission.service.INoticeService;

public class CycleServiceImpl implements ICycleService {

	@Autowired
	private CycleDao cycleDao;
	
	@Autowired
	private INoticeService noticeService;
	
	@Override
	public Page<CycleEntity> getCycle(Pageable instance) {
		return cycleDao.findAll(instance);
	}

	@Override
	public void change(CycleEntity cycleEntity, Customer customer) {
		CycleEntity dbCycleEntity = this.getCycle(cycleEntity.getId());
		cycleEntity.setSubmitIpAddress(customer.getIp());
		cycleEntity.setSubmitUserId(customer.getUser().getId());
		//验证周期的时间是否有重复或重叠
		if(null == cycleEntity.getCycleBegin()) {
			throw new IllegalArgumentException("周期变更中,起始时间不能为空.");
		}
		if(null == cycleEntity.getCycleEnd()) {
			throw new IllegalArgumentException("周期变更中,结束时间不能为空.");
		}
		if(cycleEntity.getCycleBegin().compareTo(cycleEntity.getCycleEnd()) >= 0) {
			throw new IllegalArgumentException("周期变更中,起始时间必须要小于结束时间.");
		}
		if(dbCycleEntity.getCycleBegin().getTime() == cycleEntity.getCycleBegin().getTime() && dbCycleEntity.getCycleEnd().getTime() == cycleEntity.getCycleEnd().getTime()) {
			throw new IllegalArgumentException("周期变更中,没有任何变更,请重新设置.");
		}
		Map<String,Object> map = this.cycleDao.findMaxEndCycleAndIdNot(cycleEntity.getId());
		if(null != map.get("MAX_END_CYCLE")) {
			String maxEndCycle = map.get("MAX_END_CYCLE").toString();
			try {
				Date maxEndCycleDate = new SimpleDateFormat("yyyy-MM-dd").parse(maxEndCycle);
				Date newBeginCycleDate = cycleEntity.getCycleBegin();
				int compareResult = newBeginCycleDate.compareTo(maxEndCycleDate);
				if(compareResult < 0 || 0 == compareResult) {
					throw new IllegalArgumentException("周期变更中,起始时间过小.设置的时间应该至少大于【" + maxEndCycle + "】");
				}
			} catch (ParseException e) {
				throw new RuntimeException(e);
			}
		}
		if(null != cycleEntity.getNotice() && null != cycleEntity.getNotice().getId()) {
			this.noticeService.remove(cycleEntity.getNotice().getId(),customer);
			cycleEntity.setNotice(null);
		}
		cycleDao.save(cycleEntity);
	}
	
	@Override
	public void add(CycleEntity cycleEntity, Customer customer) {
		cycleEntity.setSubmitIpAddress(customer.getIp());
		cycleEntity.setSubmitUserId(customer.getUser().getId());
		//验证周期的时间是否有重复或重叠
		if(null == cycleEntity.getCycleBegin()) {
			throw new IllegalArgumentException("周期设置中,起始时间不能为空.");
		}
		if(null == cycleEntity.getCycleEnd()) {
			throw new IllegalArgumentException("周期设置中,结束时间不能为空.");
		}
		if(cycleEntity.getCycleBegin().compareTo(cycleEntity.getCycleEnd()) >= 0) {
			throw new IllegalArgumentException("周期设置中,起始时间必须要小于结束时间.");
		}
		Map<String,Object> map = this.cycleDao.findMaxEndCycle();
		if(null != map.get("MAX_END_CYCLE")) {
			String maxEndCycle = map.get("MAX_END_CYCLE").toString();
			try {
				Date maxEndCycleDate = new SimpleDateFormat("yyyy-MM-dd").parse(maxEndCycle);
				Date newBeginCycleDate = cycleEntity.getCycleBegin();
				int compareResult = newBeginCycleDate.compareTo(maxEndCycleDate);
				if(compareResult < 0 || 0 == compareResult) {
					throw new IllegalArgumentException("周期设置中,起始时间过小.设置的时间应该至少大于【" + maxEndCycle + "】");
				}
			} catch (ParseException e) {
				throw new RuntimeException(e);
			}
		}
		cycleDao.save(cycleEntity);
	}

	@Override
	public CycleEntity getCycle(String cycleId) {
		return this.cycleDao.findOne(cycleId);
	}

	@Override
	@Transactional
	public void notice(String cycleId, NoticeEntity notice,List<String> noticeAreaCollection, Customer customer) {
		
		this.noticeService.notice(notice, noticeAreaCollection, customer);
		CycleEntity cycle = this.getCycle(cycleId);
		cycle.setNotice(notice);
		this.cycleDao.save(cycle);
	}

	@Override
	public CycleEntity getCycle(Date date) {
		Date formateDate;
		try {
			formateDate = new SimpleDateFormat("yyyy-MM-dd").parse(new SimpleDateFormat("yyyy-MM-dd").format(date));
			CycleEntity cycleEntity = this.cycleDao.queryByCycleDate(formateDate);
			if(null == cycleEntity) {
				throw new IllegalArgumentException("给定的日期不在某个周期设定内.");
			}
			return cycleEntity;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		throw new IllegalArgumentException("给定的日期不在某个周期设定内.");
	}

}
