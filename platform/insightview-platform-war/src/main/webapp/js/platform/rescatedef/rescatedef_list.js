$(document).ready(function() {
	doInitTable();
	doInitManOption();//初始化设备厂商下拉框
	$('#divTypeDialog').window({
		minimizable:false,
		maximizable : false,
		collapsible : false,
		resizable:false,
		closed:true,
	    width:650,
	    height:450,	 
	    modal:true
	});
});


/**
 * 加载页面并初始化表格
 * @return
 */
function doInitTable(){
	var path = getRootName();
	$('#tblDataList')
			.datagrid(
					{
						width : 'auto',
						height : 'auto',
						nowrap : false,
						striped : true,
						border : true,
						collapsible : false,// 是否可折叠的
						fit : true,// 自动大小
						fitColumns:true,
						url : path + '/platform/resCateDefine/listResCateDef',
						remoteSort : true,
						onSortColumn:function(sort,order){
//						 alert("sort:"+sort+",order："+order+"");
						},						
						idField : 'fldId',
						singleSelect : false,// 是否单选
						checkOnSelect : false,
						selectOnCheck : true,
						pagination : true,// 分页控件
						rownumbers : true,// 行号
						toolbar : [ {
							'text' : '添加',
							'iconCls' : 'icon-add',
							handler : function() {
								toAdd();
							}
						}, {
							'text' : '删除',
							'iconCls' : 'icon-cancel',
							handler : function() {
								doBatchDel();
							}
						} ],
						columns : [ [
						        {
						        	field : 'resCategoryID',
						        	checkbox : true
						        },
								{
									field : 'resCategoryName',
									title : '产品目录名称',
									width : 180,
									align : 'left',
									sortable : true
								},
								{
									field : 'resCategoryAlias',
									title : '产品别名',
									width : 180,
									align : 'center',
									sortable : true
								},
								{
									field : 'resCategoryDescr',
									title : '产品描述',
									width : 200,
									align : 'center',									
									sortable : true
								},
								{
									field : 'resModel',
									title : '设备型号',
									width : 100,
									align : 'center',									
									sortable : true
								},
								{
									field : 'resManufacturerName',
									title : '生产厂家',
									width : 100,
									align : 'center',									
									sortable : true
								},
								{
									field : 'resCategoryIDs',
									title : '操作',
									width : 65,
									align : 'center',
									formatter : function(value, row, index) {
										return  '<a style="cursor: pointer;" title="查看" onclick="javascript:toShowInfo('
												+ row.resCategoryID
												+ ');"><img src="'
												+ path
												+ '/style/images/icon/icon_view.png" alt="查看" />&nbsp;<a style="cursor: pointer;" title="修改" onclick="javascript:toUpdate('
												+ row.resCategoryID
												+ ');"><img src="'
												+ path
												+ '/style/images/icon/icon_modify.png" alt="修改" /></a>&nbsp;<a style="cursor: pointer;" title="删除" onclick="javascript:doDel('
												+ row.resCategoryID
												+ ');"><img src="'
												+ path
												+ '/style/images/icon/icon_delete.png" alt="删除" /></a>';
									}
								} ] ]
					});
	  $(window).resize(function() {
	        $('#tblDataList').resizeDataGrid(0, 0, 0, 0);
	    });
}



/**
 * 打开新增div
 */
function toAdd() {
	$("#resManufacturerID").select2("val", "-1");
	$('#divView').dialog('close');
	resetForm('tblDetailInfo');
	$("#btnSave").unbind("click");
	$("#btnSave").bind("click", function() {
		doAdd();
	});
	$("#btnUpdate").unbind();
	$("#btnUpdate").bind("click", function() {
		$('#divAddEdit').dialog('close');
	});
	$('#divAddEdit').dialog('open');
}


/**
 * 打开修改div
 */
function toUpdate(id){
	$("#ipt_resCategoryID").val(id);	
	initUpdateVal(id);	
	$("#btnSave").unbind('click');
	$("#btnSave").bind("click", function() {
		doUpdate(id);
	});
	$("#btnUpdate").unbind();
	$("#btnUpdate").bind("click", function() {
		$('#divAddEdit').dialog('close');
	});
	$('#divAddEdit').dialog('open');	
}

/**
 * 刷新表格数据
 */
