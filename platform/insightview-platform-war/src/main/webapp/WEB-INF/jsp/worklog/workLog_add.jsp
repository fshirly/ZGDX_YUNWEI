<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Pragma" contect="no-cache">
<title>新增日志计划</title>
</head>
<body>
	<table id="workLogInfo" class="formtable">
		<input type="hidden" id="workLog_id" name="id" value="${workLog.id }">
		<tr>
			<td class="title">日志名称：</td>
			<td><input type="text" name="title" value="${workLog.title }" validator="{'default':'*'}"/><b>*</b></td>
			<td class="title">是否星标：</td>
			<td>
				<img src="${pageContext.request.contextPath}/style/images/u85.png">
				<input type="checkbox" name="starLevel" ${workLog.starLevel == 2 ? "checked" : ""}/>
			</td>
		</tr>
		<tr>
			<td class="title">任务日期：</td>
			<td colspan="3">
				<input id="log_start" name="startTime" value="<fmt:formatDate value='${workLog.startTime }' pattern='yyyy-MM-dd HH:mm'/>" validator="{'default':'*','type':'datebox'}"/> - 
				<input id="log_end" name="endTime" value="<fmt:formatDate value='${workLog.endTime }' pattern='yyyy-MM-dd HH:mm'/>" validator="{'default':'*','type':'datebox'}"/><b>*</b>
			</td>
		</tr>
		<tr>
			<td class="title">日志内容：</td>
			<td colspan="3">
				<textarea rows="5" class="x2" id="log_content" name="logContent">${workLog.logContent }</textarea>
			</td>
		</tr>
		<tr>
			<td class="title">创建人：</td>
			<td><input type="text" id="log_creater" value="${workLog.createrName }" disabled="disabled"/></td>
			<td class="title">创建时间：</td>
			<td><input type="text" id="log_createTime" name="createTime" value="${workLog.createTime }" disabled="disabled"/></td>
		</tr>
		<tr>
			<td class="title">上传任务附件：</td>
			<td colspan="3">
				<input type="file" id="log_taskFile" name="taskFile" value="${workLog.taskFile }"/>
			</td>
		</tr>
		<tr>
			<td class="title">上传成果附件：</td>
			<td colspan="3">
				<input type="file" id="log_resultFile" name="resultFile" value="${workLog.resultFile }"/>
			</td>
		</tr>
	</table>
	<div class="conditionsBtn">
		<a href="javascript:worklog.add.confirm();">确定</a>
		<a href="javascript:$('#workLog_popWin').window('close');">取消</a>
	</div>
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/fui/plugin/fui-fileupload.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/worklog/workLog_add.js"></script>
</body>
</html>