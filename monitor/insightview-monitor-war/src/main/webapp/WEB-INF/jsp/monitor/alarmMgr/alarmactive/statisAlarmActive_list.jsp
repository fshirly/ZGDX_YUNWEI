<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
  <link rel="stylesheet" type="text/css"  href="${pageContext.request.contextPath}/style/css/base.css" />
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/css/reset.css" /> 
  <script type="text/javascript" src="${pageContext.request.contextPath}/fui/fui.min.js"></script>
  <script type="text/javascript" src="${pageContext.request.contextPath}/js/common/commonUtil.js"></script>
  <script type="text/javascript" src="${pageContext.request.contextPath}/js/common/base64.js"></script>
  <script type="text/javascript" src="${pageContext.request.contextPath}/plugin/easyui/jquery.easyui.min.js"></script> 
  <script type="text/javascript" src="${pageContext.request.contextPath}/js/common/easyui-lang-zh_CN.js"></script> 
  <script type="text/javascript" src="${pageContext.request.contextPath}/js/monitor/alarmMgr/alarmactive/statisAlarmActive_list.js"></script>
</head>
<body>
	 <input type="hidden" id="ciId" value="${ciId}" />
	 <input type="hidden" id="proUrl" value="${proUrl}" />
	 <input type="hidden" id="filtername" value="${filtername}" />
	 <input type="hidden" id="filterId" value="${filterId}" />
	 <input type="hidden" id="timeBegin1" value="${timeBegin}" />
	 <input type="hidden" id="timeEnd1" value="${timeEnd}" />
	 <input type="hidden" id="alarmStatus1" value="${alarmStatus}" />
	 <input type="hidden" id="alarmOrderVersion" value="${alarmOrderVersion }" />

	 <div class="rightContent">
 		 <div class="location">当前位置：运行监测&gt;&gt; 告警管理&gt;&gt;<span>活动告警</span></div>
  		 <div class="conditions" id="divFilter">
		 <table >
			<tr>
					<td>
						<b>告警状态：</b>
						<select id="alarmOperateStatus" name="alarmStatus" class="easyui-combobox">
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
							<option value="<c:out value='${vo.alarmLevelID}' />" 
									<c:if test="${vo.alarmLevelID eq alarmLevel}">selected="selected"</c:if>
							><c:out value="${vo.alarmLevelName}" /></option>	
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

