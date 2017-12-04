<%@ page language="java" pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>任务日志-任务日志配置</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/css/base.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/css/reset.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/fui/themes/default/fui-tree.min.css" />
<!--右侧列表部分-->
<script type="text/javascript" src="${pageContext.request.contextPath}/fui/fui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/plugin/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/plugin/dTree/dtree.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/commonUtil.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/fui/plugin/fui-tree.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/selectPerson.js"></script>
</head>
<body>
	<table style="width:100%">
		<tr>
			<td><a>任务启动设置</a></td>
			<td>|</td>
			<td><a>任务接收人设置</a></td>
			<td>|</td>
			<td><a>手动下发</a></td>
		</tr>
		<tr>
			<td colspan="3">
				<div>
					<ul>
						<li>是否启动任务： <input type="radio" name="start" value="true"
							onclick="tasklogconfig.show(this);"
							<c:if test="${config.TaskLogEnable eq 'true' }">checked="checked"</c:if> />是
							<input type="radio" name="start" onclick="tasklogconfig.show(this);"
							value="false"
							<c:if test="${config.TaskLogEnable eq 'false' }">checked="checked"</c:if> />否
						</li>
						<li id="startTime"
							<c:if test="${config.TaskLogEnable eq 'false' }">style="display: none"</c:if>>启动时间：
							<input value="${config.TaskLogTime }" id="TaskLogTime"
							class="easyui-timespinner" data-options="showSeconds:false"
							validator="{'default':'*','type':'timespinner'}"> <dfn>*</dfn>
						</li>
						<li class="btntd"><a onclick="tasklogconfig.update();">保&nbsp;&nbsp;存</a></li>
					</ul>
				</div>
				<div style="height:500px;">
					<a id="addUser" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true">添加接收人</a>
					<input type="hidden" id="userIds" value="${userIds }"/>
					<div id="orgTree"></div>
					<div style="width: 100%; height:300px;">
						<table id="taskLogUsers"></table>
					</div>
				</div>
				<div>
					<ul>
						<li class="btntd">下发时间： <input data-options="editable:false"
							class="easyui-datebox" id="manualTime" /><a onclick="tasklogconfig.manual();">下&nbsp;&nbsp;发</a>
						</li>
					</ul>
				</div>
			</td>
		</tr>
	</table>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/tasklog/tasklog_config.js"></script>
</body>
</html>
