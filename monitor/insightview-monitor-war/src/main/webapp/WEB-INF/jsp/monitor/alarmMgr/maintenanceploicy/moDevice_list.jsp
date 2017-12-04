<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file = "../../../common/pageincluded.jsp" %>
<html>
	<head>
	</head>

	<body>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/js/monitor/alarmMgr/maintenanceploicy/moDevice_list.js"></script>
		<script type="text/javascrict"> 
				<%String flag=(String)request.getAttribute("flag");%>
				<%String dif=(String)request.getAttribute("dif");%>
		</script>
		<div class="winbox">
			<div class="conditions" id="divFilter">
				<table>
					<tr>
						<td>
							<b>事件源对象：</b>
							<input type="text" id="txtMOName" />
							<input id="flag" type="hidden" value="<%=flag%>"/>
							<input id="dif" type="hidden" value="<%=dif%>"/>
						</td>
						<td class="btntd">
							<a href="javascript:void(0);" onclick="reloadTable();">查询</a>
							<a href="javascript:void(0);" onclick="resetForm('divFilter');">重置</a> 
						</td>
					</tr>
				</table>
			</div>
			<div class="datas">
				<table id="tblMODevice">
				</table>
			</div>
			
			<div class="conditionsBtn" id="isShow">
				<a onclick="javascript:toConfirmSelect();">确定</a>
	            <a onclick="javascript:$('#event_select_dlg').dialog('close');">关闭</a>
			</div> 
		</div>
	</body>
</html>
