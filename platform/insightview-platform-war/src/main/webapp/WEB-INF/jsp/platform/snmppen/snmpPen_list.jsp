<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file = "../../common/pageincluded.jsp" %>
<html>
<head>
<link href="${pageContext.request.contextPath}/plugin/select2/select2.css" rel="stylesheet"/>
<!-- mainframe -->
<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/commonPlugins.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/plugin/select2/select2.js"></script>	
<script type="text/javascript" src="${pageContext.request.contextPath}/plugin/dTree/dtree.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/plugin/dTree/combo/comboJS.js"></script>	
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/platform/snmppen/snmpPen_list.js"></script>

</head>
<body>
	<div class="rightContent">
		<div class="location">当前位置：${navigationBar}</div>
		<div class="conditions" id="divFilter">
			<table>
				<tr>
					<td>
						<b>企业名称：</b>				
						<input type="text"  id="organizationName" />
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
		
		<div id="divAddEdit" class="easyui-window" 	 title="PEN维护" minimizable="false"  resizable="false" maximizable="false"
		closed="true"  collapsible="false" modal="true" style="width: 800px; ">
			<input id="ipt_id" type="hidden" />
			<table id="tblDetailInfo" class="formtable" >
				<tr>
					<td  class="title">PEN：</td>
					<td><input  id="ipt_pen" name="ipt_pen" maxlength="9" validator="{'length':'1-9'}"  onblur="checkIsUnique()" /><b>*</b></td>
					<td  class="title">企业OID：</td>
					<td><input  id="ipt_enterpriseOID" name="ipt_enterpriseOID" validator="{'length':'1-30'}"  /><b>*</b></td>
				</tr>
				<tr>
					<td  class="title">企业名称：</td>
					<td colspan="3"><input id="ipt_organization" name="ipt_organization"  maxlength="100"  class="x2"/></td>					
				</tr>
				<tr>
					<td  class="title">企业地址：</td>
					<td colspan="3"><input id="ipt_orgAddress" name="ipt_orgAddress" maxlength="200" class="x2"/> </td>
				</tr>
				<tr>
					<td  class="title">邮政编码：</td>
					<td><input id="ipt_postCode" name="ipt_postCode" validator="{'default':'postNum','length':'1-10'}" /><b>*</b>
					 </td>
					<td  class="title">联系电话：</td>
					<td><input id="ipt_contactTelephone" name="ipt_contactTelephone"  validator="{'default':'phoneAndTelNum','length':'1-11'}"/><b>*</b></td>
				</tr>
				<tr>
					<td  class="title">联系人：</td>
					<td><input id="ipt_contactPerson" name="ipt_contactPerson" maxlength="30"/> </td>
					<td  class="title">Email：</td>
					<td><input id="ipt_orgEmail" name="ipt_orgEmail" validator="{'default':'email','length':'1-30'}" /><b>*</b></td>
				</tr>
				<tr>					
					<td  class="title">关联厂商：</td>
					<td colspan="3">
						<select id="resManufacturerID">
       							<option value="-1">请选择...</option>
						</select>
					</td>
				</tr>				
			</table>
			<div class="conditionsBtn">
					<a href="javascript:void(0);" id="btnSave" >确定</a>
					<a href="javascript:void(0);" id="btnUpdate" >取消</a>				
			</div>
		</div>
		
		<!--view -->		
		<div id="divView" class="easyui-window" 	 title="PEN维护详情" collapsible="false" minimizable="false" maximizable="false"
		closed="true"  style="width: 800px; ">
			<table id="tblViewInfo" class="tableinfo" >
				<tr>
					<td >
							<b class="title">PEN：</b>
							<label id="lbl_pen"></label>
					</td>
					<td>
							<b class="title">企业OID：</b>
							<label id="lbl_enterpriseOID"></label>
					</td>					
				</tr>
				<tr>
					<td colspan="2">
							<b class="title">企业名称：</b>
							<label id="lbl_organization" class="alignment"></label>
					</td>
				</tr>
				<tr>
					<td colspan="2">
							<b class="title">企业地址：</b>
							<label id="lbl_orgAddress" class="alignment"></label>
					</td>
				</tr>
				<tr>
					<td >
							<b class="title">邮政编码：</b>
							<label id="lbl_postCode"></label>
					</td>
					<td >
							<b class="title">联系电话：</b>
							<label id="lbl_contactTelephone"></label>
					</td>
				</tr>
				<tr>
					<td >
							<b class="title">联系人：</b>
							<label id="lbl_contactPerson"></label>
					</td>
					<td >
							<b class="title">Email：</b>
							<label id="lbl_orgEmail"></label>
					</td>
				</tr>
				<tr>
					<td >
							<b class="title">关联厂商：</b>
							<label id="lbl_resManufacturerName"></label>
					</td>
					<td >
							<b class="title">&nbsp;</b>
							<label >&nbsp;</label>
					</td>					
				</tr>				
			</table>
			<div class="conditionsBtn">
					<input class="buttonB" type="button" id="btnClose2" value="关闭" onclick="javascript:void(0);"/>		
			</div>
		</div>
	<!--end view  -->		
	</div>
</body>
</html>