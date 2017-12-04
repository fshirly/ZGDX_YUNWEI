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
		// 初始化下拉列表树
		initTree();
	}
);

//树数据
var _treeData = "";
var _treeOpenCount = 0;
var _organizationID = -1;
var _organizationName = "";
var _nodeID = -1;
function resetFormAdd(pControlId) {
	resetForm(pControlId);
	$("#ipt_gradeName").attr('alt', '');
	$("#ipt_organizationID").attr('alt', '');
}

/*
 * 点击树
 */
function treeClickAction(id, name) {
	resetForm1();
	_organizationID = id;
	_organizationName = name;
	_nodeID = -1;
//	isLeaf(id);
	reloadTable();
	
}

/*
 * 是否为叶子节点
 */
function isLeaf(id){
	var path=getRootName();
	var uri=path+"/platform/sysEmploymentGrade/isLeaf";
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
	var uri = path + "/platform/sysEmploymentGrade/findOrganizationTree";
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
			dataTree.add(0, -1, "单位组织", "javascript:treeClickAction('0','无');");

			// 得到树的json数据源
			var datas = eval('(' + data.organizationLstJson + ')');
			_treeData = datas;
			// 遍历数据
			var gtmdlToolList = datas;
			for (var i = 0; i < gtmdlToolList.length; i++) {
				var _id = gtmdlToolList[i].organizationID;
				var _name = gtmdlToolList[i].organizationName;
				var _parent = gtmdlToolList[i].parentOrgID;
				
				dataTree.add(_id, _parent, _name, "javascript:treeClickAction('" + _id + "','" + _name + "');","单位","",orgTreeImgPath,orgTreeImgPath);
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
 * 菜单定位
 */
function toSerach(treedata){
	var treeName=$("#treeName").val();
	var path=getRootName();
	var uri=path+"/platform/sysEmploymentGrade/searchTreeNodes";
	var ajax_param={
			url:uri,
			type:"post",
			dateType:"json",
			data:{
				"organizationName":treeName,
				"t" : Math.random()
	},
	error:function(){
		$.messager.alert("错误","ajax_error","error");
	},
	success:function(data){
		iterObj(data,"ipt");
		var nodeId=$('#ipt_nodeID').val();
		var treeIds=nodeId.split(",");
		for ( var int = 1; int < treeIds.length; int++) {
			var treeId=treeIds[int];
			treedata.openTo(treeId,true);
		}
		_nodeID = nodeId;
		reloadTable();
	}
	};
	ajax_(ajax_param);
	
	
}

//重置查询条件
function resetForm1() {
	$('#organizationName').val('');
	$('#gradeName').val('');
}

/*
 * 更新表格 
 */
function reloadTable() {
	var organizationName = $("#organizationName").val(); 
	var gradeName = $("#gradeName").val(); 
	$('#tblEmploymentGrade').datagrid('options').queryParams = {
		"organizationID" : _organizationID,
		"organizationName" : organizationName,
		"gradeName" : gradeName,
		"nodeID" : _nodeID
	};
	
	$('#tblEmploymentGrade').datagrid('reload');
	$('#tblEmploymentGrade').datagrid('unselectAll');
}


/*
 * 页面加载初始化表格
 */
function doInitTable() {
	var path = getRootName();
	
	$('#tblEmploymentGrade').datagrid({
		iconCls : 'icon-edit',// 图标
		width : 'auto',
		height : 'auto',
		nowrap : false,
		striped : true,
		border : true,
		collapsible : false,// 是否可折叠的
		fit : true,// 自动大小dd
		fitColumns:true,
		url : path + '/platform/sysEmploymentGrade/loadEmploymentGradeList',
		// sortName: 'code',
		// sortOrder: 'desc',
		remoteSort : false,
		idField : 'gradeID',
		singleSelect : false,// 是否单选
		checkOnSelect : false,
		pagination : true,// 分页控件
		rownumbers : true,// 行号
		toolbar : [ {
			text : '新增',
			iconCls : 'icon-add',
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
		}],
		columns : [ [
             {
            	 field : 'gradeID',
            	 checkbox : true
            	 
             },	
			{
				field : 'organizationName',
				title : '所属组织',
				align : 'center',
				width : 130
			},
			{
				field : 'gradeName',
				title : '职务级别名称',
				align : 'center',
				width : 130
			},
			{
				field : 'descr',
				title : '描述',
				align : 'center',
				width : 180
			},
			{
				field : 'gradeIDs',
				title : '操作',
				width : 150,
				align : 'center',
				formatter : function(value, row, index) {
				var to = "&quot;"
					+ row.organizationID
					+ "&quot;,&quot;"
					+ row.organizationName
					+ "&quot;,&quot;"
					+ row.gradeName
					+ "&quot;,&quot;"
					+ row.gradeID
					+ "&quot;,&quot;"
					+ row.descr
					+ "&quot;";
				var de = "&quot;"
					+ row.gradeID
					+ "&quot;,&quot;"
					+ row.organizationID
					+ "&quot;";
					return  '<a style="cursor: pointer;" onclick="javascript:toView('
							+ row.gradeID
							+ ');"><img src="'
							+ path
							+ '/style/images/icon/icon_view.png" alt="查看" /></a>&nbsp;<a style="cursor: pointer;" onclick="javascript:toUpdate('
							+ to
							+ ');"><img src="'
							+ path
							+ '/style/images/icon/icon_modify.png" alt="修改" /></a> &nbsp;<a style="cursor: pointer;" onclick="javascript:doDel('
							+ row.gradeID
							+ ","
							+ row.organizationID
							+ ');"><img src="'
							+ path
							+ '/style/images/icon/icon_delete.png" alt="删除" /></a>';
				}
			} ] ]
	});
	//浏览器窗口大小变化时，datagrid表格自适应窗口大小变化
    $(window).resize(function() {
        $('#tblDepartment').resizeDataGrid(0, 0, 0, 0);
    });
}

/*
 * 打开职务等级编辑页面
 */
function toAdd(){
	resetFormAdd('tblEmpGradeAdd');
	$("#btnSave").unbind("click");
	$("#btnSave").bind("click", function() {
		doAdd();
	});
	$("#btnUpdate").unbind();
	$("#btnUpdate").bind("click", function() {
		window.location.href = window.location.href;
		$('#divEmpGradeInfo').dialog('close');
	});
	if(_organizationID == -1 || _organizationID == 0){
		$("#ipt_organizationID").val("");
		_organizationID=0;
		$("#ipt_organizationID").attr("alt",_organizationID);
	}else{
		$("#ipt_organizationID").val(_organizationName);
		$("#ipt_organizationID").attr("alt",_organizationID);
	}
	$('#divEmpGradeInfo').dialog('open');
}

function doAdd(){
	var doName = "add"; 
	checkBeforeAdd(doName);
}

/*
 * 新增职务等级
 */
function add(){
	var result=checkInfo('#divEmpGradeInfo');
	var path = getRootName();
	var uri = path + "/platform/sysEmploymentGrade/addEmploymentGrade";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"gradeName" : $("#ipt_gradeName").val(),
			"organizationID" : $("#ipt_organizationID").attr("alt"),
			"descr" : $("#ipt_descr").val(),
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			if (true == data || "true" == data) {
//				$.messager.alert("提示", "职务等级新增成功！", "info");
				$('#divEmpGradeInfo').dialog('close');
				reloadTable();
			} else {
				$.messager.alert("提示", "职务等级新增失败！", "error");
			}

		}
	}
	if(result == true){
		ajax_(ajax_param);
	}
}

