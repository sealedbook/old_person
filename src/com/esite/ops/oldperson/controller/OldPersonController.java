package com.esite.ops.oldperson.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.esite.framework.security.entity.Customer;
import com.esite.framework.util.PagerRequest;
import com.esite.framework.util.PagerResponse;
import com.esite.ops.oldperson.entity.FingerprintCollectEntity;
import com.esite.ops.oldperson.entity.OldPersonEntity;
import com.esite.ops.oldperson.entity.OldPersonQueryEntity;
import com.esite.ops.oldperson.service.IFingerprintCollectService;
import com.esite.ops.oldperson.service.IOldPersonService;

@Controller
@RequestMapping("/oldperson")
public class OldPersonController {

	@Autowired
	private IFingerprintCollectService fingerprintCollectService;
	@Autowired
	private IOldPersonService oldPersonService;
	
	@RequestMapping("/list")
	public @ResponseBody PagerResponse<OldPersonEntity> list(OldPersonQueryEntity oldPersonQueryEntity,PagerRequest pageRequest) {
		Page<OldPersonEntity> p = this.oldPersonService.find(oldPersonQueryEntity, pageRequest.getInstance());
		return new PagerResponse<OldPersonEntity>(p);
	}

	@RequestMapping(value="/import",method=RequestMethod.POST)
	public String importOldPerson(@RequestParam MultipartFile file,Model model,HttpServletRequest request) {
		List<String> errorMessage = this.oldPersonService.importOldPersonExcelFile(file,new Customer(request));
		model.addAttribute("errorMessage", errorMessage);
		if(null == errorMessage || errorMessage.size() <= 0) {
			model.addAttribute("status", "success");
		}
		return "/oldperson/import";
	}
	
	@RequestMapping("/add")
	public ResponseEntity<String> add(OldPersonEntity oldPerson,HttpServletRequest request) throws Exception {
		try {
			oldPerson = this.oldPersonService.addNew(oldPerson,new Customer(request));
			return new ResponseEntity<String>(oldPerson.getId(),HttpStatus.OK);
		} catch(IllegalArgumentException e) {
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.EXPECTATION_FAILED);
		} catch(Exception e) {
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.EXPECTATION_FAILED);
		}
	}
	
	@RequestMapping("/update")
	public ResponseEntity<String> update(OldPersonEntity oldPerson,HttpServletRequest request) throws Exception {
		try {
			oldPerson = this.oldPersonService.update(oldPerson,new Customer(request));
			return new ResponseEntity<String>(oldPerson.getId(),HttpStatus.OK);
		} catch(IllegalArgumentException e) {
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.EXPECTATION_FAILED);
		} catch(Exception e) {
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.EXPECTATION_FAILED);
		}
	}
	
	@RequestMapping("/updateModelingStatus")
	public ResponseEntity<String> updateModelingStatus(String oldPersonId, String modelingStatus, 
			HttpServletRequest request) throws Exception {
		try {
			OldPersonEntity oldPerson = this.oldPersonService.getOldPerson(oldPersonId);
			oldPerson.setModelingStatus(modelingStatus);
			this.oldPersonService.update(oldPerson,new Customer(request));
			FingerprintCollectEntity fingerprintCollectEntity = fingerprintCollectService.getFingerprintCollectByOldPersonId(oldPersonId);
			if("contcreated".equals(modelingStatus)) {
				if(null == fingerprintCollectEntity) {
					fingerprintCollectEntity = new FingerprintCollectEntity();
					fingerprintCollectEntity.setOldPersonId(oldPersonId);
				}
				fingerprintCollectEntity.setFingerVerifyState("2");
				fingerprintCollectService.updateFingerprintCollect(fingerprintCollectEntity);
			} else if(null != fingerprintCollectEntity) {
				fingerprintCollectService.removeFingerprintCollect(fingerprintCollectEntity);
			}
			
			return new ResponseEntity<String>(oldPerson.getId(),HttpStatus.OK);
		} catch(IllegalArgumentException e) {
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.EXPECTATION_FAILED);
		} catch(Exception e) {
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.EXPECTATION_FAILED);
		}
	}
	
	@RequestMapping("/delete")
	public ResponseEntity<String> delete(String oldPersonId,HttpServletRequest request) throws Exception {
		try {
			this.oldPersonService.delete(oldPersonId,new Customer(request));
			return new ResponseEntity<String>("",HttpStatus.OK);
		} catch(IllegalArgumentException e) {
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.EXPECTATION_FAILED);
		} catch(Exception e) {
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.EXPECTATION_FAILED);
		}
	}
	
	@RequestMapping("/died")
	public ResponseEntity<String> died(String oldPersonId,HttpServletRequest request) throws Exception {
		try {
			this.oldPersonService.died(oldPersonId,new Customer(request));
			return new ResponseEntity<String>("",HttpStatus.OK);
		} catch(IllegalArgumentException e) {
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.EXPECTATION_FAILED);
		} catch(Exception e) {
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.EXPECTATION_FAILED);
		}
	}
	
	@RequestMapping("/{oldPersonId}")
	public String viewPage(@PathVariable String oldPersonId,Model model) {
		OldPersonEntity oldPerson = this.oldPersonService.getOldPerson(oldPersonId);
		model.addAttribute("oldPerson", oldPerson);
		//体检记录
		return "/oldperson/view";
	}
	
	@RequestMapping("/{oldPersonId}/undo")
	public ResponseEntity<String> undo(@PathVariable String oldPersonId,HttpServletRequest request) {
		try {
			this.oldPersonService.undo(oldPersonId,new Customer(request));
			return new ResponseEntity<String>("",HttpStatus.OK);
		} catch(IllegalArgumentException e) {
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.EXPECTATION_FAILED);
		} catch(Exception e) {
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.EXPECTATION_FAILED);
		}
	}
	
	@RequestMapping("/{oldPersonId}/photo")
	public @ResponseBody byte[] showImg(@PathVariable String oldPersonId){
		OldPersonEntity oldPerson = this.oldPersonService.getOldPerson(oldPersonId);
		byte[] photoByte = oldPerson.getPhoto();
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
	
}
