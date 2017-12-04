<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file = "../../../common/pageincluded.jsp" %>

<html>
	<head>
	</head> 
	<body> 
		<script type="text/javascript"	src="${pageContext.request.contextPath}/js/monitor/alarmMgr/traptask/trapTask_list.js"></script>
		<div class="location">当前位置：${navigationBar}</div>
		<div class="conditions" id="divFilter">
			<table>
				<tr>
					<td>
						<b>trap服务器地址：</b>
						<input id="txtServerIP" validator="{'default':'ipAddr','length':'1-15'}"/>
					</td>
					<td>
						<b>trap事件：</b>
						<input type="text" id="txtAlarmOID" validator="{'default':'ipAddr','length':'1-15'}"/>
					</td>
					<td>
						<b>采集机： </b>
						<input type="text" id="txtCollectorName"/>
					</td>
					<td class="btntd">
						<a href="javascript:void(0);"
							onclick="reloadTable();">查询</a>
						<a href="javascript:void(0);" onclick="resetForm('divFilter');"
							>重置</a>
					</td>
				</tr>
			</table>
		</div>
	    <div class="datas">
			<table id="tblTrapTask">
	
			</table>
		</div>
	</body>
</html>