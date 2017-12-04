<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file = "../../../common/pageincluded.jsp" %>

<html>
  <head>
    <script type="text/javascript"  src="${pageContext.request.contextPath}/js/monitor/alarmMgr/maintenanceploicy/maintenancePloicy_list.js" ></script>
  </head>
  <body>
  	<div class="rightContent">
		<div class="location">
			当前位置：${navigationBar}
		</div>
		<div class="conditions" id="divFilter">
		<input type="hidden" id="ploicyID" name="ploicyID" value="${ploicyID}" />
			<table>
				<tr>
					<td>
						<b>维护期标题：</b>
						<input type="text" class="inputs" id="textMaintainTitle" />
					</td>
					<td>
						<b>事件源对象：</b>
						<input type="text" class="inputs" id="textMOName" />
					</td>
					<td class="btntd">
						<a href="javascript:void(0);" onclick="reloadTable();">查询</a>
						<a href="javascript:void(0);" onclick="resetForm('divFilter');">重置 </a>
					</td>
				</tr>			
			</table>
		</div>
		
		<!-- begin .datas -->
		<!-- 从js中读取数据 -->
		<div class="datas">
			<table id="tblMaintenancePloicyList"></table>
		</div>		
	</div>		
  </body>
</html>
