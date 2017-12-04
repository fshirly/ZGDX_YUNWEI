<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix='security' uri='http://www.springframework.org/security/tags'%>
<html>
  <head>
  	<title>软硬件新增</title>
  </head>
  <body>
  <script type="text/javascript" src="${pageContext.request.contextPath}/js/common/fileUtil.js"></script>
  <script type="text/javascript" src="${pageContext.request.contextPath}/js/platform/supplier/softhard_add.js"></script>
				<div id="divAddInfo" >
				<table id="tblEdit1" class="formtable1">
					<tr>
						<td class="title">资质名称：</td>
						<td>
							<input id="proxyName" name="proxyName" validator="{'default':'*','length':'1-50'}"/><b>*</b>
						</td>
						<td class="title">代理产品类型：</td>
						<td>
							<select id="productType" name="productType" class="easyui-combobox" data-options="editable:false">
								<option value="1">软件</option>
							    <option value="2">硬件</option>
							</select>
						</td>				
					</tr>
					<tr>
						<td class="title">代理厂商：</td>
						<td colspan="3">
							<input id="proxyFirmName" name="proxyFirmName" validator="{'default':'*','length':'1-50'}"/><b>*</b>
						</td>
					</tr>
					<tr>
						<td class="title">代理期限：</td>
						<td colspan="3">
							<input id="proxyBeginTime" name="proxyBeginTime" validator="{'default':'*','type':'datebox'}" />&nbsp;-&nbsp;
							<input id="proxyEndTime" name="proxyEndTime" validator="{'default':'*','type':'datebox'}" /><b>*</b> 
						</td>				
					</tr>
					<tr>
						<td class="title">代理说明：</td>
						<td colspan="3">
							<textarea id="description" name="description" class="x2" rows="3" validator="{'length':'0-500'}"></textarea> 
						</td>
					</tr>
					<tr>
						<td class="title">相关证明文件：</td>
						<td colspan="2">
							<input id="application_sfile" name="ipt_attachmentURL" type="file" />
						</td>
						<td id="hshowFile"><a id="shfilename"></a></td>
					</tr>
					<!-- <tr id="hshowFile">
						<td class="title">已上传文件：</td>
						<td colspan="3">
							<a id="shfilename"></a>
						</td>
					</tr> -->
				</table>		
				<div class="conditionsBtn">
					<input class="buttonB" type="button" id="btnSave" value="确定" onclick="javascript:toAdd();"/>
					<input class="buttonB" type="button" id="btnUpdate" value="取消" onclick="javascript:$('#popWin2').window('close');"/>
				</div>
			</div>
	<div id='event_select_dlg' class='event_select_dlg'></div>	
  </body>
</html>
