var attachmentArray = [];
var g_attachmentArrays = [];
var gs_attachmentArrays = [];
var g_id = 0;
var gs_id = 0;
var objSH = null;
var objService = null;
//附件缓存
var id = 0;
$('#supplierAdd').tabs({  
    border:false,  
    onSelect:function(title){  
        if(title='基础信息') {
        	$("#downloadFile").hide();
        }  
    }  
});  
$(document).ready(function() {
	//debugger;
	var data = {
		"total":0,
		"rows":{}
	}
	initAttachment_view();
	$('#attachment_view').datagrid('loadData', data);
	
	initProxy_info();
	$('#proxy_info').datagrid('loadData', data);
	
	initServices_info();
	$('#services_info').datagrid('loadData', data);
	
	$('#ipt_build').datebox({
		editable : false
	});
	
	$("#application_file").f_fileupload(
	{
		whetherPreview : false,
		//fileFormat : "['doc', 'docx']",
		filesize : 5120,
		imgWidth:10000,
        imgHeight:10000,
		upLoadBtnId :"uploadBtn2",
		showFile:false,
		//repeatUpload : true,
		onFileUpload : function(path,name){
			initAttachmentArray(path,name);
			$("#downloadFile").hide();
		}
	});
	/**
	 * 组装附件缓存
	 */
	function initAttachmentArray(path,name){
		//debugger;
		var attachmentType = $('#ipt_attachmentType').combobox('getValue');
		var row={
			"id":id,
			"accessoryType":attachmentType,
			"accessoryUrl":path,
			"accessoryName":name
		};
		attachmentArray.push(row);
		id++;
		var gridDate={
			"total":attachmentArray.length,
			"rows":attachmentArray
		};
		$('#attachment_view').datagrid({loadFilter:pagerFilter}).datagrid('loadData', gridDate);
		//$('#attachment_view').datagrid('loadData',gridDate);
		//reloadTableCommon('attachment_view');
	}
});

function doAdd() {
	if(checkInfo('#tblAddProvider')) {
		var uri =getRootName()+"/platform/supplier/addSupplierInfo";
		var ajax_param = {
			url : uri,
			type : "post",
			datdType : "json",
			data : {
				"providerName":$("#ipt_providerName").val(),
				"contacts":$("#ipt_contacts").val(),
				"contactsTelCode":$("#ipt_contactsTelCode").val(),
				"fax":$("#ipt_fax").val(),
				"email":$("#ipt_email").val(),
				"techSupportTel":$("#ipt_techSupportTel").val(),
				"establishedTime":$("#ipt_build").datebox("getValue"),
				"employNum":$("#ipt_employee").combobox("getValue"),
				"registeredFund":$("#ipt_fund").val(),
				"address":$("#ipt_address").val(),
				"uRL":$("#ipt_uRL").val(),
				"descr":$("#ipt_descr").val(),
				"attachmentArray":JSON.stringify(attachmentArray),
				"g_attachmentArrays":JSON.stringify(g_attachmentArrays),
				"gs_attachmentArrays":JSON.stringify(gs_attachmentArrays)
			},
			success : function(data) {
				if (true == data || "true" == data) {
					$('#popWin').window('close');
					$.messager.alert("提示", "供应商新建成功！", "info");
					window.frames['component_2'].reloadTable();
					//$('#tblDataList').datagrid('load');
					//$('#tblDataList').datagrid('uncheckAll');
				} else {
					$.messager.alert("提示", "供应商新建失败！", "error");
				}
			}
		};
		ajax_(ajax_param);
	} else {
		$("#supplierAdd").tabs("select","基础信息") ;
	}
}

function pagerFilter(data){
    if (typeof data.length == 'number' && typeof data.splice == 'function'){    // 判断数据是否是数组
        data = {
            total: data.length,
            rows: data
        }
    }
    var dg = $(this);
    var opts = dg.datagrid('options');
    var pager = dg.datagrid('getPager');
    pager.pagination({
        onSelectPage:function(pageNum, pageSize){
            opts.pageNumber = pageNum;
            opts.pageSize = pageSize;
            pager.pagination('refresh',{
                pageNumber:pageNum,
                pageSize:pageSize
            });
            dg.datagrid('loadData',data);
        }
    });
    if (!data.originalRows){
        data.originalRows = (data.rows);
    }
    var start = (opts.pageNumber-1)*parseInt(opts.pageSize);
    var end = start + parseInt(opts.pageSize);
    data.rows = (data.originalRows.slice(start, end));
    return data;
}

/**
 * 初始化查看页面的专家列表
 */
