<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file = "../../common/pageincluded.jsp" %>
<html>
	<head>
	
	</head>

	<body>
	<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/monitor/portaledit/alarmEdit.js"></script>
			<!-- end .datas -->
		<table id="tblEdit" class="formtable1">
		<input type="hidden" id="widgetId" name="widgetId" value="${widgetId}" />
		<input type="hidden" id="widgetName" name="widgetName" value="${widgetName}" />
		<input type="hidden" id="url" name="url" value="${url}"/>
		<input type="hidden" id="moClass" name="moClass" value="${moClass}"/>
		
			<tr>
				<td class="title">标题名称：</td>
				<td><input id="widgetTitle"  type="text" value="${widgetTitle}" validator="{'default':'*','length':'1-100'}" /><b>*</b></td>				
			</tr>
			<tr>
					<td class="title">设备类型：</td>
					<td>
						<select name="deviceName" id="deviceName">
						  <option value="mo">所有设备</option>
							<c:forEach items="${mobLst}" var="entry">
									<option <c:if test="${moClass == entry.className}">selected</c:if>
									value="<c:out value='${entry.className}' />">
									<c:out value="${entry.classLable}" />
									</option>
							</c:forEach>
						</select>
					</td>				
				</tr>
				<tr>
					<td class="title">排名：</td>
					<td>
					<input id="num"  type="text" value="${num}" validator="{'default':'*' ,'length':'1-5'}"/><b>*</b>
					</td>				
				</tr>
		</table>
		<div class="conditionsBtn">
			<input class="buttonB" type="button" id="btnSave" value="确定" onclick="javascript:doCommit();"/>
			<input class="buttonB" type="button" id="btnClose" value="取消" onclick="javascript:$('#popView').window('close');"/>
		</div>
		
	</body>
</html>
