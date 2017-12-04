<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file = "../../common/pageincluded.jsp" %>
<html>
	<head>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/monitor/stoneu/stoneu_list.js"></script>
	</head>	
	<body>  
	<div id="tabs_window_stoneu" class="easyui-tabs viewtabs">
		<input id= "hiddenMoClassID"  type="hidden" value="${moclassID}">
		<div title="设备列表" >
		 	<div class="location">当前位置：${navigationBar}</div>
			<div class="conditions" id="divFilter">
				<table>
					<tr>
						<td>
							<b>模块名称：</b>
							<input type="text" id="moName" />
						</td>
						 
						<td class="btntd">
							<a href="javascript:void(0);" onclick="reloadTable();">查询</a>
							<a href="javascript:void(0);" onclick="resetForm('divFilter');">重置</a>
						</td>
					</tr>
				</table>
			</div>
			
			<!-- begin .datas -->
			<div class="datas topwin">
				<table id="tblStoneuList">
				</table>
			</div>
		</div>	
		</div>
	</body>
</html>