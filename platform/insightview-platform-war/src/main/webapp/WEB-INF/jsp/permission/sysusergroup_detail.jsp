<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="../common/pageincluded.jsp"%>
<html>
	<head>
	</head>
	<body>
		<script type="text/javascript"	src="${pageContext.request.contextPath}/js/permission/sysusergroup_detail.js"></script>
		<%	String groupID=(String)request.getAttribute("groupID");    %>
			<input id="ipt_groupID" type="hidden" value="<%= groupID%>"/>
			<div id="viewUserGroup"class="winbox" >
				<div class="datas">
					<table id="viewSysUser"></table>
				</div>
				
			</div>
			<div class="conditionsBtn">
				<a href="javascript:void(0);" id="btnClose" class="fltrt" onclick="javascript:$('#popWin').window('close');">关闭</a>
			</div>
	</body>
</html>