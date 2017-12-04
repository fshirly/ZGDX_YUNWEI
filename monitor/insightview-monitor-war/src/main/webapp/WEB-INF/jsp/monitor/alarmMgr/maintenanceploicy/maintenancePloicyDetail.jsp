<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file = "../../../common/pageincluded.jsp" %>
<html>
  <head>
  </head>
  <body>
		<table id="tblDetailInfo" class="tableinfo1">
			<tr>
				<td><b class="title">维护期标题：</b>
					<label class="input">${alarmVo.maintainTitle}</label>
				</td>
			</tr>
				<tr>
				<td><b class="title">事件源对象：</b>
					<label class="input" >${alarmVo.moname}</label>
				</td>
			</tr>
			<tr>
				<td><b class="title">维护期类型：</b>
					<label class="input">
						<c:choose>
							<c:when test="${alarmVo.maintainType==1}">新建</c:when>
							<c:when test="${alarmVo.maintainType==2}">割接</c:when>
							<c:when test="${alarmVo.maintainType==3}">故障</c:when>
						</c:choose>	
					</label>
				</td>
			</tr>
			<tr>
				<td><b class="title">维护起止时间：</b>
					<label class="input">${alarmVo.startTime}</label>--<label class="input">${alarmVo.endTime}</label>
				</td>
			</tr>
			<tr>
				<td><b class="title">启用状态：</b>
					<label class="input">
						<c:choose>
							<c:when test="${alarmVo.isUsed==1}">启用</c:when>
							<c:when test="${alarmVo.isUsed==0}">停用</c:when>
						</c:choose>
					</label>
				</td>
			</tr>
			<tr>
				<td><b class="title">维护期描述：</b>
					<label class="input">${alarmVo.maintainDesc}</label>
				</td>
			</tr>
		</table>
		
		<div class="conditionsBtn">
		<input class="buttonB" type="button" id="btnClose2" value="关闭" onclick="javascript:$('#popWin').window('close');"/>		
		</div>
			
  </body>
</html>
