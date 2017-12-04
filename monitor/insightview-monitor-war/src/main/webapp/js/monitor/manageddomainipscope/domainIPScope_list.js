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
			$('#txtScopeType').combobox({
		        editable : false,
		    });

			// 页面初始化加载表格
			doInitTable();
			// 初始化下拉列表树
			initTree();
		}
	);

var _treeData = "";
var _currentNodeId = -1;
var _currentNodeName = "";
var _nodeID = -1;
/**
 * 点击树
 */
function treeClickAction(id, name) {
//	console.log("click    id==="+id);
	if(id == 0){
		id = 1;
	}
	_currentNodeId = id;
	_currentNodeName = name;
//	console.log("click    _currentNodeId==="+_currentNodeId);
	reloadTable();
	
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
//			dataTree.add(0, -1, "选择管理域", "javascript:treeClickAction(null,'无');");
			
//			console.log("Json=="+data.menuLstJson);
			// 得到树的json数据源
			var jsonData = eval('(' + data.menuLstJson + ')');
			_treeData = jsonData;
			// 遍历数据
			var gtmdlToolList = jsonData;
			for (var i = 0; i < gtmdlToolList.length; i++) {
				var _id = gtmdlToolList[i].domainId;
				var _name = gtmdlToolList[i].domainName;
				var _parent = gtmdlToolList[i].parentId;
				
//				console.log("_id=="+_id+" ,_name=="+_name);
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

/**
 * 菜单定位
 */
function toSerach(treedata){
	var treeName=$("#treeName").val();
	var path=getRootName();
	var uri=path+"/monitor/domainipscope/searchTreeNodes";
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
		var parId= data.nodes;
		var treeIds=parId.split(",");
		for ( var i = 1; i < treeIds.length; i++) {
			var treeId=parseInt(treeIds[i]);
			treedata.openTo(treeId,true);
		}
		_nodeID=parId;
		reloadTable();
	}
	};
	ajax_(ajax_param);
	
}


/*
 * 页面加载初始化表格
 */
function doInitTable() {
	var path = getRootName();
	
	$('#tblDomainIPScope').datagrid({
		iconCls : 'icon-edit',// 图标
		width : 'auto',
		height : 'auto',
		nowrap : false,
		striped : true,
		border : true,
		collapsible : false,// 是否可折叠的
		fit : true,// 自动大小dd
		url : path + '/monitor/domainipscope/listDomainIPScope',
		remoteSort : false,
		idField : 'fldId',
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
				field : 'scopeID',
				checkbox : true
			},
			
			{
				field : 'domainName',
				title : '管理域名称',
				align : 'center',
				width : 120
			},
			{
				field : 'ip1',
				title : 'IP范围',
				align : 'center',
				width : 200,
				formatter:function(value,row){
					return row.ip1+' - '+row.ip2;
				} 
			},
			{
				field : 'scopeType',
				title : '范围类型',
				align : 'center',
				width : 60,
				formatter:function(value,row){
					var rtn = "";
					if(value=="1"){
						rtn = "子网";
					}else if(value=="2"){
						rtn = "IP范围";
					}	
					return rtn; 
				} 
			},
			{
				field : 'status',
				title : '状态',
				align : 'center',
				width : 60,
				formatter:function(value,row){
					var rtn = "";
					if(value=="1"){
						rtn = "有效";
					}else if(value=="2"){
						rtn = "无效";
					}	
					return rtn; 
				} 
			},
			{
				field : 'domainDescr',
				title : '管理域描述',
				align : 'center',
				width : 100
			},
			{
				field : 'scopeIDs',
				title : '操作',
				width : 60,
				align : 'center',
				formatter : function(value, row, index) {
					return '<a style="cursor: pointer;" onclick="javascript:doOpenDetail('
							+ row.scopeID
							+ ');"><img src="'
							+ path
							+ '/style/images/icon/icon_view.png" title="查看" /></a> &nbsp;<a style="cursor: pointer;" onclick="javascript:doOpenModify('
							+ row.scopeID
							+ ');"><img src="'
							+ path
							+ '/style/images/icon/icon_modify.png" title="修改" /></a> &nbsp;<a style="cursor: pointer;" onclick="javascript:doDel('
							+ row.scopeID
							+ ');"><img src="'
							+ path
							+ '/style/images/icon/icon_delete.png" title="删除" /></a>';
				}
			} ] ]
	});
	$(window).resize(function() {
        $('#tblDomainIPScope').resizeDataGrid(0, 0, 0, 0);
    });
}


