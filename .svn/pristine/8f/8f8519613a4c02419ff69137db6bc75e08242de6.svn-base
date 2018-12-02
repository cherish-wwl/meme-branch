package com.meme.core.base;

import java.util.Date;

import com.meme.core.pojo.LoginAccount;
import com.meme.core.pojo.LoginAccountInfo;
import com.meme.core.util.StringUtil;


public abstract class BaseForm {

	public static final String ASC = "asc";
	public static final String DESC = "desc";
	public static final Integer DEFAULT_OFFSET=0;
	public static final Integer DEFAULT_LIMIT=15;
	
	/**查询关键字*/
	private String searchKey;
	/**排序字段*/
	private String sort;
	/**排序规则*/
	private String order;
	/**每页显示记录数*/
	private Integer limit=DEFAULT_LIMIT;
	/**偏移量*/
	private Integer offset=DEFAULT_OFFSET;
	
	/**以下为ligerui封装请求参数**/
	private Integer page;
	private Integer pagesize;
	private String sortname;
	private String sortorder;
	
	/**easyui 分页请求参数**/
//	private String sort;
//	private String order;
//	private Integer page;
	private Integer rows;
	
	private Date startdate;
	private Date enddate;
	/**数据权限过滤参数**/
	private String sqlcondition;
	/**登录用户账号类，用于数据权限参数过滤**/
	private LoginAccount _account;
	/**登录用户信息类，用于数据权限参数过滤**/
	private LoginAccountInfo _accountinfo;
	
	/**
	 * 初始化分页排序参数
	 */
	public void init(){
		if(null!=this.page&&null!=this.pagesize){
			this.limit=this.pagesize;
			this.offset=(this.page-1)*this.pagesize;
		}
		if(null!=this.page&&null!=this.rows){
			this.limit=this.rows;
			this.offset=(this.page-1)*this.rows;
		}
		if(StringUtil.isAllNotEmpty(this.sortname,this.sortorder)){
			this.sort=this.sortname;
			this.order=this.sortorder;
		}
	}
	
	public LoginAccount get_account() {
		return _account;
	}
	public void set_account(LoginAccount _account) {
		this._account = _account;
	}
	public LoginAccountInfo get_accountinfo() {
		return _accountinfo;
	}
	public void set_accountinfo(LoginAccountInfo _accountinfo) {
		this._accountinfo = _accountinfo;
	}
	public String getSqlcondition() {
		return sqlcondition;
	}
	public void setSqlcondition(String sqlcondition) {
		this.sqlcondition = sqlcondition;
	}
	public Date getStartdate() {
		return startdate;
	}
	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}
	public Date getEnddate() {
		return enddate;
	}
	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}
	public String getSearchKey() {
		return searchKey;
	}

	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}

	public String getSort() {
		if(StringUtil.isAllNotEmpty(this.sortname,this.sortorder)){
			this.sort=this.sortname;
			this.order=this.sortorder;
		}
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public Integer getLimit() {
		return limit;
	}
	public void setLimit(Integer limit) {
		this.limit = limit;
	}
	public Integer getOffset() {
		if(null!=this.page&&null!=this.pagesize){
			this.limit=this.pagesize;
			this.offset=(this.page-1)*this.pagesize;
		}
		if(null!=this.page&&null!=this.rows){
			this.limit=this.rows;
			this.offset=(this.page-1)*this.rows;
		}
		return offset;
	}
	public void setOffset(Integer offset) {
		this.offset = offset;
	}
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public Integer getPagesize() {
		return pagesize;
	}
	public void setPagesize(Integer pagesize) {
		this.pagesize = pagesize;
	}
	public String getSortname() {
		return sortname;
	}
	public void setSortname(String sortname) {
		this.sortname = sortname;
	}
	public String getSortorder() {
		return sortorder;
	}
	public void setSortorder(String sortorder) {
		this.sortorder = sortorder;
	}
	public Integer getRows() {
		return rows;
	}
	public void setRows(Integer rows) {
		this.rows = rows;
	}
}
