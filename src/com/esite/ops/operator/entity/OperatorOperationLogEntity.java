package com.esite.ops.operator.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="OPERATOR_OPERATION_LOG")
public class OperatorOperationLogEntity {

	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid",strategy="uuid")
	private String id;
	
	@Column(name="OPERATOR_ID")
	private String operatorId;
	
	@Column(name="STATUS")
	private String status;
	
	@Column(name="SUBMIT_USER_NAME")
	private String submitUserName;
	
	@Column(name="SUBMIT_IP_ADDRESS")
	private String submitIpAddress;
	
	@Column(name="SUBMIT_DATE_TIME")
	private Date submitDateTime;
	
	@Column(name="SUBMIT_USER_AGENT")
	private String submitUserAgent;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSubmitUserName() {
		return submitUserName;
	}

	public void setSubmitUserName(String submitUserName) {
		this.submitUserName = submitUserName;
	}

	public String getSubmitIpAddress() {
		return submitIpAddress;
	}

	public void setSubmitIpAddress(String submitIpAddress) {
		this.submitIpAddress = submitIpAddress;
	}

	public Date getSubmitDateTime() {
		return submitDateTime;
	}

	public void setSubmitDateTime(Date submitDateTime) {
		this.submitDateTime = submitDateTime;
	}

	public String getSubmitUserAgent() {
		return submitUserAgent;
	}

	public void setSubmitUserAgent(String submitUserAgent) {
		this.submitUserAgent = submitUserAgent;
	}
	
	
}
