	var dataTree = new dTree("dataTree",  getRootPatch() + "/plugin/dTree/img/");

$(document).ready(function() {

	doInitTable();
	//initTree();
	initsyncTree(null);
	initDeptTree();
	//initOrgTree();
	doInitLRselect();
});

//初始化导出列转移
function doInitLRselect() {
	$.fn.LRSelect("selLeft", "selRight", "img_L_AllTo_R", "img_L_To_R",
			"img_R_To_L", "img_R_AllTo_L");
	$.fn.UpDownSelOption("imgUp", "imgDown", "selRight");
	LRSelect.moveAll( "selLeft", "selRight", true);  
}
/**
 * 初始化异步树
 */
function initsyncTree(parentOrgID){
	var path = getRootPatch();
	var orgTreeImgPath = path + "/plugin/dTree/img/tree_company.png";
	var ajax_param = {
			url : path + "/permissionOrganization/findSyncOrganizationList",
			type : "post",
			datdType : "json",
			data : {
				"t" : Math.random(),
				"parentOrgID":parentOrgID
			},
			error : function() {
				$.messager.alert("错误", "ajax_error", "error");
			},
			success : function(data) {
				//dataTree = new dTree("dataTree", path + "/plugin/dTree/img/");
				//dataTree.add(0, -1,"单位组织", "javascript:treeClickAction('0','无');");
				if(parentOrgID==null){
					dataTree.add(0, -1,"单位组织", "javascript:treeClickAction('0','无');");
				}
				// 得到树的json数据源
				var datas = eval('(' + data.organizationLstJson + ')');
				var _id, _nameTemp, _parent;
				for (var i = 0; i < datas.length; i++) {
					_id = datas[i].organizationID;
					_nameTemp = datas[i].organizationName;
					_parent = datas[i].parentOrgID=='O0'?'0':datas[i].parentOrgID;

					dataTree.add(_id, _parent, _nameTemp, "javascript:treeClickAction('" + _id + "','" + _nameTemp + "');","单位","",orgTreeImgPath,orgTreeImgPath);
				}
			$('#dataTreeDiv').empty()
								 //.append("<input type='button' class='iconopen' value='展开 ' onclick='javascript:openTree(dataTree);'/>")
								 //.append("<input type='button' class='iconclose' value='收起 ' onclick='javascript:closeTree(dataTree);'/>")
								 //.append("<input type='text' id='treeName'/><input type='button' class='iconbtn' value='.' onclick='javascript:toSerach(dataTree);'/>")
								 .append(dataTree + "");
			dataTree.openAll();
			}
		};
		ajax_(ajax_param);
}

/**
 * 初始化树菜单
 */
function initTree(){
	var path = getRootPatch();
	var uri = path + "/permissionDepartment/findOrgAndDeptVal";
	var partmentTreeImgPath = path + "/plugin/dTree/img/tree_department.png";
	var orgTreeImgPath = path + "/plugin/dTree/img/tree_company.png";
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
			dataTree.add(0, -1, "单位部门", "javascript:treeClickAction(null,'无');");

			// 得到树的json数据源
			var jsonData = eval('(' + data.menuLstJson + ')');
			// 遍历数据
			var gtmdlToolList = jsonData;
			for (var i = 0; i < gtmdlToolList.length; i++) {
				var _id = gtmdlToolList[i].id;
				var _name = gtmdlToolList[i].name;
				var _parent = gtmdlToolList[i].parentId;
				if(_id.indexOf("O") == 0) {
					dataTree.add(_id, _parent, _name, "javascript:treeClickAction('" + _id + "','" + _name + "');","单位","",orgTreeImgPath,orgTreeImgPath);
				} else {
					dataTree.add(_id, _parent, _name, "javascript:treeClickAction('" + _id + "','" + _name + "');","部门","",partmentTreeImgPath,partmentTreeImgPath);
				}
			}
			// dom操作div元素内容
			$('#dataTreeDiv').empty();
			$('#dataTreeDiv').append("<input type='button' class='iconopen' value='展开 ' onclick='javascript:openTree(dataTree);'/>");
			$('#dataTreeDiv').append("<input type='button' class='iconclose' value='收起 ' onclick='javascript:closeTree(dataTree);'/>");
			$('#dataTreeDiv').append("<input type='text' id='treeName'/><input type='button' class='iconbtn' value='.' onclick='javascript:toSerach(dataTree);'/>");
			$('#dataTreeDiv').append(dataTree + "");
			// 操作tree对象
// dataTree.openAll();
		}
	};
	ajax_(ajax_param);
}

