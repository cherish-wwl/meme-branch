package com.meme.core.base;

import java.io.Serializable;

@Deprecated
public abstract class BasePagination<K> implements Serializable{

	private static final long serialVersionUID = 393135175837883081L;
	
	public static final String ASC = "asc";
	public static final String DESC = "desc";

	/** 总记录数 */
	private int totalCount = 0;
	/** 当前页 */
	private int currentPage = 1;
	/** 总页数 */
	private int totalPage = 0;
	/** 每页显示的记录数,默认为每页显示20条记录 */
	private int pageSize = 20;
	/** 排序策略 asc or desc */
	private String order;
	/** 根据字段排序 */
	private String orderby;
	/** 当前页的记录集 */
//	private List<T> list = Collections.emptyList();
	
	private K form;

	public BasePagination(){
	}
	
	public BasePagination(int totalCount, int currentPage, int pageSize) {
		this.totalCount = totalCount;
		this.currentPage = currentPage;
		this.pageSize = pageSize;
	}

	public K getForm() {
		return form;
	}

	public void setForm(K form) {
		this.form = form;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getTotalPage() {
		this.totalPage = (totalCount - 1) / pageSize + 1;
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = (totalCount - 1) / pageSize + 1;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getOrderby() {
		return orderby;
	}

	public void setOrderby(String orderby) {
		this.orderby = orderby;
	}
}
