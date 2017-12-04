	var dataTree = new dTree("dataTree",  getRootPatch() + "/plugin/dTree/img/");

$(document).ready(function() {
	//initOrgTree();
	initAsycOrgTree(null);
	doInitTable();
	doInitLRselect();
// doChoseConstantByTypeName('selFilterUserType','UserType');
});

function doInitLRselect() {
	$.fn.LRSelect("selLeft", "selRight", "img_L_AllTo_R", "img_L_To_R",
			"img_R_To_L", "img_R_AllTo_L");
	$.fn.UpDownSelOption("imgUp", "imgDown", "selRight");
	LRSelect.moveAll( "selLeft", "selRight", true);  
}

// 树数据
var _treeData = "";
var _treeDataPro="";
var _currentNodeId = "-1";
var _currentNodeName = "";
function treeClickAction(id, name) {
	_currentNodeId = id;
	_currentNodeName = name;
	initAsycOrgTree(id);
	reloadTable();
	$('#treeType_id').val(_currentNodeId);
}

/**
 * 初始化树菜单
 */
var treeLst=[];
function initAsycOrgTree(parentOrgID) {
	var path = getRootPatch();
	var partmentTreeImgPath = path + "/plugin/dTree/img/tree_department.png";
	var orgTreeImgPath = path + "/plugin/dTree/img/tree_company.png";
	var uri = path + "/permissionOrganization/findSyncOrganizationList";
	var ajax_param = {
		url : uri,
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
			if(parentOrgID==null){
					dataTree.add(0, -1, "单位部门", "javascript:treeClickAction(0,'无');");
			}
			
			// 得到树的json数据源
		    var datas = eval('(' + data.organizationLstJson + ')');
			_treeData = datas;
			// 遍历数据
			var gtmdlToolList = datas;
			for (var i = 0; i < datas.length; i++) {
				var _id = datas[i].organizationID;
				var _name =  datas[i].organizationName;
				var _parent = datas[i].parentOrgID=='O0'?'0':datas[i].parentOrgID;
				treeLst.push(_id);
				/**if(_id.indexOf("O") == 0) {
					dataTree.add(_id, _parent, _name, "javascript:treeClickAction('" + _id + "','" + _name + "');","单位","",orgTreeImgPath,orgTreeImgPath);
				} else if(_id.indexOf("D") == 0){
					dataTree.add(_id, _parent, _name, "javascript:treeClickAction('" + _id + "','" + _name + "');","部门","",partmentTreeImgPath,partmentTreeImgPath);
				} else {**/
					dataTree.add(_id, _parent, _name, "javascript:treeClickAction('" + _id + "','" + _name + "');");
				//}
			}
			// dom操作div元素内容
			$('#dataTreeDiv').empty();
			//$('#dataTreeDiv').append("<input type='button' value='展开' onclick='javascript:openTree(dataTree);'/>");
			//$('#dataTreeDiv').append("<input type='button' value='收起' onclick='javascript:closeTree(dataTree);'/>");
			//$('#dataTreeDiv').append("<input type='text' id='treeName'/><input type='button' class='iconbtn' value='.'  onclick='javascript:toSerach(dataTree);'/>");
			$('#dataTreeDiv').append(dataTree + "");
			dataTree.openAll();
			// 操作tree对象
			//dataTree.openAll();
		}
	}
	ajax_(ajax_param);
}
function initOrgTree() {
	var path = getRootPatch();
	var partmentTreeImgPath = path + "/plugin/dTree/img/tree_department.png";
	var orgTreeImgPath = path + "/plugin/dTree/img/tree_company.png";
	var uri = path + "/permissionSysUser/findOrgAndProvideTreeVal";
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
			dataTree.add(0, -1, "单位部门", "javascript:treeClickAction(0,'无');");
			// 得到树的json数据源
			var jsonData = eval('(' + data.menuLstJson + ')');
			_treeData = jsonData;
			// 遍历数据
			var gtmdlToolList = jsonData;
			for (var i = 0; i < gtmdlToolList.length; i++) {
				var _id = gtmdlToolList[i].id;
				var _name = gtmdlToolList[i].name;
				var _parent = gtmdlToolList[i].parentId;
				treeLst.push(_id);
				if(_id.indexOf("O") == 0) {
					dataTree.add(_id, _parent, _name, "javascript:treeClickAction('" + _id + "','" + _name + "');","单位","",orgTreeImgPath,orgTreeImgPath);
				} else if(_id.indexOf("D") == 0){
					dataTree.add(_id, _parent, _name, "javascript:treeClickAction('" + _id + "','" + _name + "');","部门","",partmentTreeImgPath,partmentTreeImgPath);
				} else {
					dataTree.add(_id, _parent, _name, "javascript:treeClickAction('" + _id + "','" + _name + "');");
				}
			}
			// dom操作div元素内容
			$('#dataTreeDiv').empty();
			$('#dataTreeDiv').append("<input type='button' value='展开' onclick='javascript:openTree(dataTree);'/>");
			$('#dataTreeDiv').append("<input type='button' value='收起' onclick='javascript:closeTree(dataTree);'/>");
			$('#dataTreeDiv').append("<input type='text' id='treeName'/><input type='button' class='iconbtn' value='.'  onclick='javascript:toSerach(dataTree);'/>");
			$('#dataTreeDiv').append(dataTree + "");
			// 操作tree对象
			//dataTree.openAll();
		}
	}
	ajax_(ajax_param);
}


