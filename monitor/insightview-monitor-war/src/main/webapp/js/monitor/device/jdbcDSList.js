$(document).ready(function() {
	var parentMoId = $("#parentMoId").val();
//	if(jmxType==19 || jmxType=="19"){
//		$("#websphereTitle").show();
//		$("#tomcatTitle").hide();
//	}else if(jmxType==20 || jmxType=="20"){
//		$("#websphereTitle").hide();
//		$("#tomcatTitle").show();
//	}
	doJdbcDSInitTables(parentMoId);// 数据源列表
	var flag = $("#flag").val();
	if(flag ==null || flag =="" || flag=="null"){ 
		$('#tblJdbcDS').datagrid('hideColumn','moId');
	}
});


// 数据源列表
function doJdbcDSInitTables(parentMoId) {
	var jmxType = $("#jmxType").val();
	 var path = getRootName();
	 $('#tblJdbcDS')
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
	      url : path + '/monitor/DeviceTomcatManage/getJdbcDSList?parentMoId='+parentMoId+'&jmxType='+jmxType,
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
		         field : 'dSName',
		         title : '数据源名称',
		         width : 80,
		         align : 'center'
	        },
	        {
		         field : 'jdbcDriver',
		         title : 'JDBC驱动',
		         width : 110,
		         align : 'center'
	        },
	        {
		         field : 'ip',
		         title : '服务IP',
		         width : 90,
		         sortable:true,
		         align : 'center'
	        },
	        {
		         field : 'maxActive',
		         title : '最大连接数',
		         width : 55,
		         align : 'center'
	        }
	        ,
	        {
		         field : 'minIdle',
		         title : 'Mix空闲连接数',
		         width : 65,
		         align : 'center'
	        }
	        ,
	        {
		         field : 'maxIdle',
		         title : 'Max空闲连接数',
		         width : 65,
		         align : 'center'
	        } ,
	        {
		         field : 'maxWait',
		         title : 'Max等待时间',
		         width : 65,
		         align : 'center'
	        }
	        ,
	        {
		         field : 'dBUrl',
		         title : '数据库连接串',
		         width : 200,
		         align : 'center'
	        }
	        ,
	        {
		         field : 'userName',
		         title : '用户名',
		         width : 50,
		         align : 'center'
	        }
	        ,
	        {
		         field : 'passWord',
		         title : '密码',
		         width : 50,
		         align : 'center'
	        }
	        ,
	        {
		         field : 'initialSize',
		         title : '初始大小',
		         width : 50,
		         align : 'center'
	        }
	        ] ]
	       
	     });
	 $(window).resize(function() {
		    $('#tblJdbcDS').resizeDataGrid(0, 0, 0, 0);
		});
	 }

/**
 * 刷新表格数据
 */
function reloadTable() {
	$('#tblJdbcDS').datagrid('options').queryParams = {
		"dSName" : $("#txtDSName").val(),
		"ip" : $("#txtip").val(),
	};	
	reloadTableCommon_1('tblJdbcDS');
}

function reloadTableCommon_1(dataGridId) {
	$('#' + dataGridId).datagrid('load');
	$('#' + dataGridId).datagrid('uncheckAll');
}

function sel(moid){
	if(window.opener) { 
	     fWindowText1 = window.opener.document.getElementById("ipt_jdbcDSId"); 
	     fWindowText1.value = moid; 
	     window.opener.findJdbcDSInfo();
	     window.close();
	} 
}  