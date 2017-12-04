<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file = "../../common/pageincluded.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<title>管理域管理</title>

<script type="text/javascript"
	src="${pageContext.request.contextPath}/plugin/dTree/dtree.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/plugin/dTree/combo/comboJS.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/platform/managedDomain/managedDomainView.js"></script>
</head>
<body>
	<div class="location">当前位置：${navigationBar}</div>
	<div id="dataTreeDiv" class="treetable">
	
	</div>
	<div class="treetabler">
	<div class="conditions">
		<table>
		<tr>
			<td>
				<b>管理域名：</b>
				<input type="text" class="inputs" id="domainName" />
			</td>
			<td>
				<b>所属单位：</b>
				 <input id="organizationName" class="inputs"
								onClick='showTree(this,"addressFatherId")' readonly="readonly"
										alt="-1" />
				 <div id="combdtree" class="dtreecob" style="margin-left:71px;-webkit-margin-start: 71px;margin:0px\9;">
						<div id="dataOrgTreeDiv" class="dtree"
							style="width: 100%; height: 200px; overflow: auto;"></div>
							<div class="dBottom">
								<a href="javascript:hiddenDTree();">关闭</a>
							</div>
					</div>
			</td>
			<td class="btntd">
				<a href="javascript:reloadTable();" >查询</a>
				<a href="javascript:resetForm();" >重置</a> 
			</td>
		</tr>
		</table>
	</div>
	<div class="datas tops1" >
	<input id="ipt_parentId" type="hidden"/>
						<table id="tblManagedDomain">

						</table>
					</div>
	</div>
</body>
</html>
