<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file = "../../common/pageincluded.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<title>管理域详情</title>

</head>
<body>
	<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/platform/managedDomain/managedDomainModify.js"></script>
	<%
		String domainId=(String)request.getAttribute("domainId");
	 %>
	
			<!-- end .datas -->
	<div id="divManagedDomainInfo" >
		<input id="flag" type="hidden" value="add"/>
		<input id="ipt_domainId" type="hidden" value="<%=domainId %>"/>
		<table id="tblEditDomain" class="formtable1">
			<tr>
				<td class="title">管理域名称：</td>
				<td><input id="ipt_domainName"  class="input" validator="{'default':'*','length':'1-128'}" onblur="checkDomainName();"/><b>*</b></td>
				
			</tr>
			<tr>
				<td class="title">管理域别名：</td>
				<td><input id="ipt_domainAlias"  class="input" /></td>
				
			</tr>
			<tr>
				<td class="title">上级管理域：</td>
				<td><input id="ipt_parentDomainName" readonly onfocus="doChoseParentDomain();"/> <input id="ipt_parentId" type="hidden" />
					<a id="isShow" href="javascript:clear();" style="display: none">清空</a>
					</td>
			</tr>
			<tr>
				<td class="title">所属组织：</td>
				<td> <input id="ipt_organizationName" readonly class="input" onfocus="doChoseParentOrg();" validator="{'default':'*'}"/><b>*</b> <input id="ipt_organizationId" type="hidden"  />
					</td>
			</tr>
			<tr>
				<td class="title">管理域描述：</td>
				<td><textarea id="ipt_domainDescr" rows="3" class="input" ></textarea></td>
				
			</tr>
		</table>
		<div class="conditionsBtn">
			<input class="buttonB" type="button" id="btnSave" value="确定" onclick="javascript:doUpdateDomain();"/>
			<input class="buttonB" type="button" id="btnClose" value="取消" onclick="javascript:$('#popWin').window('close');"/>
		</div>
	</div>
		<div id="divChoseOrg" class="easyui-window" collapsible="false" minimizable="false" maximizable="false"
			closed="true" modal="true" title="选择所属单位"
			style="width: 300px; height: 300px;">
			<div id="dataTreeDivs" class="dtree"
				style="width: 100%; height: 200px;"></div>
		</div>
		
	<div id="divChoseDomain" class="easyui-window" collapsible="false" minimizable="false" maximizable="false"
			closed="true" modal="true" title="选择上级管理域"
			style="width: 300px; height: 300px;">
			<div id="dataTreeDomains" class="dtree"
				style="width: 100%; height: 200px;"></div>
	</div>
</body>
</html>
