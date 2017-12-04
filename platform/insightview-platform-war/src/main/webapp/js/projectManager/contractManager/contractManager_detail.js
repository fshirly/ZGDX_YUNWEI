f.namespace('platform.contractManager');
platform.contractManager.contractManagerDetail = (function(){
var contractManagerDetail={
	toShowInfoPaymeny:function(id){
	toShowInfoPaymeny(id);
   },
   toShowInfoChange:function(id){
	   toShowInfoChange(id); 
   }
};
$(function() {
	doInitContractBiangRecord();
	doContractPaymentTableDetail();
	contractFile();
});
/**
 * 合同变更记录
 * @return
 */
function doInitContractBiangRecord(){
	var path = getRootName();
	var uri=path+'/contractmanager/projectcontractchangelist?contractid='+contractid;
	$('#contractbiangeRecord_table').datagrid({
		width : 'auto',
		height : 470,
	    nowrap : true,
		striped : true,
		border : true,
		collapsible : false,// 是否可折叠的
		//fit : true,// 自动大小
		fitColumns:true,
		url : uri,
		remoteSort : true,					
		idField : 'providerID',
		singleSelect : false,// 是否单选
		checkOnSelect : false,
		selectOnCheck : true,
		pagination : true,// 分页控件
		rownumbers : true,// 行号
		/*onLoadSuccess: function(data){
		 window.console.log(data); 
		},*/
		columns : [ [
		    {
		  	    field : 'id',
		        checkbox : true   
		  	},{
		  	    field : 'title',
		  		title : '变更标题',
		  	    width : 120,
		  	},{
		  		field : 'changeTime',
		  		title : '变更时间',
		  		width : 120,
		  		formatter:function(value, row, index){
		  			   if(value!=null && value!="" ){
		  				   return formatDate(new Date(value), "yyyy-MM-dd");
		  			   }
		  		     }
		  	},{
		  	    field : 'confirmManName',
		  		title : '确认人',
		  	    width : 120,
		     },{
				field : 'ids',
				title : '操作',
				width : 180,
				align : 'center',
				formatter : function(value, row, index) {
					return '<a style="cursor: pointer;" onclick="javascript:platform.contractManager.contractManagerDetail.toShowInfoChange('
						+ row.id
						+ ');"><img src="'
						+ path
						+ '/style/images/icon/icon_view.png" title="查看" /></a>';
				}
			} 
	    ]]
		
	});
}
/**
 * 合同付款记录
 * @return
 */
function doContractPaymentTableDetail(){
	var path = getRootName();
	var uri=path+'/contractmanager/contractpayrecord?contractid='+contractid;
	$('#contractPayment_table_datail').datagrid({
		width : 'auto',
		height : 470,
	    nowrap : true,
		striped : true,
		border : true,
		//onLoadSuccess:total_amount,
		collapsible : false,// 是否可折叠的
		//fit : true,// 自动大小
		//fitColumns:true,
		url : uri,
		remoteSort : false,					
		idField : 'paymentID',
		singleSelect : false,// 是否单选
		checkOnSelect : false,
		selectOnCheck : true,
		pagination : true,// 分页控件
		rownumbers : true,// 行号
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
		 			   if(value!=null && value!="" ){
		 				   return formatDate(new Date(value), "yyyy-MM-dd");
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
		 				 width : 150,
		 				 align:'center'
		 			},{
		 				 field : 'handler',
		 			     title : '经手人',
		 				 width : 125,
		 				 align:'center'
		 			},{
		 				field : 'paymentIDs',
		 				title : '操作',
		 				width : 105,
		 				align : 'center',
		 				formatter : function(value, row, index) {
		 				return '<a style="cursor: pointer;" onclick="javascript:platform.contractManager.contractManagerDetail.toShowInfoPaymeny('
						+ row.paymentID
						+ ');"><img src="'
						+ path
						+ '/style/images/icon/icon_view.png" title="查看" /></a>'
		 				}
		 			}         
		 		]]
		
	});
	$(window).resize(function() {
        $('#contractPayment_table_datail').resizeDataGrid(0, 0, 0, 0);
    });
}
/*
 * 多文件表格
 */
function contractFile(){
	var path = getRootName();
	var uri=path+'/contractmanager/fileupdate_list?contractid='+contractid;
$('#contractfiledetail').datagrid({
	width : 750,
	height : 190,
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
	   }
    ]]
	
});
}
/*
 * 合同付款详情
 */
function toShowInfoPaymeny(id){
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
 * 合同变更详情
 */
function toShowInfoChange(id){
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
return contractManagerDetail;
})();