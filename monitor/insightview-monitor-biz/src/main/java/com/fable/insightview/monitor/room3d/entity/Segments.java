package com.fable.insightview.monitor.room3d.entity;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * @author Administrator
 * 用于描述点连接样式
 *
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_DEFAULT)
public class Segments {
	
	/**
	 * 属性为ht.List类型，用于描述点连接样式，数组元素为整型值
	 */
	@JsonProperty("__a")
	private List<Integer> list;

}
