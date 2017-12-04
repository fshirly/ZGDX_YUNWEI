<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
	<html>
		<head>
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
	 		src="${pageContext.request.contextPath}/js/monitor/discover/discoverResult.js"></script>
	</head>
	<body>
		<form name="formname" action="" method="post"> 
		<input type="hidden" id="result" name="result" value="${result}" />
		<input type="hidden" id="taskID" name="taskID" value="${taskID}" />
			<div id="container" align="center">
				<table  height="130" >
					<tr>
						<td>
							<table class="smallpart" cellspacing="1">
								<tr style="text-align: left; height: 20px">
									<td colspan="15">
										<strong>设备发现</strong>
									</td>
								</tr>
								<tr style="text-align: left; height: 0px">
									<td colspan="15">
										<img src='${pageContext.request.contextPath}/style/images/process3.gif' />
									</td>
								</tr>
								
								<tr style="height: 150px">
									<td colspan="15"> 
										<div id="deviceView" class="" style="width: 400px;"></div>
									</td>
								</tr>
	 
								<tr style="height:50px">
									<td colspan="15" align="center">
										<input class="buttonB" type="button" id="btnSave" value="导入结果" onclick="javascript:next3()"/> 
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