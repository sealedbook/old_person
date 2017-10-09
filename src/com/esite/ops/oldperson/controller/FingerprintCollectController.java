package com.esite.ops.oldperson.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.esite.ops.oldperson.entity.FingerprintCollectEntity;
import com.esite.ops.oldperson.entity.OldPersonEntity;
import com.esite.ops.oldperson.service.IFingerprintCollectService;

@Controller
@RequestMapping("/oldperson/fingerprint")
public class FingerprintCollectController {

	@Autowired
	private IFingerprintCollectService fingerprintCollectService;
	
	@RequestMapping("/photo")
	public @ResponseBody byte[] showImg(@RequestParam String oldPersonId){
		FingerprintCollectEntity oldPerson = this.fingerprintCollectService.getFingerprintCollectByOldPersonId(oldPersonId);
		if(null == oldPerson) {
			return null;
		}
		byte[] photoByte = oldPerson.getFingerprintPhotoOne();
		return photoByte;
	}
}
