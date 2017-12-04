package com.fable.insightview.platform.GuzhangrizManager.entity;

import java.util.ArrayList;

import antlr.collections.List;

public class GuzhangFile {
  private String fileName;
  private String createTime;
  private String weiz;
  private ArrayList list= new ArrayList<GuzhangFile>();
public String getFileName() {
	return fileName;
}
public void setFileName(String fileName) {
	this.fileName = fileName;
}
public String getCreateTime() {
	return createTime;
}
public void setCreateTime(String createTime) {
	this.createTime = createTime;
}
public ArrayList getList() {
	return list;
}
public void setList(ArrayList list) {
	this.list = list;
}
public String getWeiz() {
	return weiz;
}
public void setWeiz(String weiz) {
	this.weiz = weiz;
}
}
