package com.esite.ops.operator.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.esite.framework.organize.entity.OrganizeViewEntity;
import com.esite.framework.organize.service.impl.OrganizeService;
import com.esite.ops.operator.entity.OperatorEntity;
import com.esite.ops.operator.service.impl.AreaConfigService;
import com.esite.ops.operator.service.impl.OperatorService;

@Controller
@RequestMapping("/operator/page")
public class OperatorRequestPageController {

	@Resource
	private OperatorService operatorService;
	@Resource
	private AreaConfigService areaManageService;
	@Resource
	private OrganizeService organizeService;
	
	@RequestMapping("/manager")
	public String managerPage() {
		return "/operator/manager";
	}
	
	@RequestMapping("/addPage")
	public String addPage() {
		return "/operator/addPage";
	}
	
	@RequestMapping("/{operatorId}/updatePage")
	public String updatePage(@PathVariable String operatorId,Model model) {
		OperatorEntity operator = operatorService.getOperator(operatorId);
		model.addAttribute("operator", operator);
		List<String> areaIdArray = areaManageService.getAreaIdCollectionWithOperatorId(operatorId);
		model.addAttribute("areaIdArray", areaIdArray.toString().replaceAll("\\[", "").replaceAll("\\]", ""));
		return "/operator/updatePage";
	}
	
	@RequestMapping("/{operatorId}/view")
	public String view(@PathVariable String operatorId,Model model) {
		OperatorEntity operator = operatorService.getOperator(operatorId);
		model.addAttribute("operator", operator);
		List<String> areaIdArray = areaManageService.getAreaIdCollectionWithOperatorId(operatorId);
		StringBuffer organizeName = new StringBuffer();
		for(String areaId : areaIdArray) {
			OrganizeViewEntity org = this.organizeService.getOrganizeById(areaId);
			if(null != org) {
				organizeName.append(org.getName()).append(" ");
			}
		}
		model.addAttribute("organizeName", organizeName);
		return "/operator/view";
	}
}
