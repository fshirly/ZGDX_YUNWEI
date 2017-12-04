<%@ page pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ include file = "./pageincluded.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
                      "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%
	String importType = request.getParameter("importType");
	String templateName = "UserImportTemplate.xls";
	if("2".equals(importType)){
		templateName = "DeptImportTemplate.xls";
	}
%>
<html>
<head>
	
</head>
<body>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/platform/importdata/dataimportForm.js"></script> 
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/common/fileUtil.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/common/ajaxfileupload.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/common/commonPlugins.js"></script>
		<title></title>
		

<div id="divImportDept">
【<a href="javascript:void(0);" onclick="downloadTemplate();">点击此处下载相应的模板</a>】<br />
<input type="hidden" id="importType" value="<%=importType %>" />
<input type="hidden" id="templateName" value="<%=templateName %>" />
<input name="ipt_importFile" id="ipt_importFile" type="file" onchange='initAndCheckImport(this)' />
			<button id="btnUpload" onclick="doImportFile();">导入</button>
<span id="tipTr" style="display:none">	
<font color="red">数据导入正在进行中，请勿离开本页面.....</font>
</span>
</div>
</body>
</html>
