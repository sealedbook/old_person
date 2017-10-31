package com.esite.ops.oldperson.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.esite.framework.security.entity.Customer;
import com.esite.ops.oldperson.entity.FingerprintCollectEntity;
import com.esite.ops.oldperson.service.impl.FingerprintCollectService;

/**
 * 随访人员指纹采集
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/http/oldperson/fingerprint")
public class FingerprintCollectHttpInterface {
	
	@Resource
	private FingerprintCollectService fingerprintCollectService;
	
	@RequestMapping("/collect")
	@ResponseBody
	public Map<String,String> collect(String remark,@RequestParam(required=false) MultipartFile fingerprintTemplate,@RequestParam(required=false) MultipartFile[] fingerprintPhoto,@RequestParam(required=false) MultipartFile[] fingerprintChar,String hand,int finger,String oldPersonId,String fingerVerifyState,HttpServletRequest request) {
		Map<String,String> result = new HashMap<String, String>();
		try {
			FingerprintCollectEntity fingerprintCollectEntity = new FingerprintCollectEntity();
			fingerprintCollectEntity.setFinger(finger);
			fingerprintCollectEntity.setOldPersonId(oldPersonId);
			fingerprintCollectEntity.setHand(hand);
			fingerprintCollectEntity.setFingerVerifyState(fingerVerifyState);
			fingerprintCollectEntity.setRemark(remark);
			fingerprintCollectService.collect(fingerprintTemplate,fingerprintPhoto,fingerprintChar,fingerprintCollectEntity,new Customer(request));
			result.put("responseStatus", "OK");
			return result;
		} catch(IllegalArgumentException e) {
			result.put("responseStatus", "ERROR");
			result.put("errorMessage", e.getMessage());
			return result;
		} catch (IOException e) {
			result.put("responseStatus", "ERROR");
			result.put("errorMessage", e.getMessage());
			return result;
		}
	}
	
}
