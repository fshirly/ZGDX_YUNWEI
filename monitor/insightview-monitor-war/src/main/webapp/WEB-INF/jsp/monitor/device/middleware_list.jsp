<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file = "../../common/pageincluded.jsp" %>
<html>
	<head>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/monitor/device/middleware_list.js"></script>
	</head>	
	<body>  
	<input type="hidden" id="flag" value="${flag}"/>
	<input type="hidden" id="jmxType" value="${jmxType}"/>
	<input type="hidden" id="id" value="${id}"/>
	<div class="rightContent">
		 	<div class="location">当前位置：${navigationBar}</div>
			<div class="conditions" id="divFilter">
				<table>
					<tr>
						<td>
							<b>主机：</b>
							<input type="text" id="ip" />
						</td>
						<td>
							<b>可用状态：</b>
							<select id="operStatus" class="easyui-combobox">
								<option value="">全部</option>
								<c:forEach items="${osMap}" var="entry">
									<option value="<c:out value='${entry.key}' />"><c:out value="${entry.value}" /></option>
								</c:forEach>
							</select>
						</td>
						<td class="btntd">
							<a href="javascript:void(0);" onclick="reloadTable();">查询</a>
							<a href="javascript:void(0);" onclick="resetForm('divFilter');">重置</a>
						</td>
					</tr>
				</table>
			</div>
			
			<!-- begin .datas -->
			<div class="datas">
				<table id="tblDataList">
				</table>
			</div>
		</div>	
	</body>
</html>