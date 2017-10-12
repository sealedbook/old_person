package com.esite.framework.file.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.esite.framework.file.entity.PersonPhotoFile;
import com.esite.framework.file.service.FileService;
@Controller
@RequestMapping("/file")
public class FileController {
	@Autowired
	private FileService fileService;
	
	@RequestMapping("/addFile")
	public void test(@RequestParam MultipartFile file){
		PersonPhotoFile f = new PersonPhotoFile();
		try {
			f.setPersonId("aaa");
			f.setId("aaa");
			f.setContent(file.getBytes());
			f.setFilename(file.getName());
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.fileService.save(f);
	}
	@RequestMapping("/showImg")
	public @ResponseBody byte[] showImg(@RequestParam String id){
		PersonPhotoFile file = this.fileService.findByPersonId(id);
		return file.getContent();
	}
}
