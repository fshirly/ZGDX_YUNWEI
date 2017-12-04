<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file = "../../common/pageincluded.jsp" %>

<html>
  <head>
 
	
	<!--<script type="text/javascript"	src="${pageContext.request.contextPath}/js/monitor/portal/jquery-latest.js"></script>-->
	<script type="text/javascript"
			src="${pageContext.request.contextPath}/plugin/dTree/dtree.js"></script>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/plugin/dTree/combo/comboJS.js"></script>
	<script type="text/javascript"	src="${pageContext.request.contextPath}/js/platform/portal/platformWidgetInfo.js"></script>	
	<script>
		<%
			String widgetFilter = (String)request.getAttribute("widgetFilter");
			String tabsIndex = (String)request.getAttribute("tabsIndex");
			String portalId = (String)request.getAttribute("portalId");
			String portalName = (String)request.getAttribute("portalName");
		%>
	</script>
	
  </head>
  
  <body>
  	<div class="location">当前位置：&gt;&gt; 视图管理 &gt;&gt; <span>设备视图</span></div>
	<div id="dataTreeDiv" class="dtree" style="width: 200px; height: 650px; overflow-y: auto;">
	
	</div>
	<div class="treetabler"> 
	<div id="divPortalInfo" class="datas tops1" >
		<input type="hidden" id="widgetFilter" value="<%=widgetFilter %>">
		<input type="hidden" id="tabsIndex" value="<%=tabsIndex %>">
		<input type="hidden" id="portalId" value="<%=portalId %>">
		<input type="hidden" id="portalName" value="<%=portalName %>">
		<table id="tblWidgetInfo">
			<tr>
				<td>&nbsp;</td>
				<td>
				<input id="ipt_widgetId" type="hidden" value="0" />
				<input id="ipt_widgetContent" type="hidden" value="" />
					<div id="previewImage" ><img id="imgWidget" /></div>
				</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td class="btntd">
					<input class="buttonB" type="button" id="btnSave" value="确定" onclick="javascript:toInsert();"/>
					<input class="buttonB" type="button" id="btnClose" value="取消" onclick="javascript:doClose();"/>
				</td>
			</tr>
		</table>
	</div>
	</div>
  </body>
</html>
