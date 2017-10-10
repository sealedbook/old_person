package com.esite.ops.health.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.esite.ops.mission.entity.CycleEntity;
import com.esite.ops.oldperson.entity.OldPersonEntity;
import com.esite.ops.operator.entity.OperatorEntity;

@Entity
@Table(name="HEALTH_INFO")
public class HealthInfoEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	//@GeneratedValue(generator="system-uuid")
	//@GenericGenerator(name="system-uuid",strategy="uuid")
	private String id;
	
	@Column(name="REPORT_CODE",updatable=false)
	private String reportCode = "";
	
	/**
	 * 体检开始时间
	 */
	@Column(name="BEGIN_DATETIME",updatable=false)
	private Date beginDateTime;
	
	/**
	 * 体检结束时间
	 */
	@Column(name="END_DATETIME",updatable=false)
	private Date endDateTime;

	public String getPhoto() {
		return "是";
	}

	/**
	 * 获得认证所用时间
	 * @return
	 */
	public String getUseTime() {
		if(null == this.beginDateTime || null == this.endDateTime) {
			return "未知";
		}
		Date now = endDateTime;
		Date date = beginDateTime;
		long l=now.getTime()-date.getTime();
		long day=l/(24*60*60*1000);
		long hour=(l/(60*60*1000)-day*24);
		long min=((l/(60*1000))-day*24*60-hour*60);
		long s=(l/1000-day*24*60*60-hour*60*60-min*60);
		StringBuffer showUseTime = new StringBuffer();
		if(day > 0) {
			showUseTime.append(day).append("天");
		}
		if(hour > 0) {
			showUseTime.append(hour).append("时");
		}
		if(min > 0) {
			showUseTime.append(min).append("分");
		}
		if(s > 0) {
			showUseTime.append(s).append("秒");
		}
		return showUseTime.toString();
	}

	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="OLD_PERSON_ID",updatable=false)
	@NotFound(action=NotFoundAction.IGNORE)
	private OldPersonEntity oldPerson;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="OPERATOR_ID",updatable=false)
	@NotFound(action=NotFoundAction.IGNORE)
	private OperatorEntity operator;

	/**
	 * 终端指纹认证状态
	 */
	@Column(name="FINGER_VERIFY_STATE")
	private String fingerVerifyState;
	
	/**
	 * 导入的总批次id,同一次webservice产生的batchId是相同的
	 */
	@Column(name="BATCH_ID")
	private String batchId;
	
	@Column(name="LOG_ID")
	private String logId;
	/**
	 * 认证的最终审核状态,默认为-1 未审核
	 */
	@Column(name="VERIFY_STATE")
	private String verifyState = "-1";
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="CYCLE_ID",updatable=false)
	@NotFound(action=NotFoundAction.IGNORE)
	private CycleEntity cycle;
	
	@Column(name="INSERT_DATE_TIME")
	private Date insertDateTime = new Date();
	
	@OneToOne(fetch=FetchType.EAGER,mappedBy="health")
	@NotFound(action=NotFoundAction.IGNORE)
	private HealthResultEntity healthResult;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getReportCode() {
		return reportCode;
	}

	public void setReportCode(String reportCode) {
		this.reportCode = reportCode;
	}

	public Date getBeginDateTime() {
		return beginDateTime;
	}

	public void setBeginDateTime(Date beginDateTime) {
		this.beginDateTime = beginDateTime;
	}

	public Date getEndDateTime() {
		return endDateTime;
	}

	public void setEndDateTime(Date endDateTime) {
		this.endDateTime = endDateTime;
	}

	public OldPersonEntity getOldPerson() {
		return oldPerson;
	}

	public void setOldPerson(OldPersonEntity oldPerson) {
		this.oldPerson = oldPerson;
	}

	public OperatorEntity getOperator() {
		return operator;
	}

	public void setOperator(OperatorEntity operator) {
		this.operator = operator;
	}

	public String getFingerVerifyState() {
		return fingerVerifyState;
	}

	public void setFingerVerifyState(String fingerVerifyState) {
		this.fingerVerifyState = fingerVerifyState;
	}

	public String getVerifyState() {
		return verifyState;
	}

	public void setVerifyState(String verifyState) {
		this.verifyState = verifyState;
	}

	public CycleEntity getCycle() {
		return cycle;
	}

	public void setCycle(CycleEntity cycle) {
		this.cycle = cycle;
	}

	public Date getInsertDateTime() {
		return insertDateTime;
	}

	public void setInsertDateTime(Date insertDateTime) {
		this.insertDateTime = insertDateTime;
	}

	public String getBatchId() {
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

	public String getLogId() {
		return logId;
	}

	public void setLogId(String logId) {
		this.logId = logId;
	}

	public boolean getHasHealthResult() {
		return healthResult.hasHealthResult();
	}
	public HealthResultEntity getHealthResult() {
		return healthResult;
	}

	public void setHealthResult(HealthResultEntity healthResult) {
		this.healthResult = healthResult;
	}
	
	public float getHeartRate() {
		if(null == healthResult) {
			return 0;
		}
		return this.healthResult.getHeartRate();
	}

	public float getRespiratoryRate() {
		if(null == healthResult) {
			return 0;
		}
		return this.healthResult.getRespiratoryRate();
	}

	public float getBloodOxygen() {
		if(null == healthResult) {
			return 0;
		}
		return this.healthResult.getBloodOxygen();
	}
	
	public float getPulseRate() {
		if(null == healthResult) {
			return 0;
		}
		return this.healthResult.getPulseRate();
	}
	
	public float getAnimalHeat() {
		if(null == healthResult) {
			return 0;
		}
		return this.healthResult.getAnimalHeat();
	}
	
	public float getSystolicPressure() {
		if(null == healthResult) {
			return 0;
		}
		return this.healthResult.getSystolicPressure();
	}
	
	public float getDiastolicPressure() {
		if(null == healthResult) {
			return 0;
		}
		return this.healthResult.getDiastolicPressure();
	}
}
