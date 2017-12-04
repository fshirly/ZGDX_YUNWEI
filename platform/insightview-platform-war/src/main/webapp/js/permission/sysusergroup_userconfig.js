$(function(){
	doInitUserTable();
	var organizationID = $("#ipt_organizationID").val();
	$('#iptFilterProcider').createSelect2({
        uri : '/platform/supplier/queryProviderInOrg?organizationID='+organizationID,
        name : 'providerName',
        id : 'providerId'
    });
	
	$('#iptFilterSysuserType').combobox({
		editable : false,
		onChange : function(){
			showProvider();
		}
	});
});

function showProvider(){
	var userType = $("#iptFilterSysuserType").combobox("getValue");
	if(userType == 1){
		$("#provider").show();
	}else{
		$("#provider").hide();
	}
}

/**
 * 页面加载初始化表格
 * 
 * @author 武林
 */
function doInitUserTable() {
	var path = getRootName();
	var organizationID = $("#ipt_organizationID").val();
	$('#tblSysUser')
	.datagrid(
			{
				iconCls : 'icon-edit',// 图标
				width : 'auto',
				height : 360,
				nowrap : false,
				striped : true,
				border : true,
				collapsible : false,// 是否可折叠的
//					fit : true,// 自动大小
				fitColumns:true,
				url : path + '/permissionSysUser/listSysUserGroup?organizationID='+organizationID,
				queryParams : {
				"isAutoLock" : -1,
				"userType" : -1,
				"groupIdFilter" : $('#ipt_groupID').val(),
				"status" : -1
			},
			remoteSort : false,
			idField : 'fldId',
			singleSelect : false,// 是否单选
			checkOnSelect : true,
			selectOnCheck : true,
			pagination : true,// 分页控件
			rownumbers : true,// 行号
			toolbar : [ {
				'text' : '增加',
				'iconCls' : 'icon-add-other',
				handler : function() {
				doBatchAdd();
			}
			} ],
			columns : [ [
			             {
			            	 field : 'userID',
			            	 checkbox : true
			             },// 1
			             {
			            	 field : 'userAccount',
			            	 title : '用户名',
			            	 width : 80
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
			            	 width : 100,
			            	 align : 'center',
			            	 formatter : function(value, row, index) {
			            	 return '<a style="cursor: pointer;"  onclick="javascript:doConfigUserGroup('
			            	 + row.userID + ');"><img src="'
			            	 + path
			            	 + '/style/images/icon/icon_add.png" alt="增加" title="增加"</a>';
			             	}
			             } ] ]
			});
}

/*
 * 更新用户信息列表
 */
function reloadUserTable() {
	var userType = $("#iptFilterSysuserType").combobox("getValue");
	var providerId = $("#iptFilterProcider").select2("val");
	var organizationID = $("#ipt_organizationID").val();
	var userAccount = $("#iptFilterSysuserAccout").val();
	var userName = $("#iptFilterSysuserName").val();
	if(userType == 1){
		$('#tblSysUser').datagrid('options').url = getRootName() + '/platform/supplier/listProviderUser?providerId='+providerId+'&organizationID='+organizationID;
		$('#tblSysUser').datagrid('options').queryParams = {
			"userAccount" : userAccount,
			"userName" : userName,
			"groupIdFilter" : $('#ipt_groupID').val()
		};
	}else{
		$('#tblSysUser').datagrid('options').url = getRootName() + '/permissionSysUser/listSysUserGroup?organizationID='+organizationID;
		$('#tblSysUser').datagrid('options').queryParams = {
			"isAutoLock" : -1,
			"userType" : -1,
			"userAccount" : userAccount,
			"userName" : userName,
			"groupIdFilter" : $('#ipt_groupID').val(),
			"status" : -1
		};
	}
	
	reloadTableCommon('tblSysUser');
}

/*
 * 添加用户 
 */

function doConfigUserGroup(userId) {
	var path = getRootPatch();
	if(null != userId){
		$.messager.confirm("提示","确定添加所选中用户？",function(r){
			if (r == true) {
				var uri = path + "/permissionSysUserGroup/addSysUserInGroups";
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
//							$.messager.alert("提示", "配置用户组成功！", "info");
							reloadUserTable();
						} else {
							$.messager.alert("提示", "配置用户组失败！", "error");
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
 * 重置
 * @param pControlId
 * @return
 */
function resetFormFilter(pControlId) {
//	resetForm(pControlId);
	$("#iptFilterSysuserAccout").val("");
	$("#iptFilterSysuserName").val("");
	$("#iptFilterProcider").select2("val", -1);
	$('#iptFilterSysuserType').combobox('setValue',-1);
}


/*
 * 批量添加用户
 */
var _userIds = null;
function doBatchAdd() {
	var checkedItems = $('[name=userID]:checked');
	$.each(checkedItems, function(index, item) {
		if (null == _userIds) {
			_userIds = $(item).val();
		} else {
			_userIds += ',' + $(item).val();
		}
	});
	doConfigUserGroup(_userIds);
}

///*
// * 更新用户信息列表
// */
//function reloadUserTable() {
//	var userAccount = $("#iptFilterSysuserAccout").val();
//	var userName = $("#iptFilterSysuserName").val();
//
//	$('#tblSysUser').datagrid('options').queryParams = {
//		"isAutoLock" : -1,
//		"userType" : -1,
//		"userAccount" : userAccount,
//		"userName" : userName,
//		"groupIdFilter" : $('#ipt_groupID').val(),
//		"status" : -1
//	};
//	reloadTableCommon('tblSysUser');
//	var userAccount = $("#iptFilterSysuserAccout").val("");
//	var userName = $("#iptFilterSysuserName").val("");
//	
//}


