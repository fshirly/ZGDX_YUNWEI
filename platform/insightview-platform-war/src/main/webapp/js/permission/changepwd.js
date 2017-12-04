//修改密码
function changePwd(){
	if (checkInfo('#tblChangePwd')){
    	if(checkPwd()) {
        	var path = getRootName();
        	var uri = path + "/personalInfo/changePwd";
        	var ajax_param = {
        		url : uri,
        		type : "post",
        		datdType : "json",
        		data : {
        			"oldPwd":$("#oldPwd").val(),
        			"newPwd":$("#newPwd").val(),
        			"t" : Math.random()
        		},
        		error : function() {
        			$.messager.alert("错误", "ajax_error", "error");
        		},
        		success : function(data) {
        			if (data == "ok") {
        				$('#popWin').window('close');
        			} else if (data == "1") {
                        $.messager.alert("提示", "原密码输入不匹配", "info");
                    }
        		}
        	};
        	ajax_(ajax_param);
    	}
	}
}

//检查新密码与重复密码是否一致
function checkPwd(){
	var oldPwd=$("#oldPwd").val();
	var newPwd=$("#newPwd").val();
	var repeatPwd=$("#repeatPwd").val();
	if(oldPwd == newPwd){
        $.messager.alert("提示", "新密码不能与原密码相同，请重新输入！", "info");
        return false;
    } else if(repeatPwd != newPwd){
		$.messager.alert("提示", "两次密码输入不一致，请重新输入！", "info");
		return false;
	} else if(newPwd.length < 6){
        $.messager.alert("提示", "密码长度过短,至少6位！", "info");
        return false;
    } else if(newPwd.length > 18){
        $.messager.alert("提示", "密码长度过长,不能超过18位！", "info");
        return false;
    } else {
		return true;
	}
}
