$(document).ready(function() {
	var flag=$('#flag').val();
	if(flag == "null" || flag =="" || flag ==null){ 
		doRollSEGInitTables();// 数据文件
		$('#tblRollSEG').datagrid('hideColumn','moID');
	}else{
		doInitChooseTable();
	}
});


// 数据文件
function doRollSEGInitTables() {
	 var path = getRootName();
	 var instanceMOID = $("#instanceMOID").val()
		if(instanceMOID == "null" || instanceMOID == null || instanceMOID == ""){
			instanceMOID = -1;
		}
	 $('#tblRollSEG')
	   .datagrid(
	     {
	      iconCls : 'icon-edit',// 图标
	      width : 520,
	      height : 'auto',
	      nowrap : false,
	      striped : true,
	      border : true,
	      singleSelect : false,// 是否单选
	      checkOnSelect : false,
	      selectOnCheck : true,
	      collapsible : false,// 是否可折叠的
	      fit : true,// 自动大小
	      fitColumns:true,
	      url : path + '/monitor/orclDbManage/listOrclRollSEG?instanceMOID='+instanceMOID,
	      idField : 'fldId',
	      pagination : true,// 分页控件
		  rownumbers : true,// 行号	
	      columns : [ [
			{
			    field : 'moID',
			    title : '',
			    width : 100,
			    align : 'center',
				formatter : function(value, row, index) {
					return '<a style="cursor: pointer;" onclick="javascript:sel('+ value+');">选择</a>';
				}
			   },
	        {
	         field : 'segName',
	         title : '段名称',
	         width : 100,
	         align : 'center',
	         formatter : function(value, row, index) {
		       	return '<a onclick="javascript:doOpenDetail('+ row.moID+ ');">'+value+"</a>";
		       }	
	         
	        },
	        {
	         field : 'dbName',
	         title : '数据库名称',
	         width : 100,
	         align : 'center'
	        },{
		         field : 'ip',
		         title : '服务IP',
		         width : 90,
		         sortable:true,
		         align : 'left'
	        },
	        {
	         field : 'tbsName',
	         title : '所属表空间名称',
	         width : 100,
	         align : 'center'
	        },
	        {
	         field : 'segSize',
	         title : '段大小',
	         width : 70,
	         align : 'center'
	        },
	        {
	         field : 'initialExtent',
	         title : 'Extent初始大小',
	         width : 120,
	         align : 'center'
	        },
	        {
	         field : 'pctIncrease',
	         title : 'Extent缺省增长百分比',
	         width : 150,
	         align : 'center'
	        },
	        {
	         field : 'minExtents',
	         title : 'Extent最小数量',
	         width : 100,
	         align : 'center'
	        },
	        {
	         field : 'maxExtents',
	         title : 'Extent最大数量',
	         width : 100,
	         align : 'center'
	        },
	        {
	         field : 'segStatus',
	         title : '状态',
	         width : 70,
	         align : 'center'
	        } ] ]
	     });
	 $(window).resize(function() {
		    $('#tblRollSEG').resizeDataGrid(0, 0, 0, 0);
		});
	 }

function doInitChooseTable() {
	 var path = getRootName();
	 var instanceMOID = $("#instanceMOID").val()
		if(instanceMOID == "null" || instanceMOID == null || instanceMOID == ""){
			instanceMOID = -1;
		}
	 $('#tblRollSEG')
	   .datagrid(
	     {
	      iconCls : 'icon-edit',// 图标
	      width : 520,
	      height : 'auto',
	      nowrap : false,
	      striped : true,
	      border : true,
	      singleSelect : false,// 是否单选
	      checkOnSelect : false,
	      selectOnCheck : true,
	      collapsible : false,// 是否可折叠的
	      fit : true,// 自动大小
	      fitColumns:true,
	      url : path + '/monitor/orclDbManage/listOrclRollSEG?instanceMOID='+instanceMOID,
	      idField : 'fldId',
	      pagination : true,// 分页控件
		  rownumbers : true,// 行号	
	      columns : [ [
			{
			    field : 'moID',
			    title : '',
			    width : 100,
			    align : 'center',
				formatter : function(value, row, index) {
					return '<a style="cursor: pointer;" onclick="javascript:sel('+ value+');">选择</a>';
				}
			   },
	        {
	         field : 'segName',
	         title : '段名称',
	         width : 100,
	         align : 'center',
	        },
	        {
	         field : 'dbName',
	         title : '数据库名称',
	         width : 100,
	         align : 'center'
	        },{
		         field : 'ip',
		         title : '服务IP',
		         width : 90,
		         align : 'left'
	        },
	        {
	         field : 'tbsName',
	         title : '所属表空间名称',
	         width : 100,
	         align : 'center'
	        },
	        {
	         field : 'segSize',
	         title : '段大小',
	         width : 70,
	         align : 'center'
	        },
	        {
	         field : 'initialExtent',
	         title : 'Extent初始大小',
	         width : 120,
	         align : 'center'
	        },
	        {
	         field : 'pctIncrease',
	         title : 'Extent缺省增长百分比',
	         width : 150,
	         align : 'center'
	        },
	        {
	         field : 'minExtents',
	         title : 'Extent最小数量',
	         width : 100,
	         align : 'center'
	        },
	        {
	         field : 'maxExtents',
	         title : 'Extent最大数量',
	         width : 100,
	         align : 'center'
	        },
	        {
	         field : 'segStatus',
	         title : '状态',
	         width : 70,
	         align : 'center'
	        } ] ]
	     });
	 $(window).resize(function() {
		    $('#tblRollSEG').resizeDataGrid(0, 0, 0, 0);
		});
 }

/**
 * 刷新表格数据
 */
function reloadTable() {
	$('#tblRollSEG').datagrid('options').queryParams = {
		"dbName" : $("#dbName").val(),
		"segName" : $("#segName").val(),
		"ip" : $("#ip").val()
	};
	reloadTableCommon_1('tblRollSEG');
}

function reloadTableCommon_1(dataGridId) {
	$('#' + dataGridId).datagrid('load');
	$('#' + dataGridId).datagrid('uncheckAll');
}

function doOpenDetail(moID){
	//查看文件详情页面
	var isExistPop = parent.parent.document.getElementById("popWin");
	if(isExistPop != null){
		parent.parent.$('#popWin').window({
	    	title:'回滚段详细信息',
	        width:800,
	        height:350,
	        minimizable:false,
	        maximizable:false,
	        collapsible:false,
	        modal:true,
	        href: getRootName() + '/monitor/orclManage/toOrclRollSEGDetail?moID='+moID
	    });
	}else{
		parent.$('#popView').window({
	    	title:'回滚段详细信息',
	        width:800,
	        height:350,
	        minimizable:false,
	        maximizable:false,
	        collapsible:false,
	        modal:true,
	        href: getRootName() + '/monitor/orclManage/toOrclRollSEGDetail?flag=1&moID='+moID
	    });
	}
//	var url =getRootName() + '/monitor/orclManage/toOrclRollSEGDetail?moID='+moID;
//	window.open(url,"","height=500px,width=800px,left=400,top=200,resizable=no,scrollings=no,status=no,toolbar=no,menubar=no,location=no");
}

function sel(moid){
	if(window.opener) { 
	     fWindowText1 = window.opener.document.getElementById("ipt_oracleRollSegMoId"); 
	     fWindowText1.value = moid; 
	     window.opener.findOracleRollSegInfo();
	     window.close();
	} 
} 