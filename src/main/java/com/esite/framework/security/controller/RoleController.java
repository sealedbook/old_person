package com.esite.framework.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.esite.framework.security.entity.Role;
import com.esite.framework.security.service.RoleService;
import com.esite.framework.util.PagerRequest;
import com.esite.framework.util.PagerResponse;

@Controller
@RequestMapping("/role")
public class RoleController {
	@Autowired
	private RoleService roleService;
	
	@RequestMapping(value="/list")
	public String list(){
		return "/system/role/list";
	}
	
	@RequestMapping(value="/listForPage")
	public @ResponseBody PagerResponse<Role> listForPage(PagerRequest pagerRequest){
		Page<Role> p = roleService.queryRoleList(pagerRequest.getInstance());
		return new PagerResponse<Role>(p);
	}
	
	@RequestMapping(value="/addPage")
	public String saveRolePage(){
		return "/system/role/addPage";
	}
	@RequestMapping(value="/save")
	public ResponseEntity<String> saveRole(Role role){
		boolean b =this.roleService.findRoleByName(role.getName());
		if(b == false){
			this.roleService.saveRole(role);
			return new ResponseEntity<String>("操作成功",HttpStatus.OK);
		}
		return new ResponseEntity<String>("名称 "+role.getName()+" 已存在",HttpStatus.OK);
	}
	@RequestMapping(value="/updatePage")
	public String updateRolePage(Model model,@RequestParam String id){
		model.addAttribute("role", this.roleService.getRoleByRoleId(id));
		return "/system/role/updatePage";
	}
	@RequestMapping(value="/update")
	public String updateRole(Role role){
		this.roleService.updateRole(role);
		return "redirect:/role/updatePage.do?id="+role.getId()+"";
	}
	@RequestMapping("/delete")
	public @ResponseBody void delete(@RequestParam String[] ids){
		this.roleService.delete(ids);
	}
	/**
	 * 分配角色页面
	 * @param ids
	 * @param model
	 * @return
	 */
	@RequestMapping("/rolePage")
	public String rolePage(@RequestParam String[] ids,Model model){
		model.addAttribute("ids", ids);
		return "/system/role/rolePage";
	}

}
