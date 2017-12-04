f.namespace('platform.guzhangrizManager');
platform.guzhangrizManager.guzhangrizManagerlist = (function(){
var guzhangrizManagerlist={
	reloadTable:function(){
	  reloadTable();
	}
};
$(function() {
	var path = getRootName();
	doInitTable();
});
/*
 * 初始化表格
 */
function doInitTable(){
	var path = getRootName();
	var uri=path+'/guzhangrizManager/guzhangrizManagerInfo_list';
	$('#guzhangrizManager').datagrid({
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
		columns : [ [
		   {
			 field : 'tableName',
		     title : '备份表名',
			 width : 120,
			 align:'center'
		   },{
			field : 'originalTime',
		    title : '数据备份时间',
			width : 120,
			align:'center'
				
		   },{
			field : 'result',
			title : '备份结果',
		    width : 120,
		    align:'center',
		    formatter:function(value, row, index){
				   if(value == '1' ){
					   return "成功";
				   } else {
					   return "失败";
				   }
		     	}
		   },
		   {
				field : 'dataRestoreEndTime',
				title : '数据最近一次恢复时间',
			    width : 120,
			    align:'center',
			    formatter:function(value, row, index){
				    if(value!=null && value!="" ){
					    return formatDate(new Date(value), "yyyy-MM-dd hh:mm:ss");
				    }
				}
			},
			{
				field : 'id',
				title : '操作',
				width : 200,
				align : 'center',
				formatter : function(value, row, index) {

					/*var rt= '<a style="cursor: pointer;" onclick="javascript:toShowInfo('
							+ row.paramID
							+ ','
							+ "'"+row.paramValue+"'"
							+ ','
							+ "'"+row.paramName+"'"
							+ ');"><img src="'
							+ path
							+ '/style/images/icon/icon_view.png" title="查看" /></a> &nbsp;
							'<a style="cursor: pointer;" onclick="javascript:dataRestore(\"'+22+'\");"><img src="'
							+ path
							+ '/style/images/icon/icon_20.gif" alt="" title="数据恢复" /></a>';
					return rt;*/
					return "<a style=\"cursor: pointer;color:#1E90FF;\" " +
							"onclick=\"javascript:dataRestore('"+row.id+"');\">" +
							"<img title=\"数据恢复\" src='"+path+"/style/images/icon/icon_20.gif' />"
									"</a>";
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
	$('#guzhangrizManager').datagrid('options').queryParams=queryParams;
	$('#guzhangrizManager').datagrid('load');
}
return guzhangrizManagerlist;
})()
/**
 * 数据恢复
 */
function dataRestore(id) {
	var path = getRootName();
	$.ajax({  
        type : "POST",  //提交方式  
        url : path + '/guzhangrizManager/dataRestore',//路径  
        data : {  
            "id" : id  
        },//数据，这里使用的是Json格式进行传输  
        success : function(result) {
            if (result.success) {
            	$.messager.alert("执行消息","成功恢复"+result.successTol+"条数据","info");
            } else { 
            	$.messager.alert("执行消息","失败","info");
            }  
        },
        error : function() {
			$.messager.alert("错误", "ajax请求失败", "error");
		}
    });
} 