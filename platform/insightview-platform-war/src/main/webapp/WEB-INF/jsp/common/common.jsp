<%@ page pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/inc/jsp-common.jsp"%>
<%@ include file="/WEB-INF/jsp/inc/jstl-common.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
                      "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
                      
<html xmlns="http://www.w3.org/1999/xhtml">
	<head profile="http://www.w3.org/2005/10/profile">
		<title></title>
		<base target="_self" />
		<%@ include file="/WEB-INF/jsp/inc/htmlhead-common.jsp"%>
		
		<!-- 蓝色<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/jmesa.css"></link>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/override-jmesa.css"></link>-->
		
		<link rel="stylesheet" type="text/css" href="<%=_jmesacss%>" ></link>
		<link rel="stylesheet" type="text/css" href="<%=_css%>" ></link>
		
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.3.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.bgiframe.pack.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.jmesa.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jmesa.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/form-util.js"></script>
		<script type="text/javascript">
	          function onInvokeAction(id) {
	           
	              $.jmesa.setExportToLimit(id, '');
	              $.jmesa.createHiddenInputFieldsForLimitAndSubmit(id);
	          }
	          function onInvokeExportAction(id) {
	              var parameterString = $.jmesa.createParameterStringForLimit(id);
	              location.href = '${submitFormUrl}&' + parameterString;
	          }
	          function doImport() {
      			 location.href = "${pageContext.request.contextPath}/emv/common/dataimportForm.jsp";
    }
      	</script>
	</head>

	<body>
		<form onsubmit="return checkSubmit(this)" name="form" id="form"
			action="${submitFormUrl}">
			${TABLE_DATA}
			<br />
			<input type="button" class="button_2" value="导入" onclick="doImport()" />
		</form>
    <%@ include file="/WEB-INF/jsp/debug/debug.jsp" %>
	</body>
</html>
