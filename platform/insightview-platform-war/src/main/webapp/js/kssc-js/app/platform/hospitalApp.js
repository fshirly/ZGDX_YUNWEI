define(["base","commonApp"],function(base,common){
	var hospital = {
		processing:true,
		serverSide:false,
		searching:false,
		ordering:false,
		lengthChange:false,
		paging:true,
		info:true,
		pagingType: "full_numbers",
		ajax:{
			url:"json/line.json"
		},
		columns:[
			{"title":"连通性","data": "connectivity","sWidth":"10%"},
			{"title":"流入带宽（%）","data":"inflow","sWidth":"15%"},
			{"title":"流出带宽（%）","data":"outflow","sWidth":"15%"},
			{"title":"丢包数（个）","data":"lose","sWidth":"15%"},
			{"title":"错包数（个）","data":"falut","sWidth":"15%"},
			{"title":"接口速率","data":"rate","sWidth":"15%"},
			{"title":"接口流量","data":"flow","sWidth":"15%"},
		],
		drawCallback:function(setting){
			
		}
	}
	var setGrid = function(){
		grid = base.datatables({
			container:$("#hospital"),
			option:hospital
		})
	}
	return {
		main:function(){
			setGrid();
		}
	}
})