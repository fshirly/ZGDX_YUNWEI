$(document).ready(function() {
	doInitTable();
});

/*
 * 页面加载初始化表格 @author 武林
 */
function doInitTable() {
	var path = getRootName();
	$('#tblSiteCommunity')
			.datagrid(
					{
						iconCls : 'icon-edit',// 图标
						width : 700,
						height : 'auto',
						nowrap : false,
						striped : true,
						border : true,
						collapsible : false,// 是否可折叠的
						fit : true,// 自动大小
						fitColumns:true,
						url : path + '/monitor/siteCommunity/listSiteCommunity',
						remoteSort : false,
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
									 field : 'id',
									 checkbox : true
								}, 
								{
									field : 'ipAddress',
									title : '地址',
									align : 'center',
									width : 180,
									formatter : function(value, row, index) {
						        		if(value && value.length > 20){
						        		value2 = value.substring(0,40) + "...";
										 return '<span title="' + value + '" style="cursor: pointer;" >' +value2+'</span>';
							        	}else{
											return value;
										}
									}
								},
								{
									field : 'siteType',
									title : '站点类型',
									align : 'center',
									width : 70,
									formatter : function(value, row, index) {
										if (value == 1) {
											return 'FTP';
										}else if(value == 2){
											return 'HTTP';
										}else if(value == 3){
											return 'Email';
										}
									}
								},
								{
									field : 'userName',
									title : '用户名',
									align : 'center',
									width : 100,
									formatter : function(value, row, index) {
										if (value == null || value == "") {
											return '-';
										}else{
											return value;
										}
									}
								},
								/*{
									field : 'password',
									title : '密码',
									align : 'center',
									width : 100,
								},*/
								{
									field : 'port',
									title : '端口',
									align : 'center',
									width : 70,
									formatter : function(value, row, index) {
										if (value == null || value == "") {
											return '-';
										}else{
											return value;
										}
									}
								},
								{
									field : 'requestMethod',
									title : 'HTTP请求方式',
									align : 'center',
									width : 80,
									formatter : function(value, row, index) {
										if (value == 1) {
											return 'GET';
										}else if(value == 2){
											return 'POST';
										}else if(value == 3){
											return 'HEAD';
										}else{
											return '-'
										}
									}
								},
								{
									field : 'ids',
									title : '操作',
									width : 130,
									align : 'center',
									formatter : function(value, row, index) {
										return '<a style="cursor: pointer;" onclick="javascript:doOpenDetail('
											+ row.id
											+ ');"><img src="'
											+ path
											+ '/style/images/icon/icon_view.png" title="查看" /></a> &nbsp;<a style="cursor: pointer;" onclick="javascript:doOpenModify('
											+ row.id
											+ ');"><img src="'
											+ path
											+ '/style/images/icon/icon_modify.png" title="修改" /></a> &nbsp;<a style="cursor: pointer;" onclick="javascript:toDel('
											+ row.id
											+ ');"><img src="'
											+ path
											+ '/style/images/icon/icon_delete.png" title="删除" /></a>';
									}
								} ] ]
					});
	 $(window).resize(function() {
	        $('#tblSiteCommunity').resizeDataGrid(0, 0, 0, 0);
	    });
}

/*
 * 更新表格
 */
function reloadTable(){
	var ipAddress = $("#txtIPAddress").val();
	var siteType = $("#txtSiteType").val();
	$('#tblSiteCommunity').datagrid('options').queryParams = {
		"ipAddress" : ipAddress,
		"siteType" : siteType,
	};
	reloadTableCommon_1('tblSiteCommunity');
}

function reloadTableCommon_1(dataGridId) {
	$('#' + dataGridId).datagrid('load');
	$('#' + dataGridId).datagrid('uncheckAll');
}

/*
 * 打开查看页面
 */
function doOpenDetail(id){
	parent.$('#popWin').window({
    	title:'站点凭证详情',
        width:800,
        height : 300,
        minimizable:false,
        maximizable:false,
        collapsible:false,
        modal:true,
        href: getRootName() + '/monitor/siteCommunity/toShowDetail?id='+id
    });
}

/*
 * 删除阈值规则定义
 */
function toDel(id){
	$.messager.confirm("提示","确定删除所选中的项?",function(r){
		if (r == true) {
			var path = getRootName();
			var uri = path + "/monitor/siteCommunity/delSiteCommunity";
			var ajax_param = {
				url : uri,
				type : "post",
				datdType : "json",
				data : {
					"id" : id,
					"t" : Math.random()
				},
				error : function() {
					$.messager.alert("错误", "ajax_error", "error");
				},
				success : function(data) {
					if (true == data || "true" == data) {
						$.messager.alert("提示", "站点凭证删除成功！", "info");
						reloadTable();
					} else {
						$.messager.alert("提示", "站点凭证删除失败！", "error");
						reloadTable();
					}
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
	var checkedItems = $('[name=id]:checked');
	var ids = null;
	$.each(checkedItems, function(index, item) {
		if (null == ids) {
			ids = $(item).val();
		} else {
			ids += ',' + $(item).val();
		}
	});
	if (null != ids) {
		$.messager.confirm("提示","确定删除所选中的项？",function(r){
			if (r == true) {
				var path = getRootName();
				var uri = path + "/monitor/siteCommunity/delSiteCommunitys?ids="+ids;
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
						if (false == data.flag || "false" == data.flag) {
							$.messager.alert("提示", "站点凭证删除失败！", "error");
							reloadTable();
						} else {
							$.messager.alert("提示", "站点凭证删除成功！", "info");
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

/*
 * 打开新增页面
 */
function doOpenAdd(){
	parent.$('#popWin').window({
    	title:'站点凭证新建',
        width:800,
        height : 450,
        minimizable:false,
        maximizable:false,
        collapsible:false,
        modal:true,
        href: getRootName() + '/monitor/siteCommunity/toShowAdd',
    });
}

/*
 * 打开编辑页面
 */
function doOpenModify(id){
	parent.$('#popWin').window({
    	title:'站点凭证编辑',
        width:800,
        height : 450,
        minimizable:false,
        maximizable:false,
        collapsible:false,
        modal:true,
        href: getRootName() + '/monitor/siteCommunity/toShowModify?id='+id
    });
}
