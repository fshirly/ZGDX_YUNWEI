<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="../../common/pageincluded.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/js/monitor/device/jdbcDSList.js"></script>
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
							<b>数据源名称：</b>
							<input type="text" id="txtDSName" />
						</td>
						<td>
							<b>服务IP：</b>
							<input type="text" id="txtip" />
						</td>
						<td class="btntd">
							<a href="javascript:void(0);" onclick="reloadTable();">查询</a>
							<a href="javascript:void(0);" onclick="resetForm('divFilter');">重置</a>
						</td>
					</tr>
				</table>
			</div>
			
			<div class="datas">
				<table id="tblJdbcDS">
				</table>
			</div>
		</div>	
	</body>
</html>
