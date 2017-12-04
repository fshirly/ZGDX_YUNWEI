<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file = "../../common/pageincluded.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<title>管理域详情</title>

</head>
<body>
	<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/platform/managedDomain/managedDomainDetail.js"></script>
	<%
		String domainId=(String)request.getAttribute("domainId");
	 %>
		
		<div id="divShowDomainInfo" >
		<input id="ipt_domainId" type="hidden" value="<%=domainId %>"/>
		<table id="tblDomainInfo" class="tableinfo1">
			<tr>
				<td><b class="title">管理域名称：</b>
				<label id="lbl_domainName" class="input"></label>
				</td>
				
			</tr>
			<tr>
				<td><b class="title">管理域别名：</b>
				<label id="lbl_domainAlias"  class="input"></label>
				</td>
				
			</tr>
			<tr>
				<td><b class="title">上级管理域：</b>
				<label id="lbl_parentDomainName"  class="input"></label>
				</td>
			</tr>
			<tr>
				<td><b class="title">所属组织：</b>
				<label id="lbl_organizationName" class="input"></label>
				</td>
			</tr>
			<tr>
				<td><b class="title">管理域描述：</b>
				<label id="lbl_domainDescr" class="input" style="display:inline-block; width:75%; vertical-align: middle;"></label>
				</td>
				
			</tr>
		</table>
		<div class="conditionsBtn">
			<input class="buttonB" type="button" id="btnClose2" value="关闭" onclick="javascript:$('#popWin').window('close');"/>
		</div>
	</div>
</body>
</html>
