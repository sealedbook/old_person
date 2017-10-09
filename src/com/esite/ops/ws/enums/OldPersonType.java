package com.esite.ops.ws.enums;

/**
 * 人员状态
 * @author Administrator
 *
 */
public enum OldPersonType {

	LOCAL("1","本地"),REMOTE("2","外地");
	
	private String code;
	private String showName;
	
	private OldPersonType(String code,String showName) {
		this.code = code;
		this.showName = showName;
	}
	
	public String getCode() {
		return this.code;
	}
	
	public String getName() {
		return this.showName;
	}
	
	public static OldPersonType parse(String code) {
		if("1".equals(code)) {
			return LOCAL;
		} else {
			return REMOTE;
		}
	}
	
}
