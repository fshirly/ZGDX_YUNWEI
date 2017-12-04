<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file = "../../common/pageincluded.jsp" %>

<html>

	<body>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/monitor/acUPS/acUPS_modify.js"></script>
		<div id="divWebsiteAdd"  style="height: 450px;overflow: auto;">
		<input id="moid" type="hidden" value="${airCon.moID}">
		<input id="hiddenTemplateID" type="hidden" value="${airCon.templateID}">
		<input id="hiddenIp" type="hidden" value="${airCon.deviceIP}">
		<input id="moName" type="hidden" value="${airCon.moName}">
		<input id="neManufacturerID" type="hidden" value="${airCon.neManufacturerID}">
		<input id="domainID" type="hidden" value="${airCon.domainID}">
		<input id="readComm" type="hidden" value="${airCon.SNMPbean.readCommunity}">
		<input id="writeComm" type="hidden" value="${airCon.SNMPbean.writeCommunity}">
		<input id="port" type="hidden" value="${airCon.SNMPbean.snmpPort}">
		<input id="className" type="hidden" value="${airCon.SNMPbean.snmpPort}">
		<input id="taskId" type="hidden" value="${airCon.taskId}">
		 <input id="moTypeLstJson" type="hidden"/>
		 <input id="collectPeriod" type="hidden"/>
		<form id="ipt_acUpsFrom">
			<table id="acInfo" class="formtable" >
				<tr>
					<td class="title">监控类型：</td>
					<td>
						<input type="hidden" id="ipt_moClassID" value="${airCon.moClassID}" />
						<input id="ipt_moClassName" type="text" readonly="readonly"/>
						<b>*</b>
					</td>
					<td class="title">设备IP</td>
					<td>
						<input  id="ipt_deviceIP" class="input"  value="${airCon.deviceIP}" validator="{'default':'checkEmpty_ipAddr','length':'0-128'}"/><b>*</b>
					</td>
				</tr>
			</table>
						
				<!--SNMP凭证-->
				<table id="tblSNMPCommunityInfo" class="formtable">
				  <tr>
				  	<td class="title">别名： </td>
					<td>
						<input id="ipt_alias" type="text"  value="${airCon.mOAlias}"  class="input" validator="{'length':'0-128'}" />
					</td>
				  
				    <td class="title"> 协议版本： </td>
					<td>
						<input id="ipt_snmpVersion" type="hidden"  value="${airCon.SNMPbean.snmpVersions}"/>
						<input id="ipt_snmpVersionName" class="input" readonly="readonly"/>
						 <b>*</b>
				  </tr>
				
				  <tr id="readCommunity">
						<td class="title"> 读团体名： </td>
						<td>
							<input id="ipt_readCommunity" type="text" value="${airCon.SNMPbean.readCommunity}" validator="{'default':'*','length':'1-50'}"/><b>*</b>
						</td>
						<td class="title"> 写团体名： </td>
						<td>
							<input id="ipt_writeCommunity" value="${airCon.SNMPbean.writeCommunity}"  type="text" />
						</td>
					</tr>
					
					<tr id="usmUser" style="display: none;">
						<td class="title"> USM用户： </td>
						<td>
							<input id="ipt_usmUser" type="text"  value="${airCon.SNMPbean.USMUser}" validator="{'default':'*','length':'1-50'}"/><b>*</b>
						</td>
						<td class="title"> 上下文名称： </td>
						<td>
							<input id="ipt_contexName" type="text"  value="${airCon.SNMPbean.contexName}"/>
						</td>
					</tr>
					<tr id="authAlogrithm" style="display: none;">
						<td class="title"> 认证类型： </td>
						<td>
						<input id="ipt_authAlogrithm"  value="${airCon.SNMPbean.authAlogrithm}"/>
							 </td>
						<td class="title"> 认证KEY： </td>
						<td>
							<input id="ipt_authKey" type="text" value="${airCon.SNMPbean.authKey}"/>
						</td>
					</tr>

					<tr id="encryptionAlogrithm" style="display: none;">
						<td class="title"> 加密算法： </td>
						<td>
						<input id="ipt_encryptionAlogrithm"  value="${airCon.SNMPbean.encryptionAlogrithm}">
							 </td>
						<td class="title"> 加密KEY： </td>
						<td>
							<input id="ipt_encryptionKey" type="text" value="${airCon.SNMPbean.encryptionKey}"/>
						</td>
					</tr>
					<tr id="snmpPort">
						<td class="title"> SNMP端口： </td>
						<td>
							<input id="ipt_snmpPort" type="text" value="${airCon.SNMPbean.snmpPort}" validator="{'default':'*','length':'1-30'}"></input><b>*</b>
						</td>
					</tr>
				</table>
			
			<div id= "testDiv" style="display: none">
			 <div id="testBtn" class ="btntd" style="float: right; margin-right: 102px;">
				<a class="btntd" onclick="javascript:getTestCondition();">测试</a>
			  </div>
			 <div id="sucessTip"  style=" padding-top: 40px; text-align: center; display: none">
				 <img src="${pageContext.request.contextPath}/style/images/no_repeat.gif" />
				 <span>&nbsp;&nbsp;测试成功!</span>
			 </div>
			 <div id="errorTip"  style=" padding-top: 40px; text-align: center; display: none">
			 	 <img src="${pageContext.request.contextPath}/style/images/alarm/delete.png" />
				 <span>&nbsp;&nbsp;测试失败，请检查网络是否连通或参数配置是否正确!</span>
			 </div>
			</div>
			
			<table id="tblChooseTemplate" class="formtable1" style="margin-left:48px;margin-top: 15px;">
				<tr>
					<td class="title">选择模板：</td>
					<td style="float: left;">
						<input class="easyui-combobox" id="ipt_templateID"/>
					</td>
				</tr>
			</table>
			<table id="monitor" class="formtable" style="margin-left: 111px;">
			<tr id="monitorTitle"><td class="title" style="float: left;">监测器配置</td></tr>
			</table>
			</form>
			<div class="conditionsBtn">
				<input type="button" id="btnSave" value="确定" onclick="javascript:updateAcdition();"/>
				<input type="button" id="btnClose" value="取消" onclick="javascript:$('#popWin').window('close');"/>
			</div>
		</div>
	</body>
</html>
