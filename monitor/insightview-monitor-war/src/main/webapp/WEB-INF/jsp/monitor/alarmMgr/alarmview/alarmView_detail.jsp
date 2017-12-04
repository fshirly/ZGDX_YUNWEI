<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file = "../../../common/pageincluded.jsp" %>
<html>
  <head>
  </head>
  <body>
	        <script type="text/javascript" src="${pageContext.request.contextPath}/js/monitor/alarmMgr/alarmview/alarmView_detail.js"></script>
			<!--告警视图定义新增  -->			
	<div id="resCiAllInfoDiv" class="easyui-tabs"
		data-options="fit:false,plain:false, tabPosition:'left'" style='height:400px;overflow:auto;'>
		<input id="viewCfgID" type="hidden" value="${alarmVo.viewCfgID}" />		
		<!-- 视图定义 -->
		<div title="视图定义 " data-options="iconCls:'icon-info',closable:false"
			style="overflow: hidden;">
					<table id="viewAddInfo" class="tableinfo">
					<tr>
					<td><b class="title">视图名称：</b><label>${alarmVo.cfgName}</label></td>
					</tr>
					<tr>
					<td><b class="title">是否默认：</b>
					     <label>
						 <input type="radio" name="userDefault" value="1"  disabled="disabled"
    						<c:if test="${alarmVo.userDefault==1}">  
    						checked="checked"  
   						 	</c:if>  
						  />是
						 <input type="radio" name="userDefault" value="0"  disabled="disabled"
						 	<c:if test="${alarmVo.userDefault==0}">  
    						checked="checked"  
   						 	</c:if>
						 />否
						 </label>
					</td>
					</tr>
					<tr>
					<td><b class="title">告警加载条数：</b><label><c:if test="${alarmVo.defaultRows!=-1}">${alarmVo.defaultRows}</c:if></label></td>
					</tr>
					<tr>
					<td ><b class="title">加载时间（小时 ）：</b><label><c:if test="${alarmVo.defaultInterval!=-1}">${alarmVo.defaultInterval}</c:if></label></td>
					</tr>
					<tr>
					<td><b class="title">描述：</b><label>${alarmVo.descr}</label></td>
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
		</div>
		<!-- 过滤条件 -->
		<div title="过滤条件" data-options="iconCls:'icon-set',closable:false"
			style="overflow: auto;">
					<!-- begin .datas -->
					<div class="datas" >
					<table id="filtertblDataList">
					</table>
					</div>
					<!-- end .datas -->
		</div>
		<!-- 通知声音 -->
		<div title="通知声音" data-options="iconCls:'icon-set',closable:false"
			style="overflow: auto;">
				<!-- begin .datas -->
				<div class="datas">
					<table id="soundtblDataList">
					</table>
				</div>
				<!-- end .datas -->
		</div>
	</div>
	<div class="conditionsBtn">
			<input class="buttonB" type="button" id="btnClose2" value="关闭" onclick="javascript:$('#popWin').window('close');"/>		
	</div>
	<!-- 显示列新建弹出Div--> <div id='newWindowAddDlg' />
  </body>
</html>