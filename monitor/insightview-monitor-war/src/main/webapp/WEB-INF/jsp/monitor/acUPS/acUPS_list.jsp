<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file = "../../common/pageincluded.jsp" %>
<html>
	<head>
	<% String MOClassID =(String)request.getAttribute("MOClassID"); %>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/monitor/acUPS/acUPS_list.js"></script>
	</head>	
	<body>  
	<div id="tabs_window_acups" class="easyui-tabs viewtabs">
		 	<input id= "hiddenMoClassID"  type="hidden" value="<%=MOClassID %>">
		<div title="设备列表" >
		 	<div class="location">当前位置：${navigationBar}</div>
			<div class="conditions" id="divFilter">
				<table>
					<tr>
						<td>
							<b>厂商名称：</b>
							<input type="text" id="txtAcditionName" />
						</td>
						<td>
							<b>监控类型：</b>
							<select class="easyui-combobox" id="txtAcditionType" panelHeight="80">
							<option value="-1"></option>
							<option value="96">空调</option>
							<option value="73">UPS</option>
							</select>
						</td>
						<td class="btntd">
							<a href="javascript:void(0);" onclick="reloadTable();">查询</a>
							<a href="javascript:void(0);" onclick="resetForm('divFilter');">重置</a>
						</td>
					</tr>
				</table>
			</div>
			
			<!-- begin .datas -->
			<div class="datas topwin">
				<table id="tblAcUPSList">
				</table>
			</div>
		</div>	
		</div>
			<script>
	   (function(){
	       var parent_height = $("#tabs_window").closest("body").height();
	       $("#tabs_window").css("height",parent_height+"px");
	       $("#tabs_window").css("overflow","hidden");
	       $(window).resize(function() {
	         var parent_height = $("#tabs_window").closest("body").height();
	         $("#tabs_window").css("height",parent_height+"px");
	          $(".panel-body-noheader.panel-body-noborder").css("width","100%");
	       });
	    })()
	</script>
	</body>
</html>