<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file = "../../../common/pageincluded.jsp" %>
<html>
  <head>
  </head>
  <body>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/monitor/alarmMgr/alarmDispatchFilter/add.js"></script>
	<!--告警派单规则新增  -->	
	<input id="viewCfgID" type="hidden" value="" />				
	<div id="resCiAllInfoDiv" class="easyui-tabs"  data-options="tabPosition:'left',fit:true,plain:false">
		<!-- 派单规则 -->
		<div title="派单规则 " data-options="iconCls:'icon-info',closable:false" 	style="overflow: hidden;">
			<table id="viewAddInfo" class="formtable">
			<tr>
			<td class="title">规则名称：</td>
			<td ><input type="text" id="cfgName" name="cfgName"  maxlength="64"/><b>*</b></td>
			</tr>
			<tr>
			<td class="title">是否启用：</td>
			<td >
				 <input type="radio" name="userDefault" value="1" onclick="$.messager.alert('提示','如果存在已启用的自动派单设置，启动该设置，则已启动的设置将会被禁用。')"/>是
				 <input type="radio" name="userDefault" value="0" checked="checked"/>否
			</td>
			</tr>
			<tr>
			<td class="title">描述：</td>
			<td >
				<textarea rows="3" id="descr" name="descr" maxlength="128"></textarea>
			</td>
			</tr>
			</table>
		</div>
		<!-- 过滤条件 -->
		<div title="派单策略" data-options="iconCls:'icon-set',closable:false"
			style="overflow: auto;"  class="datas">
					<!-- begin .datas -->
					<table id="filtertblDataList">
					</table>
					<!-- end .datas -->
		</div>
	</div>
	<div class="conditionsBtn">
						<a href="javascript:void(0);" onclick="doAdd();">确定</a>
						<a href="javascript:void(0);" onclick="toClosed();">取消</a>
	</div>
	<div id='event_select_dlg' class='event_select_dlg'></div>
  </body>
</html>