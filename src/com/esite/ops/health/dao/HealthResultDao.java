package com.esite.ops.health.dao;

import java.util.Map;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.esite.ops.health.entity.HealthResultEntity;

public interface HealthResultDao extends CrudRepository<HealthResultEntity, java.lang.String> {

	//@Query(value="SELECT AVG(R.HEART_RATE) HEART_RATE,AVG(R.RESPIRATORY_RATE) RESPIRATORY_RATE,AVG(R.BLOOD_OXYGEN) BLOOD_OXYGEN,AVG(R.PULSE_RATE) PULSE_RATE,AVG(R.ANIMAL_HEAT) ANIMAL_HEAT,AVG(R.SYSTOLIC_PRESSURE) SYSTOLIC_PRESSURE,AVG(R.DIASTOLIC_PRESSURE) DIASTOLIC_PRESSURE FROM HEALTH_RESULT_INFO R where r.health_id=?1",nativeQuery=true)
	@Query(value="SELECT AVG(R.HEART_RATE) HEART_RATE,AVG(R.RESPIRATORY_RATE) RESPIRATORY_RATE,AVG(R.BLOOD_OXYGEN) BLOOD_OXYGEN,AVG(R.PULSE_RATE) PULSE_RATE,AVG(R.ANIMAL_HEAT) ANIMAL_HEAT,AVG(R.SYSTOLIC_PRESSURE) SYSTOLIC_PRESSURE,AVG(R.DIASTOLIC_PRESSURE) DIASTOLIC_PRESSURE FROM HEALTH_RESULT_INFO R,health_info h where r.health_id = h.id and h.CYCLE_ID=?1",nativeQuery=true)
	public Map<String, Object> avgHealthResultByCycleId(String cycleId);

	@Query(value="SELECT AVG(R.HEART_RATE) HEART_RATE FROM HEALTH_RESULT_INFO R,health_info h where r.health_id = h.id and h.CYCLE_ID=?1 and R.HEART_RATE>0",nativeQuery=true)
	public Map<String,Object> getHeartRateAvgByCycleId(String id);

	@Query(value="SELECT AVG(R.RESPIRATORY_RATE) RESPIRATORY_RATE FROM HEALTH_RESULT_INFO R,health_info h where r.health_id = h.id and h.CYCLE_ID=?1 and R.RESPIRATORY_RATE>0",nativeQuery=true)
	public Map<String,Object> getRespiratoryRateAvgByCycleId(String id);
	
	@Query(value="SELECT AVG(R.BLOOD_OXYGEN) BLOOD_OXYGEN FROM HEALTH_RESULT_INFO R,health_info h where r.health_id = h.id and h.CYCLE_ID=?1 and R.BLOOD_OXYGEN>0",nativeQuery=true)
	public Map<String,Object> getBloodOxygenAvgByCycleId(String id);

	@Query(value="SELECT AVG(R.PULSE_RATE) PULSE_RATE FROM HEALTH_RESULT_INFO R,health_info h where r.health_id = h.id and h.CYCLE_ID=?1 and R.PULSE_RATE>0",nativeQuery=true)
	public Map<String,Object> getPulseRateAvgByCycleId(String id);

	@Query(value="SELECT AVG(R.SYSTOLIC_PRESSURE) SYSTOLIC_PRESSURE FROM HEALTH_RESULT_INFO R,health_info h where r.health_id = h.id and h.CYCLE_ID=?1 and R.SYSTOLIC_PRESSURE>0",nativeQuery=true)
	public Map<String,Object> getSystolicPressureAvgByCycleId(String id);

	@Query(value="SELECT AVG(R.DIASTOLIC_PRESSURE) DIASTOLIC_PRESSURE FROM HEALTH_RESULT_INFO R,health_info h where r.health_id = h.id and h.CYCLE_ID=?1 and R.DIASTOLIC_PRESSURE>0",nativeQuery=true)
	public Map<String,Object> getDiastolicPressureAvgByCycleId(String id);
	
	public HealthResultEntity getHealthResultByHealthId(String healthId);
	
	@Modifying
	@Query(value="UPDATE HEALTH_RESULT_INFO set ECG_DATA=?2 where id=?1",nativeQuery=true)
	@Transactional
	public void updateHealthEcg(String id, byte[] bs);

}
