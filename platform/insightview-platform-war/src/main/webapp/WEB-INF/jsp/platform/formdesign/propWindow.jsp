<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
	<form id="propWindow_Form">
		<jsp:include page="/platform/form/prop">
			<jsp:param value="${attrId }" name="attrId"/>
			<jsp:param value="${propView }" name="propView"/>
			<jsp:param value="${formId }" name="formId"/>
		</jsp:include>
	</form>
	<div class="conditionsBtn">	
		<a href="javascript:void(0);" id="propWindow_Submit" >确定</a>
		<a href="javascript:void(0);" id="propWindow_Cancel">取消</a>
    </div>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/platform/formdesign/PropWindow.js"></script>
</body>
</html>