var _currentNodeId = -1;
var _currentNodeName = "";
var _nodeID = -1;
/**
 * 点击树
 */
function treeClickAction(id, name) {
	_currentNodeId = id;
	_currentNodeName = name;
	_nodeID = -1;
	reloadTable();
	$('#treeType_id').val(_currentNodeId);
	$('#nodeID_id').val(_nodeID);
	initsyncTree(id);
	
}

/**
 * 展开树
 */
function openTree(treedata){
	treedata.openAll();
}
/**
 * 收缩树
 */
function closeTree(treedata){
	treedata.closeAll();
}

/**
 * 树定位
 */
function toSerach(treedata){
	var treeName=$("#treeName").val();
	var path=getRootName();
	var uri=path+"/permissionDepartment/searchTreeNodes";
	var ajax_param={
			url:uri,
			type:"post",
			dateType:"json",
			data:{
				"deptName":treeName,
				"t" : Math.random()
	},
	error:function(){
		$.messager.alert("错误","ajax_error","error");
	},
	success:function(data){
// console.log(data);
		iterObj(data,"ipt");
		var nodeId=$('#ipt_nodeID').val();
		var treeIds=nodeId.split(",");
		for ( var int = 1; int < treeIds.length; int++) {
			var treeId="D"+treeIds[int];
			treedata.openTo(treeId,true);
		}
		_nodeID = nodeId;
		reloadTable();
	}
	};
	ajax_(ajax_param);
	
	
}

/**
 * 验证表单信息
 * 
 * @author 武林
 */
function checkForm() {
	var checkControlAttr = new Array('ipt_deptName', 'ipt_organizationID');
	var checkMessagerAttr = new Array('请输入部门名称 ！', '请选择所属单位！');
	return checkFormCommon(checkControlAttr, checkMessagerAttr);
}

/**
 * 重置查询表单
 * 
 * @param pControlId:表单容器ID
 */
function resetFormFilter(pControlId) {
	resetForm(pControlId);
	$("#selFilterParentId").attr('alt', '-1');
}

function resetFormAdd(pControlId) {
	resetForm(pControlId);
	$("#ipt_parentDeptID").attr('alt', '0');
	$("#ipt_organizationID").attr('alt', '');
}

/**
 * 选择部门树
 * 
 * @return
 */
function choseDeptTree() {
	initDeptTree();
	$('#divChoseParentDept').dialog('open');
}

/**
 * 选择部门领导树
 * 
 * @return
 */
function choseHeadTree(){
	var organizationID =  $("#ipt_organizationID").attr("alt");
	if(organizationID == ''){  
		organizationID= 0;
	}
	initHeadTree(organizationID);
	$('#divChoseHead').dialog('open');
}

/**
 * 初始化部门领导树
 * 
 * @param organizationID
 * @return
 */
function initHeadTree(organizationID){
		var path = getRootPatch();
		var uri = path + "/permissionDepartment/findHeadList";
		var ajax_param = {
			url : uri,
			type : "post",
			datdType : "json",
			data : {
				"t" : Math.random(),
				"organizationID" : organizationID
			},
			error : function() {
				$.messager.alert("错误", "ajax_error", "error");
			},
			success : function(data) {
				dataTreeHead = new dTree("dataTreeOrg", path
						+ "/plugin/dTree/img/");
				dataTreeHead.add(0, -1, "选择部门领导", "");

				// 得到树的json数据源
				var datas = eval('(' + data.headLstJson + ')');
				// 遍历数据
				var gtmdlToolList = datas;
				for (var i = 0; i < gtmdlToolList.length; i++) {
					var _id = gtmdlToolList[i].empId;
					var _nameTemp = gtmdlToolList[i].userName;
					var _parent = 0;

					dataTreeHead.add(_id, _parent, _nameTemp,
							"javascript:hiddenDTreeSetValEasyUi('divChoseHead','ipt_headOfDept','"
									+ _id + "','" + _nameTemp + "');");
				}
				$('#dataHeadTreeDiv').empty();
				$('#dataHeadTreeDiv').append(dataTreeHead + "");
			}
		};
		ajax_(ajax_param);
}

