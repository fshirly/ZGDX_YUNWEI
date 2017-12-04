<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file = "../../common/pageincluded.jsp" %>

<html>
  <head>
  	<script type="text/javascript"
	src="${pageContext.request.contextPath}/plugin/dTree/dtree.js"></script>
	<script type="text/javascript"
	src="${pageContext.request.contextPath}/plugin/dTree/combo/comboJS.js"></script> 	
	<script type="text/javascript"	src="${pageContext.request.contextPath}/js/monitor/device/deviceManager_list.js"></script>
  </head>
  
  <body>
  	<!--<div class="location">当前位置：运行监测&gt;&gt; 监测对象&gt;&gt; <span>设备列表</span></div>
	-->
	<div id="tabs_window" class="easyui-tabs viewtabs">
	  <div title="监测对象" >
		<input type="hidden" id="navigationBar" value="${navigationBar}"/>
	  <div id="dataTreeDiv" class="treetable">
	
	</div>
	  <div class="treetabler">
		<iframe id="component_2" name="component_2" src="${pageContext.request.contextPath}/monitor/deviceManager/toDeviceList?mOClassID=5,6&relationPath=1/2"  frameborder="0" style="height: 99%;"></iframe>
	 </div>
	 </div>
	 </div>
	 	<script>
	   (function(){
	       var parent_height = $("#tabs_window").closest("body").height();
	       $("#tabs_window").css("height",parent_height+"px");
	       $(window).resize(function() {
	       //  $('#tabs_window').resizeDataGrid(0, 0, 0, 0);
	         var parent_height = $("#tabs_window").closest("body").height();
	         $("#tabs_window").css("height",parent_height+"px");
	         $(".panel-body-noheader.panel-body-noborder").css("width","100%");
	       });
	    })()
	</script>
  </body>
</html>