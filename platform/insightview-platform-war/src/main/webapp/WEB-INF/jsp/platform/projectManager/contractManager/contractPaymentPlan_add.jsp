<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  
  <body>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/css/base.css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/css/reset.css" />
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/common/commonUtil.js"></script><%--
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/common/easyui-lang-zh_CN.js"></script>
    --%><!-- 增加计划 -->
	 <div id="contractPlan_add">
	   <table id="editcontractcontractPlan" class="formtable">
	   <tr>
	      <td class="title">计划付款日期：</td>
	      <td >
	      <input  id="ipt_planPayTime" name="palnpaydate" validator="{'default':'*','type':'datetimebox'}" /><dfn>*</dfn>
 	      </td>
 	     <td class="title">计划付款金额（万元）：</td>
 	     <td><input  id="ipt_planPayAmount" name="plan_pay" validator="{'default':'*','reg':'/^([1-9]\\d{0,3}|0)(\\.\\d{1,6})?$/'}" msg="{reg:'请输入小数点前四位，小数点后6位数字!'}" /><dfn>*</dfn></td>
 	   </tr>
	   </table>
	    <div class="conditionsBtn">
					<a href="javascript:void(0);" id="btnSavePlan" class="fltrt">确定</a>
					<a href="javascript:void(0);" id="btnUpdatePlan" onclick="javascript:parent.$('#popWin2').window('close');" class="fltrt">取消</a>
		</div>
		
	</div>
	<script type="text/javascript">
	 f.namespace('platform.contractManager');
	 platform.contractManager.contractManagerPlanAdd = (function(){
	  var contractid="${contractid}";
	  f('#btnSavePlan').click(contractPlanPaymentInfo_add);
	     $(function(){
		    $('#ipt_planPayTime').datebox({
		    	editable:false
		    	//onSelect:function(date){
		    	  // validatorPayPlanTime();
		    	//}
			})
		   //$('#ipt_planPayAmount').bind('blur',validatorPlanPayment);
     });
	function contractPlanPaymentInfo_add(){
		if (checkInfo('#editcontractcontractPlan')&&contractid!=null&&contractid.length>0) {
			validatorPayPlanTime();
		}
	}
	//付款金额效验(不能早于签订时间)
	function validatorPayPlanTime(){
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
						//$("#btnSavePlan").attr({"disabled":"disabled"});
					}else{
						validatorPlanPayment();
				    }
				}
			}
			ajax_(ajax_param);
    }
    //付款金额效验
    function validatorPlanPayment(){
    	var zt=true;
    	var path = getRootName();
    	//debugger;
        	var uri = path + "/contractmanager/contractPlanPaymentvalidatorCount";
    		var ajax_param = {
    				url : uri,
    				type : "post",
    				datdType : "json",
    				data : {
    			         planPayAmount:$('#ipt_planPayAmount').val(),
    			         contractID:contractid,
    					 "t" : Math.random()	
    				},
    				error : function() {
    					$.messager.alert("错误", "ajax_error", "error");
    				},
    				success : function(data) {
    					if (data>0) {
    						$.messager.alert('错误','计划付款金额不能大于合同余额，请重新输入！', "warning");
    					}else{
    						vaildatorPlanTimeDouble();
        				}
    				}
    			}
    			ajax_(ajax_param);
    		    return zt;	
    }
    /*
     *判断计划时间是否重复
     */
     function vaildatorPlanTimeDouble(){
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
 					return false;
 				},
 				success : function(data) {
 					if (false == data || "false" == data) {
 						$.messager.alert('错误','计划付款时间不能重复！');
 						
 					}else{
 						insertInfo();
 	 	 			}
 				}
 			}
 			ajax_(ajax_param);
      }
    /*
     *插入数据
     */
     function  insertInfo(){
    	 var path = getRootName();
			var uri = path + "/contractmanager/contractPaymentPlanInfo_add";
			var ajax_param = {
					url : uri,
					type : "post",
					datdType : "json",
					data : {
				         planPayTime:$('#ipt_planPayTime').datebox('getValue'),
				         planPayAmount:$('#ipt_planPayAmount').val(),
				         contractID:contractid,
						"t" : Math.random()	
					},
					error : function() {
						$.messager.alert("错误", "ajax_error", "error");
					},
					success : function(data) {
						if (true == data || "true" == data) {
							parent.$('#popWin2').window('close');
							reloadTableCommon('contract_payRecord_table');
						} else {
							$.messager.alert("提示", "增加付款计划失败！", "error");
							reloadTableCommon('contract_payRecord_table');
						}
					}
				}
				ajax_(ajax_param);

      }
	//return contractManagerPlanAdd;
	 })()
	</script>
  </body>
</html>
