package com.esite.ops.health.util;

public enum HealthResult {
	
	IS_OK("fine","正常"),HIGH("2","偏高"),LOW("3","偏低"),UNKNOW("4","无法确认");
	
	private String code;
	private String showName;
	
	private HealthResult(String code,String showName) {
		this.code = code;
		this.showName = showName;
	}
	
	public String getCode() {
		return this.code;
	}
	
	public String getName() {
		return this.showName;
	}
}
