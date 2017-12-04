<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
</head>
<body>
	<div id="divPopPage" style="height:320px;overflow-y:auto;">
		<table id="tblAddInputAttr" class="formtable1" >
			<tr>
				<td class="title">属性名称：</td>
				<td ><input id="attributesLabel" class="element" elementType="normal" name="attributesLabel" validator="{'default':'*'}" value="${attribute.attributesLabel}" /><b>*</b></td>
			</tr>
			<tr>
				<td class="title">字段名称：</td>
				<td ><input id="columnName" class="element" elementType="normal" name="columnName" value="${attribute.columnName}" validator="{'reg':'/^[0-9a-zA-Z_]+$/','default':'*'}" msg="{reg:'输入信息必须为英文、数字、下划线，如“Insight_123”。'}" /><b>*</b></td>
			</tr>
			<tr>
				<td class="title">显示在列表页：</td>
				<td >
				  <input id="isTableDisplay" value="${attribute.isTableDisplay }" class="easyui-combobox element" elementType="combobox" style="width:182px" editable="false" name="isTableDisplay""/>
				</td>
			</tr>
			<tr>
				<td class="title">是否必填：</td>
				<td>
				   <input id="required" value="${attribute.required }" class="easyui-combobox element" elementType="combobox" style="width:182px" editable="false" name="required""/>
				</td>
			</tr>
			<tr>
				<td class="title">初始化数据SQL：</td>
				<td ><textarea id="sql" class="element" elementType="normal" validator="{'default':'*'}" name="sql">${attribute.sql}</textarea><b>*</b></td>
			</tr>
			<tr>
				<td class="title">columns数据：</td>
				<td><textarea id="columnsData" class="element" elementType="normal" validator="{'default':'*'}" name="columnsData">${attribute.columnsData}</textarea><b>*</b></td>
			</tr>
			<tr>
				<td class="title">columnsName：</td>
				<td><textarea id="columnsName" class="element" elementType="normal" validator="{'default':'*'}" name="columnsName">${attribute.columnsName}</textarea><b>*</b></td>
			</tr>
			<tr>
				<td class="title">取值字段名称：</td>
				<td><textarea id="fieldName" class="element" elementType="normal" name="fieldName" validator="{'default':'*'}">${attribute.fieldName}</textarea><b>*</b></td>
			</tr>
			<tr>
				<td class="title">关联SQL：</td>
				<td><textarea id="linkSQL" class="element" elementType="normal" name="linkSQL">${attribute.linkSQL}</textarea></td>
			</tr>
						<tr>
			  <td class="title">所跨列数：</td>
			  <td ><input id="columnNum" name="columnNum" value="${attribute.columnNum}" class="easyui-combobox element" elementType="combobox" style="width:182px" editable="false"></td>
			</tr>
		</table>
	</div>
	<script>
       $(document).ready(function() {
            checkColumnName();
       });
       function checkColumnName(){
    	   var columnName = $("#columnName").val();
    	   if(columnName !=""){
    		   $("#columnName").attr("disabled","disabled").css("background-color","rgb(231, 232, 232)");
    	   }
       }
	</script>
</body>
</html>