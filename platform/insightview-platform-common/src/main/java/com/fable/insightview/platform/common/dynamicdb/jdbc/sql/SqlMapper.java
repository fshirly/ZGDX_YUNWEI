package com.fable.insightview.platform.common.dynamicdb.jdbc.sql;
/**
 * 存储相应操作SQL语句的Model
 * 用于和sql配置文件的映射
 * @author 郑自辉
 *
 */
public class SqlMapper {

	/**
	 * 创建表
	 */
	private String create;
	
	/**
	 * 删除表
	 */
	private String drop;
	
	/**
	 * 新增字段
	 */
	private String add;
	
	/**
	 * 删除字段
	 */
	private String remove;
	
	/**
	 * 新增数据
	 */
	private String insert;
	
	/**
	 * 更新数据
	 */
	private String update;
	
	/**
	 * 查询数据
	 */
	private String select;
	
	/**
	 * 删除数据
	 */
	private String delete;
	
	/**
	 * 查询表字段
	 */
	private String listcolumn;
	
	/**
	 * 分页查询
	 */
	private String list;
	
	/**
	 * 分页查询总条数
	 */
	private String listtotal;
	
		/**
	 * 查询主键
	 */
	private String queryPrimaryId;
	
	/**
	 * 批量查询组件信息
	 */
	private String listComp;
	

	public String getList() {
		return list;
	}

	public void setList(String list) {
		this.list = list;
	}

	public String getCreate() {
		return create;
	}

	public String getListComp() {
		return listComp;
	}

	public void setListComp(String listComp) {
		this.listComp = listComp;
	}

	public void setCreate(String create) {
		this.create = create;
	}

	public String getDrop() {
		return drop;
	}

	public void setDrop(String drop) {
		this.drop = drop;
	}

	public String getListtotal() {
		return listtotal;
	}

	public void setListtotal(String listtotal) {
		this.listtotal = listtotal;
	}

	public String getAdd() {
		return add;
	}

	public void setAdd(String add) {
		this.add = add;
	}

	public String getRemove() {
		return remove;
	}

	public void setRemove(String remove) {
		this.remove = remove;
	}

	public String getInsert() {
		return insert;
	}

	public void setInsert(String insert) {
		this.insert = insert;
	}

	public String getUpdate() {
		return update;
	}

	public void setUpdate(String update) {
		this.update = update;
	}

	public String getSelect() {
		return select;
	}

	public void setSelect(String select) {
		this.select = select;
	}

	public String getDelete() {
		return delete;
	}

	public void setDelete(String delete) {
		this.delete = delete;
	}

	public String getListcolumn() {
		return listcolumn;
	}

	public void setListcolumn(String listcolumn) {
		this.listcolumn = listcolumn;
	}
	
		public String getQueryPrimaryId() {
		return queryPrimaryId;
	}

	public void setQueryPrimaryId(String queryPrimaryId) {
		this.queryPrimaryId = queryPrimaryId;
	}
}
