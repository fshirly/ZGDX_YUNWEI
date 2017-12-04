<%@ page language="java" pageEncoding="utf-8"%>
<html>
	<head>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/style/style_one/js/main.js"></script>
	</head>
	<body>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/fileUtil.js"></script>
		<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/platform/supplier/supplierLLook.js"></script>
	<div id="divRoomResDetail" style="overflow:hidden;">
	<div id="view_supplier" class="easyui-tabs">  
		<div title="基本信息" data-options="closable:false">
			<input type="hidden" id="lProviderId" value="${lProviderId }"/>
			<div style="overflow:auto;max-height:490px;">
			<table id="tblBase" class="tableinfo">
		 	<tr>
				<td>
					<span style="display:inline-block;margin-top:0px;float:left;"><b class="title">供应商名称：</b></span>
					<label id="lbl_providerName" style="display:inline-block;max-width:138px;max-height:200px;word-wrap:break-word;word-break:break-all;overflow:auto;"></label>
				</td>
				<td>
					<b class="title">联系人：</b>
					<label id="lbl_contacts"></label>
				</td>
			</tr>
			<tr>
				<td>
					<b class="title">联系电话：</b>
					<label id="lbl_contactsTelCode"></label>
				</td>
				<td>
					<b class="title">传真：</b>
					<label id="lbl_fax"></label>
				</td>
			</tr>
			<tr>
				<td>
					<b class="title">服务电话：</b>
					<label id="lbl_techSupportTel"></label>
				</td>
				<td>
					<b class="title">E-mail：</b>
					<label id="lbl_email"></label>
				</td>
			</tr>
			<tr>
				<td>
					<b class="title">成立时间：</b>
					<label id="lbl_establishedTime"></label>
				</td>
				<td>
					<b class="title">员工数：</b>
					<label id="lbl_employNum"></label>
				</td>
			</tr>
			<tr>
				<td>
					<b class="title">注册资金（万元）：</b>
					<label id="lbl_registeredFund"></label>
				</td>
			</tr>
			<tr>
				<td colspan ="2">
					<span style="display:inline-block;margin-top:0px;float:left;"><b class="title">地 址：</b></span>
					<label id="lbl_address" style="display:inline-block;max-width:533px;max-height:200px;word-wrap:break-word;word-break:break-all;overflow:auto;"></label>
				</td>
			</tr>
			<tr>
				<td colspan ="2">
					<span style="display:inline-block;margin-top:0px;float:left;"><b class="title" style="min-width:100px;">官方网址：</b></span>
					<label id="lbl_uRL" style="display:inline-block;max-width:533px;max-height:200px;word-wrap:break-word;word-break:break-all;overflow:auto;"/>
				</td>
			</tr>
			<tr>
				<td colspan ="2">
					<div>
						<span style="display:inline-block;margin-top:0px;float:left;"><b class="title" style="min-width:100px;">备注：</b></span>
						<label id="lbl_descr" style="display:inline-block;max-width:533px;max-height:200px;word-wrap:break-word;word-break:break-all;overflow:auto;"/>
					</div>
				</td>
			</tr>
			</table>
			<div class="datas">
				<table id="providerAccessoryInfo">
				</table>
			</div>
			</div>
		</div> 
	  	<div title="软硬件代理" data-options="closable:false">
	  		<div class="datas">
		  		<table id="providerSoftHardwareProxy" >
				</table>
			</div>
		</div>
		 <div title="服务资质" data-options="closable:false">
		 	<div class="datas">
			 	<table id="providerServiceCertificate">
				</table>
			</div>
		 </div>
	  </div>
		<div class="conditionsBtn">
			<input type="button" id="btnClose" value="关闭" onclick="javascript:$('#popWin').window('close');"/>
		</div>
	</div>
	</body>
</html>
