var timerId1;
var timerId2;
$(document).ready(function(){
    doInitTable();
    //每隔0.5秒自动调用方法，实现操作进度及错误信息的实时更新
    timerId1 = window.setInterval(getOpProcess, 5000);
    timerId2 = window.setInterval(getOperateStatus, 5000);
});

/**
 * 加载页面并初始化表格
 * @return
 */
function doInitTable(){
    var path = getRootName();
    $('#tblOfflineSyslogTask').datagrid({
        iconCls: 'icon-edit',// 图标
        width: 700,
        height: 'auto',
        nowrap: false,
        striped: true,
        border: true,
        collapsible: false,// 是否可折叠的
        url: path + '/monitor/syslogTask/listOfflineSyslogTasks',
        singleSelect: true,// 是否单选
        checkOnSelect: false,
        selectOnCheck: false,
        pagination: true,// 分页控件
        rownumbers: true,// 行号
        fit: true,// 自动大小
        fitColumns: true,
        toolbar: [{
            'text': '删除',
            'iconCls': 'icon-cancel',
            handler: function(){
                doBatchDel();
            }
        }, {
            'text': '批量重发',
            'iconCls': 'icon-batchSend',
            handler: function(){
                doBatchResend();
            }
        }],
        columns: [[{
            field: 'taskID',
            checkbox: true
        }, {
            field: 'taskId',
            title: '任务ID',
            width: 100,
            align: 'center',
            formatter: function(value, row, index){
                return row.taskID;
            }
        }, {
            field: 'ruleName',
            title: '规则名称',
            width: 100,
            align: 'center',
            sortable: true
        }, {
            field: 'collectorName',
            title: '所属采集机',
            width: 150,
            align: 'center',
            sortable: true,
            formatter: function(value, row, index){
                if (value == null || value == "null" || value == "") {
                    return "";
                } else {
                    return value;
                }
            }
        }, {
            field: 'createTime',
            title: '任务创建时间',
            width: 130,
            align: 'center',
            sortable: true,
            formatter: function(value, row, index){
                if (value != null && value != null) {
                    return row.createTime.split("\.")[0];
                } else {
                    return "";
                }
                
            }
        }, {
            field: 'lastStatusTime',
            title: '最近状态时间',
            width: 130,
            align: 'center',
            sortable: true,
            formatter: function(value, row, index){
                if (value != null && value != null) {
                    return row.lastStatusTime.split("\.")[0];
                } else {
                    return "";
                }
                
            }
        }, {
            field: 'taskIDs',
            title: '操作',
            width: 130,
            align: 'center',
            formatter: function(value, row, index){
                if (row.progressStatus >= 1 && row.progressStatus < 5) {
                    return '<a style="cursor: pointer;" onclick="javascript:doOpenDetail(' +
                    row.taskID +
                    ');"><img src="' +
                    path +
                    '/style/images/icon/icon_view.png" alt="查看" /></a>&nbsp;<a href="javascript:doResend(' +
                    row.taskID +
                    ');" id="btnResend" class="fltrt"><img src =" ' +
                    path +
                    '/style/images/icon/icon_sent.png" title="重发" /></a>';
                    
                } else {
                    return '<a style="cursor: pointer;" onclick="javascript:doOpenDetail(' +
                    row.taskID +
                    ');"><img src="' +
                    path +
                    '/style/images/icon/icon_view.png" alt="查看" /></a>&nbsp;<a style="cursor: pointer;" onclick="javascript:doDel(' +
                    row.taskID +
                    ');"><img src="' +
                    path +
                    '/style/images/icon/icon_delete.png" alt="删除" /></a>';
                }
                
            }
        }, {
            field: 'progressStatus',
            title: '任务下发进度',
            width: 130,
            align: 'center',
            formatter: function(value, row, index){
                if (value == 1) {
                    return '<img src =" ' + path + '/style/images/icon/progress1.png" title="等待分发" />';
                } else if (value == 2) {
                    return '<img src =" ' + path + '/style/images/icon/progress2.png" title="正在分发" />';
                } else if (value == 3) {
                    return '<img src =" ' + path + '/style/images/icon/progress3.png" title="已分发" />';
                } else if (value == 4) {
                    return '<img src =" ' + path + '/style/images/icon/progress4.png" title="已接收" />';
                } else {
                    return '已完成';
                }
            },
            sortable: true
        }, {
            field: 'errorInfo',
            title: '错误信息',
            width: 200,
            align: 'center',
            sortable: true
        }]]
    });
    $(window).resize(function(){
        $('#tblOfflineSyslogTask').resizeDataGrid(0, 0, 0, 0);
    });
}

