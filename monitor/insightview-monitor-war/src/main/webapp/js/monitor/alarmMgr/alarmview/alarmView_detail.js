$(document).ready(function() {	
	doInitTableColCfg();
	doInitTableSound();
	doInitTableFilter();
});

/**
 * 加载页面并初始化表格
 * @return
 */
function doInitTableColCfg() {
	var path = getRootName();
	var viewCfgID = $("#viewCfgID").val();
	var uri	= path + "/monitor/alarmView/listColCfg?viewCfgID="+viewCfgID;
	$('#colCfgtblDataList')
			.datagrid(
					{
						iconCls : 'icon-edit',// 图标
						width : 'auto',
						height : 'auto',
						nowrap : false,
						striped : true,
						border : true,
						collapsible : false,// 是否可折叠的
						url : uri,
						remoteSort : false,
						idField : 'fldId',//event主键
						fitColumns : true,
						singleSelect : false,// 是否单选
						checkOnSelect : true,
						selectOnCheck : true,
						pagination : true,// 分页控件
						rownumbers : true,// 行号						
						columns : [ [
									    {
											field : 'cfgID',
											title : '是否启用',
											width : 80,
											align : 'center',
											formatter : function(value, row, index) {
									    		if(value=="" || value==null){
									    			return "未启用";
									    		}else{
									    			return "<span style='color:blue;'>已启用</span>";
									    		}
											}
										},
										{
											field : 'colName',
											title : '列名',
											width : 100,
											align : 'center'
										},
										{
											field : 'colTitle',
											title : '标题',
											width : 100,
											align : 'center'									
										},
										{
											field : 'colWidth',
											title : '列宽（px）',
											width : 65,
											align : 'center'
										},
										{
											field : 'colOrder',
											title : '显示顺序',
											width : 65,
											align : 'center'
										},
										{
											field : 'colValueOrder',
											title : '排序序号',
											width : 65,
											align : 'center'
										},
										{
											field : 'isVisible',
											title : '是否显示',
											width : 65,
											align : 'center',
											formatter : function(value, row, index) {
												if(value==0){
													return "否";
												}else if(value==1){
													return "是";
												}else{
													return "";
												}
											}
										} ] ]
					});
	$(window).resize(function() {
        $('#colCfgtblDataList').resizeDataGrid(0, 0, 0, 0);
    });
}

/**
 * 加载页面并初始化表格
 * @return
 */
function doInitTableSound() {
	var path = getRootName();
	var viewCfgID = $("#viewCfgID").val();
	var uri = path + "/monitor/alarmView/listSound?viewCfgID="+viewCfgID;
	$('#soundtblDataList')
			.datagrid(
					{
						iconCls : 'icon-edit',// 图标
						width : 'auto',
						height : 'auto',
						nowrap : false,
						striped : true,
						border : true,
						collapsible : false,// 是否可折叠的
						url : uri,
						remoteSort : false,
						fitColumns : true,
						idField : 'fldId',//event主键
						singleSelect : false,// 是否单选
						checkOnSelect : true,
						selectOnCheck : true,
						pagination : true,// 分页控件
						rownumbers : true,// 行号						
						columns : [ [
								{
									field : 'alarmLevelName',
									title : '告警等级',
									width : 200,
									align : 'left'
								},
								{
									field : 'soundFileURL',
									title : '声音',
									width : 200,
									align : 'center'									
								},
								{
									field : 'loopTime',
									title : '时间（秒）',
									width : 80,
									align : 'center'
								}] ]
					});
	$(window).resize(function() {
        $('#soundtblDataList').resizeDataGrid(0, 0, 0, 0);
    });
}

/**
 * 加载页面并初始化表格
 * @return
 */
function doInitTableFilter() {
	var path = getRootName();
	var viewCfgID = $("#viewCfgID").val();
	var uri = path + "/monitor/alarmView/listFilter?viewCfgID="+viewCfgID;
	$('#filtertblDataList')
			.datagrid(
					{
						iconCls : 'icon-edit',// 图标
						width : 'auto',
						height : 'auto',						
						nowrap : false,
						striped : true,
						border : true,
						collapsible : false,// 是否可折叠的
						url : uri,
						remoteSort : false,
						idField : 'fldId',//event主键
						singleSelect : false,// 是否单选
						checkOnSelect : true,
						fitColumns : true,
						selectOnCheck : true,
						pagination : true,// 分页控件
						rownumbers : true,// 行号
						columns : [ [
								{
									field : 'filterKey',
									title : '过滤关键字',
									width : 200,
									align : 'left',
									formatter : function(value, row, index) {
										var rtnVal = "";
										if(value=="AlarmLevel"){
											rtnVal = "告警等级";
										}else if(value=="AlarmType"){
											rtnVal = "告警类型";
										}else if(value=="AlarmSourceMOID"){ 
											rtnVal = "告警源对象";
										}else if(value=="AlarmDefineID"){
											rtnVal = "告警事件";
										}
										return rtnVal;
									}
								},
								{
									field : 'filterValeName',
									title : '过滤值',
									width : 200,
									align : 'center'									
								} ] ]	
					});
	$(window).resize(function() {
        $('#filtertblDataList').resizeDataGrid(0, 0, 0, 0);
    });
}