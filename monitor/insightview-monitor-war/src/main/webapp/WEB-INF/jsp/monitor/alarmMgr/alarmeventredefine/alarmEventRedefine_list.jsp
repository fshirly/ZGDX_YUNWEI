<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file = "../../../common/pageincluded.jsp" %>
<html>
	<head>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/js/monitor/alarmMgr/alarmeventredefine/alarmEventRedefine_list.js"></script>

	</head>

	<body>
		<div class="rightContent">
			<div class="location">
				当前位置：${navigationBar}
			</div>
			<div class="conditions" id="divFilter">
				<table>
					<tr>
						<td>
							<b>规则名称：</b>
							<input type="text" id="txtRuleName" />
						</td>
						<td>
							<b>是否启用：</b>
							<select id="txtIsEnable" class="easyui-combobox" data-options="editable:false">
								<option value="-1">全部</option>
								<option value="1">是</option>
								<option value="2">否</option>
							</select>
						</td>
						<td>
							<b>规则描述：</b>
							<input type="text" id="txtRuleDesc" />
						</td>
						<td class="btntd">
							<a onclick="reloadTable();">查询</a>
							<a onclick="reset();">重置</a> 
						</td>
					</tr>
				</table>
			</div>
			<div class="datas">
				<table id="tblAlarmEventRedefine">
				</table>
			</div>
		</div>
	</body>
</html>
