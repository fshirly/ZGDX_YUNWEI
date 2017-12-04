<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file = "../../common/pageincluded.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		
	</head>
	<body>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/platform/ipmanager/subnetSplit.js"></script>
		<%
			String subNetId =(String)request.getAttribute("subNetId");
			String ipAddress =(String)request.getAttribute("ipAddress");
			String subNetMark =(String)request.getAttribute("subNetMark");
		 %>
	  <div style="overflow: hidden">
		<input type="hidden" id="subNetId" value="<%= subNetId%>"/>
		<input type="hidden" id="ipAddress" value="<%= ipAddress%>"/>
		<input type="hidden" id="subNetMark" value="<%= subNetMark%>"/>
		<table id="tblSubnetSplit" class="formtable1">
			<tr>
 	  	    	<td class="title">
					拆分数量：
				</td>
				<td>
	  	  		    <select class="easyui-combobox" id="txtSplitNum" validator="{'default':'*'}">
					<option value="2">2</option>
					<option value="4">4</option>
					<option value="8">8</option>
					<option value="16">16</option>
					<option value="32">32</option>
				  </select><b>*</b>
	    		</td>
			</tr>
		</table>
		
		<table class="tableinfo1">
			<tr>
				<td>
					<label colspan="2">提示：拆分将会产生新的网络地址和广播地址，导致某些IP地址变为保留状态。</label>
				</td>
			</tr>
		</table>
		<div class="conditionsBtn">
			<input class="buttonB" type="button" value="拆分预览" onclick="javascript:toPreview();"/>
			<input class="buttonB" type="button" value="取消" onclick="javascript:$('#popWin').window('close');"/>
		</div>
		
	  </div>
	</body>
</html>