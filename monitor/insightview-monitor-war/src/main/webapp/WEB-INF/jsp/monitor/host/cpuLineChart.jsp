<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="../../common/pageincluded.jsp"%>
<html>
	<head>
	</head><!-- 主机详情 -->
	<body><input type="hidden" id="MOID" name="MOID" value="${MOID}"/>
				
				<div id="cpu_line" style="height:400px; border:1px solid #ccc;"></div>
			
	</body>
	<!-- 注意需要放到文件末尾处 -->
<script src="${pageContext.request.contextPath}/js/monitor/echarts/echarts-plain-map.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/monitor/host/cpuLineChart.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/monitor/host/commLineChart.js"></script>
</html>