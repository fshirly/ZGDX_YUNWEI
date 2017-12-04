<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="UTF-8">
<title>listview管理</title>
<script type="text/javascript" charset="utf-8" src="../../../../js/platform/system/head.js"></script>


<script type="text/javascript">

	var listviewName = "listviewManager";

	/**
	预览EasyUI
	*/
	var previewData = {};
	function previewEasyUi(){
		
		var jsonObj = $('#fm').serializeObject();
		
		var conditionObj = $('#dg1').datagrid('getRows');
		
		var colsObj = $("#fieldLabelsdg").datagrid('getRows');
		
		previewData = {
			"basic" : jsonObj ,
			"condition" : conditionObj,
			"cols" : colsObj
		}; 
		
		//alert("敬请期待")
		//openModalWin(rootPath + '/tag/listviewQuery/listview/preview/html', "查询配置-预览", 1024, 768 , 'no' , 'second');
		//window.open (rootPath + '/tag/listviewQuery/listview/previewEasyUi/html','查询配置-预览','height=768,width=1024,top=0,left=0,toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no')
		setJsonObj('previewData', previewData);
		OpenWin(rootPath + '/tag/listviewQuery/listview/previewEasyUi/html', "查询配置-预览", 900, 600);
	}
	
	/**
	*预览OniUi
	**/
	function previewOniUi(){
		
			/* window.top.$.messager.alert('提示','敬请期待！');
			return false; */
			
			var jsonObj = $('#fm').serializeObject();
			
			var conditionObj = $('#dg1').datagrid('getRows');
			
			var colsObj = $("#fieldLabelsdg").datagrid('getRows');
			
			previewData = {
				"basic" : jsonObj ,
				"condition" : conditionObj,
				"cols" : colsObj
			}; 
			
			//openModalWin(rootPath + '/tag/listviewQuery/listview/preview/html', "查询配置-预览", 1024, 768 , 'no' , 'second');
			window.open (rootPath + '/tag/listviewQuery/listview/previewOniUi/html','查询配置-预览','height=768,width=1024,top=0,left=0,toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no') 
		
			//setJsonObj('previewData', previewData);
			//OpenWin(rootPath + '/tag/listviewQuery/listview/previewOniUi/html', "查询配置-预览", 1024, 768 , 'yes');
		}

	/**
	 * 保存
	 */
	function saveListview() {
		
		if(!endEditing() ||　!endEditing1() ){
			window.top.$.messager.alert('提示','请检查条件DataGrid输入！');
			return false;
		}
		
		var jsonObj = $('#fm').serializeObject();
		
		var conditionObj = $('#dg1').datagrid('getRows');
		
		var colsObj = $("#fieldLabelsdg").datagrid('getRows');
		
		 var data = {
			"basic" : jsonObj ,
			"condition" : conditionObj,
			"cols" : colsObj
		}; 
		 
		 var aaa = [];
		 aaa[0]=jsonObj;
		 aaa[1]=jsonObj;
		 
		 var data2 = {
				 "basic" : jsonObj ,
				 "basic2" : jsonObj ,
				 "aaa" : aaa
		 }
		 console.log(JSON.stringify(data));
		 
		 
		$.ajax({
			type : jsonObj.id ? "PUT" : "POST",
			url : rootPath + "/tag/listviewManager/listview",
			data : JSON.stringify(data),
			dataType : 'json',
			contentType : "application/json",
			beforeSend : function() {
				return $("#fm").form('validate');
			},
			success : function(result) {
				if (result.success) {
					window.top.$.messager.alert("提示信息", result.message , 'success' , function(){
						//$('#dlg').dialog('close'); //close the dialog
						/* try{
							closeModalWinoOff();
						}catch(e){
							if(jsonObj.id){
								parent.closeDialog("update");
							}else{
								parent.closeDialog("add");
							}
							return;
						} */
						
						/* closeModalWinoOff_compatible();
						//$('#dg').datagrid('reload'); //reload the user data
						if(parent.query != undefined){
							parent.query(1,10);	
						}else if(parent.listviewMethod != undefined) {
							parent.listviewMethod["refresh"](listviewName);
							//refreshListview(listviewName);
						}else{
							parent.refreshGrid();
						} */
						
						closeModalWindow(true);
					});
					
				} else {
					window.top.$.messager.alert("提示信息", result.errorCode + ":"
							+ result.message);
				}
			},
			error : function(result) {
				show_error(result);
			}
		});
	}

	$(function() {
		
		//var id = parent.listviewId.id;
		var id = getJsonObj("listviewId").id;
		
		$('#fieldLabelsdg').datagrid({
			//url : "../../listview/cols/"+dataObjectId,
			//method : 'GET',
			iconCls: 'icon-modify',
			singleSelect:true,
			autoRowHeight : true,
			fitColumns: true,
			striped : true,
			nowrap : false,
	        resizeHandle : 'both',
			rownumbers: true,
			pagination: false,
			scrollbarSize : 10,
			//queryParams:{fid: $("#form1 input[name='FID']").val()},
			//toolbar:'#tb',
			columns:[[
					{field:'propertyName',title:'属性名称',width:150,
						editor:{
							type:'combobox',
							options:{
								editable:false,required:true,panelHeight:'auto'//, 
								//data: result.data , valueField:'propertyName' , textField:'propertyName'
							}
						}
					},
					{field:'displayTitle',title:'显示标题',width:200,
						editor:{
							type:'text'
						}
					},
					{field:'note',title:'备注',width:280,
						editor:{
							type:'text'
						}
					}
				]] ,
			onClickRow: onClickRow1
		});
		
		
		$('#dg1').datagrid({
			//url:window.location.pathname+'/getConditions',
			iconCls: 'icon-modify',
			singleSelect:true,
			autoRowHeight : true,
			fitColumns: true,
			striped : true,
			nowrap : false,
	        resizeHandle : 'both',
			rownumbers: true,
			pagination: false,
			scrollbarSize : 10,
			//queryParams:{fid: $("#form1 input[name='FID']").val()},
			//toolbar:'#tb',
			columns:[[
					{field:'id',title:'id',hidden:true},
					{field:'listviewId',title:'视图名称',hidden:true},
					//{field:'objectname',title:'对象名称',hidden:true},
					//{field:'propertytitle',title:'属性描述',hidden:true},
					{field:'controlType',title:'控件类型',width:70,
						formatter:function(value,row,index){
	                    	if(value=='text'){return '文本框';}else if(value=='select'){return '选择框';}else if(value=='tree'){return '下拉树';}else if(value=='date'){return '日期';}else if(value=='datetime'){return '日期时间';}
	                    },editor:{
							type:'combobox',
							options:{
								editable:false,required:true,panelHeight:'auto', 
								data: [{value:'text',text:'文本框'},{value:'select',text:'选择框'},{value:'tree',text:'下拉树'},{value:'date',text:'日期'},{value:'datetime',text:'日期时间'}]
							}
						}
					},
					{field:'leftBracket',title:'左括号',width:50,
						editor:{
							type:'combobox',
							options:{
								editable:false,required:false,panelHeight:'auto', data: [{value:'',text:'无'},{value:'(',text:'('},{value:'((',text:'(('},{value:'(((',text:'((('}]
							}
						}
					},
					{field:'propertyName',title:'属性名',width:150,
						/* formatter:function(value,row,index){
							//if(row.displayTitle != null && row.displayTitle!=''){return row.displayTitle;}else{return value;}
	                    }, */
						editor:{
							type:'combobox',
							options:{
								required:true
								//url:window.location.pathname+'/getPropertyDDL?objectname='+$("#objectName").combobox('getValue'),editable:true,required:true,panelHeight:'auto',
								//onSelect:function(record){refreshTitle(record.text);},
								//onChange:function(){var s1 = $(this).combobox('getValue');var s2 =$(this).combobox('getText'); if((s1==undefined || s1=='') && s2!=''){$(this).combobox('setValue',s2);}}
							}
						}
					},
					{field:'displayTitle',title:'标题',width:100,editor:{type:'validatebox',options:{required:true}}},
					{field:'operator',title:'比较符',width:70,
						formatter:function(value,row,index){
	                    	if(value=='equal'){return '等于';}else if(value=='notEqual'){return '不等于';}else if(value=='greater'){return '大于';}else if(value=='less'){return '小于';}else if(value=='greaterEqual'){return '大于等于';}else if(value=='lessEqual'){return '小于等于';}else if(value=='leftLike'){return '左匹配';}else if(value=='rightLike'){return '右匹配';}else if(value=='allLike'){return '匹配';}else if(value=='in'){return '包含';}else if(value=='notIn'){return '不包含';}
	                    },
						editor:{
							type:'combobox',
							options:{
								editable:false,required:true, panelHeight:'auto',data: [{value:'equal',text:'等于'},{value:'notEqual',text:'不等于'},{value:'greater',text:'大于'},
								          		 										{value:'less',text:'小于'},{value:'greaterEqual',text:'大于等于'},{value:'lessEqual',text:'小于等于'},
								          		 										{value:'leftLike',text:'左匹配'},{value:'rightLike',text:'右匹配'},{value:'allLike',text:'匹配'},
								          		 										{value:'in',text:'包含'},{value:'notIn',text:'不包含'}]
							}
						}
					},
					{field:'rightBracket',title:'右括号',width:50,
						editor:{
							type:'combobox',
							options:{
								editable:false,required:false,panelHeight:'auto', data: [{value:'',text:'无'},{value:')',text:')'},{value:'))',text:'))'},{value:')))',text:')))'}]
							}
						}	
					},
					{field:'logicSymbol',title:'逻辑符',width:70,
						formatter:function(value,row,index){
	                    	if(value=='and'){return '并且';}else if(value=='or'){return '或者';}
	                    },
						editor:{
							type:'combobox',
							options:{
								editable:false,required:true,panelHeight:'auto', data: [{value:'and',text:'并且'},{value:'or',text:'或者'}]
							}
						}
					},
					{field:'sortOrder',title:'顺序',width:50, editor:{type:'validatebox',options:{required:true}}},
					{field:'sourceType',title:'数据源类型',width:80,
						formatter:function(value,row,index){
	                    	if(value=='0'){return '无';}else if(value=='1'){return '数据字典';}else if(value=='5'){return '单位机构';}else if(value=='6'){return '部门';}
	                    	else if(value=='7'){return '岗位';}else if(value=='8'){return '人员带岗位';}else if(value=='9'){return '人员不带岗位';}else if(value=='10'){return '角色';}
							else if(value=='11'){return '角色人员';}
	                    	else if(value=='2'){return 'JSON';}else if(value=='3'){return 'URL';}else if(value=='4'){return 'SQL';}
	                    },editor:{
							type:'combobox',
							options:{
								editable:false,required:false,panelHeight:'auto', data: [{value:'0',text:'无'},{value:'1',text:'数据字典'}
								,{value:'2',text:'JSON'},{value:'3',text:'URL'},{value:'4',text:'SQL'},{value:'5',text:'单位机构'}
								,{value:'6',text:'部门'},{value:'7',text:'岗位'},{value:'8',text:'人员带岗位'},{value:'9',text:'人员不带岗位'},{value:'10',text:'角色'},{value:'11',text:'角色人员'}]
							}
						}
					}, 
					{field:'listSource',title:'数据源',width:50,
						editor:'text',formatter:function(value,row,index){if(value!=null && value!=''){return '...';}else{return value;} }},
					{field:'defaultValue',title:'默认值',width:50,editor:'text'}//,
					//{field:'isaddall',title:'是否添加全部',hidden:true,editor:'text'}
				]] ,
			onClickRow: onClickRow
		});
		
		
		$.ajax({
			type : "GET",
			url : rootPath + "/tag/listviewManager/dataObjects",
			dataType : 'json',
			contentType : "application/json",
			success : function(result) {
				$('#dataobjectId').combobox({
					data : result.data,
					valueField : 'id',
					textField : 'objectName'
				});
				
				
				$.ajax({
					type : "GET",
					url : rootPath + "/tag/listviewManager/listview/"+id,
					dataType : 'json',
					contentType : "application/json",
					success : function(result) {
						$('#fm').form('load' , result.basic);
						$('#fieldLabelsdg').datagrid('loadData' , result.cols);
						$('#dg1').datagrid('loadData' , result.condition);
					},
					error : function(data) {
						//alert(data);
					}
				});
				
			},
			error : function(data) {
				//alert(data);
			}
		});
		
	});
	
	function getFieldLabels(){
		
		var dataObjectId = $("#dataobjectId").combobox('getValue');
		
		$.ajax({
			type : "GET",
			url : rootPath + "/tag/listviewManager/listview/camelCols/"+dataObjectId,
			dataType : 'json',
			contentType : "application/json",
			success : function(result) {
				
				
				$('#fieldLabelsdg').datagrid('loadData', result.data);
				
			},
			error : function(data) {
				//alert(data);
			}
		});
		
		
	}
	
	var editIndex1 = undefined;
	function endEditing1(){
		if (editIndex1 == undefined){return true;}
		if ($('#fieldLabelsdg').datagrid('validateRow', editIndex1)){
			/* var ed = $('#fieldLabelsdg').datagrid('getEditor', {index:editIndex1,field:'propertyname'});
			var propertyName = $(ed.target).combobox('getText'); */
			$('#fieldLabelsdg').datagrid('endEdit', editIndex1);
			//$('#fieldLabelsdg').datagrid('updateRow',{index:editIndex1,row:{propertytitle:propertyName}});
			editIndex1 = undefined;
			return true;
		} else {
			return false;
		}
	}
	
	function onClickRow1(index){
		if (editIndex1 != index){
			if (endEditing1()){
				$('#fieldLabelsdg').datagrid('selectRow', index)
						.datagrid('beginEdit', index);
				/* var ed = $('#fieldLabelsdg').datagrid('getEditor', {index:index,field:'listsource'});
				$(ed.target).bind('click',function(){
					$('#dd').dialog('open');
					$('#detailText').val($(ed.target).val());
	            });*/
				var ed2 = $('#fieldLabelsdg').datagrid('getEditor', {index:index,field:'propertyName'});
	            var value1 = $(ed2.target).combobox("getValue");
	            
	            var dataObjectId = $("#dataobjectId").combobox('getValue');
	            $.ajax({
	    			type : "GET",
	    			url : rootPath + "/tag/listviewManager/listview/camelCols/"+dataObjectId,
	    			dataType : 'json',
	    			contentType : "application/json",
	    			success : function(result) {
	    				$(ed2.target).combobox({
	    					data : result.data,
	    					valueField : 'propertyName',
	    					textField : 'propertyName'
	    				});
	    				$(ed2.target).combobox("setValue",value1);
	    				
	    			},
	    			error : function(data) {
	    				//alert(data);
	    			}
	    		});
	            //$(ed2.target).combobox('reload',window.location.pathname+'/getPropertyDDL?objectname='+$("#objectName").combobox('getValue'));
				editIndex1 = index; 
			} else {
				$('#fieldLabelsdg').datagrid('selectRow', editIndex1);
			}
		}
	}
	
	
	/////////////
	var editIndex = undefined;
	function endEditing(){
		//alert(editIndex);
		if (editIndex == undefined){return true;}
		if ($('#dg1').datagrid('validateRow', editIndex)){
			//var ed = $('#dg1').datagrid('getEditor', {index:editIndex,field:'propertyName'});
			//var propertyName = $(ed.target).combobox('getText');
			$('#dg1').datagrid('endEdit', editIndex);
			//$('#dg1').datagrid('updateRow',{index:editIndex,row:{displayTitle:propertyName}});
			editIndex = undefined;
			return true;
		} else {
			return false;
		}
	}
	function onClickRow(index){
		if (editIndex != index){
			if (endEditing()){
				$('#dg1').datagrid('selectRow', index)
						.datagrid('beginEdit', index);
				var ed = $('#dg1').datagrid('getEditor', {index:index,field:'listSource'});
				$(ed.target).bind('click',function(){
					$('#dd').dialog('open');
					$('#detailText').val($(ed.target).val());
	            });
				//var ed2 = $('#dg1').datagrid('getEditor', {index:index,field:'propertyName'});
				//$(ed2.target).combobox('reload',window.location.pathname+'/getPropertyDDL?objectname='+$("#objectName").combobox('getValue'));
				
				var dataObjectId = $("#dataobjectId").combobox('getValue');
            	$.ajax({
    			type : "GET",
    			url : rootPath + "/tag/listviewManager/listview/cols/"+dataObjectId,
    			dataType : 'json',
    			contentType : "application/json",
    			success : function(result) {
    				
    				var ed2 = $('#dg1').datagrid('getEditor', {index:editIndex,field:'propertyName'});
    				
    				var value1 = $(ed2.target).combobox("getValue");
    				$(ed2.target).combobox({
    					data : result.data,
    					valueField : 'propertyName',
    					textField : 'propertyName'
    				});
    				$(ed2.target).combobox("setValue",value1);
    				
    			},
    			error : function(data) {
    				//alert(data);
    			}
    		});
				
				editIndex = index;
			} else {
				$('#dg1').datagrid('selectRow', editIndex);
			}
		}
	}
	
	
	function append(){
		if (endEditing()){
			$('#dg1').datagrid('appendRow',{controlType:'text',operator:'allLike',logicSymbol:'and',sortOrder:$('#dg1').datagrid('getRows').length+1});
			editIndex = $('#dg1').datagrid('getRows').length-1;
			$('#dg1').datagrid('selectRow', editIndex)
					.datagrid('beginEdit', editIndex);
			var ed = $('#dg1').datagrid('getEditor', {index:editIndex,field:'listSource'});
			$(ed.target).bind('click',function(){
				$('#dd').dialog('open');
				$('#detailText').val($(ed.target).val());
	        });
            
            var dataObjectId = $("#dataobjectId").combobox('getValue');
            $.ajax({
    			type : "GET",
    			url : rootPath + "/tag/listviewManager/listview/cols/"+dataObjectId,
    			dataType : 'json',
    			contentType : "application/json",
    			success : function(result) {
    				
    				var ed2 = $('#dg1').datagrid('getEditor', {index:editIndex,field:'propertyName'});
    				
    				//var value1 = $(ed2.target).combobox("getValue");
    				$(ed2.target).combobox({
    					data : result.data,
    					valueField : 'propertyName',
    					textField : 'propertyName'
    				});
    				//$(ed2.target).combobox("setValue",value1);
    				
    			},
    			error : function(data) {
    				//alert(data);
    			}
    		});
			//$(ed2.target).combobox('reload',window.location.pathname+'/getPropertyDDL?objectname='+$("#objectName").combobox('getValue'));
		}
	}
	function removeit(){
		if (editIndex == undefined){return;}
		$('#dg1').datagrid('cancelEdit', editIndex)
				.datagrid('deleteRow', editIndex);
		editIndex = undefined;
	}
	
	function setDetailText(){
		var detailText = $('#detailText').val();
		var ed = $('#dg1').datagrid('getEditor', {index:editIndex,field:'listSource'});
		$(ed.target).val(detailText);
		$('#dd').dialog('close');
	}
	
	function refreshOptions(objectname){
		
		var dataObjectId = $("#dataobjectId").combobox('getValue');
		
		$.ajax({
			type : "GET",
			url : rootPath + "/tag/listviewManager/listview/cols/"+dataObjectId,
			dataType : 'json',
			contentType : "application/json",
			success : function(result) {
				
				var length = $('#dg1').datagrid('getRows').length;
				
				for(var x=0;x<length;x++){
					
					var ed = $('#dg1').datagrid('getEditor', {index:x,field:'propertyName'});
					
					if(ed!=null){
						var value = $(ed.target).combobox("getValue");
						
						$(ed.target).combobox({
							data : result.data,
							valueField : 'propertyName',
							textField : 'propertyName'
						});
						
						$(ed.target).combobox("setValue",value);
						//$(ed.target).combobox('reload',window.location.pathname+'/getPropertyDDL?objectname='+objectname);
					}
				}
				
				//
				
			},
			error : function(data) {
				//alert(data);
			}
		});
		
		
	}
	
	
	function closeModalWinoOff_compatible(){
		var jsonObj = $('#fm').serializeObject();
		try{
			//closeModalWinoOff();
			closeModalWindow();
		}catch(e){
			if(jsonObj.id){
				parent.closeDialog("update");
			}else{
				parent.closeDialog("add");
			}
		}
	}
	
	
	
	
