<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file = "../../common/pageincluded.jsp" %>
<html>
<head>
<link href="${pageContext.request.contextPath}/plugin/select2/select2.css" rel="stylesheet"/>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/commonPlugins.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/plugin/select2/select2.js"></script>	
<!-- mainframe -->
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/main.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/plugin/dTree/dtree.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/plugin/dTree/combo/comboJS.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/platform/rescatedef/rescatedef_list.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/common/ajaxfileupload.js"></script>
</head>
<body>
	<div class="rightContent">
		<div class="location">当前位置：${navigationBar }</div>
		   <div class="conditions" id="divFilter">
			 <table>
				<tr>
					<td>
						<b>产品目录名称：</b>				
						<input type="text" id="resCategoryName" />
					</td>
					<td class="btntd">
						 <a href="javascript:void(0);" onclick="reloadTable();" >查询</a>		
						 <a href="javascript:void(0);" onclick="resetForm('divFilter');">重置</a> 
					</td>
				</tr>
			</table>
		</div>
		
		<!-- begin .datas -->
		<div class="datas">
			<table id="tblDataList">
			</table>
		</div>
		<!-- end .datas -->
		
		<div id="divAddEdit" class="easyui-window" title="产品目录"  minimizable="false"  resizable="false" maximizable="false"
		closed="true"  collapsible="false" modal="true" style="width: 800px;">
		    <form id="ff"  method="post" enctype="multipart/form-data">
			<input id="ipt_resCategoryID" type="hidden" />
			<table id="tblDetailInfo" class="formtable">
				<tr>
					<td  class="title">产品目录名称：</td>
					<td><input type="text" id="ipt_resCategoryName" name="ipt_resCategoryName"  validator="{'length':'1-64'}" /><b>*</b></td>
					<td  class="title">产品别名：</td>
					<td><input  id="ipt_resCategoryAlias" name="ipt_resCategoryAlias"  /></td>
				</tr>
				<tr>
					<td  class="title">生产厂家：</td>
					<td>
						<select id="resManufacturerID" validator="{'default':'*','type':'select2'}">
       							<option value="-1">请选择...</option>
						</select>						
					 </td>
					<td  class="title">设备型号：</td>
					<td><input type="text" id="ipt_resModel" name="ipt_resModel"  validator="{'length':'1-64'}" /> <b>*</b>						
					</td>
				</tr>
				<tr>
					<td  class="title">产品类型：</td>
					<td><input type="hidden" id="ipt_resTypeID" name="ipt_resTypeID"   />
						<input type="text" id="ipt_resTypeName" name="ipt_resTypeName"  readonly="readonly" validator="{'length':'1-64'}" onclick="toAddTypelog()" /><b>*</b>
					 </td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td  class="title">产品描述：</td>
					<td colspan="3">
						<textarea rows="3" id="ipt_resCategoryDescr" name="ipt_resCategoryDescr" class="x2" ></textarea>
					</td>					
				</tr>
				<!--<tr>
					<td  class="title">设置图标：</td>
					<td colspan="3"><input type="hidden" id="ipt_imageFileNames" name="ipt_imageFileNames"   />
						<input type="file"  id="file1"  onchange="doUpload()"/>
					<p>
						图片预览
					</p>
						<img id="imgPre" src="" width="100" height="100"/>
					</td>
				</tr>
			--></table>
			</form>
			<div class="conditionsBtn">
					<a href="javascript:void(0);" id="btnSave" >确定</a>
					<a href="javascript:void(0);" id="btnUpdate" >取消</a>					
			</div>
		</div>	
	 <!--view -->		
		<div id="divView" class="easyui-window" title="产品目录详情" collapsible="false" minimizable="false" maximizable="false"
		closed="true"  style="width: 800px; ">
			<table id="tblViewInfo" class="tableinfo" >
				<tr>
					<td >
							<b class="title">产品目录名称：</b>
							<label id="lbl_resCategoryName"></label>
					</td>
					<td>
							<b class="title">产品别名：</b>
							<label id="lbl_resCategoryAlias"></label>
					</td>
				</tr>
				<tr>
					<td >
							<b class="title">生产厂家：</b>
							<label id="lbl_resManufacturerName"></label>
					</td>
					<td>
							<b class="title">设备型号：</b>
							<label id="lbl_resModel"></label>
					</td>
				</tr>
				<tr>
					<td >
							<b class="title">产品类型：</b>
							<label id="lbl_resTypeName"></label>
					</td>
					<td >
							<b class="title">&nbsp;</b>
							<label >&nbsp;</label>
					</td>							
				</tr>
				<tr>
					<td colspan="2">
							<b class="title">产品描述：</b>
							<label id="lbl_resCategoryDescr"></label>
					</td>					
				</tr>			
			</table>
			<div class="conditionsBtn">
					<input class="buttonB" type="button" id="btnClose2" value="关闭" onclick="javascrlbl:void(0);"/>		
			</div>
		</div>
	<!--end view  -->
	
	<div id="divTypeDialog" title="产品类型信息">
		<!--对应资源查询条件-->
		<div class="winbox">
			<div class="conditions" id="divTypeFilter">
				<table>
					<tr>
						<td>
							<b>类型名称：</b>				
							<input type="text" id="resTypeName" />
						</td>
						<td class="btntd">
							<a href="javascript:void(0);" onclick="reloadTypeTable();">查询</a>
							<a href="javascript:void(0);" onclick="resetForm('divTypeFilter');">重置</a>
						</td>
					</tr>
				</table>
			</div>
			<!--对应资源信息表 -->
			<div class="datas">
				<table id="tblTypeList"></table>
			</div>
			<div class="conditionsBtn">
		      	<a onclick="doAddTypelog();">确认</a>
		      	<a onclick="doTypeCancel();">取消</a>
		    </div>
		</div>
	</div>	
		
	</div>
</body>
</html>