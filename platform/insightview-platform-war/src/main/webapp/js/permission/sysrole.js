$(document).ready(function() {
	doInitTable();
});
var _roleId = "";


/**
 * 验证表单信息
 * 
 * @author 武林
 */
function checkForm() {
	var checkControlAttr = new Array('ipt_roleName');
	var checkMessagerAttr = new Array('请输入角色名称！');
	return checkFormCommon(checkControlAttr, checkMessagerAttr);
}

/**
 * 配置菜单
 * 
 */
var _openMenuCount = 0;
var dataTreeMenu = new dTree("dataTreeMenu", getRootPatch()+ "/plugin/dTree/img/");
dataTreeMenu.config.check=true;
function toConfigMenu(roleId) {
	_roleId = roleId;
		var path = getRootPatch();
		var uri = path + "/permissionSysMenuModule/getSysMenuTreeVal";
		var ajax_param = {
			url : uri,
			type : "post",
			datdType : "json",
			data : {
				"roleId" : roleId,
				"t" : Math.random()
			},
			error : function() {
				$.messager.alert("错误", "ajax_error", "error");
			},
			success : function(data) {
				dataTreeMenu.add(0, -1, "选择上级菜单", "");

				// 得到树的json数据源
				var datas = eval('(' + data.menuLstJson + ')');
				var menuRoleIdArr = data.menuRoleId;
				_treeData = datas;
				// 遍历数据
				var gtmdlToolList = datas;
				for (var i = 0; i < gtmdlToolList.length; i++) {
					var _id = gtmdlToolList[i].menuId;
					var _nameTemp = gtmdlToolList[i].menuName;
					var _parent = gtmdlToolList[i].parentMenuID;
					var _menuLevel = gtmdlToolList[i].menuLevel;

					dataTreeMenu.add(_id, _parent, _nameTemp,
							"javascript:hiddenDTreeSetVal('selFilterParentId','"
									+ _id + "','" + _nameTemp + "');");
				}
				$('#dataMenuTreeDiv').empty();
				$('#dataMenuTreeDiv').append(dataTreeMenu + "");
				dataTreeMenu.setDefaultCheck(menuRoleIdArr);
				dataTreeMenu.openAll();
			}
		}
		ajax_(ajax_param);
	$('#divMenuConfig').dialog('open');
}

function doConfigMenu() {
	var selids=dataTreeMenu.getCheckedNodes();  
	var selMenuId="";
	for ( var i = 0, l = selids.length; i < l; i++) {
		if(selMenuId !="" ){
			if(selMenuId.indexOf(selids[i]+",") <0){
				selMenuId +=selids[i]+","; 
			}
		}else{
			selMenuId +=selids[i]+",";
		}
	}
	var path = getRootName();
	var uri = path + "/permissionSysRole/addSysRoleMenu";
	var ajax_param = {
		url : uri,
		type : "post",
		async:false,
		datdType : "json",
		data : {
			"roleID" : _roleId,
			"menuIdAttr" : selMenuId,
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			if (true == data || "true" == data) {
//				$.messager.alert("提示", "配置菜单修改成功！", "info");
				reloadTable();
				$('#divMenuConfig').dialog('close');
			} else {
				$.messager.alert("提示", "配置菜单修改失败！！", "error");
				reloadTable();
			}
		}
	}
	ajax_(ajax_param);
}

function doUpdate(){
	var doName = "update";
	var result = checkInfo('#divAddSysRole');
	if(result == true){
		checkRoleName(doName);
	}
}
function update() {
	var path = getRootName();
	var uri = path + "/permissionSysRole/updateSysRole";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"roleId" : $("#ipt_roleId").val(),
			"organizationId" : $("#ipt_organizationId").val(),
			"roleName" : $("#ipt_roleName").val(),
			"descr" : $("#ipt_descr").val(),
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			if (true == data || "true" == data) {
//				$.messager.alert("提示", "用户组修改成功！", "info");
				$('#divAddSysRole').dialog('close');
				reloadTable();
			} else {
				$.messager.alert("提示", "用户组修改失败！", "error");
				reloadTable();
			}
		}
	}
	ajax_(ajax_param);
}

