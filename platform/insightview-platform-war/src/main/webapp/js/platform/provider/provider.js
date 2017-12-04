$(document).ready(function(){
	doInitTable();
});

/**
 * 刷新表格数据
 */
function reloadTable(){
	var pName = $("#txtFilterProviderName").val();
	$('#tblProvider').datagrid('options').queryParams = {
		"providerName" : pName
	};
	reloadTableCommon_1('tblProvider');
}

function reloadTableCommon_1(dataGridId) {
	$('#' + dataGridId).datagrid('load');
	$('#' + dataGridId).datagrid('uncheckAll');
}

/**
 * 加载页面并初始化表格
 * @return
 */
function doInitTable(){
	var path = getRootName();
	$('#tblProvider')
		.datagrid(
				{
					width : 'auto',
					height : 'auto',
					nowrap : false,
					striped : true,
					border : true,
					collapsible : false,// 是否可折叠的
					fit : true,// 自动大小
					fitColumns : true,
					url : path + '/platform/provider/listProvider',
					remoteSort : false,
					onSortColumn : function(sort, order){
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
							toAdd();
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
								field : 'providerId',
								checkbox : true
							},{
								field : 'providerName',
								title : '供商名称',
								width : 110,
								align : 'center'
							},{
								field : 'contacts',
								title : '联系人',
								width : 90,
								align : 'center'
							},{
								field : 'contactsTelCode',
								title : '联系电话',
								width : 90,
								align : 'center'
							},{
								field : 'fax',
								title : '传真',
								width : 90,
								align : 'center'
							},{
								field : 'techSupportTel',
								title : '服务电话',
								width : 90,
								align : 'center'
							},{
								field : 'email',
								title : 'E-mail',
								width : 110,
								align : 'center'
							},{
								field : 'providerIds',
								title : '操作',
								width : 70,
								align : 'center',
								formatter : function(value, row, index) {
									return '<a style="cursor: pointer;" title="查看" onclick="javascript:toShowInfo('
											+ row.providerId
											+ ');"><img src="'
											+ path
											+ '/style/images/icon/icon_view.png" alt="查看" /></a> &nbsp;'
											+ ' <a style="cursor: pointer;" title="修改" onclick="javascript:toUpdate('
											+ row.providerId
											+ ');"><img src="'
											+ path
											+ '/style/images/icon/icon_modify.png" alt="修改" /></a> &nbsp;'
											+ ' <a style="cursor: pointer;" title="删除" onclick="javascript:doDel('
											+ row.providerId
											+ ');"><img src="'
											+ path
											+ '/style/images/icon/icon_delete.png" alt="删除" /></a>';
								}
							}]]
				});
	$(window).resize(function() {
		$('#tblProvider').resizeDataGrid(0, 0, 0, 0);
	});
}

function checkForm() {
	return checkInfo('#tblAddProvider');
}

/**
 * 删除信息
 */
