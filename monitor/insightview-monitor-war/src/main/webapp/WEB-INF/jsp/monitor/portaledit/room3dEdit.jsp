<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file = "../../common/pageincluded.jsp" %>

<html>
	<head>

	</head>

	<body>
    <script type="text/javascript"
	src="${pageContext.request.contextPath}/js/monitor/portaledit/room3dEdit.js"></script>
	
		<table id="tblEdit" class="formtable1">
		<input type="hidden" id="widgetId" name="widgetId" value="${widgetId}" />
		<input type="hidden" id="widgetName" name="widgetName" value="${widgetName}" />
		<input type="hidden" id="url" name="url" value="${url}"/>

		
			<tr>
				<td class="title">标题名称：</td>
				<td><input id="widgetTitle"  type="text" value="${widgetTitle}" /></td>				
			</tr>
			<tr>
				<td class="title">机房名称：</td>
				<td>
					<select name="roomId" id="roomId">
						<c:forEach items="${roomList}" var="entry">
								<option value="<c:out value='${entry.ciId}' />" <c:if test="${entry.ciId == roomId}">selected="selected"</c:if> ><c:out value="${entry.name}" /></option>
						</c:forEach>
					</select>
				</td>				
			</tr>
			<tr>
				<td class="title">机房视图：</td>
				<td>
					<select name="roomView" id="roomView">
						
					 <option value="alarmView" <c:if test="${viewType == 'alarmView'}">selected="selected"</c:if> >告警视图</option>
					 <option value="spaceView" <c:if test="${viewType == 'spaceView'}">selected="selected"</c:if> >空间利用率视图</option>
					 <option value="assetsView" <c:if test="${viewType == 'assetsView'}">selected="selected"</c:if> >资产视图</option>
					 <option value="temperatureView" <c:if test="${viewType == 'temperatureView'}">selected="selected"</c:if> >动环视图</option>
						
					</select>
				</td>				
			</tr>
			<tr id="temTr">
				<td class="title">动环视图：</td>
				<td>
					<select name="temperView" id="temperView" >
						
					 <option value="TemperatureTag" <c:if test="${drivceType == 'TemperatureTag'}">selected="selected"</c:if> >温度视图</option>
					 <option value="WaterHoseTag" <c:if test="${drivceType == 'WaterHoseTag'}">selected="selected"</c:if> >水带视图</option>
					 <option value="TemperatureHumidityTag" <c:if test="${drivceType == 'TemperatureHumidityTag'}">selected="selected"</c:if> >湿度视图</option>
					 <option value="DoorMagneticTag" <c:if test="${drivceType == 'DoorMagneticTag'}">selected="selected"</c:if> >门禁视图</option>
					 <option value="DryContactTag" <c:if test="${drivceType == 'DryContactTag'}">selected="selected"</c:if> >干节点视图</option>
					</select>
				</td>				
			</tr>
		</table>
		<div class="conditionsBtn">
			<input class="buttonB" type="button" id="btnSave" value="确定" onclick="javascript:doRoom3dEdit();"/>
			<input class="buttonB" type="button" id="btnClose" value="取消" onclick="javascript:$('#popView').window('close');"/>
		</div>
		
	</body>
</html>