/**
 * 初始化部门树
 */
var _initDeptCount = 0;
function initDeptTree() {
	if (0 == _initDeptCount) {
		++_initDeptCount;
		var path = getRootPatch();
		var uri = path + "/permissionDepartment/findDeptLst";
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
				dataTreeOrg = new dTree("dataTreeOrg", path
						+ "/plugin/dTree/img/");
				dataTreeOrg.add(0, -1, "选择上级部门", "");

				// 得到树的json数据源
				var datas = eval('(' + data.deptLstJson + ')');
				// 遍历数据
				var gtmdlToolList = datas;
				for (var i = 0; i < gtmdlToolList.length; i++) {
					var _id = gtmdlToolList[i].deptId;
					var _nameTemp = gtmdlToolList[i].deptName;
					var _parent = gtmdlToolList[i].parentDeptID;

					dataTreeOrg.add(_id, _parent, _nameTemp,
							"javascript:hiddenDeptDTreeSetValEasyUi('divChoseParentDept','ipt_parentDeptID','"
									+ _id + "','" + _nameTemp + "');");
				}
				$('#dataParentDeptTreeDiv').empty();
				$('#dataParentDeptTreeDiv').append(dataTreeOrg + "");
			}
		};
		ajax_(ajax_param);
	}
}

function hiddenDeptDTreeSetValEasyUi(divId,controlId, showId,showVal) {
	$("#" + controlId).val(showVal);
	$("#" + controlId).attr("alt", showId);
	$("#" + divId).dialog('close');
//	console.log("tree.....");
	isShow();
}

/**
 * 上级单位组织
 * 
 * @return
 */
function doChoseParentOrg() {
	cancelRedBox("ipt_organizationID");
	if (0 == _treeOpenCount) {
		++_treeOpenCount;
		var path = getRootPatch();
		dataTreeTwo = new dTree("dataTreeTwo", path + "/plugin/dTree/img/");
		dataTreeTwo.add(0, -1, "选择上级单位", "");
		// 得到树的json数据源
		var datas = _treeData;

		// 遍历数据
		var gtmdlToolList = datas;
		for (var i = 0; i < gtmdlToolList.length; i++) {
			var _id = gtmdlToolList[i].organizationID;
			var _nameTemp = gtmdlToolList[i].organizationName;
			var _parent = gtmdlToolList[i].parentOrgID;

			dataTreeTwo.add(_id, _parent, _nameTemp,
					"javascript:hiddenDTreeSetValEasyUi('divChoseOrg','ipt_organizationID','"
							+ _id + "','" + _nameTemp + "');");
		}
		$('#dataTreeDivs').append(dataTreeTwo + "");
	}
	$('#divChoseOrg').dialog('open');
}

/**
 * 初始化单位组织树
 */
var _treeData = "";
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
			dataTree = new dTree("dataTree", path + "/plugin/dTree/img/");
			dataTree.add(0, -1, "选择上级单位", "");

			// 得到树的json数据源
			var datas = eval('(' + data.organizationLstJson + ')');
			_treeData = datas;
			// 遍历数据
			var gtmdlToolList = datas;
			for (var i = 0; i < gtmdlToolList.length; i++) {
				var _id = gtmdlToolList[i].organizationID;
				var _nameTemp = gtmdlToolList[i].organizationName;
				var _parent = gtmdlToolList[i].parentOrgID;

				dataTree.add(_id, _parent, _nameTemp,
						"javascript:hiddenDTreeSetVal('selFilterParentId','"
								+ _id + "','" + _nameTemp + "');");
			}
			$('#dataOrgTreeDiv').append(dataTree + "");

		}
	};
	ajax_(ajax_param);
}

