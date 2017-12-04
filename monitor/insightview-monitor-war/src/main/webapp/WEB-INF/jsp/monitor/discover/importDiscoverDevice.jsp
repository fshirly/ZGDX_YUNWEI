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
		
		<form name="formname">
			<input type="hidden" id="result" name="result" value="${result}" />
			<input type="hidden" id="taskID" name="taskID" value="${taskID}" />
			<div id="container" align="center">
				<table>
					<tr>
						<td>
							<table class="smallpart" cellspacing="1">
								<tr style="text-align: left; height: 30px">
									<td colspan="15">
										<strong>设备发现</strong>
									</td>
								</tr> 
								<tr style="text-align: left; height: 10px">
									<td colspan="15">
										<img src='${pageContext.request.contextPath}/style/images/process4.gif' />
									</td>
								</tr>
								<tr style="height: 40px">
									<td colspan="15">
										<strong>选择被管设备</strong>
									</td>
								</tr>
								
								<tr style="text-align: left; height:80px">
									<td colspan="15">
										<div id="dataTreeDiv"> 
									</td>
								</tr>
								<tr style="height:30px"> 
								  	<td colspan="15" align="center">
										<input class="buttonB" type="button" id="btnSave" value="导入结果" onclick="javascript:store()"/>
								     	<input class="buttonB" type="button" id="btnUpdate" value="发现更多" onclick="javascript:afresh()"/>
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