<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
 <!-- 时间转换 -->
    <fmt:formatDate value="${projectContract.signTime}" pattern="yyyy-MM-dd"  var="singtime_temp"/>
    <fmt:formatDate value="${projectContract.validTimeBegin}" pattern="yyyy-MM-dd"  var="validTimeBegin_temp"/>
    <fmt:formatDate value="${projectContract.validTimeEnd}" pattern="yyyy-MM-dd"  var="validTimeEnd_temp"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <body>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/css/base.css" />
    <script type="text/javascript" src="${pageContext.request.contextPath}/fui/plugin/fui-fileupload.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/common/commonUtil.js"></script><%--
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/common/easyui-lang-zh_CN.js"></script>
    --%><!--下拉框变量 -->
    <script >
     var projectid="${projectContract.projectId}";
     var contracttype="${projectContract.contractType}";
     var contractid="${projectContract.id}";
     var singtime_temp_iniy="${singtime_temp}";
     var validTimeBegin_temp_init="${validTimeBegin_temp}";
     var validTimeEnd_temp_init="${validTimeEnd_temp}";
     var choosetab=parseInt("${choosetab}");
    </script>
     <script type="text/javascript" src="${pageContext.request.contextPath}/js/projectManager/contractManager/contractManager_edit.js"></script>
   
   <!-- 编辑页 -->
	  <div id="contractManagerInfoTabs_edit" >
	    <div title="合同信息 "  style="overflow: auto;">
	   <table id="editcontractManager" class="formtable">
	   <tr>
	     <td class="title">合同标题：</td>
	     <td colspan="3">
	       <input type="text" style=" width:94%" id="ipt_title_update" value="${projectContract.title}" name="editcontractTitle"   validator="{'default':'*','length':'1-25'}" msg="{reg:'只能输入1-25位字符！'}" /><dfn>*</dfn>
	     </td>
	   </tr>
	   <tr>
	     <td class="title">签订日期：</td>
	     <td><input  id="ipt_signTime_update" class="easyui-datebox" editable=false name="signingDate" value="${singtime_temp}" validator="{'default':'*','type':'datetimebox'}" data-options="editable:false"/><dfn>*</dfn></td>
	     <td class="title">合同编号：</td>
	     <td><input  id="ipt_contractNo_update" name="contract_no" validator="{'default':'*','length':'1-50'}" msg="{reg:'只能输入1-50位字符！'}" value="${projectContract.contractNo}" /><dfn>*</dfn></td>
	   </tr>
	   <tr>
	     <td class="title">合同有效期：</td>
	     <td colspan="3">
	      <input id="ipt_validTimeBegin_update" name="validTimeBegin" editable=false class="easyui-datebox" value="${validTimeBegin_temp}" validator="{'default':'*','type':'datetimebox'}" data-options="editable:false"><dfn>*</dfn>-<input  id="ipt_validTimeEnd_update" class="easyui-datebox" editable=false validator="{'default':'*','type':'datetimebox'}" name="validTimeEnd" value="${validTimeEnd_temp}" data-options="editable:false"/><dfn>*</dfn>
	     </td>
	     
	   </tr>
	   <tr>
	    <td class="title">甲方：</td>
	    <td><input  id="ipt_owner_update" name="jiafang" value="${projectContract.owner}"  validator="{'default':'*','length':'1-25'}" msg="{reg:'只能输入1-25位字符！'}"/><dfn>*</dfn></td>
	    <td class="title">乙方：</td>
	    <td ><input  id="ipt_partyB_update" name="yifang" value="${projectContract.partyB}" validator="{'default':'*','length':'1-25'}" msg="{reg:'只能输入1-25位字符！'}"/><dfn>*</dfn></td>
	   </tr>
	   <tr>
	    <td class="title">所属项目：</td>
	     <td><input  id="ipt_projectId_update"></input></td>
	     <td class="title">合同类型：</td>
	     <td><input  id="ipt_contractType_update" /></td>
	   </tr>
	   <tr>
	    <td class="title">保证金(万元)：</td>
	    <td><input  id="ipt_cashDeposit_update" name="bond" validator="{'reg':'/^([1-9]\\d{0,3}|0)(\\.\\d{1,6})?$/'}" msg="{reg:'请输入小数点前四位，小数点后6位数字!'}"  value="${cashDeposit}"/></td>
	    <td class="title">合同金额（万元）：</td>
	     <td ><input  id="ipt_moneyAmount_update" name="moneyAmount" validator="{'default':'*','reg':'/^([1-9]\\d{0,3}|0)(\\.\\d{1,6})?$/'}" msg="{reg:'请输入小数点前四位，小数点后6位数字!'}"  value="${moneyAmount}"/><dfn>*</dfn></td> 
	   </tr>
	     <tr>
	     <td class="title">负责人：</td>
	     <td ><input  id="ipt_responserName_update" name="unpaid" value="${projectContract.responserName}" validator="{'length':'0-10'}" msg="{reg:'只能输入1-10位字符！'}"/> </td>
	      <td class="title">合同余额（万元）：</td>
	      <td  colspan="2">${remainder}</td>
	   </tr>
	   <tr>
	     <td class="title">合同摘要：</td>
	     <td colspan="3"><textarea id="ipt_contractSummary_update" class="x2" rows="3" validator="{'length':'0-255'}" msg="{reg:'只能输入1-255位字符！'}" >${projectContract.contractSummary}</textarea></td>
	   </tr>
	   <tr>
	     <td class="title">合同扫描附件：</td>
	     <td colspan="3">
	        <input  type="file" id='contractfile_url' name="contractfile" />
	     </td>
	   </tr>
	   </table>
	    <div class='datas tops3' >
          <table id="contractfileedit"></table>
        </div>
	     <div class="conditionsBtn">
					<a href="javascript:void(0);" id="btnContractManager_edit" class="fltrt">确定</a>
					<a href="javascript:void(0);" id="btnContractManager_update_resert" onclick="javascript:parent.$('#popWin').window('close')" class="fltrt">取消</a>
		</div>
	  </div>
	 
	  <div title="付款记录 " id="tabpayment" style="overflow: auto;">
		  <div class='datas' style="height: 470px;">
		   	<table id="contract_payRecord_table"></table>
		  </div>
		  <div class="conditionsBtn">
		  	<a href="javascript:void(0);"  onclick="javascript:platform.contractManager.contractManagerEdit.toreloadiframe();" class="fltrt">关闭</a>
		  </div>
	  </div>
	  <div title="合同变更 "  style="overflow: auto;">
		  <div class='datas' >
		    <table id="edit_contract_biange_Record"></table>
		  </div>
		  <div class="conditionsBtn">
			<a href="javascript:void(0);"  onclick="javascript:platform.contractManager.contractManagerEdit.toreloadiframe();" class="fltrt">关闭</a>
		  </div>
	  </div>
	  </div>
	  
	</body>
</html>