/*
 * 打开DIV @author 武林
 */
function toAdd() {
	resetFormAdd('tblDeptAdd');
	
	$("#btnSave").unbind("click");
	$("#btnSave").bind("click", function() {
		doAdd();
	});
	$("#btnUpdate").unbind();
	$("#btnUpdate").bind("click", function() {
		window.location.href = window.location.href;
		$('#divDepartment').dialog('close');
	});
	if(_currentNodeId==null){
		_currentNodeName="";
		_currentNodeId=null;
	}
	var id = _currentNodeId+"";
	if( id.charAt(0) == "O"){
		var orgID =_currentNodeId.substring(1);
		$("#ipt_organizationID").val(_currentNodeName);
		$("#ipt_organizationID").attr("alt",orgID);
		
	}
	if(id.charAt(0) == "D"){
		var deptID =_currentNodeId.substring(1);
		$("#ipt_parentDeptID").val(_currentNodeName);
		$("#ipt_parentDeptID").attr("alt",deptID);
	}
	isShow();
	$('#divDepartment').dialog('open');
}

function doAdd(){
	var doName = "add";
	var parentDeptID = $("#ipt_parentDeptID").attr("alt");
	var result = checkInfo("#divDepartment");
	if(result == true){
		if(parentDeptID!=0){
			checkParentDeptBeforeAdd(doName);
		}else{
			checkBeforeAdd(doName);
		}
	}
}
/*
 * 新增单位组织 @author 武林
 */
function add() {
	var headOfDept = $("#ipt_headOfDept").val();
	if(headOfDept == ''){  
		headOfDept = 0;
	}else{
		headOfDept = $("#ipt_headOfDept").attr("alt");
	}
	if (checkForm()) {
		var path = getRootName();
		var uri = path + "/permissionDepartment/addDepartment";
		var ajax_param = {
			url : uri,
			type : "post",
			datdType : "json",
			data : {
				"deptName" : $("#ipt_deptName").val(),
				"organizationBean.organizationID" : $("#ipt_organizationID")
						.attr("alt"),
				"parentDeptID" : $("#ipt_parentDeptID").attr("alt"),
				"headOfDept" : headOfDept,
				"descr" : $("#ipt_descr").val(),
				"deptCode" : $("#ipt_deptCode").val(),
				"t" : Math.random()
			},
			error : function() {
				$.messager.alert("错误", "ajax_error", "error");
			},
			success : function(data) {
				if (true == data || "true" == data) {
					$('#divDepartment').dialog('close');
					initTree();
					_initDeptCount = 0;
					initDeptTree();
					reloadTable();
				} else {
					$.messager.alert("提示", "单位部门新增失败！", "error");
				}

			}
		};
		ajax_(ajax_param);
	}
}
/*
 * 更新表格
 */
function reloadTable() {
	var departmentName = $("#iptFilterDepartmentName").val();
	var orgName = $("#selFilterParentId").val();
	$('#tblDepartment').datagrid('options').queryParams = {
		"deptName" : departmentName,
		"organizationName" : orgName,
		"treeType" : _currentNodeId,
		"nodeID" : _nodeID
	};
	reloadTableCommon_1('tblDepartment');
}

function reloadTableCommon_1(dataGridId) {
	$('#' + dataGridId).datagrid('load');
	$('#' + dataGridId).datagrid('uncheckAll');
}

/*
 * 页面加载初始化表格
 */
