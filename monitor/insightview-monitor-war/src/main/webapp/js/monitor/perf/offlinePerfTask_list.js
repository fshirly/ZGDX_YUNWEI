var timerId;
var timerId2;
var timerId3;
$(document).ready(function(){
    doInitTable();
    //每隔0.5秒自动调用方法，实现操作进度的实时更新
    timerId = window.setInterval(getOpProcess, 5000);
    timerId2 = window.setInterval(getOpCollector, 5000);
    timerId3 = window.setInterval(getStatus, 5000);
});

/**
 * 加载页面并初始化表格
 * @return
 */
function doInitTable(){
    var path = getRootName();
    $('#tblPerfTask').datagrid({
        iconCls: 'icon-edit',// 图标
        width: 700,
        height: 'auto',
        nowrap: false,
        striped: true,
        border: true,
        collapsible: false,// 是否可折叠的
        fit: true,// 自动大小
        fitColumns: true,
        url: path + '/monitor/offlinePerfTask/listOfflinePerfTasks',
        remoteSort: false,
        queryParams: {
            "status": -2,
            "taskId": 0
        },
        idField: 'taskId',
        singleSelect: false,// 是否单选
        checkOnSelect: false,
        selectOnCheck: true,
        pagination: true,// 分页控件
        rownumbers: true,// 行号
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
        }, {
            'text': '批量重发',
            'iconCls': 'icon-batchSend',
            handler: function(){
                doBatchResend();
            }
        }],
        columns: [[{
            field: 'taskId',
            checkbox: true
        }, {
            field: 'status',
            title: '任务状态',
            width: 65,
            align: 'center',
            formatter: function(value, row, index){
                if (value == -1) {
                    return '已删除';
                }
                else if (value == 0) {
                    return '<img src =" ' + path + '/style/images/icon/icon_run.png"  title="运行中" />';
                }
                else if (value == 1) {
                    return '<img src =" ' + path + '/style/images/icon/icon_stop.png"  title="已停止" />';
                }
                else {
                    return '';
                }
            },
            sortable: true
        }, {
            field: 'lastStatusTime',
            title: '最近状态时间',
            width: 100,
            align: 'center',
            sortable: true
        }, {
            field: 'deviceIp',
            title: '被采设备',
            width: 150,
            align: 'center',
            sortable: true,
            formatter: function(value, row, index){
                if (value && value.length > 20) {
                    value2 = value.substring(0, 20) + "...";
                    return '<span title="' + value + '" style="cursor: pointer;" >' + value2 + '</span>';
                }
                else {
                    return value;
                }
            }
        }, {
            field: 'className',
            title: '对象类型',
            width: 80,
            align: 'center',
            sortable: true
        }, {
            field: 'deviceManufacture',
            title: '被采设备厂商',
            width: 100,
            align: 'center',
            formatter: function(value, row, index){
                if (value == null) {
                    return "未知";
                }
                else {
                    return value;
                }
            },
        }, {
            field: 'deviceType',
            title: '被采设备类型',
            width: 100,
            align: 'center',
            formatter: function(value, row, index){
                if (value == null) {
                    return "未知";
                }
                else {
                    return value;
                }
            },
        }, {
            field: 'moCounts',
            title: '监测器数',
            width: 60,
            align: 'center',
            formatter: function(value, row, index){
                return '<a style="cursor: pointer;" onclick="javascript:toUpdate(' +
                row.taskId +
                ',&quot;' +
                row.serverName +
                '&quot;);">' +
                value +
                '</a>';
            },
        }, {
            field: 'createTime',
            title: '任务创建时间',
            width: 100,
            align: 'center',
        }, {
            field: 'serverName',
            title: '所属采集机',
            width: 110,
            align: 'center',
            sortable: true,
            formatter: function(value, row, index){
                if (value == null || value == "null" || value == "") {
                    return "";
                }
                else {
                    return value;
                }
            }
        }, {
            field: 'taskIds',
            title: '操作',
            width: 180,
            align: 'center',
            formatter: function(value, row, index){
                if (row.progressStatus >= 1 && row.progressStatus < 5) {
                    return '<a style="cursor: pointer;" onclick="javascript:toShowInfo(' +
                    row.taskId +
                    ');"><img src="' +
                    path +
                    '/style/images/icon/icon_view.png" title="查看" /></a> &nbsp;<a style="cursor: pointer;" onclick="javascript:void(0);"><img src="' +
                    path +
                    '/style/images/icon/icon_modify.png" alt="修改" /></a> &nbsp;<a style="cursor: pointer;" onclick="javascript:void(0);"><img src="' +
                    path +
                    '/style/images/icon/icon_delete.png" alt="删除" /></a> &nbsp;<a href="javascript:doResend(' +
                    row.taskId +
                    ');" id="btnResend" class="fltrt"><img src =" ' +
                    path +
                    '/style/images/icon/icon_sent.png"  title="重发" /></a>&nbsp;<a href="javascript:toAppointCollector(' +
                    row.taskId +
                    "," +
                    row.serverId +
                    ',&quot;' +
                    row.serverName +
                    '&quot;);" id="btnStart" class="fltrt"><img src =" ' +
                    path +
                    '/style/images/icon/icon_appointcollector.png"  title="指定采集机" /></a>';
                    
                }
                else {
                    return '<a style="cursor: pointer;" onclick="javascript:toShowInfo(' +
                    row.taskId +
                    ');"><img src="' +
                    path +
                    '/style/images/icon/icon_view.png" title="查看" /></a> &nbsp;<a style="cursor: pointer;" onclick="javascript:toUpdate(' +
                    row.taskId +
					 "," +
                    row.serverId +
                    ',&quot;' +
                    row.serverName +
                    '&quot;);"><img src="' +
                    path +
                    '/style/images/icon/icon_modify.png" alt="修改" /></a> &nbsp;<a style="cursor: pointer;" onclick="javascript:doDel(' +
                    row.taskId +
                    ');"><img src="' +
                    path +
                    '/style/images/icon/icon_delete.png" alt="删除" /></a>&nbsp;<a href="javascript:toAppointCollector(' +
                    row.taskId +
                    ',' +
                    row.serverId +
                    ',&quot;' +
                    row.serverName +
                    '&quot;);" id="btnStart" class="fltrt"><img src =" ' +
                    path +
                    '/style/images/icon/icon_appointcollector.png"  title="指定采集机" /></a>';
                }
                
            }
        }, {
            field: 'progressStatus',
            title: '任务下发进度',
            width: 150,
            align: 'center',
            formatter: function(value, row, index){
                if (value == 1) {
                    return '<img src =" ' + path + '/style/images/icon/progress1.png" title="等待分发" />';
                }
                else if (value == 2) {
                    return '<img src =" ' + path + '/style/images/icon/progress2.png" title="正在分发" />';
                }
                else if (value == 3) {
                    return '<img src =" ' + path + '/style/images/icon/progress3.png" title="已分发" />';
                }
                else if (value == 4) {
                    return '<img src =" ' + path + '/style/images/icon/progress4.png" title="已接收" />';
                }
                else {
                    return '已完成';
                }
            },
            sortable: true
        }, {
            field: 'errorInfo',
            title: '错误信息',
            width: 180,
            align: 'center',
        }]]
    });
    $(window).resize(function(){
        $('#tblPerfTask').resizeDataGrid(0, 0, 0, 0);
    });
}

