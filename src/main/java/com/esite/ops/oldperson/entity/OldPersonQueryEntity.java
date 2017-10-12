package com.esite.ops.oldperson.entity;

public class OldPersonQueryEntity {
	private String idCard;
	private String name;
	private int sex = -1;
	private int type = -1;
	private String status = "";
	private String areaId;
	private String ageRange;
	private int ageRangeBegin = -1;
	private int ageRangeEnd = -1;
	private String modelingStatus;
	
	public String getModelingStatus() {
		return modelingStatus;
	}
	public void setModelingStatus(String modelingStatus) {
		this.modelingStatus = modelingStatus;
	}
	public int getAgeRangeBegin() {
		return ageRangeBegin;
	}
	public void setAgeRangeBegin(int ageRangeBegin) {
		this.ageRangeBegin = ageRangeBegin;
	}
	public int getAgeRangeEnd() {
		return ageRangeEnd;
	}
	public void setAgeRangeEnd(int ageRangeEnd) {
		this.ageRangeEnd = ageRangeEnd;
	}
	private String socialNumber;
	
	
	public String getSocialNumber() {
		return socialNumber;
	}
	public void setSocialNumber(String socialNumber) {
		this.socialNumber = socialNumber;
	}
	public String getAgeRange() {
		return ageRange;
	}
	public void setAgeRange(String ageRange) {
		this.ageRange = ageRange;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getAreaId() {
		return areaId;
	}
	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		//idCard = IdentityCardHelper.get18idCard(idCard);
		this.idCard = idCard;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
