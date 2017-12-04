$(document).ready(function() {
	doInitTable();

	
	doChoseConstantByTypeName('txtFilterUserType','UserType');
});

/**
 * 加载页面并初始化表格
 * @return
 */
function doInitTable(){
	var path = getRootName();
	$('#tblUser')
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
						url : path + '/platform/demoUser/listUsers',
					    sortName: 'useraccount',
						sortOrder: 'desc',
						remoteSort : true,
						onSortColumn:function(sort,order){
//						 alert("sort:"+sort+",order："+order+"");
						},
						queryParams : {
							"isautolock" : -1,
							"usertype" : -1
						},
						
						idField : 'fldId',
						singleSelect : true,// 是否单选
						pagination : true,// 分页控件
						rownumbers : true,// 行号
						toolbar : [ {
							'text' : '添加',
							'iconCls' : 'icon-add',
							handler : function() {
								toAdd();
							}
						} ],
						columns : [ [
								{
									field : 'useraccount',
									title : '用户名',
									width : 70,
									align : 'center',
									sortable : true,
								},
								{
									field : 'username',
									title : '用户姓名',
									width : 80,
									align : 'center',
									sortable : true
								},
								{
									field : 'mobilephone',
									title : '手机号码',
									width : 80,
									align : 'center',
									sortable : true
								},
								{
									field : 'usertype',
									title : '用户类型',
									width : 180,
									align : 'center',
									formatter : 
										function(value, row, index) {
										if (value == 0) {
											return '管理员';
										}
										if (value == 1) {
											return '企业内IT部门用户';
										}
										if (value == 2) {
											return '企业业务部门用户';
										} else {
											return '外部供应商用户';
										}
									
									
									},
									sortable : true
								},
								{
									field : 'isautolock',
									title : '是否锁定',
									width : 100,
									align : 'center',
									formatter : function(value, row, index) {
										if (value == 0) {
											return '是';
										} else {
											return '否';
										}
									},
									sortable : true
								},
								{
									field : 'lockedreason',
									title : '锁定原因',
									width : 180,
									align : 'center',
									sortable : true
								},
								{
									field : 'userid',
									title : '操作',
									width : 100,
									align : 'center',
									formatter : function(value, row, index) {
										return '<a style="cursor: pointer;" onclick="javascript:toUpdate('
												+ row.userid
												+ ');"><img src="'
												+ path
												+ '/style/images/icon/icon_modify.gif" alt="修改" /></a> &nbsp;&nbsp;&nbsp;&nbsp;<a style="cursor: pointer;" onclick="javascript:doDel('
												+ row.userid
												+ ');"><img src="'
												+ path
												+ '/style/images/icon/icon_delete.gif" alt="删除" /></a>';
									}
								} ] ]
					});
}


/**
 * 检查用户是否存在
 */
var _auType=0;
function checkSysUser(){
	if(_auType==1){
		var userAccount=$("#ipt_user" +
				"account").val();
		var path=getRootName();
		var uri=path+"/platform/demoUser/checkSysUser";
		var ajax_param={
			url:uri,
			type:"post",
			dateType:"json",
			data:{
				"useraccount" : userAccount,
				"t" : Math.random()
			},
			error : function(){
				$.messager.alert("错误","ajax_error","error");
			},
			success : function(data){
				if(true==data || "true"==data){
					$.messager.alert("提示","该用户名已经存在！","error");
				}else{
					return;
				}
			}
		};
		ajax_(ajax_param);		
	}
}

/**
 * 新增用户
 * @return
 */
function doAddUser(){
//	var result=checkUserInfo();
	var typeValue=$('#ipt_usertype').combobox('getValue');
	var result = checkInfo('#divAddUser');
	var path=getRootName();
	var uri=path+"/platform/demoUser/addUser";
	var ajax_param={
			url:uri,
			type:"post",
			dateType:"json",
			data:{
				"status" : 1,
				"useraccount" : $("#ipt_useraccount").val(),
				"username" : $("#ipt_username").val(),
				"userpassword" : $("#ipt_userpassword").val(),
				"mobilephone" : $("#ipt_mobilephone").val(),
				"email" : $("#ipt_email").val(),
				"telephone" : $("#ipt_telephone").val(),
				"usertype" : typeValue,
				"isautolock" : "1",
				"t" : Math.random()
	},
	error:function(){
		$.messager.alert("错误","ajax_error","error");
	},
	success:function(data){
		if(true==data || "true"==data || ""==data){
			$.messager.alert("提示","用户新增成功！","info");
			$("#divAddUser").dialog('close');
			reloadTable();
		}else{
			$.messager.alert("提示","用户新增失败！","error");
		}
	}
	};
	if(result==true || result=="true"){
		ajax_(ajax_param);
	}
}

/**
 * 检查用户信息规范
 * @return
 */
