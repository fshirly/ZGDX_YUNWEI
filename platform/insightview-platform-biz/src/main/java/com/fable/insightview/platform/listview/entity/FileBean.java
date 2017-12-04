/*
 * FileBean.java
 * Copyright(C) 2015-2020 飞搏软件公司
 * All rights reserved.
 *-------------------------------------------------
 * 2015-10-19 Created
 */ 
package com.fable.insightview.platform.listview.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 系统文件表
 * 
 * @author fables
 * @version 1.0 2015-10-19
 */
//@ApiModel(value="系统文件表")
public class FileBean {
    /**
     * 主键
     */
    //@ApiModelProperty(value="主键")
    private String id;

    /**
     * 系统ID
     */
    //@ApiModelProperty(value="系统ID")
    private String sysId;

    /**
     * 文件名称
     */
    //@ApiModelProperty(value="文件名称")
    private String fileName;

    /**
     * 文件大小
     */
    //@ApiModelProperty(value="文件大小")
    private BigDecimal fileSize;

    /**
     * 文件路径
     */
    //@ApiModelProperty(value="文件路径")
    private String filePath;

    /**
     * 文件修改时间
     */
    //@ApiModelProperty(value="文件修改时间")
    private Date fileModifiedTime;

    /**
     * 文件上传者
     */
    //@ApiModelProperty(value="文件上传者")
    private String fileUploader;

    /**
     * 文件上传时间
     */
    //@ApiModelProperty(value="文件上传时间")
    private Date fileTimestamp;

    /**
     * 文件标志
     */
    //@ApiModelProperty(value="文件标志")
    private String fileLogo;

    /**
     * 文件说明
     */
    //@ApiModelProperty(value="文件说明")
    private String fileDes;

    /**
     * 文件是否已上传完成
     */
    //@ApiModelProperty(value="文件是否已上传完成")
    private String fileFn;

    /**
     * 备注
     */
    //@ApiModelProperty(value="备注")
    private String note;

    /**
     * 创建者
     */
    //@ApiModelProperty(value="创建者")
    private String creatorId;

    /**
     * 创建者姓名
     */
    //@ApiModelProperty(value="创建者姓名")
    private String creatorName;

    /**
     * 创建时间
     */
    //@ApiModelProperty(value="创建时间")
    private Date createdTime;

    /**
     * 修改者
     */
    //@ApiModelProperty(value="修改者")
    private String updateId;

    /**
     * 修改者姓名
     */
    //@ApiModelProperty(value="修改者姓名")
    private String updateName;

    /**
     * 修改时间
     */
    //@ApiModelProperty(value="修改时间")
    private Date updatedTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSysId() {
        return sysId;
    }

    public void setSysId(String sysId) {
        this.sysId = sysId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public BigDecimal getFileSize() {
        return fileSize;
    }

    public void setFileSize(BigDecimal fileSize) {
        this.fileSize = fileSize;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Date getFileModifiedTime() {
        return fileModifiedTime;
    }

    public void setFileModifiedTime(Date fileModifiedTime) {
        this.fileModifiedTime = fileModifiedTime;
    }

    public String getFileUploader() {
        return fileUploader;
    }

    public void setFileUploader(String fileUploader) {
        this.fileUploader = fileUploader;
    }

    public Date getFileTimestamp() {
        return fileTimestamp;
    }

    public void setFileTimestamp(Date fileTimestamp) {
        this.fileTimestamp = fileTimestamp;
    }

    public String getFileLogo() {
        return fileLogo;
    }

    public void setFileLogo(String fileLogo) {
        this.fileLogo = fileLogo;
    }

    public String getFileDes() {
        return fileDes;
    }

    public void setFileDes(String fileDes) {
        this.fileDes = fileDes;
    }

    public String getFileFn() {
        return fileFn;
    }

    public void setFileFn(String fileFn) {
        this.fileFn = fileFn;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getUpdateId() {
        return updateId;
    }

    public void setUpdateId(String updateId) {
        this.updateId = updateId;
    }

    public String getUpdateName() {
        return updateName;
    }

    public void setUpdateName(String updateName) {
        this.updateName = updateName;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }
}