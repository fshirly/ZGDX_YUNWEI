<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="../../common/pageincluded.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	
	</head>

	<body>
		<div class="lossdata">
			<c:if test="${!empty mo.one}">
			<img alt="0" src="${pageContext.request.contextPath}/style/images/webclient/${mo.one}.gif" />
			</c:if>
			<c:if test="${!empty mo.two}">
			<img alt="0" src="${pageContext.request.contextPath}/style/images/webclient/${mo.two}.gif" />
			</c:if>
			<img alt="0" src="${pageContext.request.contextPath}/style/images/webclient/${mo.three}.gif" />
			<img alt="0" src="${pageContext.request.contextPath}/style/images/webclient/percent.gif" />
		</div>
	</body>
</html>
