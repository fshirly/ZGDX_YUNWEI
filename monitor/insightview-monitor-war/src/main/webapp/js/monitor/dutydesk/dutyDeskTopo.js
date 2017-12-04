$(document).ready(function() {	
	$('#ipt_topos').combobox({
		editable : false,
		onSelect:function(record){    
       		setTopoUrl();
		}
	});
	var defaultTopoId = $("#defaultTopoId").val();
	$('#ipt_topos').combobox("setValue",defaultTopoId);
});

/**
 * 跳转至3d机房大图
 */
function toTopoView(){
	var topoId = $('#ipt_topos').combobox("getValue");
	window.open(getRootPatch()+'/topo/toTopoView?id='+topoId);
//	window.open('http://192.168.20.167:8088/insightview/topo/toTopoView?id='+topoId)
}

/**
 * 根据选择的机房切换机房视图
 */
function setTopoUrl(){
	var topoId = $('#ipt_topos').combobox("getValue");
	window.$('#component_2').attr('src',getRootPatch()+'/topo/toTopoMini?topoId='+topoId);
//	window.$('#component_2').attr('src','http://192.168.20.167:8088/insightview/topo/toTopoMini?topoId='+topoId);
}
