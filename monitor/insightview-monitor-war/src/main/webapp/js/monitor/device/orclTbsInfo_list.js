$(document).ready(function() {
	var moID = $("#moId").val();
	var flag=$('#flag').val();
	if(flag == "null" || flag =="" || flag ==null){ 
		doTbsInitTables(moID);//ORACLE 表空间信息列表
		$('#tblTbsInfo').datagrid('hideColumn','moid');
	}else{
		doInitChooseTable(moID);
	}
});

// ORACLE 表空间信息列表
function doTbsInitTables(moID) {
	var instanceMOID = $("#instanceMOID").val()
	if(instanceMOID == "null" || instanceMOID == null || instanceMOID == ""){
		instanceMOID = -1;
	}
	var path = getRootName();
	$('#tblTbsInfo')
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
						url : path + '/monitor/orclDbManage/getOrclTbsInfo?moID='+moID+'&instanceMOID='+instanceMOID,

						idField : 'fldId',
						pagination : true,// 分页控件
						rownumbers : true,// 行号
						columns : [ [
								{
									field : 'moid',
									title : '',
									width : 80,
									align : 'center',
									formatter : function(value, row, index) {
										return '<a style="cursor: pointer;" onclick="javascript:sel('+ value+');">选择</a>';
									}
								},
								{
									field : 'tbsname',
									title : '表空间名称',
									width : 120,
									align : 'center',
									 formatter : function(value, row, index) {
							       	return '<a onclick="javascript:doOpenDetail('+ row.moid+ ');">'+value+"</a>";
							       }	
								},
								{
							         field : 'dbName',
							         title : '数据库名称',
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
									field : 'initialextent',
									title : 'Extent初始大小',
									width : 120,
									align : 'center'
								},
								{
									field : 'alloctype',
									title : '分配类型',
									width : 120,
									align : 'center'
								}
								,
								{
									field : 'tbsstatus',
									title : '表空间状态',
									width : 120,
									align : 'center'
								}
								,
								{
									field : 'tbstype',
									title : '表空间类型',
									width : 120,
									align : 'center'
								}
								] ],
						onLoadSuccess : function() {
							$('.easyui-progressbar').progressbar().progressbar(
									'setColor');
						}
					});
	$(window).resize(function() {
	    $('#tblTbsInfo').resizeDataGrid(0, 0, 0, 0);
	});
}

function doInitChooseTable(moID) {
	var instanceMOID = $("#instanceMOID").val()
	if(instanceMOID == "null" || instanceMOID == null || instanceMOID == ""){
		instanceMOID = -1;
	}
	var path = getRootName();
	$('#tblTbsInfo')
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
						url : path + '/monitor/orclDbManage/getOrclTbsInfo?moID='+moID+'&instanceMOID='+instanceMOID,

						idField : 'fldId',
						pagination : true,// 分页控件
						rownumbers : true,// 行号
						columns : [ [
								{
									field : 'moid',
									title : '',
									width : 80,
									align : 'center',
									formatter : function(value, row, index) {
										return '<a style="cursor: pointer;" onclick="javascript:sel('+ value+');">选择</a>';
									}
								},
								{
									field : 'tbsname',
									title : '表空间名称',
									width : 120,
									align : 'center',
								},
								{
							         field : 'dbName',
							         title : '数据库名称',
							         width : 100,
							         align : 'center'
						        },
								{
							         field : 'ip',
							         title : '服务IP',
							         width : 80,
							         align : 'center'
						        },
								{
									field : 'initialextent',
									title : 'Extent初始大小',
									width : 120,
									align : 'center'
								},
								{
									field : 'alloctype',
									title : '分配类型',
									width : 120,
									align : 'center'
								}
								,
								{
									field : 'tbsstatus',
									title : '表空间状态',
									width : 120,
									align : 'center'
								}
								,
								{
									field : 'tbstype',
									title : '表空间类型',
									width : 120,
									align : 'center'
								}
								] ],
						onLoadSuccess : function() {
							$('.easyui-progressbar').progressbar().progressbar(
									'setColor');
						}
					});
	$(window).resize(function() {
	    $('#tblTbsInfo').resizeDataGrid(0, 0, 0, 0);
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
	$('#tblTbsInfo').datagrid('options').queryParams = {
		"dbName" : $("#dbname").val(),
		"tbsName" : $("#tbsname").val(),
		"ip" : $("#ip").val()
	};
	reloadTableCommon_1('tblTbsInfo');
}

function reloadTableCommon_1(dataGridId) {
	$('#' + dataGridId).datagrid('load');
	$('#' + dataGridId).datagrid('uncheckAll');
}

function sel(moid){
	if(window.opener) { 
	     fWindowText1 = window.opener.document.getElementById("ipt_oracleTbsMoId"); 
	     fWindowText1.value = moid; 
	     window.opener.findOracleTBSInfo();
	     window.close();
	} 
}  