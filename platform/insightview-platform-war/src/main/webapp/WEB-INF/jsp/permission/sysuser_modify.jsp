<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file = "../common/pageincluded.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
	
  </head>
  
  <body>
    <script type="text/javascript"
        src="${pageContext.request.contextPath}/js/permission/LRSelect.js"></script>
  	<script type="text/javascript"
	   src="${pageContext.request.contextPath}/js/permission/sysusermodify.js"></script>
	   
	<%
		String userId=(String)request.getAttribute("userId");
		String isAdmin =(String)request.getAttribute("isAdmin");
		String isSameOrg =(String)request.getAttribute("isSameOrg");
		String currentUserOrgId =(String)request.getAttribute("currentUserOrgId");
    %>    
    	<!-- 编辑用户 -->
	<div id="divModifyUser">
		<input id="ipt_userID" type="hidden" value="<%= userId%>"/><input id="ipt_isAutoLock"
			type="hidden" /><input id="ipt_status" type="hidden" /> <input
			id="ipt_createTime" type="hidden" /> <input id="ipt_lockedTime"
			type="hidden" /> 
			<input id="ipt_treeType" type="hidden" />
			<input id="flag" type="hidden" />
		<input type="hidden" id="isAdmin" value="<%= isAdmin%>"/>
		<input type="hidden" id="isSameOrg" value="<%= isSameOrg%>"/>
		<input type="hidden" id="currentUserOrgId" value="<%= currentUserOrgId%>"/>
		
		<div id="sysUserTabs" class="easyui-tabs">
            <div title="用户信息" id="userInfoTab">
				<table id="tblModifyUser" class="formtable">
					<tr>
						<td class="title">帐号：</td>
						<td>
						<input id="userAccountHide" type="hidden">
						<input id="ipt_userAccount" class="inputVal"
							 validator="{'default':'enAndNum','length':'1-20'}" msg="{'reg':'只能输入1-20位数字或字母！'}"/><b>*</b></td>
						<td class="title">用户姓名：</td>
						<td><input id="ipt_userName" class="inputVal" validator="{'length':'2-20'}" msg="{reg:'只能输入2-20位汉字和字母！'}"/><b>*</b></td>
					</tr>
					<tr>
						<td class="title">用户密码：</td>
						<td><input id="ipt_userPassword" type="password" class="inputVal" validator="{'default':'pwd','length':'6-18'}"  /><b>*</b></td>
						<td class="title">确认密码：</td>
						<td><input id="confirmPassword" type="password" class="inputVal" validator="{'default':'pwd','length':'6-18'}"  onblur="checkConfirmPwd();"/><b>*</b></td>
						
					</tr>
					<tr>
						<td class="title">手机号码：</td>
						<td><input id="ipt_mobilePhone" class="inputVal" validator="{'default':'phoneNum','length':'0-11'}"/></td>
						<td class="title">电话号码：</td>
						<td><input id="ipt_telephone" class="inputVal" validator="{'default':'phoneAndTelNum'}"/></td>
					</tr>
					<tr>
						<td class="title">邮箱地址：</td>
						<td><input id="ipt_email" class="inputVal" validator="{'default':'email'}" /></td>
						<td class="title">身份证号码：</td>
                    	<td><input id="ipt_idcard" class="inputVal" validator="{'default':'IDCard'}"/></td>
					</tr>
					<!-- <tr>
					<td class="title">用户类型：</td>
					<td colspan="3">
					<input type="hidden" id="ipt_userType"/>
					<input type="radio" id="ipt_userType1" name="userType" checked value="1"  onclick="javascript:edit();"/>IT部门用户&nbsp;
					<input type="radio" id="ipt_userType2" name="userType" value="2" onclick="javascript:edit();"/>业务部门用户&nbsp;
					<input type="radio" id="ipt_userType3" name="userType" value="3" onclick="javascript:edit();"/>外部供应商用户&nbsp;
					</td>
					</tr> -->
					<tr id="employeeInfos" style="display:'none'">
						<td class="title">员工编码：</td>
						<td><input id="ipt_employeeCode" class="inputVal" validator="{'default':'*'}"/><input id="ipt_empId" class="inputV1" type="hidden"/><b>*</b></td>
						<td class="title">所属部门：</td>
						<td><input id="ipt_deptName" />
						<input id="ipt_deptId" type="hidden" /><input id="ipt_organizationID" type="hidden" /><b>*</b></td>
					</tr>
					<!-- <tr id="employeeGrade" style="display:'none';"> 
						<td class="title">职位级别：</td>
						<td><select id="ipt_gradeID" class="inputVal" >
							</select><input id="ipt_gradeName" type="hidden"/><input id="ipt_gradeId" type="hidden"/><b>*</b></td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
					</tr> -->
					<!-- <tr id="providerInfos" style="display:'none';">
						<td class="title">所属供应商：</td>
						<td><input id="ipt_providerName" class="inputVal" validator="{'default':'*'}" readonly onfocus="doChoseProvider();"/>
						<input id="ipt_providerId" type="hidden"/><input id="ipt_proUserId" type="hidden"/><b>*</b></td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
					</tr> -->
				</table>
		    </div>
