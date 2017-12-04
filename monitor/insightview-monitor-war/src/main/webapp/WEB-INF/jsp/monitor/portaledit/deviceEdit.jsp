<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file = "../../common/pageincluded.jsp" %>
<html>
	<head>
	
	</head>

	<body>
	<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/monitor/portaledit/deviceEdit.js"></script>
			<!-- end .datas -->
		<table id="tblEdit" class="formtable1">
		<input type="hidden" id="timeBegin" name="timeBegin"  />
		<input type="hidden" id="timeEnd" name="timeEnd"  />
		<input type="hidden" id="widgetId" name="widgetId" value="${widgetId}" />
		<input type="hidden" id="widgetName" name="widgetName" value="${widgetName}" />
		<input type="hidden" id="moClassID" name="moClassID" />
		<input type="hidden" id="url" name="url" value="${url}"/>
		
			<tr>
				<td class="title">标题名称：</td>
				<td><input id="widgetTitle"  type="text" value="${widgetTitle}" validator="{'default':'*' ,'length':'1-100'}"/><b>*</b></td>						
			</tr>
			<tr>
					<td class="title">设备名称：</td>
					<td>
						<select name="deviceName" id="deviceName" onchange="selDeviceName()">
							<c:forEach items="${deviceLst}" var="entry">
									<option value="<c:out value='${entry.moid}' />"><c:out value="${entry.moname}" /></option>
							</c:forEach>
						</select>
					</td>				
				</tr>
				<tr>
					<td class="title">时间周期：</td>
					<td>
					
						<select id="timeTemplate" name="timeTemplate" onchange="changeTime()">
							<option value="1" selected="selected">最近半小时</option>
							<option value="2" >最近1小时</option>
							<option value="3" >最近2小时</option>
							<option value="4" >最近4小时</option>
							<option value="5" >最近6小时</option>
							<option value="6" >最近12小时</option>
							<option value="7" >最近24小时</option>
							<option value="8" >今天</option>
							<option value="9" >昨天</option>			
							<option value="10" >本周</option>
							<option value="11">七天</option>
							<option value="12">本月</option>
							<option value="13">上月</option>
				</select>
					</td>				
				</tr>
		</table>
		<div class="conditionsBtn">
			<input class="buttonB" type="button" id="btnSave" value="确定" onclick="javascript:doCommit();"/>
			<input class="buttonB" type="button" id="btnClose" value="取消" onclick="javascript:$('#popView').window('close');"/>
		</div>
		
	</body>
</html>
