package com.esite.ops.ws.entity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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

	/**
	 * 原始基线队列编号
	 */
	private String baseQueueCode;

	/**
	 * 基线队列调查时住址
	 */
	private String baseQueueAddress;

	/**
	 * 随访编号.
	 * 生成规则 : 最后一次体检年份 + 上传时编号 + 体检总次数
	 */
	private String surveyCode;

	/**
	 * 体检总次数,预计算的值
	 */
	private int healthCount;
	
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
		this.baseQueueCode = oldPersonEntity.getBaseQueueCode();
		this.baseQueueAddress = oldPersonEntity.getBaseQueueAddress();
		this.healthCount = oldPersonEntity.getHealthCount() + 1;

		String year = new SimpleDateFormat("yy").format(new Date());
		this.surveyCode = year + oldPersonEntity.getConvertBaseCode() + this.healthCount;
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

	public String getBaseQueueCode() {
		return baseQueueCode;
	}

	public void setBaseQueueCode(String baseQueueCode) {
		this.baseQueueCode = baseQueueCode;
	}

	public String getBaseQueueAddress() {
		return baseQueueAddress;
	}

	public void setBaseQueueAddress(String baseQueueAddress) {
		this.baseQueueAddress = baseQueueAddress;
	}

	public int getHealthCount() {
		return healthCount;
	}

	public void setHealthCount(int healthCount) {
		this.healthCount = healthCount;
	}

	public String getSurveyCode() {
		return surveyCode;
	}

	public void setSurveyCode(String surveyCode) {
		this.surveyCode = surveyCode;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("DownloadOldPersonInfoVO{");
		sb.append("id='").append(id).append('\'');
		sb.append(", name='").append(name).append('\'');
		sb.append(", nameSpell='").append(nameSpell).append('\'');
		sb.append(", idCard='").append(idCard).append('\'');
		sb.append(", socialNumber='").append(socialNumber).append('\'');
		sb.append(", sex=").append(sex);
		sb.append(", birthday=").append(birthday);
		sb.append(", type=").append(type);
		sb.append(", area='").append(area).append('\'');
		sb.append(", baseQueueCode='").append(baseQueueCode).append('\'');
		sb.append(", baseQueueAddress='").append(baseQueueAddress).append('\'');
		sb.append(", surveyCode='").append(surveyCode).append('\'');
		sb.append(", healthCount=").append(healthCount);
		sb.append('}');
		return sb.toString();
	}
}
