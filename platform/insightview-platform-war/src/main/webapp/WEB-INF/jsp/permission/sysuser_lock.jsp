<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file = "../common/pageincluded.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
	
  </head>
  
  <body>
  	<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/permission/sysuserlock.js"></script> 
	<%
	String userId=(String)request.getAttribute("userId");
    %>
    	<!-- 锁定用户 -->
	<div id="divLockUser" >
		<input id="ipt_userAccount" type="hidden"/>
		<input id="ipt_userID" type="hidden" value="<%= userId%>"/>
		<input id="ipt_lockedReason" type="hidden" />
		<table id="tblLockUser" class="formtable1">
			<tr>
				<td class="title">用户名：</td>
				<td ><input id="lockAccountName" readonly/></td>
			</tr>
			<tr>
				<td class="title">锁定原因：</td>
				<td ><!--<input id="lockedReason" 
					maxlength="20"  validator="{'length':'1-15'}" msg="{reg:'只能输入1-15位字符！'}"/><b>*</b>-->
					<input id="lockedReason"/>
					</td>
			</tr>
			<tr></tr>
		</table>
		<div class="conditionsBtn">
			<a href="javascript:void(0);" onclick="javascript:doLock();" id="btnSave2" >确定</a>
			<a href="javascript:void(0);" onclick="javascript:$('#popWin').window('close');" id="btnBack2" >取消</a>
		</div>
	</div>
	
  </body>
</html>
