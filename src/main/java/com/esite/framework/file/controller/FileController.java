package com.esite.framework.file.controller;

import java.io.IOException;
import java.util.UUID;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.esite.framework.file.entity.SysFileInfo;
import com.esite.framework.file.service.impl.FileService;

@Controller
@RequestMapping("/file")
public class FileController {

    private static final Logger LOG = LoggerFactory.getLogger(FileController.class);

    @Resource
    private FileService fileService;

    @RequestMapping("/addFile")
    public void test(@RequestParam MultipartFile file) {

        byte[] fileByteArray = null;
        try {
            fileByteArray = file.getBytes();
        } catch (IOException e) {
            LOG.error("file upload error.", e);
        }

        this.fileService.save(fileByteArray);
    }

    @RequestMapping("/showImg")
    public @ResponseBody byte[] showImg(@RequestParam String fileKey) {
        SysFileInfo file = this.fileService.findByFileKey(fileKey);
        if (null == file) {
            return null;
        }
        return file.getContent();
    }
}
