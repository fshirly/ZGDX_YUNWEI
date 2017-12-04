<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="../../common/pageincluded.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/js/monitor/host/alarmActiveInfoList.js"></script>
<script type="text/javascript"
			src="${pageContext.request.contextPath}/js/monitor/host/commHref.js"></script>
	</head>

	<body>
	<input id="liInfo" value="${liInfo}" type="hidden"/>
		<input type="hidden" id="moClass" name="moClass" value="${moClass}" />
		<input type="hidden" id="alarmNum" name="alarmNum" value="${alarmNum}" />
		<input type="hidden" id="flag" name="flag" value="${flag}" />
			
					<div class="datas topdashboard" style="height:262px;">
						<table id="tblAlarmActive">

						</table>
					</div>
					<c:if test="${flag eq '2'}">
						<div class="butdiv">
							<a href="javascript:void(0);" id="btnSearch" class="btn" onclick="parent.parent.toShowTabsInfo('/monitor/alarmActive/toAlarmActiveList','告警列表','','RecentAlarm');">查看更多</a>
					</div>
					</c:if>
					
	</body>
</html>
