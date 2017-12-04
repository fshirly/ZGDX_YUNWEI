<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

</head>
<body>
	<link rel="stylesheet" type="text/css"
		href="${pageContext.request.contextPath}/style/css/base.css" />
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/monitor/alarmMgr/alarmSendOrderUnion/oldAlarmSendOrderUnion.js"></script>
	<div id="winAlarmSendSingleStep1" >
		<form id="winAlarmSendSingleForm1">
		<input type="hidden" id="alarmIds" value="${alarmIds }" />
			<table class="formtable">
				<tr>
					<td class="title">标题：</td>
					<td colspan="3"><input class="x2"
						 name="title" type="text" validator="{'default':'*','length':'1-50'}" /> <dfn>*</dfn></td>
				</tr>
				<tr>
					<td class="title">备注：</td>
					<td colspan="3"><textarea rows="3" name="remark" class="x2"
							style="resize: none"></textarea></td>
				</tr>
				<tr>
					<td class="title">派单时间：</td>
					<td><input id="transferTimeId" type="text" name="transferTime"
						value="${transferTimeStr }" class="easyui-datetimebox"
						data-options="required:true,editable:false" /> <dfn>*</dfn></td>
					<td class="title">派单人：</td>
					<td><input type="hidden" name="acceptPeople" value="${userId}" />
						<input type="hidden" name="handlingPeople" value="${username}" />${username}</td>
					</td>
					<!--
            <td><input type="hidden" name="acceptPeople" value="${userId}"/>${username}</td>	
		-->
				</tr>
				<tr>
					<td class="title">服务台是否核实：</td>
					<td><select class="easyui-combobox" name="isChecked"
						data-options="panelHeight:'auto',editable: false">
							<option selected="selected" value="1">是</option>
							<option value="2">否</option>
					</select></td>
					<td class="title">单据状态：</td>
					<td>未登记</td>
				</tr>
			</table>
		</form>
		<table id="alarmSendSingleTableId"></table>
		<div class="conditionsBtn">
			<a href="javascript:void(0)">下一步</a> <a href="javascript:void(0)"
				onclick="$('#alarm_Old_Version').window('close')">取消</a>
		</div>
	</div>

	<div id="winAlarmSendSingleStep2" class="easyui-window"
		title="告警派单任务流转"
		data-options="modal:true,closed:true,minimizable:false,maximizable:false,collapsible:false,resizable: false"
		style="width: 800px; height: 488px;">
		<form id="winAlarmSendSingleForm2">
			<table class="formtable">
				<tr>
					<td class="title">受理意见：</td>
					<td><textarea id="summary" rows="3" name="summary" class="x2"
							style="resize: none"></textarea></td>
				</tr>
				<tr>
					<td class="title">当前活动：</td>
					<td><label>派单中...</label></td>
				</tr>
				<tr>
					<td class="title">选择处理人：</td>
					<td>
						<table class="borderbox">
							<tr>
								<td colspan="2" id="selectCondition"><input type="radio"
									value="departmentTree" checked="checked" name="selectCondition"
									id="sOrganization" /><label for="sOrganization">按组织架构</label>
									<input type="radio" value="groupList" name="selectCondition"
									id="sGroup" /><label for="sGroup">按工作组</label></td>
							</tr>
							<tr>
								<td>
									<div id="tree" class="divbox"></div>
									<div id="groupList" class="divbox" style="display: none;"></div>
								</td>
								<td><div id="departmentMan" class="divbox"></div></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td class="title">已选择处理人：</td>
					<td colspan="3"><input type="hidden" name="selectPeopleId"
						id="selectPeopleId" value="" /> <input name="selectPeopleName"
						class="x2" id="selectPeopleName" readonly="readonly" /> <dfn>*</dfn></td>
				</tr>
			</table>
		</form>
		<div class="conditionsBtn">
			<a href="javascript:void(0)">上一步</a> <a href="javascript:void(0)">确定</a>
			<a href="javascript:void(0)"
				onclick="closeAllWindow();">取消</a>
		</div>
	</div>
	</body>
	</html>