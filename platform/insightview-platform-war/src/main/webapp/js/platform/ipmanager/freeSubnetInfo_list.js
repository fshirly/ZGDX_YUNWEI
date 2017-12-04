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
	var subNetIdStr = $("#subNetId").val();
	var subNetId =parseInt(subNetIdStr);
	var path = getRootName();
	$('#tblFreeSubnetInfoList')
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
						url : path + '/platform/subnetViewManager/listFreeSubnetInfo',
						remoteSort : false,
						idField : 'fldId',
						singleSelect : false,// 是否单选
						checkOnSelect : false,
						selectOnCheck : true,
						pagination : true,// 分页控件
						rownumbers : true,// 行号
						queryParams : {
							"subNetId" : subNetId
						},
						toolbar : [ {
							'text' : '批量分配到部门',
							'iconCls' : 'icon-batchassigntodept',
							handler : function() {
								doBatchAssign();
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
									field : 'gateway',
									title : '网关',
									align : 'center',
									width : 100,
								},
								{
									field : 'dnsAddress',
									title : 'DNS',
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
									field : 'note',
									title : '备注',
									align : 'center',
									width : 130,
									formatter : function(value, row, index) {
										if(value == "" || value == null){
											return "-";
										}else {
											return value;
										}
									}
								},
								{
									field : 'ids',
									title : '操作',
									width : 100,
									align : 'center',
									formatter : function(value, row, index) {
										return '<a style="cursor: pointer;" onclick="javascript:toAssignDept('
											+ row.id
											+ ');"><img src="'
											+ path
											+ '/style/images/icon/assigntodept.png" title="分配到部门" alt="分配到部门" /></a>';
									}
								} ] ]
					});
	 $(window).resize(function() {
	        $('#tblFreeSubnetInfoList').resizeDataGrid(0, 0, 0, 0);
	    });
}

/**
 * 更新表格
 */
function reloadTable(){
	var startIp = $("#txtStartIp").val();
	var endIp = $("#txtEndIp").val();
	var subNetId = $("#subNetId").val();
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
			$("#endIp").focus();
			return;
		}
	}
	$('#tblFreeSubnetInfoList').datagrid('options').queryParams = {
		"startIp" : startIp,
		"endIp" : endIp,
		"subNetId":subNetId
	};
	reloadTableCommon_1('tblFreeSubnetInfoList');
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
 * 弹出导出界面
 * @return
 */
function toExport() {
	$('#divExportFree').dialog({
		title : '选择导出列',
		width : 400,
		height : 330,
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
	var subNetId = $("#subNetId").val();
	$("#belongSubnetId").val(subNetId);
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
 * 打开分配到部门页面
 */
function toAssignDept(id){
	var subNetId = $("#subNetId").val();
	parent.parent.$('#popWin').window({
    	title:'分配到部门',
        width:400,
        height : 200,
        minimizable:false,
        maximizable:false,
        collapsible:false,
        modal:true,
        href: getRootName() + '/platform/subnetViewManager/toAssignDept?ids='+id+'&subNetId='+subNetId
    });
}


/**
 * 批量分配
 */
function doBatchAssign(){
	var subNetId = $("#subNetId").val();
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
		ids = ids + ",";
		$.messager.confirm("提示","确定分配所选中项？",function(r){
			if (r == true) {
				parent.parent.$('#popWin').window({
			    	title:'分配到部门',
			        width:400,
			        height : 200,
			        minimizable:false,
			        maximizable:false,
			        collapsible:false,
			        modal:true,
			        href: getRootName() + '/platform/subnetViewManager/toAssignDept?ids='+ids+'&subNetId='+subNetId
			    });
			}
		});
	} else {
		$.messager.alert('提示', '没有任何选中项', 'error');
	}
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