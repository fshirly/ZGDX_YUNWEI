<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<!-- 统一引用样式 -->
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/css/base.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/css/reset.css" />
<!-- mainframe -->
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/common/jquery-1.9.1.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/common/commonUtil.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/plugin/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/common/base64.js"></script>
<%-- <script type="text/javascript"
	src="${pageContext.request.contextPath}/js/eventManage/eventQueryList.js"></script> --%>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/dashboardPageManage/toBeAllocatedEvent.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/common/easyui-lang-zh_CN.js"></script>
<title>待分配事件</title>
</head>
<style>
.panel-body {
	height: 180px !important;
}
</style>
<body>
	<input type="hidden" name="pageID" id="pageID" value="allocateEventList">
	<div class="datas topdashboard">
		<table id="tblAllocateEventList">

		</table>
	</div>
</body>
</html>
