<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>选择人员</title>
</head>
<body><%--
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/css/base.css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/css/reset.css" />
    <link rel="stylesheet" type="text/css" href="../plugin/easyui/themes/default/easyui.css" />
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/common/commonUtil.js"></script>  
    <script type="text/javascript" src="${pageContext.request.contextPath}/fui/fui.min.js"></script>
    --%>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/fui/themes/default/fui-tree.min.css" />
    <script type="text/javascript" src="${pageContext.request.contextPath}/fui/plugin/fui-tree.min.js"></script>
    <script type="text/javascript">
      var attendanceId=${attendanceId};
    </script>
	<div class="conditions" id="divFilter">
		<table>
			<tr>
				<td>
					<b>用户类型：</b>
					<select id="userType" name="userType">
					  <option value="1">内部员工</option>
					  <option value="2">供应商用户名</option>
					</select>
				</td>
				<td>
					<b>部门/供应商：</b>
                    <input type="text" id="department" name="department" class="input" style="width:200px"/>
				</td>
				<td style="display:none;">
				<b>部门/供应商：</b>
				    <input type="text" id="Supplier" name="Supplier" class="input" style="width:250px"/>
				</td>
			</tr>
			<tr>
				<td>
					<b>姓名：</b>
                    <input type="text" id="name" name="name" class="input"/>
				</td>
				<td class="btntd">
					<a href="javascript:void(0);" onclick="Sys.PersonnelSelection.queryPersonnel();">查询</a>
					<a href="javascript:void(0);" onclick="Sys.PersonnelSelection.resetQueryCondition();">重置</a>
				</td>
			 </tr>
		</table>
	</div> 
	<div class="datas tops2">
		<table id="personalMessageTable"></table>
	</div> 
	<div class="conditionsBtn">
         <a onclick="javascript:Sys.PersonnelSelection.confirmButton();">确定</a> 
         <a onclick="javascript:Sys.PersonnelSelection.cancelButton();">取消</a>
    </div>
 <script src="${pageContext.request.contextPath}/js/platform/setSignIn/personnelSelection.js"></script>
</body>
</html>