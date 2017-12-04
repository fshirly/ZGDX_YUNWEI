<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file = "../../common/pageincluded.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
	<head>
	<%
		String mid = (String)request.getAttribute("mid");
	 %>
	</head> 
	<body> 
		<script type="text/javascript"	src="${pageContext.request.contextPath}/js/monitor/molist/sysMoInfoDetail.js"></script>
		  <input id="ipt_mid" type="hidden" value="<%=mid %>"/>
		  <div id="tabs_window" style="width:100%;height:100%;">
			<div title="基本信息" id="moBaseInfo" style="overflow: hidden;">
				<iframe id="ifr0" scrolling="auto" frameborder="0" style="width:100%;height:100%;"></iframe>
			</div>
			<div title="适用范围" id="moRange">
				<iframe id="ifr1" scrolling="auto" frameborder="0" style="width:100%;height:100%;"></iframe>
	        </div>
	</div>
	</body>
</html>