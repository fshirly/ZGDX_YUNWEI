<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/style/css/base.css" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/style/css/reset.css" />
<link rel="stylesheet" type="text/css"
	href="../plugin/easyui/themes/default/easyui.css" />
</head>
<body>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/common/jquery-1.9.1.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/common/commonUtil.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/dashboardPageManage/knowledgeSearch.js"></script>

	<table class="knowledgeTab">
		<tr>
			<td><div style="height:50px;"></div></td>
		</tr>
		<tr>
			<td align="center"><b><font color="#6495ED">检索一下</font></b></td>
		</tr>
		<tr>
		<td>
			<div style="float:left;width:283px;"><input type="text" name="title" id="title" /></div>
			<div style="float:left"><input class="btn" id="searchBtn" type="button" value="检索" /></div>
			</td>
		</tr>
		<tr>
			<td><div id="errorText"></div></td>
		</tr>
	</table>
</body>
</html>
