<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file = "../../common/pageincluded.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		
	</head>
	<body>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/monitor/perf/appointOfflineCollector.js"></script>
		<%
			String taskId =(String)request.getAttribute("taskId");
			String oldServerId =(String)request.getAttribute("serverId");
			String serverName =(String)request.getAttribute("serverName");
		 %>
		<input type="hidden" id="taskId" value="<%= taskId%>"/>
		<input type="hidden" id="serverName" value="<%= serverName%>"/>
		<input type="hidden" id="ipt_oldCollectorId"  class="input"  value="<%= oldServerId%>"/>
		<table id="tblChooseCollector" class="formtable1">
			<tr>
				<td class="title">
					所属采集机：
				</td>
				<td>
					<input id="ipt_serverId"  class="input" onfocus="choseHostTree();" readonly="readonly"/>
					<a id="btnUnChose" href="javascript:cancelChose();" style="display: none">清空</a>
				</td>
			</tr>
		</table>
		<div class="conditionsBtn">
			<input class="buttonB" type="button" id="btnSave" value="确定" onclick="javascript:appointCollector();"/>
			<input class="buttonB" type="button" id="btnUpdate" value="取消" onclick="javascript:$('#popWin').window('close');"/>
		</div>
		
		<div id="divHost" class="easyui-window" maximizable="false"
			collapsible="false" minimizable="false" closed="true" modal="true"
			title="选择所属采集机" style="width: 400px; height: 450px;">
			<div id="dataHostTreeDiv" class="dtree"
				style="width: 100%; height: 200px;">
			</div>
		</div>
	</body>
</html>