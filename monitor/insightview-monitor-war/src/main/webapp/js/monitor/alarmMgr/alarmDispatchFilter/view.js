/**
 * 加载页面并初始化表格
 * @return
 */
function doInitTableFilter(){
    var path = getRootName();
    var viewCfgID = $("#viewCfgID").val();
	var uri = path + '/monitor/AlarmDispatchFilter/ajaxAlarmDispatchFilterDataGrid?defid=' + viewCfgID;
    $('#filtertblDataList').datagrid({
        iconCls: 'icon-edit',// 图标
        width: 'auto',
        height: 'auto',
        nowrap: false,
        striped: true,
        border: true,
        collapsible: false,// 是否可折叠的
        url: uri,
        remoteSort: false,
		scrollbarSize: 0,
        singleSelect: false,// 是否单选
        checkOnSelect: true,
		fit: true,
        fitColumns: true,
        selectOnCheck: true,
        pagination: true,// 分页控件
        rownumbers: true,// 行号
        columns: [[{
            field: 'filterKey',
            title: '过滤关键字',
            width: 200,
            align: 'left',
            formatter: function(value, row, index){
                var rtnVal = "";
                if (value == "AlarmLevel") {
                    rtnVal = "告警等级";
                } else if (value == "AlarmType") {
                    rtnVal = "告警类型";
                } else if (value == "AlarmSourceMOID") {
                    rtnVal = "告警源对象";
                } else if (value == "AlarmDefineID") {
                    rtnVal = "告警事件";
                }
                return rtnVal;
            }
        }, {
            field: 'filterKeyValueName',
            title: '过滤值',
            width: 200,
            align: 'center'
        }]]
    });
    $(window).resize(function(){
        $('#filtertblDataList').resizeDataGrid(0, 0, 0, 0);
    });
}
doInitTableFilter();