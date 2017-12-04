<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file = "../../../common/pageincluded.jsp" %>
<html>
  <head>
  </head>
  <body>
	        <script type="text/javascript" src="${pageContext.request.contextPath}/js/monitor/alarmMgr/alarmactive/alarmActive.js"></script>
			<!--告警清除  -->
			<input id="alarmID" type="hidden" value="${alarmVo.alarmID}"/>
			<table  class="formtable">
			<tr>
				<td class="title">告警内容：</td>
				<td >${alarmVo.alarmContent}</td>
			</tr>
			<tr>
				<td class="title">发生时间：</td>
				<td >${alarmVo.startTime}</td>
			</tr>
			<tr>
				<td class="title">告警级别：</td>
				<td >${alarmVo.alarmLevelName}</td>
			</tr>
			<tr>
				<td class="title">处理结论：</td>
				<td>
					<textarea rows="3" id="cleanInfo" name="cleanInfo" maxlength="128"></textarea>
				</td>
			</tr>
		</table>
		<div class="conditionsBtn">
			<a href="javascript:doProClear();"  id="btnSave" >确定</a>
			<a href="javascript:void(0);" onclick="javascript:$('#popWin').window('close');" id="btnBack" >取消</a>
		</div>
  </body>
</html>
