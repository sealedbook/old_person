package com.esite.ops.health.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.esite.ops.ws.entity.UpLoadOldPersonPhotoVO;
import com.esite.ops.ws.enums.PhotoPositionEnum;

@Entity
@Table(name="HEALTH_PHOTO_INFO")
public class HealthPhotoEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	//@GeneratedValue(generator="system-uuid")
	//@GenericGenerator(name="system-uuid",strategy="uuid")
	private String id;
	
	@Column(name="OLD_PERSON_ID")
	private String oldPersonId;
	
	@Column(name="PHOTO_POSITION")
	private String photoPosition;
	
	public String getPhotoPositionName() {
		return PhotoPositionEnum.parse(photoPosition).getName();
	}
	
	@Lob
	@Column(name="PHOTO_FILE")
	private byte[] photoFile;
	
	@Column(name="HEALTH_ID")
	private String healthId;

	public HealthPhotoEntity(){}
	
	public HealthPhotoEntity(String healthId,Serializable oldPersonId,UpLoadOldPersonPhotoVO personPhotoVO) {
		this.healthId = healthId;
		this.oldPersonId = oldPersonId.toString();
		this.photoPosition = personPhotoVO.getPosition().getCode();
		this.photoFile = personPhotoVO.getPhotoFile();
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

	public String getPhotoPosition() {
		return photoPosition;
	}

	public void setPhotoPosition(String photoPosition) {
		this.photoPosition = photoPosition;
	}

	public byte[] getPhotoFile() {
		return photoFile;
	}

	public void setPhotoFile(byte[] photoFile) {
		this.photoFile = photoFile;
	}

	public String getHealthId() {
		return healthId;
	}

	public void setHealthId(String healthId) {
		this.healthId = healthId;
	}
	
}
