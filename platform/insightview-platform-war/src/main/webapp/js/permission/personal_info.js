$(function(){
	var userId=$('#userId').val();
	toShowInfo(userId);
});
 
//查看用户详情
function toShowInfo(userId){
	$(".inputVal").text("");
	setRead(userId);
}

//详情信息
function setRead(userId){
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
			iterObj(data, "lbl");
			iterObj(data, "ipt");
			var userType=$("#lbl_userType").val();
			if (userType==0) {
				$("#isShow1").show();
				$("#isShow2").show();
				$("#isShow3").hide();
				$("#lbl_userType").text("系统管理员");
			} else if(userType==1){
				$("#isShow1").show();
				$("#isShow2").show();
				$("#isShow3").hide();
				$("#lbl_userType").text("企业内IT部门用户");
			}else if(userType==2){
				$("#isShow1").show();
				$("#isShow2").show();
				$("#isShow3").hide();
				$("#lbl_userType").text("企业业务部门用户");
			}else if(userType==3){
				$("#isShow1").hide();
				$("#isShow2").hide();
				$("#isShow3").show();
				$("#lbl_userType").text("外部供应商用户");
				$("#lbl_userType2").text("外部供应商用户");
			}
		}   
	};
	ajax_(ajax_param);
}

function editResume(userId) {
//    var path = getRootName();
//    var uri = path + "/userResume/toResume?userId=" + userId;
//    var width = window.screen.width - 10;
//    var height = window.screen.height - 80;
//    window.open(uri,'编辑个人简历','status=no,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,top=0,left=0,width=' + width + ',height=' + height);
	
	 parent.$('#popWin2').window({
        title:'编辑个人简历',
        width:800,
        height:800,
        minimizable:false,
        maximizable:false,
        collapsible:false,
        modal:true,
        href: getRootName() + "/userResume/toResume?userId=" + userId
    }); 
    
}

//function showCertificateList() {
//	 parent.$('#popWin').window({
//        title:'证书列表',
//        width:800,
//        height:400,
//        minimizable:false,
//        maximizable:false,
//        collapsible:false,
//        modal:true,
//        href: getRootName() + '/personalInfo/toUserCertificateList'
//    }); 
//}

/**
 * 打开修改密码页面
 * 
 * @param userId
 * @return
 */
//function changePwd(userId){
//    // 查看配置项页面
//    parent.$('#popWin').window({
//        title:'修改密码',
//        width:600,
//        minimizable:false,
//        maximizable:false,
//        collapsible:false,
//        modal:true,
//        href: getRootName() + '/personalInfo/toChangePwd'
//    });
//}

function savePersonalInfo() {
	if (checkInfo('#tblUserInfo')){
    	var path = getRootName();
        var uri = path + "/personalInfo/savePersonalInfo";
        var ajax_param = {
            url : uri,
            type : "post",
            datdType : "json",
           data : {
                "userId":$('#userId').val(),
                "mobilePhone":$('#ipt_mobilePhone').val(),
                "telephone":$("#ipt_telephone").val(),
                "email":$("#ipt_email").val(),
                "t" : Math.random()
            },
            error : function() {
                $.messager.alert('提示', '提交失败', 'error');
            },
            success : function(data) {
            	$.messager.alert('提示', '保存成功!', 'info');
            	//$('#personalInfoTabs').tabs('select', '资质证书');
            }
        };
        ajax_(ajax_param);
	}
}

