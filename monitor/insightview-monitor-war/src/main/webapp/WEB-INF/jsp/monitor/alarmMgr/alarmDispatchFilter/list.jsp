<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file = "../../../common/pageincluded.jsp" %>
<html>
  <head>
  <!-- mainframe -->
	<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/monitor/alarmMgr/alarmDispatchFilter/list.js"></script>
  </head>
  <body>
		<div class="rightContent">
		<div class="location">当前位置：${navigationBar}</div>
		<div class="conditions" id="divFilter">
			<table>
				<tr>
					<td>
						<b>规则名称：</b>				
						<input type="text"  id="cfgName" name="name"/>
					</td>
					<td class="btntd">
						 <a href="javascript:void(0);" onclick="reloadAllTable();" >查询</a>		
						 <a href="javascript:void(0);" onclick="resetForm('divFilter');">重置</a> 				    	 
					</td>
			</tr>
			</table>
		</div>
		
		<!-- begin .datas -->
		<div class="datas">
			<table id="tblDataList">
			</table>
		</div>
		<!-- end .datas -->
			
	</div>
  </body>
</html>
