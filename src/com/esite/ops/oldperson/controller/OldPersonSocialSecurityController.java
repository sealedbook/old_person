package com.esite.ops.oldperson.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.esite.framework.security.entity.Customer;
import com.esite.framework.util.PagerRequest;
import com.esite.framework.util.PagerResponse;
import com.esite.ops.oldperson.entity.OldPersonEntity;
import com.esite.ops.oldperson.entity.OldPersonQueryEntity;
import com.esite.ops.oldperson.entity.OldPersonSocialSecurityEntity;
import com.esite.ops.oldperson.entity.OldPersonSocialSecurityQueryVO;
import com.esite.ops.oldperson.service.IOldPersonSocialSecurityImportLogService;
import com.esite.ops.oldperson.service.IOldPersonSocialSecurityService;

/**
 * 老年人社保
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/oldperson/social/security")
public class OldPersonSocialSecurityController {

	@Autowired
	private IOldPersonSocialSecurityService oldPersonSocialSecurityService;
	
	@RequestMapping("/manager")
	public String manager() {
		return "/oldperson/socialSecurity/manager";
	}
	
	@RequestMapping("/importPage")
	public String importPage() {
		return "/oldperson/socialSecurity/import";
	}
	
	@RequestMapping(value="/import",method=RequestMethod.POST)
	public String importOldPerson(@RequestParam MultipartFile file,Model model,HttpServletRequest request) {
		List<String> errorMessage = this.oldPersonSocialSecurityService.importExcelFile(file,new Customer(request));
		model.addAttribute("errorMessage", errorMessage);
		if(null == errorMessage || errorMessage.size() <= 0) {
			model.addAttribute("status", "success");
		}
		return "/oldperson/socialSecurity/import";
	}
	
	@RequestMapping("/list")
	public @ResponseBody PagerResponse<OldPersonSocialSecurityEntity> list(OldPersonSocialSecurityQueryVO oldPersonSocialSecurityQueryVO,PagerRequest pageRequest) {
		Page<OldPersonSocialSecurityEntity> p = this.oldPersonSocialSecurityService.find(oldPersonSocialSecurityQueryVO, pageRequest.getInstance());
		return new PagerResponse<OldPersonSocialSecurityEntity>(p);
	}
}
