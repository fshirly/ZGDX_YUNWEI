<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="../../../common/pageincluded.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	</head>
	<body>
	<script type="text/javascript"
			src="${pageContext.request.contextPath}/js/monitor/middleware/middlewarecommunity/middlewarecommunity_add.js"></script>
			<input id="ipt_middleWareId" type="hidden" />
				<table id="tblMiddlewareAdd" class="formtable">
					<tr>
						<td class="title">设备IP：</td>
						<td>
							<input id="ipt_deviceIp"  class="input" validator="{'default':'*','length':'1-64'}" onblur="ipChange();"/><b>*</b> <input id="ipt_deviceId" type="hidden" value=""/>
							<!--<a href="javascript:loadDeviceInfo();" id="btnChose">选择</a>
						--></td>
						<!--<td class="title">所属管理域：</td>
						<td>
							<input id="ipt_domainName" readonly class="input"/><input id="ipt_domainID" type="hidden"/>
						</td>
					-->
						<td class="title">端&nbsp;&nbsp;口：</td>
						<td>
							<input id="ipt_port" type="text" validator="{'default':'*' ,'length':'1-128'}" onblur="portChange();" /><b>*</b>
						</td>
					</tr>
					
					<tr>
						<td class="title">中间件名称：</td>
						<td>
							<select name="port" id="ipt_middleWareName" validator="{'default':'*'}" onchange="resetType()">
								<option value="1">weblogic</option>
								<option value="2">tomcat</option>
								<option value="3">websphere</option>
							</select><b>*</b>
						</td>
						<td class="title">中间件类型：</td>
						<td>
							<input id="ipt_middleWareType" type="text" readonly="readonly" validator="{'default':'*','length':'1-128'}" /><b>*</b>
						</td>
					</tr>

					<tr id="isShowUserAndPwd" style="display: none">
						<td class="title">用户名：</td>
						<td>
							<input id="ipt_userName" type="text" validator="{'length':'0-255'}"/>
						</td>
						<td class="title">密&nbsp;&nbsp;码：</td>
						<td>
							<input id="ipt_passWord" type="password" validator="{'length':'0-255'}" />
						</td>
					</tr>
					<!--<tr>
						<td class="title">端&nbsp;&nbsp;口：</td>
						<td colspan="3">
							<input id="ipt_port" type="text" validator="{'default':'*' ,'length':'1-128'}" onblur="portChange();" /><b>*</b>
						</td>
					</tr>
					--><tr>
						<td class="title">控制台URL：</td>
						<td colspan="3">
							<input id="ipt_url"  type="text" class="x2"
								validator="{'default':'*','length':'1-150'}" msg="{reg:'1-150位字符！'}" />
								<b>*</b>
						</td>
					</tr>
				</table>
				<div class="conditionsBtn">
					<input class="buttonB" type="button" id="btnSave" value="确定" onclick="javascript:toAdd();"/>
					<input class="buttonB" type="button" id="btnUpdate" value="取消" onclick="javascript:$('#popWin').window('close');"/>
				</div>
		</body>
</html>