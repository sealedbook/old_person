package com.esite.framework.cache.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.esite.framework.dictionary.service.DictionaryService;
import com.esite.framework.security.service.SystemMenuService;
import com.esite.framework.security.service.ResourceService;

@Controller
@RequestMapping("/cache")
public class CacheController {
	
	@Autowired
	private SystemMenuService systemMenuService;
	
	@Autowired
	private ResourceService resourceService;
	
	@Autowired
	private DictionaryService dictionaryService;
	
	@RequestMapping("/index")
	public String index() {
		return "/cache/index";
	}
	
	@RequestMapping("/flush/menu")
	public ResponseEntity<String> flushMenu() {
		systemMenuService.initMenuWithRole();
		return new ResponseEntity<String>("SUCCESS",HttpStatus.OK);
	}
	
	@RequestMapping("/flush/global/resource")
	public ResponseEntity<String> flushGlobalResource() {
		resourceService.initGlobalResource();
		return new ResponseEntity<String>("SUCCESS",HttpStatus.OK);
	}
	
	@RequestMapping("/flush/dictionary")
	public ResponseEntity<String> flushDictionary() {
		dictionaryService.flashDictionary();
		return new ResponseEntity<String>("SUCCESS",HttpStatus.OK);
	}
	
}
