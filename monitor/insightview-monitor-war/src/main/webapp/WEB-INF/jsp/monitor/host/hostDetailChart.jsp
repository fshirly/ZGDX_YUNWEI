<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="../../common/pageincluded.jsp"%>
<html>
	<head>
	</head><!-- 宿主机详情 -->
	<body>
	<script src="${pageContext.request.contextPath}/js/monitor/echarts/echarts-plain-map.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/monitor/host/hostDetailChart.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/monitor/host/commLineChart.js"></script>
	<input type="hidden" id="MOID" name="MOID" value="${MOID}"/>
	<div id="serviceLevelTabs"   class="easyui-tabs"  style="height: 270px;">
			<div title="CPU">
				<select id="cpu" name="cpu" onchange="getCpuChartJsonData()">
					<option value="1" >单核使用率</option>
					<option value="2" >总使用时间</option>
					<option value="3" >空闲时间</option>
				</select>
				<div id="cpu_line" style="height:210px; " ondblclick="toCpuDeatil()"></div>
			</div>
			<div title="内存" style="overflow-x: hidden;">
				<select id="memory" name="memory" onchange="getMemoryChartJsonData()">
					<option value="1" >使用率</option>
					<option value="2" >活动内存</option>
					<option value="3" >空闲容量</option>
					<option value="4" >系统开销内存</option>
					<option value="5" >共享内存</option>
					<option value="6" >共享的通用内存</option>
					<option value="7" >写入交换内存</option>
					<option value="8" >读出交换内存</option>
					<option value="9" >已用交换内存</option>
				</select>
				<div id="memory_line" style="height:210px; " ondblclick="toMemoryDeatil()"></div>
			</div>
			<div title="数据存储" style="overflow-x: hidden;">
				<select id="storage" name="storage" onchange="getStorageChartJsonData()">
				    <option value="1">使用率</option>
				    <option value="2">空闲容量</option>
				    <option value="3">读速度</option>
				    <option value="4">写速度</option>
				    <option value="5">读请求</option>
				    <option value="6">写请求</option>
				    <option value="7">读延时</option>
				    <option value="8">写延时</option>
				    <option value="9">规范延时</option>
				    <option value="10">IO操作数据存储总数</option>
				</select>
				<div id="storage_line" style="height:210px; " ondblclick="toStorageDeatil()"></div>
			</div>				
			<div title="磁盘" >
				<select id="hardDisk" name="hardDisk" onchange="getHardChartJsonData()">
				    <option value="1">I/O使用率</option>
				    <option value="2">读盘速度</option>
				    <option value="3">写盘速度</option>
				    <option value="4">读盘请求</option>
				    <option value="5">写盘请求</option>
				    <option value="6">磁盘总线重置</option>
				    <option value="7">中止磁盘命令</option>
				    <option value="8">写盘延时</option>
				    <option value="9">读盘延时</option>
				</select>
				<div id="hardDisk_line" style="height:210px; " ondblclick="toHardDiskDeatil()"></div>
			</div>
			<div title="接口" style="overflow-x: hidden;">
				<select id="interface" name="interface" onchange="getInterfaceChartJsonData()"><!-- 接口指标 -->
				   	<option value="1" >总流量</option>
				   	<option value="2" >流入流量</option>
					<option value="3" >流出流量</option>
					<option value="4" >流入包数</option>
					<option value="5" >流出包数</option>
					<option value="6" >总利用率</option>
					<option value="7" >流入利用率</option>
					<option value="8" >流出利用率</option>
					<option value="9" >接口速率</option>
				</select>
				<div id="interface_line" style="height:210px;" ondblclick="toInterfaceDeatil()"></div>
			</div>
		</div>
	</body>
</html>