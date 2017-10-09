package com.esite.ops.mission.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import com.esite.framework.security.entity.Customer;
import com.esite.framework.util.JpaLikeQueryHelper;
import com.esite.framework.util.StringHelper;
import com.esite.ops.mission.dao.NoticeDao;
import com.esite.ops.mission.entity.NoticeEntity;
import com.esite.ops.mission.entity.NoticeQueryVO;
import com.esite.ops.mission.entity.NoticeReceiveEntity;
import com.esite.ops.mission.service.INoticeReceiveService;
import com.esite.ops.mission.service.INoticeService;
import com.esite.ops.oldperson.entity.OldPersonEntity;
import com.esite.ops.operator.entity.OperatorEntity;
import com.esite.ops.operator.service.IOperatorService;

public class NoticeServiceImpl implements INoticeService {

	@Autowired
	private NoticeDao noticeDao;
	
	@Autowired
	private IOperatorService operatorService;
	
	@Autowired
	private INoticeReceiveService noticeReceiveService;
	
	@Override
	public Page<NoticeEntity> getNotice(final NoticeQueryVO noticeQueryVO,Pageable instance) {
		return noticeDao.findAll(new Specification<NoticeEntity>() {
			@Override
			public Predicate toPredicate(Root<NoticeEntity> root, CriteriaQuery<?> query,CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				List<Expression<Boolean>> expressions = predicate.getExpressions();
				expressions.add(cb.equal(root.<String>get("status"), noticeQueryVO.getStatus()));
				query.orderBy(cb.desc(root.get("submitDateTime")));
				return predicate;
			}
		}, instance);
	}

	@Override
	@Transactional
	public void notice(NoticeEntity noticeEntity,List<String> noticeAreaCollection, Customer customer) {
		noticeEntity.setSubmitIpAddress(customer.getIp());
		noticeEntity.setSubmitUserId(customer.getUser().getId());
		
		Iterator<OperatorEntity> operatorCollection = null;
		if(null != noticeAreaCollection) {
			//根据选定的管辖地区获得操作员
			operatorCollection = this.operatorService.getOperatorInArea(noticeAreaCollection).iterator();
		} else {
			//获得所有操作员，并插入通知日志信息
			operatorCollection = operatorService.getAll().iterator();
		}
		if(null == operatorCollection || operatorCollection.hasNext() == false) {
			throw new IllegalArgumentException("您所选的地区中还没有任何操作员.本次发送的通知是无效通知.请核对后再发送.");
		}
		
		this.noticeDao.save(noticeEntity);
		save(noticeEntity,operatorCollection);
	}
	
	@Override
	public void notice(NoticeEntity noticeEntity, Customer customer) {
		this.notice(noticeEntity,null,customer);
	}
	
	private void save(NoticeEntity noticeEntity,Iterator<OperatorEntity> operatorCollection) {
		List<NoticeReceiveEntity> noticeObjectCollection = new ArrayList<NoticeReceiveEntity>();
		while(operatorCollection.hasNext()) {
			OperatorEntity operator = operatorCollection.next();
			
			NoticeReceiveEntity noticeReceiveEntity = new NoticeReceiveEntity();
			noticeReceiveEntity.setNoticeEntity(noticeEntity);
			noticeReceiveEntity.setReceiveUserId(operator.getId());
			
			noticeObjectCollection.add(noticeReceiveEntity);
		}
		noticeReceiveService.createReceiveLog(noticeObjectCollection);
	}

	@Override
	public NoticeEntity getNotice(String id) {
		return this.noticeDao.findOne(id);
	}

	@Override
	public List<NoticeEntity> getUnReceiveNoticeByOperatorIdCard(String operatorIdCard) {
		if(StringHelper.isEmpty(operatorIdCard)) {
			throw new IllegalArgumentException("操作员的身份证号是空的,系统无法查询");
		}
		OperatorEntity operator = operatorService.getOperatorByIdentityCard(operatorIdCard);
		if(null == operator) {
			throw new IllegalArgumentException("系统中不存在这个操作员");
		}
		return this.noticeDao.queryUnReceivNoticeByOperator(operator.getId());
	}
	
	@Override
	public List<NoticeEntity> getReceivedNoticeByOperatorIdCard(String operatorIdCard) {
		if(StringHelper.isEmpty(operatorIdCard)) {
			throw new IllegalArgumentException("操作员的身份证号是空的,系统无法查询");
		}
		OperatorEntity operator = operatorService.getOperatorByIdentityCard(operatorIdCard);
		if(null == operator) {
			throw new IllegalArgumentException("系统中不存在这个操作员");
		}
		return this.noticeDao.queryReceivedByOperator(operator.getId());
	}

	@Override
	public void remove(String id,Customer customer) {
		if(StringHelper.isEmpty(id)) {
			return;
		}
		NoticeEntity notice = this.getNotice(id);
		if(null == notice) {
			return;
		}
		notice.setStatus("delete");
		this.noticeDao.save(notice);
	}

}
