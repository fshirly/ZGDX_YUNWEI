<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %> 
<%@ include file = "../../../common/pageincluded.jsp" %>
<html>
  <head>
  </head>
  <body>
	        <script type="text/javascript" src="${pageContext.request.contextPath}/js/monitor/alarmMgr/alarmactive/alarmActive.js"></script>
			<table class="tableinfo">
				<tr>
					<td>
							<b class="title">段名称：</b>
							<label >${rollSEG.segName}</label>
					</td>
					<td>
							<b class="title">数据库名称：</b>
							<label >${rollSEG.dbName}</label>
					</td>
				</tr>
				<tr>
					<td>
							<b class="title">所属表空间名称：</b>
							<label >${rollSEG.tbsName}</label>
					</td>
					<td >
							<b class="title">段大小：</b>
							<label >${rollSEG.segSize}</label>
					</td>
				</tr>
				<tr>
					<td >
							<b class="title">Extent初始大小：</b>
							<label >${rollSEG.initialExtent}</label>
					</td>
					<td >
							<b class="title">Extent缺省增长百分比：</b>
							<label >${rollSEG.pctIncrease}</label>
					</td>
				</tr>
				<tr>
					<td >
							<b class="title">Extent最小数量 ：</b>
							<label >${rollSEG.minExtents}</label>
					</td>
					<td >
							<b class="title">Extent最大数量：</b>
							<label >${rollSEG.maxExtents }</label>
					</td>
				</tr>
				<tr>
					<td colspan="2">
							<b class="title">状态：</b>
							<label >${rollSEG.segStatus }</label>
					</td>
				</tr>
		</table>
		<div class="conditionsBtn">
					<c:choose>
						<c:when test="${flag==1}">
							<input class="buttonB" type="button" id="btnClose2" value="关闭" onclick="javascript:$('#popView').window('close');"/>
						</c:when>
						<c:otherwise>						
							<input class="buttonB" type="button" id="btnClose2" value="关闭" onclick="javascript:$('#popWin').window('close');"/>
						</c:otherwise>					
					</c:choose>							
			</div>
						<style>
			  .panel.window{
			       margin-left: -400px;
			       margin-top: -200px;
			       }    
			</style>
			<script>
			   $(function(){
			    $(".panel.window").css({
			       position : "fixed",
			       left:"50%",
			       top:"50%"       
			    });
			   });
			</script>
  </body>
</html>