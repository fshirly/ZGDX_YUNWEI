<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file = "../../common/pageincluded.jsp" %>
<html>
	<head>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/monitor/component/reader_list.js"></script>
	</head>	
	<body>  
	<input type="hidden" id="flag" value="${flag}"/>
	<input type="hidden" id="parentID" value="${parentID}"/>
	<div class="rightContent">
			<div class="conditions" id="divFilter">
				<table>
					<tr>
						<td>
							<b>阅读器IP：</b>
							<input type="text" id="iPAddress" />
						</td>
						<td>
							<b>阅读器名称：</b>
							<input type="text" id="readerName" />
						</td>
						<td class="btntd">
							<a href="javascript:void(0);" onclick="reloadTable();">查询</a>
							<a href="javascript:void(0);" onclick="resetForm('divFilter');">重置</a>
						</td>
					</tr>
				</table>
			</div>
			
			<div class="datas">
				<table id="tblRoomList">
				</table>
			</div>
		</div>	
	</body>
</html>