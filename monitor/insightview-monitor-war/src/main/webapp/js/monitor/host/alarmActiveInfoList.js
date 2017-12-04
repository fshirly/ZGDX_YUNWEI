$(document).ready(function() {
	var liInfo = $("#liInfo").val();
	doAlarmInitTables(liInfo);// 最新告警
});

// 最新告警
function doAlarmInitTables(liInfo) {
	var moClass=$("#moClass").val();
	var alarmNum=$("#alarmNum").val();
	var flag =$("#flag").val();
	 var path = getRootName();
	 var colu=[[{
         field : 'levelicon',
         title : '告警级别',
         width : 70,
         align : 'center',
         formatter : function(value, row, index) {
	          return '<img src="'
				+ path
				+ '/style/images/levelIcon/'
				+ value
				+ '"/>'+row.alarmLevelName;
	         }
         
        },
        {
         field : 'sourcemoipaddress',
         title : '告警源',
         width : 110,
         align : 'center'
//     	formatter : function(value, row, index) {
//        		if(row.moClassId != 0){
//        			var to = "&quot;" + row.moid
//					+ "&quot;,&quot;" + value
//					+ "&quot;"
//        			return '<a style="cursor: pointer;" onclick="javascript:toShowView('
//					+ to
//					+ ');">'+value+'</a>'; 
//        		}else{
//        			return value;
//        		}
//		}
        },
        {
         field : 'alarmtitle',
         title : '告警标题',
         width : 120,
         align : 'center',
         formatter : function(value, row, index) {
        	if(value && value.length > 16){
        		value2 = value.substring(0,16) + "...";
        		/**if(flag ==2){
        			return value2;
        		}else{
				 return '<a onclick="javascript:doOpenDetail('+ row.alarmid+ ');" title="' + value +  '" >'+value2+"</a>";
        		}*/
				return '<a onclick="javascript:doOpenDetail('+ row.alarmid+ ');" title="' + value +  '" >'+value2+"</a>";
        	}else{
        		/**if(flag ==2){
        			return value;
        		}else{
				return '<a onclick="javascript:doOpenDetail('+ row.alarmid+ ');" >'+value+"</a>";
        		}*/
				return '<a onclick="javascript:doOpenDetail('+ row.alarmid+ ');" >'+value+"</a>";
			}
	       
			}
        },
        {
	         field : 'starttime',
	         title : '发生时间',
	         width : 130,
	         align : 'center',
	         formatter:function(value,row,index){
			if(value!=null && value!="" ){
				return formatDate(new Date(value), "yyyy-MM-dd hh:mm:ss");
			}else{
				return "";
			}	
    	}
        }]];
	 if(flag ==2){
		 colu[0].push( {
	         field : 'statusname',
	         title : '告警操作状态',
	         width : 70,
	         align : 'center'
	        },{
	         field : 'alarmid',
	         title : '处理',
	         width : 70,
	         align : 'center',
	         formatter : function(value, row, index) {
		          return '<a style="cursor: pointer;" title="处理" onclick="javascript:doOpenDetail('+ row.alarmid+ ');">'
		            +'<img src="' + path+ '/style/images/icon/icon_processing.png/"/></a>';
		         }
	        }
		 );
	 }
	 $('#tblAlarmActive')
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
	      url : path + '/monitor/hostManage/topAlarmActiveInfo?moClass='+moClass+'&num='+alarmNum,
	      idField : 'fldId',
	      columns : colu,
	       onDblClickRow:function(rowIndex, rowData){
				doOpenDetail(rowData.alarmid);
			},
			onLoadSuccess: function(){   
				//自适应部件大小
			 /*window.parent.resizeWidgetByParams*/
	    	 resizeWidget(liInfo);
			}   
	       
	     });
	 $(window).resize(function() {
		    $('#tblAlarmActive').resizeDataGrid(0, 0, 0, 0);
		});
	 }

function resizeWidget(liInfoAndIndex){
//	console.log(">>>>>>>>>>1 "+new Date());
	var widgetIndex = liInfoAndIndex.split("|")[0];
	var liId = liInfoAndIndex.split("|")[1];
	var liWidth = liInfoAndIndex.split("|")[2];
	var isExist = $(".datagrid-view2").length;
	if (isExist > 0) {
		var tr_count = $(".datagrid-view2").find(".datagrid-body").find(".datagrid-btable").find("tr").length;
		if (tr_count <= 2) {
			window.parent.gridster[0].resize_widget(window.parent.gridster[0].$widgets.eq(widgetIndex), liWidth, 9);
		} else if (tr_count > 2 && tr_count < 9) {
			window.parent.gridster[0].resize_widget(window.parent.gridster[0].$widgets.eq(widgetIndex),liWidth,9);
		} else if (tr_count >= 9) {
			window.parent.gridster[0].resize_widget(window.parent.gridster[0].$widgets.eq(widgetIndex),liWidth,9);
		} 
	}
//	console.log(">>>>>>>>>>2 "+new Date());
}

function doOpenDetail(alarmid){
	//查看配置项页面
	var isExistPop = parent.parent.document.getElementById("popWin");
	var flag = $("#flag").val();
	if(isExistPop != null){
		parent.parent.$('#popWin').window({
	    	title:'告警详情',
	        width:800,
	        height:550,
	        minimizable:false,
	        maximizable:false,
	        collapsible:false,
	        modal:true,
	        onClose : function(){
		         window.location.reload();
		        },
	        href:getRootName()+'/monitor/hostManage/toAlarmActiveDetail?alarmID='+alarmid+'&flag='+flag
	    });
	}else{
		parent.$('#popView').window({
	    	title:'告警详情',
	        width:800,
	        height:520,
	        minimizable:false,
	        maximizable:false,
	        collapsible:false,
	        modal:true,
	        href:getRootName()+'/monitor/hostManage/toAlarmActiveDetail?flag=1&alarmID='+alarmid
	    });
	}
//	var url = getRootName()+'/monitor/hostManage/toAlarmActiveDetail?alarmID='+alarmid+'&flag=1';
//	window.open(url,"","height=500px,width=800px,left=400,top=200,resizable=no,scrollings=no,status=no,toolbar=no,menubar=no,location=no");
	
}