/**
 * 重置查询条件
 */
function resetFormDiv(divId){
    resetForm(divId);
    $("#txtTaskStatus").combobox("setValue", "-2");
}

/*
 * 更新表格
 */
function reloadTable(){
    var taskId = $("#txtTaskId").val();
    if (taskId != null && taskId != "") {
        if (!(/^[0-9]*[1-9][0-9]*$/.test(taskId))) {
            $.messager.alert("提示", "任务ID只能输入正整数！", "info", function(e){
                $("#txtTaskId").focus();
            });
            return;
        }
    }
    var deviceIp = $("#txtDeviceIp").val();
    var serverName = $("#txtServerName").val();
    var taskStatus = $("#txtTaskStatus").combobox('getValue');
    if (taskId == '') {
        taskId = 0;
    }
    if (taskStatus == '') {
        taskStatus = -2;
    }
    $('#tblPerfTask').datagrid('options').queryParams = {
        "taskId": taskId,
        "deviceIp": deviceIp,
        "serverName": serverName,
        "status": taskStatus
    };
    reloadTableCommon_1('tblPerfTask');
}

function reloadTableCommon_1(dataGridId){
    $('#' + dataGridId).datagrid('load');
    $('#' + dataGridId).datagrid('uncheckAll');
}

/**
 * 更新任务进度及错误信息
 */
