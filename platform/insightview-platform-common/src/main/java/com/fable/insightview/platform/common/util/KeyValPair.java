package com.fable.insightview.platform.common.util;

import org.apache.ibatis.type.Alias;

public class KeyValPair<K, V> {

	private K key;

	private V val;

	private boolean selected;

	public KeyValPair() {
		super();
		this.selected = false;
	}
	
	public KeyValPair(K key, V val) {
		super();
		this.key = key;
		this.val = val;
		this.selected = false;
	}

	public KeyValPair(K key, V val, boolean b) {
		super();
		this.key = key;
		this.val = val;
		this.selected = b;
	}

	public K getKey() {
		return key;
	}

	public void setKey(K key) {
		this.key = key;
	}

	public V getVal() {
		return val;
	}

	public void setVal(V val) {
		this.val = val;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean b) {
		this.selected = b;
	}
}
