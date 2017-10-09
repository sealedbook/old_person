package com.esite.ops.oldperson.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="OLD_PERSON_SOCIAL_SECURITY_IMPORT_LOG")
public class OldPersonSocialSecurityImportLogEntity {

	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid",strategy="uuid")
	@Column(name="ID",updatable=false)
	private String id;
	
	@Column(name="IMPORT_DATE_TIME",updatable=false)
	private Date importDateTime = new Date();
	
	@Column(name="IMPORT_USER_ID")
	private String importUserId;
	
	@Column(name="IMPORT_USER_NAME")
	private String importUserName;
	
	@Column(name="IMPORT_IP_ADDRESS")
	private String importIpAddress;
	
	@Column(name="USER_AGENT")
	private String userAgent;
	
	@Lob
	@Column(name="IMPORT_FILE")
	private byte[] importFile;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getImportDateTime() {
		return importDateTime;
	}

	public void setImportDateTime(Date importDateTime) {
		this.importDateTime = importDateTime;
	}

	public String getImportUserId() {
		return importUserId;
	}

	public void setImportUserId(String importUserId) {
		this.importUserId = importUserId;
	}

	public String getImportIpAddress() {
		return importIpAddress;
	}

	public void setImportIpAddress(String importIpAddress) {
		this.importIpAddress = importIpAddress;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public byte[] getImportFile() {
		return importFile;
	}

	public void setImportFile(byte[] importFile) {
		this.importFile = importFile;
	}

	public String getImportUserName() {
		return importUserName;
	}

	public void setImportUserName(String importUserName) {
		this.importUserName = importUserName;
	}

	
}
