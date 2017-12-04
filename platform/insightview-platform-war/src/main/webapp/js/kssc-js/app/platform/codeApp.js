define(["base","commonApp"],function(base,common){
	var run = {
		processing:true,
		serverSide:false,
		searching:false,
		ordering:false,
		lengthChange:false,
		paging:true,
		info:true,
		pagingType: "full_numbers",
		ajax:{
			url:"json/codeRun.json"
		},
		columns:[
			{"title":"运行状态","data":"status","sWidth":"13%"},
			{"title":"编解码器名称","data":"name","sWidth":"20%"},
			{"title":"新视通账号","data":"account","sWidth":"20%"},
			{"title":"IP地址","data":"ip","sWidth":"15%"},
			// {"title":"MAC地址","data":"mac","sWidth":"15%"},
			{"title":"帧数","data":"frames","sWidth":"16%"},
			{"title":"分辨率","data":"power","sWidth":"16%"}
		]
	}
	var code = {
			processing:true,
			serverSide:false,
			searching:false,
			ordering:false,
			lengthChange:false,
			paging:true,
			info:true,
			pagingType: "full_numbers",
			ajax:{
				url:"json/monitorCode.json"
			},
			columns:[
				{"title":"名称","data":"name","sWidth":"15%"},
				{"title":"新视通账号","data":"account","sWidth":"10%"},
				{"title":"IP","data":"ip","sWidth":"10%"},
				{"title":"所属医院","data":"hospital","sWidth":"15%"},
				{"title":"总空间","data":"total","sWidth":"10%"},
				{"title":"已用空间","data":"used","sWidth":"10%"},
				{"title":"剩余空间","data":"unUsed","sWidth":"10%"},
				{"title":"使用率","data":"rate","sWidth":"10%"},
			]
		}
	var setGrid = function(table,gridOption){
		grid = base.datatables({
			container:table,
			option:gridOption
		})
	}
	return {
		main:function(){
			setGrid($("#code"),code);
			setGrid($("#run"),run);
		}
	}
})