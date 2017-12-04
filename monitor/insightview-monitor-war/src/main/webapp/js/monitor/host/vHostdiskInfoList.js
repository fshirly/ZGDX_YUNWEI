$(document).ready(function() {
	var liInfo = $("#liInfo").val();
	  doHostDiskInitTables(liInfo);// 主机硬盘

});
// TOP10主机 硬盘使用率
function doHostDiskInitTables(liInfo) {
	 var path = getRootName();
	 $('#tblHostTopDisk')
	   .datagrid(
	     {
	      iconCls : 'icon-edit',// 图标
	      width : 550,
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
	      url : path + '/monitor/hostManage/topPerfVhostDiskInfo',
	      idField : 'fldId',
	      columns : [ [
	        {
	         field : 'moname',
	         title : '设备名称',
	         width : 100,
	         align : 'center',
	        },
	        {
	         field : 'deviceip',
	         title : '设备IP',
	         width : 120,
	         align : 'center',
				formatter : function(value, row, index) {
	        		if(row.moClassId != 0){
	        			var to = "&quot;" + row.moid
						+ "&quot;,&quot;" + value
						+ "&quot;"
	        			return '<a style="cursor: pointer;" onclick="javascript:toShowView('
						+ to
						+ ');">'+value+'</a>'; 
	        		}else{
	        			return value;
	        		}
					
				}
	        },
	        {
	         field : 'rawdescr',
	         title : '硬盘分区',
	         width : 220,
	         align : 'center',
	        },
	        {
	         field : 'perfvalue',
	         title : 'I/O使用率(KBps)',
	         width : 180,
	         align : 'center'
	        }
	        ] ],
			onLoadSuccess: function(){   
				//自适应部件大小
	    	 window.parent.resizeWidgetByParams(liInfo);
			}   
	     });
	 $(window).resize(function() {
		    $('#tblHostTopDisk').resizeDataGrid(0, 0, 0, 0);
		});
	 }

