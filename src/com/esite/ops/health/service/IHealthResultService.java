package com.esite.ops.health.service;

import java.util.Map;

import com.esite.ops.health.entity.HealthResultEntity;

public interface IHealthResultService {

	public Map<String, Object> getAvgHealthResultByCycleId(String id);

	public double getHeartRateAvgByCycleId(String id);

	public double getRespiratoryRateAvgByCycleId(String id);

	public double getBloodOxygenAvgByCycleId(String id);

	public double getPulseRateAvgByCycleId(String id);

	public double getSystolicPressureAvgByCycleId(String id);

	public double getDiastolicPressureAvgByCycleId(String id);

	public void updateEcg(String healthId, byte[] ecg);

	public HealthResultEntity getHealthResult(String healthResultId);
}
