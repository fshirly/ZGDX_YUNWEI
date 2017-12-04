$(document).ready(function() {
	doInitTable();
});

/**
 * 刷新表格数据
 */
function reloadTable(){
	var maintainTitle = $("#textMaintainTitle").val();
	var moname = $("#textMOName").val();
	$('#tblMaintenancePloicyList').datagrid('options').queryParams = {
		"maintainTitle" : maintainTitle,
		"moname" : moname
	};
	reloadTableCommon_1('tblMaintenancePloicyList');
}

function reloadTableCommon_1(dataGridId) {
	$('#' + dataGridId).datagrid('load');
	$('#' + dataGridId).datagrid('uncheckAll');
}

/**
 * 加载页面并初始化表格
 * @return
 */
function doInitTable(){
	var path = getRootName();
	$('#tblMaintenancePloicyList')
		.datagrid(
				{
					iconCls : 'icon-edit', //图标
					width : 'auto',
					height : 'auto',
					nowrap : false,
					striped : true,
					border : true,
					collapsible : false,//是否可折叠的
					fit : true,// 自动大小
					fitColumns : true,
					url : path + '/monitor/alarmMgr/mPloicy/listMaintenancePloicy',
					onSortColumn:function(sort,order){
					},
					idField : 'fldId',
					singleSelect : false,// 是否单选
					checkOnSelect : false,
					selectOnCheck : true,
					pagination : true,// 分页控件
					rownumbers : true,// 行号
					toolbar : [{
						'text' : '新增',
						'iconCls' : 'icon-add',
						handler : function(){
							doOpenAdd();
						}
					},
					{
						'text' : '删除',
						'iconCls' : 'icon-cancel',
						handler : function() {
							doBatchDel();
						}
					}],
					
					columns : [[
								{
									field : 'ploicyID',
									checkbox : true
								},{
									field : 'maintainTitle',
									title : '维护期标题',
									width : 100,
									align : 'center'
								},{
									field : 'moname',
									title : '事件源对象',
									width : 160,
									align : 'center'
								},
								{
									field : 'maintainType',
									title : '维护期类型',
									width : 80,
									align : 'center',
									formatter : function(value,row,index){
									if(value==1){
										return '新建';
									}else if(value==2){
										return '割接';
									}else{
										return '故障';
									}
								}
								},{
									field : 'startTime',
									title : '维护开始时间',
									width : 160,
									align : 'center',
									formatter:function(value,row,index){
									if(value!=null && value!="" ){
										return formatDate(new Date(value), "yyyy-MM-dd hh:mm:ss");
									}else{
										return "";
									}	
	                        	}
								},{
									field : 'endTime',
									title : '维护结束时间',
									width : 160,
									align : 'center',
									formatter:function(value,row,index){
									if(value!=null && value!="" ){
										return formatDate(new Date(value), "yyyy-MM-dd hh:mm:ss");
									}else{
										return "";
									}	
								}
								},{
									field : 'isUsed',
									title : '是否启用',
									width : 80,
									align : 'center',
									formatter : function(value,row,index){
										if(value==0){
											return '否';
										}else if(value==1){
											return '是';
										}else{
											return '';
										}
									}
								},{
									field : 'ploicyIDs',
									title : '操作',
									width : 100,
									align : 'center',
									formatter : function(value, row, index) {
										return '<a style="cursor: pointer;" title="查看" onclick="javascript:toShowInfo('
												+ row.ploicyID
												+ ');"><img src="'
												+ path
												+ '/style/images/icon/icon_view.png" alt="查看" /></a> &nbsp;'
												+ ' <a style="cursor: pointer;" title="修改" onclick="javascript:toOpenModify('
												+ row.ploicyID
												+ ');"><img src="'
												+ path
												+ '/style/images/icon/icon_modify.png" alt="修改" /></a> &nbsp;'
												+ ' <a style="cursor: pointer;" title="删除" onclick="javascript:doDel('
												+ row.ploicyID
												+ ');"><img src="'
												+ path
												+ '/style/images/icon/icon_delete.png" alt="删除" /></a>';
									}
								}]]
					});
		$(window).resize(function() {
			$('#tblMaintenancePloicyList').resizeDataGrid(0, 0, 0, 0);
		});
}

//function checkForm() {
//	return checkInfo('#tblEdit');
//}

/**
 * 删除维护期策略
 * @param alarmTypeId
 * @return
 */
function doDel(ploicyID){
	var path = getRootName();
	$.messager.confirm("提示", "确定删除此信息?", function(r) {
		if (r == true) {
			var uri = path + "/monitor/alarmMgr/mPloicy/delMaintenancePloicy";
			var ajax_param = {
				url : uri,
				type : "post",
				datdType : "json",
				data : {
					"ploicyID" : ploicyID,
					"t" : Math.random()
				},
				error : function() {
					$.messager.alert("错误", "ajax_error", "error");
				},
				success : function(data) {
					if (true == data || "true" == data) {
						$.messager.alert("提示", "信息删除成功！", "info");
						reloadTable();
					} else {
						$.messager.alert("", "信息删除失败！", "error");
						reloadTable();
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
function doBatchDel(){
	var path=getRootName();
	var checkedItems = $('[name=ploicyID]:checked');
	var ids = null;
	$.each(checkedItems, function(index, item) {
		if (null == ids) {
			ids = $(item).val();
		} else {
			ids += ',' + $(item).val();
		}
	});
	if (null != ids) {
		$.messager.confirm("提示","确定删除所选中项？",function(r){
			if (r == true) {
				var uri = path+ "/monitor/alarmMgr/mPloicy/doBatchDel?ploicyIDs="+ids;
				var ajax_param = {
					url : uri,
					type : "post",
					datdType : "json",
					data : {
						"t" : Math.random()
					},
					error : function() {
						$.messager.alert("错误", "ajax_error", "error");
					},
					success : function(data) {
						if (true == data || "true" == data) {
							$.messager.alert("提示", "信息删除成功！", "info");
							reloadTable();
						} else {
							$.messager.alert("错误", "信息删除失败！", "error");
							reloadTable();
						}
					}
				};
				ajax_(ajax_param);
			}
		});
	} else {
		$.messager.alert('提示', '没有任何选中项', 'error');
	}
}


/**
 * 新增维护期策略页面跳转
 * @return
 */
function doOpenAdd(){
		parent.$('#popWin').window({
    	title:'新增维护期策略',
        width:800,
        height:430,
        minimizable:false,
        maximizable:false,
        collapsible:false,
        modal:true,
        href: getRootName() + '/monitor/alarmMgr/mPloicy/toMaintenancePloicyAdd'
    });
}


function toOpenModify(ploicyID){
	//查看配置项页面
	parent.$('#popWin').window({
    	title:'编辑维护期策略',
        width:600,
        height:430,
        minimizable:false,
        maximizable:false,
        collapsible:false,
        modal:true,
        href: getRootName() + '/monitor/alarmMgr/mPloicy/toMaintenancePloicyModify?ploicyID='+ploicyID
    });
}


/**
 * 维护期策略详情
 * @return
 */
function toShowInfo(id){
	 parent.$('#popWin').window({
    	title:'维护期策略详情',
        width:800,
        height:430,
        minimizable:false,
        maximizable:false,
        collapsible:false,
        modal:true,
        href: getRootName() + '/monitor/alarmMgr/mPloicy/toMaintenancePloicyDetail?ploicyID='+id
     });
	
}
