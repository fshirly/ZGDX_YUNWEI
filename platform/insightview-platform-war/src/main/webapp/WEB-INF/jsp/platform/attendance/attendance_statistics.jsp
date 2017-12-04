<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>签到统计</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/css/base.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/css/reset.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/plugin/select2/select2.css"/>
<!-- mainframe -->
<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/commonUtil.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/base64.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/plugin/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/plugin/select2/select2.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/commonPlugins.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/printarea/jquery.PrintArea.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/permission/LRSelect.js"></script>


</head>
<body>
	<div class="rightContent">
		<div class="location">当前位置：${navigationBar }</div>
		<div class="conditions" style="border-bottom:1px solid rgb(182, 182, 182);">
		<form id="frmExport" method="post"
				action="${pageContext.request.contextPath}/attendanceRecord/toExpStatisAttRecord">
			<input name="colNameArrStr" id="iptColName" type="hidden" /> 
			<input name="colTitleArrStr" id="iptTitleName" type="hidden" /> 
			<input name="exlName" id="iptExlName" value="签到统计信息.xls" type="hidden" />
			<input name="attPlanName" id="selAttPlanName" type="hidden" />
				
			<table>
				<tr>
					<td><b>签到计划：</b><select id="signPlan" name="attPlanId"></select></td>
					<td><b>签到人姓名：</b><select id="name" name="userId"><option value="-1">请选择...</option></select></td>
					<td><input type="checkbox" id="isShow" name="hasUncheckedIn" value="1"/>只显示存在未签到记录</td>
				</tr>
				<tr>
				    <td colspan="2"><b>签到日期：</b><input id="startTime" name="checkInStartTime" /> - <input id="endTime" name="checkInEndTime" /></td>
					<td class="btntd">
						<a onclick="Sys.attendance.reloadTable();">查询</a>
						<a onclick="Sys.attendance.cancelButton();">重置</a>
					</td>
				</tr>
			</table>
	    </form>
		</div>
		<div style="height:30px;line-height:30px;margin-left:20px;"><strong>签到总数：<span id="totalCount"></span></strong>&nbsp;&nbsp;&nbsp;&nbsp;<strong><font color="blue">已签到总数：<span id="checkedCount"></span></font></strong>&nbsp;&nbsp;&nbsp;&nbsp;<strong><font color="red">未签到总数：<span id="uncheckedCount"></span></font></strong></div>
		<div id="divData" class="datas" style="top:142px;">
			<table id="ASTable"/>
		</div>
			
	</div>
	<div id="popWin"></div>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/platform/attendance/attendance_statistics.js"></script>
</body>
</html>