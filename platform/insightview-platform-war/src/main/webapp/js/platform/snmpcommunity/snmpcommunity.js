$(document).ready(function() {
	doInitTable();
	showType();
});

/*
 * 页面加载初始化表格 @author 王淑平
 */
function doInitTable() {
	var path = getRootName();
	$('#tblSNMPCommunity')
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
						url : path + '/platform/snmpCommunity/listSnmpCommunity',
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
									field : 'readCommunity',
									title : '读团体名',
									width : 350,
									align : 'center'
								},
								{
									field : 'writeCommunity',
									title : '写团体名',
									width : 350,
									align : 'center'
								},
								{
									field : 'alias',
									title : '别名',
									width : 280,
									align : 'center',
									sortable : true
								},
								{
									field : 'snmpVersion',
									title : '协议版本',
									width : 350,
									align : 'center',
									formatter : function(value, row, index) {
										if (value == 0) {
											return 'V1';
										} else if (value == 1) {
											return 'V2';
										} else if (value == 3) {
											return 'V3';
										}
									}
								},
								{
									field : 'snmpPort',
									title : '端口',
									width : 350,
									align : 'center'
								},
								{
									field : 'ids',
									title : '操作',
									width : 150,
									align : 'center',
									formatter : function(value, row, index) {
										var param = "&quot;" + row.id
												+ "&quot;,&quot;"
												+ row.deviceIP + "&quot;";
										return '<a style="cursor: pointer;" onclick="javascript:toShowInfo('
												+ row.id
												+ ');"><img src="'
												+ path
												+ '/style/images/icon/icon_view.png" title="查看" /></a> &nbsp;<a style="cursor: pointer;" onclick="javascript:doDel('
												+ row.id
												+ ');"><img src="'
												+ path
												+ '/style/images/icon/icon_delete.png" alt="" title="删除" /></a>';
									}
								} ] ]
					});
	// 浏览器窗口大小变化时，datagrid表格自适应窗口大小变化
	$(window).resize(function() {
		$('#tblSNMPCommunity').resizeDataGrid(0, 0, 0, 0);
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
	if ($("#ipt_snmpVersion").val() == "0"
			|| $("#ipt_snmpVersion").val() == "1") {
		$("#usmUser").hide();
		$("#authAlogrithm").hide();
		$("#encryptionAlogrithm").hide();
		$("#readCommunity").show();
	} else {
		$("#readCommunity").hide();
		$("#usmUser").show();
		$("#authAlogrithm").show();
		$("#encryptionAlogrithm").show();
	}
}

function showType() {
	$("#ipt_authAlogrithm")
			.on(
					'change',
					function() {
						document.getElementById('ipt_encryptionAlogrithm').disabled = (this.selectedIndex == 0);
					})
}

function reloadTable() {
	var alias = $("#txtFilterdeviceIP").val();
	$('#tblSNMPCommunity').datagrid('options').queryParams = {
		"alias" : alias,
	};
	reloadTableCommon_1('tblSNMPCommunity');
}

function reloadTableCommon_1(dataGridId) {
	$('#' + dataGridId).datagrid('load');
	$('#' + dataGridId).datagrid('uncheckAll');
}

function getMoIDBymoName(moName) {
	var path = getRootName();
	// var moID=$("#ipt_moID").val();
	var uri = path + "/platform/snmpCommunity/getMoIdBymoName";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"moName" : moName,
			// "moID": moID,
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			iterObj(data, "ipt");
			var moID = $("#ipt_moID").val();

			$("#txtFiltermoID").val(moID);
			// alert($("#txtFiltermoID").val()+"88888");
	}
	};
	ajax_(ajax_param);
}

/**
 * V1、V2版本是验证凭证是否存在
 * @param flag
 * @return
 */
