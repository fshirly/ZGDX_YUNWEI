$(function(){
    //初始化资源等级下拉框
    $('#txtResLevel').combobox({
        panelHeight: '120',
        valueField: 'key',
        textField: 'val',
        url: getRootName() + '/monitor/alarmEventRedefine/getAllResLevel',
        editable: false,
    });
    $("#txtResLevel ").combobox('setValue', -1);
    doInitResTable();
});

/**
 * 资源列表
 *
 */
function doInitResTable(){
    var path = getRootName();
    var moClassId = $("#moClassId").attr("alt");
    var moClassIds = moClassId;
    if (moClassId == 6) {
        moClassIds = "6,59,60";
    }
    var moIds = "";
    if (selectedMoIdArray.length > 0) {
        moIds = selectedMoIdArray.join(",");
    }
    var uri = path + '/monitor/alarmEventRedefine/listForSelectRes?moClassIds=' + moClassIds + '&moIds=' + moIds;
    $("#tblForSelectRes").datagrid({
        width: 'auto',
        height: 330,
        nowrap: false,
        striped: true,
        border: true,
        collapsible: false,// 是否可折叠的
        fitColumns: true,
        url: uri,
        idField: 'moId',
        singleSelect: false,// 是否单选
        checkOnSelect: false,
        selectOnCheck: true,
        pagination: true,// 分页控件
        rownumbers: true,// 行号
        columns: [[{
            field: 'moId',
            checkbox: true
        }, {
            field: "moName",
            title: "资源名称",
            align: "center",
            width: 100
        }, {
            field: "moAlias",
            title: "资源别名",
            align: "center",
            width: 100,
            formatter: function(value, row, index){
                if (value == null || value == "null" || value == "") {
                    return "";
                }
                else {
                    return value;
                }
            }
        }, {
            field: "deviceIp",
            title: "资源地址",
            align: "center",
            width: 100,
        }, {
            field: "classLable",
            title: "资源类型",
            align: "center",
            width: 80,
        }, {
            field: "resLevelName",
            title: "资源等级",
            align: "center",
            width: 70,
            formatter: function(value, row, index){
                if (value == null || value == "null" || value == "") {
                    return "";
                }
                else {
                    return value;
                }
            }
        }, {
            field: "domainName",
            title: "所属管理域",
            align: "center",
            width: 90,
            formatter: function(value, row, index){
                if (value == null || value == "null" || value == "") {
                    return "";
                }
                else {
                    return value;
                }
            }
        }]]
    });
}


/**
 * 更新资源列表
 */
function reloadResTable(){
    var txtResName = $("#txtResName").val();
    var txtDeviceIp = $("#txtDeviceIp").val();
    var txtResLevel = $("#txtResLevel").combobox("getValue");
    $('#tblForSelectRes').datagrid('options').queryParams = {
        "moName": txtResName,
        "deviceIp": txtDeviceIp,
        "resLevel": txtResLevel,
    };
    reloadTableCommon('tblForSelectRes');
}

function resetResMOForm(){
    resetForm('tblSearchRes');
    $("#txtResLevel").combobox("setValue", -1);
}

/**
 * 选择添加资源
 * @return
 */
function toAddResMO(){
    var resRows = $('#tblForSelectRes').datagrid('getSelections');
    if (resRows.length > 0) {
        for (var i = 0; i < resRows.length; i++) {
            selectedResArray.push(resRows[i]);
            selectedMoIdArray.push(resRows[i].moId)
        }
        var gridData = {
            "total": selectedResArray.length,
            "rows": selectedResArray
        };
        $('#tblMORes').datagrid({
            loadFilter: pagerFilter
        }).datagrid('loadData', gridData);
        reloadTableCommon('tblMORes');
        $("#moClassId").attr("disabled", true);
        $('#popWin3').window('close');
    }
    else {
        $.messager.alert('提示', '没有任何选中项', 'error');
    }
    
}
