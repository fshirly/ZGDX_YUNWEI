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
	src="${pageContext.request.contextPath}/js/platform/snmpdevice/snmpDevice_list.js"></script>
</head>
<body>
	<div class="rightContent">
		<div class="location">当前位置：${navigationBar}</div>
		<div class="conditions" id="divFilter">
			<table>
				<tr>
					<td>
						<b>设备OID：</b>				
						<input type="text"  id="deviceOID" />
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
		
		<div id="divAddEdit" class="easyui-window" title="设备OID维护" minimizable="false"  resizable="false" maximizable="false"
		closed="true"  collapsible="false" modal="true" style="width: 800px;">
			<input id="ipt_id" type="hidden" />
			<table id="tblDetailInfo" class="formtable">
				<tr>
					<td  class="title">PEN：</td>
					<td>
						<input type="text" id="ipt_pen" name="ipt_pen" onclick="toAddPenlog()" readonly="readonly" validator="{'default':'num','length':'1-11'}" /><b>*</b>
					</td>
					<td  class="title">设备OID：</td>
					<td><input id="ipt_deviceOID" name="ipt_deviceOID" validator="{'length':'1-64'}" maxlength="64"/> <b>*</b></td>
				</tr>
				<tr>
					<td  class="title">设备型号：</td>
					<td><input id="ipt_deviceModelName" name="ipt_deviceModelName" maxlength="64"/></td>
					<td  class="title">设备类型：</td>
					<td><input id="ipt_deviceType" name="ipt_deviceType" maxlength="20"/> </td>
				</tr>
				<tr>					
					<td  class="title">操作系统：</td>
					<td><input id="ipt_os" name="ipt_os" validator="{'length':'0-50'}"/> </td>
					<td  class="title">设备图标：</td>
					<td><input id="ipt_deviceIcon" name="ipt_deviceIcon" /> </td>
				</tr>
				<tr>					
					<td  class="title">对应资源类型：</td>
					<td colspan="3">
						<input id="ipt_resCategoryID" name="ipt_resCategoryID" type="hidden" readonly="readonly"/>
						<input type="text" id="ipt_resCategoryName" name="ipt_resCategoryName" readonly="readonly" onclick="toAddCategorylog()"/>
					</td>					
				</tr>
				<tr>
					<td  class="title">设备备注：</td>
					<td colspan="3">
						<textarea rows="3" id="ipt_deviceNameAbbr" name="ipt_deviceNameAbbr" class="x2" 
								validator="{'length':'0-64'}" maxlength="64">
						</textarea> </td>					
				</tr>				
			</table>
			<div class="conditionsBtn">
					<a href="javascript:void(0);" id="btnSave" >确定</a>
					<a href="javascript:void(0);" id="btnUpdate" >取消</a>					
			</div>			
		</div>		
		<!--view -->		
		<div id="divView" class="easyui-window" title="设备OID维护详情" collapsible="false" minimizable="false" maximizable="false"
		closed="true"  style="width: 800px; ">
			<table id="tblViewInfo" class="tableinfo" >
				<tr>
					<td >
							<b class="title">PEN：</b>
							<label id="lbl_pen"></label>
					</td>
					<td>
							<b class="title">设备OID：</b>
							<label id="lbl_deviceOID"></label>
					</td>
				</tr>
				<tr>
					<td >
							<b class="title">设备型号：</b>
							<label id="lbl_deviceModelName"></label>
					</td>
					<td>
							<b class="title">设备类型：</b>
							<label id="lbl_deviceType"></label>
					</td>
				</tr>
				<tr>
					<td >
							<b class="title">操作系统：</b>
							<label id="lbl_os"></label>
					</td>
					<td>
							<b class="title">设备图标：</b>
							<label id="lbl_deviceIcon"></label>
					</td>
				</tr>
				<tr>					
					<td colspan="2">
							<b class="title">对应资源类型：</b>
							<label id="lbl_resCategoryName"></label>
					</td>
				</tr>
				<tr>
					<td colspan="2">
							<b class="title">设备备注：</b>
							<label id="lbl_deviceNameAbbr" class="alignment"></label>
					</td>				
				</tr>				
			</table>
			<div class="conditionsBtn">
					<input class="buttonB" type="button" id="btnClose2" value="关闭" onclick="javascript:void(0);"/>		
			</div>
		</div>
	</div>
	
	<!--PEN -->	
	<div id="divPenDialog" title="PEN维护信息">
		<!--PEN查询条件-->
		<div class="winbox">
			<div class="conditions" id="divPenFilter">
				<table>
					<tr>
						<td>
							<b>企业名称：</b>				
								<input type="text"  id="organizationName" />
						</td>
						<td class="btntd">
							<a href="javascript:void(0);" onclick="reloadPenTable();">查询</a>
							<a href="javascript:void(0);" onclick="resetForm('divPenFilter');">重置</a>
						</td>
					</tr>
				</table>
			</div>
			<!--PEN信息表 -->
			<div class="datas">
				<table id="tblPenList"></table>
			</div>
			<div class="conditionsBtn">
		      	<a onclick="doAddPenlog();">确认</a>
		      	<a onclick="doPenCancel();">取消</a>
		    </div>
		</div>
	</div>
	
	<div id="divCategoryDialog" title="对应资源信息">
		<!--对应资源查询条件-->
		<div class="winbox">
			<div class="conditions" id="divCategoryFilter">
				<table>
					<tr>
						<td>
							<b>目录名称：</b>				
								<input type="text"  id="resCategoryName" />
						</td>
						<td class="btntd">
							<a href="javascript:void(0);" onclick="reloadCategoryTable();">查询</a>
							<a href="javascript:void(0);" onclick="resetForm('divCategoryFilter');">重置</a>
						</td>
					</tr>
				</table>
			</div>
			<!--对应资源信息表 -->
			<div class="datas">
				<table id="tblCategoryList"></table>
			</div>
			<div class="conditionsBtn">
		      	<a onclick="doAddCategorylog();">确认</a>
		      	<a onclick="doCategoryCancel();">取消</a>
		    </div>
		</div>
	</div>
				
</body>
</html>