<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>设备发现</title>
		<link rel="stylesheet" type="text/css"
			href="${pageContext.request.contextPath }/style/css/newProblemBill.css" />
		<link rel="stylesheet" type="text/css"
			href="${pageContext.request.contextPath}/style/css/base.css" />
		<link rel="stylesheet" type="text/css" 
		    	href="${pageContext.request.contextPath}/style/common/include_css.css" /> 
		<!-- mainframe -->
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/js/common/jquery-1.9.1.min.js"></script>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/js/common/commonUtil.js"></script> 
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/js/common/base64.js"></script>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/plugin/easyui/jquery.easyui.min.js"></script> 
			 
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/js/common/easyui-lang-zh_CN.js"></script>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/plugin/autocomplete/jquery.autocomplete.js"></script>
		<script type="text/javascript"	src="${pageContext.request.contextPath}/js/monitor/discover/indexProcess.js"></script>
	</head>
	<body>
		<form id="formname" name="formname" action="" method="post">
		<input type="hidden" id="taskID" name="taskID" value="${taskID}" />
			<div id="container"  align="center">
				<table>
					<tr>
						<td>
							<table class="smallpart" cellspacing="1">
								<tr  style="text-align: left; height: 20px">
									<td colspan="15">
										<strong>设备发现</strong>
									</td>
								</tr> 
								<tr style="text-align: left; height: 0px">
									<td colspan="15">
										<img src='${pageContext.request.contextPath}/style/images/process2.gif' />
									</td>
								</tr>
								<tr style="height: 40px">
									<td colspan="15">
										<div style="width: 480px; height: auto;"></div>
										<div id="p" class="easyui-progressbar" style="width: 400px;"></div>
									</td>
								</tr> 
								<tr>
									<td colspan="15">
									          详细信息:<br>
										<textarea id="processtxt" name="" cols="55" rows="5"></textarea>
									</td>  
								</tr>
								<tr style="height: 60px">
									<td colspan="15" align="center">
											<div id="nextstep" style="display: none;"> 
											<input class="buttonB" type="button" id="btnSave" value="下一步" onclick="javascript:next()"/>
										</div> 	
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</div>
		</form>
	</body>
</html>