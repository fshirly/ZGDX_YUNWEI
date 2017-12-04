	function doCheck() {
		alert(_u_sel_auto_val);
	}

	$(document).ready(function() {
		//页面初始化的时候初始化控件
		doGetUserByName('');
	});

	//保存用户选择数据的ID
	var _u_sel_auto_val = "";

	//输入联想用户的点击事件
	function autoEventClick(uSelValTemp,$input) {
		autoEventClickCommonRadio(uSelValTemp,$input);
		//autoEventClickCommonMultiple(uSelValTemp, 'li_bn',$input);
	}
	/**
	 * autoComplate插件配置
	 * 
	 * @parent 1
	 * @since 2012-7-13
	 * @author 武林
	 */
	function doGetUserByName(userName) {
		var path = getRootPatch();
		var uri = path + "/permissionSysUser/queryUserByAuto";
		var ajax_param = {
			url : uri,
			type : "post",
			datdType : "json",
			data : {
				"userName" : userName,
				"t" : Math.random()
			},
			error : function() {
				alert("ajax_error");
			},
			success : function(data) {
				if (data != "") {
					var userList = eval('(' + data.userListJson + ')');
					$("#txtFilterUserAccount").autocomplete(userList, {
						minChars : 1,
						max : 9000000,
						autoFill : false,
						mustMatch : false,
						matchContains : true,
						scrollHeight : 220,
						formatItem : function(row, i, max) {
							return row.userName;
						},
						formatMatch : function(row, i, max) {
							return row.userAccount;
						},
						formatResult : function(row) {
							return row.userName + ":" + row.userID;
						}
					});
				}
			}
		};
		ajax_(ajax_param);
	}