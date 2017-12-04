<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="../../common/pageincluded.jsp"%>
<html>
	<head>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/js/platform/snmpcommunity/sysvmware.js"></script>
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
				closed="true" collapsible="false" modal="true" title="VMware信息"
				style="width: 700px;">
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

					</tr>
					<!--  <tr>
						<td class="title">
							管理域：
						</td>
						<td>
							<input id="ipt_domainID" class="input" type="hidden"/>
							<input id="ipt_domainName" type="text" readonly="readonly"  />
							<a href="javascript:loadDomianInfo();" id="btnChose">所属管理域</a>
						</td>

					</tr>
					-->
					<tr>
						<td class="title">
							端口：
						</td>
						<td>
							<input id="ipt_port" type="text" validator="{'default':'*'}"/>
							<b>*</b>
						</td>
					</tr>

					<tr id="readCommunitys">
						<td class="title">
							用户名：
						</td>
						<td>
							<input id="ipt_userName" type="text" validator="{'default':'*','length':'1-50'}" />
							<b>*</b>
						</td>

					</tr>
					<tr>
						<td class="title">
							密码：
						</td>
						<td>
							<input id="ipt_password" type="password" validator="{'default':'*','length':'1-50'}" />
							<b>*</b>
						</td>
					</tr>
				</table>
				<div class="conditionsBtn">
					<a href="javascript:void(0);" id="btnSave">确定</a>
					<a href="javascript:void(0);" id="btnUpdate">取消</a>

				</div>
			</div>
			<div id="divShowAuthInfo" class="easyui-window" collapsible="false"
				minimizable="false" maximizable="false" closed="true"
				title="VMware详情" style="width: 600px;">
				<table id="tblShowInfo" class="tableinfo1">
					<tr>

						<td>
							<b class="title"> 设备IP： </b>
							<label id="lbl_deviceIP" class="input"></label>
						</td>

					</tr>
					<tr>
						<td>
							<b class="title"> 端口： </b>
							<label id="lbl_port" class="input"></label>
						</td>
					</tr>
					<tr>
						<td>
							<b class="title"> 用户名： </b>
							<label id="lbl_userName" class="input"></label>
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