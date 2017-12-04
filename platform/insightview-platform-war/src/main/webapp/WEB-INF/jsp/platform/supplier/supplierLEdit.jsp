<%@ page language="java" pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>供应商新增</title>


</head>
<body>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/plugin/easyui/themes/default/easyui.css" />

<script type="text/javascript" src="${pageContext.request.contextPath}/fui/plugin/fui-fileupload.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/fileUtil.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/platform/supplier/supplierLEdit.js"></script>

	<div id="supplierAdd" class="easyui-tabs">   
	    <div title="基础信息" data-options="closable:false">
	    	<input type="hidden" id="lProviderId" value='${lProviderId}'/>
	    	<input type="hidden" id="lProviderId1" value='${attachmentArray}'/>
	    	<input type="hidden" id="lProviderId2" value='${g_attachmentArrays }'/>
	    	<input type="hidden" id="lProviderId3" value='${gs_attachmentArrays }'/>
	    	<input type="hidden" id="init_employee" value='${supplier.employNum}'/>
	    	<div style="overflow:auto;max-height:490px;">
	        <table id="tblAddProvider" class="formtable">
					<tr>
						<td class="title">
							供应商名称：
						</td>
						<td>
							<input type="text" id="ipt_providerName" name="ipt_providerName" value="${supplier.providerName}"  validator="{'length':'1-30'}" msg="{reg:'只能输入1-30位字符！'}"/><b>*</b>
						</td>
						<td class="title">
							联系人：
						</td>
						<td>
							<input type="text" id="ipt_contacts" name="ipt_contacts"  value="${supplier.contacts}" validator="{'length':'2-15'}" msg="{reg:'只能输入2-15位字符！'}" /><b>*</b>
						</td>
					</tr>
					<tr>
						<td class="title">
							联系电话：
						</td>
						<td>
							<input type="text" id="ipt_contactsTelCode" value="${supplier.contactsTelCode}" name="ipt_contactsTelCode" validator="{'default':'phoneAndTelNum'}" msg="{reg:'只能输入数字！'}"/>
						</td>
						<td class="title">
							传真：
						</td>
						<td>
							<input id="ipt_fax" name="ipt_fax" value="${supplier.fax}" validator="{'default':'checkEmpty_fax'}"/><b>*</b>
						</td>
					</tr>
					<tr>
						<td class="title">
							服务电话：
						</td>
						<td>
							<input id="ipt_techSupportTel" name="ipt_techSupportTel" value="${supplier.techSupportTel}" validator="{'default':'phoneAndTelNum'}" msg="{reg:'只能输入数字！'}"/>
						</td>
						<td class="title">
							E-mail：
						</td>
						<td>
							<input id="ipt_email" name="ipt_email" value="${supplier.email}"
								validator="{'reg':'/^([a-z0-9_\\.-]+)@([\\da-z\\.-]+)\\.([a-z\\.]{2,6})$/'}"  msg="{'reg':'邮箱格式不正确！'}"/>
						</td>
					</tr>
					<tr>
						<td class=title>
							成立时间：
						</td>
						<td>
							<input id="ipt_build" name="ipt_build" value="${establishedTime}" class="easyui-datebox" data-options="editable:false" validator="{'default':'*','type':'datebox'}"/><b>*</b>
						</td>
						<td class=title>
							员工数：
						</td>
						<td >
							<select id="ipt_employee" name="ipt_employee" data-options="editable:false">
								<option value="1">20人以下</option>
							    <option value="2">20-99人</option>
							    <option value="3">100-499人</option>
							    <option value="4">500-9999人</option>
							    <option value="5">10000人及以上</option>
							</select>
						</td>
					</tr>
					<tr>
						<td class=title>
							注册资金（万元）：
						</td>
						<td colspan="3">
							<input type="text" id="ipt_fund" name="ipt_fund" value="${supplier.registeredFund}" validator="{'default':'num','length':'1-16'}"/><b>*</b>
						</td>
					</tr>
					<tr>
						<td class=title>
							地 址：
						</td>
						<td colspan="3">
							<input type="text" id="ipt_address" name="ipt_address" value="${supplier.address}" class="x2" validator="{'default':'*','length':'0-200'}"/>
						</td>
					</tr>
					<tr>
						<td class="title">
							官方网址：
						</td>
						<td colspan="3">
							<input type="text" id="ipt_uRL" name="ipt_uRL" class="x2" rows="3" value="${supplier.uRL}" validator="{'default':'url'}" />
						</td>
					</tr>
					<tr>
						<td class="title">
							备注：
						</td>
						<td colspan="3">
							<textarea id="ipt_descr" name="ipt_dnescr" class="x2" rows="3" validator="{'default':'*','length':'0-500'}">${supplier.descr}</textarea>
						</td>
					</tr>
					<tr>
						<td class=title>附件类型：</td>
						<td>
							<select id="ipt_attachmentType" name="ipt_attachmentType" class="easyui-combobox" data-options="editable:false">
								<option value="1">经营状况</option>
							    <option value="2">财务实力</option>
							    <option value="3">诚信历史</option>
							    <option value="4">安全资质</option>
							    <option value="5">技术服务实力</option>
							    <option value="6">责任承担能力</option>
							    <option value="7">其他</option>
							</select>
						</td>
						<td colspan="2">
							<input id="application_file" name="ipt_attachmentURL" type="file" />
						</td> 
					</tr>
				</table>
				<div class="datas">
					<table id="attachment_view">
					</table>
				</div>
			</div>
		</div>
	    <div title="软硬件代理" data-options="closable:false">  
	    	<div class="datas">
				<table id="proxy_info">
				</table>
			</div> 
	    </div>   
	    <div title="服务资质" data-options="closable:false">
	    	<div class="datas">
				<table id="services_info">
				</table>
			</div>
	    </div>   
	</div>
	<div class="conditionsBtn">
		<input class="buttonB" type="button" id="btnSave" value="确定" onclick="javascript:doAdd();"/>
		<input class="buttonB" type="button" id="btnUpdate" value="取消" onclick="javascript:$('#popWin').window('close');"/>
	</div>    
</body>
</html>