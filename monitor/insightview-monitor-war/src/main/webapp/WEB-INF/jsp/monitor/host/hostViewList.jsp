<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="../../common/pageincluded.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	<script src="${pageContext.request.contextPath}/js/monitor/echart/echarts-plain-map.js"></script>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/js/monitor/host/hostViewList.js"></script>
<script type="text/javascript"
			src="${pageContext.request.contextPath}/js/monitor/host/commHref.js"></script>
	</head>

	<body>
		<div id="serviceLevelTabs" class="easyui-tabs"
			style="width: 1200px; height: 500px;">
			<div title="列表视图">
				<table id="tblListView">

				</table>
			</div>
			<!--  
			<div title="图表视图" style="overflow-x: hidden;width: 1200px;height: 500px;">
				<table class="formtable">
					<tr>
						<td class="title">
							 <div id="cpuUsage" style="height:350px;border:1px solid #ccc;padding:5px;"></div>
						</td>
					</tr>

				</table>
			</div>
			-->
		</div>
		
	</body>
	
</html>
