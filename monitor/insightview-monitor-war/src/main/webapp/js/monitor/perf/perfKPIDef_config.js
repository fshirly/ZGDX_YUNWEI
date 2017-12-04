$(document).ready(function(){
    initTblKPIOfMOClass();
});

/**
 * 加载页面并初始化表格
 * @return
 */
function initTblKPIOfMOClass(){
    var kpiID = $("#r_kpiID").val();
    var path = getRootName();
    $('#tblKPIOfMOClass').datagrid({
        iconCls: 'icon-edit',// 图标
        width: 'auto',
        //        height: '310',
        height: 'auto',
        nowrap: false,
        striped: true,
        border: true,
        collapsible: false,// 是否可折叠的
        url: path + '/monitor/perfKPIDef/initTblKPIOfMOClass',
        remoteSort: false,
        fitColumns: true,
        queryParams: {
            "kpiID": kpiID,
        },
        idField: 'id',
        singleSelect: false,// 是否单选
        checkOnSelect: false,
        selectOnCheck: true,
        pagination: true,// 分页控件
        rownumbers: true,// 行号
        columns: [[{
            field: 'name',
            title: '指标名称',
            width: 100,
            align: 'center',
            formatter: function(value, row, index){
                if (value && value.length > 10) {
                    value2 = value.substring(0, 15) + "...";
                    return '<span title="' + value + '" style="cursor: pointer;" >' + value2 + '</span>';
                } else {
                    return value;
                }
            }
        }, {
            field: 'moClassName',
            title: '父对象类型',
            align: 'center',
            width: 80,
        }, {
            field: 'isSupport',
            title: '是否支持阈值',
            width: 80,
            align: 'center',
            formatter: function(value, row, index){
                if (value == 1) {
                    return '是';
                } else {
                    return '否';
                }
            }
        }, {
            field: 'ids',
            title: '操作',
            width: 80,
            align: 'center',
            formatter: function(value, row, index){
                return '<a style="cursor: pointer;" onclick="javascript:toDelKPIOfMOClass(' +
                row.id +
                ');"><img src="' +
                path +
                '/style/images/icon/icon_delete.png" title="删除" /></a>';
            }
        }]]
    });
}

/*
 * 更新表格
 */
function reloadTable(){
    var kpiID = $("#r_kpiID").val();
    $('#tblKPIOfMOClass').datagrid('options').queryParams = {
        "kpiID": kpiID,
    };
    reloadTableCommon_1('tblKPIOfMOClass');
}

function reloadTableCommon_1(dataGridId){
    $('#' + dataGridId).datagrid('load');
    $('#' + dataGridId).datagrid('uncheckAll');
}

/**
 * 选择父对象类型
 */
var treeLst = [];
function choseMObjectTree(){
    var childClassID = $("#r_classID").val();
    var path = getRootPatch();
    var uri = path + "/monitor/perfKPIDef/initParentTree?childClassID=" + childClassID;
    var ajax_param = {
        url: uri,
        type: "post",
        datdType: "json",
        data: {
            "trmnlBrandNm": "",
            "qyType": "brandName",
            "t": Math.random()
        },
        error: function(){
            $.messager.alert("错误", "ajax_error", "error");
        },
        success: function(data){
            dataTreeOrg = new dTree("dataTreeOrg", path +
            "/plugin/dTree/img/");
            dataTreeOrg.add(0, -1, "选择对象类型", "");
            
            // 得到树的json数据源
            var datas = eval('(' + data.menuLstJson + ')');
            // 遍历数据
            var gtmdlToolList = datas;
            for (var i = 0; i < gtmdlToolList.length; i++) {
                var _id = gtmdlToolList[i].classId;
                var _nameTemp = gtmdlToolList[i].classLable;
                var _newParentID = gtmdlToolList[i].newParentID;
                var _parent = gtmdlToolList[i].parentClassId;
                var className = gtmdlToolList[i].className;
                var _relationID = gtmdlToolList[i].relationID;
                var _relationPath = gtmdlToolList[i].relationPath;
                treeLst.push(_relationID);
                dataTreeOrg.add(_relationID, _newParentID, _nameTemp, "javascript:hiddenMObjectTreeSetValEasyUi('divMObject','ipt_moClassID','" +
                _id +
                "','" +
                _parent +
                "','" +
                _relationPath +
                "','" +
                _nameTemp +
                "');");
            }
            $('#dataMObjectTreeDiv').empty();
            $('#dataMObjectTreeDiv').append(dataTreeOrg + "");
            $('#divMObject').dialog('open');
        }
    }
    ajax_(ajax_param);
}

