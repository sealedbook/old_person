package com.esite.framework.util;

import java.util.ArrayList;
import java.util.List;

public class Pager<T> {
	/**
	 * 当前页数
	 */
	private int page = 1;
	
	/**
	 * 每页显示行数
	 */
	private int rows = 10;
	
	public Pager(){}
	
	public Pager(int page,int rows) {
		this.page = page == 0 ? (page + 1) : page;
		this.rows = rows;
	}
	
	/**
	 * 本次查询总数量
	 */
	private int totalRows;
	
	/**
	 * 本次查询结果集
	 */
	private List<T> rowsdata = new ArrayList<T>();

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}

	public List<T> getRowsdata() {
		return rowsdata;
	}

	public void setRowsdata(List<T> rowsdata) {
		this.rowsdata = rowsdata;
	}
	

	
}
