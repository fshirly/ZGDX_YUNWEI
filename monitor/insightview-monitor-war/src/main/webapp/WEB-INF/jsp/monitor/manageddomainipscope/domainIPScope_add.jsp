<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file = "../../common/pageincluded.jsp" %>

<html>
	<head>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/plugin/dTree/dtree.js"></script>
	</head>

	<body>
		<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/monitor/manageddomainipscope/domainIPScope_add.js"></script>
		
		<div id="divDomainIPScopeAdd">
		
			<table id="tblDomainIPScopeAdd" class="formtable1">
				<tr>
					<td class="title">管理域：</td>
					<td>
					  <input id ="ipt_domainID" readonly="readonly" onfocus="choseDomainTree();" validator="{'default':'*'}"/><b>*</b>
					</td>
				</tr>
				<tr>	
					<td class="title">范围类型：</td>
					<td colspan="3">
					  <input type="hidden" id="ipt_scopeType"/>
					  <input type="radio" id="ipt_scopeType1" name="scopeType" checked value="1"  onclick="javascript:edit();"/>子网&nbsp;
					  <input type="radio" id="ipt_scopeType2" name="scopeType" value="2" onclick="javascript:edit();"/>IP范围&nbsp;
					</td>
				</tr>
				<tr>
					<td class="title">起始IP：</td>
					<td><input id ="ipt_startIPStr" validator="{'default':'*'}" /><b>*</b></tr>
				<tr id="show1" style="display: none">
					<td class="title">终止IP：</td> 
					<td><input id ="ipt_endIPStr" validator="{'default':'*'}" onblur="javascript:setNetwork();"/><b>*</b></td>
				</tr>
				<tr id="show2">
					<td class="title">网络掩码：</td> 
					<td><input id ="ipt_network" validator="{'default':'*'}" onblur="javascript:setEndip();"/><b>*</b></td>
				</tr>
				<tr>
					<td class="title">管理域描述：</td>
					<td><textarea rows="3" id="ipt_domainDescr" readonly="readonly"></textarea></td>
				</tr>
			</table>
			<div class="conditionsBtn">
				<input class="buttonB" type="button" id="btnSave" value="确定" onclick="javascript:toAdd();"/>
				<input class="buttonB" type="button" id="btnUpdate" value="取消" onclick="javascript:$('#popWin').window('close');"/>
			</div>
		</div>
		<div id="divDomain" class="easyui-window" maximizable="false"
		collapsible="false" minimizable="false" closed="true" modal="true"
		title="选择管理域" style="width: 300px; height: 300px;">
			<div id="dataDomainTreeDiv" class="dtree"
				style="width: 100%; height: 200px;">
			</div>
		</div>
	</body>
</html>
