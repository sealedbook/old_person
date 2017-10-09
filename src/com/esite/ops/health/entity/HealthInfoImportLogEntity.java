package com.esite.ops.health.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="HEALTH_INFO_IMPORT_LOG")
public class HealthInfoImportLogEntity {

	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid",strategy="uuid")
	private String id;
	
	@Column(name="INSERT_DATE_TIME")
	private Date insertDateTime = new Date();
	
	@Column(name="SUBMIT_USER_ID")
	private String submitUserId;
	
	@Column(name="SUBMIT_IP_ADDRESS")
	private String submitIpAddress;
	
	@Lob
	@Column(name="import_content")
	private String importContent;
	
	@Column(name="BATCH_ID")
	private String batchId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getInsertDateTime() {
		return insertDateTime;
	}

	public void setInsertDateTime(Date insertDateTime) {
		this.insertDateTime = insertDateTime;
	}

	public String getSubmitUserId() {
		return submitUserId;
	}

	public void setSubmitUserId(String submitUserId) {
		this.submitUserId = submitUserId;
	}

	public String getSubmitIpAddress() {
		return submitIpAddress;
	}

	public void setSubmitIpAddress(String submitIpAddress) {
		this.submitIpAddress = submitIpAddress;
	}

	public String getImportContent() {
		return importContent;
	}

	public void setImportContent(String importContent) {
		this.importContent = importContent;
	}

	public String getBatchId() {
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}
	
	
}
