<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file = "../../../common/pageincluded.jsp" %>
<html>
	<head>
	</head><!-- 数据库拆线图表 -->
	<body>
	<link href="${pageContext.request.contextPath}/style/css/sDashboard.css" rel="stylesheet" />
	<link href="${pageContext.request.contextPath}/style/css/reset.css" rel="stylesheet" />
		  <input type="hidden" id="proUrl" value="${proUrl}"/><!--查找数据的url  -->
		  <input type="hidden" id="perfKind" value="${perfKind}"/><!-- 根据字段类型展示图 -->
		  <input type="hidden" id="moid" value="${moid}"/><!--数据库服务ID -->
		  <div id="timeBar" class="databbar">
		  		<c:if test="${!empty seltItem}">
		  			<select id="seltItem" name="seltItem" onchange="getChartJsonData('24H')" >
					<c:forEach items="${seltItem}" var="entry">
						<option value="<c:out value='${entry.key}' />"><c:out value="${entry.value}" /></option>
					</c:forEach>										
				</select>&nbsp;
		  		</c:if>				
				<a href="javascript:void(0);" class="line" onclick="getChartJsonData('24H');"/><b>24h</b></a>
		  		<a href="javascript:void(0);" class="line"  onclick="getChartJsonData('Today');"/><b>今天</b></a>
		  		<a href="javascript:void(0);" class="line"  onclick="getChartJsonData('Yes');"/><b>昨天</b></a>
		  		<a href="javascript:void(0);" class="line"  onclick="getChartJsonData('Week');"/><b>本周</b></a>
		  		<a href="javascript:void(0);" class="line"  onclick="getChartJsonData('7D');"/><b>7天</b></a>
		    	<a href="javascript:void(0);" class="line"  onclick="getChartJsonData('Month');"/><b>本月</b></a>
		  		<a href="javascript:void(0);" class="line"  onclick="getChartJsonData('LastMonth');"/><b>上月</b></a>
			</div>	
			<div id="database_line" style="height:210px; " ></div>		
	</body>
	<!-- 注意需要放到文件末尾处 -->
<script src="${pageContext.request.contextPath}/js/monitor/echarts/echarts-plain-map.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/monitor/database/db2/db2LineChart.js"></script>
</html>