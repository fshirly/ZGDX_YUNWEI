$(document).ready(function() {
	var moID = $("#moId").val();
	var liInfo = $("#liInfo").val();
	doDataFileInitTables(moID,liInfo);// 数据文件
	$('#tblDataFile').datagrid('hideColumn','moId');
});


// 数据文件
function doDataFileInitTables(moID,liInfo) {
	 var path = getRootName();
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
	      url : path + '/monitor/orclManage/listOrclDataFile?moID='+moID,
	      idField : 'fldId',
	      columns : [ [
			{
			    field : 'moId',
			    title : '数据文件Id',
			    width : 100,
			    align : 'left'
			
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
		         align : 'left',
	        }
	        ,
	        {
		         field : 'tbsName',
		         title : '所属表空间名称',
		         width : 80,
		         align : 'left',
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
		         align : 'center',
		         formatter : function(value, row, index) {
		        		var statusName = "可用";
		        		if(row.dataFileStatus == "AVAILABLE"){
		        			statusName = "可用";
		        		}else if(row.dataFileStatus == "INVALID"){
		        			statusName = "不可用";
		        		}
				          return statusName;
				         }
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
		         title : '是否自动扩展',
		         width : 70,
		         align : 'center',
		         formatter : function(value, row, index) {
		        		var autoName = "启用";
		        		if(row.autoExtensible == "YES"){
		        			autoName = "启用";
		        		}else if(row.autoExtensible == "NO"){
		        			autoName = "未启用";
		        		}
				          return autoName;
				         }
		        }
	        ] ],
			onLoadSuccess: function(){   
	 		//自适应部件大小
	 		window.parent.resizeWidgetByParams(liInfo);
	 	} 
	       
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
	        inline:true,
	        minimizable:false,
	        maximizable:false,
	        collapsible:false,
	        modal:true,
	        href: getRootName() + '/monitor/orclManage/toOrclDataFileDetail?flag=1&moId='+moId
	    });
	}
	

	
//	var url = getRootName()+ '/monitor/orclManage/toOrclDataFileDetail?moId='+moId;
//	window.open(url,"","height=500px,width=800px,left=400,top=200,resizable=no,scrollings=no,status=no,toolbar=no,menubar=no,location=no");
	
	
}
