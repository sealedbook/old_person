package com.esite.ops.health.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.esite.ops.oldperson.entity.OldPersonEntity;

public class OldPersonWdVerifyInfoQueryVO {
	
	private String verifyState = "-1";
	private String oldPersonName;
	private String oldPersonIdCard;
	
	public String getOldPersonIdCard() {
		return oldPersonIdCard;
	}
	public void setOldPersonIdCard(String oldPersonIdCard) {
		this.oldPersonIdCard = oldPersonIdCard;
	}
	public String getVerifyState() {
		return verifyState;
	}
	public void setVerifyState(String verifyState) {
		this.verifyState = verifyState;
	}
	public String getOldPersonName() {
		return oldPersonName;
	}
	public void setOldPersonName(String oldPersonName) {
		this.oldPersonName = oldPersonName;
	}
	
}
