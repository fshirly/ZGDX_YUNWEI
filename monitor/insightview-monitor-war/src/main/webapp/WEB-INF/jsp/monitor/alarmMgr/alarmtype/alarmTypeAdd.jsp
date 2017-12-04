<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ include file = "../../../common/pageincluded.jsp" %>

<html>
  <head>
  </head>
  <body>
  		 <script type="text/javascript"  src="${pageContext.request.contextPath}/js/monitor/alarmMgr/alarmtype/alarmTypeAdd.js" ></script>
			<!-- 新增告警类型信息 -->
			<input id="ipt_alarmTypeID" type="hidden" />
			<input id="flag" type="hidden" value="add"/>
			<table id="tblEdit" class="formtable1">
				<tr>
					<td class="title">类型名称：</td>
					<td><input id="ipt_alarmTypeName"  type="text"  validator="{'default':'*' ,'length':'1-20'}" onblur="checkNameUnique();"  /><b>*</b></td>				
				</tr>
			</table>		
		<div class="conditionsBtn">
			<input class="buttonB" type="button" id="btnSave" value="确定" onclick="javascript:doAdd();"/>
			<input class="buttonB" type="button" id="btnUpdate" value="取消" onclick="javascript:$('#popWin').window('close');"/>
		</div>
  </body>
</html>
