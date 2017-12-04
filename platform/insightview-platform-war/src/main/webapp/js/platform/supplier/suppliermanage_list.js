var path = getRootName();
$(document).ready(function() {	
	doInitTable();
});

/**
 * 加载页面并初始化表格
 * @return
 */
function doInitTable() {
	var path = getRootName();
	$('#tblDataList')
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
						url : path + '/platform/supplier/baseInfo',
						remoteSort : true,
						onSortColumn:function(sort,order){
//						 alert("sort:"+sort+",order："+order+"");
						},						
						//idField : 'fldId',
						singleSelect : true,// 是否单选
						checkOnSelect : false,
						selectOnCheck : false,
						pagination : true,// 分页控件
						rownumbers : true,// 行号
						toolbar : [ {
							'text' : '新建',
							'iconCls' : 'icon-add',
							handler : function() {
								toAdd();
							}
						}, {
							text : '删除',
							iconCls : 'icon-cancel',
							handler : function() {
								doBatchDel();
							}
						} ],
						columns : [ [
								{
									field : 'providerId',
									checkbox : true
								},
						        {
						        	field : 'providerName',
						        	title : '供应商名称',
						        	width : 80,
						        	align : 'center'
						        },
								{
									field : 'contacts',
									title : '联系人',
									width : 80,
									align : 'center'									
								},     
								{
									field : 'contactsTelCode',
									title : '联系电话',
									width : 80,
									align : 'center'
								},
								{
									field : 'fax',
									title : '传真',
									width : 80,
									align : 'center'									
								},
								{
									field : 'techSupportTel',
									title : '服务电话',
									width : 80,
									align : 'center'									
								},
								{
									field : 'email',
									title : 'E-mail',
									width : 80,
									align : 'center'									
								},{
									field : 'option',
									title : '操作',
									width : 120,
									align : 'center',
									formatter : function(value, row, index) {
										return '<a style="cursor: pointer;" title="查看" class="easyui-tooltip" onclick="javascript:toSLLook('
												+ row.providerId
												+ ');"><img src="'
												+ path
												+ '/style/images/icon/icon_view.png" alt="查看" /></a>&nbsp;&nbsp;&nbsp;&nbsp;<a style="cursor: pointer;" title="修改" class="easyui-tooltip" onclick="javascript:toSLUpdate('
												+ row.providerId
												+ ');"><img src="'
												+ path
												+ '/style/images/icon/icon_modify.png" alt="修改" /></a>&nbsp;&nbsp;&nbsp;&nbsp; <a  style="cursor: pointer;" title="删除" class="easyui-tooltip" onclick="javascript:doSLDel('
												+ row.providerId+",'"+row.providerName
												+ '\');"><img src="'
												+ path
												+ '/style/images/icon/icon_delete.png" alt="删除" /></a> &nbsp;&nbsp;&nbsp;&nbsp;'
												+ '<a style="cursor: pointer;" onclick="javascript:toConfigOrganization('
												+ row.providerId
												+ ');"><img src="'
												+path
												+'/style/images/icon/icon_menuconfig.png" alt="配置服务范围"  title="配置服务范围" /></a>';
									}
								} ] ]
					});
	$(window).resize(function() {
        $('#tblDataList').resizeDataGrid(0, 0, 0, 0);
    });
}

function doSLDel(id,name) {
	$.messager.confirm("提示", "确定删除所选中项?", function(r){
		if (r == true){
			var uri = path + "/platform/supplier/deleteSupplier?providerId="+id;
			var ajax_param = {
				url : uri,
				type : "post",
				datdType : "json",
				data : {
					"providerId" : id
				},
				success : function(data){
					if (data == false ) {
						$.messager.alert("错误", "供应商："+name+" 已被用户使用，删除失败！", "error");
						reloadTable();
					}else{
						$.messager.alert("提示", "信息删除成功！", "info");
						reloadTable();
					}
				}
			};
			ajax_(ajax_param);
		} 
	});
}

function toSLUpdate(id) {
	parent.$('#popWin').window({
    	title:'供应商修改',
        width:850,
        height:650,
        minimizable:false,
        maximizable:false,
        collapsible:false,
        modal:true,
        href: getRootName() + '/platform/supplier/supplierLEdit?providerId='+id
   });	
}

/**
 * 判断厂商是否唯一
 */
function checkNameUnique(){
	var providerName = $("#ipt_providerName").val();
	if(providerName != null || providerName != ""){
		var path = getRootName();
		var uri = path + "/platform/provider/checkNameUnique";
		var ajax_param = {
			url : uri,
			type : "post",
			datdType : "json",
			data : {
				"providerName" : providerName,
				"t" : Math.random()
			},
			error : function(){
				$.messager.alert("错误", "ajax_error", "error");
			},
			success : function(data){
				if(data == false){
					$.messager.alert("提示", "该供应商已存在！", "info");
					$('#ipt_providerName').val("");
					$('#ipt_providerName').focus();
				}else{
					return;
				}
			}
		};
		ajax_(ajax_param);
	}
}

/**
 * 查看供应商管理详情
 * @return
 */
