<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file = "../../common/pageincluded.jsp" %>
<html>
	<head>
	</head><!-- 数据库拆线图表 -->
	<body>
	<link href="${pageContext.request.contextPath}/style/css/sDashboard.css" rel="stylesheet" />
	<link href="${pageContext.request.contextPath}/style/css/reset.css" rel="stylesheet" />
		  <input type="hidden" id="proUrl" value="${proUrl}"/>
		  <input type="hidden" id="moID" value="${moID}"/>
		   <input type="hidden" id="siteType" value="${siteType}"/>
		  <div id="timeBar" class="databbar">
				<a href="javascript:void(0);" class="line" onclick="getChartJsonData('24H');"/><b>24h</b></a>
		  		<a href="javascript:void(0);" class="line"  onclick="getChartJsonData('Today');"/><b>今天</b></a>
		  		<a href="javascript:void(0);" class="line"  onclick="getChartJsonData('Yes');"/><b>昨天</b></a>
		  		<a href="javascript:void(0);" class="line"  onclick="getChartJsonData('Week');"/><b>本周</b></a>
		  		<a href="javascript:void(0);" class="line"  onclick="getChartJsonData('7D');"/><b>7天</b></a>
		    	<a href="javascript:void(0);" class="line"  onclick="getChartJsonData('Month');"/><b>本月</b></a>
		  		<a href="javascript:void(0);" class="line"  onclick="getChartJsonData('LastMonth');"/><b>上月</b></a>
			</div>	
			<div id="database_line" style="height:210px; " ondblclick="toResponseDeatil()"></div>		
	</body>
	<!-- 注意需要放到文件末尾处 -->
<script src="${pageContext.request.contextPath}/js/monitor/echarts/echarts-plain-map.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/monitor/website/webSiteLineChart.js"></script>
</html>