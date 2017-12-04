$(document).ready(
	function() {
		$.extend($.fn.datagrid.methods, {
			getChecked : function(jq) {
				var rr = [];
				var rows = jq.datagrid('getRows');
				jq.datagrid('getPanel').find(
						'div.datagrid-cell-check input:checked').each(
						function() {
							var index = $(this).parents('tr:first').attr(
									'datagrid-row-index');
							rr.push(rows[index]);
						});
				return rr;
			}
		});

		// 页面初始化加载表格
		doInitTable();
		$('#tblConstantTypeManagement').datagrid('hideColumn','typeOrItem');
		// 初始化下拉列表树
		initTree();
	}
);

//树数据
var _treeData = "";

var _currentNodeId = -1;
var _currentNodeName = "";

function treeClickAction(id, name) {
	_currentNodeId = id;
	_currentNodeName = name;
//	isLeaf(id);
	reloadTable();
}

function isLeaf(id){
	var path=getRootName();
	var uri=path+"/platform/dataDictManage/isLeaf";
	var ajax_param={
			url:uri,
			type:"post",
			datdType:"json",
			async : false,

			data:{
				"id":id,
				"t":Math.random()
			},
			error:function(){
				$.messager.alert("错误","ajax_error","error");
			},
			success:function(data){
				$('#isleaf').val(data);
				reloadTable();
			}
		};
	ajax_(ajax_param);


}
/**
 * 初始化树菜单
 */
function initTree() {
	var path = getRootPatch();
	var uri = path + "/platform/dataDictManage/findConstantTypeTreeVal";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"trmnlBrandNm" : "",
			"qyType" : "brandName",
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			dataTree = new dTree("dataTree", path + "/plugin/dTree/img/");
			dataTree.add(0, -1, "数据字典类别", "javascript:treeClickAction('0','无');");

			// 得到树的json数据源
			var jsonData = eval('(' + data.menuLstJson + ')');
			_treeData = jsonData;
			// 遍历数据
			var gtmdlToolList = jsonData;
			for (var i = 0; i < gtmdlToolList.length; i++) {
				var _id = gtmdlToolList[i].constantTypeId;
				var _name = gtmdlToolList[i].constantTypeCName;
				var _parent = gtmdlToolList[i].parentTypeID;
				
				dataTree.add(_id, _parent, _name, "javascript:treeClickAction('" + _id + "','" + _name + "');");
			}
			//dom操作div元素内容
			$('#dataTreeDiv').empty();
			$('#dataTreeDiv').append("<input type='button' class='iconopen' value='展开 ' onclick='javascript:openTree(dataTree);'/>");
			$('#dataTreeDiv').append("<input type='button' class='iconclose' value='收起 ' onclick='javascript:closeTree(dataTree);'/>");
			$('#dataTreeDiv').append("<input type='text' id='treeName'/><input type='button' value='.' class='iconbtn' onclick='javascript:toSerach(dataTree);'/>");
			$('#dataTreeDiv').append(dataTree + "");
			//操作tree对象
//			dataTree.openAll();
		}
	}
	ajax_(ajax_param);
}

// 新建数据字典
function toAdd(){
	$('#constantItemIdHide').val('');
	$('.input').val("");
	$('#modifyId').val('');
	var typeId=$("#ipt_constantTypeId").val();
	if(typeId==""){
		typeId=0;
	}
	getParentTypeName(typeId);
	var id = -1;
	var typeOrItem = -1;
	$("#btnSave").unbind('click');
	$("#btnSave").bind("click", function() {
		checkIsExist("add",id,typeOrItem);
		
	});
	$("#btnClose").unbind();
	$("#btnClose").bind("click", function() {
		$('.input').val(""); 
		$('#divDataDictInfo').dialog('close');
	});
	_auType = 1;
	$('#divDataDictInfo').dialog('open');
}

function getParentTypeName(constantTypeId){
	var path=getRootName();
	var uri=path+"/platform/dataDictManage/getParentTypeName";
	var ajax_param={
			url:uri,
			type:"post",
			datdType:"json",
			async : false,

			data:{
				"constantTypeId":constantTypeId, 
				"t":Math.random()
			},
			error:function(){
				$.messager.alert("错误","ajax_error","error");
			},
			success:function(data){
				iterObj(data,"ipt");
				var parentTypeName=$("#ipt_parentTypeName").val();
				$("#ipt_constantTypeCName").val(parentTypeName);
				$("#ipt_parentTypeName").val(parentTypeName);
			}
		};
	ajax_(ajax_param);


}

