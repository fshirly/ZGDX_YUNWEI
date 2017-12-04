<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>值班班次</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/css/base.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/css/reset.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/css/resassettype.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/plugin/easyui/themes/default/easyui.css">

<script type="text/javascript"	src="${pageContext.request.contextPath}/js/common/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/fui/fui.min.js"></script>
<script type="text/javascript"	src="${pageContext.request.contextPath}/js/common/commonUtil.js"></script>
<script type="text/javascript"	src="${pageContext.request.contextPath}/plugin/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript"	src="${pageContext.request.contextPath}/js/common/easyui-lang-zh_CN.js"></script>
<script type="text/javascript"	src="${pageContext.request.contextPath}/js/common/commonPlugins.js"></script>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/dutymanager/dutyOrder_list.js"></script>

<style type="text/css">
.datagrid-sort-asc .datagrid-sort-icon{background: none; padding: 0}
</style>

</head>
<body>
	<div class="location">当前位置：${navigationBar }</div>
	
	<div class='datas' style="top:28px">
		<table id="tblDutyOrder"></table>
    </div>
</body>
</html>