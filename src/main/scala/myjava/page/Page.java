/**
 * File: Page.java
 * General 
 * @author luosong
 * version 1.0 2016年1月27日: 下午12:26:53
 * Copyright (C) 2008-2015 oneapm.com all rights reserved
 */
package myjava.page;

import java.util.List;

public class Page {

	private int pageNo; // 页码(前台传入)
	private int pageSize; // (每)页码条数(配置中)
	private int pageCount; // 总页码(未知)
	private int totalCount; // 总条数(数据库查询)
	private int startNum; // 开始行号(分页查询是只按每页查询的，查询条件包括开始结束行号)(未知)
	private int endNum; // 结束行号(未知)
	
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getPageCount() {
		int count = pageCount / pageSize;
		if( pageCount % pageSize != 0 /* || pageCount == 0 */){ // 不能整除
			return ++count;
		}else{
			if(count == 0) return ++count;
			return count;
		}
	}
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	// (页码 - 1) * 页码条数 ;开始行号和结束行号都是大小于条件，所以比看到的行号要大1或小1。
	public int getStartNum() {
		return (pageNo - 1) * pageSize;
	}
	public void setStartNum(int startNum) {
		this.startNum = startNum;
	}
	// 页码 * 页码条数  + 1
	public int getEndNum() {
		return pageNo * pageSize + 1;
	}
	public void setEndNum(int endNum) {
		this.endNum = endNum;
	}
	
	/* mysql分页
	 * 关键字：limit
	 * 示例：select * from item where id = '' ORDER BY update DESC
	 * 		limit m,n
	 * m:开始行号
	 * n:结束行号
	 * 
	 * oracle分页
	 * 关键字：rownum
	 * 1、查询结束行号
	 * 2、将行号作为结果集返回，再作开始行号查询，因为rownum不支持大于
	 * 示例：select * from 
	 * 			(select *,rownum rm from
	 * 				(select * from item) a
	 * 			where rownum < 6) b
	 * 		where rm > 0
	 */
	
	private List<?> list;
	public List<?> getList() {
		return list;
	}
	public void setList(List<?> list) {
		this.list = list;
	}
}
