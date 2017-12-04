<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %> 
<%@ include file="../../../common/pageincluded.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	</head>

	<body>
	
		<table style='width:100%;'>
			<tr>
				<td>
					<div class="easyui-panel" >
						<table id="tblInstanceDetail" class="tableinfo2" title="数据库基本信息">
							<tr>
								<td>
									<b class="title"> 内部标识号： </b>
									<label id="lbl_moname" class="input">${ms.databaseID}</label>
								</td>
								<td>
									<b class="title"> 数据库名称： </b>
									<label id="lbl_moname" class="input">${ms.databaseName}</label>
								</td>
							</tr>
							<tr>
							<td>
									<b class="title"> 数据库属主： </b>
									<label id="lbl_deviceip" class="input">${ms.databaseOwner}</label>
								</td>
								<td>
									<b class="title"> 页大小： </b>
									<label id="lbl_moname" class="input">${ms.pageSize}</label>
								</td>
							</tr>
							<tr>
								<td>
									<b class="title"> 总空间大小： </b>
									<label id="lbl_rescategoryname" class="input">${ms.totalSize}</label>
								</td>
								<td>
									<b class="title"> 日志空间使用率： </b>
									<label id="lbl_moname" class="input">${ms.spaceUsage}</label>
								</td>
							</tr>
							<tr>
							<td>
									<b class="title"> 已用空间： </b><label id="lbl_rescategoryname" class="input">${ms.usedSize}</label>
								</td>
								<td>
									<b class="title"> 空闲空间： </b><label id="lbl_rescategoryname" class="input">${ms.freeSize}</label>
								</td>
							</tr>
							<tr>
								
								<td colspan="2">
									<b class="title"> 创建时间： </b>
									<label id="lbl_lastupdatetime" class="input"><fmt:formatDate value="${ms.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></label>
								</td>
							</tr>
								<tr>
								<td colspan="2">
									<b class="title"> 数据库选项： </b>
									<label id="lbl_moname" class="input">${ms.databaseOptions}</label>
								</td>
							</tr>
						</table>
					</div>
				</td>
			</tr>
		</table>
		
	</body>
	
</html>
