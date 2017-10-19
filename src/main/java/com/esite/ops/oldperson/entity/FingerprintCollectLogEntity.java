package com.esite.ops.oldperson.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="FINGERPRINT_COLLECT_LOG_INFO")
public class FingerprintCollectLogEntity {

	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid",strategy="uuid")
	@Column(name="ID",updatable=false)
	private String id;
	
	@Column(name="OLD_PERSON_ID")
	private String oldPersonId;
	
	/**
	 * 指纹状态
	 */
	@Column(name="FINGER_VERIFY_STATE")
	private String fingerVerifyState;
	
	
	/**
	 * 描述左手还是右手
	 */
	@Column(name="HAND")
	private String hand;

	
	/**
	 * 描述是第几根手指(1-5)
	 */
	@Column(name="FINGER")
	private int finger;
	
	/**
	 * 指纹模板
	 */
	@Lob
	@Column(name="FINGERPRINT_TEMPLATE")
	private byte[] fingerprintTemplate;
	
	/**
	 * 第一个指纹图片
	 */
	@Lob
	@Column(name="FINGERPRINT_PHOTO_ONE")
	private byte[] fingerprintPhotoOne;
	/**
	 * 第二个指纹图片
	 */
	@Lob
	@Column(name="FINGERPRINT_PHOTO_TWO")
	private byte[] fingerprintPhotoTwo;
	/**
	 * 第三个指纹图片
	 */
	@Lob
	@Column(name="FINGERPRINT_PHOTO_THREE")
	private byte[] fingerprintPhotoThree;
	/**
	 * 第一个指纹特征码
	 */
	@Lob
	@Column(name="FINGERPRINT_CHAR_ONE")
	private byte[] fingerprintCharOne;
	
	/**
	 * 第二个指纹特征码
	 */
	@Lob
	@Column(name="FINGERPRINT_CHAR_TWO")
	private byte[] fingerprintCharTwo;
	
	/**
	 * 第三个指纹特征码
	 */
	@Lob
	@Column(name="FINGERPRINT_CHAR_THREE")
	private byte[] fingerprintCharThree;
	
	@Column(name="INSERT_DATE_TIME")
	private Date insertDateTime;
	
	@Column(name="INSERT_USER_NAME")
	private String insertUserName;
	
	@Column(name="INSERT_IP_ADDRESS")
	private String insertIpAddress;

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

	public String getFingerVerifyState() {
		return fingerVerifyState;
	}

	public void setFingerVerifyState(String fingerVerifyState) {
		this.fingerVerifyState = fingerVerifyState;
	}

	public String getHand() {
		return hand;
	}

	public void setHand(String hand) {
		this.hand = hand;
	}

	public int getFinger() {
		return finger;
	}

	public void setFinger(int finger) {
		this.finger = finger;
	}

	public byte[] getFingerprintTemplate() {
		return fingerprintTemplate;
	}

	public void setFingerprintTemplate(byte[] fingerprintTemplate) {
		this.fingerprintTemplate = fingerprintTemplate;
	}

	public byte[] getFingerprintCharOne() {
		return fingerprintCharOne;
	}

	public void setFingerprintCharOne(byte[] fingerprintCharOne) {
		this.fingerprintCharOne = fingerprintCharOne;
	}

	public byte[] getFingerprintCharTwo() {
		return fingerprintCharTwo;
	}

	public void setFingerprintCharTwo(byte[] fingerprintCharTwo) {
		this.fingerprintCharTwo = fingerprintCharTwo;
	}

	public byte[] getFingerprintCharThree() {
		return fingerprintCharThree;
	}

	public void setFingerprintCharThree(byte[] fingerprintCharThree) {
		this.fingerprintCharThree = fingerprintCharThree;
	}

	public Date getInsertDateTime() {
		return insertDateTime;
	}

	public void setInsertDateTime(Date insertDateTime) {
		this.insertDateTime = insertDateTime;
	}

	public String getInsertUserName() {
		return insertUserName;
	}

	public void setInsertUserName(String insertUserName) {
		this.insertUserName = insertUserName;
	}

	public String getInsertIpAddress() {
		return insertIpAddress;
	}

	public void setInsertIpAddress(String insertIpAddress) {
		this.insertIpAddress = insertIpAddress;
	}

	public byte[] getFingerprintPhotoOne() {
		return fingerprintPhotoOne;
	}

	public void setFingerprintPhotoOne(byte[] fingerprintPhotoOne) {
		this.fingerprintPhotoOne = fingerprintPhotoOne;
	}

	public byte[] getFingerprintPhotoTwo() {
		return fingerprintPhotoTwo;
	}

	public void setFingerprintPhotoTwo(byte[] fingerprintPhotoTwo) {
		this.fingerprintPhotoTwo = fingerprintPhotoTwo;
	}

	public byte[] getFingerprintPhotoThree() {
		return fingerprintPhotoThree;
	}

	public void setFingerprintPhotoThree(byte[] fingerprintPhotoThree) {
		this.fingerprintPhotoThree = fingerprintPhotoThree;
	}
	
}
