var path = getRootName();//全局变量
$(document).ready(function(){
    doInitTableFilter();
//    doInitAlarmLevel();//告警级别下拉框
});

/**
 * 新增处理
 */
function doAdd(){
    if (checkForm()) {
        var path = getRootName();
        var viewCfgID = $("#viewCfgID").val();
        if (viewCfgID == "") {
            var uri = path + "/monitor/alarmView/addAlarmView";
            var ajax_param = {
                url: uri,
                type: "post",
                datdType: "json",
                data: {
                    "cfgName": $("#cfgName").val(),
                    "userDefault": $('input[name="userDefault"]:checked').val(),
                    "defaultRows": $("#defaultRows").val(),
                    "defaultInterval": $("#defaultInterval").val(),
                    "descr": $("#descr").val()
                },
                error: function(){
                    $.messager.alert("错误", "ajax_error", "error");
                },
                success: function(data){
                    var flag = data.flag;
                    if (flag == "-1") {
                        $.messager.alert("提示", "信息新增失败！", "error");
                    } else {
                        $("#viewCfgID").val(flag);
                        window.frames['component_2'].reloadAllTable();
                    }
                }
            };
            if ($('input[name="colID"]:checked').val() == null) {
                $.messager.confirm("操作提示", "确定不启动显示列吗？", function(data){
                    if (data) {
                        ajax_(ajax_param);
                    }
                });
            }
        } else {
            var uri = path + "/monitor/alarmView/updateAlarmView";
            var ajax_param = {
                url: uri,
                type: "post",
                datdType: "json",
                data: {
                    "viewCfgID": viewCfgID,
                    "cfgName": $("#cfgName").val(),
                    "userDefault": $('input[name="userDefault"]:checked').val(),
                    "defaultRows": $("#defaultRows").val(),
                    "defaultInterval": $("#defaultInterval").val(),
                    "descr": $("#descr").val()
                },
                error: function(){
                    $.messager.alert("错误", "ajax_error", "error");
                },
                success: function(data){
                    if (true == data || "true" == data) {
                        parent.$('#popWin').window('close');
                        window.frames['component_2'].reloadAllTable();
                    } else {
                        $.messager.alert("提示", "信息修改失败！", "error");
                    }
                }
            };
            ajax_(ajax_param);
        }
    }
}

/**
 * 验证表单信息
 *
 */
function checkForm(){
    var cfgName = $("#cfgName").val();
    if (cfgName == "" || cfgName == null) {
        $.messager.alert("提示", "视图名称不能为空！", "warning");
        return false;
    }
    return true;
}

/**
 * 加载页面并初始化表格
 * @return
 */
function doInitTableFilter(){
    var path = getRootName();
    var uri = "";
    var viewCfgID = $("#viewCfgID").val();
    if (viewCfgID != "" && viewCfgID != null) {
        uri = path + "/monitor/AlarmDispatchFilter/ajaxAlarmDispatchFilterDataGrid?defid=" + viewCfgID;
    }
    $('#filtertblDataList').datagrid({
        iconCls: 'icon-edit',// 图标
        width: 650,
        height: 'auto',
        nowrap: false,
        striped: true,
        border: true,
        collapsible: false,// 是否可折叠的
        url: uri,
		fitColumns: true,
		scrollbarSize: 0,
        remoteSort: false,
        idField: 'id',//event主键
        singleSelect: false,// 是否单选
        pagination: true,// 分页控件
        rownumbers: true,// 行号
        toolbar: [{
            'text': '告警等级',
            'iconCls': 'icon-add',
            handler: function(){
                toSelectAlarmLevel();
            }
        }, {
            'text': '告警类型',
            'iconCls': 'icon-add',
            handler: function(){
                toSelectAlarmType();
            }
        }, {
            'text': '告警源对象',
            'iconCls': 'icon-add',
            handler: function(){
                toSelectMOSource();
            }
        }, {
            'text': '告警事件',
            'iconCls': 'icon-add',
            handler: function(){
                toSelectAlarmEvent();
            }
        }],
        columns: [[{
            field: 'filterKey',
            title: '过滤关键字',
            width: 200,
            align: 'left',
            formatter: function(value, row, index){
                var rtnVal = "";
                if (value == "AlarmLevel") {
                    rtnVal = "告警等级";
                } else if (value == "AlarmType") {
                    rtnVal = "告警类型";
                } else if (value == "AlarmSourceMOID") {
                    rtnVal = "告警源对象";
                } else if (value == "AlarmDefineID") {
                    rtnVal = "告警事件";
                }
                return rtnVal;
            }
        }, {
            field: 'filterKeyValueName',
            title: '过滤值',
            width: 200,
            align: 'center'
        }, {
            field: 'ids',
            title: '操作',
            width: 80,
            align: 'center',
            formatter: function(value, row, index){
                return '<a style="cursor: pointer;" title="删除" onclick="javascript:doDelFilter(' +
                row.id +
                ');"><img src="' +
                path +
                '/style/images/icon/icon_delete.png" alt="删除" /></a>';
            }
        }]]
    });
    $(window).resize(function(){
        $('#filtertblDataList').resizeDataGrid(0, 0, 0, 0);
    });
}


