<%@ page language="java" pageEncoding="utf-8"%>

<html>
	<head>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/style/style_one/js/main.js"></script>

	</head>

	<body>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/fileUtil.js"></script>
		<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/platform/supplier/serviceLook.js"></script>
	<div id="divRoomResDetail" style="overflow:hidden;">
	<div id="view" class="easyui-tabs">   
	  <div title="服务资质">
	  <div style="overflow:auto;max-height:272px;">
		<table id="tblRoomResDetail" class="tableinfo">
			<tr>
				<td>
					<b class="title">服务名称：</b>
					<label id="lbl_serviceName"></label>
				</td>
			</tr>
			<tr>
				<td>
					<b class="title">服务厂商：</b>
					<label id="lbl_serviceFirmName" />
				</td>
			</tr>
			<tr>
				<td>
					<b class="title">服务期限：</b>
					<label id="lbl_serviceBeginTime" />&nbsp;-&nbsp;<label id="lbl_serviceEndTime" />
				</td>
			</tr>
			<tr>
				<td>
					<div>
						<span style="float:left;display:inline-block;margin-top:0px;"><b class="title" >服务说明：</b></span>
						<label id="lbl_description" class="x2" style="display:inline-block;max-width:535px;max-height:76px;word-wrap:break-word;word-break:break-all;overflow:auto;"/>
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<b class="title">相关证明文件：</b>
					<a id="lbl_certificateUrl" ></a>
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
