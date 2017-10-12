package com.esite.framework.user.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.esite.framework.security.entity.Customer;
import com.esite.framework.security.entity.Role;
import com.esite.framework.security.service.RoleService;
import com.esite.framework.user.entity.User;
import com.esite.framework.user.service.impl.UserService;
import com.esite.framework.util.PagerRequest;
import com.esite.framework.util.PagerResponse;
import com.esite.framework.util.StringHelper;

@Controller
@RequestMapping("/user")
public class UserController {
	
	private static final String MANAGER_ROLE_ID = "E89E35331C234CAB86439C06AF0F32F9";
	
	@Resource
	private UserService userService;
	
	@Autowired
	private RoleService roleService;
	
	@RequestMapping(value="/up/manager")
	public @ResponseBody String upManager(String id, String idCard) {
		if(StringUtils.hasText(id)) {
			this.roleService.saveUrMap(id, MANAGER_ROLE_ID);
		}
		this.roleService.saveUrMapForIdCard(idCard, MANAGER_ROLE_ID);
		return "SUCCESS";
	}
	
	@RequestMapping(value="/down/manager")
	public @ResponseBody String downManager(String id, String idCard) {
		if(StringUtils.hasText(id)) {
			this.roleService.deleteUrMap(id, MANAGER_ROLE_ID);
		}
		this.roleService.deleteUrMapForIdCard(idCard, MANAGER_ROLE_ID);
		return "SUCCESS";
	}
	
	@RequestMapping(value="/list")
	public @ResponseBody PagerResponse<User> list(PagerRequest pagerRequest){
		Page<User> page = this.userService.getUserCollection(pagerRequest.getInstance());
		return new PagerResponse<User>(page);
	}
	
	@RequestMapping(value="/list/page")
	public String listPage(){
		return "/system/user/list";
	}
	@RequestMapping("/addPage")
	public String addPage(Model model){
		 List<Role> roleCollection = roleService.getAllRoleInSystem();
		 model.addAttribute("roleCollection", roleCollection);
		return "/system/user/addPage";
	}
	@RequestMapping(value="/add")
	public @ResponseBody String add(User user,String[] roleId){
		this.userService.save(user,roleId);
		return "redirect:/user/addPage.do";
	}
	@RequestMapping("/updatePage")
	public String updatePage(@RequestParam String id,Model model){
		model.addAttribute("user",this.userService.getUserById(id));
		return "/system/user/updatePage";
	}
	@RequestMapping("/update")
	public @ResponseBody String update(User user){
		this.userService.update(user);
		return "redirect:/user/updatePage.do?id="+user.getId()+"";
	}
	@RequestMapping("/delete")
	public @ResponseBody void delete(@RequestParam String[] ids){
		this.userService.delete(ids);
	}
	@RequestMapping(value="/editPasswordPage")
	public String editPage(){
		return "/system/user/editPass";
	}
	@RequestMapping(value="/editPassword")
	public ResponseEntity<String> updatePass(String oldPassword,String newPassword,HttpServletRequest request){
		Customer  customer = new Customer(request);
		User customerUser= customer.getUser();
		if(StringHelper.isNotEmpty(oldPassword) && StringHelper.isNotEmpty(newPassword)){
			if(customerUser.getPassword().equals(StringHelper.getMd5(oldPassword))){
				customerUser.setPassword(StringHelper.getMd5(newPassword));
				return new ResponseEntity<String>("",HttpStatus.OK);
			}
		}	
		return new ResponseEntity<String>("您输入的原密码错误",HttpStatus.FORBIDDEN);
	}
	
	@ResponseBody
	@RequestMapping("/modifyPassword")
	/**
	 * 
	 * @param oldPassword 原密码
	 * @param newPassword 新密码
	 * @param request
	 * @return
	 */
	public Map<String,Object> updatePassJson(String oldPassword,String newPassword,HttpServletRequest request){
		Map<String,Object> info = new HashMap<String, Object>();
		info.put("flag", false);
		try {
			Customer  customer = new Customer(request);
			User customerUser= customer.getUser();
			if(StringHelper.isNotEmpty(oldPassword) && StringHelper.isNotEmpty(newPassword)){
				if(customerUser.getPassword().equals(StringHelper.getMd5(oldPassword))){
					customerUser.setPassword(StringHelper.getMd5(newPassword));
					//this.userService.editPassword(customerUser);
					info.put("flag", true);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			info.put("flag", false);
			info.put("msg", e.getMessage());
		}	
		 return info;
	}
	
	@RequestMapping("/find")
	public @ResponseBody User findUserById(@RequestParam String id) {
		return this.userService.getUserById(id);
	}
	
}
