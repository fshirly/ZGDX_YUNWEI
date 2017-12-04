
$(document).ready(function() {	
	var flag=$('#flag').val();
	doInitTable();
	if(flag ==null || flag ==""){ 
		$('#tblDataList').datagrid('hideColumn','moId');
	}
});

/**
 * 加载页面并初始化表格
 * @return
 */
function doInitTable() {
	var parentMoId = $("#parentMoId").val();
	var jmxType = $("#jmxType").val();
	var flag = $("#flag").val();
	var path = getRootName();
	var uri = path + '/monitor/DeviceTomcatManage/listMiddleClassLoad?parentMoId=' + parentMoId+'&jmxType='+jmxType;
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
										return '<a style="cursor: pointer;" onclick="javascript:sel('+ value+');">选择</a>';
									}
								 },
								 {
									field : 'serverName',
									title : '应用服务名称',
									width : 80,
									align : 'center',
								},
								 {
									field : 'ip',
									title : '服务IP',
									width : 90,
									sortable:true,
									align : 'center',
								},
								 {
									field : 'loadedClasses',
									title : '当前加载类数',
									width : 80,
									align : 'center',
								},
								{
									field : 'unloadedClasses',
									title : '已卸载类总数',
									width : 80,
									align : 'center'
								},
								{
									field : 'totalLoadedClasses',
									title : '已加载类总数',
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
		"mOClassID" : $("#mOClassID").val(),
		"serverName" : $("#serverName").val(),
		"ip" : $("#ip").val()
	};
	reloadTableCommon_1('tblDataList');
}

function reloadTableCommon_1(dataGridId) {
	$('#' + dataGridId).datagrid('load');
	$('#' + dataGridId).datagrid('uncheckAll');
}

function sel(moid){
	if(window.opener) { 
	     fWindowText1 = window.opener.document.getElementById("ipt_classLoadId"); 
	     fWindowText1.value = moid; 
	     window.opener.findClassLoadInfo();
	     window.close();
	} 
}  