/*
 * 选择隐藏树，是否显示宿主设备
 */
function hiddenMObjectTreeSetValEasyUi(divId, controlId, showId, parentId, relationPath, showVal){
    $("#" + controlId).val(showVal);
    $("#" + controlId).attr("alt", showId);
    $("#" + divId).dialog('close');
}

/**
 * 点击确定按钮的操作
 */
function toConfig(){
    var checkResult = checkInfo("#tblConfigPerfKPIDef");
    if (checkResult) {
        isExistKPIOfMOClass();
    }
}

/**
 * 重置表单
 */
function resetKPIOfMOClassForm(){
    $("#ipt_moClassID").attr("alt", "");
    $("#ipt_moClassID").val("");
    $("input:radio[name='isSupport'][value=1]").attr("checked", 'checked');
}

/**
 * 关系是否存在
 */
function isExistKPIOfMOClass(){
    var kpiID = $("#r_kpiID").val();
    var moClassID = $("#ipt_moClassID").attr("alt");
//    console.log("kpiID==" + kpiID + ",moClassID==" + moClassID);
    var path = getRootName();
    var uri = path + "/monitor/perfKPIDef/isExistKPIOfMOClass";
    var ajax_param = {
        url: uri,
        type: "post",
        dateType: "json",
        async: false,
        data: {
            "kpiID": kpiID,
            "moClassID": moClassID,
            "t": Math.random()
        },
        error: function(){
            $.messager.alert("错误", "ajax_error", "error");
        },
        success: function(data){
            if (false == data || "false" == data) {
                $.messager.alert("提示", "该指标与对象类型关系已存在！", "info");
            } else {
                doConfig();
            }
        }
    };
    ajax_(ajax_param);
}

/**
 * 配置关系
 */
function doConfig(){
    var kpiID = $("#r_kpiID").val();
    var moClassID = $("#ipt_moClassID").attr("alt");
//    console.log("kpiID==" + kpiID + ",moClassID==" + moClassID);
    var isSupport = $('input[name="isSupport"]:checked').val();
    var path = getRootName();
    var uri = path + "/monitor/perfKPIDef/addKPIOfMOClass";
    var ajax_param = {
        url: uri,
        type: "post",
        dateType: "json",
        data: {
            "kpiID": kpiID,
            "moClassID": moClassID,
            "isSupport": isSupport,
            "t": Math.random()
        },
        error: function(){
            $.messager.alert("错误", "ajax_error", "error");
        },
        success: function(data){
            if (data == true) {
                $.messager.alert("提示", "指标与对象类型关系配置成功！", "info");
                resetKPIOfMOClassForm();
                reloadTable();
            } else {
                $.messager.alert("提示", "指标与对象类型关系配置失败！", "error");
            }
        }
    };
    ajax_(ajax_param);
}

function toDelKPIOfMOClass(id){
    $.messager.confirm("提示", "确定删除此指标与对象类型关系?", function(r){
        if (r == true) {
            var path = getRootName();
            var uri = path + "/monitor/perfKPIDef/delKPIOfMOClass";
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
                        $.messager.alert("提示", "指标与对象类型关系删除成功！", "info");
                        reloadTable();
                    } else {
                        $.messager.alert("提示", "指标与对象类型关系删除失败！", "error");
                        reloadTable();
                    }
                }
            };
            ajax_(ajax_param);
        }
    });
}
