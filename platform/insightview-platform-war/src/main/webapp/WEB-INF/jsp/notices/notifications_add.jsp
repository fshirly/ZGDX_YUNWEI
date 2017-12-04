<%@ page language="java" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>消息提醒-提醒创建</title>
</head>
<body>
	<form id="notifications_form">
		<c:if test="${info.Id ne null }">
			<input type="hidden" value="${info.Id }" name="id" id="notifiId" />
		</c:if>
		<table class="formtable">
			<tr>
				<td class="title">提醒主题：</td>
				<td colspan="3"><input
					validator="{'default':'*','length':'1-50'}" type="text"
					name="title" class="x2" value="${info.Title }" /> <dfn>*</dfn></td>
			</tr>
			<tr>
				<td class="title">提醒内容：</td>
				<td colspan="3"><textarea
						validator="{'default':'*','length':'1-500'}" name="noticeContent"
						rows="6" class="x2">${info.NoticeContent }</textarea> <dfn>*</dfn></td>
			</tr>
			<tr>
				<td class="title">接收人：</td>
				<td colspan="3"><input type="hidden" id="userIds"
					value="${info.UserIds }" /><input readonly="readonly"
					validator="{'default':'*'}" type="text"
					<c:if test='${info.UserTit eq null }'>
						value="${info.UserNames }"
					</c:if>
					<c:if test='${info.UserTit ne null }'>
						value="${info.UserTit }" title="${info.UserNames }"
					</c:if>
					id="userNames" class="x2" 
					onclick="javascript:notification.toUser();" /> <dfn>*</dfn></td>
			</tr>
			<tr>
				<td class="title">创建时间：</td>
				<td>${CreateTime }<input type="hidden" name="createTime"
					value="${CreateTime }" /></td>
				<td class="title">短信发送：</td>
				<td><input type="checkbox" id="isNote"
					<c:if test="${info.IsNote eq 2 }">checked="checked"</c:if> />是</td>
			</tr>
			<tr>
				<td class="title">上传附件：</td>
				<td colspan="3"><input type="file" name="file"
					id="notification_file" value="${info.FileName }" /></td>
			</tr>
		</table>
	</form>
	<div class="conditionsBtn">
		<a onclick="javascript:notification.add();">确定</a> <a
			onclick="javascript:$('#notifications').window('close');">关闭</a>
	</div>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/fui/plugin/fui-fileupload.min.js"></script>
	<script type="text/javascript">
		/*初始化上传控件项目附件*/
		$('#notification_file').f_fileupload({
			filePreview : true,
			filesize : 5,
			unit : 'M',
			forbidFormat : '["exe"]'
		});
	</script>
</body>
</html>
