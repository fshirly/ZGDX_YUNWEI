<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file = "../../common/pageincluded.jsp" %>
<html>
	<head>
	<!-- mainframe -->
		<script type="text/javascript"
				src="${pageContext.request.contextPath}/js/main.js"></script>
		<script type="text/javascript"
				src="${pageContext.request.contextPath}/plugin/dTree/dtree.js"></script>
		<script type="text/javascript"
				src="${pageContext.request.contextPath}/plugin/dTree/combo/comboJS.js"></script>
		<script type="text/javascript"
				src="${pageContext.request.contextPath}/js/platform/snmpdevice/selectSnmpPenList.js"></script>
	</head> 
	<body> 
		<div class="rightContent">
			<div class="conditions" id="divFilter">
				<table>
				<tr>
					<td>
						<b>企业名称：</b>				
						<input type="text" class="inputs" id="organizationName" />
					</td>
					<td class="btntd">
						 <a href="javascript:void(0);" onclick="reloadTable();" >查询</a>		
						 <a href="javascript:void(0);" onclick="resetForm('divFilter');">重置</a> 
					</td>
				</tr>
				</table>
			</div>
			<div class="datas">
				<table id="tblSelectDataList">
				</table>
			</div>
		</div>
	</body>
</html>
