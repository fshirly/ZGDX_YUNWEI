<%@ page language="java"  pageEncoding="utf-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <link rel="stylesheet" type="text/css" 
    	href="${pageContext.request.contextPath}/style/common/include_css.css" />
<!-- mainframe -->
<script charset="utf-8" language="javascript" type="text/javascript" src="ntkoocx.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/common/jquery-1.9.1.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/common/commonUtil.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/common/Validdiv.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/common/base64.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/plugin/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/style/style_one/js/main.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/platform/demo/testsysuser_list.js"></script>
	

  </head>
  
  <body>
  
  <div class="rightContent">
 
    <div class="conditions" id="divFilter">
    	<p class="conditionOne">
    		用户名：<input type="text" class="inputs" id="txtuserAccount"/>
    	</p>
    	<p class="conditionOne">
    		姓名：<input type="text" class="inputs" id="txtFilterUserName"/>
    	</p>
    	<p>
    		用户类型：<input type="text" id="txtFilterUserType" name="txtFilterUserType" value="${txtFilterUserType}" /> 
    		<!-- <select class="inputs" id="txtFilterUserType" style="width:130px">
    				<option value="-1">请选择</option>
    				<option value="0">管理员</option>
    				<option value="1">企业内IT部门用户</option>
    				<option value="2">企业业务部门用户</option>
    				<option value="3">外部供应商用户</option>
    		</select>-->
    	</p>
    	<p class="conditionOne">
				是否自动锁定：<select id="selFilterIsAutoLock" class="inputs"
					style="width: 120px"><option value="-1">请选择</option>
					<option value="0">是</option>
					<option value="1">否</option>
				</select>
			</p>
			<p class="conditionsBtn">
				<a href="javascript:void(0);" onclick="resetForm('divFilter');"
					class="fltrt">重置</a> <a href="javascript:void(0);"
					onclick="reloadTable();" class="fltrt">查询</a>
			</p>
    </div>
    <div class="datas">
		<table id="tblUser">

		</table>
	</div>
	</div>
	
	<!-- end .datas -->
	<div id="divAddUser" class="easyui-window" minimizable="false"
		closed="true" modal="true" title="用户信息"
		style="width: 600px; height: 275px;">
		<input id="ipt_userid" type="hidden" /><input id="ipt_isautolock"
			type="hidden" /><input id="ipt_status" type="hidden" /> <input
			id="ipt_createtime" type="hidden" /> <input id="ipt_lockedtime"
			type="hidden" /> <input id="ipt_lockedreason" type="hidden" />
		<table id="tblAddUser" cellspacing="13">
			<tr>
				<td>用&nbsp;户&nbsp;名：</td>
				<td><input id="ipt_useraccount" onblur="checkSysUser();" datatype="enAndNum"/><span
					style="color: red; margin-left: 6px;">*</span></td>
				<td>用户姓名：</td>
				<td><input id="ipt_username" datatype="chnStr"/><span
					style="color: red; margin-left: 6px;">*</span></td>
			</tr>
			<tr>
				<td>用户密码：</td>
				<td><input id="ipt_userpassword" /><span
					style="color: red; margin-left: 6px;">*</span></td>
				<td>手机号码：</td>
				<td><input id="ipt_mobilephone" /><span
					style="color: red; margin-left: 6px;">*</span></td>
			</tr>
			<tr>
				<td>邮箱地址：</td>
				<td><input id="ipt_email" /><span
					style="color: red; margin-left: 6px;">*</span></td>
				<td>电话号码：</td>
				<td><input id="ipt_telephone" /></td>
			</tr>
			<tr>
				<td>用户类型：</td>
				<td>
				<!-- 
				<select id="ipt_usertype" style="width: 155px">
						<option value="0">管理员</option>
						<option value="1">企业内IT部门用户</option>
						<option value="2">企业业务部门用户</option>
						<option value="3">外部供应商用户</option>
				</select>
				 -->
				<input type="text" id="ipt_usertype" name="ipt_usertype" value="${ipt_usertype}"/> 
				<span style="color: red; margin-left: 6px;">*</span></td>
			</tr>
			<tr>
				<td colspan="4">
					<p class="formBtn" />
					<p class="formBtn" />
					<p class="formBtn">
						<a href="javascript:void(0);" id="btnUpdate" class="fltrt">重置</a>
						<a href="javascript:void(0);" id="btnSave" class="fltrt">保存</a>
					</p>
				</td>
			</tr>
		</table>
	</div>
	
  </body>
</html>
