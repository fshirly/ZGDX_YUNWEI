<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file = "../../../common/pageincluded.jsp" %>
<html>
  <head>
    <script type="text/javascript"  src="${pageContext.request.contextPath}/js/monitor/alarmMgr/alarmcategory/alarmCategory_list.js" ></script>
  </head>
  
  <body>
  	<div class="rightContent">
		<div class="location">
			当前位置：${navigationBar}
		</div>
		<div class="conditions" id="divFilter">
		<input type="hidden" id="categoryID" name="categoryID" value="${categoryID}" />	
			<table>
				<tr>
					<td>
						<b>分类名称：</b>
						<input type="text" class="inputs" id="textCategoryName" />
					</td>
					<td>
						<b>SNMP企业私有ID：</b>
						<input type="text" class="inputs" id="textAlarmOID" />
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
			<table id="tblAlarmCategoryList"></table>
		</div>
		
		
	</div>		
  </body>
</html>
