<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>值班日历表</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/css/base.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/css/reset.css" />
<link href='${pageContext.request.contextPath}/js/fullcalendar/fullcalendar.css' rel='stylesheet' />
<style type="text/css">
.fc-header{margin-top:14px;}
.fc-header-title h2 {font-size:14px;}
.edit_duty_log{cursor: pointer;position: absolute;right: 0;top: 22px;width: 20px;height: 20px}
.line_sp{height: 3px; background: #fff}
.fc-event-title {
	display: none;
	padding: 0 1px;
}
</style>
</head>
<body>
	<div class="rightContent">
		<div class="location">当前位置：${navigationBar }<span class="clc_bck"></div>
		<div id='duty_calendar' style="width: 70%; margin: 0 auto"></div>
	</div>	
<script type="text/javascript" src="${pageContext.request.contextPath}/fui/fui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/commonUtil.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/plugin/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/jquery.easyui.extend.js"></script>
<script type="text/javascript" src='${pageContext.request.contextPath}/js/fullcalendar/moment.min.js'></script>
<script type="text/javascript" src='${pageContext.request.contextPath}/js/fullcalendar/fullcalendar.min.js'></script>
<script	type="text/javascript" src='${pageContext.request.contextPath}/js/fullcalendar/zh-cn.js'></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/dutymanager/duty_log.js"></script>
</body>
</html>