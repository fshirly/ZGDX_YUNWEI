$(document).ready(function(){
    doInitTable();
    
});

/**
 * 告警事件列表数据
 */
function doInitTable(){
    var path = getRootName();
    var moClassId = $("#moClassId").attr("alt");
    var alarmDefineIds = "";
    if (selectedEventIDArray.length > 0) {
        alarmDefineIds = selectedEventIDArray.join(",");
    }
    var uri = path + '/monitor/alarmEventRedefine/listForSelectEvent?moClassId=' + moClassId + '&alarmDefineIds=' + alarmDefineIds;
    $('#tblAlarmEventList').datagrid({
        iconCls: 'icon-edit',// 图标
        width: 'auto',
        height: 350,
        nowrap: false,
        striped: true,
        border: true,
        collapsible: false,// 是否可折叠的
        fitColumns: true,
        url: uri,
        remoteSort: false,
        idField: 'alarmDefineID',
        singleSelect: false,// 是否单选
        checkOnSelect: true,
        selectOnCheck: true,
        pagination: true,// 分页控件
        rownumbers: true,// 行号					
        columns: [[{
            field: 'alarmDefineID',
            checkbox: true
        }, {
            field: 'alarmName',
            title: '告警名称',
            width: 120,
            align: 'left'
        }, {
            field: 'alarmTypeName',
            title: '告警类型',
            width: 80,
            align: 'center'
        }, {
            field: 'categoryName',
            title: '告警分类',
            width: 80,
            align: 'center',
        }, {
            field: 'description',
            title: '告警描述',
            width: 150,
            align: 'center'
        }]]
    });
}

/**
 * 刷新表格数据
 */
function reloadTable(){
    var alarmName = $("#txtAlarmName").val();
    var alarmTypeID = $("#txtAlarmType").combobox("getValue");
    var categoryID = $("#txtAlarmCategory").combobox("getValue");
    $('#tblAlarmEventList').datagrid('options').queryParams = {
        "alarmName": alarmName,
        "alarmTypeID": alarmTypeID,
        "categoryID": categoryID
    };
    $('#tblAlarmEventList').datagrid('load');
    $('#tblAlarmEventList').datagrid('uncheckAll');
}

/**
 * 重置
 */
function resetEventFrom(){
	resetForm('divEventFilter');
	$("#txtAlarmType").combobox("setValue", "-1");
	$("#txtAlarmCategory").combobox("setValue", "-1");
}

/**
 * 添加告警事件
 */
function doAddDefinedEvent(){
    var resRows = $('#tblAlarmEventList').datagrid("getChecked");
    if (resRows.length > 0) {
        for (var i = 0; i < resRows.length; i++) {
            definedEventArray.push(resRows[i]);
            selectedEventIDArray.push(resRows[i].alarmDefineID)
        }
        var gridData = {
            "total": definedEventArray.length,
            "rows": definedEventArray
        };
        $('#tblDefinedEvent').datagrid({loadFilter: pagerFilter}).datagrid('loadData', gridData);
        $('#popWin3').window('close');
    }
    else {
        $.messager.alert('提示', '没有任何选中项', 'error');
    }
}
