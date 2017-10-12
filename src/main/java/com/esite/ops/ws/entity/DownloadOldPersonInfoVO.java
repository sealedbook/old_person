package com.esite.ops.ws.entity;

import com.esite.ops.oldperson.entity.OldPersonEntity;
import com.esite.ops.ws.enums.OldPersonType;
import com.esite.ops.ws.enums.PersonSex;

public class DownloadOldPersonInfoVO implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4880287950546581508L;

	/**
	 * 服务器端唯一id
	 */
	private String id;
	
	/**
	 * 人员姓名
	 */
	private String name;
	
	/**
	 * 姓名拼音
	 */
	private String nameSpell;
	
	/**
	 * 身份证号
	 */
	private String idCard;
	
	/**
	 * 社保编号
	 */
	private String socialNumber;
	
	/**
	 * 人员性别
	 */
	private PersonSex sex;
	
	/**
	 * 出生日期
	 */
	private long birthday;
	
	/**
	 * 人员状态
	 */
	private OldPersonType type;
	
	/**
	 * 所属地区
	 */
	private String area;
	
	public DownloadOldPersonInfoVO(OldPersonEntity oldPersonEntity) {
		this.area = oldPersonEntity.getArea().getName();
		this.birthday = oldPersonEntity.getBirthday().getTime();
		this.id = oldPersonEntity.getId();
		this.idCard = oldPersonEntity.getIdCard();
		this.name = oldPersonEntity.getName();
		this.sex = PersonSex.parse(oldPersonEntity.getSex() + "");
		this.socialNumber = oldPersonEntity.getSocialNumber();
		this.nameSpell = oldPersonEntity.getNameSpell();
		this.type = OldPersonType.parse(oldPersonEntity.getType() + "");
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
		this.idCard = idCard;
	}

	public String getSocialNumber() {
		return socialNumber;
	}

	public void setSocialNumber(String socialNumber) {
		this.socialNumber = socialNumber;
	}

	public PersonSex getSex() {
		return sex;
	}

	public void setSex(PersonSex sex) {
		this.sex = sex;
	}

	public String getNameSpell() {
		return nameSpell;
	}

	public void setNameSpell(String nameSpell) {
		this.nameSpell = nameSpell;
	}

	public long getBirthday() {
		return birthday;
	}

	public void setBirthday(long birthday) {
		this.birthday = birthday;
	}

	public OldPersonType getType() {
		return type;
	}

	public void setType(OldPersonType type) {
		this.type = type;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}
	
	
}
