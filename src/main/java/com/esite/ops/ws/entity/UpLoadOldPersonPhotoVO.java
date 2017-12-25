package com.esite.ops.ws.entity;

import javax.persistence.criteria.CriteriaBuilder.In;

import com.esite.ops.ws.enums.PhotoPositionEnum;

public class UpLoadOldPersonPhotoVO implements java.io.Serializable, Comparable<UpLoadOldPersonPhotoVO> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5963452637444365154L;

	/**
	 * 照片
	 */
	private byte[] photoFile;
	
	/**
	 * 方向
	 */
	private PhotoPositionEnum position;
	
	public byte[] getPhotoFile() {
		return photoFile;
	}
	public void setPhotoFile(byte[] photoFile) {
		this.photoFile = photoFile;
	}
	public PhotoPositionEnum getPosition() {
		return position;
	}
	public void setPosition(PhotoPositionEnum position) {
		this.position = position;
	}

	@Override
	public int compareTo(UpLoadOldPersonPhotoVO upLoadOldPersonPhotoVO) {
		if (null == upLoadOldPersonPhotoVO) {
			return 0;
		}
		if (null == upLoadOldPersonPhotoVO.getPosition()) {
			return 0;
		}
		int comparePosition = Integer.parseInt(upLoadOldPersonPhotoVO.getPosition().getCode());
		int thisPosition = Integer.parseInt(this.position.getCode());
		return thisPosition - comparePosition;
	}
}
