<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/style/common/include_css.css" />

<!-- mainframe -->
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/common/jquery-1.9.1.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/common/commonUtil.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/plugin/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/style/style_one/js/main.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/plugin/autocomplete/jquery.autocomplete.js"></script>

<script language="javascript">
	function doCheck() {
		alert(_u_sel_auto_val);
	}

	$(document)
			.ready(
					function() {
						//页面初始化的时候初始化控件
						doGetUserByName('',
								'txtFilterUserAccount,txtFilterUserAccountTwo,txtFilterUserAccountThree');
					});

	//保存用户选择数据的ID
	var _u_sel_auto_val = "";

	/**
	 * autoComplate插件配置
	 * 
	 * @parent 1
	 * @since 2012-7-13
	 * @author 武林
	 */
	function doGetUserByName(userAccount, idAttr) {
		var path = getRootPatch();
		var uri = path + "/permissionSysUser/queryUserByAuto";
		var ajax_param = {
			url : uri,
			type : "post",
			datdType : "json",
			data : {
				"userAccount" : userAccount,
				"t" : Math.random()
			},
			error : function() {
				alert("ajax_error");
			},
			success : function(data) {
				if (data != "") {
					var userList = eval('(' + data.userListJson + ')');
					var idAttrArr = idAttr.split(',');
					for ( var idAttrTemp in idAttrArr) {
						doTestOne(idAttrArr[idAttrTemp], userList);
					}
				}
			}
		}
		ajax_(ajax_param);
	}

	// 输入联想用户的点击事件
	function autoEventClick(uSelValTemp, $input) {
		var uSelValTempArr = uSelValTemp.split(':');
		$input.val(uSelValTempArr[0]);
		$input.attr('alt', uSelValTempArr[1]);
	}

	function doTestOne(idval, userLstJson) {
		$("#" + idval).autocomplete(userLstJson, {
			minChars : 1,
			max : 9000000,
			autoFill : false,
			mustMatch : false,
			matchContains : true,
			scrollHeight : 220,
			formatItem : function(row, i, max) {
				return row.userAccount;
			},
			formatMatch : function(row, i, max) {
				return row.userAccount;
			},
			formatResult : function(row) {
				return row.userAccount + ":" + row.userID;
			}
		});
	}
</script>

</head>
<body>

	<table width="100%">
		<tr>
			<td><input type="text" class="inputs" id="txtFilterUserAccount" />
				<input type="text" class="inputs" id="txtFilterUserAccountTwo" /> <input
				type="text" class="inputs" id="txtFilterUserAccountThree" />
				<button onclick="doCheck();">测试</button></td>
		</tr>
		<tr>
			<td id="li_bn"></td>
		</tr>
	</table>
</body>
</html>