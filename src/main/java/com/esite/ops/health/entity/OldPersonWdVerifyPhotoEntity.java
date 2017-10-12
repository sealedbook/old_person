package com.esite.ops.health.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.esite.ops.oldperson.entity.OldPersonEntity;

@Entity
@Table(name="old_person_wd_verify_photo")
public class OldPersonWdVerifyPhotoEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	//@GeneratedValue(generator="system-uuid")
	//@GenericGenerator(name="system-uuid",strategy="uuid")
	private String id;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="old_person_id")
	private OldPersonEntity oldPerson;
	
	@Column(name="photo_position")
	private String photoPosition;
	
	@Column(name="PHOTO_FILE")
	private byte[] photoFile;
	
	@Column(name="WD_OLD_PERSON_VERIFY_ID")
	private String oldPersonVerifyId;

	@Column(name="CYCLE_ID")
	private String cycleId;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public OldPersonEntity getOldPerson() {
		return oldPerson;
	}

	public void setOldPerson(OldPersonEntity oldPerson) {
		this.oldPerson = oldPerson;
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

	public String getOldPersonVerifyId() {
		return oldPersonVerifyId;
	}

	public void setOldPersonVerifyId(String oldPersonVerifyId) {
		this.oldPersonVerifyId = oldPersonVerifyId;
	}

	public String getCycleId() {
		return cycleId;
	}

	public void setCycleId(String cycleId) {
		this.cycleId = cycleId;
	}
	
}
