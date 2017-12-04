$(document).ready(function() {
	$('#ipt_subNetType').combobox({
		panelHeight : '120',
		url : getRootName() + '/platform/subnetViewManager/getAllSubNetType',
		valueField : 'subNetType',
		textField : 'subNetTypeName',
		value : '1',
		editable : false,
	});

});


function toAdd(){
	var result = checkInfo("#tblSubnetInfo");
	if(result == true){
		if(checkMask()){
			checkAddSubnet();
		}else{
			$.messager.alert("提示", "子网掩码不合法，请重新输入！", "info");
			$("#ipt_subNetMark").focus();
		}
	}
}

/**
 * 验证该网段是否已经建立子网
 */
function checkAddSubnet(){
	var ipAddress = $("#ipt_ipAddress").val();
	var subNetMark = $("#ipt_subNetMark").val();
	var path = getRootName();
	var uri = path + "/platform/subnetViewManager/checkAddSubnet";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"ipAddress" : ipAddress,
			"subNetMark" : subNetMark,
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
//			console.log(data);
			if (false == data.isStartIP || "false" == data.isStartIP) {
				$.messager.alert("提示", "输入的子网地址不是标准的子网地址，标准的子网地址为："+data.ipAddress, "info");
			}else{
				if (false == data.isExist || "false" == data.isExist) {
					$.messager.alert("提示", "已有的子网段中包含该网段或者存在覆盖,无法新建,请重新输入！", "info");
				} else {
					if(false == data.isRightNum || "false" == data.isRightNum){
						$.messager.alert("提示", "子网容量至少为4,请重新输入！", "info");
					}else{
						doAdd();
						return;
					}
				}
			}
		}
	};
	ajax_(ajax_param);
}

/**
 * 新增子网
 * @return
 */
function doAdd(){
	var ipAddress = $("#ipt_ipAddress").val();
	var subNetMark = $("#ipt_subNetMark").val();
	var gateway = $("#ipt_gateway").val();
	var dnsAddress = $("#ipt_dnsAddress").val();
	var subNetType = $("#ipt_subNetType").combobox("getValue");
//	console.log("subNetType="+subNetType);
	openWarning();
	var path = getRootName();
	var uri = path + "/platform/subnetViewManager/doAddSubnet";
	var ajax_param = {
		url : uri,
		type : "post",
//		async : true,
		datdType : "json",
		data : {
			"ipAddress" : ipAddress,
			"subNetMark" : subNetMark,
			"gateway" : gateway,
			"dnsAddress" : dnsAddress,
			"subNetType":subNetType,
			"descr" : $("#ipt_descr").val(),
			"t" : Math.random()
		},
		error : function() {
			$("#toAdd").removeAttr("disabled");
			$("#divWarning").dialog('close');
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			$("#toAdd").removeAttr("disabled");
			if (data == true) {
				$("#divWarning").window('close');
				$.messager.alert("提示", "新增子网成功！", "info");
				$('#popWin').window('close');
				window.frames['component_2'].frames['component_2'].reloadTable();
				window.frames['component_2'].initTree();
			} else {
				$("#divWarning").window('close');
				$.messager.alert("提示", "新增子网失败！", "error");
			}
		}
	};
	ajax_(ajax_param);
}

function _checkIput_fomartIP(ip) 
{ 
    return (ip+256).toString(2).substring(1); //格式化输出(补零) 
} 

/**
 * 校验子网掩码
 * @return
 */
function checkMask() 
{ 
	var subNetMark = $("#ipt_subNetMark").val();
    var IPArray = subNetMark.split("."); 
    var ip1 = parseInt(IPArray[0]); 
    var ip2 = parseInt(IPArray[1]); 
    var ip3 = parseInt(IPArray[2]); 
    var ip4 = parseInt(IPArray[3]); 

    if ((ip1 < 0 || ip1 > 255) || (ip2 < 0 || ip2 > 255) || (ip3 < 0 || ip3 > 255) || (ip4 < 0 || ip4 > 255))      
       { 
           return false; 
    } 

    var ip_binary = _checkIput_fomartIP(ip1) + _checkIput_fomartIP(ip2) + _checkIput_fomartIP(ip3) + _checkIput_fomartIP(ip4); 

    if(-1 != ip_binary.indexOf("01")) 
        { 
        return false; 
    } 

    return true; 
} 

function openWarning(){
	$("#toAdd").attr('disabled', true);
	$("#divWarning").show();
	$("#divWarning").dialog({
		title:'提示',
        width:300,
        height : 150,
        minimizable:false,
        maximizable:false,
        collapsible:false,
        modal:true,
	}).dialog('open');
}