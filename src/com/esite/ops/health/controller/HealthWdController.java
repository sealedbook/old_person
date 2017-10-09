package com.esite.ops.health.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.esite.framework.security.entity.Customer;
import com.esite.framework.util.PagerRequest;
import com.esite.framework.util.PagerResponse;
import com.esite.framework.util.StringHelper;
import com.esite.ops.health.entity.HealthFingerprintEntity;
import com.esite.ops.health.entity.HealthInfoEntity;
import com.esite.ops.health.entity.HealthPhotoEntity;
import com.esite.ops.health.entity.OldPersonWdVerifyInfoEntity;
import com.esite.ops.health.entity.OldPersonWdVerifyInfoQueryVO;
import com.esite.ops.health.entity.OldPersonWdVerifyPhotoEntity;
import com.esite.ops.health.service.IOldPersonWdVerifyService;
import com.esite.ops.health.vo.HealthInfoQueryVO;
import com.esite.ops.oldperson.entity.OldPersonEntity;

@Controller
@RequestMapping("/health/wd")
public class HealthWdController {
	
	@Autowired
	private IOldPersonWdVerifyService oldPersonWdVerifyService;
	
	@RequestMapping(value="/verify/manager")
	public String manager() {
		return "/health/wd/manager";
	}
	
	@RequestMapping(value="/list")
	public @ResponseBody PagerResponse<OldPersonWdVerifyInfoEntity> list(String oldPersonId,PagerRequest pageRequest) {
		Page<OldPersonWdVerifyInfoEntity> page = oldPersonWdVerifyService.getVerifyHealthByOldPersonId(oldPersonId,pageRequest.getInstance());
		return new PagerResponse<OldPersonWdVerifyInfoEntity>(page);
	}
	@RequestMapping(value="/verify/list")
	public @ResponseBody PagerResponse<OldPersonWdVerifyInfoEntity> list(OldPersonWdVerifyInfoQueryVO oldPersonWdVerifyInfoQueryVO,PagerRequest pageRequest) {
		Page<OldPersonWdVerifyInfoEntity> page = oldPersonWdVerifyService.getVerifyHealth(oldPersonWdVerifyInfoQueryVO,pageRequest.getInstance());
		return new PagerResponse<OldPersonWdVerifyInfoEntity>(page);
	}
	
	@RequestMapping(value="/verify/{verifyId}/view")
	public String view(@PathVariable String verifyId,Model model) {
		OldPersonWdVerifyInfoEntity oldPersonWdVerifyInfo = oldPersonWdVerifyService.getVerifyInfoById(verifyId);
		model.addAttribute("oldPersonWdVerifyInfo", oldPersonWdVerifyInfo);
		model.addAttribute("oldPerson", oldPersonWdVerifyInfo.getOldPerson());
		
		String nextVerifyId = oldPersonWdVerifyService.getNextVerifyInfoId(verifyId);
		model.addAttribute("nextVerifyId", nextVerifyId);

		List<OldPersonWdVerifyPhotoEntity> verifyPhotoCollection = oldPersonWdVerifyService.getPhotoCollectionByVerifyId(oldPersonWdVerifyInfo.getId());
		model.addAttribute("verifyPhotoCollection", verifyPhotoCollection);
		
		OldPersonEntity oldPerson = oldPersonWdVerifyInfo.getOldPerson();
		if(null != oldPerson) {
			OldPersonWdVerifyInfoEntity firstVerifyInfo = this.oldPersonWdVerifyService.getFirstHealthByOldPersonId(oldPerson.getId());
			if(null == firstVerifyInfo) {
				model.addAttribute("firstVerifyPhotoCollection", verifyPhotoCollection);
			} else {
				List<OldPersonWdVerifyPhotoEntity> firstVerifyPhotoCollection = oldPersonWdVerifyService.getPhotoCollectionByVerifyId(firstVerifyInfo.getId());
				model.addAttribute("firstVerifyPhotoCollection", firstVerifyPhotoCollection);
			}
		}
		return "/health/wd/view";
	}
	
	@RequestMapping(value="/verify/next/{verifyId}")
	public String next(String verifyState,@PathVariable String verifyId,HttpServletRequest request,Model model) {
		String nextVerifyId = oldPersonWdVerifyService.getNextVerifyInfoId(verifyId);
		if(StringHelper.isEmpty(nextVerifyId)) {
			nextVerifyId = verifyId;
		}
		if(StringHelper.isNotEmpty(verifyId) && StringHelper.isNotEmpty(verifyState)) {
			this.oldPersonWdVerifyService.updateVerifyState(verifyId,verifyState,new Customer(request));
		}
		return "redirect:/health/wd/verify/" + nextVerifyId + "/view.do";
	}
	
	@RequestMapping(value="/verify")
	public @ResponseBody String verify(@RequestParam String verifyId,@RequestParam String verifyState,HttpServletRequest request) {
		this.oldPersonWdVerifyService.updateVerifyState(verifyId,verifyState,new Customer(request));
		//return "redirect:/health/wd/verify/" + verifyId + "/view.do";
		return "OK";
	}
	

	@RequestMapping("/face/photo")
	public @ResponseBody byte[] facePhoto(@RequestParam String id){
		OldPersonWdVerifyPhotoEntity oldPersonWdVerifyPhoto = oldPersonWdVerifyService.getFacePhotoById(id);
		if(null == oldPersonWdVerifyPhoto) {
			return null;
		}
		return oldPersonWdVerifyPhoto.getPhotoFile();
	}
}
