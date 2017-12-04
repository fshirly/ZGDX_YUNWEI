<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file = "../../../common/pageincluded.jsp" %>
<html>
  <head>
  </head>
  <body>
	        <script type="text/javascript" src="${pageContext.request.contextPath}/js/monitor/alarmMgr/alarmview/alarmView_modify.js"></script>
			<!--告警视图定义修改  -->			
	<div id="resCiAllInfoDiv" class="easyui-tabs" data-options="fit:false,plain:false, tabPosition:'left'" style='height:400px;overflow:auto;'>
		<input id="viewCfgID" type="hidden" value="${alarmVo.viewCfgID}" />		
		<!-- 视图定义 -->
		<div title="视图定义 " data-options="iconCls:'icon-info',closable:false" 	style="overflow: hidden;">
					<table id="viewAddInfo" class="formtable">
					<tr>
					<td class="title">视图名称：</td>
					<td ><input type="text" id="cfgName" name="cfgName" maxlength="64" value="${alarmVo.cfgName}" /><b>*</b></td>
					</tr>
					<tr>
					<td class="title">是否默认：</td>
					<td >
						 <input type="radio" name="userDefault" value="1" <c:if test="${alarmVo.userDefault==1}">checked="checked"</c:if> />是
						 <input type="radio" name="userDefault" value="0" <c:if test="${alarmVo.userDefault==0}">checked="checked"</c:if> />否
					</td>
					</tr>
					<tr>
					<td class="title">告警加载条数：</td>
					<td >
						 <input type="text" id="defaultRows" name="defaultRows" value="<c:if test='${alarmVo.defaultRows!=-1}'>${alarmVo.defaultRows}</c:if>"/>
					</td>
					</tr>
					<tr>
					<td class="title">加载时间（小时 ）：</td>
					<td >
						 <input type="text" id="defaultInterval" name="defaultInterval" value="<c:if test='${alarmVo.defaultInterval!=-1}'>${alarmVo.defaultInterval}</c:if>"/>
					</td>
					</tr>
					<tr>
					<td class="title">描述：</td>
					<td >
						<textarea rows="3" id="descr" name="descr" maxlength="128">${alarmVo.descr}</textarea>
					</td>
					</tr>
					</table>
		</div>
		<!-- 显示列 -->
		<div title="显示列" data-options="iconCls:'icon-set',closable:false"
			style="overflow: auto;">
				<!-- begin .datas -->
				<div class="datas">
					<table id="colCfgtblDataList">
					</table>
				</div>
				<!-- end .datas -->
		</div>
		<!-- 过滤条件 -->
		<div title="过滤条件" data-options="iconCls:'icon-set',closable:false" style="overflow: auto;">
					<!-- begin .datas -->
					<div class="datas">
					<table id="filtertblDataList">
					</table>
					</div>
					<!-- end .datas -->
		</div>
		<!-- 通知声音 -->
		<div title="通知声音" data-options="iconCls:'icon-set',closable:false" 	style="overflow: auto;">
				<!-- begin .datas -->
				<div class="datas">
					<table id="soundtblDataList"></table>
				</div>
				<!-- end .datas -->
		</div>			
	</div>
	<div class="conditionsBtn">
						<a href="javascript:void(0);" onclick="doUpdate();">确定</a>
						<a href="javascript:void(0);" onclick="toClosed();">取消</a>
	</div>	
	<!-- 显示列修改--> 	
	<div id="divColcfgEdit" class="easyui-window" 	 title="显示列修改" minimizable="false"  resizable="false" maximizable="false"
		closed="true"  collapsible="false" modal="true" style="width: 600px;">
			<input id="ipt_cfgID" type="hidden" />
			<table id="colCfgTab" class="formtable1">
			<tr>
				<td class="title">列名：</td><!-- 仅用于展示 -->
				<td ><input id="ipt_colName" name="ipt_colName" readonly="readonly"/></td>
			</tr>
			<tr>
				<td class="title">列标题：</td><!-- 仅用于展示 -->
				<td ><input id="ipt_colTitle" name="ipt_colTitle" readonly="readonly"/></td>
			</tr>
			<tr>
				<td class="title">列宽：</td>
				<td ><input id="ipt_colWidth" name="ipt_colWidth" validator="{'default':'ptInteger', 'length':'1-11'}" /><b>*</b></td>
			</tr>
			<tr>
				<td class="title">显示顺序：</td>
				<td ><input id="ipt_colOrder" name="ipt_colOrder" validator="{'default':'ptInteger', 'length':'1-11'}" /><b>*</b></td>
			</tr>
			<tr>
				<td class="title">排序序号：</td>
				<td >
					<input id="ipt_colValueOrder" name="ipt_colValueOrder" validator="{'default':'ptInteger', 'length':'1-11'}" /><b>*</b>
				</td>
			</tr>
			<tr>
				<td class="title">是否显示：</td>
				<td >
					<input type="checkbox" id="isVisible"  />
				</td>
			</tr>
		</table>
			<div class="conditionsBtn">
					<a href="javascript:doUpdateCfgDlg();" >确定</a>
					<a href="javascript:closeCfgDialg();">取消</a>					
			</div>
		</div>
	<!-- 通知声音增加--> 	
	<div id="divSoundAdd" class="easyui-window" 	 title="通知声音增加" minimizable="false"  resizable="false" maximizable="false"
		closed="true"  collapsible="false" modal="true" style="width: 600px;">
			<table id="soundTab" class="formtable1">
			<tr>
				<td class="title">告警等级：</td>
				<td >
				<select id="alarmLevelID" name="alarmLevelID" validator="{'length':'1-10'}">
					<option></option>				    
				</select><b>*</b>
				</td>
			</tr>
			<tr>
				<td class="title">声音文件：</td>
				<td >
				<select id="soundFileURL" name="soundFileURL" validator="{'length':'1-10'}">
						<option></option>
						<option value="alarm1.wav">alarm1.wav</option>
						<option value="alarm2.wav">alarm2.wav</option>
						<option value="alarm3.wav">alarm3.wav</option>
						<option value="alarm4.wav">alarm4.wav</option>
						<option value="alarm5.wav">alarm5.wav</option>
						<option value="alarm6.wav">alarm6.wav</option>
				</select><b>*</b> 
				</td>
			</tr>
			<tr>
				<td class="title">时间窗口（秒）：</td>
				<td ><input id="loopTime" name="loopTime" validator="{'default':'ptInteger', 'length':'1-11'}" /><b>*</b></td>
			</tr>
		</table>
			<div class="conditionsBtn">
					<a  href="javascript:doAddSound();" >确定</a>
					<a  href="javascript:closeSoundDialog();" >取消</a>					
			</div>
		</div>
		
		<div id='event_select_dlg' class='event_select_dlg'/>
  </body>
</html>
