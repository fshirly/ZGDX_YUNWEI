<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file = "../../common/pageincluded.jsp" %>
<html>
	<head>
	</head>

	<body>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/monitor/offlinecollector/offlineCollector_list.js"></script>
		<div class="rightContent">
			<div class="location">
				当前位置：${navigationBar}
			</div>
			<div class="conditions" id="divFilter">
				<table>
					<tr>
						<td>
							<b>采集机IP：</b>
							<input type="text" id="txtIP" />
						</td>
						<td>
							<b>服务名称：</b>
							<input type="text" id="txtServiceName" />
						</td>
						
						<td class="btntd">
							<a href="javascript:void(0);" onclick="reloadTable();">查询</a>
							<a href="javascript:void(0);" onclick="resetForm('divFilter');">重置</a> 
						</td>
					</tr>
				</table>
			</div>
			<div class="datas">
				<table id="tblOfflineCollector">
				</table>
			</div>
		</div>
	</body>
</html>
