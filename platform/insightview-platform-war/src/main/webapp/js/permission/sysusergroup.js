$(document).ready(function() {
	doInitTable();
	doInitLRselect();
	initTree();
});

/**
 * 验证表单信息
 * 
 * @author 武林
 */
function checkForm() {
	return checkInfo('#tblAddUserGroup');
}

/*
 * 更新用户信息列表
 */
function reloadUserTable() {
	var userAccount = $("#iptFilterSysuserAccout").val();
	var userName = $("#iptFilterSysuserName").val();

	$('#tblSysUser').datagrid('options').queryParams = {
		"isAutoLock" : -1,
		"userType" : -1,
		"userAccount" : userAccount,
		"userName" : userName,
		"groupIdFilter" : _groupId,
		"status" : -1
	};
	reloadTableCommon_1('tblSysUser');
	var userAccount = $("#iptFilterSysuserAccout").val("");
	var userName = $("#iptFilterSysuserName").val("");
}

/*
 * 添加用户 
 */
var _groupId;
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
						"groupId" : _groupId,
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

/*
 * 配置用户
 */
function toConfigUser(groupID,organizationID) {
//	_groupId = groupID;
//	doInitUserTable();
//	$('#divSysUserConfig').dialog('open');
//	var userAccount = $("#iptFilterSysuserAccout").val("");
//	var userName = $("#iptFilterSysuserName").val("");
	parent.$('#popWin').window({
    	title:'配置用户',
        width:800,
        height:570,
        top:160,
        minimizable:false,
        maximizable:false,
        collapsible:false,
        modal:true,
        href: getRootName() + '/permissionSysUserGroup/toShowUserConfig?groupID='+groupID+'&organizationID='+organizationID
    });
}

/*
 * 关闭配置用户界面
 */
function closeUserConfig(){
	$('#divSysUserConfig').dialog('close');
}


/*
 * 批量添加用户
 */
var _userIds = null;
function doBatchAdd() {
	var checkedItems = $('[name=0]:checked');
	$.each(checkedItems, function(index, item) {
		if (null == _userIds) {
			_userIds = $(item).val();
		} else {
			_userIds += ',' + $(item).val();
		}
	});
	doConfigUserGroup(_userIds);
}
/**
 * 用户组名称唯一性验证(新增时）
 * 
 * @author 武林
 */
function checkGroupName(doName) {
	var orgId = $("#ipt_organizationID").attr("alt");
	if(doName == "update"){
		var groupID = $("#ipt_groupID").val();
	}
	var userAccount = $("#ipt_groupName").val();
	var path = getRootName();
	var uri = path + "/permissionSysUserGroup/checkUserGroup";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"groupID" : groupID,
			"groupName" : userAccount,
			"organizationBean.organizationID" : orgId,
			"doName" : doName,
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			if (false == data || "false" == data) {
				$.messager.alert("提示", "该用户组在此单位已存在！", "error");
				return false;
			} else if(doName == "add"){
				doAddTwo();
			}else{
				doUpdateTwo();
			}
		}
	};
	ajax_(ajax_param);
}
/**
 * 页面加载初始化表格
 * 
 * @author 武林
 */
