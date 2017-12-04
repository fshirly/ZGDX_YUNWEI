<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file = "../../common/pageincluded.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
	
  </head>
  
  <body>
  	<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/monitor/molist/sysMoTypeAdd.js"></script> 
	<div id="divAddMoType" >
		<table id="tblAddMoType" class="formtable1">
			<tr>
				<td class="title">监测器类型名称：</td>
				<td ><input id="ipt_monitorTypeName" validator="{'default':'*','length':'1-128'}"/><b>*</b></td>
			</tr>
		</table>
		<div class="conditionsBtn">
			<a href="javascript:void(0);" onclick="javascript:doAdd();" id="btnSave" >确定</a>
			<a href="javascript:void(0);" onclick="javascript:$('#popWin').window('close');" id="btnBack" >取消</a>
		</div>
	</div>
	
  </body>
</html>
