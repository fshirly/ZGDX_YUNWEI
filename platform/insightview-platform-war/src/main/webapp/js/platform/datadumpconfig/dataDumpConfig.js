$(document).ready(function(){
    initDataDumpConfig();
    initDumpListTable();
});

/**
 * 获得数据转储设置的初始值
 */
function initDataDumpConfig(){
    var path = getRootName();
    var uri = path + "/platform/dataDumpConfig/initDataDumpConfig";
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
            if (data != null) {
                $("#originalDataRetentionTime").val(data.originalDataRetentionTime);
                $("#dumpDataRetentionTime").combobox("setValue", data.dumpDataRetentionTime);
                $("#executeTime").timespinner('setValue', data.executeTime);
            }
        }
    };
    ajax_(ajax_param);
}

function checkTimeVal(val){
    if (!(/^[0-9]*[1-9][0-9]*$/.test(val))) {
        return false;
    } else {
        return true;
    }
}

/**
 * 配置数据转储设置
 */
function doSetDataDumpConfig(){
    var result = checkInfo("#tblDataDumpConfig");
    if (result == true) {
        var originalDataRetentionTime = $("#originalDataRetentionTime").val();
        var dumpDataRetentionTime = $("#dumpDataRetentionTime").combobox("getValue");
        var executeTime = $("#executeTime").timespinner('getValue');
//        console.log("originalDataRetentionTime=" + originalDataRetentionTime);
//        console.log("dumpDataRetentionTime=" + dumpDataRetentionTime);
//        console.log("executeTime=" + executeTime);
        if (!checkTimeVal(originalDataRetentionTime)) {
            $.messager.alert("提示", "原始数据保留时间只能输入正整数！", "info");
        } else if (!checkTimeVal(dumpDataRetentionTime)) {
            $.messager.alert("提示", "转储数据保留时间只能输入正整数！", "info");
        } else {
            var path = getRootName();
            var uri = path + "/platform/dataDumpConfig/doSetDataDumpConfig";
            var ajax_param = {
                url: uri,
                type: "post",
                datdType: "json",
                data: {
                    "originalDataRetentionTime": originalDataRetentionTime,
                    "dumpDataRetentionTime": dumpDataRetentionTime,
                    "executeTime": executeTime,
                    "t": Math.random()
                },
                error: function(){
                    $.messager.alert("错误", "ajax_error", "error");
                },
                success: function(data){
                    if (data == true) {
                        $.messager.alert("提示", "数据转储配置成功！", "info");
                    } else {
                        $.messager.alert("错误", "数据转储配置失败！", "error");
                    }
                }
            };
            
            ajax_(ajax_param);
        }
    }
}

/**
 * 转储列表
 */
function initDumpListTable(){
    var path = getRootName();
    $('#tblDumpList').datagrid({
        iconCls: 'icon-edit',// 图标
        width: 'auto',
        height: 'auto',
        nowrap: false,
        striped: true,
        border: true,
        collapsible: false,// 是否可折叠的
        fit: true,// 自动大小
        fitColumns: true,
        url: path + '/platform/dataDumpConfig/listDump',
        remoteSort: false,
//        idField: 'fldId',
        singleSelect: true,// 是否单选
        checkOnSelect: false,
        selectOnCheck: false,
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
            field: 'id',
            checkbox: true
        }, {
            field: 'tableName',
            title: '表名',
            width: 120,
			align: 'center',
        }, {
            field: 'timeColumnName',
            title: '时间列名',
            width: 180,
            align: 'center',
        }, {
            field: 'ids',
            title: '操作',
            width: 180,
            align: 'center',
            formatter: function(value, row, index){
                return '<a style="cursor: pointer;" onclick="javascript:doOpenModify(' +
                row.id +
                ');"><img src="' +
                path +
                '/style/images/icon/icon_modify.png" title="修改" /></a> &nbsp;<a style="cursor: pointer;" onclick="javascript:toDel(' +
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
    reloadTableCommon('tblDumpList');
}

/*
 * 删除转储
 */
function toDel(id){
	$.messager.confirm("提示","确定删除所选中的项?",function(r){
		if (r == true) {
			var path = getRootName();
			var uri = path + "/platform/dataDumpConfig/delDump";
			var ajax_param = {
				url : uri,
				type : "post",
				datdType : "json",
				data : {
					"id" : id,
					"t" : Math.random()
				},
				error : function() {
					$.messager.alert("错误", "ajax_error", "error");
				},
				success : function(data) {
					if (true == data || "true" == data) {
						$.messager.alert("提示", "该转储表信息删除成功！", "info");
						reloadTable();
					} else{
						$.messager.alert("错误", "该转储删除失败！", "error");
					}
				}
			};
			ajax_(ajax_param);
		}
	});
}

/*
 * 批量删除
 */
function doBatchDel(){
	var path=getRootName();
	var checkedItems = $('[name=id]:checked');
	var ids = null;
	$.each(checkedItems, function(index, item) {
		if (null == ids) {
			ids = $(item).val();
		} else {
			ids += ',' + $(item).val();
		}
	});
	if (null != ids) {
		$.messager.confirm("提示","确定删除所选中项？",function(r){
			if (r == true) {
				var path = getRootName();
				var uri = path + "/platform/dataDumpConfig/delDumps?ids="+ids;
				var ajax_param = {
					url : uri,
					type : "post",
					datdType : "json",
					data : {
						"t" : Math.random()
					},
					error : function() {
						$.messager.alert("错误", "ajax_error", "error");
					},
					success : function(data) {
						if (true == data || "true" == data) {
							$.messager.alert("提示", "删除转储表信息成功！", "info");
						} else{
							$.messager.alert("错误", "删除转储表信息失败！", "error");
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

/**
 * 打开新增界面
 */
function doOpenAdd(){
    parent.$('#popWin').window({
        title: '新增转储表信息',
        width: 600,
        height: 300,
        minimizable: false,
        maximizable: false,
        collapsible: false,
        modal: true,
        href: getRootName() + '/platform/dataDumpConfig/toShowDataDumpAdd',
    });
}

var _className = "";
var _classLable = "";
var _collectPeriod = -1;
/**
 * 打开编辑界面
 */
function doOpenModify(id){
    parent.$('#popWin').window({
        title: '编辑转储表信息',
        width: 600,
        height: 300,
        minimizable: false,
        maximizable: false,
        collapsible: false,
        modal: true,
        href: getRootName() + '/platform/dataDumpConfig/toShowDataDumpModify?id='+id,
    });
}


