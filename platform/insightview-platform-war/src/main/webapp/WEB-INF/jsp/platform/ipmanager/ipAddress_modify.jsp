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
		src="${pageContext.request.contextPath}/js/platform/ipmanager/ipAddress_modify.js"></script>
		<%
			String id =(String)request.getAttribute("id");
			String deptId =(String)request.getAttribute("deptId");
			String userId =(String)request.getAttribute("userId");
		 %>
		<div id="divIPAddressModify">
			<input type="hidden" id="id" value="<%= id%>"/>
			<input type="hidden" id="deptId" value="<%= deptId%>"/>
			<table id="tblIPAddressModify" class="formtable1">
				<tr>
					<td class="title">子网地址：</td>
					<td>
					<input id ="ipt_ipAddress"  disabled="disabled"/>
					</td>
				</tr>
				<tr>
					<td class="title">子网掩码：</td>
					<td><input id="ipt_subNetMark"  disabled="disabled"/>
				</tr>
				<tr>
					<td class="title">使用人：</td> 
					<td>
						<select id="ipt_userId" value="<%= userId%>">
						<option value="-1">请选择...</option>
						</select>
					</td>
				</tr>
				<tr>
					<td class="title">所属网络类型：</td>
					<td><input id="ipt_subNetTypeName"  disabled="disabled"/>
				</tr>	
				<tr>
					<td class="title">备注：</td>
					<td><textarea rows="3" id="ipt_note" validator="{'length':'0-100'}"></textarea></td>
				</tr>
			</table>
		
			<div class="conditionsBtn">
				<input type="button" value="确定" onclick="javascript:doUpdate();"/>
				<input type="button" value="取消" onclick="javascript:$('#popWin').window('close');"/>
			</div>
		</div>
	</body>
</html>
