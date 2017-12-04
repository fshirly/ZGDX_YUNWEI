var path = getRootName();
$(document).ready(function() {
	var flag = $("#flag").val();
	if (flag == "1") {
		doInitChooseTable();
	} else {
		doInitTable();
	}
	
});

/**
 * 加载页面并初始化表格
 * @return
 */
function doInitTable() {
	$('#tblWebSitePortList')
			.datagrid(
					{
						iconCls : 'icon-edit',// 图标
						width : 'auto',
						height : 'auto',
						nowrap : false,
						striped : true,
						border : true,
						collapsible : false,// 是否可折叠的
						fit : true,// 自动大小
						fitColumns:true,
						url : path + '/monitor/webSite/listWebSitePortInfo',
						// sortName: 'code',
						// sortOrder: 'desc',
						remoteSort : false,
						idField : 'moID',
						singleSelect : false,// 是否单选
						checkOnSelect : false, 
						selectOnCheck : true,
						pagination : true,// 分页控件
						rownumbers : true,// 行号
						toolbar : [{
						    'text' : '同步到资源库',
						    'iconCls' : 'icon_cmdb',
						     handler : function(){
						        showCMDB();
						        }
						    },
						    {
							'text' : '删除',
							'iconCls' : 'icon-cancel',
							handler : function() {
								doDeleProt();
							}
						}],
						columns : [ [
						        {
						        	field : 'moID',
						        	checkbox : true
						        },
						        {
									field : 'available',
									title : '可用/持续时间',
									width : 60,
									align : 'left',
									formatter:function(value,row){
										return '<img title="' + row.availableTip + '" src="' + path
											+ '/style/images/levelIcon/' + row.availablePng + '"/>'+row.durationTime;
								}  
								},
								{
									field : 'alarmLevel',
									title : '告警状态',
									width : 60,
									align : 'center',
									formatter:function(value,row){
									var val = row.alarmLevelName;
									var t = row.levelIcon;
									if(val==null || val==""){
										val="正常";
										t="right.png";
									}
									return '<img src="' + path
											+ '/style/images/levelIcon/' + t + '"/>' + val;
								} 
								},
								{
									field : 'siteName',
									title : '站点名称',
									width : 80,
									align : 'center',
						            formatter: function(value, row, index){
									var to = "&quot;" + row.moID +
				                    "&quot;,&quot;" +
				                    value +
				                    "&quot;,&quot;" +
				                    row.siteType +
				                    "&quot;"
									if(value && value.length > 16){
						        		value2 = value.substring(0,16) + "...";
						        		return '<a style="cursor: pointer;" onclick="javascript:viewDevicePortal(' + to + ');">' + '<span title="' + value + '" style="cursor: pointer;" >' +value2+'</span>' + '</a>'; 
						        	}else{
						        		return '<a style="cursor: pointer;" onclick="javascript:viewDevicePortal(' + to + ');">' + value + '</a>';
									}
					                    
					                    
					            }
								},
								{
									field : 'ipAddr',
									title : '站点地址',
									width : 100,
									align : 'center'
								},
								{
									field : 'port',
									title : '端口',
									width : 60,
									align : 'center'
								},
								{
									field : 'portType',
									title : '端口协议类型',
									width : 100,
									align : 'center',
									formatter:function(value,row){
										if(value == 1){
											return "TCP";
										} else if (value == 2) {
											return "UDP";
										}
								} 
								},
								{
									field : 'formatTime',
									title : '响应时间',
									width : 100,
									align : 'center'
								}
						/**		,
								{
									field : 'moids',
									title : '操作',
									width : 60,
									align : 'center',
									formatter : function(value,row,index){
									 var to = "&quot;" + row.moID
										+ "&quot;,&quot;" + row.siteName
										+ "&quot;"
									return ' <a href="javascript:doRediscover('+to+');"  class="fltrt"><img src =" '+path+'/style/images/icon/icon_refresh.png" title="重新发现" /></a>&nbsp;'
											+'<a style="cursor:pointer;" title="配置" onclick="javascript:toSet('
											+ to
											+ ');"><img src =" '+path+'/style/images/icon/icon_setting.png" alt="配置" /></a>';	
							    }
								}
								*/
								 ] ]
					});
    $(window).resize(function() {
        $('#tblWebSitePortList').resizeDataGrid(0, 0, 0, 0);
    });
}

