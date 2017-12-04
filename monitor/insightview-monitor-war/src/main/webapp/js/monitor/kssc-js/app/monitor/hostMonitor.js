define(["base","commonApp"],function(base,common){
	//获取row的宽度
	var rowWidth = $(".row").width();
	$(".commonTable").width((rowWidth-10)/2);
	//主机cpu使用率
	var cpu = {
		processing:true,
		serverSide:true,
		searching:false,
		ordering:false,
		lengthChange:false,
		paging:false,
		info:false,
		ajax:{
			url:$.base+"/monitor/deviceManager/listCpu?mOClassID=7",
			type:"post",
			contentType:"application/json",
			data:function(d){
				return {pageNo:d.start/d.length+1,pageSize:d.length,param:{}}
			},
			dataSrc:function (d) {
				var data=[];
				data = d.rows;
				return data
			}
		},
		columns:[
			{"title":"设备名称","data": "deviceMOName","sWidth":"30%"},
			{"title":"设备ip","data":"deviceIP","sWidth":"30%"},
			{"title":"使用率","data":"usage","sWidth":"40%"}
		],
		columnDefs:[
			{
				"render":function(data,type,row,meta){
					if(row.usage!=null && row.usage!=""){
						var width = row.usage+"%";
						var background="";
						if(row.usage>0 && row.usage<=60){
							background="#81b601";
						}else if(row.usage>60 && row.usage<=70){
							background="#ec9b00";
						}else if(row.usage>70 && row.usage<=80){
							background="#e16602"
						}else{
							background="#ce0000"
						}
						var html = "<div class='progressWrapperDiv'><div class='progressWrapper'>"+
							"<div class='progressBar' style='width:"+width+";background:"+background+"'></div>"
							+"</div><span>"+width+"</span></div>";
						return html
					}

				},
				"targets":2
			}
		],
		drawCallback:function(setting){
			setGrid($("#host"),host);
		}
	};
	//主机硬盘使用率
	var host = {
		processing:true,
		serverSide:true,
		searching:false,
		ordering:false,
		lengthChange:false,
		paging:false,
		info:false,
		ajax:{
			url:$.base+"/monitor/deviceManager/listDisc?mOClassID=7",
			type:"post",
			contentType:"application/json",
			data:function(d){
				return {pageNo:d.start/d.length+1,pageSize:d.length,param:{}}
			},
			dataSrc:function (d) {
				var data=[];
				data = d.rows;
				return data
			}
		},
		columns:[
			{"title":"设备名称","data": "deviceMOName","sWidth":"30%"},
			{"title":"设备ip","data":"deviceIP","sWidth":"30%"},
			{"title":"使用率","data":"usage","sWidth":"40%"}
		],
		columnDefs:[
			{
				"render":function(data,type,row,meta){
					if(row.usage!=null && row.usage!=""){
						var width = row.usage+"%";
						var background="";
						if(row.usage>0 && row.usage<=60){
							background="#81b601";
						}else if(row.usage>60 && row.usage<=70){
							background="#ec9b00";
						}else if(row.usage>70 && row.usage<=80){
							background="#e16602"
						}else{
							background="#ce0000"
						}
						var html = "<div class='progressWrapperDiv'><div class='progressWrapper'>"+
							"<div class='progressBar' style='width:"+width+";background:"+background+"'></div>"
							+"</div><span>"+width+"</span></div>";
						return html
					}else{
						return ""
					}

				},
				"targets":2
			}
		],
		drawCallback:function(setting){
			//setGrid($("#hardDisk"),io);
            setGrid($("#memory"),memory);
		}
	}
	//主机硬盘IO
	var io = {
		processing:true,
		serverSide:false,
		searching:false,
		ordering:false,
		lengthChange:false,
		paging:false,
		info:false,
		ajax:{
			url:$.base+"/js/kssc-js/json/hostMonitor.json"
		},
		columns:[
			{"title":"设备名称","data": "name","sWidth":"30%"},
			{"title":"设备ip","data":"ip","sWidth":"30%"},
			{"title":"使用率","data":"rate","sWidth":"40%"}
		],
		columnDefs:[
			{
				"render":function(data,type,row,meta){
					var width = row.rate+"%";
					var background="";
					if(row.rate>0 && row.rate<=60){
						background="#81b601";
					}else if(row.rate>60 && row.rate<=70){
						background="#ec9b00";
					}else if(row.rate>70 && row.rate<=80){
						background="#e16602"
					}else{
						background="#ce0000"
					}
					var html = "<div class='progressWrapperDiv'><div class='progressWrapper'>"+
						"<div class='progressBar' style='width:"+width+";background:"+background+"'></div>"
						+"</div><span>"+width+"</span></div>";
					return html
				},
				"targets":2
			}
		],
		drawCallback:function(setting){
			//setGrid($("#memory"),memory);
		}
	}
	//主机内存使用率
	var memory = {
		processing:true,
		serverSide:true,
		searching:false,
		ordering:false,
		lengthChange:false,
		paging:false,
		info:false,
		ajax:{
			url:$.base+"/monitor/deviceManager/listMemory?mOClassID=7",
			type:"post",
			contentType:"application/json",
			data:function(d){
				return {pageNo:d.start/d.length+1,pageSize:d.length,param:{}}
			},
			dataSrc:function (d) {
				var data=[];
				data = d.rows;
				return data
			}
		},
		columns:[
			{"title":"设备名称","data": "deviceMOName","sWidth":"30%"},
			{"title":"设备ip","data":"deviceIP","sWidth":"30%"},
			{"title":"使用率","data":"usage","sWidth":"40%"}
		],
		columnDefs:[
			{
				"render":function(data,type,row,meta){
					if(row.usage!=null && row.usage!=""){
						var width = row.usage+"%";
						var background="";
						if(row.usage>0 && row.usage<=60){
							background="#81b601";
						}else if(row.usage>60 && row.usage<=70){
							background="#ec9b00";
						}else if(row.usage>70 && row.usage<=80){
							background="#e16602"
						}else{
							background="#ce0000"
						}
						var html = "<div class='progressWrapperDiv'><div class='progressWrapper'>"+
							"<div class='progressBar' style='width:"+width+";background:"+background+"'></div>"
							+"</div><span>"+width+"</span></div>";
						return html
					}

				},
				"targets":2
			}
		],
		drawCallback:function(setting){
			setGrid($("#port"),port);
            var bodyHeight=$("body").height();
            $(".allTable").height(bodyHeight-30);
            base.scroll({
                container:$(".allTable")
            })
		}
	}
	//主机接口流量
	var port = {
		processing:true,
		serverSide:false,
		searching:false,
		ordering:false,
		lengthChange:false,
		paging:true,
		info:true,
		pagingType: "full_numbers",
		ajax:{
			url:$.base+"/js/kssc-js/json/hostMonitor.json"
		},
		columns:[
			{"title":"设备名称","data": "name","sWidth":"30%"},
			{"title":"设备ip","data":"ip","sWidth":"30%"},
			{"title":"使用率","data":"rate","sWidth":"40%"}
		],
		columnDefs:[
			{
				"render":function(data,type,row,meta){
					var width = row.rate+"%";
					var background="";
					if(row.rate>0 && row.rate<=60){
						background="#81b601";
					}else if(row.rate>60 && row.rate<=70){
						background="#ec9b00";
					}else if(row.rate>70 && row.rate<=80){
						background="#e16602"
					}else{
						background="#ce0000"
					}
					var html = "<div class='progressWrapperDiv'><div class='progressWrapper'>"+
						"<div class='progressBar' style='width:"+width+";background:"+background+"'></div>"
						+"</div><span>"+width+"</span></div>";
					return html

				},
				"targets":2
			}
		],
		drawCallback:function(setting){
			// var bodyHeight=$("body").height();
			// $(".allTable").height(bodyHeight-30);
			// base.scroll({
			// 	container:$(".allTable")
			// })
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
			setGrid($("#cpu"),cpu);
		}
	}
});