//告警级别选择
function toSelectAlarmLevel(){
    var viewCfgID = $("#viewCfgID").val();
    if (viewCfgID == "") {
        doMainAdd("toSelectAlarmLevel()");
    } else {
        var uri = path + "/monitor/AlarmDispatchFilter/toSelectAlarmLevel?id=" + viewCfgID;
        //		window.open(uri,"","height=480px,width=780px,left=400,top=200,resizable=no,scrollings=no,status=no,toolbar=no,menubar=no,location=no");
        $('#event_select_dlg').window({
            title: '告警级别选择',
            width: 620,
            height: 450,
            modal: true,
            onBeforeOpen: function(){
                if ($(".event_select_dlg").size() > 1) {
                    $('.event_select_dlg:first').parent().remove();
                }
            },
            href: uri
        });
    }
}

//告警类型选择
function toSelectAlarmType(){
    var viewCfgID = $("#viewCfgID").val();
    if (viewCfgID == "") {
        doMainAdd("toSelectAlarmType()");
    } else {
        var uri = path + "/monitor/AlarmDispatchFilter/toSelectAlarmType?id=" + viewCfgID;
        //		window.open(uri,"","height=480px,width=780px,left=400,top=200,resizable=no,scrollings=no,status=no,toolbar=no,menubar=no,location=no");
        $('#event_select_dlg').window({
            title: '告警类型选择',
            width: 620,
            height: 450,
            modal: true,
            onBeforeOpen: function(){
                if ($(".event_select_dlg").size() > 1) {
                    $('.event_select_dlg:first').parent().remove();
                }
            },
            href: uri
        });
    }
}

//告警源对象选择
function toSelectMOSource(){
    var viewCfgID = $("#viewCfgID").val();
    if (viewCfgID == "") {
        doMainAdd("toSelectMOSource()");
    } else {
        var uri = path + "/monitor/AlarmDispatchFilter/toSelectMOSource?id=" + viewCfgID;
        //		window.open(uri,"","height=480px,width=780px,left=400,top=200,resizable=no,scrollings=no,status=no,toolbar=no,menubar=no,location=no");
        $('#event_select_dlg').window({
            title: '告警源对象选择',
            width: 660,
            height: 470,
            modal: true,
            onBeforeOpen: function(){
                if ($(".event_select_dlg").size() > 1) {
                    $('.event_select_dlg:first').parent().remove();
                }
            },
            href: uri
        });
    }
}

//告警事件选择
function toSelectAlarmEvent(){
    var viewCfgID = $("#viewCfgID").val();
    if (viewCfgID == "") {
        doMainAdd("toSelectAlarmEvent()");
    } else {
        var uri = path + "/monitor/AlarmDispatchFilter/toSelectAlarmEvent?id=" + viewCfgID;
        //		window.open(uri,"","height=480px,width=780px,left=400,top=200,resizable=no,scrollings=no,status=no,toolbar=no,menubar=no,location=no");
        $('#event_select_dlg').window({
            title: '告警事件选择',
            width: 620,
            height: 450,
            modal: true,
            onBeforeOpen: function(){
                if ($(".event_select_dlg").size() > 1) {
                    $('.event_select_dlg:first').parent().remove();
                }
            },
            href: uri
        });
    }
}

/**
 * 告警级别下拉框
 * @return
 */
function doInitAlarmLevel(){
    var path = getRootPatch();
    var uri = path + "/monitor/alarmView/initAlarmLevel";
    var ajax_param = {
        url: uri,
        type: "post",
        datdType: "json",
        data: {
            "t": Math.random()
        },
        error: function(){
            $.messager.alert("错误", "ajax_error", "error");
        },
        success: function(data){
            // 得到树的json数据源
            var datas = eval('(' + data.alarmLstJson + ')');
            for (var i = 0; i < datas.length; i++) {
                var _id = datas[i].alarmLevelID;
                var _name = datas[i].alarmLevelName;
                $("#selectAlarm1").append("<option value='" + _id + "'>" + _name + "</option>");
            }
        }
    }
    ajax_(ajax_param);
}

