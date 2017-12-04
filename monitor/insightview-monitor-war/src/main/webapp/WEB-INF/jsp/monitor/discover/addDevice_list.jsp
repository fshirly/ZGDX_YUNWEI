<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file = "../../common/pageincluded.jsp" %>

<html>
	<head>
	</head> 
	<body> 
		<script type="text/javascript"	src="${pageContext.request.contextPath}/js/monitor/discover/addDevice_list.js"></script>
		<div class="location" id="location" style="display: none">当前位置：${navigationBar}</div>
		<div class="conditions" id="divFilter">
			<table>
				<tr>
					<td>
						<b>IP地址：</b>
						<input type="text" id="txtIPaddress1" validator="{'default':'ipAddr','length':'1-15'}"/>
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
	    <div class="datas" id="divDeviceTask">
			<table id="tblDeviceTask">
	
			</table>
		</div>
	</body>
</html>