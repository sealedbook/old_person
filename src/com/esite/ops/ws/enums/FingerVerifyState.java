package com.esite.ops.ws.enums;

/**
 * 指纹认证状态
 * @author Administrator
 *
 */
public enum FingerVerifyState {

	SUCCESS("2","终端认证成功")
	,FAIL("3","终端认证失败")
	,UNKNOW("4","终端无法确认");
	
	private String code;
	private String showName;
	
	private FingerVerifyState(String code,String showName) {
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