function initAttachment_view() {
	var path = getRootName();
	$('#attachment_view')
			.datagrid(
					{
						iconCls : 'icon-edit',// 图标
						width : 'auto',
						height : 320,
						nowrap : false,
						striped : true,
						border : true,
						collapsible : false,// 是否可折叠的
						fitColumns:true,
						//url : path+'/platform/supplier/attachmentInfo',
						remoteSort : false,
						idField : 'id',
						singleSelect : true,// 是否单选
						pagination : true,// 分页控件
						rownumbers : true,// 行号
						columns : [ [
								{
									field : 'accessoryType',
									title : '附件类型',
									width : 200,
									align : 'center',
									formatter : function(value, row, index) {
										if(value == 1) {
											return '经营状况';
										} else if(value == 2) {
											return '财务实力';
										} else if(value == 3) {
											return '诚信历史';
										} else if(value == 4) {
											return '安全资质';
										} else if(value == 5) {
											return '技术服务实力';
										} else if(value == 6) {
											return '责任承担能力';
										} else if(value == 7) {
											return '其他';
										}
										
									}
								},
								{
									field : 'accessoryUrl',
									title : '附件名称',
									width : 280,
									align : 'center',
									formatter : function(value, row, index) {
										var href =  getRootName() + '/commonFileUpload/CosDownload?fileDir=' + value;
										href = href.replace(/['\/\/']/g, "\\");
										return '<a title="下载文件" class="easyui-tooltip"  href="' + href + '">' + row.accessoryName + '</a>';
									}
								},{
									field : 'option',
									title : '操作',
									width : 200,
									align : 'center',
									formatter : function(value, row, index) {
										return '<a  style="cursor: pointer;" title="删除" class="easyui-tooltip" onclick="javascript:doDel('
												+ row.id
												+ ');"><img src="'
												+ path
												+ '/style/images/icon/icon_delete.png" alt="删除" /></a>';
									}
								}] ]
					});
}

/**
 * 附件缓存的删除
 */
function doDel(id){
	//debugger;
	$.messager.confirm("提示", "确定删除这条记录？", function(r) {
		if(r) {
			var index = $('#attachment_view').datagrid('getRowIndex',id);
			var pageNum = $('#attachment_view').datagrid('options').pageNumber;
			var pageSize = $('#attachment_view').datagrid('options').pageSize;
			attachmentArray.splice(index + pageSize * (pageNum - 1),1);
			var gridDate={
				"total":attachmentArray.length,
				"rows":attachmentArray
			};
			$('#attachment_view').datagrid({loadFilter:pagerFilter}).datagrid('loadData',gridDate);
			//reloadTableCommon('attachment_view');
		}
	});
}

/**
 * 初始化查看页面的供应商列表
 */
function initProxy_info() {
	var path = getRootName();
	$('#proxy_info')
			.datagrid(
					{
						iconCls : 'icon-edit',// 图标
						width : 'auto',
						height : 490,
						nowrap : false,
						striped : true,
						border : true,
						collapsible : false,// 是否可折叠的
						fitColumns:true,
						//url : path + '/platform/supplier/softHardwareInfo',
						remoteSort : false,
						idField : 'id',
						singleSelect : true,// 是否单选
						pagination : true,// 分页控件
						rownumbers : true,// 行号
						toolbar : [ {
							'text' : '新建',
							'iconCls' : 'icon-add',
							handler : function() {
								toAddSoftHard();
							}
						}],
						columns : [ [
								{
									field : 'proxyName',
									title : '资质名称',
									width : 200,
									align : 'center',
									styler: function(value,row,index){
										return 'word-break:break-all;';
									}
								},
								{
									field : 'productType',
									title : '代理产品类型',
									width : 160,
									align : 'center',
									formatter : function(value, row, index) {
										if(value==1) {
											return "软件";
										} else if(value==2) {
											return "硬件";
										}
									}
								},
								{
									field : 'proxyFirmName',
									title : '代理厂商',
									width : 160,
									align : 'center',
									styler: function(value,row,index){
										return 'word-break:break-all;';
									}
								},
								{
									field : 'certificateUrl',
									title : '相关证明文件',
									width : 160,
									align : 'center',
									formatter : function(value, row, index) {
										var href =  getRootName() + '/commonFileUpload/CosDownload?fileDir=' + value;
										value = (value == '' ? '' : initDownLinkTag_table("proxyfilename_add",value));
										href = href.replace(/['\/\/']/g, "\\");
										return '<a title="下载文件" class="easyui-tooltip"  id="proxyfilename_add" href="' + href + '">' + value + '</a>';
									}
								},{
									field : 'option',
									title : '操作',
									width : 200,
									align : 'center',
									formatter : function(value, row, index) {
										return '<a style="cursor: pointer;" title="查看" class="easyui-tooltip" onclick="javascript:toSHLook('
												+ row.id
												+ ');"><img src="'
												+ path
												+ '/style/images/icon/icon_view.png" alt="查看" /></a>&nbsp;&nbsp;&nbsp;&nbsp;<a style="cursor: pointer;" title="修改" class="easyui-tooltip" onclick="javascript:toSHUpdate('
												+ row.id
												+ ');"><img src="'
												+ path
												+ '/style/images/icon/icon_modify.png" alt="修改" /></a>&nbsp;&nbsp;&nbsp;&nbsp; <a  style="cursor: pointer;" title="删除" class="easyui-tooltip" onclick="javascript:doSHDel('
												+ row.id
												+ ');"><img src="'
												+ path
												+ '/style/images/icon/icon_delete.png" alt="删除" /></a>';
									}
								}] ]
					});
}
/**
 * 附件缓存的删除
 */
