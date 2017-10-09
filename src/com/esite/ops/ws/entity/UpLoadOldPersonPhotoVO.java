package com.esite.ops.ws.entity;

import com.esite.ops.ws.enums.PhotoPositionEnum;

public class UpLoadOldPersonPhotoVO implements java.io.Serializable {
	
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
	
}
