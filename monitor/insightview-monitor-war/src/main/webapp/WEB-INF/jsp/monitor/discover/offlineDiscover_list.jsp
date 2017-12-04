<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file = "../../common/pageincluded.jsp" %>

<html>
	<head>
	</head> 
	<body> 
		<script type="text/javascript"	src="${pageContext.request.contextPath}/js/monitor/discover/offlineDiscover_list.js"></script>
		<div class="location">当前位置：${navigationBar}</div>
		<div class="conditions" id="divFilter">
			<table>
				<tr>
					<td>
						<b>起始IP地址：</b>
						<input type="text" id="txtIPaddress1" validator="{'default':'ipAddr','length':'1-15'}"/>
					</td>
					<td>
						<b>发现类型：</b>
						<select class="easyui-combobox" id="txtTaskType" data-options="editable:false">
							<option value="-1">请选择</option>
							<option value="1">地址段</option>
							<option value="2">子网</option>
							<option value="3">路由</option>
							<option value="4">单对象</option>
						</select>
					</td>
					<td>
						<b>采集机： </b>
						<input type="text" id="txtCollectorName"/>
					</td>
					
					<td class="btntd">
						<a href="javascript:void(0);"
							onclick="reloadTable();">查询</a>
						<a href="javascript:void(0);" onclick="resetFormDiv('divFilter');"
							>重置</a>
					</td>
				</tr>
			</table>
		</div>
	    <div class="datas">
			<table id="tblOfflineDiscoverTask">
	
			</table>
		</div>
	</body>
</html>