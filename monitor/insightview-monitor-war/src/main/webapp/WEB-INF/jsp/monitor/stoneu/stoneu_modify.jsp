<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file = "../../common/pageincluded.jsp" %>

<html>

	<body>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/monitor/stoneu/stoneu_modify.js"></script>
		<div id="divWebsiteAdd">
			<input id="moid" type="hidden" value="${stoneu.MOid}">
			<input id="moClassID" type="hidden" value="${stoneu.MOClassID}">
			<input id="hiddenTemplateID" type="hidden" value="${stoneu.templateID}">
			<input id="moName" type="hidden" value="${stoneu.moName}">
			<input id="readComm" type="hidden" value="${stoneu.SNMPbean.readCommunity}">
			<input id="port" type="hidden" value="${stoneu.SNMPbean.snmpPort}">
			<input id="taskid" type="hidden" value="${stoneu.taskId}">
			<input id="moTypeLstJson" type="hidden"/>
			<input id="collectPeriod" type="hidden"/>
			<input id="manufacturerID" type="hidden"/>
			<input id="deviceIp" type="hidden" value="${stoneu.deviceIp}"/>
			<input id="domainID" type="hidden" value="${stoneu.domainid}"/>
			<input id="snmpID" type="hidden"  value="${stoneu.SNMPbean.ID}">
		<form id="ipt_acUpsFrom">
			<table id="acInfo" class="formtable" >
				<tr>
					<td class="title">设备IP</td>
					<td>
						<input  id="ipt_deviceIP" class="input"  value="${stoneu.deviceIp}" validator="{'default':'checkEmpty_ipAddr','length':'0-128'}"/><b>*</b>
					</td>
				</tr>
			</table>
						
				<!--SNMP凭证-->
				<table id="tblSNMPCommunityInfo" class="formtable">
				  <tr>
				  
				    <td class="title"> 协议版本： </td>
					<td>
						<input id="ipt_snmpVersion" type="hidden"  value="${stoneu.SNMPbean.snmpVersions}"/>
						<input id="ipt_snmpVersionName" class="input" readonly="readonly"/>
						 <b>*</b>
				  </tr>
				
				  <tr id="readCommunity">
						<td class="title"> 读团体名： </td>
						<td>
							<input id="ipt_readCommunity" type="text" value="${stoneu.SNMPbean.readCommunity}" validator="{'default':'*','length':'1-50'}"/><b>*</b>
						</td>
					</tr>
					
					<tr id="usmUser" style="display: none;">
						<td class="title"> USM用户： </td>
						<td>
							<input id="ipt_usmUser" type="text"  value="${stoneu.SNMPbean.USMUser}" validator="{'default':'*','length':'1-50'}"/><b>*</b>
						</td>
						<td class="title"> 上下文名称： </td>
						<td>
							<input id="ipt_contexName" type="text"  value="${stoneu.SNMPbean.contexName}"/>
						</td>
					</tr>
					<tr id="authAlogrithm" style="display: none;">
						<td class="title"> 认证类型： </td>
						<td>
						<input id="ipt_authAlogrithm"  value="${stoneu.SNMPbean.authAlogrithm}"/>
							 </td>
						<td class="title"> 认证KEY： </td>
						<td>
							<input id="ipt_authKey" type="text" value="${stoneu.SNMPbean.authKey}"/>
						</td>
					</tr>

					<tr id="encryptionAlogrithm" style="display: none;">
						<td class="title"> 加密算法： </td>
						<td>
						<input id="ipt_encryptionAlogrithm"  value="${stoneu.SNMPbean.encryptionAlogrithm}">
							 </td>
						<td class="title"> 加密KEY： </td>
						<td>
							<input id="ipt_encryptionKey" type="text" value="${stoneu.SNMPbean.encryptionKey}"/>
						</td>
					</tr>
					<tr id="snmpPort">
						<td class="title"> SNMP端口： </td>
						<td>
							<input id="ipt_snmpPort" type="text" value="${stoneu.SNMPbean.snmpPort}" validator="{'default':'*','length':'1-30'}"></input><b>*</b>
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
