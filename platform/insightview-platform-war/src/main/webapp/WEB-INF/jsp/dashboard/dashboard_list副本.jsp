<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/fui/fui.min.js"></script>
<script
	src="${pageContext.request.contextPath}/js/common/commonUtil.js"
	type="text/javascript"></script>
<script
	src="${pageContext.request.contextPath}/plugin/dashboard/jquery-ui.js"
	type="text/javascript"></script>
	
<script type="text/javascript"
	src="${pageContext.request.contextPath}/plugin/easyui/jquery.easyui.min.js"></script>
<!-- load the sDashboard css -->
<link
	href="${pageContext.request.contextPath}/style/css/sDashboard.css"
	rel="stylesheet" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/plugin/echarts/demo.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/plugin/echarts/jquery.gridster.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/plugin/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/plugin/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/plugin/jquery-easyui-portal/portal.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/css/base.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/css/reset.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/plugin/easyui/themes/icon.css"/>

<script type="text/javascript" src="${pageContext.request.contextPath}/plugin/jquery-easyui-portal/jquery.portal.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/plugin/echarts/echarts-plain.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/plugin/echarts/jquery.gridster.js"></script>
   
<script
	src="${pageContext.request.contextPath}/plugin/dashboard/flotr2.js"
	type="text/javascript"></script>
<script
	src="${pageContext.request.contextPath}/plugin/dashboard/jquery-sDashboard.js"
	type="text/javascript"></script>

<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/common/base64.js"></script>
<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/common/easyui-lang-zh_CN.js"></script>
	
<script type="text/javascript">
	var dashboardData = [];
</script>
<script type="text/javascript">
/**
	var dashboardData = [];
	$(function() {
		var path = getRootName();
		var uri = path + "/dashboard/doGetDashboardList";
		var ajax_param = {
			url : uri,
			type : "post",
			datdType : "json",
			data : {
				"t" : Math.random()
			},
			error : function() {
				alert("ajax_error");
			},
			success : function(data) {
				var datas = eval('(' + data.widgetsLstJson + ')');
				dashboardData = datas;

				var ownDashboardData = new Array();
				$.each(dashboardData, function(index, item) {
					if(2 === item.id || 4 === item.id || 5 === item.id || 6 === item.id || 7 === item.id || 8 === item.id || 10 === item.id ){
						ownDashboardData.push(item);
					}
			    });
				
				$("#homeDashboard").sDashboard({ 
					dashboardData : ownDashboardData
				});
			}
		}
		ajax_(ajax_param);
	});
*/
	function toAddDashboart() {
		$('#divDashboardLst').dialog('open');
	}
</script>
<script type="text/javascript" charset="utf-8">
<%
	String tabsEditIndex = (String)request.getAttribute("tabsIndex");
 %>
</script>
</head>

<body id="main">
	<input id="tabsIndex" type="hidden"/>
	<input id="eidtPortalName" type="hidden"/>
	<input id="tabsEditIndex" type="hidden" value="<%=tabsEditIndex %>"/>
	<div id="popWin" class="popWin"></div>
	<div id="popView" class="popWin"></div>
	<div id="popView2" class="popWin">
		<div id="dataMObjectTreeDiv" class="dtree"
			style="width: 100%;">
		</div>
	</div>
	<div>
		
	</div>
	<%--<div id="tabs_window" style="width:100%;height:100%;">--%>
		<!--<div title="首页" id="homeDashboard" style="overflow: hidden;">
			<iframe id="ifr0" scrolling="auto" frameborder="0" style="width:100%;height:100%;"></iframe>
		</div>
		
		<div title="服务台" id="viewMoreTab">
        </div>

	-->
	<%--<c:if test="${!empty map}">--%>
					<%--<c:forEach items="${map}" var="entry">--%>
						<%--<div title="<c:out value='${entry.value}' />" id="<c:out value="${entry.key}" />">--%>
        					<%--<iframe id="ifr<c:out value="${entry.key}" />" scrolling="auto" frameborder="0" style="width:100%;height:100%;"></iframe>--%>
        				<%--</div>--%>
					<%--</c:forEach>										--%>
		  		<%--</c:if>		--%>
	<%--</div>--%>
	<!-- 
	 <div style="z-index:9999;top: 4px;right: 10px;position: absolute;">
        <input class="btn" type="button" id="btnAddWidget" value="新增部件" style="" onclick="javascript:toOpenWidgets();"/>
        &nbsp;&nbsp;
        <input class="btn" type="button" id="btnAddWidget" value="新增视图" style="" onclick="javascript:doOpenAdd();"/>
        &nbsp;&nbsp;
        <input class="btn" type="button" id="btnSave" value="保存视图" style="" onclick="javascript:toChangePortal();"/>
        &nbsp;&nbsp;
        <input class="btn" type="button" id="btnDel" value="删除视图" style="" onclick="javascript:toDelPortal();"/>
        </div>
	 -->
</body>
<script
	src="${pageContext.request.contextPath}/js/dashboardPageManage/dashboard_list.js"
	type="text/javascript"></script>
</html>