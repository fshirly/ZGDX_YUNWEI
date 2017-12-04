var path = getRootName();
var levelMap = getMap();
//已经选择的资源
var selectedResArray = [];
//已经选择的资源ID
var selectedMoIdArray = [];
//告警事件的列表
var definedEventArray = [];
//已经选择的告警事件ID 
var selectedEventIDArray = [];
doInitMonitorResTable();

// 定义全局变量：告警事件当前编辑的行
var editRow = undefined;
var selectedAlarmLevelID = undefined;
var selectedAlarmLevelName = undefined;
var selectedLevelColorName = undefined;
var selectedLevelColor = undefined;
$(document).ready(function(){
    $('#ipt_alarmLevelId').combobox({
        panelHeight: '120',
        panelWidth: '180',
        editable: false,
        onSelect: function(record){
            changeLevelColor(record.value);
        }
    });
    initAlarmLevel();
    //初始化已经选择的告警事件列表
    doInitDefinedEventTable();
    //    $("#divSame").hide();
    //    $("#divDifferent").show();
    //初始化资源类型树
    initOrgTree();
    var ruleId = $("#ruleId").val();
    if (ruleId != null && ruleId != "") {
        //初始化资源类型
        var editMoClassId = $("#editMoClassId").val();
        var editMoClassName = $("#editMoClassName").val();
        $("#moClassId").attr("alt", editMoClassId);
        $("#moClassId").val(editMoClassName);
        $("#moClassId").attr("disabled", true);
        //初始化已经选择的资源
        initSelectedRes(ruleId);
        //初始化已经添加的事件
        initSelectedEvent(ruleId);
    }
    else {
        editIsSame();
    }
    $("#combdtree").hide();
});

/**
 * 初始化map_,给map_对象增加方法，使map_像Map
 */
function getMap(){
    var map_ = new Object();
    map_.put = function(key, value){
        map_[key + '_'] = value;
    };
    map_.get = function(key){
        return map_[key + '_'];
    };
    map_.remove = function(key){
        delete map_[key + '_'];
    };
    map_.keyset = function(){
        var ret = "";
        for (var p in map_) {
            if (typeof p == 'string' && p.substring(p.length - 1) == "_") {
                ret += ",";
                ret += p.substring(0, p.length - 1);
            }
        }
        if (ret == "") {
            return ret.split(",");
        }
        else {
            return ret.substring(1).split(",");
        }
    };
    return map_;
}


/**
 * 监测对象资源列表
 */
function doInitMonitorResTable(){
    var $dg = $("#tblMORes");
    $("#tblMORes").datagrid({
        width: 695,
        height: 320,
        nowrap: false,
        striped: true,
        border: true,
        collapsible: false,// 是否可折叠的
        fitColumns: true,
        remoteSort: false,
        idField: 'moId',
        singleSelect: false,// 是否单选
        checkOnSelect: false,
        selectOnCheck: true,
        pagination: true,// 分页控件
        rownumbers: true,// 行号
        scrollbarSize: 0,
        toolbar: [{
            text: "添加资源",
            iconCls: "icon-add",
            handler: function(){
                toAddMORes();
            }
        }, {
            'text': '删除',
            'iconCls': 'icon-cancel',
            handler: function(){
                doBatchDelMORes();
            }
        }],
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
        }, {
            field: "domainName",
            title: "所属管理域",
            align: "center",
            width: 90,
        }, {
            field: "moIds",
            title: "操作",
            align: "center",
            width: 50,
            formatter: function(value, row, index){
                return '<a style="cursor: pointer;" onclick="javascript:toDelMORes(' +
                row.moId +
                ');"><img src="' +
                path +
                '/style/images/icon/icon_delete.png" title="删除" /></a>'
            }
        }]]
    }).datagrid({
        loadFilter: pagerFilter
    }).datagrid('loadData', {
        "total": selectedResArray.length,
        "rows": selectedResArray
    });
}

/**
 * 删除资源
 */
