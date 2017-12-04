<!-- 系统属性（下拉列表）的绘制模版 -->
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<label class="label">${attribute.attributesLabel}: </label></div><div class="div_right">
<input id="${attribute.id}_hidden" type="hidden" value="${value }"/>
<input id="${attribute.id}" name="${attribute.columnName}" class="element input" elementType="InputPopUp" value="${value }" editable="false" style="width:150px"/>
<script type="text/javascript">
     $('#${attribute.id}').click(function(){ 
    	 var formId = $('#formId').val() == '' ? 0 : $('#formId').val();
    	 $('#propWin').window({
 			title:'数据表格',
 			width:600,
 			height:400,
 			minimizable:false,
 			draggable : false,
 			maximizable:false,
 			collapsible:false,
 			inline : true,
 			modal:true,
 			href: f.contextPath + "/bpmconsole/formDesign/queryLinkList?id=${attribute.id}&formId="+formId
 		}).window("center");
     });
</script>
