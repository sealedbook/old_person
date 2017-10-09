package com.esite.ops.mission.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="NOTICE_INFO")
@JsonIgnoreProperties(value={"noticeReceiveStatus"})
public class NoticeEntity {

	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid",strategy="uuid")
	private String id;
	
	@Column(name="content")
	private String content;

	@Column(name="submit_date_time",updatable=false)
	private Date submitDateTime = new Date();
	
	@Column(name="submit_user_id",updatable=false)
	private String submitUserId;
	
	@Column(name="submit_ipaddress",updatable=false)
	private String submitIpAddress;
	
	@Column(name="STATUS")
	private String status = "";
	
	@OneToMany(fetch=FetchType.EAGER,mappedBy="noticeEntity")
	private List<NoticeReceiveEntity> noticeReceiveStatus;
	
	public String getReceiveStatus() {
		if(null == noticeReceiveStatus) {
			return "没有找到任何签收记录.这可能是系统异常引起.";
		} else {
			StringBuffer sb = new StringBuffer();
			int receiveCount = 0;
			for(NoticeReceiveEntity noticeReceive : noticeReceiveStatus) {
				if(null != noticeReceive.getReceiveDateTime()) {
					++receiveCount;
				}
			}
			sb.append(receiveCount).append("/").append(noticeReceiveStatus.size()).append("人签收");
			return sb.toString();
		}
	}

	
	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public List<NoticeReceiveEntity> getNoticeReceiveStatus() {
		return noticeReceiveStatus;
	}

	public void setNoticeReceiveStatus(List<NoticeReceiveEntity> noticeReceiveStatus) {
		this.noticeReceiveStatus = noticeReceiveStatus;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getSubmitDateTime() {
		return submitDateTime;
	}

	public void setSubmitDateTime(Date submitDateTime) {
		this.submitDateTime = submitDateTime;
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
}
