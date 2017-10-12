package com.esite.ops.mission.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.esite.framework.security.entity.Customer;
import com.esite.framework.util.PagerRequest;
import com.esite.framework.util.PagerResponse;
import com.esite.ops.mission.entity.CycleEntity;
import com.esite.ops.mission.entity.NoticeEntity;
import com.esite.ops.mission.service.impl.CycleService;

/**
 * 周期管理
 *
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/cycle")
public class CycleController {

	@Resource
	private CycleService cycleService;
	
	@RequestMapping("/manager")
	public String managerPage() {
		return "/cycle/manager";
	}
	
	@RequestMapping("/list")
	public @ResponseBody PagerResponse<CycleEntity> list(PagerRequest request) {
		Page<CycleEntity> page = cycleService.getCycle(request.getInstance());
		return new PagerResponse<CycleEntity>(page);
	}
	
	@RequestMapping("/addPage")
	public String addPage() {
		return "/cycle/addPage";
	}
	
	@RequestMapping("/noticePage")
	public String noticePage(@RequestParam String cycleId,Model model) {
		model.addAttribute("cycle", this.cycleService.getCycle(cycleId));
		return "/cycle/cycleNotice";
	}
	
	@RequestMapping("/changePage")
	public String changePage(String cycleId,Model model) {
		CycleEntity cycle = this.cycleService.getCycle(cycleId);
		model.addAttribute("cycle", cycle);
		return "/cycle/changePage";
	}
	
	@RequestMapping("/{cycleId}/notice")
	public ResponseEntity<String> notice(@PathVariable String cycleId,NoticeEntity notice,@RequestParam(required=false) List<String> noticeAreaCollection,HttpServletRequest request) {
		try {
			this.cycleService.notice(cycleId,notice,noticeAreaCollection,new Customer(request));
			return new ResponseEntity<String>(HttpStatus.OK);
		} catch(IllegalArgumentException e) {
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping("/add")
	public ResponseEntity<String> add(CycleEntity cycleEntity,HttpServletRequest request) {
		try {
			cycleService.add(cycleEntity,new Customer(request));
			return new ResponseEntity<String>(cycleEntity.getId(), HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping("/change")
	public ResponseEntity<String> change(CycleEntity cycleEntity,HttpServletRequest request) {
		try {
			cycleService.change(cycleEntity,new Customer(request));
			return new ResponseEntity<String>(cycleEntity.getId(), HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
}