function toClosed(){
    parent.$('#popWin').window('close');
}

/**
 * 增加显示列
 * @return
 */
function toAddColCfg(){
    var viewCfgID = $("#viewCfgID").val();
    if (viewCfgID == "") {
        //$.messager.alert('提示', '请先保存主表视图信息,点右下角确定', 'hint');
        doMainAdd("toAddColCfg()");
    } else {
        var ids = null;
        var rows = $('#colCfgtblDataList').datagrid('getSelections');
        var flag = null;
        for (var i = 0; i < rows.length; i++) {
            if (rows[i].cfgID != null && rows[i].cfgID != "") {
                flag = "1";
            }
            if (null == ids) {
                ids = rows[i].colID;
            } else {
                ids += ',' + rows[i].colID;
            }
        }
        if (null != ids) {
            if (flag == null) {
                var uri = getRootName() + "/monitor/alarmView/addColCfgDlg";
                var ajax_param = {
                    url: uri,
                    type: "post",
                    datdType: "json",
                    data: {
                        "id": ids,
                        "viewCfgID": viewCfgID
                    },
                    success: function(data){
                        if (true == data || "true" == data) {
                            doInitTableColCfg();
                        } else {
                            $.messager.alert("提示", "信息启用失败！", "error");
                        }
                    }
                };
                ajax_(ajax_param);
            } else {
                $.messager.alert("提示", "错误选择已启用的信息！", "error");
            }
        } else {
            $.messager.alert('提示', '没有任何选中项', 'error');
        }
    }
}

/**
 * 删除过滤条件
 * @param id
 * @return
 */
function doDelFilter(id){
    $.messager.confirm("操作提示", "您确定删除此信息吗？", function(data){
        if (data) {
            var path = getRootName();
            var uri = path + "/monitor/AlarmDispatchFilter/ajaxRemoveAlarmDispatchFilter";
            var ajax_param = {
                url: uri,
                type: "post",
                datdType: "json",
                data: {
                    "id": id,
                    "t": Math.random()
                },
                error: function(){
                    $.messager.alert("错误", "ajax_error", "error");
                },
                success: function(data){
                    if (true == data || "true" == data) {
                        doInitTableFilter();
                    } else {
                        $.messager.alert("提示", "信息删除失败！", "error");
                    }
                }
            }
            ajax_(ajax_param);
        }
    });
}

function doSelect(filterKey, filterVal){
    var id = $("#viewCfgID").val();
    //保存数据		
    var uri = path + "/monitor/AlarmDispatchFilter/ajaxAddAlarmDispatchFilter";
    var ajax_param = {
        url: uri,
        type: "post",
        datdType: "json",
        data: {
            "id": id,
            "key": filterKey,
			"val": filterVal
        },
        error: function(){
            $.messager.alert("错误", "ajax_error", "error");
        },
        success: function(data){
            if (true == data || "true" == data) {
                doInitTableFilter();
            } else {
                $.messager.alert("提示", "信息增加失败！", "error");
            }
        }
    };
    ajax_(ajax_param);
}

/**
 * 同步保步前处理
 * @return
 */
function doMainAdd(exeMethod){
    if (checkForm()) {
        var path = getRootName();
        var uri = path + "/monitor/AlarmDispatchFilter/ajaxAddAlarmDispatchFilterDef";
        var ajax_param = {
            url: uri,
            type: "post",
            datdType: "json",
            data: {
                "name": $("#cfgName").val(),
                "isDefault": $('input[name="userDefault"]:checked').val(),
                "descr": $("#descr").val()
            },
            error: function(){
                $.messager.alert("错误", "ajax_error", "error");
            },
            success: function(flag){
                if (flag == "-1" || flag == -1) {
                    $.messager.alert("提示", "信息新增失败！", "error");
                } else {
                    $("#viewCfgID").val(flag);
                    window.frames['component_2'].reloadAllTable();
                    eval(exeMethod);
                }
            }
        };
        ajax_(ajax_param);
    }
}


//显示列操作

/**
 * 修改显示列
 * @param id
 * @return
 */
