<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
</head>
    <body>
        <link rel="stylesheet" type="text/css"
            href="${pageContext.request.contextPath}/js/My97DatePicker/skin/WdatePicker.css" />
	    <link rel="stylesheet" type="text/css"
	        href="${pageContext.request.contextPath}/style/css/base.css" />
	    <link rel="stylesheet" type="text/css"
	        href="${pageContext.request.contextPath}/style/css/reset.css" />
        <script language="javascript" 
            type="text/javascript" src="${pageContext.request.contextPath}/js/My97DatePicker/WdatePicker.js"></script>
	    <script type="text/javascript"
	        src="${pageContext.request.contextPath}/js/permission/userLearningExp.js"></script>

				<form id="form1">
					<input type="hidden" id="learningExpId" name="learningExpId" value="${userLearningExp.learningExpId}" />
                    <input type="hidden" id="resumeID" name="resumeID" value="${userLearningExp.resumeID}" />
					<table class="formtable1">
						<tr>
							<td class="title">开始年月：</td>
							<td><input type="text" id="startTime" name="startTime" onClick="WdatePicker({dateFmt:'yyyy-MM'});"  value="${userLearningExp.startTime}" validator="{'length':'1-20'}"/><dfn>*</dfn></td>
						</tr>
						<tr>
                            <td class="title">结束年月：</td>
                            <td><input type="text" id="endTime" name="endTime" onClick="WdatePicker({dateFmt:'yyyy-MM'})"  value="${userLearningExp.endTime}" validator="{'length':'0-20'}"/></td>
                        </tr>
						<tr>
	                        <td class="title">毕业院校及系、专业：</td>
	                        <td><input id="eduAndMajor" name="eduAndMajor" value="${userLearningExp.eduAndMajor}" validator="{'length':'1-200'}"/><dfn>*</dfn></td>
	                    </tr>
	                    <tr>
                            <td class="title">证书类型：</td>
                            <td><input id="graduationInfo" name="graduationInfo" value="${userLearningExp.graduationInfo}" style="width:182px" validator="{'length':'0-50'}"/></td>
                        </tr>
                        <tr>
                            <td class="title">证明人：</td>
                            <td><input id="reference" name="reference" value="${userLearningExp.reference}" validator="{'length':'0-50'}"/></td>
                        </tr>
					</table>
                    <div class="conditionsBtn">
			              <a id="saveBtn">确定</a>
			              <a onclick="javascript:$('#popWin3').window('close');" >取消</a>
				    </div>
				</form>
			</div>
		</div>
    </body>
</html>