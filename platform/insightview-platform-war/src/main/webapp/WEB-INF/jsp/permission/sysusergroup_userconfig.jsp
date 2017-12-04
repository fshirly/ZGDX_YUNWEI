<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="../common/pageincluded.jsp"%>
<html>
	<head>
	</head>
	<body>
		<link href="${pageContext.request.contextPath}/plugin/select2/select2.css" rel="stylesheet"/>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/commonPlugins.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/commonUtil.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/plugin/select2/select2.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/permission/sysusergroup_userconfig.js"></script> 
		<%	String groupID =(String)request.getAttribute("groupID");    %>
		<%	String organizationID =(String)request.getAttribute("organizationID");    %>
		<div id="divSysUserConfig" class="winbox" >
		<input id="ipt_groupID" type="hidden" value="<%= groupID%>"/>
		<input id="ipt_organizationID" type="hidden" value="<%= organizationID%>"/>
		<div class="conditions" id="divFilter">
			<table id="tblSearchUser">
				<tr>
					<td>
						<b>用户名</b>
						<input id="iptFilterSysuserAccout" />
					</td>
					<td>
						<b>用户姓名</b>
						<input id="iptFilterSysuserName" />
					</td>
					<td></td>
				</tr>
				<tr>
					<td style="display:none">
						<b>用户类型</b>
						<select id="iptFilterSysuserType" class="easyui-combobox">
						    <option value="-1">单位用户</option>
							<option value="1">供应商用户</option>
						</select>
					</td>
					<td id="provider" style="display:none">
						<b>供应商</b>
						<select id="iptFilterProcider">
                             <option value="-1">全部</option>
                        </select>
					</td>
					<td class="btntd">
						<a href="javascript:void(0);" onclick="reloadUserTable();"
							class="fltrt">查询</a>
						<a href="javascript:void(0);"
							onclick="resetFormFilter('tblSearchUser');" class="fltrt">重置</a>
					</td>
				</tr>
			</table>
		</div>
		
		<div class="datas">
			<table id="tblSysUser"></table>
		</div>
			
		</div>
			<div class="conditionsBtn">
				<a href="javascript:void(0);" id="btnClose" class="fltrt" onclick="javascript:$('#popWin').window('close');">关闭</a>
			</div>
	</body>
</html>