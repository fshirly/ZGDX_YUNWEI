<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="../../common/pageincluded.jsp"%>
<html>
	<head>
	</head><!-- 主机详情 -->
	<body>
	<script src="${pageContext.request.contextPath}/js/monitor/echarts/echarts-plain-map.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/monitor/host/mainDetailChart.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/monitor/host/commLineChart.js"></script>
	<input type="hidden" id="MOID" name="MOID" value="${MOID}"/>
	<div id="serviceLevelTabs"   class="easyui-tabs" style="height: 270px;">
			<div title="CPU">
				<select id="cpu" name="cpu" >
					<option value="1" >使用率</option>
				</select>
				<div id="cpu_line" style="height:210px;" ondblclick="toCpuDeatil()"></div>
			</div>
			<div title="内存" style="overflow-x: hidden;">
				<select id="memory" name="memory" onchange="getMemoryChartJsonData()">
					<option value="1">内存使用率</option>
				    <option value="2">内存空闲容量</option>
				</select>
				<div id="memory_line" style="height:210px;" ondblclick="toMemoryDeatil()"></div>
			</div>			
			<div title="磁盘" >
				<select id="hardDisk" name="hardDisk" onchange="getHardChartJsonData()">
				    <option value="1">使用率</option>
				    <option value="2">空闲容量</option>
				</select>
				<div id="hardDisk_line" style="height:210px;" ondblclick="toHardDiskDeatil()"></div>
			</div>
			<div title="接口" style="overflow-x: hidden;">
				<select id="interface" name="interface" onchange="getInterfaceChartJsonData()"><!-- 接口指标 -->
				   	<option value="2" >流入流量</option>
					<option value="3" >流出流量</option>
					<option value="4" >流入单播包</option>
					<option value="5" >流出单播包</option>
					<option value="6" >流入非单播包</option>
					<option value="7" >流出非单播包</option>
					<option value="8" >流入丢包</option>
					<option value="9" >流出丢包</option>
					<option value="10" >流入错包</option>
					<option value="11" >流出错包</option>
				</select>
				<div id="interface_line" style="height:210px; " ondblclick="toInterfaceDeatil()"></div>
			</div>
		</div>
	</body>
</html>