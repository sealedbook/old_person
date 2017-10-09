package com.esite.ops.mission.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.esite.framework.security.entity.Customer;
import com.esite.ops.mission.entity.CycleEntity;
import com.esite.ops.mission.entity.NoticeEntity;

public interface ICycleService {

	public Page<CycleEntity> getCycle(Pageable instance);

	public void add(CycleEntity cycleEntity, Customer customer);

	public CycleEntity getCycle(String cycleId);
	
	public CycleEntity getCycle(Date date);

	public void notice(String cycleId, NoticeEntity notice,List<String> noticeAreaCollection, Customer customer);

	public void change(CycleEntity cycleEntity, Customer customer);


}
