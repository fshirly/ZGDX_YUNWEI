<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file = "../../../common/pageincluded.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<title>告警类型</title>

</head>
<body>
	<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/monitor/alarmMgr/alarmtype/alarmTypeModify.js"></script>
			<!-- end .datas -->
	<input id="ipt_alarmTypeID" type="hidden" value="${alarmVo.alarmTypeID}"/>
		<table id="tblEdit" class="formtable1">
			<tr>
				<td class="title">类型名称：</td>
				<td><input id="ipt_alarmTypeName"  type="text"  validator="{'default':'*' ,'length':'1-20'}" 
					value="${alarmVo.alarmTypeName}"  onblur="checkNameUnique2();" /><b>*</b></td>				
			</tr>
		</table>
		<div class="conditionsBtn">
			<input class="buttonB" type="button" id="btnSave" value="确定" onclick="javascript:doUpdate();"/>
			<input class="buttonB" type="button" id="btnClose" value="取消" onclick="javascript:$('#popWin').window('close');"/>
		</div>
		
</body>
</html>