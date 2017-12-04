<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file = "../../../common/pageincluded.jsp" %>
<html>
	<head>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/monitor/database/db2/db2Info_list.js"></script>
		<%
		String moID = request.getParameter("moID");
		 %>
	</head>	
	<body>  
		<table>	
			<tr>
				<td>
				<div class="datas tops5">
				<input id="liInfo" value="${liInfo}" type="hidden"/>
				<input id="moID" type="hidden" value="<%=moID %>"/>
				<table id="tblDb2InfoList">
				</table>
				</div>
				</td>
			</tr>
		</table>
			
	</body>
</html>