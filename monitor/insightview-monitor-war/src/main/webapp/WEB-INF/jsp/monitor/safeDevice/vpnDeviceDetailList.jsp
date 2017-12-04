<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="../../common/pageincluded.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/monitor/safeDevice/vpnDeviceDetailList.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/monitor/host/commHref.js"></script>


<body>
	<input id="liInfo" value="${liInfo}" type="hidden" />
	<input id="MOID" value="${MOID}" type="hidden" />
	<div class="easyui-tabs" fit="true">
		<div title="CPU">
			<table id="tblCPU">
			</table>
		</div>
		<div title="内存">
			<table id="tblMemory">
			</table>
		</div>
		<div id="interface" title="接口">
			<table id="tblFlows">
			</table>
		</div>
	</div>
</body>
</html>
