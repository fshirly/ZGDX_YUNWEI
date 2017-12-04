<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		
	</head>
	<body>
	    <link rel="stylesheet" type="text/css"  href="${pageContext.request.contextPath}/style/css/base.css" />
	    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/css/reset.css" /> 
	    <script type="text/javascript" src="${pageContext.request.contextPath}/fui/fui.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/commonUtil.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/base64.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/plugin/easyui/jquery.easyui.min.js"></script> 
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/easyui-lang-zh_CN.js"></script> 
		<script type="text/javascript" src="${pageContext.request.contextPath}/plugin/dTree/dtree.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/plugin/dTree/combo/comboJS.js"></script>
	    <script type="text/javascript" src="${pageContext.request.contextPath}/js/platform/supplier/suppliermanage_list.js"></script>
	
	 <div class="rightContent">
 		 <div class="location">当前位置：${navigationBar}</div>
  		 <div class="conditions" id="divFilter">
		 <table >
				<tr>
					<td>
					    <b>供应商名称：</b>
						<input type="text" id="providerName" name="providerName"/>
					</td>
					<td>
						<b>代理厂商： </b>
						<input type="text" id="proxyFirmName" name="proxyFirmName"/>
					</td>
					<td>
						<b style="min-width: 72px;">服务厂商： </b>
						<input type="text" id="serviceFirmName" name="serviceFirmName"/>
					</td>
					<td class="btntd">
					   	<a href="javascript:void(0);" onclick="javascript:reloadTable();" >查询</a>		
						<a href="javascript:void(0);" onclick="javascript:resetForm('divFilter');">重置</a>
					</td>
				</tr>
	</table>
	</div>	
		<!-- begin .datas -->
		<div class="datas tops">
			<table id="tblDataList">
			</table>
		</div>
		<!-- end .datas -->	
		
		<div id="divOrganizationConfig" class="easyui-window" minimizable="false"
			maximizable="false" collapsible="false" closed="true" modal="false"
			title="配置服务范围" style="width: 600px; height: 450px;">
				<div id="dataOrganizationTreeDiv" class="dtree"
					style="width: 100%; height: 351px; overflow: auto;">
				</div>
			<div class="conditionsBtn" style="position:absolute">
				<a href="javascript:void(0);" onclick="doConfigOrganization();"
					class="fltrt">确定</a>
				<a href="javascript:void(0);" onclick="closeConfigOrganization();">取消</a>
			</div>
		</div>	
		
  </div>

</body>
</html>