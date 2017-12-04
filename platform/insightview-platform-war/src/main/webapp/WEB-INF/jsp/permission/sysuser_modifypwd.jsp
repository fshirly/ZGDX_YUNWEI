<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file = "../common/pageincluded.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
	
  </head>
  
  <body>
  	<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/permission/sysusermodifypwd.js"></script> 
	<%
	String userId=(String)request.getAttribute("userId");
    %>
    <!-- 修改密码 -->
	<div id="divModifyPwd" >
	<input id="ipt_userID" type="hidden" value="<%= userId%>"/>
	<input id="ipt_userPassword" type="hidden"/>
		<table id="tblModifyPwd" class="formtable1">
			<tr>
				<td class="title">旧密码：</td>
				<td ><input id="oldPwd" readonly type="password"/></td>
			</tr>
			<tr>
				<td class="title">新密码：</td>
				<td ><input id="newPwd" 
					type="password" validator="{'default':'*'}" msg="{reg:'只能输入11位数字！'}" onblur="checkIsNull();"/><b>*</b></td>
			</tr>
			<tr>
				<td class="title">重复新密码：</td>
				<td ><input id="repeatPwd" 
					type="password" validator="{'default':'*'}" msg="{reg:'只能输入11位数字！'}" onblur="checkPwd();"/><b>*</b></td>
			</tr>
			<tr>
				<td class="title">&nbsp;</td>
				<td ><input type="checkbox" id="sendEmail" name="sendEmail"/>是否发送邮件</td>
			</tr>
		</table>
		<div class="conditionsBtn">
			<a href="javascript:void(0);" onclick="javascript:doModifyPwd();" id="btnSave3" >确定</a>
			<a href="javascript:void(0);" onclick="javascript:$('#popWin').window('close');" id="btnBack3" >取消</a>
		</div>
	</div>
  </body>
</html>
