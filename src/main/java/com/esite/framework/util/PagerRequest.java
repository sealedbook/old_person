package com.esite.framework.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

/**
 * 分页请求对象
 * @author sealedbook
 *
 */
public class PagerRequest {
	
	private int page = 0;
	private int rows = 10;
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
	
	public Pageable getInstance() {
		if(this.page > 0) {
			page -= 1;
		}
		return new PageRequest(page,rows);
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PagerRequest [page=");
		builder.append(page);
		builder.append(", rows=");
		builder.append(rows);
		builder.append("]");
		return builder.toString();
	}
	
	
}
