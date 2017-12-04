<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file = "../../common/pageincluded.jsp" %>

<html>
	<head>
		<script type="text/javascript"	src="${pageContext.request.contextPath}/js/monitor/component/monetworkIfList.js"></script>
		<script type="text/javascrict"> 
			<%String flag=(String)request.getAttribute("flag");%>
		</script>
	</head> 
	<body> 
	    <div class="rightContent">
		<div class="location">当前位置：系统管理&gt;&gt;运维平台 &gt;&gt; <span>接口管理</span></div>
		<div class="conditions" id="divFilter">
			<input type="hidden" id="deviceMOID" name="deviceMOID" value="${deviceMOID}" />	
			<input id="flag" type="hidden" value="<%=flag%>"/>
			<table>
			  <tr>
			    <td>
			      <b>接口名称：</b>
			      <input type="text" class="inputs" id="txtIfName" />
			    </td>
			    <td class="btntd">
			    	<a href="javascript:void(0);" onclick="reloadTable();">查询</a>
				    <a href="javascript:void(0);" onclick="resetForm('divFilter');">重置</a>
			    </td>
			  </tr>
			</table>
		</div> 
		<div class="datas">
			<table id="tblMONetworkIf">
			</table>
		</div> 
	</body>
</html>