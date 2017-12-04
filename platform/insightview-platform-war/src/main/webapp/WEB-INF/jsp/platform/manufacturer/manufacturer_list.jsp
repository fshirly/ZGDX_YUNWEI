<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../common/pageincluded.jsp"%>


<html>
	<head>
		<title>厂商列表</title>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/js/platform/manufacturer/manufacturer.js"></script>
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
							<b>厂商名称：</b>
							<input type="text" id="txtFilterManufacturerName" />
						</td>
						<td class="btntd">
							<a href="javascript:reloadTable();">查询</a>
							<a href="javascript:resetForm('divFilter');">重置</a>
						</td>
					</tr>
				</table>
			</div>

			<!-- begin .datas -->
			<!-- 从js中读取数据 -->
			<div class="datas">
				<table id="tblManufacturer">
				</table>
			</div>

			<!-- end .datas -->
			<div id="divAddManufacturer" class="easyui-window"	minimizable="false" maximizable="false" collapsible="false"
				closed="true" modal="true" title="厂商信息" style="width: 800px;">
				<input id="ipt_resManuFacturerId" type="hidden" />

				<table id="tblAddManufacturer" class="formtable">
					<tr>
						<td class="title">
							厂商名称：
						</td>
						<td>
							<input type="text" id="ipt_resManuFacturerName"	validator="{'default':'*','length':'1-128'}"/><b>*</b>
						</td>
						<td class="title">
							厂商别名：
						</td>
						<td>
							<input type="text" id="ipt_resManuFacturerAlias" validator="{'length':'0-128'}"/>
						</td>
					</tr>
					<tr>
						<td class="title">
							联系人：
						</td>
						<td>
							<input type="text" id="ipt_contacts" validator="{'length':'0-80'}"/>
						</td>
						<td class="title">
							联系电话：
						</td>
						<td>
							<input  type="text" id="ipt_contactsTelCode" name="ipt_contactsTelCode" validator="{'default':'phoneAndTelNum'}"/>
						</td>
					</tr>
					<tr>
						<td class="title">
							传真：
						</td>
						<td>
							<input id="ipt_fax" name="ipt_fax" validator="{'default':'fax'}"/>
						</td>
						<td class="title">
							服务电话：
						</td>
						<td>
							<input id="ipt_techSupportTel" name="ipt_techSupportTel" validator="{'default':'phoneAndTelNum'}" />
						</td>
					</tr>
					<tr>
						<td class="title">
							E-mail：
						</td>
						<td colspan="3">
						<input id="ipt_email" name="ipt_email" 	validator="{'default':'email'}"/>
						</td>
<!--						<td class="title">-->
<!--							LOGO图标：-->
<!--						</td>-->
<!--						<td>-->
<!--							<input id="ipt_logoFileName" type="file" />-->
<!--						</td>-->
					</tr>
					<tr>
						<td class="title">
							地址：
						</td>
						<td colspan="3">
							<input type="text" id="ipt_address" class="x2" validator="{'default':'*','length':'0-80'}"/>
						</td>
					</tr>
					<tr>
						<td class="title">
							官方网址：
						</td>
						<td colspan="3">
							<input type="text" id="ipt_uRL" class="x2" rows="3" validator="{'default':'url'}"/>
						</td>
					</tr>
					<tr>
						<td class="title">
							备注：
						</td>
						<td colspan="3">
							<textarea id="ipt_descr" class="x2" rows="3" validator="{'default':'*','length':'0-120'}"></textarea>
						</td>
					</tr>
				</table>

				<div class="conditionsBtn">
					<a href="javascript:void(0);" id="btnSave" class="fltrt">确定</a>
					<a href="javascript:void(0);" id="btnUpdate" class="fltrt">取消</a>
				</div>
			</div>

			<div id="divShowManufacturerInfo" class="easyui-window"
				collapsible="false" minimizable="false" maximizable="false"
				closed="true" title="厂商详情" style="width: 800px;">
				<table id="tblManufacturerInfo" class="tableinfo">
					<tr>
						<td>
							<b class="title">厂商名称：</b>
							<label id="lbl_resManuFacturerName" class="input"></label>
						</td>
						<td>
							<b class="title">厂商别名：</b>
							<label id="lbl_resManuFacturerAlias" class="input"></label>
						</td>
					</tr>
					<tr>
						<td>
							<b class="title">联系人：</b>
							<label id="lbl_contacts" class="input"></label>
						</td>
						<td>
							<b class="title">联系电话：</b>
							<label id="lbl_contactsTelCode" class="input"></label>
						</td>
					</tr>
					<tr>
						<td>
							<b class="title">传真：</b>
							<label id="lbl_fax" class="input"></label>
						</td>
						<td>
							<b class="title">服务电话：</b>
							<label id="lbl_techSupportTel" name="ipt_techSupportTel"	class="input"></label>
						</td>
					</tr>
					<tr>
						<td>
							<b class="title">E-mail：</b>
							<label id="lbl_email" name="ipt_email" class="input"></label>
						</td>
						<td>
							<b class="title">地 址：</b>
							<label id="lbl_address" class="x2"></label>
						</td>
					</tr>
					<tr>
						<td>
							<b class="title">官方网址：</b>
							<label id="lbl_uRL" class="x2" rows="3"></label>
						</td>
<!--						<td>-->
<!--							<b class="title">LOGO图标：</b>-->
<!--							<label id="lbl_logoFileName" class="x2"></label>-->
<!--						</td>-->
					</tr>
					<tr>
						<td>
							<b class="title">备注：</b>
							<label id="lbl_descr" class="x2" rows="3"></label>
						</td>
					</tr>
				</table>
				<div class="conditionsBtn">
					<a href="#" class="easyui-linkbutton"
						onclick="javascript:$('#divShowManufacturerInfo').dialog('close')">关闭</a>
				</div>
			</div>
		</div>
	</body>
</html>
