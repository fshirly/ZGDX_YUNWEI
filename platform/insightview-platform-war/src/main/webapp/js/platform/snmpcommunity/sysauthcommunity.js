$(document).ready(function() {
	doInitTable();
});

/**
 * 验证表单信息
 * 
 * @author 王淑平
 */
function checkForm() {
	var checkControlAttr = new Array('ipt_deviceIP','ipt_userName','ipt_password', 'ipt_loginPattern');
	var checkMessagerAttr = new Array('请先选择设备！','请输入用户名！','请输入密码！', '请输入登录提示符！');
	return checkFormCommon(checkControlAttr, checkMessagerAttr);
}
function checkIP() {
	var deviceIP = $("#ipt_deviceIP").val();
	if (!(/^(\*|(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5]|\*)\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5]|\*)\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5]|\*))$/
			.test(deviceIP))) {
		$.messager.alert("提示", "设备IP格式错误，请填写正确的设备IP！", "info", function(e) {
			$("#ipt_deviceIP").focus();
		});
		return false;
	}
	var port = $("#ipt_port").val();
	if (!(/^[0-9]*[1-9][0-9]*$/.test(port))) {
		$.messager.alert("提示", "端口只能输入正整数！", "info", function(e) {
			$("#ipt_port").focus();
		});
		return false;
	}
	return true;
}

/*
 * 页面加载初始化表格 @author 武林
 */
function doInitTable() {
	var path = getRootName();
	$('#tblAuthCommunity')
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
						fitColumns : true,
						url : path + '/platform/sysAuthCommunity/listSysAuthCommunity',
						remoteSort : false,
						idField : 'fldId',
						singleSelect : false,// 是否单选
						checkOnSelect : false,
						selectOnCheck : true,
						pagination : true,// 分页控件
						rownumbers : true,// 行号
						toolbar : [ {
							'text' : '新增',
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
									field : 'id',
									checkbox : true
								},
								{
									field : 'deviceIP',
									title : '设备IP',
									width : 180,
									sortable : true
								},
								{
									field : 'moName',
									title : '设备名称',
									width : 180,
									align : 'center'
								},

								{
									field : 'authType',
									title : '登录方式',
									width : 160,
									align : 'center',
									formatter : function(value, row, index) {
										if (value == 1) {
											return 'Telnet';
										} else if (value == 2) {
											return 'SSH';
										}
									}
								},

								{
									field : 'port',
									title : '端口',
									width : 160,
									align : 'center'
								},
								{
									field : 'userName',
									title : '用户名',
									width : 160,
									align : 'center'
								},
								{
									field : 'loginPattern',
									title : '登录提示符',
									width : 160,
									align : 'center'
								},
								{
									field : 'enableUserName',
									title : '特权用户名',
									width : 160,
									align : 'center'
								},
								{
									field : 'enLoginPattern',
									title : '特权登录提示符',
									width : 160,
									align : 'center'
								},
								{
									field : 'ids',
									title : '操作',
									width : 160,
									align : 'center',
									formatter : function(value, row, index) {
										var param = "&quot;" + row.id
												+ "&quot;,&quot;"
												+ row.deviceIP + "&quot;";
										return '<a style="cursor: pointer;" onclick="javascript:toShowInfo('
												+ row.id
												+ ');"><img src="'
												+ path
												+ '/style/images/icon/icon_view.png" title="查看" /></a> &nbsp;<a style="cursor: pointer;" onclick="javascript:toUpdate('
												+ param
												+ ');"><img src="'
												+ path
												+ '/style/images/icon/icon_modify.png" alt="" title="修改"/></a> &nbsp;<a style="cursor: pointer;" onclick="javascript:doDel('
												+ row.id
												+ ');"><img src="'
												+ path
												+ '/style/images/icon/icon_delete.png" alt="" title="删除" /></a>';
									}
								} ] ]
					});
	// 浏览器窗口大小变化时，datagrid表格自适应窗口大小变化
	$(window).resize(function() {
		$('#tblAuthCommunity').resizeDataGrid(0, 0, 0, 0);
	});
}
/**
 * 加载设备信息列表页面
 * 
 * @return
 */
function loadDeviceInfo() {
	var path = getRootName();
	var uri = path + "/monitor/discover/toDiscoverDeviceList?flag=choose";
	window
			.open(
					uri,
					"",
					"height=500px,width=800px,left=400,top=200,resizable=no,scrollings=no,status=no,toolbar=no,menubar=no,location=no");

}

/**
 * 查找设备及snmp信息
 */
function findDeviceInfo() {
	var deviceId = $("#ipt_deviceId").val();
	var path = getRootName();
	var uri = path + "/platform/sysAuthCommunity/findDeviceInfo";
	var ajax_param = {
		url : uri,
		type : "post",
		dateType : "json",
		data : {
			"moID" : deviceId,
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			iterObj(data, "ipt");
		}
	};
	ajax_(ajax_param);
}

