package com.esite.framework.security.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.esite.framework.security.service.UserLoginService;
import com.esite.framework.security.service.impl.Security;
import com.esite.framework.user.entity.User;
import com.esite.framework.util.JsonConverter;
import com.esite.framework.util.WebRequestHelper;
import com.esite.ops.operator.service.impl.OperatorSecurityService;

@Controller
@RequestMapping("/security")
public class UserLoginController {
	
	private static Logger logger = Logger.getLogger(UserLoginController.class);
	
	@Autowired
	private UserLoginService userLoginService;
	
	@RequestMapping("/login/page")
	public String loginPage() {
		return "/login";
	}
	
	@RequestMapping("/login")
	public ResponseEntity<String> login(User user,HttpSession session,HttpServletRequest request) {
		String errorMessage = "";
		try {
			user = userLoginService.login(user);
			session.setAttribute(Security.SESSION_USER_KEY, user);
			if(WebRequestHelper.isWebClient(request)) {
				return new ResponseEntity<String>("SUCCESS",HttpStatus.OK);
			}
			String token = user.getId();
			request.getServletContext().setAttribute(OperatorSecurityService.SERVLET_OPERATOR_TOKEN_KEY_PREFIX + token,user);
			Map<String,Object> m = new HashMap<String,Object>();
			m.put("token", token);
			m.put("user", user);
			String responseBodyString = JsonConverter.convert(m);
			logger.info("终端已经正常登录.获得令牌:" + token);
			return new ResponseEntity<String>(responseBodyString,HttpStatus.OK);
		} catch(IllegalArgumentException e) {
			e.printStackTrace();
			errorMessage = e.getMessage();
		} catch(Exception e) {
			e.printStackTrace();
			errorMessage = e.getMessage();
		}
		return new ResponseEntity<String>(errorMessage,HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/security/login/page.do";
	}
}