function checkUserInfo(){
	var userAccount=$("#ipt_useraccount").val();
	if(!(/[\w_]{4,20}/.test(userAccount)) || userAccount.indexOf('\.')>=0){
		$.messager.alert("提示","用户名不能为空，且由字母和数字组成！","info",function(e){
			$("#ipt_useraccount").focus();
		});
		return false;
	}
	var userName=$("#ipt_username").val();	
	if(userName.length<=0){
		$.messager.alert("提示","请输入用户名！","info",function(e){
			$("#ipt_username").focus();
		});
	}
	var userPassword = $("#ipt_userpassword").val();
	if (!(/[\w]{6,30}/.test(userPassword))) {
		$.messager.alert("提示", "密码至少为六位字符！", "info", function(e) {
			$("#ipt_userpassword").focus();
		});
		return false;
	}

	var mobilePhone = $("#ipt_mobilephone").val();
	if (mobilePhone.length <= 0) {
		$.messager.alert("提示", "请输入正确的手机号码！", "info", function(e) {
			$("#ipt_mobilephone").focus();
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
	var userType = $("#ipt_usertype").val();
	if (userType.length <= 0) {
		$("#ipt_usertype").focus();
		return false;
	}

	return true;
}


function doUpdateUser() {
	var result = checkUserInfo();
	var path = getRootName();
	var uri = path + "/platform/demoUser/updateUser";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"userid" : $("#ipt_userid").val(),
			"useraccount" : $("#ipt_useraccount").val(),
			"username" : $("#ipt_username").val(),
			"userpassword" : $("#ipt_userpassword").val(),
			"mobilephone" : $("#ipt_mobilephone").val(),
			"email" : $("#ipt_email").val(),
			"telephone" : $("#ipt_telephone").val(),
			"usertype" : $("#ipt_usertype").val(),
			"isautolock" : $("#ipt_isautolock").val(),
			"status" : $("#ipt_status").val(),
//			"createtime" : $("#ipt_createtime").val(),
			// "lockedTime" : $("#ipt_lockedTime").val(),
			"lockedreason" : $("#ipt_lockedreason").val(),
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			if (true == data || "true" == data) {
				$.messager.alert("提示", "用户修改成功！", "info");
				$('#divAddUser').dialog('close');
				reloadTable();
			} else {
				$.messager.alert("提示", "用户修改失败！", "error");
				reloadTable();
			}
		}
	};
	if (result == true || result == 'true') {
		ajax_(ajax_param);
	}
}


/**
 * 修改用户信息
 * @param userId
 * @return
 */
function toUpdate(userId){
	_auType = 0;
	resetForm('tblAddUser');
	initUpdateVal(userId);
	$("#btnSave").unbind('click');
	$("#btnSave").bind("click", function() {
		doUpdateUser();
	});
	$("#btnUpdate").unbind();
	$("#btnUpdate").bind("click", function() {
		initUpdateVal(userId);
	});

	_auType = 1;
	$('#divAddUser').dialog('open');
}

/**
 * 根据userId获取用户信息
 * @param userId
 * @return
 */
function initUpdateVal(userId){
	var path=getRootName();
	var uri=path+"/platform/demoUser/findUser";
	var ajax_param={
			url:uri,
			type:"post",
			datdType:"json",
			data:{
				"userid":userId,
				"t":Math.random()
			},
			error:function(){
				$.messager.alert("错误","ajax_error","error");
			},
			success:function(data){
				iterObj(data,"ipt");
			}
		};
	ajax_(ajax_param);
}

/*
 * 更新表格 @author 武林
 */
function reloadTable() {
	var typeValue=$("#txtFilterUserType").combobox('getValue');
	var userAccount = $("#txtuserAccount").val();
	var userName = $("#txtFilterUserName").val();
	var userType = typeValue;
	var isAutoLock = $("#selFilterIsAutoLock").val();
	$('#tblUser').datagrid('options').queryParams = {
		"useraccount" : userAccount,
		"username" : userName,
		"usertype" : userType,
		"isautolock" : isAutoLock
	};
	reloadTableCommon('tblUser');
}


function toAdd(){
	doChoseConstantByTypeName('ipt_usertype','UserType');
	resetForm('tblAddUser');

	$("#btnSave").unbind('click');
	$("#btnSave").bind("click", function() {
		doAddUser();
	});
	$("#btnUpdate").unbind();
	$("#btnUpdate").bind("click", function() {
		resetForm('tblAddUser');
	});

	_auType = 1;
	$('#divAddUser').dialog('open');
}

/*
 * 删除用户 @author 武林
 */
function doDel(userId) {
	var r = confirm("确定删除此用户?")
	if (r == true) {
		var path = getRootName();
		var uri = path + "/platform/demoUser/delUser";
		var ajax_param = {
			url : uri,
			type : "post",
			datdType : "json",
			data : {
				"userid" : userId,
				"t" : Math.random()
			},
			error : function() {
				$.messager.alert("错误", "ajax_error", "error");
			},
			success : function(data) {
				if (true == data || "true" == data) {
					$.messager.alert("提示", "用户删除成功！", "info");
					reloadTable();
				} else {
					$.messager.alert("提示", "用户删除失败！", "error");
				}
			}
		}
		ajax_(ajax_param);
	}
}