function checkSnmpCommunity() {
	var readCommunity = $("#ipt_readCommunity").val();
	var snmpVersion = $("#ipt_snmpVersion").val();
	var snmpPort = $("#ipt_snmpPort").val();
	var path = getRootName();
	var uri = path + "/platform/snmpCommunity/checkAddSnmp";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"readCommunity" : readCommunity,
			"snmpVersion" : snmpVersion,
			"snmpPort" : snmpPort ,
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			if (false == data || "false" == data) {
				$.messager.alert("提示", "该凭证已存在！", "error");
			} else {
				addSnmp();
				return;
			}
		}
	};
	ajax_(ajax_param);
}
function checkupdateSnmpCommunity(flag, deviceIP) {
	var checkIP = $("#ipt_deviceIP").val();
	var path = getRootName();
	var uri = path + "/platform/snmpCommunity/checkSnmpCommunity";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"checkIP" : checkIP,
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

function doUpdate(deviceIP) {
	var snmpVersion = $("#ipt_snmpVersion").val();
//	console.log("snmpVersion=="+snmpVersion);
	var checkFormRS = true;
	var checkKeyRS = true;
	if(snmpVersion==0 || snmpVersion==1){
		var deviceIP=$("#ipt_deviceIP").val();
//		console.log("deviceIP=="+deviceIP+"  ,驗證結果==="+checkInfo('#ipAndVersion'));
		
		checkFormRS = checkInfo('#ipAndVersion')&& checkInfo('#readCommunity')&& checkInfo('#port');
	}else if(snmpVersion == 3){
		checkFormRS = checkInfo('#ipAndVersion')&& checkInfo('#usmUser')&& checkInfo('#port');
		var authAlogrithm= $("#ipt_authAlogrithm").val();
		if(authAlogrithm != -1){
			checkKeyRS = checkKey();
		}
	}
	
	if (checkFormRS == true) {
		if (checkKeyRS == true) {
			var flag = "update";
			checkupdateSnmpCommunity(flag, deviceIP);
		}
	}
}
function doCheckUpdate() {
	if (checkIP()) {
		var path = getRootName();
		var uri = path + "/platform/snmpCommunity/updateSnmpCommunity";
		var ajax_param = {
			url : uri,
			type : "post",
			datdType : "json",
			data : {
				"id" : $("#ipt_id").val(),
				"deviceIP" : $("#ipt_deviceIP").val(),
				"readCommunity" : $("#ipt_readCommunity").val(),
				"writeCommunity" : $("#ipt_writeCommunity").val(),
				"snmpPort" : $("#ipt_snmpPort").val(),
				"moID" : $("#ipt_deviceId").val(),
				"snmpVersion" : $("#ipt_snmpVersion").val(),
				"usmUser" : $("#ipt_usmUser").val(),
				"contexName" : $("#ipt_contexName").val(),
				"authAlogrithm" : $("#ipt_authAlogrithm").val(),
				"authKey" : $("#ipt_authKey").val(),
				"encryptionAlogrithm" : $("#ipt_encryptionAlogrithm").val(),
				"encryptionKey" : $("#ipt_encryptionKey").val(),
				"t" : Math.random()
			},
			error : function() {
				$.messager.alert("错误", "ajax_error", "error");
			},
			success : function(data) {
				if (true == data || "true" == data) {
					$.messager.alert("提示", "信息修改成功！", "info");
					$('#divAddSNMPCommunity').dialog('close');
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
	resetForm('tblSNMPCommunityInfo');
	var path = getRootName();
	var uri = path + "/platform/snmpCommunity/findSnmpCommunityByID";
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
			if (data.snmpVersion == "0" || data.snmpVersion == "1") {
				$("#usmUser").hide();
				$("#authAlogrithm").hide();
				$("#encryptionAlogrithm").hide();
				$("#readCommunity").show();
			} else {
				$("#readCommunity").hide();
				$("#usmUser").show();
				$("#authAlogrithm").show();
				$("#encryptionAlogrithm").show();
			}
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
		$('#divAddSNMPCommunity').dialog('close');
	});
	var txtN = document.getElementById("ipt_deviceIP"); 
	txtN.readOnly = true;
	$('#divAddSNMPCommunity').dialog('open');
}

function doDel(id) {
	var path = getRootName();
	$.messager.confirm("提示", "确定删除此条信息吗？", function(r) {
		if (r == true) {
			var uri = path + "/platform/snmpCommunity/delSnmpCommunity";
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
//						$.messager.alert("提示", "信息删除成功！", "info");
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
		$.messager.confirm("提示", "确定删除所选中项？", function(r) {
			if (r == true) {
				var uri = path
						+ "/platform/snmpCommunity/batchdelSnmpCommunity?ids="
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
//							$.messager.alert("提示", "信息批量删除成功！", "info");
							reloadTable();
						} else {
							$.messager.alert("提示", "信息批量删除失败！", "error");
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

function checkKey(){
	var authKey= $("#ipt_authKey").val();
	var encryptionAlogrithm = $("#ipt_encryptionAlogrithm").val();
	if(authKey!="" && authKey!=null && encryptionAlogrithm !="-1" && encryptionAlogrithm !=-1){
		return true;
	}else{
		if(authKey=="" || authKey==null){
			$.messager.alert('提示', '请输入认证KEY!', 'info');
		}
		if(encryptionAlogrithm=="-1" || encryptionAlogrithm==-1){
			$.messager.alert('提示', '请选择加密算法!', 'info');
		}
		return false;
	}
}

/*
 * 新增凭证 @author 王淑平
 */
function doAdd() {
	var snmpVersion = $("#ipt_snmpVersion").val();
//	console.log("snmpVersion=="+snmpVersion);
	var checkFormRS = true;
	var checkKeyRS = true;
	if(snmpVersion==0 || snmpVersion==1){
//		var deviceIP=$("#ipt_deviceIP").val();
//		console.log("deviceIP=="+deviceIP+"  ,驗證結果==="+checkInfo('#ipAndVersion'));
		
		checkFormRS = checkInfo('#ipAndVersion')&& checkInfo('#readCommunity')&& checkInfo('#port');
	}else if(snmpVersion == 3){
		checkFormRS = checkInfo('#ipAndVersion')&& checkInfo('#usmUser')&& checkInfo('#port');
		var authAlogrithm= $("#ipt_authAlogrithm").val();
		if(authAlogrithm != -1){
			checkKeyRS = checkKey();
		}
	}
	
	if (checkFormRS == true) {
		if (checkKeyRS == true) {
			if(snmpVersion==0 || snmpVersion==1){
				checkSnmpCommunity();
			}else{
				addSnmp();
			}
		}
	}
}

/**
 * 新增凭证
 * @return
 */
function addSnmp() {
//	if (checkIP()) {
		var path = getRootName();
		var uri = path + "/platform/snmpCommunity/addSnmpCommunity";
		var ajax_param = {
			url : uri,
			type : "post",
			datdType : "json",
			data : {
				"alias" : $("#ipt_alias").val(),
				"readCommunity" : $("#ipt_readCommunity").val(),
				"writeCommunity" : $("#ipt_writeCommunity").val(),
				"snmpPort" : $("#ipt_snmpPort").val(),
				"snmpVersion" : $("#ipt_snmpVersion").val(),
				"usmUser" : $("#ipt_usmUser").val(),
				"contexName" : $("#ipt_contexName").val(),
				"authAlogrithm" : $("#ipt_authAlogrithm").val(),
				"authKey" : $("#ipt_authKey").val(),
				"encryptionAlogrithm" : $("#ipt_encryptionAlogrithm").val(),
				"encryptionKey" : $("#ipt_encryptionKey").val(),
				"t" : Math.random()
			},
			error : function() {
				$.messager.alert("错误", "ajax_error", "error");
			},
			success : function(data) {
				if (true == data || "true" == data) {
					$.messager.alert("提示", "信息添加成功！", "info");
					$('#divAddSNMPCommunity').dialog('close');
					reloadTable();
				} else {
					$.messager.alert("提示", "信息添加失败！", "error");
				}

			}
		};
		ajax_(ajax_param);
//	}
}
/*
 * 打开新增div @author 王淑平
 */
function toAdd() {
	resetForm('tblSNMPCommunityInfo');
	$("#usmUser").hide();
	$("#authAlogrithm").hide();
	$("#encryptionAlogrithm").hide();
	$("#readCommunity").show();
	
	$("#ipt_moID").val("");
	$("#btnSave").unbind("click");
	$("#btnSave").bind("click", function() {
		doAdd();
	});
	$("#btnUpdate").unbind();
	$("#btnUpdate").bind("click", function() {
		// resetForm('tblSNMPCommunityInfo');
			$('#divAddSNMPCommunity').dialog('close');
		});
//	var txtN = document.getElementById("ipt_deviceIP"); 
//	txtN.readOnly = false;
	$('#divAddSNMPCommunity').dialog('open');
	var snmpVersion = $("#ipt_snmpVersion").val();
	var readCommunity = $("#ipt_readCommunity").val();
	var writeCommunity = $("#ipt_writeCommunity").val();
	var snmpPort = $("#ipt_snmpPort").val();
	if (snmpPort == 0) {
		$("#ipt_snmpPort").val('161');
	}
	if (readCommunity == '') {
		$("#ipt_readCommunity").val('public');
	}
	if (writeCommunity == '') {
		$("#ipt_writeCommunity").val('public');
	}
	snmpPort = $("#ipt_snmpPort").val();
	readCommunity = $("#ipt_readCommunity").val();
	writeCommunity = $("#ipt_writeCommunity").val();
	$("#ipt_readCommunity").val(readCommunity);
	$("#ipt_writeCommunity").val(writeCommunity);
	$("#ipt_snmpPort").val(snmpPort);
	
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
		$('#divShowSnmpInfo').dialog('close');
	});
	$('#divShowSnmpInfo').dialog('open');
}

// 详情信息
function setRead(id) {
	var path = getRootName();
	var uri = path + "/platform/snmpCommunity/findSnmpCommunityByID";
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
			if (data.snmpVersion == "0") {
				data.snmpVersion = "V1";
				$("#usmUsers").hide();
				$("#authAlogrithms").hide();
				$("#encryptionAlogrithms").hide();
				$("#readCommunitys").show();
			} else if (data.snmpVersion == "1") {
				data.snmpVersion = "V2";
				$("#usmUsers").hide();
				$("#authAlogrithms").hide();
				$("#encryptionAlogrithms").hide();
				$("#readCommunitys").show();

			} else {
				data.snmpVersion = "V3";
				$("#readCommunitys").hide();
				$("#usmUsers").show();
				$("#authAlogrithms").show();
				$("#encryptionAlogrithms").show();
			}

			iterObj(data, "lbl");
		}
	};
	ajax_(ajax_param);
}
function checkIP() {
	var deviceIP = $("#ipt_deviceIP").val();
	if (!(/^(\*|(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5]|\*)\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5]|\*)\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5]|\*))$/
			.test(deviceIP))) {
		$.messager.alert("提示", "ip地址错误，请填写正确的ip地址！", "info", function(e) {
			$("#ipt_deviceIP").focus();
		});
		return false;
	}
	var port = $("#ipt_snmpPort").val();
	if (!(/^[0-9]*[1-9][0-9]*$/.test(port))) {
		$.messager.alert("提示", "SNMP端口只能输入正整数！", "info", function(e) {
			$("#ipt_snmpPort").focus();
		});
		return false;
	}
	return true;
}