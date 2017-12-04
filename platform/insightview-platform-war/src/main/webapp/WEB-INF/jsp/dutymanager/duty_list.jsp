<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>值班管理</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/css/base.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/css/reset.css" />
<style type="text/css">
.clc_bck{width: 24px;height: 24px;position: absolute;right: 0;margin: 6px 16px; cursor: pointer;}
</style>
<script type="text/javascript" src="${pageContext.request.contextPath}/fui/fui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/plugin/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/commonUtil.js"></script>
</head>
<body>
	<div class="rightContent">
		<div class="location">当前位置：${navigationBar }<span class="clc_bck"><img alt="日历" src="${pageContext.request.contextPath}/style/images/duty_search.png"></span></div>
		<div style="width: 100%;height: 100%"><div id="duty_search_panel"></div></div>
	</div>	
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/dutymanager/duty_list.js"></script>
</body>
</html>