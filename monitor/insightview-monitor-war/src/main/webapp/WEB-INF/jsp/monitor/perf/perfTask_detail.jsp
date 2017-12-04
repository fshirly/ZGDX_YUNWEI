<%@ page language="java" pageEncoding="utf-8"%>

<html>
	<head>
		<link rel="stylesheet" type="text/css"
			href="${pageContext.request.contextPath}/style/css/base.css" />
		<link rel="stylesheet" type="text/css"
			href="${pageContext.request.contextPath}/style/css/reset.css" />
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

	</head>

	<body>
	<style>
      .label{
      width:500px;
        max-height: 100px;
        float: left;
        overflow: auto;
        word-wrap: break-word;
      }
       .label2{
      width:180px;
        max-height: 100px;
        float: left;
        overflow: auto;
        word-wrap: break-word;
      }
    </style>
		<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/monitor/perf/perfTask_detail.js"></script>
		<%
			String taskId =(String)request.getAttribute("taskId");
		 %>
	<div id="divTaskInfo" style="overflow:hidden;">
	<div style="overflow:auto;height: 350px;">
		<input type="hidden" id="taskId" value="<%= taskId%>"/>
		<input type="hidden" id="authType"/>
		<table id="tblViewTask" class="tableinfo">
			<tr>
				<td>
					<div style='float:left;'><b class="title">任务ID：</b></div>
					<div class="label2"><label id="lbl_taskId" class="input"></label></div>
				</td>
				<td>
					<div style='float:left;'><b class="title">设备ID：</b></div>
					<div class="label2"><label id="lbl_moId" ></label></div>
				</td>
			</tr>
			<tr id="isShowIp">
				<td>
					<div style='float:left;'><b class="title">设备IP：</b></div>
					<div class="label2"><label id="lbl_deviceIp"  class="input"></label></div>
				</td>
				<td>
					<div style='float:left;'><b class="title">所属管理域：</b></div>
					<div class="label2"><label id="lbl_domainName" class="input"></label></div>
				</td>
			</tr>
			<tr id="isShowSite">
				<td colspan="2">
					<div style='float:left;'><b class="title">站点名称：</b></div>
					<div class="label"><label id="lbl_siteName"  class="input"></label></div>
				</td>
			</tr>
			<tr id="isShowSite2">
				<td colspan="2">
					<div style='float:left;'><b class="title">监控地址：</b></div>
					<div class="label"><label id="lbl_siteUrl"  class="x2"></label></div>
				</td>
			</tr>
			<tr id="deviceInfoView">
				<td>
					<div style='float:left;'><b class="title">设备厂商：</b></div>
					<div class="label2"><label id="lbl_deviceManufacture" class="input"></label></div>
				</td>
				<td>
					<div style='float:left;'><b class="title">设备型号：</b></div>
					<div class="label2"><label id="lbl_deviceType" class="input"></label></div>
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
				<td>
					<b class="title">读团体名：</b>
					<label id="lbl_readCommunity" class="inputV1"></label>
				</td>
				<td>
					<b class="title">写团体名：</b>
					<label id="lbl_writeCommunity" class="inputV1"></label>
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
					<b class="title">读团体名：</b>
					<label id="lbl_readCommunityV3" class="inputV3"></label>
				</td>
				<td>
					<b class="title">写团体名：</b>
					<label id="lbl_writeCommunity3" class="inputV3"></label>
				</td>
			</tr>
			<tr id="row5">
				<td colspan="2">
					<b class="title">安全名：</b>
					<label id="lbl_usmUser" class="input2"></label>
				</td>
				<td>
					<b class="title">上下文名称：</b>
					<label id="lbl_contexName" class="input2"></label>
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
			<tr>
				<td colspan="2">
					<b class="title">URL：</b>
					<label id="lbl_url" rows="3" class="x2"></label>
				</td>
			</tr>
		</table>
		
		<!-- 机房环境监控凭证 -->
		<table id="tblRoomInfo" class="tableinfo" style="display: none">
			<tr>
				<td>
					<b class="title">用户名：</b>
					<label id="lbl_roomUserName" class="input"></label>
				</td>
				<td>
					<b class="title">密码：</b>
					<label id="lbl_roomPassword" class="input"></label>
				</td>
			</tr>			
			<tr>
				<td>
					<b class="title">端口：</b>
					<label id="lbl_roomPort" class="input"></label>
				</td>
				<td>&nbsp;</td>
			</tr>
		</table>
		
		<!-- ftp凭证 -->
		<table id="tblFtpInfo" class="tableinfo" style="display: none">
			<tr>
				<td>
					<b class="title">用户名：</b>
					<label id="lbl_ftpUserName" class="input"></label>
				</td>
				<td>
					<b class="title">密码：</b>
					<label id="lbl_ftpPassword" class="input"></label>
				</td>
			</tr>			
			<tr>
				<td>
					<b class="title">端口：</b>
					<label id="lbl_ftpPort" class="input"></label>
				</td>
				<td>&nbsp;</td>
			</tr>
		</table>
		
		<!-- http凭证 -->
		<table id="tblHttpInfo" class="tableinfo" style="display: none">
			<tr>
				<td>
					<b class="title">请求方式：</b>
					<label id="lbl_requestMethod" class="input"></label>
				</td>
				<td>&nbsp;</td>
			</tr>
		</table>
		
		<table id="monitorView" class="tableinfo"				>
			<tr>
				<td><b class="title">监测器信息:</b></td>
			</tr>
		</table>
		</div>
		<div class="conditionsBtn">
			<input type="button" id="btnClose" value="关闭" onclick="javascript:$('#popWin').window('close');"/>
		</div>
	</div> 
	</body>
</html>
