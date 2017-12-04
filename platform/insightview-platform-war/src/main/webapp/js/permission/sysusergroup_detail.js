$(function(){
	toShowInfo();
});
 
//查看用户详情
function toShowInfo(){
	var path = getRootName();
	$('#viewSysUser')
			.datagrid(
					{
						iconCls : 'icon-edit',// 图标
						width : 'auto',
						height : 350,
						nowrap : false,
						striped : true,
						border : true,
						collapsible : false,// 是否可折叠的
						fitColumns:true,
						url : path + '/permissionSysUser/listSysUserGroup',
						queryParams : {
							"isAutoLock" : -1,
							"userType" : -1,
							"belongGroupId" : $('#ipt_groupID').val(),
							"status" : -1
						},
						remoteSort : false,
						idField : 'fldId',
						singleSelect : false,// 是否单选
						checkOnSelect : true,
						selectOnCheck : true,
						pagination : true,// 分页控件
						rownumbers : true,// 行号
						toolbar : [
						{
							'text' : '移除',
							'iconCls' : 'icon-remove-other',
							handler : function() {
								doBatchRemove();
							}
						}],
						columns : [ [
					             {
					            	 field : 'userID',
					            	 checkbox : true
					            	 
					             },	
								{
									field : 'userAccount',
									title : '用户名',
								},
								{
									field : 'userName',
									title : '姓名',
									width : 80
								},
								{
									field : 'mobilePhone',
									title : '移动电话',
									width : 100,
								},
								{
									field : 'te',
									title : '操作',
									align : 'center',
									formatter : function(value, row, index) {
										return 	    '<a style="cursor: pointer;" onclick="javascript:toRemove('
													+ row.userID
													+ ');"><img src="'
													+ path
													+ '/style/images/icon/icon_subtract.png" alt="移除" title="移除"/></a>';
									}
								}
								 ] ]
					});
}

/*
 * 更新用户信息列表
 */
function reloadUserTable() {
	$('#viewSysUser').datagrid('options').queryParams = {
		"isAutoLock" : -1,
		"userType" : -1,
		"belongGroupId" : $('#ipt_groupID').val(),
		"status" : -1
	};
	reloadTableCommon('viewSysUser');
}

/* 将用户从用户组中移除*/
function  toRemove(userId){
	var path = getRootPatch();
	if(null != userId){
		$.messager.confirm("提示","确定移除所选中用户？",function(r){
			if (r == true) {
				var uri = path + "/permissionSysUserGroup/removeSysUserInGroups";
				var ajax_param = {
					url : uri,
					type : "post",
					datdType : "json",
					data : {
						"userIds" : userId,
						"groupId" : $('#ipt_groupID').val(),
						"t" : Math.random()
					},
					error : function() {
						$.messager.alert("错误", "ajax_error", "error");
					},
					success : function(data) {
						if (true == data || "true" == data) {
							_userIds = null;
//							$.messager.alert("提示", "将用户从用户组移除成功！", "info");
							reloadUserTable();
						} else {
							$.messager.alert("提示", "将用户从用户组移除失败！", "error");
						}
					}
				}
				ajax_(ajax_param);
				}
		});
	}else{
		$.messager.alert('提示', '没有任何选中项', 'error');
	}
}

/**
 * 批量移除用户
 * @return
 */
var _userIds = null;
function doBatchRemove(){
	var checkedItems = $('[name=userID]:checked');
	$.each(checkedItems, function(index, item) {
		if (null == _userIds) {
			_userIds = $(item).val();
		} else {
			_userIds += ',' + $(item).val();
		}
	});
	toRemove(_userIds);
}