function doInitTable() {
	var path = getRootName();
	$('#tblDepartment')
			.datagrid(
					{
						iconCls : 'icon-edit',// 图标
						width : 'auto',
						height : 'auto',
						nowrap : false,
						striped : true,
						border : true,
						collapsible : false,// 是否可折叠的
						fit : true,// 自动大小
						fitColumns:true,
						fitColumns:true,
						url : path + '/permissionDepartment/listDepartment',
						remoteSort : false,
//						idField : 'fldId',
						checkOnSelect : false,
						singleSelect : false,// 是否单选
						pagination : true,// 分页控件
						rownumbers : true,// 行号
						queryParams : {
							"organizationID" : -1
						},
						toolbar : [ {
							'text' : '新增',
							'iconCls' : 'icon-add',
							handler : function() {
								toAdd();
							}
						},
						{
							'text' : '删除',
							'iconCls' : 'icon-cancel',
							handler : function() {
								doBatchDel();
							}
						},
						{
							'text' : '导入',
							'iconCls' : 'icon-execl',
							handler : function() {
								doImport();
							}
						},
						{
							text : '导出',
							iconCls : 'icon-execl',
							handler : function() {
								toExport();
							}
						} ],
						columns : [ [
					             {
					            	 field : 'deptId',
					            	 checkbox : true
					            	 
					             },
								{
									field : 'deptName',
									title : '部门名称',
									width : 250
								},
								{
									field : 'deptCode',
									title : '部门编码',
									width : 100
								},
								{
									field : 'parentDeptName',
									title : '上级部门',
									width : 200,
									align : 'center',
								},
								{
									field : 'organizationName',
									title : '所属单位',
									width : 200,
									align : 'center',
									formatter : function(value, org) {
										return org.organizationBean.organizationName;
									}
								},
								{
									field : 'headName',
									title : '部门领导',
									width : 250,
									align : 'center',
								},
								{
									field : 'descr',
									title : '部门描述',
									width : 250
								},
								{
									field : 'deptIds',
									title : '操作',
									width : 200,
									align : 'center',
									formatter : function(value, row, index) {
										if(row.parentDept!=null){
											_parentDeptName = row.parentDept.deptName;
											var pdName = row.parentDept.deptName;
										}else{
											_parentDeptName = "";
											var pdName = "";
										}
										_orgName = row.organizationBean.organizationName;
										var to = "&quot;"
												+ row.deptId
												+ "&quot;,&quot;"
												+ pdName
												+ "&quot;,&quot;"
												+ row.organizationBean.organizationName
												+ "&quot;,&quot;"
												+ row.headName
												+ "&quot;";

										return  '<a style="cursor: pointer;" onclick="javascript:toView('
												+ to
												+ ');"><img src="'
												+ path
												+ '/style/images/icon/icon_view.png" alt="查看" /></a>&nbsp;<a style="cursor: pointer;" onclick="javascript:toUpdate('
												+ to
												+ ');"><img src="'
												+ path
												+ '/style/images/icon/icon_modify.png" alt="修改" /></a>&nbsp;<a style="cursor: pointer;" onclick="javascript:doDel('
												+ row.deptId
												+ ');"><img src="'
												+ path
												+ '/style/images/icon/icon_delete.png" alt="删除" /></a>';
									}
								} ] ]
					});
	// 浏览器窗口大小变化时，datagrid表格自适应窗口大小变化
    $(window).resize(function() {
        $('#tblDepartment').resizeDataGrid(0, 0, 0, 0);
    });
}
/**
 * 删除单位部门
 */
function doDel(deptID) {
	var path=getRootName();
	$.messager.confirm("提示","确定删除此条？",function(r){
		if (r == true) {
			var uri = path+"/permissionDepartment/delDepartment";
			var ajax_param = {
				url : uri,
				type : "post",
				datdType : "json",
				data : {
					"deptId" : deptID,
					"t" : Math.random()
				},
				error : function() {
					$.messager.alert("错误", "ajax_error", "error");
				},
				success : function(data) {
					if(data.result == "DelSuccess"){
						 //initTree();
						 _initDeptCount = 0;
							//initDeptTree();
						 _treeOpenCount = 0;
						reloadTable();
					} else {
						$.messager.alert("提示", data.result, "error");
					}
				}
			};
			ajax_(ajax_param);
		}
	});
}

/**
 * 批量删除
 */
