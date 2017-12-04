<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file = "../../common/pageincluded.jsp" %>

<html>
  <head>
  </head>
  
  <body>
	<script type="text/javascript"	src="${pageContext.request.contextPath}/js/platform/ipmanager/orgUseSubNetInfo.js"></script>
	<script type="text/javascript"	src="${pageContext.request.contextPath}/js/permission/LRSelect.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/plugin/dTree/dtree.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/plugin/dTree/combo/comboJS.js"></script>
	<%	
		String organizationId=(String)request.getAttribute("organizationId");
    %>
	<input id="organizationId" type="hidden" value="<%= organizationId%>"/>
	<div class="rightContent">
	  	<div class="conditions" id="divFilter">
	  	  <table>
	  	  	<tr>
	  	  	  <td>
				  <b>部门名称：</b>
				  <input type="text" id="txtDeptName" />
			  </td>
			  
			  <td class="btntd">
				  <a href="javascript:void(0);"	onclick="reloadTable();">查询</a>
				  <a href="javascript:void(0);" onclick="resetForm('divFilter');">重置</a>
			  </td>
	  	  	</tr>
	  	  </table>
	  	</div>
	  	
	  	<div class="datas tops1">
			<table id="tblOrgUseSubNetList">
			</table>
		</div>
	</div>
  </body>
</html>