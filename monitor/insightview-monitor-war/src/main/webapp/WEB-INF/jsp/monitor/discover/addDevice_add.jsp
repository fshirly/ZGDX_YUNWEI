<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file = "../../common/pageincluded.jsp" %>

<html>
	<head>
		<script type="text/javascript"	src="${pageContext.request.contextPath}/plugin/dTree/dtree.js"></script>
	</head> 
	<body> 
		<script type="text/javascript"	src="${pageContext.request.contextPath}/js/monitor/discover/addDevice_add.js"></script>
		  <%
			String flag =(String)request.getAttribute("flag");
		 %>
		  <div id="addDevice" style="height: 400px;overflow: auto">
		  <input type="hidden" id="flag" value="<%= flag%>"/>
		      <input type="hidden" id="authType">
		      <input type="hidden" id="className">
		      <input type="hidden" id="isExistSNMP">
		      <input type="hidden" id="isExsitVmware">
		      <input type="hidden" id="isExistDB">
		      <input type="hidden" id="isExistMiddle">
		      <input type="hidden" id="isExistRoom">
		      <input type="hidden" id="isExistSite">
		      <input type="hidden" id="siteCommunityId" />
		      <input id="moTypeLstJson" type="hidden"/>
		      <div>
				<table id="tblAddDevice" class="formtable">
					<tr id="objTypeTr">
						<td class="title">
							对象类型：
						</td>
						<td>
							<input id="ipt_classID"  readonly onfocus="choseMObjectTree();" validator="{'default':'*','length':'1-64'}" /><b>*</b>
						</td>
						<td class="title" id="ipTilte">
							对象IP：
						</td>
						<td id="ipInput">
							<input id="ipt_deviceIP" class="input" validator="{'default':'*','length':'1-64'}" onfocus="isClass();" onblur="isDiscovered();"/><b>*</b>
							<input id="ipt_deviceId" type="hidden" value="" />
							<!--<a href="javascript:loadDeviceInfo();" id="btnChose">选择</a>
						--></td>
					</tr>
					
					<tr>
						<td id="authsTitle" class="title" style="display: none">
							认证信息：
						</td>
						<td id="auths" colspan="3" >
							<label id="snmpAuth"  style="display: none">SNMP凭证 </label> 
							
							<label id="dbAuth"  style="display: none">数据库凭证</label>
							<label id="vmwareAuth"  style="display: none">Vmware凭证</label>
							<label id="middleAuth"  style="display: none">JMX凭证</label>
							<label id="roomAuth"  style="display: none">机房环境监控凭证</label>
						</td>
					</tr>
				</table>
			</div>	
			<div id="viewSnmp" class="winbox">
				<div class="datas">
					<table id="tblSNMPCommunity"></table>
				</div>
				
			</div>
				
			<div>
				<!--Vmware凭证-->
				<table id="tblAuthCommunityInfo" class="formtable" style="display: none">
				    <tr>
						<td class="title">
							登录端口：
						</td>
						<td colspan="3">
							<input id="ipt_port" type="text" />
						</td>
					</tr>

					<tr id="readCommunitys">
						<td class="title">
							用户名：
						</td>
						<td>
							<input id="ipt_userName" type="text" validator="{'default':'*','length':'1-30'}"></input><b>*</b>
						</td>

						<td class="title">
							密码：
						</td>
						<td>
							<input id="ipt_password" type="password" validator="{'default':'*','length':'1-30'}"></input><b>*</b>
						</td>
					</tr>
				</table>
			
			</div>
			
			<div>	
			<input id="db2CommunityId" type="hidden"/>
				<!-- 数据库凭证 -->
				<table id="tblDBMSCommunity" class="formtable" style="display: none">
					<tr id="isMysql" style="display: none">
						<td class="title">
							数据库类别：
						</td>
						<td colspan="3">
							<input id="ipt_mysqlDbmsType" type="text" readonly>
							<b>*</b>
						</td>
					</tr>
					
					<tr id="isOracle">
						<td class="title">
							数据库名：
						</td>
						<td>
							<input id="ipt_dbName" type="text"  validator="{'default':'*','length':'1-80'}" onblur="initDB2Community()"/>
							<b>*</b>
						</td>

						<td class="title">
							数据库类别：
						</td>
						<td>
							<input id="ipt_dbmsType" type="text" readonly>
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
						<td colspan="3">
							<input id="ipt_dbPort" type="text" onchange="resetIP()" onblur="checkPort(3)"/>
						</td>
					</tr>
				</table>
			</div>
			
			<div>	
				<!-- JMX凭证 -->
				<table id="tblMiddlewareCommunity" class="formtable" style="display: none">
					<tr id="isShowUserAndPwd" style="display: none">
						<td class="title">用户名：</td>
						<td>
							<input id="ipt_middleUserName" type="text" validator="{'length':'0-255'}"/>
						</td>
						<td class="title">密码：</td>
						<td>
							<input id="ipt_middlePassWord" type="password" validator="{'length':'0-255'}" />
						</td>
					</tr>
					<tr id="middlePortTr">
						<td class="title">端口：</td>
						<td colspan="3">
							<input id="ipt_middlePort" type="text" validator="{'default':'*' ,'length':'1-128'}" onchange="resetIP()" onblur="checkPort(4)"/><b>*</b>
						</td>
					</tr>
					<tr>
						<td class="title">控制台URL：</td>
						<td colspan="3">
							<input id="ipt_url"  type="text" class="x2"	validator="{'default':'*','length':'1-255'}"/><b>*</b>
						</td>
					</tr>
				</table>
			</div>
			
			<div>	
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
							<input id="ipt_roomPort" type="text" validator="{'default':'*'}" onchange="resetIP()" onblur="checkPort(5)"/><b>*</b>
						</td>
					</tr>
				</table>
			</div>
			
			<div style="margin-left: -5px;">	
				<!--http监控 -->
				<table id="tblSiteHttp" class="formtable" style="display: none">
					<tr>
						<td class="title">站点名称：</td>
						<td colspan="3">
							<input id="ipt_httpSiteName" type="text" class="x2" validator="{'length':'1-40'}" /><b>*</b>
						</td>
					</tr>
					<tr>
						<td class="title">监控地址：</td>
						<td colspan="3">
							<input id="ipt_httpUrl" type="text"  class="x2" validator="{'default':'url','length':'1-5000'}" onblur="initHttpCommunity();"/><b>*</b>
						</td>
					</tr>
					<%--<tr>	
						<td class="title">监控周期：</td>
						<td colspan="3">
							<input id="ipt_httpPeriod" type="text" validator="{'default':'checkEmpty_ptInteger'}"/>&nbsp;分钟&nbsp;<b>*</b>
						</td>
					</tr>
					--%><tr>
						<td class="title">请求方式：</td>
						<td colspan="3">
							<input id="ipt_requestMethod" class="input"  type="hidden"/>
							<input type="radio" id="ipt_requestMethod1" name="requestMethod"  value="1" checked style="width:13px">&nbsp;GET
							&nbsp;
							<input type="radio" id="ipt_requestMethod2" name="requestMethod" value="2" style="width:13px"/>&nbsp;POST
							&nbsp;
							<input type="radio" id="ipt_requestMethod3" name="requestMethod" value="3" style="width:13px"/>&nbsp;HEAD
						</td>
					</tr>
					<tr>
						<td class="title">站点说明：</td>
						<td colspan="3">
							<textarea rows="3" id="ipt_httpSiteInfo"  class="x2" validator="{'length':'0-500'}"></textarea>
						</td>
					</tr>
				</table>
			</div>
			
			<div style="margin-left: -5px;">	
				<!--DNS监控 -->
				<table id="tblSiteDNS" class="formtable" style="display: none;">
					<tr>
						<td class="title">站点名称：</td>
						<td colspan="3">
							<input id="ipt_dnsSiteName" class="x2"  type="text" validator="{'length':'1-40'}" /><b>*</b>
						</td>
					</tr>
					<tr>
						<td class="title">监控地址：</td>
						<td colspan="3">
							<input id="ipt_domainName" type="text" class="x2" validator="{'default':'domain','length':'1-128'}"/><b>*</b>
						</td>
					</tr>
					<%--<tr>
						<td class="title">监控周期：</td>
						<td colspan="3">
							<input id="ipt_dnsPeriod" type="text" validator="{'default':'checkEmpty_ptInteger'}"/>&nbsp;分钟&nbsp;<b>*</b>
						</td>
					</tr>
					--%><tr>
						<td class="title">期望解析IP：</td>
						<td colspan="3">
							<input id="ipt_dnsIPAddr" type="text" validator="{'default':'ipAddr','length':'0-128'}" />
						</td>
					</tr>
					<tr>
						<td class="title">站点说明：</td>
						<td colspan="3">
						    <textarea rows="3" id="ipt_dnsSiteInfo"  class="x2" validator="{'length':'0-500'}"></textarea>
						</td>
					</tr>
				</table>
			</div>
			
			<div style="margin-left: -15px;">	
				
				<!--FTP监控 -->
				<table id="tblSiteFTP" class="formtable" style="display: none">
					<tr>
						<td class="title">站点名称：</td>
						<td colspan="3">
							<input id="ipt_ftpSiteName" class="x2" type="text" validator="{'length':'1-40'}" /><b>*</b>
						</td>
					</tr>
					<tr>	
						<td class="title">监控地址：</td>
						<td colspan="3">
							<input id="ipt_ftpIPAddr" class="x2" type="text" validator="{'default':'ipAddr','length':'1-128'}" onblur="initFTPCommunity();"/><b>*</b>
						</td>
					</tr>
					<tr>
						<td class="title">FTP端口号：</td>
						<td colspan="3">
							<input id="ipt_ftpPort" type="text" validator="{'default':'checkEmpty_ptInteger'}"/><b>*</b>
						</td>
					</tr><%--
					<tr>
						<td class="title">监控周期：</td>
						<td colspan="3">
							<input id="ipt_ftpPeriod" type="text" validator="{'default':'checkEmpty_ptInteger'}"/>&nbsp;分钟&nbsp;<b>*</b>
						</td>
					</tr>
					--%><tr>
						<td class="title">站点说明：</td>
						<td colspan="3">
						    <textarea rows="3" id="ipt_ftpSiteInfo"  class="x2" validator="{'length':'0-500'}"></textarea>
						</td>
					</tr>
					<tr>
						<td class="title">FTP身份验证选项：</td>
						<td colspan="3">
							<input id="ipt_isAuth" class="input"  type="hidden"/>
							<input type="radio" id="ipt_isAuth1" name="isAuth"  value="1" checked style="width:13px" onclick="javascript:edit();">&nbsp;匿名登录
							&nbsp;
							<input type="radio" id="ipt_isAuth2" name="isAuth" value="2" style="width:13px" onclick="javascript:edit();"/>&nbsp;需要身份验证
						</td>
					</tr>
				</table>
				<table id="isShowFtpcom"  class="formtable"  style="display: none">
					<tr>
						<td class="title">用户名：</td>
						<td>
							<input id="ipt_ftpUserName" type="text" validator="{'default':'*','length':'1-128'}"/><b>*</b>
						</td>
						<td class="title">密码：</td>
						<td>
							<input id="ipt_ftpPassWord" type="password" validator="{'default':'*','length':'1-128'}" /><b>*</b>
						</td>
					</tr>
				</table>
			</div>
			
			<!--TCP监控 -->
			<div style="margin-left: -7px;">
			<table id="tblSiteTcp" class="formtable" style="display: none">
				<tr>
					<td class="title">站点名称：</td>
					<td colspan="3">
						<input id="ipt_tcpSiteName" class="x2" type="text" validator="{'length':'1-40'}" /><b>*</b>
					</td>
				</tr>
				<tr>	
					<td class="title">监控地址：</td>
					<td colspan="3">
						<input id="ipt_tcpIPAddr" class="x2" type="text" validator="{'default':'ipAddr','length':'1-128'}" onblur="initFTPCommunity();"/><b>*</b>
					</td>
				</tr>
				<tr>
					<td class="title">端口号：</td>
					<td colspan="3">
						<input id="ipt_tcpPort" type="text" validator="{'default':'checkEmpty_ptInteger'}"  class="input"/><b>*</b>
					</td>
				</tr>
				<tr>
					<td class="title">站点说明：</td>
					<td colspan="3">
					    <textarea rows="3" id="ipt_tcpSiteInfo"  class="x2" validator="{'length':'0-500'}"></textarea>
					</td>
				</tr>
			</table>
			</div>
			
			<div id= "testDiv" style="display: none">
			 <div id="testBtn" class ="btntd" style="float: right; margin-right: 102px;">
				<a class="btntd" onclick="javascript:getTestSite();">测试</a>
			  </div>
			 <div id="sucessTip"  style="padding-top: 35px; text-align: center; display: none">
				 <img src="${pageContext.request.contextPath}/style/images/no_repeat.gif" />&nbsp;&nbsp;测试成功！
			 </div>
			 <div id="errorTip"  style="padding-top: 35px; text-align: center; display: none">
			 	 <img src="${pageContext.request.contextPath}/style/images/alarm/delete.png" />
				&nbsp;&nbsp; 测试失败，请检查网络是否连通或参数配置是否正确！
			 </div>
			</div>
			
			<table id="tblChooseTemplate" class="formtable1" style="margin-left:33px;margin-top: 15px;">
				<tr>
					<td class="title">选择模板：</td>
					<td style="float: left;">
						<input class="easyui-combobox" id="ipt_templateID"/>
					</td>
				</tr>
			</table>
			
			<table id="monitor" class="formtable" style="margin-left: 99px;">
				<tr id="monitorTitle"><td class="title" style="float: left;">监测器配置</td></tr>
			</table>
			
			<div class="conditionsBtn">
				<input type="button" id="btnSave" value="确定" onclick="javascript:toAdd();"/>
				<input type="button" id="btnClose" value="取消" onclick="javascript:$('#popWin').window('close');"/>
			</div>
		</div>
			
			<div id="divAddSnmp" class="easyui-window" minimizable="false"
				closed="true" maximizable="false" collapsible="false" modal="true"
				title="新增SNMP凭证" style="width: 800px;">
				<!--SNMP凭证-->
				<table id="tblSNMPCommunityInfo" class="formtable">
				  <tr>
				  	<td class="title">
							别名：
						</td>
						<td>
							<input id="ipt_alias" type="text" class="input" validator="{'length':'0-128'}" />
						</td>
				  
				    <td class="title">
							协议版本：
					</td>
					<td>
						<select id="ipt_snmpVersion" class="inputs"	onChange=isOrnoCheck(); >
							<option value="0">
								V1
							</option>
							<option value="1">
								V2
							</option>
							<option value="3">
								V3
							</option>
						</select><b>*</b>
				  </tr>
				
				  <tr id="readCommunity">
						<td class="title">
							读团体名：
						</td>
						<td>
							<input id="ipt_readCommunity" type="text"  validator="{'default':'*','length':'1-50'}"/><b>*</b>
						</td>
						
						<td class="title">
							写团体名：
						</td>
						<td>
							<input id="ipt_writeCommunity" type="text" />
						</td>
					</tr>
					<tr id="usmUser" style="display: none;">
						<td class="title">
							USM用户：
						</td>
						<td>
							<input id="ipt_usmUser" type="text" validator="{'default':'*','length':'1-50'}"/><b>*</b>
						</td>
						<td class="title">
							上下文名称：
						</td>
						<td>
							<input id="ipt_contexName" type="text" />
						</td>
					</tr>
					<tr id="authAlogrithm" style="display: none;">
						<td class="title">
							认证类型：
						</td>
						<td>
							<select id="ipt_authAlogrithm" class="inputs">

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
						</td>
						<td class="title">
							认证KEY：
						</td>
						<td>
							<input id="ipt_authKey" type="text" />
						</td>
					</tr>

					<tr id="encryptionAlogrithm" style="display: none;">
						<td class="title">
							加密算法：
						</td>
						<td>
							<select id="ipt_encryptionAlogrithm" class="inputs">
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
						</td>
						<td class="title">
							加密KEY：
						</td>
						<td>
							<input id="ipt_encryptionKey" type="text" />
						</td>
					</tr>
					<tr id="snmpPort">
						<td class="title">
							SNMP端口：
						</td>
						<td>
							<input id="ipt_snmpPort" type="text" value='161' validator="{'default':'*','length':'1-30'}"></input><b>*</b>
						</td>
					</tr>
				</table>
				<div class="conditionsBtn">
					<a href="javascript:void(0);" id="btnAddSnmpNow" class="fltrt">确定</a>
					<a href="javascript:void(0);" id="btnColseSnmpNow" class="fltrt">取消</a>
				</div>
			</div>
			
		  
			<div id="divMObject" class="easyui-window" maximizable="false"
			collapsible="false" minimizable="false" closed="true" modal="true"
			title="选择对象类型" style="width: 400px; height: 450px;">
				<div id="dataMObjectTreeDiv" class="dtree"
					style="width: 100%; height: 200px;">
				</div>
			</div>
		
	</body>
</html>