/**
 * 获得采集机信息
 */
function getHostInfo(obj){
    var ipaddress = obj.value;
    var path = getRootName();
    var uri = path + "/monitor/offlineCollector/getHostInfo";
    var ajax_param = {
        url: uri,
        type: "post",
        datdType: "json",
        data: {
            "ipaddress": ipaddress,
        },
        error: function(){
            $.messager.alert("错误", "ajax_error", "error");
        },
        success: function(data){
            if (data != null) {
                $("#serverId").val(data.serverid);
                $("#ipt_servername").val(data.servername);
                $("#ipt_natipadress").val(data.natipadress);
                $("#ipt_serverdesc").val(data.serverdesc);
            }
            else {
                $("#serverId").val("");
                $("#ipt_servername").val("");
                $("#ipt_natipadress").val("");
				$("#ipt_serverdesc").val("");
            }
        }
    };
    ajax_(ajax_param);
}

/**
 * 新增
 */
function toEdit(){
    var result = checkInfo("#tblAddOfflineCollector")
    if (result == true) {
        isExist();
    }
}

function isExist(){
    var id = $("#id").val();
	if(id == null || id == ""){
		id = -1;
	}
    var ipaddress = $("#ipt_ipaddress").val();
    var installServiceId = $("#ipt_installServiceId").combobox("getValue");
    var path = getRootName();
    var uri = path + "/monitor/offlineCollector/isExist";
    var ajax_param = {
        url: uri,
        type: "post",
        datdType: "json",
        data: {
            "id": id,
            "ipaddress": ipaddress,
            "installServiceId": installServiceId,
            "isOffline": '1',
        },
        error: function(){
            $.messager.alert("错误", "ajax_error", "error");
        },
        success: function(data){
            if (false == data || "false" == data) {
                editOfflineService();
                return;
            }
            else {
                $.messager.alert("提示", "该离线采集机服务已存在！", "error");
            }
        }
    };
    ajax_(ajax_param);
}

/**
 * 新增/更新
 */
function editOfflineService(){
	var oldIpaddress = $("#oldIpaddress").val();
	var oldServerId = $("#oldServerId").val();
    var id = $("#id").val();
    if (id == "" || id == null) {
        id = -1
    }
	var serverId = $("#serverId").val();
    if (serverId == "" || serverId == null) {
        serverId = -1
    }
    var ipaddress = $("#ipt_ipaddress").val();
    var installServiceId = $("#ipt_installServiceId").combobox("getValue");
    var path = getRootName();
    var uri = path + "/monitor/offlineCollector/editOfflineService?oldServerId="+oldServerId;
    var ajax_param = {
        url: uri,
        type: "post",
        datdType: "json",
        data: {
            "id": id,
            "serverid": serverId,
            "ipaddress": ipaddress,
            "installServiceId": installServiceId,
            "servername": $("#ipt_servername").val(),
            "natipadress": $("#ipt_natipadress").val(),
            "serverdesc": $("#ipt_serverdesc").val(),
            "isOffline": '1',
        },
        error: function(){
            $.messager.alert("错误", "ajax_error", "error");
        },
        success: function(data){
            if (true == data || "true" == data) {
                $('#popWin').window('close');
                window.frames['component_2'].reloadTable();
            }
            else {
				if (id == -1) {
                	$.messager.alert("提示", "离线采集机服务添加失败！", "error");
				}else{
                	$.messager.alert("提示", "离线采集机服务编辑失败！", "error");
				}
            }
        }
    };
    ajax_(ajax_param);
}


