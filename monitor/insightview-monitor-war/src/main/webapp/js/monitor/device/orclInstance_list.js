$(document).ready(function() {
	var flag=$('#flag').val();
	doInitTables();// 数据文件
	if(flag == "null" || flag =="" || flag ==null){ 
		$('#tblOrclInsList').datagrid('hideColumn','moid');
	}
});


// 数据文件
function doInitTables() {
	 var flag=$('#flag').val();
	 var dbmsMoId = $("#dbmsMoId").val();
	 if(dbmsMoId == "" || dbmsMoId == null || dbmsMoId == "null"){
		 dbmsMoId = -1;
	 }
	 var path = getRootName();
	 $('#tblOrclInsList')
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
	      url : path + '/monitor/orclDbManage/listOrclInstance?dbmsMoId='+dbmsMoId,
	      idField : 'fldId',
	      pagination : true,// 分页控件
		  rownumbers : true,// 行号	
	      columns : [ [ 
			{
			    field : 'moid',
			    title : '',
			    width : 100,
			    align : 'center',
				formatter : function(value, row, index) {
					return '<a style="cursor: pointer;" onclick="javascript:sel('+ value+');">选择</a>';
				}
			   },
	        {
	         field : 'instancename',
	         title : '数据库实例名称',
	         width : 100,
	         align : 'center'
	        },
	        {
	         field : 'ip',
	         title : '数据库服务IP',
	         width : 100,
	         sortable:true,
	         align : 'center',
	         formatter : function(value, row, index) {
//					if(flag !=null && flag !=""){ 
//	        			return value;
//					}else {
//						var to = "&quot;" + row.moid
//						+ "&quot;,&quot;" + "oracle"
//						+ "&quot;,&quot;" + value
//						+ "&quot;"
//						return '<a style="cursor: pointer;" onclick="javascript:viewDevicePortal('+ to +');">'+value+'</a>'; 
//					}	
	        	return value;
				}
	        },
	        {
	         field : 'port',
	         title : '端口',
	         width : 100,
	         align : 'center'
	        },
	        {
	         field : 'deviceip',
	         title : '运行主机',
	         width : 70,
	         align : 'center',
				formatter : function(value, row, index) {
     			if(row.moClassId != 0 && (flag ==null || flag =="")){
     				var to = "&quot;" + row.moDeviceId
						+ "&quot;,&quot;" + value
						+ "&quot;"
     				return '<a style="cursor: pointer;" onclick="javascript:toShowView('
						+ to
						+ ');">'+value+'</a>'; 
	        		}else{
	        			return value;
	        		}
			}
	        },
	        {
	         field : 'formatTime',
	         title : '启动时间',
	         width : 120,
	         align : 'center'
	        },
	        {
	         field : 'freesize',
	         title : '空闲空间',
	         width : 150,
	         align : 'center'
	        },
	        {
	         field : 'totalsize',
	         title : '总空间',
	         width : 100,
	         align : 'center'
	        },
	        {
	         field : 'installpath',
	         title : '安装路径',
	         width : 100,
	         align : 'center'
	        }
	        ] ]
	     });
	 $(window).resize(function() {
		    $('#tblOrclInsList').resizeDataGrid(0, 0, 0, 0);
		});
	 }

/**
 * 刷新表格数据
 */
function reloadTable() {
	$('#tblOrclInsList').datagrid('options').queryParams = {
		"instanceName" : $("#instancename").val(),
		"ip" : $("#ip").val()
	};
	reloadTableCommon_1('tblOrclInsList');
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
					parent.$('#tabs_window').tabs('add',{
				    title:title,
				    content:content,
				    closable:true
				       });
				}else if(isExistTabs != null){
					parent.parent.$('#tabs_window').tabs('add',{
					    title:title,
					    content:content,
					    closable:true
					    });
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
	     fWindowText1 = window.opener.document.getElementById("ipt_oracleInsMoId"); 
	     fWindowText1.value = moid; 
	     window.opener.findOracleInsInfo();
	     window.close();
	} 
} 