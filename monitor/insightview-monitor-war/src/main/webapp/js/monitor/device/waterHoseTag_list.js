$(document).ready(function() {
	var flag=$('#flag').val();
	doInitTable();
	if(flag ==null || flag ==""){ 
		$('#tblTagList').datagrid('hideColumn','moID');
	}else{
		$('#tblTagList').datagrid('hideColumn','moIDs');
	}
});

/**
 * 加载页面并初始化表格
 * @return
 */
function doInitTable() {
	var flag=$('#flag').val();
	var moClassId=$('#moClassId').val();
	var path = getRootName();
	var uri = path + '/monitor/envManager/listWaterHoseTag?moClassId='+moClassId;
	$('#tblTagList')
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
						pagination : true,// 分页控件
						rownumbers : true,// 行号	
						columns : [ [
								{
								    field : 'moID',
								    title : '',
								    width : 100,
								    align : 'center',
									formatter : function(value, row, index) {
										return '<a style="cursor: pointer;" onclick="javascript:sel('+ value+');">选择</a>';
									}
								},
								{
									field : 'tagID',
									title : '标签ID',
									width : 80,
									align : 'center',
								 
								 },
								{
									field : 'ipAddress',
									title : '阅读器IP',
									sortable:true,
									width : 60,
									align : 'center'
								/*	formatter : function(value, row, index) {
					        			if(row.moClassId != 0 && (flag ==null || flag =="")){
					        				var to = "&quot;" + row.deviceMoid
											+ "&quot;,&quot;" + value
											+ "&quot;"
					        				return '<a style="cursor: pointer;" onclick="javascript:toShowView('
											+ to
											+ ');">'+value+'</a>'; 
						        		}else{
						        			return value;
						        		}
								}*/
								},
								{
									field : 'lowBattery',
									title : '电池电量',
									width : 60,
									align : 'center',
									formatter : function(value, row, index) {
										if (value == "true") {
											return "低电量";
										}
										return "正常电量";
									}
								 
								 },
								{
									field : 'fluidDetected',
									title : '流体检测',
									width : 60,
									align : 'center',
									formatter : function(value, row, index) {
										if (value == "true") {
											return "湿的";
										}
										return "干的";
									}
								},
								{
									field : 'tamper',
									title : '篡改检测',
									width : 80,
									align : 'center',
									formatter : function(value, row, index) {
										if (value == "true") {
											return "检测到篡改";
										}
										return "没检测到篡改";
									}
								},
								{
									field : 'sensorDisconnected',
									title : '传感器',
									width : 60,
									align : 'center',
									formatter : function(value, row, index) {
										if (value == "true") {
											return "断开";
										}
										return "连接";
									}
								}/*,
								{
									field : 'moids',
									title : '操作',
									width : 80,
									align : 'center',
									formatter : function(value,row,index){
									 var to = "&quot;" + row.moID
										+ "&quot;,&quot;" + row.deviceIP
										+ "&quot;"
									 return ' <a href="javascript:doRediscover('+to+');"  class="fltrt"><img src =" '+path+'/style/images/icon/icon_refresh.png" title="重新发现" /></a>&nbsp;'
									 	+'<a style="cursor:pointer;" title="配置" onclick="javascript:toSet('
										+ to
										+ ');"><img src =" '+path+'/style/images/icon/icon_setting.png" alt="配置" /></a>';	
									}
								}*/
								] ]
					});
	$(window).resize(function() {
        $('#tblTagList').resizeDataGrid(0, 0, 0, 0);
    });
}

/**
 * 刷新表格数据
 */
function reloadTable() {
	$('#tblTagList').datagrid('options').queryParams = {
		"tagID" : $("#tagID").val()
	};
	reloadTableCommon_1('tblTagList');
}

function reloadTableCommon_1(dataGridId) {
	$('#' + dataGridId).datagrid('load');
	$('#' + dataGridId).datagrid('uncheckAll');
}

/**
 * 重新发现
 * @param moid
 * @return
 */
function doRediscover(moid,deviceip){
//	console.log("deviceip==="+deviceip+")
	var moClassId = 44;
	$.messager.confirm("提示","确定要重新发现?",function(r){
		if (r == true) {
			var path = getRootPatch();
			var uri = path + "/monitor/deviceManager/doRediscover?moClassId="+moClassId+"&deviceip="+deviceip;
			var ajax_param = {
				url : uri,
				type : "post",
				datdType : "json",
				data : {
					"t" : Math.random()
				},
				error : function() {
					$.messager.alert("错误", "ajax_error", "error"); 
				},
				success : function(data) {
					if(true == data.flag || "true" == data.flag){
		//				console.log("data===="+data)
						var taskid = data.taskid;
						showDeviceTask(taskid,moid,deviceip);
					}
				}
			}
			ajax_(ajax_param);
		}
	});
}

/**
 * 跳转至设备任务界面
 * @return
 */
function showDeviceTask(taskid,moid,deviceip){
	var moClassId = 44;
	parent.parent.$('#popWin').window({
		title:'设备任务',
	    width:800,
	    height:300,
	    minimizable:false,
	    maximizable:false,
	    collapsible:false,
	    modal:true,
	    href: getRootName() + '/monitor/deviceManager/showDeviceTask?taskid='+taskid+'&moid='+moid+'&deviceip='+deviceip+'&moClassId='+moClassId
	});
}

/**
 * 打开采集配置
 * @param moid
 * @param deviceip
 * @param moClassId
 * @param nemanufacturername
 * @param taskId
 * @return
 */
function toSet(moid,deviceip,dbmstype){
	var moClassId = 44;
	parent.parent.$('#popWin').window({
	title:'采集设备配置',
    width:800,
    height:500,
    minimizable:false,
    maximizable:false,
    collapsible:false,
    modal:true,
    href: getRootName() + '/monitor/configObjMgr/toSetMonitor?moid='+moid+'&deviceip='+deviceip+'&moClassId='+moClassId
});
}

/**
 * 跳转至CMDB界面
 * @return
 */
function showCMDB(){
	var rows  = $('#tblTagList').datagrid('getSelections');
	var flag = null;
	if(rows.length>0){
		var moClassId = rows[0].moClassId;
		var ids=rows[0].moid;
		for(var i=1; i<rows.length; i++){
			ids+=',' +rows[i].moid;
			if( moClassId != rows[i].moClassId){
				flag = "1";
			}		
		}
		if(flag == null){
				parent.parent.$('#popWin').window({
				title:'资源分类',
			    width:700,
			    height:400,
			    minimizable:false,
			    maximizable:false,
			    collapsible:false,
			    modal:true,
			    href: getRootName() + '/monitor/envManager/showCmdb?moClassId='+moClassId+"&moids="+ids
			});
		}else{
			$.messager.alert('提示', '设备类型不一致', 'error');
		}
	}else{
		$.messager.alert('提示', '没有任何选中项', 'error');
	}	
}


function sel(moid){
	if(window.opener) { 
	     fWindowText1 = window.opener.document.getElementById("ipt_moTagMoId"); 
	     fWindowText1.value = moid; 
	     window.opener.findMOTagInfo();
	     window.close();
	} 
} 