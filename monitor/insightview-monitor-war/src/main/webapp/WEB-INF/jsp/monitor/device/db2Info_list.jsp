<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file = "../../common/pageincluded.jsp" %>
<html>
	<head>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/monitor/device/db2Info_list.js"></script>
		<%
		String flag = (String)request.getAttribute("flag");
		String instanceMOID = (String)request.getAttribute("instanceMOID");
		String isMoid = (String)request.getAttribute("isMoid");
		String dbmsMoId = (String)request.getAttribute("dbmsMoId");
	 %>
	</head>	
	<body>  
			<input type="hidden" id="flag" value="<%=flag %>"/>
			<input type="hidden" id="instanceMOID" value="<%=instanceMOID %>"/>
			<input type="hidden" id="isMoid" value="<%=isMoid %>"/>
			<input type="hidden" id="dbmsMoId" value="<%=dbmsMoId %>"/>
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
						<td>
							<b>库实例名：</b>
							<input type="text" id="instanceName" />
						</td>
					</tr>
					<tr>
						<td>
							&nbsp;
						</td>
						<td>
							&nbsp;
						</td>
						<td class="btntd">
							<a href="javascript:void(0);" onclick="reloadTable();">查询</a>
							<a href="javascript:void(0);" onclick="resetForm('divFilter');">重置</a>
						</td>
					</tr>
				</table>
			</div>
			
			<!-- begin .datas -->
			<div class="datas tops2">
				<table id="tblDb2InfoList">
				</table>
			</div>
		</div>	
	</body>
</html>