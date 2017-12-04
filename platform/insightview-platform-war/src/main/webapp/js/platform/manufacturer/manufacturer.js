$(document).ready(function() {	
	doInitTable();
});


/**
 * 刷新表格数据
 */
function reloadTable() {
	var mfName=$("#txtFilterManufacturerName").val();
	$('#tblManufacturer').datagrid('options').queryParams = {
		"resManuFacturerName" : mfName
	};
	reloadTableCommon('tblManufacturer');
}

/**
 * 加载页面并初始化表格
 */
function doInitTable(){
	var path = getRootName();
	$('#tblManufacturer')
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
					url : path + '/platform/manufacturer/listManufacturer',
					remoteSort : false,
					onSortColumn:function(sort,order){
					},						
					idField : 'resManuFacturerId',
					singleSelect : true,// 是否单选
					selectOnCheck: false,
					checkOnSelect: false,
					pagination : true,// 分页控件
					rownumbers : true,// 行号
					toolbar : [{
								'text' : '新增',
								'iconCls' : 'icon-add',
								handler : function() {									
									toAdd();
								}
							},
							{
								'text': '删除',
								'iconCls': 'icon-cancel',
								handler : function(){
									doBatchDel();
								}
							}],
					columns : [[
							{
								field : 'resManuFacturerId',
								checkbox : true  //单选框
							},{
								field : 'resManuFacturerName',
								title : '厂商名称',
								width : 110,
								align : 'center',
								sortable : true
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
								field : 'resManuFacturerIds',
								title : '操作',
								width : 70,
								align : 'center',
								formatter : function(value, row, index) {
								var param = "&quot;" + row.resManuFacturerId
										  + "&quot;,&quot;"
										  + row.resManuFacturerName + "&quot;";	
								return '<a style="cursor: pointer;" title="查看" onclick="javascript:toShowInfo('
										+ row.resManuFacturerId
										+ ');"><img src="'
										+ path
										+ '/style/images/icon/icon_view.png" alt="查看" /></a> &nbsp;'
										+ '<a style="cursor: pointer;" title="修改" onclick="javascript:toUpdate('
										+ param
										+ ');"><img src="'
										+ path
										+ '/style/images/icon/icon_modify.png" alt="修改" /></a> &nbsp;'
										+ '<a style="cursor: pointer;" title="删除" onclick="javascript:doDel('
										+ row.resManuFacturerId
										+ ');"><img src="'
										+ path
										+ '/style/images/icon/icon_delete.png" alt="删除" /></a>';
								}
						}]]
					});
	 $(window).resize(function() {
	        $('#tblManufacturer').resizeDataGrid(0, 0, 0, 0);
	});
}

function checkForm() {
	return checkInfo('#tblAddManufacturer');
}

/**
 * 删除信息
 */
function doDel(resManuFacturerId){
	var path = getRootName();
	$.messager.confirm("提示","确定删除此信息?",function(r){
	if(r==true){
		var uriChk = path + "/platform/manufacturer/isUsedByRes";
		var ajax_param = {
				url : uriChk,
				type: "post",
				datdType: "json",
				data : {
					"resManuFacturerId" : resManuFacturerId,
					"t" : Math.random()
			},
			error : function(){
				$.messager.alert("错误", "ajax_error", "error");			
			},
			success : function(data){
				if(data == true || "true" == data){
					$.messager.alert("提示","该厂商信息正在使用，不能删除！","error");
				}else{
					var uri = path + "/platform/manufacturer/delManufacturer";
					var ajax_param_m = {
							url : uri,
							type: "post",
							datdType: "json",
							data : {
								"resManuFacturerId" : resManuFacturerId,
								"t" : Math.random()
						},
						error : function(){
							$.messager.alert("错误", "ajax_error", "error");			
						},
						success : function(data){
							if(true == data || "true" == data){
//								$.messager.alert("提示","厂商删除成功！","info");
								reloadTable();
							}else {
								$.messager.alert("","厂商删除失败！","error");
							}
						}
					}
					ajax_(ajax_param_m);
				}
			}
		}
		
		
		ajax_(ajax_param);
		}
	});
}

/**
 * 打开新增div
 */
function toAdd() {
	resetForm('tblAddManufacturer');
	
	$("#btnSave").unbind("click");
	$("#btnSave").bind("click", function() {
		doCheckAdd();
	});
	
	$("#btnUpdate").unbind();
	$("#btnUpdate").bind("click", function() {
		cancelRedBox("ipt_resManuFacturerName");
		$('#divAddManufacturer').dialog('close');
	});
	$('#divAddManufacturer').dialog({    
	    title: '新增厂商信息',    
	});
	$('#divAddManufacturer').dialog('open');
}

function doCheckAdd(){
	if(checkForm()){
		checkNameUnique();
	}
}

function checkNameUnique() {
		var resManuFacturerName = $("#ipt_resManuFacturerName").val(); 
		if(resManuFacturerName !=null || resManuFacturerName !=""){
			var path = getRootName();
			var uri = path + "/platform/manufacturer/checkNameUnique";
			var ajax_param = {
				url : uri,
				type : "post",
				datdType : "json",
				data : {
					"resManuFacturerName" : resManuFacturerName,
					"t" : Math.random()
				},
				error : function() {
					$.messager.alert("错误", "ajax_error", "error");
				},
				success : function(data) {
					if ( data ==false) {
						$.messager.alert("提示", "该厂商已存在！", "info");
						$('#ipt_resManuFacturerName').focus();
					}else{ 
						doAdd();
					}
				}
			};
			ajax_(ajax_param);
}
}

