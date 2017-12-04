<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file = "../../../common/pageincluded.jsp" %>
<html>
  <head>
  </head>
  <body>
	        <script type="text/javascript" src="${pageContext.request.contextPath}/js/monitor/alarmMgr/alarmactive/roomAlarm.js"></script>
			<!--告警确认  -->
			<input id="id" type="hidden" value="${id}"/>
			<table class="formtable">
			<tr>
				<td class="title">确认意见：</td>
				<td >
					<textarea rows="3" id="confirmInfo" name="confirmInfo" maxlength="128"></textarea>
				</td>
			</tr>
		</table>
		<div class="conditionsBtn">
			<a href="javascript:doProBathConfirm();"  id="btnSave" >确定</a>
			<a href="javascript:void(0);" onclick="javascript:$('#popWin').window('close');" id="btnBack" >取消</a>
		</div>
  </body>
</html>
