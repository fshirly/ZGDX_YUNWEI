<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix='security' uri='http://www.springframework.org/security/tags'%>
<html>
  <head>
  	<title>软硬件新增</title>
  </head>
  <body>
  <script type="text/javascript" src="${pageContext.request.contextPath}/js/common/fileUtil.js"></script>
  <script type="text/javascript" src="${pageContext.request.contextPath}/js/platform/supplier/service_add.js"></script>
  
				<div id="divAddInfo" >
				<table id="tblEdit2" class="formtable1">
					<tr>
						<td class="title">服务名称：</td>
						<td>
							<input id="serviceName" name="serviceName" validator="{'default':'*','length':'1-50'}"/><b>*</b>
						</td>
						<td class="title">服务厂商：</td>
						<td>
							<input id="serviceFirmName" name="serviceFirmName" validator="{'default':'*','length':'1-50'}"/>
						</td>
						<td><dfn>*</dfn></td>
					</tr>
					<tr>
						<td class="title">服务期限：</td>
						<td colspan="3">
							<input  id="serviceBeginTime" name="serviceBeginTime" validator="{'default':'*','type':'datebox'}" />&nbsp;-&nbsp;
							<input  id="serviceEndTime" name="serviceEndTime" validator="{'default':'*','type':'datebox'}" /><b>*</b> 
						</td>				
					</tr>
					<tr>
						<td class="title">服务说明：</td>
						<td colspan="3">
							<textarea id="fw_description" name="decription" class="x2" rows="3" validator="{'length':'0-500'}"></textarea> 
						</td>
					</tr>
					<tr>
						<td class="title">相关证明文件：</td>
						<td colspan="2">
							<input id="application_ffile" name="ipt_attachmentURL" type="file" />
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
					<input class="buttonB" type="button" id="btnSave" value="确定" onclick="javascript:toSAdd();"/>
					<input class="buttonB" type="button" id="btnUpdate" value="取消" onclick="javascript:$('#popWin2').window('close');"/>
				</div>
			</div>
	<div id='event_select_dlg' class='event_select_dlg'></div>	
  </body>
</html>
