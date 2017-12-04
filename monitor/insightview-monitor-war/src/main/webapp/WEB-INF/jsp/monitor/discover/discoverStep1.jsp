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
				src="${pageContext.request.contextPath}/js/monitor/discover/indexDiscover.js"></script> 
	</head>
	<body onload="selectDiscoverWay(1)">
		<input type="hidden" id="navigationBar" name="navigationBar" value="${navigationBar}"/>
		<form id="formname" method="post">
			<div>
				<div class="location">
					当前位置：${navigationBar}
				</div>
				<div class="easyui-window" 
				 	minimizable="false"  resizable="false" maximizable="false" closed="false" closable="false"  
		 			collapsible="false" modal="true" title="设备发现--填写发现范围"
					style="width: 900px;height: 450px;">
				
  				<div class="stepDivContainer">
				 <ul>
					<li class="handleFirstIng">
						1、填写发现范围
					</li>
					<li class="handleOtherUndone">
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
					<tr style="height: 30px;"> 
						<td width="70%" align="left" colspan="12" > 
							<input type="radio" checked="" onclick="selectDiscoverWay(1)" value="1" name="selectDiscover">&nbsp;起始IP 
							&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="radio" onclick="selectDiscoverWay(2)" value="2" name="selectDiscover">&nbsp;子网发现 
							&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="radio" onclick="selectDiscoverWay(3)" value="3" name="selectDiscover">&nbsp;路由发现 
						</td>
					</tr>
					<tr style="height: 50px">
						<td colspan="12">
							<div id="byIPScpoe" style="display: block;">
								<table>
									<tr style="height: 10px">
										<td style="text-align: right;">
											起始IP&nbsp;:&nbsp;
										</td>
										<td>
											<input size="15" type="text" id="startIP1" value=""
												data-options="required:true"
												validator="{'default':'chnStr','length':'1-5'}" />
										</td>
										<td>
											&nbsp;&nbsp;&nbsp;&nbsp;终止IP&nbsp;:&nbsp;
										</td>
										<td>
											<input size="15" type="text" id="endIP1" value=""
												data-options="required:true" />
										</td>
									</tr>
								</table>
							</div>
							<div id="byStartIP">
								<table>
									<tr style="height: 10px">
										<td style="text-align: right;">
											起始IP&nbsp;:&nbsp;
										</td>
										<td>
											<input size="15" type="text" id="startIP2" value=""
												data-options="required:true" />
										</td>
										<td>
											<p class="conditionOne">
												&nbsp;&nbsp;&nbsp;&nbsp;网络掩码&nbsp;:&nbsp;
												<select class="inputs" id="netMask2" style="width: 135px">
													<option value="255.255.255.252">
														255.255.255.252
													</option>
													<option value="255.255.255.248">
														255.255.255.248
													</option>
													<option value="255.255.255.240">
														255.255.255.240
													</option>
													<option value="255.255.255.224">
														255.255.255.224
													</option>
													<option value="255.255.255.192">
														255.255.255.192
													</option>
													<option value="255.255.255.128">
														255.255.255.128
													</option>

													<option value="255.255.255.0">
														255.255.255.0
													</option>
													<option value="255.255.254.0">
														255.255.254.0
													</option>
													<option value="255.255.252.0">
														255.255.252.0
													</option>
													<option value="255.255.248.0">
														255.255.248.0
													</option>
													<option value="255.255.240.0">
														255.255.240.0
													</option>
													<option value="255.255.224.0">
														255.255.224.0
													</option>
													<option value="255.255.192.0">
														255.255.192.0
													</option>
													<option value="255.255.128.0">
														255.255.128.0
													</option>

													<option value="255.255.0.0">
														255.255.0.0
													</option>
													<option value="255.254.0.0">
														255.254.0.0
													</option>
													<option value="255.252.0.0">
														255.252.0.0
													</option>
													<option value="255.248.0.0">
														255.248.0.0
													</option>
													<option value="255.240.0.0">
														255.240.0.0
													</option>
													<option value="255.224.0.0">
														255.224.0.0
													</option>
													<option value="255.192.0.0">
														255.192.0.0
													</option>
												</select>
											</p>
										</td>
									</tr>
								</table>
							</div>
							<div id="byRouteStep">
								<table>
									<tr style="height: 10px">
										<td style="text-align: right;">
											起始IP&nbsp;:&nbsp;
										</td>
										<td>
											<input size="15" type="text" id="startIP3" value=""
												data-options="required:true"
												validator="{'default':'chnStr','length':'1-5'}" />
										</td>
										<td>
											&nbsp;&nbsp;&nbsp;&nbsp;发现跳数&nbsp;:&nbsp;
										</td>
										<td>
											<input size="15"  type="text" id="step3" value="" />
										</td>
									</tr>
								</table> 
							</div>
						</td>
					</tr>
<!--  
					<tr style="height: 50px">
						<td colspan="12" style="text-align: left;">
							要用的凭证： SNMP&nbsp;:&nbsp;
							<input type="checkbox" name="checkbox" id="v1" />
							v1&nbsp;
							<input type="checkbox" name="checkbox" id="v2" />
							v2&nbsp;
							<input type="checkbox" name="checkbox" id="v3" />
							v3&nbsp;
						</td>
					</tr>
-->
					<tr style="height: 150px;width:10px;">
						<td class="btntd" style="weight:10px;text-align: center;">
							<a href="javascript:void(0);" id="btnSave" onclick="saveInfo();">开始发现</a>
							<a href="javascript:void(0);" id="btnUpdate" onclick="reset();">重置</a>
						</td> 
					</tr> 
				</table>
			</div>
	</body>
</html>
