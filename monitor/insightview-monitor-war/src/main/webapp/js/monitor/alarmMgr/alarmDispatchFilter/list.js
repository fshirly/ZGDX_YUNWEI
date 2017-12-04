$(document).ready(function(){
    doInitTable();
});

/**
 * 加载页面并初始化表格
 * @return
 */
function doInitTable(){
    var path = getRootName();
    $('#tblDataList').datagrid({
        width: 'auto',
        height: 'auto',
        nowrap: false,
        striped: true,
        border: true,
        collapsible: false,// 是否可折叠的
        fit: true,// 自动大小
        fitColumns: true,
        url: path + '/monitor/AlarmDispatchFilter/ajaxAlarmDispatchFilterDefDataGrid',
        remoteSort: true,
        onSortColumn: function(sort, order){
            //						 alert("sort:"+sort+",order："+order+"");
        },
		scrollbarSize: 0,
        idField: 'id',
        singleSelect: false,// 是否单选
        checkOnSelect: false,
        selectOnCheck: true,
        pagination: true,// 分页控件
        rownumbers: true,// 行号
        toolbar: [{
            'text': '新建',
            'iconCls': 'icon-add',
            handler: function(){
                toAdd();
            }
        }, {
            text: '删除',
            iconCls: 'icon-cancel',
            handler: function(){
                doBatchDel();
            }
        }],
        columns: [[{
            field: 'ck',
            checkbox: true
        }, {
            field: 'filterName',
            title: '规则名称',
            width: 100,
            align: 'left'
        }, {
            field: 'isDefault',
            title: '是否启用',
            width: 100,
            align: 'center',
            formatter: function(value, row){
                if (value == 1) {
                    return '是';
                } else if (value == 0) {
                    return '否';
                }
            }
        }, {
            field: 'descr',
            title: '描述',
            align: 'center',
            width: 100
        }, {
            field: 'ids',
            title: '操作',
            width: 85,
            align: 'center',
            formatter: function(value, row, index){
                var to = "";
                    to = '<a style="cursor: pointer;" title="修改" onclick="javascript:toUpdate(' +
                    row.id +
                    ');"><img src="' +
                    path +
                    '/style/images/icon/icon_modify.png" alt="修改" /></a> &nbsp;<a style="cursor: pointer;" title="删除" onclick="javascript:doDel(' +
                    row.id + ',' + row.isDefault +
                    ');"><img src="' +
                    path +
                    '/style/images/icon/icon_delete.png" alt="删除" /></a>';
                return '<a style="cursor: pointer;" title="查看" onclick="javascript:toShowInfo(' +
                row.id +
                ');"><img src="' +
                path +
                '/style/images/icon/icon_view.png" alt="查看" /></a>&nbsp' +
                to;
            }
        }]]
    });
    $(window).resize(function(){
        $('#tblDataList').resizeDataGrid(0, 0, 0, 0);
    });
}


function reloadAllTable(){
    $('#tblDataList').datagrid('reload', {
		name: $("#cfgName").val()
	});
}
function reloadTable(){
    $('#tblDataList').datagrid('reload');
}

/**
 * 打开新建页面
 * @return
 */
function toAdd(){
    parent.$('#popWin').window({
        title: '派单规则新建',
        width: 800,
        height: 500,
        minimizable: false,
        maximizable: false,
        collapsible: false,
        modal: true,
        href: getRootName() + '/monitor/AlarmDispatchFilter/toAlarmDispatchFilterAdd'
    });
}

/**
 * 打开修改页面
 * @return
 */
function toUpdate(id){
    parent.$('#popWin').window({
        title: '派单规则修改',
        width: 800,
        height: 500,
        minimizable: false,
        maximizable: false,
        collapsible: false,
        modal: true,
        href: getRootName() + '/monitor/AlarmDispatchFilter/toAlarmDispatchFilterUpdate?id=' + id
    });
}

/**
 * 打开查看页面
 * @return
 */
function toShowInfo(id){
    parent.$('#popWin').window({
        title: '派单规则查看',
        width: 800,
        height: 500,
        minimizable: false,
        maximizable: false,
        collapsible: false,
        modal: true,
        href: getRootName() + '/monitor/AlarmDispatchFilter/toAlarmDispatchFilterView?id=' + id
    });
}

/*
 * 删除任务
 */
function doDel(id, is){
	var path = getRootName();
	if(1 == is){
		$.messager.alert("提示", "已经启用的规则不能删除！", "error");
		return;
	}
    $.messager.confirm("操作提示", "您确定删除此信息吗？", function(r){
        if (!r) {
			return;
		}
        var uri = path + "/monitor/AlarmDispatchFilter/ajaxRemoveAlarmDispatchFilterDef";
        var ajax_param = {
            url: uri,
            type: "post",
            datdType: "json",
            data: {
                "ids": id
            },
            success: function(data){
                if (true == data || "true" == data) {
                    $.messager.alert("提示", "信息删除成功！", "info");
					reloadTable();
                } else {
                    $.messager.alert("提示", "信息删除失败！", "error");
                }
            }
        };
        ajax_(ajax_param);
    });
}


/**
 * 批量删除
 */
function doBatchDel(){
    var path = getRootName();
    var rows = $('#tblDataList').datagrid('getChecked');
	var ids = $.map(rows, function(v){
		if(v.isDefault == 1){
			return null;
		}
		return v.id;
	});
	if(rows.length == 0){
		$.messager.alert('提示', '没有任何选中项', 'error');
		return;
	}
	if(ids.length != rows.length){
		$.messager.alert("提示", "已经启用的规则不能删除！", "error");
		return;
	}
	$.messager.confirm("提示", "确定删除所选中项？", function(r){
        if (r == true) {
            var uri = path + "/monitor/AlarmDispatchFilter/ajaxRemoveAlarmDispatchFilterDef";
            var ajax_param = {
                url: uri,
                type: "post",
                datdType: "json",
                data: {
                    "ids": ids.join()
                },
                success: function(data){
                    if (true == data || "true" == data) {
                        $.messager.alert("提示", "信息删除成功！", "info");
						reloadTable();
                    } else {
                        $.messager.alert("提示", "信息删除失败！", "error");
                    }
                }
            };
            ajax_(ajax_param);
        }
    });
}