function doInitChooseTable() {
	$('#tblWebSitePortList')
			.datagrid(
					{
						iconCls : 'icon-edit',// 图标
						width : 'auto',
						height : 'auto',
						nowrap : false,
						striped : true,
						border : true,
						collapsible : false,// 是否可折叠的
						fit : true,// 自动大小
						fitColumns:true,
						url : path + '/monitor/webSite/listWebSitePortInfo',
						// sortName: 'code',
						// sortOrder: 'desc',
						remoteSort : false,
						idField : 'moID',
						singleSelect : false,// 是否单选
						checkOnSelect : false, 
						selectOnCheck : true,
						pagination : true,// 分页控件
						rownumbers : true,// 行号
						columns : [ [
						        {
						        	field : 'moID',
						        	title : '',
								    width : 60,
								    align : 'center',
									formatter : function(value, row, index) {
										return '<a style="cursor: pointer;" onclick="javascript:sel('+ value+');">选择</a>';
									}
						        },
						        {
									field : 'available',
									title : '可用状态',
									width : 60,
									align : 'center',
									formatter:function(value,row){
									var t="unknown.png";
									var tipValue = "未知";
									if(value=="1"){
										t="up.png";
										tipValue = "UP";
									}else if(value=="2"){
										t="down.png";
										tipValue = "DOWN";
									}
									return '<img title="' + tipValue + '" src="' + path
											+ '/style/images/levelIcon/' + t + '"/>';
								} 
								},
								{
									field : 'alarmLevel',
									title : '告警状态',
									width : 60,
									align : 'center',
									formatter:function(value,row){
									var val = row.alarmLevelName;
									var t = row.levelIcon;
									if(val==null || val==""){
										val="正常";
										t="right.png";
									}
									return '<img src="' + path
											+ '/style/images/levelIcon/' + t + '"/>' + val;
								} 
								},
								{
									field : 'siteName',
									title : '站点名称',
									width : 80,
									align : 'center',
									formatter : function(value, row, index) {
						        		if(value && value.length > 16){
						        		value2 = value.substring(0,16) + "...";
										 return '<span title="' + value + '" style="cursor: pointer;" >' +value2+'</span>';
						        	}else{
										return value;
									}
						        }
								},
								{
									field : 'ipAddr',
									title : '站点地址',
									width : 100,
									align : 'center'
								},
								{
									field : 'port',
									title : '端口',
									width : 60,
									align : 'center'
								},
								{
									field : 'portType',
									title : '端口协议类型',
									width : 100,
									align : 'center',
									formatter:function(value,row){
									if(value == 1){
										return "TCP";
									} else if (value == 2) {
										return "UDP";
									}
							} 
								},
								{
									field : 'formatTime',
									title : '响应时间',
									width : 100,
									align : 'center'
								},
								{
									field : 'durationTime',
									title : '持续时间',
									width : 100,
									align : 'center'
								}
								 ] ]
					});
    $(window).resize(function() {
        $('#tblWebSitePortList').resizeDataGrid(0, 0, 0, 0);
    });
}

/**
 * 刷新表格数据
 */
function reloadTable() {
	$('#tblWebSitePortList').datagrid('options').queryParams = {
		"siteName" : $("#txtSiteName").val()
	};
	reloadTableCommon_1('tblWebSitePortList');
}

function reloadTableCommon_1(dataGridId) {
	$('#' + dataGridId).datagrid('load');
	$('#' + dataGridId).datagrid('uncheckAll');
}

function sel(moId){
	if(window.opener) { 
	     fWindowText1 = window.opener.document.getElementById("ipt_webSiteMoID"); 
	     fWindowText1.value = moId; 
	     window.opener.findSitePortInfo();
	     window.close();
	} 

}

