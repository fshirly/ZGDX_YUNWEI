$(document).ready(function() {
	var liInfo = $("#liInfo").val();
	var moID = $("#moID").val();
	doInitTable(moID,liInfo);
});

/**
 * 加载页面并初始化表格
 * @return
 */
function doInitTable(moID,liInfo) {
	var path = getRootName();
	var uri = path + '/monitor/db2Manage/listDb2Database?moID='+moID;
	$('#tblDb2InfoList')
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
						remoteSort : true,
						idField : 'idField',
						singleSelect : false,// 是否单选
						checkOnSelect : false,
						selectOnCheck : true,
						columns : [ [
								{
									field : 'databaseStatus',
									title : '数据库状态',
									width : 80,
									align : 'center',
									formatter : function(value, row, index) {
										if(value == 0){ 
						        			return '<img src="'
											+ path
											+ '/style/images/levelIcon/up.png'
											+ '"/>'+"活跃";
										}else if(value == 1){ 
						        			return '<img src="'
											+ path
											+ '/style/images/levelIcon/3.png'
											+ '"/>'+"停顿－暂挂";
										}else if(value == 2){ 
						        			return '<img src="'
											+ path
											+ '/style/images/levelIcon/unknown.png'
											+ '"/>'+"已停顿";
										}else if(value == 3){ 
						        			return '<img src="'
											+ path
											+ '/style/images/levelIcon/4.png'
											+ '"/>'+"正在前滚";
										}
									}
								},
								{
									field : 'databaseName',
									title : '数据库名称',
									width : 80,
									align : 'center'
								 },
								 {
									field : 'moalias',
									title : '数据库别名',
									width : 80,
									align : 'center'
								 },
								{
									field : 'databasePath',
									title : '数据库路径',
									width : 150,
									align : 'center'
								},
								{
									field : 'formatTime',
									title : '连接时间',
									width : 100,
									align : 'center'
								}
								] ]
								/*,onLoadSuccess: function(){   
									//自适应部件大小
									window.parent.resizeWidgetByParams(liInfo);
								} */  
					});
	$(window).resize(function() {
        $('#tblDb2InfoList').resizeDataGrid(0, 0, 0, 0);
    });
}


