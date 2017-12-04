<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>工作日志</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/style/css/base.css" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/style/css/reset.css" />
	
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

</head>
<body>
	<div class="conditions">
		<table>
			<tr>
				<td><b>日志主题：</b> <input id="log_title" type="text"/></td>
				<td><b>日志开始时间：</b> 
					<input id="log_start1" class="easyui-datetimebox" data-options="editable:false,panelWidth:'100%',showSeconds:false"/> -
					<input id="log_start2" class="easyui-datetimebox" data-options="editable:false,panelWidth:'100%',showSeconds:false"/>
				</td>
			</tr>
			<tr>
				<td><b>是否星标：</b>
					<select id="log_starLevel" class="easyui-combobox" data-options="panelHeight:70,editable:false">
						<option value="-1">全部</option>
						<option value="2">是</option>
						<option value="1">否</option>
					</select>
				</td>
				<td><b>日志结束时间：</b> 
					<input id="log_end1" class="easyui-datetimebox" data-options="editable:false,panelWidth:'100%',showSeconds:false"/> -
					<input id="log_end2" class="easyui-datetimebox" data-options="editable:false,panelWidth:'100%',showSeconds:false"/>
				</td>
				<td class="btntd">
					<a href="javascript:worklog.list.search();">查询</a>
					<a href="javascript:worklog.list.reset();">重置</a>
				</td>
			</tr>
		</table>
	</div>
	
	<div class="datas">
		<table id="tblWorkLogList"></table>
	</div>

	<div id="workLog_popWin"></div>
		
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/worklog/workLog_list.js"></script>
</body>
</html>