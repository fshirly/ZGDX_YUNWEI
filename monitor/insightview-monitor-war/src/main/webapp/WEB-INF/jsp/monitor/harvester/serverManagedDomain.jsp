<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file = "../../common/pageincluded.jsp" %>

<html>
	<head>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/plugin/dTree/dtree.js"></script>
	</head>

	<body>
		<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/monitor/harvester/serverManagedDomain.js"></script>
		<%
			String serverid =(String)request.getAttribute("serverid");
		 %>
		<div id="divDomainConfig" >
			<input type="hidden" id="serverid" value="<%= serverid%>"/>
			
			<div id="dataDomainTreeDiv" class="dtree"
				style="width: 100%; height: 350px; overflow: auto;">
			</div>
			
		</div>
			<div class="conditionsBtn" style="position:absolute">
				<a href="javascript:void(0);" onclick="doConfig();"
					class="fltrt">确定</a>
				<a href="javascript:void(0);" onclick="closeConfig();">取消</a>
			</div>
	</body>
</html>
