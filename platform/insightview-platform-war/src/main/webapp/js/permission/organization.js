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
var updateOpNameFlag=true;
var opNameFlag =true;
var _organizationID = -1;
var _organizationName = "";
var _nodeID = -1;
function treeClickAction(id, name) {
	resetFormPrivateFilter('divFilter');
	_organizationID = id;
	_organizationName = name;
	_nodeID = -1;
	reloadTable();
	
}

/**
 * 重置表单
 * 
 * @param pControlId:表单容器ID
 */
function resetFormPrivate(pControlId) {
	resetForm(pControlId);
}

/**
 * 重置表单
 * 
 * @param pControlId:表单容器ID
 */
function resetFormPrivateFilter(pControlId) {
	resetForm(pControlId);
	$("#selFilterParentName").attr('');
}

/**
 * 隐藏树
 */
function hiddenOrgDTreeSetValEasyUi(divId,controlId, showId,showVal) {
	$("#" + controlId).val(showVal);
	$("#" + controlId).attr("alt", showId);
	$("#" + divId).dialog('close');
//	console.log("tree.....");
	isShow();
}

/**
 * 选择上级单位
 */
function doChoseParentOrg() {
	var path = getRootPatch();
	var ajax_param = {
		url : path + "/permissionOrganization/findOrganizationLst",
		type : "post",
		datdType : "json",
		data : {
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			dataTreeTwo = new dTree("dataTreeTwo", path + "/plugin/dTree/img/");
			dataTreeTwo.add(0, -1, "选择上级单位", "");
			// 得到树的json数据源
			var datas = eval('(' + data.organizationLstJson + ')');
			var _id, _nameTemp, _parent;
			for (var i = 0; i < datas.length; i++) {
				_id = datas[i].organizationID;
				_nameTemp = datas[i].organizationName;
				_parent = datas[i].parentOrgID;
				
				dataTreeTwo.add(_id, _parent, _nameTemp,
						"javascript:hiddenOrgDTreeSetValEasyUi('divChoseOrg','ipt_parentOrgID','"
						+ _id + "','" + _nameTemp + "');");
			}
			$('#dataTreeDivs').empty().append(dataTreeTwo + "");
			$('#divChoseOrg').dialog('open');
		}
	};	
	ajax_(ajax_param);
}

/**
 * 初始化树
 */
function initTree() {
	var path = getRootPatch();
	var orgTreeImgPath = path + "/plugin/dTree/img/tree_company.png";
	var ajax_param = {
		url : path + "/permissionOrganization/findOrganizationLst",
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
			dataTree.add(0, -1,"单位组织", "javascript:treeClickAction('0','无');");
			// 得到树的json数据源
			var datas = eval('(' + data.organizationLstJson + ')');
			var _id, _nameTemp, _parent;
			for (var i = 0; i < datas.length; i++) {
				_id = datas[i].organizationID;
				_nameTemp = datas[i].organizationName;
				_parent = datas[i].parentOrgID;

				dataTree.add(_id, _parent, _nameTemp, "javascript:treeClickAction('" + _id + "','" + _nameTemp + "');","单位","",orgTreeImgPath,orgTreeImgPath);
			}
			$('#dataTreeDiv').empty()
							 .append("<input type='button' class='iconopen' value='展开 ' onclick='javascript:openTree(dataTree);'/>")
							 .append("<input type='button' class='iconclose' value='收起 ' onclick='javascript:closeTree(dataTree);'/>")
							 .append("<input type='text' id='treeName'/><input type='button' class='iconbtn' value='.' onclick='javascript:toSerach(dataTree);'/>")
							 .append(dataTree + "");
		}
	};
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
	var uri=path+"/permissionOrganization/searchTreeNodes";
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

/**
 * 更新树
 */
function update() {
	var result=checkInfo('#divOrganization');
	var ParentID = $("#ipt_parentOrgID").attr("alt");
	var path = getRootName();
	var uri = path + "/permissionOrganization/updateOrganization";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"organizationID" : $("#ipt_organizationID").val(),
			"organizationName" : $("#ipt_organizationName").val(),
			"organizationCode" : $("#ipt_organizationCode").val(),
			"parentOrgID" : $("#ipt_parentOrgID").attr("alt"),
			"descr" : $("#ipt_descr").val(),
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			if (true == data || "true" == data) {
				$('#divOrganization').dialog('close');
				initTree();
				reloadTable();
			} else {
				$.messager.alert("提示", "单位修改失败！", "error");
				reloadTable();
			}
		}
	}
	if(result == true){
		ajax_(ajax_param);
	}
			
}
/**
 * 到查看页面
 * @return
 */
