package com.esite.ops.mission.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.esite.framework.security.entity.Customer;
import com.esite.framework.util.StringHelper;
import com.esite.ops.mission.dao.NoticeReceiveDao;
import com.esite.ops.mission.entity.NoticeReceiveEntity;
import com.esite.ops.operator.entity.OperatorEntity;
import com.esite.ops.operator.service.impl.OperatorService;

@Service("noticeReceiveService")
public class NoticeReceiveService {

	@Autowired
	private NoticeReceiveDao noticeReceiveDao;
	
	@Resource
	private OperatorService operatorService;
	
	public void createReceiveLog(NoticeReceiveEntity noticeReceiveEntity) {
		noticeReceiveDao.save(noticeReceiveEntity);
	}

	public void createReceiveLog(List<NoticeReceiveEntity> noticeObjectCollection) {
		noticeReceiveDao.save(noticeObjectCollection);
	}

	public Page<NoticeReceiveEntity> getReceiveStatus(String noticeId,Pageable instance) {
		return this.noticeReceiveDao.findByNoticeEntityId(noticeId,instance);
	}

	public void receive(String noticeId,String imei, Customer customer) {
		if(StringHelper.isEmpty(noticeId)) {
			throw new IllegalArgumentException("系统无法查询通知,因为上传了一个空的通知编号");
		}
		OperatorEntity operator = this.operatorService.getOperatorByIdentityCard(customer.getUser().getIdCard());
		if(null == operator) {
			throw new IllegalArgumentException("系统查不到操作员");
		}
		NoticeReceiveEntity noticeReceive = this.noticeReceiveDao.queryByReceiveUserIdAndNoticeId(operator.getId(),noticeId);
		if(null == noticeReceive) {
			throw new IllegalArgumentException("系统没有查到这个通知,它可能已被签收.");
		}
		noticeReceive.setReceiveDateTime(new Date());
		noticeReceive.setReceiveIpAddress(customer.getIp());
		noticeReceive.setReceiveDeviceNumber(imei);
		this.noticeReceiveDao.save(noticeReceive);
	}


}
