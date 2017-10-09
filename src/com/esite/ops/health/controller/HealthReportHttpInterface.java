package com.esite.ops.health.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.esite.ops.health.entity.HealthInfoEntity;
import com.esite.ops.health.entity.HealthResultEntity;
import com.esite.ops.health.service.IHealthInfoService;
import com.esite.ops.oldperson.entity.OldPersonEntity;
import com.esite.ops.oldperson.service.IOldPersonService;
import com.esite.ops.operator.entity.OperatorEntity;

@Controller
@RequestMapping("/http/health")
public class HealthReportHttpInterface {

	@Autowired
	private IHealthInfoService healthInfoService;
	@Autowired
	private IOldPersonService oldPersonService;
	
	@RequestMapping("/{oldPersonId}/report/view")
	public String reportOnlineView(@PathVariable String oldPersonId,Model model) {
		HealthInfoEntity health = healthInfoService.getLastHealthByOldPersonId(oldPersonId);
		if(null != health) {
			OperatorEntity operator = health.getOperator();
			OldPersonEntity oldPerson = health.getOldPerson();
			HealthResultEntity healthResult = health.getHealthResult();
			
			model.addAttribute("operator", operator);
			model.addAttribute("health", health);
			model.addAttribute("oldPerson", oldPerson);
			model.addAttribute("healthResult",healthResult);
			return "/health/report/httpview";
		}
		OldPersonEntity oldPerson = this.oldPersonService.getOldPerson(oldPersonId);
		model.addAttribute("oldPerson", oldPerson);
		return "/health/report/noview";
	}
	
}
