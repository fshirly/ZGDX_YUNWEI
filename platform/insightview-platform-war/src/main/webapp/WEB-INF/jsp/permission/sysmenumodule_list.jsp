<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="../common/pageincluded.jsp"%>
<html>
	<head>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/plugin/dTree/dtree.js"></script>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/plugin/dTree/combo/comboJS.js"></script>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/js/permission/sysmenumodule.js"></script>
	</head>
	<body>

		<div class="location">
			当前位置：${navigationBar}
		</div>
		<div id="dataTreeDiv" class="treetable"></div>
		<div class="treetabler">
			<div class="conditions" id="divFilter">
				<table>
					<tr>
						<td>
							<b>菜单名称：</b>
							<input type="text" id="txtFilterMenuName" />
							<input id="selFilterParentId" type="hidden" />
						</td>
						<!--<td>
							<b>菜单级别：</b>
							<select id="selFilterMenuLevel">
								<option value="">
									-------请选择-------
								</option>
								<option value="0">
									一级菜单
								</option>
								<option value="1">
									二级菜单
								</option>
								<option value="2">
									三级菜单
								</option>
								<option value="3">
									四级菜单
								</option>
							</select>
						</td>
						<td>
							<b>上级菜单：</b>
							<input id="selFilterParentId"
								onClick='showTree(this,"addressFatherId")' readonly="readonly" />
						</td>
						-->
						<td class="btntd">
							<a href="javascript:void(0);" onclick="reloadTable();">查询</a>
							<a href="javascript:void(0);"
								onclick="resetFormPrivate('divFilter');">重置</a>
						</td>
					</tr>
				</table>
			</div>
			<!-- begin .datas -->
			<div class="datas">
				<table id="tblSysMenuodule">

				</table>
			</div>
		</div>

		<div id="combdtree" class="dtreecob">
			<div id="dataTreeDiv" class="dtree"
				style="width: 100%; height: 200px; overflow: auto;"></div>
			<div class="dBottom">
				<a href="javascript:hiddenDTree();">关闭</a>
		</div>
		</div>
		<div id="divChoseMenu" class="easyui-window" minimizable="false"
			closed="true" modal="true" title="选择上级菜单"
			style="width: 300px; height: 300px;">
			<div id="dataTreeDivs" class="dtree"
				style="width: 100%; height: 200px;"></div>
		</div>

		<!-- end .datas -->
		<div id="divAddMenu" class="easyui-window" collapsible="false"
			minimizable="false" maximizable="false" closed="true" title="功能配置"
			style="width: 800px;">
			<input id="ipt_menuId" type="hidden" />
			<table id="tblMenuInfo" class="formtable">
				<tr>
					<td class="title">
						菜单名称：
					</td>
					<td>
						<input id="ipt_menuName" validator="{'length':'2-50'}"
							msg="{reg:'2-50位字符！'}" />
						<b>*</b>
					</td>
					<td class="title">
						上级菜单：
					</td>
					<td>
						<input id="ipt_parentMenuID" onfocus="doChoseParentMenu();"
							readonly="readonly" />
						<input type="hidden" id="ipt_parID" />
					</td>
				</tr>
				<tr>
					<td class="title">
						菜单级别：
					</td>
					<td>
						<select id="ipt_menuLevel" class="inputs">
							<option value="1">
								一级菜单
							</option>
							<option value="2">
								二级菜单
							</option>
							<option value="3">
								三级菜单
							</option>
							<option value="4">
								四级菜单
							</option>
						</select>
						<b>*</b>
					</td>
					<td class="title">
						菜单排序：
					</td>
					<td>
						<input id="ipt_showOrder" validator="{'length':'1-11'}"
							msg="{reg:'1-999之间的数字！'}"
							onKeyUp="ipt_showOrder.value=(this.value=this.value.replace(/\D/g,'').substring(0,600)).substring(0,999)" />
						<b>*</b>
						<input id="ipt_icon" type="hidden" />
					</td>

					<!-- 
					<td>上传图标：</td>
					<td><input id="ipt_icon" /></td> -->
				</tr>
				<tr>
					<td class="title">
						菜单类型：
					</td>
					<td>
						<select id="ipt_menuClass" class="inputs">
							<option value="1">
								常规菜单
							</option>
						</select>
						<b>*</b>
					</td>
				</tr>
				<tr>
					<td class="title">
						链接地址：
					</td>
					<td colspan="3">
						<textarea id="ipt_linkURL" rows="3" class="x2" validator="{'reg':'/^(.){1,200}$/'}" msg="{'reg':'只能输入1-200位字符！'}">
						
 							</textarea>
					</td>
				</tr>
				<tr>

					<td class="title">
						备注：
					</td>
					<td colspan="3">
						<textarea id="ipt_descr" rows="3" class="x2" validator="{'reg':'/^(.){1,100}$/'}" msg="{'reg':'只能输入1-100位字符！'}"></textarea>
					</td>
				</tr>
				<tr>

				</tr>
			</table>
			<div class="conditionsBtn">
				<a href="javascript:void(0);" id="btnSave">确定</a>
				<a href="javascript:void(0);" id="btnUpdate">取消</a>
			</div>
		</div>
			
			<div id="divModelInfo" class="easyui-window" collapsible="false"
				minimizable="false" maximizable="false" closed="true" title="功能配置详情"
				style="width: 800px;">
				<input id="ipt_menuId" type="hidden" />
				<table id="tblMenuInfo" class="tableinfo">
					<tr>
						<td>
							<b class="title"> 菜单名称： </b>
							<label id="lbl_menuName" class="input"></label>
						</td>
						<td>
							<b class="title"> 上级菜单： </b>
							<label id="lbl_parentMenuID" class="input"></label>
						</td>
					</tr>
					<tr>
						<td>
							<b class="title"> 菜单级别： </b>
							<label id="lbl_menuLevel" class="input"></label>
						</td>
						<td>
							<b class="title"> 菜单排序： </b>
							<label id="lbl_showOrder" class="input"></label>
						</td>
					</tr>
					<tr>
						<td>
							<b class="title"> 菜单类型： </b>
							<label id="lbl_menuClass" class="input"></label>
						</td>
						<!-- 
					<td>上传图标：</td>
					<td><input id="ipt_icon" /></td> -->
					</tr>
					<tr>
						<td>
							<b class="title"> 链接地址： </b>
							<label id="lbl_linkURL" class="input" style="display:inline-block; width:60%; vertical-align: middle;"></label>
						</td>
						<td>
							<b class="title"> 备注： </b>
							<label id="lbl_descr" class="input" style="display:inline-block; width:60%; vertical-align: middle;"></label>
						</td>
					</tr>
				</table>
				<div class="conditionsBtn">
					<input class="buttonB" type="button" id="btnClose2" value="关闭"
						onclick="javascript:void(0);" />
				</div>
			</div>
	</body>
</html>