<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file = "../../../common/pageincluded.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<title>维护期策略修改</title>

</head>
<body>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/monitor/alarmMgr/maintenanceploicy/maintenancePloicyModify.js"></script>
			<!-- end .datas -->
	<div id="divModifyInfo" >
	<input id="ipt_ploicyID" type="hidden" value="${alarmVo.ploicyID}"/>
		<table id="tblEdit" class="formtable1">
				<tr>
					<td class="title">维护期标题：</td>
					<td colspan="3">
						<input id="ipt_maintainTitle"  type="text" value="${alarmVo.maintainTitle}"  validator="{'default':'*' ,'length':'1-20'}" onblur="checkNameUnique2();"/><b>*</b>
					</td>			
				</tr>
				<tr>
					<td class="title">事件源对象：</td>
					<td colspan="3">
						<input id="ipt_moid" type="hidden"  value="${alarmVo.moid}" />
						<input id="ipt_moname"  value="${alarmVo.moname}" readonly="readonly" /><b>*</b>
					</td>						
				</tr>
				<tr>
					<td class="title">维护期类型：</td>
					<td>
						<select id="ipt_maintainType" validator="{'default':'*'}">
							<option value="1" <c:if test="${alarmVo.maintainType==1}" >selected</c:if> >新建</option>
							<option value="2" <c:if test="${alarmVo.maintainType==2}" >selected</c:if>>割接</option>
							<option value="3" <c:if test="${alarmVo.maintainType==3}" >selected</c:if>>故障</option>
						</select><b>*</b>
					</td>				
				</tr>
				<tr>
					<td class="title">维护起止时间：</td>
					<td>
						<input id="ipt_startTime"  class="easyui-datetimebox"  name="startTime"  value="${alarmVo.startTime}" /><b>*</b>
						<input id="ipt_endTime"    class="easyui-datetimebox"  name="endTime"    value="${alarmVo.endTime }" /><b>*</b>
					</td>				
				</tr>
				<tr>
					<td class="title">启用状态：</td>
					<td colspan="3"><input id="ipt_isUsed" class="input" type="hidden"/>
						<input  type="radio" name="isUsed" value="1" <c:if test="${alarmVo.isUsed==1}" >checked</c:if>  />&nbsp;启用
						<input  type="radio" name="isUsed" value="0" <c:if test="${alarmVo.isUsed==0}" >checked</c:if>  />&nbsp;停用
					</td>				
				</tr>
				<tr>
					<td class="title">维护期描述：</td>
					<td>
						<textarea id="ipt_descr"   rows="3">${alarmVo.maintainDesc}</textarea>
					</td>
				</tr>
		</table>
		<div class="conditionsBtn">
			<input class="buttonB" type="button" id="btnSave" value="确定" onclick="javascript:toUpdate();"/>
			<input class="buttonB" type="button" id="btnClose" value="取消" onclick="javascript:$('#popWin').window('close');"/>
		</div>
	</div>
		
</body>
</html>