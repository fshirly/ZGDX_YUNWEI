<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/plugin/easyui/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/style/css/reset.css" />
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/common/jquery-1.9.1.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/common/commonUtil.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/common/base64.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/plugin/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/common/easyui-lang-zh_CN.js"></script>
</head>
<body>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/style/css/base.css" />
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/monitor/alarmMgr/alarmSendOrderUnion/newAlarmSendOrderUnion.js"></script>
	<div id="workOrderForm">
		<input type="hidden" id="alarmIds" value="${alarmIds }" />
		<form id="formWorkOrder">
			<table id="tableWorkOrder" class="formtable">
				<tr>
					<td class="title">标题：</td>
					<td colspan="3"><input type="text" id="titleId1" name="title"
						class="x2" validator="{'default':'*','length':'1-50'}" /> <dfn>*</dfn></td>
				</tr>
				<tr>
					<td class="title">备注：</td>
					<td colspan="3"><textarea id="comment" class="x2" rows="3"
							validator="{'default':'*','length':'0-500'}"   style="resize: none"></textarea></td>
				</tr>
				<tr>
					<td class="title">派单时间：</td>
					<td><input type="text" id="transferTimeId1"
						name="transferTime"
						validator="{'default':'*', 'type':'datetimebox'}"
						value="${transferTimeStr }" class="easyui-datetimebox"
						dataoptions="required:true,editable:false"></input> <dfn>*</dfn></td>
					<td class="title">派单人：</td>
					<td><input type="hidden" name="acceptPeople" value="${userId}" />
						<input type="hidden" name="handlingPeople" value="${username}" />
						${username}</td>
				</tr>
			</table>
		</form>
		<table id="workOrderSingleTableId"></table>
		<div class="conditionsBtn">
			<a href="javascript:void(0)">下一步</a> <a href="javascript:viod(0)"
				onclick="$('#popWin').window('close')">取消</a>
		</div>
	</div>

	<div id="workOrderProcessNext" class="easyui-window" title="告警派单任务流转" data-options="modal:true,closed:true,minimizable:false,maximizable:false,collapsible:false,resizable: false" style="width:800px;height:525px;">
		<form id="formProcessNext">
			<table id="formtable" class="formtable">
				<tr>
					<td class="title">当前环节：</td>
					<td>告警接收</td>
				</tr>
				<tr>
					<td class="title">受理意见：</td>
					<td><textarea rows="3" id="summary" class="x2" name="summary"
							style="resize: none" validator="{'default':'*','length':'0-500'}"></textarea></td>
				</tr>
				<tr>
					<td class="title">选择处理人：</td>

					<td>
						<table class="borderbox" style="width: 500px;">
							<tr>
								<td colspan="2"><div id="departmentMan4WorkOrder"
										class="divbox" style="width: 435px;"></div></td>
							</tr>

						</table>
					</td>
				</tr>
				<tr>
					<td class="title">已选择处理人：</td>
					<td><input type="hidden" name="selectPeopleId"
						id="selectPeopleId4WorkOrder" value="" /> <input
						name="selectPeopleName" class="x2" id="selectPeopleName4WorkOrder"
						readonly="readonly" /> <dfn>*</dfn></td>
				</tr>
				<tr>
					<td></td>
					<td><input type="checkbox" id="sendMsg">短信通知处理人</td>
				</tr>
			</table>
		</form>
		<div class="conditionsBtn">
        <a href="javascript:void(0)">上一步</a>
        <a href="javascript:void(0)">确定</a>
        <a href="javascript:void(0)" onclick="closeAllWindow();">取消</a>
    </div>

</div>

</body>
</html>