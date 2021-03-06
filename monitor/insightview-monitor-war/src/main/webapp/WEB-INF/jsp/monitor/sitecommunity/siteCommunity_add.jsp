<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file = "../../common/pageincluded.jsp" %>

<html>
	<head>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/plugin/dTree/dtree.js"></script>
	</head>

	<body>
		<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/monitor/sitecommunity/siteCommunity_add.js"></script>
		<div id="divThresholdAdd">
			<table class="formtable" >
				<tr style="float:left;margin-left: 9px;">
					<td class="title">监控类型：</td>
					<td>
						<input class="easyui-combobox" id="ipt_siteType" /><b>*</b>
					</td>
				</tr>
			</table>
		
			<!--FTP凭证 -->
			<table id="addFtpCommunity"  class="formtable"  style="display: none">
				<tr>
					<td class="title">地址：</td>
					<td>
						<input id="ipt_ftpIPAddress" type="text" validator="{'default':'checkEmpty_ipAddr','length':'1-128'}" /><b>*</b>
					</td>
					<td class="title">端口：</td>
					<td>
						<input id="ipt_port"type="text" validator="{'default':'checkEmpty_ptInteger'}"/><b>*</b>
					</td>
				</tr>
				<tr>
					<td class="title">用户名：</td>
					<td>
						<input id="ipt_userName" type="text"  validator="{'default':'*','length':'1-128'}"/><b>*</b>
					</td>
					<td class="title">密码：</td>
					<td>
						<input id="ipt_password" type="password" validator="{'default':'*','length':'1-128'}"/><b>*</b>
					</td>
				</tr>
			</table>
		
			<!--http凭证 -->
			<table id="addHttpCommunity" class="formtable" style="display: none">
				<tr>
					<td class="title">地址：</td>
					<td colspan="3">
						<input id="ipt_httpIPAddress" type="text" class="x2" validator="{'default':'checkEmpty_url','length':'1-5000'}" /><b>*</b>
					</td>
				</tr>
				<tr>
					<td class="title">请求方式：</td>
					<td colspan="3">
						<input id="ipt_requestMethod" class="input"  type="hidden"/>
						<input type="radio" id="ipt_requestMethod1" name="requestMethod"  value="1" checked style="width:13px">&nbsp;GET
						&nbsp;
						<input type="radio" id="ipt_requestMethod2" name="requestMethod" value="2" style="width:13px"/>&nbsp;POST
						&nbsp;
						<input type="radio" id="ipt_requestMethod3" name="requestMethod" value="3" style="width:13px"/>&nbsp;HEAD
					</td>
				</tr>
			</table>
			
			<div class="conditionsBtn">
				<input type="button" value="确定" onclick="javascript:toAdd();"/>
				<input type="button" value="取消" onclick="javascript:$('#popWin').window('close');"/>
			</div>
			
		</div>
	</body>
</html>