function toView(organizationID){
	initViewVal(organizationID);
	$("#btnClose").bind("click", function() {
		$('#viewOrganization').dialog('close');
	});
	$('#viewOrganization').dialog('open');

}
/**
 * 查看页面初始化
 * @return
 */
function initViewVal(organizationID){
	var path = getRootName();
	var uri = path + "/permissionOrganization/findOrganization";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"organizationID" : organizationID,
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
//var _organizationID = "";
//var _orgName = "";
function resetUpdate() {
	toUpdate(_organizationID, _organizationName);
}

/**
 * 初始化更新页面
 * @param organizationID
 * @param parentOrganizationName
 * @return
 */
function initOrgVal(organizationID, parentOrganizationName) {
	var path = getRootName();
	var uri = path + "/permissionOrganization/findOrganization";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"organizationID" : organizationID,
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			iterObj(data, "ipt");

			$("#ipt_parentOrgID").attr("alt", data.parentOrgID);
			if (null != data.parentOrganizationName && 'null' != data.parentOrganizationName) {
				$("#ipt_parentOrgID").val(data.parentOrganizationName);
			} else {
				$("#ipt_parentOrgID").val('');
			}
			isShow();
		}
	}
	ajax_(ajax_param);
}
/**
 * 到更新页面
 * 
 * @param organizationID
 */
function toUpdate(organizationID, parentOrganizationName) {
	resetFormPrivate('tblOrgInfo');

	initOrgVal(organizationID, parentOrganizationName);

	$("#btnSave").unbind();
	$("#btnSave").bind("click", function() {
		doUpdate(organizationID);
	});
	$("#btnUpdate").unbind();
	$("#btnUpdate").bind("click", function() {
		$('#divOrganization').dialog('close');
	});

	$('#divOrganization').dialog('open');

}

function doUpdate(organizationID){
	var doName = "update";
	 checkBeforeUpdate(organizationID,doName);
	 if(updateOpNameFlag ==true)
	 {
		 checkCodeBeforeUpdate(organizationID,doName);
	 }
}
/**
 * 打开单位DIV
 * 
 * @author 武林
 */
function toAdd() {
	resetFormPrivate('tblOrgInfo');
	$("#btnSave").unbind();
	$("#btnSave").bind("click", function() {
		doAdd();
	});
	$("#btnUpdate").unbind();
	$("#btnUpdate").bind("click", function() {
		window.location.href = window.location.href;
		$('#divOrganization').dialog('close');
	});
	if(_organizationID == -1 || _organizationID == 0){
		$("#ipt_parentOrgID").val("单位组织");
		_organizationID=0;
		$("#ipt_parentOrgID").attr("alt",_organizationID);
	}else{
		$("#ipt_parentOrgID").val(_organizationName);
		$("#ipt_parentOrgID").attr("alt",_organizationID);
	}
	isShow();
	$('#divOrganization').dialog('open');
}

/**
 * 验证表单信息
 * 
 * @author 武林
 */
function checkForm() {
	return checkInfo('#tblOrgInfo');
}

/**
 * 新增单位
 * 
 * @author 武林
 */
function doAdd(){
	var doName = "add"; 
	checkBeforeAdd(doName);
	if(opNameFlag == true)
	{
		checkCodeBeforeAdd(doName);
	}
}
function add() {
	var result=checkInfo('#divOrganization');
	var path = getRootName();
	var uri = path + "/permissionOrganization/addOrganization";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"organizationName" : $("#ipt_organizationName").val(),
			"organizationCode" : $("#ipt_organizationCode").val(),
			"parentOrgID" : $("#ipt_parentOrgID").attr("alt"),
			"descr" : $("#ipt_descr").val(),
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			if (true == data || "true" == data) {
				$('#divOrganization').dialog('close');
				initTree();
				reloadTable();
			} else {
				$.messager.alert("提示", "单位新增失败！", "error");
			}
		}
	}
	if(result == true){
		ajax_(ajax_param);
	}
			
}

/**
 * 更新表格
 * 
 * @author 武林
 */