function toDelMORes(moId){
    $.messager.confirm("提示", "确定删除所选中的项？", function(r){
        if (r == true) {
            var index = getDelResMOIndex(moId);
            selectedResArray.splice(index, 1);
            var idIndex = getEventIDIndex(moId, selectedMoIdArray);
            if (idIndex != -1) {
                selectedMoIdArray.splice(idIndex, 1);
            }
            var gridData = {
                "total": selectedResArray.length,
                "rows": selectedResArray
            };
            $('#tblMORes').datagrid({
                loadFilter: pagerFilter
            }).datagrid('loadData', gridData);
            reloadTableCommon('tblMORes');
            if (selectedResArray.length <= 0) {
                $("#moClassId").removeAttr("disabled");
            }
        }
    });
}

/**
 * 批量删除监测资源
 */
function doBatchDelMORes(){
    var path = getRootName();
    var rows = $('#tblMORes').datagrid('getSelections');
    if (rows.length > 0) {
        $.messager.confirm("提示", "确定删除所选中的项？", function(r){
            if (r == true) {
                for (var i = 0; i < rows.length; i++) {
                    var moId = rows[i].moId;
                    var index = getDelResMOIndex(moId);
                    selectedResArray.splice(index, 1);
                    //获得删除的id在已选择的id数组中的索引
                    var idIndex = getEventIDIndex(moId, selectedMoIdArray);
                    if (idIndex != -1) {
                        selectedMoIdArray.splice(idIndex, 1);
                    }
                }
                var gridData = {
                    "total": selectedResArray.length,
                    "rows": selectedResArray
                };
                $('#tblMORes').datagrid({
                    loadFilter: pagerFilter
                }).datagrid('loadData', gridData);
                reloadTableCommon('tblMORes');
                if (selectedResArray.length <= 0) {
                    $("#moClassId").removeAttr("disabled");
                }
            }
        });
    }
    else {
        $.messager.alert('提示', '没有任何选中项', 'error');
    }
}

function reloadTableCommon(dataGridId){
    $('#' + dataGridId).datagrid('load');
    $('#' + dataGridId).datagrid('uncheckAll');
}

/**
 * 资源类型树
 */
function initOrgTree(){
    var path = getRootPatch();
    var uri = path + "/monitor/alarmEventRedefine/findResClassLst";
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
            dataResTree = new dTree("dataResTree", path + "/plugin/dTree/img/");
            dataResTree.add(0, -1, "选择资源类型", "");
            
            // 得到树的json数据源
            var datas = eval('(' + data.menuLstJson + ')');
            // 遍历数据
            var gtmdlToolList = datas;
            for (var i = 0; i < gtmdlToolList.length; i++) {
                var _id = gtmdlToolList[i].classId;
                var _nameTemp = gtmdlToolList[i].classLable;
                var _parent = gtmdlToolList[i].parentClassId;
                var _childCount = gtmdlToolList[i].childCount;
                dataResTree.add(_id, _parent, _nameTemp, "javascript:hiddenResDTreeSetVal('moClassId','" + _id + "','" + _nameTemp + "','" + _childCount + "');");
            }
            $('#dataResTreeDiv').append(dataResTree + "");
        }
    }
    ajax_(ajax_param);
}

/**
 * 选择关闭隐藏资源类型树
 */
function hiddenResDTreeSetVal(controlId, showId, showVal, childCount){
    //判断是否为叶子节点
    if (childCount <= 0) {
        var moClassId = $("#moClassId").attr("alt");
        if (moClassId != null && moClassId != "" && moClassId != -1 && selectedEventIDArray.length > 0) {
            $.messager.confirm("提示", "已选择的告警事件将会清除，确定切换？", function(r){
                if (r == true) {
                    definedEventArray = [];
                    selectedEventIDArray = [];
                    var gridData = {
                        "total": definedEventArray.length,
                        "rows": definedEventArray
                    };
                    $('#tblDefinedEvent').datagrid({
                        loadFilter: pagerFilter
                    }).datagrid('loadData', gridData);
                    reloadTableCommon('tblDefinedEvent');
					
					$("#" + controlId).val(showVal);
			        $("#" + controlId).attr("alt", showId);
			        var item = document.getElementById("combdtree");
			        if (item) {
			            item.style.display = 'none';
			        }
                }
            });
        }else{
	        $("#" + controlId).val(showVal);
	        $("#" + controlId).attr("alt", showId);
	        var item = document.getElementById("combdtree");
	        if (item) {
	            item.style.display = 'none';
	        }
		}
    }
}

