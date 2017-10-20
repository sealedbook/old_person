package com.esite.framework.file.dao;


import org.springframework.data.repository.CrudRepository;

import com.esite.framework.file.entity.SysFileInfo;

public interface FileDAO extends CrudRepository<SysFileInfo, String> {

    SysFileInfo findByFileKey(String fileKey);
}
