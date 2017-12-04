<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>填写任务日志</title>
</head>
<body>
<table id="taskLogInfo" class="formtable">
		<input type="hidden" id="tasklog_id" name="id" value="${taskLog.id }">
		<tr>
			<td class="title">日志名称：</td>
			<td><input type="text" name="title" value="${taskLog.title }" validator="{'default':'*'}"/><b>*</b></td>
			<td class="title">是否星标：</td>
			<td>
				<img src="${pageContext.request.contextPath}/style/images/u85.png">
				<input type="checkbox" name="starLevel" ${taskLog.starLevel == 2 ? "checked" : ""}/>
			</td>
		</tr>
		<tr>
			<td class="title">任务日期：</td>
			<td><input value="<fmt:formatDate value='${taskLog.taskTime }' pattern='yyyy-MM-dd'/>" disabled="disabled"/></td>
			<td class="title">填写日期：</td>
			<td><input id="write_time" name="writeTime" value="<fmt:formatDate value='${taskLog.writeTime }' pattern='yyyy-MM-dd HH:mm'/>" disabled="disabled"/></td>
		</tr>
		<tr>
			<td class="title">日志内容：</td>
			<td colspan="3">
				<textarea rows="5" class="x2" id="log_content" name="logContent">${taskLog.logContent }</textarea>
			</td>
		</tr>
		<tr>
			<td class="title">上传附件：</td>
			<td colspan="3">
				<input type="file" id="task_file" name="file" value="${taskLog.file }"/>
			</td>
		</tr>
	</table>
	<div class="conditionsBtn">
		<a href="javascript:tasklog.add.confirm();">确定</a>
		<a href="javascript:$('#tasklog_popWin').window('close');">取消</a>
	</div>
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/fui/plugin/fui-fileupload.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/tasklog/tasklog_add.js"></script>
</body>
</html>