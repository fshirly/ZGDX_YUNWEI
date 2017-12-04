<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="../../common/pageincluded.jsp"%>
<html>
	<head>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/js/platform/snmpcommunity/sysauthcommunity.js"></script>
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
							<b>设备IP：</b>
							<input type="text" class="inputs" id="txtFilterdeviceIP" />
							<input type="hidden" class="inputs" id="txtFiltermoID" />
						</td>
						<td>
							<b>用户名：</b>
							<input type="text" class="inputs" id=txtFilteruserName />
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
				<table id="tblAuthCommunity">

				</table>
			</div>
			<!-- end .datas -->
			<div id="divAddAuthCommunity" class="easyui-window"
				minimizable="false" resizable="false" maximizable="false"
				closed="true" collapsible="false" modal="true" title="Telnet/SSH信息"
				style="width: 800px;">
				<input id="ipt_id" type="hidden" />
				<input id="ipt_moID" type="hidden" />
				<table id="tblAuthCommunityInfo" class="formtable">
					<tr>
						<td class="title">
							设备IP：
						</td>
						<td>
							<input id="ipt_deviceIP" class="input" validator="{'default':'*','length':'1-128'}" /><b>*</b>
							<input id="ipt_deviceId" type="hidden" value="" />
							<!--<a href="javascript:loadDeviceInfo();" id="btnChose">选择设备</a>
						--></td>
						<td class="title">
							登录方式：
						</td>
						<td>
							<select id="ipt_authType" class="inputs" onclick="isOrnoCheck();">
								<option value="1">
									Telnet
								</option>
								<option value="2">
									SSH
								</option>
							</select><b>*</b>
						</td>
					</tr>
					<tr id="readCommunity">
						<td class="title">
							端口：
						</td>
						<td>
							<input id="ipt_port" type="text" value='23' validator="{'default':'*'}"/><b>*</b>
						</td>
						<td class="title">
							登录提示符：
						</td>
						<td>
							<input id="ipt_loginPattern" type="text" value=':' validator="{'default':'*','length':'1-80'}"/>
							<b>*</b>
						</td>
					</tr>
					<tr id="readCommunitys">
						<td class="title">
							用户名：
						</td>
						<td>
							<input id="ipt_userName" type="text" validator="{'default':'*','length':'1-50'}"/>
							<b>*</b>
						</td>
						<td class="title">
							密码：
						</td>
						<td>
							<input id="ipt_password" type="password" validator="{'default':'*','length':'1-50'}"/>
							<b>*</b>
						</td>
					</tr>
					<tr id="writeCommunitys">
						<td class="title">
							特权登录提示符：
						</td>
						<td>
							<input id="ipt_enLoginPattern" value='$' />
						</td>
						<td class="title">
							特权用户名：
						</td>
						<td>
							<input id="ipt_enableUserName" type="text" value="161" />
						</td>
					</tr>
					<tr id="usmUser">
						<td class="title">
							特权口令：
						</td>
						<td>
							<input id="ipt_enablePassword" type="password" />
						</td>
					</tr>
				</table>
				<div class="conditionsBtn">
					<a href="javascript:void(0);" id="btnSave">确定</a>
					<a href="javascript:void(0);" id="btnUpdate">取消</a>

				</div>
			</div>
			<div id="divShowAuthInfo" class="easyui-window" minimizable="false"
				resizable="false" maximizable="false" closed="true"
				collapsible="false" modal="true" title="Telnet/SSH详情"
				style="width: 800px;">
				<table id="tblAuthCommunityInfo" class="tableinfo">
					<tr>

						<td>
							<b class="title">设备IP：</b>
							<label id="lbl_deviceIP" class="input"></label>
						</td>
						<td>
							<b class="title">登录方式：</b>
							<label id="lbl_authType" class="input"></label>
						</td>
					</tr>
					<tr id="readCommunity">
						<td>
							<b class="title"> 端口： </b>
							<label id="lbl_port" class="input"></label>
						</td>
						<td>
							<b class="title"> 登录提示符： </b>
							<label id="lbl_loginPattern" class="input"></label>
						</td>
					</tr>
					<tr id="readCommunitys">
						<td>
							<b class="title"> 用户名： </b>
							<label id="lbl_userName" class="input"></label>
						</td>
						<!--<td>
							<b class="title"> 登录口令： </b>
							<label id="lbl_password" class="input"></label>
						</td>
					--></tr>
					<tr id="writeCommunitys">
						<td>
							<b class="title"> 特权登录提示符： </b>
							<label id="lbl_enLoginPattern" value='$'></label>
						</td>
						<td>
							<b class="title"> 特权用户名： </b>
							<label id="lbl_enableUserName" class="input"></label>
						</td>
					</tr>
					<!--<tr id="usmUser">
						<td>
							<b class="title"> 特权口令： </b>
							<label id="lbl_enablePassword" class="input"></label>
						</td>
					</tr>
				--></table>
				<div class="conditionsBtn">
					<input class="buttonB" type="button" id="btnClose2" value="关闭"
						onclick="javascript:void(0);" />
				</div>
			</div>
		</div>
	</body>
</html>