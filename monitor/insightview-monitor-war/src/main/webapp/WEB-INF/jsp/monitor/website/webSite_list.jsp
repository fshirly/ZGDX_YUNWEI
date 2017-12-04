<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file = "../../common/pageincluded.jsp" %>
<html>
	<head>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/monitor/website/webSite_list.js"></script>
	</head>	
	<body>  
	<input type="hidden" id="flag" value="${flag}"/>
	<input type="hidden" id="includeType" value="${includeType}"/>
    <input type="hidden" id="mOClassID" value="${mOClassID}"/>
	<input type="hidden" id="id" value="${id}"/>
	<input type="hidden" id="relationPath" value="${relationPath}"/>
	<div class="rightContent">
		 	<div class="location">当前位置：${navigationBar}</div>
			<div class="conditions" id="divFilter">
				<table>
					<tr>
						<td>
							<b>站点名称：</b>
							<input type="text" id="txtSiteName" />
						</td>
						<td>
							<b>监控类型：</b>
							<select class="easyui-combobox" id="txtSiteType" panelHeight="80"><option value="-1"></option>
							<option value="1">FTP</option>
							<option value="2">DNS</option>
							<option value="3">HTTP</option>
							<option value="4">TCP</option>
							</select>
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
				<table id="tblWebSiteList">
				</table>
			</div>
		</div>	
	</body>
</html>