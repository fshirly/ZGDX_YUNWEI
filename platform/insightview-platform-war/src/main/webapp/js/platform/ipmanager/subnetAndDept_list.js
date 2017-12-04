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
//	console.log("subNetId=="+subNetId);
	var path = getRootName();
	$('#tblSubnetAndDeptList')
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
						url : path + '/platform/subnetViewManager/listSubnetAndDeptInfo',
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
								doBatchAssignFromSubNet();
							}
						},
						{
							text : '批量收回到子网',
							iconCls : 'icon-batchwithdrawtosubnet',
							handler : function() {
								toBatchWithdrawDept();
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
									field : 'deptName',
									title : '部门名称',
									align : 'center',
									width : 100,
								},
								{
									field : 'usedNum',
									title : '占用地址数',
									align : 'center',
									width : 70,
								},
								{
									field : 'preemptNum',
									title : '空闲地址数',
									align : 'center',
									width : 70,
								},
								{
									field : 'totalNum',
									title : '总计',
									align : 'center',
									width : 70,
								},
								{
									field : 'ids',
									title : '操作',
									width : 100,
									align : 'center',
									formatter : function(value, row, index) {
										return '<a style="cursor: pointer;" onclick="javascript:toWithdrawDept('
											+ row.id
											+ ');"><img src="'
											+ path
											+  '/style/images/icon/withdrawtosubnet.png" title="收回到子网" alt="收回到子网" /></a>';
									}
								} ] ]
					});
	 $(window).resize(function() {
	        $('#tblSubnetAndDeptList').resizeDataGrid(0, 0, 0, 0);
	    });
}

/**
 * 更新表格
 */
function reloadTable(){
	var subNetIdStr = $("#subNetId").val();
	var subNetId =parseInt(subNetIdStr);
	var deptName = $("#txtDeptName").val();
	$('#tblSubnetAndDeptList').datagrid('options').queryParams = {
		"deptName" : deptName,
		"subNetId" :subNetId
	};
	reloadTableCommon_1('tblSubnetAndDeptList');
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
	$('#divExportSubAndDepet').dialog({
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
 * 导出列表数据
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
 * 从子网中收回部门
 */
function toWithdrawDept(id){
	$.messager.confirm("提示","确定收回该部门?",function(r){
		if (r == true) {
			var subNetId =$("#subNetId").val();
			var path = getRootName();
			var uri = path + "/platform/subnetViewManager/doWithdrawDept?ids="+id+"&subNetId="+subNetId;
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
					if (true == data.withdrwaFlag || "true" == data.withdrwaFlag) {
						$.messager.alert("提示", "该部门收回成功！", "info");
					} else if(true ==data.isReserve|| "true" == data.isReserve) {
						$.messager.alert("提示", "该部门中存在已占用的IP地址，请收回到预占状态再收回到子网！", "info");
					}else{
						$.messager.alert("错误", "数据库操作异常，该部门收回失败！", "error");
					}
					reloadTable();
					parent.window.initTree();
				}
			};
			ajax_(ajax_param);
		}
	});
}

/**
 * 批量收回部门
 */
function toBatchWithdrawDept(){
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
		$.messager.confirm("提示","确定收回所选中项？",function(r){
			if (r == true) {
				var subNetId =$("#subNetId").val();
				var path = getRootName();
				var uri = path + "/platform/subnetViewManager/doWithdrawDept?ids="+ids+"&subNetId="+subNetId;
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
						if (true == data.withdrwaFlag || "true" == data.withdrwaFlag) {
							$.messager.alert("提示", "部门收回成功！", "info");
						} else if(true ==data.isReserve|| "true" == data.isReserve) {
							$.messager.alert("提示", "下列部门中存在已占用的IP地址，请收回到预占状态再收回到子网！"+data.deptName, "info");
						}else{
							$.messager.alert("错误", "部门收回失败！", "error");
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
 * 根据ip地址范围批量分配到部门
 */
function doBatchAssignFromSubNet(){
	var subNetId =$("#subNetId").val();
	parent.parent.$('#popWin').window({
    	title:'批量分配到部门',
        width:600,
        height : 300,
        minimizable:false,
        maximizable:false,
        collapsible:false,
        modal:true,
        href: getRootName() + '/platform/subnetViewManager/toBatchAssignFromSubNet?subNetId='+subNetId
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