<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>查看任务日志</title>
</head>
<body>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/fileUtil.js"></script>
	<table id="taskLogDetail" class="tableinfo">
		<tr>
			<td><b class="title">日志名称：</b> <label>${taskLog.title }</label></td>
			<td><b class="title">是否星标：</b> 
				<label><img src="${pageContext.request.contextPath}/style/images/u85.png">
				<input type="checkbox" name="starLevel" disabled="disabled" ${taskLog.starLevel == 2 ? "checked" : ""} />
				</label>
			</td>
		</tr>
		<tr>
			<td><b class="title">任务日期：</b> 
			<label><fmt:formatDate value="${taskLog.taskTime }" pattern="yyyy-MM-dd"/></label></td>
			<td><b class="title">填写日期：</b> 
			<label><fmt:formatDate value="${taskLog.writeTime }" pattern="yyyy-MM-dd HH:mm"/></label></td>
		</tr>
		<tr>
			<td colspan="2"><b class="title">计划内容：</b> <label>${taskLog.logContent }</label></td>
		</tr>
		<tr>
			<td colspan="2"><b class="title">上传附件：</b> 
			<label><input id="previousFileNameLook1" type="hidden" value="${taskLog.file}" name="attachmentUrl" />
			<a id="downloadFileLook1" title="下载文件"></a></label>
			</td>
		</tr>
	</table>
	<div class="conditionsBtn">
		<a href="javascript:$('#tasklog_popWin').window('close');">关闭</a>
	</div>
	
	<script type="text/javascript">
		initDownLinkTag("downloadFileLook1", $("#previousFileNameLook1").val());
	</script>
</body>
</html>