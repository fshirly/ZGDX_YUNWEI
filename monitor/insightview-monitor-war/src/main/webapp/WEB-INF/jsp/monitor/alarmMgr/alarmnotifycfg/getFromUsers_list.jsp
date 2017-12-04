<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file = "../../../common/pageincluded.jsp" %>
<html>
	<head>
	</head>

	<body>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/monitor/alarmMgr/alarmnotifycfg/getUserlist.js"></script>
		<div id="divSysUserConfig"  >
			<table id="tblSearchUser" class="conditions" >
				<tr>
					<td>
						<b>用户名：</b>
						<input type="text" class="inputs" id="txtFilterUserAccount" />
					</td>
					<td class="btntd">
						<a href="javascript:void(0);" onclick="reloadUserTable();"
							class="fltrt">查询</a>
						<a href="javascript:void(0);"
							onclick="resetFormFilter('tblSearchUser');" class="fltrt">重置</a>
					</td>
				</tr>
			</table>
			
			<div class="conditionsBtn">
				<input type="button" id="btnAdduser" value="确定" onclick="javascript:addUser();"/>
				<input type="button" id="btnCloseUser" value="取消" onclick="javascript:$('#popWin').window('close');"/>
			</div>
			
			<div class="datas">
				<table id="tblSysUser"></table>
			</div>
		</div>
	</body>
</html>