/**
 * 新增厂商 
 */
function doAdd(){
		var path=getRootName();
		var uri=path+"/platform/manufacturer/addManufacturer";
		var ajax_param = {
			url : uri,
			type : "post",
			datdType : "json",
			data : {
			"resManuFacturerName" : $("#ipt_resManuFacturerName").val(),
			"resManuFacturerAlias" : $("#ipt_resManuFacturerAlias").val(),
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
					$.messager.alert("提示", "厂商新增成功！", "info");
					$('#divAddManufacturer').dialog('close');
					reloadTable();
				} else {
					$.messager.alert("提示", "厂商新增失败！", "error");
				}

			}
		};
		ajax_(ajax_param);
}

/**
 * 新增修改
 */ 
function toUpdate(resManuFacturerId,resManuFacturerName){
	initUpdateVal(resManuFacturerId);
	
	$("#btnSave").unbind('click');
	$("#btnSave").bind("click", function() {
		doCheckUpdate(resManuFacturerName);
	});
	
	$("#btnUpdate").unbind();
	$("#btnUpdate").bind("click", function() {
		$('#divAddManufacturer').dialog('close');
	});
	$('#divAddManufacturer').dialog({    
	    title: '编辑厂商信息',    
	});  
	$('#divAddManufacturer').dialog('open');	
}

function doCheckUpdate(resManuFacturerName){
	if(checkForm()){	
		checkNameUniqueBeforeUpdate(resManuFacturerName);
	}
}

/**
 * 修改前判断厂商是否唯一
 * 
 */
function checkNameUniqueBeforeUpdate(resManuFacturerName) {
			var checkResManuFacturerName = $("#ipt_resManuFacturerName").val();
			var path = getRootName();
			var uri = path + "/platform/manufacturer/checkNameUniqueBeforeUpdate";
			var ajax_param = {
				url : uri,
				type : "post",
				datdType : "json",
				data : {
					"checkResManuFacturerName" : checkResManuFacturerName,
					"resManuFacturerName" : resManuFacturerName,
					"t" : Math.random()
				},
				error : function() {
					$.messager.alert("错误", "ajax_error", "error");
				},
				success : function(data) {
					if ( data ==false || "false" == data) {
						$.messager.alert("提示", "该厂商已存在！", "error");
						$('#ipt_resManuFacturerName').focus();
					}else{
						doUpdate();
					}
				}
			};
			ajax_(ajax_param);
}


/**
 * 修改厂商信息
 */ 
function doUpdate() {
	var resManuFacturerName = $("#ipt_resManuFacturerName").val(); 
		var path=getRootName();
		var uri =path+"/platform/manufacturer/updateManufacturer";
		var ajax_param = {
			url : uri,
			type : "post",
			datdType : "json",
			data : {
				"resManuFacturerId" : $("#ipt_resManuFacturerId").val(),
				"resManuFacturerName" : $("#ipt_resManuFacturerName").val(),
				"resManuFacturerAlias" : $("#ipt_resManuFacturerAlias").val(),
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
//					$.messager.alert("提示", "厂商信息修改成功！", "info");
					$('#divAddManufacturer').dialog('close');
					reloadTable();
				} else {
					$.messager.alert("提示", "厂商信息修改失败！", "error");
					reloadTable();
				}
			}
		}
		ajax_(ajax_param);
}

/**
 * 根据ID获取对象
 * 
 */
function initUpdateVal(resManuFacturerId) {
	resetForm('tblAddManufacturer');
	var path=getRootName();
	var uri=path+"/platform/manufacturer/findManufacturer";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"resManuFacturerId" : resManuFacturerId,
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
 * 批量删除
 */
function doBatchDel() {
	var path =getRootName();
    var checkedItems = $('[name=resManuFacturerId]:checked');
    var ids = null;
    for(var i = 0; i < checkedItems.length; i++)
    	{
    		var id = checkedItems[i].value;
    		if (null == ids) {
    			ids = id;
   			} else {
   				ids += ',' + id;
   			}
   		}
    
    if (null != ids) {
   		$.messager.confirm("提示","确定删除所选中项?",function(r){
    	   	if (r == true) {
    	    		var uri = path+ "/platform/manufacturer/delBatchManufacturer?resManuFacturerIds="+ids;
    	    		var ajax_param = {
    				url : uri,
    				type : "post",
    				datdType : "json",
    				data : {
    					"t" : Math.random()
    				},
    				success : function(data) {
    					if(data.flag == false) {
    						$.messager.alert("错误", "被删厂商"+data.manuName+"正在被使用，删除失败！", "error");
    						reloadTable();
    					} else {
    						//$.messager.alert("提示", "批量删除厂商删除成功！", "info");
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
 * 新增查看Div
 */
function toShowInfo(resManuFacturerId){
	$('.input').val(""); 
	setRead(resManuFacturerId);
	
	$("#btnClose2").unbind();
	$("#btnClose2").bind("click", function() {
		$('.input').val(""); 
		$('#divShowManufacturerInfo').dialog('close');
	});
	$('#divShowManufacturerInfo').dialog('open');
}

/**
 * 详情信息
 * @param id
 * @return
 */
function setRead(resManuFacturerId){
	var path=getRootName();
	var uri = path+"/platform/manufacturer/findManufacturer";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"resManuFacturerId" : resManuFacturerId,
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