function getOpProcess(){
    var grid = $('#tblPerfTask');
    var options = grid.datagrid('getPager').data("pagination").options;
    //当前第几页
    var curr = options.pageNumber;
    //	console.log("curr==="+curr);
    //数据总数
    var total = options.total;
    //	console.log("total==="+total);
    //总共几页
    var max = Math.ceil(total / options.pageSize);
    //	console.log("max==="+max);
    //当前页的行数
    var rows = 10;
    if (curr < max) {
        rows = 10;
    }
    else {
        rows = total - (curr - 1) * 10;
    }
    //	console.log("rows===="+rows);
    
    var taskIds = '';
    for (var i = 0; i < rows; i++) {
        //		console.log("i====="+i+",  id====="+$('#tblPerfTask').datagrid('getRows')[i]['taskId']+" 	,progressStatus======"+$('#tblPerfTask').datagrid('getRows')[i]['progressStatus']);
        var processValue = $('#tblPerfTask').datagrid('getRows')[i]['progressStatus'];
        
        if (processValue < 5) {
            var taskId = $('#tblPerfTask').datagrid('getRows')[i]['taskId'];
            taskIds += taskId + ":" + i + ",";
        }
        
    }
    if (taskIds != '') {
        if (taskIds.indexOf(",") >= 0) {
            taskIds = taskIds.substring(0, taskIds.lastIndexOf(','));
        }
    }
    var path = getRootName();
    var uri = path + "/monitor/perfTask/getProcessByTaskId";
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
            var statusLst = data.statusLst;
            // 更新操作进度,错误信息,任务状态
            for (var i = 0; i < processLst.length; i++) {
                var indexValue = +processLst[i].split(":")[1];
                var process = (processLst[i].split(":")[0]);
                var errorInfo = "";
                var status = (statusLst[i].split(":")[0]);
                var errorInfo2 = (errorInfoLst[i].split(":")[0]);
                if (errorInfo2 != null && errorInfo2 != "" && errorInfo2 != "null") {
                    errorInfo = (errorInfoLst[i].split(":")[0]);
                }
                $('#tblPerfTask').datagrid('updateRow', {
                    index: indexValue,
                    row: {
                        progressStatus: process,
                        errorInfo: errorInfo,
                        status: status
                    }
                });
            }
            
        }
    };
    ajax_(ajax_param);
    
}

/**
 * 更新采集机
 */
function getOpCollector(){
    var grid = $('#tblPerfTask');
    var options = grid.datagrid('getPager').data("pagination").options;
    //当前第几页
    var curr = options.pageNumber;
    //	console.log("curr==="+curr);
    //数据总数
    var total = options.total;
    //	console.log("total==="+total);
    //总共几页
    var max = Math.ceil(total / options.pageSize);
    //	console.log("max==="+max);
    //当前页的行数
    var rows = 10;
    if (curr < max) {
        rows = 10;
    }
    else {
        rows = total - (curr - 1) * 10;
    }
    //	console.log("rows===="+rows);
    var taskIds = '';
    for (var i = 0; i < rows; i++) {
        var serverName = $('#tblPerfTask').datagrid('getRows')[i]['serverName'];
        //		console.log(i+"====="+serverName);
        if (serverName == "" || serverName == null) {
            var taskId = $('#tblPerfTask').datagrid('getRows')[i]['taskId'];
            taskIds += taskId + ":" + i + ",";
        }
        
    }
    if (taskIds != '') {
        if (taskIds.indexOf(",") >= 0) {
            taskIds = taskIds.substring(0, taskIds.lastIndexOf(','));
        }
    }
    if (taskIds != null && taskIds != "") {
        var path = getRootName();
        var uri = path + "/monitor/perfTask/getServerNameByTaskId";
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
                for (var j = 0; j < data.length; j++) {
                    var indexValue = +data[j].split(":")[1];
                    var serverName = "";
                    var name = (data[j].split(":")[0]);
                    if (name != null && name != "" && name != "null") {
                        serverName = name;
                    }
                    $('#tblPerfTask').datagrid('updateRow', {
                        index: indexValue,
                        row: {
                            serverName: serverName
                        }
                    });
                }
            }
        };
        ajax_(ajax_param);
    }
}

/**
 * 更新表格 去除已经删除的任务
 *
 */
function getStatus(){
    var grid = $('#tblPerfTask');
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
    }
    else {
        rows = total - (curr - 1) * 10;
    }
    
    var taskIds = '';
    for (var i = 0; i < rows; i++) {
        var processValue = $('#tblPerfTask').datagrid('getRows')[i]['progressStatus'];
        
        if (processValue = 5) {
            var taskId = $('#tblPerfTask').datagrid('getRows')[i]['taskId'];
            taskIds += taskId + ":" + i + ",";
        }
        
    }
    if (taskIds != '') {
        if (taskIds.indexOf(",") >= 0) {
            taskIds = taskIds.substring(0, taskIds.lastIndexOf(','));
        }
    }
    var path = getRootName();
    var uri = path + "/monitor/perfTask/getProcessByTaskId";
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
            var statusLst = data.statusLst;
            // 更新操作进度,错误信息,任务状态
            for (var i = 0; i < statusLst.length; i++) {
                var status = (statusLst[i].split(":")[0]);
                
                //如果状态是已删除，操作进度已完成，刷新表格使其不展示
                if (status == -1) {
                    reloadTable();
                }
            }
            
        }
    };
    ajax_(ajax_param);
}

