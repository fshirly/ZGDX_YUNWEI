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
		<script type="text/javascript"	src="${pageContext.request.contextPath}/js/monitor/molist/sysMoInfoBaseModify.js"></script>
		 <style>
		   input,textarea,span{
		       margin-left:6px; 
		   }
		   span > input,span >span{
		    margin-left:0;
		   }
		   #updateSysMoInfoDiv{
		      overflow:hidden;
		   }
		 </style>
		  <input id="ipt_mid" type="hidden" value="<%=mid %>"/>
		  <input id="monitorProperty" type="hidden" value=""/>
		  <div id="updateSysMoInfoDiv">
				<table id="tblUpdateSysMoInfo" class="formtable" style="margin: 8px auto;">
					<tr>
						<td class="title">
							监测器名称：
						</td>
						<td colspan="3">
							<input id="ipt_moName"  validator="{'default':'*','length':'1-30'}" /><b>*</b>
						</td>
					</tr>
					
					<tr>
						<td class="title">
							监测器调度类名：
						</td>
						<td>
							<input id="ipt_moClassPre" value="Job" style="width:18px;border-right: 0 none; margin-right: -8px;text-align: right;" readonly="readonly" /><input id="ipt_moClass" validator="{'default':'en','length':'0-47'}"  style="width: 156px;border-left: 0 none;"/><b>*</b>
						</td>
					</tr>
					
					<tr>
						<td class="title">
							监测器类型：
						</td>
						<td>
						  <select id="ipt_monitorTypeName" name="asfas" class="easyui-combobox" panelHeight="160" panelWidth="180">
								<!--<c:if test="${!empty typeMap}">
									--><c:forEach items="${typeMap}" var="entry">
										<option value="<c:out value='${entry.key}' />"><c:out value='${entry.value}' /></option>
									</c:forEach>										
						  		<!--</c:if>	-->	
						  </select><b>*</b>
						</td>
					</tr>
					
					<tr>
						<td class="title">
							监测对象性质：
						</td>
						<td>
						  <select id="ipt_moProperty" class="easyui-combobox" panelHeight="80" panelWidth="180">
							<option value="0">设备</option>
							<option value="1">其他</option>
						  </select><b>*</b>
						</td>
					</tr>
					<tr>
						<td class="title">
							默认采集周期：
						</td>
						<td>
							<input id="ipt_doIntervals" class="inputs" type="text" style="width:60px;"/>&nbsp;
							<select id="unit" class="inputs" style="width:60px;">
							<option value="1">
								分
							</option>
							<option value="2">
								小时
							</option>
							<option value="3">
								天
							</option>
							</select>
						</td>
					</tr>
					<tr>
						<td class="title">
							监测器描述：
						</td>
						<td colspan="3" >
							<textarea rows="6" cols="50" class="x2" id="ipt_moDescr" validator="{'length':'0-200'}"></textarea>
						</td>
					</tr>
				</table>
			</div>	
	</body>
</html>