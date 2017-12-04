$(function(){     
	var userId=$('#ipt_userID').val();
	var isAdmin = $("#isAdmin").val();
	var flag=true;
	$("#ipt_deptName").combotree({
		url:getRootPatch()+"/permissionSysUser/findDeptAndOrgTreeValAjax",
		lines:true,
		panelWidth:400,
		panelHeight:300,
		onClick:function(node){
			toAsycDeptPost(node.id);
			$("#ipt_deptName").combotree("showPanel");
			$("#ipt_organizationID").val(node.attributes.organizationID);
			$("#ipt_deptId").val(node.attributes.deptId);
			
		},
		onLoadSuccess:function(node, data){
			if(flag){
				toUpdate(userId);
				flag=false;
			}
			
		}
	});
	/*if(isAdmin == false || isAdmin == "false"){
		doChoseDeptGrade();
	}*/
	
	//toDivAllotPost(userId);
    
    /*$.fn.LRSelect("selLeft", "selRight", "img_L_AllTo_R", "img_L_To_R",
            "img_R_To_L", "img_R_AllTo_L");
    $.fn.UpDownSelOption("imgUp", "imgDown", "selRight");   */
});

var accountFlag=true;
function toUpdate(userId) {
	_auType = 0;
	$(".inputVal").val("");
	initUpdateVal(userId);
}


function doUpdate() {
	checkSysUser();
	/*
	var result = checkFormInfo();
	var providerId=$("#ipt_providerName").attr("alt");
	var gradeInfo=$("#ipt_gradeID").val();
	if(gradeInfo != null){
		var gradeId=gradeInfo.split("O")[0];
		$("#ipt_organizationID").val(gradeInfo.split("O")[1]);
	}
	var path = getRootName();
	var uri = path + "/permissionSysUser/updateSysUser";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"userID" : $("#ipt_userID").val(),
			"userAccount" : $("#ipt_userAccount").val(),
			"userName" : $("#ipt_userName").val(),
			"userPassword" : $("#ipt_userPassword").val(),
			"mobilePhone" : $("#ipt_mobilePhone").val(),
			"email" : $("#ipt_email").val(),
			"telephone" : $("#ipt_telephone").val(),   
			"userType" : $("#ipt_userType").val(),
			"isAutoLock" : $("#ipt_isAutoLock").val(),
			"status" : $("#ipt_status").val(),
			"lockedReason" : $("#ipt_lockedReason").val(),
			"sysEmploymentBean.employeeCode":$("#ipt_employeeCode").val(),
			"sysEmploymentBean.organizationID":$("#ipt_organizationID").val(),
			"sysEmploymentBean.deptID":$("#ipt_deptId").val(),
			"sysEmploymentBean.gradeID":gradeId,
			"sysEmploymentBean.empId":$("#ipt_empId").val(),
			"sysEmploymentBean.userId":$("#ipt_userID").val(),
			"sysProviderUserBean.userId":$("#ipt_userID").val(),
			"sysProviderUserBean.Id":$("#ipt_proUserId").val(), 
			"sysProviderUserBean.providerId":providerId, 
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			if (true == data || "true" == data) {
				$.messager.alert("提示", "用户修改成功！", "info");
				$('#popWin').window('close');
				window.frames['component_2'].reloadTable();
				window.frames['component_2'].initOrgTree();
			} else {
				$.messager.alert("提示", "用户修改失败！", "error");
				window.frames['component_2'].reloadTable();
			}
		}
	};
	if (result == true) {
		ajax_(ajax_param);
	}

*/}



function initUpdateVal(userId) {
	var path = getRootName();
	var uri = path + "/permissionSysUser/findSysUser";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"userID" : userId,
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			iterObj(data, "ipt");
			var pwd=$("#ipt_userPassword").val();
			$("#confirmPassword").val(pwd);
			$("#ipt_deptName").combotree('setText',data.deptName);
			$("#ipt_deptId").val(data.sysEmploymentBean.deptID);
			$("#ipt_organizationID").val(data.sysEmploymentBean.organizationID);
			/*var userType=$("#ipt_userType").val();
			$("input:radio[name='userType'][value='"+userType+"']").attr("checked",'checked');*/
			/*var providerId=$("#ipt_providerId").val();*/
			//edit();
			/*if($("#ipt_deptId").val() != 0){
				doInitDeptGrade();
			}*/
			/*if(providerId != null){
				$("#ipt_providerName").attr("alt",providerId);
			}*/
			$('#userAccountHide').val(data.userAccount);
			 
		}   
	};
	ajax_(ajax_param);
}


