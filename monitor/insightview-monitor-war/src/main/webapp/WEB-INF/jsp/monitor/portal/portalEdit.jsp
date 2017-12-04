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
	<script type="text/javascript"	src="${pageContext.request.contextPath}/js/monitor/portal/portalEdit.js"></script>
 	<script type="text/javascript">
 	<%
 		String portalDefine=(String)request.getAttribute("portalDefine");
 		System.out.println("portalDefine= "+portalDefine);
 	 %>
 	</script>
 	<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/js/common/ajaxFileUpload.js"></script>
 	
  </head>
  
  <body>
  	<div class="location">当前位置：${navigationBar}</div>
	<div id="dataTreeDiv" class="treetable">
	
	</div>
	<div class="treetabler">
	<div class="conditions">
		<table>
		<tr>
		<!-- 
			<td>
				<input type="radio" id="editPortal" name="portalView" value="0" onclick="javascript:edit();"/>&nbsp;编辑视图
			</td>
			 -->
			<td>
				<input type="radio" id="editPortalConfig" name="portalView" value="1" checked onclick="javascript:edit();"/>&nbsp;编辑视图配置
			</td>
			<td>
				<input type="radio" id="uploadPortalConfig" name="portalView" value="2" onclick="javascript:edit();"/>&nbsp;上传视图配置&nbsp;
			</td>
			<td>
				监测对象：<input id="urlParams" type="hidden">
				<input id="ipt_deviceIp" readonly class="input" style="width:200px"/> <input id="ipt_deviceId" type="hidden" value=""/>
				<input id="ipt_moClassId" type="hidden" value=""/>
				<input id="ipt_networkIfId" type="hidden"/><input id ="ipt_ifId" type="hidden"/>
				<input id="ipt_oracleId" type="hidden" value=""/>
				<input id="ipt_middleWareId" type="hidden" value=""/>
				<input id="ipt_db2InsMoId" type="hidden" value=""/>
				<input id="ipt_db2DbMoId" type="hidden" value="" />
				<input id="ipt_jdbcPoolId" type="hidden" value="" />
				<input id="ipt_moId" type="hidden" value="" />
				<input id="ipt_webSiteMoID" type="hidden" value=""/>
				<a href="javascript:loadDeviceInfo();" id="btnChose" >选择</a>
			</td>
			<!--<td>
				接口名称：<input id="ipt_networkIfId" type="hidden"/><input id ="ipt_ifId" type="hidden"/><input id="ipt_ifName"  readonly="readonly" />
				<a href="javascript:loadMoNetqorkIf();">选择</a>
			</td>
			<td>
				数据库名称：
				<input id="ipt_ip" readonly class="input"/> <input id="ipt_oracleId" type="hidden" value=""/>
				<a href="javascript:loadOracleInfo();" >选择</a>
			</td>
		--></tr>
		</table>
	</div>
	<div id="divPortalInfo" class="datas tops1" >
		<table id="tblPortalInfo">
			<tr>
				<td>
				<input type="hidden" id="ipt_portalName" value="0"/>
				<input type="hidden" id="portalName" value="0"/>
					<textarea id="ipt_portalContent" rows="30" cols="200" ><%=portalDefine %></textarea>
					
				</td>
			</tr>
			<tr>
				<td class="btntd">
					<input class="buttonB" type="button" id="btnSave" value="确定" onclick="javascript:save();"/>
					<input class="buttonB" type="button" id="btnClose" value="取消" onclick="javascript:doBack();"/>
					<input class="buttonB" type="button" id="btnPreview" value="预览" onclick="javascript:previewPortal2();"/>
					<input class="buttonB" type="button" id="btnAddWidget" value="添加部件" onclick="javascript:toShowWidget();"/>
					<!-- <input type="button" value="得到光标位置" onclick="getPos(ipt_portalContent)">   -->
				</td>
			</tr>
		</table>
	</div>
	<form id="fm" >
	<div id="divUploadPortal" class="datas tops1" style="display:none;">
		<table id="divUploadPortal">
			
	        <tr>
	            <td class="title">上传文件:</td>
	            <td>
	              <input id="file" name="file" type="file" style="width:260px" onchange='initFile(this);'/>

	            </td>
			</tr>
			
			<tr>
				<td class="btntd">
					<input class="buttonB" type="button" id="btnUpload" value="上传" onclick="doupload();"/>
				</td>
			</tr>
			
		</table>
	</div>
	</form>
	</div>
	
	
	
      
 
	
	
	
	
  </body>
</html>
