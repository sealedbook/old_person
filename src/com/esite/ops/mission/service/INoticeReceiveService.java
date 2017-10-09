package com.esite.ops.mission.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.esite.framework.security.entity.Customer;
import com.esite.ops.mission.entity.NoticeReceiveEntity;

public interface INoticeReceiveService {

	public void createReceiveLog(NoticeReceiveEntity noticeReceiveEntity);

	public void createReceiveLog(List<NoticeReceiveEntity> noticeObjectCollection);

	public Page<NoticeReceiveEntity> getReceiveStatus(String noticeId,Pageable instance);

	public void receive(String noticeId, String imei, Customer customer);

}
