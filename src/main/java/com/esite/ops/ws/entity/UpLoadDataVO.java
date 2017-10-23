package com.esite.ops.ws.entity;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * 老年人体检上传实体类
 * @author Administrator
 *
 */
@JsonIgnoreProperties(value={"oldPersonFingerprint","photoCollection"})
public class UpLoadDataVO implements java.io.Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = -6563696996556079718L;

	/**
	 * 老年人唯一编号
	 */
	private java.io.Serializable oldPersonId;

	/**
	 * 最后一次体检年份
	 */
	private int lastHealthYear;

	/**
	 * 体检次数综合
	 */
	private int healthCount;

	/**
	 * 体检开始时间
	 */
	private long beginDateTime = -1L;
	
	/**
	 * 体检结束时间
	 */
	private long endDateTime = -1L;
	
	/**
	 * 体检信息 6项指标
	 */
	private UpLoadOldPersonHealthVO oldPersonHealthVO;
	
	/**
	 * 指纹信息
	 */
	private UpLoadOldPersonFingerprint oldPersonFingerprint;
	
	/**
	 * 照片信息
	 */
	private List<UpLoadOldPersonPhotoVO> photoCollection;

	public long getBeginDateTime() {
		return beginDateTime;
	}

	public void setBeginDateTime(long beginDateTime) {
		this.beginDateTime = beginDateTime;
	}

	public long getEndDateTime() {
		return endDateTime;
	}

	public void setEndDateTime(long endDateTime) {
		this.endDateTime = endDateTime;
	}

	public java.io.Serializable getOldPersonId() {
		return oldPersonId;
	}

	public void setOldPersonId(java.io.Serializable oldPersonId) {
		this.oldPersonId = oldPersonId;
	}

	public UpLoadOldPersonHealthVO getOldPersonHealthVO() {
		return oldPersonHealthVO;
	}

	public void setOldPersonHealthVO(UpLoadOldPersonHealthVO oldPersonHealthVO) {
		this.oldPersonHealthVO = oldPersonHealthVO;
	}

	public UpLoadOldPersonFingerprint getOldPersonFingerprint() {
		return oldPersonFingerprint;
	}

	public void setOldPersonFingerprint(
			UpLoadOldPersonFingerprint oldPersonFingerprint) {
		this.oldPersonFingerprint = oldPersonFingerprint;
	}

	public List<UpLoadOldPersonPhotoVO> getPhotoCollection() {
		return photoCollection;
	}

	public void setPhotoCollection(List<UpLoadOldPersonPhotoVO> photoCollection) {
		this.photoCollection = photoCollection;
	}

	public int getLastHealthYear() {
		return lastHealthYear;
	}

	public void setLastHealthYear(int lastHealthYear) {
		this.lastHealthYear = lastHealthYear;
	}

	public int getHealthCount() {
		return healthCount;
	}

	public void setHealthCount(int healthCount) {
		this.healthCount = healthCount;
	}
}