<!-- 	        <div title="岗位信息" id="postInfoTab">
	            <table class="formtable1">
	               <tr>
	                  <td>
	                                         岗位名称：
	                  </td>
	                  <td></td>
	                  <td>
	                                          已添加岗位：
	                  </td>
	               </tr>
	               <tr>
	                   <td style='vertical-align: bottom;'>
	                       <select id="selLeft" multiple="multiple"
	                           style="width: 205px; height: 200px" class="dataSelect">
	                       </select>
	                   </td>
	                   <td style="width: 30px; text-align: center;">
	                       <button id="img_L_AllTo_R">
	                           >>>
	                       </button>
	                       <button id="img_L_To_R">
	                           &nbsp;&nbsp;>&nbsp;&nbsp;
	                       </button>
	                       <br />
	                       <button id="img_R_To_L">
	                           &nbsp;&nbsp;<&nbsp;&nbsp;
	                       </button>
	                       <br />
	                       <button id="img_R_AllTo_L">
	                           <<<
	                       </button>
	                       <br />
	                   </td>
	                   <td style="vertical-align: bottom;">
	                       <select id="selRight" multiple="multiple"
	                           style="width: 205px; height: 200px" class="dataSelect">
	                       </select>
	                   </td>
	               </tr>
	            </table> 
	        </div> -->
	    </div>
		<div class="conditionsBtn">
			<a href="javascript:void(0);" onclick="javascript:doUpdate();" id="btnSave" >确定</a>
			<a href="javascript:void(0);" onclick="javascript:$('#popWin').window('close');" id="btnBack" >取消</a>
			
		</div>
	</div>
		<!-- <div id="divChoseDept" class="easyui-window" collapsible="false" minimizable="false" maximizable="false"
			closed="true" modal="true" title="选择所属部门"
			style="width: 300px; height: 300px;">
			<div id="dataTreeDepts" class="dtree"
				style="width: 100%; height: 200px;"></div>
		</div> -->
		<!-- <div id="divChoseDeptGrade" class="easyui-window" collapsible="false" minimizable="false" maximizable="false"
			closed="true" modal="true" title="选择职位级别"
			style="width: 300px; height: 300px;">
			<div id="dataTreeDeptGrades" class="dtree"
				style="width: 100%; height: 200px;"></div>
		</div>
		<div id="divChoseProvider" class="easyui-window" collapsible="false" minimizable="false" maximizable="false"
			closed="true" modal="true" title="选择供应商"
			style="width: 300px; height: 300px;">
			<div id="dataTreeProviders" class="dtree"
				style="width: 100%; height: 200px;"></div>
		</div> -->
  </body>
</html>
