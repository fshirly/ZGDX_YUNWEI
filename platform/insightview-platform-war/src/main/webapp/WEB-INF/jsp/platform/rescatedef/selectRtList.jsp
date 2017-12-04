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
	src="${pageContext.request.contextPath}/js/platform/rescatedef/selectRtList.js"></script>
</head>
<body>
	<div class="rightContent">
		<div class="conditions" id="divFilter">
			<table>
				<tr>
					<td>
						<b>类型名称：</b>				
						<input type="text" id="resTypeName" />
					</td>
					<td class="btntd">
						 <a href="javascript:void(0);" onclick="reloadTable();" >查询</a>		
						 <a href="javascript:void(0);" onclick="resetForm('divFilter');">重置</a> 
					</td>
			</tr>
			</table>
		</div>
		
		<!-- begin .datas -->
		<div class="datas">
			<table id="tblDataList">
			</table>
		</div>
		<!-- end .datas -->
		
	</div>
</body>
</html>