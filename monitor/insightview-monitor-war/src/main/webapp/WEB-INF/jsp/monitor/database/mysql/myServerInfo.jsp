<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %> 
<%@ include file="../../../common/pageincluded.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	</head>

	<body>
	
		<table  style='width:100%;'>
			<tr>
				<td>
					<div class="easyui-panel" >
						<table id="tblInstanceDetail" class="tableinfo2" >
							<tr>
								<td>
									<b class="title"> 数据库服务名称： </b>
									<label class="input">${my.moName}</label>
								</td>
								<td>
									<b class="title"> 数据库服务别名： </b>
									<label  class="input">${my.moAlias}</label>
								</td>
							</tr>
							<tr>
								<td>
									<b class="title"> 所属主机： </b>
									<label  class="input">${my.deviceIP}</label>
								</td>
								<td>
									<b class="title"> 服务器字符集： </b>
									<label  class="input">${my.serverCharset}</label>
								</td>
							</tr>
							<tr>
								<td>
									<b class="title"> IP地址： </b>
									<label  class="input">${my.ip}</label>
								</td>
								<td>
									<b class="title"> 端口号： </b>
									<label  class="input">${my.port}</label>
								</td>
							</tr>
							<tr>
								<td>
									<b class="title"> 告警级别： </b>
									<img alt="0" src="${pageContext.request.contextPath}/style/images/levelIcon/${my.levelIcon}" border="0"/>${my.alarmLevelName}
								</td>
								<td>
									<b class="title"> 可用/持续时间： </b>
										<img title="${my.operaTip}" src="${pageContext.request.contextPath}/style/images/levelIcon/${my.operStatus}" border="0"/>${my.durationTime}
								</td>
							</tr>
							<tr>
								<td colspan="2">
									<b class="title"> 启动时间： </b>
									<label  class="input"><fmt:formatDate value="${my.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/></label>
									<b class="title"> 创建时间： </b>
									<label  class="input"><fmt:formatDate value="${my.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></label>
									<b class="title"> 修改时间： </b>
									<label  class="input"><fmt:formatDate value="${my.lastUpdateTime}" pattern="yyyy-MM-dd HH:mm:ss"/></label>
								</td>
							</tr>
							<tr>
								<td colspan="2">
									<b class="title"> 安装路径： </b>
									<label  class="input">${my.installPath}</label>
								</td>
							</tr>
							<tr>
								<td colspan="2">
									<b class="title"> 数据路径： </b>
									<label  class="input">${my.dataPath}</label>
								</td>
							</tr>
							<tr>
								<td colspan="2">
									<b class="title"> 数据库个数： </b>
									<label  class="input">${my.dbNum}</label>
								</td>
								<!--<td>
									<b class="title"> 修改人： </b>
									<label class="input">${my.updateBy}</label>
								</td>-->
							</tr>
							</table>
					</div>
				</td>
			</tr>
		</table>
		<c:if test="${!empty flag}">
		<div class="conditionsBtn">												
				<input class="buttonB" type="button" id="btnClose2" value="关闭" onclick="javascript:$('#popWin').window('close');"/>
		</div>
		</c:if>
	</body>
	
</html>
