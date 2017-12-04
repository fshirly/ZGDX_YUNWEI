<%@ page language="java" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>消息提醒-提醒信息详情</title>
</head>
<body>
	<input type="hidden" value="${info.Id }" id="notice_Id" />
	<table class="formtable">
		<tr>
			<td class="title">提醒主题：</td>
			<td colspan="3">${info.Title }</td>
		</tr>
		<tr>
			<td class="title">创建人：</td>
			<td>${info.UserName }</td>
			<td class="title">创建时间：</td>
			<td>${CreateTime }</td>
		</tr>
		<tr>
			<td class="title">提醒附件：</td>
			<td><a href="${info.filePath }">${info.File }</a></td>
			<td class="title">短信发送：</td>
			<td><input type="checkbox" disabled="disabled"
				<c:if test="${info.IsNote eq 2 }">checked="checked"</c:if> /></td>
		</tr>
		<tr>
			<td class="title">提醒内容：</td>
			<td colspan="3">${info.NoticeContent }</td>
		</tr>
	</table>

	<div class="datas" style="height: 242px;">
		<table id="notifications_persons"></table>
	</div>
	<div class="conditionsBtn">
		<a onclick="javascript:$('#notifications').window('close');">关闭</a>
	</div>
	<script type="text/javascript">
		$('#notifications_persons').datagrid({
			iconCls : 'icon-edit',// 图标
			fit : true,// 自动大小
			fitColumns : true,
			url : getRootName() + '/platform/noticePerson/queryPersons?noticeId=' + $('#notice_Id').val(),
			idField : 'id',
			singleSelect : true,// 是否单选
			pagination : true,// 分页控件
			rownumbers : true,// 行号
			striped : true,
			columns : [ [ {
				title : '接收人',
				field : 'userName',
				align : 'center',
				width : 100,
			}, {
				title : '确认时间',
				field : 'formateConfigeTime',
				align : 'center',
				width : 140
			}, {
				title : '短信发送',
				field : 'isNote',
				align : 'center',
				width : 60,
				formatter : function(value, row, index) {
					if (value === 1) {
						return '否';
					} else {
						return '是';
					}
				}
			}, {
				title : '确认内容',
				field : 'confirmDes',
				align : 'center',
				width : 180
			}, {
				title : '确认附件',
				field : 'file',
				align : 'center',
				width : 140,
				formatter : function(value, row, index) {
					if (value) {
						return '<a href="' + row.filePath + '">' + value + '</a>';
					}
				}
			} ] ]
		});
	</script>
</body>
</html>