function checkFormInfo()
{	
	/*var radioChecked = $(':radio:checked');
	if(radioChecked.val()=='3'){
		$('#ipt_providerName').attr('validator',"{'default':'*'}");
		$('#ipt_employeeCode').removeAttr('validator');
		$('#ipt_deptName').removeAttr('validator');
	}else{
		$('#ipt_employeeCode').attr('validator',"{'default':'*'}");
		$('#ipt_deptName').attr('validator',"{'default':'*'}");
		$('#ipt_providerName').removeAttr('validator');
	}*/
	return checkInfo('#tblModifyUser');
}

/**
 * 根据所选用户类型不一样，显示对应的编辑页面
 * @return
 */
/*function edit(){
	var oTable = document.getElementById('tblAddUser');
	var radioChecked = $(':radio:checked');
	if(radioChecked.val()=='3'){
		$('#ipt_userType').val(radioChecked.val());
		$("#employeeInfos").hide();
		$("#employeeGrade").hide();
		$("#providerInfos").show();
		
		$('#sysUserTabs').tabs('disableTab', 1);
	}else {
		$('#ipt_userType').val(radioChecked.val());
		$("#employeeInfos").show();
		$("#employeeGrade").show();
		$("#providerInfos").hide();
		
		$('#sysUserTabs').tabs('enableTab', 1);
	}
	
}*/


//修改时初始化职位等级
/*function doInitDeptGrade(){
	var isAdmin = $("#isAdmin").val();
	var deptId=$("#ipt_deptId").val();
	if(isAdmin == true || isAdmin == "true"){
		var id = deptId;
	}else{
		var id = -1;
	}
	var currentUserOrgId = $("#currentUserOrgId").val();
	var path = getRootPatch();
	var uri = path + "/permissionSysUser/findDeptGradeTreeVal?deptId="+id+"&organizationId="+currentUserOrgId;
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"trmnlBrandNm" : "",
			"qyType" : "brandName",
//			"deptId":deptId, 
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {

			// 得到树的json数据源
			var jsonData = eval('(' + data.menuLstJson + ')');
			// 遍历数据
			var gtmdlToolList = jsonData;
			for (var i = 0; i < gtmdlToolList.length; i++) {
				var _id = gtmdlToolList[i].gradeID;
				var _name = gtmdlToolList[i].gradeName;
				var _orgId=gtmdlToolList[i].organizationID;
				if($("#ipt_gradeId").val()==_id){
					$("#ipt_gradeID").append("<option value='"+_id+"O"+_orgId+"' selected>"+_name+"</option>");  
				}else{
					$("#ipt_gradeID").append("<option value='"+_id+"O"+_orgId+"'>"+_name+"</option>");  
				}
				   
			}
		}
	}
	ajax_(ajax_param);
}
*/

