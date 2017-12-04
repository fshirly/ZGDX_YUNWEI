$(document).ready(function() {
	var flag=$('#flag').val();
	if(flag ==null || flag ==""){ 
		doInitTable();
		$('#tblDataList').datagrid('hideColumn','moId');
	}else{
		doInitChooseTable();
	}
});

/**
 * 加载页面并初始化表格
 * @return
 */
function doInitTable() {
	var flag=$('#flag').val();
	var dbmsMoId = $('#dbmsMoId').val();
    if(dbmsMoId == null || dbmsMoId == ""){
    	dbmsMoId = -1;
    }
	var path = getRootName();
	var uri = path + '/monitor/dbObjMgr/listMysqlServer?dbmsMoId='+dbmsMoId;
	$('#tblDataList')
			.datagrid(
					{
						width : 'auto',
						height : 'auto',
						nowrap : false,
						striped : true,
						border : true,
						collapsible : false,// 是否可折叠的
						fit : true,// 自动大小
						fitColumns:true,
						url : uri,
						remoteSort : false,
						onSortColumn:function(sort,order){
//						 alert("sort:"+sort+",order："+order+"");
						},						
						idField : 'idField',
						singleSelect : false,// 是否单选
						checkOnSelect : false,
						selectOnCheck : true,
						pagination : true,// 分页控件
						rownumbers : true,// 行号						
						columns : [ [
								{
										field : 'moId',
										title : '',
										width : 40,
										align : 'center',
										formatter : function(value, row, index) {
											return '<a style="cursor: pointer;" onclick="javascript:sel('+ value +');">选择</a>';
										}
								},
								{
									field : 'serverName',
									title : '服务名称',
									width : 100,
									align : 'center',
									formatter : function(value, row, index) {
										if(flag !=null && flag !=""){ 
						        			return value;
										}else {
											return '<a style="cursor: pointer;" onclick="javascript:viewDetail('+ row.dbmsMoid +');">'+value+'</a>'; 
										}	
									}
								 },
								 {
										field : 'ip',
										title : '服务IP',
										width : 80,
										sortable:true,
										align : 'center'
								},
								{
									field : 'port',
									title : '端口号',
									width : 80,
									align : 'center'
								},
								{
									field : 'moName',
									title : '所属主机',
									width : 80,
									align : 'center'
								},
								{
									field : 'startTime',
									title : '启动时间',
									width : 120,
									align : 'center',
									formatter:function(value,row,index){
										if(value!=null && value!="" ){
											return formatDate(new Date(value), "yyyy-MM-dd hh:mm:ss");
										}else{
											return "";
										}	
	                        		}
								},
								{
									field : 'serverCharset',
									title : '服务器字符集',
									width : 80,
									align : 'center'
								},							
								{
									field : 'dbNum',
									title : '数据库个数',
									width : 80,
									align : 'center'
								}
								] ]
					});
	$(window).resize(function() {
        $('#tblDataList').resizeDataGrid(0, 0, 0, 0);
    });
}

/**
 * 加载页面并初始化表格
 * @return
 */
function doInitChooseTable() {
	var flag=$('#flag').val();
	var dbmsMoId = $('#dbmsMoId').val();
    if(dbmsMoId == null || dbmsMoId == ""){
    	dbmsMoId = -1;
    }
	var path = getRootName();
	var uri = path + '/monitor/dbObjMgr/listMysqlServer?dbmsMoId='+dbmsMoId;
	$('#tblDataList')
			.datagrid(
					{
						width : 'auto',
						height : 'auto',
						nowrap : false,
						striped : true,
						border : true,
						collapsible : false,// 是否可折叠的
						fit : true,// 自动大小
						fitColumns:true,
						url : uri,
						remoteSort : true,
						onSortColumn:function(sort,order){
//						 alert("sort:"+sort+",order："+order+"");
						},						
						idField : 'idField',
						singleSelect : false,// 是否单选
						checkOnSelect : false,
						selectOnCheck : true,
						pagination : true,// 分页控件
						rownumbers : true,// 行号						
						columns : [ [
								{
										field : 'moId',
										title : '',
										width : 40,
										align : 'center',
										formatter : function(value, row, index) {
											return '<a style="cursor: pointer;" onclick="javascript:sel('+ value +');">选择</a>';
										}
								},
								{
									field : 'serverName',
									title : '服务名称',
									width : 100,
									align : 'center',
								 },
								 {
									field : 'ip',
									title : '服务IP',
									width : 80,
									align : 'center'
								},
								{
									field : 'port',
									title : '端口号',
									width : 80,
									align : 'center'
								},
								{
									field : 'moName',
									title : '所属主机',
									width : 80,
									align : 'center'
								},
								{
									field : 'startTime',
									title : '启动时间',
									width : 120,
									align : 'center',
									formatter:function(value,row,index){
										if(value!=null && value!="" ){
											return formatDate(new Date(value), "yyyy-MM-dd hh:mm:ss");
										}else{
											return "";
										}	
	                        		}
								},
								{
									field : 'serverCharset',
									title : '服务器字符集',
									width : 80,
									align : 'center'
								},							
								{
									field : 'dbNum',
									title : '数据库个数',
									width : 80,
									align : 'center'
								}
								] ]
					});
	$(window).resize(function() {
        $('#tblDataList').resizeDataGrid(0, 0, 0, 0);
    });
}

/**
 * 刷新表格数据
 */
function reloadTable() {
	$('#tblDataList').datagrid('options').queryParams = {
		"serverName" : $("#serverName").val(),
		"ip" : $("#IP").val()
	};
	reloadTableCommon_1('tblDataList');
}

function reloadTableCommon_1(dataGridId) {
	$('#' + dataGridId).datagrid('load');
	$('#' + dataGridId).datagrid('uncheckAll');
}

/**
 * 展示数据库详情信息
 * @return
 */
function viewDetail(moId){
	parent.parent.$('#popWin').window({
		title:'详情',
	    width:800,
	    height:500,
	    minimizable:false,
	    maximizable:false,
	    collapsible:false,
	    modal:true,
	    href: getRootName() + '/monitor/myManage/toShowServerInfo?flag=1&moID='+moId
	});
}

function sel(moid){
	if(window.opener) { 
	     fWindowText1 = window.opener.document.getElementById("ipt_mysqlServerId"); 
	     fWindowText1.value = moid; 
	     window.opener.findMysqlServerInfo();
	     window.close();
	} 
}  