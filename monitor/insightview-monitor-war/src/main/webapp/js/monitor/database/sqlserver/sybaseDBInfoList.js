$(document).ready(function(){
    var moID = $("#moId").val();
    var liInfo = $("#liInfo").val();
    doDBInitTables(moID, liInfo);// 数据库列表
    $('#tblDB').datagrid('hideColumn', 'moId');
});


// 数据库列表
function doDBInitTables(moID){
    var type = $("#type").val();
    var path = getRootName();
    var uri = path;
    if (type == "sybase") {
        uri += '/monitor/sybaseManage/getDBInfoList?moID=' + moID
    }
    else {
        uri += '/monitor/msManage/getDBInfoList?moID=' + moID
    }
    $('#tblDB').datagrid({
        iconCls: 'icon-edit',// 图标
        width: 120,
        height: 'auto',
        nowrap: false,
        rownumbers: true,
        striped: true,
        border: true,
        singleSelect: false,// 是否单选
        checkOnSelect: false,
        selectOnCheck: true,
        collapsible: false,// 是否可折叠的
        fit: true,// 自动大小
        fitColumns: true,
        url: uri,
        idField: 'fldId',
        columns: [[
		/**{
            field: 'databaseID',
            title: '内部标识号',
            width: 50,
            align: 'left'
        },*/ {
            field: 'databaseName',
            title: '数据库名称',
            width: 80,
            align: 'left',
            formatter: function(value, row, index){
                if (value == null) {
                    return value;
                }
                else {
                    var to = "&quot;" + row.moId +
                    "&quot;,&quot;" +
                    row.databaseName +
                    "&quot;,&quot;" +
                    row.ip +
                    "&quot;"
                    return '<a style="cursor: pointer;" onclick="javascript:viewDevicePortal(' + to + ');">' + value + '</a>';
                }
            }
        }, {
            field: 'databaseOwner',
            title: '数据库属主',
            width: 100,
            align: 'left',
			  formatter: function(value, row, index){
                if (value && value.length > 8) {
                    value2 = value.substring(0, 15) + "...";
                    return '<span title="' + value + '" >' + value2 + "</span>";
                }
                else {
                    return value;
                }
                
            }
        }, {
            field: 'databaseOptions',
            title: '数据库选项',
            width: 160,
            align: 'left',
            formatter: function(value, row, index){
                if (value && value.length > 20) {
                    value2 = value.substring(0, 30) + "...";
                    return '<span title="' + value + '" >' + value2 + "</span>";
                }
                else {
                    return value;
                }
                
            }
        }, {
            field: 'createTime',
            title: '创建时间',
            width: 90,
            align: 'left',
            formatter: function(value, row, index){
                if (value != null && value != "") {
                    return formatDate(new Date(value), "yyyy-MM-dd hh:mm:ss");
                }
                else {
                    return "";
                }
            }
        }, {
            field: 'totalSize',
            title: '总空间大小',
            width: 50,
            align: 'left'
        }, {
            field: 'spaceUsage',
            title: '空间使用率',
            width: 50,
            align: 'left'
        }, {
            field: 'pageSize',
            title: '页大小',
            width: 50,
            align: 'left'
        }]],
        onLoadSuccess: function(){
            $('.easyui-progressbar').progressbar().progressbar('setColor');
            //自适应部件大小
            window.parent.resizeWidgetByParams(liInfo);
        }
        
    });
    $(window).resize(function(){
        $('#tblDB').resizeDataGrid(0, 0, 0, 0);
    });
}

/**
 * 选择
 * @return
 */
//查看视图
function viewDevicePortal(moid, dbmstype, moName){
    var type = $("#type").val();
    var title = 'Sqlserver数据库 ' + dbmstype + '视图';
    var moClass = "MsSqlDb";
    var moClassName = "MsSqlDb";
    if (type == "sybase") {
        title = 'Sybase数据库 ' + dbmstype + '视图';
        moClass = "SybaseDatabase";
        moClassName = "SybaseDatabase";
    }
    var path = getRootPatch();
    var uri = path + "/monitor/gridster/viewDevicePortal";
    var ajax_param = {
        url: uri,
        type: "post",
        datdType: "json",
        data: {
            "portalName": moClassName,
            "t": Math.random()
        },
        error: function(){
            $.messager.alert("错误", "ajax_error", "error");
        },
        success: function(data){
            if (data == true) {
                var urlParams = "?moID=" + moid + "&moClass=" + moClass + "&flag=device";
                var uri = path + "/monitor/gridster/showPortalView" + urlParams;
                var iWidth = window.screen.availWidth - 210; //弹出窗口的宽度190
                var y = window.screen.availHeight - 102;
                var y2 = y - 29;
                var iHeight = window.screen.availHeight - 265; //弹出窗口的高度;
                var iTop = 165;
                var iLeft = 188;
                //				window.open(uri,"","height="+iHeight+",width="+iWidth+",left="+iLeft+",top="+iTop+",resizable=no,scrollbars=yes,status=no,toolbar=no,menubar=no,location=yes");
                var content = '<iframe scrolling="auto" frameborder="0"  src="' + uri + '" style="width:100%;height:100%;"></iframe>';
                var isExistTabs = parent.parent.document.getElementById("tabs_window");
                var isPartentTabs = parent.document.getElementById("tabs_window");
                if (isPartentTabs != null) {
                    parent.$('#tabs_window').tabs('add', {
                        title: title,
                        content: content,
                        closable: true
                    });
                }
                else 
                    if (isExistTabs != null) {
                        parent.parent.$('#tabs_window').tabs('add', {
                            title: title,
                            content: content,
                            closable: true
                        });
                    }
                    else {
                        //					window.parent.frames.location = uri;
                        window.open(uri, "", "height=" + iHeight + ",width=" + iWidth + ",left=" + iLeft + ",top=" + iTop + ",resizable=no,scrollbars=yes,status=no,toolbar=no,menubar=no,location=yes");
                    }
                
            }
            else {
                $.messager.alert("提示", "视图加载失败！", "error");
            }
            
        }
    }
    ajax_(ajax_param);
    
}
