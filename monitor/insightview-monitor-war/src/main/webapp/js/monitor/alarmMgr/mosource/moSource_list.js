$(document).ready(function() {
	var flag=$('#flag').val();
	doInitTable();
	if(flag=='choose'){ 
//		$('#tblMOSource').datagrid('showColumn','moid');
		$("#isShow").show();
	}else{
//		$('#tblMOSource').datagrid('hideColumn','moid');
		$("#isShow").hide();
	}
	
});

/**
 * 加载页面并初始化表格
 * @return
 */
function doInitTable(){
	var path = getRootName();
	$('#tblMOSource')
			.datagrid(
					{ 
						iconCls : 'icon-edit',// 图标
						width : 'auto',
						height : 410,
						nowrap : false,
						striped : true,
						border : true,
						collapsible : false,// 是否可折叠的
//						fit:true,// 自动大小
						fitColumns:true,
						url : path + '/monitor/moSource/selectMOSource',
						remoteSort : false,
						idField : 'fldId',
						singleSelect : true,// 是否单选
						checkOnSelect : false,
						selectOnCheck : false,
						pagination : true,// 分页控件
						rownumbers : true,// 行号
						
						columns : [ [
//						        {
//									field : 'moID',
//									title : '',
//									width : 70,
//									align : 'center',
//									formatter : function(value, row, index) {
//										return '<a style="cursor: pointer;" onclick="javascript:sel('
//												+ value
//												+ ');">选择</a>';
//									}
//						        },
								{  
									field : 'moName',
									title : '告警源名称',  
									width : 150,
									align : 'center',
									sortable : true,
								},
								/**{
									field : 'moAlias',
									title : '告警源别名',
									width : 80,
									align : 'center',
									sortable : true,
								},**/
								{
									field : 'alarmLevelName',
									title : '告警等级',
									width : 80,
									align : 'center',
									sortable : true,
										formatter:function(value,row){
										if(value== null || value == ""){
											return "正常";
										}else{
											return value;
										}
									}
								},
								/**{
									field : 'operStatus',
									title : '可用状态', 
									width : 50,
									align : 'center',
									sortable : true,
									formatter : function(value, row, index) {
										if (value == 1) {
											return 'UP';
										}else if (value == 2) {
											return 'DOWN';
										}else{
											return '未知';
										}
									}
								},**/
								{
									field : 'deviceIP',
									title : '设备IP',
									width : 130,
									align : 'center',
									sortable : true,
								},
								{
									field : 'className',
									title : '对象类型',
									width : 80,
									align : 'center'
								},
								{
									field : 'domainName',
									title : '管理域', 
									width : 100,
									align : 'center',
									sortable : true,
								}]]
					});
}
/*
 * 更新表格
 */
function reloadTable(){
	var moName = $("#txtMOName").val();
	$('#tblMOSource').datagrid('options').queryParams = {
		"moName" : moName
	};
	reloadTableCommon_1('tblMOSource');
}

function reloadTableCommon_1(dataGridId) {
	$('#' + dataGridId).datagrid('load');
	$('#' + dataGridId).datagrid('uncheckAll');
}

function sel(moid){
	if(window.opener) { 
		var dif=$('#dif').val();
		if(dif=="cause"){
			fWindowText1 = window.opener.document.getElementById("ipt_moID");
		}else{
			fWindowText1 = window.opener.document.getElementById("ipt_recoverMoID"); 
		}
	     fWindowText1.value = moid; 
	     window.opener.findMOSourceInfo(dif);
	     window.close();
	} 
}


function toConfirmSelect(){
	var moSource = $('#tblMOSource').datagrid('getSelected');
	if (moSource.moID != null&&moSource.moID !="") {
		var dif=$('#dif').val();
		showMOSourceInfo(dif,moSource.moID,moSource.moName,moSource.deviceIP);
    		$('#event_select_dlg').dialog('close');
	}else{
		$.messager.alert('提示', '没有任何选中项', 'error');
	} 
}