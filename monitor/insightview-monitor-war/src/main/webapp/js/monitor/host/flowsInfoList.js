$(document).ready(function() {
	var moClass = $("#moClass").val();
	var liInfo = $("#liInfo").val();
	doFlowsInitTables(moClass,liInfo);// 主机硬盘

});
//接口流量
function doFlowsInitTables(moClass,liInfo) {
	 var path = getRootName();
	 	var num = $("#num").val();
		var topOrder = $("#topOrder").val();
		var timeBegin = $("#timeBegin").val();
		var timeEnd = $("#timeEnd").val();
	 $('#tblTopFlows')
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
	      url : path + '/monitor/hostManage/topPerfFlowsInfo?moClass='+moClass,
	      queryParams : {
	    	 	"num" : num,
				"topOrder" : topOrder,
				"timeBegin" : timeBegin,
				"timeEnd" : timeEnd,
			},
	      idField : 'fldId',
	      columns : [ [
	        {
	         field : 'moname',
	         title : '设备名称',
	         width : 200,
	         align : 'center',
	         formatter : function(value, row, index) {
	        	 if(row.moalias!=null && row.moalias!='null' && row.moalias.toString().length>0){
	        		 return row.moalias;
	        	 }else{
	        		 return value;
	        	 }
	         }
	        },
	        {
	         field : 'deviceip',
	         title : '设备IP',
	         width : 90,
	         align : 'center'
	        },
	        {
	         field : 'ifname',
	         title : '接口名称',
	         width : 90,
	         align : 'center',
	         // return "<a href='/fable/monitor/hostManage/toShowIfDetail?moID="+ row.moid+"&IfMOID="+row.ifMOID+"'>"+value+"</a>";
	         formatter : function(value, row, index) {
	        		if(row.moclassid != 0 && row.moclassid!=9){
	        			var to = "&quot;" + row.moid
						+ "&quot;,&quot;" + row.ifMOID
						+ "&quot;,&quot;" + row.deviceip
						+ "&quot;,&quot;" + value
						+ "&quot;"
	        			return '<a style="cursor: pointer;" onclick="javascript:toShowIfView('
						+ to
						+ ');">'+value+'</a>'; 
	        		}else{
	        			return value;
	        		}
					
				}	        
	        },
	        {
		         field : 'inflows',
		         title : '流入流量',
		         width : 60,
		         align : 'center'
		        },
		        {
			         field : 'outflows',
			         title : '流出流量',
			         width : 60,
			         align : 'center'
			        }
	       
	        ] ],
			onLoadSuccess: function(){   
				//自适应部件大小
	    	 window.parent.resizeWidgetByParams(liInfo);
			}   
	       
	     });
	 $(window).resize(function() {
		    $('#tblTopFlows').resizeDataGrid(0, 0, 0, 0);
		});
	 }