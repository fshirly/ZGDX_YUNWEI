<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>任务日志</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/style/css/base.css" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/style/css/reset.css" />
	
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/common/jquery-1.9.1.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/fui/fui.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/plugin/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/common/easyui-lang-zh_CN.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/common/commonUtil.js"></script>
</head>
<body>
	<div class="conditions">
		<table>
			<tr>
				<td><b>日志主题：</b> <input id="task_title" type="text"/></td>
				<td><b>任务日期：</b> 
					<input id="task_start1" class="easyui-datebox" data-options="editable:false,panelWidth:'100%'"/> -
					<input id="task_start2" class="easyui-datebox" data-options="editable:false,panelWidth:'100%'"/>
				</td>
				<td><b>状态：</b>
					<select id="task_status" class="easyui-combobox" data-options="panelHeight:70,editable:false">
						<option value="-1">全部</option>
						<option value="1">未填写</option>
						<option value="2">已填写</option>
					</select>
				</td>
				<td class="btntd">
					<a href="javascript:tasklog.list.search();">查询</a>
					<a href="javascript:tasklog.list.reset();">重置</a>
				</td>
			</tr>
		</table>
	</div>
	
	<div class="datas" style="top: 48px;">
		<table id="tblTaskLogList"></table>
	</div>
	
	<div id="tasklog_popWin"></div>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/tasklog/tasklog_list.js"></script>
</body>
</html>