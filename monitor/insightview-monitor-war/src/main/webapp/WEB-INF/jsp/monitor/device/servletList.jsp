<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="../../common/pageincluded.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/js/monitor/device/servletList.js"></script>
	<%
		String flag = (String)request.getAttribute("flag");
		String jmxType = (String)request.getAttribute("jmxType");
		String parentMoId = (String)request.getAttribute("parentMoId");
	 %>
	</head>

	<body>
		<div class="rightContent">
		 	<div class="location" id="websphereTitle">当前位置：${navigationBar}</div>
			<div class="conditions" id="divFilter">
			<input type="hidden" id="jmxType" value="<%=jmxType %>"/>
			<input type="hidden" id="flag" value="<%=flag %>"/>
			<input type="hidden" id="parentMoId" value="<%=parentMoId %>"/>
				<table>
					<tr>
						<td>
							<b>应用名称：</b>
							<input type="text" id="txtParentMOName" />
						</td>
						<td>
							<b>所属WAR包名称：</b>
							<input type="text" id="txtWarName" />
						</td>
						<td>
							<b>服务IP：</b>
							<input type="text" id="txtIP" />
						</td>
					</tr>
					<tr>
						<td>
							&nbsp;
						</td>
						<td>
							&nbsp;
						</td>
						<td class="btntd">
							<a href="javascript:void(0);" onclick="reloadTable();">查询</a>
							<a href="javascript:void(0);" onclick="resetForm('divFilter');">重置</a>
						</td>
					</tr>
				</table>
			</div>
			
			<div class="datas tops2">
				<table id="tblServlet">
				</table>
			</div>
		</div>	
	</body>
</html>
