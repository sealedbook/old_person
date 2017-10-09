package com.esite.framework.organize.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.esite.framework.interfaces.IConvertToEasyUITree;

@Entity
@Table(name="VW_AREA")
@JsonIgnoreProperties(value={"parent","rootOrganize"})
public class OrganizeViewEntity implements java.io.Serializable,IConvertToEasyUITree<OrganizeViewEntity> {

	private static final long serialVersionUID = 2998724196731240530L;

	@Id
	@Column(name="ID")
	private String id;
	
	@Column(name="NAME")
	private String name;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="PARENT_ID")
	@NotFound(action=NotFoundAction.IGNORE)
	private OrganizeViewEntity parent;
	
	@Column(name="TYPE")
	private String type;
	
	@Column(name="STATUS")
	private String status;
	
	@Transient
	private boolean checked = false;
	
	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.EAGER,mappedBy="parent")
	//@Transient
	@OrderBy(value="status")
	private List<OrganizeViewEntity> children = new ArrayList<OrganizeViewEntity>();
	
	@Column(name="IS_LEAF")
	private boolean isLeaf;
	
	public List<OrganizeViewEntity> getChildren() {
		return children;
	}

	public void setChildren(List<OrganizeViewEntity> children) {
		this.children = children;
	}

	public boolean isLeaf() {
		return isLeaf;
	}

	public void setLeaf(boolean isLeaf) {
		this.isLeaf = isLeaf;
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

	public OrganizeViewEntity getParent() {
		return parent;
	}

	public void setParent(OrganizeViewEntity parent) {
		this.parent = parent;
	}

	@Override
	public String getText() {
		return this.name;
	}

	@Override
	public String getState() {
		if(isLeaf) {
			return "open";
		} else if(null!=children && this.children.size() > 0) {
			return "open";
		}
		return "closed";
	}
	
	@Override
	public boolean getChecked() {
		return checked;
	}
	
	public OrganizeViewEntity getRootOrganize() {
		if(null == this.parent) {
			return this;
		}
		return this.parent.getRootOrganize();
	}
	
	public boolean isRoot() {
		return null == this.parent;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == this) {
			return true;
		}
		if(obj instanceof OrganizeViewEntity) {
			OrganizeViewEntity other = (OrganizeViewEntity)obj;
			if(other.id.equals(this.id)) {
				return true;
			}
			return false;
		} else {
			return false;
		}
	}

	public String getFullName() {
		if(null != this.parent) {
			String name = this.parent.getFullName();
			if("fl".equals(this.type)) {
				return name;
			}
			return name + " " + this.name;
		} else {
			return this.name;
		}
	}


}
