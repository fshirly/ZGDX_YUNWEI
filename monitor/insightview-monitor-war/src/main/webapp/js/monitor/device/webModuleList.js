$(document).ready(function() {
	var jmxType = $("#jmxType").val();
//	if(jmxType==19 || jmxType=="19"){
//		$("#websphereTitle").show();
//		$("#tomcatTitle").hide();
//	}else if(jmxType==20 || jmxType=="20"){
//		$("#websphereTitle").hide();
//		$("#tomcatTitle").show();
//	}
	var parentMoId = $("#parentMoId").val();
	doInitWebModuleTables(parentMoId);// 数据源列表
	var flag = $("#flag").val();
	if(flag ==null || flag =="" || flag=="null"){ 
		$('#tblWebModule').datagrid('hideColumn','moId');
	}
});


// 数据源列表
function doInitWebModuleTables(parentMoId) {
	var jmxType = $("#jmxType").val();
	 var path = getRootName();
	 $('#tblWebModule')
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
	      url : path + '/monitor/DeviceTomcatManage/getWebModuleList?parentMoId='+parentMoId+'&jmxType='+jmxType,
	      idField : 'fldId',
	      singleSelect : false,// 是否单选
	      checkOnSelect : false,
	      selectOnCheck : true,
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
		         field : 'status',
		         title : '状态',
		         width : 80,
		         align : 'center'
	        },
	        {
		         field : 'warName',
		         title : 'WAR包名称',
		         width : 120,
		         align : 'center'
	        },
	        {
		         field : 'parentServerName',
		         title : '应用服务名称',
		         width : 150,
		         align : 'center'
	        },
	        {
		         field : 'parentMoName',
		         title : '应用名称',
		         width : 150,
		         align : 'center'
	        }
	        
	        ] ]
	       
	     });
	 $(window).resize(function() {
		    $('#tblWebModule').resizeDataGrid(0, 0, 0, 0);
		});
}

/**
 * 刷新表格数据
 */
function reloadTable() {
	$('#tblWebModule').datagrid('options').queryParams = {
		"parentMoName" : $("#txtParentMOName").val(),
		"warName" : $("#txtWarName").val()
	};	
	reloadTableCommon_1('tblWebModule');
}

function reloadTableCommon_1(dataGridId) {
	$('#' + dataGridId).datagrid('load');
	$('#' + dataGridId).datagrid('uncheckAll');
}

function sel(moid){
	if(window.opener) { 
	     fWindowText1 = window.opener.document.getElementById("ipt_webModuleId"); 
	     fWindowText1.value = moid; 
	     window.opener.findWebModuleInfo();
	     window.close();
	} 
}  