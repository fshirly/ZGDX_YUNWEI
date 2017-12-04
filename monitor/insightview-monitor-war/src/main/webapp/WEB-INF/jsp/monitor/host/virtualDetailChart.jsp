<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="../../common/pageincluded.jsp"%>
<html>
	<head>
	</head><!-- 虚拟机详情 -->
	<body>
	<script type="text/javascript" src="${pageContext.request.contextPath}/plugin/echarts/echarts-plain.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/monitor/host/virtualDetailChart.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/monitor/host/commLineChart.js"></script>
	<input type="hidden" id="MOID" name="MOID" value="${MOID}"/>
	<div id="serviceLevelTabs"   class="easyui-tabs" style="height: 270px;" >
			<div title="CPU">
				<select id="cpu" name="cpu" onchange="getCpuChartJsonData()">
					<option value="1" >单核使用量</option>
					<option value="2" >总使用时间</option>
					<option value="3" >准备时间</option>
					<option value="4" >等待时间</option>
				</select>
				<div id="cpu_line" style="height:210px; " ondblclick="toCpuDeatil()"></div>
			</div>
			<div title="内存" style="overflow-x: hidden;">
				<select id="memory" name="memory" onchange="getMemoryChartJsonData()">
					<option value="1">使用率</option>
					<option value="2">内存控制</option>
					<option value="3">活动内存</option>
					<option value="4">系统内存开销</option>
					<option value="5">共享内存</option>
					<option value="6">交换内存</option>
					<option value="7">消耗内存</option>
				</select>
				<div id="memory_line" style="height:210px; " ondblclick="toMemoryDeatil()"></div>
			</div>
			<div title="数据存储" style="overflow-x: hidden;">
				<select id="storage" name="storage" style="width:100px;" onchange="getStorageChartJsonData()" >
				    <option value="1">读速度</option>
				    <option value="2">写速度</option>
				    <option value="3">读请求</option>
				    <option value="4">写请求</option>
				    <option value="5">读延时</option>
				    <option value="6">写延时</option>
				</select>
				<div id="storage_line" style="height:210px; " ondblclick="toStorageDeatil()"></div>
			</div>				
			<div title="磁盘" >
				<select id="hardDisk" name="hardDisk" onchange="getHardChartJsonData()">
				    <option value="1">读盘速度</option>
				    <option value="2">写盘速度</option>
				    <option value="3">读盘请求</option>
				    <option value="4">写盘请求</option>
				    <option value="5">磁盘总线重置</option>
				    <option value="6">磁盘大小</option>
				    <option value="7">磁盘空闲大小</option>
				    <option value="8">磁盘已用大小</option>
				    <option value="9">磁盘使用率</option>
				</select>
				<div id="hardDisk_line" style="height:210px;" ondblclick="toHardDiskDeatil()"></div>
			</div>
			<div title="接口" style="overflow-x: hidden;">
				<select id="interface" name="interface" style="width:100px;" onchange="getInterfaceChartJsonData()"><!-- 接口指标 -->
					<option value="1" >总流量</option>
				   	<option value="2" >流入流量</option>
					<option value="3" >流出流量</option>
					<option value="4" >流入包数</option>
					<option value="5" >流出包数</option>
				</select>
				<div id="interface_line" style="height:210px;" ondblclick="toInterfaceDeatil()"></div>
			</div>
		</div>
	</body>
</html>