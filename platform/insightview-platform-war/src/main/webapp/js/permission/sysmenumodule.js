$(document).ready(function() {
	// 初始化表格
	doInitTable();
	// 初始化下拉列表树
	initTree();
});

// 树数据
var _treeData = "";
// 打开树层次数
var _treeOpenCount = 0;

var _currentNodeId = -1;
var _currentNodeName = "";
var _parID = -1;
var _currentMenuLevel = 1;

function treeClickAction(id, name) {
	_currentNodeId = id;
	_currentNodeName = name;
	reloadTable();
	
}

/**
 * 验证表单信息
 * 
 * @author 武林
 */
function checkForm() {
	return checkInfo('#tblMenuInfo');
}

/**
 * 重置表单
 * 
 * @param pControlId:表单容器ID
 */
function resetFormPrivate(pControlId) {
	resetForm(pControlId);
}
function treeClickAction(controlId, showId, showVal, menuLevel) {
	$("#" + controlId).val(showVal);
	$("#" + controlId).attr("alt", showId);
	_currentNodeId = showId;
	_currentMenuLevel = menuLevel;
	_currentNodeName = showVal;
	var item = document.getElementById("combdtree");
	if (item) {
		item.style.display = 'none';
	}
	reloadTable();
}
/**
 * 隐藏tree层
 */
function hiddenDTreeSetValEasyUiTemp(divId, controlId, showId, showVal, menuLevel) {
	if(showId === '301'){
		$.messager.alert("错误", "不能选择首页为上级菜单", "error");
		return;
	}
	$("#ipt_menuLevel").val(menuLevel);
	hiddenDTreeSetValEasyUi(divId, controlId, showId, showVal);
}

/**
 * 选择菜单事件
 */
function doChoseParentMenu() {
	if (0 == _treeOpenCount) {
		++_treeOpenCount;
		var path = getRootPatch();
		dataTreeTwo = new dTree("dataTreeTwo", path + "/plugin/dTree/img/");
		dataTreeTwo.add(0, -1, "选择上级菜单", "");

		// 得到树的json数据源
		var datas = _treeData;
		// 遍历数据
		var gtmdlToolList = datas;
		for (var i = 0; i < gtmdlToolList.length; i++) {
			var _id = gtmdlToolList[i].menuId;
			var _nameTemp = gtmdlToolList[i].menuName;
			var _parent = gtmdlToolList[i].parentMenuID;
			var _menuLevel = gtmdlToolList[i].menuLevel+1;
			dataTreeTwo.add(_id, _parent, _nameTemp,
					"javascript:hiddenDTreeSetValEasyUiTemp('divChoseMenu','ipt_parentMenuID','"
							+ _id + "','" + _nameTemp + "','"+_menuLevel+"');");
		}
		$('#dataTreeDivs').empty();
		$('#dataTreeDivs').append(dataTreeTwo + "");
	}
	$('#divChoseMenu').dialog('open');
}
/**
 * 初始化树菜单
 */
function initTree() {
	var path = getRootPatch();
	var uri = path + "/permissionSysMenuModule/findSysMenuTreeVal";
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
			dataTree.add(0, -1, "选择上级菜单", "");

			// 得到树的json数据源
			var datas = eval('(' + data.menuLstJson + ')');
			_treeData = datas;
			// 遍历数据
			var gtmdlToolList = datas;
			for (var i = 0; i < gtmdlToolList.length; i++) {
				var _id = gtmdlToolList[i].menuId;
				var _nameTemp = gtmdlToolList[i].menuName;
				var _parent = gtmdlToolList[i].parentMenuID;
				var _menuLevel = gtmdlToolList[i].menuLevel+1;
				dataTree.add(_id, _parent, _nameTemp,
						"javascript:treeClickAction('selFilterParentId','"
								+ _id + "','" + _nameTemp + "','"+ _menuLevel +"');");
				// dataTree.add(_id, _parent, _name,
				// "javascript:treeClickAction('" + _id + "','" + _name +
				// "');");
			}
			$('#dataTreeDiv').empty();
			//dom操作div元素内容
			$('#dataTreeDiv').append("<input type='button' class='iconopen' value='展开 ' onclick='javascript:openTree(dataTree);'/>");
			$('#dataTreeDiv').append("<input type='button' class='iconclose' value='收起 ' onclick='javascript:closeTree(dataTree);'/>");
			$('#dataTreeDiv').append("<input type='text' id='treeName'/><input type='button' value='.' class='iconbtn' onclick='javascript:toSerach(dataTree);'/>");
			$('#dataTreeDiv').append(dataTree + "");
			
			//操作tree对象   
			dataTree.openAll();
		}
	}
	ajax_(ajax_param);
}

