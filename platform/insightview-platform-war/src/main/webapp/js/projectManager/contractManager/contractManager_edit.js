f.namespace('platform.contractManager');
platform.contractManager.contractManagerEdit = (function(){
/*
* 绑定事件
*/
f('#btnContractManager_edit').click(payMomey_count);
var contractManagerEdit={
	    doContractFileDel:function(id){
		        doContractFileDel(id);
	    },
	    toContractPaymentUpdate:function(id){
	        toContractPaymentUpdate(id);	
        },
        toProjectContractChangeUpdate:function(id){
        	toProjectContractChangeUpdate(id);
        },
        doProjectContractChangeDel:function(id){
        	doProjectContractChangeDel(id);
        },
        doContractPaymentDel:function(id){
        	doContractPaymentDel(id);
        },
        toContractPaymentShowInfo:function(id){
        	toContractPaymentShowInfo(id);
        },
        toProjectContractChangeShowInfo:function(id){
        	toProjectContractChangeShowInfo(id);
        },
        toreloadiframe:function(){
        	reloadiframe();
        }
	};
var fileCachearrat=[];
var id=0;
$(function() {
	var path = getRootName();
	
	 /*
	  * 合同文件上传
	  */
	 $('#contractfile_url').f_fileupload({
			whetherPreview : false,
			//fileFormat : "['doc', 'docx', 'pdf', 'jpg', 'png', 'gif', 'rar', 'zip', 'txt']",
			filesize : 100000,
			imgWidth: 10000,
			imgHeight:10000,
			repeatUpload : true,
			showFile:false,
			onFileUpload : function(path,name){
				initAttachmentArray(path,name);
			}
		});
	 /*
	  * 关联项目下拉框
	  */
	 $('#ipt_projectId_update').combobox({
			valueField : 'id',
			textField : 'neir',
			url: path+'/contractmanager/projectcombox',
			editable : false,
			panelWidth:180,
			onLoadSuccess:function(){
				$(this).combobox('setValue', projectid);
			}
		});
	 /*
	  * 合同类型下来框
	  */
	 $('#ipt_contractType_update').combobox({
			valueField : 'id',
			textField : 'neir',
			url: path+'/contractmanager/contracttypecombox',
			editable : false,
			panelWidth:180,
			onLoadSuccess:function(){
				$(this).combobox('setValue', contracttype);
			}
		});
	doInitContractBiang_table();
	doInitContractPay();
	contractFile(true);
	$('#contractManagerInfoTabs_edit').tabs();
	$('#contractManagerInfoTabs_edit').tabs("select",choosetab);
});

/*
 * 合同付款编辑
 */
function doInitContractPay(){
	var path = getRootName();
	var uri=path+'/contractmanager/contractpayrecord?contractid='+contractid;
	$("#contract_payRecord_table").datagrid({
		height : 470,
	    nowrap : true,
		striped : true,
		border : true,
		onLoadSuccess:total_amount,
		collapsible : false,// 是否可折叠的
		fit : true,// 自动大小
		//fitColumns:true,
		url : uri,
		showFooter:true,
		remoteSort : true,					
		idField : 'paymentID',
		singleSelect : false,// 是否单选
		checkOnSelect : false,
		selectOnCheck : true,
		pagination : true,// 分页控件
		rownumbers : true,// 行号
		toolbar :[{
			text : '增加计划',
			iconCls : 'icon-add',
			handler : function(){
			   toputPlan();
			}	
		},{
			 text:'计划外付款',
			 iconCls : 'icon-add',
			 handler : function(){
			     toputNoPlan();
			}
			
		}],
		columns : [ [
		    /*{
		      field : 'paymentID',
		      checkbox : true   
		    },*/{
				 field : 'planPayTime',
			     title : '计划付款日期',
				 width : 120,
				 align:'center',
				 formatter:function(value, row, index){
				   if(value!=null && value!="" && value!='总共'){
					   return formatDate(new Date(value), "yyyy-MM-dd");
				   }else{
					   return value;
				   }
			     }
			},{
				 field : 'planPayAmount',
			     title : '计划付款金额（万元）',
				 width : 140,
				 align:'center'
			},{
				 field : 'paymentTime',
			     title : '实际付款日期',
				 width : 120,
				 align:'center',
				 formatter:function(value, row, index){
				   if(value!=null && value!="" ){
					   return formatDate(new Date(value), "yyyy-MM-dd");
				   }
			     }
			},{
				 field : 'amount',
			     title : '实际付款金额（万元）',
				 width : 144,
				 align:'center'
			},{
				 field : 'handler',
			     title : '经手人',
				 width : 100,
				 align:'center'
			},{
				field : 'paymentIDs',
				title : '操作',
				width : 80,
				align : 'center',
				formatter : function(value, row, index) {
				if(row.planPayTime=='总共'){
				   return '';
				}else{
				   return '<a style="cursor: pointer;" onclick="javascript:platform.contractManager.contractManagerEdit.toContractPaymentShowInfo(' 
				           + row.paymentID
				           + ');"><img src="'
				           + path
				           + '/style/images/icon/icon_view.png" title="查看" /></a>&nbsp;<a style="cursor: pointer;" onclick="javascript:platform.contractManager.contractManagerEdit.toContractPaymentUpdate('
				           + row.paymentID
						   + ');"><img src="'
						   + path
						   + '/style/images/icon/icon_modify.png" title="修改" /></a>&nbsp;<a style="cursor: pointer;" onclick="javascript:platform.contractManager.contractManagerEdit.doContractPaymentDel('
						   + row.paymentID
						   + ');"><img src="'
						   + path
						   + '/style/images/icon/icon_delete.png" title="删除" /></a>';
				 }
			  }
			}         
		]]
		
	});
	$(window).resize(function() {
        //$('#contract_payRecord_table').resizeDataGrid(0, 0, 0, 0);
    });
}
/*
 * 合同变更记录编辑
 */
function doInitContractBiang_table(){
	var path = getRootName();
	var uri=path+'/contractmanager/projectcontractchangelist?contractid='+contractid;
	$('#edit_contract_biange_Record').datagrid({
	    height : 470,
		nowrap : true,
		striped : true,
		border : true,
		collapsible : false,// 是否可折叠的
		//fit : true,// 自动大小
		//fitColumns:true,
		url : uri,
		remoteSort : true,					
		idField : 'id',
		singleSelect : false,// 是否单选
		checkOnSelect : false,
		selectOnCheck : true,
		pagination : true,// 分页控件
		rownumbers : true,// 行号
		toolbar :[{
			text : '新建',
			iconCls : 'icon-add',
			handler : function(){
			toaddContractChane();
			}	
		},{
			 text:'删除',
			 iconCls : 'icon-cancel',
			 handler : function(){
				doProjectContractChangeBatchDel();
			}
			
		}],
		columns : [ [
		   {
			 field : 'id',
             checkbox : true   
		   },{
			 field : 'title',
		     title : '变更标题',
		     align : 'center',
			 width : 200,
			 styler: function(value,row,index){
				return 'word-break:break-all;';
			}
		   },{
			 field : 'changeTime',
			 title : '变更时间',
		     width : 180,
		     align : 'center',
		     formatter:function(value, row, index){
			   if(value!=null && value!="" ){
				   return formatDate(new Date(value), "yyyy-MM-dd");
			   }
		     }
		   },{
			field : 'confirmManName',
		    title : '确认人',
		    align : 'center',
			width :180,
		   },{
				field : 'ids',
				title : '操作',
				width : 145,
				align : 'center',
				formatter : function(value, row, index) {
					return '<a style="cursor: pointer;" onclick="javascript:platform.contractManager.contractManagerEdit.toProjectContractChangeShowInfo(' 
			               + row.id
			               + ');"><img src="'
			               + path
			               +'/style/images/icon/icon_view.png" title="查看" /></a>&nbsp;<a style="cursor: pointer;" onclick="javascript:platform.contractManager.contractManagerEdit.toProjectContractChangeUpdate('
					       + row.id
					       + ');"><img src="'
					       + path
					       + '/style/images/icon/icon_modify.png" title="修改" /></a>&nbsp;<a style="cursor: pointer;" onclick="javascript:platform.contractManager.contractManagerEdit.doProjectContractChangeDel('
						   + row.id
						   + ');"><img src="'
						   + path
						   + '/style/images/icon/icon_delete.png" title="删除" /></a>';
				}
			} 
	    ]]
		
	});
	$(window).resize(function() {
        //$('#edit_contract_biange_Record').resizeDataGrid(0, 0, 0, 0);
    });
}
/**
 * 多文件上传
 * @return
 */
function contractFile(a){
	var path = getRootName();
	var uri="";
	if(a==true){
         uri=path+'/contractmanager/fileupdate_list?contractid='+contractid;
	}else{
		uri="";
	}
	$('#contractfileedit').datagrid({
		width : 'auto',
		height : 130,
	    nowrap : true,
		striped : true,
		border : true,
		collapsible : false,// 是否可折叠的
		//fit : true,// 自动大小
		fitColumns:true,
		url : uri,
		//pageSize:5,
		remoteSort : true,					
		idField : 'id',
		singleSelect : false,// 是否单选
		onLoadSuccess:function(data){
		 if(a==true){
		   var count=data.rows.length;
		   for(var i=0;i<count;i++){
			   var row={
						"id":id,
						"url":data.rows[i].url,
						"fileName":data.rows[i].fileName
					};
				fileCachearrat.push(row);
				id++;
		   }
		   contractFile(false);
		 }
		   
	    },
		checkOnSelect : false,
		selectOnCheck : true,
		//pagination : true,// 分页控件
		rownumbers : true,// 行号
		columns : [ [
		   {
			 field : 'url',
		     title : '附件名称',
			 width : 50,
			 align:'center',
			 formatter : function(value, row, index) {
					var href =  getRootName() + '/commonFileUpload/CosDownload?fileDir=' + value;
					href = href.replace(/['\/\/']/g, "\\");
					return '<a title="下载文件" class="easyui-tooltip"  href="' + href + '">' + row.fileName + '</a>';
				}
		   },{
				field : 'ids',
				title : '操作',
				width : 50,
				align : 'center',
				formatter : function(value, row, index) {
					return '<a style="cursor: pointer;" onclick="javascript:platform.contractManager.contractManagerEdit.doContractFileDel('
						+ row.id
						+ ');"><img src="'
						+ path
						+ '/style/images/icon/icon_delete.png" title="删除" /></a>';
				}
			} 
	    ]]
		
	});
	if(a==false){
		var gridDate={
				"total":fileCachearrat.length,
				"rows":fileCachearrat
				};
		$('#contractfileedit').datagrid('loadData',gridDate);
	}
}
/**
 * 组装附件缓存
 */
function initAttachmentArray(path,name){
	var row={
			"id":id,
			"url":path,
			"fileName":name
		};
	fileCachearrat.push(row);
	id++;
	var gridDate={
			"total":fileCachearrat.length,
			"rows":fileCachearrat
			};
	$('#contractfileedit').datagrid('loadData',gridDate);
	reloadTableCommon('contractfileedit');
}
/**
 *删除附件缓存
 * 
 */
function doContractFileDel(id){
	var index = $('#contractfileedit').datagrid('getRowIndex',id);
	fileCachearrat.splice(index,1);
	var gridDate={
			"total":fileCachearrat.length,
			"rows":fileCachearrat
			};
	$('#contractfileedit').datagrid('loadData',gridDate);
	reloadTableCommon('contractfileedit');
}
/*
 * 获取已付款总数
 */
function payMomey_count(){
	var path = getRootName();
    var uri = path + "/contractmanager/projectcontractpaymentcount?contractid="+contractid;
    var ajax_param = {
			url : uri,
			type : "post",
			datdType : "json",
			error : function() {
				$.messager.alert("错误", "ajax_error", "error");
			},
			success : function(data) {
				var new_date=$("#ipt_moneyAmount_update").val();
				if((new_date-data)>=0){
					contractMangerInfo_edit();
				}else{
					$.messager.alert("提示", "输入合同总金额"+new_date+"（万元）小于实际付款合计"+data+"（万元），请重新输入！");
				}
			}
		 };
		 ajax_(ajax_param);
}
/**
 * 合同信息编辑
 */
function contractMangerInfo_edit(){
	var  validTimeEnd_temp=$('#ipt_validTimeEnd_update').datebox('getValue');
	var  signTime_temp=$('#ipt_signTime_update').datebox('getValue');
	var  validTimeBegin_temp=$('#ipt_validTimeBegin_update').datebox('getValue');
	var  validTimeEnd_date=new Date(validTimeEnd_temp);
	var  signTime_date=new Date(signTime_temp);
	var  validTimeBegin_date=new Date(validTimeBegin_temp);
	if (checkInfo('#editcontractManager')) {
		if(signTime_date>validTimeBegin_date){
			$.messager.alert("错误", "有效期开始时间不能在签订时间之前！", "error");
		    $('#ipt_validTimeBegin_update').datebox('setValue',validTimeBegin_temp_init);
		}else if(validTimeEnd_date<validTimeBegin_date){
			$.messager.alert("错误", "有效期结束时间不能在开始时间之前！", "error");
		    $('#ipt_validTimeEnd_update').datebox('setValue',validTimeEnd_temp_init);
		}else{
		    var path = getRootName();
		    var uri = path + "/contractmanager/contractManagerInfo_update";
		    var ajax_param = {
			url : uri,
			type : "post",
			datdType : "json",
			data : {
					"id" : contractid,
					"title" : $("#ipt_title_update").val(),//合同标题
					"signTime" : $("#ipt_signTime_update").datebox("getValue") ,//签订日期
					"contractNo" : $("#ipt_contractNo_update").val(),//合同编号
					"validTimeBegin" : $("#ipt_validTimeBegin_update").datebox("getValue"),//合同有效期_开始
					"validTimeEnd":$("#ipt_validTimeEnd_update").datebox("getValue"),//合同有效期_结束
					"owner" : $("#ipt_owner_update").val(),//甲方
					"partyB" : $("#ipt_partyB_update").val(),//乙方
					"cashDeposit" : $("#ipt_cashDeposit_update").val(),//保证金
					"moneyAmount" : $("#ipt_moneyAmount_update").val(),//合同金额
					"projectId" : $("#ipt_projectId_update").combobox("getValue"),
					"contractSummary" : $("#ipt_contractSummary_update").val(),
					"contractType":$("#ipt_contractType_update").combobox("getValue"),
					"responserName" :$("#ipt_responserName_update").val(),
					"fileCachearrat":JSON.stringify(fileCachearrat),
					"t" : Math.random()	
			},
			error : function() {
				$.messager.alert("错误", "ajax_error", "error");
			},
			success : function(data) {
				if (true == data || "true" == data) {
					window.frames['component_2'].platform.contractManager.contractManagerList.reloadTable();
					parent.$('#popWin').dialog('close');
					
				} else {
					platform.contractManager.contractManagerList.reloadTable();
					$.messager.alert("提示", "合同信息修改失败！", "error");
					
				}
			}
		 };
		 ajax_(ajax_param);
	 }
	}
}
/*
 * 添加计划
 */
function toputPlan(){
	var uri=getRootName() + '/contractmanager/contractPaymentPlanadd?contractid='+contractid;
	//var aa=parent.$('#popWin2').parent().attr('class');
	//if(aa=="panel window"){
		//parent.$('#popWin2').window('open').panel('refresh',uri);
		//parent.$('#popWin2').panel('setTitle','添加计划');
	//}else{
	    parent.$('#popWin2').window({//parent.$('#popWin2')
		  title : '添加计划',
          width : 700,
          height: 'auto',
	      draggable:true,
	      minimizable : false,
	      maximizable : false,
		  collapsible : false,
		  modal : true,
		  href : uri
	});	 
	//}
}
/*
 * 计划外添加
 */
function toputNoPlan(){
	var uri= getRootName() + '/contractmanager/contractPaymentNoPlanadd?contractid='+contractid;
	//var aa=parent.$('#popWin2').parent().attr('class');
	//if(aa=="panel window"){
		//parent.$('#popWin2').window('open').panel('refresh',uri);
		//parent.$('#popWin2').panel('setTitle','计划外付款');
	//}else{
		parent.$('#popWin2').window({
			title : '计划外付款',
	        width : 700,
	        height :'auto',
	        minimizable : false,
			maximizable : false,
			collapsible : false,
			draggable:true,
			modal : true,
			href : uri
		});	 
	//}
	
}
/*
 * 修改付款信息
 */
function toContractPaymentUpdate(id){
	var uri=getRootName() + '/contractmanager/contractPaymentInfo_update?paymentID='+id;
	//var aa=parent.$('#popWin2').parent().attr('class');
	//if(aa=="panel window"){
		//parent.$('#popWin2').window('open').panel('refresh',uri);
		//parent.$('#popWin2').panel('setTitle','修改付款信息');
	//}else{
		parent.$('#popWin2').window({
			title : '修改付款信息',
	        width : 700,
	        height :'auto',
	        minimizable : false,
			maximizable : false,
			collapsible : false,
			draggable:true,
			modal : true,
			href : uri
		});	 
	//}
	
}
/*
 * 增加合同变更
 */
function toaddContractChane(){
	var uri=getRootName() + '/contractmanager/contractChaneInfo_add?contractid='+contractid+"&singtime="+singtime_temp_iniy;
	//var aa=parent.$('#popWin2').parent().attr('class');
	//if(aa=="panel window"){
		//parent.$('#popWin2').window('open').panel('refresh',uri);
		//parent.$('#popWin2').panel('setTitle','增加合同变更信息');
	//}else{
		parent.$('#popWin2').window({
			title : '增加合同变更信息',
	        width : 800,
	        height :'auto',
	        minimizable : false,
			maximizable : false,
			collapsible : false,
			draggable:true,
			modal : true,
			href : uri
		});	 
	//}
	
}
/*
 * 付款详细信息
 */
function toContractPaymentShowInfo(id){
	parent.$('#popWin2').window({
		title : '查看付款信息',
        width : 720,
        height :'auto',
        minimizable : false,
		maximizable : false,
		collapsible : false,
		modal : true,
		href : getRootName() + '/contractmanager/contractPaymentInfoDetail?paymentID='+id
	});	 
	
}
/*
 * 变更维护信息
 */
function toProjectContractChangeShowInfo(id){
	parent.$('#popWin2').window({
		title : '查看变更信息',
        width : 720,
        height :'auto',
        minimizable : false,
		maximizable : false,
		collapsible : false,
		modal : true,
		href : getRootName() + '/contractmanager/contractchangeInfoDetail?id='+id
	});	 
}
/*
 * 修改合同变更
 */
function toProjectContractChangeUpdate(id){
	var uri=getRootName()+'/contractmanager/contractChaneInfoupdateview?id='+id+"&singtime="+singtime_temp_iniy;
	//var aa=parent.$('#popWin2').parent().attr('class');
	//if(aa=="panel window"){
		//parent.$('#popWin2').window('open').panel('refresh',uri);
		//parent.$('#popWin2').panel('setTitle','修改合同变更信息');
	//}else{
		parent.$('#popWin2').window({
			title : '修改合同变更信息',
	        width : 800,
	        height :'auto',
	        minimizable : false,
			maximizable : false,
			collapsible : false,
			draggable:true,
			modal : true,
			href : uri
		});	 
	//}
}
/*
 * 删除合同变更信息
 */
function doProjectContractChangeDel(id){
	var path = getRootName();
	$.messager.confirm("提示", "确定删除此信息?", function(r) {
		if (r == true) {
			var uri = path + "/contractmanager/contractChaneInfodelete";
			var ajax_param = {
				url : uri,
				type : "post",
				datdType : "json",
				data : {
					"id" : id,
					"t" : Math.random()
				},
				error : function() {
					$.messager.alert("错误", "ajax_error", "error");
				},
				success : function(data) {
					if (true == data || "true" == data) {
						$.messager.alert("提示", "变更信息删除成功！", "info");
						reloadTableCommon('edit_contract_biange_Record');
					} 
				}
			};
			ajax_(ajax_param);
		}
	});
}
/*
 * 批量删除合同变更信息
 */
function doProjectContractChangeBatchDel(){
	var aa=$('#edit_contract_biange_Record').datagrid('getChecked');
	//console.log(aa[0].id);
	var path = getRootName();
	var ids = null;
	for ( var i = 0; i < aa.length; i++){
		var id = aa[i].id;
		if (null == ids){
			ids = id;
		}else{
			ids += ',' + id;
		}
	}
	if (null != ids){
		$.messager.confirm("提示", "确定删除所选中项?", function(r){
			if (r == true){
				var uri = path + "/contractmanager/contractChaneInfoBathchdelete?ids="+ids;
				var ajax_param = {
					url : uri,
					type : "post",
					datdType : "json",
					data : {
					   "t" : Math.random()
					},
					success : function(data){
						if (true == data || "true" == data) {
							$.messager.alert("提示", "信息删除成功！", "info");
							reloadTableCommon('edit_contract_biange_Record');
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
/*
 * 删除合同付款信息
 */
function doContractPaymentDel(paymentID){
	var path = getRootName();
	$.messager.confirm("提示", "确定删除此信息?", function(r) {
		if (r == true) {
			var uri = path + "/contractmanager/contracepaymentInfodelete";
			var ajax_param = {
				url : uri,
				type : "post",
				datdType : "json",
				data : {
					"paymentID" : paymentID,
					"t" : Math.random()
				},
				error : function() {
					$.messager.alert("错误", "ajax_error", "error");
				},
				success : function(data) {
					if (true == data || "true" == data) {
						$.messager.alert("提示", "合同付款记录删除成功！", "info");
						reloadTableCommon('contract_payRecord_table');
					} 
				}
			};
			ajax_(ajax_param);
		}
	});
}

//获取统计
function total_amount(data){
	//var rows=$('#contract_payRecord_table').datagrid('getRows');
	//var ptotal = 0//计算listprice的总和
	 //for (var i = 0; i < rows.length; i++) {
        // ptotal+=rows[i]['amount'];
     //}
	//console.log(data);
	var path = getRootName();
	var uri = path + '/contractmanager/projectcontractpaymentcount?contractid='+contractid;
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
			var record={
					'paymentID': '',
					'planPayTime':'总共',
					'planPayAmount': '',
					'paymentTime':'',
					'amount':'0',
					'handler':'',
					'paymentIDs':''
					
				};
			$('#contract_payRecord_table').datagrid('appendRow',record);
		},
		success : function(data) {
			var data1=0;
			if (data!=null && data!='') {
				data1=data;
			}
				var array=[];
				var record={
						'paymentID': '',
						'planPayTime':'总共',
						'planPayAmount': '',
						'paymentTime':'',
						'amount':data1,
						'handler':'',
						'paymentIDs':''
						
					};
				array.push(record);
				$('#contract_payRecord_table').datagrid('reloadFooter',array);  
		}
	};
	ajax_(ajax_param);
    
}
/*
 * 刷新主页面
 */
function reloadiframe(){
	window.frames['component_2'].platform.contractManager.contractManagerList.reloadTable();
	parent.$('#popWin').window('close');
}
return contractManagerEdit;

})();