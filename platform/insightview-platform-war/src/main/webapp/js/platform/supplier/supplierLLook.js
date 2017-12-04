var path = getRootName();
var lProviderId=$('#lProviderId').val();
$(document).ready(function() {
	initRoomResDetail();
	doInitBaseTable();
	initProxySHinfo();
	initPServicesinfo();
});

/*
 * 初始化信息
 */
function initRoomResDetail(){
	var path=getRootName();
	var uri=path+"/platform/supplier/baseSdetail";
	var ajax_param={
			url:uri,
			type:"post",
			datdType:"json",
			data:{
				"providerId":lProviderId,
				"t":Math.random()
			},
			error:function(){
				$.messager.alert("错误","ajax_error","error");
			},
			success:function(data){
				if(data['employNum'] == 1) {
					data['employNum'] = '20人以下';
				} else if(data['employNum'] == 2){
					data['employNum'] = '20-99人';
				}else if(data['employNum'] == 3){
					data['employNum'] = '100-499人';
				}else if(data['employNum'] == 4){
					data['employNum'] = '500-9999人';
				} else {
					data['employNum'] = '10000人及以上';
				}
				if(data['establishedTime']) {
					data['establishedTime'] = formatDateText2(new Date(data['establishedTime']));
				}
				iterObj(data,"lbl");
			}
		};
	ajax_(ajax_param);
}

function doInitBaseTable() {
	var path = getRootName();
	$('#providerAccessoryInfo')
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
						url : path+'/platform/supplier/attachmentInfo',
						queryParams : {
							"providerId" : lProviderId,
						},
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
								}] ]
					});
}

function initProxySHinfo() {
	var path = getRootName();
	$('#providerSoftHardwareProxy')
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
			url : path + '/platform/supplier/softHardwareInfo',
			queryParams : {
				"providerId" : lProviderId,
			},
			remoteSort : false,
			idField : 'id',
			singleSelect : true,// 是否单选
			pagination : true,// 分页控件
			rownumbers : true,// 行号
			columns : [ [
					{
						field : 'proxyName',
						title : '代理名称',
						width : 200,
						align : 'center'
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
						align : 'center'
					}/*,
					{
						field : 'proxyBeginTime',
						title : '代理开始时间',
						width : 160,
						align : 'center',
						formatter:function(value,row,index){
							if(value!=null && value!="" ){
								return formatDate(new Date(value), "yyyy-MM-dd");
							}else{
								return "";
							}	
                    	}
					},
					{
						field : 'proxyEndTime',
						title : '代理结束时间',
						width : 160,
						align : 'center',
						formatter:function(value,row,index){
							if(value!=null && value!="" ){
								return formatDate(new Date(value), "yyyy-MM-dd");
							}else{
								return "";
							}	
                    	}
					},
					{
						field : 'description',
						title : '代理说明',
						width : 160,
						align : 'center'
					}*/,
					{
						field : 'certificateUrl',
						title : '相关证明文件',
						width : 160,
						align : 'center',
						formatter : function(value, row, index) {
							var href =  getRootName() + '/commonFileUpload/CosDownload?fileDir=' + value;
							value = (value == '' ? '' : initDownLinkTag_table("proxyFileName_look",value));
							href = href.replace(/['\/\/']/g, "\\");
							return '<a title="下载文件" class="easyui-tooltip"  id="proxyFileName_look" href="' + href + '">' + value + '</a>';
						}
					},
					{
						field : 'option',
						title : '操作',
						width : 160,
						align : 'center',
						formatter : function(value, row, index) {
							return '<a style="cursor: pointer;" title="查看" class="easyui-tooltip" onclick="javascript:toSLLook('
									+ row.proxyId
									+ ');"><img src="'
									+ path
									+ '/style/images/icon/icon_view.png" alt="查看" /></a>';
						}
					}] ]
		});
}

/**
 * 初始化查看页面的供应商列表
 */
function initPServicesinfo() {
	var path = getRootName();
	$('#providerServiceCertificate')
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
						url : path + '/platform/supplier/getServiceInfo',
						queryParams : {
							"providerId" : lProviderId,
						},
						remoteSort : false,
						idField : 'id',
						singleSelect : true,// 是否单选
						pagination : true,// 分页控件
						rownumbers : true,// 行号
						columns : [ [
								{
									field : 'serviceName',
									title : '服务名称',
									width : 80,
									align : 'center'
								},
								{
									field : 'serviceFirmName',
									title : '服务厂商',
									width : 80,
									align : 'center'
								},
								{
									field : 'serviceBeginTime',
									title : '服务开始时间',
									width : 80,
									align : 'center',
									formatter:function(value,row,index){
										if(value!=null && value!="" ){
											return formatDate(new Date(value), "yyyy-MM-dd");
										}else{
											return "";
										}	
		                        	}
								},
								{
									field : 'serviceEndTime',
									title : '服务结束时间',
									width : 80,
									align : 'center',
									formatter:function(value,row,index){
										if(value!=null && value!="" ){
											return formatDate(new Date(value), "yyyy-MM-dd");
										}else{
											return "";
										}	
		                        	}
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
										value = (value == '' ? '' : initDownLinkTag_table("serviceFileName_look",value));
										href = href.replace(/['\/\/']/g, "\\");
										return '<a title="下载文件" class="easyui-tooltip" id="serviceFileName_look" href="' + href + '">' + value + '</a>';
									}
								},
								{
									field : 'option',
									title : '操作',
									width : 80,
									align : 'center',
									formatter : function(value, row, index) {
										return '<a style="cursor: pointer;" title="查看" class="easyui-tooltip" onclick="javascript:toSLook('
												+ row.serviceId
												+ ');"><img src="'
												+ path
												+ '/style/images/icon/icon_view.png" alt="查看" /></a>';
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

function toSLook(id){
		parent.$('#popWin2').window({
	    	title:'服务资质详情',
	        width:800,
	        height:400,
	        minimizable:false,
	        maximizable:false,
	        collapsible:false,
	        modal:true,
	        href: getRootName() + '/platform/supplier/toServiceCertificateLook?serviceId='+id
	     });
}

function toSLLook(id){
	parent.$('#popWin2').window({
    	title:'软硬件代理详情',
        width:800,
        height:450,
        minimizable:false,
        maximizable:false,
        collapsible:false,
        modal:true,
        href: getRootName() + '/platform/supplier/toSoftHardCertificateLook?proxyId='+id
     });
}