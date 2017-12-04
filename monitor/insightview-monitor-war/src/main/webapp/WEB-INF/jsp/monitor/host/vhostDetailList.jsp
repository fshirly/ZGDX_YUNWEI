<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="../../common/pageincluded.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/js/monitor/host/vhostDetailList.js"></script>
<script type="text/javascript"
			src="${pageContext.request.contextPath}/js/monitor/host/commHref.js"></script>
	</head>

	<body>
	<input id="liInfo" value="${liInfo}" type="hidden"/>
		<div class="easyui-tabs" fit="true">
			<div title="CPU">
				<table id="tblCPU">
	
				</table>
			</div>
			<div title="内存" >
				<table id="tblMemory">
				</table>
			</div>
			<div title="数据存储">
				<table id="tblDatastore">
				</table>
			</div>
			<div title="磁盘" >
				<table id="tblDisk">
				</table>
			</div>
			<div title="接口" >
				<table id="tblFlows">
				</table>
			</div>
		</div>
		
	</body>
</html>
