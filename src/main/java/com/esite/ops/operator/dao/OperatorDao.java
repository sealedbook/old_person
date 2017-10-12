package com.esite.ops.operator.dao;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.esite.ops.operator.entity.OperatorEntity;

public interface OperatorDao extends CrudRepository<OperatorEntity, java.lang.String> {

	public Page<OperatorEntity> findAll(Specification<OperatorEntity> spec,Pageable instance);

	@Query(value="from OperatorEntity t where t.status='' and t.id in (select c.operatorId from AreaConfigEntity c where c.areaId in (?1))")
	public Iterable<OperatorEntity> queryOperatorInArea(List<String> areaIdCollection);

	public OperatorEntity queryOperatorByIdCard(String identityCard);
	
	@Query(value="select count(t.id) from OperatorEntity t where t.status=''")
	public long count();
	
	@Query(value="select count(t.id) from OperatorEntity t where t.manageArea like ?1 and t.status=''")
	public long countByManageAreaLike(String string);

	@Query(value="select (select count(o.id) from old_person o where o.type='1' and o.status='' and o.AREA_ID in (select cfg.area_id from operator_area_config cfg where cfg.operator_id=t.id)) old_person_count,  (select count(1) from health_info h where h.OPERATOR_ID=t.ID and h.cycle_id=?1) health_total,t.* from operator_info t where t.status=''",nativeQuery=true)
	public List<Map<String, Object>> workSchedule(String cycleId);

}