function doAddDictItem(){
		var result=checkInfo('#divDataDictInfo');
		var path=getRootName();
		var typeId = $("#ipt_constantTypeId").val();
		var itemName = $("#ipt_constantItemName").val();
		var itemAlias = $("#ipt_constantItemAlias").val();
		if (result==true) {
			var uri=path+"/platform/dataDictManage/checkDataDictItemName";
			var ajax_param={
					url:uri,
					type:"post",
					dateType:"json",
					data:{
						"constantTypeId":typeId,
						"constantItemName" : itemName,
						"t" : Math.random()
			},
			error:function(){
				$.messager.alert("错误","ajax_error","error");
			},
			success:function(data){
				if(data == true){
					$.messager.alert("提示","该字典项名称在此类型中已经存在！","info");
				}else{
					if (itemAlias != "") {
						var uri=path+"/platform/dataDictManage/checkDataDictItemAlias";
						var ajax_param={
								url:uri,
								type:"post",
								dateType:"json",
								data:{
									"constantTypeId":typeId,
									"constantItemAlias" : itemAlias,
									"t" : Math.random()
						},
						error:function(){
							$.messager.alert("错误","ajax_error","error");
						},
						success:function(data){
							if(data == true){
								$.messager.alert("提示","该字典项别名在此类型中已经存在！","info");
							}else{
								var uri=path+"/platform/dataDictManage/addDataDictItem";
								var ajax_param={
										url:uri,
										type:"post",
										dateType:"json",
										data:{
											"constantTypeId" : typeId,
											"constantItemId" : $("#ipt_constantItemId").val(),
											"constantItemName" : itemName,
											"constantItemAlias" : itemAlias ,
											"remark":$("#ipt_remark1").val(),
											"showOrder" : $("#ipt_showOrder").val(),
											"t" : Math.random()
								},
								error:function(){
									$.messager.alert("错误","ajax_error","error");
								},
								success:function(data){
									if(data == true){
//										$.messager.alert("提示","任务新增成功！","info");
										$("#divDataDictInfo").dialog('close');
										reloadTable();
										initTree();
									}else{
										$.messager.alert("提示","字典项新增失败！","error");
										reloadTable();
									}
								}
								};
								ajax_(ajax_param);
							}
						}
						};
						ajax_(ajax_param);
					
					} else {
						var uri=path+"/platform/dataDictManage/addDataDictItem";
						var ajax_param={
								url:uri,
								type:"post",
								dateType:"json",
								data:{
									"constantTypeId" : typeId,
									"constantItemId" : $("#ipt_constantItemId").val(),
									"constantItemName" : itemName,
									"constantItemAlias" : itemAlias ,
									"remark":$("#ipt_remark1").val(),
									"t" : Math.random()
						},
						error:function(){
							$.messager.alert("错误","ajax_error","error");
						},
						success:function(data){
							if(data == true){
//								$.messager.alert("提示","任务新增成功！","info");
								$("#divDataDictInfo").dialog('close');
								reloadTable();
								initTree();
							}else{
								$.messager.alert("提示","字典项新增失败！","error");
								reloadTable();
							}
						}
						};
						ajax_(ajax_param);
					
					}
				}
			}
			};
			ajax_(ajax_param);
			
		}
}

function doDel(id,typeOrItem) {
	var path=getRootName();
	$.messager.confirm("提示","确定删除此条？",function(r){
		if (r == true) {
			var uri = path+"/platform/dataDictManage/delConstantItem";
			var ajax_param = {
				url : uri,
				type : "post",
				datdType : "json",
				data : {
					"id" : id,
					"typeOrItem":typeOrItem,
					"t" : Math.random()
				},
				error : function() {
					$.messager.alert("错误", "ajax_error", "error");
				},
				success : function(data) {
					if(data == false) {
	                  $.messager.alert("错误", "请先删除该节点下的子节点并确定该字典项类别未被使用", "error");
	                  reloadTable();
					} else {
	                  reloadTable();
	                  initTree();
	                }
					
				}
			};
			ajax_(ajax_param);
		}
	});
}

/*
 * 批量删除字典项
 */
