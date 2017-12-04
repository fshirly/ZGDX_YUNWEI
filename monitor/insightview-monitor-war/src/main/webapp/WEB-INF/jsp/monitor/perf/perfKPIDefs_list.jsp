<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file = "../../common/pageincluded.jsp" %>
<html>
	<head>
	</head>

	<body>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/monitor/perf/perfKPIDefs_list.js"></script>
		<div class="rightContent">
			<div class="location">
				当前位置：${navigationBar}
			</div>
			<div class="conditions" id="divFilter">
				<table>
					<tr>
						<td>
							<b>指标名称：</b>
							<input type="text" id="txtName" />
						</td>
						<td>
							<b>指标英文名：</b>
							<input type="text" id="txtEnName" />
						</td>
						<td>
							<b>指标类别：</b>
							<select id="txtKPICategory" name="txtKPICategory" class="easyui-combobox">
							<option value="">全部</option>
						    <c:forEach items="${categoryLst}" var="vo">
							<option value="<c:out value='${vo.kpiCategory}' />" ><c:out value="${vo.kpiCategory}" /></option>	
							</c:forEach>
						</select>
						</td>
					</tr>
					<tr>
						<td>
							<b>对象类型：</b>
							<input type="text" id="txtClassName" />
						</td>
						<td>
							<%--<b>是否支持阈值</b>
							<select id="txtIsSupport" class="inputs">
								<option value="-1">
									请选择
								</option>
								<option value="1">
									是
								</option>
								<option value="2">
									否
								</option>
							</select>
						--%></td>
						
						<td class="btntd">
							<a href="javascript:void(0);" onclick="reloadTable();">查询</a>
							<a href="javascript:void(0);" onclick="resetForm('divFilter');">重置</a> 
						</td>
					</tr>
				</table>
			</div>
			<div class="datas tops2">
				<table id="tblPerfKPIDef">
				</table>
			</div>
		</div>
	</body>
</html>
