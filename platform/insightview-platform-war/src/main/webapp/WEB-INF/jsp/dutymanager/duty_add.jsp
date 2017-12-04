<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>新建值班信息</title>
</head>
<body>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/plugin/select2/select2.css"/>
	<div>
		<form id="add_duty_form">
			<table class="formtable">
				<tr>
					<td class="title">带班领导：</td>
					<td>
						<select name="leaderId" id="leaderId">
							<option value="-1">请选择...</option>
						</select>
					</td>
					<td class="title">值班日期：</td>
					<td>
						<input id="dutyDate" name="dutyDate" class="easyui-datebox" validator="{'default':'*','type':'datebox'}" data-options="editable:false"/> <dfn>*</dfn>
					</td>
				</tr>
				<tr>
					<td class="title">备勤人员：</td>
					<td colspan="3">
						<input type="hidden" name="readys"/>
						<input id="readys" readonly="readonly" class="x2" onclick="dutymanager.addduty.confige(1);"/>
					</td>
				</tr>
				<tr>
					<td class="title">备勤等级：</td>
					<td>
						<input id="cc"/>
					</td>
				</tr>	
			</table>		
		</form>
		<div style="width: 670px; height: 200px;"><table id="orders"></table></div>
		<div id="add_duty_toolbar">
	        <a class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true"  onclick="dutymanager.addduty.addOrder();">新建班次及值班人</a>
		</div>
	</div>
	<div class="conditionsBtn">
		<a onclick="javascript:dutymanager.addduty.add();">确定</a>
		<a onclick="javascript:f('#popWin').window('close');">取消</a>
	</div>
	
	<div id="add_order_dialog" class="add_order_dialog" style="display: none">
		<table class="formtable" style="width: 370px;">
				<tr>
					<td class="title">班次名称：</td>
					<td>
						<input id="order_intervals" type="hidden"/>
						<input id="duty_orders" validator="{'default':'*','type':'combobox'}"/> <dfn>*</dfn>
					</td>
				</tr>
				<tr>
					<td class="title">班次时间段：</td>
					<td>
						<input id="duty_orders_time" disabled="disabled"/>
					</td>
				</tr>
				<tr>
					<td class="title">值班人：</td>
					<td>
						<input type="hidden" name="dutyers"/>
						<input id="dutyers" readonly="readonly" onclick="dutymanager.addduty.confige(2);" validator="{'default':'*'}"/> <dfn>*</dfn>
					</td>
				</tr>	
		</table>	
		<div class="conditionsBtn">
			<a onclick="javascript:dutymanager.addduty.confirm();">确定</a>
			<a onclick="javascript:f('#add_order_dialog').dialog('close');">取消</a>
		</div>	
	</div>
	<script type="text/javascript" src="${pageContext.request.contextPath}/plugin/select2/select2.js"></script>	
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/commonPlugins.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/dutymanager/duty_add.js"></script>
</body>
</html>