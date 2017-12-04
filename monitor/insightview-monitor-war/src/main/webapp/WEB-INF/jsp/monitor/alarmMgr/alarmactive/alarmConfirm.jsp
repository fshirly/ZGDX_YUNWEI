<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file = "../../../common/pageincluded.jsp" %>
<html>
  <head>
  </head>
  <body>
	        <script type="text/javascript" src="${pageContext.request.contextPath}/js/monitor/alarmMgr/alarmactive/alarmActive.js"></script>
			<!--告警确认  -->
			<input id="hiddenId" type="hidden" value="${id}"/>
			<input id="shouyeflag" type="hidden" value="${flag}"/>
			<table class="formtable">
			<tr>
				<td class="title">确认意见：</td>
				<td >
					<textarea rows="3" id="shouyeconfirmInfo" name="shouyeconfirmInfo" maxlength="128"></textarea>
				</td>
			</tr>
		</table>
		<div class="conditionsBtn">
			<a href="javascript:doShouyeAlarm();"  id="btnSave" >确定</a>
			<a href="javascript:void(0);" onclick="javascript:$('#popWin3').window('close');$('#winAlarmSendSingleStep1').panel('destroy');$('#winAlarmSendSingleStep2').panel('destroy');" id="btnBack" >取消</a>
		</div>
  </body>
</html>
