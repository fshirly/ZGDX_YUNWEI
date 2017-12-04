<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file = "../../common/pageincluded.jsp" %>

<html>
	<head>
	</head> 
	<body> 
		<script type="text/javascript"	src="${pageContext.request.contextPath}/js/monitor/discover/rediscover_list.js"></script>
		<%
			String taskid =(String)request.getAttribute("taskid");
			String deviceip =(String)request.getAttribute("deviceip");
			String moid =(String)request.getAttribute("moid");
			String moClassId =(String)request.getAttribute("moClassId");
			String nemanufacturername =(String)request.getAttribute("nemanufacturername");
			String port =(String)request.getAttribute("port");
			String dbName =(String)request.getAttribute("dbName");
		 %>
	    <div  class="datas" style="height: 99%">
	    <input type="hidden" id="taskid" value="<%= taskid%>"/>
	    <input type="hidden" id="deviceip" value="<%= deviceip%>"/>
        <input type="hidden" id="moid" value="<%= moid%>"/>
        <input type="hidden" id="moClassId" value="<%= moClassId%>"/>
        <input type="hidden" id="nemanufacturername" value="<%= nemanufacturername%>"/>
        <input type="hidden" id="port" value="<%= port%>"/>
        <input type="hidden" id="dbName" value="<%= dbName%>"/>
			<table id="tblDeviceTask">
			</table>
		</div>
			<div class="conditionsBtn">
				<input type="button" id="btnClose" value="关闭" onclick="javascript:$('#popWin').window('close');"/>
			</div>
	</body>
</html>