function reloadTable() {
	var organizationName = $("#iptFilterOrganizationName").val();
	var parentOrganizationName = $("#selFilterParentName").val();
	$('#tblOrganization').datagrid('options').queryParams = {
		"organizationID" : _organizationID,
		"organizationName" : organizationName,
		"parentOrganizationName" : parentOrganizationName,
		"nodeID" : _nodeID
	};
	reloadTableCommon_1('tblOrganization');
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
	$('#tblOrganization')
			.datagrid(
					{
						iconCls : 'icon-edit',// 图标
						width : 700,
						height : 'auto',
						nowrap : false,
						striped : true,
						border : true,
						collapsible : false,// 是否可折叠的
						fit : true,// 自动大小
						fitColumns:true,
						url : path + '/permissionOrganization/listOrganization',
						// sortName: 'code',
						// sortOrder: 'desc',
						remoteSort : false,
						checkOnSelect : false, 
						selectOnCheck : true,
//						idField : 'fldId',
						singleSelect : false,// 是否单选
						pagination : true,// 分页控件
						rownumbers : true,// 行号
						queryParams : {
							"parentOrgID" : -1
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
						} ],
						columns : [ [
					             {
					            	 field : 'organizationID',
					            	 checkbox : true
					            	 
					             },
								{
									field : 'organizationName',
									title : '单位名称',
									width : 250
								},
								{
									field : 'parentOrganizationName',
									title : '上级单位',
									width : 250
								},
								{
									field : 'organizationCode',
									title : '单位编码',
									width : 250
								},								
								{
									field : 'descr',
									title : '备注',
									width : 250,
								},
								{
									field : 'organizationIDs',
									title : '操作',
									width : 200,
									align : 'center',
									formatter : function(value, row, index) {
										var to = "&quot;"
												+ row.organizationID
												+ "&quot;,&quot;"
												+ row.parentOrganizationName
												+ "&quot;";

										return  '<a style="cursor: pointer;" onclick="javascript:toView('
												+ to
												+ ');"><img src="'
												+ path
												+ '/style/images/icon/icon_view.png" alt="查看" title="查看"/></a>&nbsp;<a style="cursor: pointer;" onclick="javascript:toUpdate('
												+ to
												+ ');"><img src="'
												+ path
												+ '/style/images/icon/icon_modify.png" alt="修改"  title="修改"/></a>&nbsp;<a style="cursor: pointer;" onclick="javascript:doDel('
												+ row.organizationID
												+ ');"><img src="'
												+ path
												+ '/style/images/icon/icon_delete.png" alt="删除" title="删除"/></a>';
									}
								} ] ]
					});
	//浏览器窗口大小变化时，datagrid表格自适应窗口大小变化
    $(window).resize(function() {
        $('#tblOrganization').resizeDataGrid(0, 0, 0, 0);
    });
}

/**
 * 删除单位
 * 
 * @author 武林
 */
function doDel(organizationID) {
	var path=getRootName();
	$.messager.confirm("提示","确定删除此条？",function(r){
		if (r == true) {
			var uri = path + "/permissionOrganization/delOrganization";
			var ajax_param = {
				url : uri,
				type : "post",
				datdType : "json",
				data : {
					"organizationId" : organizationID,
					"t" : Math.random()
				},
				error : function() {
					$.messager.alert("错误", "ajax_error", "error");
				},
				//Modified By Zhang Ya at 2014年12月25日 下午12:53:03
				// Begin .... 
				success : function(data) {
					if (data.result == "NotUsed") {
						if(_organizationID == organizationID){
							_organizationID = -1;
						}
						initTree();
						reloadTable();
					} else {
						$.messager.alert("提示", "此单位" + data.result + "，无法删除！", "error");
					}
				}
				// ...... End
			};
			ajax_(ajax_param);
		}
	});
}
/**
 * 批量删除
 * @return
 */
