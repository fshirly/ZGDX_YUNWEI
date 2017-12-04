<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="../../common/pageincluded.jsp"%>
<html>
	<head>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/plugin/select2/select2.css" />
   		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/fui/themes/default/fui-tree.min.css" />
   		    
		<script type="text/javascript" src="${pageContext.request.contextPath}/fui/fui.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/plugin/easyui/jquery.easyui.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/commonUtil.js"></script>  
		<script type="text/javascript" src="${pageContext.request.contextPath}/fui/plugin/fui-tree.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/platform/positionManagement/positionManagement.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/easyui-lang-zh_CN.js"></script> 	
	</head>
	<body>
		<div class="location">
			当前位置：${navigationBar}
		</div>
		<div id="dataTreeDiv" class="treetable"></div>
		<div class="treetabler">
		<!-- 页面查询区域 -->
		  <div class="conditions" id="divFilter">
			  <table>
				  <tr>
					<td>
						<b>岗位名称：</b>
						<input type="text" id="positionName_main" class="input" />
					</td>
					<td>
						<b>是否是重要岗位：</b>
						<select id="isImportantPosition" editable="false">
							<option value="">全部</option>
							<option value="1">是</option>
							<option value="2">否</option>			
						</select>
					</td>
					<td class="btntd">
						<a href="javascript:void(0);" onclick="System.PostMgm.reloadTable();">查询</a>
						<a href="javascript:void(0);" onclick="System.PostMgm.resetQueryCondition();">重置</a>
					</td>
					</tr>
				</table>
		   </div>
		   <!-- 此处是生成岗位信息的datagrid -->
		   <div class="datas tops1">
				<table id="positionMessageTable"></table>
			</div>
		</div>

	</body>
</html>