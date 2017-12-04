<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@ include file = "../../common/pageincluded.jsp" %>
<html>
<head>
<style type="text/css">
.table{
	float: left;
	width:45%;
}
 .table td{
	min-width: 6px;
	font-size: 10px;
	vertical-align: top;
	line-height: 15px;
	}
</style>
</head>
	<body>  
	<div id="tabs_window" >
		 	<div class="location">当前位置：${navigationBar}</div>
			<!-- begin .datas -->
			<div  style="overflow: auto;margin-left: 50px;">
 				<table id="tblList" class="table">
				<c:forEach  items="${resultMap}" var="machineRoom"> 
				<tr> <td colspan="9" style="background:  #c2dff9;"><b class="title">${machineRoom.key}</b></td></tr>
				<tr>
					<td> <b class="title">模块地址</b> </td>
					<td> <b class="title">类型(编号)</b> </td>
					<td> <b class="title">监测说明</b> </td>
					<td> <b class="title">当前值</b> </td>
				</tr>
					<c:forEach items="${machineRoom.value}" var="bean" begin="0" end="${fn:length(machineRoom.value)/2-1}">
					<tr>
						<td>${bean.address}</td>
						<c:if test="${bean.valueType==0}">
							<td>STATION(${bean.indexOfType})</td>
						</c:if>
						<c:if test="${bean.valueType==1}">
							<td>DEVICE(${bean.indexOfType})</td>
						</c:if>
						<c:if test="${bean.valueType==2}">
							<td>DI(${bean.indexOfType})</td>
						</c:if>
						<c:if test="${bean.valueType==3}">
							<td>AI(${bean.indexOfType})</td>
						</c:if>
						<c:if test="${bean.valueType==4}">
							<td>DO(${bean.indexOfType})</td>
						</c:if>
						<c:if test="${bean.valueType==5}">
							<td>AO(${bean.indexOfType})</td>
						</c:if>
						<c:if test="${bean.valueType==6}">
							<td>STRINGI(${bean.indexOfType})</td>
						</c:if>
						<c:if test="${bean.valueType==7}">
							<td>STRINGO(${bean.indexOfType})</td>
						</c:if>
						<c:if test="${bean.valueType==8}">
							<td>ENUMI(${bean.indexOfType})</td>
						</c:if>
						<c:if test="${bean.valueType==9}">
							<td>ENUMO(${bean.indexOfType})</td>
						</c:if>
						<c:if test="${bean.valueType==10}">
							<td>DOORI(${bean.indexOfType})</td>
						</c:if>
						<c:if test="${bean.valueType==11}">
							<td>DOORO(${bean.indexOfType})</td>
						</c:if>
						<td>${bean.oriDesc}</td>
						<c:choose>
						<c:when test="${bean.valueType==2}">
							<td>${bean.curValue}(${bean.curIntValue})</td>
						</c:when>
						<c:when test="${bean.valueType==3}">
							<td>${bean.curValue}(${bean.curIntValue})</td>
						</c:when>
						<c:otherwise>
							<td>${bean.curValue}</td>
						</c:otherwise>
						</c:choose>
					</tr>
	    			</c:forEach>
	    			<c:if test="${fn:length(machineRoom.value)%2 == 1}"> 
	    				<tr>
	    					<td colspan="4">&nbsp;</td>
	    				</tr>
	    			</c:if>
				</c:forEach>
				</table>
				
				<table  class="table">
				
				<c:forEach  items="${resultMap}" var="machineRoom"> 
				<tr> <td colspan="4" style="background:  #c2dff9;"><b class="title">${machineRoom.key}</b></td></tr>
				<tr>
					<td> <b class="title">模块地址</b> </td>
					<td> <b class="title">类型(编号)</b> </td>
					<td> <b class="title">监测说明</b> </td>
					<td> <b class="title">当前值</b> </td>
				</tr>
					<c:forEach items="${machineRoom.value}" var="bean" begin="${fn:length(machineRoom.value)/2}" end="${fn:length(machineRoom.value)}">
					<tr>
						<td>${bean.address}</td>
						<c:if test="${bean.valueType==0}">
							<td>STATION(${bean.indexOfType})</td>
						</c:if>
						<c:if test="${bean.valueType==1}">
							<td>DEVICE(${bean.indexOfType})</td>
						</c:if>
						<c:if test="${bean.valueType==2}">
							<td>DI(${bean.indexOfType})</td>
						</c:if>
						<c:if test="${bean.valueType==3}">
							<td>AI(${bean.indexOfType})</td>
						</c:if>
						<c:if test="${bean.valueType==4}">
							<td>DO(${bean.indexOfType})</td>
						</c:if>
						<c:if test="${bean.valueType==5}">
							<td>AO(${bean.indexOfType})</td>
						</c:if>
						<c:if test="${bean.valueType==6}">
							<td>STRINGI(${bean.indexOfType})</td>
						</c:if>
						<c:if test="${bean.valueType==7}">
							<td>STRINGO(${bean.indexOfType})</td>
						</c:if>
						<c:if test="${bean.valueType==8}">
							<td>ENUMI(${bean.indexOfType})</td>
						</c:if>
						<c:if test="${bean.valueType==9}">
							<td>ENUMO(${bean.indexOfType})</td>
						</c:if>
						<c:if test="${bean.valueType==10}">
							<td>DOORI(${bean.indexOfType})</td>
						</c:if>
						<c:if test="${bean.valueType==11}">
							<td>DOORO(${bean.indexOfType})</td>
						</c:if>
						<td>${bean.oriDesc}</td>
						<c:choose>
						<c:when test="${bean.valueType==2}">
							<td>${bean.curValue}(${bean.curIntValue})</td>
						</c:when>
						<c:when test="${bean.valueType==3}">
							<td>${bean.curValue}(${bean.curIntValue})</td>
						</c:when>
						<c:otherwise>
							<td>${bean.curValue}</td>
						</c:otherwise>
						</c:choose>
					</tr>
	    			</c:forEach> 
					</c:forEach>
				</table>
			</div>
		</div>
	</body>
</html>