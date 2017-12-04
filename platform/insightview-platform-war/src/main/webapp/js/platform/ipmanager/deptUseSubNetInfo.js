$(document).ready(function() {
	var deptId = $("#deptId").val();
	$('#txtSubNetId').combobox({
		panelHeight : '120',
		url : getRootName() + '/platform/deptViewManager/getAllSubNet?deptId='+deptId,
		valueField : 'subNetId',
		textField : 'ipAddress',
		editable : false,
	});
	doInitTable();
});

/*
 * 页面加载初始化表格 
 */
function doInitTable() {
	var deptId = $("#deptId").val();
	var path = getRootName();
	$('#tblDeptUseSubNetList')
			.datagrid(
					{
						iconCls : 'icon-edit',// 图标
						width : 700,
						height : 'auto',
						nowrap : false,
						striped : true,
						border : true,
						collapsible : false,// 是否可折叠的
						fit : true,// 自动大小
						fitColumns:true,
						url : path + '/platform/deptViewManager/listDeptUseSubNet?deptId='+deptId,
						remoteSort : false,
						idField : 'fldId',
						singleSelect : false,// 是否单选
						checkOnSelect : false,
						selectOnCheck : true,
						pagination : true,// 分页控件
						rownumbers : true,// 行号
						toolbar : [ {
							'text' : '批量分配到设备',
							'iconCls' : 'icon-batchassigntitodevice',
							handler : function() {
								doBatchGiveToDevice();
							}
						},
						{
							'text' : '批量收回到部门',
							'iconCls' : 'icon-batchwithdrawtodept',
							handler : function() {
								doBatchWithdrawToDept();
							}
						}],
						columns : [ [
								{
									 field : 'id',
									 checkbox : true
								}, 
								{
									field : 'ipAddress',
									title : 'IP地址',
									align : 'center',
									width : 100,
								},
								{
									field : 'subNetMark',
									title : '子网掩码',
									align : 'center',
									width : 100,
								},
								{
									field : 'status',
									title : '状态',
									align : 'center',
									width : 70,
									formatter : function(value, row, index) {
										if(value == 2){
											return "预占";
										}else if(value == 3){
											return "占用";
										}
									}
								},
								{
									field : 'note',
									title : '备注',
									align : 'center',
									width : 100,
									formatter : function(value, row, index) {
										if(value == "" || value == null){
											return "-";
										}else {
											return value;
										}
									}
								},
								{
									field : 'subNetIds',
									title : '操作',
									width : 100,
									align : 'center',
									formatter : function(value, row, index) {
									//预占状态(2) 显示分配操作，占用状态(3)显示收回操作
									if(row.status == 3){
										return '<a style="cursor: pointer;" onclick="javascript:doWithdrawToDept('
										+ "&quot;" + row.id
										+ "&quot;,&quot;" + row.subNetId
										+ "&quot;,&quot;" + row.deptId
										+ "&quot;"
										+ ');"><img src="'
										+ path
										+ '/style/images/icon/withdrawtodept.png" title="收回到部门" alt="收回到部门" /></a>&nbsp;<a style="cursor: pointer;" onclick="javascript:doOpenModify('
										+ row.id
										+ ');"><img src="'
										+ path
										+ '/style/images/icon/icon_modify.png" title="修改" />';
									}else{
										return '<a style="cursor: pointer;" onclick="javascript:doGiveToDevice('
										+ "&quot;" + row.id
										+ "&quot;,&quot;" + row.subNetId
										+ "&quot;,&quot;" + row.deptId
										+ "&quot;"
										+ ');"><img src="'
										+ path
										+ '/style/images/icon/assigntodevice.png" title="分配到设备" alt="分配到设备"/></a>';
									}
										
									}
								} ] ]
					});
	 $(window).resize(function() {
	        $('#tblDeptUseSubNetList').resizeDataGrid(0, 0, 0, 0);
	    });
}

/**
 * 更新表格
 */
function reloadTable(){
	var startIp = $("#txtStartIp").val();
	var endIp = $("#txtEndIp").val();
	var subNetId = $("#txtSubNetId").combobox('getValue');
	var status = $("#txtStatus").combobox('getValue');
//	console.log("startIp=="+startIp+",endIp="+endIp+"，subNetId=="+subNetId+",status="+status);
	if(startIp != null && startIp !=""){
		if(!isIP(startIp)){
			$.messager.alert("提示","请输入有效的起始IP!","error"); 
			return 
		}
	}
	if(endIp != null && endIp !=""){
		if(!isIP(endIp)){
			$.messager.alert("提示","请输入有效的终止IP!","error"); 
			return 
		}
	}
	if(startIp != null && startIp !="" && endIp != null && endIp !=""){
		if (judge_ip(startIp) > judge_ip(endIp)) {
			$.messager.alert("提示","请输入有效的IP地址范围!","error"); 
			return;
		}
	}
	$('#tblDeptUseSubNetList').datagrid('options').queryParams = {
		"startIp" : startIp,
		"endIp" : endIp,
		"subNetId" : subNetId,
		"status" : status
	};
	$('#tblDeptUseSubNetList').datagrid('load');
	$('#tblDeptUseSubNetList').datagrid('uncheckAll');
}

/**
 * 判断是否为合法IP
 */
