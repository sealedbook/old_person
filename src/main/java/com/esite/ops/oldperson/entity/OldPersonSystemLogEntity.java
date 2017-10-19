package com.esite.ops.oldperson.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="OLD_PERSON_OPERATION_LOG")
public class OldPersonSystemLogEntity {

	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid",strategy="uuid")
	private String id;
	
	@Column(name="OLD_PERSON_ID")
	private String oldPersonId;
	
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

	public String getOldPersonId() {
		return oldPersonId;
	}

	public void setOldPersonId(String oldPersonId) {
		this.oldPersonId = oldPersonId;
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

	public Date getSubmitDateTime() {
		return submitDateTime;
	}

	public void setSubmitDateTime(Date submitDateTime) {
		this.submitDateTime = submitDateTime;
	}

	public String getSubmitIpAddress() {
		return submitIpAddress;
	}

	public void setSubmitIpAddress(String submitIpAddress) {
		this.submitIpAddress = submitIpAddress;
	}

	public String getSubmitUserAgent() {
		return submitUserAgent;
	}

	public void setSubmitUserAgent(String submitUserAgent) {
		this.submitUserAgent = submitUserAgent;
	}
	
}
