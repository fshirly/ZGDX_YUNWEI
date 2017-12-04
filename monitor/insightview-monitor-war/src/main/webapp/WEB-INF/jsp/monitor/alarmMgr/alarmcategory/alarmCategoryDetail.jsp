<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file = "../../../common/pageincluded.jsp" %>


<html>
  <head>
	
  </head>
  
  <body>
		<table id="tblDetailInfo" class="tableinfo1">
			<tr>
				<td><b class="title">分类名称：</b>
					<label class="input">${alarmVo.categoryName}</label>
				</td>
			</tr>
				<tr>
				<td><b class="title">SNMP企业私有ID：</b>
					<label class="input">${alarmVo.alarmOID}</label>
				</td>
			</tr>
			<tr>
				<td><b class="title">描述：</b>
					<label class="input">${alarmVo.descr}</label>
				</td>
			</tr>
		</table>
		
		<div class="conditionsBtn">
		<input class="buttonB" type="button" id="btnClose2" value="关闭" onclick="javascript:$('#popWin').window('close');"/>		
		</div>
			
  </body>
</html>
