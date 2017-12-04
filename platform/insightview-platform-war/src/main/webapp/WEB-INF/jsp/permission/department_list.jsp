<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="../common/pageincluded.jsp"%>
<html>
	<head>
		<link rel="stylesheet" type="text/css"
   		 href="${pageContext.request.contextPath}/plugin/select2/select2.css" />
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/plugin/dTree/dtree.js"></script>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/plugin/dTree/combo/comboJS.js"></script>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/js/permission/department.js"></script>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/js/common/commonPlugins.js"></script>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/plugin/select2/select2.js"></script>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/js/permission/LRSelect.js"></script>			
	</head>
	
	<body>
		<style>
	      .label{
	      width:500px;
	        max-height: 100px;
	        float: left;
	        overflow: auto;
	        word-wrap: break-word;
	      }
	       .label2{
	      	width:180px;
	        max-height: 100px;
	        float: left;
	        overflow: auto;
	        word-wrap: break-word;
	      }
	    </style>
		<div class="location">
			当前位置：${navigationBar}
		</div>
		<div id="dataTreeDiv" class="treetable"></div>
		<div class="treetabler">
			<div class="conditions" id="divFilter">
			<form id="frmExport"
				action="${pageContext.request.contextPath}/platform/sysdeptExport/exportVal">
				<input name="colNameArrStr" id="iptColName" type="hidden" /> 
				<input name="colTitleArrStr" id="iptTitleName" type="hidden" /> 
				<input name="exlName" id="iptExlName" value="部门列表.xls" type="hidden" />
				<input name="treeType" id="treeType_id" type="hidden"/>
				<input name="nodeID" id="nodeID_id" type="hidden" />
				<table>
					<tr>
						<td>
							<b>部门名称：</b>
							<input type="text" id="iptFilterDepartmentName" name="sysDeptBean.deptName"/>
						</td>
						<td>
							<b>所属单位：</b>
							<input id="selFilterParentId" type="text" class="inputs" name="sysDeptBean.organizationName"/>
						</td>
						<td class="btntd">
							<a href="javascript:void(0);" onclick="reloadTable();">查询</a>
							<a href="javascript:void(0);"
								onclick="resetFormFilter('divFilter');">重置</a>
						</td>
					</tr>
				</table>
				</form>
			</div>
			
			<!-- begin .datas -->
			<div class="datas tops1">
				<table id="tblDepartment">
				</table>
			</div>
		</div>
		
		<!-- end .datas -->
		<div id="divDepartment" class="easyui-window" minimizable="false"
			maximizable="false" collapsible="false" closed="true" modal="true"
			title="部门信息" style="width: 800px;">
			<script>
			$(document).ready(function() {
			  $(".window-mask").css("height","100%");
			  $(".window-mask").css("width","100%");
			  $(".panel.window").css("top","100px");
			})
			</script>
			<input id="ipt_deptId" type="hidden" />
			<table cellspacing="10" id="tblDeptAdd" class="formtable">
				<tr>
					<td class="title">
						部门名称：
					</td>
					<td>
						<input id="ipt_deptName" class="input"  validator="{'default':'*','length':'1-80'}" /><b>*</b>
						<input id="ipt_nodeID" type="hidden" />
					</td>
					<td class="title">
						所属单位：
					</td>
					<td>
						<input id="ipt_organizationID" class="input" readonly
							validator="{'default':'*'}"
							onfocus="doChoseParentOrg();" />
						<b>*</b>
					</td>
				</tr>
				
				<tr>
					<td class="title">
						上级部门：
					</td>
					<td>
						<input id="ipt_parentDeptID" onfocus="choseDeptTree();" alt="0" />
						<a id="isShow" href="javascript:clear();" style="display: none">清空</a>
					</td>
					<td class="title">
						部门领导：
					</td>
					<td>
						<input id="ipt_headOfDept" onfocus="choseHeadTree();" alt="0" />
					</td>
				</tr>
				
				<tr>
					<td class="title">
						部门编码：
					</td>
					<td colspan="3">
						<input id="ipt_deptCode" validator="{'default':'*','length':'1-30'}" onblur="checkDeptCode()"/><b>*</b>
					</td>
				</tr>
				
				<tr>
					<td class="title">
						部门描述：
					</td>
					<td colspan="3">
						<textarea class="x2" id="ipt_descr" rows="3" cols="40" validator="{'length':'0-255'}"></textarea>
					</td>
				</tr>
			</table>
			
			<div class="conditionsBtn">
				<a href="javascript:void(0);" id="btnSave" class="fltrt">确定</a>
				<a href="javascript:void(0);" id="btnUpdate" class="fltrt">取消</a>
			</div>
		</div>

		<div id="viewDepartment" class="easyui-window" minimizable="false"
			maximizable="false" collapsible="false" closed="true" modal="true"
			title="部门信息" style="width: 800px;">
			<input id="flag" type="hidden" value="add" />
			<script>
			$(document).ready(function() {
			  $(".window-mask").css("height","100%");
			  $(".window-mask").css("width","100%");
			  $(".panel.window").css("top","100px");
			})
			</script>
			<table id="tblDeptView" class="tableinfo">
				<tr>
					<td>
						<div style='float:left;'><b class="title">部门名称：</b></div>
						<div class="label2"><label id="lbl_deptName"></label></div>
					</td>
					<td>
						<div style='float:left;'><b class="title">所属单位：</b></div>
						<div class="label2"><label id="lbl_organizationName" /></div>
					</td>
				</tr>
				
				<tr>
					<td>
						<div style='float:left;'><b class="title">上级部门：</b></div>
						<div class="label2"><label id="lbl_parentDeptName" /></div>
					</td>
					<td>
						<div style='float:left;'><b class="title">部门领导：</b></div>
						<div class="label2"><label id="lbl_headName" /></div>
					</td>
				</tr>
				
				<tr>
					<td colspan="2">
						<div style='float:left;'><b class="title">部门编码：</b></div>
						<div class="label"><label id="lbl_deptCode"/></div>
					</td>
				</tr>
				
				<tr>
					<td colspan="2">
						<div style='float:left;'><b class="title">部门描述：</b></div>
						<div class="label"><label id="lbl_descr"  class="x2"/></div>
					</td>
				</tr>
			</table>
			
			<div class="conditionsBtn">
				<a href="javascript:void(0);" id="btnClose" class="fltrt">关闭</a>
			</div>
		</div>

		<div id="divChoseOrg" class="easyui-window" maximizable="false"
			collapsible="false" minimizable="false" closed="true" modal="true"
			title="选择上级单位" style="width: 300px; height: 300px;">
			<div id="dataTreeDivs" class="dtree"
				style="width: 100%; height: 200px;"></div>
		</div>
		
		<div id="divChoseParentDept" class="easyui-window" maximizable="false"
			collapsible="false" minimizable="false" closed="true" modal="true"
			title="选择上级部门" style="width: 300px; height: 300px;">
			<div id="dataParentDeptTreeDiv" class="dtree"
				style="width: 100%; height: 200px;">
			</div>
		</div>

		<div id="divChoseHead" class="easyui-window" maximizable="false"
			collapsible="false" minimizable="false" closed="true" modal="true"
			title="选择部门领导" style="width: 300px; height: 300px;">
			<div id="dataHeadTreeDiv" class="dtree"
				style="width: 100%; height: 200px;">
			</div>
		</div>
		
				
				<!-- 动态填加产品目录信息 -->
		<div id="divExportDate" class="easyui-window" minimizable="false"
			closed="true" modal="true" title="选择导出列"
			style="width: 600px; height: 400px;">
			<table cellspacing="5" class="tdpad">
				<tr>
					<td style='vertical-align: bottom;'><select id="selLeft"
						multiple="multiple" style="width: 150px; height: 200px"
						class="dataSelect">
							<option alt="deptCode">部门编码</option>
							<option alt="deptName">部门名称</option>
							<option alt="parentDeptName">上级部门</option>
							<option alt="organizationBean.organizationName">所属单位</option>
							<option alt="headName">部门领导</option>
							<option alt="descr">部门描述</option>
					</select></td>
					<td style="width: 30px; text-align: center;">
						<button id="img_L_AllTo_R" type="button" style="width: 50px">>>></button>
						<button id="img_L_To_R" type="button" style="width: 50px">></button> <br />
						<button type="button" onclick="upSelectedOption()" style="width: 50px">上移</button><br /> 
						<button type="button" onclick="downSelectedOption()" style="width: 50px">下移</button><br /> 
						<button id="img_R_To_L" type="button" style="width: 50px"><</button> <br />
						<button id="img_R_AllTo_L" type="button" style="width: 50px"><<<</button> 
					</td>
					<td style="vertical-align: bottom;"><select id="selRight"
						multiple="multiple" style="width: 150px; height: 200px"
						class="dataSelect">
					</select></td>
				</tr>
			</table>
			<div class="conditionsBtn">
				<button onclick="doExport()" type="button">导出</button>
			</div>
		</div>
	</body>
</html>