/**
 * 添加资源
 */
function toAddMORes(){
    var moClassName = $("#moClassId").val();
    if (moClassName != "" && moClassName != null) {
        parent.$('#popWin3').window({
            title: '添加资源',
            width: 850,
            height: 500,
            minimizable: false,
            maximizable: false,
            collapsible: false,
            modal: true,
            href: getRootName() + '/monitor/alarmEventRedefine/toAddResMO',
        });
    }
    else {
        $.messager.alert('提示', '请选择资源类型！', 'info');
    }
}

/**
 * 初始化告警等级
 */
function initAlarmLevel(){
    var levelJson = $("#levelJson").val();
    var levelArray = jQuery.parseJSON(levelJson);
    for (var i = 0; i < levelArray.length; i++) {
        var alarmLevel = levelArray[i].alarmLevelID;
        var levelColorName = levelArray[i].levelColorName;
        var levelColor = levelArray[i].levelColor;
        levelMap.put(alarmLevel, levelColorName + "," + levelColor);
    }
    changeLevelColor(1);
}

/**
 * 告警等级点击事件
 */
function changeLevelColor(alarmLevelID){
    var levelColorName = levelMap.get(alarmLevelID).split(",")[0];
    var levelColor = levelMap.get(alarmLevelID).split(",")[1];
    $("#alarmlevelVal").text(levelColorName);
    $("#alarmlevelVal").css({
        "background-color": levelColor
    });
}

/**
 * 告警等级单选按钮的点击事件
 */
function editIsSame(){
    var isSame = $('input[name="isSame"]:checked').val();
    if (isSame == "1") {
        $("#divSame").show();
        $("#divDifferent").hide();
    }
    else {
        $("#divSame").hide();
        $("#divDifferent").show();
    }
}

/**
 * 初始化已经选择的告警事件的列表
 */
