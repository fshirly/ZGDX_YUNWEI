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
	var uri = path + '/monitor/DeviceTomcatManage/listMiddlewareMemPool?parentMoId=' + parentMoId+'&jmxType='+jmxType;
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
											+ "&quot;,&quot;" + row.memName
											+ "&quot;"
						    	   			return '<a style="cursor: pointer;" onclick="javascript:sel('+ to+');">选择</a>';
						       	   }
						       },
								{
									field : 'memoryUsage',
									title : '内存使用百分比',
									width : 100,
									align : 'center',
									formatter:function(value,row){
						    	   		var color = (value <= 75) ? '#00ff00' : (value >= 90 ? '#ff0000' : '#FFFF33');
						    	   			return '<div class="easyui-progressbar" color="'
											+ color
											+ '" value="'
											+ value
											+ '" style="width:100px;height:15px;"></div>';
									} 
								 },
								{
									field : 'memName',
									title : '名称',
									width : 100,
									align : 'center'
								},
								{
									field : 'serverName',
									title : '服务名称',
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
									field : 'port',
									title : '端口',
									width : 60,
									align : 'center'
								},
								{
									field : 'memType',
									title : '类型',
									width : 80,
									align : 'center'
								},
								/**,
								{
									field : 'mGRName',
									title : '内存管理器名称',
									width : 100,
									align : 'center'
								}*/
								
								{
									field : 'initSize',
									title : '初始大小',
									width : 80,
									align : 'center'
								},
								{
									field : 'memoryFree',
									title : '空闲内存',
									width : 80,
									align : 'center'
								},
								{
									field : 'maxSize',
									title : '最大值',
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
        $('#tblDataList').resizeDataGrid(0, 0, 0, 0);
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

/**
 * 刷新表格数据
 */
function reloadTable() {
	$('#tblDataList').datagrid('options').queryParams = {
		"memName" : $("#memName").val(),
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
	     fWindowText1 = window.opener.document.getElementById("ipt_memPoolId"); 
	     fWindowText1.value = moid; 
	     window.opener.findMemPoolInfo();
	     window.close();
	} 
} 