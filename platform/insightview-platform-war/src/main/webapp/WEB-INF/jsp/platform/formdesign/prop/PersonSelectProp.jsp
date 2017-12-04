<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
</head>
<body>
	<div id="divPopPage">
		<table id="tblFormModel" class="formtable1" >
			<tr>
				<td class="title">属性名称：</td>
				<td ><input id="attributesLabel" class="element" elementType="normal" name="attributesLabel" validator="{'default':'*'}" value="${attribute.attributesLabel}" /><b>*</b></td>
			</tr>
			<tr>
				<td class="title">系统属性：</td>
				<td ><input id="initSQL" name="initSQL" value="${attribute.initSQL}" class="easyui-combobox element" elementType="combobox" style="width:182px" editable="false"></td>
			</tr>
		 	<tr>
			  <td class="title">所跨列数：</td>
			  <td ><input id="columnNum" name="columnNum" value="${attribute.columnNum}" class="easyui-combobox element" elementType="combobox" style="width:182px" editable="false"></td>
			</tr>
		</table>
	</div>
</body>
</html>