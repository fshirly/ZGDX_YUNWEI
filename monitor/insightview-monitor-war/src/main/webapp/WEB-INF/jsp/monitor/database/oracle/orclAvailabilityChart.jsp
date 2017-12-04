<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="../../../common/pageincluded.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<%
		String moId = (String)request.getAttribute("moId");
	 %>
	</head>
		
	<body>
	<script type="text/javascript"
				src="${pageContext.request.contextPath}/js/monitor/echart/echarts-plain-map.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/monitor/database/oracle/orclAvailabilityChart.js"></script>
		  <div>
		  	<input type="hidden" id="moId" value="<%=moId %>"/>
				<select id="seltDate" name="seltDate" onchange="getChartJsonData()">
						<option value="0">最近1小时</option>
						<option value="1">最近一天</option>
						<option value="2">最近一周</option>
						<option value="3">最近一月</option>
						<option value="4">最近三个月</option>
						<option value="5">最近半年</option>
						<option value="6">最近一年</option>
				</select>
				<div id="database_Chart" style="height:250px;" ></div>
			</div>			
	</body>
</html>