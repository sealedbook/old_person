package com.esite.ops.mission.dao;

import java.util.Date;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.esite.ops.mission.entity.CycleEntity;

public interface CycleDao extends CrudRepository<CycleEntity, java.lang.String> {

	@Query("from CycleEntity c order by c.submitDateTime desc")
	public Page<CycleEntity> findAll(Pageable instance);

	@Query(value="SELECT MAX(T.CYCLE_END) MAX_END_CYCLE FROM CYCLE_INFO T",nativeQuery=true)
	public Map<String,Object> findMaxEndCycle();

	@Query(value="SELECT MAX(T.CYCLE_END) MAX_END_CYCLE FROM CYCLE_INFO T where T.id!=?1",nativeQuery=true)
	public Map<String, Object> findMaxEndCycleAndIdNot(String id);
	
	@Query("from CycleEntity t where t.cycleBegin<= ?1 and t.cycleEnd>=?1")
	public CycleEntity queryByCycleDate(Date date);

}