/*function doChoseProvider(){ 
	var path = getRootPatch();
	var uri = path + "/platform/provider/findProvideTreeVal";
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
			dataProTree = new dTree("dataProTree", path + "/plugin/dTree/img/");
			dataProTree.add(0, -1, "供应商", "");

			// 得到树的json数据源
			var jsonData = eval('(' + data.menuLstJson + ')');
			_treeDataPro = jsonData;
			// 遍历数据
			var gtmdlToolList = jsonData;
			for (var i = 0; i < gtmdlToolList.length; i++) {
				var _id = gtmdlToolList[i].providerId;
				var _name = gtmdlToolList[i].providerName;
				var _parent = 0;
				dataProTree.add(_id, _parent, _name,
						"javascript:hiddenDTreeSetValEasyUi('divChoseProvider','ipt_providerName','"
						+ _id + "','" + _name + "');");
			}
			$('#dataTreeProviders').empty();
			$('#dataTreeProviders').append(dataProTree + "");
		$('#divChoseProvider').dialog('open');
		}
	}
	ajax_(ajax_param);
}
*/ 
/*function doChoseDept(){
	var path = getRootPatch();
	var isAdmin = $("#isAdmin").val();
	if(isAdmin == true || isAdmin == "true"){
		var uri = path + "/permissionSysUser/findDeptTreeVal";
	}else{
		var organizationId = $("#currentUserOrgId").val();
		var uri = path + "/permissionSysUser/findDeptTreeVal?organizationId="+organizationId;
	}
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
			deptDataTree = new dTree("deptDataTree", path + "/plugin/dTree/img/");
			deptDataTree.add(0, -1, "部门", "");

			// 得到树的json数据源
			var jsonData = eval('(' + data.menuLstJson + ')');
			// 遍历数据
			var gtmdlToolList = jsonData;
			for (var i = 0; i < gtmdlToolList.length; i++) {
				var _id = gtmdlToolList[i].deptId;
				var _name = gtmdlToolList[i].deptName;
				var _parent = gtmdlToolList[i].parentDeptID;
				
				deptDataTree.add(_id, _parent, _name, "javascript:hiddenGradeDTreeSetValEasyUi('divChoseDept','ipt_deptName','"
						+ _id + "','" + _name + "');");
			}
			$('#dataTreeDepts').empty();
			$('#dataTreeDepts').append(deptDataTree + "");
			$('#divChoseDept').dialog('open');
			
		}
	}
	ajax_(ajax_param);
	
}
*/
/*function hiddenGradeDTreeSetValEasyUi(divId, controlId, showId, showVal) {
	$("#" + controlId).val(showVal);
	$("#" + controlId).attr("alt", showId);
	$("#" + divId).dialog('close');
	var deptId=$('#ipt_deptName').attr("alt");
	$("#ipt_deptId").val(deptId);
	var isAdmin = $("#isAdmin").val();
	if(isAdmin == true || isAdmin == "true"){
		doChoseDeptGrade();
	}
}*/

/*function doChoseDeptGrade(){
	var isAdmin = $("#isAdmin").val();
	var currentUserOrgId = $("#currentUserOrgId").val();
	var deptId=$('#ipt_deptName').attr("alt");
	$("#ipt_deptId").val(deptId);
	var path = getRootPatch();
	var id = -1;
	if((isAdmin == true || isAdmin == "true") && deptId != undefined){
		id = $("#ipt_deptId").val();
	}
	var uri = path + "/permissionSysUser/findDeptGradeTreeVal?deptId="+id+"&organizationId="+currentUserOrgId;
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

			// 得到树的json数据源
			var jsonData = eval('(' + data.menuLstJson + ')');
			// 遍历数据
			var gtmdlToolList = jsonData;
			$("#ipt_gradeID").empty();
			for (var i = 0; i < gtmdlToolList.length; i++) {
				var _id = gtmdlToolList[i].gradeID;
				var _name = gtmdlToolList[i].gradeName;
				var _orgId=gtmdlToolList[i].organizationID;
				$("#ipt_gradeID").append("<option value='"+_id+"O"+_orgId+"'>"+_name+"</option>");     
			}
		}
	}
	ajax_(ajax_param);
}*/

//检查新密码与重复密码是否一致
function checkConfirmPwd(){
	var pwd=$("#ipt_userPassword").val();
	if(pwd=="" || pwd==null){
		$.messager.alert("提示", "用户密码不能为空！", "error");
	}else{
		var confirmPwd=$("#confirmPassword").val();
		if(confirmPwd != pwd){
			$.messager.alert("提示", "两次密码输入不一致，请重新输入！", "error");
			$("#confirmPassword").val("");
		}
	}
	
}


