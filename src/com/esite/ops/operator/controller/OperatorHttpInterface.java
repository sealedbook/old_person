package com.esite.ops.operator.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.esite.framework.security.entity.Customer;
import com.esite.framework.security.service.impl.Security;
import com.esite.ops.operator.entity.OperatorEntity;
import com.esite.ops.operator.service.IOperatorService;

@Controller
@RequestMapping("/http/operator")
public class OperatorHttpInterface {
	
	private static Logger logger = Logger.getLogger(OperatorHttpInterface.class);
	
	@Autowired
	private IOperatorService operatorService;

	@RequestMapping("/info")
	public @ResponseBody Map<String,Object> info(HttpServletRequest request) {
		Map<String,Object> operatorInfoMap = new HashMap<String, Object>();
		Customer customer = new Customer(request);
		OperatorEntity operator = operatorService.getOperatorByIdentityCard(customer.getUser().getIdCard());
		if(null != operator) {
			operatorInfoMap.put("operator", operator);
		}
		//查看是否有指纹录入权限
		operatorInfoMap.put("inputFingerprint", Security.isRight("LNR_ZHIWEN", customer.getUser()));
		return operatorInfoMap;
	}
	
	@RequestMapping("/oldpersonNumber")
	public @ResponseBody long count(HttpServletRequest request) {
		Customer customer = new Customer(request);
		return this.operatorService.countOldPersonByOperatorIdCard(customer.getUser().getIdCard());
	}
	
	@RequestMapping("/download/photo")
	public @ResponseBody byte[] downloadPhoto(HttpServletRequest request) {
		Customer customer = new Customer(request);
		return this.operatorService.downloadPhoto(customer.getUser().getIdCard());
	}
	
//	@RequestMapping("/upload/photo")
//	public ResponseEntity<String> uploadPhoto(@RequestParam MultipartFile photo,HttpServletRequest request) {
//		Customer customer = new Customer(request);
//		try {
//			this.operatorService.uploadPhoto(photo,customer.getUser().getIdCard());
//			return new ResponseEntity<String>(HttpStatus.OK);
//		} catch(IllegalArgumentException e) {
//			return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
//		} catch (IOException e) {
//			logger.error(e.getMessage(), e);
//			return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
//		}
//	}
	
}
