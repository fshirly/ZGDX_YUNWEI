<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<title>终端违规事件列表</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/css/base.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/css/reset.css" />
<!-- mainframe -->
<script type="text/javascript" src="${pageContext.request.contextPath}/fui/fui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/commonUtil.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/base64.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/plugin/easyui/jquery.easyui.min.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/fui/themes/default/fui-tree.min.css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/fui/plugin/fui-tree.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/monitor/TerminalIllegalEvent/ProxyAccessList.js"></script>
<link href="${pageContext.request.contextPath}/plugin/select2/select2.css" rel="stylesheet" />
<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<div class="rightContent">
		<div class="location">
			当前位置：${navigationBar }
		</div>
		<div class="conditions" id="divFilter">
				<table>
					<tr>
						<td><b>用户名称：</b><input id="userName" name="userName"/></td>
						<td><b>身份证号：</b><input id="identity" name="identity"/></td>
						
					</tr>
					<tr>
						<td><b>单位：</b><input id="orgname" name="orgname"></td>
						<td><b>发生时间：</b><input  id="firstTime" name="firstTime"/> 
						    - <input  id="lastTime" name="lastTime"/></td>
					    <td class="btntd">
							<a href="javascript:void(0);" id="queryBtn">查询</a>
							<a href="javascript:void(0);" id="resetBtn">重置</a>
						</td>
					</tr>
				</table>
		</div>
		<!-- 查询列表 -->
		<div class="datas tops2">
			<table id="tblProxyAccessList">
			</table>
		</div>
		<!-- end .datas -->
	</div>
</body>
</html>