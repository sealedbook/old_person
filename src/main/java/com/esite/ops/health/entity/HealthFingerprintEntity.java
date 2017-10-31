package com.esite.ops.health.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.esite.ops.ws.entity.UpLoadOldPersonFingerprint;
import com.esite.ops.ws.enums.FingerVerifyState;

/**
 * 随访人员体检指纹信息,指定了哪个随访人员的哪次体检,并且提供了指纹特征码和指纹照片
 * @author Administrator
 *
 */
@Entity
@Table(name="HEALTH_FINGERPRINT_INFO")
public class HealthFingerprintEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	//@GeneratedValue(generator="system-uuid")
	//@GenericGenerator(name="system-uuid",strategy="uuid")
	private String id;
	
	@Column(name="OLD_PERSON_ID")
	private String oldPersonId;
	
	@Column(name="HEALTH_ID")
	private String healthId;
	
	@Column(name="FINGER_VERIFY_STATE")
	private String fingerVerifyState;
	
	@Column(name="INSERT_DATE_TIME",updatable=false)
	private Date insertDateTime = new Date();

	/**
	 * 指纹特征码
	 */
	@Column(name="FINGER_CHAR")
	private byte[] fingerChar;
	
	/**
	 * 指纹照片
	 */
	@Column(name="FINGER_IMAGE")
	private byte[] fingerImage;
	
	@Column(name="REMARK")
	private String remark;

	public HealthFingerprintEntity(){}
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public HealthFingerprintEntity(String healthId, Serializable oldPersonId,UpLoadOldPersonFingerprint fingerprint) {
		this.healthId = healthId;
		this.oldPersonId = oldPersonId.toString();
		if(null != fingerprint) {
			this.fingerChar = fingerprint.getFingerChar();
			this.fingerImage = fingerprint.getFingerImage();
			this.fingerVerifyState = fingerprint.getFingerVerifyState().getCode();
			this.remark = fingerprint.getRemark();
		}
	}


	public Date getInsertDateTime() {
		return insertDateTime;
	}

	public void setInsertDateTime(Date insertDateTime) {
		this.insertDateTime = insertDateTime;
	}
	
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

	public String getHealthId() {
		return healthId;
	}

	public void setHealthId(String healthId) {
		this.healthId = healthId;
	}

	public byte[] getFingerChar() {
		return fingerChar;
	}

	public void setFingerChar(byte[] fingerChar) {
		this.fingerChar = fingerChar;
	}

	public byte[] getFingerImage() {
		return fingerImage;
	}

	public void setFingerImage(byte[] fingerImage) {
		this.fingerImage = fingerImage;
	}

	public String getFingerVerifyState() {
		return fingerVerifyState;
	}

	public void setFingerVerifyState(String fingerVerifyState) {
		this.fingerVerifyState = fingerVerifyState;
	}

}