/*
 * 删除用户 @author 武林
 */
function doDel(userId) {
	$.messager.confirm("提示","确定删除此用户？",function(r){
		if (r == true) {
			if(userId>=10000){
				var path = getRootName();
				var uri = path + "/permissionSysUser/delSysUser";
				var ajax_param = {
						url : uri,
						type : "post",
						datdType : "json",
						data : {
					"userId" : userId,
					"t" : Math.random()
				},
				error : function() {
					$.messager.alert("错误", "ajax_error", "error");
				},
				success : function(data) {
					if (data.result === 0) {
						reloadTable();
						//initOrgTree();
					} else {
						if(data.result === 1){
							$.messager.alert("提示", "该角色已被分批任务，无法删除", "error");
						}
						else if(data.result === 2){
							$.messager.alert("提示", "角色删除失败！", "error");
						}
					}
				}
				}
				ajax_(ajax_param);
			}else{
				$.messager.alert("错误", "该用户为系统自定义用户，不能删除！", "error");
			}
		}
	});
	
}

/*
 * 批量删除
 */
function doBatchDel() {
	var path=getRootName();
	var checkedItems = $('[name=userID]:checked');
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
				var uri = path+"/permissionSysUser/delSysUsers?userIds=" + ids;
				var ajax_param = {
					url : uri,
					type : "post",
					datdType : "json",
					data : {
						"t" : Math.random()
					},
					success : function(data) {
						if(data.flag == false) {
			            	$.messager.alert("错误", "用户删除失败！", "error");
						} else if(data.isSystem == true) {
// $.messager.alert("提示", "用户删除成功！", "info");
							reloadTable();
							//initOrgTree();
						}else if(data.isSystem == false) {
							$.messager.alert("提示", "用户"+data.userName+"为系统自定义用户，不能删除！", "info");
							reloadTable();
							//initOrgTree();
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

function checkUserInfo() {
	var userAccount = $("#ipt_userAccount").val();
	if (!(/[\w_]{4,20}/.test(userAccount)) || userAccount.indexOf('\.') >= 0) {
		$.messager.alert("提示", "用户名至少输入4个字符，且由a-z0-9A-Z_组成！", "info", function(e) {
			$("#ipt_userAccount").focus();
		});
		return false;
	}
	var userName = $("#ipt_userName").val();
	if (userName.length <= 0) {
		$.messager.alert("提示", "请输入用户姓名！", "info", function(e) {
			$("#ipt_userName").focus();
		});
		return false;
	}
	var userPassword = $("#ipt_userPassword").val();
	if (!(/[\w]{6,30}/.test(userPassword))) {
		$.messager.alert("提示", "密码至少为六位字符！", "info", function(e) {
			$("#ipt_userPassword").focus();
		});
		return false;
	}

	var mobilePhone = $("#ipt_mobilePhone").val();
	if (mobilePhone.length <= 0) {
		$.messager.alert("提示", "请输入正确的手机号码！", "info", function(e) {
			$("#ipt_mobilePhone").focus();
		});
		return false;
	}

	var email = $("#ipt_email").val();
	if (!(/^[\w-]+(\.[\w-]+)*@[\w-]+(\.[\w_]+)+$/.test(email))) {
		$.messager.alert("提示", "请输入正确的邮箱地址！", "info", function(e) {
			$("#ipt_email").focus();
		});
		return false;
	}
	/*var userType = $("#ipt_userType").val();
	if (userType.length <= 0) {
		$("#ipt_userType").focus();
		return false;
	}*/

	return true;
}







/*
 * 更新表格 @author 武林
 */
function reloadTable() {
	var userAccount = $("#txtFilterUserAccount").val();
	var userName = $("#txtFilterUserName").val();
	/*var userType = $("#selFilterUserType").combobox('getValue');*/
	var isAutoLock = $("#selFilterIsAutoLock").combobox('getValue');
	var mobilePhone = $("#txtFilterMobilePhone").val();
	var status = $("#selFilterStatus").combobox('getValue'); 
	/*if(userType == ""){
		userType = -1;
	}*/
	if(isAutoLock == ""){
		isAutoLock = -1;
	}
	if(status == ""){
		status = -1;
	}
	$('#tblSysUser').datagrid('options').queryParams = {
		"userAccount" : userAccount,
		"userName" : userName,
		"mobilePhone" : mobilePhone,
		"status" : status,
		/*"userType" : userType,*/
		"isAutoLock" : isAutoLock,
		"treeType" : _currentNodeId
	};
	reloadTableCommon_1('tblSysUser');
}

function reloadTableCommon_1(dataGridId) {
	$('#' + dataGridId).datagrid('load');
	$('#' + dataGridId).datagrid('uncheckAll');
}

/*
 * 页面加载初始化表格 @author 武林
 */
function doInitTable() {
	var path = getRootName();
	$('#tblSysUser')
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
						url : path + '/permissionSysUser/listSysUser',
						// sortName: 'code',
						// sortOrder: 'desc',
						queryParams : {
							"status" : -1,
							"isAutoLock" : -1
							/*"userType" : -1*/
						},
						/*onLoadSuccess:function(data){
							console.log(data);
						},*/
						remoteSort : false,
						idField : 'userID',
						singleSelect : false,// 是否单选
						checkOnSelect : false, 
						selectOnCheck : true,
						pagination : true,// 分页控件
						rownumbers : true,// 行号
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
						}
						],
						
						columns : [ [
						        {
						        	field : 'userID',
						        	checkbox : true
						        },
								{
									field : 'userAccount',
									title : '帐号',
									width : 30,
									align : 'center',
								},
								{
									field : 'userName',
									title : '用户姓名',
									width : 30,
									align : 'center'
								},
								{
									field : 'mobilePhone',
									title : '手机号码',
									width : 30,
									align : 'center'
								},
								{
									field : 'deptName',
									title : '所属部门',
									width : 40,
									align : 'center',
									formatter : function(value, row, index) {
										return 	value;					
									}
								},
								{
									field : 'isAutoLock',
									title : '自动锁定', 
									width : 20,
									align : 'center',
									formatter : function(value, row, index) {
										if (value == 0) {
											return '是';
										} else {
											return '否';
										}
									}
								},
								{
									field : 'status',
									title : '状态',
									width : 20,
									align : 'center',
									formatter : function(value, row, index) {
										if (value == 1) {
											return '正常';
										} else {
											return '锁定';
										}
									}
								},
								{
									field : 'lockedTime',
									title : '锁定时间',
									width : 40,
									align : 'center',
									formatter:function(value,row,index){
									if(value!=null && value!="" ){
										return formatDate(new Date(value), "yyyy-MM-dd hh:mm:ss");
									}else{
										return "";
									}	
								}

								},
								{
									field : 'lockedReason',
									title : '锁定原因',
									width : 50,
									align : 'center'
								},
								{
									field : 'userIDs', 
									title : '操作',
									width : 50,
									align : 'center',
									formatter : function(value, row, index) {
									 var dis = "&quot;" + row.userID
										+ "&quot;,&quot;" + row.userAccount
										+ "&quot;"
										var lockOp='<a style="cursor: pointer;" onclick="javascript:doOpenLock('
										+ row.userID
										+ ');"><img src="'     
										+ path
										+ '/style/images/icon/icon_lock.png" title="锁定"/></a> &nbsp;';
										if(row.status==0){
											lockOp='<a style="cursor: pointer;" onclick="javascript:toUnLock('
											+ row.userID+','+row.status
											+ ');"><img src="'
											+ path 
											+ '/style/images/icon/icon_unlock.png" title="解锁"/></a> &nbsp;'
										}
										var edit = '';
										if(row.userID >= 10000){
											edit = '<a style="cursor: pointer;" onclick="javascript:toUpdate('
												+ dis
												+ ');"><img src="'
												+ path
												+ '/style/images/icon/icon_modify.png" title="修改"/></a>';
										}else{
											edit = '<a style="cursor: pointer;" onclick="return false"><img src="'
												+ path
												+ '/style/images/icon/icon_modify1.png" title="系统自定义用户，不可修改"/></a>';
										}
										return '<a style="cursor: pointer;" onclick="javascript:doOpenDetail('
												+ row.userID
												+ ');"><img src="'
												+ path
												+ '/style/images/icon/icon_view.png" title="查看" /></a> &nbsp;'
												+ edit +' &nbsp;'
												+'<a style="cursor: pointer;" onclick="javascript:doDel('
												+ row.userID
												+ ');"><img src="'
												+ path
												+ '/style/images/icon/icon_delete.png" title="删除"/></a> &nbsp;'
												+lockOp
												+'<a style="cursor: pointer;" onclick="javascript:doOpenModifyPwd('
												+ row.userID
												+ ');"><img src="'
												+ path
												+ '/style/images/icon/icon_password.png" title="修改密码"/></a> &nbsp;'
												+'<a style="cursor: pointer;" onclick="javascript:toResetPwd('
												+ row.userID
												+ ');"><img src="'
												+ path
												+ '/style/images/icon/icon_passwordreset.png" title="重置密码"/></a>';
									}
								} ] ]
					});
    $(window).resize(function() {
        $('#tblSysUser').resizeDataGrid(0, 0, 0, 0);
    });
}

