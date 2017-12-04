<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>个人简历</title>
</head>
    <body>
	    <link rel="stylesheet" type="text/css"
	       href="${pageContext.request.contextPath}/style/css/base.css" />
	    <link rel="stylesheet" type="text/css"
	       href="${pageContext.request.contextPath}/style/css/reset.css" />
           
	   <script type="text/javascript" 
           src="${pageContext.request.contextPath}/fui/plugin/fui-fileupload.js"></script>
	    <script type="text/javascript" 
	       src="${pageContext.request.contextPath}/js/common/commonUtil.js"></script>
        <script type="text/javascript"
           src="${pageContext.request.contextPath}/js/permission/resume.js"></script>
       
	   <div id="resumeTabs" class="easyui-tabs" data-options="tabPosition:'left'">
          <div  title="基本信息" id="userInfoTab" >
          <div style="height:520px;overflow-y:auto;">
			<form id="resumeForm">
			    <input type="hidden" id="resumeID" name="resumeID" value="${userResume.resumeID}" />
			    <input type="hidden" id="userID" name="userID" value="${userResume.userID}" />
			        <div style="margin-left: 88%; margin-top: 2px;" onclick="openResume(${userResume.userID});"><img src="${pageContext.request.contextPath}/style/images/icon/icon_audit.png"/><a href="javascript:void(0);">简历预览</a></div>
			    
			    <div style="margin-top:4px;  border: 1px double rgb(216, 219, 219);">
				<table class="formtable">
					<tr>
						<td class="title">姓名：</td>
						<td><input type="text" validator="{'length':'2-20'}" id="userName" name="userName" value="${userResume.userName }" /></td>
						<td class="title"></td>
						<td colspan="2" rowspan="7" align="left">
	                        <div style="width:180px; height:200px; border:1px solid #B7B7BC;">
	                           <img id="scanImg" src="${userResume.userIconURL}" onerror="this.src='${pageContext.request.contextPath}/style/images/imgerror.png'" style="width:100%;height:100%;">
	                        </div>
	                        <div style="width:180px; text-align:left;">
	                           <input type="file" id="userIcon" name="userIcon" style="width:125px" value="${userResume.userIcon }"/>
	                        </div>
                        </td>
					</tr>
					<tr>
		                <td class="title">性别：</td>
		                <td><input id="gender" name="gender" value="${userResume.gender}" style="width:182px"/><dfn>*</dfn></td>
		            </tr>
		            <tr>
		                <td class="title">民族：</td>
		                <td><input type="text" validator="{'length':'1-20'}" id="nation" name="nation" value="${userResume.nation }" /><dfn>*</dfn></td>
		            </tr>
		            <tr>
		                <td class="title">籍贯：</td>
		                <td><input type="text" validator="{'length':'0-20'}" id="nativePlace" name="nativePlace" value="${userResume.nativePlace }" /></td>
		            </tr>
		            <tr>
		                <td class="title">身份证号：</td>
		                <td><input type="text" validator="{'default':'checkEmpty_IDCard'}" id="IDNumber" name="IDNumber" value="${userResume.IDNumber }" /><dfn>*</dfn></td>
		            </tr>
		            <tr>
		                <td class="title">出生日期：</td>
		                <td><input validator="{'default':'*','type':'datebox'}" id="birthday" name="birthday" value="${userResume.birthday }" /><dfn>*</dfn></td>
		            </tr>
					<tr>
						<td class="title">学历：</td>
						<td><input id="education" name="education" validator="{'length':'0-50'}" value="${userResume.education }" /></td>
					</tr>
					<tr>
		                <td class="title">职务：</td>
		                <td><input id="post" name="post" validator="{'length':'0-50'}" value="${userResume.post }" /></td>
		                 <td class="title">专业：</td>  
                        <td><input id="major" name="major" validator="{'length':'0-50'}" value="${userResume.major }" /></td>
		               
		            </tr>
		            <tr>
		                <td class="title">职称：</td>
		                <td><input id="postTitle" name="postTitle" validator="{'length':'0-50'}" value="${userResume.postTitle }" /></td>
		                 <td class="title">手机号码：</td>  
                        <td><input id="mobilePhone" name="mobilePhone" value="${userResume.mobilePhone }" validator="{'default':'phoneNum','length':'0-11'}"/></td>
		                
		            </tr>
		             <tr>
		                <td class="title">政治面貌：</td>
		                <td><input id="politicsStatus" name="politicsStatus" validator="{'length':'0-50'}" value="${userResume.politicsStatus }" /></td>
		                <td class="title">邮箱：</td>  
                        <td><input id="email" name="email" value="${userResume.email }" validator="{'default':'email'}"/></td>
		            </tr>
		            <tr>
		                <td class="title">入行时间：</td>
		                <td><input id="duringTime" name="duringTime" value="${userResume.duringTime }"/></td>
		                <td class="title">任职时间：</td>  
                        <td><input id="tenure" name="tenure" value="${userResume.tenure }"/></td>
		            </tr>
		            <tr>
		                <td class="title">聘任时间：</td>
		                <td><input id="engageTime" name="engageTime" value="${userResume.engageTime }"/></td>
		                 <td class="title">参加工作时间：</td>  
                        <td><input id="beOnTheJob" name="beOnTheJob" value="${userResume.beOnTheJob }"/></td>
		            </tr>
		            </table>
		            </div>
		            <div style="  border: 1px double rgb(216, 219, 219);">
		            <table class="formtable tabBorder">
		            <tr>
		                <td class="title">何年何月何处参加工作：</td> 
		                <td colspan="3" style="width:56%">
		                 <textarea rows="3" style="width: 480px;" id="politicalInfoOne" name="politicalInfoOne" validator="{'length':'0-500'}">${userResume.politicalInfoOne}</textarea>
		                </td>
		            </tr>
		            <tr>
		                <td class="title">何年何月何人介绍加入中国共产党，何时转正：</td> 
		                <td colspan="3">
		                 <textarea rows="3" style="width: 480px;" id="politicalInfoTwo" name="politicalInfoTwo" validator="{'length':'0-500'}">${userResume.politicalInfoTwo}</textarea>
		                </td>
		            </tr>
		            <tr>
		                <td class="title">何年何月何人介绍加入中国共产主义青年团：</td> 
		                <td colspan="3">
		                 <textarea rows="3" style="width: 480px;" id="politicalInfoThree" name="politicalInfoThree" validator="{'length':'0-500'}">${userResume.politicalInfoThree}</textarea>
		                </td>
		            </tr>
		            <tr>
		                <td class="title"> 何时经何机关审批任何专业技术职务或任职资格：</td> 
		                <td colspan="3">
		                 <textarea rows="3" style="width: 480px;" id="professionInfo" name="professionInfo" validator="{'length':'0-500'}">${userResume.professionInfo}</textarea>
		                </td>
		            </tr>
				</table>
				</div>
			 </form>
			 </div>
			 <div class="conditionsBtn">
                 <a id="saveUserResumeBtn" >保存</a>
                 <a onclick="javascript:$('#popWin2').window('close');"" >取消</a>
             </div>
           </div>
           
		   <div  title="学习经历" id="userLearningExpTab" >
		       <table id="tblUserLearningExp"/><!-- 学习经历 -->
		       <div class="conditionsBtn">
                    <!-- <a onclick="javascript:$('#resumeTabs').tabs('select', '培训及后续教育');" >下一步</a> -->
                    <a onclick="javascript:$('#popWin2').window('close');"" >取消</a>
               </div>
		   </div>
		   <div  title="培训及后续教育" id="userTrainingExpTab" >
               <table id="tblUserTrainingExp"/><!-- 参加培训及后续教育 -->
               <div class="conditionsBtn">
                     <!-- <a onclick="javascript:$('#resumeTabs').tabs('select', '工作经历');"" >下一步</a> -->
                    <a onclick="javascript:$('#popWin2').window('close');"" >取消</a>
               </div>
           </div>
           <div  title="工作经历" id="userWorkingExpTab" >
               <table id="tblUserWorkingExp"/><!-- 工作经历 -->
               <div class="conditionsBtn">
                    <!-- <a onclick="javascript:$('#resumeTabs').tabs('select', '参与项目建设');"" >下一步</a> -->
                    <a onclick="javascript:$('#popWin2').window('close');"" >取消</a>
               </div>
           </div>
           <div  title="参与项目建设" id="userProjectExpTab" >
               <table id="tblUserProjectExp"/><!-- 参与项目建设 -->
               <div class="conditionsBtn">
                    <a onclick="javascript:$('#popWin2').window('close');"" >取消</a>
               </div>
           </div>
    </body>
</html>