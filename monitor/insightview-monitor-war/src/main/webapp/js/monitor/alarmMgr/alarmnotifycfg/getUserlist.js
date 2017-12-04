$(document).ready(function() {
	doInitUserTable();
	
});

/*
 * 页面加载初始化表格 @author 武林
 */
function doInitUserTable() {
	var path = getRootName();
	$('#tblSysUser')
			.datagrid(
					{
						iconCls : 'icon-edit',// 图标
						width : 700,
						height : 'auto',
						nowrap : false,
						striped : true,
						border : true,
						collapsible : false,// 是否可折叠的
//						fit : true,// 自动大小
						fitColumns:true,
						url : path + '/permissionSysUser/listSysUser',
						// sortName: 'code',
						// sortOrder: 'desc',
						queryParams : {
							"status" : -1,
							"isAutoLock" : -1,
							"userType" : -1
						},
						remoteSort : false,
						idField : 'fldId',
						singleSelect : false,// 是否单选
						checkOnSelect : false,
						selectOnCheck : true,
						pagination : true,// 分页控件
						rownumbers : true,// 行号
						columns : [ [
						        {
						        	field : 'userID',
						        	checkbox : true,
						        },
								{
									field : 'userName',
									title : '用户姓名',
									width : 130,
									align : 'center'
								},
								{
									field : 'mobilePhone',
									title : '手机号码',
									width : 180,
									align : 'center'
								},
								{
									field : 'email',
									title : '邮箱',
									width : 180,
									align : 'center'
								},
								{
									field : 'userIDs', 
									title : '操作',
									width : 100,
									align : 'center',
									formatter : function(value, row, index) {
										return '<a style="cursor: pointer;" onclick="javascript:doDel('
												+ row.userID
												+ ');"><img src="'
												+ path
												+ '/style/images/icon/icon_delete.png" title="删除"/></a>';
									}
								} ] ]
					});
    $(window).resize(function() {
        $('#tblSysUser').resizeDataGrid(0, 0, 0, 0);
    });
}

function reloadUserTable() {
	var userName =  $("#txtFilterUserAccount").val();
	$('#tblSysUser').datagrid('options').queryParams = {
		"userName" : userName
	};
	reloadTableCommon_1('tblSysUser')
}

function reloadTableCommon_1(dataGridId) {
	$('#' + dataGridId).datagrid('load');
	$('#' + dataGridId).datagrid('uncheckAll');
}

/*
 * 添加用户
 */
function addUser(){
	var path=getRootName();
	var checkedItems = $('[name=userID]:checked');
	var ids = null;
	$.each(checkedItems, function(index, item) {
		if (null == ids) {
			ids = $(item).val();
		} else {
			ids += ',' + $(item).val();
		}
	});
	if (null != ids) {
		$.messager.confirm("提示","确定添加所选中项？",function(r){
			if (r == true) {
				var uri = path+"monitor/alarmNotifyToUsers/addUserFromList?userIds=" + ids;
				var ajax_param = {
					url : uri,
					type : "post",
					datdType : "json",
					data : {
						"t" : Math.random()
					},
					success : function(data) {
						if(data == false) {
			            	$.messager.alert("错误", "用户添加失败！", "error");
						} else {
							window.frames['component_2'].reloadTable();
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


