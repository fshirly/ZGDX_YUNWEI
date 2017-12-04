<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file = "../../../common/pageincluded.jsp" %>
<html>
	<head>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/monitor/database/db2/db2BufferPool_list.js"></script>
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
						<table id="tblDb2BufferPoolInfo">

						</table>
				</div>
				</td>
			</tr>
		</table>
			
	</body>
</html>