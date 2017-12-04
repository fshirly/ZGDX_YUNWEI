//已经选择的资源
var selectedResArray = [];
//告警事件的列表
var definedEventArray = [];

$(document).ready(function(){
	var ruleId = $("#ruleId").val();
	doInitMonitorResTable();
	initSelectedRes(ruleId);
	initSelectedEvent(ruleId);
});

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
				if(isSame == 1 || isSame == "1"){
					$("input:radio[name='isSame'][value=1]").attr("checked",'checked');
					$("#lbl_alarmLevelId").text(data.eventList[0].alarmLevelName);
					$("#lbl_alarmlevelVal").text(data.eventList[0].levelColorName).css({"background-color": data.eventList[0].levelColor});
				}else{
					$("input:radio[name='isSame'][value=2]").attr("checked",'checked');
					doInitDefinedEventTable();
					var resRows = data.eventList;
					for (var i = 0; i < resRows.length; i++) {
			            definedEventArray.push(resRows[i]);
			        }
			        var gridData = {
			            "total": definedEventArray.length,
			            "rows": definedEventArray
			        };
			        $('#tblDefinedEvent').datagrid({loadFilter: pagerFilter}).datagrid('loadData', gridData);
				}
				$("#isSame1").attr("disabled", true);
				$("#isSame2").attr("disabled", true);
            }
        }
    };
    ajax_(ajax_param);
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
        columns: [[{
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
        }]]
    }).datagrid({
        loadFilter: pagerFilter
    }).datagrid('loadData', {
        "total": selectedResArray.length,
        "rows": selectedResArray
    });
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
        columns: [[{
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

function reloadTableCommon(dataGridId){
    $('#' + dataGridId).datagrid('load');
    $('#' + dataGridId).datagrid('uncheckAll');
}


/**
 * 跳转至指定tab页
 */
function selectTab(index){
    $('#editRuleTab').tabs('select', index);
}

