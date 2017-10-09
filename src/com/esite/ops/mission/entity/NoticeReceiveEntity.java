package com.esite.ops.mission.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="NOTICE_RECEIVE")
@JsonIgnoreProperties(value={"noticeEntity"})
public class NoticeReceiveEntity {

	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid",strategy="uuid")
	private String id;
	
	@Column(name="RECEIVE_USER_ID")
	private String receiveUserId;
	
	@Column(name="RECEIVE_DATE_TIME")
	private Date receiveDateTime;
	
	@Column(name="RECEIVE_DEVICE_NUMBER")
	private String receiveDeviceNumber;
	
	@Column(name="RECEIVE_IP_ADDRESS")
	private String receiveIpAddress;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="NOTICE_ID")
	private NoticeEntity noticeEntity;

	public NoticeEntity getNoticeEntity() {
		return noticeEntity;
	}

	public void setNoticeEntity(NoticeEntity noticeEntity) {
		this.noticeEntity = noticeEntity;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getReceiveIpAddress() {
		return receiveIpAddress;
	}

	public void setReceiveIpAddress(String receiveIpAddress) {
		this.receiveIpAddress = receiveIpAddress;
	}

	public String getReceiveUserId() {
		return receiveUserId;
	}

	public void setReceiveUserId(String receiveUserId) {
		this.receiveUserId = receiveUserId;
	}

	public Date getReceiveDateTime() {
		return receiveDateTime;
	}

	public void setReceiveDateTime(Date receiveDateTime) {
		this.receiveDateTime = receiveDateTime;
	}

	public String getReceiveDeviceNumber() {
		return receiveDeviceNumber;
	}

	public void setReceiveDeviceNumber(String receiveDeviceNumber) {
		this.receiveDeviceNumber = receiveDeviceNumber;
	}
	
}
