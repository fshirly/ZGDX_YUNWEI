<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>快速创建</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/css/base.css" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/css/reset.css" />
	<link rel="stylesheet" type="text/css"
		href="../style/css/jquery.autocomplete.css" />
	<link rel="stylesheet" type="text/css"
		href="../plugin/easyui/themes/default/easyui.css" />
</head>

<body>


	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/common/jquery-1.9.1.min.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/plugin/easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/plugin/autocomplete/jquery.autocomplete.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/common/commonUtil.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/dashboardPageManage/rapidQuery.js"></script>

	<table class="rapidCreatTab">
	    <tr>
	       <td colspan="2"><div id="errorText"></div></td>
	    </tr>
		<tr>
			<td class="title" height="35px">查询模块：</td>
			<td><input class="easyui-combobox" id="module" name="module" /></td>
		</tr>
		<tr>
			<td class="title" height="35px">标题：</td>
			<td><input type="text" name="title" id="title" /></td>
		</tr>
		<tr>
			<td class="title" height="35px">编号：</td>
			<td><input type="text" name="serialNo" id="serialNo" /></td>
		</tr>
		<tr>
			<td></td>
			<td>
				<input class="btn" id="searchBtn" type="button" value="搜索" />
			</td>
		</tr>
	</table>
</body>
</html>
