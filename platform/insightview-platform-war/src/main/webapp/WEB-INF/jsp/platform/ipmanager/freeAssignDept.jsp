<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file = "../../common/pageincluded.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		
	</head>
	<body>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/platform/ipmanager/freeAssignDept.js"></script>
		<%
			String ids =(String)request.getAttribute("ids");
			String subNetId =(String)request.getAttribute("subNetId");
		 %>
		<input type="hidden" id="ids" value="<%= ids%>"/>
		<input type="hidden" id="subNetId" value="<%= subNetId%>"/>
		<table id="tblSubnetInfo" class="formtable1">
			<tr>
				<td class="title">
					选择部门：
				</td>
				<td>
					<input id="ipt_deptId" onfocus="choseDeptTree();" alt="0"  validator="{'default':'*'}"/><b>*</b>
				</td>
			</tr>
		</table>
		<div class="conditionsBtn">
			<input class="buttonB" type="button" id="btnSave" value="确定" onclick="javascript:toAssign();"/>
			<input class="buttonB" type="button" id="btnUpdate" value="取消" onclick="javascript:$('#popWin').window('close');"/>
		</div>
		
		<div id="divChoseDept" class="easyui-window" maximizable="false"
			collapsible="false" minimizable="false" closed="true" modal="true"
			title="选择部门" style="width: 300px; height: 300px;">
			<div id="dataDeptTreeDiv" class="dtree"
				style="width: 100%; height: 200px;">
			</div>
		</div>
	</body>
</html>