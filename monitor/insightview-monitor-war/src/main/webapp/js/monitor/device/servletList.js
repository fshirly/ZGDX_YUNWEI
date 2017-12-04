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
	doInitServletTables(parentMoId);// 数据源列表
	var flag = $("#flag").val();
	if(flag ==null || flag =="" || flag=="null"){ 
		$('#tblServlet').datagrid('hideColumn','moId');
	}
});


// 数据源列表
function doInitServletTables(parentMoId) {
	var jmxType = $("#jmxType").val();
	 var path = getRootName();
	 $('#tblServlet')
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
	      url : path + '/monitor/DeviceTomcatManage/getServletList?parentMoId='+parentMoId+'&jmxType='+jmxType,
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
		         title : '应用名称',
		         width : 120,
		         align : 'center'
	        },
	        {
		         field : 'warName',
		         title : '所属WAR包名称',
		         width : 120,
		         align : 'center'
	        },
	        {
		         field : 'servletName',
		         title : 'Servlet名称',
		         width : 150,
		         align : 'center'
	        },
	        {
		         field : 'ip',
		         title : '服务IP',
		         width : 150,
		         sortable:true,
		         align : 'center'
	        }
	        ] ]
	       
	     });
	 $(window).resize(function() {
		    $('#tblServlet').resizeDataGrid(0, 0, 0, 0);
		});
}

/**
 * 刷新表格数据
 */
function reloadTable() {
	$('#tblServlet').datagrid('options').queryParams = {
		"parentMoName" : $("#txtParentMOName").val(),
		"warName" : $("#txtWarName").val(),
		"ip" : $("#txtIP").val()
	};	
	reloadTableCommon_1('tblServlet');
}

function reloadTableCommon_1(dataGridId) {
	$('#' + dataGridId).datagrid('load');
	$('#' + dataGridId).datagrid('uncheckAll');
}

function sel(moid){
	if(window.opener) { 
	     fWindowText1 = window.opener.document.getElementById("ipt_servletId"); 
	     fWindowText1.value = moid; 
	     window.opener.findServletInfo();
	     window.close();
	} 
}  