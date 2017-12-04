$(document).ready(function() {
	var flag=$('#flag').val();
	doInitTable();
	if(flag=='choose'){ 
//		$('#tblMODevice').datagrid('showColumn','moid');
		$("#isShow").show();
	}else{
//		$('#tblMODevice').datagrid('hideColumn','moid');
		$("#isShow").hide();
	}
	
});

/**
 * 加载页面并初始化表格
 * @return
 */
function doInitTable(){
	var path = getRootName();
	$('#tblMODevice')
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
						url : path + '/monitor/discover/selectDeviceList',
						remoteSort : false,
						idField : 'fldId',
						singleSelect : true,// 是否单选
						checkOnSelect : false,
						selectOnCheck : false,
						pagination : true,// 分页控件
						rownumbers : true,// 行号
						
						columns : [ [
//						        {
//									field : 'moid',
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
									field : 'moname',
									title : '告警源名称',  
									width : 130,
									align : 'center',
									sortable : true,
								},
								{
									field : 'deviceip',
									title : '告警源IP',
									width : 130,
									align : 'center',
									sortable : true,
								},
								/**{
									field : 'operstatus',
									title : '可用状态', 
									width : 50,
									align : 'center',
									sortable : true,
									formatter:function(value,row){
									var rtn = "";
									if(value=="1"){
										rtn = "UP";
									}else if(value=="2"){
										rtn = "DOWN";
									}else if(value=="3"){
										rtn = "未知";
									}	
									return rtn; 
								}
								},**/
								{
									field : 'alermlevelInfo',
									title : '告警等级',
									width : 60,
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
	$('#tblMODevice').datagrid('options').queryParams = {
		"moname" : moName
	};
	reloadTableCommon_1('tblMODevice');
}

function reloadTableCommon_1(dataGridId) {
	$('#' + dataGridId).datagrid('load');
	$('#' + dataGridId).datagrid('uncheckAll');
}

function sel(moid){
	if(window.opener) { 
		var dif=$('#dif').val();
		if(dif=="cause"){
			fWindowText1 = window.opener.document.getElementById("ipt_moid");
		}else{
			fWindowText1 = window.opener.document.getElementById("ipt_recoverMoid"); 
		}
	     fWindowText1.value = moid; 
	     window.opener.findMODeviceInfo(dif);
	     window.close();
	} 
}


function toConfirmSelect(){
	var moSource = $('#tblMODevice').datagrid('getSelected');
	if (moSource.moid != null&&moSource.moid !="") {
		showDeviceInfo(moSource.moid,moSource.moname);
    		$('#event_select_dlg').dialog('close');
	}else{
		$.messager.alert('提示', '没有任何选中项', 'error');
	} 
}