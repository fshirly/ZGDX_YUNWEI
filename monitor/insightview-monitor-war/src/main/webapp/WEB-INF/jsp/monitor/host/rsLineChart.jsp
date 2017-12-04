<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file = "../../common/pageincluded.jsp" %>
<html>
	<head>
	</head><!-- 数据库拆线图表 -->
	<body>
	<link href="${pageContext.request.contextPath}/style/css/sDashboard.css" rel="stylesheet" />
	<link href="${pageContext.request.contextPath}/style/css/reset.css" rel="stylesheet" />
		  <input type="hidden" id="proUrl" value="${proUrl}"/><!--查找数据的url  -->
		  <input type="hidden" id="preUrl" value="${preUrl}"/><!--查找数据的url  -->
		  <input type="hidden" id="moClass" value="${moClass}"/><!-- 根据字段类型展示图 -->
		  <input type="hidden" id="moid" value="${moid}"/><!--数据库服务ID -->
		  <input type="hidden" id="perfKind" value="1"/>
		   <input type="hidden" id="timeVal" />
		  <div id="timeBar" class="databbar">
		  		<c:if test="${!empty seltItem}">
		  			<select id="seltItem" name="seltItem" onchange="getChartJsonData('24H')" >
					<c:forEach items="${seltItem}" var="entry">
						<option value="<c:out value='${entry.key}' />"><c:out value="${entry.value}" /></option>
					</c:forEach>										
				</select>&nbsp;
		  		</c:if>				
			        <a   href="javascript:void(0);" onclick="getChartJsonData('24H');" class="line"><b>24h</b></a>
					<a   href="javascript:void(0);" onclick="getChartJsonData('Today');" class="line"><b>今天</b></a>
					<a   href="javascript:void(0);" onclick="getChartJsonData('Yes');" class="line"><b>昨天</b></a>
					<a   href="javascript:void(0);" onclick="getChartJsonData('Week');" class="line"><b>本周</b></a>
					<a   href="javascript:void(0);" onclick="getChartJsonData('7D');" class="line"><b>7天</b></a>
					<a   href="javascript:void(0);" onclick="getChartJsonData('Month');" class="line"><b>本月</b></a>
					<a   href="javascript:void(0);" onclick="getChartJsonData('LastMonth');"><b>上月</b></a>	
			</div>	
			<div id="rs_line" style="height:210px;" ondblclick="toInterfaceDeatil()"></div>		
	</body>
	<!-- 注意需要放到文件末尾处 -->
<script src="${pageContext.request.contextPath}/js/monitor/echarts/echarts-plain-map.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/monitor/host/rsLineChart.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/monitor/host/commLineChart.js"></script>
</html>