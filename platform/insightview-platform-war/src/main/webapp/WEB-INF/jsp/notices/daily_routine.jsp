<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>日常事务管理</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/style/css/dutyinfo/reset.css" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/style/css/dutyinfo/main_syf.css" />
<style type="text/css">
.leftmenu ul li .list1 {
	width: 170px;
}
</style>
</head>
<body>
	<div class="mainbody">
		<!--头部部分-->
		<div class="topbody margin20">
			<a href="javascript:void(0);" title="logo" class="logo"></a>
		</div>
		<!--头部部分-->
		<div class="centerbd">
			<!--左侧导航部分-->
			<div class="leftmenu" style="width: 170px;">
				<div class="ls" style="width: 170px;">
					<ul>
						<%-- <li><a
							href="${pageContext.request.contextPath}/duty/config/toDutyInfoList"
							class="list1 yellow" target="duty_info_config"
							onclick="modifyClass(this);">地市值班信息查询</a></li>
						<li><a
							href="${pageContext.request.contextPath}/duty/config/toDutyConfig"
							class="list2" target="duty_info_config"
							onclick="modifyClass(this);">值班信息配置</a></li> --%>
						<li><a onclick="modifyClass(this);"
							href="${pageContext.request.contextPath}/platform/taskLog/toTaskLogList"
							target="notificationd" class="list1 yellow">任务日志</a></li>
						<li><a onclick="modifyClass(this);"
							href="${pageContext.request.contextPath}/platform/taskLogConfig/toConfig"
							target="notificationd" class="list1">任务日志配置</a></li>
						<li><a onclick="modifyClass(this);"
							href="${pageContext.request.contextPath}/platform/taskLog/toTaskLogQueryList"
							target="notificationd" class="list1">任务日志查询</a></li>
						<li><a onclick="modifyClass(this);"
							href="${pageContext.request.contextPath}/platform/notification/toNotifications"
							target="notificationd" class="list1">消息提醒</a></li>
						<li><a onclick="modifyClass(this);"
							href="${pageContext.request.contextPath}/workLog/toWorkLogList"
							target="notificationd" class="list1">工作日志</a></li>
						<li><a onclick="modifyClass(this);"
							href="${pageContext.request.contextPath}/workPlan/toWorkPlanList"
							target="notificationd" class="list1">工作计划</a></li>
					</ul>
				</div>
			</div>
			<div class="rightbody" style="height: 650px;">
				<iframe name="notificationd"
					src="${pageContext.request.contextPath}/platform/taskLog/toTaskLogList"
					frameborder="0" height="auto"></iframe>
			</div>
		</div>
	</div>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/fui/fui.min.js"></script>
	<script type="text/javascript">
		function modifyClass(that) {
			f('a.yellow').removeClass('yellow');
			f(that).addClass('yellow');
		}
	</script>
</body>
</html>
