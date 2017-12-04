<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ include file = "../../common/pageincluded.jsp" %>
<html>
  <head>  
<!-- mainframe -->
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/main.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/plugin/dTree/dtree.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/plugin/dTree/combo/comboJS.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/platform/rescatedef/selectMfList.js"></script>
  </head> 
  <body>
    <div class="rightContent">
		<div class="conditions" id="divFilter">
			<table>
			  <tr>
			    <td>
			      <b>厂商名称：</b>
			      <input type="text" class="inputs" id="txtFilterManufacturerName" />
			    </td>
			    <td class="btntd">
			    	<a href="javascript:void(0);" onclick="reloadTable();">查询</a>
				    <a href="javascript:void(0);" onclick="resetForm('divFilter');">重置</a> 
			    </td>
			  </tr>
			</table>
		</div>
		
		<!-- begin .datas --><!-- 从js中读取数据 -->
		<div class="datas">
			<table id="tblManufacturer">
			</table>
		</div>
		
	</div>
  </body>
</html>
