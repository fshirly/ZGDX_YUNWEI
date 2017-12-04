<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>工作计划详情</title>
</head>
<body>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/fileUtil.js"></script>
	<table id="workLogDetail" class="tableinfo">
		<tr>
			<td><b class="title">日志名称：</b> <label>${workLog.title }</label></td>
			<td><b class="title">是否星标：</b> 
				<label><img src="${pageContext.request.contextPath}/style/images/u85.png">
				<input type="checkbox" name="starLevel" disabled="disabled" ${workLog.starLevel == 2 ? "checked" : ""} />
				</label>
			</td>
		</tr>
		<tr>
			<td colspan="2"><b class="title">任务日期：</b> 
			<label><fmt:formatDate value='${workLog.startTime }' pattern='yyyy-MM-dd HH:mm'/> - 
			<fmt:formatDate value='${workLog.endTime }' pattern='yyyy-MM-dd HH:mm'/></label></td>
		</tr>
		<tr>
			<td colspan="2"><b class="title">计划内容：</b> <label>${workLog.logContent }</label></td>
		</tr>
		<tr>
			<td><b class="title">创建人：</b> <label>${workLog.createrName }</label></td>
			<td><b class="title">创建时间：</b> <label>${workLog.createTime }</label></td>
		</tr>
		<tr>
			<td colspan="2"><b class="title">任务附件：</b> 
			<label><input id="previousFileNameLook1" type="hidden" value="${workLog.taskFile}" name="attachmentUrl" />
			<a id="downloadFileLook1" title="下载文件"></a></label>
			</td>
		</tr>
		<tr>
			<td colspan="2"><b class="title">成果附件：</b> 
			<label><input id="previousFileNameLook2" type="hidden" value="${workLog.resultFile}" name="attachmentUrl" />
			<a id="downloadFileLook2" title="下载文件"></a></label>
			</td>
		</tr>
	</table>
	<div class="conditionsBtn">
		<a href="javascript:doDel(${workLog.id });">删除</a>
		<a href="javascript:toEdit(${workLog.id });">编辑</a>
	</div>
	
	<script type="text/javascript">
		initDownLinkTag("downloadFileLook1", $("#previousFileNameLook1").val());
		initDownLinkTag("downloadFileLook2", $("#previousFileNameLook2").val());
		
		var path = getRootName();
		function doDel(id) {
			$.messager.confirm('提示', '是否确定删除该值班日志？', function(r) {
				if(r) {
					$.ajax({
						url: path+'/workLog/deleteWorkLog?id='+id,
						success: function(data) {
							if(data == 'success') {
								$('#log_calendar').window('close');
								//重新加载events事件
								$('#workLog_calendar').fullCalendar('refetchEvents');
								//刷新主页面的工作计划列表
								window.opener.worklog.list.reload();
							}
						}
					});
				}
			});
		}
		function toEdit(id) {
			$('#log_calendar').window('close');
			worklog.calendar.toEdit(id);
		}
	</script>
</body>
</html>