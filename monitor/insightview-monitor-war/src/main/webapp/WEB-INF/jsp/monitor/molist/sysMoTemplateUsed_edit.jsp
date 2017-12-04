<%@ page language="java"  pageEncoding="utf-8"%>
<%@ page import="java.lang.*"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file = "../../common/pageincluded.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    
  </head>
  
  <body>
  <% 
  	String moClassID = (String)request.getAttribute("moClassID"); 
  	String moIDs = (String)request.getAttribute("moIDs"); 
   %>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/js/monitor/molist/sysMoTemplateUsedEdit.js"></script>
	
		<!-- 模板选择 -->
	<div id="divUsedTemplet">
	<input type="hidden" id="moClassID" value="<%=moClassID %>"/>
	<input type="hidden" id="moIDs" value="<%=moIDs %>"/>
	<input id="moTypeLstJson" type="hidden"/>
		<table id="tblUsedTempalet" class="formtable">
			<tr>
				<td class="title">选择模板：</td>
				<td>
				<select id="ipt_monitorTemplateName" class="easyui-combobox">
									<option value="-1">未使用模板</option>
									<c:forEach items="${templateMap}" var="entry">
										<option value="<c:out value='${entry.key}' />"><c:out value='${entry.value}' /></option>
									</c:forEach>										
						  </select>
				</td>
			</tr>
		</table>
		<table id="monitor" class="formtable">
		</table>
		<div class="conditionsBtn">
			<a href="javascript:void(0);" id="btnSave" onclick="javascript:toAdd();">确定</a>
			<a href="javascript:void(0);" id="btnBack" onclick="javascript:$('#popWin').window('close');">取消</a>
						
		</div>
	</div> 
	
  </body>
</html>
