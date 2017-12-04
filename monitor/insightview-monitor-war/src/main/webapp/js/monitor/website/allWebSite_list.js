var path = getRootName();
var tabsLst = [];
$(document).ready(function() {
	var flag = $("#flag").val();
	if (flag == "choose") {
		doInitChooseTable();
	} else {
		doInitTable();
	}
	$('#tabs_window').tabs({   
		onClose:function(title){   
			tabsLst.remove(title);     
	      }   
	  });
});

/**
 * 加载页面并初始化表格
 * @return
 */
function doInitTable() {
	$('#tblAllWebSiteList')
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
						url : path + '/monitor/webSite/listWebSiteInfo',
						// sortName: 'code',
						// sortOrder: 'desc',
						remoteSort : false,
						idField : 'moID',
						singleSelect : false,// 是否单选
						checkOnSelect : false, 
						selectOnCheck : true,
						pagination : true,// 分页控件
						rownumbers : true,// 行号
						toolbar : [ {
							'text' : '新增',
							'iconCls' : 'icon-add',
							handler : function() {
								doOpenAdd();
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
									width : 80,
									sortable:true,
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
									sortable:true,
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
									if(value && value.length > 12){
						        		value2 = value.substring(0,12) + "...";
						        		return '<a style="cursor: pointer;" onclick="javascript:viewDevicePortal(' + to + ');">' + '<span title="' + value + '" style="cursor: pointer;" >' +value2+'</span>' + '</a>'; 
						        	}else{
						        		return '<a style="cursor: pointer;" onclick="javascript:viewDevicePortal(' + to + ');">' + value + '</a>';
									}
					                    
					                    
					            }
								},
								{
									field : 'siteAddr',
									title : '站点地址',
									width : 100,
									align : 'center',
									formatter : function(value, row, index) {
						        		if(value && value.length > 30){
						        			value2 = value.substring(0,30) + "...";
						        			if(row.siteType == 3){
						        				var url = value.replace(":","\:");
						        				url = url.replace("/","\/");
						        				url = url.replace(".","\\.");
						        				var to = "&quot;" + url+ "&quot;"
						        				return '<a style="cursor: pointer;" onclick="javascript:viewHttp(' + to + ');">' + '<span title="' + value + '" style="cursor: pointer;" >' +value2+'</span>' + '</a>';
						        			}else{
						        				return '<span title="' + value + '" style="cursor: pointer;" >' +value2+'</span>';
						        			}
							        	}else{
							        		if(row.siteType == 3){
							        			var url = value.replace(":","\:");
						        				url = url.replace("/","\/");
						        				url = url.replace(".","\\.");
						        				var to = "&quot;" + url+ "&quot;"
							        			return '<a style="cursor: pointer;" onclick="javascript:viewHttp(' + to + ');">' + value + '</a>';
						        			}else{
						        				return value;
						        			}
										}
						        }
								},
								{
									field : 'siteType',
									title : '监控类型',
									width : 100,
									align : 'center',
									formatter:function(value,row){
										if (value == 1) {
											return "FTP";
										} else if (value == 2) {
											return "DNS";
										} else if (value == 3) {
											return "HTTP";
										} else if (value == 4) {
											return "TCP";
										}
									} 
								},
								{
									field : 'responseTimeFormat',
									title : '响应时间',
									width : 100,
									align : 'center'
								},
								{
									field : 'moids',
									title : '操作',
									width : 60,
									align : 'center',
									formatter : function(value,row,index){
									 var to = "&quot;" + row.moID
										+ "&quot;,&quot;" + row.siteType
										+ "&quot;"
									return '<a style="cursor:pointer;" title="配置" onclick="javascript:toUpdate('
											+ to
											+ ');"><img src =" '+path+'/style/images/icon/icon_setting.png" alt="配置" /></a>';	
							    }
								}
								 ] ]
					});
    $(window).resize(function() {
        $('#tblAllWebSiteList').resizeDataGrid(0, 0, 0, 0);
    });
}


/**
 * 刷新表格数据
 */
function reloadTable() {
	var type = $("#txtSiteType").combobox('getValue');
	if (type == "") {
		type = -1;
	}
	$('#tblAllWebSiteList').datagrid('options').queryParams = {
		"siteName" : $("#txtSiteName").val(),
		"siteType" : type
	};
	reloadTableCommon_1('tblAllWebSiteList');
}

function reloadTableCommon_1(dataGridId) {
	$('#' + dataGridId).datagrid('load');
	$('#' + dataGridId).datagrid('uncheckAll');
}

function viewDevicePortal(moID, siteName, siteType){
	var type = "";
	if (siteType == 1) {
		type = "FTP";
	} else if (siteType == 2) {
		type = "DNS";
	} else if (siteType == 3) {
		type = "HTTP";
	} else if (siteType == 4) {
		type = "TCP";
	}
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
                var isPartentTabs = document.getElementById("tabs_window");
                if (isPartentTabs != null) {
                	if (tabsLst.in_array(title) == true) {
						$.messager.alert("提示","该站点视图已经打开！","info");
					} else {
						$('#tabs_window').tabs('add', {
	                        title: title,
	                        content: content,
	                        closable: true
	                    });
						tabsLst.push(title);
					}
                }
                
            }
            else {
                $.messager.alert("提示", "视图加载失败！", "error");
            }
            
        }
    }
    ajax_(ajax_param);
}


/*
 * 打开新增页面
 */
function doOpenAdd(){
	parent.$('#popWin').window({
    	title:'站点新建',
        width:800,
        height : 580,
        minimizable:false,
        maximizable:false,
        collapsible:false,
        modal:true,
        href: getRootName() + '/monitor/webSite/toShowSiteAdd',
    });
}

function toUpdate(moID,siteType){
	parent.$('#popWin').window({
    	title:'站点编辑',
        width:800,
        height : 580,
        minimizable:false,
        maximizable:false,
        collapsible:false,
        modal:true,
        href: getRootName() + '/monitor/webSite/toShowSiteModify?moID='+moID+'&siteType='+siteType,
    });
}

Array.prototype.in_array = function(e) {  
	 for(i=0;i<this.length;i++){  
		 if(this[i] == e){
			 return true;  
		 } 
	 }  
return false;  
}

/**
 * 跳转至http地址页面
 * @return
 */
function viewHttp(siteAddr){
	if(siteAddr.indexOf("http://") == 0){
		window.open(siteAddr);
	}else{
		window.open("http://" + siteAddr);
	}
}