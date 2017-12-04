<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file = "../../../common/pageincluded.jsp" %>
<html>
  <head>
  </head>
  <body>
  		 <script type="text/javascript"  src="${pageContext.request.contextPath}/js/monitor/alarmMgr/alarmlevel/alarmLevelAdd.js" ></script>
			<!-- 新增告警等级信息 -->
			<input id="ipt_alarmLevelID" type="hidden" />
			<input id="flag" type="hidden" value="add"/>
			<table id="tblEdit" class="formtable1">
				<tr>
					<td class="title">等级名称：</td>
					<td><input id="ipt_alarmLevelName"  type="text"  validator="{'default':'*' ,'length':'1-20'}" 
								onblur="checkNameUnique();" /><b>*</b></td>				
				</tr>
				<tr>
					<td class="title">等级颜色：</td>
					<td>
						<select name="levelColor" id="ipt_levelColor" validator="{'default':'*'}" >
							<option></option>
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
					<td class="title">等级图标：</td>
					<td>
						<select name="levelIcon" id="ipt_levelIcon">
							<option></option>
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
			<input class="buttonB" type="button" id="btnSave" value="确定" onclick="javascript:doAdd();"/>
			<input class="buttonB" type="button" id="btnUpdate" value="取消" onclick="javascript:$('#popWin').window('close');"/>
		</div>
  </body>
</html>
