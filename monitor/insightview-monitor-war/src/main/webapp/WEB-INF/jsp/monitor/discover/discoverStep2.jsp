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
			src="${pageContext.request.contextPath}/js/monitor/discover/indexProcess.js"></script>
	</head>
	<body>
		<form id="formname" name="formname" action="" method="post">
		<input type="hidden" id="taskID" name="taskID" value="${taskID}" />
		<input type="hidden" id="way" name="way" value="${way}" />
		<input type="hidden" id="navigationBar" name="navigationBar" value="${navigationBar}"/>
			<div class="rightContent">
				<div class="location">
					当前位置：${navigationBar}
				</div>
				<div class="easyui-window"
				 	minimizable="false"  resizable="false" maximizable="false" closed="false" closable="false"  
		 			collapsible="false" modal="true" title="设备发现--开始发现"
					style="width: 900px;hight:800px;height: 450px;">
				     <div class="stepDivContainer"> 
						<ul>
							<li class="handleFirstDone">
								1、填写发现范围
							</li>
							<li class="handleOtherIng">
								2、开始发现
							</li>
							<li class="handleOtherUndone">
								3、结果视图
							</li>
							<li class="handleOtherUndone">
								4、导入设备
							</li>
						</ul>
					</div>
					<table class="formtable">
						<tr style="height: 40px" id="showProcess">
							<td>
								<div id="p" class="easyui-progressbar" style="width: 439px;"></div>
							</td>
						</tr>
						
						<tr id = "hideProcess" style="display: none">
							<td>
							<div style="margin-left: -275px; text-align: center;">
								<img src='${pageContext.request.contextPath}/style/images/wait.gif' alt="正在扫描" />
							</div>
							</td>
						</tr>
						
						<tr>
							<td>
								详细信息:
								<br>
							</td>
						</tr>
						<tr style="height: 40px">
							<td>
								<textarea rows="10" id="processtxt" class="x3" style=" width: 436px;"></textarea>
							</td>
						</tr>

						<tr style="height: 100px; width: 10px;">
							<td colspan="15" class="btntd"
								style="weight: 10px; text-align: center;"> 
								<div id="cancelDiscover" style="display: block;">
									<a href="javascript:void(0);" id="btnCancel" onclick="cancel();">取消发现</a>
								</div>
								<div id="nextstep" style="display: none;">
									<a href="javascript:void(0);" id="btnSave" onclick="next();">下一步</a>
									<a href="javascript:void(0);" id="btnCancel" onclick="cancel();">取消发现</a>
								</div>
								
							</td>
							
						</tr>
					</table>
				</div>
	</body>
</html>
