package com.esite.ops.oldperson.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.esite.ops.oldperson.entity.FingerprintCollectEntity;
import com.esite.ops.oldperson.service.impl.FingerprintCollectService;

@Controller
@RequestMapping("/oldperson/fingerprint")
public class FingerprintCollectController {

	@Resource
	private FingerprintCollectService fingerprintCollectService;
	
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
