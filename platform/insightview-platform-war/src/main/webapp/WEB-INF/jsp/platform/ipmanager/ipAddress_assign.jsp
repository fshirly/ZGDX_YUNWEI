<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file = "../../common/pageincluded.jsp" %>
<html>
	<head>
	</head>

	<body>
		<link href="${pageContext.request.contextPath}/plugin/select2/select2.css" rel="stylesheet"/>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/commonPlugins.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/commonUtil.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/plugin/select2/select2.js"></script>
		<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/platform/ipmanager/ipAddress_assign.js"></script>
		<%
			String id =(String)request.getAttribute("id");
			String deptId =(String)request.getAttribute("deptId");
		 %>
		<div id="divIPAddressAssign">
			<input type="hidden" id="id" value="<%= id%>"/>
			<input id="addressSubNetId" type="hidden" >
			<input id="addressDeptId" type="hidden"  value="<%= deptId%>">
			<table id="tblIPAddressAssign" class="formtable1">
				<tr>
					<td class="title">子网地址：</td>
					<td>
					  <input id ="ipt_ipAddress"  disabled="disabled"/>
					</td>
				</tr>
				<tr>
					<td class="title">子网掩码：</td>
					<td>
					  <input id="ipt_subNetMark"  disabled="disabled"/>
					</td>
				</tr>
				<tr>
					<td class="title">使用人：</td> 
					<td>
						<select id="ipt_userId">
						<option value="-1">请选择...</option>
						</select>
					</td>
				</tr>
				<tr>
					<td class="title">所属网络类型：</td>
					<td>
					  <input id="ipt_subNetTypeName"  disabled="disabled"/>
					</td>
				</tr>	
				<tr>
					<td class="title">备注：</td>
					<td><textarea rows="3" id="ipt_note" validator="{'length':'0-100'}"></textarea></td>
				</tr>
			</table>
		
			<div class="conditionsBtn">
				<input type="button" value="确定" onclick="javascript:doGiveIP();"/>
				<input type="button" value="取消" onclick="javascript:$('#popWin').window('close');"/>
			</div>
		</div>
	</body>
</html>
