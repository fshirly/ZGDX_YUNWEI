<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/dashboardPageManage/announcementDetailEdit.js"></script>
	<div>
		<input id="ipt_id" type="hidden" value="${announcement.id }"/>
		<table id="tblAnnouncementInfo" class="formtable1">
			<tr>
				<td class="title">标题：</td>
				<td><input id="ipt_title" value="${announcement.title }"
					validator="{'default':'*','length':'1-120'}"/></td>
			</tr>
			<tr>
				<td class="title">类型：</td>
				<td><input id="ipt_typeId" value="${announcement.typeId }"</input></td>
			</tr>
			<tr>
				<td class="title">创建者：</td>
				<td><input id="ipt_creator" value="${createrName }" disabled="disabled"></input> 
				<input type="hidden" id="creater" name="creater" value="${announcement.creater }"/></td>
			</tr>
			<tr>
				<td class="title">创建时间：</td>
				<td><input id="ipt_createTime" value="${announcement.createTime }"></input></td>
			</tr>
			<tr>
				<td class="title">期限：</td>
				<td><input id="ipt_deadLine" value="${announcement.deadLine }"
					validator="{'default':'*','type':'datetimebox'}"></input></td>
			</tr>
			<tr>
				<td class="title">说明：</td>
				<td><textarea rows="3" id="ipt_summary" validator="{'default':'*','length':'0-500'}">${announcement.summary }</textarea></td>
			</tr>
		</table>
		<p class="conditionsBtn">
			<a href="javascript:void(0);" onclick="doAnnouncementUpdate();">确认</a>
			<a href="javascript:void(0);" onclick="$('#popWin').window('close');">关闭</a>
		</p>
	</div>
</body>
</html>