function toSLLook(id) {
	 parent.$('#popWin').window({
	    	title:'供应商详情',
	        width:850,
	        height:650,
	        minimizable:false,
	        maximizable:false,
	        collapsible:false,
	        modal:true,
	        href: getRootName() + '/platform/supplier/supplierLLook?providerId='+id
	    });	
}
/**
 * 刷新表格数据
 */
function reloadTable() {
		var providerName=$("#providerName").val();
		var proxyFirmName=$("#proxyFirmName").val();
		var serviceFirmName=$("#serviceFirmName").val();
		$('#tblDataList').datagrid('options').queryParams = {
			"providerName" : providerName,
			"proxyFirmName" : proxyFirmName,
			"serviceFirmName" : serviceFirmName
		};
		reloadTableCommon_1('tblDataList');
}

function reloadTableCommon_1(dataGridId) {
	$('#' + dataGridId).datagrid('load');
	$('#' + dataGridId).datagrid('uncheckAll');
}

function doBatchDel(){
	var path = getRootName();
	var checkedItems = $('[name=providerId]:checked');
	var ids = null;
	for ( var i = 0; i < checkedItems.length; i++){
		var id = checkedItems[i].value;
		if (null == ids){
			ids = id;
		}else{
			ids += ',' + id;
		}
	}
	if (null != ids){
		$.messager.confirm("提示", "确定删除所选中项?", function(r){
			if (r == true){
				var uri = path + "/platform/supplier/deleteSuppliers?providerIds="+ids;
				var ajax_param = {
					url : uri,
					type : "post",
					datdType : "json",
					data : {
						"providerId" : ids
					},
					success : function(data){
						if (data.res != "" ) {
							$.messager.alert("错误", "供应商 ：" + data.res + " 已被用户使用，删除失败！", "error");
							reloadTable();
						}else{
							$.messager.alert("提示", "信息删除成功！", "info");
							reloadTable();
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

/**
 * 打开供应商新增页面
 * @return
 */
function toAdd(){	
	 parent.$('#popWin').window({
    	title:'供应商新增',
        width:850,
        height:650,
        minimizable:false,
        maximizable:false,
        collapsible:false,
        modal:true,
        href: getRootName() + '/platform/supplier/toSupplierAdd'
     });		
}

var _providerId = "";
var dataTreeOrg = new dTree("dataTreeOrg", getRootPatch()+ "/plugin/dTree/img/");
dataTreeOrg.config.check=true;
/**
 * 服务单位配置
*/
function toConfigOrganization(providerId){

	_providerId = providerId;
		var path = getRootPatch();
		var uri = path + "/platform/supplier/getOrganizationTreeVal";
		var ajax_param = {
			url : uri,
			type : "post",
			datdType : "json",
			data : {
				"providerId" : providerId,
				"t" : Math.random()
			},
			error : function() {
				$.messager.alert("错误", "ajax_error", "error");
			},
			success : function(data) {
				dataTreeOrg.add(0, -1, "选择服务范围", "");
				// 得到树的json数据源
				var datas = eval('(' + data.orgLstJson + ')');
				var OrgIdArr = data.OrgId;
				_treeData = datas;
				// 遍历数据
				var gtmdlToolList = datas;
				for (var i = 0; i < gtmdlToolList.length; i++) {
					var _id = gtmdlToolList[i].organizationID;
					var _nameTemp = gtmdlToolList[i].organizationName;
					var _parent = gtmdlToolList[i].parentOrgID;
//					var _menuLevel = gtmdlToolList[i].menuLevel;

					dataTreeOrg.add(_id, _parent, _nameTemp,
							"javascript:hiddenDTreeSetVal('selFilterParentId','"
									+ _id + "','" + _nameTemp + "');");
				}
				$('#dataOrganizationTreeDiv').empty();
				$('#dataOrganizationTreeDiv').append(dataTreeOrg + "");
				dataTreeOrg.setDefaultCheck(OrgIdArr);
				dataTreeOrg.openAll();
			}
		}
		ajax_(ajax_param);
	$('#divOrganizationConfig').dialog('open');

}

function doConfigOrganization() {
	var selids=dataTreeOrg.getCheckedNodes();  
	var selOrgId="";
	for ( var i = 0, l = selids.length; i < l; i++) {
		if(selOrgId !="" ){
			if(selOrgId.indexOf(selids[i]+",") <0){
				selOrgId +=selids[i]+","; 
			}
		}else{
			selOrgId +=selids[i]+",";
		}
	}
	var path = getRootName();
	var uri = path + "/platform/supplier/addProviderOrganization";
	var ajax_param = {
		url : uri,
		type : "post",
		async:false,
		datdType : "json",
		data : {
			"providerId" : _providerId,
			"orgIdAttr" : selOrgId,
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			if (true == data || "true" == data) {
//				$.messager.alert("提示", "配置菜单修改成功！", "info");
				reloadTable();
				$('#divOrganizationConfig').dialog('close');
			} else {
				$.messager.alert("提示", "配置单位修改失败！！", "error");
				reloadTable();
			}
		}
	}
	ajax_(ajax_param);
}

function closeConfigOrganization(){
	$('#divOrganizationConfig').dialog('close');
}
