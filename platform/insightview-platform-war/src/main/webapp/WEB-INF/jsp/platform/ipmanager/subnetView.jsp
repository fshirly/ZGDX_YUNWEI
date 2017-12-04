<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file = "../../common/pageincluded.jsp" %>

<html>
  <head>
  	<script type="text/javascript"
	src="${pageContext.request.contextPath}/plugin/dTree/dtree.js"></script>
	<script type="text/javascript"
	src="${pageContext.request.contextPath}/plugin/dTree/combo/comboJS.js"></script> 	
	<script type="text/javascript"	src="${pageContext.request.contextPath}/js/platform/ipmanager/subnetView.js"></script>
  </head>
  
  <body>
  	<div class="location">当前位置：${navigationBar }</div>
	
	<div id="dataTreeDiv" class="treetable">
	
	</div>
	  <div class="treetabler">
		<iframe id="component_2" name="component_2" src="${pageContext.request.contextPath}/platform/subnetViewManager/toSubnetInfoList"  frameborder="0" style="height: 99%;"></iframe>
	 </div>
  </body>
</html>