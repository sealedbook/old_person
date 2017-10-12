package com.esite.ops.oldperson.service.impl;

import java.io.IOException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.esite.framework.security.entity.Customer;
import com.esite.ops.oldperson.dao.OldPersonImportLogDao;
import com.esite.ops.oldperson.entity.OldPersonImportLogEntity;

@Service("oldPersonImportLogService")
public class OldPersonImportLogService {

	@Autowired
	private OldPersonImportLogDao oldPersonImportLogDao;
	
	public String log(MultipartFile file, Customer customer) throws IOException {
		OldPersonImportLogEntity oldPersonImportLogEntity = new OldPersonImportLogEntity();
		oldPersonImportLogEntity.setImportDateTime(new Date());
		oldPersonImportLogEntity.setImportFile(file.getBytes());
		oldPersonImportLogEntity.setImportIpAddress(customer.getIp());
		oldPersonImportLogEntity.setImportUserId(customer.getUser().getId());
		oldPersonImportLogEntity.setImportUserName(customer.getUser().getShowName());
		oldPersonImportLogEntity.setUserAgent(customer.getUserAgent());
		oldPersonImportLogDao.save(oldPersonImportLogEntity);
		return oldPersonImportLogEntity.getId();
	}

}
