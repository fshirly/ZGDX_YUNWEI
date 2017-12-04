$(document).ready(function() {
	
	$('#requestMode').combobox({
		panelHeight : '120',
		url : '../dict/readItems?id=3001',
		valueField : 'key',
		textField : 'val',
		editable : false
	});
	
	$('#bookTime').datetimebox({
		required : false,
		showSeconds : true,
		formatter : formatDateText ,
		editable:false,
		formatter : function(date) {
			return formatDate(date, "yyyy-MM-dd hh:mm:ss");
		}
	});
	
	$('#requester').createSelect2({
   		uri : '/permissionSysUser/querySysUserInfo',//获取数据
   		name : 'userName',//显示名称
   		id : 'userID', //对应值值
   		initVal :{requester:$("#requester").attr("value")}
	});
	
	
});

function doAllocate(callback){
	var kind = $("#kind").combobox('getValue');
	//默认是故障，appId=200
	var appId = 200;
	//如果是服务请求，appId=100
	if(1 == kind){
		appId = 100;
	}
	var uri = getRootName() + "/eventManage/doAllocate";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"id" : $("#id").val(),
			"title" : $('#title').val(),
			"requester" : $("#requester").select2("val"),
			"requestMode" : $('#requestMode').combobox("getValue"),
			"bookTime" : $("#bookTime").datetimebox('getValue'),
			"description" : $("#description").val(),
			"kind" : kind,
			"serialNO" : $("#serialNO").val(),
			"mFlag" : $("#mFlag").val(),
			"appId" : appId,
			"t" : Math.random()
		},
		error : function() {
		    $.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			$('#popWin').window('close');
			callback();
//			window.frames['component_2'].$('#dashboard-widget-toBeAllocatedEvent .sDashboard-icon.sDashboard-refresh-icon').click();
		}
	};
	ajax_(ajax_param);
}