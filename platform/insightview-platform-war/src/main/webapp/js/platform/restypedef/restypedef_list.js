$(document).ready(function() {
	doInitTable();
	initTree();
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
						url : path + '/platform/resTypeDefine/listResTypeDefine',
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
						        	 field : 'resTypeID',
						        	 checkbox : true
						         },
								{
									field : 'resTypeName',
									title : '类型名称',
									width : 180,
									align : 'left',
									sortable : true
								},
								{
									field : 'resTypeAlias',
									title : '类型别名',
									width : 180,
									align : 'center',
									sortable : true
								},
								{
									field : 'resTypeDescr',
									title : '类型描述',
									width : 200,
									align : 'center',									
									sortable : true
								},
								{
									field : 'icon',
									title : '图标',
									width : 100,
									align : 'center',									
									sortable : true
								},
								{
									field : 'resTypeIDs',
									title : '操作',
									width : 65,
									align : 'center',
									formatter : function(value, row, index) {
										return '<a style="cursor: pointer;" title="查看" onclick="javascript:toShowInfo('
												+ row.resTypeID
												+ ');"><img src="'
												+ path
												+ '/style/images/icon/icon_view.png" alt="查看" />&nbsp;<a style="cursor: pointer;" title="修改" onclick="javascript:toUpdate('
												+ row.resTypeID
												+ ');"><img src="'
												+ path
												+ '/style/images/icon/icon_modify.png" alt="修改" /></a>&nbsp;<a style="cursor: pointer;" title="删除" onclick="javascript:doDel('
												+ row.resTypeID
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
	$("#ipt_resTypeID").val(id);	
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
	var resTypeName=$("#resTypeName").val();
	$('#tblDataList').datagrid('options').queryParams = {
		"resTypeName" : resTypeName
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
	var uri=path+"/platform/resTypeDefine/findResTypeDefine";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"resTypeID" : id,
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			iterObj(data, "ipt");
			if(data.parentTypeId==-1){
				$("#ipt_parentTypeId").val("无");
				$("#ipt_parentTypeId").attr('alt',"-1");
			}else{	
				$("#ipt_parentTypeId").val(data.parentTypeName);
				$("#ipt_parentTypeId").attr('alt', data.parentTypeId);
			}	
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
        	var uri = path + "/platform/resTypeDefine/delResTypeDefine";
			var ajax_param = {
				url : uri,
				type : "post",
				datdType : "json",
				data : {
					"resTypeID" : id,
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
		_treeOpenCount = 0;//重新更新树
		var path=getRootName();
		var uri=path+"/platform/resTypeDefine/addResTypeDefine";
		var parentTypeId = $("#ipt_parentTypeId").attr("alt");
		if(null == parentTypeId || parentTypeId==""){
			parentTypeId = -1;
		}	
		var ajax_param = {
			url : uri,
			type : "post",
			datdType : "json",
			data : {
				"resTypeName" : $("#ipt_resTypeName").val(),
				"resTypeAlias" : $("#ipt_resTypeAlias").val(),
				"resTypeDescr" : $("#ipt_resTypeDescr").val(),
				"parentTypeId" : parentTypeId,
				"icon" : $("#ipt_icon").val()
			},
			error : function() {
				$.messager.alert("错误", "ajax_error", "error");
			},
			success : function(data) {
				if (true == data || "true" == data) {
//					$.messager.alert("提示", "信息新增成功！", "info");
					$('#divAddEdit').dialog('close');
					reloadTable();
					initTree();
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
		_treeOpenCount = 0;//重新更新树
		var path = getRootName();
		var uri = path + "/platform/resTypeDefine/updateResTypeDefine";
		var parentTypeId = $("#ipt_parentTypeId").attr("alt");
		var resTypeID = $("#ipt_resTypeID").val();
		if(null == parentTypeId || parentTypeId==""){
			parentTypeId = -1;
		}
		if(parentTypeId == resTypeID ){
			$.messager.alert("提示", "父类不能选自己！", "error");
			return false;
		}
		var ajax_param = {
			url : uri,
			type : "post",
			datdType : "json",
			data : {
				"resTypeID" : $("#ipt_resTypeID").val(),
				"resTypeName" : $("#ipt_resTypeName").val(),
				"resTypeAlias" : $("#ipt_resTypeAlias").val(),
				"resTypeDescr" : $("#ipt_resTypeDescr").val(),
				"parentTypeId" : parentTypeId,
				"icon" : $("#ipt_icon").val(),				
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
					initTree();
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
 * 初始化树
 */

var _treeData = "";
var _treeOpenCount = 0;
function initTree() {
	var path = getRootPatch();
	var uri = path + "/platform/resTypeDefine/initTreeResTypeDefine";
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
			// 得到树的json数据源
			var datas = eval('(' + data.resTypeDefLstJson + ')');
			_treeData = datas;			
		}
	}
	ajax_(ajax_param);
}


/**
 * 选择父类名称
 */
function doChoseParentOrg() {
	if (0 == _treeOpenCount) {
		++_treeOpenCount;
		var path = getRootPatch();
		dataTreeTwo = new dTree("dataTreeTwo", path + "/plugin/dTree/img/");
		dataTreeTwo.add(0,-1, "无", "javascript:hiddenDTreeSetValEasyUi('divChoseOrg','ipt_parentTypeId','-1','无');");
		// 得到树的json数据源
		var datas = _treeData;
		// 遍历数据

		var gtmdlToolList = datas;
		for (var i = 0; i < gtmdlToolList.length; i++) {
			var _id = gtmdlToolList[i].resTypeID;
			var _nameTemp = gtmdlToolList[i].resTypeName;
			var _parent = gtmdlToolList[i].parentTypeId;
			if(_parent==-1){
				_parent=0;
			}	
			dataTreeTwo.add(_id, _parent, _nameTemp,
					"javascript:hiddenDTreeSetValEasyUi('divChoseOrg','ipt_parentTypeId','"
							+ _id + "','" + _nameTemp + "');");
		}
		$('#dataTreeDivs').empty();
		$('#dataTreeDivs').append(dataTreeTwo + "");
	}
	$('#divChoseOrg').dialog('open');
}


/**
 * 批量删除
 */
function doBatchDel() {
    	var path = getRootName();
    	var checkedItems = $('[name=resTypeID]:checked');
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
    				var uri =path+"/platform/resTypeDefine/delBathResTypeDefine";
        			var ajax_param = {
        				url : uri,
        				type : "post",
        				datdType : "json",
        				data : {
        					"id":ids
        				},
    					success : function(data) {
    						if (true == data || "true" == data) {
//    							$.messager.alert("提示", "信息批量删除成功！", "info");
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
	var uri=path+"/platform/resTypeDefine/findResTypeDefine";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"resTypeID" : id,
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


