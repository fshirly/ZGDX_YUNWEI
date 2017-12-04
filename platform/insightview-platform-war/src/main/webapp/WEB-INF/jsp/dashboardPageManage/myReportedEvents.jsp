<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<title>事件管理</title>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/css/base.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/css/reset.css" />

<!-- mainframe -->
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/common/jquery-1.9.1.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/plugin/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/common/commonUtil.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/dashboardPageManage/myReportedEvents.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/common/easyui-lang-zh_CN.js"></script>

</head>
<body>

<div class="myReportedTabT">
	<div style="width:200px;">事件标题：<input type="text" class="inputs" id="title" /></div>
	<div style="width:200px;">事件状态：<input class="easyui-combobox" id="status" name="status" /></div>
	<div style="width:200px;">报告日期：<input class="easyui-combobox" id="limitTime" name="limitTime"/></div>
	<div class="butTd">
		<a href="javascript:resetFormFilter();" class="btn">重置</a>
		<a href="javascript:reloadManageTable();" class="btn">查询</a>
	</div>
</div>
	<div class="datas topdashboard">
	<table id="tblEventManagement"></table>
	</div>
</body>
</html>