<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新增值班班次</title>
</head>
<body>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/css/base.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/css/reset.css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/commonUtil.js"></script>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/dutymanager/dutyOrder_add.js"></script>
			
	<form id="dutyOrderInfo">
		<input type="hidden" id="id" name="id" value="${dutyOrder.id }" />
		<table id="tblDutyOrderInfo" class="formtable" style="width: 480px;">
			<tr>
				<td class="title">班次名称：</td>
				<td>
					<input id="title" name="title" type="text" value="${dutyOrder.title }" validator="{'length':'1-50'}" /><dfn>*</dfn>
				</td>
			</tr>
			<tr>
				<td class="title">开始时间：</td>
				<td>
					<input id="beginPoint" name="beginPoint" type="text" value="${dutyOrder.beginPoint }" validator="{'default':'*', 'type':'timespinner'}"/><dfn>*</dfn>
				</td>
			</tr>
			<tr>
				<td class="title">
				<select id="intervalDays" name="intervalDays" style="width: 60px;">
						<option value="0" ${dutyOrder.intervalDays==0?"selected":"" }>今天</option>
						<option value="1" ${dutyOrder.intervalDays==1?"selected":"" }>第2天</option>
						<option value="2" ${dutyOrder.intervalDays==2?"selected":"" }>第3天</option>
						<option value="3" ${dutyOrder.intervalDays==3?"selected":"" }>第4天</option>
						<option value="4" ${dutyOrder.intervalDays==4?"selected":"" }>第5天</option>
						<option value="5" ${dutyOrder.intervalDays==5?"selected":"" }>第6天</option>
						<option value="6" ${dutyOrder.intervalDays==6?"selected":"" }>第7天</option>
					</select>&nbsp;&nbsp;&nbsp;
				结束时间：</td>
				<td>
					<input id="endPoint" name="endPoint" type="text" value="${dutyOrder.endPoint }" validator="{'default':'*', 'type':'timespinner'}"/><dfn>*</dfn>
				</td>
			</tr>
			<tr>
				<td class="title">交班开始时间：</td>
				<td>
					<input id="exchangeStart" name="exchangeStart" type="text" value="${dutyOrder.exchangeStart }" validator="{'default':'*', 'type':'timespinner'}"/><dfn>*</dfn>
				</td>
			</tr>
			<tr>
				<td class="title">交班结束时间：</td>
				<td>
					<input id="exchangeEnd" name="exchangeEnd" type="text" value="${dutyOrder.exchangeEnd }" validator="{'default':'*', 'type':'timespinner'}"/><dfn>*</dfn>
				</td>
			</tr>
			<tr>
				<td class="title">强制交班时间：</td>
				<td>
					<input id="forceTime" name="forceTime" type="text" value="${dutyOrder.forceTime }" validator="{'default':'*', 'type':'timespinner'}"/><dfn>*</dfn>
				</td>
			</tr>
		</table>
		<div class="conditionsBtn">
			<a href="javascript:void(0);" id="dutyOrderInfo_save">确定</a>
			<a href="javascript:void(0);" onclick="javascript:$('#popWin').window('close');">取消</a>
		</div>
	</form>
</body>
</html>