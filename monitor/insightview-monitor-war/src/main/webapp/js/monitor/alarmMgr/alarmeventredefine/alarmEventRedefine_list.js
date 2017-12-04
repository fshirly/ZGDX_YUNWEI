$(document).ready(function(){
    doInitTable();
});

/**
 * 页面加载初始化表格
 */
function doInitTable(){
    var path = getRootName();
    $('#tblAlarmEventRedefine').datagrid({
        iconCls: 'icon-edit',// 图标
        width: 700,
        height: 'auto',
        nowrap: false,
        striped: true,
        border: true,
        collapsible: false,// 是否可折叠的
        fit: true,// 自动大小
        fitColumns: true,
        url: path + '/monitor/alarmEventRedefine/listAlarmEventRedefine',
        remoteSort: false,
        idField: 'ruleId',
        singleSelect: false,// 是否单选
        checkOnSelect: false,
        selectOnCheck: true,
        pagination: true,// 分页控件
        rownumbers: true,// 行号
        toolbar: [{
            'text': '新增',
            'iconCls': 'icon-add',
            handler: function(){
                doOpenAdd();
            }
        }, {
            'text': '删除',
            'iconCls': 'icon-cancel',
            handler: function(){
                doBatchDel();
            }
        }],
        columns: [[{
            field: 'ruleId',
            checkbox: true
        }, {
            field: 'ruleName',
            title: '规则名称',
            align: 'center',
            width: 150,
        }, {
            field: 'isEnable',
            title: '是否启用',
            align: 'center',
            width: 100,
            formatter: function(value, row, index){
                if (value == 1) {
                    return "是";
                }
                else {
                    return "否";
                }
            }
        }, {
            field: 'ruleDesc',
            title: '规则描述',
            align: 'center',
            width: 200,
            formatter: function(value, row, index){
                if (value && value.length > 16) {
                    var value2 = value.substring(0, 16) + "...";
                    return value2;
                }
                else {
                    return value;
                }
            }
        }, {
            field: 'ruleIds',
            title: '操作',
            width: 100,
            align: 'center',
            formatter: function(value, row, index){
                return '<a style="cursor: pointer;" onclick="javascript:doOpenDetail(' +
                row.ruleId +
                ');"><img src="' +
                path +
                '/style/images/icon/icon_view.png" title="查看" /></a> &nbsp;<a style="cursor: pointer;" onclick="javascript:doOpenUpdate(' +
                row.ruleId +
                ');"><img src="' +
                path +
                '/style/images/icon/icon_modify.png" title="修改" /></a> &nbsp;<a style="cursor: pointer;" onclick="javascript:toDel(' +
                row.ruleId +
                ');"><img src="' +
                path +
                '/style/images/icon/icon_delete.png" title="删除" /></a>';
            }
        }]]
    });
    $(window).resize(function(){
        $('#tblAlarmEventRedefine').resizeDataGrid(0, 0, 0, 0);
    });
}

/**
 * 更新表格
 */
function reloadTable(){
    var ruleName = $("#txtRuleName").val();
    var isEnable = $("#txtIsEnable").combobox("getValue");
    var ruleDesc = $("#txtRuleDesc").val();
    $('#tblAlarmEventRedefine').datagrid('options').queryParams = {
        "ruleName": ruleName,
        "isEnable": isEnable,
        "ruleDesc": ruleDesc
    };
    $('#tblAlarmEventRedefine').datagrid('load');
    $('#tblAlarmEventRedefine').datagrid('uncheckAll');
}


/**
 * 重置查询表单
 */
function reset(){
    resetForm('divFilter');
    $("#txtIsEnable").combobox("setValue", "-1");
}

/**
 * 删除规则
 */
function toDel(ruleId){
    $.messager.confirm("提示", "确定删除所选中项？", function(r){
        if (r == true) {
            var path = getRootName();
            var uri = path + "/monitor/alarmEventRedefine/delAlarmEventRedefine?ruleIds=" + ruleId;
            var ajax_param = {
                url: uri,
                type: "post",
                datdType: "json",
                error: function(){
                    $.messager.alert("错误", "ajax_error", "error");
                },
                success: function(data){
                    if (true == data || "true" == data) {
                        $.messager.alert("提示", "该重定义规则删除成功！", "info");
                        reloadTable();
                    }
                    else {
                        $.messager.alert("提示", "数据库操作异常，该重定义规则删除失败！", "error");
                    }
                }
            };
            ajax_(ajax_param);
        }
    });
}

/**
 * 批量删除
 */
function doBatchDel(){
    var path = getRootName();
    var checkedItems = $('#tblAlarmEventRedefine').datagrid("getChecked");
    var ruleIds = null;
    var idArray = [];
    $.each(checkedItems, function(index, item){
        idArray.push(this.ruleId);
    });
    ruleIds = idArray.join(",");
    if (null != ruleIds) {
        $.messager.confirm("提示", "确定删除所选中项？", function(r){
            if (r == true) {
                var path = getRootName();
                var uri = path + "/monitor/alarmEventRedefine/delAlarmEventRedefine?ruleIds=" + ruleIds;
                var ajax_param = {
                    url: uri,
                    type: "post",
                    datdType: "json",
                    error: function(){
                        $.messager.alert("错误", "ajax_error", "error");
                    },
                    success: function(data){
                        if (true == data || "true" == data) {
                            $.messager.alert("提示", "重定义规则删除成功！", "info");
                            reloadTable();
                        }
                        else {
                            $.messager.alert("提示", "数据库操作异常，重定义规则删除失败！", "error");
                        }
                    }
                };
                ajax_(ajax_param);
            }
        });
    }
    else {
        $.messager.alert('提示', '没有任何选中项', 'error');
    }
}

/**
 * 打开新增页面
 */
function doOpenAdd(){
	parent.$('#popWin').window({
    	title:'新增告警等级重定义规则',
        width:800,
        height : 500,
        minimizable:false,
        maximizable:false,
        collapsible:false,
        modal:true,
        href: getRootName() + '/monitor/alarmEventRedefine/toEditAlarmEventRedefine',
    });
}

/**
 * 打开编辑页面
 */
function doOpenUpdate(ruleId){
	parent.$('#popWin').window({
    	title:'编辑告警等级重定义规则',
        width:800,
        height : 500,
        minimizable:false,
        maximizable:false,
        collapsible:false,
        modal:true,
        href: getRootName() + '/monitor/alarmEventRedefine/toEditAlarmEventRedefine?ruleId='+ruleId,
    });
}

/**
 * 打开查看页面
 */
function doOpenDetail(ruleId){
	parent.$('#popWin').window({
    	title:'告警等级重定义规则详情',
        width:800,
        height : 500,
        minimizable:false,
        maximizable:false,
        collapsible:false,
        modal:true,
        href: getRootName() + '/monitor/alarmEventRedefine/toAlarmEventRedefineDetail?ruleId='+ruleId,
    });
}
