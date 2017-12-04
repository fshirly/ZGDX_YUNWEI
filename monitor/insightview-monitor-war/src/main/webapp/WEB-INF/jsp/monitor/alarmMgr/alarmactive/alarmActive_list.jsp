<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file = "../../../common/pageincluded.jsp" %>
<html>
<head>
	<!-- mainframe --> 
</head>
<body>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/monitor/alarmMgr/alarmactive/alarmActive_list.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/monitor/alarmMgr/alarmactive/jquery.mockjax.js"></script>
	<input type="hidden" id="moid" value="${moid}" />
	<input type="hidden" id="host" value="${host}" />
	<input type="hidden" id="tzType" value="${tzType}" />
	<input type="hidden" id="tzResType" value="${tzResType}" />
	<input type="hidden" id="tzDeviceIP" value="${tzDeviceIP}" />
	<input type="hidden" id="tzTimeBegin" value="${tzTimeBegin}" />
	<input type="hidden" id="tzTimeEnd" value="${tzTimeEnd}" />
	<input type="hidden" id="moclassID" value="${moclassID}" />
	 <input type="hidden" id="neManufacturerID" value="${neManufacturerID}" />
	 <input type="hidden" id="filtername" value="${filtername}" />
	 <input type="hidden" id="filterId" value="${filterId}" />
	 <input type="hidden" id="alarmOrderVersion" value="${alarmOrderVersion }" />
	 <div class="rightContent">
 		 <div class="location">当前位置：${navigationBar}</div>
  		 <div class="conditions" id="divFilter">
		 <table >
			<tr>
					<td>
						<b>告警状态：</b>
						<select id="alarmOperateStatus" name="alarmOperateStatus" class="easyui-combobox">
							<option value="">全部</option>
						    <c:forEach items="${statusList}" var="vo">
							<option value="<c:out value='${vo.statusID}' />" ><c:out value="${vo.statusName}" /></option>	
							</c:forEach>
						</select>
					</td>
					<td>
						<b>告警级别：</b>
						<select id="alarmLevel" name="alarmLevel" class="easyui-combobox">
							<option value="">全部</option>
						    <c:forEach items="${levelList}" var="vo">
							<option value="<c:out value='${vo.alarmLevelID}' />" ><c:out value="${vo.alarmLevelName}" /></option>	
							</c:forEach>
						</select>
					</td>
					<td colspan="2">
						<b>首次发生时间：</b>
						<input class="easyui-datetimebox" id="timeBegin" name="timeBegin" /> 
						- <input class="easyui-datetimebox" id="timeEnd" name="timeEnd" />
					</td>
				</tr>
				<tr>
					<td>
					    <b>告警标题：</b>
						<input type="text" id="alarmTitle" name="alarmTitle"/>
					</td>
					<td>
						<b>告警名称： </b>
						<input type="text" id="moName" name="moName"/>
					</td>
				  <td>
						<b>告警源IP： </b>
						<input type="text" id="sourceMOIPAddress" name="sourceMOIPAddress"/>
					</td>
					<td>&nbsp;&nbsp;&nbsp;
						<b>告警类型：</b>
						<select id="alarmType" name="alarmType" class="easyui-combobox">
							<option value="">全部</option>
							<c:forEach items="${typeList}" var="vo">
							<option value="<c:out value='${vo.alarmTypeID}' />" ><c:out value="${vo.alarmTypeName}" /></option>	
							</c:forEach>
						</select>
					</td>
					<td class="btntd">
					   	<a href="javascript:void(0);" onclick="reloadTable();" >查询</a>		
						<a href="javascript:void(0);" onclick="resetFilterForm('divFilter');">重置</a>
					</td>
				</tr>
	</table>
	</div>	
		<!-- begin .datas -->
		<div class="datas tops2">
			<table id="tblDataList">
			</table>
		</div>
		<!-- end .datas -->		
  </div>
    <div id="popWin"></div>
	<div id="popWin2"></div>
	<div id="popWin3"></div>
</body>
</html>