<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

</head>
<body>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/style/css/base.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/css/reset.css" />
<link href="${pageContext.request.contextPath}/plugin/select2/select2.css" rel="stylesheet"/>
	
<script type="text/javascript"
		src="${pageContext.request.contextPath}/fui/plugin/fui-fileupload.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/common/commonPlugins.js"></script>
<script type="text/javascript" 
	src="${pageContext.request.contextPath}/plugin/select2/select2.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/plugin/autocomplete/jquery.autocomplete.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/common/easyui-lang-zh_CN.js"></script>
<script type="text/javascript"
    src="${pageContext.request.contextPath}/js/isv/comp/ddgrid.js"></script>	
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/platform/serviceReport/serviceReport_add.js"></script>

<div>
	<div id="divServiceReportInfo">
		
		<table id="tblServiceReportInfo" class="formtable">
			<tr>
				<input id="ipt_serviceReportID" type="hidden" value="${serviceReport.serviceReportID }"/>
				<td class="title">服务报告名称：</td>
				<td colspan="3">
					<input id="ipt_name" class="x2" name="name" value="${serviceReport.name }" validator="{'length':'1-50'}"/><dfn>*</dfn>
				</td>
			</tr>
			<tr>
				<td class="title">报告时间：</td>
				<td>
					<input id="ipt_reportTime" name="reportTime" value="${serviceReport.strReportTime}" validator="{'default':'*','type':'datetimebox'}"/><dfn>*</dfn>
				</td>
				<td class="title">报告人：</td>
				<td>
					<select id="ipt_reporter"
						name="reporter" value="${serviceReport.reporter}"
						validator="{'default':'*','type':'select2'}" style="width: 182px">
							<option value="-1">请选择...</option>
					</select><dfn>*</dfn>
				</td>
			</tr>
			<tr>
				<td class="title">报告摘要：</td>
				<td colspan="3">
					<textarea id="ipt_Summary" rows="5" class="x2" validator="{'length':'0-500'}">${serviceReport.summary }</textarea>
				</td>
			</tr>
			<tr>
				<td class="title">服务报告文件：</td>
				<td colspan="3">
				    <input id="ipt_reportFile_path" type="hidden" value="${serviceReport.reportFile }"/>
					<input id="ipt_reportFile" name="reportFile" type="file"/>
				</td>
			</tr>
		</table>
	</div>
	<p class="conditionsBtn">
		<a href="javascript:void(0);" id="ServiceReportInfo_submit">提交</a>
		<a href="javascript:void(0);" id="ServiceReportInfo_cancle">取消</a>
	</p>
</div>

</body>
</html>