package com.esite.framework.security.entity;

import java.io.Serializable;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
@Entity
@Table(name="SYS_SECURITY_ROLE")
public class Role implements Serializable {
	
	@Transient
	private static final long serialVersionUID = 5750931893955904474L;
	
	@Id
	@Column(name="ID")
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid",strategy="uuid")
	private String id;
	
	@Column(name="ROLE_NAME")
	private String name;
	
	@Transient
	private List<String> requestUrlCollection;
	
	@Transient
	private List<SystemFunction> functionCollection;
	
	public List<SystemFunction> getFunctionCollection() {
		return functionCollection;
	}
	public void setFunctionCollection(List<SystemFunction> functionCollection) {
		this.functionCollection = functionCollection;
	}
	public List<String> getRequestUrlCollection() {
		return requestUrlCollection;
	}
	public void setRequestUrlCollection(List<String> requestUrlCollection) {
		this.requestUrlCollection = requestUrlCollection;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
