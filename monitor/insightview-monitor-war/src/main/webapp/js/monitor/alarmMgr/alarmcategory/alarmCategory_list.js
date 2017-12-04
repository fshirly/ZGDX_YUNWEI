$(document).ready(function() {
	doInitTable();
});

/**
 * 刷新表格数据
 */
function reloadTable(){
	var categoryName = $("#textCategoryName").val();
	var alarmOID = $("#textAlarmOID").val();
	$('#tblAlarmCategoryList').datagrid('options').queryParams = {
		"categoryName" : categoryName,
		"alarmOID" : alarmOID
	};
	reloadTableCommon_1('tblAlarmCategoryList');
}

function reloadTableCommon_1(dataGridId) {
	$('#' + dataGridId).datagrid('load');
	$('#' + dataGridId).datagrid('uncheckAll');
}

/**
 * @return
 */
function doInitTable(){
	var path = getRootName();
	$('#tblAlarmCategoryList')
		.datagrid(
				{
					iconCls : 'icon-edit', //图标
					width: 'auto',
					height : 'auto',
					nowrap : false,
					striped : true,
					border : true,
					collapsible : false,//是否可折叠的
					fit : true,// 自动大小
					fitColumns : true,
					url : path + '/monitor/alarmMgr/alarmcategory/listAlarmCategory',
					onSortColumn:function(sort,order){
//					 alert("sort:"+sort+",order："+order+"");
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
									field : 'categoryID',
									checkbox : true
								},{
									field : 'categoryName',
									title : '分类名称',
									width : 100,
									align : 'center'
								},{
									field : 'alarmOID',
									title : 'SNMP企业私有ID',
									width : 160,
									align : 'center'
								},{
									field : 'categoryIDs',
									title : '操作',
									width : 90,
									align : 'center',
									formatter : function(value, row, index) {
										return '<a style="cursor: pointer;" title="查看" onclick="javascript:toShowInfo('
												+ row.categoryID
												+ ');"><img src="'
												+ path
												+ '/style/images/icon/icon_view.png" alt="查看" /></a> &nbsp;'
												+ ' <a style="cursor: pointer;" title="修改" onclick="javascript:toOpenModify('
												+ row.categoryID
												+ ');"><img src="'
												+ path
												+ '/style/images/icon/icon_modify.png" alt="修改" /></a> &nbsp;'
												+ ' <a style="cursor: pointer;" title="删除" onclick="javascript:toDel('
												+ row.categoryID
												+ ');"><img src="'
												+ path
												+ '/style/images/icon/icon_delete.png" alt="删除" /></a>';
									}
								}]]
					});
		$(window).resize(function() {
			$('#tblAlarmCategoryList').resizeDataGrid(0, 0, 0, 0);
		});
}

function checkForm() {
	return checkInfo('#tblEdit');
}

function toDel(categoryID){
	$.messager.confirm("提示", "确定删除此信息?", function(r) {
		if (r == true) {
			checkIsUsed(categoryID);
		}
	});
}

function checkIsUsed(categoryID){
	var path = getRootName();
	var uri = path + "/monitor/alarmMgr/alarmcategory/checkIsUsed";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"categoryID" : categoryID,
			"t" : Math.random()
		},
		error : function(){
			$.messager.alert("错误","ajax_error","error");
		},
		success : function(data){
			if(false == data || "false" == data){
				$.messager.alert("提示","该告警分类已经被引用,删除失败！","error");
			}else{
				doDel(categoryID);
			}
		}
	};
	ajax_(ajax_param);
}

/**
 * 删除自定义告警分类
 * @param alarmTypeId
 * @return
 */
function doDel(categoryID){
	var path = getRootName();
			var uri = path + "/monitor/alarmMgr/alarmcategory/delAlarmCategory";
			var ajax_param = {
				url : uri,
				type : "post",
				datdType : "json",
				data : {
					"categoryID" : categoryID,
					"t" : Math.random()
				},
				error : function() {
					$.messager.alert("错误", "ajax_error", "error");
				},
				success : function(data) {
					if (true == data || "true" == data) {
						$.messager.alert("提示", "删除成功！", "info");
						reloadTable();
					} else  {
						$.messager.alert("错误", "该告警分类为系统定义,删除失败！", "error");
					}
				}
			};
			ajax_(ajax_param);
}

/**
 * 批量删除
 * 
 */
function doBatchDel(){
	var path=getRootName();
	var checkedItems = $('[name=categoryID]:checked');
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
				var uri = path+ "/monitor/alarmMgr/alarmcategory/doBatchDel?categoryIDs="+ids;
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
						if (1 == data.flag ) {
							$.messager.alert("错误", "被删告警分类为系统定义，不能删除", "error");
							reloadTable();
						}else if(2 == data.flag) {
							$.messager.alert("错误", "被删告警分类已被引用，不能删除", "error");
							reloadTable();
						}else{
							$.messager.alert("提示", "删除成功！", "info");
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
 * 新增告警分类页面跳转
 * @return
 */
function doOpenAdd(){
		parent.$('#popWin').window({
    	title:'新增告警分类',
        width:600,
        height:300,
        minimizable:false,
        maximizable:false,
        collapsible:false,
        modal:true,
        href: getRootName() + '/monitor/alarmMgr/alarmcategory/toAlarmCategoryAdd'
    });
}


function toOpenModify(categoryID){
	//查看配置项页面
	parent.$('#popWin').window({
    	title:'编辑告警分类',
        width:600,
        height:300,
        minimizable:false,
        maximizable:false,
        collapsible:false,
        modal:true,
        href: getRootName() + '/monitor/alarmMgr/alarmcategory/toAlarmCategoryModify?categoryID='+categoryID
    });
}


/**
 * 告警类型详情
 * @return
 */
function toShowInfo(id){
	 parent.$('#popWin').window({
    	title:'告警分类详情',
        width:600,
        height:400,
        minimizable:false,
        maximizable:false,
        collapsible:false,
        modal:true,
        href: getRootName() + '/monitor/alarmMgr/alarmcategory/toAlarmCategoryDetail?categoryID='+id
     });
	
}