/**
 * 更新菜单
 */
function doUpdate() {
	if (checkForm()) {
		var path = getRootName();
		var uri = path + "/permissionSysMenuModule/updateSysMenu";
		var ajax_param = {
			url : uri,
			type : "post",
			datdType : "json",
			data : {
				"menuId" : $("#ipt_menuId").val(),
				"menuName" : $("#ipt_menuName").val(),
				"menuLevel" : $("#ipt_menuLevel").val(),
				"parentMenuID" : $("#ipt_parentMenuID").attr("alt"),
				"menuClass" : $("#ipt_menuClass").val(),
				"showOrder" : $("#ipt_showOrder").val(),
				"linkURL" : $("#ipt_linkURL").val(),
				"icon" : $("#ipt_icon").val(),
				"descr" : $("#ipt_descr").val(),
				"t" : Math.random()
			},
			error : function() {
				$.messager.alert("错误", "ajax_error", "error");
			},
			success : function(data) {
				if (true == data || "true" == data) {
					reloadTable();
					initTree();
					_treeOpenCount = 0;

					$('#divAddMenu').dialog('close');
				} else {
					$.messager.alert("提示", "菜单修改失败！", "error");
					reloadTable();
				}
			}
		}
		ajax_(ajax_param);
	}
}

function initUpdateVal(menuId, menuName) {
	var path = getRootName();
	var uri = path + "/permissionSysMenuModule/findSysMenu";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"menuId" : menuId,
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			iterObj(data, "ipt");
			$("#ipt_parentMenuID").attr("alt", data.parentMenuID);

			if (null != menuName && 'null' != menuName) {
				$("#ipt_parentMenuID").val(menuName);
			} else {
				$("#ipt_parentMenuID").val('');
			}
		}
	}
	ajax_(ajax_param);
}
/**
 * 打开更新层
 * 
 * @param menuId:菜单ID
 */
function toUpdate(menuId, menuName) {
	initUpdateVal(menuId, menuName);

	$("#btnSave").unbind("click");
	$("#btnSave").bind("click", function() {
		doUpdate();
	});
	$("#btnUpdate").unbind();
	$("#btnUpdate").bind("click", function() {
		// initUpdateVal(menuId, menuName);
		//window.location.href = window.location.href;
		$('#divAddMenu').dialog('close');
	});

	$('#divAddMenu').dialog('open');
}

/**
 * 删除菜单
 * 
 * @author 武林
 */
