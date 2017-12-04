<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>通知公告</title>
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
	src="${pageContext.request.contextPath}/js/common/base64.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/dashboardPageManage/announcement.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/common/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<!-- 显示浏览列表 -->
	<div class="datas topdashboard" style="height:262px;">
		<table id="announceList">
		</table>
	</div>
	<div class="butdiv">
		<a href="javascript:void(0);" id="btnSearch" class="btn" onclick="parent.parent.toShowTabsInfo('/announcement/queryItsmAnnouList','通知公告','','ifrAnnouList');">查看更多</a>
	</div>
</body>
</html>