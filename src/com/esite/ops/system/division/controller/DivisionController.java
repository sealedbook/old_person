package com.esite.ops.system.division.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.esite.framework.organize.service.impl.OrganizeService;
import com.esite.framework.util.StringHelper;
import com.esite.ops.oldperson.service.impl.OldPersonService;
import com.esite.ops.operator.service.impl.OperatorService;

@Controller
@RequestMapping("/division")
public class DivisionController {
	
	@Resource
	private OldPersonService oldPersonService;
	
	@Resource
	private OperatorService operatorService;
	
	@Resource
	private OrganizeService organizeService;
	
	@RequestMapping("/manager")
	public String manager() {
		return "/system/division/manager";
	}
	
	@RequestMapping("/detail")
	public String detail(@RequestParam(required=false) String areaId,Model model) {
		long operatorNumber = operatorService.count(areaId);
		model.addAttribute("operatorNumber", operatorNumber);
		long oldPersonNumber = oldPersonService.count(areaId);
		model.addAttribute("oldPersonNumber", oldPersonNumber);
		
		if(StringHelper.isNotEmpty(areaId)) {
			model.addAttribute("area", organizeService.getOrganizeByIdForEntity(areaId));
		}
		return "/system/division/detail";
	}
	
}