function doBatchDel() {
//	alert("typeOrItem=== "+$("#typeOrItem").val());
	var typeOrItem=$("#typeOrItem").val();
	var path=getRootName();
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
		$.messager.confirm("提示","确定删除所选中项？",function(r){
			if (r == true) {
				var uri = path+"/platform/dataDictManage/delConstantItems?ids=" + ids+"&typeOrItem="+typeOrItem;
				var ajax_param = {
					url : uri,
					type : "post",
					datdType : "json",
					data : {
						"t" : Math.random()
					},
					success : function(data) {
						if(data == false) {
			            	$.messager.alert("错误", "批量删除中断,请确定所有节点下无子节点并确定该字典类别未被使用", "error");
			            	reloadTable();
						} else {
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


//重置过滤条件
function resetForm() {
	$('#constantItemName').val('');
}

/*
 * 更新表格 
 */
function reloadTable() {
	var constantItemName = $("#constantItemName").val(); 
//	if(_currentNodeId==0){
//		$('#tblConstantTypeManagement').datagrid('options').queryParams = {
//			"constantItemName" : constantItemName
//		};
//	}else{
	$("#ipt_constantTypeId").val(_currentNodeId);
	$('#tblConstantTypeManagement').datagrid('options').queryParams = {
		"constantTypeId" : _currentNodeId,
		"constantItemName" : constantItemName
	};
//	}
	
	$('#tblConstantTypeManagement').datagrid('load');
	$('#tblConstantTypeManagement').datagrid('uncheckAll');
}


/*
 * 页面加载初始化表格
 */
function doInitTable() {
	var path = getRootName();
	
	$('#tblConstantTypeManagement').datagrid({
		iconCls : 'icon-edit',// 图标
		width : 'auto',
		height : 'auto',
		nowrap : false,
		striped : true,
		border : true,
		collapsible : false,// 是否可折叠的
		fit : true,// 自动大小dd
		url : path + '/platform/dataDictManage/loadDataDictionaryList',
		// sortName: 'code',
		// sortOrder: 'desc',
		remoteSort : false,
		idField : 'id',
		singleSelect : false,// 是否单选
		checkOnSelect : false,
		selectOnCheck : true,
		pagination : true,// 分页控件
		rownumbers : true,// 行号
		fitColumns:true,
		fit:true,
		toolbar : [ {
			text : '新增类型',
			iconCls : 'icon-add',
			handler : function() {
				toAddType();
			}
		},
		{
			text : '编辑类型',
			iconCls : 'icon-edit',
			handler : function() {
				toUpdateType(_currentNodeId);
			}
		},
		{
			text : '删除类型',
			iconCls : 'icon-cancel',
			handler : function() {
				doDelType(_currentNodeId);
			}
		},
//		{
//			text : '',
//			iconCls : 'datagrid-btn-separator'
////			handler : function() {
////				doBatchDel();
////			}
//		},
		'-',
		{
			text : '新增字典项',
			iconCls : 'icon-add',
			handler : function() {
				toAdd();
			}
		},
		 {
			text : '删除字典项',
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
//			{
//				field : 'id',
//				title : '编码',
//				align : 'center',
//				width : 90
//			},
			{
				field : 'constantItemName',
				title : '内容',
				align : 'center',
				width : 90
			},
			{
				field : 'constantItemAlias',
				title : '别名',
				align : 'center',
				width : 90
			},
			{
				field : 'remark',
				title : '描述',
				align : 'center',
				width : 150
			},
			{
				field : 'level',
				title : '级数',
				align : 'center',
				width : 50
			},
			{
				field : 'typeOrItem',
				title : '对象类型',
				align : 'center',
				width : 90,
				formatter : function(value, row, index) {
					if(value != null || "" !=value){
						$("#typeOrItem").val(value);
					}
				}
				
			},
			{
				field : 'constantTypeId',
				title : '上级编码',
				align : 'center',
				width : 90
			},
			{
				field : 'ids',
				title : '操作',
				width : 90,
				align : 'center',
				formatter : function(value, row, index) {
					return '<a style="cursor: pointer;" onclick="javascript:toShowInfo('
							+ row.id+','+row.typeOrItem
							+ ');"><img src="'
							+ path
							+ '/style/images/icon/icon_view.png" title="查看" /></a> &nbsp;<a style="cursor: pointer;" onclick="javascript:toUpdate('
							+ row.id+','+row.typeOrItem
							+ ');"><img src="'
							+ path
							+ '/style/images/icon/icon_modify.png" title="修改" /></a> &nbsp;<a style="cursor: pointer;" onclick="javascript:doDel('
							+ row.id+','+row.typeOrItem
							+ ');"><img src="'
							+ path
							+ '/style/images/icon/icon_delete.png" title="删除" /></a>';
				}
			} ] ]
	});
	$(window).resize(function() {
        $('#tblConstantTypeManagement').resizeDataGrid(0, 0, 0, 0);
    });
}


function loadConsTypeInfo(){
	var path=getRootName();
	var typeOrItem=$("#typeOrItem").val();
	var type='';
	if(typeOrItem==1){
		type=$('#modifyId').val();
	}
//	var uri=path+"/platform/dataDictTypeManage/toConstantTypeView?flag=choose&type="+type;
//	window.open(uri,"","height=550px,width=800px,left=500,top=200,resizable=no,scrollings=no,status=no,toolbar=no,menubar=no,location=no");
	parent.$('#popWin').window({
    	title:'新增字典项',
        width:600,
        height:460,
        minimizable:false,
        maximizable:false,
        collapsible:false,
        modal:true,
        href: getRootName() + "/platform/dataDictTypeManage/toConstantTypeView?flag=choose&type="+type
    });
}

function getConstantTypeName(){

	var constantTypeId=$("#ipt_constantTypeId").val();
//	alert("constantTypeId= "+constantTypeId);
	var path=getRootName();
	var uri=path+"/platform/dataDictManage/getConstantTypeName";
	var ajax_param={
		url:uri,
		type:"post",
		dateType:"json",
		data:{
			"constantTypeId" : constantTypeId,
			"t" : Math.random() 
		},
		error : function(){
			$.messager.alert("错误","ajax_error","error");
		},
		success : function(data){
			iterObj(data,"ipt");
		}
	};
	ajax_(ajax_param);		

}


/**
 * 修改数据字典项信息
 * @param userId
 * @return
 */
function toUpdate(id,typeOrItem){
	$('.input').val("");
	$('#modifyId').val(id);
	initUpdateVal(id,typeOrItem);
	$("#btnSave").unbind('click');
	$("#btnSave").bind("click", function() {
		checkIsExist("update",id,typeOrItem);
	});
	$("#btnClose").unbind();
	$("#btnClose").bind("click", function() {
		$('.input').val(""); 
		$('#divDataDictInfo').dialog('close');
	});
	$('#divDataDictInfo').dialog('open');
	
}

function initUpdateVal(id,typeOrItem){
	var path=getRootName();
	var uri=path+"/platform/dataDictManage/initDataDictItem";
	var ajax_param={
			url:uri,
			type:"post",
			datdType:"json",
			async : false,

			data:{
				"id":id,
				"typeOrItem":typeOrItem, 
				"t":Math.random()
			},
			error:function(){
				$.messager.alert("错误","ajax_error","error");
			},
			success:function(data){
				iterObj(data,"ipt");
				var parentTypeName=$("#ipt_parentTypeName").val();
				var constantItemName=$("#ipt_constantItemName").val();
				var remark=$("#ipt_remark").val();
				$("#ipt_constantTypeCName").val(parentTypeName);
				$("#ipt_remark1").val(remark);
				$("#ipt_constantItemId").val(data.constantItemId);
				$('#constantItemIdHide').val(data.constantItemId);
				$('#constantItemNameHide').val(data.constantItemName);
				$('#constantItemAliasHide').val(data.constantItemAlias);
			}
		};
	ajax_(ajax_param);
}


function doUpdateTask(id,typeOrItem){
	var result=checkInfo('#divDataDictInfo');
	var path=getRootName();
	var itemNameHide = $('#constantItemNameHide').val();
	var itemAliasHide = $('#constantItemAliasHide').val();
	var itemName = $("#ipt_constantItemName").val();
	var itemAlias = $("#ipt_constantItemAlias").val();
	var typeId = $("#ipt_constantTypeId").val();
	if (result==true) {
		if (itemName != itemNameHide) {
			var uri=path+"/platform/dataDictManage/checkDataDictItemName";
			var ajax_param={
					url:uri,
					type:"post",
					dateType:"json",
					data:{
						"constantTypeId":typeId,
						"constantItemName" : itemName,
						"t" : Math.random()
			},
			error:function(){
				$.messager.alert("错误","ajax_error","error");
			},
			success:function(data){
				if(data == true){
					$.messager.alert("提示","该字典项名称在此类型中已经存在！","info");
				}else{
					if (itemAlias != itemAliasHide) {
						if (itemAlias != "") {
							var uri=path+"/platform/dataDictManage/checkDataDictItemAlias";
							var ajax_param={
									url:uri,
									type:"post",
									dateType:"json",
									data:{
										"constantTypeId":typeId,
										"constantItemAlias" : itemAlias,
										"t" : Math.random()
							},
							error:function(){
								$.messager.alert("错误","ajax_error","error");
							},
							success:function(data){
								if(data == true){
									$.messager.alert("提示","该字典项别名在此类型中已经存在！","info");
								}else{
									var uri=path+"/platform/dataDictManage/editDataDictItem";
									var ajax_param={
											url:uri,
											type:"post",
											dateType:"json",
											data:{
												"id":id,
												"constantItemId":$("#ipt_constantItemId").val(),
												"constantTypeId":typeId,
												"constantItemName" : itemName,
												"constantItemAlias" : itemAlias,
												"remark":$("#ipt_remark1").val(),
												"showOrder":$("#ipt_showOrder").val(),
												"typeOrItem":typeOrItem,
												"t" : Math.random()
									},
									error:function(){
										$.messager.alert("错误","ajax_error","error");
									},
									success:function(data){
										if(data == true){
//											$.messager.alert("提示","修改成功！","info");
											$("#divDataDictInfo").dialog('close');
											reloadTable();
											initTree();
										}else{
											$.messager.alert("提示","修改失败！","error");
										}
									}
									};
									ajax_(ajax_param);
								}
							}
							};
							ajax_(ajax_param);
						
						} else {
							var uri=path+"/platform/dataDictManage/editDataDictItem";
							var ajax_param={
									url:uri,
									type:"post",
									dateType:"json",
									data:{
										"id":id,
										"constantItemId":$("#ipt_constantItemId").val(),
										"constantTypeId":typeId,
										"constantItemName" : itemName,
										"constantItemAlias" : itemAlias,
										"remark":$("#ipt_remark1").val(),
										"showOrder":$("#ipt_showOrder").val(),
										"typeOrItem":typeOrItem,
										"t" : Math.random()
							},
							error:function(){
								$.messager.alert("错误","ajax_error","error");
							},
							success:function(data){
								if(data == true){
//									$.messager.alert("提示","修改成功！","info");
									$("#divDataDictInfo").dialog('close');
									reloadTable();
									initTree();
								}else{
									$.messager.alert("提示","修改失败！","error");
								}
							}
							};
							ajax_(ajax_param);
						
						}
					} else {
						var uri=path+"/platform/dataDictManage/editDataDictItem";
						var ajax_param={
								url:uri,
								type:"post",
								dateType:"json",
								data:{
									"id":id,
									"constantItemId":$("#ipt_constantItemId").val(),
									"constantTypeId":typeId,
									"constantItemName" : itemName,
									"constantItemAlias" : itemAlias,
									"remark":$("#ipt_remark1").val(),
									"showOrder":$("#ipt_showOrder").val(),
									"typeOrItem":typeOrItem,
									"t" : Math.random()
						},
						error:function(){
							$.messager.alert("错误","ajax_error","error");
						},
						success:function(data){
							if(data == true){
//								$.messager.alert("提示","修改成功！","info");
								$("#divDataDictInfo").dialog('close');
								reloadTable();
								initTree();
							}else{
								$.messager.alert("提示","修改失败！","error");
							}
						}
						};
						ajax_(ajax_param);
					
					}
				}
			}
			};
			ajax_(ajax_param);
		} else {
			if (itemAlias != itemAliasHide) {
				if (itemAlias != "") {
					var uri=path+"/platform/dataDictManage/checkDataDictItemAlias";
					var ajax_param={
							url:uri,
							type:"post",
							dateType:"json",
							data:{
								"constantTypeId":typeId,
								"constantItemAlias" : itemAlias,
								"t" : Math.random()
					},
					error:function(){
						$.messager.alert("错误","ajax_error","error");
					},
					success:function(data){
						if(data == true){
							$.messager.alert("提示","该字典项别名在此类型中已经存在！","info");
						}else{
							var uri=path+"/platform/dataDictManage/editDataDictItem";
							var ajax_param={
									url:uri,
									type:"post",
									dateType:"json",
									data:{
										"id":id,
										"constantItemId":$("#ipt_constantItemId").val(),
										"constantTypeId":typeId,
										"constantItemName" : itemName,
										"constantItemAlias" : itemAlias,
										"remark":$("#ipt_remark1").val(),
										"showOrder":$("#ipt_showOrder").val(),
										"typeOrItem":typeOrItem,
										"t" : Math.random()
							},
							error:function(){
								$.messager.alert("错误","ajax_error","error");
							},
							success:function(data){
								if(data == true){
//									$.messager.alert("提示","修改成功！","info");
									$("#divDataDictInfo").dialog('close');
									reloadTable();
									initTree();
								}else{
									$.messager.alert("提示","修改失败！","error");
								}
							}
							};
							ajax_(ajax_param);
						}
					}
					};
					ajax_(ajax_param);
				} else {

					var uri=path+"/platform/dataDictManage/editDataDictItem";
					var ajax_param={
							url:uri,
							type:"post",
							dateType:"json",
							data:{
								"id":id,
								"constantItemId":$("#ipt_constantItemId").val(),
								"constantTypeId":typeId,
								"constantItemName" : itemName,
								"constantItemAlias" : itemAlias,
								"remark":$("#ipt_remark1").val(),
								"showOrder":$("#ipt_showOrder").val(),
								"typeOrItem":typeOrItem,
								"t" : Math.random()
					},
					error:function(){
						$.messager.alert("错误","ajax_error","error");
					},
					success:function(data){
						if(data == true){
//							$.messager.alert("提示","修改成功！","info");
							$("#divDataDictInfo").dialog('close');
							reloadTable();
							initTree();
						}else{
							$.messager.alert("提示","修改失败！","error");
						}
					}
					};
					ajax_(ajax_param);
				
				}
			} else {
				var uri=path+"/platform/dataDictManage/editDataDictItem";
				var ajax_param={
						url:uri,
						type:"post",
						dateType:"json",
						data:{
							"id":id,
							"constantItemId":$("#ipt_constantItemId").val(),
							"constantTypeId":typeId,
							"constantItemName" : itemName,
							"constantItemAlias" : itemAlias,
							"remark":$("#ipt_remark1").val(),
							"showOrder":$("#ipt_showOrder").val(),
							"typeOrItem":typeOrItem,
							"t" : Math.random()
				},
				error:function(){
					$.messager.alert("错误","ajax_error","error");
				},
				success:function(data){
					if(data == true){
//						$.messager.alert("提示","修改成功！","info");
						$("#divDataDictInfo").dialog('close');
						reloadTable();
						initTree();
					}else{
						$.messager.alert("提示","修改失败！","error");
					}
				}
				};
				ajax_(ajax_param);
			
			}
		}
	}
	
}

//查看详情
function toShowInfo(id,typeOrItem){
	$('.input').val("");
	setRead(id,typeOrItem);
	$("#btnClose2").unbind();
	$("#btnClose2").bind("click", function() {
		$('.input').val(""); 
		$('#divDictInfo').dialog('close');
	});
	$('#divDictInfo').dialog('open');
	
}

//初始化详情数据
function setRead(id,typeOrItem){
	var path=getRootName();
	var uri=path+"/platform/dataDictManage/initDataDictItem";
	var ajax_param={
			url:uri,
			type:"post",
			datdType:"json",
			async : false,

			data:{
				"id":id,
				"typeOrItem":typeOrItem, 
				"t":Math.random()
			},
			error:function(){
				$.messager.alert("错误","ajax_error","error");
			},
			success:function(data){
				iterObj(data,"lbl");
			}
		};
	ajax_(ajax_param);
}

//菜单定位
function toSerach(treedata){   
	var treeName=$("#treeName").val();
	if(treeName==""){
		_currentNodeId="";
		reloadTable();
	}else{
		var path=getRootName();
		var uri=path+"/platform/dataDictManage/searchTreeNodes";
		var ajax_param={
				url:uri,
				type:"post",
				dateType:"json",
				data:{
					"constantTypeCName":treeName,
					"t" : Math.random()
		},
		error:function(){
			$.messager.alert("错误","ajax_error","error");
		},
		success:function(data){
			iterObj(data,"ipt");
			var constantTypeId=$('#ipt_constantTypeIds').val();
			var treeIds=constantTypeId.split(",");
			for ( var int = 1; int < treeIds.length; int++) {
				var treeId=treeIds[int];
				treedata.openTo(treeId,true);
			}
			_currentNodeId=constantTypeId;
			reloadTable();
		}
		};
		ajax_(ajax_param);
	}
	
}



//数据字典标识失去焦点是触发
function checkIsExist(flag,id,typeOrItem){
	var itemId = $('#constantItemIdHide').val();
	var constantItemId = $('#ipt_constantItemId').val();
	if (itemId == '' || itemId != constantItemId) {
		var constantTypeId = $('#ipt_constantTypeId').val();
		if (constantTypeId == '') {
			$.messager.alert("提示","字典项类型不能为空！","info");
		} else {
			var path=getRootName();
			var uri=path+"/platform/dataDictManage/checkItemId";
			var ajax_param={
					url:uri,
					type:"post",
					dateType:"json",
					data:{
						"constantItemId":constantItemId,
						"constantTypeId":constantTypeId,
						"t" : Math.random()
			},
			error:function(){
				$.messager.alert("错误","ajax_error","error");
			},
			success:function(data){
				if(data == true){
					$.messager.alert("提示","该字典类型下字典项标识为"+constantItemId+"的已经存在！","info", function(e) {
					});
				}else{
					if(flag == "add"){
						doAddDictItem();
					}else{
						doUpdateTask(id,typeOrItem);
					}
				}
			}
			};
			var checkItemIDRs = checkItemID();
			if(checkItemIDRs==true){
				ajax_(ajax_param);
			}
		}
		
	} else {
		if(flag == "add"){
			doAddDictItem();
		}else{
			doUpdateTask(id,typeOrItem);
		}
	}
}

$.extend(
		$.fn.validatebox.defaults.rules,
		{ 
			//整数验证
			ptInteger : {
				validator : function(value) {
					return /^[0-9]*[1-9][0-9]*$/.test(value);
				},
				message : "字典标识必须为整数！"
			}
		})


function checkItemID(){
	var message ="字典标识必须为整数！";
	var constantItemId = $('#ipt_constantItemId').val();
	if (!(/^[0-9]*[1-9][0-9]*$/.test(constantItemId))) {
		$.messager.alert("提示", message, "info", function(e) {
			$("#ipt_constantItemId").val("");
			$("#ipt_constantItemId").focus();
		});
		return false;
	}
	return true;
}


//新增字典类型
function toAddType(){
	$('#flag').val('add');
	$('.input').val("");
	getParentTypeName(_currentNodeId);
	$("#btnSave3").unbind('click');
	$("#btnSave3").bind("click", function() {
		toAddDictType();
	});
	$("#btnClose3").unbind();
	$("#btnClose3").bind("click", function() {
		$('.input').val(""); 
		window.location.href = window.location.href;
		$('#divDataDictTypeInfo').dialog('close');
	});
	_auType = 1;
	$('#divDataDictTypeInfo').dialog('open');
}

function toAddDictType(){
	var result=checkInfo('#divDataDictTypeInfo');
	if(result){
		checkTypeName(-1);
	}
}

/**
 * 校验字典类型别名的唯一性
 */
function checkTypeName(constantTypeId){
	var path=getRootName();
	var uri=path+"/platform/dataDictTypeManage/checkTypeName";
    var ajax_param = {
        url: uri,
        type: "post",
        dateType: "json",
        data: {
            "constantTypeId": constantTypeId,
            "constantTypeName": $("#ipt_constantTypeName1").val(),
            "t": Math.random()
        },
        error: function(){
            $.messager.alert("错误", "ajax_error", "error");
        },
        success: function(data){
            if (true == data || "true" == data) {
                if(constantTypeId == -1){
					doAddDictType();
				}else{
					doUpdateType(constantTypeId);
				}
            }
            else {
                $.messager.alert("提示", "该字典类别名已存在！", "error");
            }
        }
    };
    ajax_(ajax_param);
}

function doAddDictType(){
	var result=checkInfo('#divDataDictTypeInfo');
	var path=getRootName();
	var uri=path+"/platform/dataDictTypeManage/addDataDictType";
	var parentId=0;
	var typeId = $("#ipt_constantTypeId").val();
	$("#ipt_parentTypeID").val(typeId);
	if($("#ipt_parentTypeID").val()!=null && $("#ipt_parentTypeID").val()!=""){
		parentId=$("#ipt_parentTypeID").val();
	}
	var ajax_param={
			url:uri,
			type:"post",
			dateType:"json",
			data:{
				"parentTypeID":parentId,
				"constantTypeName" : $("#ipt_constantTypeName1").val(),
				"constantTypeCName" : $("#ipt_constantTypeCName1").val(),
				"remark":$("#ipt_remark2").val(),
				"t" : Math.random()
	},
	error:function(){
		$.messager.alert("错误","ajax_error","error");
	},
	success:function(data){
		if(true==data || "true"==data || ""==data){
//			$.messager.alert("提示","任务新增成功！","info");
			$("#divDataDictTypeInfo").dialog('close');
			initTree();
		}else{
			$.messager.alert("提示","任务新增失败！","error");
		}
	}
	};
	if(result==true){
		ajax_(ajax_param);
	}
}

//加载字典类型
function loadParentTypeInfo(){
	var path=getRootName();
//	var uri=path+"/platform/dataDictTypeManage/toConstantTypeView?flag=choose";
//	window.open(uri,"","height=500px,width=800px,left=500,top=200,resizable=no,scrollings=no,status=no,toolbar=no,menubar=no,location=no");
	parent.$('#popWin').window({
    	title:'新增字典类型',
        width:630,
        height:480,
        minimizable:false,
        maximizable:false,
        collapsible:false,
        modal:true,
        href: getRootName() + "/platform/dataDictTypeManage/toConstantTypeView?flag=choose"
    });
}


/**
 * 修改数据字典类型信息
 * @param userId
 * @return
 */
function toUpdateType(constantTypeId){
	$('#flag').val('update');
	initUpdateTypeVal(constantTypeId);
	$("#btnSave3").unbind('click');
	$("#btnSave3").bind("click", function() {
		toUpdateDicType(constantTypeId);
	});
	$("#btnClose3").unbind();
	$("#btnClose3").bind("click", function() {
		$('.input').val(""); 
		 window.location.href = window.location.href;
		$('#divDataDictTypeInfo').dialog('close');
	});
	$('#divDataDictTypeInfo').dialog('open');
	
}

function initUpdateTypeVal(constantTypeId){
	var path=getRootName();
	var uri=path+"/platform/dataDictTypeManage/initDataDictType";
	var ajax_param={
			url:uri,
			type:"post",
			datdType:"json",
			async : false,

			data:{
				"constantTypeId":constantTypeId,
				"t":Math.random()
			},
			error:function(){
				$.messager.alert("错误","ajax_error","error");
			},
			success:function(data){
				iterObj(data,"ipt");
				var constantTypeName=$("#ipt_constantTypeName").val();
				var constantTypeCName=$("#ipt_constantTypeCName").val();
				var remark=$("#ipt_remark").val();
				$("#ipt_constantTypeName1").val(constantTypeName);
				$("#ipt_constantTypeCName1").val(constantTypeCName);
				$("#ipt_remark2").val(remark);
				
			}
		};
	ajax_(ajax_param);
}

function toUpdateDicType(constantTypeId){
	var result=checkInfo('#divDataDictTypeInfo');
	if(result == true){
		checkTypeName(constantTypeId);
	}
}
function doUpdateType(constantTypeId){
	var result=checkInfo('#divDataDictTypeInfo');
	var path=getRootName();
	var uri=path+"/platform/dataDictTypeManage/editDataDictType";
	var ajax_param={
			url:uri,
			type:"post",
			dateType:"json",
			data:{
				"constantTypeId":constantTypeId,
				"parentTypeID":$("#ipt_parentTypeID").val(),
				"constantTypeName" : $("#ipt_constantTypeName1").val(),
				"constantTypeCName" : $("#ipt_constantTypeCName1").val(),
				"remark":$("#ipt_remark2").val(),
				"t" : Math.random()
	},
	error:function(){
		$.messager.alert("错误","ajax_error","error");
	},
	success:function(data){
		if(true==data || "true"==data || ""==data){
//			$.messager.alert("提示","修改成功！","info");
			$("#divDataDictTypeInfo").dialog('close');
			initTree();
			reloadTable();
		}else{
			$.messager.alert("提示","修改失败！","error");
			reloadTable();
		}
	}
	};
	if(result==true){
		ajax_(ajax_param);
	}

}

/*
 * 删除字典类型
 */
function doDelType(constantTypeId) {
	var path=getRootName();
	$.messager.confirm("提示","确定删除此条？",function(r){
		if (r == true) {
			var uri = path+"/platform/dataDictTypeManage/delConstantType";
			var ajax_param = {
				url : uri,
				type : "post",
				datdType : "json",
				data : {
					"constantTypeId":constantTypeId,
					"t" : Math.random()
				},
				error : function() {
					$.messager.alert("错误", "ajax_error", "error");
				},
				success : function(data) {
					if(data == false) {
	                  $.messager.alert("错误", "请先删除该节点下的子节点并确定该字典项类别未被使用", "error");
	                  reloadTable();
					} else {
	                	_currentNodeId = 0;
	                  reloadTable();
	                  initTree();
	                }
					
				}
			};
			ajax_(ajax_param);
		}
	});
}
