package com.esite.ops.health.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esite.ops.health.dao.HealthResultDao;
import com.esite.ops.health.entity.HealthResultEntity;

@Service("healthResultService")
public class HealthResultService {

	@Autowired
	private HealthResultDao healthResultDao;

	public Map<String, Object> getAvgHealthResultByCycleId(String healthId) {
		return healthResultDao.avgHealthResultByCycleId(healthId);
	}

	public double getHeartRateAvgByCycleId(String id) {
		Map<String,Object> map = healthResultDao.getHeartRateAvgByCycleId(id);
		if(null != map && null != map.get("HEART_RATE")) {
			return Double.parseDouble(map.get("HEART_RATE").toString());
		}
		return 0;
	}

	public double getRespiratoryRateAvgByCycleId(String id) {
		Map<String,Object> map = healthResultDao.getRespiratoryRateAvgByCycleId(id);
		if(null != map && null != map.get("RESPIRATORY_RATE")) {
			return Double.parseDouble(map.get("RESPIRATORY_RATE").toString());
		}
		return 0;
	}

	public double getBloodOxygenAvgByCycleId(String id) {
		Map<String,Object> map = healthResultDao.getBloodOxygenAvgByCycleId(id);
		if(null != map && null != map.get("BLOOD_OXYGEN")) {
			return Double.parseDouble(map.get("BLOOD_OXYGEN").toString());
		}
		return 0;
	}

	public double getPulseRateAvgByCycleId(String id) {
		Map<String,Object> map = healthResultDao.getPulseRateAvgByCycleId(id);
		if(null != map && null != map.get("PULSE_RATE")) {
			return Double.parseDouble(map.get("PULSE_RATE").toString());
		}
		return 0;
	}

	public double getSystolicPressureAvgByCycleId(String id) {
		Map<String,Object> map = healthResultDao.getSystolicPressureAvgByCycleId(id);
		if(null != map && null != map.get("SYSTOLIC_PRESSURE")) {
			return Double.parseDouble(map.get("SYSTOLIC_PRESSURE").toString());
		}
		return 0;
	}

	public double getDiastolicPressureAvgByCycleId(String id) {
		Map<String,Object> map = healthResultDao.getDiastolicPressureAvgByCycleId(id);
		if(null != map && null != map.get("DIASTOLIC_PRESSURE")) {
			return Double.parseDouble(map.get("DIASTOLIC_PRESSURE").toString());
		}
		return 0;
	}

	public void updateEcg(String healthId, byte[] ecg) {
		HealthResultEntity healthResult = 
				this.healthResultDao.getHealthResultByHealthId(healthId);
		if(null == healthResult || null == ecg) {
			return;
		}
		this.healthResultDao.updateHealthEcg(healthResult.getId(),ecg);
	}

	public HealthResultEntity getHealthResult(String healthResultId) {
		return this.healthResultDao.findOne(healthResultId);
	}
	
}
