<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/css/base.css" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/css/reset.css" />
	<link rel="stylesheet" type="text/css"
		href="../plugin/easyui/themes/default/easyui.css" />
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/common/jquery-1.9.1.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/common/commonUtil.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/plugin/easyui/jquery.easyui.min.js"></script>
</head>
<body>
	 <link rel="stylesheet" 
        href="${pageContext.request.contextPath}/plugin/select2/select2.css" /> 
    <script  type="text/javascript" 
        src="${pageContext.request.contextPath}/js/common/commonPlugins.js"></script>
    <script type="text/javascript" 
        src="${pageContext.request.contextPath}/plugin/select2/select2.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/dashboardPageManage/toAllocate.js"></script>
		
	<div>
		<table id="updateProviderInfo" class="tableinfo1">
					<tr>
						<td>
							<b class="title">标题：</b>
							<label>${event.title }</label>
						</td>
					</tr>
					<tr>
						<td>
							<b class="title">报告人：</b>
							<label>${requester }</label>
						</td>
					</tr>
					<tr>
						<td>
							<b class="title">报告方式：</b>
							<label>${requestMode }</label>
						</td>
					</tr>
					<tr>
						<td>
							<b class="title">报告时间：</b>
							<label>
								<fmt:formatDate value="${projectAppraisal.appraisalTme }" pattern="yyyy-MM-dd HH:mm:ss" />
							</label>
						</td>
					</tr>
					<tr>
						<td>
							<b class="title">描述：</b>
							<label>${event.description }</label>
						</td>
					</tr>
		</table>
		<p class="conditionsBtn">
			<a href="javascript:void(0);" onclick="javascript:$('#popWin').window('close');">关闭</a>
		</p>
	</div>	
</body>
</html>
