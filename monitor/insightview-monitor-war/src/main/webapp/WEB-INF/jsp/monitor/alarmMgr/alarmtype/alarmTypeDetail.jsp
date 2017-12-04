<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file = "../../../common/pageincluded.jsp" %>


<html>
  <head>
    <script type="text/javascript"  src="${pageContext.request.contextPath}/js/monitor/alarmMgr/alarmType/alarmTypeDetail.js" ></script>

  </head>
  
  <body>
		
			<table id="tblDetailInfo" class="tableinfo1">
				<tr>
					<td><b class="title">类型名称：</b>
					<label class="input">${alarmVo.alarmTypeName}</label>
					</td>
				</tr>
			</table>
		
		<div class="conditionsBtn">
		<input class="buttonB" type="button" id="btnClose2" value="关闭" onclick="javascript:$('#popWin').window('close');"/>		
		</div>
			
  </body>
</html>
