<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  
  <body><%--
    
    <script type="text/javascript" src="${pageContext.request.contextPath}/plugin/easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/common/easyui-lang-zh_CN.js"></script>
    --%><script type="text/javascript" src="${pageContext.request.contextPath}/js/common/commonUtil.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/common/fileUtil.js"></script>
    <fmt:formatDate value="${ProjectContractChange.changeTime}" pattern="yyyy-MM-dd"  var="changeTime_temp"/>
     <div>
        <table id="tblChangeInfo" class="tableinfo">
        <tr>
          <td>
            <b class="title" style="float:left;">变更标题：</b>
             <div style="max-height:100px;float:left;overflow:auto;word-wrap: break-word;max-width:200px">
            <label class="input">${ProjectContractChange.title}</label>
            </div>
          </td>
          <td>
            <b class="title">变更时间：</b><label class="input">${changeTime_temp}</label>
          </td>
        </tr>
        <tr>
          <td colspan="2"><b class="title" style="float:left;">变更内容：</b>
           <div style="max-height:100px;float:left;overflow:auto;word-wrap: break-word;max-width:530px">
          <label class="input">${ProjectContractChange.description}</label></td>
          </div>
        </tr>
        <tr>
          <td>
             <b class="title">确认人：</b><label class="input">${ProjectContractChange.confirmManName}</label>
          </td>
          <td>
             <b class="title" style="float:left;">相关附件：</b>
             <div style="max-height:100px;float:left;overflow:auto;word-wrap: break-word;max-width:150px">
             <label class="input" >
             <a id="downloadContractChangeFileLook" title="下载文件"></a>
             </label>
             
             </div>
          </td>
        </tr>
        </table>
        <div class="conditionsBtn">
					<a href="#" class="easyui-linkbutton"  onclick="javascript:parent.$('#popWin2').dialog('close')">关闭</a>
				</div>
     </div>
     <script type="text/javascript">
     initDownLinkTag("downloadContractChangeFileLook","${ProjectContractChange.url}");
     </script>
  </body>
</html>
