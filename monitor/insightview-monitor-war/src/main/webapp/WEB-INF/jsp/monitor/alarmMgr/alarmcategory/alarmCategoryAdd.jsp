<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ include file = "../../../common/pageincluded.jsp" %>

<html>
  <head>
  </head>
  <body>
  		 <script type="text/javascript"  src="${pageContext.request.contextPath}/js/monitor/alarmMgr/alarmcategory/alarmCategoryAdd.js" ></script>
			<!-- 新增告警类型信息 -->
			<div id="divAddInfo" >
			<input id="ipt_categoryID" type="hidden" />
			<input id="flag" type="hidden" value="add"/>
			<table id="tblEdit" class="formtable1">
				<tr>
					<td class="title">分类名称：</td>
					<td><input id="ipt_categoryName"  type="text"  validator="{'default':'*' ,'length':'1-20'}" onblur="checkNameUnique();" /><b>*</b></td>				
				</tr>
				<tr>
					<td class="title">SNMP企业私有ID：</td>
					<td><input id="ipt_alarmOID"  type="text"  validator="{'default':'*' ,'length':'1-30'}"  /><b>*</b></td>				
				</tr>
				<tr>
					<td class="title">描述：</td>
					<td>
						<textarea id="ipt_descr"  rows="3"></textarea>
					</td>
				</tr>
			</table>		
		<div class="conditionsBtn">
			<input class="buttonB" type="button" id="btnSave" value="确定" onclick="javascript:doAdd();"/>
			<input class="buttonB" type="button" id="btnUpdate" value="取消" onclick="javascript:$('#popWin').window('close');"/>
		</div>
	</div>
				
  </body>
</html>