function doBatchDel(){
	var path=getRootName();
	var checkedItems = $('[name=organizationID]:checked');
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
				var uri = path+"/permissionOrganization/delOrganizations?organizationIDs=" + ids;
				var ajax_param = {
						url : uri,
						type : "post",
						datdType : "json",
						data : {
						"t" : Math.random()
				},
				success : function(data) {
					if(ids.indexOf(_organizationID) >= 0 )  
					{  
						_organizationID = -1;  
					}  
					if(data.flag == false) {
						$.messager.alert("错误", "被删单位"+data.orgName+"存在子单位或正在被使用，单位删除失败！", "error");
						initTree();
						reloadTable();
					} else {
						initTree();
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
function doSubmit() {
	var path = getRootName();
	var uri = path + "/permissionOrganization/listOrganization";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"organizationName" : $("#organizationName").val(),
			"parentOrganizationName" : $("#parentOrganizationName").val(),
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			alert("单位名称:" + data.name + "上级单位名称:" + data.parentname);
		}
	}
	ajax_(ajax_param);
}
function checkBeforeAdd(doName) {
	var organizationName = $("#ipt_organizationName").val();
	var parentOrgID = $("#ipt_parentOrgID").attr("alt");
	var path = getRootName();
	var uri = path + "/permissionOrganization/checkOrganizationName";
	var ajax_param = {
		url : uri,
		type : "post",
		async:false,
		datdType : "json",
		data : {
			"organizationName" : organizationName,
			"parentOrgID" : parentOrgID,
			"doName" : doName,
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			if (false == data || "false" == data) {
				$.messager.alert("提示", "该单位名称已存在！", "info");
				return  opNameFlag=false;
			} else {				 
				return opNameFlag = true;
			}
		}
	};
	ajax_(ajax_param);
}

function checkCodeBeforeAdd(doName) {
	var organizationCode = $("#ipt_organizationCode").val() 
		, parentOrgID = $("#ipt_parentOrgID").attr("alt");
	if (/^\s*$/.test(organizationCode)) {
		add();
		return;
	}
	var ajax_param = {
		url : getRootName() + "/permissionOrganization/checkOrganizationCode",
		type : "post",
		datdType : "json",
		data : {
			"organizationCode" : organizationCode,
			"parentOrgID" : parentOrgID,
			"doName" : doName,
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			if (false == data || "false" == data) {
				$.messager.alert("提示", "该单位编码已存在！", "info");
			} else {
				add();
			}
		}
	};
	ajax_(ajax_param);
}

function checkBeforeUpdate(organizationID,doName) {
	var rs = checUpdateName(organizationID);
	if(rs){
		var organizationName = $("#ipt_organizationName").val();
		var parentOrgID = $("#ipt_parentOrgID").attr("alt");
		var path = getRootName();
		var uri = path + "/permissionOrganization/checkOrganizationName";
		var ajax_param = {
				url : uri,
				type : "post",
				datdType : "json",
				data : {
			"organizationID" :organizationID,
			"organizationName" : organizationName,
			"parentOrgID" : parentOrgID,
			"doName" : doName,
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			if (false == data || "false" == data) {
				$.messager.alert("提示", "该单位名称已存在！", "info");
				return updateOpNameFlag=false;
			} else {
//				update();
				return updateOpNameFlag=true;
			}
		}
		};
		ajax_(ajax_param);
	}else{
		$.messager.alert("提示", "上级单位不能为本身！", "info");
	}
}

function checkCodeBeforeUpdate(organizationID,doName) {
	var organizationCode = $("#ipt_organizationCode").val()
		, parentOrgID = $("#ipt_parentOrgID").attr("alt");
	if (/^\s*$/.test(organizationCode)) {
		update();
		return;
	}
	var ajax_param = {
		url : getRootName() + "/permissionOrganization/checkOrganizationCode",
		type : "post",
		datdType : "json",
		data : {
			"organizationID" :organizationID,
			"organizationCode" : organizationCode,
			"parentOrgID" : parentOrgID,
			"doName" : doName,
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			if (false == data || "false" == data) {
				$.messager.alert("提示", "该单位编码已存在！", "info");
			} else {
				update();
			}
		}
	};
	ajax_(ajax_param);
}




/**
 * 验证编辑时，上级单位名称是否为本身
 * @param organizationID
 * @return
 */
function checUpdateName(organizationID){
	var parentOrgID = $("#ipt_parentOrgID").attr("alt");
	if (organizationID==parentOrgID){
		return false;
	}else{
		return true;
	}
}

/**
 * 是否显示清空
 * @return
 */
function isShow(){
	var parentOrgID =$("#ipt_parentOrgID").val();
//	console.log("parentOrgID==="+parentOrgID);
	if(parentOrgID == null || parentOrgID == "" || parentOrgID == "单位组织"){
		$("#isShow").hide();
	}else{
		$("#isShow").show();
	}
}

/**
 * 清空上级单位
 * @return
 */
function clear(){
	$("#ipt_parentOrgID").val("");
	$("#ipt_parentOrgID").attr("alt", "0");
	$("#isShow").hide();
}