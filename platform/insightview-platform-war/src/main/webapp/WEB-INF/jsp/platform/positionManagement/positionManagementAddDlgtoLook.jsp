<%@ page language="java" pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>岗位查看</title>
</head>
<body>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/css/base.css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/css/reset.css" />
    <link rel="stylesheet" type="text/css" href="../plugin/easyui/themes/default/easyui.css" />
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/platform/positionManagement/positionManagementAddDlg.js"></script>
    <div id="positionDlgTabs" class="easyui-tabs" fit=true>
    <input type="hidden" id="postId" value="${postID}" />
        <input type="hidden" value=${pageCondition} id="toLookFlag"  />
        <div title="岗位信息">
            <table class="formtable">
               <tr>
                 <td class="title" style="width:20%;">岗位名称：</td>
                 <td ><label>${postName}</label></td>
               </tr>
               <tr>
                 <td class="title">所属组织：</td>
                 <td><label>${organizationName}</label></td>
               </tr>
               <tr>
                 <td class="title">是否重要岗位：</td>
                 <td colspan="3">
                 <c:if test="${isImportant eq 2}">
                                                                          否
                 </c:if>
                 <c:if test="${isImportant eq 1}">
                                                                         是
                 </c:if>
                 </td>
               </tr>
               <tr>
                 <td class="title">岗位分工：</td>
                 <td ><label>${postDivision}</label></td>
               </tr>
            </table>
        </div>
        <div title="人员信息">
            <div class="datas tops1">
                <table id="personalTable">
                </table>
            </div>
        </div>
    </div>
    	<div class="conditionsBtn">
         <a onclick="javascript:$('#popWin').window('close');" >取消</a>
    </div>
</body>
</html>