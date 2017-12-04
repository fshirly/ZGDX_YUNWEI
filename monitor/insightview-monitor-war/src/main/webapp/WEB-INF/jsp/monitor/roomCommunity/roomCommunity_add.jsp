<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="../../common/pageincluded.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	</head>
	<body>
	<script type="text/javascript"
			src="${pageContext.request.contextPath}/js/monitor/roomCommunity/roomcommunity_add.js"></script>
			<input id="ipt_id" type="hidden" />
				<table id="tblRoomAdd" class="formtable1">
					<tr>
						<td class="title">IP：</td>
						<td>
							<input id="ipt_ipAddress"  class="input" validator="{'default':'ipAddr','length':'1-64'}" /><b>*</b> <input id="ipt_deviceId" type="hidden" value=""/>
							<!--<a href="javascript:loadDeviceInfo();" id="btnChose">选择</a>
						--></td>
						<!--<td class="title">所属管理域：</td>
						<td>
							<input id="ipt_domainName" readonly class="input"/><input id="ipt_domainID" type="hidden"/>
						</td>
					--></tr>
					<tr>
						<td class="title">用户名：</td>
						<td>
							<input id="ipt_userName" type="text" validator="{'length':'0-128'}"/>
						</td>
					</tr>
					<tr>
						<td class="title">密码：</td>
						<td>
							<input id="ipt_passWord" type="password" validator="{'length':'0-128'}" />
						</td>
					</tr>
					<tr>
						<td class="title">端口：</td>
						<td>
							<input id="ipt_port" type="text" validator="{'default':'*' ,'length':'1-11'}" /><b>*</b>
						</td>
					</tr>
				</table>
				<div class="conditionsBtn">
					<input class="buttonB" type="button" id="btnSave" value="确定" onclick="javascript:toAdd();"/>
					<input class="buttonB" type="button" id="btnUpdate" value="取消" onclick="javascript:$('#popWin').window('close');"/>
				</div>
		</body>
</html>