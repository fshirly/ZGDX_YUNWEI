define(["base","commonApp"],function(base,common){
	var bodyHeight=$("body").height();
	var containerHeight = $(".container").height(bodyHeight-30);
	var gridOption = {
		processing:true,
		serverSide:true,
		searching:false,
		ordering:false,
		lengthChange:false,
		paging:true,
		info:true,
		pagingType: "full_numbers",
		ajax:{
			url:$.base+"/monitor/alarmMgr/alarmPanel/loadActiveAlarmListNew",
			type:"post",
			contentType:"application/json",
			dataType:"json",
			data:function (d) {
				return JSON.stringify({pageNo:d.start/d.length+1,pageSize:d.length,
					param:{"userId":"","viewCfgID":""}})
			}
		},
		columns:[
			{"title":"告警序号","data": "number","sWidth":"10%"},
			{"title":"告警时间","data":"time","sWidth":"18%"},
			{"title":"告警级别","data":"level","sWidth":"16%"},
			{"title":"告警标题","data":"title","sWidth":"20%"},
			{"title":"告警类型","data":"type","sWidth":"16%"},
			{"title":"告警源名称","data":"ename","sWidth":"20%"}
		],
		columnDefs:[
			{
				"render":function(data,type,row,meta){
					switch(row.level){
						case "3":return "紧急";break;
						case "2":return "严重";break;
						case "1":return "一般";break;
						case "0":return "正常";break;
					}
				},
				targets:2
			}
		],
		drawCallback:function(setting){
			base.scroll({
				container:$(".container")
			})
		}
	}
	var setGrid = function(){
		grid = base.datatables({
			container:$("#monitor"),
			option:gridOption
		})
	} 
	return {
		main:function(){
			setGrid();
		}
	}
})