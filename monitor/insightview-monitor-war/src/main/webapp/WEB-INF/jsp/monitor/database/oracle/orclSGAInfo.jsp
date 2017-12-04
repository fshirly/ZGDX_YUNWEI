<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="../../../common/pageincluded.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	</head>

	<body>
	
		<table style='width:100%;'>
			<tr>
				<td>
					<div class="easyui-panel" >
						<table id="tblInstanceDetail" class="tableinfo2"><!--
							<tr>
								<td colspan="2">
									<b class="title"> SGA标识： </b>
									<label id="lbl_moName" class="input">${sga.moName}</label>
								</td>
							</tr>
							--><tr>
								<td>
									<b class="title"> SGA大小： </b>
									<label id="lbl_totalSize" class="input">${sga.totalSize}</label>
								</td>
								<td>
									<b class="title"> 固定区大小： </b>
									<label id="lbl_fixedSize" class="input">${sga.fixedSize}</label>
								</td>
							</tr>
							<tr>
								<td>
									<b class="title"> 数据缓冲区： </b>
									<label id="lbl_bufferSize" class="input">${sga.bufferSize}</label>
								</td>
								<td>
									<b class="title"> 重做日志缓存： </b>
									<label id="lbl_redologBuf" class="input">${sga.redologBuf}</label>
								</td>
							</tr>
							<tr>
								<td>
									<b class="title"> Pool大小： </b>
									<label id="lbl_poolSize" class="input">${sga.poolSize}</label>
								</td>
								<td>
									<b class="title"> 共享池大小： </b>
									<label id="lbl_sharedPool" class="input">${sga.sharedPool}</label>
								</td>
							</tr>
							<tr>
								<td>
									<b class="title"> 大池大小： </b>
									<label id="lbl_largePool" class="input">${sga.largePool}</label>
								</td>
								<td>
									<b class="title"> Java池大小： </b>
									<label id="lbl_javaPool" class="input">${sga.javaPool}</label>
								</td>
							</tr>
							<tr>
								<td>
									<b class="title"> 流池大小： </b>
									<label id="lbl_streamPool" class="input">${sga.streamPool}</label>
								</td>
								<td>
									<b class="title"> 库缓存： </b>
									<label id="lbl_libraryCache" class="input">${sga.libraryCache}</label>
								</td>
							</tr>
								<tr>
								<td colspan="2">
									<b class="title"> 数据字典缓存： </b>
									<label id="lbl_dicaCache" class="input">${sga.dicaCache}</label>
								</td>
							</tr>
						</table>
					</div>
				</td>
			</tr>
		</table>
		<c:choose>
				<c:when test="${openFlag==1}">
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
				</c:when>
		</c:choose>	
		
		
	</body>
	
</html>
