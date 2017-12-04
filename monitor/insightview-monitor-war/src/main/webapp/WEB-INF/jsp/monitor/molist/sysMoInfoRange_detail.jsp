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
		<script type="text/javascript"	src="${pageContext.request.contextPath}/js/monitor/molist/sysMoInfoRangeDetail.js"></script>
		 <style>
		   #othersConfigRangeDiv{
		      overflow:hidden;
		   }
		   #deviceConfigRangeDiv{
		   	  overflow:hidden;
		   }
		 </style>
		 <input id="ipt_mid" type="hidden" value="<%=mid %>"/>
			<!-- 配置适用范围 -->
			<div id="deviceConfigRangeDiv">
			<!-- begin .datas -->
				<div>
					<table id="tblSysMoManufactureList">
					</table>
				</div>
				<div class="conditionsBtn">
				<input type="button" id="btnSave2" value="关闭" onclick="parent.$('#popWin').window('close');"/>
				</div>
			</div>
				
			<div id="othersConfigRangeDiv">
				<table id="tblOthersConfig" class="tableinfo" style="margin:0 auto;">
					<tr>
						<td class="title">
							<b>对象类型：</b>
							<label id ="lbl_moClassID"></label>
						</td>
					</tr>
				</table>
				<div class="conditionsBtn">
				<input type="button" id="btnSave3" value="关闭" onclick="parent.$('#popWin').window('close');"/>
				</div>
			</div>
	</body>
</html>