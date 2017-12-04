$(function(){
	var userId=$('#ipt_userID').val();
	toShowInfo(userId);
	
	//toDivAllotPost(userId);
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
			var userType=$("#lbl_userType").val();
			if(userType==0) {
				$("#isShow1").show();
				$("#isShow2").show();
				$("#isShow3").hide();
				$("#lbl_userType").text("系统管理员");
			}else if(userType==1){
				$("#isShow1").show();
				$("#isShow2").show();
				$("#isShow3").hide();
				$("#lbl_userType").text("IT部门用户");
			}else if(userType==2){
				$("#isShow1").show();
				$("#isShow2").show();
				$("#isShow3").hide();
				$("#lbl_userType").text("业务部门用户");
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
            for (var i = 0; i < data.addedPostList.length; i++) {
                $("#selRight").append(
                        "<option value='" + data.addedPostList[i].postID + "'>"
                                + data.addedPostList[i].postName + "</option>");
            }

            $('#divAllotRole').dialog('open');
        }
    };
    ajax_(ajax_param);
}*/

function openResume(userId) {
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
