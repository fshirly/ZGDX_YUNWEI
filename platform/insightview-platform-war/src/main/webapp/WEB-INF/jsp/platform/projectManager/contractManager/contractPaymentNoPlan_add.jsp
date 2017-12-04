<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

  
  <body>
     <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/css/base.css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/css/reset.css" />
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/common/commonUtil.js"></script><%--
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/common/easyui-lang-zh_CN.js"></script>
    --%><!-- 计划外付款 -->
	<div id="no_PlanPay_edit" >
	 <table id="editNoPlanPay" class="formtable">
	   <tr>
	      <td class="title">付款日期：</td>
	      <td >
	      <input  id="ipt_paymentTime" name="paydate" validator="{'default':'*','type':'datetimebox'}" /><dfn>*</dfn>
 	      </td>
 	      <td class="title">付款金额：</td>
 	      <td><input  id="ipt_amount" name="pay" validator="{'default':'*','reg':'/^([1-9]\\d{0,3}|0)(\\.\\d{1,6})?$/'}" msg="{reg:'请输入小数点前四位，小数点后6位数字!'}" /><dfn>*</dfn></td>
 	   </tr>
 	   <tr>
 	     <td class="title">经手人：</td>
 	     <td colspan="3"><input  id="ipt_handler" name="jingsr" validator="{'length':'0-10'}" msg="{reg:'只能输入1-10位字符！'}" /></td>
 	   </tr>
 	   <tr>
 	     <td class="title">付款说明：</td>
 	      <td colspan="3"><textarea id="ipt_desc" class="x2" rows="3" validator="{'length':'0-255'}" msg="{reg:'只能输入1-255位字符！'}"></textarea></td>
 	   </tr>
 	   <tr>
 	     <td class="title">付款凭证：</td>
 	     <td colspan="3"><input type="file" id="fkpz" name='fkpz'/></td>
 	   </tr>
	   </table>
	    <div class="conditionsBtn">
					<a href="javascript:void(0);" id="btnSaveNoPlanPayadd" class="fltrt">确定</a>
					<a href="javascript:void(0);" id="btnNoPlanPayResert" onclick="javascript:parent.$('#popWin2').window('close');" class="fltrt">取消</a>
		</div>
	</div>
	<script type="text/javascript">
	   f.namespace('platform.contractManager');
	   platform.contractManager.contractManagerNoplanAdd = (function(){
	    var contractid="${contractid}";
	    var certificateUrl='';
	    f('#btnSaveNoPlanPayadd').click(contractNoPlanPaymentInfo_add);
	    //$('#ipt_amount').bind('blur',validatorPayment);
	    $(function(){
		     $('#ipt_paymentTime').datebox({
		    	 editable:false
                // onSelect:function(date){
                     //validatorPaymentTime();
                // }
			 });
	    	 $('#fkpz').f_fileupload({
	 			whetherPreview : false,
	 			//fileFormat : "['doc', 'docx', 'pdf', 'jpg', 'png', 'gif', 'rar', 'zip', 'txt']",
	 			filesize : 100000,
	 			imgWidth: 10000,
				imgHeight:10000,
	 			downloadFile:'cc',
	 			upLoadBtnId:'paymentuploadid',
	 			repeatUpload : true,
	 			onFileUpload : function(path,name){
	    		    $.messager.alert("提示", "付款凭证上传成功！", "success");
	 	    	    certificateUrl=path;
	 			}
	 		});
		})
		 function contractNoPlanPaymentInfo_add(){
        	   if (checkInfo('#editNoPlanPay')&&contractid!=null&&contractid.length>0) {
        		   validatorPaymentTime();
       		}
 
          }
        /*
         *效验时间
         */
        function validatorPaymentTime(){
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
    					    //zhuangt=false;
    					}else{
    						//zhuangt=true;
    						validatorPayment();
            			}
    				}
    			}
    			ajax_(ajax_param);
        }
        /*
         *校验金钱
         */
        function validatorPayment(){
        	var path = getRootName();
    		var uri = path + "/contractmanager/contractNoPlanPaymentvalidatorCount";
        		var ajax_param = {
        				url : uri,
        				type : "post",
        				datdType : "json",
        				data : {
        			         amount:$('#ipt_amount').val(),
        			         contractID:contractid,
        					 "t" : Math.random()	
        				},
        				error : function() {
        					$.messager.alert("错误", "ajax_error", "error");
        				},
        				success : function(data) {
        					if (data>0) {
        						$.messager.alert('错误','付款金额不能大于合同余额，请重新输入！', "warning");
        						//zhuangt=false;
        					}else{
        						 insertInfo();
                		   }
        				}
        			}
        			ajax_(ajax_param);
    
        }
        /*
         *插入信息
         */
        function insertInfo(){
        	var path = getRootName();
   			var uri = path + "/contractmanager/contractPaymentNoPlanInfo_add";
   			var ajax_param = {
   					url : uri,
   					type : "post",
   					datdType : "json",
   					data : {
   				         contractID:contractid,
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
        //return contractManagerNoplanAdd;
	   })();
	</script>
  </body>
</html>
