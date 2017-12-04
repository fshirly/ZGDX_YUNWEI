<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file = "../../common/pageincluded.jsp" %>
<html>
	<head>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/monitor/website/webSiteFtp_list.js"></script>
	</head>	
	<body>  
	<input type="hidden" id="flag" value="${flag}"/>
	<input type="hidden" id="id" value="${id}"/>
    <input type="hidden" id="relationPath" value="${relationPath}"/>
    <input type="hidden" id="mOClassID" value="${mOClassID}"/>
	<div class="rightContent">
		 	<div class="location">当前位置：${navigationBar}</div>
			<div class="conditions" id="divFilter">
				<table>
					<tr>
						<td>
							<b>站点名称：</b>
							<input type="text" id="txtSiteName" />
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
				<table id="tblWebSiteListFtp">
				</table>
			</div>
		</div>	
	</body>
</html>