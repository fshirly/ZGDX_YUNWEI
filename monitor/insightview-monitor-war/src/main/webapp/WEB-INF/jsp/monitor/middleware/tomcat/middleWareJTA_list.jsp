<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file = "../../../common/pageincluded.jsp" %>
<html>
	<head>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/monitor/middleware/tomcat/middleWareJTA_list.js"></script>
	</head>	
	<body>  
	<input type="hidden" id="flag" value="${flag}"/>
	<input type="hidden" id="jmxType" value="${jmxType}"/>
	<input type="hidden" id="parentMoId" value="${parentMoId}"/>
	<div class="rightContent">
		 	<div class="location">当前位置：${navigationBar}</div>
			<div class="conditions" id="divFilter">
				<table>
					<tr>
						<td>
							<b>应用服务名称：</b>
							<input type="text" id="serverName" />
						</td>
						<td>
							<b>JTA名称：</b>
							<input type="text" id="name" />
						</td>
						<td>
							<b>服务IP：</b>
							<input type="text" id="ip" />
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
				<table id="tblDataList">
				</table>
			</div>
		</div>	
	</body>
</html>