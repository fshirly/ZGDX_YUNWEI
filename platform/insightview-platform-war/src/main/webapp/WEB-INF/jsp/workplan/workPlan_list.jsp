<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>工作计划</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/style/css/base.css" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/style/css/reset.css" />
<link rel="stylesheet" type="text/css" 
	href="${pageContext.request.contextPath}/plugin/select2/select2.css"/>
	
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/common/jquery-1.9.1.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/fui/fui.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/plugin/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/common/easyui-lang-zh_CN.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/common/commonUtil.js"></script>
<script type="text/javascript" 
	src="${pageContext.request.contextPath}/js/common/commonPlugins.js"></script>
<script type="text/javascript" 
	src="${pageContext.request.contextPath}/plugin/select2/select2.js"></script>

</head>
<body>
	<div class="conditions">
		<table>
			<tr>
				<td><b>计划名称：</b> <input id="plan_title" type="text"/></td>
				<td><b>计划开始时间：</b> 
					<input id="plan_start1" class="easyui-datetimebox" data-options="editable:false,panelWidth:'100%',showSeconds:false"/> -
					<input id="plan_start2" class="easyui-datetimebox" data-options="editable:false,panelWidth:'100%',showSeconds:false"/>
				</td>
			</tr>
			<tr>
				<td><b>类别：</b>
					<select id="plan_type">
						<option value="-1">全部</option>
					</select>
				</td>
				<td><b>计划结束时间：</b> 
					<input id="plan_end1" class="easyui-datetimebox" data-options="editable:false,panelWidth:'100%',showSeconds:false"/> -
					<input id="plan_end2" class="easyui-datetimebox" data-options="editable:false,panelWidth:'100%',showSeconds:false"/>
				</td>
				<td class="btntd">
					<a href="javascript:workplan.list.search();">查询</a>
					<a href="javascript:workplan.list.reset();">重置</a>
				</td>
			</tr>
		</table>
	</div>
	<div class="datas">
		<table id="tblWorkPlanList"></table>
	</div>

	<div id="workPlan_popWin"></div>
		
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/workplan/workPlan_list.js"></script>
</body>
</html>