function doInitDefinedEventTable(){
    var $dg = $("#tblDefinedEvent");
    var levelJson = $("#levelJson").val();
    $("#tblDefinedEvent").datagrid({
        width: 695,
        height: 350,
        nowrap: false,
        striped: true,
        border: true,
        collapsible: false,// 是否可折叠的
        fitColumns: true,
        remoteSort: false,
        idField: 'alarmDefineID',
        singleSelect: false,// 是否单选
        checkOnSelect: false,
        selectOnCheck: true,
        pagination: true,// 分页控件
        rownumbers: true,// 行号
        scrollbarSize: 0,
        toolbar: [{
            text: "添加事件",
            iconCls: "icon-add",
            handler: function(){
                toAddDefinedEvent();
            }
        }, {
            'text': '删除',
            'iconCls': 'icon-cancel',
            handler: function(){
                doBatchDelDefinedEvent();
            }
        }],
        onBeforeEdit: function(index, row){
            row.editing = true;
            $dg.datagrid('refreshRow', index);
        },
        onAfterEdit: function(index, row){
            row.editing = false;
            $dg.datagrid('refreshRow', index);
        },
        onCancelEdit: function(index, row){
            row.editing = false;
            $dg.datagrid('refreshRow', index);
        },
        columns: [[{
            field: 'alarmDefineID',
            checkbox: true
        }, {
            field: "alarmName",
            title: "告警名称",
            align: "center",
            width: 100
        }, {
            field: "alarmTypeName",
            title: "告警类型",
            align: "center",
            width: 80,
            sortable: true
        }, {
            field: "alarmLevelID",
            title: "告警等级",
            align: "center",
            width: 80,
            formatter: function(value, row){
                return row.alarmLevelName;
            },
            editor: {
                type: 'combobox',
                options: {
                    required: true,
                    editable: false,
                    data: [{
                        "alarmLevelID": 1,
                        "alarmLevelValue": 1,
                        "levelIcon": null,
                        "alarmLevelName": "紧急",
                        "levelColor": "#ff0000",
                        "levelColorName": "红色",
                        "isSystem": 1
                    }, {
                        "alarmLevelID": 2,
                        "alarmLevelValue": 2,
                        "levelIcon": null,
                        "alarmLevelName": "严重",
                        "levelColor": "#ff9900",
                        "levelColorName": "橙色",
                        "isSystem": 1
                    }, {
                        "alarmLevelID": 3,
                        "alarmLevelValue": 3,
                        "levelIcon": null,
                        "alarmLevelName": "一般",
                        "levelColor": "#ffff00",
                        "levelColorName": "黄色",
                        "isSystem": 1
                    }, {
                        "alarmLevelID": 4,
                        "alarmLevelValue": 4,
                        "levelIcon": null,
                        "alarmLevelName": "提示",
                        "levelColor": "#0000ff",
                        "levelColorName": "蓝色",
                        "isSystem": 1
                    }, {
                        "alarmLevelID": 5,
                        "alarmLevelValue": 5,
                        "levelIcon": null,
                        "alarmLevelName": "未确定",
                        "levelColor": "#c0c0c0",
                        "levelColorName": "灰色",
                        "isSystem": 1
                    }],
                    valueField: 'alarmLevelID',
                    textField: 'alarmLevelName',
                    onSelect: function(record){
                        setLevelColor(record.alarmLevelID, record.alarmLevelName);
                    }
                    
                }
            }
        }, {
            field: "levelColorName",
            title: "告警等级颜色",
            align: "center",
            width: 80,
            editor: {
                type: 'validatebox',
                editable: false,
                required: false
            },
            formatter: function(value, row){
                return "<div style='width:55%;margin-left: 23%;background:" + row.levelColor + ";'>" + value + "</div>";
            }
        }, {
            field: "alarmDefineIDs",
            title: "操作",
            align: "center",
            width: 60,
            formatter: function(value, row, index){
                if (row.isModify == "" || row.isModify == null) {
                    return '<a style="cursor: pointer;" onclick="javascript:toUpdate(' +
                    row.alarmDefineID +
                    "," +
                    index +
                    ');"><img src="' +
                    path +
                    '/style/images/icon/icon_modify.png" title="修改" /></a> &nbsp;<a style="cursor: pointer;" onclick="javascript:toDel(' +
                    row.alarmDefineID +
                    ');"><img src="' +
                    path +
                    '/style/images/icon/icon_delete.png" title="删除" /></a>';
                }
                else {
                    return '<a style="cursor: pointer;" onclick="javascript:toSave(' +
                    row.alarmDefineID +
                    "," +
                    index +
                    ');"><img src="' +
                    path +
                    '/plugin/easyui/themes/icons/filesave.png" title="保存" /></a> &nbsp;<a style="cursor: pointer;" onclick="javascript:toDel(' +
                    row.alarmDefineID +
                    ');"><img src="' +
                    path +
                    '/style/images/icon/icon_delete.png" title="删除" /></a>';
                }
            }
        }]]
    }).datagrid({
        loadFilter: pagerFilter
    }).datagrid('loadData', {
        "total": definedEventArray.length,
        "rows": definedEventArray
    });
}

/**
 * 缓存分页
 */
function pagerFilter(data){
    if (typeof data.length == 'number' && typeof data.splice == 'function') { // 判断数据是否是数组
        data = {
            total: data.length,
            rows: data
        }
    }
    var dg = $(this);
    var opts = dg.datagrid('options');
    var pager = dg.datagrid('getPager');
    pager.pagination({
        onSelectPage: function(pageNum, pageSize){
            opts.pageNumber = pageNum;
            opts.pageSize = pageSize;
            pager.pagination('refresh', {
                pageNumber: pageNum,
                pageSize: pageSize
            });
            dg.datagrid('loadData', data);
        }
    });
    if (!data.originalRows) {
        data.originalRows = (data.rows);
    }
    var start = (opts.pageNumber - 1) * parseInt(opts.pageSize);
    var end = start + parseInt(opts.pageSize);
    data.rows = (data.originalRows.slice(start, end));
    return data;
}

