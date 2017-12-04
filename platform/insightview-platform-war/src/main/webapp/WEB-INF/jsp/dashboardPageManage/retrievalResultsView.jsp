<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div>
		<input type="hidden" value="${knowledge.id }"/>
		<table class="tableinfo">
			<tr>
				<td colspan="2"><b class="title">标题：</b> <label>${knowledge.title }</label></td>
			</tr>
			<tr>
				<td colspan="2"><b  class="title">内容：</b> <label>${knowledge.content }</label></td>
			</tr>
		</table>
		<p class="conditionsBtn">
			<a href="javascript:void(0);" onclick="$('#popWin').window('close');">关闭</a>
		</p>
	</div>
</body>
</html>