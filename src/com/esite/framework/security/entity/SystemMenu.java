package com.esite.framework.security.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.esite.framework.interfaces.IConvertToEasyUITree;

public class SystemMenu implements Serializable,Comparable<SystemMenu>,IConvertToEasyUITree<SystemMenu> {
	private static final long serialVersionUID = -253273362371879833L;
	
	private String id = "";
	private String name;
	private String url;
	private String icon;
	private int order;
	private List<SystemFunction> subFunction = new ArrayList<SystemFunction>();
	private List<SystemMenu> subSystemMenu = new ArrayList<SystemMenu>();
	
	public SystemMenu() {}
	public SystemMenu(SystemMenu systemMenu) {
		this.id = systemMenu.id;
		this.name = systemMenu.name;
		this.url = systemMenu.url;
		this.icon = systemMenu.icon;
		this.order = systemMenu.order;
		this.subFunction = systemMenu.subFunction;
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
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public List<SystemMenu> getSubSystemMenu() {
		return subSystemMenu;
	}
	public void setSubSystemMenu(List<SystemMenu> subMenu) {
		this.subSystemMenu = subMenu;
	}
	public int getSubSystemMenuSize() {
		if(null == subSystemMenu) {
			return 0;
		}
		return subSystemMenu.size();
	}
	@Override
	public boolean equals(Object obj) {
		if(null == obj) {
			return false;
		}
		if(obj instanceof SystemMenu) {
			SystemMenu compareMenu = (SystemMenu)obj;
			if(this.id.equals(compareMenu.id)) {
				return true;
			}
			return false;
		}
		return false;
	}
	public List<SystemFunction> getSubFunction() {
		return subFunction;
	}
	public void setSubFunction(List<SystemFunction> subFunction) {
		this.subFunction = subFunction;
	}
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
	@Override
	public int compareTo(SystemMenu systemMenu) {
		return this.order - systemMenu.order;
	}
	@Override
	public String getText() {
		return this.name;
	}
	@Override
	public List<SystemMenu> getChildren() {
		return this.subSystemMenu;
	}
	@Override
	public String getState() {
		//closed or open
		return "open";
	}
	@Override
	public boolean getChecked() {
		return false;
	}


}

