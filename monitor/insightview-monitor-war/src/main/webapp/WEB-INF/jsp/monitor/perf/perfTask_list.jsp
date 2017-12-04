<%@ page language="java"  pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.lang.*"  %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/style/css/base.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/css/reset.css" />
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
	src="${pageContext.request.contextPath}/style/style_one/js/main.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/monitor/perf/perfTask_list.js"></script>
<script type="text/javascript"	src="${pageContext.request.contextPath}/plugin/dTree/dtree.js"></script>
<style type="text/css">
.a3{
	width:30px;border:0;text-align:center
}
</style>
<%
	String serverId=(String)request.getAttribute("serverId");
 %>
  </head>
  
  <body>
  <div class="rightContent">
  <div class="location">当前位置：${navigationBar}</div>
  <div class="conditions" id="divFilter">
	<table>
		<tr>
			<td>
				<b>任务ID：</b>
				<input type="text" id="txtTaskId"/>
			</td>
			<td>
				<b>被采设备：</b>
				<input id="txtDeviceIp" validator="{'default':'ipAddr','length':'1-15'}"/>
			</td>
			<td>
				<b>采集机： </b>
				<input type="text" id="txtServerName"/>
			</td>
		</tr>
		<tr>
			<td>
				<b>任务状态：</b>
				<select class="easyui-combobox" id="txtTaskStatus">
					<option value="-2">请选择</option>
					<option value="0">运行中</option>
					<option value="1">已停止</option>
				</select>
			</td>
			<td>&nbsp;</td>
			
			<td class="btntd">
				<a href="javascript:void(0);"
					onclick="reloadTable();">查询</a>
				<a href="javascript:void(0);" onclick="resetForm('divFilter');"
					>重置</a>
			</td>
		</tr>
	</table>
	</div>
	<input id="serverId" type="hidden" value="<%= serverId%>"/>
    <div class="datas tops2">
		<table id="tblPerfTask">

		</table>
	</div>
	</div>
	<!-- end .datas -->
	<div id="divEditTask" class="easyui-window" collapsible="false" minimizable="false" maximizable="false"
		closed="true" modal="true" title="任务信息"
		style="width: 800px;overflow: hidden">
		<script>
		$(document).ready(function() {
		  $(".window-mask").css("height","100%");
		  $(".window-mask").css("width","100%");
		  $(".panel.window").css("top","100px");
		})
		</script>
	  <div style="overflow: auto;">
		<input id="flag" type="hidden" value="add"/>
		<input id="taskId" type="hidden" />
		<input id="ipt_taskId" type="hidden" />
		<input id="ipt_moId" type="hidden" />
		<input id="ipt_status" type="hidden" />
		<input type="hidden" id="authType"/>
		<input id="ipt_dbmsServerId" type="hidden" />
		<input id="ipt_middleWareId" type="hidden" />
		<input id="ipt_zoneManagerId" type="hidden" />
		<input id="ipt_webSiteMoID" type="hidden"/>
		<input id="collectPeriod" type="hidden"/>
		<input id="templateID" type="hidden"/>
		<input id="moTypeLstJson" type="hidden"/>
		<table id="tblEditTask" class="formtable">
			<tr id="classFlag">
				<td class="title">
					对象类型：
				</td>
				<td colspan="3">
					<input id="ipt_moClassId"  class="input" onfocus="choseMObjectTree();" validator="{'default':'*','length':'1-64'}" /><b>*</b>
				</td>
			</tr>
			
			<tr id="isShowIPTr">
				<td class="title">设备IP：</td>
				<td><input id="ipt_deviceIp" readonly class="input" validator="{'default':'*','length':'1-64'}" /><b>*</b> <input id="ipt_deviceId" type="hidden" value=""/>
						<a href="javascript:loadDeviceInfo();" id="btnChose">选择</a>
					</td>
				<td class="title">所属管理域：</td>
				<td><input id="ipt_domainName" readonly class="input"/><input id="ipt_domainId" type="hidden"/></td>
			</tr>
			<tr id="isShowSiteTr" style="display: none;">
				<td class="title">
					站点名称：
				</td>
				<td colspan="3"><input id="ipt_siteName" readonly class="input" validator="{'default':'*'}" /><b>*</b> <input id="ipt_deviceId" type="hidden" value=""/>
					<a href="javascript:loadSiteInfo();" id="btnChose2">选择</a>
				</td>
			</tr>
			<tr id="isShowSiteTr2" style="display: none;">
				<td class="title">
					监控地址：
				</td>
				<td colspan="3">
				<input id="ipt_siteUrl" readonly validator="{'default':'*'}" class="x2"/>
				</td>
			</tr>
			<tr id="hostFlag">
				<td class="title">
					所属采集机：
				</td>
				<td>
					<input type="hidden" id="ipt_oldCollectorId"  class="input"/>
					<input id="ipt_serverId"  class="input" onfocus="choseHostTree();" />
					<a id="btnUnChose" href="javascript:cancelChose();" style="display: none">清空</a>
				</td>
			</tr>
			<tr id="deviceInfo">
				<td class="title">设备厂商：</td>
				<td><input id="ipt_deviceManufacture" readonly class="input"/></td>
				<td class="title">设备型号：</td>
				<td><input id="ipt_deviceType" readonly class="input"/></td>
			</tr>
		</table>
		<!--SNMP凭证-->
		<table id="tblSNMPCommunityInfo" class="formtable" style="display: none">
			<tr>
				<td class="title">SNMP版本：</td>
				<td colspan="3">
				<input type="hidden" id="ipt_snmpVersion" readonly="readonly" disabled="disabled"/>
				<input type="text" id="ipt_snmpVersion1" readonly="readonly" disabled="disabled"/>
				<!--<input type="radio" id="ipt_snmpVersion1" name="version" onclick="javascript:edit()" value="0" checked/>&nbsp;V1
				&nbsp;
				<input type="radio" id="ipt_snmpVersion2" name="version" onclick="javascript:edit()" value="1"/>&nbsp;V2
				&nbsp;
				<input type="radio" id="ipt_snmpVersion3" name="version" onclick="javascript:edit()" value="3"/>&nbsp;V3
				&nbsp;
				--></td>
				
			</tr>
			<tr id="V1_snmp" style="display: none;">
				<td class="title">管理对象：</td>
				<td><input id="ipt_moNameV1" class="inputV1" disabled="disabled" readonly/><input id="ipt_moName" type="hidden" disabled="disabled"/></td>
				<td class="title">SNMP端口：</td>
				<td><input id="ipt_snmpPortV1"  value='161' validator="{'default':'*','length':'1-128'}" readonly="readonly" disabled="disabled"/><b>*</b><input id="ipt_snmpPort" type="hidden" /></td>
			</tr>
			<tr id="V1_readCommunity" style="display: none;">
				<td class="title">读团体名：</td>
				<td><input id="ipt_readCommunityV1"  value="public" validator="{'default':'*','length':'1-50'}" class="inputV1" readonly="readonly" disabled="disabled"/><b>*</b><input id="ipt_readCommunity" type="hidden"/></td>
				<td class="title">写团体名：</td>
				<td><input id="ipt_writeCommunityV1" validator="{'length':'0-50'}"  class="inputV1" readonly="readonly" disabled="disabled"/><input id="ipt_writeCommunity" type="hidden"/></td>
			</tr>
			<tr id="V3_snmp" style="display: none;">
				<td class="title">管理对象：</td>
				<td><input id="ipt_moNameV3" class="inputV3" readonly disabled="disabled"/></td>
				<td class="title">SNMP端口：</td>
				<td><input id="ipt_snmpPortV3"  value='161' validator="{'default':'*','length':'1-128'}" readonly="readonly" disabled="disabled"/><b>*</b></td>
			</tr>
			<tr id="V3_readCommunity" style="display: none;">
				<td class="title">读团体名：</td>
				<td><input id="ipt_readCommunityV3" value="public" validator="{'default':'*','length':'1-50'}" class="inputV3" readonly="readonly" disabled="disabled"/><b>*</b></td>
				<td class="title">写团体名：</td>
				<td><input id="ipt_writeCommunityV3" validator="{'length':'0-50'}"  class="inputV1" readonly="readonly" disabled="disabled"/></td>
				
			</tr>
			<tr id="V3_usm" style="display: none;">
				<td class="title">上下文名称：</td>
				<td><input id="ipt_contexName" class="input2" readonly="readonly" disabled="disabled"/></td>
				<td class="title">安全名：</td>
				<td><input id="ipt_usmUser" class="input2" validator="{'default':'*','length':'1-50'}" readonly="readonly" disabled="disabled"/><b>*</b></td>
			</tr>
			<tr id="V3_auth" style="display: none;">
				<td class="title">认证类型：</td>
				<td>
				
				<!--<input id="ipt_authAlogrithm" class="input2"/><b>*</b>
				<select class="easyui-combobox" id="ipt_authAlogrithm">
					<option value="-1">请选择</option>
					<option value="MD5">MD5</option>
					<option value="SHA">SHA</option>
				</select>-->
				<input id="ipt_authAlogrithm" class="input2" readonly="readonly" disabled="disabled"/>
				</td>
				<td class="title">认证KEY：</td>
				<td><input id="ipt_authKey" class="input2" readonly="readonly" disabled="disabled"/></td>
			</tr>
			<tr id="V3_encryption" style="display: none;">
				<td class="title">加密类型：</td>
				<td>
				<!--<input id="ipt_encryptionAlogrithm" class="input2"/><b>*</b>
				<select class="easyui-combobox" id="ipt_encryptionAlogrithm" disabled="disabled">
					<option value="-1">请选择</option>
					<option value="DES">DES</option>
					<option value="3DES">3DES</option>
					<option value="AES-128">AES-128</option>
					<option value="AES-192">AES-192</option>
					<option value="AES-256">AES-256</option>
				</select>-->
				<input id="ipt_encryptionAlogrithm" class="input2" readonly="readonly" disabled="disabled"/>
				</td>
				<td class="title">加密KEY：</td>
				<td><input id="ipt_encryptionKey" class="input2" readonly="readonly" disabled="disabled"/></td>
			</tr>
		</table>
		
		<!--Vmware凭证-->
		<table id="tblAuthCommunityInfo" class="formtable" style="display: none">
		    <tr>
				<td class="title">
					登录端口：
				</td>
				<td colspan="3">
					<input id="ipt_port" type="text" class="input"/>
				</td>
			</tr>

			<tr id="readCommunitys">
				<td class="title">
					用户名：
				</td>
				<td>
					<input id="ipt_userName" type="text" validator="{'default':'*','length':'1-50'}" class="input"></input><b>*</b>
				</td>

				<td class="title">
					密码：
				</td>
				<td>
					<input id="ipt_password" type="password" validator="{'default':'*','length':'1-50'}" class="input"></input><b>*</b>
				</td>
			</tr>
		</table>
		
		<!-- 数据库凭证 -->
		<table id="tblDBMSCommunity" class="formtable" style="display: none">
			<tr>
				<td class="title">
					数据库名：
				</td>
				<td>
					<input id="ipt_dbName" type="text"  validator="{'default':'*','length':'1-80'}" class="input" onblur="initDB2Community()"/>
					<b>*</b>
				</td>

				<td class="title">
					数据库类别：
				</td>
				<td>
					<input id="ipt_dbmsType" class="input" type="text" readonly/><b>*</b>
				</td>
			</tr>
			
			<tr>
				<td class="title">
					用户名：
				</td>
				<td>
					<input id="ipt_dbUserName" type="text" class="input" validator="{'default':'*','length':'1-50'}" />
					<b>*</b>
				</td>

				<td class="title">
					密码：
				</td>
				<td>
					<input id="ipt_dbPassword" type="password" class="input" validator="{'default':'*','length':'1-50'}"/>
					<b>*</b>
				</td>
			</tr>
			
			<tr>
				<td class="title">
					端口：
				</td>
				<td colspan="3">
					<input id="ipt_dbPort" class="input" type="text" validator="{'default':'*'}"/><b>*</b>
				</td>
			</tr>
		</table>
		
		<!-- 中间件认证 -->
		<table id="tblMiddleWareCommunity" class="formtable" style="display: none">
			<tr>
				<td class="title">
					中间件名称：
				</td>
				<td>
					<input id="ipt_middleWareName" class="input" type="text"  readonly/>
					<b>*</b>
				</td>

				<td class="title">
					中间件类型：
				</td>
				<td>
					<input id="ipt_middleWareType" class="input" type="text" readonly><b>*</b>
				</td>
			</tr>
			
			<tr id="isShowUserAndPwd" style="display: none">
				<td class="title">
					用户名：
				</td>
				<td>
					<input id="ipt_middleUserName" class="input" type="text" />
				</td>

				<td class="title">
					密码：
				</td>
				<td>
					<input id="ipt_middlePassword" class="input" type="password"/>
				</td>
			</tr>
			
			<tr>
				<td class="title">
					端口：
				</td>
				<td colspan="3">
					<input id="ipt_middlePort" type="text" class="input" validator="{'default':'*','length':'1-128'}"/>
				</td>
			</tr>
			<tr>
				<td class="title">控制台URL：</td>
				<td colspan="3">
					<input id="ipt_url"  type="text" class="x2"
							validator="{'default':'*','length':'1-150'}" msg="{reg:'1-150位字符！'}" onfocus="urlClick();"/>
								<b>*</b>
				</td>
			</tr>
		</table>
		
		<!-- 机房环境监控凭证 -->
		<table id="tblRoomCommunity" class="formtable" style="display: none">
			<tr>
				<td class="title">用户名：</td>
				<td>
					<input id="ipt_roomUserName" type="text" validator="{'length':'0-128'}"/>
				</td>
				<td class="title">密码：</td>
				<td>
					<input id="ipt_roomPassWord" type="password" validator="{'length':'0-128'}" />
				</td>
			</tr>
			<tr>
				<td class="title">端口：</td>
				<td colspan="3">
					<input id="ipt_roomPort" type="text" validator="{'default':'*'}" /><b>*</b>
				</td>
			</tr>
		</table>
		
		<!-- ftp凭证 -->
		<table id="tblFtpCommunity" class="formtable" style="display: none">
			<tr>
				<td class="title">用户名：</td>
				<td>
					<input id="ipt_isAuth" class="input"  type="hidden"/>
					<input id="ipt_ftpIPAddr" class="input"  type="hidden"/>
					<input id="ipt_ftpUserName" type="text" validator="{'default':'*','length':'1-128'}"/><b>*</b>
				</td>
				<td class="title">密码：</td>
				<td>
					<input id="ipt_ftpPassWord" type="password" validator="{'default':'*','length':'1-128'}" /><b>*</b>
				</td>
			</tr>
			<tr>
				<td class="title">端口：</td>
				<td colspan="3">
					<input id="ipt_ftpPort" type="text" validator="{'default':'*'}" readonly="readonly"/><b>*</b>
				</td>
			</tr>
		</table>
		
		<!-- http凭证 -->
		<table id="tblHttpCommunity" class="formtable" style="display: none">
			<tr style="float: left;">
				<td class="title">请求方式：</td>
				<td colspan="3">
					<input id="ipt_httpUrl" class="input"  type="hidden"/>
					<input id="ipt_requestMethod" class="input"  type="hidden"/>
					<input type="radio" id="ipt_requestMethod1" name="requestMethod"  value="1" checked style="width:13px">&nbsp;GET
					&nbsp;
					<input type="radio" id="ipt_requestMethod2" name="requestMethod" value="2" style="width:13px"/>&nbsp;POST
					&nbsp;
					<input type="radio" id="ipt_requestMethod3" name="requestMethod" value="3" style="width:13px"/>&nbsp;HEAD
				</td>
			</tr>
		</table>
			
		
		<table id="tblChooseTemplate" class="formtable1" style="margin-left:48px;">
			<tr>
				<td class="title">选择模板：</td>
				<td style="float: left;">
					<input class="easyui-combobox" id="ipt_templateID"/>
				</td>
			</tr>
		</table>
			
		<table id="monitor" class="formtable" style="margin-left: 111px;">
			<tr id="monitorTitle"><td class="title" style="float: left;">监测器配置</td></tr>
			<!--<tr>
			<td></td>
				<td align="left">
				<div id="divEditMo" style="float:left" style="width:120px; height: 200px;">
				</div>
				</td> 
				<td align="left">
				<div id="divEditMoInterval"  style="float:left" style="width: 90px; height: 200px;">
				</div>
				</td> 
				<td></td>
			</tr>
		--></table>
	    </div>
		<div class="conditionsBtn">
			<a href="javascript:void(0);" id="btnSave">确定</a>
			<a href="javascript:void(0);" id="btnBack">取消</a>
						
		</div>
	</div> 
	
	<div id="divMObject" class="easyui-window" maximizable="false"
		collapsible="false" minimizable="false" closed="true" modal="true"
		title="选择对象类型" style="width: 400px; height: 450px;">
		<div id="dataMObjectTreeDiv" class="dtree"
			style="width: 100%; height: 200px;">
		</div>
	</div>
	
	<div id="divHost" class="easyui-window" maximizable="false"
		collapsible="false" minimizable="false" closed="true" modal="true"
		title="选择所属采集机" style="width: 400px; height: 450px;">
		<div id="dataHostTreeDiv" class="dtree"
			style="width: 100%; height: 200px;">
		</div>
	</div>
	
		<!-- 任务详情 -->
	<div id="divTaskInfo" class="easyui-window" collapsible="false" minimizable="false" maximizable="false"
		closed="true" modal="true" title="任务详情">
		<div style="width: 800px; height:500px; overflow: auto;">
		<table id="tblViewTask" class="tableinfo">
			<tr>
				<td>
					<b class="title">任务ID：</b>
					<label id="lbl_taskId" class="input"></label>
				</td>
				<td>
					<b class="title">设备ID：</b>
					<label id="lbl_moId" ></label>
				</td>
			</tr>
			<tr>
				<td>
					<b class="title">设备IP：</b>
					<label id="lbl_deviceIp"  class="input"></label>
				</td>
				<td>
					<b class="title">所属管理域：</b>
					<label id="lbl_domainName" class="input"></label>
				</td>
			</tr>
			<tr id="deviceInfoView">
				<td>
					<b class="title">设备厂商：</b>
					<label id="lbl_deviceManufacture" class="input"></label>
				</td>
				<td>
					<b class="title">设备型号：</b>
					<label id="lbl_deviceType" class="input"></label>
				</td>
			</tr>
		</table>
		<table id="tblSnmpInfo" class="tableinfo" style="display: none">
			<tr>
				<td colspan="2">
					<b class="title">SNMP版本：</b>
					<label id="lbl_snmpVersion"></label>
				</td>
			</tr>
			<tr id="row1">
				<td>
					<b class="title">管理对象：</b>
					<label id="lbl_moName" class="inputV1" ></label>
				</td>
				<td>
					<b class="title">SNMP端口：</b>
					<label id="lbl_snmpPort" class="inputV1" ></label>
				</td>
			</tr>
			<tr id="row2">
				<td colspan="2">
					<b class="title">读团体字：</b>
					<label id="lbl_readCommunity" class="inputV1"></label>
				</td>
			</tr>
			<tr id="row3">
				<td>
					<b class="title">管理对象：</b>
					<label id="lbl_moNameV3" class="inputV3" ></label>
				</td>
				<td>
					<b class="title">SNMP端口：</b>
					<label id="lbl_snmpPortV3" class="inputV3" value='161'></label>
				</td>
			</tr>
			<tr id="row4">
				<td>
					<b class="title">读团体字：</b>
					<label id="lbl_readCommunityV3" class="inputV3"></label>
				</td>
				<td>
					<b class="title">上下文名称：</b>
					<label id="lbl_contexName" class="input2"></label>
				</td>
			</tr>
			<tr id="row5">
				<td colspan="2">
					<b class="title">安全名：</b>
					<label id="lbl_usmUser" class="input2"></label>
				</td>
			</tr>
			<tr id="row6">
				<td>
					<b class="title">认证类型：</b>
					<label id="lbl_authAlogrithm" class="input2"></label>
				</td>
				<td>
					<b class="title">认证KEY：</b>
					<label id="lbl_authKey" class="input2"></label>
				</td>
			</tr>
			<tr id="row7">
				<td>
					<b class="title">机密类型：</b>
					<label id="lbl_encryptionAlogrithm" class="input2"></label>
				</td>
				<td>
					<b class="title">加密KEY：</b>
					<label id="lbl_encryptionKey" class="input2"></label>
				</td>
			</tr>
		</table>
		
		<!--Vmware凭证-->
		<table id="tblVMwareInfo" class="tableinfo">
			<tr>
				<td>
					<b class="title">登录端口：</b>
					<label id="lbl_port" class="input"></label>
				</td>
				<td>&nbsp;</td>
			</tr>
			
			<tr>
				<td>
					<b class="title">用户名：</b>
					<label id="lbl_userName" class="input"></label>
				</td>
				<td>
					<b class="title">密码：</b>
					<label id="lbl_password" class="input"></label>
				</td>
			</tr>
		</table>
		
		<!-- 数据库凭证 -->
		<table id="tblDBMSInfo" class="tableinfo" style="display: none">
			<tr>
				<td>
					<b class="title">数据库名：</b>
					<label id="lbl_dbName" class="input"></label>
				</td>
				<td>
					<b class="title">数据库类别：</b>
					<label id="lbl_dbmsType" class="input"></label>
				</td>
			</tr>	
			<tr>
				<td>
					<b class="title">用户名：</b>
					<label id="lbl_dbUserName" class="input"></label>
				</td>
				<td>
					<b class="title">密码：</b>
					<label id="lbl_dbPassword" class="input"></label>
				</td>
			</tr>			
			<tr>
				<td>
					<b class="title">登录端口：</b>
					<label id="lbl_dbPort" class="input"></label>
				</td>
				<td>&nbsp;</td>
			</tr>
		</table>
		
		<!-- 中间件凭证 -->
		<table id="tblMiddleWareInfo" class="tableinfo" style="display: none">
			<tr>
				<td>
					<b class="title">中间件名称:</b>
					<label id="lbl_middleWareName" class="input"></label>
				</td>
				<td>
					<b class="title">中间件类型：</b>
					<label id="lbl_middleWareType" class="input"></label>
				</td>
			</tr>	
			<tr>
				<td>
					<b class="title">用户名：</b>
					<label id="lbl_middleUserName" class="input"></label>
				</td>
				<td>
					<b class="title">密码：</b>
					<label id="lbl_middlePassword" class="input"></label>
				</td>
			</tr>			
			<tr>
				<td>
					<b class="title">端口：</b>
					<label id="lbl_middlePort" class="input"></label>
				</td>
				<td>&nbsp;</td>
			</tr>
		</table>
		
		<table id="monitorView" class="tableinfo">
			<tr>
				<td><b class="title">监测器信息:</b></td>
			</tr>
		</table>
		</div>
		<div class="conditionsBtn">
			<a href="javascript:void(0);" id="btnBack2">关闭</a>
						
		</div>
	</div> 
	
  </body>
</html>