/**
 * 选择告警事件
 */
function toAddDefinedEvent(){
    var moClassName = $("#moClassId").val();
    if (moClassName == null || moClassName == "" || selectedResArray == null || selectedResArray.length <= 0) {
        $.messager.alert('提示', '请先添加资源！', 'info');
    }
    else {
        parent.$('#popWin3').window({
            title: '添加告警事件定义告警等级',
            width: 850,
            height: 500,
            minimizable: false,
            maximizable: false,
            collapsible: false,
            modal: true,
            href: getRootName() + '/monitor/alarmEventRedefine/toAddDefinedEvent',
        });
    }
}

/**
 * 删除告警事件
 */
function toDel(alarmDefineID){
    $.messager.confirm("提示", "确定删除所选中的项？", function(r){
        if (r == true) {
            var index = getDelEventIndex(alarmDefineID);
            definedEventArray.splice(index, 1);
            var idIndex = getEventIDIndex(alarmDefineID, selectedEventIDArray);
            if (idIndex != -1) {
                selectedEventIDArray.splice(idIndex, 1);
            }
            var gridData = {
                "total": definedEventArray.length,
                "rows": definedEventArray
            };
            $('#tblDefinedEvent').datagrid({
                loadFilter: pagerFilter
            }).datagrid('loadData', gridData);
            reloadTableCommon('tblDefinedEvent');
        }
    });
}

/**
 * 批量删除告警事件
 */
function doBatchDelDefinedEvent(){
    var path = getRootName();
    var rows = $('#tblDefinedEvent').datagrid('getChecked');
    if (rows.length > 0) {
        $.messager.confirm("提示", "确定删除所选中的项？", function(r){
            if (r == true) {
                for (var i = 0; i < rows.length; i++) {
                    var alarmDefineID = rows[i].alarmDefineID;
                    var index = getDelEventIndex(alarmDefineID);
                    definedEventArray.splice(index, 1);
                    //获得删除的id在已选择的id数组中的索引
                    var idIndex = getEventIDIndex(alarmDefineID, selectedEventIDArray);
                    if (idIndex != -1) {
                        selectedEventIDArray.splice(idIndex, 1);
                    }
                }
                var gridData = {
                    "total": definedEventArray.length,
                    "rows": definedEventArray
                };
                $('#tblDefinedEvent').datagrid({
                    loadFilter: pagerFilter
                }).datagrid('loadData', gridData);
                reloadTableCommon('tblDefinedEvent');
            }
        });
    }
    else {
        $.messager.alert('提示', '没有任何选中项', 'error');
    }
}

/**
 * 获得删除的已选的告警事件的索引
 * @param moId
 * @return
 */
function getDelEventIndex(alarmDefineID){
    for (var m = 0; m < definedEventArray.length; m++) {
        if (alarmDefineID == definedEventArray[m].alarmDefineID) {
            return m;
        }
    }
    return -1;
}

/**
 * 获得删除的已选的监测资源的索引
 * @param moId
 * @return
 */
function getDelResMOIndex(moId){
    for (var m = 0; m < selectedResArray.length; m++) {
        if (moId == selectedResArray[m].moId) {
            return m;
        }
    }
    return -1;
}

/**
 * 获得删除的id在已选事件id数组中的索引
 */
function getEventIDIndex(id, array){
    for (var i = 0, n = array.length; i < n; i++) {
        if (array[i] == id) {
            return i;
        }
    }
    return -1;
}

/**
 * 点击编辑
 * @param {Object} alarmDefineID
 * @param {Object} index
 */
