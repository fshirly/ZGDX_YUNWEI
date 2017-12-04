<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>配置接收人员</title>
</head>
<body>
	<style type="text/css">
.left_mid_right {
	width: 100%;
	height: 80%;
	text-align: center;
	margin: 10px 0 0 0;
	border: 1px solid #d8e5eb
}

.selete_st {
	height: 260px;
	width: 200px !important;
}

.left_mid_right button {
	width: 50px;
}
</style>
	<link rel="stylesheet" type="text/css"
		href="${pageContext.request.contextPath}/fui/themes/default/fui-tree.min.css" />
	<input type="hidden" value="${orgId }" id="current_orgId" />
	<div class="conditions">
		<table>
			<tr>
				<td><b>姓名：</b> <input type="text" name="userName" /></td>
				<td><b>部门： </b> <input type="text" id="user_department"
					name="department" readonly="readonly" />&nbsp;&nbsp;</td>
				<td class="btntd"><a onclick="User.configerUser.search();">查询</a>
					<a onclick="User.configerUser.reset();">重置</a></td>
			</tr>
		</table>
	</div>
	<table class="left_mid_right">
		<tr>
			<td>
				<ul>
					<li><b>待选人员：</b></li>
					<li><select id="selLeft" multiple="multiple" class="selete_st">
							<c:forEach items="${users }" var="user">
								<option value="${user.id }">${user.name }</option>
							</c:forEach>
					</select></li>
				</ul>
			</td>
			<td>
				<button id="img_L_AllTo_M" type="button">>>></button> <br />
				<button id="img_L_To_M" type="button">></button> <br />
				<button id="img_M_To_L" type="button"><</button> <br />
				<button id="img_M_AllTo_L" type="button"><<<</button>
			</td>
			<td>
				<ul>
					<li><b>已选人员：</b></li>
					<li><select id="selmid" multiple="multiple" class="selete_st">
							<c:forEach items="${userSl }" var="m">
								<option value="${m.key }">${m.value }</option>
							</c:forEach>
					</select></li>
				</ul>
			</td>
		</tr>
	</table>
	<div class="conditionsBtn">
		<a onclick="User.configerUser.confirm();">确定</a> <a
			onclick="$('#configer').dialog('close');">关闭</a>
	</div>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/fui/plugin/fui-tree.min.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/permission/LRSelect.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/notices/configerUser.js"></script>
</body>
</html>