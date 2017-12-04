$(document).ready(function() {
	var moID = $("#moId").val();
	var flag=$('#flag').val();
	
	doTbsInitTables(moID);//ORACLE 表空间信息列表
	if(flag == "null" || flag =="" || flag ==null){ 
		$('#tblSgaInfo').datagrid('hideColumn','moID');
	}
	});

// ORACLE 表空间信息列表
function doTbsInitTables(moID) {
	var path = getRootName();
	var instanceMOID = $("#instanceMOID").val()
	if(instanceMOID == "null" || instanceMOID == null || instanceMOID == ""){
		instanceMOID = -1;
	}
	$('#tblSgaInfo')
			.datagrid(
					{
						iconCls : 'icon-edit',// 图标
						width : 1000,
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
						url : path + '/monitor/orclDbManage/listOrclSga?instanceMOID='+instanceMOID,

						idField : 'fldId',
						pagination : true,// 分页控件
						rownumbers : true,// 行号
						columns : [ [
								{
									field : 'moID',
									title : '',
									width : 80,
									align : 'center',
									formatter : function(value, row, index) {
										return '<a style="cursor: pointer;" onclick="javascript:sel('+ value+');">选择</a>';
									}
//										,
//							      	formatter : function(value, row, index) {
//							       	return '<a onclick="javascript:doOpenDetail('+ value+ ');">'+value+"</a>";
//							       }	 
								},
								{
									field : 'ip',
									title : '数据库服务IP',
									width : 160,
									sortable:true,
									align : 'center'
								},
								{
									field : 'instanceName',
									title : '数据库实例名称',
									width : 120,
									align : 'center'
								},
								{
									field : 'totalSize',
									title : 'SGA大小',
									width : 120,
									align : 'center'
								},
								{
									field : 'fixedSize',
									title : '固定区大小',
									width : 150,
									align : 'center'
								},
								{
									field : 'bufferSize',
									title : '数据缓冲区',
									width : 150,
									align : 'center'
								},
								{
									field : 'redologBuf',
									title : '重做日志缓存',
									width : 150,
									align : 'center'
								},
								{
									field : 'poolSize',
									title : 'POOL大小',
									width : 150,
									align : 'center'
								},
								{
									field : 'sharedPool',
									title : '共享池大小',
									width : 150,
									align : 'center'
								},
								{
									field : 'largePool',
									title : '大池大小',
									width : 150,
									align : 'center'
								},
								{
									field : 'javaPool',
									title : 'JAVA池大小',
									width : 150,
									align : 'center'
								},
								{
									field : 'streamPool',
									title : '流池大小',
									width : 150,
									align : 'center'
								},
								{
									field : 'libraryCache',
									title : '库缓存',
									width : 120,
									align : 'center'
								},
								{
									field : 'dicaCache',
									title : '数据字典缓存',
									width : 150,
									align : 'center'
								}
								] ],
						onLoadSuccess : function() {
							$('.easyui-progressbar').progressbar().progressbar(
									'setColor');
						}
					});
	$(window).resize(function() {
	    $('#tblSgaInfo').resizeDataGrid(0, 0, 0, 0);
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
		alert("test");
		parent.parent.$('#popWin').window({
	    	title:'SGA详细信息',
	        width:800,
	        height:350,
	        inline:true,
	        minimizable:false,
	        maximizable:false,
	        collapsible:false,
	        modal:true,
	        href: getRootName() + '/monitor/orclManage/toShowOrclSGADetail?openFlag=1&moID='+moId
	    });

	}else{
		parent.$('#popView').window({
	    	title:'SGA详细信息',
	        width:800,
	        height:350,
	        inline:true,
	        minimizable:false,
	        maximizable:false,
	        collapsible:false,
	        modal:true,
	        href: getRootName() + '/monitor/orclManage/toShowOrclSGADetail?openFlag=1&flag=1&moID='+moId
	    });
	}
//	var url = getRootName() + '/monitor/orclManage/toOrclTbsDetail?moId='+moId;
//	window.open(url,"","height=500px,width=800px,left=400,top=200,resizable=no,scrollings=no,status=no,toolbar=no,menubar=no,location=no");
}
/**
 * 刷新表格数据
 */
function reloadTable() {
	$('#tblSgaInfo').datagrid('options').queryParams = {
		"instanceName" : $("#instanceName").val(),
		"ip" : $("#ip").val()
	};
	reloadTableCommon_1('tblSgaInfo');
}

function reloadTableCommon_1(dataGridId) {
	$('#' + dataGridId).datagrid('load');
	$('#' + dataGridId).datagrid('uncheckAll');
}

function sel(moid){
	if(window.opener) { 
	     fWindowText1 = window.opener.document.getElementById("ipt_oracleSgaMoId"); 
	     fWindowText1.value = moid; 
	     window.opener.findOracleSgaInfo();
	     window.close();
	} 
} 