function reloadTable() {
	var resCategoryName=$("#resCategoryName").val();
	$('#tblDataList').datagrid('options').queryParams = {
		"resCategoryName" : resCategoryName
	};
	reloadTableCommon_1('tblDataList');
}

function reloadTableCommon_1(dataGridId) {
	$('#' + dataGridId).datagrid('load');
	$('#' + dataGridId).datagrid('uncheckAll');
}

/**
 * 验证表单信息
 * 
 */
function checkForm() {
	return checkInfo('#tblDetailInfo');
}


/**
 * 根据ID获取对象
 * 
 */
function initUpdateVal(id) {
	resetForm('tblDetailInfo');
	var path=getRootName();
	var uri=path+"/platform/resCateDefine/findResCateDefine";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"resCategoryID" : id,
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			iterObj(data, "ipt");
			$("#resManufacturerID").select2("val", data.resManufacturerID);
		}
	}
	ajax_(ajax_param);
}


/**
 * 删除信息
 */
function doDel(id) {
	$.messager.confirm("操作提示", "您确定删除此信息吗？", function (data) {
        if (data) {
        	var path = getRootName();
			var uri = path + "/platform/resCateDefine/delResCateDefine";
			var ajax_param = {
				url : uri,
				type : "post",
				datdType : "json",
				data : {
					"resCategoryID" : id,
					"t" : Math.random()
				},
				error : function() {
					$.messager.alert("错误", "ajax_error", "error");
				},
				success : function(data) {
					var flag = data.flag;
					if (flag=="1") {
//						$.messager.alert("提示", "信息删除成功！", "info");
						reloadTable();
					}else if(flag=="2") {
						$.messager.alert("提示", "该信息存在外键关系,不能删除！", "warning");
						reloadTable();
					}else{
						$.messager.alert("提示", "信息删除失败！", "error");
						reloadTable();
					}	
				}
			}
			ajax_(ajax_param);
        }            
    });	
}


/**
 * 新增处理
 */
function doAdd(){
	if (checkForm()) {
		var path=getRootName();
		var uri=path+"/platform/resCateDefine/addResCateDefine";
		var resManufacturerID = $("#resManufacturerID").select2("val");
		
		if(resManufacturerID=="-1"){
			resManufacturerID="";
		}	
		var ajax_param = {
			url : uri,
			type : "post",
			datdType : "json",
			data : {
			"resCategoryName" : $("#ipt_resCategoryName").val(),
			"resCategoryAlias" : $("#ipt_resCategoryAlias").val(),
			"resCategoryDescr" : $("#ipt_resCategoryDescr").val(),
			"resManufacturerID" : resManufacturerID,
			"resModel" : $("#ipt_resModel").val(),			
			"resTypeID" : $("#ipt_resTypeID").val(),
			"t" : Math.random()
			},
			error : function() {
				$.messager.alert("错误", "ajax_error", "error");
			},
			success : function(data) {
				if (true == data || "true" == data) {
//					$.messager.alert("提示", "信息新增成功！", "info");
					$('#divAddEdit').dialog('close');
					reloadTable();
				} else {
					$.messager.alert("提示", "信息新增失败！", "error");
				}
			}
		};
		ajax_(ajax_param);
	}
}


/**
 * 修改处理
 */
