package com.esite.ops.oldperson.dao;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.esite.ops.oldperson.entity.OldPersonEntity;

public interface OldPersonDao extends CrudRepository<OldPersonEntity, String> {

	public Page<OldPersonEntity> findAll(Specification<OldPersonEntity> spec,Pageable instance);

	@Query("select count(o.id) from OldPersonEntity o where o.status=''")
	public long count();
	
	@Query("select count(o.id) from OldPersonEntity o where o.area.id=?1 and o.status=''")
	public long countByAreaId(String areaId);
	
	@Query("select count(o.id) from OldPersonEntity o where o.area.id in (?1) and o.status=''")
	public long countByAreaId(List<String> areaIdCollection);

	@Query("from OldPersonEntity person where person.area.id in (?1) and person.status=''")
	public List<OldPersonEntity> queryByAreaCollection(List<String> areaCollection);
	
	@Query("from OldPersonEntity person where person.area.id in (?1) and person.status='' and person.type='1'")
	public List<OldPersonEntity> queryLocalOldPersonByAreaCollection(List<String> areaCollection);
	
	@Query("from OldPersonEntity person where person.area.id in (?1) and person.status='' and person.type='1'")
	public Page<OldPersonEntity> queryLocalOldPersonByAreaCollection(List<String> areaCollection, Pageable instance);

	public OldPersonEntity queryByIdCard(String idCard);

	/**
	 * 只查询本地老年人
	 * @param areaCollection
	 * @param cycleId
	 * @return
	 */
	//@Query(value="select op.id,op.name,op.NAME_SPELL as nameSpell,op.ID_CARD as idCard,op.SOCIAL_NUMBER as socialNumber,(select org.name from sys_security_org org where org.id=op.AREA_ID) as areaName,(select count(1) from health_result_info hri where hri.HEALTH_ID=h.id) as health,if(h.VERIFY_STATE is NULL,'',h.VERIFY_STATE) as verifyState from old_person op left join health_info h on op.id=h.OLD_PERSON_ID and h.cycle_id=?2 where op.type='1' and op.status='' and op.area_id in (?1)  order by h.begin_datetime desc,h.end_datetime desc,op.sys_insert_datetime desc",nativeQuery=true)
	@Query(value="select (select case when count(f.id)>0 then 'true' else 'false' end from fingerprint_collect_info f where f.OLD_PERSON_ID=op.id) hasFingerprint,(case when length(op.photo) >0 then 'true' else 'false' end) hasPhoto,op.id,op.name,op.NAME_SPELL as nameSpell,op.ID_CARD as idCard,op.SOCIAL_NUMBER as socialNumber,(select org.name from sys_security_org org where org.id=op.AREA_ID) as areaName,(select count(1) from health_result_info hri where hri.HEALTH_ID=h.id) as health,if(h.VERIFY_STATE is NULL,'',h.VERIFY_STATE) as verifyState from old_person op left join health_info h on op.id=h.OLD_PERSON_ID and h.cycle_id=?2 where op.type='1' and op.status='' and op.area_id in (?1)  order by h.begin_datetime desc,h.end_datetime desc,op.sys_insert_datetime desc",nativeQuery=true)
	public List<Map<String, Object>> queryLocalOldPersonByAreaListAndCycle(List<String> areaCollection,String cycleId);
	
	@Query(value="select op.id,op.name,op.NAME_SPELL as nameSpell,op.ID_CARD as idCard,op.SOCIAL_NUMBER as socialNumber,(select org.name from sys_security_org org where org.id=op.AREA_ID) as areaName,(select count(1) from health_result_info hri where hri.id=h.id) as health,h.VERIFY_STATE as verifyState from old_person op ,health_info h where op.id=h.OLD_PERSON_ID and op.status='' and h.verify_state!='' and h.operator_id=?1 and h.cycle_id=?2 order by h.begin_datetime desc,h.end_datetime desc,op.sys_insert_datetime desc",nativeQuery=true)
	public List<Map<String, Object>> queryFinishAutoOldPersonByOldPersonIdAndCycle(String id, String cycleId);


}
