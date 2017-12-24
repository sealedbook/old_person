package com.esite.ops.health.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.esite.ops.health.entity.HealthInfoEntity;
import com.esite.ops.health.vo.HealthInfoQueryVO;

public interface HealthInfoDao extends CrudRepository<HealthInfoEntity, String> {

	public Page<HealthInfoEntity> findAll(Specification<HealthInfoEntity> specification, Pageable instance);

	@Query(value="select sum(case when t.VERIFY_STATE='' then 0 else 1 end ) total,sum(case when t.VERIFY_STATE='1' then 1 else 0 end ) auth_suc,sum(case when t.VERIFY_STATE='0' then 1 else 0 end ) auth_fail,(select count(1) from health_result_info hri where hri.HEALTH_ID=t.id) healthy from health_info t,old_person op where op.id=t.old_person_id and op.status='' and t.operator_id=?1 and unix_timestamp(t.BEGIN_DATETIME) between unix_timestamp(?2) and unix_timestamp(?3) and unix_timestamp(t.END_DATETIME) between unix_timestamp(?2) and unix_timestamp(?3)",nativeQuery=true)
	public Map<String, Object> statisticsHealthInfoByOperatorAndCycleDate(String id, Date cycleBegin, Date cycleEnd);

	@Query("from HealthInfoEntity t where t.cycle.id=?1 and t.oldPerson.id=?2")
	public HealthInfoEntity queryByCycleIdAndOldPersonId(String cycleId, String oldPersonId);

	@Query("from HealthInfoEntity t where t.oldPerson.id=?1 order by insertDateTime desc")
	public Page<HealthInfoEntity> queryByOldPersonId(String oldPersonId,Pageable instance);

	//@Query(value="select ID from health_info where verify_state='-1' and id = (select id from health_info where id < ?1 and verify_state='-1'  order by id desc limit 1)",nativeQuery=true)
	//public Map<String,Object> queryNextHealthById(String healthId);
	
	@Query(value="select ID from health_info where verify_state=?2 and id = (select id from health_info where id < ?1 and verify_state=?2  order by id desc limit 1)",nativeQuery=true)
	public Map<String,Object> queryNextHealthById(String healthId , String verifyState);
	
	@Query(value="select ID from health_info where verify_state=?2 and operator_id=?3 and id = (select id from health_info where id < ?1 and verify_state=?2 and operator_id=?3  order by id desc limit 1)",nativeQuery=true)
	public Map<String,Object> queryNextHealthByIdAndOperatorId(String healthId , String verifyState, String operatorId);
	
	@Query(value="select ID from health_info where verify_state=?2 and old_person_id=?3 and id = (select id from health_info where id < ?1 and verify_state=?2 and old_person_id=?3  order by id desc limit 1)",nativeQuery=true)
	public Map<String,Object> queryNextHealthByIdAndOldPersonId(String healthId , String verifyState, String oldPersonId);
	
	@Query(value="select ID from health_info where verify_state=?2 and operator_id=?3 and old_person_id=?4 and id = (select id from health_info where id < ?1 and verify_state=?2 and operator_id=?3 and old_person_id=?4  order by id desc limit 1)",nativeQuery=true)
	public Map<String,Object> queryNextHealth(String healthId , String verifyState,String operatorId, String oldPersonId);
	
	@Query(value="from HealthInfoEntity where oldPerson.id=?1 order by insertDateTime desc")
	public List<HealthInfoEntity> queryLastByOldPersonId(String oldPersonId);

	@Query(value="select count(t.id) from HealthInfoEntity t where t.oldPerson.id=?1 and t.insertDateTime<=(select o.insertDateTime from HealthInfoEntity o where o.id=?2)")
	public int queryHealthNumber(String oldPersonId, String healthId);

	@Query("from HealthInfoEntity where oldPerson.id=?1 order by insertDateTime")
	public List<HealthInfoEntity> queryByOldPersonId(String oldPersonId);

	@Query("from HealthInfoEntity where oldPerson.id=?1 and insertDateTime<?2 order by insertDateTime desc")
	public List<HealthInfoEntity> queryLastHealthByOldPersonId(String oldPersonId, Date lastHealthDateTime);

	@Query("from HealthInfoEntity where cycle.id=?1")
	public List<HealthInfoEntity> queryByCycleId(String cycleId);
	
	@Query(value="select h.OLD_PERSON_ID from health_info h where h.cycle_id=?1", nativeQuery=true)
	public List<String> queryOldPersonIdByCycleId(String id);

}