function doSHDel(id){
	$.messager.confirm("提示", "确定删除这条记录？", function(r) {
		if(r) {
			var index = $('#proxy_info').datagrid('getRowIndex',id);
			var pageNum = $('#proxy_info').datagrid('options').pageNumber;
			var pageSize = $('#proxy_info').datagrid('options').pageSize;
			g_attachmentArrays.splice(index + pageSize * (pageNum - 1),1);
			//g_attachmentArrays.splice(index,1);
			var gridDate={
				"total":g_attachmentArrays.length,
				"rows":g_attachmentArrays
			};
			$('#proxy_info').datagrid({loadFilter:pagerFilter}).datagrid('loadData',gridDate);
			//reloadTableCommon('proxy_info');
		}
	});
}
/**
 * 
 */
function toSHLook(id){
	for(var i = 0;i < g_attachmentArrays.length; i++) {
		if(id == g_attachmentArrays[i].id) {
			objSH = g_attachmentArrays[i];
			parent.$('#popWin2').window({
		    	title:'软硬件代理详情',
		        width:800,
		        height:450,
		        minimizable:false,
		        maximizable:false,
		        collapsible:false,
		        modal:true,
		        href: getRootName() + '/platform/supplier/softHardLook'
		     });
		}
	}
}
/**
 * 
 */
function toSHUpdate(id){
	
	for(var i = 0;i < g_attachmentArrays.length; i++) {
		if(id == g_attachmentArrays[i].id) {
			objSH = g_attachmentArrays[i];
			parent.$('#popWin2').window({
		    	title:'软硬件代理修改',
		        width:800,
		        height:410,
		        minimizable:false,
		        maximizable:false,
		        collapsible:false,
		        modal:true,
		        href: getRootName() + '/platform/supplier/toSHUpdate'
		     });
			break;
		}
	}
}
/**
 * 进入软硬件新增页面
 *
 */
function toAddSoftHard() {
		 	parent.$('#popWin2').window({
		    	title:'软硬件代理新增',
		        width:800,
		        height:410,
		        minimizable:false,
		        maximizable:false,
		        collapsible:false,
		        modal:true,
		        href: getRootName() + '/platform/supplier/toSoftHardAdd'
		     });
}
/**
 * 进入软硬件新增页面
 *
 */
function toServiceAdd() {
 	parent.$('#popWin2').window({
    	title:'服务资质新增',
        width:800,
        height:310,
        minimizable:false,
        maximizable:false,
        collapsible:false,
        modal:true,
        href: getRootName() + '/platform/supplier/toServiceAdd'
     });
}
/**
 * 附件缓存的删除
 */
function doSDel(id){
	$.messager.confirm("提示", "确定删除这条记录？", function(r) {
		if(r) {
			var index = $('#services_info').datagrid('getRowIndex',id);
			var pageNum = $('#services_info').datagrid('options').pageNumber;
			var pageSize = $('#services_info').datagrid('options').pageSize;
			gs_attachmentArrays.splice(index + pageSize * (pageNum - 1),1);
			//gs_attachmentArrays.splice(index,1);
			var gridDate={
				"total":gs_attachmentArrays.length,
				"rows":gs_attachmentArrays
			};
			$('#services_info').datagrid({loadFilter:pagerFilter}).datagrid('loadData',gridDate);
			//reloadTableCommon('services_info');
		}
	});
}
/**
 * 
 */
