<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>快速创建</title>
	<link rel="stylesheet" type="text/css"
		href="${pageContext.request.contextPath}/style/css/base.css" />
	<link rel="stylesheet" type="text/css"
		href="${pageContext.request.contextPath}/style/css/reset.css" />
	<link rel="stylesheet" type="text/css"
		href="../style/css/jquery.autocomplete.css" />
	<link rel="stylesheet" type="text/css"
		href="../plugin/easyui/themes/default/easyui.css" />
</head>
<body>


	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/common/jquery-1.9.1.min.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/plugin/easyui/jquery.easyui.min.js"></script>
	<link rel="stylesheet" 
        href="${pageContext.request.contextPath}/plugin/select2/select2.css" /> 
    <script  type="text/javascript" 
        src="${pageContext.request.contextPath}/js/common/commonPlugins.js"></script>
    <script type="text/javascript" 
        src="${pageContext.request.contextPath}/plugin/select2/select2.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/plugin/autocomplete/jquery.autocomplete.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/common/commonUtil.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/dashboardPageManage/rapidCreateEvent.js"></script>

	<form id="eventForm">
		<table class="rapidCreatTab">
		    <tr>
		       <td colspan="2"><div id="errorText"></div></td>
		    </tr>
			<tr>
				<td class="title">报告人：</td>
				<td height="32px">
					<input type="hidden" name="userId" id="userId" value="${userId}" />
					<select id="requester" name="requester" style="width:182px"
					 validator="{'default':'*','type':'select2'}">
	                     <option value="-1">请选择...</option>
	                </select>
				</td>
			</tr>
			<tr>
				<td class="title">请求标题：</td>
				<td><input type="text" name="title" id="title" style="width:250px" validator="{'length':'1-50'}" /></td>
			</tr>
			<tr>
				<td class="title">报告方式：</td>
	            <td><input id="requestMode" name="requestMode" class="easyui-combobox" style="width:178px"  validator="{'default':'*','type':'combobox'}"/></td>
			</tr>
			<tr>
				<td class="title">描述：</td>
				<td>
					<textarea id="description" name="description" rows="6" style="width:250px" validator="{'length':'1-500'}">相关设备编号：
请求描述：</textarea>
				</td>
			</tr>
			<tr>
				<td></td>
				<td>
					<input id="commitBtn" type="button" value="提交" class="btn" />
				</td>
			</tr>
		</table>
	</form>
</body>
</html>
