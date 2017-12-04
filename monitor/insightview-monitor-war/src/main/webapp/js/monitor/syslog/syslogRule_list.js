$(document).ready(function(){
    doInitTable();
});

/**
 * 加载页面并初始化表格
 * @return
 */
function doInitTable(){
    var path = getRootName();
    $('#tblSyslogRule').datagrid({
        iconCls: 'icon-edit',// 图标
        width: 700,
        height: 'auto',
        nowrap: false,
        striped: true,
        border: true,
        collapsible: false,// 是否可折叠的
        url: path + '/monitor/syslogRule/listSyslogRules',
        remoteSort: true,
        //        idField: 'ruleID',
        singleSelect: true,// 是否单选
        checkOnSelect: false,
        selectOnCheck: false,
        pagination: true,// 分页控件
        rownumbers: true,// 行号
        fit: true,// 自动大小
        fitColumns: true,
        checkOnSelect: false,
        toolbar: [{
            'text': '新增',
            'iconCls': 'icon-add',
            handler: function(){
                toAdd();
            }
        }, {
            'text': '删除',
            'iconCls': 'icon-cancel',
            handler: function(){
                doBatchDel();
            }
        }],
        columns: [[{
            field: 'ruleID',
            checkbox: true
        }, {
            field: 'ruleName',
            title: '规则名称',
            width: 100,
            align: 'center',
            sortable: true
        }, {
            field: 'alarmOID',
            title: 'syslog事件',
            width: 180,
            align: 'center',
            sortable: true,
            formatter: function(value, row, index){
                if (value && value.length > 27) {
                    value2 = value.substring(0, 27) + "...";
                    return '<span title="' + value + '" style="cursor: pointer;" >' + value2 + '</span>';
                } else {
                    return value;
                }
            }
        }, {
            field: 'filterExpression',
            title: 'syslog过滤表达式',
            width: 250,
            align: 'center',
            sortable: true,
            formatter: function(value, row, index){
                if (value && value.length > 50) {
                    value2 = value.substring(0, 50) + "...";
                    return '<span title="' + value + '" style="cursor: pointer;" >' + value2 + '</span>';
                } else {
                    return value;
                }
            }
        }, {
            field: 'serverIP',
            title: '范围',
            width: 160,
            align: 'center',
            sortable: true,
            formatter: function(value, row, index){
                if (value.indexOf(",") >= 0) {
                    showVal = value.split(",")[0];
                    return showVal + '（' + '<a title="' + value + '" style="cursor: pointer;" >	更多</a>' + '）';
                } else if (value != "*") {
                    return value;
                } else {
                    return "全局";
                }
            }
        }, {
            field: 'note',
            title: '说明',
            width: 200,
            align: 'center',
            sortable: true,
            formatter: function(value, row, index){
                if (value && value.length > 20) {
                    value2 = value.substring(0, 20) + "...";
                    return '<span title="' + value + '" style="cursor: pointer;" >' + value2 + '</span>';
                } else {
                    return value;
                }
            }
        }, {
            field: 'ruleIDs',
            title: '操作',
            width: 130,
            align: 'center',
            formatter: function(value, row, index){
				var dis = "&quot;" + row.ruleID
				+ "&quot;,&quot;" + row.isOffline
				+ "&quot;"
                return '<a style="cursor: pointer;" onclick="javascript:doOpenDetail(' +
                row.ruleID +
                ');"><img src="' +
                path +
                '/style/images/icon/icon_view.png" alt="查看" /></a>&nbsp;<a style="cursor: pointer;" onclick="javascript:toUpdate(' +
                row.ruleID +
                ');"><img src="' +
                path +
                '/style/images/icon/icon_modify.png" alt="修改" /></a>&nbsp;<a style="cursor: pointer;" onclick="javascript:toDel(' +
                row.ruleID +
                ');"><img src="' +
                path +
                '/style/images/icon/icon_delete.png" alt="删除" /></a>&nbsp;<a style="cursor: pointer;" onclick="javascript:toAddCollector(' +
                dis +
                ');"><img src="' +
                path +
                '/style/images/icon/icon_appointcollector.png" alt="新增采集机" /></a>&nbsp;';
                
            }
        }]]
    });
    $(window).resize(function(){
        $('#tblSyslogRule').resizeDataGrid(0, 0, 0, 0);
    });
}

function resetSyslogForm(){
    $("#txtIsAll").removeAttr("checked");
    resetForm('divFilter');
}

/*
 * 更新表格
 */
function reloadTable(){
    var result = checkInfo("#divFilter");
    if (result) {
        var ruleName = $("#txtRuleName").val();
        var serverIP = $("#txtServerIP").val();
        var txtIsAll = $("#txtIsAll").is(":checked");
        var isAll = 0;
        if (txtIsAll == true) {
            isAll = 1;
        }
        //        console.log(isAll);
        $('#tblSyslogRule').datagrid('options').queryParams = {
            "ruleName": ruleName,
            "serverIP": serverIP,
            "isAll": isAll,
        };
        reloadTableCommon('tblSyslogRule');
    }
}

