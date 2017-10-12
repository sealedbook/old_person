package com.esite.framework.security.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.esite.framework.security.entity.SystemMenu;
import com.esite.framework.security.service.SystemMenuService;
import com.esite.framework.security.service.impl.Security;
import com.esite.framework.user.entity.User;

@Controller
public class SystemMenuController {

	@Autowired
	private SystemMenuService systemMenuService;
	
	@RequestMapping(value="/system/security/manager")
	public String userQx(Model model){
		List<SystemMenu> systemMenu = systemMenuService.getSystemMenu();
		model.addAttribute("menu", systemMenu);
		return "/system/security/manager";
	}
	
	@RequestMapping(value="/system/menu/load")
	public @ResponseBody List<SystemMenu> loadMenus(HttpSession session) {
		User user = (User)session.getAttribute(Security.SESSION_USER_KEY);
		List<SystemMenu> menu = systemMenuService.getMenu(user);
		return menu;
	}
	
}
