package com.esite.ops.mission.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.esite.ops.mission.entity.NoticeEntity;

public interface NoticeDao extends PagingAndSortingRepository<NoticeEntity, java.lang.String> {

	@Query("from NoticeEntity t where t.status='' and  t.id in (select r.noticeEntity.id from NoticeReceiveEntity r where r.receiveUserId=?1 and r.receiveDateTime is null)")
	public List<NoticeEntity> queryUnReceivNoticeByOperator(String id);

	public Page<NoticeEntity> findAll(Specification<NoticeEntity> specification, Pageable instance);

	@Query("from NoticeEntity t where t.status='' and  t.id in (select r.noticeEntity.id from NoticeReceiveEntity r where r.receiveUserId=?1 and r.receiveDateTime is not null)")
	public List<NoticeEntity> queryReceivedByOperator(String id);

}
