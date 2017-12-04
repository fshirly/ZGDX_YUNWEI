$(document).ready(function() {
	var liInfo = $("#liInfo").val();
	doVHostInitTables(liInfo);// 最新告警

});


// 宿主机下的虚拟机列表
function doVHostInitTables(liInfo) {
	 var path = getRootName();
	 $('#tblVHost')
	   .datagrid(
	     {
	      iconCls : 'icon-edit',// 图标
	      width : 530,
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
	      url : path + '/monitor/hostManage/findVHostForVMInfo',
	      idField : 'fldId',
	      columns : [ [
	        {
	         field : 'operstatus',
	         title : '虚拟机状态',
	         width : 50,
	         align : 'center',
	         formatter:function(value,row){
					return '<img title="' + row.operaTip + '" src="' + path
							+ '/style/images/levelIcon/' + value + '"/>';
				}
	        }
	        ,
	        {
	         field : 'os',
	         title : '虚拟机类型',
	         width : 150,
	         align : 'left',
	        }
	        ,
	        {
	         field : 'moname',
	         title : '虚拟机名称',
	         width : 150,
	         align : 'left',
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
	         field : 'deviceip',
	         title : '虚拟机IP',
	         width : 100,
	         align : 'center',
	        },
	        {
		         field : 'cpunumber',
		         title : 'CPU数量',
		         width : 50,
		         align : 'center'
		        }
	        ,
	        {
		         field : 'memory',
		         title : '内存大小',
		         width : 100,
		         align : 'center'
		        }
	        ] ],
			onLoadSuccess: function(){   
	 		//自适应部件大小
	    	 window.parent.resizeWidgetByParams(liInfo);
	 	} 
	       
	     });
		 $(window).resize(function() {
			    $('#tblVHost').resizeDataGrid(0, 0, 0, 0);
			});
	 }
