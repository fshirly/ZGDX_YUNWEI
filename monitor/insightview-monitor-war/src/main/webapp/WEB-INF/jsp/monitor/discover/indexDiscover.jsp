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
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/js/common/jquery-1.9.1.min.js"></script>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/js/common/commonUtil.js"></script> 
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/js/monitor/discover/indexDiscover.js"></script>	 
 
	</head>

	<body onload="selectDiscoverWay(1)">
		<form id="formname" name="formname" action="" method="post">
			<div id="container" align="center">
				<table height="130" width="200">
					<tr>
						<td>
							<table class="smallpart" cellspacing="1"  width="200">
								<tr style="text-align: left; height: 20px">
									<td colspan="12">
										<strong>设备发现</strong>  
									</td>
								</tr> 
								<tr style="text-align: left; height: 0px">
									<td colspan="12">
										<img src='${pageContext.request.contextPath}/style/images/process.gif' />
									</td>
								</tr>
								<tr style="height: 30px">
									<td colspan="12">
										&nbsp;&nbsp;
										<input type="radio" checked="" onclick="selectDiscoverWay(1)" value="1" name="selectDiscover">
										起始IP
										<input type="radio" onclick="selectDiscoverWay(2)" value="2" name="selectDiscover">
										子网发现
										<input type="radio" onclick="selectDiscoverWay(3)" value="3" name="selectDiscover">
										路由发现
									</td>
								</tr>
								<tr style="height: 50px">
									<td colspan="12">
										<div id="byIPScpoe" style="display: block;">
											<table>
												<tr style="height: 10px">
													<td style="text-align: right;">
														&nbsp;&nbsp;起始IP:
													</td>
													<td>
														<input  size=15" type="text" id="startIP1" value="" data-options="required:true" validator="{'default':'chnStr','length':'1-5'}" />
													</td>
													<td>
														&nbsp;&nbsp;终止IP:
													</td>
													<td>
														<input size=15" type="text" id="endIP1" value="" data-options="required:true" />
													</td>
												</tr>
											</table>
										</div>
										<div id="byStartIP">
											<table>
												<tr style="height: 10px">
													<td style="text-align: right;">
														&nbsp;&nbsp;起始IP:
													</td>
													<td>
														<input size=15" type="text" id="startIP2" value="" data-options="required:true" />
													</td>
													<td>
														<p class="conditionOne">
															&nbsp;&nbsp;网络掩码:
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
														&nbsp;&nbsp;起始IP:
													</td>
													<td>
														<input size=15" type="text" id="startIP3" value=""
															data-options="required:true" />
													</td>
													<td>
														<p class="conditionOne">
															&nbsp;&nbsp;选择跳数:
															<select class="inputs" id="step3" style="width: 50px">
																<option value="1">
																	1
																</option>
																<option value="2">
																	2
																</option>
																<option value="3">
																	3
																</option>
															</select>
														</p>
													</td>
												</tr>
											</table>
										</div>
									</td>
								</tr>

								<tr style="height: 50px">
									<td colspan="12" style="text-align: left;">
										&nbsp;&nbsp;要用的凭证： SNMP:
										<input type="checkbox" name="checkbox" id="v1" />
										v1&nbsp;
										<input type="checkbox" name="checkbox" id="v2" />
										v2&nbsp;
										<input type="checkbox" name="checkbox" id="v3" />
										v3&nbsp;
									</td>
								</tr>
								<tr style="height: 50px">
									<td colspan="12" align="center">
										<input class="buttonB" type="button" id="btnSave" value="开始发现" onclick="javascript:saveInfo()"/>
								     	<input class="buttonB" type="button" id="btnUpdate" value="重置" onclick="javascript:reset()"/>
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