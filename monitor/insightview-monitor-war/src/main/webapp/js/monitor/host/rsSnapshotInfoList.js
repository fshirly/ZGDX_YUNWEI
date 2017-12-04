$(document).ready(function() {
	var liInfo = $("#liInfo").val();
	doSnapshotInitTables(liInfo);// 设备快照

	});

// 设备快照
function doSnapshotInitTables(liInfo) {
	var path = getRootName();
	var moClass = $("#moClass").val();
	$('#tblSnapshot')
			.datagrid(
					{
						iconCls : 'icon-edit',// 图标
						width : 900,
						height : 'auto',
						nowrap : false,
						striped : true,
						border : true,
						singleSelect : false,// 是否单选
						checkOnSelect : false,
						selectOnCheck : true,
						collapsible : false,// 是否可折叠的
						fit : true,// 自动大小
						fitColumns:true,
//						url : path + '/monitor/rsManage/getRsSnapshotInfo?moClass='+moClass,
						url : path + '/monitor/rsManage/queryRsSnapshotInfo?moClass='+moClass,
						idField : 'fldId',
						columns : [ [
								{
									field : 'resManufacturerName',
									title : '厂商类型',
									width : 150,
									align : 'center'
								},
								{
									field : 'alarmcount',
									title : '告警数量',
									width : 150,
									align : 'center',
									formatter : function(value, row, index) {
										var to = "&quot;" + row.moClassID
												+ "&quot;,&quot;" + row.neManufacturerID
												+ "&quot;"
										return '<a style="cursor: pointer;" onclick="javascript:toShowAlarmList('
										+ to
										+ ');">'+row.alarmCount+'</a>'; 
										}
								},
								{
									field : 'perfvalue',
									title : '设备数量',
									width : 150,
									align : 'center',
									formatter : function(value, row, index) {
										return "<font color='red'>"
												+ row.alarmDeviceCount
												+ "</font>/" +row.countDevice;
									}
								} ] ],
					onLoadSuccess: function(){   
						//自适应部件大小
						window.parent.resizeWidgetByParams(liInfo);
					} 
					});
					$(window).resize(function() {
					    $('#tblSnapshot').resizeDataGrid(0, 0, 0, 0);
					});
}
function toShowAlarmList(moclassid,nemanufacturerid){
	if(moclassid=="59,60,6")
	{
		moclassid =6;
	}
	var url=getRootName() +'/monitor/alarmActive/toAlarmActiveList?moClassID='+moclassid+'&neManufacturerID='+nemanufacturerid+'&host=snapsHost';
	var content = '<iframe id="alarmsInfo" name="alarmsInfo" scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';
	var title = '告警列表';
	var isExistTabs = parent.parent.document.getElementById("tabs_window");
	var isPartentTabs = parent.document.getElementById("tabs_window");
	if(isPartentTabs != null){
		parent.$('#tabs_window').tabs('add',{
	    title:title,
	    content:content,
	    closable:true
	       });
	}else if(isExistTabs != null){
		parent.parent.$('#tabs_window').tabs('add',{
		    title:title,
		    content:content,
		    closable:true
		    });
	}else{
		window.parent.frames.location = uri;
	}
}

