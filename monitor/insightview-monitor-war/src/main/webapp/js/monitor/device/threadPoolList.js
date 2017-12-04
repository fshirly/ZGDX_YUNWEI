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
	doThreadPoolInitTables(parentMoId);// 数据源列表
	var flag = $("#flag").val();
	if(flag ==null || flag =="" || flag=="null"){ 
		$('#tblThreadPool').datagrid('hideColumn','moId');
	}
});


// 数据源列表
function doThreadPoolInitTables(parentMoId) {
	var jmxType = $("#jmxType").val();
	 var path = getRootName();
	 $('#tblThreadPool')
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
	      url : path + '/monitor/DeviceTomcatManage/getThreadPoolList?parentMoId='+parentMoId+'&jmxType='+jmxType,
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
		         field : 'threadName',
		         title : '池名称',
		         width : 120,
		         align : 'left'
	        },
	        {
		         field : 'ip',
		         title : '服务IP',
		         sortable:true,
		         width : 90,
		         align : 'left'
	        },
	        {
		         field : 'currThreads',
		         title : '当前线程数',
		         width : 70,
		         align : 'center'
	        },
	        {
		         field : 'busyThreads',
		         title : '繁忙线程数',
		         width : 70,
		         align : 'center'
	        },
	        {
		         field : 'maxConns',
		         title : '最大线程数',
		         width : 80,
		         align : 'center'
	        }
	        ,
	        {
		         field : 'minConns',
		         title : '最小线程数',
		         width : 80,
		         align : 'center'
	        }
	        ,
	        {
		         field : 'inactivityTimeOut',
		         title : '线程不活跃超时',
		         width : 80,
		         align : 'center'
	        } ,
	    /**    {
		         field : 'growable',
		         title : '是否允许超额',
		         width : 80,
		         align : 'center'
	        }
	        , */
	        {
		         field : 'initCount',
		         title : '初始线程数',
		         width : 80,
		         align : 'center'
	        }
	        ,
	        {
		         field : 'maxSpareSize',
		         title : '最大空闲线程数',
		         width : 80,
		         align : 'center'
	        }
	        
	        ] ]
	       
	     });
	 $(window).resize(function() {
		    $('#tblThreadPool').resizeDataGrid(0, 0, 0, 0);
		});
}

/**
 * 刷新表格数据
 */
function reloadTable() {
	$('#tblThreadPool').datagrid('options').queryParams = {
		"threadName" : $("#txtThreadName").val(),
		"ip" : $("#txtIP").val()
	};	
	reloadTableCommon_1('tblThreadPool');
}

function reloadTableCommon_1(dataGridId) {
	$('#' + dataGridId).datagrid('load');
	$('#' + dataGridId).datagrid('uncheckAll');
}

function sel(moid){
	if(window.opener) { 
	     fWindowText1 = window.opener.document.getElementById("ipt_threadPoolId"); 
	     fWindowText1.value = moid; 
	     window.opener.findThreadPoolInfo();
	     window.close();
	} 
}  