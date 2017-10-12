package com.esite.ops.oldperson.service.impl;

import java.io.IOException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.esite.framework.security.entity.Customer;
import com.esite.ops.oldperson.dao.OldPersonSocialSecurityImportLogDao;
import com.esite.ops.oldperson.entity.OldPersonSocialSecurityImportLogEntity;

@Service("oldPersonSocialSecurityImportLogService")
public class OldPersonSocialSecurityImportLogService {

	@Autowired
	private OldPersonSocialSecurityImportLogDao oldPersonSocialSecurityImportLogDao;

	public String log(MultipartFile file, Customer customer) throws IOException {
		OldPersonSocialSecurityImportLogEntity oldPersonSocialSecurityImportLogEntity = new OldPersonSocialSecurityImportLogEntity();
		oldPersonSocialSecurityImportLogEntity.setImportDateTime(new Date());
		oldPersonSocialSecurityImportLogEntity.setImportFile(file.getBytes());
		oldPersonSocialSecurityImportLogEntity.setImportIpAddress(customer.getIp());
		oldPersonSocialSecurityImportLogEntity.setImportUserId(customer.getUser().getId());
		oldPersonSocialSecurityImportLogEntity.setImportUserName(customer.getUser().getShowName());
		oldPersonSocialSecurityImportLogEntity.setUserAgent(customer.getUserAgent());
		this.oldPersonSocialSecurityImportLogDao.save(oldPersonSocialSecurityImportLogEntity);
		return oldPersonSocialSecurityImportLogEntity.getId();
	}
}
