<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>工作计划日历视图</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/css/base.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/css/reset.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/fullcalendar/fullcalendar.css"/>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/fui/fui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/commonUtil.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/plugin/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/jquery.easyui.extend.js"></script>
<script type="text/javascript" src='${pageContext.request.contextPath}/js/fullcalendar/moment.min.js'></script>
<script type="text/javascript" src='${pageContext.request.contextPath}/js/fullcalendar/fullcalendar.min.js'></script>
<script	type="text/javascript" src='${pageContext.request.contextPath}/js/fullcalendar/zh-cn.js'></script>
<script	type="text/javascript" src='${pageContext.request.contextPath}/js/workplan/workPlan_calender.js'></script>

<style type="text/css">
	#calendar_header {
		height: 40px;
		background-color: #c2eaf2;
	}
	.event_style {
		height: 15px;
	}
	.fc-event {
		background-color: pink;
		border-color: pink;
	}
</style>
</head>
<body>
	<div class="rightContent">
		<!-- <div id="calendar_header" style="text-align: center;">
			<a href="javascript:workplan.calendar.toAdd();">新增</a>
			<a href="javascript:workplan.calendar.toList();">列表</a>
		</div> -->
		<div id="workPlan_calendar" style="width: 80%; margin: 0 auto"></div>
		<div id="plan_calendar"></div>
	</div>
	<span id="wspace" class="fc-header-space" style="display:none"></span>
	<span id="workPlanAddNew" class="fc-button fc-button-today fc-state-default fc-corner-left fc-corner-right" style="display:none" unselectable="on">新增</span>
</body>
</html>