/*
 * 更新表格 
 */
function reloadTable() {
	var startIPStr = $("#txtStartIPStr").val();
	if(startIPStr != null && startIPStr !=""){
		if(!isIP(startIPStr)){
			$.messager.alert("提示","请输入有效的起始IP!","error"); 
			return ;
		}
	}
	var scopeType = $("#txtScopeType").combobox('getValue');
	$('#tblDomainIPScope').datagrid('options').queryParams = {
		"ip1" : startIPStr,
		"scopeType" : scopeType,
		"domainID" : _currentNodeId,
		"node" : _nodeID
	};
	reloadTableCommon_1('tblDomainIPScope');
}

/**
 * 判断是否为合法IP
 */
function isIP(strIP) {
	var re = /^(\d+)\.(\d+)\.(\d+)\.(\d+)$/g //匹配IP地址的正则表达式
	if (re.test(strIP)) {
		if (RegExp.$1 < 256 && RegExp.$2 < 256 && RegExp.$3 < 256
				&& RegExp.$4 < 256)
			return true;
	}
	return false;
}

function reloadTableCommon_1(dataGridId) {
	$('#' + dataGridId).datagrid('load');
	$('#' + dataGridId).datagrid('uncheckAll');
}

/*
 * 打开查看页面
 */
function doOpenDetail(scopeID){
	parent.$('#popWin').window({
    	title:'管理域IP范围详情',
        width:600,
        height : 400,
        minimizable:false,
        maximizable:false,
        collapsible:false,
        modal:true,
        href: getRootName() + '/monitor/domainipscope/toShowDomainIPScopeDetail?scopeID='+scopeID
    });
}

/*
 * 打开新增页面
 */
function doOpenAdd(){
//	alert();
//	console.log("打开新增  _currentNodeId："+_currentNodeId);
	parent.$('#popWin').window({
    	title:'新增管理域IP范围',
        width:600,
        height : 380,
        minimizable:false,
        maximizable:false,
        collapsible:false,
        modal:true,
        href: getRootName() + '/monitor/domainipscope/toShowDomainIPScopeAdd'
    });
}

/*
 * 打开编辑页面
 */
function doOpenModify(scopeID){
	parent.$('#popWin').window({
    	title:'编辑管理域IP范围',
        width:600,
        height : 380,
        minimizable:false,
        maximizable:false,
        collapsible:false,
        modal:true,
        href: getRootName() + '/monitor/domainipscope/toShowDomainIPScopeModify?scopeID='+scopeID
    });
}

/*
 * 删除
 */
function doDel(scopeID){
	$.messager.confirm("提示","确定删除此管理域IP范围?",function(r){
		if (r == true) {
			var path = getRootName();
			var uri = path + "/monitor/domainipscope/delDomainIPScope";
			var ajax_param = {
				url : uri,
				type : "post",
				datdType : "json",
				data : {
					"scopeID" : scopeID,
					"t" : Math.random()
				},
				error : function() {
					$.messager.alert("错误", "ajax_error", "error");
				},
				success : function(data) {
					if (true == data || "true" == data) {
						$.messager.alert("提示", "此管理域IP范围删除成功！", "info");
					} else {
						$.messager.alert("提示", "此管理域IP范围删除失败！", "error");
					}
					reloadTable();
				}
			};
			ajax_(ajax_param);
		}
	});
}


/*
 * 批量删除
 */
function doBatchDel(){
	var path=getRootName();
	var checkedItems = $('[name=scopeID]:checked');
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
				var path = getRootName();
				var uri = path + "/monitor/domainipscope/delDomainIPScopes?scopeIDs="+ids;
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
						if (false == data.flag || "false" == data.flag) {
							$.messager.alert("提示", "管理域IP范围删除失败！", "error");
						} else {
							$.messager.alert("提示", "管理域IP范围删除成功！", "info");
						}
						reloadTable();
					}
				};
				ajax_(ajax_param);
			}
		});
	} else {
		$.messager.alert('提示', '没有任何选中项', 'error');
	}
}