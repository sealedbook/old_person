package com.esite.ops.ws.enums;

/**
 * 人员性别
 * @author Administrator
 *
 */
public enum PersonSex {

	MAN("1","男"),WOMAN("2","女"),UNKNOW("3","未知");
	
	private String code;
	private String showName;
	
	private PersonSex(String code,String showName) {
		this.code = code;
		this.showName = showName;
	}
	
	public String getCode() {
		return this.code;
	}
	
	public String getName() {
		return this.showName;
	}
	
	public static PersonSex parse(String code) {
		if("1".equals(code)) {
			return MAN;
		} else if("2".equals(code)) {
			return WOMAN;
		} else {
			return UNKNOW;
		}
	}
}
