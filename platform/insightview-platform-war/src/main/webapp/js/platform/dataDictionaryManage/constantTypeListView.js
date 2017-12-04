$(document).ready(function() {
	
	var flag=$('#flag').val();
	var type=$('#type').val();
	if(flag=='choose'){
		doInitChooseTable(type);
		$('#tblConsTypeManagement').datagrid('hideColumn','constantTypeId');
		$('#tblConsTypeManagement').datagrid('hideColumn','parentTypeID');
	}else{
		doInitTable();
	}
});

/*
 * 页面加载初始化表格
 */
function doInitTable() {
	var path = getRootName();
	
	$('#tblConsTypeManagement').datagrid({
		iconCls : 'icon-edit',// 图标
		width : 'auto',
		height : 'auto',
		nowrap : false,
		striped : true,
		border : true,
		collapsible : false,// 是否可折叠的
		fit : true,// 自动大小dd
		url : path + '/platform/dataDictTypeManage/loadConstantTypeList',
		// sortName: 'code',
		// sortOrder: 'desc',
		remoteSort : false,
		idField : 'fldId',
		singleSelect : false,// 是否单选
		checkOnSelect : false,
		selectOnCheck : true,
		pagination : true,// 分页控件
		rownumbers : true,// 行号
		rownumbers : true,// 行号
		fitColumns:true,
		fit:true,
		toolbar : [ {
			text : '新增',
			iconCls : 'icon-add',
			handler : function() {
				toAdd();
			}
		},
		 {
			text : '删除',
			iconCls : 'icon-cancel',
			handler : function() {
				doBatchDel();
			}
		} ],
		columns : [ [
//			{
//				field : 'constantTypeId',
//				checkbox : true
//			},
			{
				field : 'constantTypeId',
				title : '类型ID',
				align : 'center',
				width : 90
			},
			{
				field : 'constantTypeName',
				title : '类型名称',
				align : 'center',
				width : 120
			},
			{
				field : 'constantTypeCName',
				title : '类型中文名',
				align : 'center',
				width : 120
			},
			{
				field : 'remark',
				title : '描述',
				align : 'center',
				width : 230
			},
//			{
//				field : 'parentTitle',
//				title : '级数',
//				align : 'center',
//				width : 90
//			},
			{
				field : 'parentTypeID',
				title : '上级编码',
				align : 'center',
				width : 90
			},
			{
				field : 'constantTypeIds',
				title : '操作',
				width : 90,
				align : 'center',
				formatter : function(value, row, index) {
					return '<a style="cursor: pointer;" onclick="javascript:toShowInfo('
							+ row.constantTypeId
							+ ');"><img src="'
							+ path
							+ '/style/images/icon/icon_view.png" title="查看" /></a> &nbsp;<a style="cursor: pointer;" onclick="javascript:toUpdate('
							+ row.constantTypeId
							+ ');"><img src="'
							+ path
							+ '/style/images/icon/icon_modify.png" title="修改" /></a> &nbsp<a style="cursor: pointer;" onclick="javascript:doDel('
							+ row.constantTypeId
							+ ');"><img src="'
							+ path
							+ '/style/images/icon/icon_delete.png" title="删除" /></a>';
				}
			} 
			] ]
	});
	$(window).resize(function() {
        $('#tblConsTypeManagement').resizeDataGrid(0, 0, 0, 0);
    });
}

