<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <body>
    <script type="text/javascript" 
        src="${pageContext.request.contextPath}/js/common/fileUtil.js"></script>   
    <script type="text/javascript"
        src="${pageContext.request.contextPath}/js/permission/personal_info.js"></script> 
    <script type="text/javascript"
       src="${pageContext.request.contextPath}/js/permission/certificateList.js"></script>
       <style>
       .tableinfo td{
         border-bottom:0;
         padding-bottom: 3px;
       }
       </style>
       
    <div style="position: absolute;margin-left: 88%; margin-top: -6px;z-index: 10;" onclick="editResume(${userId});"><a href="javascript:void(0);">个人简历维护</a></div>

    <div id="personalInfoTabs" class="easyui-tabs" data-options="tabPosition:'left'">
	    <div  title="用户信息" id="userInfoTab" >
	         <input id="userId" type="hidden" value="${userId}"/>
	        
	         <table id="tblUserInfo" class="formtable" style="margin:32px 37px 0 0 ;">
	             <tr>
	                 <td class="title">账号：</td>
	                 <td><label id="lbl_userAccount" class="inputVal"></label></td>
	                 <td  class="title">姓名：</td>
	                 <td>
	                     <label id="lbl_userName" class="inputVal" ></label>
	                 </td>
	             </tr>
	             <tr>
	                 <td class="title">手机号码：</td>
	                 <td>
	                     <input id="ipt_mobilePhone" class="inputVal" validator="{'default':'phoneNum','length':'0-11'}"/>
	                 </td>
	                  <td class="title">电话号码：</td>
	                  <td>
	                     <input id="ipt_telephone" class="inputVal" validator="{'default':'phoneAndTelNum'}"/>
	                 </td>
	             </tr>
	             <tr>
	                 <td class="title">邮箱地址：</td>
	                 <td>
	                     <input id="ipt_email" class="inputVal" validator="{'default':'email'}" />
	                 </td>
	                 <td></td>
	             </tr>
	             <tr>
	                 <td class="title">用户类型：</td>
	                 <td>
	                     <label id="lbl_userType" class="inputVal"></label>
	                 </td>
	                  <td class="title">员工编码：</td>
	                  <td>
	                     <label id="lbl_employeeCode" class="inputVal" ></label>
	                 </td>
	             </tr>
	             <tr>
	                 <td class="title">所属部门：</td>
	                 <td>
	                     <label id="lbl_deptName" class="inputVal"></label>
	                 </td>
	                  <td class="title">职位级别：</td>
	                  <td>
	                     <label id="lbl_gradeName" class="inputVal"></label>
	                 </td>
	             </tr>
	         </table>
	         <div class="conditionsBtn">
	             <a onclick="javascript:savePersonalInfo();"" >保存</a>
	             <a onclick="javascript:$('#popWin').window('close');"" >取消</a>
	         </div>
	     </div>
	     <div  title="资质证书" id="certificateListTab">
	         <table id="tblCertificateList"/>
	         
             <div class="conditionsBtn">
               <a onclick="javascript:$('#popWin').window('close');">关闭</a>
             </div>
	     </div>
	  </div>         
  </body>
</html>
