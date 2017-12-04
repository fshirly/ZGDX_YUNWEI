f.namespace('platform.gurzManager');
platform.gurzManager.gurzManagerManagerlist = (function(){
var gurzManagerManagerlist={
	reloadTable:function(){
	  reloadTable();
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
	var uri=path+'/guzhangrizManager/gzManagerInfo_list';
	$('#guzrizlist').datagrid({
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
		idField : 'fileName',
		singleSelect : false,// 是否单选
		checkOnSelect : false,
		selectOnCheck : true,
		//pagination : true,// 分页控件
		rownumbers : true,// 行号
		columns : [ [
		   {
			 field : 'fileName',
		     title : '文件名',
			 width : 120,
			 align:'center'
		   },{
			 field : 'createTime',
			 title : '创建时间',
		     width : 120, 
		     align:'center'/*,
		     formatter:function(value, row, index){
			   if(value!=null && value!="" ){
				   return formatDate(new Date(value), "yyyy-MM-dd");
			   }
		     }*/
		     
		   },{
			field : 'id',
		    title : '下载',
			width : 120,
			align:'center',
			formatter:function(value, row, index){
				   var a="<a href='"+getRootName()+"/guzhangrizManager/gzManagerInfo_download?fileName="+row.fileName+"'>下载</a>";
				   return a;
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
 * 查询配置项信息 条件(根据查询条件查询配置项信息)
 */
function reloadTable() {
	var tablename = $("#tablename").val();
	var queryParams = {
		"tablename":tablename
	};
	$('#guzrizlist').datagrid('options').queryParams=queryParams;
	$('#guzrizlist').datagrid('load');
}
return gurzManagerManagerlist;
})()