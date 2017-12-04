$(document).ready(function(){
    doInitCollecterTable();
});


/**
 * 初始化未新增的采集机列表
 */
function doInitCollecterTable(){
    var ruleID = $('#ruleID').val();
    var path = getRootName();
    $('#tblAddCollectors').datagrid({
        iconCls: 'icon-edit',// 图标
        width: 'auto',
        height: '250',
        nowrap: false,
        striped: true,
        border: true,
        collapsible: false,// 是否可折叠的
        url: path + '/monitor/syslogRule/getUnIssuedCollectors?ruleID=' + ruleID,
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
        columns: [[{
            field: 'serverid',
            checkbox: true
        }, {
            field: 'ipaddress',
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

/*
 * 更新表格
 */
function reloadUserTable(){
	var ipaddress = $("#txtIPAddress").val();
	$('#tblAddCollectors').datagrid('options').queryParams = {
		"ipaddress" : ipaddress
	};
	reloadTableCommon_1('tblAddCollectors');
}

function reloadTableCommon_1(dataGridId) {
	$('#' + dataGridId).datagrid('load');
	$('#' + dataGridId).datagrid('uncheckAll');
}
/**
 * 新增采集机
 */
function doAddCollectors(){
    //获得采集机id
    var checkedItems = $('#tblAddCollectors').datagrid("getChecked");
    var collectorIds = null;
    $.each(checkedItems, function(index, item){
        var isOffline = '0';
		if(item.isOffline == '1'){
			isOffline = '1';
		}
        if (null == collectorIds) {
            collectorIds = item.serverid+":"+isOffline;
        } else {
            collectorIds += ',' + item.serverid + ":" + isOffline;
        }
    });
    if (collectorIds != "" && collectorIds != null) {
        var ruleID = $('#ruleID').val();
        var path = getRootName();
        var uri = path + "/monitor/syslogRule/doAddCollectors";
        var ajax_param = {
            url: uri,
            type: "post",
            dataType: "json",
            async: false,
            data: {
                "ruleID": ruleID,
				"collectorIds": collectorIds,
                "t": Math.random()
            },
            error: function(){
                $.messager.alert("错误", "ajax_error", "error");
            },
            success: function(data){
                if (data == true || data == "true") {
                    $.messager.alert("提示", "新增新的采集机成功！", "info");
                } else {
                    $.messager.alert("错误", "新增新的采集机失败！", "error");
                }
				$('#popWin').window('close');
            }
        };
        ajax_(ajax_param);
    } else {
        $.messager.alert("提示", "没有任何选中项！", "error");
    }
}
