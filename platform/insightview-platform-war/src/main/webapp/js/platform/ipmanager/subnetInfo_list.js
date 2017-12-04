$(document).ready(function() {
	doInitTable();
	doInitLRselect();
});

//初始化导出列转移
function doInitLRselect() {
	$.fn.LRSelect("selLeft", "selRight", "img_L_AllTo_R", "img_L_To_R",
			"img_R_To_L", "img_R_AllTo_L");
	$.fn.UpDownSelOption("imgUp", "imgDown", "selRight");
	LRSelect.moveAll( "selLeft", "selRight", true);  
}

/*
 * 页面加载初始化表格 
 */
function doInitTable() {
	var path = getRootName();
	$('#tblSubnetInfoList')
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
						url : path + '/platform/subnetViewManager/listSubnetInfo',
						remoteSort : false,
						idField : 'fldId',
						singleSelect : false,// 是否单选
						checkOnSelect : false,
						selectOnCheck : true,
						pagination : true,// 分页控件
						rownumbers : true,// 行号
						toolbar : [ {
							'text' : '新增',
							'iconCls' : 'icon-add',
							handler : function() {
							doOpenAdd();
							}
						},
						{
							'text' : '删除',
							'iconCls' : 'icon-cancel',
							handler : function() {
								doBatchDel();
							}
						},
						{
							text : '导出',
							iconCls : 'icon-execl',
							handler : function() {
								toExport();
							}
						}],
						columns : [ [
								{
									 field : 'subNetId',
									 checkbox : true
								}, 
								{
									field : 'ipAddress',
									title : '网络地址',
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
									field : 'usedNum',
									title : '占用地址数',
									align : 'center',
									width : 70,
								},
//								{
//									field : 'preemptNum',
//									title : '预占地址数',
//									align : 'center',
//									width : 70,
//								},
								{
									field : 'freeNum',
									title : '空闲地址数',
									align : 'center',
									width : 70,
									formatter: function(value, row, index){
						                return row.preemptNum + row.freeNum
						            }
								},
								{
									field : 'totalNum',
									title : '子网容量',
									align : 'center',
									width : 70,
								},
								{
									field : 'broadCast',
									title : '广播地址',
									align : 'center',
									width : 100,
								},
								{
									field : 'subNetTypeName',
									title : '所属网络类型',
									align : 'center',
									width : 100,
								},
								{
									field : 'subNetIds',
									title : '操作',
									width : 100,
									align : 'center',
									formatter : function(value, row, index) {
										return '<a style="cursor: pointer;" onclick="javascript:doOpenDetail('
										+ row.subNetId
										+ ');"><img src="'
										+ path
										+ '/style/images/icon/icon_view.png" title="查看" /></a> &nbsp;<a style="cursor: pointer;" onclick="javascript:toOpenModify('
										+ row.subNetId
										+ ');"><img src="'
										+ path
										+ '/style/images/icon/icon_modify.png" title="编辑" /></a>&nbsp;<a style="cursor: pointer;" onclick="javascript:toDel('
										+ row.subNetId
										+ ');"><img src="'
										+ path
										+ '/style/images/icon/icon_delete.png" title="删除" /></a> &nbsp;<a style="cursor: pointer;" onclick="javascript:doOpenResolution('
										+ "&quot;" + row.subNetId
										+ "&quot;,&quot;" + row.ipAddress
										+ "&quot;,&quot;" + row.subNetMark
										+ "&quot;"
										+ ');"><img src="'
										+ path
										+ '/style/images/icon/splitsubnet.png" title="拆分" alt="拆分"/></a>';
									}
								} ] ]
					});
	 $(window).resize(function() {
	        $('#tblSubnetInfoList').resizeDataGrid(0, 0, 0, 0);
	    });
}

/**
 * 更新表格
 */
