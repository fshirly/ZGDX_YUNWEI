<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file = "../../common/pageincluded.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
	
  </head>
  
  <body>
  	<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/platform/portal/platformPortal_add.js"></script> 
	<div id="divAddPortal" >
		<input id="tabsIndex" type="hidden" value="${tabsIndex}"/>
		<table id="tblAddPortal" class="formtable1">
			<tr>
				<td class="title">视图英文名：</td>
				<td><input id="portalName" class="input" validator="{'default':'en','length':'1-20'}"/><b>*</b></td>
			</tr>
			<tr>
				<td class="title">视图中文名：</td>
				<td><input id="portalDesc" class="input" validator="{'default':'chnStr','length':'1-20'}"/><b>*</b></td>
			</tr>
			<tr></tr>
		</table>
		<div class="conditionsBtn">
			<a href="javascript:void(0);" onclick="javascript:doSave();" id="btnSave" >确定</a>
			<a href="javascript:void(0);" onclick="javascript:$('#popWin').window('close');" id="btnBack2" >取消</a>
		</div>
	</div>
	
  </body>
</html>
