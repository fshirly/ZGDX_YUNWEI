<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
 <!-- 时间转换 -->
    <fmt:formatDate value="${projectContractInfo.signTime}" pattern="yyyy-MM-dd"  var="singtime_temp"/>
    <fmt:formatDate value="${projectContractInfo.validTimeBegin}" pattern="yyyy-MM-dd"  var="validTimeBegin_temp"/>
    <fmt:formatDate value="${projectContractInfo.validTimeEnd}" pattern="yyyy-MM-dd"  var="validTimeEnd_temp"/>
    <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
    <html>
  <body><%--
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/css/base.css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/css/reset.css" />
    --%><script type="text/javascript">
      var contractid="${projectContractInfo.id}";
    </script><%--
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/plugin/easyui/themes/default/easyui.css" />
    --%><script type="text/javascript" src="${pageContext.request.contextPath}/js/common/commonUtil.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/projectManager/contractManager/contractManager_detail.js"></script>
	       <div id="contractManagerInfoTabs" class="easyui-tabs" data-options="fit:true">
		      <div title="合同信息 " data-options="closable:false" style="overflow-Y:auto;">
		         <table id="tblContractInfo" class="tableinfo" >
		          <tr>
		                <td >
		                    <div style="float:left;">
					        <b class="title" >合同标题：</b>
					        </div>
					        <div style="max-height:100px;float:left;word-wrap: break-word;max-width:120px">
					           <label id="lbl_contractTitle" class="input">${projectContractInfo.title}</label>
					        </div>
						</td>
						<td >
		                   <b class="title" style="float:left;">合同编号：</b>
		                   <div style="max-height:100px;float:left;word-wrap: break-word;max-width:120px">
						   <label id="lbl_contract_no" class="input" >${projectContractInfo.contractNo}</label>
						   </div>
		               </td>
		          </tr>
		          <tr>
		              <td>
		                  <b class="title" style="float:left;">签订日期：</b>
		                  <div>
						  <label id="lbl_signingDate" class="input" >${singtime_temp}</label>
						  </div>
		              </td> 
		              <td>
		                  <b class="title" style="float:left;">合同有效期：</b>
		                  <div>
						  <label id="lbl_contract_duration" class="input" >${validTimeBegin_temp}&nbsp;至&nbsp;${validTimeEnd_temp}</label>
						  </div>
		              </td>
		          </tr>
		          <tr>
		                <td>
		                    <div style="float:left;">
		                    <b class="title" >甲方：</b>
		                    </div>
		                    <div style="max-height:100px;float:left;word-wrap: break-word;max-width:120px">
						    <label id="lbl_jiafang" name="ipt_techSupportTel" class="input" >${projectContractInfo.owner}</label>
						    </div>
		                </td>
		                <td>
		                    <b class="title" style="float:left;">乙方：</b>
		                    <div style="max-height:100px;float:left;word-wrap: break-word;max-width:120px">
							<label id="lbl_yifang" name="ipt_email" class="input" >${projectContractInfo.partyB}</label>
							</div>
		                </td>
		          </tr>
		          <tr>
					  <td>
							<b class="title">所属项目：</b>
							<label  class="input" >${projectContractInfo.projecttitle}</label>
						</td>
						<td>
							<b class="title">合同类型：</b>
							<label class="input" >${projectContractInfo.typeneir}</label>
						</td>
				   </tr>
				   <tr>
						<td>
							<b class="title">保证金(万元)：</b>
							<label type="text" id="lbl_bond" class="input" >${cashDeposit}</label>
						</td>
						<td>
							<b class="title">合同金额(万元)：</b>
							<label type="text" id='lbl_account_balance' class="input">${moneyAmount}</label>
						</td>
					</tr>
					<tr>
					    <td>
						    <b class='title' style="float:left;">负责人：</b>
						    <div>
						    <label type="text"  class="input">${projectContractInfo.responserName}</label>
						    </div>
						</td>
						<td>
							<b class="title">未付金额(万元)：</b>
							<label type="text"  class="input">${remainder}</label>
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<b class="title" style="float:left;">合同摘要：</b>
							<div style="max-height:100px;float:left;word-wrap: break-word;max-width:530px">
							<label  id="lbl_contract_summary"  class="x2" rows="3">${projectContractInfo.contractSummary}</label>
							</div>
						</td>
					</tr>
		         </table>
				   <div class='datas tops3' style="overflow-y:auto;">
                      <table id="contractfiledetail" title="合同附件"></table>
                   </div>

		</div>
		<div title="合同付款记录" data-options="closable:false" >
		  <div class='datas' >
		    <table id="contractPayment_table_datail"></table>
		  </div>

		</div>
		<div title="合同变更信息"  data-options="closable:false" >
		  <div class='datas'>
		      <table id="contractbiangeRecord_table"></table>
		  </div>

		</div>
	  </div>
	  <div class="conditionsBtn" >
			<a href="#" class="easyui-linkbutton"  onclick="javascript:parent.$('#popWin').dialog('close')">关闭</a>
	  </div>
  </body>
</html>

