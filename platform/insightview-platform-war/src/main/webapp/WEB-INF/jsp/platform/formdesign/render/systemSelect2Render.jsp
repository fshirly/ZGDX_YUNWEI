<!-- 系统属性（下拉列表）的绘制模版 -->
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<label class="label">${attribute.attributesLabel}: </label></div><div class="div_right">
<input id="select2hidden" type="hidden" value="${value }"/>
<select id="${attribute.id}" name="${attribute.columnName}" class="element" elementType="select2" value="${value }" editable="false" style="width:157px">
	<option value="-1">请选择...</option>
</select>
<script type="text/javascript">
	$(document).ready(function() {	
		var userId = $("#${attribute.id}").attr("value");
		var isShowUserName = '${attribute.isShowUserName}'
		if(isShowUserName == 2){
			userId = '${sessionScope.sysUserInfoBeanOfSession.id}';
		}
		$('#${attribute.id}').createSelect2({
			uri: '/platform/form/prop/value/init?initSQL=${attribute.initSQL}',
		    name : 'name',
		    id : 'id',
		    initVal: {${attribute.id}:userId}
		});
	});
</script>