/* 重置 原来的表单数据 */
function resetFormValue(pcId) {
	var userId = $("#ipt_userID").val();
	if (userId.trim() == "") {
		resetForm(pcId);
	} else {
		toUpdate(userId);
	}
}


function toUnLock(userId,status){
	if(status==1){
		$.messager.alert("提示", "只有已锁定的用户才能被解锁，请先锁定客户！", "info");
	}else{
		doUnLock(userId)
	}
}

function doUnLock(userId){
	$.messager.confirm("提示","确定将此用户解锁？",function(r){
		if(r==true){
			var path = getRootName();
			var uri = path + "/permissionSysUser/unLockSysUser";
			var ajax_param = {
				url : uri,
				type : "post",
				datdType : "json",
				data : {
					"userID" : userId,
					"status" : 1,
					"lockedReason" : null,
					"t" : Math.random()
				},
				error : function() {
					$.messager.alert("错误", "ajax_error", "error");
				},
				success : function(data) {
					if (true == data) {
// $.messager.alert("提示", "用户解锁成功！", "info");
						reloadTable();
					} else {
						$.messager.alert("提示", "用户解锁失败！", "error");
						reloadTable();
					}
				}
			};
			ajax_(ajax_param);
		}
	});

	
}





function toResetPwd(userId){
	$.messager.confirm("提示","确定重置该用户密码？",function(r){
		if(r==true){
			var path = getRootName();
			var uri = path + "/permissionSysUser/modifyPwd";
			var ajax_param = {
				url : uri,
				type : "post",
				datdType : "json",
				data : {
					"userID" : userId,
					"userPassword":"123456",
					"t" : Math.random()
				},
				error : function() {
					$.messager.alert("错误", "ajax_error", "error");
				},
				success : function(data) {
					if (true == data) {
// $.messager.alert("提示", "密码重置成功！", "info");
						reloadTable();
					} else {
						$.messager.alert("提示", "密码重置失败！", "error");
						reloadTable();
					}
				}
			};
			ajax_(ajax_param);
		}
	});

	
}



