<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="../common/pageincluded.jsp"%>

<html>
	<head>
		<!-- mainframe -->
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/plugin/dTree/dtree.js"></script>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/plugin/dTree/combo/comboJS.js"></script>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/js/permission/organization.js"></script>
	</head>
	
	<body>
		<style>
	      .label{
	        width:340px;
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
				<table>
					<tr>
						<td>
							<b>单位名称：</b>
							<input type="text" id="iptFilterOrganizationName" class="input" />
						</td>
						<td>
							<b>上级单位：</b>
							<input type="text" class="input" id="selFilterParentName" />
						</td>
						<td class="btntd">
							<a href="javascript:void(0);" onclick="reloadTable();">查询</a>
							<a href="javascript:void(0);"
								onclick="resetFormPrivateFilter('divFilter');">重置</a>
						</td>
					</tr>
				</table>
			</div>
			
			<!-- begin .datas -->
			<div class="datas tops1">
				<table id="tblOrganization"></table>
			</div>
			
			<div id="divOrganization" class="easyui-window" minimizable="false"
				closed="true" maximizable="false" collapsible="false" modal="true"
				title="单位信息" style="width: 600px;">
				<input id="ipt_organizationID" type="hidden" />
				<table id="tblOrgInfo" cellspacing="10" class="formtable1">
					<tr>
						<td class="title">
							单位名称：
						</td>
						<td>
							<input id="ipt_organizationName" class="input"
								validator="{'default':'*','length':'1-120'}"
								msg="{reg:'请输入1-120个字符！'}" />
							<b>*</b>
							<input id="ipt_nodeID" type="hidden" />
						</td>
					</tr>
					
					<tr>
						<td class="title">
							上级单位：
						</td>
						<td>
							<input id="ipt_parentOrgID" class="input" readonly
								onfocus="doChoseParentOrg();"/>
							<a id="isShow" href="javascript:clear();" style="display: none">清空</a>
						</td>
					</tr>
					
					<tr>
						<td class="title">
							单位编码：
						</td>
						<td>
							<input id="ipt_organizationCode" class="input" validator="{'length':'0-30'}"/>
						</td>
					</tr>
					
					<tr>
						<td class="title">
							描述：
						</td>
						<td>
							<textarea id="ipt_descr" rows="3" cols="20" validator="{'length':'0-120'}"></textarea>
						</td>
					</tr>
				</table>
				
				<div class="conditionsBtn">
					<a href="javascript:void(0);" id="btnSave" class="fltrt">确定</a>
					<a href="javascript:void(0);" id="btnUpdate" class="fltrt">取消</a>
				</div>
			</div>

			<div id="viewOrganization" class="easyui-window" minimizable="false"
				closed="true" maximizable="false" collapsible="false" modal="true"
				title="单位信息" style="width: 600px;">
				<table id="tblOrganizationView" cellspacing="10" class="tableinfo1">
					<tr>
						<td>
							<div style='float:left;'><b class="title">单位名称：</b></div>
							<div class="label"><label id="lbl_organizationName" class="input"></label></div>
						</td>
					</tr>
					
					<tr>
						<td>
							<div style='float:left;'><b class="title">上级单位：</b></div>
							<div class="label"><label id="lbl_parentOrganizationName" class="input" /></div>
						</td>
					</tr>
					
					<tr>
						<td>
							<div style='float:left;'><b class="title">单位编码：</b></div>
							<div class="label"><label id="lbl_organizationCode" class="input" /></div>
						</td>
					</tr>

					<tr>
						<td>
							<div style='float:left;'><b class="title">描述：</b></div>
							<div class="label"><label id="lbl_descr" class="input" /></div>
						</td>
					</tr>
				</table>
				
				<div class="conditionsBtn">
					<a href="javascript:void(0);" id="btnClose" class="fltrt">关闭</a>
				</div>
			</div>
			<!-- end .forShow -->

			<div id="divChoseOrg" class="easyui-window" maximizable="false"
				collapsible="false" minimizable="false" maximized="false"
				closed="true" modal="true" title="选择上级单位"
				style="width: 300px; height: 300px;">
				<div id="dataTreeDivs" class="dtree"
					style="width: 100%; height: 200px;"></div>
			</div>
			<!-- end .datas -->
	</body>
</html>