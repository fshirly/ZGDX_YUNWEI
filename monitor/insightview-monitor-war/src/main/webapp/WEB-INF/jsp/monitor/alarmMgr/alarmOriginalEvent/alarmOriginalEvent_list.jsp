<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file = "../../../common/pageincluded.jsp" %>
<html>
<head>
	<!-- mainframe --> 
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/monitor/alarmMgr/alarmOriginalEvent/alarmOriginalEvent_list.js"></script>
</head>
<body>
	 <div class="rightContent">
 		 <div class="location">当前位置：${navigationBar}</div>
  		 <div class="conditions" id="divFilter">
		 <table >
			<tr>
					<td>
						<b>事件设备名称： </b>
						<input type="text" id="sourceMoName" name="sourceMoName"/>
					</td>
					<td colspan="2">
						<b>事件发生时间：</b>
						<input class="easyui-datebox" id="timeBegin" name="timeBegin" /> 
						- <input class="easyui-datebox" id="timeEnd" name="timeEnd" />
					</td>
				</tr>
				<tr>
					<td>
						<b>设备对象名称： </b>
						<input type="text" id="moName" name="moName"/>
					</td>
				  	<td>
						<b>事件设备IP： </b>
						<input type="text" id="ipAddr" name="ipAddr" style="margin-left: 12px;"/>
					</td>
					<td class="btntd">
					   	<a href="javascript:void(0);" onclick="reloadTable();" >查询</a>		
						<a href="javascript:void(0);" onclick="reset();">重置</a>
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

</body>
</html>
