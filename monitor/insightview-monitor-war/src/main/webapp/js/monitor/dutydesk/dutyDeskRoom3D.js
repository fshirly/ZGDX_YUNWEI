$(document).ready(function() {	
	$('#ipt_rooms').combobox({
		editable : false,
		onSelect:function(record){    
       		setRoomUrl();
		}
	});
	var defaultRoomId = $("#defaultRoomId").val();
	$('#ipt_rooms').combobox("setValue",defaultRoomId);
});

/**
 * 跳转至3d机房大图
 */
function toRoomView(){
	var roomId = $('#ipt_rooms').combobox("getValue");
	var roomName = $('#ipt_rooms').combobox("getText");
	window.open(getRootPatch()+'/room3dController/room3dView?roomId='+roomId+'&roomName='+roomName);
//	window.open('http://192.168.20.167:8088/insightview/room3dController/room3dView?roomId='+roomId+'&roomName='+roomName)
}

/**
 * 根据选择的机房切换机房视图
 */
function setRoomUrl(){
	var roomId = $('#ipt_rooms').combobox("getValue");
	var roomName = $('#ipt_rooms').combobox("getText");
	window.$('#component_2').attr('src',getRootPatch()+'/room3dController/room3dDutyDeskView?roomId='+roomId+'&amp;viewType=alarmView&amp;drivceType=null');
//	window.$('#component_2').attr('src','http://192.168.20.167:8088/insightview/room3dController/room3dHomeView?roomId=${defaultRoomId}&amp;viewType=alarmView&amp;drivceType=null');
}
