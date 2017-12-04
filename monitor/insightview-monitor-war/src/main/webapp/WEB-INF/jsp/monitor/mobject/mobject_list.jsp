<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file = "../../common/pageincluded.jsp" %>
<html>
	<head>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/js/monitor/mobject/mobject_list.js"></script>
			<script type="text/javascrict"> 
				<%String flag=(String)request.getAttribute("flag");%>
			</script>
	</head>

	<body>
		<div class="rightContent">
		<input id="flag" type="hidden" value="<%=flag%>"/>
			<div class="location">
				当前位置：运行监测 &gt;&gt;
				<span>管理对象定义</span>
			</div>
			<div class="conditions" id="divFilter">
				<table>
					<tr>
						<td>
							<b>对象类名称：</b>
							<input type="text" id="txtClassName" />
						</td>
						<td class="btntd">
							<a href="javascript:void(0);" onclick="reloadTable();">查询</a>
							<a href="javascript:void(0);" onclick="resetForm('divFilter');">重置</a> 
						</td>
					</tr>
				</table>
			</div>
			<div class="datas">
				<table id="tblMObject">
				</table>
			</div>
		</div>
	</body>
</html>
