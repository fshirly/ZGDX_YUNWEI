<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file = "../../common/pageincluded.jsp" %>
<html>
	<head>
	<link href="${pageContext.request.contextPath}/plugin/select2/select2.css" rel="stylesheet"/>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/commonPlugins.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/plugin/select2/select2.js"></script>	
		<script type="text/javascript" src="${pageContext.request.contextPath}/plugin/dTree/dtree.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/plugin/dTree/combo/comboJS.js"></script>	
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/monitor/device/unkonwnDevice_list.js"></script>
	</head>		
	<body>  
	<input type="hidden" id="mOClassID" value="${mOClassID}"/>
	<input type="hidden" id="flag" value="${flag}"/>
		<div class="rightContent">
		 	<div class="location">当前位置：${navigationBar}</div>
			<div class="conditions" id="divFilter">
				<table>
					<tr>
						<td>
							<b>设备IP：</b>
							<input type="text" id="deviceip" />
						</td>
						<td>
							<b>管理域：</b>
							 <input id="domainName" class="inputs"
											onClick='showTree(this,"addressFatherId")' readonly="readonly"
													alt="-1" />
							 <div id="combdtree" class="dtreecob">
									<div id="dataOrgTreeDiv" class="dtree"
										style="width: 100%; height: 200px; overflow: auto;"></div>
										<div class="dBottom">
											<a href="javascript:hiddenDTree();">关闭</a>
										</div>
					 			</div>
					     	</td>
						<td class="btntd">
							<a href="javascript:void(0);" onclick="reloadTable();">查询</a>
							<a href="javascript:void(0);" onclick="resetFormFilter('divFilter');">重置</a>
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