<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file = "../../common/pageincluded.jsp" %>
<html>
	<head>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/monitor/device/msDB_list.js"></script>
		<%
		String flag = (String)request.getAttribute("flag");
		String serverMoId = (String)request.getAttribute("serverMoId");
	 %>
	</head>	
	<body>  
		<input type="hidden" id="flag" value="<%=flag %>"/>
		<input type="hidden" id="serverMoId" value="<%=serverMoId %>"/>
		<div class="rightContent">
		 	<div class="location">当前位置：${navigationBar}</div>	
			<div class="conditions" id="divFilter">
				<table>
					<tr>
						<td>
							<b>服务IP：</b>
							<input type="text" id="ip" />
						</td>
						<td>
							<b>数据库名称：</b>
							<input type="text" id="databaseName" />
						</td>
						<td class="btntd">
							<a href="javascript:void(0);" onclick="reloadTable();">查询</a>
							<a href="javascript:void(0);" onclick="resetForm('divFilter');">重置</a>
						</td>
					</tr>
				</table>
			</div>
			
			<!-- begin .datas -->
			<div class="datas">
				<table id="tblMsDBList">
				</table>
			</div>
		</div>	
	</body>
</html>