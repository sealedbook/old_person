package com.esite.ops.operator.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.esite.framework.file.entity.SysFileInfo;
import com.esite.framework.file.service.impl.FileService;
import com.esite.framework.security.entity.Customer;
import com.esite.framework.util.PagerRequest;
import com.esite.framework.util.PagerResponse;
import com.esite.ops.operator.entity.OperatorEntity;
import com.esite.ops.operator.entity.OperatorQueryEntity;
import com.esite.ops.operator.service.impl.OperatorService;

@Controller
@RequestMapping("/operator")
public class OperatorController {

	@Resource
	private OperatorService operatorService;
	@Resource
	private FileService fileService;

	@RequestMapping("/list")
	public @ResponseBody PagerResponse<OperatorEntity> list(OperatorQueryEntity operatorQueryEntity,PagerRequest pageRequest) {
		Page<OperatorEntity> p = this.operatorService.find(operatorQueryEntity, pageRequest.getInstance());
		return new PagerResponse<OperatorEntity>(p);
	}
	
	@RequestMapping("/add")
	public ResponseEntity<String> add(OperatorEntity operatorEntity,@RequestParam String[] manageAreaArray,HttpServletRequest request) throws Exception {
		try {
			operatorEntity = this.operatorService.addNew(operatorEntity,manageAreaArray,new Customer(request));
			return new ResponseEntity<String>(operatorEntity.getId(),HttpStatus.OK);
		} catch(IllegalArgumentException e) {
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.EXPECTATION_FAILED);
		} catch(JpaSystemException e) {
			return new ResponseEntity<String>("身份证号【" + operatorEntity.getIdCard() + "】的操作员已经存在",HttpStatus.EXPECTATION_FAILED);
		} catch(Exception e) {
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.EXPECTATION_FAILED);
		}
	}
	
	@RequestMapping("/update")
	public ResponseEntity<String> update(OperatorEntity operatorEntity,@RequestParam String[] manageAreaArray,HttpServletRequest request) throws Exception {
		try {
			this.operatorService.update(operatorEntity,manageAreaArray,new Customer(request));
			return new ResponseEntity<String>(operatorEntity.getId(),HttpStatus.OK);
		} catch(IllegalArgumentException e) {
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.EXPECTATION_FAILED);
		} catch(JpaSystemException e) {
			return new ResponseEntity<String>("身份证号【" + operatorEntity.getIdCard() + "】的操作员已经存在",HttpStatus.EXPECTATION_FAILED);
		} catch(Exception e) {
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.EXPECTATION_FAILED);
		}
	}
	
	@RequestMapping("/{id}")
	public @ResponseBody OperatorEntity load(@PathVariable String id) {
		return this.operatorService.getOperator(id);
	}
	
	@RequestMapping("/delete")
	public @ResponseBody String delete(String operatorId,HttpServletRequest request) {
		this.operatorService.delete(operatorId,new Customer(request));
		return "";
	}
	
	@RequestMapping("/undo")
	public @ResponseBody String undo(String operatorId,HttpServletRequest request) {
		this.operatorService.undo(operatorId,new Customer(request));
		return "";
	}
	
	@RequestMapping("/{operatorId}/photo")
	public @ResponseBody byte[] showImg(@PathVariable String operatorId){
		OperatorEntity operator = this.operatorService.getOperator(operatorId);
		SysFileInfo sysFileInfo = fileService.findByFileKey(operator.getPhotoKey());
		byte[] photoByte = sysFileInfo.getContent();
		if(null == photoByte) {
			String filePath = this.getClass().getResource("").getPath() + "nobody.jpg";
			try {
				photoByte = IOUtils.toByteArray(new FileInputStream(new File(filePath)));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return photoByte;
	}
	
	@RequestMapping("/photo/upload")
	public String uploadPhoto(String id,@RequestParam MultipartFile photo,HttpServletRequest request,Model model) {
		Customer customer = new Customer(request);
		try {
			OperatorEntity operator = this.operatorService.getOperator(id);
			this.operatorService.uploadPhoto(photo,operator);
		} catch(IllegalArgumentException e) {
			
		} catch (IOException e) {
			
		}
		return "redirect:/operator/page/"+id+"/view.do";
	}
	
}
