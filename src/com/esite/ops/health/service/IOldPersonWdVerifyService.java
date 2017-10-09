package com.esite.ops.health.service;

import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.esite.framework.security.entity.Customer;
import com.esite.ops.health.entity.OldPersonWdVerifyInfoEntity;
import com.esite.ops.health.entity.OldPersonWdVerifyInfoQueryVO;
import com.esite.ops.health.entity.OldPersonWdVerifyPhotoEntity;

public interface IOldPersonWdVerifyService {

	public void saveVerifyPhoto(String oldPersonId, MultipartFile[] photo,Customer customer)  throws IOException;

	public Page<OldPersonWdVerifyInfoEntity> getVerifyHealth(
			OldPersonWdVerifyInfoQueryVO oldPersonWdVerifyInfoQueryVO,
			Pageable instance);

	public OldPersonWdVerifyInfoEntity getVerifyInfoById(String id);

	public String getNextVerifyInfoId(String verifyId);

	public List<OldPersonWdVerifyPhotoEntity> getPhotoCollectionByVerifyId(
			String id);

	public void updateVerifyState(String verifyId, String verifyState,
			Customer customer);

	public OldPersonWdVerifyPhotoEntity getFacePhotoById(String id);

	public Page<OldPersonWdVerifyInfoEntity> getVerifyHealthByOldPersonId(String oldPersonId, Pageable instance);

	public OldPersonWdVerifyInfoEntity getFirstHealthByOldPersonId(String oldPersonId);


}
