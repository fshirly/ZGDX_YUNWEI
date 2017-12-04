<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file = "../../common/pageincluded.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<title>数据字典管理</title>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/plugin/dTree/dtree.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/plugin/dTree/combo/comboJS.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/platform/dataDictionaryManage/dataDictionaryManageView.js"></script>
</head>
<body>
	<div class="location">当前位置：${navigationBar}</div>
	<input id="isleaf" type="hidden" value="false"/>

	<div id="dataTreeDiv" class="treetable"></div>
	<div class="treetabler">
	<div class="conditions">
	<table>
		<tr>
			<td>
				<b>内容：</b>
				<input type="text" class="inputs" id="constantItemName" />
			</td>
			<td class="btntd">
				<a href="javascript:reloadTable();" >查询</a>
				<a href="javascript:resetForm();" >重置</a>
			</td>
		</tr>
	</table>
	</div>
	<div class="datas tops1">
						<table id="tblConstantTypeManagement">

						</table>
					</div>
	</div>	
		<!-- end .datas -->
	<div id="divDataDictInfo" class="easyui-window" collapsible="false" minimizable="false" maximizable="false"
		closed="true" modal="true" title="数据字典项"
		style="width: 600px;">
		<input id="flag" type="hidden" value="add"/>
		<input id="modifyId" type="hidden"/>
		<input id="typeOrItem" type="hidden" />
		<input id="ipt_constantTypeIds" type="hidden"  value=""/>
		<table id="tblEditDict" class="formtable1" >
			<tr>
				<td class="title">字典项类型：</td>
				<td><input id="ipt_constantTypeCName" readonly class="input" validator="{'default':'*'}" onclick="javascript:loadConsTypeInfo();"/> <input id="ipt_constantTypeId" type="hidden"  value=""/>
				<!--<input id="ipt_parentTypeName" type="hidden" value=""/>
						<a href="javascript:loadConsTypeInfo();">选择</a>-->
					<b>*</b></td>
			</tr>
			<tr>
				<td class="title">字典标识：</td>
				<td>
				<input id="constantItemIdHide" type="hidden"/>
				<input id="ipt_constantItemId" class="input"  validator="{'default':'ptInteger'}"/>
				<b>*</b></td>
			</tr>
			<tr>
				<td class="title">字典内容：</td>
				<td>
				<input id="constantItemNameHide" type="hidden"/>
				<input id="ipt_constantItemName"  class="input" validator="{'default':'*','length':'1-128'}"/><b>*</b></td>
			</tr>
			<tr>
				<td class="title">字典别名：</td>
				<td>
				<input id="constantItemAliasHide" type="hidden"/>
				<input id="ipt_constantItemAlias"  class="input" validator="{'default':'*','length':'0-50'}"/></td>
			</tr>
			<tr>
				<td class="title">字典项排序：</td>
				<td>
					<input id="ipt_showOrder" class="input"  validator="{'default':'ptInteger'}"/>
				</td>
			</tr>
			<tr>
				<td class="title">字典描述：</td>
				<td><textarea id="ipt_remark1"  class="input" validator="{'length':'0-1000'}"></textarea><input id="ipt_remark" type="hidden" class="input"/></td>
				
			</tr>
		</table>
		<div class="conditionsBtn">
			<input class="buttonB" type="button" id="btnSave" value="确定" onclick="javascript:void(0);"/>
			<input class="buttonB" type="button" id="btnClose" value="取消" onclick="javascript:void(0);"/>
		</div>
		
	</div>
	
			<!-- 字典项详情 -->
	<div id="divDictInfo" class="easyui-window" collapsible="false" minimizable="false" maximizable="false"
		closed="true" modal="true" title="字典项详情"
		style="width: 600px;">
		<table id="tblDictInfo" class="tableinfo1" >
			<tr>
				<td>
					<b class="title">字典项类型：</b>
					<label id="lbl_parentTypeName"  class="input" ></label>
				</td>
			</tr>
			<tr>
				<td>
					<b class="title">字典内容：</b>
					<label id="lbl_constantItemName"  class="input" ></label>
				</td>
				
			</tr>
			<tr>
				<td>
					<b class="title">字典别名：</b>
					<label id="lbl_constantItemAlias"  class="input" ></label>
				</td>
				
			</tr>
			<tr>
				<td>
					<b class="title">字典项排序：</b>
					<label id="lbl_showOrder"  class="input" ></label>
				</td>
			</tr>
			<tr>
				<td>
					<b class="title">字典描述：</b>
					<label id="lbl_remark"  class="input" style="display:inline-block; width:75%; vertical-align: middle;"></label>
				</td>
			</tr>
		</table>
		<div class="conditionsBtn">
			<input class="buttonB" type="button" id="btnClose2" value="关闭" onclick="javascript:void(0);"/>
		</div>
	</div>
	
			<!-- end .datas -->
	<div id="divDataDictTypeInfo" class="easyui-window" collapsible="false" minimizable="false" maximizable="false"
		closed="true" modal="true" title="数据字典类型"
		style="width: 600px;">
		<input id="flag" type="hidden" value="add"/>
		<table id="tblEditDictType" class="formtable1">
			<tr>
				<td class="title">上级类型：</td>
				<td><input id="ipt_parentTypeName" readonly class="input" onclick="javascript:loadParentTypeInfo();"/> <input id="ipt_parentTypeID" type="hidden" value=""/>
				<input id="ipt_constantTypeId" type="hidden" value=""/>
						<!--<a href="javascript:loadParentTypeInfo();" >选择</a>-->
					</td>
			</tr>
			<tr>
				<td class="title">字典类别名：</td>
				<td><input id="ipt_constantTypeName1"  class="input" validator="{'default':'enAndNum','length':'1-30'}"/><b>*</b><input id="ipt_constantTypeName" type="hidden" class="input"/></td>
				
			</tr>
			<tr>
				<td class="title">类别中文名：</td>
				<td><input id="ipt_constantTypeCName1"  class="input" validator="{'default':'chnStr','length':'1-30'}"/><b>*</b><input id="ipt_constantTypeCName" type="hidden" class="input"/></td>
				
			</tr>
			<tr>
				<td class="title">描述：</td>
				<td><textarea  id="ipt_remark2"  class="input" ></textarea></td>
			</tr>
			
		</table>
		<div class="conditionsBtn">
			<input class="buttonB" type="button" id="btnSave3" value="确定" onclick="javascript:void(0);"/>
			<input class="buttonB" type="button" id="btnClose3" value="取消" onclick="javascript:void(0);"/>
		</div>
	</div>
	
		<!-- 字典类型详情 -->
		<div id="divDataTypeInfo" class="easyui-window" collapsible="false" minimizable="false" maximizable="false"
		closed="true" modal="true" title="数据字典类型详情"
		style="width: 600px;">
		<table id="tblDataDictInfo" class="tableinfo1">
			<tr>
				<td>
					<b class="title">上级类型：</b>
					<label id="lbl_parentTypeName"  class="input"></label>
				</td>
			</tr>
			<tr>
				<td>
					<b class="title">字典类别名：</b>
					<label id="lbl_constantTypeName"  class="input" ></label>
				</td>
				
			</tr>
			<tr>
				<td>
					<b class="title">类别中文名：</b>
					<label id="lbl_constantTypeCName"  class="input" ></label>
				</td>
				
			</tr>
			<tr>
				<td>
					<b class="title">描述：</b>
					<label  id="lbl_remark"  class="input" ></label>
				</td>
			</tr>
			
		</table>
		<div class="conditionsBtn">
			<input class="buttonB" type="button" id="btnClose2" value="关闭" onclick="javascript:void(0);"/>
		</div>
	</div>
	<div id='event_select_dlg' class='event_select_dlg'></div>
</body>
</html>
