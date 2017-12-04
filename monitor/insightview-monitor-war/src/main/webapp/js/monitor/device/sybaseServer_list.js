$(document).ready(function(){
    var flag = $('#flag').val();
    if (flag == "null" || flag == "" || flag == null) {
    	doInitTable();
        $('#tblSybaseServerList').datagrid('hideColumn', 'dbmsMoid');
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
    var dbmsMoId = $('#dbmsMoId').val();
    if(dbmsMoId == null || dbmsMoId == "" || dbmsMoId == "null"){
    	dbmsMoId = -1;
    }
    var path = getRootName();
    var uri = path + '/monitor/sybaseDbManage/listSybaseServerInfos?dbmsMoId='+dbmsMoId;
    $('#tblSybaseServerList').datagrid({
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
            field: 'dbmsMoid',
            title: '',
            width: 40,
            align: 'center',
            formatter: function(value, row, index){
                return '<a style="cursor: pointer;" onclick="javascript:sel(' + row.moId + ','+value+');">选择</a>';
            }
        }, {
            field: 'serverName',
            title: '服务名称',
            width: 100,
            align: 'center',
            formatter: function(value, row, index){
                var to = "&quot;" + row.dbmsMoid +
                "&quot;,&quot;" +
                row.dbmsType +
                "&quot;,&quot;" +
                value +
                "_" +
                row.ip +
                "&quot;"
                return '<a style="cursor: pointer;" onclick="javascript:viewDevicePortal(' + to + ');">' + value + '</a>';
                
            }
        }, {
            field: 'ip',
            title: '服务IP',
            width: 80,
            sortable:true,
            align: 'center'
        }, {
            field: 'port',
            title: '端口号',
            width: 80,
            align: 'center'
        }, {
            field: 'moName',
            title: '所属主机',
            width: 80,
            align: 'center'
        }, {
            field: 'startTime',
            title: '启动时间',
            width: 120,
            align: 'center',
            formatter: function(value, row, index){
                if (value != null && value != "") {
                    return formatDate(new Date(value), "yyyy-MM-dd hh:mm:ss");
                }
                else {
                    return "";
                }
            }
        }]]
    });
    $(window).resize(function(){
        $('#tblSybaseServerList').resizeDataGrid(0, 0, 0, 0);
    });
}

/**
 * 选择
 * @return
 */
function doInitChooseTable(){
    var flag = $('#flag').val();
    var dbmsMoId = $('#dbmsMoId').val();
    if(dbmsMoId == null || dbmsMoId == "" || dbmsMoId == "null"){
    	dbmsMoId = -1;
    }
    var path = getRootName();
    var uri = path + '/monitor/sybaseDbManage/listSybaseServerInfos?dbmsMoId='+dbmsMoId;
    $('#tblSybaseServerList').datagrid({
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
            field: 'dbmsMoid',
            title: '',
            width: 40,
            align: 'center',
            formatter: function(value, row, index){
                return '<a style="cursor: pointer;" onclick="javascript:sel(' + row.moId + ','+value+');">选择</a>';
            }
        }, {
            field: 'serverName',
            title: '服务名称',
            width: 100,
            align: 'center',
        }, {
            field: 'ip',
            title: '服务IP',
            width: 80,
            align: 'center'
        }, {
            field: 'port',
            title: '端口号',
            width: 80,
            align: 'center'
        }, {
            field: 'moName',
            title: '所属主机',
            width: 80,
            align: 'center'
        }, {
            field: 'startTime',
            title: '启动时间',
            width: 120,
            align: 'center',
            formatter: function(value, row, index){
                if (value != null && value != "") {
                    return formatDate(new Date(value), "yyyy-MM-dd hh:mm:ss");
                }
                else {
                    return "";
                }
            }
        }]]
    });
    $(window).resize(function(){
        $('#tblSybaseServerList').resizeDataGrid(0, 0, 0, 0);
    });
}

/**
 * 刷新表格数据
 */
function reloadTable(){
    $('#tblSybaseServerList').datagrid('options').queryParams = {
        "serverName": $("#serverName").val(),
        "ip": $("#ip").val()
    };
    reloadTableCommon_1('tblSybaseServerList');
}

function reloadTableCommon_1(dataGridId){
    $('#' + dataGridId).datagrid('load');
    $('#' + dataGridId).datagrid('uncheckAll');
}


//查看视图
function viewDevicePortal(moid, dbmstype, moName){
    var  title = 'SybaseServer ' + moName + '视图';
    var moClass = dbmstype;
    var moClassName = "SybaseServer";
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
						if(parent.parent.tabsLst.in_array(title) == true) {
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

function sel(moId,dbmsMoid){
	if(window.opener) { 
		 var flag = $('#flag').val();
		 console.log(flag);
	    if (flag == "chooseForThreshold") {
	    	 fWindowText2 = window.opener.document.getElementById("ipt_sybaseServerMoId"); 
		     fWindowText2.value = moId; 
		     window.opener.findSybaseServerDetail();
		     window.close();
	    }else{
	    	fWindowText1 = window.opener.document.getElementById("ipt_moId"); 
	    	fWindowText1.value = dbmsMoid; 
	    	window.opener.findSybaseServerInfo();
			window.close();
	    }
	} 
} 