function doBatchDel(){
	var path=getRootName();
	var checkedItems = $('[name=deptId]:checked');
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
				var uri = path+"/permissionDepartment/delDepartments?deptIds=" + ids;
				var ajax_param = {
						url : uri,
						type : "post",
						datdType : "json",
						data : {
						"t" : Math.random()
				},
				success : function(data) {
					if(data.flag == false) {
						$.messager.alert("错误", "被删部门"+data.deptName+"有子部门或员工或者被子网使用，部门删除失败！", "error");
						 //initTree();
						 _initDeptCount = 0;
							//initDeptTree();
						 _treeOpenCount = 0;
						reloadTable();
					} else {
						 //initTree();
						 _initDeptCount = 0;
							//initDeptTree();
						 _treeOpenCount = 0;
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
 * 到查看页面
 */
function toView(deptId){
	initViewVal(deptId);
	$("#btnClose").bind("click", function() {
		$('#viewDepartment').dialog('close');
	});
	$('#viewDepartment').dialog('open');
	
}

/**
 * 初始化查看页面
 */
function initViewVal(deptId){
	var path = getRootName();
	var uri = path + "/permissionDepartment/findDepartment";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"deptId" : deptId,
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			iterObj(data, "lbl");
			$("#lbl_parentDeptName").text(data.parentDeptName);
		}
	};
	ajax_(ajax_param);
}
var _parentDeptName = "";
var _orgName = "";

/**
 * 初始化更新页面
 */
function initUpdateVal(deptID, deptName, orgName,headName) {
	var path = getRootName();
	var uri = path + "/permissionDepartment/findDepartment";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"deptId" : deptID,
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			iterObj(data, "ipt");
			if (null != orgName && 'null' != orgName) {
				$("#ipt_organizationID").attr('alt',
						data.organizationBean.organizationID);
				$("#ipt_organizationID").val(orgName);
			} else {
				$("#ipt_organizationID").attr('alt', '');
				$("#ipt_organizationID").val('');
			}

			if (null != deptName && 'null' != deptName) {
				$("#ipt_parentDeptID").attr('alt', data.parentDeptID);
				$("#ipt_parentDeptID").val(data.parentDeptName);
			} else {
				$("#ipt_parentDeptID").attr('alt', 0);
				$("#ipt_parentDeptID").val('');
			}
			
			if (null != headName && 'null' != headName) {
				$("#ipt_headOfDept").attr('alt', data.headOfDept);
				$("#ipt_headOfDept").val(headName);
			} else {
				$("#ipt_headOfDept").attr('alt', 0);
				$("#ipt_headOfDept").val('');
			}
			isShow();
		}
	};
	ajax_(ajax_param);
}
/**
 * 到更新页面
 * 
 * @param organizationID
 */
function toUpdate(deptID, deptName, orgName,headName) {

	initUpdateVal(deptID, deptName, orgName,headName);
	$("#btnSave").unbind();
	$("#btnSave").bind("click", function() {
		doUpdate(deptID);
	});
	$("#btnUpdate").unbind();
	$("#btnUpdate").bind("click", function() {
// initUpdateVal(deptID, deptName, orgName,headName);
		$('#divDepartment').dialog('close');
	});
	$('#divDepartment').dialog('open');
}
/**
 * 更新部门
 */
function doUpdate(deptID){
	var doName = "update";
	var parentDeptID = $("#ipt_parentDeptID").attr("alt");
	var result = checkInfo("#divDepartment");
	if(result == true){
		if(parentDeptID ==0 || parentDeptID == ''){
			checkBeforeUpdate(deptID,doName);
		}else{
			checkParentDeptBeforeUpdate(deptID,doName);
		}
	}
}

