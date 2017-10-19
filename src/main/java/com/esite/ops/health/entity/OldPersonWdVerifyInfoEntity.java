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
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.esite.ops.oldperson.entity.OldPersonEntity;

@Entity
@Table(name="old_person_wd_verify_info")
public class OldPersonWdVerifyInfoEntity {

	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid",strategy="uuid")
	private String id;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="old_person_id")
	@NotFound(action=NotFoundAction.IGNORE)
	private OldPersonEntity oldPerson;
	
	@Column(name="VERIFY_STATE")
	private String verifyState = "-1";
	
	@Column(name="CYCLE_ID")
	private String cycleId;
	
	@Column(name="insert_date_time")
	private Date insertDateTime;
	
	@Column(name="INSERT_USER_NAME")
	private String insertUserName;
	
	@Column(name="insert_ip_address")
	private String insertIpAddress;

	public int getSex() {
		if(null == oldPerson) {
			return -1;
		}
		return this.oldPerson.getSex();
	}
	public String getIdCard() {
		if(null == oldPerson) {
			return "";
		}
		return this.oldPerson.getIdCard();
	}
	public String getSocialNumber() {
		if(null == oldPerson) {
			return "";
		}
		return this.oldPerson.getSocialNumber();
	}
	public Date getBirthday() {
		if(null == oldPerson) {
			return null;
		}
		return this.oldPerson.getBirthday();
	}
	public int getAge() {
		if(null == oldPerson) {
			return -1;
		}
		return this.oldPerson.getAge();
	}

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

	public String getVerifyState() {
		return verifyState;
	}

	public void setVerifyState(String verifyState) {
		this.verifyState = verifyState;
	}

	public String getCycleId() {
		return cycleId;
	}

	public void setCycleId(String cycleId) {
		this.cycleId = cycleId;
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

		
}
