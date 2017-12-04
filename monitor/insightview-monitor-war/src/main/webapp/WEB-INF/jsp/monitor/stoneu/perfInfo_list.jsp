<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@ include file = "../../common/pageincluded.jsp" %>
<html>
<head>
</head>
	<body>  
<style type="text/css">
	.table {
	width: 70%;
	margin: 35px auto;
	}
	.table td {
    width: 170px;	
    font-size:14px; 
    }
	 .title{
		text-align: right;
		min-width: 167px;
		font-size:14px; 
		vertical-align: top;
		/*padding-top: 6px;*/
		line-height: 36px;
		font-weight:normal;
	}
	.h2{
		line-height: 24px;
		padding-left: 10px;
	}
</style>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/monitor/stoneu/perfInfo.js"></script>
	<div>
 				<table id="tbltemList" class="table">
				<c:forEach  items="${tempHumMap}" var="temPerf"> 
				<tr> <td colspan="8" style="background: #ebeff2;"><b class="h2">${temPerf.key}</b></td></tr>
				<tr>
					<td class="title">温度 </td>
					<td> 
						<input id="temp" name="temp" type= "hidden" value="${temPerf.value.temp}"/>
					</td>
					<td class="title">湿度 </td>
					<td> 
						<input id="humidity" name="humidity" type= "hidden" value="${temPerf.value.humidity}"/>
					</td>
					<td class="title"> 通信状态</td>
					<td> 
						<input id="status" type= "hidden" value="${temPerf.value.status}"/>
					</td>
					<td class="title">设备状态</td>
					<td> 
						<input  id="devStatus" type= "hidden" value="${temPerf.value.devStatus}"/>
					</td>
				</tr>
				</c:forEach>
				</table>
				<table id="tblsmokeList" class="table">
						<c:forEach  items="${somkeMap}" var="smokePerf">
							<tr>
								<td colspan="8" style="background: #ebeff2;"><b class="h2">${smokePerf.key}</b></td>
							</tr>
							<tr id="smokeStatus">
								<td class="title">设备状态</td>
								<td> 
									<input type= "hidden" value="${smokePerf.value.deviceStatus}"/>
								</td>
								<td colspan="6"></td>
							</tr>
						</c:forEach>
					</table>
				
				<table id="tblsoundList" class="table">
					<c:forEach  items="${soundLightMap}" var="soundLightPerf">
						<tr>
							<td colspan="8" style="background: #ebeff2;"><b class="h2">${soundLightPerf.key}</b></td>
						</tr>
						<tr id="soundStatus">
							<td class="title">声光状态</td>
							<td> 
								<input type= "hidden" value="${soundLightPerf.value.slStatus}"/>
							</td>
							<td colspan="6"></td>
						</tr>
					</c:forEach>
				</table>
				
					<table id="tblcontList" class="table">
					<c:forEach  items="${contatMap}" var="contatPerf">
						<tr>
							<td colspan="8" style="background: #ebeff2;"><b class="h2">${contatPerf.key}</b></td>
						</tr>
						<tr id="contStatus"> 
							<td class="title">通信状态</td>
							<td> 
								<input type= "hidden" value="${contatPerf.value.linkStatus}"/>
							 </td>
							<td class="title">设备状态</td>
							<td>
								<input type= "hidden" value="${contatPerf.value.consStatus}"/>
							 </td>
							 <td colspan="4"></td>
						</tr>
					</c:forEach>
				</table>
		</div>
	</body>
</html>