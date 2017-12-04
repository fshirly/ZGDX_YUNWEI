<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>消息提醒-我创建的提醒</title>
<%-- <link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/style/css/base.css" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/style/css/reset.css" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/plugin/easyui/themes/default/easyui.css" /> --%>
</head>
<body>
	<div class="conditions">
		<table>
			<tr>
				<td>
					<b>提醒主题：</b> <input id="title" type="text" />
				</td>
				<td>
					<b>创建时间：</b> 
					<input id="createBegin" class="easyui-datetimebox" data-options="editable:false,panelWidth:'100%'" />
					 - 
					<input id="createEnd" class="easyui-datetimebox" data-options="editable:false,panelWidth:'100%'" />
				</td>
				<td>
					<b>接收人：</b> 
					<select id="recipient">
						<option value="-1">全部</option>
					</select>
				</td>
				<td class="btntd">
					<a href="javascript:notification.search();">查询</a> 
					<a href="javascript:notification.reset();">重置</a></td>
			</tr>
		</table>
	</div>

	<div class="datas">
		<table id="notifications_datagrid"></table>
	</div>

<%-- 	<script type="text/javascript"
		src="${pageContext.request.contextPath}/fui/fui.min.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/plugin/easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/common/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/common/commonUtil.js"></script> --%>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/notices/notifications_list.js"></script>
</body>
</html>
