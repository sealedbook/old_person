package com.esite.ops.mission.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Entity
@Table(name="CYCLE_INFO")
public class CycleEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	//@GeneratedValue(generator="system-uuid")
	//@GenericGenerator(name="system-uuid",strategy="uuid")
	private String id;
	
	@Column(name="CYCLE_BEGIN")
	@DateTimeFormat(iso=ISO.DATE)
	private Date cycleBegin;
	
	@Column(name="CYCLE_END")
	@DateTimeFormat(iso=ISO.DATE)
	private Date cycleEnd;
	
	@Column(name="SUBMIT_USER_ID",updatable=false)
	private String submitUserId;
	
	@Column(name="SUBMIT_IPADDRESS",updatable=false)
	private String submitIpAddress;
	
	@Column(name="SUBMIT_DATE_TIME",updatable=false)
	private Date submitDateTime = new Date();
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="NOTICE_ID")
	@NotFound(action=NotFoundAction.IGNORE)
	private NoticeEntity notice;

	public NoticeEntity getNotice() {
		return notice;
	}

	public void setNotice(NoticeEntity notice) {
		this.notice = notice;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getCycleBegin() {
		return cycleBegin;
	}

	public void setCycleBegin(Date cycleBegin) {
		this.cycleBegin = cycleBegin;
	}

	public Date getCycleEnd() {
		return cycleEnd;
	}

	public void setCycleEnd(Date cycleEnd) {
		this.cycleEnd = cycleEnd;
	}

	public String getSubmitUserId() {
		return submitUserId;
	}

	public void setSubmitUserId(String submitUserId) {
		this.submitUserId = submitUserId;
	}

	public String getSubmitIpAddress() {
		return submitIpAddress;
	}

	public void setSubmitIpAddress(String submitIpAddress) {
		this.submitIpAddress = submitIpAddress;
	}

	public Date getSubmitDateTime() {
		return submitDateTime;
	}

	public void setSubmitDateTime(Date submitDateTime) {
		this.submitDateTime = submitDateTime;
	}
	
}
