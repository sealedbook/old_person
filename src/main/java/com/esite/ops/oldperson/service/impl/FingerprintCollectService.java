package com.esite.ops.oldperson.service.impl;

import java.io.IOException;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.esite.framework.security.entity.Customer;
import com.esite.framework.util.StringHelper;
import com.esite.ops.oldperson.dao.FingerprintCollectDao;
import com.esite.ops.oldperson.entity.FingerprintCollectEntity;
import com.esite.ops.oldperson.entity.OldPersonEntity;

@Service("fingerprintCollectService")
public class FingerprintCollectService {
	@Autowired
	private FingerprintCollectDao fingerprintCollectDao;
	@Resource
	private FingerprintCollectLogService fingerprintCollectLogService;
	@Resource
	private OldPersonService oldPersonService;

	/**
	 * 指纹采集
	 * @param fingerprintChar
	 * @param fingerprintPhoto
	 * @param fingerprintTemplate
	 * @param fingerprintCollectEntity
	 * @param customer
	 */
	public void collect(MultipartFile fingerprintTemplate,MultipartFile[] fingerprintPhoto, MultipartFile[] fingerprintChar,FingerprintCollectEntity fingerprintCollectEntity, Customer customer) throws IOException {
		if(null == fingerprintCollectEntity) {
			throw new IllegalArgumentException("请上传采集的指纹数据.");
		}
		if(StringHelper.isEmpty(fingerprintCollectEntity.getOldPersonId())) {
			throw new IllegalArgumentException("请指定一个随访人员.");
		}
		OldPersonEntity oldPerson = oldPersonService.getOldPerson(fingerprintCollectEntity.getOldPersonId());
		oldPerson.setFfStatus("ffStatus01");
		if(null == oldPerson) {
			throw new IllegalArgumentException("系统中没有找到对应的随访人员.");
		}
		if(null != fingerprintTemplate) {
			fingerprintCollectEntity.setFingerprintTemplate(fingerprintTemplate.getBytes());
		}
		if(null != fingerprintPhoto && fingerprintPhoto.length>=1 && null != fingerprintPhoto[0]) {
			fingerprintCollectEntity.setFingerprintPhotoOne(fingerprintPhoto[0].getBytes());
		}
		if(null != fingerprintPhoto && fingerprintPhoto.length>=2 && null != fingerprintPhoto[1]) {
			fingerprintCollectEntity.setFingerprintPhotoTwo(fingerprintPhoto[1].getBytes());
		}
		if(null != fingerprintPhoto && fingerprintPhoto.length>=3 && null != fingerprintPhoto[2]) {
			fingerprintCollectEntity.setFingerprintPhotoThree(fingerprintPhoto[2].getBytes());
		}
		if(null != fingerprintChar && fingerprintChar.length >= 1 && null != fingerprintChar[0]) {
			fingerprintCollectEntity.setFingerprintCharOne(fingerprintChar[0].getBytes());
		}
		if(null != fingerprintChar && fingerprintChar.length >= 2 && null != fingerprintChar[1]) {
			fingerprintCollectEntity.setFingerprintCharTwo(fingerprintChar[1].getBytes());
		}
		if(null != fingerprintChar && fingerprintChar.length >= 3 && null != fingerprintChar[2]) {
			fingerprintCollectEntity.setFingerprintCharThree(fingerprintChar[2].getBytes());
		}
		if("1".equals(fingerprintCollectEntity.getFingerVerifyState())) {
			oldPerson.setModelingStatus("created");
		} else if("2".equals(fingerprintCollectEntity.getFingerVerifyState())) {
			oldPerson.setModelingStatus("contcreated");
		} else if("-1".equals(fingerprintCollectEntity.getFingerVerifyState())) {
			//oldPerson.setModelingStatus("contcreated");
		}
		this.oldPersonService.update(oldPerson, null);
		FingerprintCollectEntity dbFingerprintCollectEntity = getFingerprintCollectByOldPersonId(fingerprintCollectEntity.getOldPersonId());
		if(null == dbFingerprintCollectEntity) {
			fingerprintCollectDao.save(fingerprintCollectEntity);
			fingerprintCollectLogService.log(fingerprintCollectEntity,customer);
			return;
		}
		if("1".equals(dbFingerprintCollectEntity.getFingerVerifyState())) {
			throw new IllegalArgumentException("该随访人员指纹已经采集成功,不能重复采集指纹.");
		}
		dbFingerprintCollectEntity.setFinger(fingerprintCollectEntity.getFinger());
		dbFingerprintCollectEntity.setFingerprintCharOne(fingerprintCollectEntity.getFingerprintCharOne());
		dbFingerprintCollectEntity.setFingerprintCharThree(fingerprintCollectEntity.getFingerprintCharThree());
		dbFingerprintCollectEntity.setFingerprintCharTwo(fingerprintCollectEntity.getFingerprintCharTwo());
		dbFingerprintCollectEntity.setFingerprintTemplate(fingerprintCollectEntity.getFingerprintTemplate());
		dbFingerprintCollectEntity.setFingerVerifyState(fingerprintCollectEntity.getFingerVerifyState());
		dbFingerprintCollectEntity.setHand(fingerprintCollectEntity.getHand());
		dbFingerprintCollectEntity.setFingerprintPhotoOne(fingerprintCollectEntity.getFingerprintPhotoOne());
		dbFingerprintCollectEntity.setFingerprintPhotoTwo(fingerprintCollectEntity.getFingerprintPhotoTwo());
		dbFingerprintCollectEntity.setFingerprintPhotoThree(fingerprintCollectEntity.getFingerprintPhotoThree());
		this.fingerprintCollectDao.save(dbFingerprintCollectEntity);
		fingerprintCollectLogService.log(dbFingerprintCollectEntity,customer);
	}
	
	public FingerprintCollectEntity getFingerprintCollectByOldPersonId(String oldPersonId) {
		return this.fingerprintCollectDao.queryByOldPersonId(oldPersonId);
	}

	public void removeFingerprintCollect(FingerprintCollectEntity fingerprintCollectEntity) {
		fingerprintCollectDao.delete(fingerprintCollectEntity);
	}

	public void updateFingerprintCollect(
			FingerprintCollectEntity fingerprintCollectEntity) {
		fingerprintCollectDao.save(fingerprintCollectEntity);
	}
}
