f.namespace('platform.contractManager');
platform.contractManager.contractManagerList = (function(){
var contractManagerList={
	reloadTable:function(){
	  reloadTable();
	},
	toShowInfo:function(id){
		toShowInfo(id);
	},
	toEditPage:function(id,a){
		toEditPage(id,a);
	},
	doProjectContractdelete:function(id){
		doProjectContractdelete(id);
	}
};
$(function() {
	var path = getRootName();
	doInitTable();//合同信息列表初始化
});
/*
 * 初始化表格
 */
function doInitTable(){
	var path = getRootName();
	var uri=path+'/contractmanager/contractManagerInfo_list';
	$('#contractManager').datagrid({
		width : 'auto',
		height : 'auto',
	    nowrap : true,
		striped : true,
		border : true,
		collapsible : false,// 是否可折叠的
		fit : true,// 自动大小
		fitColumns:true,
		url : uri,
		remoteSort : true,					
		idField : 'id',
		singleSelect : false,// 是否单选
		checkOnSelect : false,
		selectOnCheck : true,
		pagination : true,// 分页控件
		rownumbers : true,// 行号
		/*onLoadSuccess: function(data){
		 window.console.log(data); 
		},*/
		toolbar :[{
			text : '新建',
			iconCls : 'icon-add',
			handler : function(){
			     toadd_show();
			}	
		},{
		   text:'删除',
		   iconCls : 'icon-cancel',
		   handler : function(){
			doBatchProjectContractDel();
		}	
			
		}],
		columns : [ [
		   {
			 field : 'id',
             checkbox : true   
		   },{
			 field : 'title',
		     title : '合同标题',
			 width : 120,
			 align:'center',
			 styler: function(value,row,index){
				return 'word-break:break-all;';
			}
		   },{
			 field : 'signTime',
			 title : '签订日期',
		     width : 120, 
		     align:'center',
		     formatter:function(value, row, index){
			   if(value!=null && value!="" ){
				   return formatDate(new Date(value), "yyyy-MM-dd");
			   }
		     }
		     
		   },{
			field : 'nextPayDate',
		    title : '下一笔计划付款日期',
			width : 120,
			align:'center',
			formatter:function(value, row, index){
			   if(value!=null && value!="" ){
				   if (row.nextAmount!=null && row.nextAmount!='' ){
					   return formatDate(new Date(value), "yyyy-MM-dd");
				   }else{
					   return "<div style='color:red'>"+formatDate(new Date(value), "yyyy-MM-dd")+"</div>"
				   }
				  
			   }
		     }
		   },{
			field : 'nextPayment',
			title : '下一笔计划付款金额(万元)',
		    width : 120,
		    align:'center'
		   },{
			field : 'owner',
			title : '甲方',
			width : 120,
			align:'center',
			 styler: function(value,row,index){
				return 'word-break:break-all;';
			}
		  },{
			field : 'partyB',
		    title : '乙方',
			width : 120,
			align:'center',
			styler: function(value,row,index){
				return 'word-break:break-all;';
			}
		  },{
				field : 'ids',
				title : '操作',
				width : 180,
				align : 'center',
				formatter : function(value, row, index) {
					return '<a style="cursor: pointer;" onclick="javascript:platform.contractManager.contractManagerList.toShowInfo('
						+ row.id
						+ ');"><img src="'
						+ path
						+ '/style/images/icon/icon_view.png" title="查看" /></a>&nbsp;<a href="javascript:void(0)" style="cursor: pointer;" title="付款" onclick="javascript:platform.contractManager.contractManagerList.toEditPage('+
						+ row.id
						+',1)" ><img src="'
						+path
						+ '/style/images/icon/icon_cost.png" title="付款" /></a>&nbsp;<a href="javascript:void(0)" style="cursor: pointer;" title="变更" onclick="javascript:platform.contractManager.contractManagerList.toEditPage('
						+row.id
						+',2)"><img src="'
						+path
						+'/style/images/icon/icon_change.png" title="变更"/></a>&nbsp;<a style="cursor: pointer;" title="修改" onclick="javascript:platform.contractManager.contractManagerList.toEditPage('
						+ row.id
						+ ',0)"><img src="'
						+ path
						+ '/style/images/icon/icon_modify.png" title="修改" /></a> &nbsp;<a style="cursor: pointer;" title="删除" onclick="javascript:platform.contractManager.contractManagerList.doProjectContractdelete('
						+ row.id
						+ ')"><img src="'
						+ path
						+ '/style/images/icon/icon_delete.png" title="删除" /></a>';
				}
			} 
	    ]]
		
	});
	// 浏览器窗口大小变化时，datagrid表格自适应窗口大小变化
	$(window).resize(function() {
		$('#contractManager').resizeDataGrid(0, 0, 0, 0);
	});
}
/**
 * 合同信息详情页
 */
function toShowInfo(id){
	//debugger;
	var uri=getRootName() + '/contractmanager/contractManager_detail?contractid='+id;
	//var aa=parent.$('#popWin').parent().attr('class');
	//if(aa=="panel window"){
		//parent.$('#popWin').window('open').panel('refresh',uri);
		//parent.$('#popWin').panel('setTitle','合同信息详情');
	//}else{
		parent.$('#popWin').window({
			 title : '合同信息详情',
	         width : 800,
	         height :600,
	         minimizable : false,
			 maximizable : false,
			 collapsible : false,
			 draggable:true,
			 modal : true,
			 href : uri
			
		});
	//}
	
}
/**
 * 合同信息添加
 */
function toadd_show(id){
	var uri=getRootName() + '/contractmanager/contractManager_add';
	//var aa=parent.$('#popWin').parent().attr('class');
	//if(aa=="panel window"){
		//parent.$('#popWin').window('open').panel('refresh',uri);
		//parent.$('#popWin').panel('setTitle','合同信息添加');
	//}else{
		parent.$('#popWin').window({
			 title : '合同信息添加',
	         width : 830,
	         height : 650,
	         minimizable : false,
			 maximizable : false,
			 collapsible : false,
			 draggable:true,
			 modal : true,
			 href : uri
			
		});
	//}
	
}
/**
 * 合同信息编辑
 * @param id
 * @return
 */
function toEditPage(id,a){
	//debugger;
	var uri=getRootName() + '/contractmanager/contractManager_edit?contractid='+id+'&choosetab='+a;
	//var aa=parent.$('#popWin').parent().attr('class');
	//if(aa=="panel window"){
		//parent.$('#popWin').window('open').panel('refresh',uri);
		//parent.$('#popWin').panel('setTitle','合同信息编辑');
	//}else{
		parent.$('#popWin').window({
			 title : '合同信息编辑',
	         width : 800,
	         height : 620,
	         minimizable : false,
			 maximizable : false,
			 collapsible : false,
			 draggable:true,
			 modal : true,
			 href : uri
		});	 
	//}
		
}
/**
 * 查询配置项信息 条件(根据查询条件查询配置项信息)
 */
function reloadTable() {
	var title = $("#contractTitle").val();//合同标题
	var validTimeBegin = $("#timeBegin").datebox('getValue');//合同有效期开始时间timeBegin
	var validTimeEnd= $("#timeEnd").datebox('getValue');//合同有效期结束时间
	var owner= $("#jiafang").val();//甲方
	var partyB= $("#yifang").val();//乙方
	if(validTimeBegin && validTimeEnd) {
		if(new Date(validTimeBegin) > new Date(validTimeEnd)) {
			$.messager.alert('提示', '签订日期的开始时间不能大于结束时间！', 'info');
			return;
		}
	}
	var queryParams = {
		"title" :title ,
		"validTimeBegin" : validTimeBegin,
		"validTimeEnd" : validTimeEnd,
		"owner":owner,
		"partyB":partyB
	};
	$('#contractManager').datagrid('options').queryParams=queryParams;
	$('#contractManager').datagrid('load');
	$('#contractManager').datagrid('uncheckAll');
}
/*
 * 删除合同信息
 */
function doProjectContractdelete(id){
	var path = getRootName();
	$.messager.confirm("提示", "确定删除此信息?", function(r) {
		if (r == true) {
			var uri = path + "/contractmanager/projectcontraceInfodelete";
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
						$.messager.alert("提示", "合同信息删除成功！", "info");
						reloadTable();
					} 
				}
			}
			ajax_(ajax_param);
		}
	});
}
/*
 * 批量删除合同信息
 */
function doBatchProjectContractDel(){
	var aa=$('#contractManager').datagrid('getChecked');
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
				var uri = path + "/contractmanager/projectcontractInfoBathchdelete?ids="+ids;
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
return contractManagerList;
})()