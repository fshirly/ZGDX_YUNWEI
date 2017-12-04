$(document).ready(function(){
    var flag = $('#flag').val();
    if (flag == "null" || flag == "" || flag == null) {
    	doInitTable();
        $('#tblMsDBList').datagrid('hideColumn', 'moId');
    }else{
		doInitChooseTable();
	}
});

/**
 * 加载页面并初始化表格
 * @return
 */
function doInitTable(){
    var flag = $('#flag').val();
    var path = getRootName();
    var serverMoId = $("#serverMoId").val();
    if (serverMoId != null && serverMoId != "") {
        var uri = path + '/monitor/msDbManage/listMsDBInfos?serverMoId=' + serverMoId;
    }
    else {
        var uri = path + '/monitor/msDbManage/listMsDBInfos';
    }
    
    $('#tblMsDBList').datagrid({
        width: 'auto',
        height: 'auto',
        nowrap: false,
        striped: true,
        border: true,
        collapsible: false,// 是否可折叠的
        fit: true,// 自动大小
        fitColumns: true,
        url: uri,
        remoteSort: false,
        onSortColumn: function(sort, order){
            //						 alert("sort:"+sort+",order："+order+"");
        },
        idField: 'idField',
        singleSelect: false,// 是否单选
        checkOnSelect: false,
        selectOnCheck: true,
        pagination: true,// 分页控件
        rownumbers: true,// 行号						
        columns: [[{
            field: 'moId',
            title: '',
            width: 40,
            align: 'center',
            formatter: function(value, row, index){
                return '<a style="cursor: pointer;" onclick="javascript:sel(' + value + ');">选择</a>';
            }
        }, {
            field: 'databaseName',
            title: '数据库名称',
            width: 110,
            align: 'center',
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
            title: '数据库所属',
            width: 120,
            align: 'left',
            formatter: function(value, row, index){
                if (value && value.length > 10) {
                    value2 = value.substring(0, 20) + "...";
                    return '<span title="' + value + '" >' + value2 + "</span>";
                }
                else {
                    return value;
                }
                
            }
        }, {
            field: 'ip',
            title: '服务IP',
            width: 80,
            sortable:true,
            align: 'center'
        }, {
            field: 'databaseOptions',
            title: '描述',
            width: 120,
            align: 'center',
            formatter: function(value, row, index){
                if (value && value.length > 10) {
                    value2 = value.substring(0, 20) + "...";
                    return '<span title="' + value + '" >' + value2 + "</span>";
                }
                else {
                    return value;
                }
                
            }
        }, {
            field: 'dataSize',
            title: '数据空间大小',
            width: 80,
            align: 'center'
        }, {
            field: 'logSize',
            title: '日志空间大小',
            width: 80,
            align: 'center'
        }, {
            field: 'totalSize',
            title: '总空间大小',
            width: 80,
            align: 'center'
        }, {
            field: 'usedSize',
            title: '已用空间',
            width: 80,
            align: 'center'
        }, {
            field: 'freeSize',
            title: '空闲空间',
            width: 80,
            align: 'center'
        }]]
    });
    $(window).resize(function(){
        $('#tblMsDBList').resizeDataGrid(0, 0, 0, 0);
    });
}

