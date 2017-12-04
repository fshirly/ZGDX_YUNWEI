$(document).ready(function() {
	$('#txtKPICategory').combobox({
		editable : false
	});
	doInitTable();
});

function getAllCategory(){
	$('#txtKPICategory').combobox({
		panelHeight : '120',
		url : getRootName() + '/monitor/perfKPIDef/getAllCategory',
		valueField : 'kpiCategory',
		textField : 'kpiCategory',
		editable : false
	});
}

/**
 * 加载页面并初始化表格
 * @return
 */
function doInitTable(){
	var path = getRootName();
	$('#tblPerfKPIDef')
			.datagrid(
					{ 
						iconCls : 'icon-edit',// 图标
						width : 700,
						height : 'auto',
						nowrap : false,
						striped : true,
						border : true,
						collapsible : false,// 是否可折叠的
						fit:true,// 自动大小
						fitColumns:true,
						url : path + '/monitor/perfKPIDef/toListPerfKPIDef',
						remoteSort : true,
						idField : 'kpiID',
						singleSelect : false,// 是否单选
						checkOnSelect : false,
						selectOnCheck : true,
						pagination : true,// 分页控件
						rownumbers : true,// 行号
						toolbar : [ {
							'text' : '新增',
							'iconCls' : 'icon-add',
							handler : function() {
							doOpenAdd();
							}
						},
						{
							'text' : '删除',
							'iconCls' : 'icon-cancel',
							handler : function() {
								doBatchDel();
							}
						} ],
						columns : [ [
								{
									 field : 'kpiID',
									 checkbox : true
								}, 
								{  
									field : 'name',
									title : '指标名称',  
									width : 100,
									align : 'center',
									formatter : function(value, row, index) {
						        		if(value && value.length > 10){
						        		value2 = value.substring(0,15) + "...";
										 return '<span title="' + value + '" style="cursor: pointer;" >' +value2+'</span>';
							        	}else{
											return value;
										}
									}
								},
								{  
									field : 'enName',
									title : '指标英文名',  
									width : 100,
									align : 'center',
									formatter : function(value, row, index) {
						        		if(value && value.length > 10){
						        		value2 = value.substring(0,15) + "...";
										 return '<span title="' + value + '" style="cursor: pointer;" >' +value2+'</span>';
							        	}else{
											return value;
										}
									}
								},
								{
									field : 'kpiCategory',
									title : '指标类别',
									width : 110,
									align : 'center',
									sortable : true,
									formatter : function(value, row, index) {
						        		if(value && value.length > 10){
						        		value2 = value.substring(0,15) + "...";
										 return '<span title="' + value + '" style="cursor: pointer;" >' +value2+'</span>';
							        	}else{
											return value;
										}
									}
								},
								{
									field : 'className',
									title : '对象类型',
									width : 80,
									align : 'center',
								},
								{
									field : 'quantifier',
									title : '单位', 
									width : 60,
									align : 'center',
									formatter : function(value, row, index) {
						        		if(value && value.length > 10){
						        		value2 = value.substring(0,8) + "...";
										 return '<span title="' + value + '" style="cursor: pointer;" >' +value2+'</span>';
							        	}else{
											return value;
										}
									}
								},
								{
									field : 'valueType',
									title : '值类型', 
									width : 60,
									align : 'center',
									formatter : function(value, row, index) {
										if (value == 0) {
											return '数值型';
										}else{
											return '文本型';
										}
									}
								},
								{
									field : 'amountType',
									title : '汇总规则', 
									width : 60,
									align : 'center',
									formatter : function(value, row, index) {
										if (value == -1) {
											return '不汇总';
										}else if(value == 0){
											return '求和';
										}else if(value == 1){
											return '平均值';
										}else if(value == 2){
											return '最大值';
										}else if(value == 3){
											return '最小值';
										}else{
											return '记录个数';
										}
									}
								},
								{
									field : 'valueRange',
									title : '数据有效性范围', 
									width : 100,
									align : 'center',
									formatter : function(value, row, index) {
						        		if(value && value.length > 10){
						        		value2 = value.substring(0,15) + "...";
										 return '<span title="' + value + '" style="cursor: pointer;" >' +value2+'</span>';
							        	}else{
											return value;
										}
									}
								},
								/*{
									field : 'isSupport',
									title : '是否支持阈值', 
									width : 80,
									align : 'center',
									formatter : function(value, row, index) {
										if (value == 1) {
											return '是';
										}else{
											return '否';
										}
									}
								},*/
								{
									field : 'note',
									title : '备注',
									width : 100,
									align : 'center',
									formatter : function(value, row, index) {
						        		if(value && value.length > 10){
						        		value2 = value.substring(0,15) + "...";
										 return '<span title="' + value + '" style="cursor: pointer;" >' +value2+'</span>';
							        	}else{
											return value;
										}
									}
								},
								{
									field : 'kpiIDs',
									title : '操作',
									width : 120,
									align : 'center',
									formatter : function(value, row, index) {
										return '<a style="cursor: pointer;" onclick="javascript:doOpenDetail('
											+ row.kpiID
											+ ');"><img src="'
											+ path
											+ '/style/images/icon/icon_view.png" title="查看" /></a> &nbsp;<a style="cursor: pointer;" onclick="javascript:doOpenModify('
											+ row.kpiID
											+ ');"><img src="'
											+ path
											+ '/style/images/icon/icon_modify.png" title="修改" /></a> &nbsp;<a style="cursor: pointer;" onclick="javascript:toDel('
											+ row.kpiID
											+ ');"><img src="'
											+ path
											+ '/style/images/icon/icon_delete.png" title="删除" /></a>'
											+' &nbsp;<a style="cursor: pointer;" title="配置" onclick="javascript:toOpenSet('+ row.kpiID + ',' + row.classID + ');"><img src="'
											+ path
											+ '/style/images/icon/icon_setting.png" title="配置"/></a>';
									}
								} 
								]]
								
					});
	$(window).resize(function() {
        $('#tblPerfKPIDef').resizeDataGrid(0, 0, 0, 0);
    });
}
/*
 * 更新表格
 */