function isIP(strIP) {
	var re = /^(\d+)\.(\d+)\.(\d+)\.(\d+)$/g //匹配IP地址的正则表达式
	if (re.test(strIP)) {
		if (RegExp.$1 < 256 && RegExp.$2 < 256 && RegExp.$3 < 256
				&& RegExp.$4 < 256)
			return true;
	}
	return false;
}

/**
 * IP地址转换为整数
 */
function judge_ip(ip){
	var num = 0;  
    ip = ip.split(".");  
    num = Number(ip[0]) * 256 * 256 * 256 
    	+ Number(ip[1]) * 256 * 256 + Number(ip[2]) * 256 + Number(ip[3]);  
    num = num >>> 0;  
    return num;
}

/**
 * 分配到设备
 */
function doGiveToDevice(id,subNetId,deptId){
	$.messager.confirm("提示","确定将该IP地址分配到设备?",function(r){
		if (r == true) {
			var path = getRootName();
			var uri = path + "/platform/deptViewManager/doGiveToDevice";
			var ajax_param = {
				url : uri,
				type : "post",
				datdType : "json",
				data : {
					"id" : id,
					"subNetId" : subNetId,
					"deptId" : deptId,
					"t" : Math.random()
				},
				error : function() {
					$.messager.alert("错误", "ajax_error", "error");
				},
				success : function(data) {
					if (data == true) {
						$.messager.alert("提示", "分配到设备成功！", "info");
						reloadTable();
						parent.window.initTree();
					} else {
						$.messager.alert("提示", "分配到设备失败！", "error");
					}
				}
			};
			ajax_(ajax_param);
		}
	});
}

/**
 * 收回到部门
 */
function doWithdrawToDept(id,subNetId,deptId){
	$.messager.confirm("提示","确定将该IP地址收回到部门?",function(r){
		if (r == true) {
			var path = getRootName();
			var uri = path + "/platform/deptViewManager/doWithdrawToDept";
			var ajax_param = {
				url : uri,
				type : "post",
				datdType : "json",
				data : {
					"id" : id,
					"subNetId" : subNetId,
					"deptId" : deptId,
					"t" : Math.random()
				},
				error : function() {
					$.messager.alert("错误", "ajax_error", "error");
				},
				success : function(data) {
					if (data == true) {
						$.messager.alert("提示", "收回到部门成功！", "info");
						reloadTable();
						parent.window.initTree();
					} else {
						$.messager.alert("提示", "收回到部门失败！", "error");
					}
				}
			};
			ajax_(ajax_param);
		}
	});
}

/**
 * 批量分配到设备
 */
function doBatchGiveToDevice(){
	var checkedItems = $('[name=id]:checked');
	var ids = null;
	$.each(checkedItems, function(index, item) {
		if (null == ids) {
			ids = $(item).val();
		} else {
			ids += ',' + $(item).val();
		}
	});
	if (null != ids) {
		$.messager.confirm("提示","确定分配所选中项？",function(r){
			if (r == true) {
				var path = getRootName();
				var uri = path + "/platform/deptViewManager/doBatchGiveToDevice?ids="+ids;
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
						if (true == data.updateFlag || "true" == data.updateFlag) {
							$.messager.alert("提示", "分配到设备成功！", "info");
						} else if(true ==data.isReserve|| "true" == data.isReserve) {
							$.messager.alert("提示", "下列IP地址已经占用，不能分配！"+data.ipAddress, "info");
						}else{
							$.messager.alert("错误", "数据库操作异常，分配到设备失败！", "error");
						}
						reloadTable();
						parent.window.initTree();
					}
				};
				ajax_(ajax_param);
			}
		});
	} else {
		$.messager.alert('提示', '没有任何选中项', 'error');
	}
}


/**
 * 批量收回到部门
 */
function doBatchWithdrawToDept(){
	var checkedItems = $('[name=id]:checked');
	var ids = null;
	$.each(checkedItems, function(index, item) {
		if (null == ids) {
			ids = $(item).val();
		} else {
			ids += ',' + $(item).val();
		}
	});
	if (null != ids) {
		$.messager.confirm("提示","确定分配所选中项？",function(r){
			if (r == true) {
				var path = getRootName();
				var uri = path + "/platform/deptViewManager/doBatchWithdrawToDept?ids="+ids;
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
						if (true == data.updateFlag || "true" == data.updateFlag) {
							$.messager.alert("提示", "收回到部门成功！", "info");
						} else if(true ==data.isReserve|| "true" == data.isReserve) {
							$.messager.alert("提示", "下列IP地址已经是预占状态，不能收回！"+data.ipAddress, "info");
						}else{
							$.messager.alert("错误", "数据库操作异常，收回到部门失败！", "error");
						}
						reloadTable();
						parent.window.initTree();
					}
				};
				ajax_(ajax_param);
			}
		});
	} else {
		$.messager.alert('提示', '没有任何选中项', 'error');
	}
}

/*
 * 打开编辑页面
 */
function doOpenModify(id){
	parent.parent.$('#popWin').window({
    	title:'ip地址编辑',
        width:600,
        height : 350,
        minimizable:false,
        maximizable:false,
        collapsible:false,
        modal:true,
        href: getRootName() + '/platform/deptViewManager/toShowIPaddrModify?id='+id
    });
}