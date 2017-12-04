<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>选择人员</title>
</head>
<body>
<style type="text/css">
.left_mid_right {width:100%;height: 80%;text-align: center; margin: 10px 0 0 0; border:1px solid #d8e5eb}
.selete_st {height: 200px; width: 130px !important;}
.left_mid_right button {width:38px;}
.parent_input_div{width : 135px !important;}
.combotree{width : 135px !important;}
</style>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/fui/themes/default/fui-tree.min.css" />
	<input type="hidden" value="${orgId }" id="current_orgId"/>
	<input type="hidden" value="${userType }" id="userType"/>
	<div class="conditions">
		<table>
			<tr>
				<td>
					<b>姓名：</b> 
					<input type="text" name="userName" />
				</td>
				<td>
					<b>部门： </b>
					<input type="text" id="user_department" name="department" readonly="readonly"/>&nbsp;&nbsp;
				</td>
				<td class="btntd">
					<a onclick="dutymanager.configerUser.search();">查询</a>
					<a onclick="dutymanager.configerUser.reset();">重置</a>
				</td>
			</tr>
		</table>
	</div>
	<table class="left_mid_right">
		<tr>
			<td>
				<ul>
					<li><b>待选人员：</b></li>
					<li>
					<select id="selLeft" multiple="multiple" class="selete_st">
						<c:forEach items="${users }" var="user">
							<option value="${user.id }">${user.name }</option>
						</c:forEach>
					</select>
					</li>
				</ul>
			</td>
			<td>
				<button id="img_L_AllTo_M" type="button">>>></button><br/>
				<button id="img_L_To_M" type="button">></button><br/>
				<button id="img_M_To_L" type="button"><</button><br/>
				<button id="img_M_AllTo_L" type="button"><<<</button>
			</td>
			<td>
				<ul>
					<li><b>已选人员：</b></li>
					<li><select id="selmid" multiple="multiple" class="selete_st">
						<c:forEach items="${dutyers }" var="user">
							<c:forEach items="${user }" var="m">
								<option value="${m.key }">${m.value }</option>
							</c:forEach>
						</c:forEach>
						</select>
					</li>
				</ul>
			</td>
			<c:if test="${userType ne 1}">
				<td>
					<button id="img_M_To_R" type="button">></button><br/>
					<button id="img_R_To_M" type="button"><</button><br/>
				</td>
				<td>
					<ul>
						<li><b>负责人(必填且1人)：</b></li>
						<li><select id="selRight" multiple="multiple" class="selete_st">
							<c:if test="${Pincicla ne null }">
								<c:forEach items="${Pincicla }" var="m">
									<option value="${m.key }">${m.value }</option>
								</c:forEach>
							</c:if>
							</select>
						</li>
					</ul>
				</td>
			</c:if>
		</tr>
	</table>
	<div class="conditionsBtn">
		<a onclick="dutymanager.configerUser.confirm();">确定</a>
		<a onclick="f('#configer').window('close');">取消</a>
	</div>
	<script type="text/javascript" src="${pageContext.request.contextPath}/fui/plugin/fui-tree.min.js"></script>
	<script type="text/javascript"	src="${pageContext.request.contextPath}/js/permission/LRSelect.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/dutymanager/configerUser.js"></script>
</body>
</html>