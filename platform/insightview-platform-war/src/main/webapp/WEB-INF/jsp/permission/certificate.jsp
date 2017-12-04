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
            src="${pageContext.request.contextPath}/fui/plugin/fui-fileupload.min.js"></script>
        <script type="text/javascript"
            src="${pageContext.request.contextPath}/js/permission/certificate.js"></script>

				<form id="form1">
                    <input type="hidden" id="certificateId" name="certificateId" value="${userCertificate.certificateId}" />
					<table class="formtable">
						<tr>
	                        <td class="title">证书编号：</td>
	                        <td><input type="text" validator="{'default':'*','length':'1-50'}" id="certificateNo" name="certificateNo" value="${userCertificate.certificateNo }" /><dfn>*</dfn></td>
	                        <td class="title">证书名称：</td>
	                        <td><input type="text" validator="{'default':'*','length':'1-50'}" id="certificateName" name="certificateName" value="${userCertificate.certificateName }" /><dfn>*</dfn></td>
	                    </tr>
	                    <tr>
                            <td class="title">颁发时间：</td>
                            <td><input id="dateOfIssue" name="dateOfIssue" value="${userCertificate.dateOfIssue }" /></td>
                            <td class="title">有效时间：</td>
                            <td><input id="effectiveTime" name="effectiveTime" value="${userCertificate.effectiveTime }" /></td>
                        </tr>
	                    <tr>
                            <td class="title">相关附件：</td>
                            <td colspan="3">
                                <input id="accessoryUrl" name="accessoryUrl" type="file"  value="${userCertificate.accessoryUrl }"/>
                            </td>
                        </tr>
                        <tr>
	                        <td class="title">说明：</td> 
	                        <td colspan="3">
	                           <textarea rows="5" class="x2" id="description" name="description" validator="{'length':'0-500'}">${userCertificate.description}</textarea>
	                        </td>
	                    </tr>
					</table>
                    <div class="conditionsBtn">
		              <a id="saveCertificateBtn">确定</a>
		              <a onclick="javascript:$('#popWin2').window('close');" >取消</a>
				    </div>
				</form>
			</div>
		</div>
    </body>
</html>