function update() {
	if (checkForm()) {
		var path = getRootName();
		var uri = path + "/permissionDepartment/updateDepartment";
		var ajax_param = {
			url : uri,
			type : "post",
			datdType : "json",
			data : {
				"deptId" : $("#ipt_deptId").val(),
				"deptName" : $("#ipt_deptName").val(),
				"organizationBean.organizationID" : $("#ipt_organizationID")
						.attr("alt"),
				"parentDeptID" : $("#ipt_parentDeptID").attr("alt"),
				"headOfDept" : $("#ipt_headOfDept").attr("alt"),
				"descr" : $("#ipt_descr").val(),
				"deptCode" : $("#ipt_deptCode").val(),
				"t" : Math.random()
			},
			error : function() {
				$.messager.alert("错误", "ajax_error", "error");
			},
			success : function(data) {
				if (true == data || "true" == data) {
					$('#divDepartment').dialog('close');

					_initDeptCount = 0;
					initTree();
					initDeptTree();
					reloadTable();
				} else {
					$.messager.alert("提示", "部门修改失败！", "error");
					reloadTable();
				}
			}
		};
		ajax_(ajax_param);
	}
}

/**
 * 验证上级部门与所属组织是否匹配(新增前)
 * 
 * @param doName
 * @return
 */
function checkParentDeptBeforeAdd(doName){
	var organizationID = $("#ipt_organizationID").attr("alt");
	var parentDeptID = $("#ipt_parentDeptID").attr("alt");
	var path = getRootName();
	var uri = path + "/permissionDepartment/checkOrgAndParentDept";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"organizationID" : organizationID,
			"parentDeptID" :parentDeptID,
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			if (false == data || "false" == data) {
				$.messager.alert("提示", "所属单位与上级部门不匹配，请检查！", "error");
				// $("#ipt_organizationName").focus();
			} else {
					checkBeforeAdd(doName);
					return;
			}
		}
	};
	ajax_(ajax_param);
}

/**
 * 验证上级部门与所属组织是否匹配(更新前)
 * 
 * @param doName
 * @return
 */
function checkParentDeptBeforeUpdate(deptID,doName){
	var organizationID = $("#ipt_organizationID").attr("alt");
	var parentDeptID = $("#ipt_parentDeptID").attr("alt");
	var path = getRootName();
	var uri = path + "/permissionDepartment/checkOrgAndParentDept";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"organizationID" : organizationID,
			"parentDeptID" :parentDeptID,
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			if (false == data || "false" == data) {
				$.messager.alert("提示", "所属单位与上级部门不匹配，请检查！", "error");
				// $("#ipt_organizationName").focus();
			} else {
					checkBeforeUpdate(deptID,doName);
					return;
			}
		}
	};
	ajax_(ajax_param);
}

/**
 * 验证部门名称的唯一性(新增前)
 * 
 * @param doName
 * @return
 */
function checkBeforeAdd(doName){
	var deptName = $("#ipt_deptName").val();
	var organizationID = $("#ipt_organizationID").attr("alt");
	var parentDeptID = $("#ipt_parentDeptID").attr("alt");
	var path = getRootName();
	var uri = path + "/permissionDepartment/checkDeptName";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"deptName" : deptName,
			"organizationID" : organizationID,
			"parentDeptID" :parentDeptID,
			"doName" : doName,
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			if (false == data || "false" == data) {
				$.messager.alert("提示", "该部门名称已存在！", "error");
				// $("#ipt_organizationName").focus();
			} else {
				add();
				return;
			}
		}
	};
	ajax_(ajax_param);
}

/**
 * 验证部门名称的唯一性(新增前)
 * 
 * @param doName
 * @return
 */
function checkBeforeUpdate(deptID,doName){
	var deptName = $("#ipt_deptName").val();
	var organizationID = $("#ipt_organizationID").attr("alt");
	var parentDeptID = $("#ipt_parentDeptID").attr("alt");
	
	var path = getRootName();
	var uri = path + "/permissionDepartment/checkDeptName";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"deptId" : deptID,
			"deptName" : deptName,
			"organizationID" : organizationID,
			"parentDeptID" :parentDeptID,
			"doName" : doName,
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			if (false == data || "false" == data) {
				$.messager.alert("提示", "该部门名称已存在！", "error");
				// $("#ipt_organizationName").focus();
			} else {
				update();
				return;
			}
		}
	};
	ajax_(ajax_param);
}

