<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<title>搜索结果</title>

<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/style/css/base.css" />
<link rel="stylesheet" type="text/css"
	href="../plugin/easyui/themes/default/easyui.css" />

<!-- mainframe -->
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/common/jquery-1.9.1.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/plugin/easyui/jquery.easyui.min.js"></script>



</head>
<body>
<script type="text/javascript"
    src="${pageContext.request.contextPath}/plugin/dTree/dtree.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/common/commonUtil.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/common/easyui-lang-zh_CN.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/dashboardPageManage/dashboard_list.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/dashboardPageManage/mainresults.js"></script>

	
	<table class="resultstable">
		<td class="resultstdL">
			<table>
				<tr>
					<td colspan="2"><div id="errorText"></div></td>
				</tr>
				<tr>
					<td colspan="2" class="title">检索关键字：</td>
				</tr>
				<tr>
					<td><input type="text" name="title" id="title" value="${keywords}" /></td>
					<td><input id="searchBtn" type="button" value="检索" /></td>
				</tr>
				<tr>
					<td colspan="2" id="dataTreeDiv"></td>
				</tr>
			</table>
		</td>
		<td class="resultstdR">
			<div id="content" class="easyui-panel " 
				data-options="href:'searchKnowledge?rows=10&page=1&category=${category}&keywords=${keywords}'"></div>
			<div class="easyui-pagination"
				data-options="
	            total: ${count},
	            pageSize: 10,
	            onSelectPage: function(pageNumber, pageSize){
	                $('#content').panel('refresh', 'searchKnowledge?rows=10&page=' + pageNumber + '&category=${category}&keywords=${keywords}');
            }">
			</div>
		</td>
	</table>
</body>
</html>