function doInitChooseTable(){
    var flag = $('#flag').val();
    var path = getRootName();
    var serverMoId = $("#serverMoId").val();
    if (serverMoId != null && serverMoId != "") {
        var uri = path + '/monitor/msDbManage/listMsDBInfos?serverMoId=' + serverMoId;
    }
    else {
        var uri = path + '/monitor/msDbManage/listMsDBInfos';
    }
    
    $('#tblMsDBList').datagrid({
        width: 'auto',
        height: 'auto',
        nowrap: false,
        striped: true,
        border: true,
        collapsible: false,// 是否可折叠的
        fit: true,// 自动大小
        fitColumns: true,
        url: uri,
        remoteSort: true,
        onSortColumn: function(sort, order){
            //						 alert("sort:"+sort+",order："+order+"");
        },
        idField: 'idField',
        singleSelect: false,// 是否单选
        checkOnSelect: false,
        selectOnCheck: true,
        pagination: true,// 分页控件
        rownumbers: true,// 行号						
        columns: [[{
            field: 'moId',
            title: '',
            width: 40,
            align: 'center',
            formatter: function(value, row, index){
                return '<a style="cursor: pointer;" onclick="javascript:sel(' + value + ');">选择</a>';
            }
        }, {
            field: 'databaseName',
            title: '数据库名称',
            width: 110,
            align: 'center',
        }, {
            field: 'databaseOwner',
            title: '数据库所属',
            width: 120,
            align: 'left',
            formatter: function(value, row, index){
                if (value && value.length > 10) {
                    value2 = value.substring(0, 20) + "...";
                    return '<span title="' + value + '" >' + value2 + "</span>";
                }
                else {
                    return value;
                }
                
            }
        }, {
            field: 'ip',
            title: '服务IP',
            width: 80,
            align: 'center'
        }, {
            field: 'databaseOptions',
            title: '描述',
            width: 120,
            align: 'center',
            formatter: function(value, row, index){
                if (value && value.length > 10) {
                    value2 = value.substring(0, 20) + "...";
                    return '<span title="' + value + '" >' + value2 + "</span>";
                }
                else {
                    return value;
                }
                
            }
        }, {
            field: 'dataSize',
            title: '数据空间大小',
            width: 80,
            align: 'center'
        }, {
            field: 'logSize',
            title: '日志空间大小',
            width: 80,
            align: 'center'
        }, {
            field: 'totalSize',
            title: '总空间大小',
            width: 80,
            align: 'center'
        }, {
            field: 'usedSize',
            title: '已用空间',
            width: 80,
            align: 'center'
        }, {
            field: 'freeSize',
            title: '空闲空间',
            width: 80,
            align: 'center'
        }]]
    });
    $(window).resize(function(){
        $('#tblMsDBList').resizeDataGrid(0, 0, 0, 0);
    });
}

/**
 * 刷新表格数据
 */
function reloadTable(){
    $('#tblMsDBList').datagrid('options').queryParams = {
        "databaseName": $("#databaseName").val(),
        "ip": $("#ip").val()
    };
    reloadTableCommon_1('tblMsDBList');
}

function reloadTableCommon_1(dataGridId){
    $('#' + dataGridId).datagrid('load');
    $('#' + dataGridId).datagrid('uncheckAll');
}

/**
 * 选择
 * @return
 */
//查看视图
function viewDevicePortal(moid, dbmstype, moName){
    var title = 'SqlServer数据库 ' + moName + '视图';
    var moClass = dbmstype;
    var moClassName = "MsSqlDb";
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
					if (parent.tabsLst.in_array(title) == true) {
						//跳转到已经打开的视图页面
						parent.$('#tabs_window').tabs('select', title);
						var tab = parent.$('#tabs_window').tabs('getSelected');
						//更新视图
						parent.$('#tabs_window').tabs('update', {
							tab: tab,
							options: {
								title: title,
								content: content,
								closable: true,
								selected: true
							}
						});
					}
					else {
	                    parent.$('#tabs_window').tabs('add', {
	                        title: title,
	                        content: content,
	                        closable: true
	                    });
						parent.tabsLst.push(title);
					}
                }
                else 
                    if (isExistTabs != null) {
						if (parent.parent.tabsLst.in_array(title) == true) {
							//跳转到已经打开的视图页面
							parent.parent.$('#tabs_window').tabs('select', title);
							var tab = parent.parent.$('#tabs_window').tabs('getSelected');
							//更新视图
							parent.parent.$('#tabs_window').tabs('update', {
								tab: tab,
								options: {
									title: title,
									content: content,
									closable: true,
									selected: true
								}
							});
						}
						else {
	                        parent.parent.$('#tabs_window').tabs('add', {
	                            title: title,
	                            content: content,
	                            closable: true
	                        });
							parent.parent.tabsLst.push(title);
						}
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

function sel(moId){
    if (window.opener) {
        var flag = $('#flag').val();
        if (flag == "1") {
            fWindowText1 = window.opener.document.getElementById("ipt_moId");
            fWindowText1.value = moId;
            window.opener.findMsSqlDbInfo();
            window.close();
        }
        else {
            fWindowText2 = window.opener.document.getElementById("ipt_msSQLDbMoId");
            fWindowText2.value = moId;
            window.opener.findMsSQLDbInfo();
            window.close();
        }
    }
}
