<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
  
  <body>
   <script type="text/javascript" src="${pageContext.request.contextPath}/js/common/commonUtil.js"></script>
    <!-- 合同变更编辑页 -->
	<div id='contract_biange_Record_edit'  title="合同变更记录" >
	<table id="editBiangRecord" class="formtable">
	   <tr>
	      <td class="title">变更标题：</td>
	      <td >
	      <input  id="ipt_biangetitle" name="biangetitle" validator="{'default':'*','length':'1-25'}" msg="{reg:'只能输入1-25位字符！'}" /><dfn>*</dfn>
 	      </td>
 	      <td class="title">变更时间：</td>
 	      <td >
 	      <input type="hidden" value="${singtime }" id="singtime"/>
 	      <input  id="ipt_biangshij" name="biangshij" class="easyui-datebox" editable=false validator="{'default':'*','type':'datetimebox'}" /><dfn>*</dfn></td>
 	   </tr>
 	   <tr>
 	     <td class="title">变更内容：</td>
 	     <td colspan="3"><textarea id="ipt_bgnr" class="x2" rows="3" validator="{'length':'0-255'}" msg="{reg:'只能输入1-255位字符！'}"></textarea></td>
 	   </tr>
 	   <tr>
 	     <td class="title">确认人：</td>
 	      <td colspan="3"><input id="qrr" validator="{'default':'*','length':'1-10'}" msg="{reg:'只能输入1-10位字符！'}" /><dfn>*</dfn></td>
 	   </tr>
 	   <tr>
 	     <td class="title">相关附件：</td>
 	     <td colspan="3"><input type="file" id="xgfj"/></td>
 	   </tr>
 	 </table>
 	  <div class="conditionsBtn">
		 <a href="javascript:void(0);" id="btnContractchaneadd" class="fltrt">确定</a>
		 <a href="javascript:void(0);" onclick="javascript:parent.$('#popWin2').window('close');" class="fltrt">取消</a>
	</div>
   </div>
    <script type="text/javascript">
     f.namespace('platform.contractManager');
     platform.contractManager.contractChaneAdd = (function(){
        f('#btnContractchaneadd').click(contractChaneInfoAdd);
        var upload_path='';
        $(function(){
              /*
               *上传变更附件
               */
        	 $('#xgfj').f_fileupload({
     			whetherPreview : false,
     			//fileFormat : "['doc', 'docx']",
     			filesize : 100000,
     			imgWidth: 10000,
				imgHeight:10000,
     			upLoadBtnId:'contractchangefile',
     			downloadFile:'aa',
     			repeatUpload : true,
     			onFileUpload : function(path,name){
        		 $.messager.alert("提示", "合同变更附件上传成功！", "success");
        		 upload_path=path;
     			}
     		});
        });
        function contractChaneInfoAdd(){
        	if (checkInfo('#editBiangRecord')) {
        		var singtime = $("#singtime").val();
        		var biang = $('#ipt_biangshij').datebox('getValue');
        		if(singtime>biang){
        			$.messager.alert("警告","变更时间不能早于签订时间,请检查!");
        			return ;
        		}
        		var path = getRootName();
        		var uri = path + "/contractmanager/contractChangeInfo_add";
        		var ajax_param = {
        			url : uri,
        			type : "post",
        			datdType : "json",
        			data : {
        			    contractId:contractid,
        			    title:$('#ipt_biangetitle').val(),
        			    changeTime:$('#ipt_biangshij').datebox('getValue'),
        			    description:$('#ipt_bgnr').val(),
        			    confirmManName:$('#qrr').val(),
        			    url:upload_path,
        				"t" : Math.random()	
        			},
        			error : function() {
        				$.messager.alert("错误", "ajax_error", "error");
        			},
        			success : function(data) {
        				if (true == data || "true" == data) {
        					reloadTableCommon('edit_contract_biange_Record');
        					parent.$('#popWin2').dialog('close');
        					
        				} else {
        					reloadTableCommon('edit_contract_biange_Record');
        					$.messager.alert("提示", "合同变更信息添加失败！", "error");
        					
        				}
        			}
        		}
        		ajax_(ajax_param);
        	}
        }

     })();
  </script>
  </body>
</html>
