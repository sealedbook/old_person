package com.esite.ops.health.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.esite.framework.security.entity.Customer;
import com.esite.ops.health.service.IHealthInfoService;
import com.esite.ops.oldperson.service.impl.OldPersonService;

@Controller
@RequestMapping("/http/health")
public class HealthStatisticsHttpInterface {
	
	@Autowired
	private IHealthInfoService healthInfoService;
	@Resource
	private OldPersonService oldPersonService;
	
	@RequestMapping("/oldperson/statistics")
	public @ResponseBody Map<String,Object> statisticsOldPerson(HttpServletRequest request) {
		Map<String,Object> result = new HashMap<String, Object>();
		try {
			Map<String,Object> oldPersonHealthInfoStatistics = healthInfoService.statisticsHealthInfoByOperatorIdCard(new Customer(request).getUser().getIdCard());
			if(null == oldPersonHealthInfoStatistics.get("total")) {
				oldPersonHealthInfoStatistics.put("total", 0);
			}
			if(null == oldPersonHealthInfoStatistics.get("auth_fail")) {
				oldPersonHealthInfoStatistics.put("auth_fail", 0);
			}
			if(null == oldPersonHealthInfoStatistics.get("auth_suc")) {
				oldPersonHealthInfoStatistics.put("auth_suc", 0);
			}
			result.put("oldPersonHealthInfoStatistics", oldPersonHealthInfoStatistics);
			List<Map<String,Object>> list = oldPersonService.getFinishAuthOldPersonWithOperatorIdCard(new Customer(request).getUser().getIdCard());
			result.put("finishAuthList", list);
			result.put("responseStatus", "OK");
		} catch(IllegalArgumentException e) {
			result.put("responseStatus", "ERROR");
			result.put("errorMessage", e.getMessage());
		}
		return result;
	}
	
}
