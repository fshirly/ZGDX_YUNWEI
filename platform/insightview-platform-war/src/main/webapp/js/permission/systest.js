$(document).ready(function() {
	doInitTable();
	doChoseConstantByTypeName('ipt_userType', 'UserType');
});

/**
 * 文件上传前验证
 * 
 * @param imgFile
 */
function initAndCheckUpload(imgFile) {
	// passfixArr 允许上传的类型
	// fileSize 允许上传文件的大小
	// previewControlId 预览的控件ID
	// isPreview 是否预览
	// uploadBtnId 上传按钮

	var fileObj = new Object();
	fileObj.passfixArr = _imgFileType;
	fileObj.fileSize = "202400";
	fileObj.previewControlId = 'imgPreview';
	fileObj.isPreview = true;
	fileObj.uploadBtnId = 'btnUpload';

	commonChkFile(imgFile, fileObj);
}

/**
 * 文件上传
 * 
 * @param imgFile
 */
function doUploadFile() {
	var fileObj = new Object();
	fileObj.elementId = "ipt_imageFileNames";
	fileObj.uploadBtnId = "btnUpload";

	updateFileCommon(fileObj);
}

/**
 * 上传过后的回调
 * 
 * @param imgFile
 */
function uploadCallBack(fileP) {
	$("#lbl_filepath").html(fileP);
	$("#img_filepreview").attr("src", _fileServerPath + fileP);

	initDownload(fileP);
}

function initDownload(fileP) {
	initDownLinkTag('a1', fileP);
}

/**
 * 图片上传并预览
 * 
 * @author 武林
 * @param imgFile
 *            上传的图片
 */
function initFile(imgFile) {
	var fileObj = new Object();
	fileObj.passfixArr = new Array("doc", "war", "rar", "zip");
	fileObj.fileSize = "202400";
	fileObj.controlId = "ipt_fileupload";

	commonPreviewFile(imgFile, fileObj);
}

/*
 * 更新表格 @author 武林
 */
function reloadTable() {
	var userAccount = $("#txtFilterUserAccount").val();
	var userName = $("#txtFilterUserName").val();
	var userType = $("#selFilterUserType").val();
	var isAutoLock = $("#selFilterIsAutoLock").val();

	$('#tblSysUser').datagrid('options').queryParams = {
		"userAccount" : userAccount,
		"userName" : userName,
		"userType" : userType,
		"isAutoLock" : isAutoLock
	};
	reloadTableCommon('tblSysUser');
}

function toUploadImg() {
	$('#divUploadImg').dialog('open');
}

function toSelect2() {
	for (var i = 0; i < jsonObject.blogrolls.length; i++) {
		var pObj = jsonObject.blogrolls[i];

		buildselect("select2", pObj.text, pObj.value);
		buildselect("select2_1", pObj.text, pObj.value);
		buildselect("select2_2", pObj.text, pObj.value);
		buildselect("select2_3", pObj.text, pObj.value);
	}
	$("#select2").select2({
		maximumSelectionSize : 2
	});

	$("#select2_1").select2();

	$("#select2_2").select2({
		placeholder : "请选择",
		allowClear : true
	});

	$("#select2_3").select2({
		placeholder : "请选择",
		allowClear : true
	});

	$("#e2").select2({
		placeholder : "Select a State",
		allowClear : true
	});
	$('#divSelect2').dialog('open');

}

/*
 * 页面加载初始化表格 @author 武林
 */
