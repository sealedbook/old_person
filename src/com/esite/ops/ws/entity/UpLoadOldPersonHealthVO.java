package com.esite.ops.ws.entity;

import java.util.Arrays;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(value={"ecgData","spo2Data","respData"})
public class UpLoadOldPersonHealthVO implements java.io.Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5292413588735114249L;

	/**
	 * 心率
	 */
	private float heartRate = -1;
	
	/**
	 * 呼吸率
	 */
	private float respiratoryRate = -1;
	
	/**
	 * 血氧
	 */
	private float bloodOxygen = -1;
	
	/**
	 * 脉率
	 */
	private float pulseRate = -1;
	
	/**
	 * 体温
	 */
	private float animalHeat = 0.0F;
	
	/**
	 * 收缩压
	 */
	private float systolicPressure = -1;
	
	/**
	 * 舒张压
	 */
	private float diastolicPressure = -1;
	
	/**
	 * 心电图波形数据
	 */
	private byte[] ecgData;
	
	/**
	 * 血氧波形图数据
	 */
	private byte[] spo2Data;
	
	/**
	 * 呼吸率波形图数据
	 */
	private byte[] respData;
	

	public byte[] getEcgData() {
		return ecgData;
	}

	public void setEcgData(byte[] ecgData) {
		this.ecgData = ecgData;
	}

	public byte[] getSpo2Data() {
		return spo2Data;
	}

	public void setSpo2Data(byte[] spo2Data) {
		this.spo2Data = spo2Data;
	}

	public byte[] getRespData() {
		return respData;
	}

	public void setRespData(byte[] respData) {
		this.respData = respData;
	}

	public float getHeartRate() {
		return heartRate;
	}

	public void setHeartRate(float heartRate) {
		this.heartRate = heartRate;
	}

	public float getRespiratoryRate() {
		return respiratoryRate;
	}

	public void setRespiratoryRate(float respiratoryRate) {
		this.respiratoryRate = respiratoryRate;
	}

	public float getBloodOxygen() {
		return bloodOxygen;
	}

	public void setBloodOxygen(float bloodOxygen) {
		this.bloodOxygen = bloodOxygen;
	}

	public float getPulseRate() {
		return pulseRate;
	}

	public void setPulseRate(float pulseRate) {
		this.pulseRate = pulseRate;
	}

	public float getAnimalHeat() {
		return animalHeat;
	}

	public void setAnimalHeat(float animalHeat) {
		this.animalHeat = animalHeat;
	}

	public float getSystolicPressure() {
		return systolicPressure;
	}

	public void setSystolicPressure(float systolicPressure) {
		this.systolicPressure = systolicPressure;
	}

	public float getDiastolicPressure() {
		return diastolicPressure;
	}

	public void setDiastolicPressure(float diastolicPressure) {
		this.diastolicPressure = diastolicPressure;
	}

}
