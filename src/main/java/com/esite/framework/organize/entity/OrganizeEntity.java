package com.esite.framework.organize.entity;

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
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.esite.framework.interfaces.IConvertToEasyUITree;

@Entity
@Table(name="SYS_SECURITY_ORG")
public class OrganizeEntity implements IConvertToEasyUITree<OrganizeEntity> {
	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid",strategy="uuid")
	private String id;
	
	@Column(name="NAME")
	private String name;
	
	@Column(name="PARENT_ID")
	private String parentId;
	
//	@ManyToOne(fetch=FetchType.EAGER)
//	@JoinColumn(name="PARENT_ID")
//	@NotFound(action=NotFoundAction.IGNORE)
//	private OrganizeEntity parent;
	
	@Column(name="STATUS")
	private String status;
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name="TYPE")
	private String type;

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
	
//
//	public OrganizeEntity getParent() {
//		return parent;
//	}
//
//	public void setParent(OrganizeEntity parent) {
//		this.parent = parent;
//	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String getText() {
		// TODO Auto-generated method stub
		return this.name;
	}

	@Override
	public String getState() {
		// TODO Auto-generated method stub
		return "closed";
	}

	@Override
	public boolean getChecked() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterable<OrganizeEntity> getChildren() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
