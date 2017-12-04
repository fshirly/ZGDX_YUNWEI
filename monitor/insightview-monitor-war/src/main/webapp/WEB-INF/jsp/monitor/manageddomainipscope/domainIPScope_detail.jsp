<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file = "../../common/pageincluded.jsp" %>
<html>
	<head>
	</head>

	<body>
		<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/monitor/manageddomainipscope/domainIPScope_detail.js"></script>
		<%
			String scopeID =(String)request.getAttribute("scopeID");
		 %>
	<div id="divDomainIPScopeDetail" style="overflow:hidden;">
		<input type="hidden" id="scopeID" value="<%= scopeID%>"/>
	  <div>
		<table id="tblDomainIPScopeDetail" class="tableinfo1">
			<tr>
				<td>
					<b class="title">管理域：</b>
					<label id="lbl_domainName"></label>
				</td>
			</tr>
			
			<tr>
				<td>
					<b class="title">范围类型：</b>
					<label id="lbl_scopeType"></label>
				</td>
			</tr>
			
			<tr>
				<td>
					<b class="title">起始IP：</b>
					<label id="lbl_startIPStr" />
				</td>
			</tr>
			
			<tr id="show1" style="display: none">
				<td>
					<b class="title">终止IP：</b>
					<label id="lbl_endIPStr" />
				</td>
			</tr>
			
			<tr id="show2" style="display: none">
				<td>
					<b class="title">网络掩码：</b>
					<label id="lbl_network" />
				</td>
			</tr>
			
			<tr>
				<td>
					<b class="title">状态：</b>
					<label id="lbl_status"/>
				</td>
			</tr>
			
			<tr>
				<td>
					<b class="title">管理域描述：</b>
					<label id="lbl_domainDescr" />
				</td>
			</tr>
		</table>
	  </div>
		<div class="conditionsBtn">
			<input type="button" id="btnClose" value="关闭" onclick="javascript:$('#popWin').window('close');"/>
		</div>
	</div>



		
	</body>
</html>
