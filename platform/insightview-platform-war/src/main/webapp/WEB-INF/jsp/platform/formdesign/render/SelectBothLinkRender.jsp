<!-- 系统属性（下拉列表）的绘制模版 -->
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<label class="label">${attribute.attributesLabel}: </label></div><div class="div_right">
<input id="select2hidden" type="hidden" value="${value }"/>
<!--<select id="${attribute.id}" name="${attribute.columnName}" class="element" elementType="select2" value="${value }" editable="false" style="width:150px">
	<option value="-1">请选择...</option>
</select>-->
<input type="hidden" id="${attribute.id}" name="${attribute.columnName}" class="element" elementType="select2" value="${value }" editable="false" style="width:150px">
<script type="text/javascript">
	$(document).ready(function() {
		$('#${attribute.id}').select2({
			placeholder : "请选择....",
			data : []
		});
 		$('#${attribute.id}').on("change", function(e){
			var userId = $('#${attribute.id}').select2("val");
			var linkSQL = '${attribute.linkSQL}';
	 		var ajax_param = {
					url : getRootName() + "/bpmconsole/formDesign/findLinkFieldsForUser?linkSql=" + linkSQL + "&userId=" + userId,
					type : "post",
					dataType : "json",
					error : function() {
						$.messager.alert("错误", "ajax_error", "error");
					},
					success : function(data) {
						for (var p in data) {
							var elementType = $("input[name='"+ p +"']").attr("elementType");
							if( elementType == "select2"){
								$("input[name='"+ p +"']").select2({
									placeholder : "请选择....",
									data : data[p]
								});
							}
						}
					}
			};
			ajax_(ajax_param);
		}); 
	});
</script>
