<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
</head>
<body>
	<div id="divPopPage">
		<table id="tblAddInputAttr" class="formtable1" >
			<tr>
				<td class="title">属性名称：</td>
				<td ><input id="attributesLabel" class="element" elementType="normal" name="attributesLabel" value="${attribute.attributesLabel}"  validator="{'default':'*'}"/><b>*</b></td>
			</tr>
			<tr>
				<td class="title">显示在列表页：</td>
				<td >
				  <input id="isTableDisplay" value="${attribute.isTableDisplay }" class="easyui-combobox element" elementType="combobox" style="width:182px" editable="false" name="isTableDisplay""/>
				</td>
			</tr>
			<tr>
			  <td class="title">所跨列数：</td>
			  <td ><input id="columnNum" name="columnNum" value="${attribute.columnNum}" class="easyui-combobox element" elementType="combobox" style="width:182px" editable="false"></td>
			</tr>
			<tr>
				<td class="title">是否必填：</td>
				<td>
				   <input id="required" value="${attribute.required }" class="easyui-combobox element" elementType="combobox" style="width:182px" editable="false" name="required"/>
				</td>
			</tr>
			<tr>
				<td class="title">系统属性：</td>
				<td ><input id="initSQL" name="initSQL" value="${attribute.initSQL}" class="easyui-combobox element" elementType="combobox" style="width:182px" editable="false"></td>
			</tr>
		</table>
	</div>
</body>
</html>