function doDel(menuId) {
	var path = getRootName();
	$.messager.confirm("提示", "确定删除此菜单吗?", function(r) {
	if (r == true) {
		
		var uri = path + "/permissionSysMenuModule/delSysMenuModule";
		var ajax_param = {
			url : uri,
			type : "post",
			datdType : "json",
			data : {
				"menuId" : menuId,
				"t" : Math.random()
			},
			error : function() {
				$.messager.alert("错误", "ajax_error", "error");
			},
			success : function(data) {
				if (true == data || "true" == data) {
					initTree();
					_treeOpenCount = 0;
					reloadTable();
				} else {
					 $.messager.alert("错误", "此菜单已被用户使用，不能删除！", "error");
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
	var checkedItems = $('[name=menuId]:checked');
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
						+ "/permissionSysMenuModule/batchdelSysMenuModule?ids="
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
							initTree();
							_treeOpenCount = 0;
							reloadTable();
						} else {
							$.messager.alert("错误", "此菜单已被用户使用，不能删除！", "error");
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
 * 
 * @author 武林
 */
function toAdd() {
	resetFormPrivate('tblMenuInfo');

	$("#btnSave").unbind("click");
	$("#btnSave").bind("click", function() {
		doAdd();
	});
	$("#btnUpdate").unbind();
	$("#btnUpdate").bind("click", function() {
		// resetFormPrivate('tblMenuInfo');
		window.location.href = window.location.href;
		$('#divAddMenu').dialog('close');
	});
	if(_currentNodeId == -1 || _currentNodeId == 0){
		$("#ipt_parentMenuID").val("");
		_currentNodeId=0;
		$("#ipt_parentMenuID").attr("alt",_currentNodeId);
	}else{
		$("#ipt_parentMenuID").val(_currentNodeName);
		$("#ipt_parentMenuID").attr("alt",_currentNodeId);
		$("#ipt_menuLevel").val(_currentMenuLevel);
	}
	
	$('#divAddMenu').dialog('open');
}

/**
 * 新增菜单
 * 
 * @author 武林
 */
function doAdd() {
	if (checkForm()) {
		var path = getRootName();
		var uri = path + "/permissionSysMenuModule/addSysMenuModule";
		var ajax_param = {
			url : uri,
			type : "post",
			datdType : "json",
			data : {
				"menuName" : $("#ipt_menuName").val(),
				"menuLevel" : $("#ipt_menuLevel").val(),
				"parentMenuID" : $("#ipt_parentMenuID").attr("alt"),
				"menuClass" : $("#ipt_menuClass").val(),
				"showOrder" : $("#ipt_showOrder").val(),
				"linkURL" : $("#ipt_linkURL").val(),
				"icon" : $("#ipt_icon").val(),
				"descr" : $("#ipt_descr").val(),
				"t" : Math.random()
			},
			error : function() {
				$.messager.alert("错误", "ajax_error", "error");
			},
			success : function(data) {
				if (true == data || "true" == data) {
					$('#divAddMenu').dialog('close');
					initTree();
					_treeOpenCount = 0;
					reloadTable();
				} else {
					$.messager.alert("提示", "菜单新增失败！", "error");
				}

			}
		};
		ajax_(ajax_param);
	}
}

/**
 * 更新表格
 * 
 * @author 武林
 */
function reloadTable() {
	var txtFilterMenuName = $("#txtFilterMenuName").val();
	var selFilterMenuLevel = $("#selFilterMenuLevel").val();
	var selFilterParentMenu = $("#selFilterParentId").attr("alt");
	$('#tblSysMenuodule').datagrid('options').queryParams = {
		"menuNameFilter" : txtFilterMenuName,
		"menuLevelFilter" : selFilterMenuLevel,
		"parentMenuIDFilter" : selFilterParentMenu,
		"parID" : _parID
	};
	reloadTableCommon_1('tblSysMenuodule');
}

function reloadTableCommon_1(dataGridId) {
	$('#' + dataGridId).datagrid('load');
	$('#' + dataGridId).datagrid('uncheckAll');
}

/**
 * 页面加载初始化表格
 * 
 * @author 武林
 */
function doInitTable() {
	var path = getRootName();
	$('#tblSysMenuodule')
			.datagrid(
					{
						border : true,
						collapsible : false,// 是否可折叠的
						fit : true,// 自动大小
						fitColumns:true,
						url : path
								+ '/permissionSysMenuModule/listSysMenuModule',
						// sortName: 'code',
						// sortOrder: 'desc',
						remoteSort : false,
						idField : 'menuId',
						singleSelect : false,// 是否单选
						checkOnSelect : false,
						selectOnCheck : true,
						pagination : true,// 分页控件
						rownumbers : true,// 行号
						toolbar : [ {
							text : '添加',
							iconCls : 'icon-add',
							handler : function() {
								toAdd();
							}
						}, {
							text : '删除',
							iconCls : 'icon-cancel',
							handler : function() {
								doBatchDel();
							}
						}  ],
						columns : [ [
								{
									field : 'menuId',
									checkbox : true
								},
								{
									field : 'menuName',
									title : '菜单名称',
									width : 280
								},
								/*{
									field : 'menuLevel',
									title : '菜单级别',
									width : 80,
									formatter : function(value, row, index) {
										if (0 == row.menuLevel) {
											return "一级菜单";
										}
										if (1 == row.menuLevel) {
											return "二级菜单";
										}
										if (2 == row.menuLevel) {
											return "三级菜单";
										}
										if (3 == row.menuLevel) {
											return "四级菜单";
										}
									}
								},*/
								{
									field : 'parentSysMenu',
									title : '上级菜单',
									width : 250,
									formatter : function(value, pSysMenu) {
										if (null != pSysMenu) {
											return pSysMenu.parentSysMenu.menuName;
										}
									}

								},
								{
									field : 'linkURL',
									title : '菜单访问地址',
									width : 200,
								},
								{
									field : 'menuIds',
									title : '操作',
									width : 100,
									align : 'center',
									formatter : function(value, row, index) {
										var to = "&quot;" + row.menuId
												+ "&quot;,&quot;"
												+ row.parentSysMenu.menuName
												+ "&quot;";

										
										return '<a style="cursor: pointer;" onclick="javascript:toShowInfo('
										+ to
										+ ');"><img src="'
										+ path
										+ '/style/images/icon/icon_view.png" title="查看" /></a> &nbsp;<a  style="cursor: pointer;" onclick="javascript:toUpdate('
										+ to
										+ ');"><img src="'
										+ path
										+ '/style/images/icon/icon_modify.png" alt="" title="修改"/></a> &nbsp;<a style="cursor: pointer;" onclick="javascript:doDel('
												+ row.menuId
												+ ');"><img src="'
												+ path
												+ '/style/images/icon/icon_delete.png" alt="" title="删除"></a>';
										
										
									}
								} ] ]
					});
	// 浏览器窗口大小变化时，datagrid表格自适应窗口大小变化
    $(window).resize(function() {
        $('#tblSysMenuodule').resizeDataGrid(0, 0, 0, 0);
       // alert('1111');
    });
}
// 查看详情
function toShowInfo(menuId, menuName) {
	$('.input').val("");
	setRead(menuId,menuName);
	$("#btnClose2").unbind();
	$("#btnClose2").bind("click", function() {
		$('.input').val("");
		$('#divModelInfo').dialog('close');
	});
	$('#divModelInfo').dialog('open');
}

// 详情信息
function setRead(menuId, menuName) {
	var path = getRootName();
	var uri = path + "/permissionSysMenuModule/findSysMenu";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		async : false,

		data : {
			"menuId" : menuId,
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			if(data.menuLevel==1)
			{
				data.menuLevel="一级菜单";
			}
			if(data.menuLevel==2)
			{
				data.menuLevel="二级菜单";
			}
			if(data.menuLevel==3)
			{
				data.menuLevel="三级菜单";
			}
			if(data.menuLevel==4)
			{
				data.menuLevel="四级菜单";
			}
			if(data.menuClass==1)
			{
				data.menuClass="常规菜单";
			}
			if (null != menuName && 'null' != menuName) {
				data.parentMenuID=menuName;
			} else {
				data.parentMenuID="";
			}
			iterObj(data, "lbl");
		}
	};
	ajax_(ajax_param);
}

//展开树
function openTree(treedata){
	treedata.openAll();
}
//收缩树
function closeTree(treedata){
	treedata.closeAll();
}

//菜单定位
function toSerach(treedata){
	var treeName=$("#treeName").val();
	var path=getRootName();
	var uri=path+"/permissionSysMenuModule/searchTreeNodes";
	var ajax_param={
			url:uri,
			type:"post",
			dateType:"json",
			data:{
				"menuName":treeName,
				"t" : Math.random()
	},
	error:function(){
		$.messager.alert("错误","ajax_error","error");
	},
	success:function(data){
		iterObj(data,"ipt");
		var parId=$('#ipt_parID').val();
		var treeIds=parId.split(",");
		for ( var int = 1; int < treeIds.length; int++) {
			var treeId=treeIds[int];
			treedata.openTo(treeId,true);
		}
		_parID=parId;
		reloadTable();
	}
	};
	ajax_(ajax_param);
	
	
}