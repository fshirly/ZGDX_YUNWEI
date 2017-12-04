<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ include file = "../../../common/pageincluded.jsp" %>

<html>
  <head>
  	<title>新增维护期策略</title>
  </head>
  <body>
  		<script type="text/javascript"  src="${pageContext.request.contextPath}/js/monitor/alarmMgr/maintenanceploicy/maintenancePloicyAdd.js" ></script>
		<%String ploicyID = (String)request.getAttribute("ploicyID"); %>
			<!-- 新增维护期策略 -->
			<div id="divAddInfo" >
			<input id="ipt_ploicyID" type="hidden"  value="<%=ploicyID %>" />
			<table id="tblEdit" class="formtable1">
				<tr>
					<td class="title">维护期标题：</td>
					<td colspan="3">
						<input id="ipt_maintainTitle"  type="text" validator="{'default':'*' ,'length':'1-20'}" onblur="checkNameUnique();" /><b>*</b>
					</td>				
				</tr>
				<tr>
					<td class="title">事件源对象：</td>
					<td colspan="3">
						<input id="ipt_moid" type="hidden" /><input id="ipt_moname"  readonly="readonly" validator="{'default':'*'}"  onclick="javascript:loadMoRescource();"/><b>*</b>
					</td>				
				</tr>
				<tr>
					<td class="title">维护期类型：</td>
					<td colspan="3">
						<select id="ipt_maintainType" validator="{'default':'*'}" >
							<option></option>
							<option value="1">新建</option>
							<option value="2">割接</option>
							<option value="3">故障</option>
						</select><b>*</b>
					</td>				
				</tr>
				<tr>
					<td class="title">维护起止时间：</td>
					<td colspan="3">
						<input id="ipt_startTime"  class="easyui-datetimebox" validator="{'default':'*','type':'datetimebox'}" /><b>*</b>&nbsp;-&nbsp;
						<input id="ipt_endTime"  class="easyui-datetimebox" validator="{'default':'*','type':'datetimebox'}"  /><b>*</b>
					</td>				
				</tr>
				<tr>
					<td class="title">启用状态：</td>
					<td colspan="3"><input id="ipt_isUsed" class="input" type="hidden"/>
						<input name="isUsed"  type="radio" value="1" checked  />&nbsp;启用
						<input name="isUsed"  type="radio" value="0"   />&nbsp;停用
					</td>				
				</tr>
				<tr>
					<td class="title">维护期描述：</td>
					<td colspan="3">
						<textarea id="ipt_descr"  rows="3"  class="x2"></textarea>
					</td>
				</tr>
			</table>		
		<div class="conditionsBtn">
			<input class="buttonB" type="button" id="btnSave" value="确定" onclick="javascript:toAdd();"/>
			<input class="buttonB" type="button" id="btnUpdate" value="取消" onclick="javascript:$('#popWin').window('close');"/>
		</div>
	</div>
	<div id='event_select_dlg' class='event_select_dlg'></div>	
  </body>
</html>