function reloadTable(){
	var startIp = $("#txtStartIp").val();
	var endIp = $("#txtEndIp").val();
	var deptName = $("#txtDeptName").val();
	var totalNum = $("#txtTotalNum").combobox('getValue');
//	console.log("startIp=="+startIp+",endIp="+endIp)
	if(totalNum != -1 && totalNum != null && totalNum != ""){
		if (!isNum(totalNum)) {
			$.messager.alert("提示","子网容量只能为正整数！","error"); 
			return;
		}
	}
	
	if(startIp != null && startIp !=""){
		if(!isIP(startIp)){
			$.messager.alert("提示","请输入有效的起始IP!","error"); 
			return ;
		}
	}
	if(endIp != null && endIp !=""){
		if(!isIP(endIp)){
			$.messager.alert("提示","请输入有效的终止IP!","error"); 
			return ;
		}
	}
	if(startIp != null && startIp !="" && endIp != null && endIp !=""){
		if (judge_ip(startIp) > judge_ip(endIp)) {
			$.messager.alert("提示","请输入有效的IP地址范围!","error"); 
			$("#endIp").focus();
			return;
		}
	}
	$('#tblSubnetInfoList').datagrid('options').queryParams = {
		"startIp" : startIp,
		"endIp" : endIp,
		"deptName" : deptName,
		"totalNum" : totalNum
	};
	reloadTableCommon_1('tblSubnetInfoList');
}

function isNum(totalNum){
	if (!(/^[0-9]*[1-9][0-9]*$/.test(totalNum))) {
		return false;
	}
	return true;
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


function reloadTableCommon_1(dataGridId) {
	$('#' + dataGridId).datagrid('load');
	$('#' + dataGridId).datagrid('uncheckAll');
}

/**
 * 打开查看页面
 */
function doOpenDetail(subNetId){
//	console.log("subNetId=="+subNetId);
	parent.parent.$('#popWin').window({
    	title:'子网详情',
        width:800,
        height : 400,
        minimizable:false,
        maximizable:false,
        collapsible:false,
        modal:true,
        href: getRootName() + '/platform/subnetViewManager/toShowSubnetDetail?subNetId='+subNetId
    });
}

/**
 * 弹出导出界面
 * @return
 */
function toExport() {
	$('#divExportAll').show();
	$('#divExportAll').dialog({
		title : '选择导出列',
		width : 400,
		height : 400,
		minimizable : false,
		maximizable : false,
		collapsible : false,
		modal : true
	}).dialog('open');
}

/**
 * 向上移动选中的option
 */
function upSelectedOption() {
	if (null == $('#selRight').val() && null == $('#selLeft').val()) {
		$.messager.alert("提示", "请选择一项!", "info");
		return false;
	}
	if($('#selRight').val() != null){
		// 选中的索引,从0开始
		var optionIndex = $('#selRight').get(0).selectedIndex;
		// 如果选中的不在最上面,表示可以移动
		if (optionIndex > 0) {
			$('#selRight option:selected').insertBefore(
					$('#selRight option:selected').prev('option'));
		}
	}
	if($('#selLeft').val() != null){
		// 选中的索引,从0开始
		var optionIndex = $('#selLeft').get(0).selectedIndex;
		// 如果选中的不在最上面,表示可以移动
		if (optionIndex > 0) {
			$('#selLeft option:selected').insertBefore(
					$('#selLeft option:selected').prev('option'));
		}
	}
}

/**
 * 向下移动选中的option
 */
function downSelectedOption() {
	if (null == $('#selRight').val() && null == $('#selLeft').val()) {
		$.messager.alert("提示", "请选择一项!", "info");
		return false;
	}
	if ($('#selRight').val() != null){
		// 索引的长度,从1开始
		var optionLength = $('#selRight')[0].options.length;
		// 选中的索引,从0开始
		var optionIndex = $('#selRight').get(0).selectedIndex;
		// 如果选择的不在最下面,表示可以向下
		if (optionIndex < (optionLength - 1)) {
			$('#selRight option:selected').insertAfter(
					$('#selRight option:selected').next('option'));
		}
	}
	if ($('#selLeft').val() != null){
		// 索引的长度,从1开始
		var optionLength = $('#selLeft')[0].options.length;
		// 选中的索引,从0开始
		var optionIndex = $('#selLeft').get(0).selectedIndex;
		// 如果选择的不在最下面,表示可以向下
		if (optionIndex < (optionLength - 1)) {
			$('#selLeft option:selected').insertAfter(
					$('#selLeft option:selected').next('option'));
		}
	}
}

/**
 * 导出全局数据
 * @return
 */
function doExport() {
	var exportInfoObj = {
		"selId" : "selRight",
		"colName" : "iptColName",
		"titleName" : "iptTitleName"
	};

	var expObject = doExportCommon(exportInfoObj);
	$("#frmExport").submit();
}

function doExportCommon(exportInfoObj) {
	var expColValueAttr = '';
	var expColTitleAttr = '';
	$("#" + exportInfoObj.selId + " option").each(function() {
		expColValueAttr += $(this).attr('alt') + ',';
		expColTitleAttr += $(this).val() + ',';
	});
	var expObject = {
		"valAttr" : expColValueAttr,
		"titleAttr" : expColTitleAttr
	};
	$("#" + exportInfoObj.colName).val(expColValueAttr);
	$("#" + exportInfoObj.titleName).val(expColTitleAttr);
	return expObject;
}

/**
 * 打开新增页面
 */
function doOpenAdd(){
	parent.parent.$('#popWin').window({
    	title:'新增子网',
        width:600,
        height : 400,
        minimizable:false,
        maximizable:false,
        collapsible:false,
        modal:true,
        href: getRootName() + '/platform/subnetViewManager/toShowSubnetAdd'
    });
}

/**
 * 删除子网
 */
function toDel(subNetId){
	$.messager.confirm("提示","确定删除此子网?",function(r){
		if (r == true) {
			var path = getRootName();
			var uri = path + "/platform/subnetViewManager/delSubNetInfo";
			var ajax_param = {
				url : uri,
				type : "post",
				datdType : "json",
				data : {
					"subNetId" : subNetId,
					"t" : Math.random()
				},
				error : function() {
					$.messager.alert("错误", "ajax_error", "error");
				},
				success : function(data) {
					if (true == data.flag || "true" == data.flag) {
						$.messager.alert("提示", "该子网删除成功！", "info");
						reloadTable();
						parent.window.initTree();
					} else if(true ==data.checkRS|| "true" == data.checkRS) {
						$.messager.alert("提示", "该子网中还有正在使用的地址，请收回至空闲后再删除该子网！", "error");
//						reloadTable();
//						parent.window.initTree();
					}else{
						$.messager.alert("提示", "数据库操作异常，该子网删除失败！", "error");
					}
				}
			};
			ajax_(ajax_param);
		}
	});
}

/**
 * 批量删除
 */
function doBatchDel(){
	var path=getRootName();
	var checkedItems = $('[name=subNetId]:checked');
	var ids = null;
	$.each(checkedItems, function(index, item) {
		if (null == ids) {
			ids = $(item).val();
		} else {
			ids += ',' + $(item).val();
		}
	});
	if (null != ids) {
		$.messager.confirm("提示","确定删除所选中项？",function(r){
			if (r == true) {
				var path = getRootName();
				var uri = path + "/platform/subnetViewManager/delSubNetInfos?subNetIds="+ids;
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
						if (true == data.flag || "true" == data.flag) {
							$.messager.alert("提示", "子网删除成功！", "info");
							reloadTable();
							parent.window.initTree();
						} else if(true ==data.rs|| "true" == data.rs) {
							$.messager.alert("提示", "下列被删子网中还有正在使用的地址，请收回至空闲后再删除该子网！"+data.subNetName, "info");
							reloadTable();
							parent.window.initTree();
						}else{
							$.messager.alert("错误", "数据库操作异常，子网删除失败！", "error");
						}
					}
				};
				ajax_(ajax_param);
			}
		});
	} else {
		$.messager.alert('提示', '没有任何选中项', 'error');
	}
}

