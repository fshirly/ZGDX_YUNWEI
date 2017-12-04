<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file = "../../common/pageincluded.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		
	</head>
	<body>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/platform/ipmanager/assignDeptFromSubNet.js"></script>
		<%
			String tipFreeIds =(String)request.getAttribute("freeIds");
			String messageTip =(String)request.getAttribute("messageTip");
		 %>
	  <input type="hidden" id="tipFreeIds" value="<%= tipFreeIds%>"/>
	  <div id="divUseInfo" >
		<table cellspacing="10" class="formtable1">
			<tr>
				<td></td>
				<td>
					<label id="messageTip" class="input"><%= messageTip%></label>
				</td>
			</tr>
		</table>
		
		<div class="conditionsBtn">
			<a href="javascript:var tipFreeIds=document.getElementById('tipFreeIds').value ; viewFreeAddress(tipFreeIds);" class="fltrt">查看空闲地址列表</a>
			<a href="javascript:var tipFreeIds=document.getElementById('tipFreeIds').value ; doAssign(tipFreeIds,2);" class="fltrt">确定</a>
			<a href="javascript:cancle();" class="fltrt">取消</a>
		</div>
	  </div>
	</body>
</html>