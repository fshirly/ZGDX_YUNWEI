<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<body>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/permission/changepwd.js"></script>
	
	<table id="tblChangePwd" class="formtable1">
		<tr>
			<td class="title">旧密码：</td>
			<td><input id="oldPwd" type="password"  validator="{'default':'pwd','length':'6-18'}"}"/><b>*</b></td>
		</tr>
		<tr>
			<td class="title">新密码：</td>
			<td><input id="newPwd" type="password"  validator="{'default':'pwd','length':'6-18'}"}"/><b>*</b></td>
		</tr>
		<tr>
			<td class="title">重复新密码：</td>
			<td><input id="repeatPwd" type="password"  validator="{'default':'pwd','length':'6-18'}"}"/><b>*</b></td>
		</tr>
	</table>
	
	<div class="conditionsBtn">
		<a href="javascript:void(0);" onclick="javascript:changePwd();">确定</a>
		<a href="javascript:void(0);" onclick="javascript:$('#popWin').window('close');">取消</a>
	</div>
	
</body>
</html>
