package com.esite.ops.mission.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.esite.framework.security.entity.Customer;
import com.esite.ops.mission.entity.NoticeEntity;
import com.esite.ops.mission.entity.NoticeQueryVO;

public interface INoticeService {

	/**
	 * 以分页的方式查询通知信息
	 * @param instance 分页信息
	 * @return
	 */
	public Page<NoticeEntity> getNotice(NoticeQueryVO noticeQueryVO,Pageable instance);
	
	/**
	 * 通过通知消息记录的id获得一个通知消息详细信息
	 * @param id 通知消息记录的唯一id
	 * @return
	 */
	public NoticeEntity getNotice(String id);

	/**
	 * 发送通知消息,该方法支持设定行政区划中的操作人员
	 * @param noticeEntity 通知消息实体信息
	 * @param noticeAreaCollection 行政区划编号集合,系统根据该集合查询行符合条件的所有操作人员.<br/>
	 * 							  如果不指定行政区划,可传入null或直接调用2个参数的重载
	 * @param customer HttpRequest对象
	 */
	public void notice(NoticeEntity noticeEntity,List<String> noticeAreaCollection, Customer customer);
	
	/**
	 * 发送通知消息,该方法将发送给所有的操作人员
	 * @param noticeEntity 通知消息实体信息
	 * @param customer HttpRequest对象
	 */
	public void notice(NoticeEntity noticeEntity, Customer customer);

	/**
	 * 根据操作员身份证号获得该操作员所有未签收的通知
	 * @param id
	 * @return
	 */
	public List<NoticeEntity> getUnReceiveNoticeByOperatorIdCard(String operatorIdCard);

	/**
	 * 移除一个通知通告
	 * @param id
	 * @param customer 
	 */
	public void remove(String id, Customer customer);

	/**
	 * 根据操作员身份证，获得已签收的通知消息
	 * @param idCard
	 * @return
	 */
	public List<NoticeEntity> getReceivedNoticeByOperatorIdCard(String idCard);
	
}
