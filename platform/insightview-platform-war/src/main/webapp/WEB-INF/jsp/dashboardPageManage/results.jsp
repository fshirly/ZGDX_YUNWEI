<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
</head>
<body>
	<c:forEach items="${rows}" var="row">
		<ol>
			<li class="titleLi">${row.title }</li>
			<li>${row.content }</li>
		</ol>
		</br>
	</c:forEach>
</body>
</html>