package com.fable.insightview.platform.common.util;

import java.util.List;

public class ComboTreeModel {
	private int id;
	private String text;
	private List<ComboTreeModel> children;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<ComboTreeModel> getChildren() {
		return children;
	}

	public void setChildren(List<ComboTreeModel> children) {
		this.children = children;
	}
}
