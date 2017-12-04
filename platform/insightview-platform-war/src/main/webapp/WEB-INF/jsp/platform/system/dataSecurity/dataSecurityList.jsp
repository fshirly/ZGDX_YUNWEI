<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <title>数据权限设置</title>
    <style type="text/css">
      .tree-node-setted{ background: #a4e9c1; }
      
    </style>
    <script type="text/javascript" charset="utf-8" src="../../../../js/platform/system/head.js"></script>
  </head>
  <body>
  	<input type="hidden" id="dataObjectId" name="dataObjectId"/>
    <div class="easyui-layout" fit="true" style="width:100%;" >
    	<div data-options="region:'west',title:'角色',toolBar:'#toolBar'" style="width: 20%;">
		    <div id="toolBar" >
			  <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add'"  onclick="$('#roleTree').tree('expandAll');">展开</a>
			  <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove'"  onclick="$('#roleTree').tree('collapseAll');">收缩</a>
		    </div>
		    
		    <ul id = "roleTree"  class="easyui-tree" style="background-color: #DDDFE0;height: 100%;"/>
	    </div>
	    <div data-options="region:'center',title:'权限条件',style:{marginLeft:1}">
    		<div>
		        <!-- 右侧grid -->
		        <table id="dataSecurityGrid" class="easyui-datagrid" data-options="rownumbers:true,onClickRow:clickSecurityItemRow,toolbar:securityItemToolbar"></table>
			    
			    <div id="securityItemToolbar" >
				  <a href="javascript:void(0)" id="newItemRow" class="easyui-splitbutton" data-options="menu:'#menu', iconCls:'icon-add'" onClick="createSecurityItemRow()">新增</a>   
				  <div id="menu" style="width:150px;" onShow="menuShow()">
				  </div> 
					
				  <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove', plain:true, onClick:deleteSecurityItemRows">删除</a>
			    </div>
	    		
	    	</div>
			<div class="dialog-button">
		      <a href="javascript:void(0)" onclick="saveDataSecurity()">确定</a>
		      <a href="javascript:void(0)" onclick="closeModalWindow()">取消</a>
		    </div>
	    </div>
	</div>
	
    <script type="text/javascript">
      var urlPrefix=rootPath+"/sys/dataSecurity/";
      var rowIndex, preNodeId, nodes, j=0, firstNode;
      var leftBrackets,operators,rightBrackets,logicSymbols,propertyNames=[];
      
      $(function(){
          var dataObjectId = getJsonObj("dataObjectId");
          $("#dataObjectId").val(dataObjectId);
          
          // 初始化左侧角色树
          initRoleTree(dataObjectId);
          
          // 初始化比较值
          initComparationValues();
          
          // 初始化权限条件各下拉框值
          initComboboxs(dataObjectId);
          
          // 初始化右侧DataGrid
          initDataGrid();
          
          $('#menu').menu({    
       	    onClick:function(item){    
       	    	menuClick(item);   
       	    }    
       	  });  
      });
      
   	  function initRoleTree(dataObjectId){
          $.ajax({
              type: "GET",
              url:urlPrefix+"dataObject/"+dataObjectId+"/roleWithDataSecuritys",
              dataType: 'json',
              contentType: "application/json",
              success: function(result) {
                var fileds = "id,dataSecurityId,dsItems";
                nodes = ConvertToTreeJson(result.data, "id", "pid", "roleName", fileds);
                findFirstNode(nodes);
                var dsItems;
                if(firstNode){
              	  preNodeId = firstNode.id;
  	              dsItems = firstNode.dsItems;
  	              $("#dataSecurityGrid").datagrid("loadData", {"total":dsItems.length, rows: dsItems});
                }
                $("#roleTree").tree({ 
              	  data:nodes,
              	  onClick: function(node){
              		   var nodeId = node.id;
              		   if(preNodeId == undefined){
              			   preNodeId = nodeId;
              			   return;
              		   }
              		   if(nodeId == preNodeId){
              			  return;
              		   }
              		  
              		   // 保存上一个角色的编辑信息
  	          		   if(!endEditSecurityItemRow()){
  	          			 var nodeDomId = $('#roleTree').tree('find', nodeId).domId;
  	          			 $("#"+nodeDomId).removeClass("tree-node-selected");
  	          		     var preNodeDomId = $('#roleTree').tree('find', preNodeId).domId;
  	          			 $("#"+preNodeDomId).addClass("tree-node-selected");
  	          		  	 return;
  	          		   }
              		
      	               var rows = $('#dataSecurityGrid').datagrid('getRows');
      	               if(rows.length != 0){
  	    	               var preNodeDomId = $('#roleTree').tree('find', preNodeId).domId;
      	            	   $("#"+preNodeDomId).addClass("tree-node-setted");
      	               }
      	               var nodeDomId = $('#roleTree').tree('find', nodeId).domId;
      	               $("#"+nodeDomId).removeClass("tree-node-setted");
      	               
         					var preNode = $('#roleTree').tree('find', preNodeId);
     						$('#roleTree').tree('update', {target: preNode.target, dsItems: rows});
     						preNodeId = nodeId;
             		  		
  	            		// node.dsItems填充表格
  	            		dsItems = node.dsItems;
  	            		$('#dataSecurityGrid').datagrid('loadData', {"total":dsItems.length, rows: dsItems});
  	            		rowIndex = undefined;
  	          	  }
                });
                
                if(firstNode){
  	          	  var roots=$('#roleTree').tree('getRoots');
  	              highlightTreeNode(roots, firstNode.id, true);
                }
              },
              error: function(data) {
				  alertMsgError(result.errorCode + ": " + result.message);
              }
          });
   	  }
   	  
   	  // 找到第一个被设置过数据权限的角色节点（用于页面加载时默认展示右侧的权限条件）
   	  function findFirstNode(nodes){
        var node;
        for(var i=0, len=nodes.length; firstNode==undefined && i<len; i++){
      	  node = nodes[i]; 
      	  if(node.dataSecurityId){
      		firstNode = node;
      		return false;
      	  } else if(node.children){
      		findFirstNode(node.children);
      	  }
        }
   	  }
   	  
   	  /**
   	  *使设置过权限条件的节点高亮显示
   	  * @param isIterateChildren是否遍历子节点 
   	  */
   	  function highlightTreeNode(treeNodes, selectedNodeId, isIterateChildren){
   		var treeNode, nodeId;
   		for(var i=0, len=treeNodes.length; i<len; i++){
        	treeNode = treeNodes[i];
	    	if(treeNode.dataSecurityId){
	    		nodeId = treeNode.id;
        		var nodeDomId = $('#roleTree').tree('find', nodeId).domId;
	        	if(nodeId == selectedNodeId){
    	          	$("#"+nodeDomId).addClass("tree-node-selected");
	        	} else{
		          	$("#"+nodeDomId).addClass("tree-node-setted");
	        	}
	    	}
	    	if(isIterateChildren){
	    		if(treeNode.children && treeNode.children.length > 0){
	    			highlightTreeNode($('#roleTree').tree('getChildren', treeNode.target), selectedNodeId, false);
	    		}
	    	}
        }
   	  }
   	  
   	  function initComparationValues(){
          $.ajax({
              type: "GET",
              url: urlPrefix+"dataSecurityItem/comparationValues",
              dataType: 'json',
              contentType: "application/json",
              success: function(result) {
              	$('#menu').empty();
              	// 构建菜单项
              	var values = result.data, value;
              	for(var i=0, len=values.length; i<len; i++){
              		value = values[i]; 
  	            	$('#menu').menu('appendItem', {text: value.name, id:value.code});
              	}
              },
              error: function(result) {
            	  show_error(result);
              }
          });
   	  }
   	  
   	  function initComboboxs(dataObjectId){
		$.ajax({
          type: "GET",
          url: urlPrefix+"dataSecurityItem/comboboxValues",
          dataType: 'json',
          async:false,
          contentType: "application/json",
          success: function(result) {
        	leftBrackets = result.leftBrackets;
        	operators = result.operators;
        	rightBrackets = result.rightBrackets;
        	logicSymbols = result.logicSymbols;
          },
          error: function(result) {
        	  show_error(result);
          }
        });
   		//propertyNames = [];
   		$.ajax({
            type: "GET",
            url: urlPrefix+"dataObject/"+dataObjectId+'/fieldLabels',
            dataType: 'json',
            async:false,
            contentType: "application/json",
            success: function(result) {
          	  initPropertyNames(result.data);
            },
            error: function(result) {
            	show_error(result);
            }
        });
   	  }
   	  
   	  // 初始化权限条件属性名称下拉框
   	  function initPropertyNames(fieldLabels){
   		  var propertyName, value, text;
   		  for(var i=0, len=fieldLabels.length; i<len; i++){
   			propertyName = {};
   			value = fieldLabels[i].propertyName;
   			propertyName.value = value;
   			text = fieldLabels[i].displayTitle;
   			if(!text){
   				text = value;
   			}
   			propertyName.text = text;
   			propertyNames.push(propertyName); 
   		  }
   	  }
   	 
   	  function initDataGrid(){
          $("#dataSecurityGrid").datagrid({
           	columns:[[
           	    {field:'ck', checkbox:true},
           	    {field:'leftBracket', title:'左括号', align:'center', width:'11%', editor:{type:'combobox', options:{data:leftBrackets, valueField:'value', textField:'text', panelHeight:'auto'}}, formatter:function(value, row, index){
           	    	return showTextByValue(value, leftBrackets);
           	    }},
           	    {field:'propertyName', title:'属性名称', align:'center', width:'14%', editor:{type:'combobox', options:{data:propertyNames, valueField:'value', textField:'text', panelHeight:'auto'}}, formatter:function(value, row, index){
           	    	return showTextByValue(value, propertyNames);
           	    }},
           	    {field:'operator', title:'比较符', align:'center', width:'14%', editor:{type:'combobox', options:{data:operators, valueField:'value', textField:'text', panelHeight:'auto'}}, formatter:function(value, row, index){
           	    	return showTextByValue(value, operators);
           	    }},
           	    {field:'value', title:'比较值', align:'center', width:'18%', editor:{type:'textbox', options:{required:true}}},
               	{field:'rightBracket', title:'右括号', align:'center', width:'10%', editor:{type:'combobox', options:{data:rightBrackets, valueField:'value', textField:'text', panelHeight:'auto'}}, formatter:function(value, row, index){
           	    	return showTextByValue(value, rightBrackets);
           	    }},
               	{field:'logicSymbol', title:'逻辑符', align:'center', width:'14%', editor:{type:'combobox', options:{data:logicSymbols, valueField:'value', textField:'text', panelHeight:'auto'}}, formatter:function(value, row, index){
           	    	return showTextByValue(value, logicSymbols);
           	    }},
               	{field:'note', title:'说明', align:'center', width:'14%', editor:{type:'textbox'}}
           	]]
         });
   	  }
   	  
   	  // 传入value，显示text
   	  function showTextByValue(value, jsonAry){
   		if(!value){
   			return;
   		}
   		var jsonObj;
    	for(var i=0, len=jsonAry.length; i<len; i++){
    		jsonObj = jsonAry[i];
    		if(jsonObj.value == value){
    			return jsonObj.text;
    		}
    	}
    	return value;
   	  }
   	   
   	  //创建一行权限条件
   	  function createSecurityItemRow(){
   		 if(preNodeId == undefined){
			 alertMsg("请选中角色进行设置！");
   		  	return;
   		 }
		 
   		 if(!endEditSecurityItemRow()){
	  		return;
	  	 }
   		 
		 var index=1;
		 var row = $('#dataSecurityGrid').datagrid('getRows');
		 if(row){
			 index = row.length; 
		 }
		$('#dataSecurityGrid').datagrid('insertRow', {index: index,row:{}});
		$('#dataSecurityGrid').datagrid('beginEdit', index);
		
		// 赋初始值
		var propertyName = $('#dataSecurityGrid').datagrid('getEditor', {index:index, field:'propertyName'});
		$(propertyName.target).combobox('setValue', propertyNames[0].value);
		
		var operator = $('#dataSecurityGrid').datagrid('getEditor', {index:index, field:'operator'});
		$(operator.target).combobox('setValue', operators[0].value);
		
		var logicSymbol = $('#dataSecurityGrid').datagrid('getEditor', {index:index, field:'logicSymbol'});
		$(logicSymbol.target).combobox('setValue', logicSymbols[0].value);
		
		rowIndex = index;
   	  }
   	  
   	  // 点击权限条件菜单项触发事件
   	  function menuClick(item){
   		if(rowIndex == undefined) {
			alertMsg("请选中行进行赋值！");
   		  	return;
   		}
       	
   		//$('#dataSecurityGrid').datagrid('endEdit', rowIndex);
   		//endEditSecurityItemRow();
   		//$('#dataSecurityGrid').datagrid('updateRow',{index:rowIndex, row:{value:item.id}});
   		//$('#dataSecurityGrid').datagrid('selectRow', rowIndex).datagrid('beginEdit', rowIndex);
   		// 上面只想更新比较值列，但会清空除比较值外的其它列，只好用下面的方式将整行值保存起来再整行替换（TODO:是easyui的bug还是还有我不知道的更优雅的方式？）
   		var rowField = $('#dataSecurityGrid').datagrid('getEditor', {index:rowIndex, field:'leftBracket'});
		var leftBracketText = $(rowField.target).combobox('getText');
   		rowField = $('#dataSecurityGrid').datagrid('getEditor', {index:rowIndex, field:'propertyName'});
		var propertyNameText = $(rowField.target).combobox('getText');
		rowField = $('#dataSecurityGrid').datagrid('getEditor', {index:rowIndex, field:'operator'});
		var operatorText = $(rowField.target).combobox('getText');
		rowField = $('#dataSecurityGrid').datagrid('getEditor', {index:rowIndex, field:'logicSymbol'});
		var logicSymbolText = $(rowField.target).combobox('getText');
		rowField = $('#dataSecurityGrid').datagrid('getEditor', {index:rowIndex, field:'rightBracket'});
		var rightBracketText = $(rowField.target).combobox('getText');
		rowField = $('#dataSecurityGrid').datagrid('getEditor', {index:rowIndex, field:'note'});
		var noteText = $(rowField.target).textbox('getText');
			
   		$('#dataSecurityGrid').datagrid('updateRow', {index:rowIndex, row:{
   			leftBracket:leftBracketText,
			propertyName:propertyNameText,
			operator:operatorText,
			value:item.id,
			logicSymbol:logicSymbolText,
			rightBracket:rightBracketText,
			note:noteText
		}});
   		
   		$('#dataSecurityGrid').datagrid('endEdit', rowIndex).datagrid('beginEdit', rowIndex);
   	  }
   	  
   	  // 若菜单没内容则提示去数据字典配置
   	  function menuShow(){
   		  if(!$("#menu").html()){
			  alertMsg('请配置类型名为"权限条件比较值"的数据字典！');
   		  }
   	  }
   	  // 删除多行权限条件
   	  function deleteSecurityItemRows(){
   		var selectedRows = $('#dataSecurityGrid').datagrid('getSelections');
   		var len = selectedRows.length;
		if(len == 0){
			alertMsg("请选中行进行删除！");
			return;
		}
   		alertConfirm('提示','确定删除?',function(r){
			if (r){
				var index;
				for(var i=0; i<len; i++){
					index = $('#dataSecurityGrid').datagrid('getRowIndex', selectedRows[i]);
			        $('#dataSecurityGrid').datagrid('deleteRow', index);
				}
				rowIndex = $('#dataSecurityGrid').datagrid('getRows').length; 
				if(rowIndex == 0){//剩余行为0，即被全部删除
					$('#dataSecurityGrid').datagrid('uncheckAll');// 将全选按钮置为未勾选
					rowIndex = undefined;
				} else{
					--rowIndex;
				}
			}
		});
   	  }
   	  
   	  //单击权限条件表格时使该行可编辑
   	  function clickSecurityItemRow(index, rowData){
   		 if(!endEditSecurityItemRow()){
	  		return;
	  	 }
		 $('#dataSecurityGrid').datagrid('beginEdit', index);
		 rowIndex = index;
   	  }
   	  
   	  function endEditSecurityItemRow(){
   		if(rowIndex != undefined){
			//if(!$('#dataSecurityGrid').datagrid('getData').rows[rowIndex].value){
			// 上面的代码不准，会出现将比较值清空后还会得到原来的值的情况
			var rowField = $('#dataSecurityGrid').datagrid('getEditor', {index:rowIndex, field:'value'});
			if(rowField){
				var valueText = $(rowField.target).textbox('getText');
				if(!valueText){
					alertMsg('比较值不能为空！');
					return false;
				}
				
				$('#dataSecurityGrid').datagrid('endEdit', rowIndex);
			}
		 }
   		 return true;
   	  }
   	  
   	  function saveDataSecurity(){
   		if(preNodeId == undefined){
   			// 用户未进行任何操作则直接关闭窗口
   			//closeWindow();
   			closeModalWindow();
            return;
   		}
   		
        // 保存最后一个选择的角色的条件数据
   		if(!endEditSecurityItemRow()){
	  		return;
	  	}

        var role = $('#roleTree').tree('find', preNodeId);
   		var rows = $('#dataSecurityGrid').datagrid('getRows');
		$('#roleTree').tree('update', {target: role.target, dsItems: rows});
        
        //将新数据更新到数据库替换老数据
        var roots=$('#roleTree').tree('getRoots'), dataSecuritys=new Array(), dataObjectId=$("#dataObjectId").val();
        j=0;
        // 创建要传给后台保存的数据权限集
        createDataSecuritys(roots, dataSecuritys, dataObjectId, true);
        // 将权限条件中的text转换成value
        convertItemsTextToValue(dataSecuritys);
        $.ajax({
          type: "PUT",
          url: urlPrefix+"dataObject/"+dataObjectId+"/dataSecuritys",
          data: JSON.stringify(dataSecuritys),
          dataType: 'json',
          contentType: "application/json",
          success: function(result) {
			  alertMsg(result.message);
              closeModalWindow();
          },
          error: function(result) {
        	  show_error(result);
          }
        });
   	  }
   	  
   	  /**
   	  * @param isIterateChildren 是否遍历子节点
   	  */
   	  function createDataSecuritys(treeNodes, dataSecuritys, dataObjectId, isIterateChildren){
   		var treeNode, dsItems, ds;
   		for(var i=0, len=treeNodes.length; i<len; i++){
        	treeNode = treeNodes[i];
	   		dsItems = treeNode.dsItems;
	    	if(dsItems.length > 0){
	    		ds = {};
	    		ds.dataobjectId = dataObjectId;
	    		ds.roleId = treeNode.id;
	    		ds.dsItems = dsItems;
	    		dataSecuritys[j++] = ds;
	    	}
	    	if(isIterateChildren){
	    		if(treeNode.children && treeNode.children.length > 0){
	    			createDataSecuritys($('#roleTree').tree('getChildren', treeNode.target), dataSecuritys, dataObjectId, false);
	    		}
	    	}
        }
   	  }
   	  
   	  function convertItemsTextToValue(dataSecuritys){
   		  var len = dataSecuritys.length; 
   		  if(len == 0){
   			  return;
   		  }
   		  var i, j, k, size, dsItems, dsItem, propertyName, operator, logicSymbol;
   		  for(i=0, len=dataSecuritys.length; i<len; i++){
   			  dsItems = dataSecuritys[i].dsItems; 
   			  for(j=0, size=dsItems.length; j<size; j++){
   				  dsItem = dsItems[j];
   				  propertyName = dsItem.propertyName;
   				  operator = dsItem.operator;
   				  logicSymbol = dsItem.logicSymbol;
   				  for(k=0, len1=propertyNames.length; k<len1; k++){
   					  if(propertyName == propertyNames[k].text){
   						dsItem.propertyName = propertyNames[k].value;
   						break;
   					  }
   				  }
 				  for(var k=0, len1=operators.length; k<len1; k++){
 					  if(operator == operators[k].text){
 						 dsItem.operator = operators[k].value;
 						 break;
 					  }
 				  }
 				  for(var k=0, len1=logicSymbols.length; k<len1; k++){
					  if(logicSymbol == logicSymbols[k].text){
						 dsItem.logicSymbol = logicSymbols[k].value;
						 break;
					  }
				  }
   			  }
   		  }
   	  }
   	  
   	  // 关闭权限设置页面
   	  function closeWindow(){
   		parent.closeWindow();
   	  }
    </script> 
  </body>
</html>