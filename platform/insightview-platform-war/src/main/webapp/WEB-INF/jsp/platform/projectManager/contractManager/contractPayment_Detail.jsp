<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  
  <body>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/common/commonUtil.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/common/fileUtil.js"></script><%--
    <script type="text/javascript" src="${pageContext.request.contextPath}/plugin/easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/common/easyui-lang-zh_CN.js"></script>
    --%><fmt:formatDate value="${ContractPayment.planPayTime}" pattern="yyyy-MM-dd"  var="planPayTime_temp"/>
   <fmt:formatDate value="${ContractPayment.paymentTime}" pattern="yyyy-MM-dd"  var="paymentTime_temp"/>
   <div>
    <table id="editcontractPayment"  class="tableinfo">
       <tr>
           <td>
               <b class="title">计划付款日期：</b><label class='input'>${planPayTime_temp}</label>
           </td>
 	       <td>
 	           <b class="title">计划付款金额(万)：</b><label class="input">${ContractPayment.planPayAmount}</label>
 	       </td>
       </tr>
       <tr>
	      <td>
	           <b class="title">付款日期：</b><label class="input">${paymentTime_temp}</label>
 	      <td>
 	           <b class="title" style="min-width:106px;">付款金额：</b><label class="input">${ContractPayment.amount}</label>
 	      </td>
 	   </tr>
 	   <tr>
 	     <td colspan="2">
 	           <b class="title">经手人：</b><label class="input">${ContractPayment.handler}</label>
 	     </td>
 	   </tr>
 	   <tr>
 	     <td colspan="2">
 	           <b class="title" style="float:left;">付款说明：</b> 
 	           <div style="max-height:100px;float:left;overflow:auto;word-wrap: break-word;max-width:530px">
 	           <lable class="input">${ContractPayment.desc}</lable>
 	           </div>
 	     </td>
 	   </tr>
 	   <tr>
 	     <td colspan="2">
 	           <b class="title">付款凭证：<lable></b><lable><%--
 	           <input id="contractPaymentFileNameLook" type="hidden" value="${ContractPayment.certificateUrl}" name="attachment" />
 	           --%><a id="downloadContractPaymentFileLook" title="下载文件"></a></lable>
 	     </td>
 	   </tr>
     </table>
      <div class="conditionsBtn">
			<a href="javascript:void(0);" id="btnContractPaymentDetailclose" class="fltrt" onclick="javascript:parent.$('#popWin2').dialog('close');">关闭</a>
	  </div>
	 </div>
	 <script type="text/javascript">
	    initDownLinkTag("downloadContractPaymentFileLook","${ContractPayment.certificateUrl}");
	 </script>
  </body>
</html>