/**
 * 更新表格
 */
function reloadTable(){
    var taskID = $("#txtTaskID").val();
    var ruleName = $("#txtRuleName").val();
    var collectorName = $("#txtCollectorName").val();
    $('#tblOfflineSyslogTask').datagrid('options').queryParams = {
        "taskID": taskID,
        "ruleName": ruleName,
        "collectorName": collectorName,
    };
    reloadTableCommon('tblOfflineSyslogTask');
}

/**
 * 实时更新操作进度
 */
function getOpProcess(){
    var grid = $('#tblOfflineSyslogTask');
    var options = grid.datagrid('getPager').data("pagination").options;
    //当前第几页
    var curr = options.pageNumber;
    //数据总数
    var total = options.total;
    //总共几页
    var max = Math.ceil(total / options.pageSize);
    //当前页的行数
    var rows = 10;
    if (curr < max) {
        rows = 10;
    } else {
        rows = total - (curr - 1) * 10;
    }
    var taskIds = '';
    for (var i = 0; i < rows; i++) {
        var processValue = $('#tblOfflineSyslogTask').datagrid('getRows')[i]['progressStatus'];
        if (processValue < 5) {
            var taskId = $('#tblOfflineSyslogTask').datagrid('getRows')[i]['taskID'];
            taskIds += taskId + ":" + i + ",";
        }
        
    }
    if (taskIds != '') {
        if (taskIds.indexOf(",") >= 0) {
            taskIds = taskIds.substring(0, taskIds.lastIndexOf(','));
        }
    }
    var path = getRootName();
    var uri = path + "/monitor/syslogTask/getProcessByTaskId";
    var ajax_param = {
        url: uri,
        type: "post",
        dateType: "text",
        data: {
            "taskIds": taskIds,
            "t": Math.random()
        },
        error: function(){
            $.messager.alert("错误", "ajax_error", "error");
        },
        success: function(data){
            var errorInfoLst = data.errorInfoLst;
            var processLst = data.processLst;
            //更新错误信息
            for (var i = 0; i < errorInfoLst.length; i++) {
                var indexValue = +errorInfoLst[i].split(":")[1];
                var errorInfo = "";
                var errorInfo2 = (errorInfoLst[i].split(":")[0]);
                if (errorInfo2 != null && errorInfo2 != "" && errorInfo2 != "null") {
                    errorInfo = (errorInfoLst[i].split(":")[0]);
                }
                $('#tblOfflineSyslogTask').datagrid('updateRow', {
                    index: indexValue,
                    row: {
                        errorInfo: errorInfo
                    }
                });
            }
            
            // 更新操作进度
            for (var i = 0; i < processLst.length; i++) {
                var indexValue = +processLst[i].split(":")[1];
                var process = (processLst[i].split(":")[0]);
                $('#tblOfflineSyslogTask').datagrid('updateRow', {
                    index: indexValue,
                    row: {
                        progressStatus: process
                    }
                });
            }
        }
    };
    ajax_(ajax_param);
}

/**
 * 监听操作状态 如果是已完成的 不展示
 */
function getOperateStatus(){
    var grid = $('#tblOfflineSyslogTask');
    var options = grid.datagrid('getPager').data("pagination").options;
    var curr = options.pageNumber;
    //数据总数
    var total = options.total;
    
    //总共几页
    var max = Math.ceil(total / options.pageSize);
    
    //当前页的行数
    var rows = 10;
    if (curr < max) {
        rows = 10;
    } else {
        rows = total - (curr - 1) * 10;
    }
    
    var taskIds = '';
    for (var i = 0; i < rows; i++) {
        var processValue = $('#tblOfflineSyslogTask').datagrid('getRows')[i]['progressStatus'];
        
        if (processValue == 5) {
            var taskId = $('#tblOfflineSyslogTask').datagrid('getRows')[i]['taskID'];
            taskIds += taskId + ":" + i + ",";
        }
        
    }
    if (taskIds != '') {
        if (taskIds.indexOf(",") >= 0) {
            taskIds = taskIds.substring(0, taskIds.lastIndexOf(','));
        }
    }
    var path = getRootName();
    var uri = path + "/monitor/syslogTask/getOperateStatus";
    var ajax_param = {
        url: uri,
        type: "post",
        dateType: "text",
        data: {
            "taskIds": taskIds,
            "t": Math.random()
        },
        error: function(){
            $.messager.alert("错误", "ajax_error", "error");
        },
        success: function(data){
            var operateLst = data.operateLst;
            
            // 更新操作进度,错误信息,任务状态
            for (var i = 0; i < operateLst.length; i++) {
                var operate = (operateLst[i].split(":")[0]);
                //如果状态是已删除，操作进度已完成，刷新表格使其不展示
                if (operate == 3) {
                    reloadTable();
                    return;
                }
            }
            
        }
    };
    ajax_(ajax_param);
}


