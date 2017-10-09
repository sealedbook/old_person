package com.esite.ops.oldperson.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.esite.framework.security.entity.Customer;
import com.esite.framework.security.service.impl.Security;
import com.esite.ops.oldperson.entity.OldPersonEntity;
import com.esite.ops.oldperson.service.IOldPersonService;

@Controller
@RequestMapping("/oldperson")
public class OldPersonRequestPageController {
	
	@Autowired
	private IOldPersonService oldPersonService;
	
	@RequestMapping("/manager")
	public String managerPage() {
		return "/oldperson/manager";
	}
	
	@RequestMapping("/importPage")
	public String importPage() {
		return "/oldperson/import";
	}
	
	@RequestMapping("/addPage")
	public String addPage() {
		return "/oldperson/addPage";
	}
	
	@RequestMapping("/{oldPersonId}/updatePage")
	public String updatePage(@PathVariable String oldPersonId,Model model,HttpServletRequest request) {
		OldPersonEntity oldPerson = oldPersonService.getOldPerson(oldPersonId);
		model.addAttribute("oldPerson", oldPerson);
		model.addAttribute("updateIdCardable", Security.isRight("LNR_IDCARDXG",new Customer(request).getUser()));
		return "/oldperson/updatePage";
	}
}
