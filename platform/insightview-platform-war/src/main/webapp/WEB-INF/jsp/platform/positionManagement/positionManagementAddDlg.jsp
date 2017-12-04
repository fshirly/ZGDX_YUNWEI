<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>岗位新增</title>
</head>
<body>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/css/base.css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/css/reset.css" />
    <link rel="stylesheet" type="text/css" href="../plugin/easyui/themes/default/easyui.css" />

    <script type="text/javascript" src="${pageContext.request.contextPath}/js/common/commonUtil.js"></script>   
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/platform/positionManagement/positionManagementAddDlg.js"></script>
    <input type="hidden" value="" id=postId />
    <div id="positionDlgTabs" class="easyui-tabs" fit=true>
        <div title="岗位信息">
            <table class="formtable" id="positionMessageTab">
               <tr>
                 <td class="title">岗位名称：</td>
                 <td><input id="positionNameTree" name="positionNameTree" validator="{'default':'*','length':'1-50'}"/><dfn>*</dfn>&nbsp;&nbsp;&nbsp;&nbsp;<b style="color: black;">所属组织：</b><label>${organizationName}</label><input type="hidden" value="${organizationID}" id="organizationID" name="organizationID"/></td>
               </tr>
               <tr>
                 <td class="title">是否重要岗位：</td>
                 <td colspan="3"><input type="radio" name="isImportantPosition" id="isImportantPosition_Dlg" value="1" checked>是
                  <input type="radio" name="isImportantPosition" id="isImportantPosition_Dlg" value="2" />否
                  </td>
               </tr>
               <tr>
                 <td class="title">岗位分工：</td>
                 <td ><textarea rows="4" class="x3" id="positionDistribution_Dlg" name="positionDistribution_Dlg" validator="{'length':'0-500'}"></textarea></td>
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
     <div class="conditionsBtn" id="tabs1_btn" style="width:698px;position:relative;  margin-left:-30px">
         <a onclick="javascript:System.PostMgmDlg.confirmData('addNew');">确定</a> 
         <a onclick="javascript:System.PostMgmDlg.delIsertPostMsg();" >取消</a>
     </div>
     <div class="conditionsBtn" id="tabs2_btn"  style="display:none;width:698px;position:relative;  margin-left:-30px;" >
         <a onclick="javascript:System.PostMgmDlg.submitBtn();">确定</a> 
         <a onclick="javascript:System.PostMgmDlg.delIsertPostMsg();" >取消</a>
     </div>
      	<script>
        $(function(){
        	System.PostMgmDlg.closeWindowEvent();
        });
    </script> 
</body>
</html>