function doInitChooseTable(type) {
	var path = getRootName();
	
	$('#tblConsTypeManagement').datagrid({
		iconCls : 'icon-edit',// 图标
		width : 'auto',
		height : 320,
		nowrap : false,
		striped : true,
		border : true,
		collapsible : false,// 是否可折叠的
		url : path + '/platform/dataDictTypeManage/loadConstantTypeList',
		queryParams : {
			"type" : type,
		},
		// sortName: 'code',
		// sortOrder: 'desc',
		remoteSort : false,
		idField : 'fldId',
		singleSelect : true,// 是否单选
		checkOnSelect : false,
		selectOnCheck : false,
		pagination : true,// 分页控件
		rownumbers : true,// 行号
		fitColumns:true,
//		fit:true,
		
		columns : [ [
//			{
//				field : 'constantTypeIds',
//				title : '',
//				width : 80,
//				align : 'center',
//				formatter : function(value, row, index) {
//					return '<a style="cursor: pointer;" onclick="javascript:sel('
//							+ row.constantTypeId+''
//							+ ');">选择</a>';
//				}
//			},
			{
				field : 'constantTypeId',
				title : '类型ID',
				align : 'center',
				width : 100
			},
			{
				field : 'constantTypeName',
				title : '类型名称',
				align : 'center',
				width : 150
			},
			{
				field : 'constantTypeCName',
				title : '类型中文名',
				align : 'center',
				width : 150
			},
			{
				field : 'remark',
				title : '描述',
				align : 'center',
				width : 150
			},
//			{
//				field : 'parentTitle',
//				title : '级数',
//				align : 'center',
//				width : 90
//			},
			{
				field : 'parentTypeID',
				title : '上级编码',
				align : 'center',
				width : 100
			}
			] ]
	});
}



/*
 * 更新表格 
 */
function reloadTable() {
	var type=$('#type').val();
	var constantTypeCName = $("#constantTypeCName").val();
	$('#tblConsTypeManagement').datagrid('options').queryParams = {
		"constantTypeCName" : constantTypeCName,
		"type" : type
	};
	
	$('#tblConsTypeManagement').datagrid('load');
	$('#tblConsTypeManagement').datagrid('uncheckAll');
}


/*
 * 批量删除
 */
function doBatchDel() {
	var checkedItems = $('[name=constantTypeId]:checked');
	var constantTypeIds = null;
	$.each(checkedItems, function(index, item) {
		if (null == constantTypeIds) {
			constantTypeIds = $(item).val();
		} else {
			constantTypeIds += ',' + $(item).val();
		}
	});
	if (null != constantTypeIds) {
		$.messager.confirm("提示","确定删除所选中项？",function(r){
			if (r == true) {
				var uri = "/platform/dataDictTypeManage/delConstantTypes?constantTypeIds=" + constantTypeIds;
				var ajax_param = {
					url : uri,
					type : "post",
					datdType : "json",
					data : {
						"t" : Math.random()
					},
					success : function(data) {
						if(data == false) {
			            	$.messager.alert("错误", "批量删除中断,请确定所有节点下无子节点并确定该字典类别未被使用", "error");
			            	reloadTable();
						} else {
							reloadTable();
						}
					}
				};
//				ajax_(ajax_param);
			}
		});
	} else {
		$.messager.alert('提示', '没有任何选中项', 'error');
	}
}


function toConfirmSelect(){
/*	 if(window.opener) { 
			 fWindowText1 = window.opener.document.getElementById("ipt_parentTypeID"); 
			 fWindowText2 = window.opener.document.getElementById("ipt_constantTypeId");
			 if(fWindowText1 == null && fWindowText2 != null){
				 fWindowText2.value = constantTypeId; 
			     window.opener.getConstantTypeName();
			     window.close();
			 }else{
				 fWindowText1.value = constantTypeId; 
				 fWindowText2.value = constantTypeId; 
			     window.opener.getParentTypeName(constantTypeId);
			     window.close();
			 }
 } */
	var moSource = $('#tblConsTypeManagement').datagrid('getSelected');
	if(moSource!=null && moSource!=""){
		fWindowText1 = window.frames['component_2'].document.getElementById("ipt_parentTypeID"); 
		fWindowText2 = window.frames['component_2'].document.getElementById("ipt_constantTypeId");
		if(fWindowText1 == null && fWindowText2 != null){
			fWindowText2.value = moSource.constantTypeId; 
			window.frames['component_2'].getConstantTypeName();
			$('#popWin').window('close');
		}else{
			fWindowText1.value = moSource.constantTypeId; 
			fWindowText2.value = moSource.constantTypeId; 
			window.frames['component_2'].getParentTypeName(moSource.constantTypeId);
			$('#popWin').window('close');
		}
	}else{
		$.messager.alert('提示', '没有任何选中项', 'error');
	}
}



