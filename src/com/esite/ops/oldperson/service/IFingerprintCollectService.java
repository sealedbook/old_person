package com.esite.ops.oldperson.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.esite.framework.security.entity.Customer;
import com.esite.ops.oldperson.entity.FingerprintCollectEntity;

public interface IFingerprintCollectService {

	/**
	 * 指纹采集
	 * @param fingerprintChar 
	 * @param fingerprintPhoto 
	 * @param fingerprintTemplate 
	 * @param fingerprintCollectEntity
	 * @param customer
	 */
	public void collect(MultipartFile fingerprintTemplate,MultipartFile[] fingerprintPhoto, MultipartFile[] fingerprintChar, FingerprintCollectEntity fingerprintCollectEntity, Customer customer) throws IOException;

	public FingerprintCollectEntity getFingerprintCollectByOldPersonId(String oldPersonId);

	public void removeFingerprintCollect(
			FingerprintCollectEntity fingerprintCollectEntity);

	public void updateFingerprintCollect(
			FingerprintCollectEntity fingerprintCollectEntity);
}
