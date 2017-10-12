package com.esite.framework.security.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.esite.framework.security.entity.Role;
import com.esite.framework.security.entity.SystemFunction;
import com.esite.framework.security.service.FunctionService;
import com.esite.framework.security.service.RoleService;

@Controller
@RequestMapping("/function")
public class SystemFunctionController {
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private FunctionService functionService;
	
	@RequestMapping(value="/{roleId}")
	public @ResponseBody List<SystemFunction> userQx(@PathVariable String roleId){
		Role role = roleService.getRoleByRoleId(roleId);
		List<SystemFunction> roleSystemFunction = role.getFunctionCollection();
		return roleSystemFunction;
	}
	@RequestMapping(value="/save")
	public @ResponseBody String saveQx(@RequestParam String roleId,@RequestParam String[] functionId){
		this.functionService.save(roleId, functionId);
		return "SUCCESS";
	}
}
