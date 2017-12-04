<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file = "../../common/pageincluded.jsp" %>
<html>
	<head>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/monitor/website/webSiteInfo.js"></script>
	</head>	
	<body>  
	<input type="hidden" id="siteType" value="${siteType}"/>
	<input type="hidden" id="moID" value="${moID}"/>
			<!-- begin .datas -->
			<div class="datas tops5">
				<table id="tblWebSiteInfo">
				</table>
			</div>
	</body>
</html>