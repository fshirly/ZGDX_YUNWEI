<%@ page language="java"  pageEncoding="utf-8"%>
<%@ include file = "../../common/pageincluded.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
  
	
	</head>
  
  <body>
  	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/plugin/echarts/demo.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/plugin/echarts/jquery.gridster.css">
  	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/plugin/easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/plugin/easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/plugin/jquery-easyui-portal/portal.css">
	<link href="${pageContext.request.contextPath}/style/css/sDashboard.css" rel="stylesheet" />
	<!--<script type="text/javascript" src="${pageContext.request.contextPath}/plugin/jquery-easyui-portal/jquery-1.9.1.min.js"></script>-->
	<script type="text/javascript" src="${pageContext.request.contextPath}/plugin/echarts/jquery.gridster.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/plugin/echarts/echarts-plain.js"></script>
	<script type="text/javascript"	src="${pageContext.request.contextPath}/js/monitor/portal/portalView.js"></script>
	<script type="text/javascript" charset="utf-8">
	<%
	/**
		response.setHeader("Cache-Control", "public"); 
        response.setHeader("Pragma", "Pragma"); 
        //本地缓存10分钟过期
        response.setDateHeader("Expires", System.currentTimeMillis()+1*60*1000);
        */
     //  response.setDateHeader("Expires", -10);  
        //设置Last-Modified
     //   response.setDateHeader("Last-Modified", System.currentTimeMillis());
		
	%>
	<%
	String portalJS=(String)request.getAttribute("portalJS");
	String contentXml = (String)request.getAttribute("contentXml");
	String portalName = (String)request.getAttribute("portalName");
	//System.out.println("contentXml === " + contentXml);
	//System.out.println("portalJS === " + portalJS);
	String flag = (String)request.getAttribute("flag");
	String widgetCount = (String)request.getAttribute("widgetCount");
	String moType = (String)request.getAttribute("moType");
	String userId = (String)request.getAttribute("userId");
	String userAccount= (String)request.getAttribute("userAccount");
	String portalDesc = (String)request.getAttribute("portalDesc");
	%>
	<%=portalJS %>

	</script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/plugin/easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/plugin/jquery-easyui-portal/jquery.portal.js"></script>
	
	
   
   
   <!-- my ui -->
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/css/reset.css" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/css/layout.css" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/plugin/easyui/themes/icon.css"/>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/main.js"></script>
	
  <div class="pright">
  	<input class="btn" type="button" id="btnSave" value="保存视图" style="" onclick="javascript:toChangePortalStyle();"/>
  </div>
   <input id="flag" type="hidden" value="<%=flag %>"/>
   <input id="widgetCount" type="hidden" value="<%=widgetCount %>"/>
   <input id="moType" type="hidden" value="<%=moType %>"/>
   <input id="contentXml" type="hidden" value='<%=contentXml %>'/>
    <input id="portalName" type="hidden" value='<%=portalName %>'/>
    <input id="userId" type="hidden" value='<%=userId %>'/>
    <input id="userAccount" type="hidden" value='<%=userAccount %>'/>
    <input id="portalDesc" type="hidden" value='<%=portalDesc %>'/>
	<div id="popView" class="popWin"></div>
	<div id="popView2" class="popWin">
		<div id="dataMObjectTreeDiv" class="dtree"
			style="width: 100%;">
		</div>
	</div>
  </body>
</html>
