$(document).ready(function() {
	$("#initRange").hide();
	$('#rangeLst').window('close');
	var moClassID = $("#moClassID").val();
	checkMoTypeOfMoClassLst(moClassID);
});

/**
 * 加载页面并初始化表格
 * @return
 */
function doInitTable(moClassID) {
	var path = getRootName();
	$('#tblSysMoTypeRangeList')
			.datagrid(
					{
						iconCls : 'icon-edit',// 图标
						width : 'auto',
						height : 350,
						nowrap : false,
						striped : true,
						border : true,
						collapsible : false,// 是否可折叠的
						//fit : true,// 自动大小
						//fitColumns:true,
						url : path + '/monitor/sysMoTemplate/listSysMoTypeRange?moClassID='+moClassID,
						remoteSort : true,
						idField : 'id',
						singleSelect : false,// 是否单选
						checkOnSelect : false, 
						selectOnCheck : true,
						pagination : true,// 分页控件
						rownumbers : true,// 行号
						toolbar : [ {
							'text' : '新增',
							'iconCls' : 'icon-add',
							handler : function() {
								doOpenAdd(moClassID);
							}
						},
						{
							'text' : '删除',
							'iconCls' : 'icon-cancel',
							handler : function() {
								doBatchDel();
							}
						}
						],
						
						columns : [ [
						        {
						        	field : 'id',
						        	checkbox : true
						        },
								{
									field : 'monitorTypeName',
									title : '监测器类型名称',
									width : 480,
									align : 'center'
								},
								{
									field : 'ids', 
									title : '操作',
									width : 225,
									align : 'center',
									formatter : function(value, row, index) {
									var to = "&quot;" + row.id
									+ "&quot;,&quot;" + row.monitorTypeID
									+ "&quot;"
									return '<a style="cursor: pointer;" onclick="javascript:doDel('
									+ to 
									+ ');"><img src="'
									+ path
									+ '/style/images/icon/icon_delete.png" title="删除" /></a>';
						}
								} ] ]
					});
}


Array.prototype.in_array = function(e) {  
	 for(i=0;i<this.length;i++){  
		 if(this[i] == e){
			 return true;  
		 } 
	 }  
return false;  
}


function toShowConfig(){
	$("#initRange").hide();
	$('#rangeLst').window('open');
	var moClassID = $("#moClassID").val();
	// 查看配置项页面
	parent.parent.$('#popWin').window({
    	title:'配置监测器类型',
        width:700,
        height:480,
        minimizable:false,
        maximizable:false,
        collapsible:false,
        modal:true,
        href: getRootName() + '/monitor/sysMoTemplate/toShowAddTypeView?moClassID='+moClassID,
        onClose: function () { //当面板关闭之前触发的事件
			$('#popWin').window('close');
			parent.window.treeClickAction(moClassID,"",-1,"");
		}
    });
}

function checkMoTypeOfMoClassLst(moClassID){
	var uri=getRootPatch()+"/monitor/sysMoTemplate/existMoTemplate";
	var ajax_param={
		url:uri,
		type:"post",
		dateType:"text",
		data:{                
			"moClassID":moClassID,
			"t" : Math.random()
		},
		error : function(){
			$.messager.alert("错误","ajax_error","error");
		},
		success:function(data){
//			console.log("data = "+data);
			if (data == true) {
				$("#initRange").hide();
				$('#rangeLst').window('open');
				doInitTable(moClassID);
			} else {
				$("#initRange").show();
				$('#rangeLst').window('close');
			}
		}
	};	
	ajax_(ajax_param);
}


function doOpenAdd(){
	var moClassID = $("#moClassID").val();
	// 查看配置项页面
	parent.parent.$('#popWin').window({
    	title:'配置监测器类型',
        width:700,
        height:480,
        minimizable:false,
        maximizable:false,
        collapsible:false,
        modal:true,
        href: getRootName() + '/monitor/sysMoTemplate/toShowAddTypeView?moClassID='+moClassID
    });

}

function doDel(id,monitorTypeID){
	$.messager.confirm("提示","确定删除此监测对象配置的监测器类型？",function(r){
		if (r == true) {
			var moClassID = $("#moClassID").val();
			var uri=getRootPatch()+"/monitor/sysMoTemplate/isUsedByTemplate";
			var ajax_param={
				url:uri,
				type:"post",
				dateType:"text",
				data:{                
					"moClassID":moClassID,
					"monitorTypeIDs":monitorTypeID,
					"t" : Math.random()
				},
				error : function(){
					$.messager.alert("错误","ajax_error","error");
				},
				success:function(data){
					if (data == true) {
						$.messager.alert("提示", "该监测器类型已经被模板使用，不能删除！", "info");
					} else {
						var uri=getRootPatch()+"/monitor/sysMoTemplate/delMoTypeOfMoClass";
						var ajax_param={
							url:uri,
							type:"post",
							dateType:"text",
							data:{                
								"id":id,
								"t" : Math.random()
							},
							error : function(){
								$.messager.alert("错误","ajax_error","error");
							},
							success:function(data){
								if (data == true) {
									$.messager.alert("提示", "监测器类型删除成功！", "info");
									doInitTable($("#moClassID").val());
								} else {
									$.messager.alert("提示", "监测器类型删除失败！", "info");
								}
							}
						};	
						ajax_(ajax_param);
					}
				}
			};	
			ajax_(ajax_param);
		}
		
	});
}

function doBatchDel(){
	var checkedItems = $('[name=id]:checked');
	var ids = null;
	if (checkedItems.length == 0) {
		$.messager.alert('提示', '没有任何选中项!', 'info');
	} else {
		var rows = $('#tblSysMoTypeRangeList').datagrid('getSelections');
		var monitorTypeIDs = null;
		for(var i=0; i<rows.length; i++){
			if (null == monitorTypeIDs) {
				monitorTypeIDs = rows[i].monitorTypeID;
			} else {
				monitorTypeIDs += ',' + rows[i].monitorTypeID;
			}
		}
		var moClassID = $("#moClassID").val();
		var uri=getRootPatch()+"/monitor/sysMoTemplate/isUsedByTemplate";
		var ajax_param={
			url:uri,
			type:"post",
			dateType:"text",
			data:{                
				"moClassID":moClassID,
				"monitorTypeIDs":monitorTypeIDs,
				"t" : Math.random()
			},
			error : function(){
				$.messager.alert("错误","ajax_error","error");
			},
			success:function(data){
				if (data == true) {
					$.messager.alert("提示", "被选监测器类型已经被模板使用，不能删除！", "info");
				} else {
					$.each(checkedItems, function(index, item) {
						if (null == ids) {
							ids = $(item).val();
						} else {
							ids += ',' + $(item).val();
						}
					});
					if(null != ids){
						var uri=getRootPatch()+"/monitor/sysMoTemplate/delMultiMoTypesOfMoClass";
						var ajax_param={
							url:uri,
							type:"post",
							dateType:"text",
							data:{                
								"ids":ids,
								"t" : Math.random()
							},
							error : function(){
								$.messager.alert("错误","ajax_error","error");
							},
							success:function(data){
								if (data == true) {
									doInitTable($("#moClassID").val());
								} else {
									$.messager.alert("提示","删除失败！","info");
								}
							}
						};	
						ajax_(ajax_param);
					}
				}
			}
		};	
		ajax_(ajax_param);
	}

}