function toShowEmp(){
	$("#divEmpInfo").dialog("open");
	$("#btnSave4").unbind('click');
	$("#btnSave4").bind("click", function() {
		$('#divEmpInfo').dialog('close');
	});
	$("#btnBack4").unbind();
	$("#btnBack4").bind("click", function() {
		$('#divEmpInfo').dialog('close');
	});
	
}

function toShowProvider(){
	$("#divProviderInfo").dialog("open");
	$("#btnSave5").unbind('click');
	$("#btnSave5").bind("click", function() {
		$('#divProviderInfo').dialog('close');
	});
	$("#btnBack5").unbind();
	$("#btnBack5").bind("click", function() {
		$('#divProviderInfo').dialog('close');
	});
}


// 菜单定位
function toSerach(treedata){   
	var treeName=$("#treeName").val();
	if(treeName==""){
		_currentNodeId=treeName;
		reloadTable();
	}else{
		var path=getRootName();
		var uri=path+"/permissionSysUser/searchTreeNodes";
		var ajax_param={
				url:uri,
				type:"post",
				dateType:"json",
				data:{
					"treeName":treeName,    
					"t" : Math.random()
		},
		error:function(){
			$.messager.alert("错误","ajax_error","error");
		},
		success:function(data){
			iterObj(data,"ipt");
			var treeTypeIds=$('#ipt_treeType').val();
			var treeIds=treeTypeIds.split(",");
			for ( var int = 1; int < treeIds.length; int++) {
				var treeId=treeIds[int];
				for ( var int2 = 0; int2 < treeLst.length; int2++) {
					if(treeId==treeLst[int2]){
						treedata.openTo(treeId,true);
					}
				}
				
			}
			_currentNodeId=treeTypeIds;
			reloadTable();
		}
		};
		ajax_(ajax_param);
	}
	
}


