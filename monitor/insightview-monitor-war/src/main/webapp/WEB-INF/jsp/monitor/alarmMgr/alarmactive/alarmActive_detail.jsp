<%@ page language="java" pageEncoding="utf-8"%>
 <%@page import="com.fable.insightview.platform.common.entity.SecurityUserInfoBean"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %> 
<%@ include file = "../../../common/pageincluded.jsp" %>
<%SecurityUserInfoBean user = (SecurityUserInfoBean)request.getSession().getAttribute("sysUserInfoBeanOfSession");
String userName =user.getUserAccount(); 
 %>
<html>
  <head>
  </head>
  					<style>
			  .panel.window{
			       margin-left: -400px;
			       margin-top: -280px;
			       }    
			</style>
  <body>
  <style>
      .label{
      width:500px;
        max-height: 100px;
        float: left;
        overflow: hidden;
        word-wrap: break-word;
      }
    
      .label2{
      width:180px;
        max-height: 100px;
        float: left;
        overflow: auto;
        word-wrap: break-word;
      }
    </style>
	        <script type="text/javascript" src="${pageContext.request.contextPath}/js/monitor/alarmMgr/alarmactive/alarmActive.js"></script>
	        <div>
	        <input type="hidden" id="alarmID" name="alarmID" value="${alarmVo.alarmID}"/>
	        <input type="hidden" id="alarmLevel" name="alarmLevel" value="${alarmVo.alarmLevel}"/>
	        <input type="hidden" id="operateStatus" name="operateStatus" value="${alarmVo.alarmOperateStatus}"/>
	        <input type="hidden" id="dispatchID" name="dispatchID" value="${alarmVo.dispatchID}"/>
	        <input type="hidden" id="userName" name="userName" value="<%=userName %>"/>
	        <input type="hidden" id="alarmOrderVersion" name="alarmOrderVersion" value="${alarmOrderVersion }"/> 
	        </div>
			<div id="divAlarmArea" style="overflow-y:auto;height: 330px">
			<!--告警详情  -->
			<table class="tableinfo">
				<tr>
					<td colspan="2">
							<b class="title">告警标题：</b>
							<label id="title">${alarmVo.alarmTitle}</label>
					</td>
				</tr>
				<tr>
					<td >
							<div style='float:left;'><b class="title">告警源IP：</b></div>
							<div class="label2"><label id="sourceMOIPAddress">${alarmVo.sourceMOIPAddress}</label></div>
					</td>
					<td >
							<b class="title">告警源名称 ：</b>
							<label id="sourceMOName">${alarmVo.sourceMOName}</label>
					</td>
				</tr>
				<tr>
					<td >
							<b class="title">告警名称：</b>
							<label >${alarmVo.moName}</label>
					</td>
					<td >
							<b class="title">告警操作状态 ：</b>
							<label id="operateStatusName">${alarmVo.operateStatusName}</label>
					</td>
				</tr>
				<tr>
					<td >
							<b class="title">告警类型：</b>
							<label >${alarmVo.alarmTypeName}</label>
					</td>
					<td >
							<b class="title">告警级别 ：</b>
							<label id="alarmLevelName">${alarmVo.alarmLevelName}</label>
					</td>
				</tr>
				<tr>
					<td >
							<b class="title">发生时间：</b>
							<label id="startTime"><fmt:formatDate value="${alarmVo.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/></label>
					</td>
					<td >
							<b class="title">更新时间：</b>
							<label ><fmt:formatDate value="${alarmVo.lastTime}" pattern="yyyy-MM-dd HH:mm:ss"/></label>
					</td>
				</tr>
				<tr>
					<td >
							<b class="title">重复次数：</b>
							<label >${alarmVo.repeatCount}</label>
					</td>
					<td >
							<b class="title">升级次数：</b>
							<label >${alarmVo.upgradeCount}</label>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<div style='float:left;'><b class="title">告警详细信息：</b></div>
                    	<div class="label">
						<label>${alarmVo.alarmContent}</label>
						</div>
					</td>
				</tr>
				<%-- <tr>
					<td >
							<b class="title">确认人：</b>
							<label >${alarmVo.confirmer}</label>
					</td>
					<td >
							<b class="title">确认时间：</b>
							<label ><fmt:formatDate value="${alarmVo.confirmTime}" pattern="yyyy-MM-dd HH:mm:ss"/></label>
					</td>
				</tr> --%>
				<%-- <tr>
					<td>
							<b class="title">确认信息：</b>
							<label >${alarmVo.confirmInfo}</label>
					</td>
					<!-- 新增（处理人） -->
					<td>
							<b class="title">当前处理人：</b>
							<label >${processMan}</label>
					</td>
				</tr> --%>
				<%-- <tr>
					<td>
							<b class="title">派发人：</b>
							<label >${alarmVo.dispatchUser}</label>
					</td>
					<td >
							<b class="title">派发时间：</b>
							<label ><fmt:formatDate value="${alarmVo.dispatchTime}" pattern="yyyy-MM-dd HH:mm:ss"/></label>
					</td>
				</tr> --%>
				
				<%-- 
							<c:choose>
								
								
								<c:when test="${alarmVo.dispatchID==null}">
									
								</c:when>
								<c:otherwise>	
									<tr>
										<td colspan="2">
											<b class="title">派发信息：</b>					
											<label style="cursor: pointer;" title="查看" onclick="javascript:toQueryInfo(${alarmVo.dispatchID});"> <font color=blue >查看</font></label>
										</td>
									</tr>
									</c:otherwise>
							</c:choose> --%>
							
<!--							<label onclick="javascript:queryInfo()">${alarmVo.dispatchInfo}</label>-->
			
		</table>
		</div>
		<div class="conditionsBtn">
					<c:choose>
						<c:when test="${flag==1}">
							<input class="buttonB" type="button" id="btnClose2" value="关闭" onclick="javascript:$('#popView').window('close');"/>
						</c:when>
						<c:when test="${flag==2}">
						<input class="buttonB" type="button" id="btnClose2" value="确认" onclick="javascript:toBathConfirm();"/>
						<input class="buttonB" type="button" id="btnClose2" value="清除" onclick="javascript:toBathClear();"/>
						<input class="buttonB" type="button" id="btnClose2" value="派单" onclick="javascript:alarmSendSingleShouye.toBathSendSingle();"/>				
						<input class="buttonB" type="button" id="btnClose2" value="关闭" onclick="javascript:closeWind();"/>
						</c:when>
						<c:otherwise>		
						<input class="buttonB" type="button" id="btnClose2" value="关闭" onclick="javascript:$('#popWin').window('close');"/>
						</c:otherwise>					
					</c:choose>							
			</div>
		
  </body>
  	<script>
			   $(function(){
			    $(".panel.window").css({
			       position : "fixed",
			       left:"50%",
			       top:"50%"  
			    });
			   });
			</script>
	
</html>


