<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="../../common/pageincluded.jsp"%>
<html>
	<head>

		<script type="text/javascript"
			src="${pageContext.request.contextPath}/js/platform/snmpcommunity/snmpcommunity.js"></script>
	</head>
	<body>
		<div class="rightContent">
			<div class="location">
				当前位置：${navigationBar}
			</div>
			<div class="conditions" id="divFilter">
				<table>
					<tr>
						<td>
							<b>别名：</b>
							<input type="text" class="inputs" id="txtFilterdeviceIP" />
							<input type="hidden" class="inputs" id="txtFiltermoID" />
						</td>
						<td class="btntd">
							<a href="javascript:void(0);" onclick="reloadTable();">查询</a>
							<a href="javascript:void(0);" onclick="resetForm('divFilter');">重置</a>

						</td>
					</tr>
				</table>
			</div>

			<!-- begin .datas -->
			<div class="datas">
				<table id="tblSNMPCommunity">

				</table>
			</div>
			<!-- end .datas -->
			<div id="divAddSNMPCommunity" class="easyui-window"
				minimizable="false" resizable="false" maximizable="false"
				closed="true" collapsible="false" modal="true" title="SNMP信息"
				style="width: 800px;">
				<input id="ipt_id" type="hidden" />
				<input id="ipt_moID" type="hidden" />
				<table id="tblSNMPCommunityInfo" class="formtable">
					<tr id="ipAndVersion">
						<td class="title">
							别名：
						</td>
						<td>
							<input id="ipt_alias" type="text" class="input" validator="{'length':'0-128'}" />
							<input id="ipt_deviceId" type="hidden" value="" />
							<!--<a href="javascript:loadDeviceInfo();" id="btnChose">选择设备</a>
						--></td>
						<td class="title">
							协议版本：
						</td>
						<td>
							<select id="ipt_snmpVersion" class="inputs"
								onclick=isOrnoCheck();>
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
						</td>
					</tr>
					<tr id="readCommunity">
						<td class="title">
							读团体名：
						</td>
						<td>
							<input id="ipt_readCommunity" type="text" validator="{'default':'*','length':'1-50'}"/><b>*</b>
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
							<select id="ipt_encryptionAlogrithm" class="inputs"
								disabled="disabled">
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
					<tr id="port">
						<td class="title">
							SNMP端口：
						</td>
						<td>
							<input id="ipt_snmpPort" type="text" value='161' validator="{'default':'*'}"/><b>*</b>
						</td>
					</tr>
				</table>
				<div class="conditionsBtn">
					<a href="javascript:void(0);" id="btnSave">确定</a>
					<a href="javascript:void(0);" id="btnUpdate">取消</a>

				</div>
			</div>
			<div id="divShowSnmpInfo" class="easyui-window" collapsible="false"
				minimizable="false" maximizable="false" closed="true" title="SNMP详情"
				style="width: 800px;">
				<table id="tblShowInfo" class="tableinfo">
					<tr>
						<td>
							<b class="title"> 别名： </b>
							<label id="lbl_alias" class="input"></label>
						</td>
						<td>
							<b class="title"> 协议版本： </b>
							<label id="lbl_snmpVersion" class="input"></label>
						</td>
					</tr>
					<tr id="readCommunitys">

						<td>
							<b class="title"> 读团体名： </b>
							<label id="lbl_readCommunity" class="input"></label>
						</td>

						<td>
							<b class="title"> 写团体名： </b>
							<label id="lbl_writeCommunity" class="input"></label>
						</td>
					</tr>
					<tr id="usmUsers" style="display: none;">

						<td>
							<b class="title"> USM用户： </b>
							<label id="lbl_usmUser" class="input"></label>
						</td>

						<td>
							<b class="title"> 上下文名称： </b>
							<label id="lbl_contexName" class="input"></label>
						</td>
					</tr>
					<tr id="authAlogrithms" style="display: none;">


						<td>
							<b class="title"> 认证类型： </b>
							<label id="lbl_authAlogrithm" class="input"></label>
						</td>

						<td>
							<b class="title"> 认证KEY： </b>
							<label id="lbl_authKey" class="input"></label>
						</td>
					</tr>

					<tr id="encryptionAlogrithms" style="display: none;">

						<td>
							<b class="title"> 加密算法： </b>
							<label id="lbl_encryptionAlogrithm" class="input"></label>
						</td>

						<td>
							<b class="title"> 加密KEY： </b>
							<label id="lbl_encryptionKey" class="input"></label>
						</td>
					</tr>
					<tr>

						<td>
							<b class="title"> SNMP端口： </b>
							<label id="lbl_snmpPort" class="input" value='161'></label>
						</td>
					</tr>
				</table>
				<div class="conditionsBtn">
					<input class="buttonB" type="button" id="btnClose2" value="关闭"
						onclick="javascript:void(0);" />
				</div>
			</div>
		</div>
	</body>
</html>