/**
 * 判断是否能删除
 */
function toDel(ruleID){
    $.messager.confirm("提示", "确定删除所选中的项？", function(r){
        if (r == true) {
            var path = getRootName();
            var uri = path + "/monitor/syslogRule/checkIsDel";
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
                    if (null == data || data == "") {
                        doDel(ruleID);
                    } else {
                        $.messager.alert("提示", "该配置规则存在下列syslog任务，不能删除该规则，请先删除syslog任务！<div style='margin-left: 55px; word-wrap: break-word; word-break: normal;'>【" + data + "】</div>", "info");
                    }
                }
            }
            ajax_(ajax_param);
        }
    });
}

/*
 * 删除任务
 */
function doDel(ruleID){
    $.messager.confirm("提示", "确定删除所选中的项？", function(r){
        if (r == true) {
            var path = getRootName();
            var uri = path + "/monitor/syslogRule/delRule";
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
                    if (data == "true" || data == true) {
                        $.messager.alert("提示", "syslog配置删除成功！", "info");
                        reloadTable();
                    } else {
                        $.messager.alert("提示", "syslog配置删除失败！", "error");
                    }
                }
            }
            ajax_(ajax_param);
        }
    });
    
}

/**
 * 批量删除
 * @return
 */
function doBatchDel(){
    var path = getRootName();
    var checkedItems = $('[name=ruleID]:checked');
    var ids = null;
    $.each(checkedItems, function(index, item){
        if (null == ids) {
            ids = $(item).val();
        } else {
            ids += ',' + $(item).val();
        }
    });
    if (null != ids) {
        $.messager.confirm("提示", "确定删除所选中项？", function(r){
            if (r == true) {
                var uri = path + "/monitor/syslogRule/delRules?ruleIDs=" + ids;
                var ajax_param = {
                    url: uri,
                    type: "post",
                    datdType: "json",
                    data: {
                        "t": Math.random()
                    },
                    success: function(data){
                        var undelRuleNames = data.undelRuleNames;
                        var delResult = data.delResult;
                        if (delResult == true && undelRuleNames == "") {
                            $.messager.alert("提示", "syslog配置删除成功！", "info");
                        } else if (delResult == true && undelRuleNames != "") {
                            $.messager.alert("提示", "下列syslog配置存在下发的syslog任务，不能删除,请先删除syslog任务！<div style='margin-left: 55px; word-wrap: break-word; word-break: normal;'>【" + undelRuleNames + "】</div>", "info");
                        } else {
                            $.messager.alert("错误", "syslog配置删除失败！", "error");
                        }
                        reloadTable();
                    }
                };
                ajax_(ajax_param);
            }
        });
    } else {
        $.messager.alert('提示', '没有任何选中项', 'error');
    }
}

/*
 * 打开查看页面
 */
function doOpenDetail(ruleID){
    parent.$('#popWin').window({
        title: 'syslog监测配置详情',
        width: 800,
        height: 480,
        minimizable: false,
        maximizable: false,
        collapsible: false,
        modal: true,
        href: getRootName() + '/monitor/syslogRule/toSyslogRuleDetail?ruleID=' + ruleID
    });
}

/*
 * 打开新增页面
 */
function toAdd(){
    parent.$('#popWin').window({
        title: 'syslog监测配置新建',
        width: 800,
        height: 510,
        minimizable: false,
        maximizable: false,
        collapsible: false,
        modal: true,
        href: getRootName() + '/monitor/syslogRule/toAddSyslogRule'
    });
}

/*
 * 打开编辑页面
 */
function toUpdate(ruleID){
    var path = getRootName();
    var uri = path + "/monitor/syslogRule/checkIsEdit";
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
            if (null == data || data == "") {
                parent.$('#popWin').window({
                    title: 'syslog监测配置编辑',
                    width: 800,
                    height: 510,
                    minimizable: false,
                    maximizable: false,
                    collapsible: false,
                    modal: true,
                    href: getRootName() + '/monitor/syslogRule/toModifySyslogRule?ruleID=' + ruleID
                });
            } else {
                $.messager.alert("提示", "该配置规则下发的syslog任务中存在下列未完成的任务，不能修改该规则！<div style='margin-left: 55px; word-wrap: break-word; word-break: normal;'>【" + data + "】</div>", "info");
            }
        }
    }
    ajax_(ajax_param);
    
}

/*
 * 打开新增采集机页面
 */
function toAddCollector(ruleID,isOffline){
	if(isOffline != "1"){
		isOffline = "0";
	}
    parent.$('#popWin').window({
        title: '新增采集机',
        width: 800,
        height: 510,
        minimizable: false,
        maximizable: false,
        collapsible: false,
        modal: true,
        href: getRootName() + '/monitor/syslogRule/toAddCollector?ruleID=' + ruleID + '&isOffline='+isOffline
    });
}
