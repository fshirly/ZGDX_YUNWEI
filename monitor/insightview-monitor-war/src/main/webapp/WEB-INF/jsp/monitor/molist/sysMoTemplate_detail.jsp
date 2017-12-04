<%@ page language="java"  pageEncoding="utf-8"%>
<%@ page import="java.lang.*;"  %>
<%@ include file = "../../common/pageincluded.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <%
    	String templateID = (String)request.getAttribute("templateID");
     %>
  </head>
  
  <body>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/js/monitor/molist/sysMoTemplateDetail.js"></script>
		
  	<input id="ipt_templateID" type="hidden" value="<%=templateID %>"/>
	
		<!-- 新增模板 -->
	<div id="divUpdateTempletInfo">
		<table id="tblUpdateTemplet" class="tableinfo">
			<tr>
				<td>
					<b class="title">模板名称：</b>
					<label id="lbl_templateName" class="input"></label>
				</td>
			</tr>
			<tr>
				<td>
					<b class="title">监测对象类型：</b>
					<label id="lbl_moClassId" class="input"></label>
				</td>
			</tr>
			<tr>
				<td>
					<b class="title">模板描述：</b>
					<label id="lbl_descr" class="input"></label>
				</td>
			</tr>
		</table>
		<table id="monitorView" class="tableinfo"				>
			<tr>
				<td><b class="title">监测器信息:</b></td>
			</tr>
		</table>
		<div class="conditionsBtn">
			<a href="javascript:void(0);" id="btnBack" onclick="javascript:$('#popWin').window('close');">取消</a>
						
		</div>
	</div> 
	
  </body>
</html>
