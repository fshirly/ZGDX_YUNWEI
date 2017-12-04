<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="UTF-8">
<title>jquery 插件</title>
<script type="text/javascript" charset="utf-8" src="../../../../js/platform/system/head.js"></script>
<!-- <script type="text/javascript" charset="utf-8" src="http://www.rfk.id.au/static/scratch/jquery.xmlns.js"></script> -->


<script type="text/javascript">

	var listviewId = {};
	//listviewName
	var listviewName = "listviewManager";//
	//如果id不存在，则为listviewName
	var id = listviewName;
	//此方法获得listview插件中的datagridId,参数为listview插件的id，如果id不存在，则为listviewName
	var datagridId = listviewMethod["getDatagridId"](id);
	$(document).ready(function() {
		 $('#div').listview({
			//id : id, //插件中id不传值，则默认等于listviewName
			listviewName : listviewName,
			//exportExcel : true,
			//initParams : {"name" : "123" , "aa" : "xxx" , "bb" : "yyy"},
			//datagridId : datagridId,
			frozenColumns : [[
								{field:'id',checkbox:true},
								//{field:'name',width:100}  
			                ]],
			columns : [[
				        {field:'name',width:150},
				        {field:'title',width:150},
				        {field:'note',width:300},
				      ]],
			toolbar : [{
						text:"新增",
						iconCls: 'icon-add',
						handler: function(){
							listviewId = {};
							//openModalWin(rootPath + '/tag/listviewManager/listview/create/html', "Listview-新增", 900, 600 , 'yes');
							
							setJsonObj('listviewId', listviewId);
							OpenWin(rootPath + '/tag/listviewManager/listview/create/html', "Listview-新增", 900, 600);
						}
					  },'-',
					  {
						text:"修改",
						iconCls: 'icon-modify',
						handler: function(){
							if ($("#"+datagridId).datagrid("getSelections").length != 1) {
								window.top.$.messager.alert("提示信息", "请选中一行进行修改！");
								return;
							}
							var row = $('#'+datagridId).datagrid('getSelected');
							listviewId = {
								"id" : row.id
							};
							//openModalWin(rootPath + '/tag/listviewManager/listview/update/html', "Listview-修改", 900, 600 , 'yes');
							setJsonObj('listviewId', listviewId);
							OpenWin(rootPath + '/tag/listviewManager/listview/update/html', "Listview-修改", 900, 600);
					  	}
					  },'-',
					  {
						text:"删除",
						iconCls: 'icon-remove',
						handler: function(){
							if ($("#"+datagridId).datagrid("getSelections").length == 0) {
								window.top.$.messager.alert("提示信息", "未选中数据！");
								return;
							}
							
							var rows = $("#"+datagridId).datagrid('getSelections');
							for (var i = 0; i < rows.length; i++) {
								if(rows[i].name == 'listviewManager' || rows[i].name == 'listviewEasyUiTest' || rows[i].name == 'listviewTest' ){
									window.top.$.messager.alert("提示信息", "选中记录中包括了【listview管理或者测试样例】，此数据不能被删除！");
									return;
								}
							}
							
							window.top.$.messager.confirm('确认', '您确定要删除选择的记录吗？', function(r) {
								if (r) {
									var jsonObj = [];
									var rows = $("#"+datagridId).datagrid('getSelections');
									for (var jj = 0; jj < rows.length; jj++) {
										jsonObj.push(rows[jj].id);
									}
									$.ajax({
										type : "DELETE",
										url : rootPath + '/tag/listviewManager/listview',
										data : JSON.stringify(jsonObj),
										dataType : 'json',
										contentType : "application/json",
										success : function(result) {
											if (result.success) {
												window.top.$.messager.alert("提示信息", result.message);
												//$('#dg').datagrid('reload'); //reload the user data
												//query(1, 10);
												listviewMethod["refresh"](id);
											} else {
												window.top.$.messager.alert("错误信息", result.errorCode + ": "
														+ result.message);
											}
										},
										error : function(result) {
											//
											show_error(result);
										}
									});
								}
							});
						}
					  },'-',
					  {
						text:"导入",
						iconCls: 'icon-import',
						handler: function(){
							//openModalWin(rootPath + '/tag/listviewManager/listview/import/html', "自定义列表查询-导入", 300, 200);
							OpenWin(rootPath + '/tag/listviewManager/listview/import/html', "自定义列表查询-导入", 300, 200);
						}
					  },'-',
					  {
						text:"导出",
						iconCls: 'icon-export',
						handler: function(){
							if ($("#"+datagridId).datagrid("getSelections").length == 0) {
								window.top.$.messager.alert("提示信息", "未选中数据！");
								return;
							}

							var jsonObj = [];
							var rows = $("#"+datagridId).datagrid('getSelections');
							for (var jj = 0; jj < rows.length; jj++) {
								jsonObj.push(rows[jj].id);
							}
							$.ajax({
									type : "POST",
									url : rootPath + '/tag/listviewManager/listviews/export',
									data : JSON.stringify(jsonObj),
									dataType : 'json',
									contentType : "application/json",
									success : function(result) {
										if (result.success) {
											var filePath = encodeURIComponent(encodeURIComponent(result.filePath));
											document.getElementById("download").src = rootPath + "/tag/listviewManager/listviews/exportByPath/"
													+ filePath;
										} else {
											window.top.$.messager.alert("错误信息", result.errorCode + ": "
													+ result.message);
										}

									},
									error : function(result) {
									}
								});
						}
					  },'-'],
  			//iconCls : 'icon-save',
			fitColumns : true,
			//singleSelect : true,
			//border : false,
			autoRowHeight : true,
			striped : true,
			nowrap : false,
			//pagination : true,
			rownumbers : true,
			fit : true,
			resizeHandle : 'both',
			pagePosition : 'bottom',
			scrollbarSize : 0 ,
			//title : 'test',
			//height : 400,
			//width : '100%' ,
			callBack : function(){
				//alert("asdssssa");
			}

		});
		 
	}); 
	
	function fresh(){
		listviewMethod["refresh"](listviewName);
	}
	
</script>
</head>
<body>
	<div id="div"></div> 
	<div id="div_info"></div>
	<iframe id="download" src="" width="0" height="0"
		style="width: 0px; height: 0px; display: none"></iframe>
</body>
</html>