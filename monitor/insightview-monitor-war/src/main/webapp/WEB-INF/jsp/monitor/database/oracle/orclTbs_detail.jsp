<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %> 
<%@ include file = "../../../common/pageincluded.jsp" %>
<html>
  <head>
  </head>
  <body>
			<table class="tableinfo">
				<tr>
					<td>
							<b class="title">名称：</b>
							<label >${tbsBean.tbsname}</label>
					</td>
						<td>
							<b class="title">Extent缺省增长：</b>
							<label >${tbsBean.nextextent}</label>
					</td>
				</tr>
				<tr>
					<td>
							<b class="title">Extent最大数量：</b>
							<label >${tbsBean.maxextents}</label>
					</td>
						<td>
							<b class="title">Extend最小数量：</b>
							<label >${tbsBean.minextents}</label>
					</td>
				</tr>
				<tr>
					<td>
							<b class="title">最小Extent空间：</b>
							<label >${tbsBean.minextlen}</label>
					</td>
						<td>
							<b class="title">Extent初始大小：</b>
							<label >${tbsBean.initialextent}</label>
					</td>
				</tr>
				<tr>
					<td >
							<b class="title">Extent缺省增长百分比：</b>
							<label >${tbsBean.pctextents}</label>
					</td>
					<td >
							<b class="title">表空间类型：</b>
							<label >${tbsBean.tbstype}</label>
					</td>
				</tr>
				<tr>
					<td >
							<b class="title">表空间状态：</b>
							<label >${tbsBean.tbsstatus}</label>
					</td>
					<td >
							<b class="title">分配类型 ：</b>
							<label >${tbsBean.alloctype}</label>
					</td>
				</tr>
				<tr>
					<td colspan="2">
							<b class="title">日志属性：</b>
							<label >${tbsBean.logattr}</label>
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