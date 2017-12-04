<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file = "../../common/pageincluded.jsp" %>
<html>
	<head>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/monitor/device/middleware_jvm.js"></script>
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
							<b>虚拟机名称：</b>
							<input type="text" id="jvmName" />
						</td>	
							<td>
							<b>服务IP：</b>
							<input type="text" id="ip" />
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
				<table id="tblDataList">
				</table>
			</div>
		</div>	
	</body>
</html>