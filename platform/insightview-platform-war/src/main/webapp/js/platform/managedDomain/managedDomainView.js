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
//		$('#tblManagedDomain').datagrid('hideColumn','typeOrItem');
		// 初始化下拉列表树
		initTree();
		initOrgTree();
	}
);

//树数据
var _treeData = "";

var _currentNodeId = -1;
var _currentNodeName = "";

function treeClickAction(id, name) {
	_currentNodeId = id;
	_currentNodeName = name;
	reloadTable();
	
}

/*
 * 更新表格 
 */
function reloadTable() {
//	console.log(_currentNodeId);
	if(_currentNodeId==null || _currentNodeId==0){
		_currentNodeName="所有管理域";
		_currentNodeId=1;
	}
	$("#ipt_parentDomainName").val(_currentNodeName);
	$("#ipt_parentDomainName").attr("alt",_currentNodeId);
	$("#ipt_parentId").val(_currentNodeId);
	var domainName = $("#domainName").val(); 
	var organizationId=$("#organizationName").attr("alt");
	$('#tblManagedDomain').datagrid('options').queryParams = {
		"parentId" : _currentNodeId,
		"domainName" : domainName,
		"organizationId" : organizationId
	};
	$('#tblManagedDomain').datagrid('load');
	$('#tblManagedDomain').datagrid('uncheckAll');
}


/**
 * 初始化树菜单
 */
