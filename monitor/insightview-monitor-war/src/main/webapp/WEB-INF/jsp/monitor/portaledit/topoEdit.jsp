<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file = "../../common/pageincluded.jsp" %>
<html>
	<head>
	
	</head>

	<body>
	<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/monitor/portaledit/topoEdit.js"></script>
			<!-- end .datas -->
		<table id="tblEdit" class="formtable1">
		<input type="hidden" id="widgetId" name="widgetId" value="${widgetId}" />
		<input type="hidden" id="widgetName" name="widgetName" value="${widgetName}" />
		<input type="hidden" id="url" name="url" value="${url}"/>
		<input type="hidden" id="topoId" name="topoId" value="${topoId}"/>
		
			<tr>
				<td class="title">标题名称：</td>
				<td><input id="widgetTitle"  type="text" value="${widgetTitle}" /></td>				
			</tr>
			<tr>
				<td class="title">拓扑名称：</td>
				<td>
					<select name="topoSel" id="topoSel">
						<c:forEach items="${topoLst}" var="entry">
								<option value="<c:out value='${entry.id}' />" <c:if test="${entry.id == topoId}">selected="selected"</c:if> ><c:out value="${entry.topoName}" /></option>
						</c:forEach>
					</select>
				</td>				
			</tr>
		</table>
		<div class="conditionsBtn">
			<input class="buttonB" type="button" id="btnSave" value="确定" onclick="javascript:doEditTopo();"/>
			<input class="buttonB" type="button" id="btnClose" value="取消" onclick="javascript:$('#popView').window('close');"/>
		</div>
		
	</body>
</html>
