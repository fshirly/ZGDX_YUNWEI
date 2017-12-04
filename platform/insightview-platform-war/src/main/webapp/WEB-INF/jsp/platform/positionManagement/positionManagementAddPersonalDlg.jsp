<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>人员选择</title>
</head>
<body>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/css/base.css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/css/reset.css" />
    <link rel="stylesheet" type="text/css" href="../plugin/easyui/themes/default/easyui.css" />
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/common/commonUtil.js"></script>  
    <div class="winbox" style="height:395px;"> 
	<div class="conditions" id="divFilter">
	<input type="hidden" value="${id}" id="parentPostID" />
		<table>
			<tr>
				<td>
					<b>工号：</b>
					<input type="text" id="employeeCode" name="employeeCode" class="input" />
				</td>
				<td>
					<b>姓名：</b>
                    <input type="text" id="userName" name="userName" class="input"/>
				</td>
				<td class="btntd">
					<a href="javascript:void(0);" onclick="System.PostMgmDlg.addPersonalPopWin.reloadTable();">查询</a>
					<a href="javascript:void(0);" onclick="System.PostMgmDlg.addPersonalPopWin.resetQueryCondition();">重置</a>
				</td>
			</tr>
		</table>
	</div> 
	<div class="datas tops1">
		<table id="positionMessage_AddPersonalTable"></table>
	</div> 
	</div>
	<div class="conditionsBtn">
         <a onclick="javascript:System.PostMgmDlg.addPersonalPopWin.addPersonalToPosition();">确定</a> 
         <a onclick="javascript:$('#popWin2').window('close');">取消</a>
    </div>
	<script>
        $(function(){
        	System.PostMgmDlg.addPersonalPopWin.initDatagrid();
        });
    </script> 
</body>
</html>