function isOrnoCheck() {
	if ($("#ipt_authType").val() == "1") {
		$("#ipt_port").val('23');
	} else {
		$("#ipt_port").val('22');
	}
}

function reloadTable() {
	var deviceIP = $("#txtFilterdeviceIP").val();
	var userName = $("#txtFilteruserName").val();
	// getMoIDBymoName(moName);
	var moID = $("#txtFiltermoID").val();
	$('#tblAuthCommunity').datagrid('options').queryParams = {
		"deviceIP" : deviceIP,
		"userName" : userName,
		"moID" : moID
	};
	reloadTableCommon_1('tblAuthCommunity');
}

function reloadTableCommon_1(dataGridId) {
	$('#' + dataGridId).datagrid('load');
	$('#' + dataGridId).datagrid('uncheckAll');
}

function getMoIDBymoName(moName) {
	var path = getRootName();
	var uri = path + "/platform/sysAuthCommunity/getMoIdBymoName";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"moName" : moName,
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			alert(data);
			/*
			 * if (true == data || "true" == data) { $.messager.alert("提示",
			 * "获取名称", "error"); } else { return; }
			 */
		}
	};
	ajax_(ajax_param);
}
function checkSyaAuthCommunity(flag, deviceIP) {
	if (checkIP()) {
		var updateIP = $("#ipt_deviceIP").val();
		var path = getRootName();
		var uri = path + "/platform/sysAuthCommunity/checkDeviceIP";
		var ajax_param = {
				url : uri,
				type : "post",
				datdType : "json",
				data : {
			"checkIP" : updateIP,
			"deviceIP" : deviceIP,
			"flag" : flag,
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			if (false == data || "false" == data) {
				$.messager.alert("提示", "该设备IP已存在！", "error");
				
			} else {
				
				doCheckUpdate();
				
			}
		}
		};
		ajax_(ajax_param);
	}
}
function checkAddAuthCommunity(flag) {
	if (checkIP()) {
		var deviceIP = $("#ipt_deviceIP").val();
		var path = getRootName();
		var uri = path + "/platform/sysAuthCommunity/checkAddSyaAuthCommunity";
		var ajax_param = {
				url : uri,
				type : "post",
				datdType : "json",
				data : {
			"deviceIP" : deviceIP,
			"flag" : flag,
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			if (true == data || "true" == data) {
				$.messager.alert("提示", "该设备IP已存在！", "error");
				
			} else {
				doCheckAdd();
				return;
			}
		}
		};
		ajax_(ajax_param);
	}
}
function doUpdate(deviceIP) {
	var result = checkInfo('#divAddAuthCommunity');
	if (result == true) {
		var flag = "update";
		checkSyaAuthCommunity(flag, deviceIP);
	}
}
function doCheckUpdate() {
	if (checkIP()) {
		var path = getRootName();
		var uri = path + "/platform/sysAuthCommunity/updateAuthCommunity";
		var ajax_param = {
			url : uri,
			type : "post",
			datdType : "json",
			data : {
				"id" : $("#ipt_id").val(),
				"moID" : $("#ipt_deviceId").val(),
				"deviceIP" : $("#ipt_deviceIP").val(),
				"authType" : $("#ipt_authType").val(),
				"loginPattern" : $("#ipt_loginPattern").val(),
				"port" : $("#ipt_port").val(),
				"userName" : $("#ipt_userName").val(),
				"password" : $("#ipt_password").val(),
				"enLoginPattern" : $("#ipt_enLoginPattern").val(),
				"enableUserName" : $("#ipt_enableUserName").val(),
				"enablePassword" : $("#ipt_enablePassword").val(),
				"t" : Math.random()
			},
			error : function() {
				$.messager.alert("错误", "ajax_error", "error");
			},
			success : function(data) {
				if (true == data || "true" == data) {
					$.messager.alert("提示", "信息修改成功！", "info");
					$('#divAddAuthCommunity').dialog('close');
					reloadTable();
				} else {
					$.messager.alert("提示", "信息修改失败！", "error");
					reloadTable();
				}
			}
		}
		ajax_(ajax_param);
	}
}

function initUpdateVal(id) {
	resetForm('tblAuthCommunityInfo');
	var path = getRootName();
	var uri = path + "/platform/sysAuthCommunity/findSysAuthCommunityByID";
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
			iterObj(data, "ipt");

		}
	}
	ajax_(ajax_param);
}

function toUpdate(id, deviceIP) {
	initUpdateVal(id);

	$("#btnSave").unbind("click");
	$("#btnSave").bind("click", function() {
		doUpdate(deviceIP);
	});
	$("#btnUpdate").unbind();
	$("#btnUpdate").bind("click", function() {
		// initUpdateVal(id);
			$('#divAddAuthCommunity').dialog('close');
		});
	var txtN = document.getElementById("ipt_deviceIP"); 
	txtN.readOnly = true;
	$('#divAddAuthCommunity').dialog('open');
}

