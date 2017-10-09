package com.esite.ops.health.vo;

import java.util.Date;

public class HealthInfoQueryVO {

	/**
	 * 周期id
	 */
	private String cycleId;
	
	/**
	 * 终端指纹验证状态
	 */
	private String fingerVerifyState;
	
	/**
	 * 操作员姓名
	 */
	private String operatorName;
	
	/**
	 * 操作员id
	 */
	private String operatorId;
	
	/**
	 * 老年人姓名
	 */
	private String oldPersonName;
	
	/**
	 * 老年人身份证号
	 */
	private String oldPersonIdCard;
	
	/**
	 * 老年人id
	 */
	private String oldPersonId;
	
	/**
	 * 认证最终状态
	 */
	private String verifyState = "-1";
	
	/**
	 * 体检开始时间
	 */
	private Date healthBeginDate;
	
	/**
	 * 体检结束时间
	 */
	private Date healthEndDate;

	
	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public String getCycleId() {
		return cycleId;
	}

	public void setCycleId(String cycleId) {
		this.cycleId = cycleId;
	}

	public String getOldPersonIdCard() {
		return oldPersonIdCard;
	}

	public void setOldPersonIdCard(String oldPersonIdCard) {
		this.oldPersonIdCard = oldPersonIdCard;
	}

	public String getOldPersonName() {
		return oldPersonName;
	}

	public void setOldPersonName(String oldPersonName) {
		this.oldPersonName = oldPersonName;
	}

	public String getVerifyState() {
		return verifyState;
	}

	public void setVerifyState(String verifyState) {
		this.verifyState = verifyState;
	}

	public Date getHealthBeginDate() {
		return healthBeginDate;
	}

	public void setHealthBeginDate(Date healthBeginDate) {
		this.healthBeginDate = healthBeginDate;
	}

	public Date getHealthEndDate() {
		return healthEndDate;
	}

	public String getOldPersonId() {
		return oldPersonId;
	}

	public void setOldPersonId(String oldPersonId) {
		this.oldPersonId = oldPersonId;
	}

	public void setHealthEndDate(Date healthEndDate) {
		this.healthEndDate = healthEndDate;
	}

	public String getFingerVerifyState() {
		return fingerVerifyState;
	}

	public void setFingerVerifyState(String fingerVerifyState) {
		this.fingerVerifyState = fingerVerifyState;
	}

}
