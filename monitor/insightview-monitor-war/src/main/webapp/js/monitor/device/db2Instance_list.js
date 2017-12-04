$(document).ready(function() {
	var flag=$('#flag').val();
	if(flag == "null" || flag =="" || flag ==null){ 
		doInitTables();// 数据文件
		$('#tblDb2InsList').datagrid('hideColumn','moid');
	}else{
		doInitChooseTable();
	}
});


// 数据文件
function doInitTables() {
	if(dbmsMoId == null || dbmsMoId == "" || dbmsMoId == "null"){
    	dbmsMoId = -1;
    }
	 var flag=$('#flag').val();
	 var path = getRootName();
	 $('#tblDb2InsList')
	   .datagrid(
	     {
	      iconCls : 'icon-edit',// 图标
	      width : 520,
	      height : 'auto',
	      nowrap : false,
	      striped : true,
	      border : true,
	      singleSelect : false,// 是否单选
	      checkOnSelect : false,
	      selectOnCheck : true,
	      collapsible : false,// 是否可折叠的
	      fit : true,// 自动大小
	      fitColumns:true,
	      url : path + '/monitor/db2Manage/listDb2Instance?dbmsMoId='+dbmsMoId,
	      idField : 'fldId',
	      pagination : true,// 分页控件
		  rownumbers : true,// 行号	
	      columns : [ [
			{
			    field : 'moid',
			    title : '',
			    width : 30,
			    align : 'center',
				formatter : function(value, row, index) {
					return '<a style="cursor: pointer;" onclick="javascript:sel('+ value+');">选择</a>';
				}
			   },
			   /**{
					field : 'perfvalue',
					title : '可用状态',
					width : 50,
					align : 'center',
					formatter:function(value,row){
						var t="unknown.png";;
						if(value=="1"){
							t="up.png";
						}else if(value=="2"){
							t="down.png";
						}
						return '<img title="' + value + '" src="' + path
							+ '/style/images/levelIcon/' + t + '"/>';
					}  
				 },*/
	        {
	         field : 'instancename',
	         title : '数据库实例名称',
	         width : 100,
	         align : 'center',
	         formatter : function(value, row, index) {
					if(value ==null && value ==""){ 
	        			return value;
					}else {
						var to = "&quot;" + row.moid
						+ "&quot;,&quot;" + "db2_instance"
						+ "&quot;,&quot;" + value +"_" +row.ip
						+ "&quot;"
						return '<a style="cursor: pointer;" onclick="javascript:viewDevicePortal('+ to +');">'+value+'</a>'; 
					}	
				}
	        },
	        {
	         field : 'ip',
	         title : '数据库服务IP',
	         width : 100,
	         sortable:true,
	         align : 'center'
	        },
	        {
	         field : 'port',
	         title : '端口',
	         width : 100,
	         align : 'center'
	        },
	        {
	         field : 'formatTime',
	         title : '启动时间',
	         width : 120,
	         align : 'center'
	        },
	        {
	         field : 'nodeNum',
	         title : '节点数',
	         width : 100,
	         align : 'center'
	        }
	        ] ]
	     });
	 $(window).resize(function() {
		    $('#tblDb2InsList').resizeDataGrid(0, 0, 0, 0);
		});
	 }

/**
 * 选择
 * @return
 */
function doInitChooseTable() {
	if(dbmsMoId == null || dbmsMoId == "" || dbmsMoId == "null"){
    	dbmsMoId = -1;
    }
	 var flag=$('#flag').val();
	 var path = getRootName();
	 $('#tblDb2InsList')
	   .datagrid(
	     {
	      iconCls : 'icon-edit',// 图标
	      width : 520,
	      height : 'auto',
	      nowrap : false,
	      striped : true,
	      border : true,
	      singleSelect : false,// 是否单选
	      checkOnSelect : false,
	      selectOnCheck : true,
	      collapsible : false,// 是否可折叠的
	      fit : true,// 自动大小
	      fitColumns:true,
	      url : path + '/monitor/db2Manage/listDb2Instance?dbmsMoId='+dbmsMoId,
	      idField : 'fldId',
	      pagination : true,// 分页控件
		  rownumbers : true,// 行号	
	      columns : [ [
			{
			    field : 'moid',
			    title : '',
			    width : 30,
			    align : 'center',
				formatter : function(value, row, index) {
					return '<a style="cursor: pointer;" onclick="javascript:sel('+ value+');">选择</a>';
				}
			   },
	        {
	         field : 'instancename',
	         title : '数据库实例名称',
	         width : 100,
	         align : 'center',
	        },
	        {
	         field : 'ip',
	         title : '数据库服务IP',
	         width : 100,
	         align : 'center'
	        },
	        {
	         field : 'port',
	         title : '端口',
	         width : 100,
	         align : 'center'
	        },
	        {
	         field : 'formatTime',
	         title : '启动时间',
	         width : 120,
	         align : 'center'
	        },
	        {
	         field : 'nodeNum',
	         title : '节点数',
	         width : 100,
	         align : 'center'
	        }
	        ] ]
	     });
	 $(window).resize(function() {
		    $('#tblDb2InsList').resizeDataGrid(0, 0, 0, 0);
		});
 }

