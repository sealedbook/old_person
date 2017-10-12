package com.esite.ops.health.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.GenericGenerator;

import com.esite.ops.health.util.HealthResultHelper;
import com.esite.ops.ws.entity.UpLoadOldPersonHealthVO;

@Entity
@Table(name="HEALTH_RESULT_INFO")
@JsonIgnoreProperties(value={"health","ecgData","spo2Data","respData"})
public class HealthResultEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	//@GeneratedValue(generator="system-uuid")
	//@GenericGenerator(name="system-uuid",strategy="uuid")
	private String id;

	
	@Column(name="INSERT_DATETIME",updatable=false)
	private Date insertDatetime = new Date();
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="HEALTH_ID",updatable=false)
	private HealthInfoEntity health;
	
	/**
	 * 心率
	 */
	@Column(name="HEART_RATE",updatable=false)
	private float heartRate = -1;
	
	/**
	 * 呼吸率
	 */
	@Column(name="RESPIRATORY_RATE",updatable=false)
	private float respiratoryRate = -1;
	
	/**
	 * 血氧
	 */
	@Column(name="BLOOD_OXYGEN",updatable=false)
	private float bloodOxygen = -1;
	
	/**
	 * 脉率
	 */
	@Column(name="PULSE_RATE",updatable=false)
	private float pulseRate = -1;
	
	/**
	 * 体温
	 */
	@Column(name="ANIMAL_HEAT",updatable=false)
	private float animalHeat = 0.0F;
	
	/**
	 * 收缩压
	 */
	@Column(name="SYSTOLIC_PRESSURE",updatable=false)
	private float systolicPressure = -1;
	
	/**
	 * 舒张压
	 */
	@Column(name="DIASTOLIC_PRESSURE",updatable=false)
	private float diastolicPressure = -1;
	
	public String formaterResultForStatistics() {
		StringBuffer bc = new StringBuffer();
		bc.append(this.heartRate).append(",").append(this.respiratoryRate).append(",").append(this.bloodOxygen).append(",")
		.append(this.pulseRate).append(",").append(this.systolicPressure).append(",").append(this.diastolicPressure);
		return bc.toString();
	}
	
	/**
	 * 心电图波形数据
	 */
	@Lob
	@Column(name="ECG_DATA",updatable=false)
	private byte[] ecgData;
	
	/**
	 * 血氧波形图数据
	 */
	@Lob
	@Column(name="SPO2_DATA",updatable=false)
	private byte[] spo2Data;
	
	/**
	 * 呼吸率波形图数据
	 */
	@Lob
	@Column(name="RESP_DATA",updatable=false)
	private byte[] respData;
	

	public HealthResultEntity(){}
	public HealthResultEntity(HealthInfoEntity health,UpLoadOldPersonHealthVO healthResultVO) {
		this.animalHeat = healthResultVO.getAnimalHeat();
		this.bloodOxygen = healthResultVO.getBloodOxygen();
		this.diastolicPressure = healthResultVO.getDiastolicPressure();
		this.heartRate = healthResultVO.getHeartRate();
		this.pulseRate = healthResultVO.getPulseRate();
		this.respiratoryRate = healthResultVO.getRespiratoryRate();
		this.systolicPressure = healthResultVO.getSystolicPressure();
		this.ecgData = healthResultVO.getEcgData();
		this.spo2Data = healthResultVO.getSpo2Data();
		this.respData = healthResultVO.getRespData();
		this.health = health;
		
	}

	public Date getInsertDatetime() {
		return insertDatetime;
	}
	public void setInsertDatetime(Date insertDatetime) {
		this.insertDatetime = insertDatetime;
	}
	
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public float getHeartRate() {
		return heartRate;
	}
	
	public String getHeartRateToString() {
		return HealthResultHelper.formaterHealthValue(heartRate);
	}
	
	public String getHeartRateResult() {
		return HealthResultHelper.getHeartRateResult(this.heartRate);
	}

	public void setHeartRate(float heartRate) {
		this.heartRate = heartRate;
	}

	public float getRespiratoryRate() {
		return respiratoryRate;
	}
	
	public String getRespiratoryRateToString() {
		return HealthResultHelper.formaterHealthValue(respiratoryRate);
	}
	
	public String getRespiratoryRateResult() {
		return HealthResultHelper.getRespiratoryRateResult(this.respiratoryRate);
	}

	public void setRespiratoryRate(float respiratoryRate) {
		this.respiratoryRate = respiratoryRate;
	}

	public float getBloodOxygen() {
		return bloodOxygen;
	}
	
	public String getBloodOxygenToString() {
		return HealthResultHelper.formaterHealthValue(bloodOxygen);
	}
	
	public String getBloodOxygenResult() {
		return HealthResultHelper.getBloodOxygenResult(this.bloodOxygen);
	}

	public void setBloodOxygen(float bloodOxygen) {
		this.bloodOxygen = bloodOxygen;
	}

	public float getPulseRate() {
		return pulseRate;
	}
	
	public String getPulseRateToString() {
		return HealthResultHelper.formaterHealthValue(pulseRate);
	}
	
	public String getPulseRateResult() {
		return HealthResultHelper.getPulseRateResult(this.pulseRate);
	}

	public void setPulseRate(float pulseRate) {
		this.pulseRate = pulseRate;
	}

	public float getAnimalHeat() {
		return animalHeat;
	}
	
	public String getAnimalHeatToString() {
		return HealthResultHelper.formaterHealthValue(animalHeat);
	}
	
	public void setAnimalHeat(float animalHeat) {
		this.animalHeat = animalHeat;
	}

	public float getSystolicPressure() {
		return systolicPressure;
	}
	
	public String getSystolicPressureToString() {
		return HealthResultHelper.formaterHealthValue(systolicPressure);
	}
	
	public String getSystolicPressureResult() {
		return HealthResultHelper.getSystolicPressureResult(this.systolicPressure);
	}

	public void setSystolicPressure(float systolicPressure) {
		this.systolicPressure = systolicPressure;
	}

	public float getDiastolicPressure() {
		return diastolicPressure;
	}
	
	public String getDiastolicPressureToString() {
		return HealthResultHelper.formaterHealthValue(diastolicPressure);
	}
	
	public String getDiastolicPressureResult() {
		return HealthResultHelper.getDiastolicPressureResult(this.diastolicPressure);
	}

	public void setDiastolicPressure(float diastolicPressure) {
		this.diastolicPressure = diastolicPressure;
	}
	public HealthInfoEntity getHealth() {
		return health;
	}
	public void setHealth(HealthInfoEntity health) {
		this.health = health;
	}

	public boolean hasHealthResult() {
		return this.animalHeat<=0 && this.bloodOxygen<=0 && this.diastolicPressure<=0 && this.heartRate<=0 && this.pulseRate<=0 && this.respiratoryRate<=0 && this.systolicPressure<=0;
	}
	
}
