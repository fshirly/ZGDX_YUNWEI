<%@ page language="java" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>消息提醒-提醒确认</title>
</head>
<body>
	<form id="confirm_form">
		<input type="hidden" name="id" value="${info.Id }" />
		<input type="hidden" name="notifications.creator" value="${info.Creator }" />
		<input type="hidden" name="notifications.title" value="${info.Title }" />
		<input type="hidden" name="userId" value="${info.UserId }" />
		<table class="formtable">
			<tr>
				<td class="title">提醒主题：</td>
				<td colspan="3">${info.Title }</td>
			</tr>
			<tr>
				<td class="title">创建人：</td>
				<td>${info.CreateName }</td>
				<td class="title">创建时间：</td>
				<td>${info.CreateTime }</td>
			</tr>
			<tr>
				<td class="title">提醒附件：</td>
				<td colspan="3"><a href="${info.filePath }">${info.File }</a></td>
			</tr>
			<tr>
				<td class="title">提醒内容：</td>
				<td colspan="3">${info.NoticeContent }</td>
			</tr>
			<tr>
				<td class="title">确认时间：</td>
				<td>${info.ConfirmTime }<input type="hidden" name="confirmTime" value="${info.ConfirmTime }" /></td>
				<td class="title">短信发送：</td>
				<td><input type="checkbox" id="isNote"
					<c:if test="${info.IsNote eq 2 }">checked="checked"</c:if> />是</td>
			</tr>
			<tr>
				<td class="title">确认说明：</td>
				<td colspan="3"><textarea validator="{'length':'0-500'}" name="confirmDes" rows="6" class="x2">${info.ConfirmDes }</textarea></td>
			</tr>
			<tr>
				<td class="title">确认附件：</td>
				<td colspan="3"><input type="file" name="file"
					id="confirm_file" value="${info.personFile }" /></td>
			</tr>
		</table>
	</form>
	<div class="conditionsBtn">
		<a onclick="javascript:noticeperson.confirm();">确定</a> <a
			onclick="javascript:$('#toConfirm').window('close');">关闭</a>
	</div>
	<script type="text/javascript" src="${pageContext.request.contextPath}/fui/plugin/fui-fileupload.min.js"></script>
	<script type="text/javascript">
		/*初始化上传控件项目附件*/
		$('#confirm_file').f_fileupload({
			filePreview : true,
			filesize : 5,
			unit : 'M',
			forbidFormat : '["exe"]'
		});
	</script>
</body>
</html>
