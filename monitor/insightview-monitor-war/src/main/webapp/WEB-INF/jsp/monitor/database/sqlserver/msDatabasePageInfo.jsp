<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%@ include file="../../../common/pageincluded.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/monitor/host/commHref.js"></script>
</head>

<body>

	<table style='width:100%;'>
		<tr>
			<td>
				<div class="easyui-panel" style='height:300px;overflow-y:hidden;'>
					<table id="tblInstanceDetail" class="tableinfo2">
						<tr>
							<td><b class="title"> 每秒刷新到磁盘的页数： </b> <label class="input">${db.checkpointPages}</label>
							</td>
							<td><b class="title"> 每秒必须等待可用页的请求次数： </b> <label class="input">${db.freeListStalls}</label>
							</td>
						</tr>
						<tr>
							<td><b class="title"> 每秒延迟写次数： </b> <label class="input">${db.lazyWrites}</label>
							</td>
							<td><b class="title"> 每秒物理数据库页读次数： </b> <label class="input">${db.pageReads}</label>
							</td>
						</tr>
						<tr>
							<td><b class="title"> 每秒物理数据库页写次数： </b> <label class="input">${db.pageWrites}</label>
							</td>
							<td><b class="title"> 每秒查找页的请求数： </b> <label class="input">${db.pageLookups}</label>
							</td>
						</tr>
					</table>
				</div>
			</td>
		</tr>
	</table>

</body>

</html>
