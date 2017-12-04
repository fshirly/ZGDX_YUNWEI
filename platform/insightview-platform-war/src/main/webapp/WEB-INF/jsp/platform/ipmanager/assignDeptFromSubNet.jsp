<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file = "../../common/pageincluded.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		
	</head>
	<body>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/platform/ipmanager/assignDeptFromSubNet.js"></script>
		<%
			String subNetId =(String)request.getAttribute("subNetId");
		 %>
	  <div style="overflow: hidden">
		<input type="hidden" id="subNetId" value="<%= subNetId%>"/>
		<table id="tblSubnetInfo" class="formtable1">
			<tr>
 	  	    	<td class="title">
					指定地址范围：
				</td>
				<td>
	  	  		    <input id="ipt_startIp" class="s50" validator="{'default':'ipAddr','length':'1-64'}"/><b>*</b>- 
	    		</td>
			</tr>
			<tr>
				<td class="title"></td>
				<td colspan="2">
	    		    <input id="ipt_endIp" class="s50" validator="{'default':'ipAddr','length':'1-64'}"/><b>*</b>
	    		</td>
			</tr>
			<tr>
				<td class="title">
					选择部门：
				</td>
				<td>
					<input id="ipt_deptId" onfocus="choseDeptTree();" alt="0" validator="{'default':'*'}"/><b>*</b>
				</td>
			</tr>
		</table>
		<table class="tableinfo1">
			<tr>
				<td>
					<label colspan="2"> 提示：如果您不确定所指定的IP地址是否空闲，请到空闲地址中进行挑选。</label>
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
	  </div><%--
	  
	  <div id="divUseInfo" class="easyui-window" minimizable="false"
				closed="true" maximizable="false" collapsible="false" modal="true"
				title="提示" style="width: 600px;">
				<table cellspacing="10" class="formtable1">
					<tr>
						<td></td>
						<td>
							<label id="messageTip" class="input"></label>
							<input id="freeIds" type="hidden"/>
						</td>
					</tr>
				</table>
				
				<div class="conditionsBtn">
					<a href="javascript:var freeIds=document.getElementById('freeIds').value ; viewFreeAddress(freeIds);" class="fltrt">查看空闲地址列表</a>
					<a href="javascript:var freeIds=document.getElementById('freeIds').value ; doAssign(freeIds,2);" class="fltrt">确定</a>
					<a href="javascript:cancle();" class="fltrt">取消</a>
				</div>
			</div>
	--%></body>
</html>