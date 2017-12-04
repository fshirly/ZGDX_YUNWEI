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
		<script type="text/javascript"	src="${pageContext.request.contextPath}/js/monitor/molist/sysMoInfoModify.js"></script>
		  <input id="ipt_mid" type="hidden" value="<%=mid %>"/>
		  <div id="tabs_window" style="width:100%;height:100%;">
			<div title="基本信息" id="moBaseInfo" style="overflow: hidden;">
				<iframe id="ifr0" name="modifyIfr" scrolling="auto" frameborder="0" style="width:100%;height:100%;"></iframe>
			</div>
			<div title="适用范围" id="moRange">
				<iframe id="ifr1" name="modifyIfr" scrolling="auto" frameborder="0" style="width:100%;height:100%;"></iframe>
	        </div>
	</div>
	<div class="conditionsBtn" style="position: relative;  bottom: -14px;right: 1px;left: -9px;width: 102.7%;">
				<input type="button" id="btnSave" value="确定" onclick="document.getElementById('ifr0').contentWindow.toUpdateBaseInfo();"/>
				<input type="button" id="btnClose" value="取消" onclick="javascript:parent.$('#popWin').window('close');"/>
				<input type="button" id="btnClose1" value="确定" onclick="javascript:parent.$('#popWin').window('close');window.frames['component_2'].reloadTable();" style="display:none;"/>
				<input type="button" id="btnSave3" value="确定" onclick="javascript:document.getElementById('ifr1').contentWindow.toUpdateOthersRange();" style="display:none;"/>
			</div>
	</body>
</html>