function doInitUserTable() {
	var path = getRootName();
	$('#tblSysUser')
			.datagrid(
					{
						iconCls : 'icon-edit',// 图标
						width : 'auto',
						height : 'auto',
						nowrap : false,
						striped : true,
						border : true,
						collapsible : false,// 是否可折叠的
//						fit : true,// 自动大小
						fitColumns:true,
						url : path + '/permissionSysUser/listSysUserGroup',
						queryParams : {
							"isAutoLock" : -1,
							"userType" : -1,
							"groupIdFilter" : _groupId,
							"status" : -1
						},
						remoteSort : false,
						idField : 'id',
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

var _openMenuCount = 0;
function toConfigMenu() {
	if (0 == _openMenuCount) {
		++_openMenuCount;
		var path = getRootPatch();
		var uri = path + "/permissionSysMenuModule/findSysMenuTreeVal";
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
				dataTreeMenu = new dTree("dataTreeMenu", path
						+ "/plugin/dTree/img/");
				dataTreeMenu.add(0, -1, "选择上级菜单", "");

				// 得到树的json数据源
				var datas = eval('(' + data.menuLstJson + ')');
				_treeData = datas;
				// 遍历数据
				var gtmdlToolList = datas;
				for (var i = 0; i < gtmdlToolList.length; i++) {
					var _id = gtmdlToolList[i].menuId;
					var _nameTemp = gtmdlToolList[i].menuName;
					var _parent = gtmdlToolList[i].parentMenuID;

					dataTreeMenu.add(_id, _parent, _nameTemp,
							"javascript:hiddenDTreeSetVal('selFilterParentId','"
									+ _id + "','" + _nameTemp + "');");
				}
				$('#dataMenuTreeDiv').append(dataTreeMenu + "");
				$('#dataMenuTreeDivs').append(dataTreeMenu + "");
			}
		}
		ajax_(ajax_param);
	}
	$('#divMenuConfig').dialog('open');
}
function resetFormAdd(pControlId) {
	resetForm(pControlId);
	$("#ipt_parentDeptID").attr('alt', '');
	$("#ipt_organizationID").attr('alt', '');
}

function hiddenDTreeSetValEasyUiTemp() {
	hiddenDTreeSetValEasyUi("divChoseOrg", "", "", "");
}

function doChoseParentOrg() {
	if (0 == _treeOpenCount) {
		++_treeOpenCount;
		var path = getRootPatch();
		dataTreeTwo = new dTree("dataTreeTwo", path + "/plugin/dTree/img/");
		dataTreeTwo.add(0, -1, "选择上级单位", "");
		// 得到树的json数据源
		var datas = _treeData;

		// 遍历数据
		var gtmdlToolList = datas;
		for (var i = 0; i < gtmdlToolList.length; i++) {
			var _id = gtmdlToolList[i].organizationID;
			var _nameTemp = gtmdlToolList[i].organizationName;
			var _parent = gtmdlToolList[i].parentOrgID;

			dataTreeTwo.add(_id, _parent, _nameTemp,
					"javascript:hiddenDTreeSetValEasyUi('divChoseOrg','ipt_organizationID','"
							+ _id + "','" + _nameTemp + "');");
		}
		$('#dataTreeDivs').append(dataTreeTwo + "");
	}
	$('#divChoseOrg').dialog('open');
}

function resetFormFilter(pControlId) {
	resetForm(pControlId);
	$("#selOrganizationName").val("");
}

var _treeData = "";
var _treeOpenCount = 0;
function initTree() {
	var path = getRootPatch();
	var uri = path + "/permissionOrganization/findOrganizationLst";
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
			dataTree = new dTree("dataTree", path + "/plugin/dTree/img/");
			dataTree.add(0, -1, "选择上级单位", "");

			// 得到树的json数据源
			var datas = eval('(' + data.organizationLstJson + ')');
			_treeData = datas;
			// 遍历数据
			var gtmdlToolList = datas;
			for (var i = 0; i < gtmdlToolList.length; i++) {
				var _id = gtmdlToolList[i].organizationID;
				var _nameTemp = gtmdlToolList[i].organizationName;
				var _parent = gtmdlToolList[i].parentOrgID;

				dataTree.add(_id, _parent, _nameTemp,
						"javascript:hiddenDTreeSetVal('selOrganizationId','"
								+ _id + "','" + _nameTemp + "');");
			}
			$('#dataTreeDiv').append(dataTree + "");

		}
	};
	ajax_(ajax_param);
}

function resetGroupRole() {
	var valArr = "";
	$("#selRight option").each(function() {
		var val = $(this).val(); // 获取单个value
		valArr += val + ",";
	});
	var path = getRootName();
	var uri = path + "/permissionSysUserGroup/updateGroupRole";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"groupID" : _groupID,
			"roleIdArr" : valArr,
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			if (true == data || "true" == data) {
//				$.messager.alert("提示", "角色分配成功！", "info");
				$('#divAllotRole').dialog('close');
			} else {
				$.messager.alert("提示", "角色分配失败！", "error");
			}
		}
	}
	ajax_(ajax_param);
}
function doInitLRselect() {
	$.fn.LRSelect("selLeft", "selRight", "img_L_AllTo_R", "img_L_To_R",
			"img_R_To_L", "img_R_AllTo_L");
	$.fn.UpDownSelOption("imgUp", "imgDown", "selRight");
}

function doUpdate(groupID){
	var doName = "update";
	checkGroupName(doName);
}

function doUpdateTwo() {
		var result=checkInfo('#divAddUserGroup');
		var path = getRootName();
		var uri = path + "/permissionSysUserGroup/updateSysUserGroup";
		var ajax_param = {
			url : uri,
			type : "post",
			datdType : "json",
			data : {
				"groupID" : $("#ipt_groupID").val(),
				"organizationBean.organizationID" : $("#ipt_organizationID")
						.attr("alt"),
				"groupName" : $("#ipt_groupName").val(),
				"descr" : $("#ipt_descr").val(),
				"t" : Math.random()
			},
			error : function() {
				$.messager.alert("错误", "ajax_error", "error");
			},
			success : function(data) {
				if (true == data || "true" == data) {
//					$.messager.alert("提示", "用户组修改成功！", "info");
					$('#divAddUserGroup').dialog('close');
					reloadTable();
				} else {
					$.messager.alert("提示", "用户组修改失败！", "error");
					reloadTable();
				}
			}
		}
		if(result == true){
			ajax_(ajax_param);
		}
}

