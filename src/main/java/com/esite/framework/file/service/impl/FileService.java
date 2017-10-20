package com.esite.framework.file.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esite.framework.file.dao.FileDAO;
import com.esite.framework.file.entity.SysFileInfo;

@Service("fileService")
public class FileService {

    private static final Logger LOG = LoggerFactory.getLogger(FileService.class);

    private static byte[] DEFAULT_PHOTO = null;
    static {
        String filePath = FileService.class.getClassLoader().getResource("nobody.jpg").getPath();
        try {
            DEFAULT_PHOTO = IOUtils.toByteArray(new FileInputStream(new File(filePath)));
        } catch (IOException e) {
            LOG.error("init default photo error", e);
        }
    }

    @Autowired
    private FileDAO fileDAO;

    public SysFileInfo findByFileKey(String fileKey) {
        SysFileInfo sysFileInfo = fileDAO.findByFileKey(fileKey);

        if (null == sysFileInfo) {
            sysFileInfo = new SysFileInfo();
            String filePath = this.getClass().getClassLoader().getResource("nobody.jpg").getPath();
            try {
                byte[] defaultPhotoByte = IOUtils.toByteArray(new FileInputStream(new File(filePath)));
                sysFileInfo.setFileKey("-1");
                sysFileInfo.setContent(DEFAULT_PHOTO);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sysFileInfo;
    }

    public SysFileInfo save(byte[] file) {
        SysFileInfo sysFileInfo = new SysFileInfo();
        sysFileInfo.setFileKey(UUID.randomUUID().toString().replaceAll("-", ""));
        sysFileInfo.setContent(file);
        return this.fileDAO.save(sysFileInfo);
    }

}