function doOpenResolution(subNetId,ipAddress,subNetMark){
	parent.parent.$('#popWin').window({
    	title:'拆分子网',
        width:600,
        height : 400,
        minimizable:false,
        maximizable:false,
        collapsible:false,
        modal:true,
        href: getRootName() + '/platform/subnetViewManager/doOpenResolution?subNetId='+subNetId+'&ipAddress='+ipAddress+'&subNetMark='+subNetMark
    });
}

function doLoseTargetFocus(){
	var obj = document.getElementById("selRight");
	var index = obj.selectedIndex; // 选中索引
	if(obj != null && obj.options.size > 0 ){
		obj.options[index].selected = false;    
	}
}

function doLoseSourceFocus(){
	var obj = document.getElementById("selLeft");
	var index = obj.selectedIndex; // 选中索引
	if(obj != null && obj.options.size > 0 ){
		obj.options[index].selected = false;    
	}
}

/**
 * 打开编辑页面
 */
function toOpenModify(subNetId){
	parent.parent.$('#popWin').window({
    	title:'子网编辑',
        width:600,
        height : 400,
        minimizable:false,
        maximizable:false,
        collapsible:false,
        modal:true,
        href: getRootName() + '/platform/subnetViewManager/toShowSubnetmodify?subNetId='+subNetId
    });
}