/*
 * 初始化更新
 */
function initUpdateVal(groupID, orgName) {
	var path = getRootName();
	var uri = path + "/permissionSysUserGroup/findSysUserGroup";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"groupID" : groupID,
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			resetForm('tblAddUserGroup');
			iterObj(data, "ipt");
			if (null != orgName && 'null' != orgName) {
				$("#ipt_organizationID").attr('alt',
						data.organizationBean.organizationID);
				$("#ipt_organizationID").val(orgName);
			} else {
				$("#ipt_organizationID").attr('alt', '');
				$("#ipt_organizationID").val('');
			}
		}
	}
	ajax_(ajax_param);
}

function toUpdate(groupID, orgName) {
	initUpdateVal(groupID, orgName);

	$("#btnSave").unbind('click');
	$("#btnSave").bind("click", function() {
		doUpdate(groupID);
	});
	$("#btnUpdate").unbind('click');
	$("#btnUpdate").bind("click", function() {
		$('#divAddUserGroup').dialog('close');
	});

	$('#divAddUserGroup').dialog('open');
}

/*
 * 分配角色新的打开方式
 */
function toDivAllotRolePop(groupID){
	parent.$('#popWin').window({
    	title:'分配角色',
        width:600,
        height:530,
        top:200,
        minimizable:false,
        maximizable:false,
        collapsible:false,
        modal:true,
        resizable:false,
        href: getRootName() + '/permissionSysUserGroup/toDivAllotRolePop?groupID='+groupID
    });
}


/*
 * 分配角色
 */
var _groupID = "";
function toDivAllotRole(groupID) {
	_groupID = groupID;
	$("#selLeft").empty();
	$("#selRight").empty();
	var path = getRootName();
	var uri = path + "/permissionSysUserGroup/findGroupRole";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"groupID" : groupID,
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			var roleLst = eval('(' + data.roleLstJson + ')');
			for (var i = 0; i < roleLst.length; i++) {
				var sysRole = roleLst[i];
				$("#selLeft").append(
						"<option value='" + sysRole.roleId + "'>"
								+ sysRole.roleName + "</option>");
			}

			var groupRoleLst = eval('(' + data.groupRoleLstJson + ')');
			for (var i = 0; i < groupRoleLst.length; i++) {
				var groupRole = groupRoleLst[i];
				$("#selRight").append(
						"<option value='" + groupRole.roleId + "'>"
								+ groupRole.roleName + "</option>");
			}

			$('#divAllotRole').dialog('open');
		}
	};
	ajax_(ajax_param);
}

/*
 * 关闭分配角色界面
 */
function closeAllotRole(){
	$('#divAllotRole').dialog('close');
}

/*
 * 打开新增div @author 武林
 */
function toAdd() {
	resetForm('tblAddUserGroup');

	$("#btnSave").unbind('click');
	$("#btnSave").bind("click", function() {
		doAdd();
	});
	$("#btnUpdate").unbind('click');
	$("#btnUpdate").bind("click", function() {
		window.location.href = window.location.href;
		$('#divAddUserGroup').dialog('close');
	});

	$('#divAddUserGroup').dialog('open');
}

/*
 * 新增用户组 @author 武林
 */
function doAdd() {
	var doName = "add";
	var result=checkInfo('#divAddUserGroup');
	if(result == true){
		checkGroupName(doName);
	}
}

function doAddTwo() {
	var result=checkInfo('#divAddUserGroup');
	var path = getRootName();
	var uri = path + "/permissionSysUserGroup/addSysUserGroup";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"groupName" : $("#ipt_groupName").val(),
			"organizationBean.organizationID" : $("#ipt_organizationID").attr(
					"alt"),
			"descr" : $("#ipt_descr").val(),
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			if (true == data || "true" == data) {
//				$.messager.alert("提示", "用户组新增成功！", "info");
				$('#divAddUserGroup').dialog('close');
				reloadTable();
			} else {
				$.messager.alert("提示", "用户组新增失败！", "error");
			}
		}
	}
	
		ajax_(ajax_param);
}
/*
 * 更新表格 @author 武林
 */
