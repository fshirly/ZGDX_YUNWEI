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
			src="${pageContext.request.contextPath}/js/monitor/discover/discoverResult.js"></script>
	</head>
	<body>
		<form id="formname" name="formname" action="" method="post">
			<input type="hidden" id="result" name="result" value="${result}" />
			<input type="hidden" id="taskID" name="taskID" value="${taskID}" />
			<input type="hidden" id="navigationBar" name="navigationBar" value="${navigationBar}"/>
			<div class="rightContent">
				<div class="location">
					当前位置：${navigationBar}
				</div>

				<div class="easyui-window"  minimizable="false"
					resizable="false" maximizable="false" closed="false"
					closable="false" collapsible="false" modal="true"
					title="设备发现--结果视图" style="width: 900px;height: 450px;">
					<div class="stepDivContainer">
						<ul>
							<li class="handleFirstDone">
								1、填写发现范围
							</li>
							<li class="handleOtherDone">
								2、开始发现
							</li>
							<li class="handleOtherIng">
								3、结果视图
							</li>
							<li class="handleOtherUndone">
								4、导入设备
							</li>
						</ul>
					</div>
					<table class="formtable">
						<tr style="height: 20px">
							<td colspan="15">
								<div id="deviceView" class="" style="width: 400px;"></div>
							</td>
						</tr>
 
						<tr style="height: 250px; width: 10px;">
							<td class="btntd" style="weight: 10px; text-align: center;">
								<a href="javascript:void(0);" id="btnSave" onclick="next3();">下一步</a>
							</td>
						</tr>
					</table>
				</div>
	</body>
</html>
