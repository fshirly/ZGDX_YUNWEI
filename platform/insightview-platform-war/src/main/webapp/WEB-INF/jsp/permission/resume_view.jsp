<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
        <link rel="stylesheet" type="text/css" 
           href="${pageContext.request.contextPath}/style/css/layout.css" />
        <link rel="stylesheet" type="text/css" 
           href="${pageContext.request.contextPath}/plugin/easyui/themes/default/easyui.css"/>
           
	    <script type="text/javascript" 
	       src="${pageContext.request.contextPath}/fui/fui.min.js"></script>
	    <script type="text/javascript" 
	       src="${pageContext.request.contextPath}/plugin/easyui/jquery.easyui.min.js"></script>
	   <script type="text/javascript" 
           src="${pageContext.request.contextPath}/fui/plugin/fui-fileupload.min.js"></script>  
	    <script type="text/javascript"   
	       src="${pageContext.request.contextPath}/js/common/easyui-lang-zh_CN.js"></script> 
        <script type="text/javascript" 
            src="${pageContext.request.contextPath}/js/common/jquery.easyui.extend.js"></script>
	    <script type="text/javascript" 
	       src="${pageContext.request.contextPath}/js/common/commonUtil.js"></script>
        <script type="text/javascript"
           src="${pageContext.request.contextPath}/js/permission/resume_view.js"></script>
           
          <style>
            .tabBorder{
              border:1px solid #B7B7BC;
              width:635px;
              border-collapse: separate;
            }
            .headDiv{
            text-align: center;
            font-size: 30px;
            }
            .btnStyle{
                display: inline-block;
                text-align: center;
                color: #FFF;
                border-width: 1px;
                border-style: solid;
                -moz-border-top-colors: none;
                -moz-border-right-colors: none;
                -moz-border-bottom-colors: none;
                -moz-border-left-colors: none;
                border-image: none;
                border-color: #5CB8E6 #297CA6 #297CA6 #5CB8E6;
                background: none repeat scroll 0% 0% #36A3D9;
                min-width: 80px;
                min-height: 24px;
                line-height: 24px;
                margin: 7px 9px 0px 0px;
              }
              .table td{
               border-bottom: 1px solid rgb(226, 217, 217);
               border-right: 1px solid rgb(226, 217, 217);
               text-align: left;
              }
          </style>
        
        <div id="popWin"></div>
        <div>
	        <div class="formtable headDiv">
	                            个人简历
	        </div>
	        <div style="position:absolute;z-index:1000;margin-left: 65%;margin-top: -2.5%;">
	           <a class="btnStyle" id="closeBtn">关闭</a>
	        </div>
			<form id="resumeForm">
			    <input type="hidden" id="resumeID" name="resumeID" value="${userResume.resumeID}" />
			    <input type="hidden" id="userID" name="userID" value="${userResume.userID}" />
			    <div>
				<table class="formtable tabBorder">
					<tr>
						<td class="title">姓名：</td>
						<td>${userResume.userName}</td>
						<td class="title"></td>
						<td colspan="2" rowspan="7" align="left">
                        <div style="width:180px; height:200px; border:1px solid #B7B7BC;">
                        <img id="scanImg" src="${userResume.userIconURL}" onerror="this.src='${pageContext.request.contextPath}/style/images/imgerror.png'" style="width:100%;height:100%;">
                        </div>
                        </td>
					</tr>
					<tr>
		                <td class="title">性别：</td>
		                <td>
		                <c:if test="${userResume.gender eq '1' }">男</c:if>
		                <c:if test="${userResume.gender eq '2' }">女</c:if>
		                </td>
		            </tr>
		            <tr>
		                <td class="title">民族：</td>
		                <td>${userResume.nation }</td>
		            </tr>
		            <tr>
		                <td class="title">籍贯：</td>
		                <td>${userResume.nativePlace }</td>
		            </tr>
		            <tr>
		                <td class="title">身份证号：</td>
		                <td>${userResume.IDNumber }</td>
		            </tr>
		            <tr>
		                <td class="title">出生日期：</td>
		                <td>${userResume.birthday }</td>
		            </tr>
					<tr>
						<td class="title">学历：</td>
						<td>${userResume.education }</td>
					</tr>
					<tr>
		                <td class="title">职务：</td>
		                <td>${userResume.post }</td>
		                <td class="title">专业：</td>  
                        <td>${userResume.major }</td>
		            </tr>
		            <tr>
		                <td class="title">职称：</td>
		                <td>${userResume.postTitle }</td>
		                <td class="title">手机号码：</td>  
                        <td>${userResume.mobilePhone }</td>
		            </tr>
		             <tr>
		                <td class="title">政治面貌：</td>
		                <td>${userResume.politicsStatus }</td>
		                <td class="title">邮箱：</td>  
                        <td>${userResume.email }</td>
		            </tr>
		            <tr>
		                <td class="title">入行时间：</td>
		                <td>${userResume.duringTime }</td>
		                <td class="title">任职时间：</td>  
                        <td>${userResume.tenure }</td>
		            </tr>
		            <tr>
		                <td class="title">聘任时间：</td>
		                <td>${userResume.engageTime }</td>
		                <td class="title">参加工作时间：</td>  
                        <td>${userResume.beOnTheJob }</td>
		            </tr>
		            </table>
		            </div>
		            <div>
		            <table class="formtable tabBorder table">
		            <tr>
		                <td class="title">何年何月何处参加工作：</td> 
		                <td colspan="3" style="width:78%">
		                <textarea rows="4" style="width:100%;height:100%;border:0;" readOnly>${userResume.politicalInfoOne}</textarea>
		                 
		                </td>
		            </tr>
		            <tr>
		                <td class="title">何年何月何人介绍加入中国共产党，何时转正：</td> 
		                <td colspan="3">
		                 <textarea rows="4" style="width:100%;height:100%;border:0;" readOnly>${userResume.politicalInfoTwo}</textarea>
		                </td>
		            </tr>
		            <tr>
		                <td class="title">何年何月何人介绍加入中国共产主义青年团：</td> 
		                <td colspan="3">
		                <textarea rows="4" style="width:100%;height:100%;border:0;" readOnly> ${userResume.politicalInfoThree}</textarea>
		                </td>
		            </tr>
		            <tr>
		                <td class="title"> 何时经何机关审批任何专业技术职务或任职资格：</td> 
		                <td colspan="3">
		                 <textarea rows="4" style="width:100%;height:100%;border:0;" readOnly>${userResume.professionInfo}</textarea>
		                </td>
		            </tr>
				</table>
				</div>
			</form>

		   <div class="formtable tabBorder">
		       <table id="tblUserLearningExp"/><!-- 学习经历 -->
		   </div>
		   <div class="formtable tabBorder">
               <table id="tblUserTrainingExp"/><!-- 参加培训及后续教育 -->
           </div>
           <div class="formtable tabBorder">
               <table id="tblUserWorkingExp"/><!-- 工作经历 -->
           </div>
           <div class="formtable tabBorder">
               <table id="tblUserProjectExp"/><!-- 参与项目建设 -->
           </div>
           <div class="formtable tabBorder">
           </div>
		</div>
    </body>
</html>