function initUpdateVal(roleId) {
	resetForm('tblRoleInfo');
	var path = getRootName();
	var uri = path + "/permissionSysRole/findSysRole";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"roleId" : roleId,
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			iterObj(data, "ipt");

		}
	}
	ajax_(ajax_param);
}

function toUpdate(roleId) {
	initUpdateVal(roleId);

	$("#btnSave").unbind("click");
	$("#btnSave").bind("click", function() {
		doUpdate();
	});
	$("#btnUpdate").unbind();
	$("#btnUpdate").bind("click", function() {
		$('#divAddSysRole').dialog('close');
	});
	$('#divAddSysRole').dialog('open');
}

function reloadTable() {
	var roleName = $("#txtFilterRoleName").val();
	$('#tblSysRole').datagrid('options').queryParams = {
		"roleName" : roleName
	};
	reloadTableCommon_1('tblSysRole');
}

function reloadTableCommon_1(dataGridId) {
	$('#' + dataGridId).datagrid('load');
	$('#' + dataGridId).datagrid('uncheckAll');
}

function doDel(roleId) {
	$.messager.confirm("提示","确定删除此角色?",function(r){
		if (r == true) {
			if(roleId >1){
				var path = getRootName();
				var uri = path + "/permissionSysRole/delSysRole";
				var ajax_param = {
						url : uri,
						type : "post",
						datdType : "json",
						data : {
					"roleId" : roleId,
					"t" : Math.random()
				},
				error : function() {
					$.messager.alert("错误", "ajax_error", "error");
				},
				success : function(data) {
					if (true == data || "true" == data) {
//						$.messager.alert("提示", "角色删除成功！", "info");
						reloadTable();
					} else {
						$.messager.alert("提示", "该角色正在使用中，角色删除失败！", "error");
						reloadTable();
					}
				}
				};
				ajax_(ajax_param);
			}else{
				$.messager.alert("错误", "该角色为系统自定义用户，不能删除！", "error");
				reloadTable();
			}
		}
	});
}

/**
 * 批量删除
 * 
 */
