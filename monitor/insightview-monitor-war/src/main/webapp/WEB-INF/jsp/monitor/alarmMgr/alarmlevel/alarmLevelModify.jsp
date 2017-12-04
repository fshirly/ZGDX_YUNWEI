<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file = "../../../common/pageincluded.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<title>告警等级</title>

</head>
<body>
	<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/monitor/alarmMgr/alarmlevel/alarmLevelModify.js"></script>
			<!-- end .datas -->
	<input id="ipt_alarmLevelID" type="hidden" value="${alarmVo.alarmLevelID}"/>
		<table id="tblEdit" class="formtable1">
			<tr>
				<td class="title">等级名称：</td>
				<td><input id="ipt_alarmLevelName"  type="text" value="${alarmVo.alarmLevelName}" validator="{'default':'*' ,'length':'1-20'}" 
							onblur="checkNameUnique2();" /><b>*</b></td>				
			</tr>
			<tr>
					<td class="title">等级颜色：</td>
					<td>
						<select name="levelColor" id="ipt_levelColor" validator="{'default':'*'}" >
							<option value="${alarmVo.levelColor}">${alarmVo.levelColorName}</option>
							<option value="#ff0000">红色（紧急）</option>
							<option value="#ff9900">橙色（严重）</option>
							<option value="#ffff00">黄色（一般）</option>
							<option value="#0000ff">蓝色（提示）</option>
							<option value="#009900">绿色（正常）</option>
							<option value="#c0c0c0">灰色（未确定）</option>
						</select><b>*</b>
					</td>				
				</tr>
				<tr style="display: none;">
					<td class="title" >等级图标：</td>
					<td>
						<select name="levelIcon" id="ipt_levelIcon" >
							<option value="${alarmVo.levelIcon}">${alarmVo.levelIcon}</option>
							<c:forEach items="${iconList}" var="vo">
								<option value="<c:out value='${vo.levelIcon}' />">
									<c:out value='${vo.levelIcon}'/>
								</option>
							</c:forEach>
						</select><b>*</b>
					</td>				
				</tr>
		</table>
		<div class="conditionsBtn">
			<input class="buttonB" type="button" id="btnSave" value="确定" onclick="javascript:doUpdate();"/>
			<input class="buttonB" type="button" id="btnClose" value="取消" onclick="javascript:$('#popWin').window('close');"/>
		</div>
		
</body>
</html>