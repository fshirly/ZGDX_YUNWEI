$(document).ready(function() {
	var flag=$('#flag').val();
	var moID = $("#moId").val();
	if(flag == "null" || flag =="" || flag ==null){
		doDataFileInitTables(moID);// 数据文件
		$('#tblDataFile').datagrid('hideColumn','moId');
	}else{
		doInitChooseTable(moID);
	}
	
});


// 数据文件
function doDataFileInitTables(moID) {
	 var path = getRootName();
	 var instanceMOID = $("#instanceMOID").val()
	if(instanceMOID == "null" || instanceMOID == null || instanceMOID == ""){
		instanceMOID = -1;
	}
	 var alarmNum=$("#alarmNum").val();
	 $('#tblDataFile')
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
	      pagination : true,// 分页控件
		  rownumbers : true,// 行号	
	      url : path + '/monitor/orclDbManage/listOrclDataFile?instanceMOID='+instanceMOID,
	      idField : 'fldId',
	      columns : [ [
			{
			    field : 'moId',
			    title : '',
			    width : 100,
			    align : 'left',
				formatter : function(value, row, index) {
					return '<a style="cursor: pointer;" onclick="javascript:sel('+ value+');">选择</a>';
				}
			
			}
			,
	        {
		         field : 'dataFileName',
		         title : '数据文件名称',
		         width : 240,
		         align : 'left',
		      	 formatter : function(value, row, index) {
			       	return '<a onclick="javascript:doOpenDetail('+ row.moId+ ');">'+value+"</a>";
			       }	 
	        }
	        ,
	        {
		         field : 'dbName',
		         title : '数据库名称',
		         width : 80,
		         align : 'left'
	        }
	        , 
	        {
		         field : 'ip',
		         title : '服务IP',
		         width : 90,
		         sortable:true,
		         align : 'left'
	        },
	        {
		         field : 'tbsName',
		         title : '所属表空间名称',
		         width : 80,
		         align : 'left'
	        }
	        ,
	        {
		         field : 'dataFileId',
		         title : '数据文件ID',
		         width : 120,
		         align : 'center'
	      
	        },
	        {
		         field : 'dataFileBytes',
		         title : '文件大小',
		         width : 90,
		         align : 'center'
	        }
	        ,
	        {
		         field : 'dataFileBlocks',
		         title : 'Block数',
		         width : 90,
		         align : 'center'
	        },
	        {
		         field : 'dataFileStatus',
		         title : '状态',
		         width : 100,
		         align : 'center'
		        }
	        ,
	        {
		         field : 'relativeID',
		         title : '相关文件ID',
		         width : 70,
		         align : 'center'
		        }
	        ,
	        {
		         field : 'autoExtensible',
		         title : '自动扩展标志',
		         width : 70,
		         align : 'center'
		        }
	        ] ]
	       
	     });
	 $(window).resize(function() {
		    $('#tblDataFile').resizeDataGrid(0, 0, 0, 0);
		});
	 }

//数据文件
function doInitChooseTable(moID) {
	 var path = getRootName();
	 var instanceMOID = $("#instanceMOID").val()
	if(instanceMOID == "null" || instanceMOID == null || instanceMOID == ""){
		instanceMOID = -1;
	}
	 var alarmNum=$("#alarmNum").val();
	 $('#tblDataFile')
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
	      pagination : true,// 分页控件
		  rownumbers : true,// 行号	
	      url : path + '/monitor/orclDbManage/listOrclDataFile?instanceMOID='+instanceMOID,
	      idField : 'fldId',
	      columns : [ [
			{
			    field : 'moId',
			    title : '',
			    width : 100,
			    align : 'left',
				formatter : function(value, row, index) {
					return '<a style="cursor: pointer;" onclick="javascript:sel('+ value+');">选择</a>';
				}
			
			}
			,
	        {
		         field : 'dataFileName',
		         title : '数据文件名称',
		         width : 240,
		         align : 'left',
	        }
	        ,
	        {
		         field : 'dbName',
		         title : '数据库名称',
		         width : 80,
		         align : 'left'
	        }
	        , 
	        {
		         field : 'ip',
		         title : '服务IP',
		         width : 90,
		         align : 'left'
	        },
	        {
		         field : 'tbsName',
		         title : '所属表空间名称',
		         width : 80,
		         align : 'left'
	        }
	        ,
	        {
		         field : 'dataFileId',
		         title : '数据文件ID',
		         width : 120,
		         align : 'center'
	      
	        },
	        {
		         field : 'dataFileBytes',
		         title : '文件大小',
		         width : 90,
		         align : 'center'
	        }
	        ,
	        {
		         field : 'dataFileBlocks',
		         title : 'Block数',
		         width : 90,
		         align : 'center'
	        },
	        {
		         field : 'dataFileStatus',
		         title : '状态',
		         width : 100,
		         align : 'center'
		        }
	        ,
	        {
		         field : 'relativeID',
		         title : '相关文件ID',
		         width : 70,
		         align : 'center'
		        }
	        ,
	        {
		         field : 'autoExtensible',
		         title : '自动扩展标志',
		         width : 70,
		         align : 'center'
		        }
	        ] ]
	       
	     });
	 $(window).resize(function() {
		    $('#tblDataFile').resizeDataGrid(0, 0, 0, 0);
		});
 }


function doOpenDetail(moId){
	//查看文件详情页面
	var isExistPop = parent.parent.document.getElementById("popWin");
	if(isExistPop != null){
		parent.parent.$('#popWin').window({
	    	title:'数据文件详细信息',
	        width:800,
	        height:500,
	        minimizable:false,
	        maximizable:false,
	        collapsible:false,
	        modal:true,
	        href: getRootName() + '/monitor/orclManage/toOrclDataFileDetail?moId='+moId
	    });
	}else{
		parent.$('#popView').window({
	    	title:'数据文件详细信息',
	        width:800,
	        height:500,
	        minimizable:false,
	        maximizable:false,
	        collapsible:false,
	        modal:true,
	        href: getRootName() + '/monitor/orclManage/toOrclDataFileDetail?flag=1&moId='+moId
	    });
	}
}

/**
 * 刷新表格数据
 */
function reloadTable() {
	$('#tblDataFile').datagrid('options').queryParams = {
		"dbName" : $("#dbName").val(),
		"dataFileName" : $("#dataFileName").val(),
		"ip" : $("#ip").val()
	};
	reloadTableCommon_1('tblDataFile');
}

function reloadTableCommon_1(dataGridId) {
	$('#' + dataGridId).datagrid('load');
	$('#' + dataGridId).datagrid('uncheckAll');
}

function sel(moid){
	if(window.opener) { 
	     fWindowText1 = window.opener.document.getElementById("ipt_oracleDataFileMoId"); 
	     fWindowText1.value = moid; 
	     window.opener.findOracleDataFileInfo();
	     window.close();
	} 
} 