function doBatchDel(){
	var path=getRootName();
	var checkedItems = $('[name=roleId]:checked');
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
				var uri = path+"/permissionSysRole/delSysRoles?roleIds=" + ids;
				var ajax_param = {
					url : uri,
					type : "post",
					datdType : "json",
					data : {
						"t" : Math.random()
					},
					success : function(data) {
						if (false == data || "false" == data) {
							$.messager.alert("提示", "数据库操作异常，角色删除失败！", "error");
							reloadTable();
						}else{
							if(data.isSystem == false) {
								$.messager.alert("提示", "角色"+data.sysRoleName+"为系统自定义用户，不能删除！", "info");
								reloadTable();
							}
							if(data.isUse == false) {
								$.messager.alert("提示", "角色"+data.roleName+"正在使用，不能删除！", "info");
								reloadTable();
							}
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
function doAdd(){
	var doName = "add";
	var result = checkInfo('#divAddSysRole');
	if(result == true){
		checkRoleName(doName);
	}
}


/*
 * 新增菜单 @author 武林
 */
function add() {
		var path = getRootName();
		var uri = path + "/permissionSysRole/addSysRole";
		var ajax_param = {
			url : uri,
			type : "post",
			datdType : "json",
			data : {
				"organizationId" : $("#ipt_organizationId").val(),
				"roleName" : $("#ipt_roleName").val(),
				"descr" : $("#ipt_descr").val(),
				"t" : Math.random()
			},
			error : function() {
				$.messager.alert("错误", "ajax_error", "error");
			},
			success : function(data) {
				if (true == data || "true" == data) {
//					$.messager.alert("提示", "角色新增成功！", "info");
					$('#divAddSysRole').dialog('close');
					reloadTable();
				} else {
					$.messager.alert("提示", "角色新增失败！", "error");
					reloadTable();
				}

			}
		};
		ajax_(ajax_param);
}

/*
 * 打开新增div @author 武林
 */
function toAdd() {
	resetForm('tblRoleInfo');
	$("#btnSave").unbind("click");
	$("#btnSave").bind("click", function() {
		doAdd();
	});
	$("#btnUpdate").unbind();
	$("#btnUpdate").bind("click", function() {
		window.location.href = window.location.href;
		$('#divAddSysRole').dialog('close');
	});

	$('#divAddSysRole').dialog('open');
}

/*
 * 页面加载初始化表格 @author 武林
 */
function doInitTable() {
	var path = getRootName();
	$('#tblSysRole')
			.datagrid(
					{
						iconCls : 'icon-edit',// 图标
						width : 'auto',
						height : 'auto',
						nowrap : false,
						striped : true,
						border : true,
						collapsible : false,// 是否可折叠的
						fit : true,// 自动大小
						fitColumns:true,
						url : path + '/permissionSysRole/listSysRole',
						// sortName: 'code',
						// sortOrder: 'desc',
						remoteSort : false,
						idField : 'roleId',
						checkOnSelect : false,
						singleSelect : false,// 是否单选
						selectOnCheck : true,
						pagination : true,// 分页控件
						rownumbers : true,// 行号
						toolbar : [ {
							'text' : '新增',
							'iconCls' : 'icon-add',
							handler : function() {
								toAdd();
							}
						} ,
						{
							'text' : '删除',
							'iconCls' : 'icon-cancel',
							handler : function() {
								doBatchDel();
							}
						}],
						columns : [ [
					             {
					            	 field : 'roleId',
					            	 checkbox : true
					            	 
					             },	
								{
									field : 'roleName',
									title : '角色名称',
									width : 180
								},
								{
									field : 'descr',
									title : '备注',
									width : 180,
								},
								{
									field : 'roleIds',
									title : '操作',
									width : 150,
									align : 'center',
									formatter : function(value, row, index) {
										return 	    '<a style="cursor: pointer;" onclick="javascript:toView('
													+ row.roleId
													+ ');"><img src="'
													+ path
													+ '/style/images/icon/icon_view.png" alt="查看" title="查看"/>&nbsp;<a style="cursor: pointer;" onclick="javascript:toUpdate('
													+ row.roleId
													+ ');"><img src="'
													+ path
													+ '/style/images/icon/icon_modify.png" alt="修改" title="修改" /></a>&nbsp;<a style="cursor: pointer;" onclick="javascript:doDel('
													+ row.roleId
													+ ');"><img src="'
													+ path
													+ '/style/images/icon/icon_delete.png" alt="删除" title="删除" /></a>&nbsp;<a style="cursor: pointer;" onclick="javascript:toConfigMenu('
													+ row.roleId
													+ ');"><img src="'
													+path
													+'/style/images/icon/icon_menuconfig.png" alt="菜单配置"  title="菜单配置" /></a>';
									}
								} ] ]
					});
    $(window).resize(function() {
        $('#tblSysRole').resizeDataGrid(0, 0, 0, 0);
    });
}
/*
 * 到查看页面
 */
function toView(roleId){
	initViewVal(roleId);
	$("#btnClose").bind("click", function() {
		$('#viewSysRole').dialog('close');
	});
	$('#viewSysRole').dialog('open');
}

/*
 * 初始化查看页面
 */

function initViewVal(roleId){
	var path = getRootName();
	var uri = path + "/permissionSysRole/findSysRole";
	var ajax_param = {
			url : uri,
			type : "post",
			datdType : "json",
			data : {
				"roleId" : roleId,
				"t" : Math.random()
			},
			error : function() {
				$.messager.alert("错误", "ajax_error", "error");
			},
			success : function(data) {
				iterObj(data, "lbl");
			}
		}
		ajax_(ajax_param);
}

/* 角色名唯一性验证 */
function checkRoleName(doName) {
	var roleName = $("#ipt_roleName").val();
	if (doName == "update"){
		var roleId = $("#ipt_roleId").val();
	}
	var path = getRootName();
	var uri = path + "/permissionSysRole/checkRoleName";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"roleId" : roleId,
			"roleName" : roleName,
			"doName" : doName,
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			if (true == data || "true" == data) {
				$.messager.alert("提示", "该角色名已存在！", "error");
			} else if (doName == "add"){
				add();
			}else{
				update();
			}
		}
	};
	ajax_(ajax_param);
}

/* 重置表单原来的数据信息 */
function resetFormValue(ptIn) {
	var roleId = $("#ipt_roleId").val();
	toUpdate(roleId);
}

function closeConfigMenu(){
	$('#divMenuConfig').dialog('close');
}

