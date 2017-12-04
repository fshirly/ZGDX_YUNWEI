<!-- 系统属性（下拉树）的绘制模版 -->
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<label class="label">${attribute.attributesLabel}: </label></div>
<div class="div_right">
<input id="${attribute.id}" name="${attribute.columnName}" class="element input" elementType="combotree" value="${value }" style="width:150px;"/>
<script type="text/javascript">
	$(document).ready(function() {
			$('#${attribute.id}').f_combotree({
				   url: f.contextPath + '/platform/form/prop/value/init?initSQL=${attribute.initSQL }',
				   rootName:"类别",
				   parentIdField: 'parentid',
				   height:100
			 });
			$('#${attribute.id}').attr('name',$('#${attribute.id}').next('input').attr('name'));
	});
</script>