function checkForm() {
	var checkControlAttr = new Array('ipt_gradeName', 'ipt_organizationID');
	var checkMessagerAttr = new Array('请输入职务等级名称 ！', '请选择所属组织！');
	return checkFormCommon(checkControlAttr, checkMessagerAttr);
}

/*
 * 验证职务等级名称唯一性（新增前）
 */
function checkBeforeAdd(doName){
	var gradeName = $("#ipt_gradeName").val();
	var organizationID = $("#ipt_organizationID").attr("alt");
	
	var path = getRootName();
	var uri = path + "/platform/sysEmploymentGrade/checkGradeName";
	var ajax_param = {
			url : uri,
			type : "post",
			datdType : "json",
			data : {
				"gradeName" : gradeName,
				"organizationID":organizationID,
				"doName" : doName,
				"t" : Math.random()
			},
			error : function() {
				$.messager.alert("错误", "ajax_error", "error");
				
			},
			success : function(data) {
				if (false == data || "false"==data) {
					$.messager.alert("提示", "该职务等级已存在！", "error");
				} else{
					add();
					return;
				}
			}
		};
	ajax_(ajax_param);
}

/*
 * 验证职务等级名称唯一性（更新前）
 */
function checkBeforeUpdate(gradeID,doName){
	var gradeName = $("#ipt_gradeName").val();
	var organizationID = $("#ipt_organizationID").attr("alt");
	var path = getRootName();
	var uri = path + "/platform/sysEmploymentGrade/checkGradeName";
	var ajax_param = {
			url : uri,
			type : "post",
			datdType : "json",
			data : {
				"gradeID" : gradeID,
				"gradeName" : gradeName,
				"organizationID":organizationID,
				"doName" : doName,
				"t" : Math.random()
			},
			error : function() {
				$.messager.alert("错误", "ajax_error", "error");
				
			},
			success : function(data) {
//				alert("验证结果======="+data);
					if (false == data || "false"==data) {
						$.messager.alert("提示", "该职务等级已存在！", "error");
				} else{
					update(gradeID);
					return;
				}
			}
		};
	ajax_(ajax_param);
}

