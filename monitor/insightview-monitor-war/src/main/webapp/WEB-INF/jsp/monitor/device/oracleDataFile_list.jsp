<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="../../common/pageincluded.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/js/monitor/device/oracleDataFile_list.js"></script>
	<%
		String flag = (String)request.getAttribute("flag");
		String instanceMOID = (String)request.getAttribute("instanceMOID");
	 %>
	</head>

	<body>
		<input type="hidden" id="flag" value="<%=flag %>"/>
		<input type="hidden" id="instanceMOID" value="<%=instanceMOID %>"/>
		<div class="rightContent">
		 	<div class="location">当前位置：${navigationBar}</div>	
			<div class="conditions" id="divFilter">
				<table>
					<tr>
						<td>
							<b>服务IP：</b>
							<input type="text" id="ip" />
						</td>
						<td>
							<b>数据库名称：</b>
							<input type="text" id="dbName" />
						</td>
						<td>
							<b>数据库文件名称：</b>
							<input type="text" id="dataFileName" />
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
			
			<!-- begin .datas -->
			<div class="datas tops2">
						<table id="tblDataFile">

						</table>	
			</div>
		</div>	
	</body>
</html>
