<%@ page language="java" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>消息提醒-提醒详情</title>
</head>
<body>
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
			<td>${info.ConfirmTime }</td>
			<td class="title">短信发送：</td>
			<td><input type="checkbox" disabled="disabled"
				<c:if test="${info.IsNote eq 2 }">checked="checked"</c:if>   />是</td>
		</tr>
		<tr>
			<td class="title">确认说明：</td>
			<td colspan="3">${info.ConfirmDes }</td>
		</tr>
		<tr>
			<td class="title">确认附件：</td>
			<td colspan="3"><a href="${info.personFilePath }">${info.personFileName }</a></td>
		</tr>
	</table>
	<div class="conditionsBtn">
		<a onclick="javascript:$('#toConfirm').window('close');">关闭</a>
	</div>
</body>
</html>
