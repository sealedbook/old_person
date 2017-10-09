package com.esite.ops.mission.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.esite.framework.util.PagerRequest;
import com.esite.framework.util.PagerResponse;
import com.esite.ops.mission.entity.NoticeReceiveEntity;
import com.esite.ops.mission.service.INoticeReceiveService;

@Controller
@RequestMapping("/notice/{noticeId}/receive")
public class NoticeReceiveController {
	
	@Autowired
	private INoticeReceiveService noticeReceiveService;
	
	@RequestMapping("/list")
	public @ResponseBody PagerResponse<NoticeReceiveEntity> list(PagerRequest pagerRequest,@PathVariable String noticeId) {
		Page<NoticeReceiveEntity> page = noticeReceiveService.getReceiveStatus(noticeId,pagerRequest.getInstance());
		return new PagerResponse<NoticeReceiveEntity>(page);
	}
	
}
