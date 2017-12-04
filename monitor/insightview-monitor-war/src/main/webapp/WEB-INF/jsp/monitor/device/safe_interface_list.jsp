<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file = "../../common/pageincluded.jsp" %>
<html>
	<head>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/monitor/device/safe_interface_list.js"></script>
	</head>	
	<body>  
	<input type="hidden" id="flag" value="${flag}"/>
	<input type="hidden" id="mOClassID" value="${mOClassID}"/>
	<div class="rightContent">
		 	<div class="location">当前位置：${navigationBar}</div>
			<div class="conditions" id="divFilter">
				<table>
					<tr>
						<td>
							<b>所属设备IP：</b> <!--接口ip改为设备ip  -->
							<input type="text" id="deviceIP" />
						</td>
						<td>
							<b>所属设备：</b>
							<input type="text" id="deviceMOName" />
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