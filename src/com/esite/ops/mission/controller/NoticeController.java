package com.esite.ops.mission.controller;

import java.util.List;

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
import com.esite.ops.mission.entity.NoticeEntity;
import com.esite.ops.mission.entity.NoticeQueryVO;
import com.esite.ops.mission.service.INoticeService;

/**
 * 通知通告
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/notice")
public class NoticeController {
	
	@Autowired
	private INoticeService noticeService;
	
	@RequestMapping("/manager")
	public String managerPage() {
		return "/notice/manager";
	}
	
	@RequestMapping("/list")
	public @ResponseBody PagerResponse<NoticeEntity> list(NoticeQueryVO noticeQueryVO,PagerRequest pageRequest) {
		Page<NoticeEntity> list = noticeService.getNotice(noticeQueryVO,pageRequest.getInstance());
		return new PagerResponse<NoticeEntity>(list);
	}
	
	@RequestMapping("/addPage")
	public String addPage() {
		return "/notice/addPage";
	}
	
	@RequestMapping("/add")
	public ResponseEntity<String> add(NoticeEntity noticeEntity,@RequestParam(required=false) List<String> noticeAreaCollection,HttpServletRequest request) {
		try {
			this.noticeService.notice(noticeEntity,noticeAreaCollection,new Customer(request));
			return new ResponseEntity<String>(HttpStatus.OK);
		} catch(IllegalArgumentException e) {
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping("/view/{id}")
	public String view(@PathVariable String id,Model model) {
		NoticeEntity noticeEntity = this.noticeService.getNotice(id);
		model.addAttribute("noticeEntity", noticeEntity);
		return "/notice/view";
	}
	
}
