<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <title>数据对象新增/修改</title>
    <style type="text/css">
      #fm{ margin:0; padding:10px 30px; } 
      .fitem{ margin-bottom:5px; } 
      .fitem label{ display:inline-block; width:80px; }
      .fitem input{ width:160px; }
      .line{ margin:0;padding:0; width:500px;height:1px;background-color:#303030;overflow:hidden;}
    </style>
  </head>
  <body>
      <form id="fm">
        <input id="id" name="id" type="hidden">
        <div id="objectNameDiv" class="fitem" fbs="true">
          <label>对象名称:</label>
          <input id="objectName" name="objectName" type="text" class="easyui-validatebox" data-options="required:true"><dfn>*</dfn>
        </div>
        <div id="typeSelectDiv" class="fitem" fbs="true">
          <label>类型:</label>
          <input id="typeSelect" name="type" class="easyui-combobox" data-options="data:[{value:'1',text:'表'},{value:'2',text:'视图'},{value:'3',text:'自定义SQL'}]" disabled="disabled"/>
        </div>
        <div id="noteDiv" class="fitem" fbs="true">
          <label>说明:</label>
          <input id="note" name="note" type="text" style="width:407px">
        </div>
        <div id="SQLDiv" class="fitem" fbs="true">
          <label>SQL:</label>
          <textarea id="fbsSql" name="fbsSql" rows="10" cols="65" onChange="changedSql(event)" style="vertical-align:top" class="easyui-validatebox" data-options="required:true"></textarea><dfn>*</dfn>
        </div>
        
        <div class="line"></div>
        <br/>
        <a href="javascript:void(0)" class="easyui-linkbutton" style="width:85px;" onclick="getDataObjectFieldLabels()">获取字段列表</a>
        <br/><br/>
        
        <table id="fieldLabelDg" class="easyui-datagrid" data-options="rownumbers:true,onClickRow:clickFieldLabelRow" style="overflow:auto;width:100%;height:170px;">
	      <thead>
	        <tr>
	          <th data-options="field:'propertyName',width:'33%',align:'center'">英文名称</th>  
	          <th data-options="field:'displayTitle',width:'33%',align:'center',editor:'text'">中文名称</th>
	          <th data-options="field:'note',width:'31%',editor:'text'">说明</th>
	        </tr>
	      </thead>
	    </table>
      <br/><br/>
	  <div id="dlg-buttons" class="dialog-button">
	      <a href="javascript:void(0)" onclick="saveDataObject()">确定</a>
	      <a href="javascript:void(0)" onclick="closeModalWindow()">取消</a>
	  </div>
      </form>
      
      <script type="text/javascript" charset="utf-8" src="../../../../js/platform/system/head.js"></script>
	  <script type="text/javascript">
	      var urlPrefix=rootPath+"/sys/dataSecurity/";
	      var fieldLabelRowIndex, sqlChanged=false, clickedGetFieldLabels=false, sqlGrammarError=false;
	      
	      $(function(){
	    	  loadData();
	      });
	      
	      function loadData(){
	    	  var dataObject = getJsonObj("dataObject");
	          $('#fm').form('load', dataObject);
	          if(dataObject && dataObject.id) {
	        	//若是表或视图类型，则对象名称、类型、SQL和英文名称不能修改
	        	if(dataObject.type == '1' || dataObject.type == '2'){
	        		$('#objectName').attr("readonly","readonly");
	        		$('#fbsSql').attr("readonly","readonly")
	        	}
	        	
	            //再查询字段列表
	            $.ajax({
	                type: "GET",
	                url: urlPrefix+"dataObject/"+dataObject.id+'/fieldLabels',
	                dataType: 'json',
	                contentType: "application/json",
	                success: function(result) {
	              	  // 加载数据字段数据
	              	  loadFieldLabelData(result.data);
	                },
	                error: function(result) {
	                	show_error(result);
	                }
	            });
	          }
	      }
	      
	      // 初始化字段列表表格
	      function loadFieldLabelData(newFieldLabels){
	    	var newLen = newFieldLabels.length;
	    	if(newLen > 0){
	    		// 将原有propertyName、displayTitle和note替换掉现有的
	  	   	 	var oldFieldLabels = $('#fieldLabelDg').datagrid('getData').rows;//原有字段列表
	  	    	var oldFieldLabel, newFieldLabel, propertyName, oldLen=oldFieldLabels.length;
	  	    	for(var i=0; i<newLen; i++){
	  	    		newFieldLabel = newFieldLabels[i];
	  	    		propertyName = newFieldLabel.propertyName.toLowerCase();
	  	    		for(var j=0; j<oldLen; j++){
	  	    			oldFieldLabel = oldFieldLabels[j];
	  	    			if(propertyName == oldFieldLabel.propertyName.toLowerCase()){
	  	    				newFieldLabel.displayTitle = oldFieldLabel.displayTitle;
	  		    			newFieldLabel.note = oldFieldLabel.note;
	  		    			break;
	  	    			}
	  	    		}
	  	    	}
	    	}
	    	$('#fieldLabelDg').datagrid('loadData', newFieldLabels);
	      }
	      
	      // 获取字段列表
	      function getDataObjectFieldLabels(){
	    	  clickedGetFieldLabels = true;
	    	  if(fieldLabelRowIndex != undefined){
	      		$('#fieldLabelDg').datagrid('endEdit', fieldLabelRowIndex);
	      	  }
	    	  $.ajax({
	              type: "GET",
	              url: urlPrefix+'fieldLabels',
	              data: {"sql" : $("#fbsSql").val()},
	              dataType: 'json',
	              contentType: "application/json",
	              success: function(result) {
	            	  loadFieldLabelData(result.data);
	            	  sqlGrammarError = false;
	              },
	              error: function(result) {
	            	  show_error(result);
	            	  sqlGrammarError = true;
	              }
	          });
	      }
	      
	      //单击字段列表行时使该行可编辑
	      function clickFieldLabelRow(index, rowData){
	    	 if(fieldLabelRowIndex != undefined){
	  			$('#fieldLabelDg').datagrid('endEdit', fieldLabelRowIndex);
	  		 }
	  		 $('#fieldLabelDg').datagrid('beginEdit', index);
	  		 fieldLabelRowIndex = index;
	      }
	      
	      function changedSql(e){
	    	  sqlChanged = true;
	    	  clickedGetFieldLabels = false;
	      }
	      
	      // 保存数据对象
	      function saveDataObject() {
	    	if(fieldLabelRowIndex != undefined){
	    		$('#fieldLabelDg').datagrid('endEdit', fieldLabelRowIndex);
	    	}
	    	
	    	if(!$("#fbsSql").val()){
				alertMsg("SQL不能为空！");
	        	return;
	    	}
	    	
	        var fieldLabels = $('#fieldLabelDg').datagrid('getData').rows;
	        if(fieldLabels.length == 0){
				alertMsg("字段列表不能为空！");
	        	return;
	        }
	        
	        if(sqlChanged && !clickedGetFieldLabels){
				alertMsg("请重新获取字段列表！");
	        	return;
	        }
	        
	        if(sqlGrammarError){
				alertMsg("SQL语法错误！");
	        	return;	
	        }
	        
	        var jsonObj = $('#fm').serializeObject();
	        jsonObj.type = $('#typeSelect').combobox('getValue');//disabled的值不能传递给后台
	        jsonObj.fieldLabels = fieldLabels;
	        $.ajax({
	          type: jsonObj.id ? "PUT": "POST",
	          url: urlPrefix+"dataObject",
	          data: JSON.stringify(jsonObj),
	          dataType: 'json',
	          contentType: "application/json",
	          beforeSend: function() {
	            return $("#fm").form('validate');
	          },
	          success: function(result) {
				  alertMsg(result.message, null, function () {
	            	closeModalWindow(true); //关闭窗口并刷新父页面
	              });
	          },
	          error: function(result) {
	        	  show_error(result);
	          }
	        });
	      }
	  </script>
  </body>
</html>