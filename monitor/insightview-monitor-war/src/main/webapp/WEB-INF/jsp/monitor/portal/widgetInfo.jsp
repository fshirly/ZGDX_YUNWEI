<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file = "../../common/pageincluded.jsp" %>

<html>
  <head>
 
	<script type="text/javascript"	src="${pageContext.request.contextPath}/js/monitor/portal/widgetInfo.js"></script>
	<!--<script type="text/javascript"	src="${pageContext.request.contextPath}/js/monitor/portal/jquery-latest.js"></script>-->
	<script type="text/javascript"
	src="${pageContext.request.contextPath}/plugin/dTree/dtree.js"></script>
	<script type="text/javascript"
	src="${pageContext.request.contextPath}/plugin/dTree/combo/comboJS.js"></script> 	
	<script>
		<%
			String widgetFilter=(String)request.getAttribute("widgetFilter");
		%>
	</script>
	
  </head>
  
  <body>
  	<div class="location">当前位置：&gt;&gt; 视图管理 &gt;&gt; <span>设备视图</span></div>
	<div id="dataTreeDiv" class="treetable">
	
	</div>
	<div class="treetabler"> 
	<div id="divPortalInfo" class="datas tops1" >
		<input type="hidden" id="widgetFilter" value=<%=widgetFilter %>>
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
					<input class="buttonB" type="button" id="btnClose" value="取消" onclick="javascript:window.close();"/>
				</td>
			</tr>
		</table>
	</div>
	</div>
  </body>
</html>
