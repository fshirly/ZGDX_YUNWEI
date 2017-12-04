<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="../../../common/pageincluded.jsp"%>
<html>
	<head>
	</head>

	<body>
		<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/monitor/middleware/middlewarecommunity/middlewarecommunity_detail.js"></script>
		<% 
		 String id =(String)request.getAttribute("id"); 
		%> 
	<div id="divTaskDetail">
	<input type="hidden" id="id" value="<%= id%>" /> 
		<table id="tblTaskDetail" class="tableinfo">
			<tr>
				<td>
					<b class="title">设备IP：</b>
					<label id="lbl_deviceIp"></label>
				</td>
				<td>
					<b class="title">所属管理域：</b>
					<label id="lbl_domainName"></label>
				</td>
			</tr>
			<tr>
				<td>
					<b class="title">中间件名称：</b>
					<label id="lbl_middleWareName" ></label>
				</td>
				<td>
					<b class="title">中间件类型：</b>
					<label id="lbl_middleWareType" ></label>
				</td>
			</tr>
			<tr>
				<td id="isShowUserAndPwd" style="display: none">
					<b class="title">用户名：</b>
					<label id="lbl_userName" ></label>
				</td>
				<!--<td>
					<b class="title">密码：</b>
					<label id="lbl_passWord" ></label>
				</td>-->
			
				<td>
					<b class="title">端口：</b>
					<label id="lbl_port"></label>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<b class="title">URL：</b>
					<label id="lbl_url" rows="3" class="x2"></label>
				</td>
			</tr>
		</table>
		<div class="conditionsBtn">
			<input type="button" id="btnClose" value="关闭" onclick="javascript:$('#popWin').window('close');"/>
		</div>
	</div>
	</body>
</html>
