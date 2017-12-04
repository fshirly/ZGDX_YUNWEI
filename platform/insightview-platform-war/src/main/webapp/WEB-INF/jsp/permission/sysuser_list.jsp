<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file = "../common/pageincluded.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css"
    href="${pageContext.request.contextPath}/plugin/select2/select2.css" />
<script type="text/javascript"
	src="${pageContext.request.contextPath}/plugin/dTree/dtree.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/plugin/dTree/combo/comboJS.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/permission/sysuserlist.js"></script> 
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/common/commonPlugins.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/plugin/select2/select2.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/permission/LRSelect.js"></script>	

</head>
<body>
		<div class="rightContent">
		<div class="location">当前位置：${navigationBar}</div>
		<div id="dataTreeDiv" class="treetable"></div>
		
		<div class="treetabler">
		<div id="divFilter" class="conditions">
		<form id="frmExport"
				action="${pageContext.request.contextPath}/platform/sysuserExport/exportVal">
				<input name="colNameArrStr" id="iptColName" type="hidden" /> 
				<input name="colTitleArrStr" id="iptTitleName" type="hidden" /> 
				<input name="exlName" id="iptExlName" value="用户列表.xls" type="hidden" />
				<input name="treeType" id="treeType_id" type="hidden" />
			<table>
				<tr>
					<td>
						<b>用户名：</b>
						<input type="text" class="inputs" id="txtFilterUserAccount" name="sysUserBean.userAccount"/>
					</td>
					<td>
						<b>姓名：</b>
						<input type="text" class="inputs" id="txtFilterUserName" name="sysUserBean.userName" 
								validator="{reg:'/[\u4e00-\u9fa5]/',length:'2-20'}"
								msg="{reg:'只能输入汉字!'}" />
					</td>
					<td>
						<b>手机： </b>
						<input type="text" class="inputs" id="txtFilterMobilePhone" name="sysUserBean.mobilePhone" 
								validator="{'default':'phoneNum','length':'1-11'}"
								/>
					</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td>
						<b>状态：</b>
						<select class="easyui-combobox" id="selFilterStatus" name="sysUserBean.status"><option value="-1">请选择</option>
						<option value="1">正常</option>
						<option value="0">锁定</option>
						</select>
					</td>
					<!-- <td>
						<b>用户类型：</b>
						<input type="text" id="selFilterUserType" name="selFilterUserType" value="${selFilterUserType}" /> 
						 
						<select id="selFilterUserType" class="easyui-combobox" name="sysUserBean.userType"><option value="-1">请选择</option>
							<option value="1">IT部门用户</option>
							<option value="2">业务部门用户</option>
							<option value="3">外部供应商用户</option></select> 
					</td> -->
					<td>
						<b>自动锁定： </b>
						<select id="selFilterIsAutoLock" class="easyui-combobox" name="sysUserBean.isAutoLock"><option value="-1">请选择</option>
						<option value="0">是</option>
						<option value="1">否</option>
						</select>
					</td>
					<td class="btntd">
					<a href="javascript:void(0);"
							onclick="reloadTable();" >查询</a>
						<a href="javascript:void(0);" onclick="resetForm('divFilter');"
							>重置</a> 
					</td>
				</tr>
	</table>
	</form>
</div>
		<div class="datas tops3" >
			<input id="ipt_userID" type="hidden" /><input id="ipt_isAutoLock"
			type="hidden" /><input id="ipt_status" type="hidden" /> <input
			id="ipt_createTime" type="hidden" /> <input id="ipt_lockedTime"
			type="hidden" /> <input id="ipt_lockedReason" type="hidden" />
			<input id="ipt_treeType" type="hidden" />
			<input id="flag" type="hidden" />
						<table id="tblSysUser">
	
						</table>
		</div>
		
				<!-- 动态填加产品目录信息 -->
		<div id="divExportDate" class="easyui-window" minimizable="false"
			closed="true" modal="true" title="选择导出列"
			style="width: 600px; height: 400px;">
			<table cellspacing="5" class="tdpad">
				<tr>
					<td style='vertical-align: bottom;'><select id="selLeft"
						multiple="multiple" style="width: 150px; height: 200px"
						class="dataSelect">
							<option alt="userAccount">用户名</option>
							<option alt="userName">用户姓名</option>
							<option alt="mobilePhone">手机号码</option>
							<option alt="telephone">电话号码</option>
							<option alt="email">邮箱地址</option>
							<!-- <option alt="userType">用户类型</option> -->
							<option alt="employeeCode">员工编码</option>
							<option alt="deptName">所属部门</option>
							<!-- <option alt="gradeName">职位级别</option> -->
					</select></td>
					<td style="width: 30px; text-align: center;">
						<button id="img_L_AllTo_R" type="button" style="width: 50px">>>></button>
						<button id="img_L_To_R" type="button" style="width: 50px">></button> <br />
						<button type="button" onclick="upSelectedOption()" style="width: 50px">上移</button><br /> 
						<button type="button" onclick="downSelectedOption()" style="width: 50px">下移</button><br /> 
						<button id="img_R_To_L" type="button" style="width: 50px"><</button> <br />
						<button id="img_R_AllTo_L" type="button" style="width: 50px"><<<</button> 
					</td>
					<td style="vertical-align: bottom;"><select id="selRight"
						multiple="multiple" style="width: 150px; height: 200px"
						class="dataSelect">
					</select></td>
				</tr>
			</table>
			<div class="conditionsBtn">
				<button onclick="doExport()" type="button">导出</button>
			</div>
		</div>
		</div>
		</div>
		
</body>
</html>