function doDel(id) {
	var path = getRootName();
	$.messager.confirm("提示", "确定删除此条信息吗？", function(r) {
		if (r == true) {
			var path = getRootName();
			var uri = path + "/platform/sysAuthCommunity/delAuthCommunity";
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
						$.messager.alert("提示", "信息删除成功！", "info");
						reloadTable();
					} else {
						$.messager.alert("提示", "信息删除失败！", "error");
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
function doBatchDel() {
	var path = getRootName();
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
		$.messager
				.confirm(
						"提示",
						"确定删除所选中项？",
						function(r) {
							if (r == true) {
								var uri = path
										+ "/platform/sysAuthCommunity/batchdelAuthCommunity?ids="
										+ ids;
								var ajax_param = {
									url : uri,
									type : "post",
									datdType : "json",
									data : {
										"t" : Math.random()
									},
									success : function(data) {
										if (true == data || "true" == data) {
											$.messager.alert("提示", "信息批量删除成功！","info");
											reloadTable();
										} else {
											$.messager.alert("提示", "信息批量删除失败！","error");
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
function doCheckAdd() {
	if (checkIP()) {
		var path = getRootName();
		var uri = path + "/platform/sysAuthCommunity/addAuthCommunity";
		var ajax_param = {
			url : uri,
			type : "post",
			datdType : "json",
			data : {
				"deviceIP" : $("#ipt_deviceIP").val(),
				"authType" : $("#ipt_authType").val(),
				"loginPattern" : $("#ipt_loginPattern").val(),
				"moID" : $("#ipt_deviceId").val(),
				"port" : $("#ipt_port").val(),
				"userName" : $("#ipt_userName").val(),
				"password" : $("#ipt_password").val(),
				"enLoginPattern" : $("#ipt_enLoginPattern").val(),
				"enableUserName" : $("#ipt_enableUserName").val(),
				"enablePassword" : $("#ipt_enablePassword").val(),
				"t" : Math.random()
			},
			error : function() {
				$.messager.alert("错误", "ajax_error", "error");
			},
			success : function(data) {
				if (true == data || "true" == data) {
					$.messager.alert("提示", "信息添加成功！", "info");
					$('#divAddAuthCommunity').dialog('close');
					reloadTable();
				} else {
					$.messager.alert("提示", "信息加失败！", "error");
				}

			}
		};
		ajax_(ajax_param);
	}
}
/*
 * 新增凭证 @author 王淑平
 */
function doAdd() {
	var result = checkInfo('#divAddAuthCommunity');
	if (result == true) {
		var flag = "add";
		checkAddAuthCommunity(flag);
	}
}

/*
 * 打开新增div @author 王淑平
 */
function toAdd() {
	resetForm('tblAuthCommunityInfo');
	$("#ipt_moID").val("");
	$("#btnSave").unbind("click");
	$("#btnSave").bind("click", function() {
		doAdd();
	});
	$("#btnUpdate").unbind();
	$("#btnUpdate").bind("click", function() {
		// resetForm('tblAuthCommunityInfo');
			$('#divAddAuthCommunity').dialog('close');
		});

	var txtN = document.getElementById("ipt_deviceIP"); 
	txtN.readOnly = false;
	$('#divAddAuthCommunity').dialog('open');
	var port = $("#ipt_port").val();
	if (port == 0) {
		$("#ipt_port").val('23');
	}
	var loginPattern = $("#ipt_loginPattern").val();
	if (loginPattern == '') {
		$("#ipt_loginPattern").val(':');
	}
	var enloginPattern = $("#ipt_enLoginPattern").val();
	if (enloginPattern == '') {
		$("#ipt_enLoginPattern").val('$');
	}
}

/* 重置表单原来的数据信息 */
function resetFormValue(ptIn) {
	var roleId = $("#ipt_id").val();
	toUpdate(roleId);
}

// 查看详情
function toShowInfo(id) {
	$('.input').val("");
	setRead(id);
	$("#btnClose2").unbind();
	$("#btnClose2").bind("click", function() {
		$('.input').val("");
		$('#divShowAuthInfo').dialog('close');
	});

	$('#divShowAuthInfo').dialog('open');
}

// 详情信息
function setRead(id) {
	var path = getRootName();
	var uri = path + "/platform/sysAuthCommunity/findSysAuthCommunityByID";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		async : false,

		data : {
			"id" : id,
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			if (data.authType == "1") {
				data.authType = "Telnet";
			} else {
				data.authType = "SSH";
			}

			iterObj(data, "lbl");
		}
	};
	ajax_(ajax_param);
}
