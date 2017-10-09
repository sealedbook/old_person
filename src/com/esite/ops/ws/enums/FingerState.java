package com.esite.ops.ws.enums;

/**
 * 指纹状态(采集用 )
 * @author Administrator
 *
 */
public enum FingerState {
	
	IS_OK("1","指纹正常"),IS_VAGUE("2","指纹模糊"),IS_INVALID("3","指纹无效");
	
	private String code;
	private String showName;
	
	private FingerState(String code,String showName) {
		this.code = code;
		this.showName = showName;
	}
	
	public String getCode() {
		return this.code;
	}
	
	public String getName() {
		return this.showName;
	}
	public static FingerState parse(int code) {
		switch(code) {
			case 1 : return IS_OK;
			case 2 : return IS_VAGUE;
			case 3 : return IS_INVALID;
		}
		return null;
	}
}
