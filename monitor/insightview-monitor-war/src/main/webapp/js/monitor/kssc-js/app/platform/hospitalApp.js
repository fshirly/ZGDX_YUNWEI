define(["base","commonApp"],function(base,common){
	var bodyHeight=$("body").height();
	$(".container").height(bodyHeight-30);
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
			"type":"post",
			"url":$.base+"/store/diskStatus",
			"contentType":"application/json",
			dataType:"json",
			"data": function ( d ) {
				return JSON.stringify({pageNo:d.start/d.length+1,pageSize:d.length,param:{"hospitalId":window.parent.$('#component_2').attr("descr")}});
			},
			dataSrc:function (d) {
				if(d.data==null){
					return []
				}else{
					return d.data
				}
			}
		},
		columns:[
			{"title":"连通性","data": "connectivity","sWidth":"10%"},
			{"title":"流入带宽（%）","data":"inflow","sWidth":"15%"},
			{"title":"流出带宽（%）","data":"outflow","sWidth":"15%"},
			{"title":"丢包数（个）","data":"lose","sWidth":"15%"},
			{"title":"错包数（个）","data":"falut","sWidth":"15%"},
			{"title":"接口速率","data":"rate","sWidth":"15%"},
			{"title":"接口流量","data":"flow","sWidth":"15%"}
		],
		drawCallback:function(setting){


		}
	};
	var run = {
		processing:true,
		serverSide:true,
		searching:false,
		ordering:false,
		lengthChange:false,
		paging:true,
		info:true,
		pagingType: "full_numbers",
		ajax:{
			"type":"post",
			"url":$.base+"/store/diskStatus",
			"contentType":"application/json",
			dataType:"json",
			"data": function ( d ) {
				return JSON.stringify({pageNo:d.start/d.length+1,pageSize:d.length,param:{"hospitalId":window.parent.$('#component_2').attr("descr")}});
			},
			dataSrc:function (d) {
				if(d.data==null){
					return []
				}else{
					return d.data
				}
			}
		},
		columns:[
			{"title":"运行状态","data":"success","sWidth":"13%"},
			{"title":"编解码器名称","data":"codecOwnership","sWidth":"20%"},
			{"title":"新视通账号","data":"newvideoNum","sWidth":"20%"},
			{"title":"IP地址","data":"ip","sWidth":"17%"},
			// {"title":"MAC地址","data":"mac","sWidth":"15%"},
			{"title":"帧数","data":"FrameRate","sWidth":"15%"},
			{"title":"分辨率","data":"resoultion","sWidth":"15%"}
		],
		columnDefs:[
			{
				"render":function (data,type,row,meta) {
					if(row.success==0){
						return "异常"
					}else{
						return "正常"
					}
				},
				"targets":0
			}
		],
		drawCallback:function(setting){
			setGrid($("#code"),code);

		}
	};
	var code = {
		processing:true,
		serverSide:true,
		searching:false,
		ordering:false,
		lengthChange:false,
		paging:true,
		info:true,
		pagingType: "full_numbers",
		ajax:{
			"type":"post",
			"url":$.base+"/store/diskStatus",
			"contentType":"application/json",
			dataType:"json",
			"data": function ( d ) {
				return JSON.stringify({pageNo:d.start/d.length+1,pageSize:d.length,param:{"hospitalId":window.parent.$('#component_2').attr("descr")}});
			},
			dataSrc:function (d) {
				if(d.data==null){
					return []
				}else{
					return d.data
				}
			}
		},
		columns:[
			{"title":"名称","data":"codecOwnership","sWidth":"15%"},
			{"title":"新视通账号","data":"newvideoNum","sWidth":"10%"},
			{"title":"IP","data":"ip","sWidth":"10%"},
			{"title":"所属医院","data":"hospitalName","sWidth":"15%"},
			{"title":"总空间(G)","data":"Capacity","sWidth":"10%"},
			{"title":"已用空间(G)","data":"Free","sWidth":"10%"},
			{"title":"剩余空间(G)","data":"Free","sWidth":"10%"},
			{"title":"使用率","data":"Free","sWidth":"20%"}
		],
		columnDefs:[
			{
				"render":function (data,type,row,meta) {
					if(row.Capacity==null){
						return 0
					}else{
						return row.Capacity
					}
				},
				"targets":4
			},
			{
				"render":function (data,type,row,meta) {
					if(row.remainStorm==null){
						return 0
					}else{
						return Number(row.remainStorm);
					}

				},
				"targets":5
			},
			{
				"render":function (data,type,row,meta) {
					if(row.Free==null){
						return 0
					}else{
						return Number(row.Free);
					}

				},
				"targets":6
			},
			{
				"render":function(data,type,row,meta){
					var rate;
					if(row.Capacity==null){
						rate = 0;
					}
					if(Number(row.Capacity)>0){
						rate = (Number(row.remainStorm)/Number(row.Capacity)*100).toFixed(2);
					}else{
						rate=0
					}
					var width = rate+"%";
					var background="";
					if(rate>0 && rate<=60){
						background="#81b601";
					}else if(rate>60 && rate<=70){
						background="#ec9b00";
					}else if(rate>70 && rate<=80){
						background="#e16602"
					}else{
						background="#ce0000"
					}
					var html = "<div class='progressWrapperDiv'><div class='progressWrapper'>"+
						"<div class='progressBar' style='width:"+width+";background:"+background+"'></div>"
						+"</div><span>"+width+"</span></div>";
					return html;
				},
				"targets":7
			}
		],
		drawCallback:function(setting){
			base.scroll({
				container:$(".container")
			})
		}
	};
	var setGrid = function(table,gridOption){
		grid = base.datatables({
			container:table,
			option:gridOption
		})
	};
	return {
		main:function(){
			setGrid($("#hospital"),run);
		}
	}
})