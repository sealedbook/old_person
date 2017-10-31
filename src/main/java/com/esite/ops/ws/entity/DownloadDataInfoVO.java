package com.esite.ops.ws.entity;

/**
 * 同步离线库实体类，总类
 * @author Administrator
 *
 */
public class DownloadDataInfoVO implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3920257198693112078L;

	/**
	 * 随访人员基本信息
	 */
	private DownloadOldPersonInfoVO oldPerson;
	
	/**
	 * 指纹信息
	 */
	private DownloadOldPersonFingerprintVO fingerprint;

	public DownloadOldPersonInfoVO getOldPerson() {
		return oldPerson;
	}

	public void setOldPerson(DownloadOldPersonInfoVO oldPerson) {
		this.oldPerson = oldPerson;
	}

	public DownloadOldPersonFingerprintVO getFingerprint() {
		return fingerprint;
	}

	public void setFingerprint(DownloadOldPersonFingerprintVO fingerprint) {
		this.fingerprint = fingerprint;
	}
	
	
}
