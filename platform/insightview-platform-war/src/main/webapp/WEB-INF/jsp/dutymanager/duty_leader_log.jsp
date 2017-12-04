<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>带班领导登记日志</title>
</head>
<body>
	<form id="duty_form">
	<div>
		<table class="formtable">
			<tr>
				<td class="title">标题：
					<input type="hidden" name="id" value="${log.LogId eq null ? 0 : log.LogId}"/>
					<input type="hidden" name="dutyId" value="${log.ID }"/>
				</td>
				<td>
					<input name="title" value="${log.Title }" validator="{'default':'*', 'length':'1-50'}"/> <dfn>*</dfn>
				</td>
				<td class="title">值班日期：</td>
				<td>
					<input value="${dutyDate }" disabled="disabled"/>
				</td>
			</tr>
			<tr>
				<td class="title">前一天交班记录：</td>
				<td colspan="3">
					<textarea rows="4" cols="6" class="x2" disabled="disabled">${log.before }</textarea>
				</td>
			</tr>	
		</table>		
	</div>
	<div>
		<h2>当天带班信息：</h2>
		<table class="formtable">
				<tr>
					<td class="title">带班领导：</td>
					<td>
						<input type="text" value="${log.UserName }" disabled="disabled"/>
					</td>
					<td class="title">登记时间：</td>
					<td>
						<input name="LeaderRegisterDate" class="easyui-datetimebox" value="${log.LeaderRegisterDate }" validator="{'default':'*','type':'datetimebox'}" data-options="editable:false"/> <dfn>*</dfn>
					</td>
				</tr>
				<tr>
					<td class="title">领导交办工作：</td>
					<td colspan="3">
						<textarea name="NoticePoints" rows="4" cols="6" class="x2" validator="{'length':'0-500'}">${log.NoticePoints }</textarea>
					</td>
				</tr>	
				<tr>
					<td class="title">带班日志：</td>
					<td colspan="3">
						<textarea name="leaderLog" rows="4" cols="6" class="x2" validator="{'length':'0-500'}">${log.LeaderLog }</textarea>
					</td>
				</tr>	
		</table>	
	</div>
	</form>
	<div class="conditionsBtn">
		<a onclick="javascript:dutyLog.handleLog();">确定</a>
		<a onclick="javascript:f('#dutyDialog').dialog('close');">取消</a>
	</div>	
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/easyui-lang-zh_CN.js"></script>
</body>
</html>