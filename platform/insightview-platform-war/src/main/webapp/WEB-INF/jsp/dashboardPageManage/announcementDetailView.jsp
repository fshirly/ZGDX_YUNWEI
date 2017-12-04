<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div>
		<input type="hidden" value="${announcement.id }"/>
		<table class="tableinfo">
			<tr>
				<td colspan="2"><b class="title">标题：</b> <label class="alignment">${announcement.title }</label></td>
			</tr>
			<tr>
				<td><b class="title">类型：</b> <label>${announcementType }</label></td>
				<td><b class="title">创建者：</b> <label>${createrName }</label></td>
			</tr>
			<tr>
				<td><b class="title">创建时间：</b> <label><fmt:formatDate value="${announcement.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/></label></td>
				<td><b class="title">期限：</b> <label><fmt:formatDate value="${announcement.deadLine }" pattern="yyyy-MM-dd HH:mm:ss"/></label></td>
			</tr>
			<tr>
				<td colspan="2"><b  class="title">说明：</b> <label class="alignment">${announcement.summary }</label></td>
			</tr>
		</table>
		<p class="conditionsBtn">
			<a href="javascript:void(0);" onclick="$('#popWin').window('close');">关闭</a>
		</p>
	</div>
</body>
</html>