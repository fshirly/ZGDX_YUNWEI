$(document).ready(function() {
	var parentMoId = $("#parentMoId").val();
//	if(jmxType==19 || jmxType=="19"){
//		$("#websphereTitle").show();
//		$("#tomcatTitle").hide();
//	}else if(jmxType==20 || jmxType=="20"){
//		$("#websphereTitle").hide();
//		$("#tomcatTitle").show();
//	}
	doJ2eeAppInitTables(parentMoId);// 数据源列表
	var flag = $("#flag").val();
	if(flag ==null || flag =="" || flag=="null"){ 
		$('#tblJ2eeApp').datagrid('hideColumn','moId');
	}
});


// 数据源列表
function doJ2eeAppInitTables(parentMoId) {
	var jmxType = $("#jmxType").val();
	var path = getRootName();
	if(parentMoId!=null && parentMoId !="" && parentMoId !="null"){
		var uri= path + '/monitor/DeviceTomcatManage/getdoJ2eeAppList?parentMoId='+parentMoId+'&jmxType='+jmxType
	}else{
		var uri= path + '/monitor/DeviceTomcatManage/getdoJ2eeAppList?jmxType='+jmxType
	}
	 $('#tblJ2eeApp')
	   .datagrid(
	     {
	      iconCls : 'icon-edit',// 图标
	      width : 700,
	      height : 'auto',
	      nowrap : false,
	      striped : true,
	      border : true,
	      collapsible : false,// 是否可折叠的
	      fit : true,// 自动大小
	      fitColumns:true,
	      url : uri,
	      idField : 'fldId',
	      singleSelect : false,// 是否单选
	      checkOnSelect : false,
	      selectOnCheck : true,
		  checkOnSelect : false,
		  pagination : true,// 分页控件
	      rownumbers : true,// 行号
	      columns : [ [
			{
			   field : 'moId',
			   title : '',
			   width : 50,
			   align : 'center',
			   formatter : function(value, row, index) {
			    	   var to = "&quot;" + value
						+ "&quot;,&quot;" + row.jmxType
						+ "&quot;"
			   			return '<a style="cursor: pointer;" onclick="javascript:sel('+ to+');">选择</a>';
			   }
			},
	        {
		         field : 'parentMoName',
		         title : '应用服务名称',
		         width : 100,
		         align : 'center'
	        },
	        {
		         field : 'appName',
		         title : '应用名称',
		         width : 100,
		         align : 'center'
	        },
	        {
		         field : 'uri',
		         title : '资源位置',
		         width : 150,
		         align : 'center'
	        }
	        ] ]
	       
	     });
	 $(window).resize(function() {
		    $('#tblJ2eeApp').resizeDataGrid(0, 0, 0, 0);
		});
	 }

/**
 * 刷新表格数据
 */
function reloadTable() {
	$('#tblJ2eeApp').datagrid('options').queryParams = {
		"parentMoName" : $("#txtParentMoName").val(),
		"appName" : $("#txtAppName").val(),
	};	
	reloadTableCommon_1('tblJ2eeApp');
}

function reloadTableCommon_1(dataGridId) {
	$('#' + dataGridId).datagrid('load');
	$('#' + dataGridId).datagrid('uncheckAll');
}

function sel(moid){
	if(window.opener) { 
	     fWindowText1 = window.opener.document.getElementById("ipt_j2eeAppId"); 
	     fWindowText1.value = moid; 
	     window.opener.findJ2eeAppInfo();
	     window.close();
	} 
} 