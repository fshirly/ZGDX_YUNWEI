<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/css/base.css" />
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/css/reset.css" />
		<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/common/jquery-1.9.1.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/common/commonUtil.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/common/Validdiv.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/common/base64.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/common/Validdiv.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/plugin/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/common/easyui-lang-zh_CN.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/style/style_one/js/main.js"></script>

<style type="text/css">
body {
	font-size: 12px;
	font-family: tahoma;
}

div#showTips {
	height: 200px;
	line-height: 200px;
}

div.align-center{ 
position:fixed;left:50%;top:50%;margin-left:width/2;margin-top:height/2;
} 
</style>
 

	</head>
	<body>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/js/monitor/device/setMonitor.js"></script>
		<div id="deviceSetting">
			 <input type="hidden" id="index" value="${flag}" class="default"/>
		     <input type="hidden" id="deviceip" value="${deviceip}" class="default"/>
		     <input type="hidden" id="moid" value="${moid}" class="default"/>
		     <input type="hidden" id="moClassId" value="${moClassId}" class="default"/>
		     <input type="hidden" id="nemanufacturername" value="${nemanufacturername}" class="default"/>
		     <input type="hidden" id="className" value="${className}" class="default"/>
		     <input type="hidden" id="taskId" value="${taskId}" class="default"/>
		     <input type="hidden" id="port" value="${port}" class="default"/>
		     <input type="hidden" id="dbName" value="${dbName}" class="default"/>
		     <input type="hidden" id="templateID" value="${templateID}" class="default"/>
			 <input type="hidden" id="monitorFlag" value="edit"/>
			 <input type="hidden" id="authType"/>
			 <input type="hidden" id="authTypeDefault"/>
			 <input type="hidden" id="treeTypeDefault"/>
		   <div id="mobjectSetting" title="对象配置" data-options="closable:false"
				style="overflow: auto;">
				<table id="tblAddDevice" class="formtable1">
					<tr>
						<td class="title">
							对象类型：
						</td>
						<td>
							<input id="ipt_mobjectClassName" type="hidden"/>
							<input id="ipt_mobjectClassID"  readonly onfocus="choseMObjectTree();" validator="{'default':'*','length':'1-64'}" /><b>*</b>
						</td>
					</tr>
					<tr id="isMoAlias">
						<td class="title">
							别名：
						</td>
						<td>
							<input id="ipt_moAlias"  validator="{'length':'0-200'}" value="${moAlias}"/>
						</td>
					</tr>
					<tr id="isShowPort" style="display: none">
						<td class="title">
							端口：
						</td>
						<td>
							<input id="ipt_serverPort" readonly="readonly" /><b>*</b>
						</td>
					</tr>
				</table>
				<div class="conditionsBtn">
					<a href="javascript:setObjectType();" >确定</a>
					<a href="javascript:closeWindow();">取消</a>
				</div>
			</div>
			
			<div id="divCommunity" title="凭证配置" data-options="closable:false" style="overflow: auto;">
				<!--SNMP凭证  -->
				<div id="divAddSNMPCommunity" style="display: none;">
				<input type="hidden" id="choice" value=""/>
					<input id="ipt_id" type="hidden"/>
					<input id="ipt_moID" type="hidden" />
					<table id="tblSNMPCommunityInfo" class="formtable">
						<tr>
							<th  colspan="4"   >
								SNMP凭证
							</th>
						</tr>
						<tr>
							<td class="title">
								设备IP：
							</td>
							<td>
								<input id="ipt_deviceIP" class="input"  value="${deviceip}" readonly="readonly"  disabled="disabled"/>
								<input id="ipt_deviceId" type="hidden"/>
							</td>
							<td class="title">
								协议版本：
							</td>
							<td>
								<!--<select id="ipt_snmpVersion" class="inputs" onclick=isOrnoCheckSnmp();>
									<option value="0">
										V1
									</option>
									<option value="1">
										V2
									</option>
									<option value="3">
										V3
									</option>
								</select>
							-->
							<input id="ipt_snmpVersion" class="input" readonly="readonly"  disabled="disabled"/>
							</td>
						</tr>
						<tr id="readCommunity">
							<td class="title">
								读Community：
							</td>
							<td>
								<input id="ipt_readCommunity" type="text" readonly="readonly" disabled="disabled" validator="{'default':'*','length':'1-50'}"/>
							</td>
							<td class="title">
								写Community：
							</td>
							<td>
								<input id="ipt_writeCommunity" type="text" readonly="readonly" disabled="disabled" />
							</td>
						</tr>
						<tr id="usmUser" style="display: none;">
							<td class="title">
								USM用户：
							</td>
							<td>
								<input id="ipt_usmUser" type="text" validator="{'default':'*','length':'1-50'}" readonly="readonly" disabled="disabled"/>
							</td>
							<td class="title">
								上下文名称：
							</td>
							<td>
								<input id="ipt_contexName" type="text" readonly="readonly" disabled="disabled"/>
							</td>
						</tr>
						<tr id="authAlogrithm" style="display: none;">
							<td class="title">
								认证类型：
							</td>
							<td>
								<!--<select id="ipt_authAlogrithm" class="inputs">
									<option value="-1">
										请选择
									</option>
									<option value="MD5">
										MD5
									</option>
									<option value="SHA">
										SHA
									</option>
								</select>
							-->
							<input id="ipt_authAlogrithm" class="input" readonly="readonly" disabled="disabled"/>
							</td>
							<td class="title">
								认证KEY：
							</td>
							<td>
								<input id="ipt_authKey" type="text" readonly="readonly"  disabled="disabled"/>
							</td>
						</tr>
						<tr id="encryptionAlogrithm" style="display: none;">
							<td class="title">
								加密算法：
							</td>
							<td>
								<!--<select id="ipt_encryptionAlogrithm" class="inputs">
									<option value="-1">
										请选择
									</option>
									<option value="DES">
										DES
									</option>
									<option value="3DES">
										3DES
									</option>
									<option value="AES-128">
										AES-128
									</option>
									<option value="AES-192">
										AES-192
									</option>
									<option value="AES-256">
										AES-256
									</option>
								</select>
								
								-->
								<input id="ipt_encryptionAlogrithm" class="input" readonly="readonly" disabled="disabled"/>
							</td>
							<td class="title">
								加密KEY：
							</td>
							<td>
								<input id="ipt_encryptionKey" type="text" readonly="readonly" disabled="disabled"/>
							</td>
						</tr>
						<tr>
							<td class="title">
								SNMP端口：
							</td>
							<td>
								<input id="ipt_snmpPort" type="text" value="161" validator="{'default':'*','length':'1-30'}" readonly="readonly" disabled="disabled"></input>
							</td>
						</tr>
					</table>
				</div>

				<!-- VMware凭证 -->
				<div id="divAddVMwareCommunity" style="display: none;">
				<input type="hidden" id="choice" value=""/>
					<input id="ipt_id" type="hidden" />
					<input id="ipt_moID" type="hidden" />
					<table id="tblAuthCommunityInfo" class="formtable1">
						<tr>
							<th  colspan="2"  >
								VMware凭证
							</th>
						</tr>
						<tr>
							<td class="title">
								登录设备：
							</td>
							<td>
								<input id="ipt_deviceIP" class="input"   value="${deviceip}" readonly="readonly"/>
								<input id="ipt_deviceId" type="hidden" />
							</td>
						</tr>
						<tr>
							<td class="title">
								登录端口：
							</td>
							<td>
								<input id="ipt_VMport" type="text"  
									value="" />
							</td>
						</tr>
						<tr id="readCommunitys">
							<td class="title">
								用户名：
							</td>
							<td>
								<input id="ipt_userName" type="text" validator="{'default':'*','length':'1-64'}"  />
								<b>*</b>
							</td>
						</tr>
						<tr>
							<td class="title">
								密码：
							</td>
							<td>
								<input id="ipt_password" type="password"  validator="{'default':'*','length':'1-64'}" />
								<b>*</b>
							</td>
						</tr>
					</table>
				</div>

				<!-- 数据库凭证 -->
				<div id="divAddDBMSCommunity" style="display: none;">
				<table id="tblDBMSCommunity" class="formtable1">
					<tr>
						<th  colspan="2"   >
							数据库凭证
						</th>
					</tr>
					<tr>
						<td class="title">
							设备IP：
						</td>
						<td>
							<input id="ipt_dbDeviceIP" class="input"   value="${deviceip}" readonly="readonly"/>
							<input id="ipt_deviceId" type="hidden"/>
						</td>
					</tr>
					<tr>
						<td class="title">
							数据库名：
						</td>
						<td>
							<input id="ipt_dbName" type="text"  validator="{'default':'*','length':'1-80'}"/>
							<b>*</b>
						</td>
					</tr>
					<tr>
						<td class="title">
							数据库类别：
						</td>
						<td>
							<input id="ipt_dbmsType" type="text" readonly/>
							<b>*</b>
						</td>
					</tr>
					
					<tr>
						<td class="title">
							用户名：
						</td>
						<td>
							<input id="ipt_dbUserName" type="text"  validator="{'default':'*','length':'1-50'}" />
							<b>*</b>
						</td>
					</tr>
					<tr>
						<td class="title">
							密码：
						</td>
						<td>
							<input id="ipt_dbPassword" type="password"  validator="{'default':'*','length':'1-50'}"/>
							<b>*</b>
						</td>
					</tr>
					
					<tr>
						<td class="title">
							端口：
						</td>
						<td>
							<input id="ipt_dbPort" type="text" readonly="readonly"/>
						</td>
					</tr>
				</table>
				</div>
				
				<!-- JMX凭证 -->
				<div id="divAddMiddlewareCommunity" style="display: none;">
				<table id="tblMiddlewareCommunity" class="formtable1">
					<tr>
						<th  colspan="2"   >
							JMX凭证
						</th>
					</tr>
					<tr>
						<td class="title">
							设备IP：
						</td>
						<td colspan="3">
							<input id="ipt_middleDeviceIP" class="input"   value="${deviceip}" readonly="readonly"/>
							<input id="ipt_deviceId" type="hidden"/>
						</td>
					</tr>
					<tr id="isShowUser" style="display: none">
						<td class="title">用户名：</td>
						<td>
							<input id="ipt_middleUserName" type="text" validator="{'length':'0-255'}"/>
						</td>
					</tr>
					<tr id="isShowPwd" style="display: none">
						<td class="title">密码：</td>
						<td>
							<input id="ipt_middlePassWord" type="password" validator="{'length':'0-255'}" />
						</td>
					</tr>
					<tr>
						<td class="title">端口：</td>
						<td>
							<input id="ipt_middlePort" type="text" validator="{'default':'*' ,'length':'1-128'}" readonly="readonly"/><b>*</b>
						</td>
					</tr>
					<tr>
						<td class="title">控制台URL：</td>
						<td>
							<textarea rows="3" id="ipt_url" validator="{'default':'*','length':'1-150'}" msg="{reg:'1-150位字符！'}" onfocus="urlClick();"/>	<b>*</b>
						</td>
					</tr>
				</table>
				</div>
				
				<!-- 机房环境监控凭证 -->
				<div id="divAddRoomCommunity" style="display: none;">
				<table id="tblRoomCommunity" class="formtable1">
					<tr>
						<td class="title">用户名：</td>
						<td>
							<input id="ipt_roomUserName" type="text" validator="{'length':'0-128'}"/>
						</td>
					</tr>
					<tr>
						<td class="title">密码：</td>
						<td>
							<input id="ipt_roomPassWord" type="password" validator="{'length':'0-128'}" />
						</td>
					</tr>
					<tr>
						<td class="title">端口：</td>
						<td>
							<input id="ipt_roomPort" type="text" validator="{'default':'*'}" readonly="readonly"/><b>*</b>
						</td>
					</tr>
				</table>
				</div>
				
				<div class="conditionsBtn" id="isShowButton">
					<a href="javascript:setCommunity();" >确定</a>
					<a href="javascript:closeWindow();">取消</a>
				</div>
			</div>

			<div id="divThresholdSetting" title="阈值配置" data-options="closable:false" >
				<div id="divThresholdAdd" class="align-center">
					<input id="setThresholdFlag" type="hidden" value="" />
					<input id="ipt_ruleID" type="hidden" value="" />
					<input id="ipt_deviceId" type="hidden" value="" />
					<input id="ipt_volumnsId" type="hidden" value="" />
					<input id="ipt_memoriesId" type="hidden" value="" />
					<input id="ipt_networkIfId" type="hidden" value="" />
					<input id="ipt_cpusId" type="hidden" value="" />
					<input id="ipt_sourceMOID" type="hidden" value="${moid}" />
					<input id="ipt_threadPoolId" type="hidden" value="" />
					<input id="ipt_classLoadId" type="hidden" value="" />
					<input id="ipt_jdbcDSId" type="hidden" value="" />
					<input id="ipt_jdbcPoolId" type="hidden" value="" />
					<input id="ipt_memPoolId" type="hidden" value="" />
					<input id="ipt_middleJTAId" type="hidden" value="" />
					<input id="ipt_middlewareJvmId" type="hidden" value="" />
					<input id="ipt_runObjId" type="hidden" value="" />
					<input id="ipt_oracleTbsMoId" type="hidden" value="" />
					<input id="ipt_oracleRollSegMoId" type="hidden" value="" />
					<input id="ipt_oracleDataFileMoId" type="hidden" value="" />
					<input id="ipt_oracleSgaMoId" type="hidden" value="" />
					<input id="ipt_oracleDbMoId" type="hidden" value="" />
					<input id="ipt_oracleInsMoId" type="hidden" value="" />
					<input id="ipt_mysqlServerId" type="hidden" value="" />
					<input id="ipt_mysqlDBId" type="hidden" value="" />
					<input id="ipt_mysqlSysVarId" type="hidden" value="" />
					<input id="ipt_j2eeAppId" type="hidden" value="" />
					<input id="ipt_middleJMSId" type="hidden" value="" />
					<input id="ipt_webModuleId" type="hidden" value="" />
					<input id="ipt_servletId" type="hidden" value="" />
					<input id="ipt_zoneManagerId" type="hidden" value="" />
					<input id="ipt_moReadMoId" type="hidden" value="" />
					<input id="ipt_moTagMoId" type="hidden" value="" />
					<input id="ipt_db2InsMoId" type="hidden" value="" />
					<input id="ipt_db2DbMoId" type="hidden" value="" />
					<input id="ipt_db2DbMoId2" type="hidden" value="" />
					<input id="ipt_db2BufferPoolMoId" type="hidden" value="" />
					<input id="ipt_db2TbsMoId" type="hidden" value="" />
					<input id="ipt_msServerMoId" type="hidden" value="" />
					<input id="ipt_msDeviceMoId" type="hidden" value="" />
					<input id="ipt_msSQLDbMoId" type="hidden" value="" />
					<input id="ipt_sybaseServerMoId" type="hidden" value="" />
					<input id="ipt_sybaseDeviceMoId" type="hidden" value="" />
					<input id="ipt_sybaseDbMoId" type="hidden" value="" />
					<table id="tblThresholdAdd" class="formtable" style='margin:0px auto;'>
						<tr>
							<td class="title">
								指标名称：
							</td>
							<td id="tdKPI">
								<input id="ipt_kpiID" type="hidden" />
								<input id ="ipt_kpiClassID" type="hidden"/>
								<input id="ipt_kpiName" readonly="readonly" validator="{'default':'*','length':'1-64'}" />
								<b>*</b>
								<a id="btnChoseKpi" href="javascript:loadPerfKPIDef();"  style="margin-top: 5px; margin-left: -1px;">选择</a>
								<a id="btnClearKpi" href="javascript:clearPerfKPIDef();" style="margin-top: 5px; margin-left: -1px;display:none">清空</a>
							</td>
							<td class="title">源对象：</td>
							<td style="display: -ms-inline-flexbox;"><input id="sourceMOID" type="hidden" value="" />
								<input type="text" id="ipt_sourceMOName" value="${deviceip}" readonly="readonly" />
								<a id="chooseSource" style="margin-top: 5px; margin-left: -1px;" href="javascript:loadSource();">选择</a>
								<a id="btnClearSource" href="javascript:clearSource();" style="margin-top: 5px; margin-left: -1px;display:none">清空</a>
							</td>
						</tr>
						<tr>
							<td class="title" id="isShowMO1">管理对象：</td> 
							<td id="isShowMO2" colspan="3"><input id ="ipt_moID" type="hidden"/><input id="ipt_moName"  readonly="readonly" />
							<a id="btnChoseMo" href="javascript:loadMoRescource();">选择</a>
							<a id="btnClearMo" href="javascript:clearMoRescource();" style="display:none">清空</a>
							</td>
						
						</tr>
						<tr>
							<td class="title">上限阈值：</td>
							<td><input class="inputVal" id="ipt_upThreshold" validator="{'default':'num'}" onfocus="javascript:isHaveUpThreshold()"/></td>
							<td class="title">上限回归阈值：</td>
							<td><input class="inputVal" id="ipt_upRecursiveThreshold" onfocus="javascript:isUpThreshold()" validator="{'default':'num'}"/></td>
						</tr>
						<tr>
							<td class="title">下限阈值：</td>
							<td><input class="inputVal" id="ipt_downThreshold" validator="{'default':'num'}"/></td>
							<td class="title">下限回归阈值：</td>
							<td><input class="inputVal" id="ipt_downRecursiveThreshold" onfocus="javascript:isDownThreshold()" validator="{'default':'num'}"/></td>
						</tr>
						<tr>
							<td class="title">
								单位：
							</td>
							<td colspan="3">
								<textarea rows="3" class="x2" id="ipt_descr" readonly="readonly"></textarea>
							</td>
						</tr>
					</table>
				</div>
				<br/>
				<!-- 显示设备阈值信息 -->
				<h2>已配置的阈值规则</h2>
				<br/>
				<div class="datas top2">
					<table id="tblThresholdList">
					</table>
				</div>
				<div style="position:relative;height:66px">
					<div class="conditionsBtn" style="position: absolute;">
						<a href="javascript:setThreshold();" id="btnSave">确定</a>
						<a href="javascript:closeWindow();">取消</a>
					</div>
				</div>
				
			</div>

			<div id="monitorSetting" title="监测器配置" data-options="closable:false"
				style="overflow: auto;">
				<input id="collectPeriod" type="hidden"/>
				<input id="moTypeLstJson" type="hidden"/>
				<table id="tblChooseTemplate" class="formtable1">
					<tr>
						<td class="title">选择模板：</td>
						<td>
							<select name="chooseTemplate" id="ipt_templateID" onchange="chooseTemplate();">
								<option value="-1">未使用模板</option>
								<c:forEach items="${templateMap}" var="entry">
									<option value="<c:out value='${entry.key}' />"><c:out value='${entry.value}' /></option>
								</c:forEach>
							</select>	
							
						</td>
					</tr>
				</table>
				<div id="" title="监测器列表">
					<table id=monitor class="formtable">
					</table>
				</div>
				<div class="conditionsBtn">
					<a href="javascript:doSetMonitors();" id="btnSetMonitor">确定</a>
					<a href="javascript:closeWindow();">取消</a>
				</div>
			</div>

		</div>
		
	 	<div id="divMObject" class="easyui-window" maximizable="false"
		  collapsible="false" minimizable="false" closed="true" modal="true"
		  title="选择对象类型" style="width: 400px; height: 450px;">
			<div id="dataMObjectTreeDiv" class="dtree"
				style="width: 100%; height: 200px;">
			</div>
		</div>
		
		<div id="divThresholdMObject" class="easyui-window" maximizable="false"
		  collapsible="false" minimizable="false" closed="true" modal="true"
		  title="选择管理对象" style="width: 300px; height: 300px;">
			<div id="dataThresholdTreeDiv" class="dtree"
				style="width: 100%; height: 200px;">
			</div>
		</div>

	</body>
</html>
