package com.esite.ops.mission.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.esite.ops.mission.entity.CycleEntity;
import com.esite.ops.mission.service.impl.CycleService;

@Controller
@RequestMapping("/http/cycle")
public class CycleHttpInterface {

	@Resource
	private CycleService cycleService;
	
	@RequestMapping("/current")
	public @ResponseBody Map<String,Object> currentCycle() {
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			CycleEntity cycle = cycleService.getCycle(new Date());
			map.put("cycle", cycle);
			map.put("responseStatus", "OK");
		} catch(IllegalArgumentException e) {
			map.put("errorMessage", e.getMessage());
			map.put("responseStatus", "ERROR");
		}
		return map;
	}
	
}
