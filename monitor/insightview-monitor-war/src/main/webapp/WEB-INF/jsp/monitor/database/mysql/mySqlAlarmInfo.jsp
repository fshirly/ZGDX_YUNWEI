<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="../../../common/pageincluded.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/js/monitor/database/mysql/mySqlAlarmInfo.js"></script>
<%
		String moId = (String)request.getAttribute("moId");
	 %>
	</head>

	<body>
		<input id="liInfo" value="${liInfo}" type="hidden"/>
	<input type="hidden" id="moId" value="<%=moId %>"/>
	<div class="rightContent">
  		 <div class="conditions" id="divFilter">
		 <table>
				<tr>					
					<td>&nbsp;&nbsp;&nbsp;
						最近
						<select id="alarmNum"  onchange="reloadTable();" style="width: 60px;">
							<option value="5" >5</option>
							<option value="10">10</option>
							<option value="20">20</option>
						</select>&nbsp;条活动告警
					</td>					
				</tr>
		</table>
		</div>	
		<div class="datas tops1">
			<table id="tblAlarmActive">
			</table>
		</div>
  </div>
	</body>
</html>