/**
 * 任务重发
 */
function doResend(taskID){
    var path = getRootName();
    var uri = path + "/monitor/syslogTask/resendTask";
    var ajax_param = {
        url: uri,
        type: "post",
        datdType: "json",
        data: {
            "taskID": taskID,
            "t": Math.random()
        },
        error: function(){
            $.messager.alert("错误", "ajax_error", "error");
        },
        success: function(data){
            if (true == data || "true" == data) {
                $.messager.alert("提示", "任务已重发！", "info");
                reloadTable();
            } else {
                $.messager.alert("提示", "任务重发失败！", "error");
            }
        }
    }
    ajax_(ajax_param);
}

/**
 * 批量重发
 * @return
 */
function doBatchResend(){
    var path = getRootName();
    var checkedItems = $('[name=taskID]:checked');
    var ids = null;
    $.each(checkedItems, function(index, item){
        if (null == ids) {
            ids = $(item).val();
        } else {
            ids += ',' + $(item).val();
        }
    });
    if (null != ids) {
        $.messager.confirm("提示", "确定重发所选中的任务？", function(r){
            if (r == true) {
                var uri = path + "/monitor/syslogTask/doBatchResend?taskIDs=" + ids;
                var ajax_param = {
                    url: uri,
                    type: "post",
                    datdType: "json",
                    data: {
                        "t": Math.random()
                    },
                    success: function(data){
                        if (data == "") {
                            $.messager.alert("提示", "任务重发成功！", "info");
                            reloadTable();
                        } else {
                            data = data.substring(0, data.lastIndexOf(","));
                            $.messager.alert("提示", "任务ID为" + data + "的任务已完成，不能执行重发操作！", "error");
                            reloadTable();
                        }
                    }
                };
                ajax_(ajax_param);
            }
        });
    } else {
        $.messager.alert('提示', '没有任何选中项', 'error');
    }
}

/**
 * 删除任务
 */
function doDel(taskID){
    $.messager.confirm("提示", "确定删除所选中的项？", function(r){
        if (r == true) {
            var path = getRootName();
            var uri = path + "/monitor/syslogTask/delTask";
            var ajax_param = {
                url: uri,
                type: "post",
                datdType: "json",
                data: {
                    "taskID": taskID,
                    "t": Math.random()
                },
                error: function(){
                    $.messager.alert("错误", "ajax_error", "error");
                },
                success: function(data){
                    if (true == data || "true" == data) {
                        $.messager.alert("提示", "syslog任务删除成功！", "info");
                        reloadTable();
                    } else {
                        $.messager.alert("提示", "syslog任务删除失败！", "error");
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
    var checkedItems = $('[name=taskID]:checked');
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
                var uri = path + "/monitor/syslogTask/delTasks?taskIDs=" + ids;
                var ajax_param = {
                    url: uri,
                    type: "post",
                    datdType: "json",
                    data: {
                        "t": Math.random()
                    },
                    success: function(data){
                        var unDelTaskIDs = data.unDelTaskIDs;
                        var delResult = data.delResult;
                        if (delResult == true && unDelTaskIDs == "") {
                            $.messager.alert("提示", "syslog任务删除成功！", "info");
                        } else if (delResult == true && unDelTaskIDs != "") {
                            $.messager.alert("提示", "下列syslog任务进度未完，不能删除！" + unDelTaskIDs, "info");
                        } else {
                            $.messager.alert("错误", "syslog任务删除失败！", "error");
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
function doOpenDetail(taskID){
    parent.$('#popWin').window({
        title: 'syslog任务详情',
        width: 800,
        height: 450,
        minimizable: false,
        maximizable: false,
        collapsible: false,
        modal: true,
        href: getRootName() + '/monitor/syslogTask/toShowSyslogDetail?taskID=' + taskID
    });
}
