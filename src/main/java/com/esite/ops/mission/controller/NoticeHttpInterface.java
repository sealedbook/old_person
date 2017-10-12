package com.esite.ops.mission.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.esite.framework.security.entity.Customer;
import com.esite.ops.mission.entity.NoticeEntity;
import com.esite.ops.mission.service.impl.NoticeReceiveService;
import com.esite.ops.mission.service.impl.NoticeService;
import com.esite.ops.operator.service.impl.OperatorSecurityService;

@Controller
@RequestMapping("/http/notice")
public class NoticeHttpInterface {

	@Resource
	private NoticeService noticeService;
	
	@Resource
	private NoticeReceiveService noticeReceiveService;
	
	/**
	 * 获得未签收的通知通告
	 * @param request
	 * @return
	 */
	@RequestMapping("/unReceiveNotice")
	public @ResponseBody Map<String,Object> unReceiveNotice(HttpServletRequest request) {
		Customer customer = new Customer(request);
		Map<String,Object> resultMap = new HashMap<String, Object>();
		try {
			List<NoticeEntity> unReceiveNoticeCollection = noticeService.getUnReceiveNoticeByOperatorIdCard(customer.getUser().getIdCard());
			resultMap.put("responseStatus", "OK");
			resultMap.put("unReceiveList", unReceiveNoticeCollection);
			return resultMap;
		} catch(IllegalArgumentException e) {
			resultMap.put("responseStatus", "ERROR");
			resultMap.put("unReceiveList", "");
			resultMap.put("errorMessage", e.getMessage());
		}
		return resultMap;
	}
	
	/**
	 * 获得已签收的通知通告
	 * @param request
	 * @return
	 */
	@RequestMapping("/receivedNotice")
	public @ResponseBody Map<String,Object> allNotice(HttpServletRequest request) {
		Customer customer = new Customer(request);
		Map<String,Object> resultMap = new HashMap<String, Object>();
		try {
			List<NoticeEntity> alloticeCollection = noticeService.getReceivedNoticeByOperatorIdCard(customer.getUser().getIdCard());
			resultMap.put("responseStatus", "OK");
			resultMap.put("unReceiveList", alloticeCollection);
			return resultMap;
		} catch(IllegalArgumentException e) {
			resultMap.put("responseStatus", "ERROR");
			resultMap.put("unReceiveList", "");
			resultMap.put("errorMessage", e.getMessage());
		}
		return resultMap;
	}
	
	@RequestMapping("/receive")
	public @ResponseBody Map<String,Object> receive(String noticeId,HttpServletRequest request) {
		Map<String,Object> resultMap = new HashMap<String, Object>();
		try {
			String imei = request.getParameter(OperatorSecurityService.TERMINAL_IMEI);
			noticeReceiveService.receive(noticeId,imei,new Customer(request));
			resultMap.put("responseStatus", "OK");
		} catch(IllegalArgumentException e) {
			resultMap.put("responseStatus", "ERROR");
			resultMap.put("errorMessage", e.getMessage());
		}
		return resultMap;
	}
	
}