//判断用户名是否存在
function checkSysUser() {
		/*var radioChecked = $(':radio:checked');*/
		var userAccountHide = $('#userAccountHide').val();
		var userAccount = $("#ipt_userAccount").val();
		if(userAccount != userAccountHide){
			var userId=$('#ipt_userID').val();
			var path = getRootName();
			var uri = path + "/permissionSysUser/checkSysUserModify";
			var ajax_param = {
				url : uri,
				type : "post",
				datdType : "json",
				data : {
					"userAccount" : userAccount,
					"userID" : userId,
					"t" : Math.random()
				},
				error : function() {
					$.messager.alert("错误", "ajax_error", "error");
				},
				success : function(data) {
//					alert(data);
					if (true == data || "true" == data) {
						accountFlag=true;
						$.messager.alert("提示", "该用户名已存在！", "error",function(e) {
							$("#ipt_userAccount").focus();
						});
					}else{
						/*var valArr = "";
                        $("#selRight option").each(function() {
                            var val = $(this).val(); // 获取单个value
                            valArr += val + ",";
                        });*/
                    
						//用户名没有存在，执行修改操作
						accountFlag=false;
						var result = checkFormInfo();
						//var providerId=$("#ipt_providerName").attr("alt");
						//var gradeInfo=$("#ipt_gradeID").val();
						/*if(gradeInfo != null){
							var gradeId=gradeInfo.split("O")[0];
							$("#ipt_organizationID").val(gradeInfo.split("O")[1]);
						}*/
						var path = getRootName();
						var uri = path + "/permissionSysUser/updateSysUser";
						var ajax_param = {
							url : uri,
							type : "post",
							datdType : "json",
							data : {
								"userID" : $("#ipt_userID").val(),
								"userAccount" : $("#ipt_userAccount").val(),
								"userName" : $("#ipt_userName").val(),
								"userPassword" : $("#ipt_userPassword").val(),
								"mobilePhone" : $("#ipt_mobilePhone").val(),
								"email" : $("#ipt_email").val(),
								"iDCard": $("#ipt_idcard").val(),
								"telephone" : $("#ipt_telephone").val(),   
								/*"userType" : $("#ipt_userType").val(),*/
								"isAutoLock" : $("#ipt_isAutoLock").val(),
								"status" : $("#ipt_status").val(),
								"lockedReason" : $("#ipt_lockedReason").val(),
								"sysEmploymentBean.employeeCode":$("#ipt_employeeCode").val(),
								"sysEmploymentBean.organizationID":$("#ipt_organizationID").val(),
								"sysEmploymentBean.deptID":$("#ipt_deptId").val(),
								//"sysEmploymentBean.gradeID":gradeId,
								"sysEmploymentBean.empId":$("#ipt_empId").val(),
								"sysEmploymentBean.userId":$("#ipt_userID").val(),
								"sysProviderUserBean.userId":$("#ipt_userID").val(),
								"sysProviderUserBean.Id":$("#ipt_proUserId").val(), 
								"sysProviderUserBean.providerId":providerId,
								/*"strPostList" : valArr,*/
								"t" : Math.random()
							},
							error : function() {
								$.messager.alert("错误", "ajax_error", "error");
							},
							success : function(data) {
								if (true == data || "true" == data) {
									$.messager.alert("提示", "用户修改成功！", "info");
									$('#popWin').window('close');
									window.frames['component_2'].reloadTable();
									//window.frames['component_2'].initOrgTree();
								} else {
									$.messager.alert("提示", "用户修改失败！", "error");
									window.frames['component_2'].reloadTable();
								}
							}
						};


						if (result == true && accountFlag==false) {
							/*if($('#ipt_email').val() == ""){
								$.messager.alert("提示", "邮箱不能为空！", "info");
							}else*//* if($('#ipt_gradeID').val() == null && radioChecked.val()!='3'){
								$.messager.alert("提示", "职位级别不能为空！", "info");
							}else{*/
								ajax_(ajax_param);
							//}
							
						}
						
					} 
				}
			};
			ajax_(ajax_param);
		} else {
			var valArr = "";
            $("#selRight option").each(function() {
                var val = $(this).val(); // 获取单个value
                valArr += val + ",";
            });
                        
			//用户名没有存在，执行修改操作
			accountFlag=false;
			var result = checkFormInfo();
			var providerId=$("#ipt_providerName").attr("alt");
			var gradeInfo=$("#ipt_gradeID").val();
			if(gradeInfo != null){
				var gradeId=gradeInfo.split("O")[0];
				$("#ipt_organizationID").val(gradeInfo.split("O")[1]);
			}
			var path = getRootName();
			var uri = path + "/permissionSysUser/updateSysUser";
			var ajax_param = {
				url : uri,
				type : "post",
				datdType : "json",
				data : {
					"userID" : $("#ipt_userID").val(),
					"userAccount" : $("#ipt_userAccount").val(),
					"userName" : $("#ipt_userName").val(),
					"userPassword" : $("#ipt_userPassword").val(),
					"mobilePhone" : $("#ipt_mobilePhone").val(),
					"email" : $("#ipt_email").val(),
					"iDCard": $("#ipt_idcard").val(),
					"telephone" : $("#ipt_telephone").val(),   
					"userType" : $("#ipt_userType").val(),
					"isAutoLock" : $("#ipt_isAutoLock").val(),
					"status" : $("#ipt_status").val(),
					"lockedReason" : $("#ipt_lockedReason").val(),
					"sysEmploymentBean.employeeCode":$("#ipt_employeeCode").val(),
					"sysEmploymentBean.organizationID":$("#ipt_organizationID").val(),
					"sysEmploymentBean.deptID":$("#ipt_deptId").val(),
					"sysEmploymentBean.gradeID":gradeId,
					"sysEmploymentBean.empId":$("#ipt_empId").val(),
					"sysEmploymentBean.userId":$("#ipt_userID").val(),
					"sysProviderUserBean.userId":$("#ipt_userID").val(),
					"sysProviderUserBean.Id":$("#ipt_proUserId").val(), 
					"sysProviderUserBean.providerId":providerId,
					"strPostList" : valArr, 
					"t" : Math.random()
				},
				error : function() {
					$.messager.alert("错误", "ajax_error", "error");
				},
				success : function(data) {
					if (true == data || "true" == data) {
						$.messager.alert("提示", "用户修改成功！", "info");
						$('#popWin').window('close');
						window.frames['component_2'].reloadTable();
						//window.frames['component_2'].initOrgTree();
					} else {
						$.messager.alert("提示", "用户修改失败！", "error");
						window.frames['component_2'].reloadTable();
					}
				}
			};


			if (result == true && accountFlag==false) {
				/*if($('#ipt_email').val() == ""){
					$.messager.alert("提示", "邮箱不能为空！", "info");
				}else *//*if($('#ipt_gradeID').val() == null && radioChecked.val()!='3'){
					$.messager.alert("提示", "职位级别不能为空！", "info");
				}else{*/
					ajax_(ajax_param);
				//}
				
			}
		}
}
function toAsycDeptPost(id){
	var path = getRootName();
	var uri = path + "/permissionSysUser/findDeptAndOrgTreeValAjax";
	var ajax_param = {
	        url : uri,
	        type : "get",
	        datdType : "json",
	        data : {
	            "organizationId" : id,
	            "t" : Math.random()
	        },
	        error : function() {
	            $.messager.alert("错误", "ajax_error", "error");
	        },
	        success : function(bb) {
	        	var t = $('#ipt_deptName').combotree('tree');	// get the tree object
	        	var n = t.tree('getSelected');
	            t.tree("append",{'parent': n.target, 'data':bb})
	        }
	    };
	    ajax_(ajax_param);
}
/*
 * 分配岗位
 */
