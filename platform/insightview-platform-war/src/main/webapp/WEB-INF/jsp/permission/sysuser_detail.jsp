<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file = "../common/pageincluded.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
	 
  </head>
  
  <body>
  	<style>
	      .label{
	      width:500px;
	        max-height: 100px;
	        float: left;
	        overflow: auto;
	        word-wrap: break-word;
	      }
	       .label2{
	      	width:180px;
	        max-height: 100px;
	        float: left;
	        overflow: auto;
	        word-wrap: break-word;
	      }
	    </style>
  	<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/permission/sysuserdetail.js"></script> 
	<%
	String userId=(String)request.getAttribute("userId");
    %>
    	<!-- 编辑用户 -->
			<!-- 用户详情 -->
	<div id="divUserInfo">
		<input id="ipt_userID" type="hidden" value="<%= userId%>"/>		
		<div id="sysUserTabs" class="easyui-tabs" style="height:310px;overflow:auto;">
	        <div title="用户信息" id="userInfoTab">
				<table id="tblUserInfo" class="tableinfo" >
					<tr>
						<td>
							<div style='float:left;'><b class="title">帐号：</b></div>
							<div class="label2"><label id="lbl_userAccount" class="inputVal"></label></div>
						</td>
						<td>
							<div style='float:left;'><b class="title">用户姓名：</b></div>
							<div class="label2"><label id="lbl_userName" class="inputVal" ></label></div>
						</td>
					</tr>
					<tr>
						<td>
							<div style='float:left;'><b class="title">手机号码：</b></div>
							<div class="label2"><label id="lbl_mobilePhone" class="inputVal" ></label></div>
						</td>
						<td>
							<div style='float:left;'><b class="title">电话号码：</b></div>
							<div class="label2"><label id="lbl_telephone" class="inputVal"></label></div>
						</td>
				   </tr>
					<tr>
						<td>
							<div style='float:left;'><b class="title">邮箱地址：</b></div>
							<div class="label2"><label id="lbl_email" class="inputVal" ></label></div>
						</td>
						<td>
							<div style='float:left;'><b class="title">身份证号码：</b></div>
							<div class="label2"><label id="lbl_idcard" class="inputVal" ></label></div>
						</td>
					</tr>
					<tr id="isShow1">
						<td>
							<div style='float:left;'><b class="title">用户类型：</b></div>
							<div class="label2"><label id="lbl_userType" class="inputVal"></label></div>
						</td>
						<td>
							<div style='float:left;'><b class="title">员工编码：</b></div>
							<div class="label2"><label id="lbl_employeeCode" class="inputVal" ></label></div>
						</td>
					</tr>
					<tr id="isShow2">
						<td colspan="2">
							<div style='float:left;'><b class="title">所属部门：</b></div>
							<div ><label id="lbl_deptName" class="inputVal"></label></div>
						</td>
						<!-- <td>
							<div style='float:left;'><b class="title">职位级别：</b></div>
							<div class="label2"><label id="lbl_gradeName" class="inputVal"></label></div>
						</td> -->
					</tr>
					<!-- <tr id="isShow3">
						<td>
							<div style='float:left;'><b class="title">用户类型：</b></div>
							<div class="label2"><label id="lbl_userType2" class="inputVal"></label></div>
						</td>
						<td>
							<div style='float:left;'><b class="title">所属供应商：</b></div>
							<div class="label2"><label id="lbl_providerName" class="inputVal"  ></label></div>
						</td>
					</tr> -->
				</table>
			</div>
	       <!--  <div title="岗位信息" id="postInfoTab">
	            <table class="formtable1">
	               <tr>
	                  <td>
	                                          已添加岗位：
	                  </td>
	               </tr>
	               <tr>
	                   <td>
	                       <select id="selRight" multiple="multiple" style="width: 500px; height: 200px">
	                       </select>
	                   </td>
	               </tr>
	            </table> 
	        </div> -->
        </div>
		<div class="conditionsBtn">
			<a href="javascript:void(0);" onclick="javascript:$('#popWin').window('close');" id="btnBackInfo" >关闭</a>
		</div>
	</div>
  </body>
</html>