function toUpdate(alarmDefineID, index){
    var $dg = $("#tblDefinedEvent");
    //变换图标
    $dg.datagrid('updateRow', {
        index: index,
        row: {
            isModify: 1
        }
    });
    // 修改之前先关闭已经开启的编辑行，当调用endEdit该方法时会触发onAfterEdit事件
    if (editRow != undefined) {
        $dg.datagrid("endEdit", editRow);
        $dg.datagrid('updateRow', {
            index: editRow,
            row: {
                isModify: null
            }
        });
        editRow = undefined;
    }
    // 当无编辑行时
    if (editRow == undefined) {
        // 开启编辑
        $dg.datagrid("beginEdit", index);
        // 把当前开启编辑的行赋值给全局变量editRow
        editRow = index;
        // 当开启了当前选择行的编辑状态之后，
        // 应该取消当前列表的所有选择行，要不然双击之后无法再选择其他行进行编辑
        $dg.datagrid("unselectAll");
    }
}

function setLevelColor(alarmLevelID, alarmLevelName){
    var levelColorName = levelMap.get(alarmLevelID).split(",")[0];
    var levelColor = levelMap.get(alarmLevelID).split(",")[1];
    $(".datagrid-row-editing td[field=levelColorName]").text(levelColorName);
    selectedAlarmLevelID = alarmLevelID;
    selectedAlarmLevelName = alarmLevelName;
    selectedLevelColorName = levelColorName;
    selectedLevelColor = levelColor;
}

/**
 * 保存
 */
function toSave(alarmDefineID, index){
    var $dg = $("#tblDefinedEvent");
    //校验
    var isValid = $dg.datagrid('validateRow', editRow);
    if (!isValid) {
        return;
    }
    //结束表格中所有的编辑行
    var rows = $dg.datagrid('getRows');
    for (var i = 0; i < rows.length; i++) {
        $dg.datagrid('endEdit', i);
    }
    
    //获得变化的编辑行 easyui有缓存的功能。它会记录你第一次加载时候和更新后的数据。你编辑完后，getChange会去对比
    var updatedRow = $dg.datagrid('getChanges', "updated");
    if (updatedRow != null && updatedRow.length > 0) {
        updatedRow[updatedRow.length - 1].isModify = null;
        updatedRow[updatedRow.length - 1].alarmLevelID = selectedAlarmLevelID;
        updatedRow[updatedRow.length - 1].alarmLevelName = selectedAlarmLevelName;
        updatedRow[updatedRow.length - 1].levelColorName = selectedLevelColorName;
        updatedRow[updatedRow.length - 1].levelColor = selectedLevelColor;
        //更新表格行数据
        $dg.datagrid('updateRow', {
            index: index,
            row: updatedRow[updatedRow.length - 1]
        });
        
        //更新已经选择的告警事件数组数据
        var alarmDefineID = updatedRow.alarmDefineID;
        for (var m = 0; m < definedEventArray.length; m++) {
            if (alarmDefineID == definedEventArray[m].alarmDefineID) {
                definedEventArray[m] = updatedRow;
            }
        }
    }
    //若果没有变化
    else {
        $dg.datagrid('updateRow', {
            index: index,
            row: {
                isModify: null
            }
        });
    }
    
    // 保存后，复原到一般状态
    editRow = undefined;
    selectedAlarmLevelID = undefined;
    selectedAlarmLevelName = undefined;
    selectedLevelColorName = undefined;
    selectedLevelColor = undefined;
    $dg.datagrid("unselectAll");
}

/**
 * 跳转至指定tab页
 */
function selectTab(index){
    $('#editRuleTab').tabs('select', index);
}

/**
 * 新增
 */
function toAdd(){
    var result = checkInfo("#divRuleInfo");
    if (result) {
        if (selectedResArray != null && selectedResArray.length > 0) {
            var isSame = $('input[name="isSame"]:checked').val();
            if (isSame == "2" && (definedEventArray == null || definedEventArray.length <= 0)) {
                $.messager.alert('提示', '请添加告警事件！', 'info');
                return;
            }
            else {
                doAdd();
            }
        }
        else {
            $.messager.alert('提示', '至少添加一个监测对象！', 'info');
            return;
        }
    }
    else {
        $.messager.alert('提示', '请完善基本信息！', 'info');
        return;
    }
}

