package com.fable.insightview.platform.dictionary.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;


/**
 * 
 * @TABLE_NAME: SysConstantTypeDef
 * @Description:
 * @author: caoj
 * @Create at:2014-06-10 
 */

@SuppressWarnings("serial")
@Entity
@Table(name = "SysConstantItemDef")
public class ConstantItemDef extends com.fable.insightview.platform.itsm.core.entity.Entity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "constantitemdef_gen")
	@TableGenerator(initialValue=100001, name = "constantitemdef_gen", table = "SysKeyGenerator", pkColumnName = "GenName", valueColumnName = "GenValue", pkColumnValue = "SysConstantItemDefPK", allocationSize = 1)
	@Column(name="ID")
	private Integer id;
	
	@Column(name="ConstantTypeId")
	private String constantTypeId;
	
	@Column(name="ConstantItemId")
	private Integer constantItemId;
	
	@Column(name="ConstantItemName")
	private String constantItemName;
	
	@Column(name="ConstantItemAlias")
	private String constantItemAlias;
	
	@Column(name="Remark")
	private String remark;
	
	@Column(name="EffTime")
	private Timestamp effTime;
	
	@Column(name="ExpTime")
	private Timestamp expTime;
	
	@Column(name="UpdateTime")
	private Timestamp updateTime;
	
	@Column(name="ShowOrder")
	private Integer ShowOrder;
	
	public String getParentTypeName() {
		return parentTypeName;
	}

	public void setParentTypeName(String parentTypeName) {
		this.parentTypeName = parentTypeName;
	}

	@Transient
	private String parentTypeName;
	
	@Transient
	private String isLeaf;
	
	@Transient
	private int level;
	
	@Transient
	private int typeOrItem; //1:type 2:item
	
	public int getTypeOrItem() {
		return typeOrItem;
	}

	public void setTypeOrItem(int typeOrItem) {
		this.typeOrItem = typeOrItem;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String isLeaf() {
		return isLeaf;
	}

	public void setLeaf(String isLeaf) {
		this.isLeaf = isLeaf;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getConstantTypeId() {
		return constantTypeId;
	}

	public void setConstantTypeId(String constantTypeId) {
		this.constantTypeId = constantTypeId;
	}

	public Integer getConstantItemId() {
		return constantItemId;
	}

	public void setConstantItemId(Integer constantItemId) {
		this.constantItemId = constantItemId;
	}


	public String getConstantItemName() {
		return constantItemName;
	}

	public void setConstantItemName(String constantItemName) {
		this.constantItemName = constantItemName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Timestamp getEffTime() {
		return effTime;
	}

	public void setEffTime(Timestamp effTime) {
		this.effTime = effTime;
	}

	public Timestamp getExpTime() {
		return expTime;
	}

	public void setExpTime(Timestamp expTime) {
		this.expTime = expTime;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public ConstantItemDef() {
		super();
	}

	public String getConstantItemAlias() {
		return constantItemAlias;
	}

	public void setConstantItemAlias(String constantItemAlias) {
		this.constantItemAlias = constantItemAlias;
	}

	public Integer getShowOrder() {
		return ShowOrder;
	}

	public void setShowOrder(Integer showOrder) {
		ShowOrder = showOrder;
	}
}
