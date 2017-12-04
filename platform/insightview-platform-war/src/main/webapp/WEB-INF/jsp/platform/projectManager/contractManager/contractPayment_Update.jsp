<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <body>
  
   <script type="text/javascript" src="${pageContext.request.contextPath}/js/common/commonUtil.js"></script>
    <fmt:formatDate value="${ContractPayment.planPayTime}" pattern="yyyy-MM-dd"  var="planPayTime_temp"/>
   <fmt:formatDate value="${ContractPayment.paymentTime}" pattern="yyyy-MM-dd"  var="paymentTime_temp"/>
    <div id='ContractPaymentinfo_update'>
     <table id="editcontractPayment" class="formtable">
       <tr>
           <td class="title">计划付款日期：</td>
	      <td >
	      <input  id="ipt_planPayTime"  name="palnpaydate" value="${planPayTime_temp}" />
 	      </td>
 	     <td class="title">计划付款金额（万元）：</td>
 	     <td><input  id="ipt_planPayAmount" name="plan_pay" readonly="readonly"  validator="{'reg':'/^([1-9]\\d{0,3}|0)(\\.\\d{1,6})?$/'}" msg="{reg:'请输入小数点前四位，小数点后6位数字!'}" value="${ContractPayment.planPayAmount}"/></td>
       </tr>
       <tr>
	      <td class="title">付款日期：</td>
	      <td >
	      <input  id="ipt_paymentTime" name="paydate" value="${paymentTime_temp}" validator="{'default':'*','type':'datetimebox'}"/><b>*</b>
 	      </td>
 	      <td class="title">付款金额：</td>
 	      <td><input  id="ipt_amount" name="pay" validator="{'default':'*','reg':'/^([1-9]\\d{0,3}|0)(\\.\\d{1,6})?$/'}" msg="{reg:'请输入小数点前四位，小数点后6位数字!'}" value="${ContractPayment.amount}" /><b>*</b></td>
 	   </tr>
 	   <tr>
 	     <td class="title">经手人：</td>
 	     <td colspan="3"><input  id="ipt_handler" name="jingsr" validator="{'length':'1-10'}" msg="{reg:'只能输入1-10位字符！'}" value="${ContractPayment.handler}" /><b>*</b></td>
 	   </tr>
 	   <tr>
 	     <td class="title">付款说明：</td>
 	      <td colspan="3"><textarea id="ipt_desc" class="x2" rows="3" validator="{'length':'0-255'}" msg="{reg:'只能输入0-255位字符！'}">${ContractPayment.desc}</textarea></td>
 	   </tr>
 	   <tr>
 	     <td class="title">付款凭证：</td>
 	     <td colspan="3"><input type="file" id="fkpzupdate" name='fkpzupdate'/></td>
 	   </tr>
     </table>
      <div class="conditionsBtn">
					<a href="javascript:void(0);" id="btnUpdateContractPayment" class="fltrt">确定</a>
					<a href="javascript:void(0);" id="btnUpdateContractPaymentResert" class="fltrt"  onclick="javascript:parent.$('#popWin2').dialog('close');">取消</a>
	  </div>
    </div>
    <script type="text/javascript">
    var planPayTime_temp="${planPayTime_temp}";//计划付款时间
    //var payTime_temp="${paymentTime_temp}";//付款时间
    var amount_temp="${ContractPayment.amount}";//付款金额
    var planPayAmount="${ContractPayment.planPayAmount}";//计划付款金额
    var paymentID="${ContractPayment.paymentID}";//付款信息ID号
    //console.log(payTime_temp);
    f.namespace('platform.contractManager');
	   platform.contractManager.contractPaymentInfoUpdate = (function(){
       var paymentID="${ContractPayment.paymentID}";
       var contractid="${ContractPayment.contractID}"
       var certificateUrl="${ContractPayment.certificateUrl}";
       f('#btnUpdateContractPayment').click(updateContractPaymentInfo);
       //$('#ipt_amount').bind('blur',validatorPayment);
       //$('#ipt_planPayAmount').bind('blur',validatorPlanPayment);
       $(function(){
           //付款时间效验
    	   $('#ipt_paymentTime').datebox({
    		   editable:false
			 });
			 //计划付款时间效验
		   $('#ipt_planPayTime').datebox({
			   editable:false,
			   disabled : true
			})
			//上传附件
    	   $('#fkpzupdate').f_fileupload({
	 			whetherPreview : false,
	 			//fileFormat : "['doc', 'docx', 'pdf', 'jpg', 'png', 'gif', 'rar', 'zip', 'txt']",
	 			filesize : 100000,
	 			upLoadBtnId:'editpaymentfile',
	 			imgWidth: 100000,
	 			imgHeight: 100000,
	 			downloadFile:'dd',
	 			repeatUpload : true,
	 			onFileUpload : function(path,name){
    		        $.messager.alert("提示", "付款凭证上传成功！", "success");
	 	    	    certificateUrl=path;
	 			}
	 		});
       })
       function updateContractPaymentInfo(){
    	   if (checkInfo('#editcontractPayment')&&(paymentID!=null)&&(paymentID.length>0)) {
    		   validatorPaymentTime();
      		}
      }
       /*
        *付款时间效验
        */
       function validatorPaymentTime(){
    	   //debugger;
        var a=$('#ipt_paymentTime').datebox("getValue");
        if(a!=null &&a!=''){
       	var path = getRootName();
   		var uri = path + "/contractmanager/contractPaymentvalidatorTime";
   		var ajax_param = {
   				url : uri,
   				type : "post",
   				datdType : "json",
   				data : {
   			         paymentTime:$('#ipt_paymentTime').datebox("getValue"),
   			         contractID:contractid,
   					 "t" : Math.random()	
   				},
   				error : function() {
   					$.messager.alert("错误", "ajax_error", "error");
   				},
   				success : function(data) {
   					if (false == data || "false" == data) {
   						$.messager.alert('错误','付款时间不能在合同签订时间之前！');
   					}else{
   						validatorPayPlanTime();
   	   	   			}
   				}
   			}
   			ajax_(ajax_param);
         }else{
        	 validatorPayPlanTime();
          }
       }
       /*
        *付款金额效验
        */
       function validatorPayment(){
    	   //debugger;
    	var time1=$('#ipt_paymentTime').datebox("getValue");
    	var a=$('#ipt_amount').val();
        if(a>0 && (a!=amount_temp)&& time1.length>0){
        	var path = getRootName();
       		var uri = path + "/contractmanager/contractNoPlanPaymentvalidatorCountUpdate";
       		var ajax_param = {
       				url : uri,
       				type : "post",
       				datdType : "json",
       				data : {
       			         amount:$('#ipt_amount').val(),
       			         contractID:contractid,
       			         paymentID:paymentID,
       					 "t" : Math.random()	
       				},
       				error : function() {
       					$.messager.alert("错误", "ajax_error", "error");
       				},
       				success : function(data) {
       					if (data>0) {
       						$.messager.alert('错误','付款金额不能大于合同余额，请重新输入！', "waining");
       					}else{
       						validatorPlanPayment();
       	   				}
       				}
       			}
       		 	ajax_(ajax_param);
         }else if(a==0 &&a.length>0){
 	    	$.messager.alert('错误','付款金额不能为零！');
	 	 }else if(time1.length==0 && a>0){
 	 		$.messager.alert('错误','付款时间不能为空！');
 	 	 }else if(time1.length>0 && a.length==0){
  	 		$.messager.alert('错误','计划付款不能为空！');
	 	 }else{
        	validatorPlanPayment();
        }
       }
       /*
        *计划付款时间效验
        */
       function validatorPayPlanTime(){
    	   //debugger;
        var a=$('#ipt_planPayTime').datebox('getValue');
        if(a!='' && a!=''&&(a!=planPayTime_temp)){
   		var path = getRootName();
   		var uri = path + "/contractmanager/contractPlanPaymentvalidatorTime";
   		var ajax_param = {
   				url : uri,
   				type : "post",
   				datdType : "json",
   				data : {
   			         planPayTime:$('#ipt_planPayTime').datebox('getValue'),
   			         contractID:contractid,
   					 "t" : Math.random()	
   				},
   				error : function() {
   					$.messager.alert("错误", "ajax_error", "error");
   				},
   				success : function(data) {
   					if (false == data || "false" == data) {
   						$.messager.alert('错误','计划付款时间不能在合同签订时间之前！');
   					}else{
   						vaildatorPlanTimeDouble();
   	   	   			}
   				}
   			}
   			ajax_(ajax_param);
        }else{
        	vaildatorPlanTimeDouble();
        }
       }
       /*
        *计划付款金额效验
        */
       function validatorPlanPayment(){
    	   //debugger;
    	   var a=$('#ipt_planPayAmount').val();
    	   var time=$('#ipt_planPayTime').datebox('getValue');
    	  if(a>0 && (a!=planPayAmount)&& time.length>0){
    		var path = getRootName();
       		var uri = path + "/contractmanager/contractPlanPaymentvalidatorCountUpdate";
       		var ajax_param = {
       				url : uri,
       				type : "post",
       				datdType : "json",
       				data : {
       			         planPayAmount:$('#ipt_planPayAmount').val(),
       			         contractID:contractid,
       			         paymentID:paymentID,
       					 "t" : Math.random()	
       				},
       				error : function() {
       					$.messager.alert("错误", "ajax_error", "error");
       				},
       				success : function(data) {
       					if (data>0) {
       						$.messager.alert('错误','计划付款金额不能大于合同余额，请重新输入！', "warning");
       					}else{	
       						updateInfo();
       	   	   			}
       				}
       			}
       			ajax_(ajax_param);
 	     }else if(a==0 &&a.length>0){
 	    	$.messager.alert('错误','计划付款金额不能为零！');
 	 	 }else if(time.length==0 && a>0){
 	 		$.messager.alert('错误','计划付款时间不能为空！');
 	 	 }else if(time.length>0 && a.length==0){
 	 		$.messager.alert('错误','计划付款不能为空！');
 	 	  }else{
 	    	updateInfo();
 	 	 }
       
       }
       /*
        *判断计划时间是否重复
        */
        function vaildatorPlanTimeDouble(){
        	   //debugger;
         var a=$('#ipt_planPayTime').datebox('getValue');
         if(a!=planPayTime_temp){
        	var path = getRootName();
     		var uri = path + "/contractmanager/contractPlanmentvalidatorTimeDouble";
     		var ajax_param = {
     				url : uri,
     				type : "post",
     				datdType : "json",
     				data : {
     			      planPayTime:$('#ipt_planPayTime').datebox('getValue'),
    	              contractID:contractid,
    			      "t" : Math.random()	
     				},
     				error : function() {
     					$.messager.alert("错误", "ajax_error", "error");
     				},
     				success : function(data) {
     					if (false == data || "false" == data) {
     						$.messager.alert('错误','计划付款时间不能重复！');
     					}else{
     						validatorPayment();
     	 	 			}
     				}
     			}
     			ajax_(ajax_param);
          }else{
        	  validatorPayment();
           }
       	 
         }
        /*
         *修改信息
         */
        function updateInfo(){
            //debugger;
        	var path = getRootName();
  			var uri = path + "/contractmanager/contractPaymentinfoupdate";
  			var ajax_param = {
  					url : uri,
  					type : "post",
  					datdType : "json",
  					data : {
  				         paymentID:paymentID,
  				         planPayTime:$('#ipt_planPayTime').datebox("getValue"),
  				         planPayAmount:$('#ipt_planPayAmount').val(),
  				         paymentTime:$('#ipt_paymentTime').datebox("getValue"),
  				         amount:$('#ipt_amount').val(),
  				         handler:$('#ipt_handler').val(),
  				         desc:$('#ipt_desc').val(),
  				         certificateUrl:certificateUrl,
  						 "t" : Math.random()
  					},
  					error : function() {
  						$.messager.alert("错误", "ajax_error", "error");
  					},
  					success : function(data) {
  						if (true == data || "true" == data) {
  							$('#popWin2').dialog('close');
  							reloadTableCommon('contract_payRecord_table');
  						} else {
  							$.messager.alert("提示", "计划外付款失败！", "error");
  							reloadTableCommon('contract_payRecord_table');
  						}
  					}
  				}
  				ajax_(ajax_param);
         }
   })()
    </script>
  </body>
</html>
