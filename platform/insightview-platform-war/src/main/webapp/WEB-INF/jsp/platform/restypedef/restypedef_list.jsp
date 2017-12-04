<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file = "../../common/pageincluded.jsp" %>
<html>
<head>
<!-- mainframe -->
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/main.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/plugin/dTree/dtree.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/plugin/dTree/combo/comboJS.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/platform/restypedef/restypedef_list.js"></script>
</head>
<body>
	<div class="rightContent">
		<div class="location">当前位置：${navigationBar }</div>
		<div class="conditions" id="divFilter">
			<table>
				<tr>
					<td>
						<b>类型名称：</b>				
						<input type="text" id="resTypeName" />
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
		
		<div id="divAddEdit" class="easyui-window" title="产品类型" minimizable="false"  resizable="false" maximizable="false"
		closed="true"  collapsible="false" modal="true" style="width: 800px;">
			<input id="ipt_resTypeID" type="hidden" />
			<table id="tblDetailInfo" class="formtable">
				<tr>
					<td  class="title">类型名称：</td>
					<td><input id="ipt_resTypeName" name="ipt_resTypeName"  validator="{'length':'1-30'}" /> <b>*</b></td>
					<td  class="title">类型别名：</td>
					<td><input id="ipt_resTypeAlias" name="ipt_resTypeAlias"  /></td>
				</tr>
				<tr>
					<td  class="title">图标：</td>
					<td><input id="ipt_icon" name="ipt_icon" /> </td>
					<td  class="title">父类名称：</td>
					<td><input type="text" id="ipt_parentTypeId" name="ipt_parentTypeId" onfocus="doChoseParentOrg();" alt="-1" readonly="readonly"/>						
					</td>
				</tr>
				<tr>
					<td  class="title">类型描述：</td>
					<td colspan="3"><textarea rows="3" id="ipt_resTypeDescr" name="ipt_resTypeDescr" class="x2"></textarea></td>
				</tr>
			</table>
			<div class="conditionsBtn">
					<a href="javascript:void(0);" id="btnSave" >确定</a>
					<a href="javascript:void(0);" id="btnUpdate" >取消</a>					
			</div>
		</div>
		<!--view -->		
		<div id="divView" class="easyui-window" title="产品类型详情" collapsible="false" minimizable="false" maximizable="false"
		closed="true"  style="width: 800px; ">
			<table id="tblViewInfo" class="tableinfo" >
				<tr>
					<td >
							<b class="title">类型名称：</b>
							<label id="lbl_resTypeName"></label>
					</td>
					<td>
							<b class="title">类型别名：</b>
							<label id="lbl_resTypeAlias"></label>
					</td>	
				</tr>
				<tr>
					<td >
							<b class="title">图标：</b>
							<label id="lbl_icon"></label>
					</td>
					<td>
							<b class="title">父类名称：</b>
							<label id="lbl_parentTypeName"></label>
					</td>
				</tr>
				<tr>
					<td colspan="2">
							<b class="title">类型描述：</b>
							<label id="lbl_resTypeDescr"></label>
					</td>
				</tr>			
			</table>
			<div class="conditionsBtn">
					<input class="buttonB" type="button" id="btnClose2" value="关闭" onclick="javascrlbl:void(0);"/>		
			</div>
		</div>
	<!--end view  -->		
		
	<!-- 树 -->
	<div id="divChoseOrg" class="easyui-window" minimizable="false"
		closed="true" modal="true" title="选择父类名称"
		style="width: 300px; height: 300px;">
		<div id="dataTreeDivs" class="dtree"
			style="width: 100%; height: 200px;">
		</div>
	</div>
		
	</div>
</body>
</html>