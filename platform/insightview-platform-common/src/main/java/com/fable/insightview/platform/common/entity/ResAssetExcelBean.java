package com.fable.insightview.platform.common.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;

@Entity
@Table(name = "ResAssetExcelBean")
public class ResAssetExcelBean extends
		com.fable.insightview.platform.itsm.core.entity.Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "ResAsset_gen")
	@TableGenerator(initialValue = INIT_VALUE, name = "ResAsset_gen", table = "SysKeyGenerator", pkColumnName = "GenName", valueColumnName = "GenValue", pkColumnValue = "ResAssetPK", allocationSize = 1)
	@Column(name = "assetId")
	private Integer assetId;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "ResCi_gen")
	@TableGenerator(initialValue = INIT_VALUE, name = "ResCi_gen", table = "SysKeyGenerator", pkColumnName = "GenName", valueColumnName = "GenValue", pkColumnValue = "ResCiPK", allocationSize = 1)
	@Column(name = "resCiId")
	private Integer resCiId;

	/* 资产编号 */
	@Column(name = "serialNo")
	private String serialNo;

	/* 资产名称 */
	@Column(name = "assetName")
	private String assetName;

	/* 产品目录 */
	@Column(name = "productCatalogName")
	private String productCatalogName;

	/* 资产状态 (0-库存、1-在用、2-报废) */
	@Column(name = "status")
	private String status;

	/* 资产类型 */
	@Column(name = "resAssetType")
	private String resAssetType;

	/* 资产来源 (0-采购、1-租赁、2-上级分发、3-受赠) */
	@Column(name = "receiveMode")
	private String receiveMode;

	/* 位置 */
	@Column(name = "address")
	private String address;

	/* 描述 */
	@Column(name = "description")
	private String description;

	/* 购进/租赁成本 */
	@Column(name = "cost")
	private Double cost;

	/* 购进/租赁日期 */
	@Column(name = "receiveTime")
	private Date receiveTime;

	/* 质保期 月数 */
	@Column(name = "warrantyPeriod")
	private Date warrantyPeriod;

	/* 供应商 */
	@Column(name = "resManufacturerName")
	private String resManufacturerName;

	/* 导入时间 */
	@Column(name = "importTime")
	private Date importTime;

	/* 资源归类 */
	@Column(name = "resTypeId")
	private Integer resTypeId;

	@Transient
	private String flag;

	@Transient
	private String message;

	public Integer getAssetId() {
		return assetId;
	}

	public ResAssetExcelBean() {
	}

	public void setAssetId(Integer assetId) {
		this.assetId = assetId;
	}

	public String getAssetName() {
		return assetName;
	}

	public void setAssetName(String assetName) {
		this.assetName = assetName;
	}

	public String getProductCatalogName() {
		return productCatalogName;
	}

	public void setProductCatalogName(String productCatalogName) {
		this.productCatalogName = productCatalogName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getResAssetType() {
		return resAssetType;
	}

	public void setResAssetType(String resAssetType) {
		this.resAssetType = resAssetType;
	}

	public String getReceiveMode() {
		return receiveMode;
	}

	public void setReceiveMode(String receiveMode) {
		this.receiveMode = receiveMode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public Date getReceiveTime() {
		return receiveTime;
	}

	public void setReceiveTime(Date receiveTime) {
		this.receiveTime = receiveTime;
	}

	public Date getWarrantyPeriod() {
		return warrantyPeriod;
	}

	public void setWarrantyPeriod(Date warrantyPeriod) {
		this.warrantyPeriod = warrantyPeriod;
	}

	public String getResManufacturerName() {
		return resManufacturerName;
	}

	public void setResManufacturerName(String resManufacturerName) {
		this.resManufacturerName = resManufacturerName;
	}

	public Date getImportTime() {
		return importTime;
	}

	public void setImportTime(Date importTime) {
		this.importTime = importTime;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getResTypeId() {
		return resTypeId;
	}

	public void setResTypeId(Integer resTypeId) {
		this.resTypeId = resTypeId;
	}

	public Integer getResCiId() {
		return resCiId;
	}

	public void setResCiId(Integer resCiId) {
		this.resCiId = resCiId;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

}
