package com.esite.ops.operator.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.GenericGenerator;

import com.esite.framework.util.IdentityCardHelper;
import com.esite.framework.util.StringHelper;

@Entity
@Table(name="OPERATOR_INFO")
@JsonIgnoreProperties(value={"terminalLoginPassword","photo"})
public class OperatorEntity {
	
	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid",strategy="uuid")
	private String id;
	
	@Column(name="NAME")
	private String name;
	
	@Column(name="ID_CARD")
	private String idCard;
	
	@Column(name="SEX")
	private int sex;
	
	@Column(name="BIRTHDAY")
	private Date birthday;
	
	@Column(name="NATIONALITY")
	private String nationality;

	@Column(name="SYS_INSERT_DATETIME",updatable=false)
	private Date sysInsertDatetime = new Date();
	
	@Column(name="MANAGE_AREA")
	private String manageArea;
	
	@Column(name="HOME_ADDRESS")
	private String homeAddress;
	
	@Column(name="PHONE_NUMBER")
	private String phoneNumber;
	
	@Column(name="STATUS")
	private String status = "";
	
	@Lob
	@Column(name="PHOTO")
	private byte[] photo;
	
	public byte[] getPhoto() {
		return photo;
	}

	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}

	public String getManageArea() {
		return manageArea;
	}

	public String getNationality() {
		return nationality;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public void setManageArea(String manageArea) {
		this.manageArea = manageArea;
	}

	public int getAge() {
		return IdentityCardHelper.getAge(idCard);
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		idCard = IdentityCardHelper.get18idCard(idCard);
		this.idCard = idCard;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Date getSysInsertDatetime() {
		return sysInsertDatetime;
	}

	public void setSysInsertDatetime(Date sysInsertDatetime) {
		this.sysInsertDatetime = sysInsertDatetime;
	}

	public String getHomeAddress() {
		return homeAddress;
	}

	public void setHomeAddress(String homeAddress) {
		this.homeAddress = homeAddress;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

}
