package com.esite.ops.health.dao;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.esite.ops.health.entity.OldPersonWdVerifyInfoEntity;

public interface OldPersonWdVerifyInfoDao extends CrudRepository<OldPersonWdVerifyInfoEntity, String> {

	public Page<OldPersonWdVerifyInfoEntity> findAll(Specification<OldPersonWdVerifyInfoEntity> specification,Pageable instance);

	@Query(value="select ID from old_person_wd_verify_info where verify_state='-1' and id = (select id from old_person_wd_verify_info where id < ?1 and verify_state='-1'  order by id desc limit 1)",nativeQuery=true)
	public Map<String,Object> queryNextVerifyInfo(String verifyId);

	public OldPersonWdVerifyInfoEntity queryByOldPersonIdAndCycleId(
			String oldPersonId, String id);

	@Query("from OldPersonWdVerifyInfoEntity t where t.oldPerson.id=?1 order by t.insertDateTime desc")
	public Page<OldPersonWdVerifyInfoEntity> queryVerifyHealthByOldPersonId(String oldPersonId, Pageable instance);

	@Query("from OldPersonWdVerifyInfoEntity t where t.oldPerson.id=?1 order by t.insertDateTime")
	public List<OldPersonWdVerifyInfoEntity> queryFirstVerifyHealthByOldPersonId(String oldPersonId);

}
