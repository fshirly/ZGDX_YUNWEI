<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file = "../../common/pageincluded.jsp" %>

<html>
	<head>
		<script type="text/javascript"	src="${pageContext.request.contextPath}/js/monitor/component/modbmsserverList.js"></script>
		<script type="text/javascrict"> 
			<%String flag=(String)request.getAttribute("flag");%>
			<%String index=(String)request.getAttribute("index");%>
		</script>
	</head> 
	<body> 
	    <div class="rightContent">
		<div class="location">当前位置：运行监测&gt;&gt;运维平台 &gt;&gt; <span>数据库服务管理</span></div>
		<div class="conditions" id="divFilter">
			<input type="hidden" id="moClassId" name="moClassId" value="${moClassId}" />	
			<input id="flag" type="hidden" value="<%=flag%>"/>
			<table>
			  <tr>
			    <td>
			      <b>IP访问地址：</b>
			      <input type="text" class="inputs" id="ip" />
			    </td>
			    <td class="btntd">
			    	<a href="javascript:void(0);" onclick="reloadTable();">查询</a>
				    <a href="javascript:void(0);" onclick="resetForm('divFilter');">重置</a>
			    </td>
			  </tr>
			</table>
		</div> 
		<div class="datas">
			<table id="tblDBMSServer">
			</table>
		</div> 
	</body>
</html>