function toAdd(){
	$("#btnSave").unbind('click');
	$("#btnSave").bind("click", function() {
		doAddDictType();
	});
	$("#btnClose").unbind();
	$("#btnClose").bind("click", function() {
		$('.input').val(""); 
		$('#divDataDictTypeInfo').dialog('close');
	});
	_auType = 1;
	$('#divDataDictTypeInfo').dialog('open');
}

function doAddDictType(){
	var result=checkInfo('#divDataDictTypeInfo');
	var path=getRootName();
	var uri=path+"/platform/dataDictTypeManage/addDataDictType";
	var parentId=0;
	if($("#ipt_parentTypeID").val()!=null && $("#ipt_parentTypeID").val()!=""){
		parentId=$("#ipt_parentTypeID").val();
	}
	var ajax_param={
			url:uri,
			type:"post",
			dateType:"json",
			data:{
				"parentTypeID":parentId,
				"constantTypeName" : $("#ipt_constantTypeName1").val(),
				"constantTypeCName" : $("#ipt_constantTypeCName1").val(),
				"remark":$("#ipt_remark1").val(),
				"t" : Math.random()
	},
	error:function(){
		$.messager.alert("错误","ajax_error","error");
	},
	success:function(data){
		if(true==data || "true"==data || ""==data){
//			$.messager.alert("提示","任务新增成功！","info");
			$("#divDataDictTypeInfo").dialog('close');
			reloadTable();
		}else{
			$.messager.alert("提示","任务新增失败！","error");
			reloadTable();
		}
	}
	};
	if(result==true){
		ajax_(ajax_param);
	}
		

}

function loadParentTypeInfo(){
	var path=getRootName();
	var uri=path+"/platform/dataDictTypeManage/toConstantTypeView?flag=choose";
	window.open(uri,"","height=500px,width=800px,left=500,top=200,resizable=no,scrollings=no,status=no,toolbar=no,menubar=no,location=no");
}

function getParentTypeName(){
	var parentTypeID=$("#ipt_parentTypeID").val();
	var path=getRootName();
	var uri=path+"/platform/dataDictTypeManage/getParentTypeName";
	var ajax_param={
		url:uri,
		type:"post",
		dateType:"json",
		data:{
			"parentTypeID" : parentTypeID,
			"t" : Math.random() 
		},
		error : function(){
			$.messager.alert("错误","ajax_error","error");
		},
		success : function(data){
			iterObj(data,"ipt");
			var typeName=$("#ipt_constantTypeName").val();
			var parentId=$("#ipt_constantTypeId").val();
			$("#ipt_parentTypeName").val(typeName);
			$("#ipt_parentTypeID").val(parentId);
		}
	};
	ajax_(ajax_param);		

}

//重置过滤条件
function resetForm() {
	$('#constantTypeCName').val('');
}

//删除
function doDel(constantTypeId) {
	var path=getRootName();
	$.messager.confirm("提示","确定删除此条？",function(r){
		if (r == true) {
			var uri = path+"/platform/dataDictTypeManage/delConstantType";
			var ajax_param = {
				url : uri,
				type : "post",
				datdType : "json",
				data : {
					"constantTypeId" : constantTypeId,
					"t" : Math.random()
				},
				error : function() {
					$.messager.alert("错误", "ajax_error", "error");
				},
				success : function(data) {
					if(data == false) {
	                  $.messager.alert("错误", "请先删除该节点下的子节点并确定该字典类别未被使用", "error");
	                  reloadTable();
	                } else {
	                  reloadTable();
	                  initTree();
	                }
					
				}
			};
			ajax_(ajax_param);
		}
	});
}


