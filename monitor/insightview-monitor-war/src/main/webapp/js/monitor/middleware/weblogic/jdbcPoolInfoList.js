$(document).ready(function() {
	var moID = $("#moId").val();
	var liInfo = $("#liInfo").val();
	doDBInitTables(moID,liInfo);// 数据库列表
	$('#tblJdbcPoolInfos').datagrid('hideColumn','moId');
});


// 数据库列表
function doDBInitTables(moID,liInfo) {
	 var path = getRootName();
	 $('#tblJdbcPoolInfos')
	   .datagrid(
	     {
	      iconCls : 'icon-edit',// 图标
	      width : 120,
	      height : 'auto',
	      nowrap : false,
	      rownumbers:true,
	      striped : true,
	      border : true,
	      singleSelect : false,// 是否单选
	      checkOnSelect : false,
	      selectOnCheck : true,
	      collapsible : false,// 是否可折叠的
	      fit : true,// 自动大小
	      fitColumns:true,
	      url : path + '/monitor/weblogicManage/getJdbcPoolInfos?moID='+moID,
	      idField : 'fldId',
	      columns : [ [
			{
			    field : 'moId',
			    title : '对象标志',
			    width : 80,
			    align : 'center'
			},
	        {
		         field : 'dsName',
		         title : '名称',
		         width : 80,
		         align : 'center',
		         formatter : function(value, row, index) {
						if(value == null){
							return value;
						}else{
							var to = "&quot;" + row.moId
							+ "&quot;,&quot;" + row.dsName
							+ "&quot;,&quot;" + row.ip
							+ "&quot;"
							return '<a style="cursor: pointer;" onclick="javascript:viewDevicePortal('+ to +');">'+value+'</a>'; 
						}
						
						
			        			
					}
	        },
	        {
		         field : 'maxConns',
		         title : '最大连接数',
		         width : 80,
		         align : 'center'
	        },
	        {
		         field : 'curConns',
		         title : '当前连接数',
		         width : 80,
		         align : 'center'
	        },
	        {
		         field : 'connTimeOut',
		         title : '连接超时',
		         width : 80,
		         align : 'center'
	        }
	        ] ],
			onLoadSuccess : function() {
				//自适应部件大小
	    	 window.parent.resizeWidgetByParams(liInfo);
			}
	       
	     });
	 $(window).resize(function() {
		    $('#tblJdbcPoolInfos').resizeDataGrid(0, 0, 0, 0);
		});
	 }

//查看视图
function viewDevicePortal(moId,dsName,ip){
	var title = 'JDBC连接池'+dsName+'视图';
	var moClass='jdbcpool';
	var moClassName="JdbcPool";
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
				var urlParams="?moID="+moId+"&moClass="+moClass+"&flag=device";
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

function toShowDetail(moId){
	//查看配置项页面
	var isExistPop = parent.parent.parent.document.getElementById("popWin");
	if(isExistPop != null){
		parent.parent.parent.$('#popWin').window({
	    	title:'JDBC连接池详情信息',
	        width:800,
	        height:400,
	        minimizable:false,
	        maximizable:false,
	        collapsible:false,
	        modal:true,
	        href:getRootName()+'/monitor/weblogicManage/toShowJdbcPoolDetail?moID='+moId
	    });
	}else{
		parent.$('#popView').window({
	    	title:'JDBC连接池详情信息',
	        width:800,
	        height:400,
	        minimizable:false,
	        maximizable:false,
	        collapsible:false,
	        modal:true,
	        href:getRootName()+'/monitor/weblogicManage/toShowJdbcPoolDetail?flag=1&moID='+moId
	    });
	}
	
}
