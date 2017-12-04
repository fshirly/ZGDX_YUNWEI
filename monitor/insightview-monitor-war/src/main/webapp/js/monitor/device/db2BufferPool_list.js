$(document).ready(function() {
	var moID = $("#moId").val();
	var flag=$('#flag').val();
	doTbsInitTables();
	if(flag == "null" || flag =="" || flag ==null){ 
		$('#tblDb2BufferPoolInfo').datagrid('hideColumn','moId');
	}
	});

// ORACLE 表空间信息列表
function doTbsInitTables() {
	var dbMoId = $("#dbMoId").val();
	if(dbMoId==null || dbMoId=="" || dbMoId=="null"){
		dbMoId=-1;
	}
	var path = getRootName();
	$('#tblDb2BufferPoolInfo')
			.datagrid(
					{
						iconCls : 'icon-edit',// 图标
						width : 550,
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
						url : path + '/monitor/db2Manage/getDb2BufferPoolInfo?dbMoId='+dbMoId,

						idField : 'fldId',
						pagination : true,// 分页控件
						rownumbers : true,// 行号
						columns : [ [
										{
											field : 'moId',
											title : '',
											width : 80,
											align : 'center',
											formatter : function(value, row, index) {
												return '<a style="cursor: pointer;" onclick="javascript:sel('+ value+');">选择</a>';
											}
										},
										{
											field : 'bufferPoolName',
											title : '缓冲池名称',
											width : 120,
											align : 'center'
											/*	,
											 formatter : function(value, row, index) {
									       	return '<a onclick="javascript:doOpenDetail('+ row.moid+ ');">'+value+"</a>";
									       }*/	
										},
										{
									         field : 'databaseName',
									         title : '数据库名称',
									         width : 100,
									         align : 'center'
								        },
								        {
									         field : 'instanceName',
									         title : '数据库实例名',
									         width : 100,
									         align : 'center'
								        },
										{
									         field : 'ip',
									         title : '服务IP',
									         width : 80,
									         sortable:true,
									         align : 'center'
								        },
										{
											field : 'formatBufferSize',
											title : '缓冲池大小',
											width : 100,
											align : 'center'
										},
										{
											field : 'npages',
											title : '页数',
											width : 80,
											align : 'center',
											formatter : function(value, row) {
								    	   if(value<0){
										   	return '';
										   }else{
										   	return value;
										   }
								    }
										}
										,
										{
											field : 'formatPageSize',
											title : '页大小',
											width : 80,
											align : 'center'
										}
										
										] ],
						onLoadSuccess : function() {
							$('.easyui-progressbar').progressbar().progressbar(
									'setColor');
						}
					});
	$(window).resize(function() {
	    $('#tblDb2BufferPoolInfo').resizeDataGrid(0, 0, 0, 0);
	});
}

$.fn.progressbar.methods.setColor = function(jq, color) {
	return jq.each(function() {
		var opts = $.data(this, 'progressbar').options;
		var color = color || opts.color || $(this).attr('color');
		$(this).css('border-color', color).find(
				'div.progressbar-value .progressbar-text').css(
				'background-color', color);
	});
}
function doOpenDetail(moId){
	//查看文件详情页面
	var isExistPop = parent.parent.document.getElementById("popWin");
	if(isExistPop != null){
		parent.parent.$('#popWin').window({
	    	title:'表空间详细信息',
	        width:800,
	        height:350,
	        minimizable:false,
	        maximizable:false,
	        collapsible:false,
	        modal:true,
	        href: getRootName() + '/monitor/orclManage/toOrclTbsDetail?moId='+moId
	    });
	}else{
		parent.$('#popView').window({
	    	title:'表空间详细信息',
	        width:800,
	        height:350,
	        minimizable:false,
	        maximizable:false,
	        collapsible:false,
	        modal:true,
	        href: getRootName() + '/monitor/orclManage/toOrclTbsDetail?flag=1&moId='+moId
	    });
	}
//	var url = getRootName() + '/monitor/orclManage/toOrclTbsDetail?moId='+moId;
//	window.open(url,"","height=500px,width=800px,left=400,top=200,resizable=no,scrollings=no,status=no,toolbar=no,menubar=no,location=no");
}
/**
 * 刷新表格数据
 */
function reloadTable() {
	$('#tblDb2BufferPoolInfo').datagrid('options').queryParams = {
		"databaseName" : $("#databaseName").val(),
		"bufferPoolName" : $("#bufferPoolName").val(),
		"ip" : $("#ip").val(),
		"instanceName" : $("#instanceName").val()
	};
	reloadTableCommon_1('tblDb2BufferPoolInfo');
}

function reloadTableCommon_1(dataGridId) {
	$('#' + dataGridId).datagrid('load');
	$('#' + dataGridId).datagrid('uncheckAll');
}

function sel(moid){
	if(window.opener) { 
	     fWindowText1 = window.opener.document.getElementById("ipt_db2BufferPoolMoId"); 
	     fWindowText1.value = moid; 
	     window.opener.findDb2BufferPoolInfo();
	     window.close();
	} 
}  