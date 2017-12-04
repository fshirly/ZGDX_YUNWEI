<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file = "../../common/pageincluded.jsp" %>
<html>
	<head>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/monitor/device/mysql_sysvar.js"></script>
	</head>	
	<body>  
			<input type="hidden" id="flag" value="${flag}"/>
			<input type="hidden" id="sqlServerMOID" value="${sqlServerMOID}"/>
		<div class="rightContent">
		 	<div class="location">当前位置：${navigationBar}</div>	
			<div class="conditions" id="divFilter">
				<table>
					<tr>
					<td>
						<select id="selName" >
								<option value="varName" >
									变量名称
								</option>
								<option value="varValue">
									变量值
								</option>
								<option value="varChnName">
									变量中文名
								</option>
								<option value="dynamicVarType">
									动态系统变量类型
								</option>
							</select>&nbsp;&nbsp;&nbsp;
					</td>
					<td>
						<select id="opera" >
								<option value="%" >
									包含
								</option>
								<option value="=">
									等于
								</option>
							</select>&nbsp;&nbsp;&nbsp;
					</td>
					<td><input type="text" id="txtValue" /></td>
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