function doUpdate() {
	if (checkForm()) {
		var path = getRootName();
		var uri = path + "/platform/resCateDefine/updateResCateDefine";
		var resManufacturerID = $("#resManufacturerID").select2("val");
		
		if(resManufacturerID=="-1"){
			resManufacturerID="";
		}
		var parentTypeId = $("#ipt_parentTypeId").attr("alt");
		var ajax_param = {
			url : uri,
			type : "post",
			datdType : "json",
			data : {
				"resCategoryID" : $("#ipt_resCategoryID").val(),
				"resCategoryName" : $("#ipt_resCategoryName").val(),
				"resCategoryAlias" : $("#ipt_resCategoryAlias").val(),
				"resCategoryDescr" : $("#ipt_resCategoryDescr").val(),
				"resManufacturerID" : resManufacturerID,
				"resModel" : $("#ipt_resModel").val(),
				"resTypeID" : $("#ipt_resTypeID").val(),
				"t" : Math.random()
			},
			error : function() {
				$.messager.alert("错误", "ajax_error", "error");
			},
			success : function(data) {
				if (true == data || "true" == data) {
//					$.messager.alert("提示", "信息修改成功！", "info");
					$('#divAddEdit').dialog('close');
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

/**
 * 加载厂商信息列表页面
 */
//function loadRelateMaf(){
//	var path=getRootName();
//	var uri=path+"/platform/resCateDefine/toSelectMfList";
//	window.open(uri,"","height=480px,width=780px,left=400,top=200,resizable=no,scrollings=no,status=no,toolbar=no,menubar=no,location=no");
//}

/**
 * 加载配置项类型信息页面
 */
function loadRelateType(){
	var path=getRootName();
	var uri=path+"/platform/resCateDefine/toSelectRtList";
	window.open(uri,"","height=480px,width=780px,left=400,top=200,resizable=no,scrollings=no,status=no,toolbar=no,menubar=no,location=no");
}


/**
 * 批量删除
 */
function doBatchDel() {
    	var path = getRootName();
    	var checkedItems = $('[name=resCategoryID]:checked');
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
    				var uri =path+"/platform/resCateDefine/delBathResCateDefine";
        			var ajax_param = {
        				url : uri,
        				type : "post",
        				datdType : "json",
        				data : {
        					"id":ids
        				},
    					success : function(data) {
        					var flag = data.flag;
        					if (flag=="1") {
//        						$.messager.alert("提示", "信息批量删除成功！", "info");
        						reloadTable();
        					}else if(flag=="2") {
        						$.messager.alert("提示", "所选信息存在外键关系,不能删除！", "warning");
        						reloadTable();
        					}else{
        						$.messager.alert("提示", "信息批量删除失败！", "error");
        						reloadTable();
        					}	
    					}
    				};
    				ajax_(ajax_param);
    			}
    		});	
    	}else{
    		$.messager.alert('提示', '没有任何选中项', 'error');
    	}				
}


/**
 * 查看Div
 */
function toShowInfo(id){
	$('.input').val(""); 
	setRead(id);
	$("#btnClose2").unbind();
	$("#btnClose2").bind("click", function() {
		$('.input').val(""); 
		$('#divView').dialog('close');
	});
	$('#divView').dialog('open');
}

/**
 * 详情信息
 * @param id
 * @return
 */
function setRead(id){
	var path=getRootName();
	var uri=path+"/platform/resCateDefine/findResCateDefine";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"resCategoryID" : id,
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

function doInitManOption(){
	$('#resManufacturerID').createSelect2({
		uri : '/platform/manufacturer/readManufacturerInfo',
		idAttr : 'e',
		name : 'resManuFacturerName',
		id : 'resManuFacturerId'
	});	
}

//对应产品类型
function toAddTypelog() {
	resetForm('divTypeFilter');
	doInitTypeTable();	
	$('#divTypeDialog').dialog('open');
}

function doInitTypeTable() {
	$('#tblTypeList').datagrid({
		iconCls : 'icon-edit',// 图标
		width : 'auto',
		height : 308,
		nowrap : false,
		striped : true,
		border : true,
		collapsible : false,// 是否可折叠的
		//fit : true,// 自动大小
		url : getRootName() + '/platform/resTypeDefine/listResTypeDefine',
		remoteSort : false,
		idField : 'fldId',
		singleSelect : true,// 是否单选
		pagination : true,// 分页控件
		rownumbers : true,// 行号
		columns : [ [ 
			{
				field : 'resTypeName',
				title : '类型名称',
				width : 180,
				align : 'left'
			},
			{
				field : 'resTypeAlias',
				title : '类型别名',
				width : 180,
				align : 'center'
			},
			{
				field : 'resTypeDescr',
				title : '类型描述',
				width : 200,
				align : 'center'								
			}
		] ]
	});
}
function reloadTypeTable() {
	var resTypeName=$("#resTypeName").val();
	$('#tblTypeList').datagrid('options').queryParams = {
		"resTypeName" : resTypeName
	};
	reloadTableCommon_1('tblTypeList');
}
function doAddTypelog() {
	var row = $('#tblTypeList').datagrid('getSelected');
	if (row) {
		$('#divTypeDialog').dialog('close');
		$("#ipt_resTypeID").val(row.resTypeID); //显示选中的数据
		$("#ipt_resTypeName").val(row.resTypeName); //显示选中的数据
	}
}
function doTypeCancel() {
	$('#divTypeDialog').dialog('close');
}