</script>
<!-- <style type="text/css">
#fm {
	margin: 0;
	padding: 10px 30px;
}

.ftitle {
	font-size: 14px;
	font-weight: bold;
	padding: 5px 0;
	margin-bottom: 10px;
	border-bottom: 1px solid #ccc;
}

.fitem {
	font-size: 12px;
	margin-bottom: 5px;
}

.fitem label {
	display: inline-block;
	width: 140px;
}

.fitem input {
	width: 160px;
}


/*--- 按钮 ---*/
.btn {width:65px;}
.btnwin a, .btnwin input, .btndiv a.btn {width:80px;}
.btn, .btnwin a, .btnwin input, .btntd a {margin-left:10px; }
.btnwin input {vertical-align:middle;}
.btn, .btnwin a, .btnwin input, .btntd a, .btnchoose input[type="button"] {text-align:center; border:1px solid;}
.btn, .btnwin a, .btnwin input, .btntd a {display:inline-block; height:24px; line-height:24px;}
.btn, .btnwin a, .btnwin input, .btnchoose input[type="button"] {color:#ffffff; border-color:#d7d7d7 #8a8a8a #8a8a8a #d7d7d7; background:#579de2;padding-top:1px;}
 
.btnwin {
	position:absolute;
	bottom:0;
	right:1px;
	left:1px;
	text-align:right;
	padding:8px 10px;
	border-top-width: 1px;
	border-top-style: solid;
	border-top-color: #dbdbdb;
	background-color: #f4f4f4;
}

</style> -->

</head>

<body>
  <div style="height: 524px; overflow: auto" >
	<div style="background-color: #E0ECFF;padding: 5px;position: relative;">
		<div class="panel-title panel-with-icon" >基本信息</div>
	</div>
		<form id="fm">
			<input id="id" name="id" type="hidden">
			<table class="tablecontent">
			<tr>
                <td class="title">标题：</td>
                <td><input type="text" id="title" name="title" style="width: 200px" class="easyui-validatebox" data-options="required:true"><dfn>*</dfn></td>
                <td class="title">英文标志：</td>
                <td><input type="text" id="name" name="name" style="width: 200px" class="easyui-validatebox" data-options="required:true"><dfn>*</dfn></td>
            </tr>
            <tr>
                <td class="title">数据对象：</td>
                <td><input id="dataobjectId" name="dataobjectId"
					class="easyui-combobox" style="width: 200px" required="true"
					data-options="editable:false,onSelect:function(record){refreshOptions(record.value)}">
				<dfn>*</dfn></td>
                <td class="title">检索条件每行列数：</td>
                <td><input type="text" id="colCount" name="colCount" value="4" style="width: 200px" class="easyui-validatebox" data-options="required:true">
				<dfn>*</dfn></td>
            </tr>
            <tr>
                <td class="title">分页记录：</td>
                <td><input id="pageSize" name="pageSize"
					class="easyui-combobox" style="width: 200px" value="10"
					data-options="editable:true,required:true, panelHeight:'auto',
    								onChange:function(){var s1 = $(this).combobox('getValue');var s2 =$(this).combobox('getText'); if((s1==undefined || s1=='') && s2!=''){$(this).combobox('setValue',s2)}},
    								data: [{value:'0',text: '不分页'},{value:'10',text: '10'},{value:'20',text: '20'},{value:'30',text: '30'},{value:'40',text: '40'},{value:'50',text: '50'}]">
				<dfn>*</dfn></td>
                <td class="title">分页可选范围：</td>
                <td><input id="pageSizeList" name="pageSizeList"
					type="text" style="width: 200px"
					data-options="required:false"></td>
            </tr>
            <tr>
                <td class="title">是否默认加载数据：</td>
                <td><input id="isAutoBind"
					class="easyui-combobox" style="width: 200px" name="isAutoBind"
					data-options="editable:false,required:true, panelHeight:'auto',data: [{value:'1',text: '是', selected:'true'},{value:'0',text: '否'}]">
				<dfn>*</dfn></td>
                <td class="title">是否导出excel：</td>
                <td><input id="isExport"
					class="easyui-combobox" name="isExport" style="width: 200px"
					data-options="editable:false,required:true, panelHeight:'auto',data: [{value:'1',text: '是' , selected:'true'},{value:'0',text: '否'}]">
				<dfn>*</dfn></td>
            </tr>
            <tr>
                <td class="title">是否启动权限：</td>
                <td><input id="isRight" class="easyui-combobox"
					style="width: 200px" name="isRight"
					data-options="editable:false,required:true, panelHeight:'auto',data: [{value:'1',text: '是', selected:'true'},{value:'0',text: '否'}]">
				<dfn>*</dfn></td>
                <td class="title"></td>
                <td></td>
            </tr>
            <tr>
                <td class="title">过滤条件：</td>
                <td colspan="3"><input type="text" id="filter" name="filter"
					 style="width: 560px"></td>
            </tr>
            <tr>
                <td class="title">排序：</td>
                <td colspan="3"><input type="text" id="sortOrder" name="sortOrder"
					style="width: 560px"
					class="easyui-validatebox" data-options="required:true">
				<dfn>*</dfn></td>
            </tr>
            <tr>
                <td class="title">备注：</td>
                <td colspan="3"><textarea id="note" name="note" class="x2" rows="3"
					style="height: 50px; width: 560px" data-options="required:false"></textarea>
				</td>
            </tr>
			</table>
		

		</form>
		<table id="fieldLabelsdg" title="展现字段描述" class="easyui-datagrid"
			style="width: auto; height: 180px;" toolbar="#fieldLabel_toolbar">
			<!-- <thead>
				<tr>
					<th field="propertyName" width="150">属性名称</th>
					<th field="displayTitle" width="200">显示标题</th>
					<th field="note" width="280">备注</th>
				</tr>
			</thead> -->
		</table>
		<div id="fieldLabel_toolbar">
			<a href="javascript:void(0)" class="easyui-linkbutton c6" plain="true" icon="icon-refresh" onclick="getFieldLabels()">获取字段列表</a>
		</div>

		<br />

		<table id="dg1" class="easyui-datagrid" title="条件参数列表"
			style="width: auto; height: 280px" toolbar="#tb"></table>

		<div id="tb" style="height: auto">
			<a href="javascript:void(0)" class="easyui-linkbutton c6"
				data-options="iconCls:'icon-add',plain:true" onclick="append()">新增</a>
			<a href="javascript:void(0)" class="easyui-linkbutton c6"
				data-options="iconCls:'icon-remove',plain:true" onclick="removeit()">删除</a>
			<!-- 			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-undo',plain:true" onclick="reject()">撤销</a> -->
		</div>
	
 </div>	

	<div class="dialog-button">
		<a href="javascript:void(0)" onclick="previewOniUi()">预览OniUi</a>
		<a href="javascript:void(0)" onclick="previewEasyUi()">预览EasyUi</a>
		<a href="javascript:void(0)" onclick="saveListview()">确定</a>
        <a href="javascript:void(0)" onclick="closeModalWinoOff_compatible()">取消</a>
	</div>
	<div id="dd" class="easyui-dialog" title="详情-编辑"
		style="width: 400px; height: 150px; padding: 10px" closed="true"
		data-options="iconCls:'icon-save',resizable:true,modal:true">
		<textarea id="detailText" name="detailText" cols=50 rows=3></textarea>
		<a href="javascript:void(0)" class="easyui-linkbutton"
			data-options="iconCls:'icon-ok',plain:true" onclick="setDetailText()">更新</a>
	</div>
	<div id="sqlDiv" class="easyui-dialog" title="SQL预览-详情"
		style="width: 400px; height: 200px; padding: 10px" closed="true"
		data-options="iconCls:'icon-save',resizable:true,modal:true">
		<textarea id="sqlText" cols=55 rows=5></textarea>
		<br />
		<div align="center" style="margin-top: 6px">
			<a href="javascript:void(0)" class="easyui-linkbutton"
				data-options="iconCls:'icon-ok',plain:true" onclick="closeSql()">确定</a>
		</div>
	</div>
	
	<div id="div_info_second"></div>

</body>
</html>
