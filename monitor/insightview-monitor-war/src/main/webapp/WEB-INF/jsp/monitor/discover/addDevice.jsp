<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file = "../../common/pageincluded.jsp" %>

<html>
	<head>
		<script type="text/javascript"	src="${pageContext.request.contextPath}/plugin/dTree/dtree.js"></script>
	</head> 
	<body> 
		<script type="text/javascript"	src="${pageContext.request.contextPath}/js/monitor/discover/addDevice.js"></script>
	    <div class="rightContent">
		  <div class="location">当前位置：系统管理&gt;&gt;运维平台 &gt;&gt;监测对象 &gt;&gt; <span>新增监测对象</span></div>
		  <div class="easyui-window" id="addDevice" minimizable="false"  resizable="false" maximizable="false" closed="false" 
		      closable="false" collapsible="false" modal="true" title="新增监测对象" style="width:800px; height:400px">
		      <input type="hidden" id="authType">
				<table id="tblAddDevice" class="formtable">
					<tr>
						<td class="title">
							对象类型：
						</td>
						<td>
							<input id="ipt_classID"  onfocus="choseMObjectTree();" validator="{'default':'*','length':'1-64'}" /><b>*</b>
						</td>
						<td class="title">
							对象IP：
						</td>
						<td >
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
							<label id="snmpAuth"  style="display: none">SNMP凭证</label>
							<label id="dbAuth"  style="display: none">数据库凭证</label>
							<label id="vmwareAuth"  style="display: none">Vmware凭证</label>
						</td>
					</tr>
				</table>
				<!--SNMP凭证-->
				<table id="tblSNMPCommunityInfo" class="formtable" style="display: none">
				  <tr>
				    <td class="title">
							协议版本：
					</td>
					<td colspan="3">
						<select id="ipt_snmpVersion" class="inputs"	onclick=isOrnoCheck(); >
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
							读Community：
						</td>
						<td>
							<input id="ipt_readCommunity" type="text" />
						</td>
						
						<td class="title">
							写Community：
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
							<input id="ipt_usmUser" type="text" />
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
					<tr>
						<td class="title">
							SNMP端口：
						</td>
						<td>
							<input id="ipt_snmpPort" type="text" value='161' validator="{'default':'*','length':'1-30'}"></input><b>*</b>
						</td>
					</tr>
				</table>
				
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
				
				<!-- 数据库凭证 -->
				<table id="tblDBMSCommunity" class="formtable" style="display: none">
					<tr>
						<td class="title">
							数据库名：
						</td>
						<td>
							<input id="ipt_dbName" type="text"  validator="{'default':'*','length':'1-80'}"/>
							<b>*</b>
						</td>

						<td class="title">
							数据库类别：
						</td>
						<td>
							<input id="ipt_dbmsType" type="text" readonly>
							<!--<select id="ipt_dbmsType" class="inputs"  >
								<option value="mysql">
									mysql
								</option>
								<option value="oracle">
									oracle
								</option>
							</select>-->
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
							<input id="ipt_dbPort" type="text" />
						</td>
					</tr>
				</table>
				<div class="conditionsBtn">
				<a  onclick="toAdd();">确定</a>
				</div>
			</div>
		  
		</div>
		<div id="divMObject" class="easyui-window" maximizable="false"
		collapsible="false" minimizable="false" closed="true" modal="true"
		title="选择对象类型" style="width: 300px; height: 300px;">
			<div id="dataMObjectTreeDiv" class="dtree"
				style="width: 100%; height: 200px;">
			</div>
		</div>
	</body>
</html>