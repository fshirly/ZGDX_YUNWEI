<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file = "../../common/pageincluded.jsp" %>
<html>
	<head>
	</head>
	<body >
	<input type="hidden" id="moClassID" value="${moClassID}" >
	<input type="hidden" id="rsMoClassID" value="${rsMoClassID}" >
	<input type="hidden" id="moids" value="${moids}" >
	<input type="hidden" id="isShowTip" value="${isShowTip}" >
	<input type="hidden" id="isAsset"  >
			<table id="tblConfig" class="formtable1">
				<tr>
					<td class="title">
						资源归类：
					</td>
					<td>
						<select id="type" onchange="selResName()">
							<c:forEach items="${typeLst}" var="entry">
									<option value="<c:out value='${entry.id}' />"><c:out value="${entry.name}" /></option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr id="assetTypeLst" style="display:none" >
					<td class="title">
						资产归类：
					</td>
					<td>
						<select id="assetType">
							
						</select>
					</td>
				</tr>
				
				<tr>
					<td colspan="2"></td>
				</tr>
				<tr>
					<td colspan="2" class="title" id="tip">
						<strong>选择是否导入子对象到CMDB 同时设置对应组件:</strong>
					</td>
				</tr>
				<c:forEach items="${nameLst}" var="lst" varStatus="no">
				<tr>
					<td  class="title">
						${lst.classLable}：
					</td>
					<td>
						<select name="typeName" id="${lst.classId}">
						</select>
					</td>
				</tr>
				</c:forEach>
			</table>
			<div class="conditionsBtn">
				<input class="buttonB" type="button" id="btnSave" value="确定"
					onclick="javascript:doConfig();" />
				<input class="buttonB" type="button" id="btnClose" value="取消"
					onclick="javascript:$('#popWin').window('close');" />
			</div>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/monitor/device/cmdb.js"></script>
</body>
</html>