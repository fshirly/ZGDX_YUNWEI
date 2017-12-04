$(document).ready(function(){
    initRuleDetail();
	doInitCollecterTable();
});

/**
 * 初始化syslog规则信息
 */
function initRuleDetail(){
    var ruleID = $('#ruleID').val();
    var path = getRootName();
    var uri = path + "/monitor/syslogRule/initRuleDetail";
    var ajax_param = {
        url: uri,
        type: "post",
        datdType: "json",
        data: {
            "ruleID": ruleID,
            "t": Math.random()
        },
        error: function(){
            $.messager.alert("错误", "ajax_error", "error");
        },
        success: function(data){
            iterObj(data, "lbl");
            var progressStatus = data.progressStatus;
            var serverIP = data.serverIP;
            if (serverIP != "*") {
                $("#lbl_serverIP").text(serverIP);
            } else {
                $("#lbl_serverIP").text("全局");
            }
        }
    };
    ajax_(ajax_param);
}

/**
 * 初始化采集机列表
 */
function doInitCollecterTable(){
	var ruleID = $('#ruleID').val();
    var path = getRootName();
    $('#tblViewCollector').datagrid({
        iconCls: 'icon-edit',// 图标
        width: 'auto',
        height: 'auto',
        nowrap: false,
        striped: true,
        border: true,
        collapsible: false,// 是否可折叠的
        url: path + '/monitor/syslogRule/getIssuedCollectors?ruleID='+ruleID,
        remoteSort: true,
        idField: 'serverid',
        singleSelect: false,// 是否单选
        checkOnSelect: false,
        selectOnCheck: true,
//        pagination: true,// 分页控件
        rownumbers: true,// 行号
        //        fit: true,// 自动大小
        fitColumns: true,
        checkOnSelect: false,
		scrollbarSize : 0,
        columns: [[{
            field: 'collectorName',
            title: '采集机IP',
            width: 150,
            align: 'center',
            sortable: true
        }, {
            field: 'isOffline',
            title: '采集机类型',
            width: 150,
            align: 'center',
			formatter : function(value, row) {
				if(value == '1'){
					return "离线";
				}
				return "在线";
			}
        }]]
    });
}