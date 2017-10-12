package com.esite.ops.ws.enums;

/**
 * 手指枚举类,描述第几根手指
 * @author Administrator
 *
 */
public enum FingerEnum {

	ONE("1","大拇指"),TWO("2","食指"),THREE("3","中指"),FOUR("4","无名指"),FIVE("5","小拇指");
	
	private String code;
	private String showName;
	
	private FingerEnum(String code,String showName) {
		this.code = code;
		this.showName = showName;
	}
	
	public String getCode() {
		return this.code;
	}
	
	public String getName() {
		return this.showName;
	}
	public static FingerEnum parse(int code) {
		switch(code) {
			case 1 : return ONE;
			case 2 : return TWO;
			case 3 : return THREE;
			case 4 : return FOUR;
			case 5 : return FIVE;
		}
		return null;
	}
}
