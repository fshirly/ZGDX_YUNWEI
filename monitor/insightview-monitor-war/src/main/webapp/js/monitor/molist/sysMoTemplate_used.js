$(function(){
	toShowDevice();
});
 
/**
 * 查看绑定设备的列表
 */
function toShowDevice(){
	var templateID =  $('#ipt_templateID').val();
	var path = getRootName();
	$('#tblUsedDevice')
			.datagrid(
					{
						iconCls : 'icon-edit',// 图标
						width : 'auto',
						height : 350,
						nowrap : false,
						striped : true,
						border : true,
						collapsible : false,// 是否可折叠的
						fitColumns:true,
						url : path + '/monitor/sysMoTemplate/listUsedDevice',
						queryParams : {
							"templateID" : templateID,
						},
						remoteSort : false,
						idField : 'moid',
						singleSelect : false,// 是否单选
						checkOnSelect : true,
						selectOnCheck : true,
						pagination : true,// 分页控件
						rownumbers : true,// 行号
						toolbar : [
						{
							'text' : '批量解除绑定',
							'iconCls' : 'icon-remove-other',
							handler : function() {
								doBatchRemove();
							}
						}],
						columns : [ [
					             {
					            	 field : 'moid',
					            	 checkbox : true
					            	 
					             },	
								{
									field : 'deviceip',
									title : '设备IP',
									width : 100
								},
								{
									field : 'moname',
									title : '设备名称',
									width : 80
								},
								{
									field : 'classLable',
									title : '对象类型',
									width : 80
								},
								{
									field : 'moids',
									title : '操作',
									align : 'center',
									formatter : function(value, row, index) {
										return 	    '<a style="cursor: pointer;" onclick="javascript:toRemove('
													+ row.moid
													+ ');"><img src="'
													+ path
													+ '/style/images/icon/icon_subtract.png" alt="解除绑定" title="解除绑定"/></a>';
									}
								}
								 ] ]
					});
}

/*
 * 更新设备信息列表
 */
function reloadDeviceTable() {
	var templateID =  $('#ipt_templateID').val();
	$('#tblUsedDevice').datagrid('options').queryParams = {
		"templateID" : templateID,
	};
	reloadTableCommon('tblUsedDevice');
}

/**
 * 解除绑定模板
 * @param {Object} userId
 */
function  toRemove(moid){
	var path = getRootPatch();
	if(null != moid){
		$.messager.confirm("提示","确定将设备解除模板绑定？",function(r){
			if (r == true) {
				var uri = path + "/monitor/sysMoTemplate/delMoTemplateOfMoDevice?moIDs="+moid;
				var ajax_param = {
					url : uri,
					type : "post",
					datdType : "json",
					error : function() {
						$.messager.alert("错误", "ajax_error", "error");
					},
					success : function(data) {
						if (true == data || "true" == data) {
							_moIds = null;
//							$.messager.alert("提示", "将用户从用户组移除成功！", "info");
							reloadDeviceTable();
						} else {
							$.messager.alert("提示", "设备解除模板绑定失败！", "error");
						}
					}
				}
				ajax_(ajax_param);
				}
		});
	}else{
		$.messager.alert('提示', '没有任何选中项', 'error');
	}
}

/**
 * 批量移除用户
 * @return
 */
var _moIds = null;
function doBatchRemove(){
	var checkedItems = $('[name=moid]:checked');
	$.each(checkedItems, function(index, item) {
		if (null == _moIds) {
			_moIds = $(item).val();
		} else {
			_moIds += ',' + $(item).val();
		}
	});
	toRemove(_moIds);
}