function viewDevicePortal(moID, siteName, siteType){
	var type = "TCP";
    var title = type +"_"+ siteName + '视图';
    var moClass = type;
    var moClassName = type;
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
                var urlParams = "?moID=" + moID + "&moClass=" + moClass + "&flag=device";
                var uri = path + "/monitor/gridster/showPortalView" + urlParams;
                var iWidth = window.screen.availWidth - 210; //弹出窗口的宽度190
                var y = window.screen.availHeight - 102;
                var y2 = y - 29;
                var iHeight = window.screen.availHeight - 265; //弹出窗口的高度;
                var iTop = 165;
                var iLeft = 188;
                var content = '<iframe scrolling="auto" frameborder="0"  src="' + uri + '" style="width:100%;height:100%;"></iframe>';
                var isExistTabs = parent.parent.document.getElementById("tabs_window");
                var isPartentTabs = parent.document.getElementById("tabs_window");
                if (isPartentTabs != null) {
                	if (parent.tabsLst.in_array(title) == true) {
//                		parent.$.messager.alert("提示","该站点视图已经打开！","info");
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
					} else {
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
//                    		parent.parent.$.messager.alert("提示","该站点视图已经打开！","info");
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
    					} else {
    						parent.parent.$('#tabs_window').tabs('add', {
                                title: title,
                                content: content,
                                closable: true
                            });
    						parent.parent.tabsLst.push(title);
    					}
                        
                    }
                    else {
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




function doDeleProt(){
	var ids=null;
	
	var rows  = $('#tblWebSitePortList').datagrid('getChecked');
	for(var i =0;i<rows.length;i++){
		if (null == ids) {
			ids = rows[i].moID+"_"+4;// 4代表TCP
		} else {
			ids += "," + rows[i].moID+"_"+4;
		}
	}
	toAffirmWebSite(ids);
}

/**
 * 跳转至CMDB界面
 * @return
 */
function showCMDB(){
	var rows  = $('#tblWebSitePortList').datagrid('getSelections');
	var relationPath=$("#relationPath").val();
	var flag = null;
	if(rows.length>0){
		var moClassId = rows[0].moClassID;
		var ids = rows[0].moID;
		for(var i=1; i<rows.length; i++){
			ids+=',' +rows[i].moID;
			if( moClassId != rows[i].moClassID){
				flag = "1";
			}		
		}
		if(flag == null){
				parent.parent.$('#popWin').window({
				title:'资源分类',
			    width:700,
			    height:400,
			    minimizable:false,
			    maximizable:false,
			    collapsible:false,
			    modal:true,
			    href: getRootName() + '/monitor/deviceManager/showCmdb?moClassId='+moClassId+"&moids="+ids+"&relationPath="+relationPath
			});
		}else{
			$.messager.alert('提示', '站点类型不一致', 'error');
		}
	}else{
		$.messager.alert('提示', '没有任何选中项', 'error');
	}	
}

function closeDlgPort(){
	$('#dlgProt').panel('close');
	$('#dlgProt').panel('destroy');
}
/**
 * 确认删除该设备是否有采集任务
 * @return
 */
function toAffirmWebSite(ids){
	if (null != ids) {		
	// 校验其是否有采集任务
	object = checkTaskStatus(ids);
	
	if( null !=object && object !=""){
		var dlgsb = "<div id='dlgProt'><div class='conditionsBtn'>"
			+"<a href='javascript:void(0);' onclick='javascript:closeDlgPort()' id='btntaskSave'>关闭</a></div></div>";
		var dlgTask = $(dlgsb).appendTo(document.body).dialog({
			title:'提示',
			width: 400,
			height: 300,
			content: '',
			modal:true,
			closed: true,
			onClose:function(){
			$('#dlgProt').panel('destroy');
			}
		});
	var serviceTab =$('<table id="ProtData"></table>').prependTo(dlgTask.dialog("body")).datagrid({
			title:'删除的设备中含有以下关联任务，请先将关联任务删除',
			nowrap : false,
			fit : true,// 自动大小
			fitColumns:true,
			 columns:[[    
				        {field:'type',title:'关联任务类型', width:100},
				        {field:'className',title:'对象类型/任务名称', width:100},    
				        {field:'deviceIp',title:'设备IP/源对象名称', width:100}  
				    ]] 
		}); 
		$('#ProtData').datagrid('loadData',object);
		dlgTask.dialog("open");
		return ;
	}
	
		$.messager.confirm("提示", "确定要删除该设备？", function(r) {
		if (r == true) {	
			$('#dlgProt').panel('close');
			$('#dlgProt').panel('destroy');
			var uri =getRootName()+"/monitor/deleteMonitorObject/deleteMonitor";
			var load ="<div id='loading' style='margin-top: 30px; margin-left: 20px;'><img src =' "+getRootName()+"/style/images/loading2.gif' /></div>";
			var loadlog = $(load).dialog({
				title:'正在删除中，请稍后....',
				width: 200,
				height: 120,
				modal:true,
				closable:false
			});
			var ajax_param = {
				url : uri,
				type : "post",
				datdType : "json",
				data : {
					"MOID":ids
				},
				success : function(data) {
					loadlog.panel("close");
					if (true === data) {
						$.messager.alert("提示", "删除成功！", "info");
						reloadTable();
					} else {
						$.messager.alert("提示", "删除失败！", "error");
					}
				}
			};
			ajax_(ajax_param);
		}
	});
	}else{
		$.messager.alert('提示', '没有任何选中项', 'error');
	}	
}
/**
 * 查询该设备是否有采集任务
 * @param ip
 * @return
 */
function checkTaskStatus(ids){
	var objectTaskData="";
		var ajax_param = {
				url : getRootName()+"/monitor/deleteMonitorObject/checkTask",
				type : "post",
				datdType : "json",
				async:false,
				data : {
				"MOID":ids
				},
		success : function(data) {
					objectTaskData =data;
				}
		};
		ajax_(ajax_param);
	return objectTaskData;
}