// 定义简单Map
function getMap() {// 初始化map_,给map_对象增加方法，使map_像Map
         var map_ = new Object();     
         map_.put = function(key, value) {     
             map_[key+'_'] = value;     
         };     
         map_.get = function(key) {     
             return map_[key+'_'];     
         };     
         map_.remove = function(key) {     
             delete map_[key+'_'];     
         };     
         map_.keyset = function() {     
             var ret = "";     
             for(var p in map_) {     
                 if(typeof p == 'string' && p.substring(p.length-1) == "_") {     
                     ret += ",";     
                     ret += p.substring(0,p.length-1);     
                 }     
             }     
             if(ret == "") {     
                 return ret.split(",");     
             } else {     
                 return ret.substring(1).split(",");     
             }     
         };     
         return map_;     
}   

function toUpdate(userId,userAccount){
	var path = getRootName();
	var uri = path + "/permissionSysUser/doBeforeUpdate?userId="+userId+"&userAccount="+userAccount;
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
			doOpenModify(userId,data.isAdmin,data.isSameOrg,data.currentUserOrgId);
		}
	};
	ajax_(ajax_param);
}

/**
 * 打开编辑页面
 * 
 * @param userId
 * @return
 */
function doOpenModify(userId,isAdmin,isSameOrg,currentUserOrgId){
	// 查看配置项页面
	parent.$('#popWin').window({
    	title:'编辑用户',
        width:800,
        height:420,
        minimizable:false,
        maximizable:false,
        collapsible:false,
        modal:true,
        href: getRootName() + '/permissionSysUser/toShowModify?userId='+userId+'&isAdmin='+isAdmin+'&isSameOrg='+isSameOrg+'&currentUserOrgId='+currentUserOrgId
    });
}


