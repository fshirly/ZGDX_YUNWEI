<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="../../common/pageincluded.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>设备发现</title>
		<link rel="stylesheet" type="text/css"
			href="${pageContext.request.contextPath}/style/css/restypedefine.css" />
		<link rel="stylesheet" type="text/css"
			href="${pageContext.request.contextPath}/style/css/resassettype.css" />
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/plugin/dTree/dtree.js"></script>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/plugin/dTree/combo/comboJS.js"></script>
		<!-- mainframe -->
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/plugin/easyui/jquery.easyui.min.js"></script>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/js/common/easyui-lang-zh_CN.js"></script>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/plugin/autocomplete/jquery.autocomplete.js"></script>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/js/monitor/discover/importDiscoverDevice.js"></script>
	</head>
	<body>
		<form id="formname" name="formname" action="" method="post">
			<input type="hidden" id="result" name="result" value="${result}" />
			<input type="hidden" id="taskID" name="taskID" value="${taskID}" />
			<input type="hidden" id="navigationBar" name="navigationBar" value="${navigationBar}" />
			<div class="rightContent">
				<div class="location">
					当前位置：${navigationBar}
				</div>

				<div class="easyui-window"  minimizable="false"
					resizable="false" maximizable="false" closed="false"
					closable="false" collapsible="false" modal="true"
					title="设备发现--导入设备" style="width: 900px;height: 450px;">
					<div class="stepDivContainer">
						<ul>
							<li class="handleFirstDone">
								1、填写发现范围
							</li>
							<li class="handleOtherDone">
								2、开始发现
							</li>
							<li class="handleOtherDone">
								3、结果视图
							</li>
							<li class="handleOtherIng">
								4、导入设备
							</li>
						</ul>
					</div>

					<table id="discoverStep4" width="100px" class="formtable">
						<tr style="height: 10px">
							<td colspan="15">
								<strong>选择被管设备</strong>
							</td>
						</tr>

						<tr style="text-align: left; height: 6px">
							<td colspan="15">
								<div id="dataTreeDiv">
							</td>
						</tr>

						<tr style="height: 200px; width: 10px;">
							<td class="btntd" style="weight: 10px; text-align: center;">
								<a href="javascript:void(0);" id="btnSave" onclick="store();">导入结果</a>
								<a href="javascript:void(0);" id="btnUpdate" onclick="afresh();">重新发现</a>
							</td>
						</tr>
					</table>
				</div>
	</body>
</html>
