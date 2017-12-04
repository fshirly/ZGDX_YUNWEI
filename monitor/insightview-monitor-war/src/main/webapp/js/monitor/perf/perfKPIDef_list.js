$(document).ready(function() {
	var flag=$('#flag').val();
	doInitTable();
	if(flag=='choose'){ 
		$('#tblPerfKPIDef').datagrid('showColumn','kpiID');
	}else{
		$('#tblPerfKPIDef').datagrid('hideColumn','kpiID');
	}
	$('#txtKPICategory').combobox({
		panelHeight : '120',
		url : getRootName() + '/monitor/perfKPIDef/getAllCategory',
		valueField : 'kpiCategory',
		textField : 'kpiCategory',
		editable : false
	});
});

/**
 * 加载页面并初始化表格
 * @return
 */
function doInitTable(){
	var classID = $("#classID").val();
	if(classID == null || classID == "null" || classID == ""){
		classID = -1;
	}
	var path = getRootName();
	$('#tblPerfKPIDef')
			.datagrid(
					{ 
						iconCls : 'icon-edit',// 图标
						width : 700,
						height : 'auto',
						nowrap : false,
						striped : true,
						border : true,
						collapsible : false,// 是否可折叠的
						fit:true,// 自动大小
						fitColumns:true,
						url : path + '/monitor/alarmmgr/moKPIThreshold/selectPerfKPIDef',
						remoteSort : true,
						onSortColumn:function(sort,order){
							// alert("sort:"+sort+",order："+order+"");
						},
						queryParams: {
							"classID" : classID,
						},
						idField : 'fldId',
						singleSelect : false,// 是否单选
						checkOnSelect : false,
						selectOnCheck : true,
						pagination : true,// 分页控件
						rownumbers : true,// 行号
						columns : [ [
						        {
									field : 'kpiID',
									title : '',
									width : 20,
									align : 'center',
									formatter : function(value, row, index) {
										return '<a style="cursor: pointer;" onclick="javascript:sel('
												+ value
												+ ');">选择</a>';
									}
						        },
								{  
									field : 'name',
									title : '指标名称',  
									width : 100,
									align : 'center',
									sortable : true,
									formatter : function(value, row, index) {
						        		if(value && value.length > 10){
						        		value2 = value.substring(0,25) + "...";
										 return '<span title="' + value + '" style="cursor: pointer;" >' +value2+'</span>';
							        	}else{
											return value;
										}
									}
								},
								{
									field : 'kpiCategory',
									title : '指标类别',
									width : 100,
									align : 'center',
									sortable : true,
									formatter : function(value, row, index) {
						        		if(value && value.length > 10){
						        		value2 = value.substring(0,25) + "...";
										 return '<span title="' + value + '" style="cursor: pointer;" >' +value2+'</span>';
							        	}else{
											return value;
										}
									}
								},
								{
									field : 'quantifier',
									title : '单位', 
									width : 60,
									align : 'center',
									sortable : true,
									formatter : function(value, row, index) {
						        		if(value && value.length > 10){
						        		value2 = value.substring(0,15) + "...";
										 return '<span title="' + value + '" style="cursor: pointer;" >' +value2+'</span>';
							        	}else{
											return value;
										}
									}
								}]]
					});
	$(window).resize(function() {
	    $('#tblPerfKPIDef').resizeDataGrid(0, 0, 0, 0);
	});
}
/*
 * 更新表格
 */
function reloadTable(){
	var classID = $("#classID").val();
	if(classID == null || classID == "null" || classID == ""){
		classID = -1;
	}
	var name = $("#txtName").val();
	var kpiCategory = $("#txtKPICategory").combobox("getValue");
	$('#tblPerfKPIDef').datagrid('options').queryParams = {
		"name" : name,
		"classID" : classID,
		"kpiCategory" :kpiCategory,
	};
	reloadTableCommon_1('tblPerfKPIDef');
}

function reloadTableCommon_1(dataGridId) {
	$('#' + dataGridId).datagrid('load');
	$('#' + dataGridId).datagrid('uncheckAll');
}

function sel(moid){
	if(window.opener) { 
		 fWindowText1 = window.opener.document.getElementById("ipt_kpiID");
	     fWindowText1.value = moid; 
	     window.opener.findPerfKPIDefInfo();
	     window.close();
	} 
}