function toSLook(id){
	for(var i = 0;i < gs_attachmentArrays.length; i++) {
		if(id == gs_attachmentArrays[i].id) {
			objService = gs_attachmentArrays[i];
			parent.$('#popWin2').window({
		    	title:'服务资质详情',
		        width:800,
		        height:400,
		        minimizable:false,
		        maximizable:false,
		        collapsible:false,
		        modal:true,
		        href: getRootName() + '/platform/supplier/toServiceLook'
		     });
		}
	}
}
/**
 * 
 */
function toSUpdate(id){
	for(var i = 0;i < gs_attachmentArrays.length; i++) {
		if(id == gs_attachmentArrays[i].id) {
			objService = gs_attachmentArrays[i];
			parent.$('#popWin2').window({
		    	title:'服务资质修改',
		        width:800,
		        height:310,
		        minimizable:false,
		        maximizable:false,
		        collapsible:false,
		        modal:true,
		        href: getRootName() + '/platform/supplier/toSUpdate'
		     });
		}
	}
}
/**
 * 初始化查看页面的供应商列表
 */
function initServices_info() {
	var path = getRootName();
	$('#services_info')
			.datagrid(
					{
						iconCls : 'icon-edit',// 图标
						width : 'auto',
						height : 490,
						nowrap : false,
						striped : true,
						border : true,
						collapsible : false,// 是否可折叠的
						fitColumns:true,
						//url : path,
						remoteSort : false,
						//idField : 'id',
						singleSelect : true,// 是否单选
						pagination : true,// 分页控件
						rownumbers : true,// 行号
						toolbar : [ {
							'text' : '新建',
							'iconCls' : 'icon-add',
							handler : function() {
								toServiceAdd();
							}
						}],
						columns : [ [
								{
									field : 'serviceName',
									title : '服务名称',
									width : 80,
									align : 'center',
									styler: function(value,row,index){
										return 'word-break:break-all;';
									}
								},
								{
									field : 'serviceFirmName',
									title : '服务厂商',
									width : 80,
									align : 'center',
									styler: function(value,row,index){
										return 'word-break:break-all;';
									}
								},
								{
									field : 'serviceBeginTime',
									title : '服务开始时间',
									width : 80,
									align : 'center'
								},
								{
									field : 'serviceEndTime',
									title : '服务结束时间',
									width : 80,
									align : 'center'
								}/*,
								{
									field : 'description',
									title : '服务说明',
									width : 80,
									align : 'center'
								}*/,{
									field : 'certificateUrl',
									title : '相关证明文件',
									width : 80,
									align : 'center',
									formatter : function(value, row, index) {
										var href =  getRootName() + '/commonFileUpload/CosDownload?fileDir=' + value;
										value = (value == '' ? '' : initDownLinkTag_table("servicefilename_add",value));
										href = href.replace(/['\/\/']/g, "\\");
										return '<a title="下载文件" class="easyui-tooltip" id="servicefilename_add" href="' + href + '">' + value + '</a>';
									}
								},{
									field : 'option',
									title : '操作',
									width : 90,
									align : 'center',
									formatter : function(value, row, index) {
										return '<a style="cursor: pointer;" title="查看" class="easyui-tooltip" onclick="javascript:toSLook('
												+ row.id
												+ ');"><img src="'
												+ path
												+ '/style/images/icon/icon_view.png" alt="查看" /></a>&nbsp;&nbsp;&nbsp;&nbsp;<a style="cursor: pointer;" title="修改" class="easyui-tooltip" onclick="javascript:toSUpdate('
												+ row.id
												+ ');"><img src="'
												+ path
												+ '/style/images/icon/icon_modify.png" alt="修改" /></a>&nbsp;&nbsp;&nbsp;&nbsp; <a  style="cursor: pointer;" title="删除" class="easyui-tooltip" onclick="javascript:doSDel('
												+ row.id
												+ ');"><img src="'
												+ path
												+ '/style/images/icon/icon_delete.png" alt="删除" /></a>';
									}
								} ] ]
					});
}
	
//解析下载文件名
function initDownLinkTag_table(tagId, fileHref) {
	if('' == fileHref || null == fileHref)
	{
		$("#" + tagId).attr("href", '#');
		$("#" + tagId).text('');
	}else{
		var path = getRootName();
		var relHref = path + '/commonFileUpload/CosDownload?fileDir=' + fileHref;
		var relFileName = construFileName(fileHref);

		relHref = relHref.replace(/['\/\/']/g, "\\");

		$("#" + tagId).attr("href", relHref);
		return relFileName;
		//$("#" + tagId).text(relFileName);
	}
	
}	