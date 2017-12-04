<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %> 
<%@ include file = "../../../common/pageincluded.jsp" %>
<html>
  <head>
  </head>
  <body>
	        <script type="text/javascript" src="${pageContext.request.contextPath}/js/monitor/alarmMgr/alarmactive/alarmActive.js"></script>
			<!--告警详情  -->
			<table class="tableinfo">
				<tr>
					<td colspan="2">
							<b class="title">名称：</b>
							<label >${dataFileBean.dataFileName}</label>
					</td>
				</tr>
				<tr>
					<td colspan="2">
							<b class="title">所属表空间：</b>
							<label >${dataFileBean.tbsName}</label>
					</td>
				</tr>
				<tr>
					<td colspan="2">
							<b class="title">数据库文件：</b>
							<label >${dataFileBean.dataFileName}</label>
					</td>
				</tr>
				<tr>
					<td >
							<b class="title">文件大小：</b>
							<label >${dataFileBean.dataFileBytes}</label>
					</td>
					<td >
							<b class="title">文件最大大小：</b>
							<label >${dataFileBean.maxSize}</label>
					</td>
				</tr>
				<tr>
					<td >
							<b class="title">Block数：</b>
							<label >${dataFileBean.dataFileBlocks}</label>
					</td>
					<td >
							<b class="title">文件最大Block数 ：</b>
							<label >${dataFileBean.maxBlocks}</label>
					</td>
				</tr>
				<tr>
					<td >
							<b class="title">状态：</b>
							<label >${dataFileBean.dataFileStatus }</label>
					</td>
					<td >
							<b class="title">自动扩展标识：</b>
							<label >${dataFileBean.autoExtensible }</label>
					</td>
				</tr>
				<tr>
					<td colspan="2">
							<b class="title">相关文件：</b>
							<label >${dataFileBean.relativeFileName}</label>
					</td>
				</tr>
				<tr>
					<td >
							<b class="title">自动增长粒度：</b>
							<label >${dataFileBean.increamentBlocks}</label>
					</td>
					<td >
							<b class="title">用户数据空间大小：</b>
							<label >${dataFileBean.userBytes}</label>
					</td>
				</tr>
				<tr>
					<td colspan="2">
							<b class="title">用户数据Block数：</b>
							<label >${dataFileBean.userBlocks}</label>
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