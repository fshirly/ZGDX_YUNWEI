<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file = "../../../common/pageincluded.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<title>告警类型详情</title>

</head>
<body>
	<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/monitor/alarmMgr/alarmcategory/alarmCategoryModify.js"></script>
			<!-- end .datas -->
	<div id="divModifyInfo" >
	<input id="ipt_categoryID" type="hidden" value="${alarmVo.categoryID}"/>
		<table id="tblEdit" class="formtable1">
			<tr>
				<td class="title">分类名称：</td>
				<td><input id="ipt_categoryName"  type="text"  validator="{'default':'*' ,'length':'1-20'}" 
				value="${alarmVo.categoryName}"  onblur="checkNameUnique2();" /><b>*</b></td>				
			</tr>
			<tr>
				<td class="title">SNMP企业私有ID：</td>
				<td><input id="ipt_alarmOID"  type="text"  validator="{'default':'*' ,'length':'1-30'}" value="${alarmVo.alarmOID}" /><b>*</b></td>				
			</tr>
			<tr>
				<td class="title">描述：</td>
				<td>
					<textarea id="ipt_descr"  rows="3" >${alarmVo.descr}</textarea>
				</td>				
			</tr>
		</table>
		<div class="conditionsBtn">
			<input class="buttonB" type="button" id="btnSave" value="确定" onclick="javascript:doUpdate();"/>
			<input class="buttonB" type="button" id="btnClose" value="取消" onclick="javascript:$('#popWin').window('close');"/>
		</div>
	</div>
		
</body>
</html>