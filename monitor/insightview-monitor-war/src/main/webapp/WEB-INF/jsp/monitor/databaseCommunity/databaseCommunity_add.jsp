<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file = "../../common/pageincluded.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		
	</head>
	<body>
	<script type="text/javascript"
			src="${pageContext.request.contextPath}/js/monitor/databaseCommunity/databaseCommunityAdd.js"></script>
			<input id="ipt_id" type="hidden" />
				<table id="tblDatabaseCommunity" class="formtable1">
					<tr>
						<td class="title">数据库类型：</td>
						<td>
							<select name="dbmsType" id="ipt_dbmsType" validator="{'default':'*'}" onchange="showDefaultPort();" >
								<option></option>
								<option value="Oracle">Oracle</option>
								<option value="Mysql">Mysql</option>
								<option value="DB2">DB2</option>
								<option value="Sybase">Sybase</option>
								<option value="MsSql">MsSql</option>
							</select>	
						</td>
					</tr>
					
					<tr>
						<td class="title">设备IP：</td>
						<td>
							<input id="ipt_ip" class="input" validator="{'default':'*','length':'1-64'}"  /><b>*</b>
						</td>
					</tr>
					<tr id="dbNameForDb2">
						<td class="title">数据库名：</td>
						<td>
							<input id="ipt_dbName" type="text"  validator="{'default':'*','length':'1-80'}"/>
							<b>*</b>
						</td>
					</tr>
					
					<tr>
						<td class="title">用户名：</td>
						<td>
							<input id="ipt_userName" type="text" validator="{'default':'*' ,'length':'1-50'}" />
							<b>*</b>
						</td>
					</tr>
					<tr>
						<td class="title">密码：</td>
						<td>
							<input id="ipt_password" type="password" validator="{'default':'*','length':'1-50'}" />
							<b>*</b>
						</td>
					</tr>
					<tr>
						<td class="title">端口：</td>
						<td>
							<input name="port" id="ipt_port" type="text" validator="{'default':'*'}" />
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