function doAdd(){
    $("#btnSave").attr('disabled', true);
    var load = "<div id='loading'><img src =' " + getRootName() + "/style/images/loading2.gif' style='margin-top: 30px; margin-left: 20px;' /></div>";
    var loadlog = $(load).window({
        title: '正在保存中，请稍后....',
        width: 200,
        height: 150,
        modal: true,
        minimizable: false,
        maximizable: false,
        collapsible: false,
        closable: false
    });
    var ruleId = $("#ruleId").val();
    if (ruleId == "") {
        ruleId = -1;
    }
    var ruleName = $("#ipt_ruleName").val();
    var isEnable = $('input[name="isEnable"]:checked').val();
    var ruleDesc = $("#ipt_ruleDesc").val();
    var moClassId = $("#moClassId").attr("alt");
    var choosedMoIds = selectedMoIdArray.join(",")
    var isSame = $('input[name="isSame"]:checked').val();
    var dataToSend = {
        "ruleId": ruleId,
        "ruleName": ruleName,
        "isEnable": isEnable,
        "ruleDesc": ruleDesc,
        "moClassId": moClassId,
        "choosedMoIds": choosedMoIds,
        "isSame": isSame,
    };
    
    //所有告警事件相同告警等级
    if (isSame == 1) {
        dataToSend.alarmLevelId = $("#ipt_alarmLevelId").combobox("getValue");
    }
    //不同告警事件定义不同告警等级
    else {
        eventData = JSON.stringify(definedEventArray);
        dataToSend.eventData = eventData;
    }
    
    var ajax_param = {
        url: getRootName() + '/monitor/alarmEventRedefine/editEventRedefine',
        type: "post",
        datdType: "json",
        data: dataToSend,
        error: function(){
            $("#btnSave").removeAttr("disabled");
            loadlog.panel("close");
            $.messager.alert("错误", "ajax_error", "error");
        },
        success: function(data){
            $("#btnSave").removeAttr("disabled");
            loadlog.panel("close");
            if (data.isEffectiveRes == false || data.isEffectiveRes == "false") {
                var tipTitle = '添加的的监测资源中所有的资源都已经有对应的告警重定义规则，请重新选择资源！';
				doCtrlDlg(tipTitle,data);
            }
            else {
	            if (true == data.editFlag || "true" == data.editFlag) {
	                if (data.definedEventLst != null && data.definedEventLst.length > 0) {
	                    var tipTitle = '添加的的监测资源中一下资源已经有对应的告警重定义规则，不能添加！';
						doCtrlDlg(tipTitle,data);
	                }
	            }
	            else {
	                $.messager.alert("错误", "重定义规则保存失败！", "error");
	            }
	            $('#popWin').window('close');
	            window.frames['component_2'].reloadTable();
            }
        }
    };
    ajax_(ajax_param);
}

function doCtrlDlg(tipTitle,data){
    var dlgsb = "<div id='dlgtask'><div class='conditionsBtn'>" +
    "<a href='javascript:void(0);' onclick='javascript:closeDlgTask()' id='btnDefineEventClose'>关闭</a></div></div>";
    var dlgTask = $(dlgsb).appendTo(document.body).dialog({
        title: '提示',
        width: 550,
        height: 300,
        content: '',
        modal: true,
        closed: true,
        onClose: function(){
            $('#dlgTask').panel('destroy');
        }
    });
    var tipTitle = '添加的的监测资源中以下资源已经有对应的告警重定义规则，不能添加！';
    if (data.isEffectiveRes == false || data.isEffectiveRes == "false") {
        tipTitle = '添加的的监测资源中所有的资源都已经有对应的告警重定义规则，请重新选择！';
    }
    var serviceTab = $('<table id="definedEventData"></table>').prependTo(dlgTask.dialog("body")).datagrid({
        title: tipTitle,
        fit: true,// 自动大小
        fitColumns: true,
        scrollbarSize: 0,
        columns: [[{
            field: 'moName',
            title: '资源名称',
            width: 100,
			align: "center",
        }, {
            field: 'deviceIp',
            title: '资源地址',
            width: 100,
			align: "center",
        }, {
            field: 'ruleName',
            title: '规则名称',
            width: 100,
			align: "center",
        }]]
    });
    $('#definedEventData').datagrid('loadData', data.definedEventMap);
    closePanle();
    dlgTask.dialog("open");
}

