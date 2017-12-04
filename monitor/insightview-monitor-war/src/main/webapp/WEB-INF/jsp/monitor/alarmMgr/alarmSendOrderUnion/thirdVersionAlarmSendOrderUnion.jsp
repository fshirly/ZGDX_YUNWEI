<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
</head>
<body>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/plugin/easyui/themes/default/easyui.css" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/fui/themes/default/fui-tree.min.css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/css/base.css" />
	<script type="text/javascript" src="${pageContext.request.contextPath}/fui/plugin/fui-tree.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/monitor/alarmMgr/alarmSendOrderUnion/thirdVersionAlarmSendOrderUnion.js"></script>
	<div id="WorkOrderForm">
	<input type="hidden" id="alarmIds" value="${alarmIds }" />
	<input type="hidden" id="workingGroupId" value="" />
	<input type="hidden" id="defaultHandlerId" value="" />
 	<input type="hidden" id="resourceWorkingGroupId" value=""/>
	<input type="hidden" id="resourceDefaultHandlerId" value=""/>
	<input type="hidden" id="alarmDetail" value="${alarmDetail }"/>
		<form id="formWorkOrder">
			<table id="tableWorkOrder" class="formtable" >
				<tr>
					<td class="title">标题：</td>
					<td colspan="3"><input type="text" id="titleId1" name="title"
						class="x2" validator="{'default':'*', 'length':'1-50'}" /> <dfn>*</dfn></td>
				</tr>
				<tr>
					<td class="title">备注：</td>
					<td colspan="3"><textarea id="remark" class="x2" rows="3" name="remark"
							validator="{'length':'0-500'}" style="resize: none"></textarea></td>
				</tr>
				<tr>
					<td class="title">派单人：</td>
					<td>
					<input type="hidden" name="acceptPeople" value="${userId}" />
						<input type="hidden" name="handlingPeople" value="${username}" />${username}</td>
					<td class="title">派单时间：</td>
					<td><input type="text" id="transferTime" name="transferTime" class="easyui-datetimebox"
						validator="{'default':'*', 'type':'datetimebox'}"
						dataoptions="required:true,editable:false"  value="${transferTimeStr}"/> </td>
				</tr>
				<tr>
					<td class="title">告警信息：</td>
					<td colspan="3"><a style="cursor: pointer;"  title="查看" onclick="javascript:toView($('#alarmIds').val())">${alarmDetail }</a></td>
				</tr>
				<tr>
					<td class="title">故障分类：</td>
					<td><input type="text" id="eventTypeId"
						name="incidentType"/></td>
					<td class="title">关联资源：</td>
					<td>
					   <input type="hidden" id="incidentConfigurationItem" name="relResCiId" value="${incident.configurationItem}"/>
					   <input type="text" dataoptions="required:false,editable:false" readonly="readonly" id="incidentConfigurationItemName" name="incidentConfigurationItemName" value="${configurationItemName}" onclick="toAddCatelog4Incident();" onchange="incidentConfigurationItemChanged();" />
					   <a href="javascript:void(0)" onclick="clearResource();">清除</a>
					 </td>
				</tr>
			</table>
		</form>
		  <div class="conditionsBtn" >
		      <a href="javascript:void(0)" id="nextPage">提交</a>
		      <a href="javascript:void(0)" onclick="firstWindowCancel();">取消</a>   
		  </div>
	</div>
	
	<!-- 点击关联资源跳转页面 -->
	<div id="divResCi4Incident" class="easyui-window divResCi4Incident" minimizable="false" maximizable="false" collapsible="false" closed="true" modal="true" title="选择资源" style="width: 810px; height: 525px;" >
	        <div class="winbox">
	          <!--资源类型查询条件-->
	          <div class="conditions" id="divFilter">
	              <table>
	                  <tr>
	                      <td>
	                          <b>资源名称：</b>
	                          <input type="text" class="inputs" id="ipt_name"/>
	                      </td>
	                      <td>
	                          <b>IP地址：</b>
	                          <input type="text" class="inputs" id="ipt_ip"  style="width:180px"/>
	                      </td>
	                      <td></td>
	                  </tr>
	                  <tr>
	                      <td>
	                        <b>资源类型：</b>
	                        <select id="ipt_resType"></select>
	                      </td>
	                      <td>
	                        <b>所属部门：</b>
	                        <input id="ipt_dept"  style="width:180px"/>
	                      </td>
	                      <td class="btntd">
	                          <a onclick="reloadIncidentCiTable();">查询</a>
	                          <a onclick="resetForm('divFilter');">重置</a> 
	                      </td> 
	                  </tr>
	              </table>
	          </div>
	  
	          <!--配置项类型信息表 -->
	          <div class="datas">
	              <table id="tblResCiList4Incident"/>
	          </div>
	        </div>
	        <div class="conditionsBtn">
	           <a id="cancelBtn4Incident" onclick="doCancel4Incident();">取消</a>
	        </div>
	</div>
		
    <div id="workOrderProcessNext" class="easyui-window workOrderProcessNext" title="告警派单任务流转" data-options="modal:true,closed:true,minimizable:false,maximizable:false,collapsible:false,resizable: false" style="width:800px;height:540px;">
         <form id="formProcessNext">
             <table id="formtable" class="formtable">
                <tr>
                    <td class="title">当前办理环节：</td>
                    <td>派发工单</td>  
                </tr>
                <tr>
                   <td class="title">办理意见：</td>
                   <td><textarea rows="3" cols="3"  id="summary" class="x2" name="summary"
							style="resize: none" validator="{'length':'0-500'}"></textarea></td>
                </tr>
                <tr>
                   <td class="title">下一步骤：</td>
                   <td>一线工程师解决</td>
                </tr>
                <tr>
                    <td class="title">下一步经办人：</td>
                    <td>
                        <table class="borderbox" style="border-collapse: initial;">
                            <tr>
                                <td colspan="2" id="selectCondition"><input type="radio" checked="checked" value="processHandler" name="selectCondition" id="processHandler" /><label>该流程节点经办人</label><input type="radio" value="faultHandler" name="selectCondition" id="faultHandler" /><label>该类故障负责人</label>
                                <input type="radio" value="resourceHandler" id="resourceHandler" name="selectCondition"/><label>该类资源负责人</label>
                                </td>
                             </tr>
                             <tr>
                                 <td>
                                     <div id="departmentMan4WorkOrder" class="divbox" style="width: 480px;"></div>
                                 </td>
                             </tr>
                        </table>
                     </td>
                </tr>
                <tr> 
                    <td class="title">已选择用户：</td>
                    <td><input type="hidden" name="selectPeopleId"
						id="selectPeopleId4WorkOrder" value="" /> <input name="selectPeopleName"
					    id="selectPeopleName4WorkOrder" readonly="readonly" /> <dfn>*</dfn>
				   		短信通知：<input type="checkbox" id="isSendMessage">是</td>
                </tr>
             </table>
         </form>
    <div class="conditionsBtn">
      <a href="javascript:void(0)">提交</a>
      <a href="javascript:void(0)">返回</a>
      <a href="javascript:void(0)">取消</a>
    </div> 
	</div>
</body>
</html>