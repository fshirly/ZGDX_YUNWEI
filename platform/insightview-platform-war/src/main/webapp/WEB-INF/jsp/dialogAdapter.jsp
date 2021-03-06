<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix='security'
	uri='http://www.springframework.org/security/tags'%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
	<meta http-equiv="Cache-Control" content="no-store" />
	<meta http-equiv="Pragma" content="no-cache" />
	<meta http-equiv="Expires" content="0" />
	<meta http-equiv="Content-Language" content="zh-cn" />
	<title>${ sessionScope.ChineseName}</title> <!-- mainframe -->
	<script type="text/javascript" src="${pageContext.request.contextPath}/fui/fui.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/commonUtil.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/base64.js"></script>
	<!-- easyui -->
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/plugin/easyui/themes/default/easyui.css"/>
	<script type="text/javascript" src="${pageContext.request.contextPath}/plugin/easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/jquery.easyui.extend.js"></script>
	<!-- my ui -->
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/css/reset.css" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/css/layout.css" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/plugin/easyui/themes/icon.css"/>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/main.js"></script>
	<!-- dTree -->
	<script type="text/javascript" src="${pageContext.request.contextPath}/plugin/dTree/dtree.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/plugin/dTree/combo/comboJS.js"></script>
	<script type="text/javascript"   src="${pageContext.request.contextPath}/js/common/easyui-lang-zh_CN.js"></script>
	
	 <script type="text/javascript" src="${pageContext.request.contextPath}/plugin/autocomplete/jquery.autocomplete.js"></script> 
	<script type="text/javascript" src="${pageContext.request.contextPath}/fui/plugin/fui-tree.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/fui/plugin/fui-fileupload.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/isv/comp/ddgrid.js"></script>   
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/easyui-lang-zh_CN.js"></script>
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/commonPlugins.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/plugin/select2/select2.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/printarea/jquery.PrintArea.js"></script>
	<script type="text/javascript">
		$(function(){
			$("<div>").dialog({
				noheader: true,
				fit: true,
				href: '${url}'
			});
		});
	</script>
</head>
<body>

</body>

</html>