<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/style/css/base.css" />

<!-- mainframe -->
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/common/jquery-1.9.1.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/common/commonUtil.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/common/base64.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/plugin/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/common/easyui-lang-zh_CN.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/main.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/plugin/dTree/dtree.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/plugin/dTree/combo/comboJS.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/permission/employmentgrade.js"></script>
</head>
<body>
	<div class="rightContent">
		<div class="location">
			<!-- begin .locationAll -->
			<div class="locationAll">
				<img
					src="${pageContext.request.contextPath}/style/images/images1000/icon_position.gif"
					align="middle" /> <span id="locationPro">当前位置：权限管理 &gt;
					单位职位管理</span>
			</div>
		</div>
		<div class="conditions">
			<table>
				<tr>
					<td>用户名：<input type="text" class="inputs"
						id="organizationName" /></td>
					<td>姓名：<input type="text" class="inputs" id="organizationName" /></td>
					<td>是否自动锁定：<select><option>-------请选择-------</option></select></td>
				</tr>
				<tr>
					<td>用户类型：<select><option>-------请选择-------</option></select></td>
					<td>所属单位：<select><option>-------请选择-------</option></select></td>
					<td>职位级别：<select><option>-------请选择-------</option></select></td>
				</tr>
			</table>
		</div>
		<!-- begin .datas -->
		<div class="datas">
			<!-- begin .buttons -->
			<div class="buttons">
				<ul>
					<li class="title">用户列表</li>

				</ul>
			</div>
			<!-- end .buttons -->
			<!-- begin .forShow -->
			<div class="forShow">
				<table id="tblEmploymentGrade">

				</table>
				<!-- end .pageToolBar-->
			</div>

		</div>
		<!-- end .datas -->
	</div>
</body>
</html>