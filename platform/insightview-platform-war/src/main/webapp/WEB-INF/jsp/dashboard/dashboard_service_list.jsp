<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script
	src="${pageContext.request.contextPath}/js/common/jquery-1.9.1.min.js"
	type="text/javascript"></script>
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
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/css/base.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/css/reset.css" />

</head>

<body id="service">
<script
	src="${pageContext.request.contextPath}/plugin/dashboard/flotr2.js"
	type="text/javascript"></script>
<script
	src="${pageContext.request.contextPath}/plugin/dashboard/jquery-sDashboard.js"
	type="text/javascript"></script>

<script type="text/javascript">
	var deskDashboardData = [];
</script>
<script type="text/javascript">
	var deskDashboardData = [];
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
				deskDashboardData = datas;
				var deskOwnDashboardData = new Array();
				$.each(deskDashboardData, function(index, item) {
					if(1 === item.id || 3 === item.id || 9 === item.id ){
						deskOwnDashboardData.push(item);
					}
			    });
				
				$("#deskDashboard").sDashboard({
					dashboardData : deskOwnDashboardData
				});
			}
		}
		ajax_(ajax_param);
	});

	function toAddDashboart() {
		$('#divDashboardLst').dialog('open');
	}
</script>
			
		<div class="content">
			<ul id="deskDashboard">
			</ul>
		</div>
	
</body>
</html>