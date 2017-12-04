<%@ page language="java" pageEncoding="utf-8"%>
<%@page import="java.util.Calendar,java.text.SimpleDateFormat"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file = "../../common/pageincluded.jsp" %>
<html>
<head>
<!-- mainframe --> 
</head>
<body>   
<%
	String time = request.getParameter("time");
 %>
<!-- 注意需要放到文件末尾处 -->
<script src="${pageContext.request.contextPath}/js/monitor/echarts/echarts-all.js"></script>
<script src="${pageContext.request.contextPath}/js/monitor/host/historyChart_detail.js"></script>
 <input type="hidden" id="proUrl" name="proUrl" value="${proUrl}"/>
		  <input type="hidden" id="perfKind" name="perfKind" value="${perfKind}"/>
		  <input type="hidden" id="MOID" name="MOID" value="${MOID}"/>
		  <input type="hidden" id="time" value="<%=time %>">
		  <input type="hidden" id="timeHide" >
		 <table id="divFilter">
		 	<tr><td colspan="5">&nbsp;</td></tr>
			<tr>
			<td><b>&nbsp;&nbsp;时间模版：</b>
				<select id="timeTemplate" name="timeTemplate" style="width: 100px;" onchange="changeTime()">
					<option value="0" >自定义</option>
					<option value="1" >最近半小时</option>
					<option value="2" >最近1小时</option>
					<option value="3" >最近2小时</option>
					<option value="4" >最近4小时</option>
					<option value="5" >最近6小时</option>
					<option value="6" >最近12小时</option>
					<option value="7" >最近1天</option>
					<option value="8" >最近2天</option>
					<option value="9" >今天</option>		
					<option value="10" >昨天</option>
					<option value="11" >本周</option>
					<option value="12" >最近一周</option>
					<option value="13" >本月</option>
					<option value="14" >上月</option>							
				</select>
			</td>
			<td>
			 	<b>&nbsp;开始时间：</b>
				<input class="easyui-datetimebox" id="timeBegin" name="timeBegin"  />
			</td>
			<td>
				<b>&nbsp;结束时间：</b>
				<input class="easyui-datetimebox" id="timeEnd" name="timeEnd"  />
			</td>
			<td class="btntd">
				<a href="javascript:void(0);" onclick="reloadShow()" >查询</a>		
				<a href="javascript:void(0);" onclick="resetForm('divFilter');">重置</a> 				
			</td>
			<td>
				<b>&nbsp;显示方式：</b>
					<a style="color:blue" onclick="selectViewPic(1)">拆线图显示</a> 
				    <a style="color:red"  onclick="selectViewPic(2)">面积图显示</a> 
				</select>
			</td>			
		</tr>		
	</table>
		
		<!-- begin .datas -->
		<div class="datas">
			<div id="line">
    		 <div id="type_line" style="height:400px;border:1px solid #ccc;"></div>
    		 </div>
    		 <div id="area" >
    		 <div id="type_area" style="height:400px;border:1px solid #ccc;"></div>
    		</div>
    		<div style="overflow:auto;">
    		<table id="tblDataList">
			</table>
			</div>
		</div>
		<!-- end .datas -->		
			<div id="loading" style="position:absolute;text-align:center;left:700px;top:200px;z-index:200;opacity: 0.7;display:none;"> 
			<img src="${pageContext.request.contextPath}/style/images/loading.gif"/> 数据加载中，请稍候...
			</div>	 
</body>

</html>