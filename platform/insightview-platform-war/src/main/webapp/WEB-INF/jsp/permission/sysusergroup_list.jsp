<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="../common/pageincluded.jsp"%>
<html>
	<head>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/plugin/dTree/dtree.js"></script>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/plugin/dTree/combo/comboJS.js"></script>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/js/permission/sysusergroup.js"></script>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/js/permission/LRSelect.js"></script>
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
							<b>用户组名称：</b>
							<input type="text" id="txtGroupName" />
						</td>
						<td>
							<b>所属单位：</b>
							<input id="selOrganizationName" type="text" />

						</td>
						<td class="btntd">
							<a href="javascript:void(0);" onclick="reloadTable();">查询</a>
							<a href="javascript:void(0);"
								onclick="resetFormFilter('divFilter');">重置</a>
						</td>
					</tr>
				</table>
			</div>
			
			<!-- begin .datas -->
			<div class="datas">
				<table id="tblSysUserGroup">
				</table>
			</div>
			<!-- end .datas -->
			
			<div id="divAddUserGroup" class="easyui-window" minimizable="false"
				maximizable="false" collapsible="false" closed="true" modal="true"
				title="用户组信息" style="width: 600px;">
				<script>
				$(document).ready(function() {
				  $(".window-mask").css("height","100%");
				  $(".window-mask").css("width","100%");
				  $(".panel.window").css("top","100px");
				})
				</script>
				<input id="ipt_groupID" type="hidden" />
				<table cellspacing="10" id="tblAddUserGroup" class="formtable1">
					<tr>
						<td class="title">
							用户组名称：
						</td>
						<td>
							<input id="ipt_groupName" validator="{'default':'*','length':'1-20'}"
								msg="{reg:'只能输入1-20位字符！'}" />
							<b>*</b>
						</td>

					</tr>
					<tr>
						<td class="title">
							用户组所属单位：
						</td>
						<td>
							<input id="ipt_organizationID"
								onfocus="doChoseParentOrg();" />
							
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

			<div id="divAllotRole" class="easyui-window" minimizable="false"
				maximizable="false" collapsible="false" closed="true" modal="true"
				title="分配角色" style="width: 600px;">
				<table class="formtable1">
					<tr>
						<td>
							角色名称：
						</td>
						<td></td>
						<td>
							已有角色：
						</td>
					</tr>
					<tr>
						<td style='vertical-align: bottom;'>
							<select id="selLeft" multiple="multiple"
								style="width: 205px; height: 330px" class="dataSelect">
							</select>
						</td>
						<td style="width: 30px; text-align: center;">
							<button id="img_L_AllTo_R">
								>>>
							</button>
							<button id="img_L_To_R">
								&nbsp;&nbsp;>&nbsp;&nbsp;
							</button>
							<br />
							<button id="img_R_To_L">
								&nbsp;&nbsp;<&nbsp;&nbsp;
							</button>
							<br />
							<button id="img_R_AllTo_L">
								<<<
							</button>
							<br />
						</td>
						<td style="vertical-align: bottom;">
							<select id="selRight" multiple="multiple"
								style="width: 205px; height: 330px" class="dataSelect">
							</select>
						</td>
					</tr>
				</table>
				<div class="conditionsBtn">
					<a href="javascript:void(0);" onclick="resetGroupRole();"
						class="fltrt">确定</a>
					<a href="javascript:void(0);" onclick="closeAllotRole();">取消</a>
				</div>
			</div>

			<div style="top:132px;">	
				<div id="viewUserGroup" class="easyui-window" minimizable="false"
					closed="true" maximizable="false" collapsible="false" modal="true"
					title="用户组已有的用户" style="width: 600px;">
					<div class="datas">
						<table id="viewSysUser"></table>
					</div>
					
					<div class="conditionsBtn">
						<a href="javascript:void(0);" id="btnClose" class="fltrt">关闭</a>
					</div>
				</div>
			</div>
			<div id="divChoseOrg" class="easyui-window" minimizable="false"
				maximizable="false" collapsible="false" closed="true" modal="true"
				title="选择上级单位" style="width: 300px; height: 300px;">
				<div id="dataTreeDivs" class="dtree"
					style="width: 100%; height: 200px;"></div>
			</div>
			<div id="divMenuConfig" class="easyui-window" minimizable="false"
				maximizable="false" collapsible="false" closed="true" modal="true"
				title="配置菜单" style="width: 600px; height: 450px;">
				<div id="dataMenuTreeDiv" class="dtree"
					style="width: 100%; height: 200px;"></div>
				<div id="dataMenuTreeDivs" class="dtree"
					style="width: 100%; height: 200px;"></div>
			</div>
			
			<div id="divSysUserConfig" class="easyui-window" minimizable="false"
				maximizable="false" collapsible="false" closed="true" modal="true"
				title="配置用户" style="width: 800px; ">
				<table id="tblSearchUser"	 class="conditions" >
					<tr>
						<td>
							<b>用户名</b>
							<input id="iptFilterSysuserAccout" />
						</td>
						<td>
							<b>用户姓名</b>
							<input id="iptFilterSysuserName" />
						</td>
						<td class="btntd">
							<a href="javascript:void(0);" onclick="reloadUserTable();"
								class="fltrt">查询</a>
							<a href="javascript:void(0);"
								onclick="resetFormFilter('tblSearchUser');" class="fltrt">重置</a>
						</td>
					</tr>
				</table>
				<div class="datas">
					<table id="tblSysUser"></table>
				</div>
				
				<div class="conditionsBtn">
					<a href="javascript:void(0);" id="btnClose" class="fltrt" onclick="closeUserConfig()">关闭</a>
				</div>
			</div>
		</div>
	</body>
</html>