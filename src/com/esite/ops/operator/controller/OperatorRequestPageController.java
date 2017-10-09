package com.esite.ops.operator.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.esite.framework.organize.entity.OrganizeViewEntity;
import com.esite.framework.organize.service.OrganizeService;
import com.esite.ops.operator.entity.OperatorEntity;
import com.esite.ops.operator.service.IAreaConfigService;
import com.esite.ops.operator.service.IOperatorService;

@Controller
@RequestMapping("/operator/page")
public class OperatorRequestPageController {

	@Autowired
	private IOperatorService operatorService;
	@Autowired
	private IAreaConfigService areaConfigService;
	@Autowired
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
		List<String> areaIdArray = areaConfigService.getAreaIdCollectionWithOperatorId(operatorId);
		model.addAttribute("areaIdArray", areaIdArray.toString().replaceAll("\\[", "").replaceAll("\\]", ""));
		return "/operator/updatePage";
	}
	
	@RequestMapping("/{operatorId}/view")
	public String view(@PathVariable String operatorId,Model model) {
		OperatorEntity operator = operatorService.getOperator(operatorId);
		model.addAttribute("operator", operator);
		List<String> areaIdArray = areaConfigService.getAreaIdCollectionWithOperatorId(operatorId);
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
