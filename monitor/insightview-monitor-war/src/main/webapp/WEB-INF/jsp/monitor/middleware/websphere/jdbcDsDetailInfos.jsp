<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %> 
<%@ include file="../../../common/pageincluded.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	</head>

	<body>
	
		<table>
			<tr>
				<td>
					<div class="easyui-panel" >
						<table id="tblJdbcDsInfos" class="tableinfo">
							<tr>
								<td>
									<b class="title"> 名称： </b>
									<label id="lbl_dsName" class="input">${jdbcDs.dsName}</label>
								</td>
								<td>
									<b class="title"> JDBC驱动： </b>
									<label id="lbl_jdbcDriver" class="input">${jdbcDs.jdbcDriver}</label>
								</td>
							</tr>
							<tr>
								<td>
									<b class="title"> 数据库连接字符串： </b>
									<label id="lbl_dbUrl" class="input">${jdbcDs.dbUrl}</label>
								</td>
								<td>
									<b class="title"> 用户名： </b>
									<label id="lbl_userName" class="input">${jdbcDs.userName}</label>
								</td>
							</tr>
							<tr>
								<td>
									<b class="title"> 密码： </b>
									<label id="lbl_passWord" class="input">${jdbcDs.passWord}</label>
								</td>
								<td>
									<b class="title"> 初始大小： </b>
									<label id="lbl_initialSize" class="input">${jdbcDs.initialSize}</label>
								</td>
							</tr>
							<tr>
								<td>
									<b class="title"> 最大连接数： </b>
									<label id="lbl_maxActive" class="input">${jdbcDs.maxActive}</label>
								</td>
								<td>
									<b class="title"> 最小空闲连接数： </b>
									<label id="lbl_minIdle" class="input">${jdbcDs.minIdle}</label>
								</td>
							</tr>
							<tr>
								<td>
									<b class="title"> 最大空闲连接数： </b>
									<label id="lbl_maxIdle" class="input">${jdbcDs.maxIdle}</label>
								</td>
								<td>
									<b class="title"> 最长等待时间： </b>
									<label id="lbl_maxWait" class="input">${jdbcDs.maxWait}</label>
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
					</div>
					
				</td>
			</tr>
		</table>
		
	</body>
	
</html>
