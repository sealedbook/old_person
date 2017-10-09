package com.esite.ops.ws.enums;

/**
 * 人像方位枚举类，描述人像的方位，如正面、侧面
 * @author Administrator
 *
 */
public enum PhotoPositionEnum implements java.io.Serializable {
	ZHENGMIAN("1","正面")
	,CEMIAN("2","侧面")
	,QITA("3","其他");
	private String code;
	private String showName;
	
	private PhotoPositionEnum(String code,String showName) {
		this.code = code;
		this.showName = showName;
	}
	
	public String getName() {
		return this.showName;
	}
	
	public String getCode() {
		return this.code;
	}
	
	public static PhotoPositionEnum parse(String code) {
		if("1".equals(code)) {
			return ZHENGMIAN;
		} else if("2".equals(code)) {
			return CEMIAN;
		} else {
			return QITA;
		}
	}
	
}
