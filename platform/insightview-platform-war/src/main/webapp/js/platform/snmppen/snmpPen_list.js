$(document).ready(function() {	
	doInitTable();
	doInitManOption();//初始化设备厂商下拉框
});

/**
 * 加载页面并初始化表格
 * @return
 */
function doInitTable() {
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
						url : path + '/platform/snmpPen/listSnmpPen',
						remoteSort : true,
						onSortColumn:function(sort,order){
//						 alert("sort:"+sort+",order："+order+"");
						},						
						idField : 'id',
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
									field : 'pen',
									title : 'PEN',
									width : 80,
									align : 'left',
									sortable : true
								},
								{
									field : 'enterpriseOID',
									title : '企业OID',
									width : 100,
									align : 'center',
									sortable : true
								},
								{
									field : 'organization',
									title : '企业名称',
									width : 150,
									align : 'center',									
									sortable : true
								},
								{
									field : 'orgAddress',
									title : '企业地址',
									width : 150,
									align : 'center',									
									sortable : true
								},
								{
									field : 'postCode',
									title : '邮政编码',
									width : 80,
									align : 'center',
									sortable : true
								},
								
								{
									field : 'contactTelephone',
									title : '联系电话',
									width : 80,
									align : 'center',
									sortable : true
								},
								{
									field : 'contactPerson',
									title : '联系人',
									width : 80,
									align : 'center',									
									sortable : true
								},
								{
									field : 'orgEmail',
									title : 'Email',
									width : 80,
									align : 'center',									
									sortable : true
								},
								{
									field : 'resManufacturerName',
									title : '关联厂商',
									width : 80,
									align : 'center',									
									sortable : true
								},
								{
									field : 'ids',
									title : '操作',
									width : 85,
									align : 'center',
									formatter : function(value, row, index) {
										return '<a style="cursor: pointer;" title="查看" onclick="javascript:toShowInfo('
												+ row.id
												+ ');"><img src="'
												+ path
												+ '/style/images/icon/icon_view.png" alt="查看" /></a> &nbsp;<a style="cursor: pointer;" title="修改" onclick="javascript:toUpdate('
												+ row.id
												+ ');"><img src="'
												+ path
												+ '/style/images/icon/icon_modify.png" alt="修改" /></a> &nbsp;<a style="cursor: pointer;" title="删除" onclick="javascript:doDel('
												+ row.id
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


/*
 * 删除任务 
 */
function doDel(id) {
		$.messager.confirm("操作提示", "您确定删除此信息吗？", function (data) {
            if (data) {
            	var path = getRootName();
    			var uri = path + "/platform/snmpPen/delSnmpPen";
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
//    						$.messager.alert("提示", "信息删除成功！", "info");
    						reloadTable();
    					} else {
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
		window.location.href = window.location.href;
		$('#divAddEdit').dialog('close');
	});
	$('#divAddEdit').dialog('open');
}

/**
 * 新增处理
 */
function doAdd(){	
	if (checkForm()) {	
		var path=getRootName();
		var uri=path+"/platform/snmpPen/addSnmpPen";
		var resManufacturerID = $("#resManufacturerID").select2("val");
		
		if(resManufacturerID=="-1"){
			resManufacturerID="";
		}	
		var ajax_param = {
			url : uri,
			type : "post",
			datdType : "json",
			data : {
				"pen" : $("#ipt_pen").val(),
				"enterpriseOID" : $("#ipt_enterpriseOID").val(),
				"organization" : $("#ipt_organization").val(),
				"orgAddress" : $("#ipt_orgAddress").val(),
				"postCode" : $("#ipt_postCode").val(),
				"contactTelephone" : $("#ipt_contactTelephone").val(),
				"contactPerson" : $("#ipt_contactPerson").val(),
				"orgEmail" : $("#ipt_orgEmail").val(),				
				"resManufacturerID" : resManufacturerID
			},
			error : function() {
				$.messager.alert("错误", "ajax_error", "error");
			},
			success : function(data) {
				if (true == data || "true" == data) {
					$('#divAddEdit').dialog('close');
//					$.messager.alert("提示", "信息新增成功！", "info");					
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
 * 打开修改页面
 */
function toUpdate(id){
	$("#ipt_id").val(id);	
	initUpdateVal(id);	
	$("#btnSave").unbind('click');
	$("#btnSave").bind("click", function() {
		doUpdate(id);
	});
	$("#btnUpdate").unbind();
	$("#btnUpdate").bind("click", function() {
		window.location.href = window.location.href;
		$('#divAddEdit').dialog('close');
	});
	$('#divAddEdit').dialog('open');	
}

/**
 * 修改页面数据
 */
function doUpdate(id) {
	if (checkForm()) {		
		var path=getRootName();
		var uri =path+"/platform/snmpPen/updateSnmpPen";
		var resManufacturerID = $("#resManufacturerID").select2("val");
		
		if(resManufacturerID=="-1"){
			resManufacturerID="";
		}	
		var ajax_param = {
			url : uri,
			type : "post",
			datdType : "json",
			data : {
				"id" : id,
				"pen" : $("#ipt_pen").val(),
				"enterpriseOID" : $("#ipt_enterpriseOID").val(),
				"organization" : $("#ipt_organization").val(),
				"orgAddress" : $("#ipt_orgAddress").val(),
				"postCode" : $("#ipt_postCode").val(),
				"contactTelephone" : $("#ipt_contactTelephone").val(),
				"contactPerson" : $("#ipt_contactPerson").val(),
				"orgEmail" : $("#ipt_orgEmail").val(),				
				"resManufacturerID" : resManufacturerID
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
 * 刷新表格数据
 */
function reloadTable() {
	var organizationName=$("#organizationName").val();
	$('#tblDataList').datagrid('options').queryParams = {
		"organization" : organizationName
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
	$("#resManufacturerID").select2("val", "-1");
	var path=getRootName();
	var uri=path+"/platform/snmpPen/findSnmpPen";
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
			$("#resManufacturerID").select2("val", data.resManufacturerID);
		}
	}
	ajax_(ajax_param);
}
/**
 * 
 * 判断PEN字段数据是否唯一
 */
function checkIsUnique() {
	var pen=$('#ipt_pen').val();	
	if(pen !=null && pen !=""){
		var flag = /^[+]?[1-9]+\d*$/i.test(pen);//验证是否为正整数
		if(!flag){
			$.messager.alert("提示", "PEN请输入正整数！", "info");		
			$('#ipt_pen').val("");
			$('#ipt_pen').focus();		
		}else {
			var path=getRootName();
			var uri=path+"/platform/snmpPen/colValIsUnique";
			var ajax_param={
			url:uri,
			type:"post",
			datdType:"json",
			async : false,
			data:{
				"pen" : pen,
				"t":Math.random()
			},
			error:function(){
				$.messager.alert("错误","ajax_error","error");
			},
			success:function(data){
				if(data==false){
					$.messager.alert("提示", "PEN已存在！", "info");
					$('#ipt_pen').val("");
					$('#ipt_pen').focus();
				}else{
					return;
				}
			}
			};
			ajax_(ajax_param);
		}
	}
}

/**
 * 加载厂商信息列表页面
 */
//function loadRelateMf(){
//	var path=getRootName();
//	var uri=path+"/platform/resCateDefine/toSelectMfList";
//	window.open(uri,"","height=500px,width=800px,left=400,top=200,resizable=no,scrollings=no,status=no,toolbar=no,menubar=no,location=no");
//}

function doInitManOption(){
	$('#resManufacturerID').createSelect2({
		uri : '/platform/manufacturer/readManufacturerInfo',
		idAttr : 'e',
		name : 'resManuFacturerName',
		id : 'resManuFacturerId'
	});	
}


/**
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
				var uri =path+"/platform/snmpPen/delBathSnmpPen";
    			var ajax_param = {
    				url : uri,
    				type : "post",
    				datdType : "json",
    				data : {
    					"id":ids
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
	var uri=path+"/platform/snmpPen/findSnmpPen";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"id" : id
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