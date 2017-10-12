package com.esite.framework.util;

import java.util.List;

import org.springframework.data.domain.Page;

/**
 * 分页响应对象
 * @author sealedbook
 *
 * @param <T>
 */
public class PagerResponse<T> {

	/*
	 p.getTotalElements();//总条数
	p.getContent();//当前页查询出的结果
	p.getNumber();//当前第几页
	p.getNumberOfElements();//当前页共有的实际条数
	p.getSize();//每页条数
	p.getTotalPages();//总页数
	 */
	
	private Page<T> p;
	public PagerResponse(Page<T> p) {
		this.p = p;
	}
	
	public List<T> getRows() {
		return p.getContent();
	}
	
	public long getTotal() {
		return p.getTotalElements();
	}

}
