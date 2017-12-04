<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file = "../../../common/pageincluded.jsp" %>
<html>
  <head>
  </head>
  <body>
			<!--告警视图定义新增  -->			
	<div id="resCiAllInfoDiv" class="easyui-tabs"
		data-options="fit:true,plain:false, tabPosition:'left'">
		<input id="viewCfgID" type="hidden" value="${id}" />		
		<!-- 视图定义 -->
		<div title="派单规则 " data-options="iconCls:'icon-info',closable:false"
			style="overflow: hidden;">
					<table id="viewAddInfo" class="tableinfo">
					<tr>
					<td><b class="title">规则名称：</b><label>${name}</label></td>
					</tr>
					<tr>
					<td><b class="title">是否启用：</b>
					     <label>
						 <input type="radio" name="isDefault" value="1"  disabled="disabled"
    						<c:if test="${isDefault==1}">  
    						checked="checked"  
   						 	</c:if>  
						  />是
						 <input type="radio" name="isDefault" value="0"  disabled="disabled"
						 	<c:if test="${isDefault==0}">  
    						checked="checked"  
   						 	</c:if>
						 />否
						 </label>
					</td>
					</tr>
					<tr>
					<td><b class="title">描述：</b><label>${descr}</label></td>
					</tr>
					</table>
		</div>
		<!-- 过滤条件 -->
		<div title="派单策略" data-options="iconCls:'icon-set',closable:false"
			style="overflow: auto;" class="datas">
					<!-- begin .datas -->
					<table id="filtertblDataList">
					</table>
					<!-- end .datas -->
		</div>
	</div>
	<div class="conditionsBtn">
			<input class="buttonB" type="button" id="btnClose2" value="关闭" onclick="javascript:$('#popWin').window('close');"/>		
	</div>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/monitor/alarmMgr/alarmDispatchFilter/view.js"></script>
  </body>
</html>