function reloadTable() {
	var groupName = $("#txtGroupName").val();
	var organizationName = $("#selOrganizationName").val();

	$('#tblSysUserGroup').datagrid('options').queryParams = {
		"organizationName" : organizationName,
		"groupName" : groupName
	};
	reloadTableCommon_1('tblSysUserGroup');
}

function reloadTableCommon_1(dataGridId) {
	$('#' + dataGridId).datagrid('load');
	$('#' + dataGridId).datagrid('uncheckAll');
}

/*
 * 删除用户组 @author 武林
 */
function doDel(groupID) {
	$.messager.confirm("提示","确定删除此条？",function(r){
	if (r == true) {
		if(groupID > 1){
			var path = getRootName();
			var uri = path + "/permissionSysUserGroup/delSysUserGroup";
			var ajax_param = {
					url : uri,
					type : "post",
					datdType : "json",
					data : {
				"groupID" : groupID,
				"t" : Math.random()
			},
			error : function() {
				$.messager.alert("错误", "ajax_error", "error");
			},
			success : function(data) {
				if (true == data || "true" == data) {
//					$.messager.alert("提示", "用户组删除成功！", "info");
					reloadTable();
				} else {
					doAfterDel(groupID);
//					$.messager.alert("提示", "该用户组正在被使用,删除失败！", "error");
				}
			}
			}
			ajax_(ajax_param);
		}else{
			$.messager.alert("错误", "该角色为系统自定义用户，不能删除！", "error");
		}
	}
	});
}

/**
 * 删除，如果正在被使用
 * @param groupID
 * @return
 */