function reloadTable(){
	var name = $("#txtName").val();
	var enName = $("#txtEnName").val();
	var kpiCategory = $("#txtKPICategory").combobox("getValue");
//	console.log("kpiCategory="+kpiCategory);
	var className = $("#txtClassName").val();
	$('#tblPerfKPIDef').datagrid('options').queryParams = {
		"name" : name,
		"enName" :enName,
		"kpiCategory" :kpiCategory,
		"className":className,
	};
	reloadTableCommon_1('tblPerfKPIDef');
}

function reloadTableCommon_1(dataGridId) {
	$('#' + dataGridId).datagrid('load');
	$('#' + dataGridId).datagrid('uncheckAll');
}

/*
 * 删除指标
 */
function toDel(kpiID){
	$.messager.confirm("提示","确定删除此指标?",function(r){
		if (r == true) {
			var path = getRootName();
			var uri = path + "/monitor/perfKPIDef/delPerfKPIDef";
			var ajax_param = {
				url : uri,
				type : "post",
				datdType : "json",
				data : {
					"kpiID" : kpiID,
					"t" : Math.random()
				},
				error : function() {
					$.messager.alert("错误", "ajax_error", "error");
				},
				success : function(data) {
					if (true == data.flag || "true" == data.flag) {
						$.messager.alert("提示", "该指标删除成功！", "info");
						reloadTable();
					} else if(true ==data.checkRS|| "true" == data.checkRS) {
						$.messager.alert("提示", "该指标正在被使用，删除失败！", "error");
						reloadTable();
					}else{
						$.messager.alert("提示", "数据库操作异常，该指标删除失败！", "error");
					}
					getAllCategory();
				}
			};
			ajax_(ajax_param);
		}
	});
}

/*
 * 批量删除
 */
function doBatchDel(){
	var path=getRootName();
	var checkedItems = $('[name=kpiID]:checked');
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
				var path = getRootName();
				var uri = path + "/monitor/perfKPIDef/delPerfKPIDefs?kpiIDs="+ids;
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
						if (true == data.flag || "true" == data.flag) {
							$.messager.alert("提示", "指标删除成功！", "info");
							reloadTable();
						} else if(true ==data.rs|| "true" == data.rs) {
							$.messager.alert("提示", "被删指标"+data.kpiName+"正在被使用，删除失败！", "error");
							reloadTable();
						}else{
							$.messager.alert("提示", "数据库操作异常，指标删除失败！", "error");
						}
						getAllCategory();
					}
				};
				ajax_(ajax_param);
			}
		});
	} else {
		$.messager.alert('提示', '没有任何选中项', 'error');
	}
}

/*
 * 打开新增页面
 */
function doOpenAdd(){
	parent.$('#popWin').window({
    	title:'新增采集指标',
        width:800,
        height : 440,
        minimizable:false,
        maximizable:false,
        collapsible:false,
        modal:true,
        href: getRootName() + '/monitor/perfKPIDef/toPerKPIDefAdd',
    });
}

/*
 * 打开更新页面
 */
function doOpenModify(kpiID){
	parent.$('#popWin').window({
    	title:'编辑采集指标',
        width:800,
        height : 440,
        minimizable:false,
        maximizable:false,
        collapsible:false,
        modal:true,
        href: getRootName() + '/monitor/perfKPIDef/toPerKPIDefModify?kpiID='+kpiID,
    });
}

/*
 * 打开查看页面
 */
function doOpenDetail(kpiID){
	parent.$('#popWin').window({
    	title:'采集指标详情',
        width:800,
        height : 500,
        minimizable:false,
        maximizable:false,
        collapsible:false,
        modal:true,
        href: getRootName() + '/monitor/perfKPIDef/toPerKPIDefDetail?kpiID='+kpiID,
    });
}

/*
 * 打开配置关系页面
 */
function toOpenSet(kpiID,classID){
	parent.$('#popWin').window({
    	title:'配置采集指标',
        width:800,
        height : 440,
        minimizable:false,
        maximizable:false,
        collapsible:false,
        modal:true,
        href: getRootName() + '/monitor/perfKPIDef/toOpenSet?kpiID='+kpiID+'&classID='+classID,
    });
}