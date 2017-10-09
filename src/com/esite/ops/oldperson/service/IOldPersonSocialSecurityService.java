package com.esite.ops.oldperson.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.esite.framework.security.entity.Customer;
import com.esite.ops.oldperson.entity.OldPersonSocialSecurityEntity;
import com.esite.ops.oldperson.entity.OldPersonSocialSecurityQueryVO;

public interface IOldPersonSocialSecurityService {

	public List<String> importExcelFile(MultipartFile file, Customer customer);

	public Page<OldPersonSocialSecurityEntity> find(
			OldPersonSocialSecurityQueryVO oldPersonSocialSecurityQueryVO,
			Pageable instance);

}
