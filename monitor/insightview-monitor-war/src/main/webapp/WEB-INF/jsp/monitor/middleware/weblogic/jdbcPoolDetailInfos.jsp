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
									<b class="title"> 可用数量： </b>
									<label  class="input">${jdbcPool.numAvailable}</label>
								</td>
								<td>
									<b class="title"> 当前连接数： </b>
									<label  class="input">${jdbcPool.currCapacity}</label>
								</td>
							</tr>
							<tr>
								<td>
									<b class="title"> 当前活动连接数： </b>
									<label  class="input">${jdbcPool.activeConnectionsCurrent}</label>
								</td>
								<td>
									<b class="title"> 重新连接失败数： </b>
									<label  class="input">${jdbcPool.failuresToReconnect}</label>
								</td>
							</tr>
							<tr>
								<td>
									<b class="title"> 当前等待连接数： </b>
									<label  class="input">${jdbcPool.waitingForConnectionCurrent}</label>
								</td>
								<td>
									<b class="title"> 泄漏连接数： </b>
									<label class="input">${jdbcPool.leakedConnection}</label>
								</td>
							</tr>
							<tr>
								<td>
									<b class="title"> 连接总数： </b>
									<label  class="input">${jdbcPool.connectionsTotal}</label>
								</td>
								<td>
									<b class="title"> 连接池使用率： </b>
									<label  class="input">${jdbcPool.connectionPoolUsage}</label>
								</td>
							</tr>
							
						</table>
						
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
