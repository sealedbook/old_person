package com.esite.framework.security.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

public class SystemFunction implements Serializable {

	private static final long serialVersionUID = 798478971176397871L;
	
	private String id;
	private String name;
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
	@Override
	public boolean equals(Object obj) {
		if(null == obj) {
			return false;
		}
		if(this == obj) {
			return true;
		}
		if(obj instanceof SystemFunction) {
			SystemFunction systemFunction = (SystemFunction)obj;
			if(systemFunction.getId().equals(this.id)) {
				return true;
			}
			return false;
		}
		return false;
	}
	
}
