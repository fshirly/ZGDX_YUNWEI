<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file = "../../common/pageincluded.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>供应商列表</title>
	<script type="text/javascript"
			src="${pageContext.request.contextPath}/js/common/commonUtil.js"></script>
	<script type="text/javascript"
			src="${pageContext.request.contextPath}/js/main.js"></script>
	<script type="text/javascript"
			src="${pageContext.request.contextPath}/plugin/dTree/dtree.js"></script>
	<script type="text/javascript"
			src="${pageContext.request.contextPath}/plugin/dTree/combo/comboJS.js"></script>
	<script type="text/javascript"
			src="${pageContext.request.contextPath}/js/platform/provider/provider.js"></script>
	</head>
	<body>
		<div class="rightContent">
			<div class="location">
				当前位置：项目管理&gt;&gt;供应商管理 &gt;&gt;<span>供应商信息</span>
			</div>
			<div class="conditions" id="divFilter">
				<table>
					<tr>
						<td>
							<b>供应商名：</b>
							<input type="text" id="txtFilterProviderName" />
						</td>
						<td class="btntd">
							<a href="javascript:void(0);" onclick="reloadTable();">查询</a>
							<a href="javascript:void(0);" onclick="resetForm('divFilter');">重置</a>
						</td>
					</tr>
				</table>
			</div>

			<!-- begin .datas -->
			<!-- 从js中读取数据 -->
			<div class="datas">
				<table id="tblProvider">
				</table>
			</div>
			
			<!-- end .datas -->
			<div id="divAddProvider" class="easyui-window" minimizable="false" resizable="false" maximizable="false" collapsible="false"
				closed="true" modal="true" title="供应商信息" style="width: 800px;">
				<input id="ipt_providerId" type="hidden" />

				<table id="tblAddProvider" class="formtable">
					<tr>
						<td class="title">
							供应商名称：
						</td>
						<td>
							<input type="text" id="ipt_providerName" name="ipt_providerName" onblur="checkNameUnique();" 
								validator="{'length':'1-30'}" msg="{reg:'只能输入1-30位字符！'}" /><b>*</b>
						</td>
						<td class="title">
							联系人：
						</td>
						<td>
							<input type="text" id="ipt_contacts" name="ipt_contacts"
								validator="{'length':'2-15'}" msg="{reg:'只能输入2-15位字符！'}" /><b>*</b>
						</td>
					</tr>
					<tr>
						<td class="title">
							联系电话：
						</td>
						<td>
							<input type="text" id="ipt_contactsTelCode" name="ipt_contactsTelCode"
								validator="{'default':'phoneAndTelNum'}" msg="{reg:'只能输入数字！'}" /><b>*</b>
						</td>
						<td class="title">
							传真：
						</td>
						<td>
							<input id="ipt_fax" name="ipt_fax" validator="{'default':'fax'}"/><b>*</b>
						</td>
					</tr>
					<tr>
						<td class="title">
							服务电话：
						</td>
						<td>
							<input id="ipt_techSupportTel" name="ipt_techSupportTel"
								validator="{'default':'phoneAndTelNum'}" msg="{reg:'只能输入数字！'}" /><b>*</b>
						</td>
						<td class="title">
							E-mail：
						</td>
						<td>
							<input id="ipt_email" name="ipt_email"
								validator="{'default':'*','reg':'/^([a-z0-9_\\.-]+)@([\\da-z\\.-]+)\\.([a-z\\.]{2,6})$/'}"  msg="{'reg':'邮箱格式不正确！'}"/><b>*</b>
						</td>
					</tr>
					<tr>
						<td class=title>
							地 址：
						</td>
						<td colspan="3">
							<input type="text" id="ipt_address" name="ipt_address" class="x2" />
						</td>
					</tr>
					<tr>
						<td class="title">
							官方网址：
						</td>
						<td colspan="3">
							<input type="text" id="ipt_uRL" name="ipt_uRL" class="x2" rows="3" validator="{'default':'url'}" />
						</td>
					</tr>
<!--					<tr>-->
<!--						<td class="title">-->
<!--							LOGO图标：-->
<!--						</td>-->
<!--						<td colspan="3">-->
<!--							<input id="ipt_logoFileName" name="ipt_logoFileName" class="x2"	type="file" />-->
<!--						</td>-->
<!--					</tr>-->
					<tr>
						<td class="title">
							备注：
						</td>
						<td colspan="3">
							<textarea id="ipt_descr" name="ipt_dnescr" class="x2" rows="3"></textarea>
						</td>
					</tr>
				</table>

				<div class="conditionsBtn">
					<a href="javascript:void(0);" id="btnSave" class="fltrt">确定</a>
					<a href="javascript:void(0);" id="btnUpdate" class="fltrt">取消</a>
				</div>
			</div>
			
			<!-- 新增详情弹窗 -->
			<div id="divShowProviderInfo" class="easyui-window"	collapsible="false" minimizable="false" maximizable="false"
				closed="true" title="供应商详情" style="width: 800px;">
				<table id="tblProviderInfo" class="tableinfo">
					<tr>
						<td >
							<b class="title">供应商名称：</b>
							<label id="lbl_providerName" class="input" />
						</td>
						<td >
							<b class="title">联系人：</b>
							<label id="lbl_contacts" class="input" />
						</td>
					</tr>
					<tr>
						<td>
							<b class="title">联系电话：</b>
							<label id="lbl_contactsTelCode" class="input" />	
						</td>
						<td>
							<b class="title">传真：</b>
							<label id="lbl_fax" class="input" />
						</td>
					</tr>
					<tr>
						<td>
							<b class="title">服务电话：</b>
							<label id="lbl_techSupportTel" name="ipt_techSupportTel" class="input" />
						</td>
						<td>
							<b class="title">E-mail：</b>
							<label id="lbl_email" name="ipt_email" class="input" />
						</td>
					</tr>
					<tr>
						<td>
							<b class="title">地 址：</b>
							<label type="text" id="lbl_address" class="x2" />
						</td>
						<td>
							<b class="title">官方网址：</b>
							<label type="text" id="lbl_uRL" class="x2" rows="3" />
						</td>
					</tr>
					<tr>
<!--						<td>-->
<!--							<b class="title">LOGO图标：</b>-->
<!--							<label id="lbl_logoFileName" class="x2" />-->
<!--						</td>-->
						<td>
							<b class="title">备注：</b>
							<label id="lbl_descr" class="x2" rows="3"></label>
						</td>
					</tr>
				</table>
				<div class="conditionsBtn">
					<a href="#" class="easyui-linkbutton"  onclick="javascript:$('#divShowProviderInfo').dialog('close')">关闭</a>
				</div>
			</div>
		</div>
	</body>
</html>
