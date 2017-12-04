<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="../common/pageincluded.jsp"%>
<html>
	<head>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/plugin/dTree/dtree.js"></script>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/plugin/dTree/combo/comboJS.js"></script>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/js/permission/sysrole.js"></script>
	</head>
	<body>
		<div class="rightContent">
			<div class="location">
				当前位置：${navigationBar}
			</div>
			<div class="conditions" id="divFilter">
				<table>
					<tr>
						<td>
							<b>角色名称：</b>
							<input type="text" id="txtFilterRoleName" />
						</td>
						<td class="btntd">
							<a href="javascript:void(0);" onclick="reloadTable();">查询</a>
							<a href="javascript:void(0);" onclick="resetForm('divFilter');">重置</a>
						</td>
					</tr>
				</table>
			</div>
			<!-- begin .datas -->
			<div class="datas">
				<table id="tblSysRole">
				</table>
			</div>
			<!-- end .datas -->

			<div id="divMenuConfig" class="easyui-window" minimizable="false"
				maximizable="false" collapsible="false" closed="true" modal="false"
				title="配置菜单" style="width: 600px; height: 480px;">
					<div id="dataMenuTreeDiv" class="dtree"
						style="width: 100%; height: 351px; overflow: auto;">
					</div>
				<div class="conditionsBtn" style="position:absolute">
					<a href="javascript:void(0);" onclick="doConfigMenu();"
						class="fltrt">确定</a>
					<a href="javascript:void(0);" onclick="closeConfigMenu();">取消</a>
				</div>
			</div>
			
			<div id="divAddSysRole" class="easyui-window" minimizable="false"
				maximizable="false" collapsible="false" closed="true" modal="true"
				title="角色信息" style="width: 600px;">
				<input id="ipt_roleId" type="hidden" />
				<table id="tblRoleInfo" cellspacing="10" class="formtable1">
					<tr>
						<td class="title">
							角色名称：
						</td>
						<td>
							<input id="ipt_roleName"
								validator="{'default':'*','length':'1-40'}" />
							<b>*</b>
						</td>
					</tr>
					<tr>
						<td class="title">
							备注：
						</td>
						<td>
							<textarea id="ipt_descr" rows="3"></textarea>
						</td>
					</tr>
				</table>

				<div class="conditionsBtn">
					<a href="javascript:void(0);" id="btnSave" class="fltrt">确定</a>
					<a href="javascript:void(0);" id="btnUpdate" class="fltrt">取消</a>
				</div>
			</div>

			<div id="viewSysRole" class="easyui-window" minimizable="false"
				maximizable="false" collapsible="false" closed="true" modal="true"
				title="角色信息" style="width: 600px;">
				<table id="tblRoleView" cellspacing="10" class="tableinfo1">
					<tr>
						<td>
							<b class="title">角色名称：</b>
							<label id="lbl_roleName"></label>
						</td>
					</tr>
					<tr>
						<td>
							<b class="title">备注：</b>
							<label id="lbl_descr" style="display:inline-block; width:75%; vertical-align: middle;"></label>
						</td>
					</tr>
				</table>
				<div class="conditionsBtn">
					<a href="javascript:void(0);" id="btnClose" class="fltrt">关闭</a>
				</div>
			</div>
		</div>
	</body>
</html>