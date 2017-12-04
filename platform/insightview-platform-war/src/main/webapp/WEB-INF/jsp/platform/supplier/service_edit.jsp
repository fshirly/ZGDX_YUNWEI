<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix='security' uri='http://www.springframework.org/security/tags'%>
<html>
  <head>
  	<title>软硬件新增</title>
  </head>
  <body>
  <script type="text/javascript" src="${pageContext.request.contextPath}/js/platform/supplier/service_edit.js"></script>
				<div id="divAddInfo" >
				<table id="tblEdit2" class="formtable1">
					<tr>
						<td class="title">服务名称：</td>
						<td>
							<input id="e_serviceName" name="e_serviceName" validator="{'default':'*','length':'1-50'}"/><b>*</b>
						</td>
						<td class="title">服务厂商：</td>
						<td colspan="3">
							<input id="e_serviceFirmName" name="e_serviceFirmName" validator="{'default':'*','length':'1-50'}"/>
						</td>
						<td><b>*</b></td>		
					</tr>
					<tr>
						<td class="title">服务期限：</td>
						<td colspan="3">
							<input id="e_serviceBeginTime" name="e_serviceBeginTime" validator="{'default':'*','type':'datebox'}"/>&nbsp;-&nbsp;
						<input id="e_serviceEndTime" name="e_serviceEndTime" validator="{'default':'*','type':'datebox'}" /><b>*</b>
						</td>				
					</tr>
					<tr>
						<td class="title">服务说明：</td>
						<td colspan="3">
							<textarea id="e_description" name="description" class="x2" rows="3" validator="{'length':'0-500'}"></textarea> 
						</td>
					</tr>
					<tr>
						<td class="title">相关证明文件：</td>
						<td colspan="2">
							<input id="application_ffile" name="ipt_attachmentURL" type="file" />&nbsp;&nbsp;&nbsp;&nbsp;
						</td>
						<td id="showFile"><a id="sfilename"></a></td>
					</tr>
					<!-- <tr id="showFile">
						<td class="title">已上传文件：</td>
						<td colspan="3">
							<a id="sfilename"></a>
						</td>
					</tr> -->
				</table>		
				<div class="conditionsBtn">
					<input class="buttonB" type="button" id="btnSave" value="确定" onclick="javascript:toSEdit();"/>
					<input class="buttonB" type="button" id="btnUpdate" value="取消" onclick="javascript:$('#popWin2').window('close');"/>
				</div>
			</div>
	<div id='event_select_dlg' class='event_select_dlg'></div>	
  </body>
</html>
