<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file = "../../common/pageincluded.jsp" %>

<html>
  <head>
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/fui/themes/default/fui-tree.min.css">
  	<script type="text/javascript"
	src="${pageContext.request.contextPath}/plugin/dTree/dtree.js"></script>
	<!--<script type="text/javascript"
	src="${pageContext.request.contextPath}/fui/fui.min.js"></script>
	<script type="text/javascript"
	src="${pageContext.request.contextPath}/fui/plugin/fui-tree.min.js"></script>-->
	<script type="text/javascript"
	src="${pageContext.request.contextPath}/plugin/dTree/combo/comboJS.js"></script> 	
  	<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/common/fileUtil.js"></script>
	<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/common/ajaxfileupload.js"></script>
	<script type="text/javascript"	src="${pageContext.request.contextPath}/js/platform/portal/platformPortalEdit.js"></script>
 	 	<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/js/common/ajaxFileUpload.js"></script>
 	
 	<script type="text/javascript">
 	<%
 		String portalDefine=(String)request.getAttribute("portalDefine");
 	 %>
 	</script>
 	
  </head>
  
  <body>
  	<div class="location">当前位置：${navigationBar}</div>
	<div id="dataTreeDiv" class="treetable">
	
	</div>
	<div class="treetabler">
	<div class="conditions">
		<table>
		<tr>
			<td>
				<input type="radio" id="editPortalConfig" name="portalView" value="1" checked onclick="javascript:edit();"/>&nbsp;编辑视图配置
			</td>
			<td>
				<input type="radio" id="uploadPortalConfig" name="portalView" value="2" onclick="javascript:edit();"/>&nbsp;上传视图配置&nbsp;
			</td>
		</tr>
		</table>
	</div>
	<div id="divPortalInfo" class="datas tops1" >
		<input id="urlParams" type="hidden"/>
		<table id="tblPortalInfo">
			<tr>
				<td>
				<input type="hidden" id="portalName" value="0"/>
				<input type="hidden" id="portalDesc" value="0"/>
					<textarea id="ipt_portalContent" rows="30" cols="200" ><%=portalDefine %></textarea>
					
				</td>
			</tr>
			<tr>
				<td class="btntd">
					<input class="buttonB" type="button" id="btnSave" value="确定" onclick="javascript:save();"/>
					<input class="buttonB" type="button" id="btnClose" value="取消" onclick="javascript:doBack();"/>
					<input class="buttonB" type="button" id="btnPreview" value="预览" onclick="javascript:previewPortal();"/>
					<input class="buttonB" type="button" id="btnAddWidget" value="添加部件" onclick="javascript:toShowWidget();"/>
					<!-- <input type="button" value="得到光标位置" onclick="getPos(ipt_portalContent)">   -->
				</td>
			</tr>
		</table>
	</div>
	<form id="fm">
	<div id="divUploadPortal" class="datas tops1" style="display:none;">
		<table id="divUploadPortal">
			
	        <tr>
	            <td class="title">上传文件</td>
	            <td>
	              <input id="file" name="file" type="file" style="width:260px" onchange='initFile(this);'/>

	            </td>
			</tr>
			
			<tr>
				<td class="btntd">
					<input class="buttonB" type="button" id="btnUpload" value="上传" onclick="doupload11();"/>
				</td>
			</tr>
			
			
			
			
		<!-- 	</tr>
			<tr>
				<td><input name="ipt_fileupload" id="ipt_fileupload"
					type="file" onchange='initFile(this);' />
					<input id="ipt_previousFileName" type="hidden"/>	
				</td>
			</tr>
			<tr>
				<td class="btntd">
					<input class="buttonB" type="button" id="btnUpload" value="上传" onclick="doUploadFile();"/>
				</td>
			</tr> -->
		</table>
	</div>
	</form>
	</div>
	
	<!-- 编辑树 -->
	<div id="divDataPortal" class="easyui-window" collapsible="false" minimizable="false" maximizable="false"
		closed="true" modal="true" title="视图模板"
		style="width: 600px;">
		
		<table id="tblEditPortal" class="formtable1" >
			<tr>
				<td class="title">模板英文名：</td>
				<td>
				<input id="ipt_portalName"  class="input" validator="{'default':'en','length':'1-20'}"/><b>*</b></td>
			</tr>
			<tr>
				<td class="title">模板中文名：</td>
				<td><input id="ipt_portalDesc"  class="input" validator="{'default':'chnStr','length':'1-20'}"/><b>*</b></td>
				
			</tr>
		</table>
		<div class="conditionsBtn">
			<input class="buttonB" type="button" id="btnSave2" value="确定" onclick="javascript:void(0);"/>
			<input class="buttonB" type="button" id="btnClose2" value="取消" onclick="javascript:void(0);"/>
		</div>
		
	</div>
	
  </body>
</html>
