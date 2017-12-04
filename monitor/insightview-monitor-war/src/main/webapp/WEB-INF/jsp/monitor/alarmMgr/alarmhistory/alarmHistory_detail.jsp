<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt"%> 
<%@ include file = "../../../common/pageincluded.jsp" %>
<html>
  <head>
  </head>
  <body>
			<!--告警详情  -->
			<table class="tableinfo">
				<tr>
					<td colspan="2">
							<b class="title">告警标题：</b>
							<label >${alarmVo.alarmTitle}</label>
					</td>
				</tr>
				<tr>
					<td >
							<b class="title">告警源IP：</b>
							<label >${alarmVo.sourceMOIPAddress}</label>
					</td>
					<td >
							<b class="title">告警源名称 ：</b>
							<label >${alarmVo.sourceMOName}</label>
					</td>
				</tr>
				<tr>
					<td >
							<b class="title">告警名称：</b>
							<label >${alarmVo.moName}</label>
					</td>
					<td >
							<b class="title">告警状态 ：</b>
							<label >${alarmVo.operateStatusName}</label>
					</td>
				</tr>
				<tr>
					<td >
							<b class="title">告警类型：</b>
							<label >${alarmVo.alarmTypeName}</label>
					</td>
					<td >
							<b class="title">告警级别 ：</b>
							<label >${alarmVo.alarmLevelName}</label>
					</td>
				</tr>
				<tr>
					<td >
							<b class="title">发生时间：</b>
							<label ><fmt:formatDate value="${alarmVo.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/></label>
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
							<b class="title">告警详细信息：</b>
							<label >${alarmVo.alarmContent}</label>
					</td>
				</tr>
				<tr>
					<td >
							<b class="title">确认人：</b>
							<label >${alarmVo.confirmer}</label>
					</td>
					<td >
							<b class="title">确认时间：</b>
							<label ><fmt:formatDate value="${alarmVo.confirmTime}" pattern="yyyy-MM-dd HH:mm:ss"/></label>
					</td>
				</tr>
				<tr>
					<td colspan="2">
							<b class="title">确认信息：</b>
							<label >${alarmVo.confirmInfo}</label>
					</td>
				</tr>
				<tr>
					<td >
							<b class="title">派发人：</b>
							<label >${alarmVo.dispatchUser}</label>
					</td>
					<td >
							<b class="title">派发时间：</b>
							<label ><fmt:formatDate value="${alarmVo.dispatchTime}" pattern="yyyy-MM-dd HH:mm:ss"/></label>
					</td>
				</tr>
				
							<c:choose>
								<c:when test="${alarmVo.dispatchID==null}">
									
								</c:when>
								<c:otherwise>	
								<tr>
								<td colspan="2">
									<b class="title">派发信息：</b>					
									<label style="cursor: pointer;" title="查看" onclick="javascript:toQueryInfo(${alarmVo.dispatchID});"><font color=blue >查看</font></label>
									</td>
								</tr>
								</c:otherwise>					
							</c:choose>	
				<tr>
					<td >
							<b class="title">清除人：</b>
							<label >${alarmVo.cleaner}</label>
					</td>
					<td >
							<b class="title">清除时间：</b>
							<label ><fmt:formatDate value="${alarmVo.cleanTime}" pattern="yyyy-MM-dd HH:mm:ss"/></label>
					</td>
				</tr>
				<tr>
					<td colspan="2">
							<b class="title">清除信息：</b>
							<label >${alarmVo.cleanInfo}</label>
					</td>
				</tr>
				<!-- <tr>
					<td >
							<b class="title">删除人：</b>
							<label >${alarmVo.deletedUser}</label>
					</td>
					<td >
							<b class="title">删除时间：</b>
							<label ><fmt:formatDate value="${alarmVo.deleteTime}" pattern="yyyy-MM-dd HH:mm:ss"/></label>
					</td>
				</tr> -->
		</table>
		<div class="conditionsBtn">
					<input class="buttonB" type="button" id="btnClose2" value="关闭" onclick="javascript:$('#popWin').window('close');"/>		
			</div>
  </body>
</html>