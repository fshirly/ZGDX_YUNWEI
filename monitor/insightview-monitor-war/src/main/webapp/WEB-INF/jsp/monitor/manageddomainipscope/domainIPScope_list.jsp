<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file = "../../common/pageincluded.jsp" %>

<html>
	<head>
	</head> 
	<body> 
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/plugin/dTree/dtree.js"></script>
		<script type="text/javascript"	src="${pageContext.request.contextPath}/js/monitor/manageddomainipscope/domainIPScope_list.js"></script>
		<div class="location" id="location">当前位置：${navigationBar}</div>
		
		<div id="dataTreeDiv" class="treetable"></div>
		
		<div class="treetabler">
			<div class="conditions" id="divFilter">
				<table>
					<tr>
						<td>
							<b>起始IP：</b>
							<input type="text" id="txtStartIPStr"/>
						</td>
						<td>
							<b>范围类型：</b>
							<select class="easyui-combobox" id="txtScopeType">
								<option value="-1">请选择</option>
								<option value="1">子网</option>
								<option value="2">IP范围</option>
							</select>
						</td>
						
						<td class="btntd">
							<a href="javascript:void(0);"
								onclick="reloadTable();">查询</a>
							<a href="javascript:void(0);" onclick="resetForm('divFilter');"
								>重置</a>
						</td>
					</tr>
				</table>
			</div>
		    <div class="datas tops1">
				<table id="tblDomainIPScope">
		
				</table>
			</div>
		</div>
	</body>
</html>