function doDel(providerId) {
	var path = getRootName();
	$.messager.confirm("提示", "确定删除此信息?", function(r) {
		if (r == true) {
			var uri = path + "/platform/provider/delProvider";
			var ajax_param = {
				url : uri,
				type : "post",
				datdType : "json",
				data : {
					"providerId" : providerId,
					"t" : Math.random()
				},
				error : function() {
					$.messager.alert("错误", "ajax_error", "error");
				},
				success : function(data) {
					if (data.flag == false ) {
						$.messager.alert("错误", "供应商已被使用，删除失败，请检查！", "error");
						reloadTable();
					} else {
						$.messager.alert("提示", "供应商删除成功！", "info");
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
 */
function doBatchDel(){
	var path = getRootName();
	var checkedItems = $('[name=providerId]:checked');
	var ids = null;
	for ( var i = 0; i < checkedItems.length; i++){
		var id = checkedItems[i].value;
		if (null == ids){
			ids = id;
		}else{
			ids += ',' + id;
		}
	}
	if (null != ids){
		$.messager.confirm("提示", "确定删除所选中项?", function(r){
			if (r == true){
				var uri = path + "/platform/provider/delBatchProvider?providerIds="+ids;
				var ajax_param = {
					url : uri,
					type : "post",
					datdType : "json",
					data : {
						"providerId" : ids
					},
					success : function(data){
						if (data.flag == false ) {
							$.messager.alert("错误", "供应商已使用，删除失败！", "error");
							reloadTable();
						}else{
							$.messager.alert("提示", "信息删除成功！", "info");
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
 * 打开新增div
 */
function toAdd() {
	resetForm('tblAddProvider');
	$("#btnSave").unbind("click");
	$("#btnSave").bind("click", function() {
		doAdd();
	});
	$("#btnUpdate").unbind();
	$("#btnUpdate").bind("click", function() {
		$('#divAddProvider').dialog('close');
	});
	$('#divAddProvider').dialog('open');
}

/**
 * 新增供应商
 */
function doAdd() {
	if (checkForm()) {
		var path = getRootName();
		var uri = path + "/platform/provider/addProvider";
		var ajax_param = {
			url : uri,
			type : "post",
			datdType : "json",
			data : {
				"providerName" : $("#ipt_providerName").val(),
				"contacts" : $("#ipt_contacts").val(),
				"contactsTelCode" : $("#ipt_contactsTelCode").val(),
				"fax" : $("#ipt_fax").val(),
				"techSupportTel" : $("#ipt_techSupportTel").val(),
				"email" : $("#ipt_email").val(),
				"address" : $("#ipt_address").val(),
				"uRL" : $("#ipt_uRL").val(),
				"logoFileName" : $("#ipt_logoFileName").val(),
				"descr" : $("#ipt_descr").val(),
				"t" : Math.random()
			},
			error : function() {
				$.messager.alert("错误", "ajax_error", "error");
			},
			success : function(data) {
				if (true == data || "true" == data) {
//					$.messager.alert("提示", "供应商新增成功！", "info");
					$('#divAddProvider').dialog('close');
					reloadTable();
				} else {
					$.messager.alert("提示", "供应商新增失败！", "error");
				}

			}
		};
		ajax_(ajax_param);
	}
}

/**
 * 新增修改
 */
function toUpdate(providerId) {
	$("#ipt_providerId").val(providerId);
	initUpdateVal(providerId);
	$("#btnSave").unbind('click');
	$("#btnSave").bind("click", function() {
		doUpdate(providerId);
	});
	$("#btnUpdate").unbind();
	$("#btnUpdate").bind("click", function() {
		$('#divAddProvider').dialog('close');
	});
	$('#divAddProvider').dialog('open');
}

/**
 * 修改厂商信息
 */
function doUpdate(providerId) {
	if (checkForm()) {
		var path = getRootName();
		var uri = path + "/platform/provider/updateProvider";
		var ajax_param = {
			url : uri,
			type : "post",
			datdType : "json",
			data : {
				"providerId" : providerId,
				"providerName" : $("#ipt_providerName").val(),
				"contacts" : $("#ipt_contacts").val(),
				"contactsTelCode" : $("#ipt_contactsTelCode").val(),
				"fax" : $("#ipt_fax").val(),
				"techSupportTel" : $("#ipt_techSupportTel").val(),
				"email" : $("#ipt_email").val(),
				"address" : $("#ipt_address").val(),
				"uRL" : $("#ipt_uRL").val(),
				"logoFileName" : $("#ipt_logoFileName").val(),
				"descr" : $("#ipt_descr").val(),
				"t" : Math.random()
			},
			error : function() {
				$.messager.alert("错误", "ajax_error", "error");
			},
			success : function(data) {
				if (true == data || "true" == data) {
					//$.messager.alert("提示", "供应商信息修改成功！", "info");
					$('#divAddProvider').dialog('close');
					reloadTable();
				} else {
					$.messager.alert("提示", "供应商信息修改失败！", "error");
					reloadTable();
				}
			}
		}
		ajax_(ajax_param);
	}
}

/**
 * 根据ID获取对象
 * 
 */
function initUpdateVal(providerId) {
	resetForm('tblAddProvider');
	var path = getRootName();
	var uri = path + "/platform/provider/findProvider";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"providerId" : providerId,
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

/**
 * 查看详情
 */
function toShowInfo(providerId) {
	$('#flag').val("edit");
	$('.input').val("");
	setRead(providerId);
	$("#btnClose2").unbind();
	$("#btnClose2").bind("click", function() {
		$('.input').val("");
		$('#divAddProvider').dialog('close');
	});

	$('#divShowDomainInfo').dialog('open');
}

/**
 * 判断厂商是否唯一
 */
function checkNameUnique(){
	var providerName = $("#ipt_providerName").val();
	if(providerName != null || providerName != ""){
		var path = getRootName();
		var uri = path + "/platform/provider/checkNameUnique";
		var ajax_param = {
			url : uri,
			type : "post",
			datdType : "json",
			data : {
				"providerName" : providerName,
				"t" : Math.random()
			},
			error : function(){
				$.messager.alert("错误", "ajax_error", "error");
			},
			success : function(data){
				if(data == false){
					$.messager.alert("提示", "该供应商已存在！", "info");
					$('#ipt_providerName').val("");
					$('#ipt_providerName').focus();
				}else{
					return;
				}
			}
		};
		ajax_(ajax_param);
	}
}

/**
 * 新增查看Div
 */
function toShowInfo(providerId){
	$('#flag').val("edit");
	$('.input').val("");
	setRead(providerId);
	$("#btnClose2").unbind();
	$("#btnClose2").bind("click", function(){
		$('.input').val("");
		$('#divShowProviderInfo').dialog('close');
	});
	$('#divShowProviderInfo').dialog('open');
}

/**
 * 详情信息
 * 
 */
function setRead(providerId){
	var path = getRootName();
	var uri = path + "/platform/provider/findProvider";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"providerId" : providerId,
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
