<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file = "../../common/pageincluded.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
	<%
		String monitorTypeID = (String)request.getAttribute("monitorTypeID");
	 %>
  </head>
  
  <body>
  	<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/monitor/molist/sysMoTypeModify.js"></script> 
	<div id="divEditMoType" >
	<input id="ipt_monitorTypeId" type="hidden" value="<%=monitorTypeID %>"/>
	<input id="ipt_monitorTypeNameTemp" type="hidden"/>
		<table id="tblEditMoType" class="formtable1">
			<tr>
				<td class="title">监测器类型名称：</td>
				<td ><input id="ipt_monitorTypeName" validator="{'default':'*','length':'1-128'}"/><b>*</b></td>
			</tr>
		</table>
		<div class="conditionsBtn">
			<a href="javascript:void(0);" onclick="javascript:doModify();" id="btnSave" >确定</a>
			<a href="javascript:void(0);" onclick="javascript:$('#popWin').window('close');" id="btnBack" >取消</a>
		</div>
	</div>
	
  </body>
</html>
