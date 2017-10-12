package com.esite.ops.oldperson.dao;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.esite.ops.oldperson.entity.OldPersonSocialSecurityEntity;

public interface OldPersonSocialSecurityDao extends CrudRepository<OldPersonSocialSecurityEntity, String> {

	public Page<OldPersonSocialSecurityEntity> findAll(
			Specification<OldPersonSocialSecurityEntity> specification,
			Pageable instance);

	@Query("from OldPersonSocialSecurityEntity t where t.oldPerson.id=?1 and t.sendDate=?2 and t.status='insert'")
	public OldPersonSocialSecurityEntity getSocialSecurityByOldPersonIdAndSendDate(
			String id, Date sendDate);

}
