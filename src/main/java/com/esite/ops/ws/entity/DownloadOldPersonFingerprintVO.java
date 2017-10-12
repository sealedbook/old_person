package com.esite.ops.ws.entity;

import com.esite.ops.ws.enums.FingerEnum;
import com.esite.ops.ws.enums.FingerState;
import com.esite.ops.ws.enums.HandEnum;


public class DownloadOldPersonFingerprintVO implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5051987190464646595L;

	/**
	 * 描述左手还是右手
	 */
	private HandEnum handEnum;
	
	/**
	 * 描述是第几根手指(1-5)
	 */
	private FingerEnum fingerEnum;
	
	/**
	 * 指纹特征码
	 */
	private byte[] fingerprintChar;
	
	/**
	 * 指纹模板
	 */
	private byte[] fingerprintMold;
	
	/**
	 * 指纹状态
	 */
	private FingerState fingerState;

	public HandEnum getHandEnum() {
		return handEnum;
	}

	public void setHandEnum(HandEnum handEnum) {
		this.handEnum = handEnum;
	}

	public FingerEnum getFingerEnum() {
		return fingerEnum;
	}

	public void setFingerEnum(FingerEnum fingerEnum) {
		this.fingerEnum = fingerEnum;
	}

	public byte[] getFingerprintChar() {
		return fingerprintChar;
	}

	public void setFingerprintChar(byte[] fingerprintChar) {
		this.fingerprintChar = fingerprintChar;
	}

	public byte[] getFingerprintMold() {
		return fingerprintMold;
	}

	public void setFingerprintMold(byte[] fingerprintMold) {
		this.fingerprintMold = fingerprintMold;
	}

	public FingerState getFingerState() {
		return fingerState;
	}

	public void setFingerState(FingerState fingerState) {
		this.fingerState = fingerState;
	}
	
}
