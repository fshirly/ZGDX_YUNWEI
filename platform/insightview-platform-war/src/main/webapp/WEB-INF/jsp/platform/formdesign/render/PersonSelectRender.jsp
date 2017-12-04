<!-- åä¸ªææ¬æ¡çç»å¶æ¨¡ç -->
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<label class="label">${attribute.attributesLabel}: </label></div><div class="div_right">
<div id="${attribute.id}_showpage" style="z-index: 1000; cursor:pointer;position: absolute;margin-left: 270px;height: 36px;line-height: 36px;"><font size="5" color="#7ca3bf">…</font></div>
<input type="hidden" id="${attribute.id}_id"  value="${value}"/>
<textarea id="${attribute.id}" name="${attribute.columnName}" class="element" elementType="normal" style="width:300px" readonly=true>${value }</textarea>
<script>
 $(document).ready(function() {
    $("#${attribute.id}_showpage").click(function(){
   	    var formId = $('#formId').val() == '' ? 0 : $('#formId').val();
	    $('#propWin').window({
			title:'人员选择',
			width:600,
			height:400,
			minimizable:false,
			draggable : false,
			maximizable:false,
			collapsible:false,
			inline : true,
			modal:true,
			href: f.contextPath + "/bpmconsole/formDesign/PersonSelect?id=${attribute.id}&formId="+formId
		}).window("center");
    })
  });
</script>