<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file = "../../common/pageincluded.jsp" %>

<html>
	<head>
		<script type="text/javascript"	src="${pageContext.request.contextPath}/js/monitor/discover/deviceInfoList.js"></script>
		<script type="text/javascrict"> 
			<%String flag=(String)request.getAttribute("flag");%>
			<%String index=(String)request.getAttribute("index");%>
		</script>
	</head> 
	<body> 
	    <div class="rightContent">
	    
	    
		<div class="location">当前位置：系统管理&gt;&gt;运维平台 &gt;&gt; <span>设备管理</span></div>
		<div>
			<input type="hidden" id="devicetype" name="devicetype" value="${devicetype}" />
			<input type="hidden" id="taskId" name="taskId" value="${taskId}" />
			<input type="hidden" id="moClassId" name="moClassId" value="${moClassId}" />	
			<input type="hidden" id="deviceType1" name="deviceType1" value="${deviceType1}" />
			<input id="flag" type="hidden" value="<%=flag%>"/>
			<input id="index" type="hidden" value="<%=index%>"/>
		<div class="conditions" id="divFilter">
			<table>
			  <tr>
			    <td>
			      <b>设备IP：</b>
			      <input type="text" class="inputs" id="deviceip" />
			    </td>
			    <td class="btntd">
			    	<a href="javascript:void(0);" onclick="reloadTable();">查询</a>
				    <a href="javascript:void(0);" onclick="resetForm('divFilter');">重置</a>
			    </td>
			  </tr>
			</table>
		</div> 
		<div class="datas" id="divDevice">
		</div><%--
		<div class="datas" id="divDeviceMore">
			<table id="tblDeviceMore">
			</table>
		</div>
		--%><div class="conditionsBtn" style="display: none" id="chooseButton">
			<input type="button" id="btnSave" value="确定" onclick="javascript:chooseMore();"/>
			<input type="button" id="btnClose" value="取消" onclick="javascript: window.close();"/>
		</div>
		</div>
	</body>
</html>