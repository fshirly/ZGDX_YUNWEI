<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>搜索结果</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/fui/fui.min.js"></script>
</head>
<body>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/css/reset.css" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/css/layout.css" />
	 <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/fui/themes/default/fui-tree.min.css" />
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/fui/plugin/fui-tree.min.js"></script>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/plugin/easyui/themes/default/easyui.css"/>
	<script type="text/javascript" src="${pageContext.request.contextPath}/plugin/easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/common/commonUtil.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/common/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/dashboardPageManage/retrievalResults.js"></script>
	<div style="width:100%;height:98%;">
		<div id="getHeightDiv" style="width:190px;margin:auto;height:99.5%;float:left;border: 1px double rgb(178, 175, 175);">
			<div style="margin: 15px auto;">
				<input style="height:25px;" type="text" id="title" value="${keywords}" />
			</div>
			<div id="retrievalTreeDiv" class="treetable" style="top: 82px;width: 100%;float: left;"></div>
		</div>
		<div style="width:75%;float:left;margin:0 2% 0 2%;">
		  <div class="treetabler" style="margin-top: 16px;overflow-x:hidden;">
			<table id="retrievalList" />
		  </div>
		</div>
	</div>
	<script>
	(function(){
		var keywords = $("#title").val();
		var str = "";
		if(keywords.indexOf("%23") !=-1 ||  keywords.indexOf("%2B") !=-1 || keywords.indexOf("%26") !=-1 || keywords.indexOf("%25") !=-1 ){
	    	str = keywords.replace(/%23/g,"#");
	    	str = str.replace(/%26/g,"&");
	    	str = str.replace(/%2B/g,"+");
	    	str = str.replace(/%25/g,"%");
	    }else{
	    	str = keywords;
	    }
		$("#title").val(str);
	})();
	</script>
</body>
</html>