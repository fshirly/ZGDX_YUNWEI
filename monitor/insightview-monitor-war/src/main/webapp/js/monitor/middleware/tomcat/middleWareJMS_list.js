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
	var uri = path + '/monitor/DeviceTomcatManage/listMiddleWareJMS?parentMoId=' + parentMoId+'&jmxType='+jmxType;
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
									field : 'name',
									title : 'JMS名称',
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
									field : 'status',
									title : '状态',
									width : 80,
									sortable:true,
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
		"name" : $("#name").val(),
		"ip" : $("#ip").val()
	};
	reloadTableCommon('tblDataList');
}

function sel(moid){
	if(window.opener) { 
	     fWindowText1 = window.opener.document.getElementById("ipt_middleJMSId"); 
	     fWindowText1.value = moid; 
	     window.opener.findMiddleJMSInfo();
	     window.close();
	} 
}  