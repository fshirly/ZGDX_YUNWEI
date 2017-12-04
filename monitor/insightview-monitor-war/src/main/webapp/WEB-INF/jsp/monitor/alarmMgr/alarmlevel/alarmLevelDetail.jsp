<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file = "../../../common/pageincluded.jsp" %>

<html>
  <head>
  </head>
  <body>
			<table id="tblDetailInfo" class="tableinfo1">
				<tr>
					<td><b class="title">等级名称：</b>
					<label class="input">${alarmVo.alarmLevelName}</label>
					</td>
				</tr>
				<tr>
					<td><b class="title">等级颜色：</b>
					<label class="input"><span style="background-color:${alarmVo.levelColor} ">${alarmVo.levelColorName}</span></label>
					</td>
				</tr>
			</table>
		
		<div class="conditionsBtn">
		<input class="buttonB" type="button" id="btnClose2" value="关闭" onclick="javascript:$('#popWin').window('close');"/>		
		</div>
			
  </body>
</html>