/**
 * 关闭对话层
 * @return
 */
function closePanle(){
    $('#dlg').panel('close');
    $('#dlg').panel('destroy');
}

function closeDlgTask(){
    $('#dlgtask').panel('close');
    $('#dlgtask').panel('destroy');
}

/**
 * 校验规则名称是否已存在
 */
function checkRuleName(){
    var ruleName = $("#ipt_ruleName").val();
    if (ruleName != null && ruleName != "") {
        var ruleId = $("#ruleId").val();
        if (ruleId == "" || ruleId == null || ruleId == "null") {
            ruleId = -1;
        }
        var path = getRootName();
        var uri = path + "/monitor/alarmEventRedefine/checkRuleName";
        var ajax_param = {
            url: uri,
            type: "post",
            datdType: "json",
            data: {
                "ruleId": ruleId,
                "ruleName": ruleName,
            },
            error: function(){
                $.messager.alert("错误", "ajax_error", "error");
            },
            success: function(data){
                if (false == data || "false" == data) {
                    $.messager.alert("提示", "该规则名称已存在,请重新输入！", "error");
                    $("#ipt_ruleName").val("");
                }
            }
        };
        ajax_(ajax_param);
    }
}

/**
 * 初始化已经添加的资源
 */
function initSelectedRes(ruleId){
    var path = getRootName();
    var uri = path + "/monitor/alarmEventRedefine/initSelectedRes?ruleId=" + ruleId;
    var ajax_param = {
        url: uri,
        type: "post",
        datdType: "json",
        error: function(){
            $.messager.alert("错误", "ajax_error", "error");
        },
        success: function(data){
            if (data != null) {
                if (data.length > 0) {
                    for (var i = 0; i < data.length; i++) {
                        selectedResArray.push(data[i]);
                        selectedMoIdArray.push(data[i].moId)
                    }
                    var gridData = {
                        "total": selectedResArray.length,
                        "rows": selectedResArray
                    };
                    $('#tblMORes').datagrid({
                        loadFilter: pagerFilter
                    }).datagrid('loadData', gridData);
                    reloadTableCommon('tblMORes');
                }
            }
        }
    };
    ajax_(ajax_param);
}

/**
 * 初始化已经添加的事件
 */
function initSelectedEvent(ruleId){
    var path = getRootName();
    var uri = path + "/monitor/alarmEventRedefine/initSelectedEvent?ruleId=" + ruleId;
    var ajax_param = {
        url: uri,
        type: "post",
        datdType: "json",
        error: function(){
            $.messager.alert("错误", "ajax_error", "error");
        },
        success: function(data){
            if (data != null) {
                var isSame = $("#isSame").val();
                if (isSame == "1") {
                    $("#divSame").show();
                    $("#divDifferent").hide();
                }
                else {
                    $("#divSame").hide();
                    $("#divDifferent").show();
                }
                if (isSame == 1 || isSame == "1") {
                    $("input:radio[name='isSame'][value=1]").attr("checked", 'checked');
                    $("#ipt_alarmLevelId").combobox("setValue", data.eventList[0].alarmLevelID);
                    changeLevelColor(data.eventList[0].alarmLevelID);
                }
                else {
                    $("input:radio[name='isSame'][value=2]").attr("checked", 'checked');
                    var resRows = data.eventList;
                    for (var i = 0; i < resRows.length; i++) {
                        definedEventArray.push(resRows[i]);
                        selectedEventIDArray.push(resRows[i].alarmDefineID)
                    }
                    var gridData = {
                        "total": definedEventArray.length,
                        "rows": definedEventArray
                    };
                    $('#tblDefinedEvent').datagrid({
                        loadFilter: pagerFilter
                    }).datagrid('loadData', gridData);
                }
            }
        }
    };
    ajax_(ajax_param);
}
