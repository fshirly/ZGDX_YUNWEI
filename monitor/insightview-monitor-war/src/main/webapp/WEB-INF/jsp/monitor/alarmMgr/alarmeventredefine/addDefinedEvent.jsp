<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file = "../../../common/pageincluded.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head>
	</head>
	<body>
		<script type="text/javascript" 	src="${pageContext.request.contextPath}/js/monitor/alarmMgr/alarmeventredefine/addDefinedEvent.js"></script>
		<div class="winbox">
			<div class="conditions" id="divEventFilter">
				<table>
					<tr>
						<td>
							<b>告警名称：</b>
							<input type="text" id="txtAlarmName"/>							
						</td>
						<td>
							<b>告警类型：</b>
							<select id="txtAlarmType" class="easyui-combobox" data-options="editable:false">
							<option value="-1">全部</option>
						    <c:forEach items="${typeList}" var="vo">
							<option value="<c:out value='${vo.alarmTypeID}' />" ><c:out value="${vo.alarmTypeName}" /></option>	
							</c:forEach>
						</select>						
						</td>
						<td>
							<b>告警分类：</b>
							<select id="txtAlarmCategory" class="easyui-combobox" data-options="editable:false">
							<option value="-1">全部</option>
						    <c:forEach items="${categoryList}" var="vo">
							<option value="<c:out value='${vo.categoryID}' />" ><c:out value="${vo.categoryName}" /></option>	
							</c:forEach>
						</select>						
						</td>
						<td class="btntd">
							<a onclick="reloadTable();">查询</a>
							<a onclick="resetEventFrom();">重置</a> 
						</td>
					</tr>
				</table>
			</div>
			<div class="datas">
				<table id="tblAlarmEventList">
				</table>
			</div>
			
			<div class="conditionsBtn">
			<a onclick="javascript:doAddDefinedEvent();">确定</a>
            <a onclick="javascript:$('#popWin3').window('close');">关闭</a>
			</div> 
		</div>
	</body>
</html>