/*function toDivAllotPost(userId) {
    $("#selLeft").empty();
    $("#selRight").empty();
    var path = getRootName();
    var uri = path + "/permissionSysUser/queryAvailAndAddedPost";
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
            for (var i = 0; i < data.availPostList.length; i++) {
                $("#selLeft").append(
                        "<option value='" + data.availPostList[i].postID + "'>"
                                + data.availPostList[i].postName + "</option>");
            }

            for (var i = 0; i < data.addedPostList.length; i++) {
                $("#selRight").append(
                        "<option value='" + data.addedPostList[i].postID + "'>"
                                + data.addedPostList[i].postName + "</option>");
            }

            $('#divAllotRole').dialog('open');
        }
    };
    ajax_(ajax_param);
}
*/
/*function openResume(userId) {
    var uri = getRootName() + "/userResume/isResumeAdded";
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
            if(data == 'false') {
            	$.messager.alert('提示', '该用户尚未添加个人简历信息!', 'info');
            } else {
            	var uri = getRootName() + "/userResume/toResumeView?userId=" + userId;
                var width = window.screen.width - 10;
                var height = window.screen.height - 80;
                window.open(uri,'查看个人简历','status=no,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,top=0,left=0,width=' + width + ',height=' + height);
            }
        }
    };
    ajax_(ajax_param);
}
*/