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
					<input type="hidden" name="id" value="${log.LogId eq null ? 0 : log.LogId }"/>
					<input type="hidden" name="dutyId" value="${log.ID }"/>
				</td>
				<td>
					<input type="text" value="${log.Title }" disabled="disabled"/>
				</td>
				<td class="title">值班日期：</td>
				<td>
					<input type="text" value="${dutyDate }" disabled="disabled"/>
				</td>
			</tr>
			<tr>
				<td class="title">前一天交班记录：</td>
				<td colspan="3">
					<textarea rows="3" cols="6" class="x2" disabled="disabled">${log.before }</textarea>
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
					<input type="text" value="${log.LeaderRegisterDate }" disabled="disabled"/>
					</td>
				</tr>
				<tr>
					<td class="title">领导交办工作：</td>
					<td colspan="3">
						<textarea rows="3" cols="6" class="x2" disabled="disabled">${log.NoticePoints }</textarea>
					</td>
				</tr>	
				<tr>
					<td class="title">带班日志：</td>
					<td colspan="3">
						<textarea rows="3" cols="6" class="x2" disabled="disabled">${log.LeaderLog }</textarea>
					</td>
				</tr>	
		</table>	
		<h2>当天值班信息：</h2>
		<table class="formtable">
				<tr>
					<td class="title">值班人：</td>
					<td>
						<input type="text" value="${dutyer }" disabled="disabled"/>
					</td>
					<td class="title">登记时间：</td>
					<td>
						<input class="easyui-datetimebox" name="DutyerRegisterDate" value="${log.DutyerRegisterDate }" validator="{'default':'*','type':'datetimebox'}" data-options="editable:false"/> <dfn>*</dfn>
					</td>
				</tr>
				<tr>
					<td class="title">交班记录：</td>
					<td colspan="3">
						<textarea rows="3" cols="6" class="x2" name="Advices" validator="{'length':'0-500'}">${log.Advices }</textarea>
					</td>
				</tr>	
				<tr>
					<td class="title">值班日志：</td>
					<td colspan="3">
						<textarea rows="3" cols="6" class="x2" name="dutyLog" validator="{'length':'0-500'}">${log.DutyLog }</textarea>
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