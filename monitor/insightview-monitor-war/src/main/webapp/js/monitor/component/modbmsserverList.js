var flag='';
$(document).ready(function() {
	flag=$('#flag').val();
	doInitTable();
	if(flag=='choose'){ 
		$('#tblDBMSServer').datagrid('showColumn','moid');
	}else{
		$('#tblDBMSServer').datagrid('hideColumn','moid');
	}
});

/**
 * 加载页面并初始化表格
 * @return
 */
function doInitTable(){
	var path = getRootName();
	$('#tblDBMSServer')
			.datagrid(
					{ 
						iconCls : 'icon-edit',// 图标
						width : 1500,
						height : 'auto',
						nowrap : false,
						striped : true,
						border : true,
						collapsible : false,// 是否可折叠的
						fit:true,// 自动大小
						fitColumns:true,
						url : path + '/monitor/perfTask/selectDBMSServerList?&moClassId='+$("#moClassId").val(),
						remoteSort : true,
						onSortColumn:function(sort,order){
							// alert("sort:"+sort+",order："+order+"");
						},
						idField : 'fldId',
						singleSelect : false,// 是否单选
						checkOnSelect : false,
						selectOnCheck : true,
						pagination : true,// 分页控件
						rownumbers : true,// 行号
						
						columns : [ [
						        {
									field : 'moid',
									title : '',
									width : 40,
									align : 'center',
									formatter : function(value, row, index) {
							        	if(row.moClassId != 54){
							        		return '<a style="cursor: pointer;" onclick="javascript:sel('
							        		+ value
							        		+ ');">选择</a>';
							        	}else{
							        		 var param = "&quot;" + value
												+ "&quot;,&quot;" + row.ip
												+ "&quot;,&quot;" + row.port
												+ "&quot;,&quot;" + row.domainid
												+ "&quot;,&quot;" + row.domainName
												+ "&quot;"
							        		return '<a style="cursor: pointer;" onclick="javascript:selDb2('
							        		+ param
							        		+ ');">选择</a>';
							        	}
									}
						        },
								{  
									field : 'moname',
									title : '数据库名称',  
									width : 100,
									align : 'center',
								},
								{
									field : 'moalias',
									title : '数据库别名',
									width : 70,
									align : 'center',
								},
								{
									field : 'dbmstype',
									title : '数据库类别', 
									width : 40,
									align : 'center',
								},
								{
									field : 'serverversion',
									title : '服务版本', 
									width : 50,
									align : 'center',
									sortable : true,
								},
								{
									field : 'ip',
									title : '访问地址', 
									width : 100,
									align : 'center',
								},
								{
									field : 'port',
									title : '访问端口', 
									width : 30,
									align : 'center',
								},
								{
									field : 'username',
									title : '访问用户名', 
									width : 50,
									align : 'center',
								},
								{
									field : 'password',
									title : '访问口令', 
									width : 30,
									align : 'center',
								},
								{
									field : 'operstatus',
									title : '可用状态',
									width : 30,
									align : 'center',
									formatter:function(value,row){
										var rtn = "";
										if(value=="1"){
											rtn = "UP";
										}else if(value=="2"){
											rtn = "DOWN";
										}else if(value=="3"){
											rtn = "未知";
										}
										return rtn; 
									}
								},
								{
									field : 'adminstatus',
									title : '操作状态',
									width : 30,
									align : 'center',
									formatter:function(value,row){
										var rtn = "";
										if(value=="0"){
											rtn = "正常";
										}else if(value=="1"){
											rtn = "新增";
										}else if(value=="2"){
											rtn = "删除";
										}else if(value=="3"){
											rtn = "修改";
										}else {
											rtn = "锁定";
										}
										return rtn; 
									}
								},
								{
									field : 'alarmlevel',
									title : '告警级别',
									width : 30,
									align : 'center',
									sortable : true,
									formatter:function(value,row){
										var rtn = "";
										if(value=="0"){
											rtn = "正常";
										}else if(value=="1"){
											rtn = "紧急";
										}else if(value=="2"){
											rtn = "严重";
										}else if(value=="3"){
											rtn = "一般";
										}else if(value=="4"){
											rtn = "提示";
										}else{
											rtn = "未确定";
										}
										return rtn; 
									}
								},
								{
									field : 'domainName',
									title : '管理域', 
									width : 50,
									align : 'center',
								}]]
					});
		$(window).resize(function() {
		    $('#tblDBMSServer').resizeDataGrid(0, 0, 0, 0);
		});
}

/**
 * 更新表格数据
 * @return
 */
function reloadTable() { 
	var ip = $("#ip").val(); 
	$('#tblDBMSServer').datagrid('options').queryParams = {
		"ip" : ip
	};
	reloadTableCommon_1('tblDBMSServer');
}

function reloadTableCommon_1(dataGridId) {
	$('#' + dataGridId).datagrid('load');
	$('#' + dataGridId).datagrid('uncheckAll');
}

/**
 * 选择 
 * @return
 */
function sel(moid){
	if(window.opener) { 
	     fWindowText1 = window.opener.document.getElementById("ipt_dbmsServerId"); 
	     fWindowText1.value = moid; 
    	 window.opener.findDBMSServerInfo();
	     window.close();
	} 
}  

function selDb2(moid,ip,port,domainid,domainName){
	if(window.opener) { 
	     fWindowText1 = window.opener.document.getElementById("ipt_dbmsServerId"); 
	     fWindowText2 = window.opener.document.getElementById("ipt_deviceIp"); 
	     fWindowText3 = window.opener.document.getElementById("ipt_dbPort"); 
	     fWindowText4 = window.opener.document.getElementById("ipt_domainId");
	     fWindowText5 = window.opener.document.getElementById("ipt_domainName"); 
	     fWindowText1.value = moid;
	     fWindowText2.value = ip;
	     fWindowText3.value = port;
	     fWindowText4.value = domainid;
	     if(domainName != null &&domainName != "null"){
	    	 fWindowText5.value = domainName;
	     }else{
	    	 fWindowText5.value = "";
	     }
	     window.opener.findDBMSServerInfo();
	     window.close();
	} 
}  

