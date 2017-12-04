<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file = "../../common/pageincluded.jsp" %>
<html>
	<head>
		<%
			String moClassID = (String)request.getAttribute("moClassID");
		 %>
		</head>	
	<body>  
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/monitor/molist/sysMoTypeConfigList.js"></script>
	
		<div class="rightContent" style="border:1px double rgb(207, 206, 206)">
			<div class="conditions" id="divFilter">
			<input id="moClassID" type="hidden" value="<%=moClassID %>"/>
					<table>
					<tr>
						<td>
							<b>监测器类型名称：</b>
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
				<table id="tblSysMoTypeList"></table>
				
			</div>
			
		</div>	
		<div class="conditionsBtn">
					<input type="button" id="btnSave" value="确定" onclick="javascript:toAddTypes();"/>
					<input type="button" id="btnClose" value="取消" onclick="javascript:doClose()"/>
				</div>
	</body>
</html>