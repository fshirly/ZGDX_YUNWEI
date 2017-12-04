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
	var uri = path + '/monitor/DeviceTomcatManage/listMiddlewareJvm?parentMoId=' + parentMoId+'&jmxType='+jmxType;
	$('#tblDataList')
			.datagrid(
					{
						width : 'auto',
						height : 'auto',
						nowrap : true,
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
						    	   width : 50,
						    	   align : 'center',
						    	   formatter : function(value, row, index) {
								    	   var to = "&quot;" + value
											+ "&quot;,&quot;" + row.jvmName
											+ "&quot;"
						    	   			return '<a style="cursor: pointer;" onclick="javascript:sel('+ to+');">选择</a>';
						       	   }
						       },
								{
									field : 'jvmName',
									title : '虚拟机名称',
									width : 100,
									align : 'center'
								 },
								{
									field : 'ip',
									title : '服务IP',
									width : 90,
									sortable:true,
									align : 'center'
								 },
								{
									field : 'jvmVendor',
									title : '虚拟机厂商',
									width : 80,
									align : 'center'
								},
								{
									field : 'jvmVersion',
									title : '虚拟机版本',
									width : 60,
									align : 'center'
								},
								{
									field : 'javaVendor',
									title : 'Java厂商',
									width : 100,
									align : 'center'
								},
								{
									field : 'javaVersion',
									title : 'Java版本',
									width : 100,
									align : 'center'
								},
								{
									field : 'oSName',
									title : 'OS名称',
									width : 50,
									align : 'center'
								},
								{
									field : 'oSVersion',
									title : 'OS版本',
									width : 50,
									align : 'center'
								},
								{
									field : 'upTime',
									title : '已运行时间',
									width : 70,
									align : 'center'
								},
								{
									field : 'heapSizeMax',
									title : '堆内存最大值',
									width : 70,
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
		"jvmName" : $("#jvmName").val(),
		"ip" : $("#ip").val(),
	};
	reloadTableCommon_1('tblDataList');
}

function reloadTableCommon_1(dataGridId) {
	$('#' + dataGridId).datagrid('load');
	$('#' + dataGridId).datagrid('uncheckAll');
}

function sel(moid){
	if(window.opener) { 
	     fWindowText1 = window.opener.document.getElementById("ipt_middlewareJvmId"); 
	     fWindowText1.value = moid; 
	     window.opener.findMiddlewareJvmInfo();
	     window.close();
	} 
}  