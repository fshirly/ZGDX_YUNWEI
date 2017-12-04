<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!DOCTYPE html>
<html>
  <head>
    <title>系统日志配置</title>
    <style type="text/css">
      .body{margin-left:100px;margin-top:20px;}
      .ftitle{font-size:14px; font-weight:bold;}
      .separator{width:580px; margin-bottom:30px; border-bottom:1px solid #ccc; }  
      .fitem label{ display:inline-block; width:80px; }
      .fitem .input{width:257px; }
      .fitem1{margin:10px}
    </style>
    <script type="text/javascript" charset="utf-8" src="../../../js/platform/system/head.js"></script>
  </head>
  <body onload="queryLogConfig()" style="height: 95%">
  	<div class="body">
		<form id="logConfigFm">
			<div>
				<div class="ftitle">系统日志配置</div>
		        <div class="fitem">
	       		  <input id="id0" name="logConfigs[0].id" type="hidden">
	       		  <input id="type0" name="logConfigs[0].type" type="hidden" value="1">
		          <div class="fitem1">
			          <label>启用:</label>
			          <input id="isStart0" type="checkbox" name="logConfigs[0].isStart" value="1"/>
		          </div>
		          
		          <div class="fitem1">
			          <label>运行时间:</label>
			          <input id="timeCfg0" type="text" name="logConfigs[0].timeCfg" class="input"/>
			          
			          <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-help', plain:true,onClick:aboutCron" title="点击查看Cron表达式简介"></a>
		          </div>
		          
		          <div class="fitem1">
			          <label>说明:</label>
			          <textarea id="note0" name="logConfigs[0].note" rows="2" cols="40" class="easyui-validatebox"  data-options="validType:'maxLength[100]'" >  

			         </textarea>
			      </div>
		        </div>
		        <div class="separator"></div>
	        </div>
	        
	        <div style="display: none">
		        <div class="ftitle">系统操作日志</div>
		        <div class="fitem">
		        	<input id="id1" name="logConfigs[1].id" type="hidden">
		        	<input id="type1" name="logConfigs[1].type" type="hidden" value="2">
		        	<div class="fitem1">
			          <label>启用:</label>
			          <input id="isStart1" type="checkbox" name="logConfigs[1].isStart" value="1"/>
			        </div>
			        
			        <div class="fitem1">
			          <label>运行时间:</label>
			          <input id="timeCfg1" type="text" name="logConfigs[1].timeCfg" class="input"/>
			          <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-help', plain:true, onClick:aboutCron" title="点击查看Cron表达式简介"></a>
			        </div>
			        
			        <div class="fitem1">  
			          <label>说明:</label>
			          <textarea id="note1" name="logConfigs[1].note" rows="2" cols="40"></textarea>
			        </div>
		        </div>
		        <div class="separator"></div>
		    </div>
	        
	        <div style="display: none">
		        <div class="ftitle">系统异常日志</div>
		        <div class="fitem">
		        	<input id="id2" name="logConfigs[2].id" type="hidden">
		        	<input id="type2" name="logConfigs[2].type" type="hidden" value="3">
		        	<div class="fitem1">
			          <label>启用:</label>
			          <input id="isStart2" type="checkbox" name="logConfigs[2].isStart" value="1"/>
			         </div>
		          
		          	<div class="fitem1">
			          <label>运行时间:</label>
			          <input id="timeCfg2" type="text" name="logConfigs[2].timeCfg" class="input"/>
			          <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-help', plain:true, onClick:aboutCron" title="点击查看Cron表达式简介"></a>
			        </div>
			        
			        <div class="fitem1">
			          <label>说明:</label>
			          <textarea id="note2" name="logConfigs[2].note" rows="2" cols="40"></textarea>
			        </div>
		        </div>
		        <div class="separator"></div>
		    </div>
	        
	        <a href="javascript:void(0)" class="easyui-linkbutton c6" data-options="iconCls:'icon-ok', onClick:saveLogConfigs" style="width:90px;margin-top:10px;margin-left:120px">应用</a>
	    </form>
	  </div>
	  
	  <div id="aboutCronWin"></div>
	  
	  <script type="text/javascript">
	  
	  $(document).ready(function(){
		  $.extend($.fn.validatebox.defaults.rules, {
              maxLength: {     
                  validator: function(value, param){     
                      return param[0] >= value.length;     
                  },     
                  message: '请输入最大{0}位字符.'
              }     
          });  
	  });
	  
	    	var urlPrefix=rootPath+"/sys/systemLog/";
	    	
	    	// 查询日志配置
	    	function queryLogConfig(){
	    		$.ajax({
	   	          type: "GET",
	   	          url: urlPrefix+"logConfigs",
	   	          dataType: 'json',
	   	          contentType:'application/json',
	   	          success: function(result){
	   	        	// 加载数据
	   	        	//$('#logConfigFm').form('load', result.data);
	   	        	
	   	        	var logCfgs = result.data, logCfg, index, isStart;
	   	        	for(var i=0, len=logCfgs.length; i<len; i++){
	   	        		logCfg = logCfgs[i];
	   	        		index = logCfg.type - 1;
	   	        		$("#id"+index).val(logCfg.id);
	   	        		isStart = logCfg.isStart;
	   	        		if(isStart == 1){
		   	        		$("#isStart"+index).attr("checked", true);
	   	        		}
	   	        		$("#timeCfg"+index).val(logCfg.timeCfg);
	   	        		$("#note"+index).val(logCfg.note);
	   	        	}
	   	          },
	   	          error: function(data) {
	   	          }
	   	        }); 
	    	}
	    	
	    	function saveLogConfigs(){
	    		if(!$("#logConfigFm").form('validate'))
	    			  return;
	    		
	    		$("input[type=checkbox]").each(function(){
	    			if($(this).is(':checked')){
	    				$(this).val("1");
	    			} else{
	    				$(this).val("0");
	    			}
	    		});
	    		
	    		/*$('#logConfigFm').attr('method', 'POST');  
	   	     	$('#logConfigFm').attr('action', urlPrefix+"logConfigs");  
	    		$("#logConfigFm").submit();*/
	    		// 可以像上面那样提交表单，后台再定义一个包含List<LogConfigBean>的对象接受传值，但前台不好获知后台处理结果
	    		var logCfgs = [];
	    		for(var i=0; i<3; i++){
	    			logCfgs.push({
	    				id:$("#id"+i).val(),
	    				type:$("#type"+i).val(),
	    				isStart:$("#isStart"+i).val(),
	    				timeCfg:$("#timeCfg"+i).val(),
	    				note:$("#note"+i).val()
	    			});
	    		}
	    		$.ajax({
	     	          type: "POST",
	     	          url: urlPrefix+"logConfigs",
	     	          data:JSON.stringify(logCfgs),
	     	          dataType: 'json',
	     	          contentType:'application/json',
	     	          success: function(result){
	     	        	alertMsg(result.message);
	     	          },
	     	          error: function(data) {
	     	          }
	     	    });
	    	}
	    	
	    	function aboutCron(){
	  			showWindow('aboutCronWin', urlPrefix+'cron/html', "cron表达式简介", 880, 500);
	    	}
	  </script>
  </body>
</html>