/**
 * 删除任务 
 */
function doDel(taskId) {
	$.messager.confirm("提示","确定删除此任务？",function(r){
		if (r == true) {
			var path = getRootName();
			var uri = path + "/monitor/perfTask/delTask";
			var ajax_param = {
				url : uri,
				type : "post",
				datdType : "json",
				data : {
					"taskId" : taskId,
					"t" : Math.random()
				},
				error : function() {
					$.messager.alert("错误", "ajax_error", "error");
				},
				success:function(data) {
					if (true == data || "true" == data) {
						$.messager.alert("提示", "任务删除成功！", "info");
						reloadTable();
					} else {
						$.messager.alert("提示", "任务删除失败！", "error");
					}
				}
			}
			ajax_(ajax_param);
		}
	});
	
}

/**
 * 批量删除
 */
function doBatchDel(){
	var path=getRootName();
	var checkedItems = $('#tblPerfTask').datagrid("getChecked");
	var ids = null;
	$.each(checkedItems, function(index, item) {
		if (null == ids) {
			ids = item.taskId;
		} else {
			ids += ',' + item.taskId;
		}
	});
	if (null != ids) {
		$.messager.confirm("提示","确定删除所选中项？",function(r){
			if (r == true) {
				var uri = path+"/monitor/perfTask/delTasks?taskIds=" + ids;
				var ajax_param = {
					url : uri,
					type : "post",
					datdType : "json",
					data : {
						"t" : Math.random()
					},
					success:function(data) {
						if(data == "") {
							$.messager.alert("提示", "任务删除成功！", "info");
							reloadTable();
						} else {
							data=data.substring(0,data.lastIndexOf(","));
							$.messager.alert("提示", "任务ID为"+data+"的数据不能删除！", "error");
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
 * 任务重发
 */
function doResend(taskId){
	var path = getRootName();
	var uri = path + "/monitor/perfTask/resendTask";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"progressStatus":1,
			"taskId":taskId,
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success:function(data) {
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
 */
function doBatchResend(){
	var path=getRootName();
	var checkedItems = $('#tblPerfTask').datagrid("getChecked");
	var ids = null;
	$.each(checkedItems, function(index, item) {
		if (null == ids) {
			ids = item.taskId;
		} else {
			ids += ',' + item.taskId;
		}
	});
	if (null != ids) {
		$.messager.confirm("提示","确定重发所选中的任务？",function(r){
			if (r == true) {
				var uri = path+"/monitor/perfTask/doBatchResend?taskIds=" + ids;
				var ajax_param = {
					url : uri,
					type : "post",
					datdType : "json",
					data : {
						"t" : Math.random()
					},
					success:function(data) {
						if(data == "") {
							$.messager.alert("提示", "任务重发成功！", "info");
							reloadTable();
						} else {
							data=data.substring(0,data.lastIndexOf(","));
							$.messager.alert("提示", "任务ID为"+data+"的任务已完成，不能执行重发操作！", "error");
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
 * 指定采集机
 */
function toAppointCollector(taskId,serverId,serverName){
	parent.$('#popWin').window({
		title:'指定采集机',
		width:600,
		height : 200,
		minimizable:false,
		maximizable:false,
		collapsible:false,
		modal:true,
		href: getRootName() + '/monitor/offlinePerfTask/toAppointCollector?taskId='+taskId+'&serverId='+serverId+'&serverName='+serverName
	});
}


/**
 * 查看详情
 */
function toShowInfo(taskId){
    parent.$('#popWin').window({
        title: '离线采集任务详情',
        width: 800,
        height: 460,
        minimizable: false,
        maximizable: false,
        collapsible: false,
        modal: true,
        href: getRootName() + '/monitor/offlinePerfTask/toShowPerfTaskDetail?taskId=' + taskId
    });
}

/**
 * 新增
 */
function toAdd(){
    parent.$('#popWin').window({
        title: '离线采集任务新增',
        width: 800,
        height: 460,
        minimizable: false,
        maximizable: false,
        collapsible: false,
        modal: true,
        href: getRootName() + '/monitor/offlinePerfTask/toEditOfflinePerfTask?flag=add'
    });
}

/**
 * 编辑
 */
function toUpdate(taskId,serverId,serverName){
    parent.$('#popWin').window({
        title: '离线采集任务编辑',
        width: 800,
        height: 460,
        minimizable: false,
        maximizable: false,
        collapsible: false,
        modal: true,
        href: getRootName() + '/monitor/offlinePerfTask/toEditOfflinePerfTask?flag=edit&taskId='+taskId+'&serverId='+serverId+'&serverName='+serverName
    });
}