<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file = "../../common/pageincluded.jsp" %>
<html>
	<head>
	</head>
	<body>
		<script type="text/javascript" 	src="${pageContext.request.contextPath}/js/monitor/component/mcMOSourceList.js"></script>
		<input type="hidden" id="proUrl" value="${proUrl}" />
		 <div class="winbox">
			<div class="conditions" id="divFilter">
				<table>
					<tr>
						<td>
							<b>告警源名称：</b>
							<input type="text" id="txtMOName" onkeydown="globelQueryAlarmSource(event);"/>							
						</td>
						<td>
							<b>IP：</b>
							<input type="text" id="txtIP" onkeydown="globelQueryAlarmSource(event);"/>							
						</td>
						<td class="btntd">
							<a href="javascript:void(0);" onclick="reloadTable();">查询</a>
							<a href="javascript:void(0);" onclick="resetForm('divFilter');">重置</a> 
						</td>
					</tr>
				</table>
			</div>
			<div class="datas">
				<table id="tblMOSource">
				</table>
			</div>
			
			<div class="conditionsBtn">
			<a onclick="javascript:toConfirmSelect();">确定</a>
            <a onclick="javascript:$('#event_select_dlg').window('close');">关闭</a>
			</div> 
		</div>
	</body>
</html>
