<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>我的任务</title>
<link rel="stylesheet" type="text/css"
		href="${pageContext.request.contextPath}/style/css/base.css" />
	<link rel="stylesheet" type="text/css"
		href="../style/css/jquery.autocomplete.css" />
	<link rel="stylesheet" type="text/css"
		href="../plugin/easyui/themes/default/easyui.css" />

	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/common/jquery-1.9.1.min.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/plugin/easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/plugin/autocomplete/jquery.autocomplete.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/common/commonUtil.js"></script>
	

</head>
<body>
	<link rel="stylesheet" 
	    href="${pageContext.request.contextPath}/plugin/select2/select2.css" /> 
	<script  type="text/javascript" 
	    src="${pageContext.request.contextPath}/js/common/commonPlugins.js"></script>
	<script type="text/javascript" 
	    src="${pageContext.request.contextPath}/plugin/select2/select2.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/common/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript"
			src="${pageContext.request.contextPath}/js/dashboardPageManage/myTaskManage.js"></script>

	<div class="taskl">
		<h2>快速发布任务</h2>
		<div class="tips" id="errorText"></div>
		<form id="taskForm">
			<table class="formtable1">
				<tr>
					<td class="title">标题：</td>
					<td><input type="text" name="title" id="title" validator="{'length':'1-50'}"/></td>
				</tr>
				<tr>
					<td class="title">负责人：</td>
					<td><input type="hidden" name="director" id="director" />
						<select id="directorName" name="directorName" style="width:182px"
						 validator="{'default':'*','type':'select2'}">
	                         <option value="-1">请选择...</option>
	                    </select>
					</td>
				</tr>
				<tr>
					<td class="title">任务类型：</td>
					<td><input class="easyui-combobox" id="type" name="type" data-options="panelWidth:182" validator="{'default':'*','type':'combobox'}"/></td>
				</tr>
				<tr>
					<td class="title">结束时间：</td>
	                <td><input class="easyui-datetimebox" id="validTimeEnd" name="validTimeEnd" validator="{'default':'*','type':'datetimebox'}"/></td>
				</tr>
				<tr>
					<td class="title">描述：</td>
					<td><textarea id="content" name="content" rows="10" class="easyui-validatebox" validator="{'length':'1-500'}"></textarea></td>
				</tr>
				<tr>
					<td colspan="2">
						<input id="resetBtn" class="btn" type="button" value="重置"/>
						<input id="commitBtn" class="btn" type="button" value="提交"/>
					</td>
				</tr>
			</table>
		</form>		
	</div>
	<div class="taskr">
		<div id="taskTabs" class="tabstable easyui-tabs">
			<div title="待回复任务" id="taskTab1" class="datas">
				<table id="tblMyTaskList"></table>
			</div>
			<div title="已回复任务" id="taskTab2" class="datas">
				<table id="tblMyTaskList2"></table>
			</div>
		</div>
	</div>
</body>
</html>
