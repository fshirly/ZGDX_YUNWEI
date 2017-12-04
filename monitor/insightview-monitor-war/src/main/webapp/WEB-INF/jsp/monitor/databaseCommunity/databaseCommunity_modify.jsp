<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/style/css/base.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/css/reset.css" />
<!-- mainframe -->

<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/common/jquery-1.9.1.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/common/commonUtil.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/common/base64.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/plugin/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/common/easyui-lang-zh_CN.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/style/style_one/js/main.js"></script>
		
</head>
	<body>
	<script type="text/javascript"
			src="${pageContext.request.contextPath}/js/monitor/databaseCommunity/databaseCommunityModify.js"></script>
			<input id="ipt_id" type="hidden" value="${dbmsCommunity.id}"/>
				<table id="tblDatabaseCommunity" class="formtable1">
					<tr>
						<td class="title">数据库类型：</td>
						<td>
							<select name="dbmsType" id="ipt_dbmsType" validator="{'default':'*'}" onchange="showDefaultPort();">
								<option value="Oracle" <c:if test="${dbmsCommunity.dbmsType=='Oracle'}" >selected</c:if> >Oracle</option>
								<option value="Mysql" <c:if test="${dbmsCommunity.dbmsType=='Mysql'}" >selected</c:if>>Mysql</option>
								<option value="DB2" <c:if test="${dbmsCommunity.dbmsType=='DB2'}" >selected</c:if>>DB2</option>
								<option value="Sybase" <c:if test="${dbmsCommunity.dbmsType=='Sybase'}" >selected</c:if>>Sybase</option>
								<option value="MsSql" <c:if test="${dbmsCommunity.dbmsType=='MsSql'}" >selected</c:if>>MsSql</option>
						</select>	
						</td>
					</tr>
					
					<tr>
						<td class="title">设备IP：</td>
						<td>
							<input id="ipt_ip" type="text"  value="${dbmsCommunity.ip}" readonly="readonly"/><b>*</b>
						</td>
					</tr>
					<tr  id="dbNameForDb2">
						<td class="title">数据库名：</td>
						<td>
							<input id="ipt_dbName" type="text" value="${dbmsCommunity.dbName}" validator="{'default':'*' ,'length':'1-80'}" />
						</td>
					</tr>

					<tr id="readCommunitys">
						<td class="title">用户名：</td>
						<td>
							<input id="ipt_userName" type="text" value="${dbmsCommunity.userName}" validator="{'default':'*' ,'length':'1-50'}" />
							<b>*</b>
						</td>
					</tr>
					<tr>
						<td class="title">密码：</td>
						<td>
							<input id="ipt_password" type="password" value="${dbmsCommunity.password}" validator="{'default':'*' ,'length':'1-50'}" />
							<b>*</b>
						</td>
					</tr>
					<tr>
						<td class="title">端口：</td>
						<td>
							<input name="port" id="ipt_port" type="text" value="${dbmsCommunity.port}" validator="{'default':'*'}" readonly="readonly"/>
							<b>*</b>
						</td>
					</tr>
				</table>
				<div class="conditionsBtn">
					<input class="buttonB" type="button" id="btnSave" value="确定" onclick="javascript:toUpdate();"/>
					<input class="buttonB" type="button" id="btnUpdate" value="取消" onclick="javascript:$('#popWin').window('close');"/>
				</div>
		</body>
</html>
