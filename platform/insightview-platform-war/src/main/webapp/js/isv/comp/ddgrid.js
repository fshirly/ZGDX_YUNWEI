/**
 * easyui动态DataGrid的封装
 */
var isv = window.isv || {};
isv.comp = isv.comp || {};
isv.comp.ddgrid = function(config){
    //生成表格对象的元素ID
    var id = config.id;
    //表格数据后台Url
    var url = config.url;
    //查询参数
    var queryParams = config.queryParams;
    //ID字段
    var idField = config.idField;
    //工具栏
    var toolbar = config.toolbar;
    //默认的列
    var defaultColumns = config.defaultColumns;
    //操作列
    var optColumn = config.optColumn;
    var $ddgrid = $('#' + id);
	var options = {
		width: 'auto',
        height: 'auto',
        nowrap: false,
        striped: true,
        border: true,
        singleSelect: true,
        checkOnSelect: false,
        selectOnCheck: false,
        fitColumns: config.fitColumns != undefined ? config.fitColumns : true,
        collapsible: false,// 是否可折叠的
        fit: config.fit != undefined ? config.fit : true,// 自动大小
        url: url,
		loadFilter: function(){
			return arguments[0].data;
		},
		onBeforeLoad: function(){
			return $.extend(arguments[0], queryParams);
		},
		onLoadSuccess: config.onLoadSuccess,
        remoteSort: false,
        idField: idField,
        pagination: config.pagination != undefined ? config.pagination : true,// 分页控件
        rownumbers: true,// 行号
        toolbar: toolbar
    };
    //调用ajax请求后台数据，生成表格的列和实际数据
    $.get(url, queryParams, function(data){
        //表格数据
        var gridData = data.data;
        //表格列
        var columnData = data.columns;
        var columns = [];
        var column = [];
        if (defaultColumns) {
            $.each(defaultColumns, function(i, value){
                column.push(value);
            });
        }
        $.each(columnData, function(i, value){
            var c = {}, paramter = {
                "attributeId": value.attributeId,
                "dataInitValue": value.dataInitValue,
                "widgetValue": value.widgetValue
            };
            c.field = value.columnName;
            c.title = value.attributeName;
            c.align = 'center';
            if (value.widgetValue === 'System' || value.widgetValue === 'CustomSelect' || value.widgetValue === 'Dict' || value.widgetValue === 'Tree') {
                c.formatter = function(valueInfo){
                    var label = "";
                    if ('null' === valueInfo || null === valueInfo || /^\s*$/.test(valueInfo)) {
                        return label;
                    };
                    paramter.valueInfo = valueInfo;
                    var ajax_param = {
                        url: getRootName() + "/widgetInit/querySeleLabel",
                        type: "post",
                        datdType: "json",
                        data: paramter,
                        async: false,
                        error: function(){
                            $.messager.alert("错误", "列表加载失败！", "error");
                        },
                        success: function(data){
                            label = data;
                        }
                    };
                    ajax_(ajax_param);
                    return label;
                };
            }
            column.push(c);
        });
        if (optColumn) {
            column.push(optColumn);
        }
        columns.push(column);
        options.columns = columns;
        $ddgrid.datagrid(options);
    });
};
