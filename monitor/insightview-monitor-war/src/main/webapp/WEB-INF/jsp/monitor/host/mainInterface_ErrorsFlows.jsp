<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="../../common/pageincluded.jsp"%>
<html>
	<head>
	</head><!-- 主机接口,统计接口流入错包数,接口流出错包数 -->
	<body><input type="hidden" id="proUrl" value="${proUrl}"/>
		  <input type="hidden" id="interfaceId" value="${interfaceId}"/>
		  <input type="hidden" id="perfKind" value="3"/>
		  <div id="interface_line" style="height:200px; " ondblclick="toInterfaceDeatil()"></div>
	</body>
	<!-- 注意需要放到文件末尾处 -->
<script src="${pageContext.request.contextPath}/js/monitor/echarts/echarts-plain-map.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/monitor/host/mainInterface_Flows.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/monitor/host/commLineChart.js"></script>
</html>