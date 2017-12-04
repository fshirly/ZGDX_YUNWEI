$(document).ready(
function() {
    $.extend($.fn.datagrid.methods, {
        getChecked : function(jq) {
            var rr = [];
            var rows = jq.datagrid('getRows');
            jq.datagrid('getPanel').find(
                    'div.datagrid-cell-check input:checked').each(
                    function() {
                        var index = $(this).parents('tr:first').attr(
                                'datagrid-row-index');
                        rr.push(rows[index]);
                    });
            return rr;
        }
    });

    $(window).resize(function () {
        $('#tblEventManagement').datagrid('resize');
    });

    
    $('#status').combobox({
        panelHeight : '120',
        panelWidth : '134',
        valueField : 'key',
        textField : 'val',
        data: [
        {
            key: '0',
            val: '全部'
        },{
            key: '1',
            val: '已登记'
        },{
            key: '2',
            val: '处理中'
        },{
            key: '3',
            val: '已解决'
        },{
            key: '4',
            val: '已关闭'
        },{
            key: '5',
            val: '待分配'
        }],
        editable : false
    });
    
    $('#limitTime').combobox({
        panelHeight : '120',
        panelWidth : '134',
        valueField : 'key',
        textField : 'val',
        data: [
        {
            key: '0',
            val: '全部'
        },{
            key: '1',
            val: '本周'
        },{
            key: '2',
            val: '本月'
        },{
            key: '3',
            val: '本年度'
        }],
        editable : false
    });
    
    
    $('#status').combobox('setValue',0);
    $('#limitTime').combobox('setValue',0);
    
    // 页面初始化加载表格
    doInitTable();

});


// 重置过滤条件
function resetFormFilter() {
    $('#title').val('');
    $('#status').combobox('setValue',0);
    $('#limitTime').combobox('setValue',0);
}

/*
 * 更新表格 
 */
function reloadManageTable() {
    $('#tblEventManagement').datagrid('options').queryParams = {
        "title" : $('#title').val(),
        "status" : $('#status').combobox('getValue'),
        "limitTime" : $('#limitTime').combobox('getValue')
    };
    $('#tblEventManagement').datagrid('load');
    $('#tblEventManagement').datagrid('unselectAll');
    $('#tblEventManagement').datagrid('uncheckAll');
}


/*
 * 查看
 */
function doView(id) {
    parent.parent.$('#popWin').window(
    {
        title : '故障单详情',
        width : 900,
        height : 590,
        collapsible : false,
        minimizable : false,
        maximizable : false,
        modal : true,
        href : '../eventManage/toIncidentView?id=' + id
    }).window("center");
}

/*
 * 修改
 */
function doUpdate(id) {
    parent.parent.$('#popWin').window(
    {
        title : '故障单修改',
        width : 900,
        height : 590,
        collapsible : false,
        minimizable : false,
        maximizable : false,
        modal : true,
        href : '../eventManage/toIncident?id=' + id
    }).window("center");
}


function doInitTable() {
    var path = getRootName();
    
    $('#tblEventManagement')
            .datagrid(
                    {
                        iconCls : 'icon-edit',// 图标
                        width : 'auto',
                        height : '180',
                        fitColumns:true,
                        nowrap : false,
                        striped : true,
                        border : true,
                        collapsible : false,// 是否可折叠的
                        //fit : true,// 自动大小
                        url : '../dashboardPageManage/queryMyEventList',
                        // sortName: 'code',
                        // sortOrder: 'desc',
                        remoteSort : false,
                        idField : 'fldId',
                        singleSelect : true,// 是否单选
                        checkOnSelect : false,
                        selectOnCheck : false,
                        pagination : true,// 分页控件
                        pageSize:5,
                        rownumbers : true,
                        columns : [ [
                                {
                                    field : 'title',
                                    title : '标题',
                                    align : 'center',
                                    width : 220,
                                    formatter : function(value, row, index) {
                                        return '<a style="cursor: pointer;color:#0064b1" title="查看" class="easyui-tooltip" onclick="javascript:doView(' + row.id+ ');">' + value + '</a>' ;
                                    }
                                },
                                {
                                    field : 'bookTime',
                                    title : '报告时间',
                                    align : 'center',
                                    width : 90,
                                    formatter : function(value, row, index) {
                                        return formatDate(
                                                new Date(row.bookTime),
                                                "yyyy-MM-dd hh:mm:ss");
                                    }
                                },
                                {
                                    field : 'status',
                                    title : '故障状态',
                                    align : 'center',
                                    width : 90,
                                    formatter : function(value, row, index) {
                                        if (row.status == 1) {
                                            return "已登记";
                                        } else if (row.status == 2) {
                                            return "处理中";
                                        } else if (row.status == 3) {
                                            return "已解决";
                                        } else if (row.status == 4) {
                                            return "已关闭";
                                        }
                                    }
                                },
                                {
                                    field : 'processCurrentMan',
                                    title : '当前处理人',
                                    align : 'center',
                                    width : 90,
                                    formatter : function(value, row, index) {
                                        return value;
                                    }
                                },
                                {
                                    field : 'id',
                                    title : '操作',
                                    width : 130,
                                    align : 'center',
                                    formatter : function(value, row, index) {
                                        return '<a style="cursor: pointer;color:#0064b1" title="修改" class="easyui-tooltip" onclick="javascript:doUpdate(' + row.id + ');"><img src="' + path
                                             + '/style/images/icon/icon_modify.png" alt="修改" /></a>';
                                    }
                                } ] ]
                    });
}