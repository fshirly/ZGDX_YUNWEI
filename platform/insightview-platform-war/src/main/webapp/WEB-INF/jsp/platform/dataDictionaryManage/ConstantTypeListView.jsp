<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file = "../../common/pageincluded.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<title>数据字典类型列表</title>



</head>
<body>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/plugin/dTree/dtree.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/plugin/dTree/combo/comboJS.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/platform/dataDictionaryManage/constantTypeListView.js"></script>
<script type="text/javascrict">
	<%
	String flag=(String)request.getAttribute("flag");
	String type=(String)request.getAttribute("type");
 %>
</script>
<div class="winbox">
	<div class="conditions">
	<table>
		<tr>
			<td>
				<b>中文名称：</b>
				<input type="text" class="inputs" id="constantTypeCName" />
			</td>
			<td class="btntd">
				<a href="javascript:reloadTable();">查询</a>
				<a href="javascript:resetForm();">重置</a>
			</td>
		</tr>
	</table>
	</div>
	<div class="datas">
	<input id="flag" type="hidden" value="<%=flag %>"/>
	<input id="type" type="hidden" value="<%=type %>"/>
		<table id="tblConsTypeManagement">
		</table>
	</div>
	
	<div class="conditionsBtn" id="isShow">
		<a onclick="javascript:toConfirmSelect();">确定</a>
           <a onclick="javascript:$('#popWin').window('close');">关闭</a>
	</div> 	
		<!-- end .datas --><!--
	<div id="divDataDictTypeInfo" class="easyui-window" collapsible="false" minimizable="false" maximizable="false"
		closed="true" modal="true" title="数据字典类型"
		style="width: 600px;">
		<input id="flag" type="hidden" value="add"/>
		<table id="tblEditDictType" class="formtable1">
			<tr>
				<td class="title">上级类型：</td>
				<td><input id="ipt_parentTypeName" readonly class="input"/> <input id="ipt_parentTypeID" type="hidden" value=""/>
				<input id="ipt_constantTypeId" type="hidden" value=""/>
						<a href="javascript:loadParentTypeInfo();" >选择</a>
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
				<td><textarea  id="ipt_remark1"  class="input" validator="{'default':'*','length':'1-50'}"></textarea><b>*</b><input id="ipt_remark" type="hidden" class="input"/></td>
			</tr>
			
		</table>
		<div class="conditionsBtn">
			<input class="buttonB" type="button" id="btnSave" value="确定" onclick="javascript:void(0);"/>
			<input class="buttonB" type="button" id="btnClose" value="取消" onclick="javascript:void(0);"/>
		</div>
	</div>
	
		 字典详情 
		<div id="divDataDictInfo" class="easyui-window" collapsible="false" minimizable="false" maximizable="false"
		closed="true" modal="true" title="数据字典详情"
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
-->
</div>
</body>
</html>
