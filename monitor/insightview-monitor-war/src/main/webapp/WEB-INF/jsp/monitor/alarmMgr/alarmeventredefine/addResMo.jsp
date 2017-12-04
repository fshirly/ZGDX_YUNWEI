<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="../../../common/pageincluded.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head></head>
	<body>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/monitor/alarmMgr/alarmeventredefine/addResMo.js"></script> 
		<div class="winbox" >
		  <div class="conditions" id="divFilter">
			<table id="tblSearchRes">
				<tr>
					<td>
						<b>资源名称：</b>
						<input id="txtResName" />
					</td>
					<td>
						<b>资源地址：</b>
						<input id="txtDeviceIp" />
					</td>
					<td>
						<b>资源等级：</b>
						<input id="txtResLevel" />
					</td>
					<td class="btntd">
						<a onclick="reloadResTable();" class="fltrt">查询</a>
						<a onclick="resetResMOForm();" class="fltrt">重置</a>
					</td>
				</tr>
			</table>
		  </div>
		
		  <div class="datas">
			<table id="tblForSelectRes"></table>
		  </div>
		</div>
		
		<div class="conditionsBtn">
			<a class="fltrt" onclick="javascript:toAddResMO();">确定</a>
			<a class="fltrt" onclick="javascript:$('#popWin3').window('close');">取消</a>
		</div>
	</body>
</html>