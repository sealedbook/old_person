package com.esite.framework.core.enums;

public enum CustomHttpStatus {

	/**
	 * 登录超时
	 */
	SESSION_TIME_OUT(800,"登录超时");
	
	private int code;
	private String message;
	
	CustomHttpStatus(int code,String message) {
		this.code = code;
		this.message = message;
	}
	
	public int getCode() {
		return code;
	}
	public String getMessage() {
		return message;
	}
}
