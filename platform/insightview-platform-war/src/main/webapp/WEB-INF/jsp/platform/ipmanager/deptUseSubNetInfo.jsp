<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file = "../../common/pageincluded.jsp" %>

<html>
  <head>
  </head>
  
  <body>
	<script type="text/javascript"	src="${pageContext.request.contextPath}/js/platform/ipmanager/deptUseSubNetInfo.js"></script>
	<script type="text/javascript"	src="${pageContext.request.contextPath}/js/permission/LRSelect.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/plugin/dTree/dtree.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/plugin/dTree/combo/comboJS.js"></script>
	<%	
		String deptId=(String)request.getAttribute("deptId");
    %>
    <input id="deptId" type="hidden" value="<%= deptId%>"/>
	<div class="rightContent">
	  	<div class="conditions" id="divFilter">
	  	  <table>
	  	  	<tr>
	  	  	  <td colspan="2">
	  	  	      <b>筛选地址范围：</b>
	  	  		  <input id="txtStartIp" type="text"/> - 
	    		  <input id="txtEndIp" type="text"/>
	  	  	  </td>
	  	  		
	  	  	  <td>
				  <b>子网地址：</b>
				  <input class="easyui-combobox" id="txtSubNetId"/>
			  </td>
			 </tr>
			 <tr>
			  <td>&nbsp;&nbsp;&nbsp;
				  <b>状态：</b>
				  <select class="easyui-combobox" id="txtStatus">
					<option value="-1">全部</option>
					<option value="2">预占</option>
					<option value="3">占用</option>
				  </select>
			  </td>
			  <td></td>
			  <td class="btntd">
				  <a href="javascript:void(0);"	onclick="reloadTable();">查询</a>
				  <a href="javascript:void(0);" onclick="resetForm('divFilter');">重置</a>
			  </td>
	  	  	</tr>
	  	  </table>
	  	</div>
	  	
	  	<div class="datas tops3">
			<table id="tblDeptUseSubNetList">
			</table>
		</div>
	</div>
	
  </body>
</html>