/**
 * 刷新表格数据
 */
function reloadTable() {
	$('#tblDb2InsList').datagrid('options').queryParams = {
		"instanceName" : $("#instancename").val(),
		"ip" : $("#ip").val()
	};
	reloadTableCommon_1('tblDb2InsList');
}

function reloadTableCommon_1(dataGridId) {
	$('#' + dataGridId).datagrid('load');
	$('#' + dataGridId).datagrid('uncheckAll');
}

function viewDevicePortal(moid,dbmstype,moName){
	var title = 'oracle'+moName+'视图';
	var moClass=dbmstype;
	var moClassName="Oracle";
	if(dbmstype=="mysql"){
		title = 'mysql'+moName+'视图';
		moClassName="Mysql";
	}
	if(dbmstype=="db2_instance"){
		title = 'db2_instance'+moName+'视图';
		moClassName="DB2_INSTANCE";
	}	
	var path = getRootPatch();
	var uri = path + "/monitor/gridster/viewDevicePortal";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"portalName" : moClassName,
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error"); 
		},
		success : function(data) {
			if(data == true){
				var urlParams="?moID="+moid+"&moClass="+moClass+"&flag=device";
				var uri=path+"/monitor/gridster/showPortalView"+urlParams;
				var iWidth = window.screen.availWidth -210; //弹出窗口的宽度190
				var y = window.screen.availHeight-102;
				var y2 = y-29;
				var iHeight = window.screen.availHeight-265; //弹出窗口的高度;
				var iTop = 165;
				var iLeft = 188;
//				window.open(uri,"","height="+iHeight+",width="+iWidth+",left="+iLeft+",top="+iTop+",resizable=no,scrollbars=yes,status=no,toolbar=no,menubar=no,location=yes");
				var content = '<iframe scrolling="auto" frameborder="0"  src="'+uri+'" style="width:100%;height:100%;"></iframe>';
				var isExistTabs = parent.parent.document.getElementById("tabs_window");
				var isPartentTabs = parent.document.getElementById("tabs_window");

				if(isPartentTabs != null){
					if (parent.tabsLst.in_array(title) == true) {
//						$.messager.alert("提示","该设备视图已经打开！","info");
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
						parent.$('#tabs_window').tabs('add',{
						    title:title,
						    content:content,
						    closable:true
						       });
							parent.tabsLst.push(title);
					}
					
				}else if(isExistTabs != null){
					if (parent.parent.tabsLst.in_array(title) == true) {
//						$.messager.alert("提示","该设备视图已经打开！","info");
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
						parent.parent.$('#tabs_window').tabs('add',{
						    title:title,
						    content:content,
						    closable:true
						    });
						parent.parent.tabsLst.push(title);
					}
					
				}else{
//					window.parent.frames.location = uri;
					window.open(uri,"","height="+iHeight+",width="+iWidth+",left="+iLeft+",top="+iTop+",resizable=no,scrollbars=yes,status=no,toolbar=no,menubar=no,location=yes");
				}
			
					
			}else{
				$.messager.alert("提示","视图加载失败！","error");
			}
			
		}
	}
	ajax_(ajax_param);
	
}


//查看设备视图
function toShowView(moid,moname){
	var path = getRootPatch();
	var uri = path + "/monitor/discover/selectMoClass";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"moid" : moid,
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error"); 
		},
		success : function(data) {
			toShowTabs(moid,data,moname);
		}
	}
	ajax_(ajax_param);
	
}

function toShowTabs(moid,moClassName,moName){
	var path = getRootPatch();
	var uri = path + "/monitor/gridster/viewDevicePortal";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"portalName" : moClassName,
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error"); 
		},
		success : function(data) {
			if(data == true){
				var moClass="host";
				var title = '主机'+moName+'视图';
				if(moClassName=="VHost"){
					moClass="vhost";
					title = '宿主机'+moName+'视图';
				}
				if(moClassName=="VM"){
					moClass="vm";
					title = '虚拟机'+moName+'视图';
				}
				if(moClassName=="Router"){
					moClass="router";
					title = '路由器'+moName+'视图';
				}
				if(moClassName=="Switcher"){
					moClass="switcher";
					title = '交换机'+moName+'视图';
				}
				var urlParams="?moID="+moid+"&moClass="+moClass+"&flag=device";
				var uri=path+"/monitor/gridster/showPortalView"+urlParams;
				var content = '<iframe scrolling="auto" frameborder="0"  src="'+uri+'" style="width:100%;height:100%;"></iframe>';
				parent.$('#tabs_window').tabs('add',{
		            title:title,
		            content:content,
		            closable:true
		        });
			}else{
				$.messager.alert("提示","视图加载失败！","error");
			}
			
		}
	}
	ajax_(ajax_param);
	
}


function sel(moid){
	if(window.opener) { 
	     fWindowText1 = window.opener.document.getElementById("ipt_db2InsMoId"); 
	     fWindowText1.value = moid; 
	     window.opener.findDB2InsInfo();
	     window.close();
	} 
} 