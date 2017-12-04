$(document).ready(function() {	
	doInitTable();
});

/**
 * 加载页面并初始化表格
 * @return
 */
function doInitTable() {
	var path = getRootName();
	$('#tblDataList')
			.datagrid(
					{
						width : 'auto',
						height : 'auto',
						nowrap : false,
						striped : true,
						border : true,
						collapsible : false,// 是否可折叠的
						fit : true,// 自动大小
						fitColumns:true,
						url : path + '/monitor/alarmView/listAlarmView',
						remoteSort : true,
						onSortColumn:function(sort,order){
//						 alert("sort:"+sort+",order："+order+"");
						},						
						idField : '',
						singleSelect : false,// 是否单选
						checkOnSelect : false,
						selectOnCheck : true,
						pagination : true,// 分页控件
						rownumbers : true,// 行号
						toolbar : [ {
							'text' : '新建',
							'iconCls' : 'icon-add',
							handler : function() {
								toAdd();
							}
						}, {
							text : '删除',
							iconCls : 'icon-cancel',
							handler : function() {
								doBatchDel();
							}
						} ],
						columns : [ [
						        {
						        	field : 'viewCfgID',
						        	checkbox : true
						        },    
								{
									field : 'cfgName',
									title : '视图名称',
									width : 80,
									align : 'left'
								},
								{
									field : 'userDefault',
									title : '是否默认',
									width : 70,
									align : 'center',
									formatter:function(value,row){ 
										if(value==1){
											return '是';
										}else if(value==0){
											return '否';
										}
									} 
								},
								{
									field : 'descr',
									title : '描述',									
									align : 'center',
									width : 200
								},
								{
									field : 'isSystem',
									title : '是否系统定义',
									width : 70,
									align : 'center',
									formatter:function(value,row){ 
										if(value==1){
											return '是';
										}else if(value==0){
											return '否';
										}
									} 
								},
								{
									field : 'ids',
									title : '操作',
									width : 85,
									align : 'center',
									formatter : function(value, row, index) {
										var to ="";
										if(row.isSystem == 0){//暂时设置admin账号可以修改和删除默认视图
											to = '<a style="cursor: pointer;" title="修改" onclick="javascript:toUpdate('
												+ row.viewCfgID
												+ ');"><img src="'
												+ path
												+ '/style/images/icon/icon_modify.png" alt="修改" /></a> &nbsp;<a style="cursor: pointer;" title="删除" onclick="javascript:doDel('
												+ row.viewCfgID
												+ ');"><img src="'
												+ path
												+ '/style/images/icon/icon_delete.png" alt="删除" /></a>';
										}						
										return '<a style="cursor: pointer;" title="查看" onclick="javascript:toShowInfo('
												+ row.viewCfgID
												+ ');"><img src="'
												+ path
												+ '/style/images/icon/icon_view.png" alt="查看" /></a>&nbsp'+to;
									}
								}] ]
					});
	$(window).resize(function() {
        $('#tblDataList').resizeDataGrid(0, 0, 0, 0);
    });
}

/**
 * 刷新表格数据
 */
function reloadAllTable() {
	var cfgName=$("#cfgName").val();
	$('#tblDataList').datagrid('options').queryParams = {
		"cfgName" : cfgName
	};
	reloadTableCommon_1('tblDataList');
}

function reloadTableCommon_1(dataGridId) {
	$('#' + dataGridId).datagrid('load');
	$('#' + dataGridId).datagrid('uncheckAll');
}

/**
 * 打开新建页面
 * @return
 */
function toAdd(){
		parent.$('#popWin').window({
	    	title:'告警视图新建',
	        width:800,
	        height:530,
	        minimizable:false,
	        maximizable:false,
	        collapsible:false,
	        modal:true,
	        href: getRootName() + '/monitor/alarmView/toAddAlarmView'
	     });		
}
/**
 * 打开修改页面
 * @return
 */
function toUpdate(id){
	parent.$('#popWin').window({
    	title:'告警视图修改',
        width:800,
        height:530,
        minimizable:false,
        maximizable:false,
        collapsible:false,
        modal:true,
        href: getRootName() + '/monitor/alarmView/toUpadteAlarmView?viewCfgID='+id
     });		
}

/**
 * 打开查看页面
 * @return
 */
function toShowInfo(id){
	parent.$('#popWin').window({
    	title:'告警视图查看',
        width:800,
        height:530,
        minimizable:false,
        maximizable:false,
        collapsible:false,
        modal:true,
        href: getRootName() + '/monitor/alarmView/toViewAlarmView?viewCfgID='+id
     });		
}

/*
 * 删除任务 
 */
function doDel(id) {
		$.messager.confirm("操作提示", "您确定删除此信息吗？", function (data) {
            if (data) {
            	var path = getRootName();
    			var uri = path + "/monitor/alarmView/delAlarmView";
    			var ajax_param = {
    				url : uri,
    				type : "post",
    				datdType : "json",
    				data : {
    					"viewCfgID" : id,
    					"t" : Math.random()
    				},
    				error : function() {
    					$.messager.alert("错误", "ajax_error", "error");
    				},
    				success : function(data) {
    					if (true == data || "true" == data) {
    						$.messager.alert("提示", "信息删除成功！", "info");
    						reloadAllTable();
    					} else {
    						$.messager.alert("提示", "信息删除失败！", "error");
    					}
    				}
    			}
    			ajax_(ajax_param);
            }            
        });	
}


/**
 * 批量删除
 */
function doBatchDel() {
	var path = getRootName();
	var ids = null;
	var rows  = $('#tblDataList').datagrid('getSelections');
	var flag = null;
	for(var i=0; i<rows.length; i++){
		if(rows[i].isSystem == 1){
			flag = "1";
		}
		if (null == ids) {
			ids = rows[i].viewCfgID;
		} else {
			ids += ',' + rows[i].viewCfgID;
		}
    }	
	if (null != ids) {
		if (null == flag){
			$.messager.confirm("提示", "确定删除所选中项？", function(r) {
				if (r == true) {			
					var uri =path+"/monitor/alarmView/delBathAlarmView";
    			    var ajax_param = {
    				url : uri,
    				type : "post",
    				datdType : "json",
    				data : {
    					"id":ids
    				},
					success : function(data) {
						if (true == data || "true" == data) {
							$.messager.alert("提示", "信息批量删除成功！", "info");
							reloadAllTable();
						} else {
							$.messager.alert("提示", "信息批量删除失败！", "error");
						}
					}
    			    };
				ajax_(ajax_param);
				}
			});
		}else{
			$.messager.alert("提示", "系统定义的视图不能删除！", "error");
		}
	}else{
		$.messager.alert('提示', '没有任何选中项', 'error');
	}	
}