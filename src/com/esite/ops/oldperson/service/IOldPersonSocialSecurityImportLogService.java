package com.esite.ops.oldperson.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.esite.framework.security.entity.Customer;

public interface IOldPersonSocialSecurityImportLogService {

	public String log(MultipartFile file, Customer customer) throws IOException;

}
