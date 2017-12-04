<%@ page language="java" pageEncoding="utf-8"%>

<html>
	<head>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/style/style_one/js/main.js"></script>

	</head>

	<body>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/fileUtil.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/platform/supplier/softHardCertificateLook.js"></script>
	<div id="divRoomResDetail" style="overflow:hidden;">
	<input type="hidden" id="proxyId" value="${proxyId}"/>
	<div id="view" class="easyui-tabs">   
	  <div title="软硬件代理" >
	  	<div style="overflow:auto;max-height:322px;">
		<table id="tblRoomResDetail" class="tableinfo">
			<tr>
				<td>
					<b class="title">资质名称：</b>
					<label id="lbl_proxyName"></label>
				</td>
			</tr>
		 	<tr>
				<td>
					<b class="title">代理产品类型：</b>
					<label id="lbl_productType"></label>
				</td>
			</tr>
			<tr>
				<td>
					<b class="title">代理厂商：</b>
					<label id="lbl_proxyFirmName" />
				</td>
			</tr>
			<tr>
				<td>
					<b class="title">代理期限：</b>
					<label id="lbl_proxyBeginTime" />&nbsp;-&nbsp;<label id="lbl_proxyEndTime" />
				</td>
			</tr>
			<tr>
				<td>
					<div>
						<span style="float:left;display:inline-block;margin-top:0px;"><b class="title">代理说明：</b></span>
						<label id="lbl_description" class="x2" style="display:inline-block;max-width:535px;max-height:91px;word-wrap:break-word;word-break:break-all;overflow:auto;"/>
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<b class="title">相关证明文件：</b>
					<a id="certificateUrl" ></a>
				</td>
			</tr>
		</table>
		</div>
		</div>
	  </div>
		<div class="conditionsBtn">
			<input type="button" id="btnClose" value="关闭" onclick="javascript:$('#popWin2').window('close');"/>
		</div>
	</div>
	</body>
</html>
