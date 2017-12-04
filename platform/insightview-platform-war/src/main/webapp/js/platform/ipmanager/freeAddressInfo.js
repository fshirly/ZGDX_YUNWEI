$(function(){
	toShowInfo();
});
 
//空闲地址列表
function toShowInfo(){
	var subNetIdStr = $("#subNetId").val();
	var subNetId =parseInt(subNetIdStr);
	var startIp = $("#startIp").val();
	var endIp = $("#endIp").val();
	var path = getRootName();
	$('#tblFreeAddress')
			.datagrid(
					{
						iconCls : 'icon-edit',// 图标
						width : 'auto',
						height : 350,
						nowrap : false,
						striped : true,
						border : true,
						collapsible : false,// 是否可折叠的
						fitColumns:true,
						url : path + '/platform/subnetViewManager/listFreeSubnetInfo',
						queryParams : {
							"subNetId" : subNetId,
							"startIp":startIp,
							"endIp": endIp
						},
						remoteSort : false,
						idField : 'fldId',
						singleSelect : false,// 是否单选
						checkOnSelect : true,
						selectOnCheck : true,
						pagination : true,// 分页控件
						rownumbers : true,// 行号
						columns : [ [
						{
							field : 'ipAddress',
							title : 'IP地址',
							align : 'center',
							width : 100,
						},
						{
							field : 'subNetMark',
							title : '子网掩码',
							align : 'center',
							width : 100,
						} ] ]
					});
}

/*
 * 更新
 */
function reloadUserTable() {
	$('#tblFreeAddress').datagrid('options').queryParams = {
		"startIp" : startIp,
		"endIp" : endIp,
		"subNetId":subNetId
	};
	reloadTableCommon('tblFreeAddress');
}

/**
 * 分配空闲ip到部门
 * @return
 */
function  toAssignFreeIds(){
	var subNetIdStr = $("#subNetId").val();
	var subNetId =parseInt(subNetIdStr);
	var startIp = $("#startIp").val();
	var endIp = $("#endIp").val();
	var path = getRootName();
	var uri = path + "/platform/subnetViewManager/getFreeIds";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"subNetId" : subNetId,
			"startIp":startIp,
			"endIp": endIp,
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			doAssign(data);
		}
	};
	ajax_(ajax_param);
}

/**
 * 分配空闲ip到部门
 * @return
 */
function  doAssign(ids){
	var deptId = $("#ipt_deptId").attr("alt");
	var subNetIdStr = $("#subNetId").val();
	var subNetId =parseInt(subNetIdStr);
//	console.log(ids);
	var path = getRootName();
	var uri = path + "/platform/subnetViewManager/doFreeAssignDept";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"ids" : ids,
			"deptId" : deptId,
			"subNetId" :subNetId,
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			if (data == true) {
				$.messager.alert("提示", "分配到部门成功！", "info");
				$('#popWin').window('close');
				$('#popWin2').window('close');
				$('#popWin3').window('close');
				window.frames['component_2'].frames['component_2'].reloadTable();
				window.frames['component_2'].initTree();
			} else {
				$.messager.alert("提示", "分配到部门失败！", "error");
			}
		}
	};
	ajax_(ajax_param);
}
