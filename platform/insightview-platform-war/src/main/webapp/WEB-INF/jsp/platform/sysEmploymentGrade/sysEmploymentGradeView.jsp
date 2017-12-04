<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="../../common/pageincluded.jsp"%>
<html>
	<head>
		<title>职务等级管理</title>
		<!-- mainframe -->
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/plugin/dTree/dtree.js"></script>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/plugin/dTree/combo/comboJS.js"></script>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/js/platform/sysEmploymentGrade/sysEmploymentGradeView.js"></script>
	</head>

	<body>
		<div class="location">
			当前位置：${navigationBar}
		</div>
		<input id="isleaf" type="hidden" value="false" />
		<div id="dataTreeDiv" class="treetable"></div>
		<div class="treetabler">
			<div class="conditions">
				<table>
					<tr>
						<td>
							<b>所属组织：</b>
							<input type="text" class="inputs" id="organizationName" />
						</td>
						<td>
							<b style="width: 110px;">职位级别名称：</b>
							<input type="text" class="inputs" id="gradeName" />
						</td>
						<td class="btntd">
							<a href="javascript:reloadTable();">查询</a>
							<a href="javascript:resetForm1();">重置</a>
						</td>
					</tr>
				</table>
			</div>
			<div class="datas tops1">
				<table id="tblEmploymentGrade"></table>
				<!-- end .pageToolBar-->
			</div>
		</div>

		<!-- end .datas -->
		<div id="divEmpGradeInfo" class="easyui-window" minimizable="false"
			maximizable="false" collapsible="false" closed="true" modal="true"
			title="职务等级信息" style="width: 600px;">
			<input id="flag" type="hidden" value="add" />
			<table id="tblEmpGradeAdd" cellspacing="10" class="formtable1">
				<tr>
					<td class="title">
						职务等级名称：
					</td>
					<td>
						<input id="ipt_gradeName" class="input"
							validator="{'default':'*','length':'1-80'}" />
						<b>*</b>
						<input id="ipt_nodeID" type="hidden" />
					</td>
				</tr>
				
				<tr>
					<td class="title">
						所属组织：
					</td>
					<td>
						<input id="ipt_organizationID" readonly class="input"
							validator="{'default':'*'}"
							onfocus="doChoseOrg();" />
						<b>*</b>
					</td>
				</tr>

				<tr>
					<td class="title">
						职务描述：
					</td>
					<td colspan="3">
						<textarea id="ipt_descr" rows="3" cols="40"></textarea>
					</td>
				</tr>
			</table>
			
			<div class="conditionsBtn">
				<a href="javascript:void(0);" id="btnSave" class="fltrt">确定</a>
				<a href="javascript:void(0);" id="btnUpdate" class="fltrt">取消</a>
			</div>
		</div>

		<div id="viewEmpGradeInfo" class="easyui-window" minimizable="false"
			maximizable="false" collapsible="false" closed="true" modal="true"
			title="职务等级信息" style="width: 600px;">
			<input id="flag" type="hidden" value="add" />
			<table id="tblEmpGradeView" cellspacing="10" class="tableinfo1">
				<tr>
					<td>
						<b class="title">职务等级名称：</b>
						<label id="lbl_gradeName"></label>
					</td>
				</tr>
				
				<tr>
					<td>
						<b class="title">所属组织：</b>
						<label id="lbl_organizationName"></label>
					</td>
				</tr>

				<tr>
					<td>
						<b class="title">职务描述：</b>
						<label id="lbl_descr" style="display:inline-block; width:75%; vertical-align: middle;"></label>
					</td>
				</tr>

			</table>
			
			<div class="conditionsBtn">
				<a href="javascript:void(0);" id="btnClose" class="fltrt">关闭</a>
			</div>
		</div>

		<div id="divChoseOrg" class="easyui-window" maximizable="false"
			collapsible="false" minimizable="false" closed="true" modal="true"
			title="选择所属组织" style="width: 300px; height: 300px;">
			<div id="dataTreeDivs" class="dtree"
				style="width: 100%; height: 200px;">
			</div>
		</div>
	</body>
</html>