function initTree() {
	var path = getRootPatch();
	var uri = path + "/platform/managedDomain/initManagedDomainTreeVal";
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
//			dataTree.add(0, -1, "根管理域", "javascript:treeClickAction(null,'无');");

			// 得到树的json数据源
			var jsonData = eval('(' + data.menuLstJson + ')');
			_treeData = jsonData;
			// 遍历数据
			var gtmdlToolList = jsonData;
			for (var i = 0; i < gtmdlToolList.length; i++) {
				var _id = gtmdlToolList[i].domainId;
				var _name = gtmdlToolList[i].domainName;
				var _parent = gtmdlToolList[i].parentId;
				dataTree.add(_id, _parent, _name, "javascript:treeClickAction('" + _id + "','" + _name + "');");
			}
			//dom操作div元素内容
			$('#dataTreeDiv').empty();
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


/*
 * 页面加载初始化表格
 */
function doInitTable() {
	var path = getRootName();
	
	$('#tblManagedDomain').datagrid({
		iconCls : 'icon-edit',// 图标
		width : 'auto',
		height : 'auto',
		nowrap : false,
		striped : true,
		border : true,
		collapsible : false,// 是否可折叠的
		fit : true,// 自动大小dd
		url : path + '/platform/managedDomain/loadManagedDomainList',
		// sortName: 'code',
		// sortOrder: 'desc',
		remoteSort : false,
		idField : 'domainId',
		singleSelect : false,// 是否单选
		checkOnSelect : false,
		selectOnCheck : true,
		pagination : true,// 分页控件
		rownumbers : true,// 行号
		fitColumns:true,
		fit:true,
		toolbar : [ {
			text : '新增',
			iconCls : 'icon-add',
			handler : function() {
				doOpenAdd();
			}
		},
		 {
			text : '删除',
			iconCls : 'icon-cancel',
			handler : function() {
				doBatchDel();
			}
		} ],
		columns : [ [
			{
				field : 'domainId',
				checkbox : true
			},
			
			{
				field : 'domainName',
				title : '管理域名称',
				align : 'center',
				width : 120
			},
			{
				field : 'domainAlias',
				title : '管理域别名',
				align : 'center',
				width : 120
			},
			{
				field : 'showOrder',
				title : '显示排序',
				align : 'center',
				width : 60
			},
			{
				field : 'organizationName',
				title : '所属组织',
				align : 'center',
				width : 120
			},
			{
				field : 'domainDescr',
				title : '管理域描述',
				align : 'center',
				width : 180
			},
			{
				field : 'parentDomainName',
				title : '上级管理域',
				align : 'center',
				width : 120
			},
			{
				field : 'level',
				title : '级数',
				align : 'center',
				width : 90
			},
			{
				field : 'domainIds',
				title : '操作',
				width : 90,
				align : 'center',
				formatter : function(value, row, index) {
					return '<a style="cursor: pointer;" onclick="javascript:doOpenDetail('
							+ row.domainId
							+ ');"><img src="'
							+ path
							+ '/style/images/icon/icon_view.png" title="查看" /></a> &nbsp;<a style="cursor: pointer;" onclick="javascript:doOpenModify('
							+ row.domainId
							+ ');"><img src="'
							+ path
							+ '/style/images/icon/icon_modify.png" title="修改" /></a> &nbsp;<a style="cursor: pointer;" onclick="javascript:doDel('
							+ row.domainId
							+ ');"><img src="'
							+ path
							+ '/style/images/icon/icon_delete.png" title="删除" /></a>';
				}
			} ] ]
	});
	$(window).resize(function() {
        $('#tblManagedDomain').resizeDataGrid(0, 0, 0, 0);
    });
}

function resetForm(){
	$('#domainName').val("");
	$('#organizationName').val("");
	$('#organizationName').attr("alt","-1");
}

function doDel(domainId) {
	var path=getRootName();
	$.messager.confirm("提示","确定删除此条？",function(r){
		if (r == true) {
			if(domainId == 1){
				$.messager.alert("提示","该管理域为系统自定义管理域，不能删除！","info");
			}else{
				var uri = path+"/platform/managedDomain/delManagedDomain";
				var ajax_param = {
					url : uri,
					type : "post",
					datdType : "json",
					data : {
						"domainId" : domainId,
						"t" : Math.random()
					},
					error : function() {
						$.messager.alert("错误", "ajax_error", "error");
					},
					success : function(data) {
						if(data == false) {
		                  $.messager.alert("错误", "被删管理域包含子管理域或该管理域已被使用，不能删除！", "error");
		                  reloadTable();
						} else {
		                  reloadTable();
		                  initTree();
		                  if(_currentNodeId == domainId){
		                	_currentNodeId = -1;
				            _currentNodeName = "";
		                  }
		                  
		                }
						
					}
				};
				ajax_(ajax_param);
			}
			
		}
	});
}

/*
 * 批量删除
 */
function doBatchDel() {
//	alert("typeOrItem=== "+$("#typeOrItem").val());
	var path=getRootName();
	var checkedItems = $('[name=domainId]:checked');
	var ids = null;
	var current = false;
	$.each(checkedItems, function(index, item) {
		if (null == ids) {
			ids = $(item).val();
			if(_currentNodeId == $(item).val()){
				current = true;
			}
		} else {
			ids += ',' + $(item).val();
			if(_currentNodeId == $(item).val()){
				current = true;
			}
		}
	});
	if (null != ids) {
		$.messager.confirm("提示","确定删除所选中项？",function(r){
			if (r == true) {
				var uri = path+"/platform/managedDomain/delManagedDomains?domainIds=" + ids;
				var ajax_param = {
					url : uri,
					type : "post",
					datdType : "json",
					data : {
						"t" : Math.random()
					},
					success : function(data) {
						if(data == false) {
			            	$.messager.alert("错误", "被删管理域包含子管理域或该管理域已被使用，不能删除！", "error");
			            	reloadTable();
						} else {
							reloadTable();
							initTree();
			                if(current){
			                   _currentNodeId = -1;
					          _currentNodeName = "";
			                }
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
 * 初始化单位列表
 */
var _orgTreeData = "";
var _treeOpenCount = 0;
function initOrgTree() {
	var path = getRootPatch();
	var uri = path + "/permissionOrganization/findOrganizationLst";
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
			dataOrgTree = new dTree("dataOrgTree", path + "/plugin/dTree/img/");
			dataOrgTree.add(0, -1, "选择所属单位", "");

			// 得到树的json数据源
			var datas = eval('(' + data.organizationLstJson + ')');
			_orgTreeData = datas;
			// 遍历数据
			var gtmdlToolList = datas;
			for (var i = 0; i < gtmdlToolList.length; i++) {
				var _id = gtmdlToolList[i].organizationID;
				var _nameTemp = gtmdlToolList[i].organizationName;
				var _parent = gtmdlToolList[i].parentOrgID;

				dataOrgTree.add(_id, _parent, _nameTemp,
						"javascript:hiddenDTreeSetVal('organizationName','"
								+ _id + "','" + _nameTemp + "');");
			}
			$('#dataOrgTreeDiv').append(dataOrgTree + "");
		}
	}
	ajax_(ajax_param);
}

//菜单定位
function toSerach(treedata){
	var treeName=$("#treeName").val();
	if(treeName==""){
		_currentNodeId=treeName;
		reloadTable();
	}else{
		var path=getRootName();
		var uri=path+"/platform/managedDomain/searchTreeNodes";
		var ajax_param={
				url:uri,
				type:"post",
				dateType:"json",
				data:{
					"domainName":treeName,
					"t" : Math.random()
		},
		error:function(){
			$.messager.alert("错误","ajax_error","error");
		},
		success:function(data){
			iterObj(data,"ipt");
			var parId=$('#ipt_parentId').val();
			var treeIds=parId.split(",");
			for ( var int = 1; int < treeIds.length; int++) {
				var treeId=treeIds[int];
				treedata.openTo(treeId,true);
			}
			_currentNodeId=parId;
			reloadTable();
		}
		};
		ajax_(ajax_param);
		
	}
	
	
}





/**
 * 打开查看管理域详情页面
 * @param userId
 * @return
 */
function doOpenDetail(domainId){
	//查看配置项页面
	parent.$('#popWin').window({
    	title:'管理域详情',
        width:600,
        height:400,
        minimizable:false,
        maximizable:false,
        collapsible:false,
        modal:true,
        href: getRootName() + '/platform/managedDomain/toShowManageDetail?domainId='+domainId
    });
}

/**
 * 打开编辑管理域页面
 * @param userId
 * @return
 */
function doOpenModify(domainId){
	//查看配置项页面
	parent.$('#popWin').window({
    	title:'编辑管理域',
        width:600,
        height:400,
        minimizable:false,
        maximizable:false,
        collapsible:false,
        modal:true,
        href: getRootName() + '/platform/managedDomain/toShowManageModify?domainId='+domainId
    });
}

/**
 * 打开新增管理域页面
 * @param userId
 * @return
 */
function doOpenAdd(){
	//查看配置项页面
	parent.$('#popWin').window({
    	title:'新增管理域',
        width:600,
        height:400,
        minimizable:false,
        maximizable:false,
        collapsible:false,
        modal:true,
        href: getRootName() + '/platform/managedDomain/toShowManageAdd'
    });
}