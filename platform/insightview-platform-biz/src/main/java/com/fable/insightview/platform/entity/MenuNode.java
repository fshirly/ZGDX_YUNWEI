package com.fable.insightview.platform.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 
 * @TABLE_NAME: SysMenuModule
 * @Description:
 * @author: 武林
 * @Create at: Fri Dec 06 14:03:31 CST 2013
 */
@SuppressWarnings("serial")
public class MenuNode
		implements Serializable {
	
	private int id;
	
	private String text;
	
	private int parentMenuID;
	
	private String linkURL;
	
	private String iconCls;

	private String descr;
	
	private String state;
	
	private Attribute attributes;
	
	private int showOrder;
	
	private String navigationBar;
	
	private List<MenuNode> children = new ArrayList<MenuNode>();
	
	public Attribute getAttributes() {
		this.attributes = new Attribute(this.linkURL,this.iconCls);
		return this.attributes;
	}

	public void setAttributes(Attribute attributes) {
		this.attributes = attributes;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public List<MenuNode> getChildren() {
		Collections.sort(this.children, new Comparator<MenuNode>() {

			@Override
			public int compare(MenuNode o1, MenuNode o2) {
				return o1.getShowOrder() - o2.getShowOrder();
			}
		});
		return this.children;
	}

	public void setChildren(List<MenuNode> children) {
		this.children = children;
	}

	

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

	public int getParentMenuID() {
		return parentMenuID;
	}

	public void setParentMenuID(int parentMenuID) {
		this.parentMenuID = parentMenuID;
	}

	public String getLinkURL() {
		return linkURL;
	}

	public void setLinkURL(String linkURL) {
		this.linkURL = linkURL;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public int getShowOrder() {
		return showOrder;
	}

	public void setShowOrder(int showOrder) {
		this.showOrder = showOrder;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
	
	public String getNavigationBar() {
		return navigationBar;
	}

	public void setNavigationBar(String navigationBar) {
		this.navigationBar = navigationBar;
	}

	private class Attribute
	{
		public Attribute(String url,String icon)
		{
			this.url = url;
			this.icon = icon;
		}
		
		private String url;
		
		private String icon;

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public String getIcon() {
			return icon;
		}

		public void setIcon(String icon) {
			this.icon = icon;
		}
	}
}
