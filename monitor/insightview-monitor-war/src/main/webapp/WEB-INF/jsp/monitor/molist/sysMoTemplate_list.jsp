<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file = "../../common/pageincluded.jsp" %>
<html>
	<head>
	</head>	
	<body>  
	<script type="text/javascript"
			src="${pageContext.request.contextPath}/plugin/autocomplete/jquery.autocomplete.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/monitor/molist/sysMoTemplateList.js"></script>
	
	<script type="text/javascript"
	src="${pageContext.request.contextPath}/plugin/dTree/dtree.js"></script>	
		<div class="rightContent">
		 	<div class="location">当前位置：${navigationBar}</div>	
			<div class="conditions" id="divFilter">
					<table>
					<tr>
						<td>
							<b>模板名称：</b>
							<input type="text" id="txtTemplateName" />
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
			<div class="datas">
				<table id="tblSysMoTemplateList">
				</table>
			</div>
		</div>	
	</body>
</html>