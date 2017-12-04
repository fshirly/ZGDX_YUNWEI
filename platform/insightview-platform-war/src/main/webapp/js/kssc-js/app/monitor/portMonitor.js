define(["base","commonApp"],function(base,common){
	var bodyHeight=$("body").height();
	$(".container").height(bodyHeight-30);
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
			"url":$.base+"/store/getMedInterfaceInfo",
			"type":"post",
			"contentType":"application/json",
			"dataType":"json",
			"data":function (d) {
				return JSON.stringify({pageNo:d.start/d.length+1,pageSize:d.length,param:{}})
			}
		},
		columns:[
			{"title":"接口状态","data": "status","sWidth":"15%"},
			{"title":"接口名称","data":"name","sWidth":"30%"},
			{"title":"接口URI","data":"url","sWidth":"40%"},
			{"title":"方法","data":"method","sWidth":"15%"}
		],
		columnDefs:[
			{
				"render":function(data,type,row,meta){
					if(row.status==0){
						return "异常"
					}else{
						return "正常"
					}
				},
				targets:0
			}
		],
		drawCallback:function(setting){
			base.scroll({
				container:$(".container")
			});
		}
	};
	var setGrid = function(){
		grid = base.datatables({
			container:$("#tableCloud"),
			option:gridOption
		})
	};
	return {
		main:function(){
			setGrid();
		}
	}
});