package com.esite.ops.ws.entity;

import com.esite.ops.ws.enums.FingerVerifyState;

public class UpLoadOldPersonFingerprint implements java.io.Serializable {
 

	/**
	 * 
	 */
	private static final long serialVersionUID = -3174209991609781126L;

	/**
	 * 终端指纹认证状态
	 */
	private FingerVerifyState fingerVerifyState;

	/**
	 * 指纹特征码
	 */
	private byte[] fingerChar;
	
	/**
	 * 指纹照片
	 */
	private byte[] fingerImage;
	
	/**
	 * 备注信息
	 */
	private String remark;
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public byte[] getFingerChar() {
		return fingerChar;
	}

	public void setFingerChar(byte[] fingerChar) {
		this.fingerChar = fingerChar;
	}

	/**
	 * 获得指纹图片
	 * @return
	 */
	public byte[] getFingerImage() {
		return fingerImage;
	}

	/**
	 * 获得指纹特征码,可能为空
	 * @param fingerImage
	 */
	public void setFingerImage(byte[] fingerImage) {
		this.fingerImage = fingerImage;
	}
	
	public FingerVerifyState getFingerVerifyState() {
		return fingerVerifyState;
	}

	public void setFingerVerifyState(FingerVerifyState fingerVerifyState) {
		this.fingerVerifyState = fingerVerifyState;
	}

}
