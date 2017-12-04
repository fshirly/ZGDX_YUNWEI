<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file = "../../common/pageincluded.jsp" %>
<html>
	<head>
	
	</head>

	<body>
	<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/monitor/portaledit/topNEdit.js"></script>
			<!-- end .datas -->
		<table id="tblEdit" class="formtable1">
		<input type="hidden" id="timeBegin" name="timeBegin"  />
		<input type="hidden" id="timeEnd" name="timeEnd"  />
		<input type="hidden" id="widgetId" name="widgetId" value="${widgetId}" />
		<input type="hidden" id="widgetName" name="widgetName" value="${widgetName}" />
		<input type="hidden" id="url" name="url" value="${url}"/>
		<input type="hidden" id="orderby" name="orderby" value="${orderby}"/>
		
			<tr>
				<td class="title">标题名称：</td>
				<td><input id="widgetTitle"  type="text" value="${widgetTitle}" style="width: 220px;" validator="{'default':'*','length':'1-100'}"/><b>*</b></td>						
			</tr>
				<tr>
				<td class="title">排序方式：</td>
				<td><select name="topOrder" id="topOrder" onchange="selDeviceName()" style="width: 170px;">
									<option <c:if test="${orderby =='desc'}">selected</c:if>  value="desc"  >正序</option>
									<option <c:if test="${orderby =='asc'}">selected</c:if> value="asc" >倒序</option>
							
						</select>&nbsp;<input id="num"  type="text" style="width:50px;" value="${num}"  onchange="checkTip();" validator="{'default':'*' ,'length':'1-5'}"/><b>*</b>
				</tr>
				<tr>
					<td class="title">时间周期：</td>
					<td>
					
						<select id="timeTemplate" name="timeTemplate" onchange="changeTime()" style="width: 230px;">
							<option value="1" >最近半小时</option>
						<option value="2" >最近1小时</option>
						<option value="3" >最近2小时</option>
						<option value="4" >最近4小时</option>
						<option value="5" >最近6小时</option>
						<option value="6" >最近12小时</option>
						<option value="7" selected="selected">最近1天 </option>
						<option value="8" >最近2天</option>
						<option value="9" >今天</option>		
						<option value="10" >昨天</option>
						<option value="11" >本周 </option>
						<option value="12" >最近一周</option>
						<option value="13" >本月</option>
						<option value="14" >上月</option>	
				</select>
					</td>				
				</tr>
				<tr>
				<td  class="title"></td>
				<td><div id="tipDiv"></div></td>
				</tr>
		</table>
		<div class="conditionsBtn">
			<input class="buttonB" type="button" id="btnSave" value="确定" onclick="javascript:doCommit();"/>
			<input class="buttonB" type="button" id="btnClose" value="取消" onclick="javascript:$('#popView').window('close');"/>
		</div>
		<script>
		  $(document).ready(function() {
	          changeTime();
           });
		</script>
	</body>
</html>
