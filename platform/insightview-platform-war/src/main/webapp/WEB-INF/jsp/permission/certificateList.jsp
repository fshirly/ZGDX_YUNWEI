<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
</head>
    <body>
	    <link rel="stylesheet" type="text/css"
	       href="${pageContext.request.contextPath}/style/css/base.css" />
	    <link rel="stylesheet" type="text/css"
	       href="${pageContext.request.contextPath}/style/css/reset.css" />
        <link rel="stylesheet" type="text/css" 
           href="${pageContext.request.contextPath}/plugin/easyui/themes/default/easyui.css"/>
        <script type="text/javascript" 
           src="${pageContext.request.contextPath}/js/common/fileUtil.js"></script>   
        <script type="text/javascript"
           src="${pageContext.request.contextPath}/js/permission/certificateList.js"></script>
        
	    <table id="tblCertificateList"/>
	    
		<div class="conditionsBtn">
		  <a onclick="javascript:parent.$('#popWin').window('close');" >关闭</a>
        </div>   
    </body>
</html>