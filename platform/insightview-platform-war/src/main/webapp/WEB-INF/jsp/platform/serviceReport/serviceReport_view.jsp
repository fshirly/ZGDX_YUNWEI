<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

</head>
<body>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/style/css/base.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/css/reset.css" />
<link href="${pageContext.request.contextPath}/plugin/select2/select2.css" rel="stylesheet"/>
	
	
<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/fileUtil.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/common/commonPlugins.js"></script>
<script type="text/javascript" 
	src="${pageContext.request.contextPath}/plugin/select2/select2.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/plugin/autocomplete/jquery.autocomplete.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/common/easyui-lang-zh_CN.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/platform/serviceReport/serviceReport_view.js"></script>
<div>
	<div id="divServiceReportInfo">
		
		<table class="tableinfo">
			<tr>
				<td colspan="2">
					<div style="float:left;"><b class="title">服务报告名称：</b></div>
					<div class="eventRequestDesc"><label>${serviceReport.name }</label></div>
				</td>
			</tr>
			<tr>
				<td>
					<b class="title">报告时间：</b>
					<label>
						<fmt:formatDate value="${serviceReport.reportTime}" pattern="yyyy-MM-dd HH:mm:ss" />
					</label>
				</td>
				<td>
					<b class="title">报告人：</b>
					<label>${serviceReport.reporterName}</label>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<div style="float:left;"><b class="title">报告摘要：</b></div>
					<div class="eventRequestDesc"><label>${serviceReport.summary }</label></div>
				</td>
			</tr>
			<tr>
				<td>
					<b class="title">服务报告文件：</b>
					<input id="previousContractFileNameLook" type="hidden" value="${serviceReport.reportFile}" name="attachment" /><a id="downloadContractFileLook" title="下载文件"></a>
					<label></label>
				</td>
			</tr>
		</table>
	</div>
	<p class="conditionsBtn">
		<a href="javascript:void(0);" id="ServiceReportInfo_cancle">取消</a>
	</p>
</div>

</body>
</html>