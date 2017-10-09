package com.esite.ops.mission.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.esite.ops.mission.entity.NoticeReceiveEntity;

public interface NoticeReceiveDao extends CrudRepository<NoticeReceiveEntity, java.lang.String> {

	@Query(value="from NoticeReceiveEntity r where r.noticeEntity.id=?1")
	public Page<NoticeReceiveEntity> findByNoticeEntityId(String noticeId,Pageable instance);

	@Query(value="from NoticeReceiveEntity r where r.receiveUserId=?1 and r.noticeEntity.id=?2")
	public NoticeReceiveEntity queryByReceiveUserIdAndNoticeId(String operatorId,String noticeId);
}
