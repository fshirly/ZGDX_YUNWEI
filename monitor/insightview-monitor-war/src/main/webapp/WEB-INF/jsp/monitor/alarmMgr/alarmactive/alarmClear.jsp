<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file = "../../../common/pageincluded.jsp" %>
<html>
  <head>
  </head>
  <body>
	        <script type="text/javascript" src="${pageContext.request.contextPath}/js/monitor/alarmMgr/alarmactive/alarmActive.js"></script>
			<!--告警清除  -->
			<input id="cleanid" type="hidden" value="${id}"/>
			<input id="cleanflag" type="hidden" value="${flag}"/>
			<input id="hiddenUserName" type="hidden" value="${userName}"/>
			<table  class="formtable">
			<tr>
				<td class="title">处理结论：</td>
				<td>
					<textarea rows="3" id="shouyecleanInfo" name="shouyecleanInfo" maxlength="128"></textarea>
				</td>
			</tr>
		</table>
		<div class="conditionsBtn">
			<a href="javascript:doAlarmClear();"  id="btnSave" >确定</a>
			<a href="javascript:void(0);" onclick="javascript:$('#popWin3').window('close');$('#winAlarmSendSingleStep1').panel('destroy');$('#winAlarmSendSingleStep2').panel('destroy');" id="btnBack" >取消</a>
		</div>
  </body>
</html>