/**
 * 打开查看详情页面
 * 
 * @param userId
 * @return
 */
function doOpenDetail(userId){
	// 查看配置项页面
	parent.$('#popWin').window({
    	title:'用户详情',
        width:800,
        height:450,
        minimizable:false,
        maximizable:false,
        collapsible:false,
        modal:true,
        href: getRootName() + '/permissionSysUser/toShowDetail?userId='+userId
    });
}

/**
 * 打开锁定页面
 * 
 * @param userId
 * @return
 */
function doOpenLock(userId){
	// 查看配置项页面
	parent.$('#popWin').window({
    	title:'锁定用户',
        width:600,
        height:400,
        minimizable:false,
        maximizable:false,
        collapsible:false,
        modal:true,
        href: getRootName() + '/permissionSysUser/toShowLock?userId='+userId
    });
}

/**
 * 打开修改密码页面
 * 
 * @param userId
 * @return
 */
function doOpenModifyPwd(userId){
	// 查看配置项页面
	parent.$('#popWin').window({
    	title:'修改密码',
        width:600,
        height:400,
        minimizable:false,
        maximizable:false,
        collapsible:false,
        modal:true,
        href: getRootName() + '/permissionSysUser/toShowModifyPwd?userId='+userId
    });
}

function toAdd(){
	if(_currentNodeId==0){
		$.messager.alert("提示","请选择单位下面具体部门","info");
		return;
	}
	if(_currentNodeId.indexOf("O") == 0) {
		$.messager.alert("提示", "请选择单位下面具体部门", "info");
	}else{
		var deptID = -1;
		var providerID = -1;
		/*var userType = 0;*/
		if(_currentNodeId.indexOf("D") == 0) {
			deptID =_currentNodeId.substring(1);
		}else if(_currentNodeId.indexOf("P") == 0) {
			providerID =_currentNodeId.substring(1);
			/*userType = 3;*/
		}
		var path = getRootName();
		var uri = path + "/permissionSysUser/doBeforeAdd";
		var ajax_param = {
			url : uri,
			type : "post",
			datdType : "json",
			data : {
				"deptId" : deptID,
				"providerId" : providerID,
				/*"userType" : userType,*/
				"t" : Math.random()
			},
			error : function() {
				$.messager.alert("错误", "ajax_error", "error");
			},
			success : function(data) {
				if(data.authority == true || data.authority == "true"){
					doOpenAdd(data.isAdmin,data.organizationId, data.deptId, data.providerId, data.userType);
				}else{
					$.messager.alert("提示", "非管理员用户只能选择自己所在单位下的部门", "info");
				}
			}
		};
		ajax_(ajax_param);
	}
	
	
}

/**
 * 打开新增页面
 * 
 * @param userId
 * @return
 */
function doOpenAdd(isAdmin,organizationId, deptId, providerId, userType){
	// 查看配置项页面
	parent.$('#popWin').window({
    	title:'新增用户',
        width:800,
        height:400,
        minimizable:false,
        maximizable:false,
        collapsible:false,
        modal:true,
        href: getRootName() + '/permissionSysUser/toShowAdd?isAdmin='+isAdmin+"&organizationId="+organizationId+"&deptId="+deptId+"&providerId="+providerId+"&userType="+userType
    });
}

function doImport(){
	parent.$('#popWin').window({
    	title:'用户导入',
        width:400,
        height:150,
        minimizable:false,
        maximizable:false,
        collapsible:false,
        modal:true,
        href: getRootName() + '/platform/dataImportor/dataImportForm'
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