function doAfterDel(groupID){
	$.messager.confirm("提示","该用户组正在被使用，确定删除？",function(r){
		if (r == true) {
			var path = getRootName();
			var uri = path + "/permissionSysUserGroup/doAfterDel";
			var ajax_param = {
				url : uri,
				type : "post",
				datdType : "json",
				data : {
					"groupID" : groupID,
					"t" : Math.random()
				},
				error : function() {
					$.messager.alert("错误", "ajax_error", "error");
				},
				success : function(data) {
					if (true == data || "true" == data) {
						reloadTable();
					} else {
						$.messager.alert("提示", "数据库操作异常，删除失败！", "error");
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
 * @return
 */
function doBatchDel(){
	var path=getRootName();
	var checkedItems = $('[name=groupID]:checked');
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
				var uri = path+"/permissionSysUserGroup/delSysUserGroups?groupIds=" + ids;
				var ajax_param = {
						url : uri,
						type : "post",
						datdType : "json",
						data : {
						"t" : Math.random()
				},
				success : function(data) {
					if (false == data || "false" == data) {
						$.messager.alert("提示", "数据库操作异常，用户组删除失败！", "error");
					}else{
						if(data.isSystem == false) {
							$.messager.alert("提示", "用户组"+data.sysGroupName+"为系统自定义用户，不能删除！", "info");
						}
						if(data.isUse == false) {
							doAfterBatchDel(data.reserveIds,data.groupName);
						}
					}
					reloadTable();
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
 * 批量删除，如果正在被使用
 * @param groupID
 * @return
 */
function doAfterBatchDel(groupIDs,groupName){
	$.messager.confirm("提示","被删用户组"+groupName+"正在被使用，确定删除？",function(r){
		if (r == true) {
			var path = getRootName();
			var uri = path + "/permissionSysUserGroup/doAfterBatchDel?groupIDs="+groupIDs;
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
						reloadTable();
					} else {
						$.messager.alert("提示", "数据库操作异常，删除失败！", "error");
						reloadTable();
					}
				}
			}
			ajax_(ajax_param);
		}
		});
}

/*
 * 页面加载初始化表格 @author 武林
 */
function doInitTable() {
	var path = getRootName();
	$('#tblSysUserGroup')
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
						url : path + '/permissionSysUserGroup/listSysUserGroup',
						// sortName: 'code',
						// sortOrder: 'desc',
						queryParams : {
							"organizationID" : -1
						},
						remoteSort : false,
						idField : 'groupID',
						checkOnSelect : false, 
						selectOnCheck : false,
						singleSelect : false,// 是否单选
						pagination : true,// 分页控件
						rownumbers : true,// 行号
						toolbar : [ {
							'text' : '新增',
							'iconCls' : 'icon-add',
							handler : function() {
								toAdd();
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
									 field : 'groupID',
									 checkbox : true
									 
								},     
								{
									field : 'groupName',
									title : '用户组名称',
									width : 180
								},
								{
									field : 'organizationName',
									title : '所属单位',
									width : 130,
									formatter : function(value, org) {
										if(org!=null && org.organizationBean!=null){
											return org.organizationBean.organizationName;
										}else{
											return '';
										}
										
									}
								},
								{
									field : 'descr',
									title : '备注',
									width : 180
								},
								{
									field : 'groupIDs',
									title : '操作',
									width : 280,
									align : 'center',
									formatter : function(value, row, index) {
										var organizationName='';
										var organizationID='';
										if(row!=null &&  row.organizationBean!=null){
											organizationName=row.organizationBean.organizationName;
											organizationID=row.organizationBean.organizationID;
										}else{
											organizationName=null;
											organizationID=null;
										}
										var to = "&quot;"
												+ row.groupID
												+ "&quot;,&quot;"
												+ organizationName
												+ "&quot;";
										var con = "&quot;"
											+ row.groupID
											+ "&quot;,&quot;"
											+ organizationID
											+ "&quot;";

										return '<a style="cursor: pointer;" onclick="javascript:doOpenDetail('
												+ row.groupID
												+ ');"><img src="'
												+ path
												+ '/style/images/icon/icon_view.png" alt="查看" title="查看"/>&nbsp;<a style="cursor: pointer;" onclick="javascript:toUpdate('
												+ to
												+ ');"><img src="'
												+ path
												+ '/style/images/icon/icon_modify.png" alt="修改" title="修改" /></a>&nbsp;<a style="cursor: pointer;" onclick="javascript:doDel('
												+ row.groupID
												+ ');"><img src="'
												+ path
												+ '/style/images/icon/icon_delete.png" alt="删除" title="删除" /></a>&nbsp;<a style="cursor: pointer;" onclick="javascript:toConfigUser('
												+ con
												+ ');"><img src="'
												+path
												+'/style/images/icon/icon_userconfig.png" alt="配置用户"  title="配置用户" /></a>&nbsp;<a style="cursor: pointer;" onclick="javascript:toDivAllotRolePop('
												+ row.groupID
												+ ');"><img src="'
												+path
												+'/style/images/icon/icon_userallocate.png" alt="分配角色" title="分配角色" /></a>'
												
//												'<a style="cursor: pointer;" onclick="javascript:toDivAllotRolePop('
//												+ row.groupID
//												+ ');"><img src="'
//												+path
//												+'/style/images/icon/icon_userallocate.png" alt="分配角色" title="分配角色" /></a>
												;
									}
								} ] ]
					});
    $(window).resize(function() {
        $('#tblSysUserGroup').resizeDataGrid(0, 0, 0, 0);
       // alert('1111');
    });
}

/* 修改窗口 重置按钮 表单中保留原来数据 */
function resetFormValue(pcId) {
	var groupID = $("#ipt_groupID").val();
	var path = getRootName();
	var uri = path + "/permissionSysUserGroup/findSysUserGroup";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"groupID" : groupID,
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			resetForm('tblAddUserGroup');
			iterObj(data, "ipt");

			if (null != data.organizationBean
					&& 'null' != data.organizationBean) {
				$("#ipt_organizationID").attr('alt',
						data.organizationBean.organizationID);
				$("#ipt_organizationID").val(
						data.organizationBean.organizationName);
			} else {
				$("#ipt_organizationID").attr('alt', '');
				$("#ipt_organizationID").val('');
			}

		}
	}
	ajax_(ajax_param);
}

/*
 * 到查看页面
 */
function toView(groupID){
	initViewVal(groupID);
	$("#btnClose").bind("click", function() {
		$('#viewUserGroup').dialog('close');
	});
	$('#viewUserGroup').dialog('open');
	
}

function doOpenDetail(groupID){
	parent.$('#popWin').window({
    	title:'用户组已有的用户',
        width:800,
        height:490,
        top:200,
        minimizable:false,
        maximizable:false,
        collapsible:false,
        modal:true,
        href: getRootName() + '/permissionSysUserGroup/toShowDetail?groupID='+groupID
    });
}

/*
 * 查看用户组下已有的所有用户
 */
function initViewVal(groupID){
	var path = getRootName();
//	alert("用户组==="+groupID);
	$('#viewSysUser')
			.datagrid(
					{
						iconCls : 'icon-edit',// 图标
						width : 'auto',
						height : 'auto',
						nowrap : false,
						striped : true,
						border : true,
						collapsible : false,// 是否可折叠的
//						fit : true,// 自动大小
						fitColumns:true,
						url : path + '/permissionSysUser/listSysUserGroup',
						queryParams : {
							"isAutoLock" : -1,
							"userType" : -1,
							"belongGroupId" : groupID,
							"status" : -1
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
								 ] ]
					});
}