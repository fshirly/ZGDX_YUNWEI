<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file = "../../common/pageincluded.jsp" %>
<html>
	<head>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/js/platform/notifypolicycfg/notifyPolicyCfg_list.js"></script>

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
							<b>规则名称：</b>
							<input type="text" id="txtPolicyName" />
						</td>
						<td class="btntd">
							<a href="javascript:void(0);" onclick="reloadTable();">查询</a>
							<a href="javascript:void(0);" onclick="resetForm('divFilter');">重置</a> 
						</td>
					</tr>
				</table>
			</div>
			<div class="datas">
				<table id="tblNotifyPolicyCfg">
				</table>
			</div>
		</div>
	</body>
</html>
