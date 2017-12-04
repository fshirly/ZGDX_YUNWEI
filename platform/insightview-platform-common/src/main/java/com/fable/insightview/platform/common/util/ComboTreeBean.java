package com.fable.insightview.platform.common.util;

import java.util.List;
import java.util.Map;

public class ComboTreeBean<K,V> {
	private K id;
	private V text;
	private List<ComboTreeBean> children;
	private Map<String,Object> attributes;
	public K getId() {
		return id;
	}
	public void setId(K id) {
		this.id = id;
	}
	public V getText() {
		return text;
	}
	public void setText(V text) {
		this.text = text;
	}
	public List<ComboTreeBean> getChildren() {
		return children;
	}
	public void setChildren(List<ComboTreeBean> children) {
		this.children = children;
	}
	public Map<String, Object> getAttributes() {
		return attributes;
	}
	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

}