/*
 * 选择上级单位
 */
function doChoseOrg(){
	cancelRedBox("ipt_organizationID");
	if (0 == _treeOpenCount) {
		++_treeOpenCount;
		var path = getRootPatch();
		dataTreeTwo = new dTree("dataTreeTwo", path + "/plugin/dTree/img/");
		dataTreeTwo.add(0, -1, "选择所属单位", "");
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
 * 到查看页面
 */
function toView(gradeID){
	initViewVal(gradeID);
	$("#btnClose").bind("click", function() {
		$('#viewEmpGradeInfo').dialog('close');
	});
	$('#viewEmpGradeInfo').dialog('open');
}
/**
 * 初始化查看页面
 */
function initViewVal(gradeID){
	var path = getRootName();
	var uri = path + "/platform/sysEmploymentGrade/findEmploymentGrade";
	var ajax_param = {
			url : uri,
			type : "post",
			datdType : "json",
			data : {
				"gradeID" : gradeID,
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
/**
 * 到更新的界面
 */
function toUpdate(organizationID,organizationName,gradeName,gradeID,descr){
	resetFormAdd('tblEmpGradeAdd');
	initUpdateVal(organizationID,organizationName,gradeName,gradeID,descr);
	$("#btnSave").unbind();
	$("#btnSave").bind("click", function() {
		doUpdate(gradeID,descr);
	});
	$("#btnUpdate").unbind();
	$("#btnUpdate").bind("click", function() {
		$('#divEmpGradeInfo').dialog('close');
	});
	$('#divEmpGradeInfo').dialog('open');
}


/**
 * 更新职务等级
 */
function doUpdate(gradeID){
	var doName = "update";
	checkBeforeUpdate(gradeID,doName);
}
function update(gradeID){
	var result=checkInfo('#divEmpGradeInfo');
	var path = getRootName();
	var uri = path + "/platform/sysEmploymentGrade/updateEmploymentGrade";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"gradeID" : gradeID,
			"gradeName" : $("#ipt_gradeName").val(),
			"organizationID" : $("#ipt_organizationID").attr("alt"),
			"descr" : $("#ipt_descr").val(),
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			if (true == data || "true" == data) {
//				$.messager.alert("提示", "职务等级修改成功！", "info");
				$('#divEmpGradeInfo').dialog('close');
				reloadTable();
			} else {
				$.messager.alert("提示", "职务等级修改失败！", "error");
			}

		}
	}
	if(result==true){
		ajax_(ajax_param);
	}
}

/*
 * 初始化编辑页面
 */
function initUpdateVal(organizationID,organizationName,gradeName,gradeID,descr){
	var path = getRootName();
	var uri = path + "/platform/sysEmploymentGrade/findEmploymentGrade";
	var ajax_param = {
			url : uri,
			type : "post",
			datdType : "json",
			data : {
				"gradeID" : gradeID,
				"t" : Math.random()
			},
			error : function() {
				$.messager.alert("错误", "ajax_error", "error");
			},
			success : function(data) {
				iterObj(data, "ipt");
				if (null != organizationID && 'null' != organizationID) {
					$("#ipt_organizationID").attr('alt',
							data.organizationID);
					$("#ipt_organizationID").val(organizationName);
				} else {
					$("#ipt_organizationID").attr('alt', '');
					$("#ipt_organizationID").val('');
				}

				if (null != gradeID && 'null' != gradeID) {
					$("#ipt_gradeName").attr('alt', data.gradeName);
					$("#ipt_gradeName").val(gradeName);
				} else {
					$("#ipt_gradeName").attr('alt', '');
					$("#ipt_gradeName").val('');
				}

				if (null != descr && 'null' != descr) {
					$("#ipt_descr").attr('alt', data.descr);
					$("#ipt_descr").val(descr);
				} else {
					$("#ipt_descr").attr('alt', '');
					$("#ipt_descr").val('');
				}
			}
		}
		ajax_(ajax_param);
}

/*
 * 删除职务等级
 */
function doDel(gradeID,organizationID){
	var path=getRootName();
	$.messager.confirm("提示","确定删除此条？",function(r){
		if (r == true) {
			var uri = path+"/platform/sysEmploymentGrade/delEmpGrade";
			var ajax_param = {
				url : uri,
				type : "post",
				datdType : "json",
				data : {
					"gradeID" : gradeID,
					"organizationID" : organizationID,
					"t" : Math.random()
				},
				error : function() {
					$.messager.alert("错误", "ajax_error", "error");
				},
				success : function(data) {
					if(data == false) {
	                  $.messager.alert("错误", "职务等级被单位员工使用中，无法删除！", "error");
	                } else {
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
function doBatchDel(){
	var path=getRootName();
	var checkedItems = $('#tblEmploymentGrade').datagrid('getChecked');
	var idArray = new Array();
	for ( var i = 0; i < checkedItems.length; i++) {
		var gradeIDAndOrgID = "";
		gradeIDAndOrgID += checkedItems[i].gradeID+ '_' +  checkedItems[i].organizationID;
		idArray.push(gradeIDAndOrgID);
	}
	var ids="";
	for ( var j = 0; j < idArray.length; j++) {
		ids += ','+idArray[j];
	}
	if (null != ids && ""!=ids) {
		$.messager.confirm("提示","确定删除所选中项？",function(r){
			if (r == true) {
				var uri = path+"/platform/sysEmploymentGrade/delEmpGrades?ids=" + ids;
				var ajax_param = {
						url : uri,
						type : "post",
						datdType : "json",
						data : {
						"t" : Math.random()
				},
				success : function(data) {
					if(data.flag == false) {
						$.messager.alert("错误", "被删职务等级"+data.gradeName+"使用中，职务等级删除失败！", "error");
						reloadTable();
					} else {
//						$.messager.alert("提示", "职务等级删除成功！", "info");
						 initTree();
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

