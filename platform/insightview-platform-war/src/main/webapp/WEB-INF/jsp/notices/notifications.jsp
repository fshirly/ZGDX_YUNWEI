<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>消息提醒</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/style/css/base.css" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/style/css/reset.css" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/plugin/easyui/themes/default/easyui.css" />
</head>
<body>
	<div id="notification_tabs">
		<div title="我创建的提醒"></div>
		<div title="我接受的提醒"></div>
	</div>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/fui/fui.min.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/plugin/easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/common/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/common/commonUtil.js"></script>
	<script type="text/javascript">
		$('#notification_tabs').tabs(
				{
					fit : true,
					plain : true,
					closable : false,
					onSelect : function(title, index) {
						if (index === 0) {
							$(this).tabs('getSelected').panel('refresh', getRootName() + '/platform/notification/toList');
						} else if (index === 1) {
							$(this).tabs("getSelected").panel('refresh', getRootName() + '/platform/noticePerson/toList');
						}
					}
				});
	</script>
</body>
</html>
