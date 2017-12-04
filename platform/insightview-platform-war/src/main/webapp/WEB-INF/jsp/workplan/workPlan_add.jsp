<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新增工作计划</title>
</head>
<body>
	<table id="workPlanInfo" class="formtable">
		<input type="hidden" id="workPlan_id" name="id" value="${workPlan.id }">
		<tr>
			<td class="title">计划名称：</td>
			<td><input type="text" id="plan_title1" name="title" value="${workPlan.title }" validator="{'default':'*'}"/><b>*</b></td>
			<td class="title">计划类型：</td>
			<td><input type="text" id="plan_type1" name="planType" value="${workPlan.planType }"/></td>
		</tr>
		<tr>
			<td class="title">创建人：</td>
			<td><input type="text" id="plan_creater" value="${workPlan.createrName }" disabled="disabled"/><b>*</b></td>
			<td class="title">相关人员：</td>
			<td><input type="text" id="plan_relationPersons" name="relationPersons" value="${workPlan.relationPersons }"/></td>
		</tr>
		<tr>
			<td class="title">计划日期：</td>
			<td colspan="3">
				<input id="plan_start" name="planStart" value="<fmt:formatDate value='${workPlan.planStart }' pattern='yyyy-MM-dd HH:mm'/>" validator="{'default':'*','type':'datebox'}"/> - 
				<input id="plan_end" name="planEnd" value="<fmt:formatDate value='${workPlan.planEnd }' pattern='yyyy-MM-dd HH:mm'/>" validator="{'default':'*','type':'datebox'}"/><b>*</b>
			</td>
		</tr>
		<tr>
			<td class="title">计划内容：</td>
			<td colspan="3">
				<textarea rows="5" class="x2" id="plan_content" name="planContent" validator="{'default':'*'}">${workPlan.planContent }</textarea><b>*</b>
			</td>
		</tr>
		<tr>
			<td class="title">附件：</td>
			<td colspan="3">
				<input type="file" id="plan_file" name="file" value="${workPlan.file }"/>
			</td>
		</tr>
	</table>
	<div class="conditionsBtn">
		<a href="javascript:workplan.add.confirm();">确定</a>
		<a href="javascript:$('#workPlan_popWin').window('close');">取消</a>
	</div>
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/fui/plugin/fui-fileupload.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/workplan/workPlan_add.js"></script>
</body>
</html>