/**
 * 修改数据字典类型信息
 * @param userId
 * @return
 */
function toUpdate(constantTypeId){
//	$('.input').val("");
	initUpdateVal(constantTypeId);
	$("#btnSave").unbind('click');
	$("#btnSave").bind("click", function() {
		doUpdateTask(constantTypeId);
	});
	$("#btnClose").unbind();
	$("#btnClose").bind("click", function() {
		$('.input').val(""); 
		$('#divDataDictTypeInfo').dialog('close');
	});
	$('#divDataDictTypeInfo').dialog('open');
	
}

function initUpdateVal(constantTypeId){
	var path=getRootName();
	var uri=path+"/platform/dataDictTypeManage/initDataDictType";
	var ajax_param={
			url:uri,
			type:"post",
			datdType:"json",
			async : false,

			data:{
				"constantTypeId":constantTypeId,
				"t":Math.random()
			},
			error:function(){
				$.messager.alert("错误","ajax_error","error");
			},
			success:function(data){
				iterObj(data,"ipt");
				var constantTypeName=$("#ipt_constantTypeName").val();
				var constantTypeCName=$("#ipt_constantTypeCName").val();
				var remark=$("#ipt_remark").val();
				$("#ipt_constantTypeName1").val(constantTypeName);
				$("#ipt_constantTypeCName1").val(constantTypeCName);
				$("#ipt_remark1").val(remark);
				
			}
		};
	ajax_(ajax_param);
}


function doUpdateTask(constantTypeId){

	var path=getRootName();
	var uri=path+"/platform/dataDictTypeManage/editDataDictType";
	var ajax_param={
			url:uri,
			type:"post",
			dateType:"json",
			data:{
				"constantTypeId":constantTypeId,
				"parentTypeID":$("#ipt_parentTypeID").val(),
				"constantTypeName" : $("#ipt_constantTypeName1").val(),
				"constantTypeCName" : $("#ipt_constantTypeCName1").val(),
				"remark":$("#ipt_remark1").val(),
				"t" : Math.random()
	},
	error:function(){
		$.messager.alert("错误","ajax_error","error");
	},
	success:function(data){
		if(true==data || "true"==data || ""==data){
//			$.messager.alert("提示","修改成功！","info");
			$("#divDataDictTypeInfo").dialog('close');
			reloadTable();
		}else{
			$.messager.alert("提示","修改失败！","error");
			reloadTable();
		}
	}
	};
		ajax_(ajax_param);


}

//查看详情
function toShowInfo(constantTypeId){
//	$('.input').val("");
	setRead(constantTypeId);
	$("#btnClose2").unbind();
	$("#btnClose2").bind("click", function() {
		$('.input').val(""); 
		$('#divDataTypeInfo').dialog('close');
	});
	$('#divDataTypeInfo').dialog('open');

}
//初始化详情信息
function setRead(constantTypeId){
	var path=getRootName();
	var uri=path+"/platform/dataDictTypeManage/initDataDictType";
	var ajax_param={
			url:uri,
			type:"post",
			datdType:"json",
			async : false,

			data:{
				"constantTypeId":constantTypeId,
				"t":Math.random()
			},
			error:function(){
				$.messager.alert("错误","ajax_error","error");
			},
			success:function(data){
				iterObj(data,"lbl");
//				var constantTypeName=$("#ipt_constantTypeName").val();
//				var constantTypeCName=$("#ipt_constantTypeCName").val();
//				var remark=$("#ipt_remark").val();
//				$("#ipt_constantTypeName1").val(constantTypeName);
//				$("#ipt_constantTypeCName1").val(constantTypeCName);
//				$("#ipt_remark1").val(remark);
				
			}
		};
	ajax_(ajax_param);
}