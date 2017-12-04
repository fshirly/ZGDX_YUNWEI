<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/css/base.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/css/reset.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/plugin/select2/select2.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/fui/themes/default/fui-tree.min.css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/fui/plugin/fui-tree.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/ajaxfileupload.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/fileUtil.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/util/setRelatedConfigItem.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/commonPlugins.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/plugin/select2/select2.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/commonUtil.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/base64.js"></script>

<table id="tblDutyOrderInfo" class="tableinfo" style="width: 480px;">
			<tr>
				<td><b class="title">班次名称：</b><label>${dutyOrder.title }</label></td>
			</tr>
			<tr>
				<td><b class="title">开始时间：</b><label>${dutyOrder.beginPoint }</label></td>
			</tr>
			<tr>
				<td><b class="title">结束时间：</b><label>${dutyOrder.endPoint }</label></td>
			</tr>
			<tr>
				<td><b class="title">交班开始时间：</b><label>${dutyOrder.exchangeStart }</label></td>
			</tr>
			<tr>
				<td><b class="title">交班结束时间：</b><label>${dutyOrder.exchangeEnd }</label></td>
			</tr>
			<tr>
				<td><b class="title">强制交班时间：</b><label>${dutyOrder.forceTime }</label></td>
			</tr>
		</table>
		<div class="conditionsBtn">
			<a href="javascript:void(0);" onclick="javascript:$('#popWin').window('close');">关闭</a>
		</div>
</body>
</html>