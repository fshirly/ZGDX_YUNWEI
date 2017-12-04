$(document).ready(function() {	
	var flag=$('#flag').val();
	doInitTable();
	if(flag ==null || flag ==""){ 
		$('#tblDataList').datagrid('hideColumn','moID');
	}
});

/**
 * 加载页面并初始化表格
 * @return
 */
function doInitTable() {
	var mOClassID = $("#mOClassID").val();
	var path = getRootName();
	var uri = path + '/monitor/deviceManager/listDisc?mOClassID=' + mOClassID;
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
						    	   field : 'moID',
						    	   title : '',
						    	   width : 40,
						    	   align : 'center',
						    	   sortable:true,
						    	   formatter : function(value, row, index) {
						    	   			return '<a style="cursor: pointer;" onclick="javascript:sel('+ value+');">选择</a>';
						       	   }
						       },
								{
									field : 'rawDescr',
									title : '磁盘描述',
									width : 100,
									align : 'center'
								},
								{
									field : 'deviceIP',
									title : 'IP地址',
									width : 80,
									sortable:true,
									align : 'center'
								},
								{
									field : 'deviceMOName',
									title : '所属设备',
									width : 100,
									align : 'center'
								},
								{
									field : 'adminStatusName',
									title : '最近一次操作类型',
									width : 80,
									align : 'center'
								},								
								{
									field : 'diskSize',
									title : '磁盘大小',
									width : 80,
									align : 'center'
								},{
									field : 'isCollect',
									title : '是否采集',
									width : 80,
									align : 'center',
								   formatter : function(value, row, index) {
										 if((row.instance==row.sourcePort && row.ifOperStatus==1)|| value =="1"){
											 return '<input type="checkbox" name="selectRadio'+index+'" id="selectRadio' + index + '"  checked="checked"   onclick="doUpdateIfInfo('+row.moID+',this)" />是 ';
										 }else{
											 return '<input type="checkbox" name="selectRadio'+index+'" id="selectRadio' + index + '"   onclick="doUpdateIfInfo('+row.moID+',this)" />是 ';
										 }
							    }
								}
								] ]
					});
	$(window).resize(function() {
        $('#tblDataList').resizeDataGrid(0, 0, 0, 0);
    });
}
/**
 * 更新磁盘是否采集的状态
 * @param moid
 * @param ifType
 * @param id
 */
function doUpdateIfInfo(moid,id){
		if(id.checked){
			value=1;
		}else{
			value =0;
		}
	var path = getRootPatch();
	var uri=path+"/monitor/deviceManager/doUpdateDiskInfo"
	var ajax_param = {
			url : uri,
			type : "post",
			datdType : "json",
			data : {
				"moID" : moid,
				"isCollect":value,
				"t" : Math.random()
			},
			error : function() {
				$.messager.alert("错误", "ajax_error", "error");
			},
			success : function(data) {
				 if(data==true){
//					 $.messager.alert("提示","更新采集任务成功！","info");
				 }else{
					 $.messager.alert("提示","采集任务失败！","error");
					 id.checked=false;
				 }
			}
		};
		ajax_(ajax_param);
		
	}

/**
 * 刷新表格数据
 */
function reloadTable() {
	$('#tblDataList').datagrid('options').queryParams = {
		"mOClassID" : $("#mOClassID").val(),
		"deviceMOName" : $("#deviceMOName").val(),
		"deviceIP":$("#deviceMOName").val(),
		"rawDescr":$("#rawDescr").val()
	};
	reloadTableCommon_1('tblDataList');
}

function reloadTableCommon_1(dataGridId) {
	$('#' + dataGridId).datagrid('load');
	$('#' + dataGridId).datagrid('uncheckAll');
}