<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
 
  
  <body>
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/css/base.css" />
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/css/reset.css" />
  <script type="text/javascript" src="${pageContext.request.contextPath}/fui/plugin/fui-fileupload.min.js"></script><%--
  <script type="text/javascript" src="${pageContext.request.contextPath}/js/common/easyui-lang-zh_CN.js"></script>
  --%><script type="text/javascript" src="${pageContext.request.contextPath}/js/common/commonUtil.js"></script>
  <script type="text/javascript" src="${pageContext.request.contextPath}/js/projectManager/contractManager/contractManager_add.js"></script>
     <div id="contractManager_edit"   style="width: 800px;">
	   <table id="editcontractManager" class="formtable">
	   <tr>
	     <td class="title">合同标题：</td>
	     <td colspan="3">
	       <input type="text" style=" width:94%" id="ipt_title" name="editcontractTitle"   validator="{'default':'*','length':'1-25'}" msg="{reg:'只能输入1-25位字符！'}" /><dfn>*</dfn>
	     </td>
	   </tr>
	   <tr>
	     <td class="title">签订日期：</td>
	     <td><input  id="ipt_signTime" class="easyui-datebox" name="signingDate" validator="{'default':'*','type':'datetimebox'}" data-options="editable:false"/><dfn>*</dfn></td>
	     <td class="title">合同编号：</td>
	     <td><input  id="ipt_contractNo" name="contract_no" validator="{'default':'*','length':'1-50'}" msg="{reg:'只能输入1-50位字符！'}" /><dfn>*</dfn></td>
	   </tr>
	   <tr>
	     <td class="title">合同有效期：</td>
	     <td colspan="3">
	      <input id="ipt_validTimeBegin" class="easyui-datebox" name="validTimeBegin"  validator="{'default':'*','type':'datetimebox'}" data-options="editable:false"><dfn>*</dfn>-<input class="easyui-datebox" validator="{'default':'*','type':'datetimebox'}" id="ipt_validTimeEnd" name="validTimeEnd" data-options="editable:false"/><dfn>*</dfn>
	     </td>
	     
	   </tr>
	   <tr>
	    <td class="title">甲方：</td>
	    <td><input  id="ipt_owner" name="jiafang"  validator="{'default':'*','length':'1-25'}" msg="{reg:'只能输入1-25位字符！'}"/><dfn>*</dfn></td>
	    <td class="title">乙方：</td>
	    <td ><input  id="ipt_partyB" name="yifang" validator="{'default':'*','length':'1-25'}" msg="{reg:'只能输入1-25位字符！'}"/><dfn>*</dfn></td>
	   </tr>
	   <tr>
	    <td class="title">所属项目：</td>
	     <td><input  id="ipt_projectId" /></td>
	     <td class="title">合同类型：</td>
	     <td><input  id="ipt_contractType" /></td>
	   </tr>
	   <tr>
	    <td class="title">保证金(万元)：</td>
	    <td><input  id="ipt_cashDeposit" name="bond" validator="{'reg':'/^([1-9]\\d{0,3}|0)(\\.\\d{1,6})?$/'}" msg="{reg:'请输入小数点前四位，小数点后6位数字!'}"  /></td>
	    <td class="title">合同金额（万元）：</td>
	     <td ><input  id="ipt_moneyAmount" name="moneyAmount"  validator="{'default':'*','reg':'/^([1-9]\\d{0,3}|0)(\\.\\d{1,6})?$/'}" msg="{reg:'请输入小数点前四位，小数点后6位数字!'}" /><dfn>*</dfn></td> 
	   </tr>
	     <tr>
	     <td class="title">负责人：</td>
	     <td colspan="3"><input  id="ipt_responserName" name="unpaid" validator="{'length':'0-10'}" msg="{reg:'只能输入1-10位字符！'}" /></td>
	   </tr>
	   <tr>
	     <td class="title">合同摘要：</td>
	     <td colspan="3"><textarea id="ipt_contractSummary" class="x2" rows="3" validator="{'length':'0-255'}" msg="{reg:'只能输入1-255位字符！'}"></textarea></td>
	   </tr>
	   <tr>
	     <td class="title">合同扫描附件：</td>
	     <td colspan="3">
	        <input  type="file" id='contractfile_url' name="contractfile" />
	     </td>
	   </tr>
	   </table>
	    <div id='datas tops3' >
          <table id="contractfile"></table>
        </div>
	     <div class="conditionsBtn">
					<a href="javascript:void(0);" id="contractManageradd" class="fltrt">确定</a>
					<a href="javascript:void(0);" id="contractManageraddresert" onclick="javascript:parent.$('#popWin').window('close')" class="fltrt">取消</a>
		</div>
		</div>
  </body>
</html>
