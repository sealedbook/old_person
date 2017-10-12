package com.esite.framework.organize.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.esite.framework.organize.entity.OrganizeEntity;
import com.esite.framework.organize.entity.OrganizeViewEntity;
import com.esite.framework.organize.service.impl.OrganizeService;
import com.esite.framework.util.StringHelper;

@Controller
@RequestMapping("/organize")
public class OrganizeController {

	@Resource
	private OrganizeService organizeService;
	
	@RequestMapping("/select")
	public String selectOrganizeTreePage() {
		return "/organize/select";
	}
	
	@RequestMapping("/simple/async/load")
	public @ResponseBody Iterable<OrganizeEntity> asyncLoadTreeOrganizeEntity(@RequestParam(required=false) String parentId) {
		Iterable<OrganizeEntity> list = organizeService.loadTreeByParentIdEntity(parentId);
		return list;
	}
	
	@RequestMapping("/async/load")
	public @ResponseBody Iterable<OrganizeViewEntity> asyncLoadTree(@RequestParam(required=false) String parentId) {
		Iterable<OrganizeViewEntity> list = organizeService.loadTreeByParentId(parentId);
		return list;
	}
	
	@RequestMapping("/query")
	public @ResponseBody OrganizeViewEntity loadOrganize(@RequestParam String id) {
		return organizeService.getOrganizeById(id);
	}
	
//	@RequestMapping("/array/query")
//	public @ResponseBody List<OrganizeViewEntity> loadOrganize(String[] idArray) {
//		return organizeService.getOrganizeByIdArray(idArray);
//	}
	
	@RequestMapping("/array/query")
	public @ResponseBody List<OrganizeEntity> loadOrganize(String[] idArray) {
		return organizeService.getOrganizeEntityByIdArray(idArray);
	}
	
	@RequestMapping("/tree/node")
	public @ResponseBody Iterable<OrganizeViewEntity> loadTree(@RequestParam(required=false) String id) {
		if(StringHelper.isEmpty(id)) {
			return organizeService.loadTreeByParentId("");
		}
		return organizeService.loadAllTreeById(id);
	}
	
	@RequestMapping("/tree/multiple/node")
	public @ResponseBody Iterable<OrganizeViewEntity> loadTreeMultipleNode(@RequestParam(required=false) String[] idArray) {
		if(null == idArray || idArray.length <= 0) {
			return organizeService.loadTreeByParentId("");
		}
		return organizeService.loadAllTreeByIdArray(idArray);
	}
	
	@RequestMapping("/addPage")
	public String addPage(String parentId,Model model) {
		model.addAttribute("parentId", parentId);
		return "/organize/addPage";
	}
	
	@RequestMapping("/add")
	public ResponseEntity<String> add(OrganizeEntity organize) {
		//判断同一级下的同名
		try {
			this.organizeService.addNewOrganize(organize);
			return new ResponseEntity<String>(HttpStatus.OK);
		} catch(IllegalArgumentException e) {
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
		} catch(Exception e) {
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping("/editPage")
	public String editPage(String id,Model model) {
		OrganizeViewEntity organize = this.organizeService.getOrganizeById(id);
		model.addAttribute("organize", organize);
		return "/organize/editPage";
	}
	
	@RequestMapping("/edit")
	public ResponseEntity<String> edit(OrganizeEntity organize) {
		//判断同一级下的同名
		try {
			this.organizeService.edit(organize);
			return new ResponseEntity<String>(HttpStatus.OK);
		} catch(IllegalArgumentException e) {
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
		} catch(Exception e) {
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping("/remove")
	public @ResponseBody String remove(String id) {
		try {
			this.organizeService.remove(id);
			return "";
		} catch(IllegalArgumentException e) {
			return e.getMessage();
		}
	}
	
	@RequestMapping("/restore")
	public @ResponseBody String restore(String id) {
		this.organizeService.restore(id);
		return "";
	}
	
}
