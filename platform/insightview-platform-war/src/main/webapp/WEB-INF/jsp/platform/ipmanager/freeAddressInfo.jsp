<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="../../common/pageincluded.jsp"%>
<html>
	<head>
	</head>
	<body>
		<script type="text/javascript"	src="${pageContext.request.contextPath}/js/platform/ipmanager/freeAddressInfo.js"></script>
		<%	String subNetId=(String)request.getAttribute("subNetId");
			String startIp=(String)request.getAttribute("startIp");
			String endIp=(String)request.getAttribute("endIp");
			String deptId=(String)request.getAttribute("deptId");
	    %>
			<input id="subNetId" type="hidden" value="<%= subNetId%>"/>
			<input id="startIp" type="hidden" value="<%= startIp%>"/>
			<input id="endIp" type="hidden" value="<%= endIp%>"/>
			<input id="deptId" type="hidden" value="<%= deptId%>"/>
			<div id="viewFreeAddressDiv"class="winbox" >
				<div class="datas">
					<table id="tblFreeAddress"></table>
				</div>
				
			</div>
			<div class="conditionsBtn">
				<a href="javascript:void(0);" id="btnClose" class="fltrt" onclick="javascript:toAssignFreeIds();">确定分配这些IP</a>
				<a href="javascript:void(0);" id="btnClose" class="fltrt" onclick="javascript:$('#popWin2').window('close');">取消</a>
			</div>
	</body>
</html>