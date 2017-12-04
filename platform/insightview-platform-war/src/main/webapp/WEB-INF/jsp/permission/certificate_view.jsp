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
            href="${pageContext.request.contextPath}/style/css/base.css" />
        <link rel="stylesheet" type="text/css"
            href="${pageContext.request.contextPath}/style/css/reset.css" />
        <script type="text/javascript" 
            src="${pageContext.request.contextPath}/js/common/fileUtil.js"></script>
        <script type="text/javascript"
            src="${pageContext.request.contextPath}/js/permission/certificate_view.js"></script>
        
        <table class="tableinfo">
            <tr>
             <td><b class="title">证书编号：</b> <label>${userCertificate.certificateNo }</label>
             </td>
             <td><b class="title">证书名称：</b> <label>${userCertificate.certificateName }</label>
             </td>
            </tr>
            <tr>
                <td><b class="title">颁发时间：</b> <label>${userCertificate.dateOfIssue }</label>
                </td>
                <td><b class="title">有效时间：</b> <label>${userCertificate.effectiveTime }</label>
                </td>
            </tr>
            <tr>
                <td colspan="2"><b class="title">相关附件：</b> 
                <input id="previousContractFileNameLook" type="hidden" value="${userCertificate.accessoryUrl}" name="attachment" /><a id="downloadContractFileLook" title="下载文件"></a>
                </td>
            </tr>
            <tr>
               <td colspan="2"><b class="title">说明：</b><label style="word-break:break-all;">${userCertificate.description }</label>
               </td>
            </tr>
        </table>
        <div class="conditionsBtn">
              <a onclick="javascript:$('#popWin2').window('close');" >关闭</a>
        </div>
           
    </body>
</html>