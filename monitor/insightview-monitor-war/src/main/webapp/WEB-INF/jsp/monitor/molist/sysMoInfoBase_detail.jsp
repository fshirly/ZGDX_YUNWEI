<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file = "../../common/pageincluded.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
	<head>
	<%
		String mid = (String)request.getAttribute("mid");
	 %>
	</head> 
	<body> 
		<script type="text/javascript"	src="${pageContext.request.contextPath}/js/monitor/molist/sysMoInfoBaseDetail.js"></script>
		 <style>
		   #updateSysMoInfoDiv{
		      overflow:hidden;
		   }
		 </style>
		  <input id="ipt_mid" type="hidden" value="<%=mid %>"/>
		  <div id="updateSysMoInfoDiv">
				<table id="tblUpdateSysMoInfo" class="tableinfo1" style="margin: 8px auto;">
					<tr>
						<td>
							<b class="title">监测器名称：</b>
							<label id="lbl_moName" class="inputVal"></label>
						</td>
					</tr>
					
					<tr>
						<td>
							<b class="title">监测器调度类名：</b>
							<label id="lbl_moClass" class="inputVal"></label>
						</td>
					</tr>
					
					<tr>
						<td>
							<b class="title">监测器类型：</b>
							<label id="lbl_monitorTypeName" class="inputVal"></label>
						</td>
					</tr>
					
					<tr>
						<td>
							<b class="title">监测对象性质：</b>
							<label id="lbl_moProperty" class="inputVal"></label>
						</td>
					</tr>
					<tr>
						<td>
							<b class="title">默认采集周期：</b>
							<label id="lbl_doIntervals" class="inputVal"></label>&nbsp;
							<label id="lbl_timeUnit" class="inputVal"></label>
						</td>
					</tr>
					<tr>
						<td>
							<b class="title">监测器描述：</b>
							<label id="lbl_moDescr" class="inputVal"></label>
						</td>
					</tr>
				</table>
				<div class="conditionsBtn">
				<input type="button" id="btnClose" value="关闭" onclick="javascript:parent.$('#popWin').window('close');"/>
				</div>
			</div>
				
	</body>
</html>