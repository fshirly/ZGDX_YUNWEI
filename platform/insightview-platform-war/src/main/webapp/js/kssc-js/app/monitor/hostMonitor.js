define(["base","commonApp"],function(base,common){
	//获取row的宽度
	var rowWidth = $(".row").width();
	$(".commonTable").width((rowWidth-10)/2);
	//主机cpu使用率
	var cpu = {
		processing:true,
		serverSide:false,
		searching:false,
		ordering:false,
		lengthChange:false,
		paging:false,
		info:false,
		ajax:{
			url:"json/hostMonitor.json"
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
					if(row.rate>0 && row.rate<60){
						background="#68e879";
					}else if(row.rate>=60 && row.rate<80){
						background="#f9ef6f";
					}else{
						background="#ff0000"
					}
					var html = "<div class='progressWrapper'>"+
						"<div class='progressBar' style='width:"+width+";background:"+background+"'>"+width+"</div>"
						+"</div>";
					return html;
				},
				"targets":2
			}
		],
		drawCallback:function(setting){
			setGrid($("#host"),host);
		}
	}
	//主机硬盘使用率
	var host = {
		processing:true,
		serverSide:false,
		searching:false,
		ordering:false,
		lengthChange:false,
		paging:false,
		info:false,
		ajax:{
			url:"json/hostMonitor.json"
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
					if(row.rate>0 && row.rate<60){
						background="#68e879";
					}else if(row.rate>=60 && row.rate<80){
						background="#f9ef6f";
					}else{
						background="#ff0000"
					}
					var html = "<div class='progressWrapper'>"+
						"<div class='progressBar' style='width:"+width+";background:"+background+"'>"+width+"</div>"
						+"</div>"
					return html
				},
				"targets":2
			}
		],
		drawCallback:function(setting){
			setGrid($("#hardDisk"),io);
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
			url:"json/hostMonitor.json"
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
					if(row.rate>0 && row.rate<60){
						background="#68e879";
					}else if(row.rate>=60 && row.rate<80){
						background="#f9ef6f";
					}else{
						background="#ff0000"
					}
					var html = "<div class='progressWrapper'>"+
						"<div class='progressBar' style='width:"+width+";background:"+background+"'>"+width+"</div>"
						+"</div>"
					return html
				},
				"targets":2
			}
		],
		drawCallback:function(setting){
			setGrid($("#memory"),memory);
		}
	}
	//主机内存使用率
	var memory = {
		processing:true,
		serverSide:false,
		searching:false,
		ordering:false,
		lengthChange:false,
		paging:false,
		info:false,
		ajax:{
			url:"json/hostMonitor.json"
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
					if(row.rate>0 && row.rate<60){
						background="#68e879";
					}else if(row.rate>=60 && row.rate<80){
						background="#f9ef6f";
					}else{
						background="#ff0000"
					}
					var html = "<div class='progressWrapper'>"+
						"<div class='progressBar' style='width:"+width+";background:"+background+"'>"+width+"</div>"
						+"</div>"
					return html
				},
				"targets":2
			}
		],
		drawCallback:function(setting){
			setGrid($("#port"),port);
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
			url:"json/hostMonitor.json"
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
					if(row.rate>0 && row.rate<60){
						background="#68e879";
					}else if(row.rate>=60 && row.rate<80){
						background="#f9ef6f";
					}else{
						background="#ff0000"
					}
					var html = "<div class='progressWrapper'>"+
						"<div class='progressBar' style='width:"+width+";background:"+background+"'>"+width+"</div>"
						+"</div>";
					return html
				},
				"targets":2
			}
		],
		drawCallback:function(setting){
			var bodyHeight=$("body").height();
			$(".allTable").height(bodyHeight-30);
			base.scroll({
				container:$(".allTable")
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
			setGrid($("#cpu"),cpu);
		}
	}
});