function doInitTable() {
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
						fit : true,// 自动大小
						fitColumns : true,
						url : path + '/permissionSysUser/listSysUser',
						sortOrder : 'desc',
						remoteSort : true,
						onSortColumn : function(sort, order) {
							// alert("sort:"+sort+",order："+order+"");
						},
						queryParams : {
							"isAutoLock" : -1,
							"userType" : -1
						},
						remoteSort : false,
						idField : 'fldId',
						singleSelect : true,// 是否单选
						pagination : true,// 分页控件
						rownumbers : true,// 行号
						toolbar : [ {
							'text' : '添加',
							'iconCls' : 'icon-add',
							handler : function() {
								toAdd();
							}
						}, {
							'text' : '图片上传',
							'iconCls' : 'icon-add',
							handler : function() {
								toUploadImg();
							}
						}, {
							'text' : 'select2',
							'iconCls' : 'icon-add',
							handler : function() {
								toSelect2();
							}
						} ],
						columns : [ [
								{
									field : 'userAccount',
									title : '用户名',
									width : 70,
									align : 'center',
									sortable : true
								},
								{
									field : 'userName',
									title : '用户姓名',
									width : 80,
									align : 'center',
									sortable : true
								},
								{
									field : 'mobilePhone',
									title : '手机号码',
									width : 80,
									align : 'center',
									sortable : true
								},
								{
									field : 'userType',
									title : '用户类型',
									width : 180,
									align : 'center',
									sortable : true,
									formatter : function(value, row, index) {
										if (value == 0) {
											return '管理员';
										}
										if (value == 1) {
											return '企业内IT部门用户';
										}
										if (value == 2) {
											return '企业业务部门用户';
										} else {
											return '外部供应商用户';
										}
									}
								},
								{
									field : 'isAutoLock',
									title : '是否锁定',
									width : 100,
									align : 'center',
									sortable : true,
									formatter : function(value, row, index) {
										if (value == 0) {
											return '是';
										} else {
											return '否';
										}
									}
								},
								{
									field : 'lockedReason',
									title : '锁定原因',
									width : 180,
									align : 'center',
									sortable : true
								},
								{
									field : 'userID',
									title : '操作',
									width : 100,
									align : 'center',
									formatter : function(value, row, index) {
										return '<a style="cursor: pointer;" onclick="javascript:toUpdate('
												+ row.userID
												+ ');"><img src="'
												+ path
												+ '/style/images/icon/icon_modify.png" alt="修改" /></a> &nbsp;&nbsp;&nbsp;&nbsp;<a style="cursor: pointer;" onclick="javascript:doDel('
												+ row.userID
												+ ');"><img src="'
												+ path
												+ '/style/images/icon/icon_delete.png" alt="删除" /></a>';
									}
								} ] ]
					});
	// 浏览器窗口大小变化时，datagrid表格自适应窗口大小变化
	$(window).resize(function() {
		$('#tblSysUser').resizeDataGrid(0, 0, 0, 0);
		// alert('1111');
	});
}

/**
 * 打开更新页面
 * 
 * @param userId
 *            用户ID
 */
function toUpdate(userId) {
	_auType = 0;
	resetForm('tblAddUser');
	initUpdateVal(userId);

	$("#btnSave").unbind('click');
	$("#btnSave").bind("click", function() {
		doUpdate();
	});
	$("#btnUpdate").unbind();
	$("#btnUpdate").bind("click", function() {
		initUpdateVal(userId);
	});
	$('#divAddUser').dialog('open');
}

function initUpdateVal(userId) {
	var path = getRootName();
	var uri = path + "/permissionSysTest/findSysUser";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"userID" : userId,
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

/* 重置 原来的表单数据 */
function resetFormValue(pcId) {
	var userId = $("#ipt_userID").val();
	if (userId.trim() == "") {
		resetForm(pcId);
	} else {
		toUpdate(userId);
	}
}

function doAdd() {
	var result = checkSysUser();
	var path = getRootName();
	var uri = path + "/permissionSysTest/addSysUser";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"status" : 1,
			"userAccount" : $("#ipt_userAccount").val(),
			"userName" : $("#ipt_userName").val(),
			"userPassword" : $("#ipt_userPassword").val(),
			"mobilePhone" : $("#ipt_mobilePhone").val(),
			"email" : $("#ipt_email").val(),
			"telephone" : $("#ipt_telephone").val(),
			"userType" : $("#ipt_userType").val(),
			"isAutoLock" : "1",
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			if (true == data || "true" == data) {
				$.messager.alert("提示", "用户新增成功！", "info");
				$('#divAddUser').dialog('close');
				reloadTable();
			} else {
				$.messager.alert("提示", "用户新增失败！", "error");
			}
		}
	};
	if (result == true || result == 'true') {
		ajax_(ajax_param);
	}
}

/*
 * 打开新增div @author 武林
 */
function toAdd() {
	resetForm('tblAddUser');

	$("#btnSave").unbind('click');
	$("#btnSave").bind("click", function() {
		doAdd();
	});
	$("#btnUpdate").unbind();
	$("#btnUpdate").bind("click", function() {
		resetForm('tblAddUser');
	});

	_auType = 1;
	$('#divAddUser').dialog('open');
}
var _auType = 0;
function checkSysUser() {
	if (_auType == 1) {
		var userAccount = $("#ipt_userAccount").val();
		var path = getRootName();
		var uri = path + "/permissionSysUser/checkSysUser";
		var ajax_param = {
			url : uri,
			type : "post",
			datdType : "json",
			data : {
				"userAccount" : userAccount,
				"t" : Math.random()
			},
			error : function() {
				$.messager.alert("错误", "ajax_error", "error");
			},
			success : function(data) {
				if (true == data || "true" == data) {
					$.messager.alert("提示", "该用户名已存在！", "error");
					// $("#ipt_userAccount").focus();
				} else {
					return;
				}
			}
		};
		ajax_(ajax_param);
	}
}