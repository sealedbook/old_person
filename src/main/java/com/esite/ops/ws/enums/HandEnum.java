package com.esite.ops.ws.enums;

/**
 * 左右手枚举类，只描述是左手还是右手
 * @author Administrator
 *
 */
public enum HandEnum {
	LEFT("1","左手")
	,RIGHT("2","右手");
	
	private String code;
	private String showName;
	
	private HandEnum(String code,String showName) {
		this.code = code;
		this.showName = showName;
	}
	
	public String getCode() {
		return this.code;
	}
	
	public String getName() {
		return this.showName;
	}
	public static HandEnum parse(int code) {
		switch(code) {
			case 1 : return LEFT;
			case 2 : return RIGHT;
		}
		return null;
	}
}