function checkDeptName() {
	var deptName = $("#ipt_deptName").val();
	var path = getRootName();
	var uri = path + "/permissionDepartment/checkDeptName";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"deptName" : deptName,
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			if (true == data || "true" == data) {
				$.messager.alert("提示", "该部门名称已存在！", "error");
				// $("#ipt_organizationName").focus();
			} else {
				return;
			}
		}
	};
	ajax_(ajax_param);
}

function doImport(){
	parent.$('#popWin').window({
    	title:'部门导入',
        width:400,
        height:150,
        minimizable:false,
        maximizable:false,
        collapsible:false,
        modal:true,
        href: getRootName() + '/platform/dataImportor/dataImportForm?importType=2'
    });
}


function toExport() {
	$('#divExportDate').dialog({
		title : '选择导出列',
		width : 400,
		height : 330,
		minimizable : false,
		maximizable : false,
		collapsible : false,
		modal : true
	}).dialog('open');
}

function doExport() {
	var exportInfoObj = {
		"selId" : "selRight",
		"colName" : "iptColName",
		"titleName" : "iptTitleName"
	};

	var expObject = doExportCommon(exportInfoObj);
	$("#frmExport").submit();
}

/**
 * 向上移动选中的option
 */
function upSelectedOption() {
	if (null == $('#selRight').val()) {
		$.messager.alert("提示", "请选择一项!", "info");
		return false;
	}
	// 选中的索引,从0开始
	var optionIndex = $('#selRight').get(0).selectedIndex;
	// 如果选中的不在最上面,表示可以移动
	if (optionIndex > 0) {
		$('#selRight option:selected').insertBefore(
				$('#selRight option:selected').prev('option'));
	}
}

/**
 * 向下移动选中的option
 */
function downSelectedOption() {
	if (null == $('#selRight').val()) {
		$.messager.alert("提示", "请选择一项!", "info");
		return false;
	}
	// 索引的长度,从1开始
	var optionLength = $('#selRight')[0].options.length;
	// 选中的索引,从0开始
	var optionIndex = $('#selRight').get(0).selectedIndex;
	// 如果选择的不在最下面,表示可以向下
	if (optionIndex < (optionLength - 1)) {
		$('#selRight option:selected').insertAfter(
				$('#selRight option:selected').next('option'));
	}
}


function doExportCommon(exportInfoObj) {
	var expColValueAttr = '';
	var expColTitleAttr = '';
	$("#" + exportInfoObj.selId + " option").each(function() {
		expColValueAttr += $(this).attr('alt') + ',';
		expColTitleAttr += $(this).val() + ',';
	});
	var expObject = {
		"valAttr" : expColValueAttr,
		"titleAttr" : expColTitleAttr
	};
	$("#" + exportInfoObj.colName).val(expColValueAttr);
	$("#" + exportInfoObj.titleName).val(expColTitleAttr);
	return expObject;
}

/**
 * 验证部门呢编码是否已经存在
 * 
 * @return
 */
function checkDeptCode(){
	var deptId = $("#ipt_deptId").val();
	if(deptId!=null && deptId != ""){
		var flag = "update";
	}else{
		var flag = "add";
		deptId = -1;
	}
	var deptCode = $("#ipt_deptCode").val();
	var path = getRootName();
	var uri = path + "/permissionDepartment/checkDeptCode?flag="+flag;
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"deptId" : deptId,
			"deptCode" : deptCode,
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			if (false == data || "false" == data) {
				$.messager.alert("提示", "该部门编码已存在！", "error");
				 $("#ipt_deptCode").val("");
			} else {
				return;
			}
		}
	};
	ajax_(ajax_param);
}


/**
 * 是否显示清空
 * @return
 */
function isShow(){
	var parentDept =$("#ipt_parentDeptID").val();
	if(parentDept == null || parentDept == ""){
		$("#isShow").hide();
	}else{
		$("#isShow").show();
	}
}

/**
 * 清空上级部门
 * @return
 */
function clear(){
	$("#ipt_parentDeptID").val("");
	$("#ipt_parentDeptID").attr("alt", "0");
	$("#isShow").hide();
}