<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file = "../../common/pageincluded.jsp" %>
<html>
	<head>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/monitor/molist/sysMoTypeList.js"></script>
	</head>	
	<body>  
		<div class="rightContent">
		 	<div class="location">当前位置：${navigationBar}</div>	
			<div class="conditions" id="divFilter">
					<table>
					<tr>
						<td>
							<b>监测器类型：</b>
							<input type="text" id="txtMonitorTypeName" />
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
				<table id="tblSysMoTypeList">
				</table>
			</div>
		</div>	
	</body>
</html>