function toUpdateCfgDlg(id){
    initCfgUpdateVal(id);
    $('#divColcfgEdit').dialog('open');
}

/**
 * 根据显示列ID获取对象
 *
 */
function initCfgUpdateVal(id){
    resetForm('colCfgTab');
    var path = getRootName();
    var uri = path + "/monitor/alarmView/findCfgDlg";
    var ajax_param = {
        url: uri,
        type: "post",
        datdType: "json",
        data: {
            "cfgID": id,
            "t": Math.random()
        },
        error: function(){
            $.messager.alert("错误", "ajax_error", "error");
        },
        success: function(data){
            iterObj(data, "ipt");
            if (data.isVisible == 1) {
                $("#isVisible").attr("checked", 'true');
            } else {
                $("#isVisible").attr("checked", '');
            }
        }
    }
    ajax_(ajax_param);
}

/**
 * 删除显示列
 * @param id
 * @return
 */
function doDelColCfg(){
    var ids = null;
    var rows = $('#colCfgtblDataList').datagrid('getChecked');
    var flag = null;
    for (var i = 0; i < rows.length; i++) {
        if (rows[i].cfgID == null || rows[i].cfgID == "") {
            flag = "1";
        }
        if (null == ids) {
            ids = rows[i].cfgID;
        } else {
            ids += ',' + rows[i].cfgID;
        }
    }
    
    if (null != ids && rows.length > 0) {
        if (flag == null) {
            var uri = path + "/monitor/AlarmDispatchFilter/ajaxRemoveAlarmDispatchFilterDef";
            var ajax_param = {
                url: uri,
                type: "post",
                datdType: "json",
                data: {
                    "cfgID": ids,
                    "t": Math.random()
                },
                error: function(){
                    $.messager.alert("错误", "ajax_error", "error");
                },
                success: function(data){
                    if (true == data || "true" == data) {
                        doInitTableColCfg();
                    } else {
                        $.messager.alert("提示", "信息禁用失败！", "error");
                    }
                }
            }
            ajax_(ajax_param);
        } else {
            $.messager.alert("提示", "错误选择已禁用的信息！", "error");
        }
    } else {
        $.messager.alert('提示', '没有任何选中项', 'error');
    }
}

/**
 * 列修改处理
 */
function doUpdateCfgDlg(){
    if (checkCfgForm()) {
        var isVisible = "";
        if ($("#isVisible").is(":checked")) {
            isVisible = 1;
        } else {
            isVisible = 0;
        }
        var path = getRootName();
        var uri = path + "/monitor/alarmView/updateCfgDlg";
        var ajax_param = {
            url: uri,
            type: "post",
            datdType: "json",
            data: {
                "cfgID": $("#ipt_cfgID").val(),
                "colWidth": $("#ipt_colWidth").val(),
                "colOrder": $("#ipt_colOrder").val(),
                "isVisible": isVisible,
                "colValueOrder": $("#ipt_colValueOrder").val()
            },
            error: function(){
                $.messager.alert("错误", "ajax_error", "error");
            },
            success: function(data){
                if (true == data || "true" == data) {
                    $('#divColcfgEdit').dialog('close');
                    doInitTableColCfg();
                } else {
                    $.messager.alert("提示", "信息新增失败！", "error");
                }
            }
        };
        ajax_(ajax_param);
    }
}

/**
 * 验证表单信息
 *
 */
function checkCfgForm(){
    var colWidth = $("#ipt_colWidth").val();
    var flag = /^[+]?[1-9]+\d*$/i.test(colWidth);//验证是否为正整数
    if (!flag) {
        $.messager.alert("提示", "列宽请输入正整数！", "warning");
        $('#ipt_colWidth').val("");
        $('#ipt_colWidth').focus();
        return;
    }
    var colOrder = $("#ipt_colOrder").val();
    flag = /^[+]?[1-9]+\d*$/i.test(colOrder);//验证是否为正整数
    if (!flag) {
        $.messager.alert("提示", "显示顺序请输入正整数！", "warning");
        $('#ipt_colOrder').val("");
        $('#ipt_colOrder').focus();
        return;
    }
    var colValueOrder = $("#ipt_colValueOrder").val();
    flag = /^[+]?[1-9]+\d*$/i.test(colValueOrder);//验证是否为正整数
    if (!flag) {
        $.messager.alert("提示", "排序序号请输入正整数！", "warning");
        $('#ipt_colValueOrder').val("");
        $('#ipt_colValueOrder').focus();
        return;
    }
    return true;
}