<%@ page language="java"  pageEncoding="utf-8"%>
<%@ page import="java.lang.*;"  %>
<%@ include file = "../../common/pageincluded.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <%
    	String templateID = (String)request.getAttribute("templateID");
     %>
  </head>
  
  <body>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/js/monitor/molist/sysMoTemplateModify.js"></script>
		
  	<input id="ipt_templateID" type="hidden" value="<%=templateID %>"/>
	<div id="divMObject" class="easyui-window" maximizable="false"
		collapsible="false" minimizable="false" closed="true" modal="true"
		title="选择对象类型" style="width: 400px; height: 450px;">
		<div id="dataMObjectTreeDiv" class="dtree"
			style="width: 100%; height: 200px;">
		</div>
	</div>
	
		<!-- 新增模板 -->
	<div id="divUpdateTempletInfo">
		<table id="tblUpdateTemplet" class="formtable1">
			<tr>
				<td class="title">模板名称：</td>
				<td><input id="ipt_templateName" class="input" validator="{'defalut':'*';'length':'0-128'}"/><b>*</b></td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
			<tr id="classFlag">
				<td class="title">
					监测对象类型：
				</td>
				<td>
					<input id="ipt_moClassId"  class="input" onfocus="choseMObjectTree();" validator="{'default':'*','length':'1-64'}" /><b>*</b>
				</td>
			</tr>
			<tr id="hostFlag">
				<td class="title">
					模板描述：
				</td>
				<td>
					<textarea rows="6" class="x2" id="ipt_descr" validator="{'length':'0-50'}"></textarea>
				</td>
			</tr>
		</table>
		<table id="monitor" class="formtable">
			<tr id="monitorTitle"><td class="title" style="float: left;">监测器配置</a></tr>
			</table>
		<div class="conditionsBtn">
			<a href="javascript:void(0);" id="btnSave" onclick="javascript:toUpdate();">确定</a>
			<a href="javascript:void(0);" id="btnBack" onclick="javascript:$('#popWin').window('close');">取消</a>
						
		</div>
	</div> 
	
  </body>
</html>
