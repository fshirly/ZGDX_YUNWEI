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
							<td><b class="title"> 事物总数： </b> <label class="input">${weblogic.transTotal}</label>
							</td>
							<td><b class="title"> 放弃事务数： </b> <label class="input">${weblogic.transAbandonedTotal}</label>
							</td>
						</tr>
						<tr>
							<td><b class="title"> 活动事务数： </b> <label class="input">${weblogic.activeTransTotal}</label>
							</td>
							<td><b class="title"> 启发式事务数： </b> <label class="input">${weblogic.transHeuristicsTotal}</label>
							</td>
						</tr>
						<tr>
							<td><b class="title"> 已提交事务数： </b> <label class="input">${weblogic.transCommittedTotal}</label>
							</td>
							<td><b class="title"> 已提交无资源事务数： </b> <label class="input">${weblogic.transNoResCommittedTotal}</label>
							</td>
						</tr>
						<tr>
							<td><b class="title"> 已提交LLR事务数： </b> <label class="input">${weblogic.transLLRCommitedTotal}</label>
							</td>
							<td><b class="title"> 一阶段提交只读事务数： </b> <label class="input">${weblogic.transReadOnePhaseTotal}</label>
							</td>
						</tr>
						<tr>
							<td><b class="title"> 一阶段提交单资源事务数： </b> <label class="input">${weblogic.transOneResOnePhaseTotal}</label>
							</td>
							<td><b class="title"> 两阶段提交事务数： </b> <label class="input">${weblogic.transTwoPhaseTotal}</label>
							</td>
						</tr>
						<tr>
							<td><b class="title"> 回滚事务数： </b> <label class="input">${weblogic.transRollBackTotal}</label>
							</td>
							<td><b class="title"> 系统错误导致回滚数： </b> <label class="input">${weblogic.transRollBackSysTotal}</label>
							</td>
						</tr>
						<tr>
							<td><b class="title"> 应用程序错误导致回滚数： </b> <label class="input">${weblogic.transRollBackAppTotal}</label>
							</td>
							<td><b class="title">超时导致回滚数： </b> <label class="input">${weblogic.transRollBackTimeoutTotal}</label>
							</td>
						</tr>

					</table>
				</div>
			</td>
		</tr>
	</table>

</body>

</html>
