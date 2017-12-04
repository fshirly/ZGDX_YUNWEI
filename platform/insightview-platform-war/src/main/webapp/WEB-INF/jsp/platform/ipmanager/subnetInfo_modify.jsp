<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file = "../../common/pageincluded.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>	</head>
	<body>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/platform/ipmanager/subnetInfo_modify.js"></script>
		<%
			String subNetId =(String)request.getAttribute("subNetId");
		 %>
		<input type="hidden" id="subNetId" value="<%= subNetId%>"/>
		<div id ="divModifySubnet">
		  <table id="tblModifySubnet" class="formtable1">
			<tr>
				<td class="title">子网地址：</td>
				<td>
					<input id="ipt_ipAddress" class="input" disabled="disabled" /><b>*</b>
				</td>
			</tr>
			
			<tr>
				<td class="title">子网掩码：</td>
				<td>
					<input id="ipt_subNetMark" class="input" disabled="disabled" /><b>*</b>
				</td>
			</tr>
			
			<tr>
				<td class="title">网关：</td>
				<td>
					<input id="ipt_gateway" class="input" validator="{'default':'ipAddr','length':'0-64'}"  />
				</td>
			</tr>
			
			<tr>
				<td class="title">DNS：</td>
				<td>
					<input id="ipt_dnsAddress" class="input" validator="{'default':'ipAddr','length':'0-64'}"  />
				</td>
			</tr>
			
			<tr>
				<td class="title">所属网络类型：</td>
				<td>
					<input id="ipt_subNetTypeName" disabled="disabled" /><b>*</b>
				</td>
			</tr>
			
			<tr>
				<td class="title">子网描述：</td>
				<td><textarea rows="3" id="ipt_descr" validator="{'length':'0-200'}"></textarea></td>
			</tr>
		  </table>
		  <div class="conditionsBtn">
			<input class="buttonB" type="button" value="确定" onclick="javascript:toUpdate();"/>
			<input class="buttonB" type="button" value="取消" onclick="javascript:$('#popWin').window('close');"/>
		  </div>
		</div>
	</body>
</html>