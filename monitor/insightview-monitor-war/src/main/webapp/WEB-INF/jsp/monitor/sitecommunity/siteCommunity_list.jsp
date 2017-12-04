<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file = "../../common/pageincluded.jsp" %>
<html>
	<head>
	</head>

	<body>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/monitor/sitecommunity/siteCommunity_list.js"></script>
		<div class="rightContent">
			<div class="location">
				当前位置：${navigationBar}
			</div>
			<div class="conditions" id="divFilter">
				<table>
					<tr>
						<td>
							<b>地址：</b>
							<input type="text" id="txtIPAddress" />
						</td>
						<td>
							<b>站点类型</b>
							<select id="txtSiteType" class="inputs">
								<option value="-1">
									请选择
								</option>
								<option value="1">
									FTP
								</option>
								<option value="2">
									HTTP
								</option><%--
								<option value="3">
									Email
								</option>
							--%></select>
						</td>
						
						<td class="btntd">
							<a href="javascript:void(0);" onclick="reloadTable();">查询</a>
							<a href="javascript:void(0);" onclick="resetForm('divFilter');">重置</a> 
						</td>
					</tr>
				</table>
			</div>
			<div class="datas">
				<table id="tblSiteCommunity">
				</table>
			</div>
		</div>
	</body>
</html>
