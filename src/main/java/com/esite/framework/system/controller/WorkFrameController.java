package com.esite.framework.system.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.esite.framework.core.factory.WebApplicationContextUtil;
import com.esite.framework.security.entity.Customer;
import com.esite.framework.security.entity.SystemMenu;
import com.esite.framework.security.service.SystemMenuService;
import com.esite.framework.security.service.impl.Security;
import com.esite.framework.user.entity.User;

@Controller
@RequestMapping("/work")
public class WorkFrameController {

	@Autowired
	private SystemMenuService systemMenuService;
	
	@RequestMapping("/frame")
	public String framePage(HttpSession session,HttpServletRequest request,Model model) {
		model.addAttribute("customer", new Customer(request));
		//System.out.println(WebApplicationContextUtil.getMessage("hello", request));
		return "/frame/frame";
	}
	
	@RequestMapping("/frame/leftPage")
	public String frameLeftPage(HttpServletRequest request,Model model) {
		model.addAttribute("customer", new Customer(request));
		model.addAttribute("loginTime",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		return "/frame/left";
	}
	
	@RequestMapping("/frame/rightPage")
	public String frameRightPage() {
		return "/frame/right";
	}
	
	@RequestMapping("/frame/menus")
	public String frameMenusPage(HttpSession session,Model model) {
		User user = (User)session.getAttribute(Security.SESSION_USER_KEY);
		List<SystemMenu> menu = systemMenuService.getMenu(user);
		model.addAttribute("menus", menu);
		return "/frame/menus";
	}
}
